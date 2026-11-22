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

import com.assignment.TaskMaster.dto.AddMemberRequest;
import com.assignment.TaskMaster.dto.TeamResponse;
import com.assignment.TaskMaster.dto.TeammateResponse;
import com.assignment.TaskMaster.service.TeammateService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api")
public class TeammateController 
{
     private final TeammateService teammateService;

    public TeammateController(TeammateService teammateService) {
        this.teammateService = teammateService;
    }

    // Add member
    @PostMapping("/teams/{teamId}/members")
    public ResponseEntity<TeammateResponse> addMember(
            @PathVariable Long teamId,
            @Valid @RequestBody AddMemberRequest request) {

        TeammateResponse response =
                teammateService.addMemeber(teamId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // Get all members
    @GetMapping("/teams/{teamId}/members")
    public ResponseEntity<List<TeammateResponse>> getMembers(
            @PathVariable Long teamId) {

        return ResponseEntity.ok(
                teammateService.getMembers(teamId));
    }

    // Remove member
    @DeleteMapping("/teams/{teamId}/members/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long teamId,
            @PathVariable Long userId) {

        teammateService.removeMember(teamId, userId);

        return ResponseEntity.noContent().build();
    }

    // Get all teams for user
    @GetMapping("/users/{userId}/teams")
    public ResponseEntity<List<TeamResponse>> getTeamsForUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                teammateService.getTeamsForUser(userId));
    }
    
}
