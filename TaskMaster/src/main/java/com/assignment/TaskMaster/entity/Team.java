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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "teams")
@AllArgsConstructor
@NoArgsConstructor 
@Getter
@Setter
public class Team 
{

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamid;

    @NotNull 
    @Column(nullable = false)
    private String teamName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createBy;


    @NotNull
    @CreationTimestamp 
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;


    
}
