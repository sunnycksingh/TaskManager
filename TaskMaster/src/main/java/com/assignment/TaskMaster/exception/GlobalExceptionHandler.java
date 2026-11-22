package com.assignment.TaskMaster.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler 
{
    //  Handle EmailAlreadyExistsException
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException ex)
    {
        return ResponseEntity
               .status(HttpStatus.CONFLICT)
               .body(ex.getMessage());
    }

    // Handle UserNotFoundException

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex)
    {
       return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(ex.getMessage());
    }


@ExceptionHandler(TeamNotFoundException.class)
public ResponseEntity<String> handleTeamNotFound(
        TeamNotFoundException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}

@ExceptionHandler(AlreadyTeamMemeberException.class)
public ResponseEntity<String> handleAlreadyTeamMember(
        AlreadyTeamMemeberException ex) {

    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
}

@ExceptionHandler(TeammateNotFoundException.class)
public ResponseEntity<String> handleTeammateNotFound(
        TeammateNotFoundException ex) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());

}

@ExceptionHandler(TaskNotFoundException.class)
public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}

@ExceptionHandler(UserNotInTeamException.class)
public ResponseEntity<String> handleUserNotInTeam(UserNotInTeamException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
}

@ExceptionHandler(CommentNotFoundException.class)
public ResponseEntity<String> handleCommentNotFound(CommentNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}


    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationErrors(
                MethodArgumentNotValidException ex) {

            Map<String, String> errors = new HashMap<>();

            ex.getBindingResult()
                    .getFieldErrors()
                    .forEach(error -> {
                        errors.put(error.getField(), error.getDefaultMessage());
                    });

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        }
    
}
