package com.szczepanik.githubrepositorylisting.githubRepository;

import com.szczepanik.githubrepositorylisting.exception.ApiError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class GithubRepositoryController {

    private GithubRepositoryService githubRepositoryService;

    public GithubRepositoryController(GithubRepositoryService githubRepositoryService) {
        this.githubRepositoryService = githubRepositoryService;
    }

    @GetMapping("/repository/{owner-login}")
    public ResponseEntity<List<GithubRepository>> getRepositories(@PathVariable(name = "owner-login") String ownerLogin){
        return ResponseEntity.ok(githubRepositoryService.getRepositoriesByUsername(ownerLogin));
    }


     @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
        }
}
