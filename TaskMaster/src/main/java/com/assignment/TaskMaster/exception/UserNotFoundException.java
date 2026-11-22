package com.assignment.TaskMaster.exception;

public class UserNotFoundException extends RuntimeException 
{
    public UserNotFoundException(String message)
    {
        super(message);
    }
    
}
