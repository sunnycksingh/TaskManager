package com.assignment.TaskMaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.TaskMaster.dto.CommentResponse;
import com.assignment.TaskMaster.dto.CreateCommentRequest;
import com.assignment.TaskMaster.service.CommentService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api")
public class CommentController 
{
    private final CommentService commentService;

    // Constructor
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 1. Add Comment
    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentResponse> addComment(

            @PathVariable Long taskId,

            @Valid @RequestBody CreateCommentRequest request
    ) {

        CommentResponse response =
                commentService.addComment(taskId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // 2. Get Comments By Task
    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(

            @PathVariable Long taskId
    ) {

        List<CommentResponse> comments =
                commentService.getCommentsByTask(taskId);

        return ResponseEntity.ok(comments);
    }

    // 3. Delete Comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(

            @PathVariable Long commentId
    ) {

        commentService.deleteComment(commentId);

        return ResponseEntity.noContent().build();
    } 
    
}
