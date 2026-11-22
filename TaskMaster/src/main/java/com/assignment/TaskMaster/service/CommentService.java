package com.assignment.TaskMaster.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.TaskMaster.dto.CommentResponse;
import com.assignment.TaskMaster.dto.CreateCommentRequest;
import com.assignment.TaskMaster.entity.Comment;
import com.assignment.TaskMaster.entity.Task;
import com.assignment.TaskMaster.entity.User;
import com.assignment.TaskMaster.exception.CommentNotFoundException;
import com.assignment.TaskMaster.exception.TaskNotFoundException;
import com.assignment.TaskMaster.exception.UserNotFoundException;
import com.assignment.TaskMaster.repository.CommentRepository;
import com.assignment.TaskMaster.repository.TaskRepository;
import com.assignment.TaskMaster.repository.UserRepository;

@Service
public class CommentService 
{
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // Constructor injection
    public CommentService(
            CommentRepository commentRepository,
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {

        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // 1. Add Comment
    public CommentResponse addComment(
            Long taskId,
            CreateCommentRequest request
    ) {

        // Find task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        // Find comment author
        User author = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        // Create comment
        Comment comment = new Comment();

        comment.setContent(request.getContent());
        comment.setTask(task);
        comment.setCreatedBy(author);

        // Save comment
        Comment saved = commentRepository.save(comment);

        return mapToResponse(saved);
    }

    // 2. Get Comments By Task
    public List<CommentResponse> getCommentsByTask(Long taskId) {

        // Check task exists
        if (!taskRepository.existsById(taskId)) {

            throw new TaskNotFoundException("Task not found");
        }

        // Fetch comments in chronological order
        List<Comment> comments =
                commentRepository.findByTaskIdOrderByCreatedAtAsc(taskId);

        return comments.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 3. Delete Comment
    public void deleteComment(Long commentId) {

        // Find comment
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommentNotFoundException("Comment not found"));

        // Delete comment
        commentRepository.delete(comment);
    }

    // 4. Helper Method: Map Entity To DTO
    private CommentResponse mapToResponse(Comment comment) {

        Long taskId = comment.getTask().getTaskId();

        Long createdById =
                comment.getCreatedBy().getUserid();

        String createdByUsername =
                comment.getCreatedBy().getUsername();

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                taskId,
                createdById,
                createdByUsername,
                comment.getCreatedAt()
        );
    }
    
}
