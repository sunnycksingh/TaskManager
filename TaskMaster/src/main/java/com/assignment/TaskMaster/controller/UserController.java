package com.assignment.TaskMaster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.TaskMaster.dto.UpdateProfileRequest;
import com.assignment.TaskMaster.dto.UserResponse;
import com.assignment.TaskMaster.service.UserService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/users")
public class UserController 
{

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }



  // Get User Endpoint or you can say ViewProfile

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id)
  {


     UserResponse response = userService.getUserById(id);

     return ResponseEntity.ok(response);


  }


  // Update User Endpoint

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateProfileRequest request)
  {
     UserResponse response = userService.updateProfile(id, request);

     return ResponseEntity.ok(response);
  }


}
