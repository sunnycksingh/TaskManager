package com.assignment.TaskMaster.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.TaskMaster.dto.CreateTeamRequest;
import com.assignment.TaskMaster.dto.TeamResponse;
import com.assignment.TaskMaster.dto.UpdateTeamRequest;
import com.assignment.TaskMaster.entity.Team;
import com.assignment.TaskMaster.entity.User;
import com.assignment.TaskMaster.exception.TeamNotFoundException;
import com.assignment.TaskMaster.exception.UserNotFoundException;
import com.assignment.TaskMaster.repository.TeamRepository;
import com.assignment.TaskMaster.repository.UserRepository;

@Service
public class TeamService 
{
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository)
    {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }


    // Method 1 : Create Team

    public TeamResponse createTeam(CreateTeamRequest request)
    {

        // Find Creator

        User creator = userRepository.findById(request.getCreatedBy()).orElseThrow(() ->
        new UserNotFoundException("User not found")
    );


    // Create new Team

    Team team = new Team();

    team.setTeamName(request.getTeamName());

    // Set Creator Object
    
    team.setCreateBy(creator);

    // Save Team

    Team savedTeam = teamRepository.save(team);

        // Convert to response
        return mapToResponse(savedTeam);    

    }

      // Method 2: Get Team By ID
    public TeamResponse getTeamById(Long id) {

        Team team = teamRepository.findById(id)
                .orElseThrow(() ->
                        new TeamNotFoundException("Team not found with ID: " + id));

        return mapToResponse(team);
    }

     // Method 3: Get Teams Created By User
    public List<TeamResponse> getTeamsCreatedByUser(Long userId) {

        // Check user exists
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        // Find teams
        List<Team> teams = teamRepository.findByCreatedById(userId);

        // Convert Team list into TeamResponse list
        return teams.stream()
                .map(this::mapToResponse)
                .toList();
    }

      // Method 4: Update Team
    public TeamResponse updateTeam(Long id, UpdateTeamRequest request) {

        // Find team
        Team team = teamRepository.findById(id)
                .orElseThrow(() ->
                        new TeamNotFoundException("Team not found with ID: " + id));

        // Update team name
        team.setTeamName(request.getTeamName());

        // Save updated team
        Team updatedTeam = teamRepository.save(team);

        return mapToResponse(updatedTeam);
    }

     // Helper Method: Convert Team into TeamResponse
    private TeamResponse mapToResponse(Team team) {

        return new TeamResponse(
                team.getTeamid(),
                team.getTeamName(),
                team.getCreateBy().getUserid(),
                team.getCreateBy().getUsername(),
                team.getCreatedAt()
        );
    }

    
}
