package com.assignment.TaskMaster.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.TaskMaster.dto.RegisterRequest;
import com.assignment.TaskMaster.dto.UserResponse;
import com.assignment.TaskMaster.service.UserService;

import jakarta.validation.Valid;

@RestController          // Helps to convert the data into json format
@RequestMapping("/api/auth")
public class AuthController 
{

  private final UserService userService;

   // Constructor Injection

   public AuthController(UserService userService)
   {
    this.userService = userService;
   }


// User Registration Endpoint

@PostMapping("/register")
public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request)
{

 // Call userService.registerUser(request)

   UserResponse response = userService.registerUser(request);


   //  Return the result wrapped in a ResponseEntity with status 201 CREATED

   return ResponseEntity
   .status(HttpStatus.CREATED)
   .body(response);
}

    
}
