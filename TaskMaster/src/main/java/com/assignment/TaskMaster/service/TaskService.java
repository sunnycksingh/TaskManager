package com.assignment.TaskMaster.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.TaskMaster.dto.AssignTaskRequest;
import com.assignment.TaskMaster.dto.CreateTaskRequest;
import com.assignment.TaskMaster.dto.TaskResponse;
import com.assignment.TaskMaster.dto.UpdateStatusRequest;
import com.assignment.TaskMaster.dto.UpdateTaskRequest;
import com.assignment.TaskMaster.entity.Task;
import com.assignment.TaskMaster.entity.TaskStatus;
import com.assignment.TaskMaster.entity.Team;
import com.assignment.TaskMaster.entity.User;
import com.assignment.TaskMaster.exception.TaskNotFoundException;
import com.assignment.TaskMaster.exception.TeamNotFoundException;
import com.assignment.TaskMaster.exception.UserNotFoundException;
import com.assignment.TaskMaster.exception.UserNotInTeamException;
import com.assignment.TaskMaster.repository.CommentRepository;
import com.assignment.TaskMaster.repository.TaskRepository;
import com.assignment.TaskMaster.repository.TeamRepository;
import com.assignment.TaskMaster.repository.TeammateRepository;
import com.assignment.TaskMaster.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class TaskService 
{
   
     private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeammateRepository teammateRepository;
    private final CommentRepository commentRepository;

    @Value ("${app.upload-dir}")
    private String uploadDir;

    // Constructor injection
    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository,
            TeamRepository teamRepository,
            TeammateRepository teammateRepository,
            CommentRepository commentRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teammateRepository = teammateRepository;
        this.commentRepository = commentRepository;
    }

    // 1. Create Task
    public TaskResponse createTask(CreateTaskRequest request) {

        // Find creator
        User creator = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() ->
                        new UserNotFoundException("Creator not found"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setCreatedBy(creator);
        task.setStatus(TaskStatus.OPEN);

        // Assign team if provided
        if (request.getTeamId() != null) {

            Team team = teamRepository.findById(request.getTeamId())
                    .orElseThrow(() ->
                            new TeamNotFoundException("Team not found"));

            // Creator must belong to the team
            if (!teammateRepository.existsByTeamIdAndUserId(
                    team.getTeamid(), creator.getUserid())) {

                throw new UserNotInTeamException(
                        "Creator is not a member of this team");
            }

            task.setTeam(team);
        }

        // Assign user if provided
        if (request.getAssignedTo() != null) {

            User assignee = userRepository.findById(request.getAssignedTo())
                    .orElseThrow(() ->
                            new UserNotFoundException("Assignee not found"));

            // If task belongs to a team, assignee must be its member
            if (task.getTeam() != null) {

                if (!teammateRepository.existsByTeamIdAndUserId(
                        task.getTeam().getTeamid(),
                        assignee.getUserid())) {

                    throw new UserNotInTeamException(
                            "Assignee is not a member of this team");
                }
            }

            task.setAssignedTo(assignee);
        }

        Task saved = taskRepository.save(task);

        return mapToResponse(saved);
    }

    // 2. Get Task By ID
    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        return mapToResponse(task);
    }

    // 3. Get Tasks Assigned To User + Sorting + Status Filter
    public List<TaskResponse> getTasksAssignedToUser(
            Long userId,
            TaskStatus status,
            String sortBy,
            String direction
    ) {

        // Check user exists
        userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        // Allowed sorting fields
        Set<String> allowedSortFields = Set.of(
                "dueDate",
                "createdAt",
                "title",
                "status"
        );

        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "dueDate";
        }

        if (!allowedSortFields.contains(sortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sorting field: " + sortBy);
        }

        if (direction == null || direction.isBlank()) {
            direction = "asc";
        }

        Sort.Direction sortDirection;

        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Direction must be asc or desc");
        }

        Sort sort = Sort.by(sortDirection, sortBy);

        List<Task> tasks;

        if (status == null) {

            tasks = taskRepository.findByAssignedToId(userId, sort);

        } else {

            tasks = taskRepository.findByAssignedToIdAndStatus(
                    userId, status, sort);
        }

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 4. Search Tasks
    public List<TaskResponse> searchTasks(
            Long userId,
            String keyword
    ) {

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        List<Task> tasks = taskRepository.search(userId, keyword);

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 5. Update Task
    public TaskResponse updateTask(
            Long id,
            UpdateTaskRequest request
    ) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());

        Task saved = taskRepository.save(task);

        return mapToResponse(saved);
    }

    // 6. Update Task Status
    public TaskResponse updateStatus(
            Long id,
            UpdateStatusRequest request
    ) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        task.setStatus(request.getStatus());

        Task saved = taskRepository.save(task);

        return mapToResponse(saved);
    }

    // 7. Assign Task
    public TaskResponse assignTask(
            Long id,
            AssignTaskRequest request
    ) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() ->
                        new UserNotFoundException("Assignee not found"));

        // Check team membership
        if (task.getTeam() != null) {

            if (!teammateRepository.existsByTeamIdAndUserId(
                    task.getTeam().getTeamid(),
                    assignee.getUserid())) {

                throw new UserNotInTeamException(
                        "Assignee is not a member of this team");
            }
        }

        task.setAssignedTo(assignee);

        Task saved = taskRepository.save(task);

        return mapToResponse(saved);
    }

    // 8. Delete Task
    @Transactional 
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        // Delete comments first
        commentRepository.deleteByTaskId(id);

        // Delete task
        taskRepository.delete(task);
    }

    // 9. Upload Attachment
    public TaskResponse addAttachment(
            Long id,
            MultipartFile file
    ) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Uploaded file cannot be empty");
        }

        try {

            // Get original filename safely
            String originalName = file.getOriginalFilename();

            if (originalName == null || originalName.isBlank()) {
                originalName = "attachment";
            }

            // Remove any directory path and unsafe characters
            String safeName = Paths.get(originalName)
                    .getFileName()
                    .toString()
                    .replaceAll("[^a-zA-Z0-9._-]", "_");

            // Generate unique filename
            String newName = UUID.randomUUID()
                    + "_" + safeName;

            // Create upload directory if it does not exist
            Path uploadPath = Paths.get(uploadDir)
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(uploadPath);

            // Save file
            Path filePath = uploadPath.resolve(newName)
                    .normalize();

            if (!filePath.startsWith(uploadPath)) {
                throw new IllegalArgumentException("Invalid filename");
            }

            file.transferTo(filePath.toFile());

            // Append filename to attachments
            if (task.getAttachments() == null
                    || task.getAttachments().isBlank()) {

                task.setAttachments(newName);

            } else {

                task.setAttachments(
                        task.getAttachments() + "," + newName);
            }

            Task saved = taskRepository.save(task);

            return mapToResponse(saved);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to upload attachment", e);
        }
    }

    // 10. Helper Method: Map Entity To DTO
    private TaskResponse mapToResponse(Task task) {

        Long assignedToId = null;
        String assignedToUsername = null;

        if (task.getAssignedTo() != null) {

            assignedToId = task.getAssignedTo().getUserid();

            assignedToUsername =
                    task.getAssignedTo().getUsername();
        }

        Long teamId = null;

        if (task.getTeam() != null) {
            teamId = task.getTeam().getTeamid();
        }

        Long createdById = task.getCreatedBy().getUserid();

        List<String> attachmentList;

        if (task.getAttachments() == null
                || task.getAttachments().isBlank()) {

            attachmentList = List.of();

        } else {

            attachmentList = Arrays.stream(
                    task.getAttachments().split(","))
                    .toList();
        }

        return new TaskResponse(
                task.getTaskId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                assignedToId,
                assignedToUsername,
                teamId,
                createdById,
                attachmentList,
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
    
}
