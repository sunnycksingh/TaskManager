package com.assignment.TaskMaster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.TaskMaster.entity.Teammate;

public interface TeammateRepository extends JpaRepository<Teammate, Long>{

    boolean existsByTeamIdAndUserId(Long teamId, Long userId);

    List<Teammate> findByTeamId(Long teamId);

    List<Teammate> findByUserId(Long userId);

    Optional<Teammate> findByTeamIdAndUserId(Long teamId, Long userId);
    
}
