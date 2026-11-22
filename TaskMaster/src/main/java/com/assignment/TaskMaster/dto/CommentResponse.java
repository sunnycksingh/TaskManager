package com.assignment.TaskMaster.dto;

import java.time.LocalDateTime;

public class CommentResponse 
{
    private Long id;
    private String content;
    private Long taskId;
    private Long createdById;
    private String createdByUsername;
    private LocalDateTime createdAt;

    // All-args constructor
    public CommentResponse(
            Long id,
            String content,
            Long taskId,
            Long createdById,
            String createdByUsername,
            LocalDateTime createdAt
    ) {

        this.id = id;
        this.content = content;
        this.taskId = taskId;
        this.createdById = createdById;
        this.createdByUsername = createdByUsername;
        this.createdAt = createdAt;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    } 
    
}
