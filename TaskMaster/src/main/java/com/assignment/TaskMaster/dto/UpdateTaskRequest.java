package com.assignment.TaskMaster.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class UpdateTaskRequest 
{
    @NotBlank(message = "Task title is required")
    private String title;

    private String description;

    private LocalDate dueDate;

    public UpdateTaskRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
}
