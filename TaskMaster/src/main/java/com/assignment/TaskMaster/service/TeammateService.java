package com.assignment.TaskMaster.service;
import java.util.List;

import org.springframework.stereotype.Service;
import com.assignment.TaskMaster.dto.AddMemberRequest;
import com.assignment.TaskMaster.dto.TeamResponse;
import com.assignment.TaskMaster.dto.TeammateResponse;
import com.assignment.TaskMaster.entity.Team;
import com.assignment.TaskMaster.entity.Teammate;
import com.assignment.TaskMaster.entity.User;
import com.assignment.TaskMaster.exception.AlreadyTeamMemeberException;
import com.assignment.TaskMaster.exception.TeamNotFoundException;
import com.assignment.TaskMaster.exception.TeammateNotFoundException;
import com.assignment.TaskMaster.exception.UserNotFoundException;
import com.assignment.TaskMaster.repository.TeamRepository;
import com.assignment.TaskMaster.repository.TeammateRepository;
import com.assignment.TaskMaster.repository.UserRepository;

@Service 
public class TeammateService 
{
    private final TeammateRepository teammateRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeammateService(TeammateRepository teammateRepository, TeamRepository teamRepository, UserRepository userRepository)
    {
        this.teammateRepository = teammateRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    // Add memeber to team

    public TeammateResponse addMemeber(Long teamId, AddMemberRequest request)
    {
        Team team = teamRepository.findById(teamId).orElseThrow(() ->
        new TeamNotFoundException("Team not found")
    
    );

        User user = userRepository.findById(request.getUserId())
                        .orElseThrow(() ->
                                new UserNotFoundException("User not found"));

        if(teammateRepository.existsByTeamIdAndUserId(teamId, request.getUserId()))
        {
            throw new AlreadyTeamMemeberException("User is already a memeber of this team");
        }

         Teammate teammate = new Teammate();
        teammate.setTeam(team);
        teammate.setUser(user);

        Teammate savedTeammate = teammateRepository.save(teammate);

        return mapToResponse(savedTeammate);        

    }

     // Get all members of a team
    public List<TeammateResponse> getMembers(Long teamId) {

        if (!teamRepository.existsById(teamId)) {
            throw new TeamNotFoundException("Team not found");
        }

        return teammateRepository.findByTeamId(teamId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

     // Get all teams belonging to a user
    public List<TeamResponse> getTeamsForUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }

        return teammateRepository.findByUserId(userId)
                .stream()
                .map(Teammate::getTeam)
                .map(this::mapTeamToResponse)
                .toList();
    }

     // Remove member from team
    public void removeMember(Long teamId, Long userId) {

        Teammate teammate = teammateRepository
                .findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() ->
                        new TeammateNotFoundException(
                                "User is not a member of this team"));

       // Don't allow removing team creator
        if (teammate.getUser().getUserid()
                .equals(teammate.getTeam().getCreateBy().getUserid())) {

            throw new IllegalArgumentException(
                    "Team creator cannot be removed");
        }

        teammateRepository.delete(teammate);
    }
    
      // Convert Teammate entity to DTO
    private TeammateResponse mapToResponse(Teammate teammate) {

    return new TeammateResponse(
            teammate.getTeammateId(),
            teammate.getTeam().getTeamid(),
            teammate.getUser().getUserid(),
            teammate.getUser().getUsername(),
            teammate.getUser().getEmail(),
            teammate.getJoinedAt()
    );
}
       // Convert Team entity to DTO
   private TeamResponse mapTeamToResponse(Team team) {

    return new TeamResponse(
            team.getTeamid(),
            team.getTeamName(),
            team.getCreateBy().getUserid(),
            team.getCreateBy().getUsername(),
            team.getCreatedAt()
    );
}

    
}
