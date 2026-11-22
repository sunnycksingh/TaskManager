package com.assignment.TaskMaster.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.assignment.TaskMaster.entity.TaskStatus;

public class TaskResponse 
{
      private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;

    private Long assignedToId;
    private String assignedToUsername;

    private Long teamId;
    private Long createdById;

    private List<String> attachment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // All-args constructor
    public TaskResponse(
            Long id,
            String title,
            String description,
            LocalDate dueDate,
            TaskStatus status,
            Long assignedToId,
            String assignedToUsername,
            Long teamId,
            Long createdById,
            List<String> attachment,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.assignedToId = assignedToId;
        this.assignedToUsername = assignedToUsername;
        this.teamId = teamId;
        this.createdById = createdById;
        this.attachment = attachment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public String getAssignedToUsername() {
        return assignedToUsername;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public List<String> getAttachment() {
        return attachment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
}
