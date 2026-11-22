package com.assignment.TaskMaster.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.TaskMaster.entity.Task;
import com.assignment.TaskMaster.entity.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> 
{
  
     // Find tasks assigned to a particular user
    List<Task> findByAssignedToId(Long userId, Sort sort);

    // Find tasks assigned to a user with a particular status
    List<Task> findByAssignedToIdAndStatus(
            Long userId,
            TaskStatus status,
            Sort sort
    );

    // Find all tasks belonging to a team
    List<Task> findByTeamId(Long teamId);

    // Search tasks by title or description
    @Query("""
            SELECT t FROM Task t
            WHERE t.assignedTo.id = :userId
            AND (
                LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR
                LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
            """)
    List<Task> search(
            @Param("userId") Long userId,
            @Param("keyword") String keyword
    );
    
}
