package com.assignment.TaskMaster.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 
public class RegisterRequest 
{
    @NotBlank 
    String username;

    @Email 
    @NotBlank(message =  "Email Id is required")
    String email;

    @NotBlank(message =  "Phone number is required")
    @Pattern(
        regexp = "^\\+?[1-9][0-9]{7,14}$",
        message = "Invalid phone number format"
    )
    String phone;


    @NotBlank (message = "Password is required")
    @Size (min = 8)
    String password;

    
}
