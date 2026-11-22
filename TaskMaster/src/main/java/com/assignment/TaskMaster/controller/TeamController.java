package com.assignment.TaskMaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.TaskMaster.dto.CreateTeamRequest;
import com.assignment.TaskMaster.dto.TeamResponse;
import com.assignment.TaskMaster.dto.UpdateTeamRequest;
import com.assignment.TaskMaster.service.TeamService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/teams")
public class TeamController 
{
  
     private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }



      // Endpoint 1: Create Team
    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(
            @Valid @RequestBody CreateTeamRequest request) {

        TeamResponse response = teamService.createTeam(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

      // Endpoint 2: Get Team By ID
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeam(
            @PathVariable Long id) {

        TeamResponse response = teamService.getTeamById(id);

        return ResponseEntity.ok(response);
    }

    // Endpoint 3: Get Teams By Creator
    @GetMapping
    public ResponseEntity<List<TeamResponse>> getTeamsByCreator(
            @RequestParam Long createdBy) {

        List<TeamResponse> response =
                teamService.getTeamsCreatedByUser(createdBy);

        return ResponseEntity.ok(response);
    }

      // Endpoint 4: Update Team
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTeamRequest request) {

        TeamResponse response = teamService.updateTeam(id, request);

        return ResponseEntity.ok(response);
    }

    
}
