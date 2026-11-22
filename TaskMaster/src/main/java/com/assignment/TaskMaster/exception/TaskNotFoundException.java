package com.assignment.TaskMaster.exception;

public class TaskNotFoundException extends RuntimeException 
{
    public TaskNotFoundException(String message) {
        super(message);
    }   
}
