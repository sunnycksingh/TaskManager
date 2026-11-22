package com.assignment.TaskMaster.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTeamRequest 
{
    @NotBlank(message = "Team name is required")
    private String teamname;

    @NotBlank(message = "Creator user id is required")
    private Long createdBy;

    public CreateTeamRequest()
    {

    }

    public String getTeamName()
    {
        return teamname;
    }

    public void setTeamName(String teamname)
    {
        this.teamname = teamname;
    }

     public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
}
