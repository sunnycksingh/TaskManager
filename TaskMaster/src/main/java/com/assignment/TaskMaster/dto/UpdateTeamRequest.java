package com.assignment.TaskMaster.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateTeamRequest {
    
    @NotBlank(message = "Team name is required")
    private String teamName;


    public UpdateTeamRequest() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
