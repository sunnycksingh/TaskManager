package com.assignment.TaskMaster.exception;

public class EmailAlreadyExistsException extends RuntimeException 
{

    public EmailAlreadyExistsException(String message)
    {
        super(message);
    }
    
}
