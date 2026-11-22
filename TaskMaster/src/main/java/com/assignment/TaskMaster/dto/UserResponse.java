package com.assignment.TaskMaster.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter
public class UserResponse 
{
    Long id;
    
    String username;

    String email;

    String phone;

    LocalDateTime createdAt;
    
}
