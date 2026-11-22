package com.assignment.TaskMaster.entity;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity 
@Table(name = "teammates", uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id","user_id"})})
public class Teammate 
{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teammateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "team_id", nullable = false)
    private Team team;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime joinedAt;

    public Teammate()
    {

    }

    public Long getTeammateId()
    {
        return teammateId;
    }

    public void setTeammateId(Long teammateId)
    {
       this.teammateId = teammateId;
    }

     public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

        public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

      public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
