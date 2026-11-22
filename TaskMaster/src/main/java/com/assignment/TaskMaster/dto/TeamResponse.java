package com.assignment.TaskMaster.dto;

import java.time.LocalDateTime;

public class TeamResponse 
{

    private Long id;
    private String teamName;
    private Long createdById;
    private String createdByUsername;
    private LocalDateTime createdAt;

     public TeamResponse(Long id, String teamName,
                        Long createdById, String createdByUsername,
                        LocalDateTime createdAt) {

        this.id = id;
        this.teamName = teamName;
        this.createdById = createdById;
        this.createdByUsername = createdByUsername;
        this.createdAt = createdAt;
    }

      public Long getId() {
        return id;
    }

     public String getTeamName() {
        return teamName;
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
