package com.assignment.TaskMaster.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter 
@Setter
public class UpdateProfileRequest 
{
    @NotBlank(message = "Username cannot be blank")
    String username;

    String phone;   

  //   - we leave email and password out on purpose:
  //      changing email affects login, and password change deserves its own endpoint
    
}
