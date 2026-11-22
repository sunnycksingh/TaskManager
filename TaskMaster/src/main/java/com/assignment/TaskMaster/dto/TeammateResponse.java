package com.assignment.TaskMaster.dto;

import java.time.LocalDateTime;

public class TeammateResponse 
{
    private Long id;
    private Long teamId;
    private Long userId;
    private String username;
    private String email;
    private LocalDateTime joinedAt;



    public TeammateResponse(Long id, Long teamId, Long userId, String username, String email, LocalDateTime joinedAt) {
        this.id = id;
        this.teamId = teamId;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.joinedAt = joinedAt;
    }



    public Long getId() {
        return id;
    }



    public Long getTeamId() {
        return teamId;
    }



    public Long getUserId() {
        return userId;
    }



    public String getUsername() {
        return username;
    }



    public String getEmail() {
        return email;
    }



    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    


    
    
}
