package com.szczepanik.githubrepositorylisting.githubrepository;

import com.szczepanik.githubrepositorylisting.exception.ApiError;
import com.szczepanik.githubrepositorylisting.exception.GithubException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class GithubRepositoryController {

     private final GithubRepositoryService githubRepositoryService;

     public GithubRepositoryController(GithubRepositoryService githubRepositoryService) {
        this.githubRepositoryService = githubRepositoryService;
    }

     @GetMapping("/repository/{owner-login}")
     public ResponseEntity<List<GithubRepositoryResponseDto>> getAllRepositoriesByUsername(@PathVariable(name = "owner-login") String ownerLogin){
        return ResponseEntity.ok(githubRepositoryService.getRepositoriesByUsername(ownerLogin));
    }


    // I could have used @ControllerAdvice but for the sake of simplicity i used @ExceptionHandler in the controller
     @ExceptionHandler(EntityNotFoundException.class)
     public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException e) {
         ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
         return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
     }

     @ExceptionHandler(GithubException.class)
     public ResponseEntity<ApiError> handleGithubException(GithubException e) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
     }



}
