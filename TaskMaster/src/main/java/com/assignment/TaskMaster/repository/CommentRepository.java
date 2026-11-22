package com.assignment.TaskMaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.TaskMaster.entity.Comment;

@Repository 
public interface CommentRepository extends JpaRepository<Comment,Long> 
{

      // Get task comments in chronological order
    List<Comment> findByTaskIdOrderByCreatedAtAsc(Long taskId);

    // Delete all comments belonging to a task
    void deleteByTaskId(Long taskId);
    
}
