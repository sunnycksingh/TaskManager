package com.assignment.TaskMaster.dto;

import com.assignment.TaskMaster.entity.TaskStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequest 
{
    @NotNull (message = "Task status is required")
    private TaskStatus status;

    public UpdateStatusRequest() {
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    
}
