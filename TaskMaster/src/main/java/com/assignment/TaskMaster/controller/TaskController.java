package com.assignment.TaskMaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.TaskMaster.dto.AssignTaskRequest;
import com.assignment.TaskMaster.dto.CreateTaskRequest;
import com.assignment.TaskMaster.dto.TaskResponse;
import com.assignment.TaskMaster.dto.UpdateStatusRequest;
import com.assignment.TaskMaster.dto.UpdateTaskRequest;
import com.assignment.TaskMaster.entity.TaskStatus;
import com.assignment.TaskMaster.service.TaskService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/tasks")
public class TaskController 
{
   
     private final TaskService taskService;

    // Constructor
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 1. Create Task
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request) {

        TaskResponse response = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // 2. Get Task By ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                taskService.getTaskById(id));
    }

    // 3. Get Tasks Assigned To User
    @GetMapping
    public ResponseEntity<List<TaskResponse>> listTasks(

            @RequestParam Long assignedTo,

            @RequestParam(required = false)
            TaskStatus status,

            @RequestParam(defaultValue = "dueDate")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) {

        List<TaskResponse> tasks =
                taskService.getTasksAssignedToUser(
                        assignedTo,
                        status,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(tasks);
    }

    // 4. Search Tasks
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(

            @RequestParam Long assignedTo,

            @RequestParam String keyword
    ) {

        return ResponseEntity.ok(
                taskService.searchTasks(assignedTo, keyword));
    }

    // 5. Update Task
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(

            @PathVariable Long id,

            @Valid @RequestBody UpdateTaskRequest request
    ) {

        return ResponseEntity.ok(
                taskService.updateTask(id, request));
    }

    // 6. Update Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(

            @PathVariable Long id,

            @Valid @RequestBody UpdateStatusRequest request
    ) {

        return ResponseEntity.ok(
                taskService.updateStatus(id, request));
    }

    // 7. Assign Task
    @PatchMapping("/{id}/assign")
    public ResponseEntity<TaskResponse> assignTask(

            @PathVariable Long id,

            @Valid @RequestBody AssignTaskRequest request
    ) {

        return ResponseEntity.ok(
                taskService.assignTask(id, request));
    }

    // 8. Delete Task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }

    // 9. Upload Attachment
    @PostMapping("/{id}/attachments")
    public ResponseEntity<TaskResponse> uploadAttachment(

            @PathVariable Long id,

            @RequestParam("file") MultipartFile file
    ) {

        return ResponseEntity.ok(
                taskService.addAttachment(id, file));
    }
    
}
