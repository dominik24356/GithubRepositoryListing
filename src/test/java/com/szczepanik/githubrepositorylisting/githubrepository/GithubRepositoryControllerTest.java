package com.szczepanik.githubrepositorylisting.githubrepository;

import com.szczepanik.githubrepositorylisting.exception.ApiError;
import com.szczepanik.githubrepositorylisting.exception.GithubException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(GithubRepositoryController.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class GithubRepositoryControllerTest {

    @MockBean
    private GithubRepositoryService githubRepositoryService;

    @InjectMocks
    private GithubRepositoryController githubRepositoryController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetAllRepositoriesByUsername_Success() throws Exception {
        String ownerLogin = "testuser";

        GithubRepositoryResponseDto mockResponseDto = new GithubRepositoryResponseDto();
        List<GithubRepositoryResponseDto> mockResponseDtoList = List.of(mockResponseDto);

        when(githubRepositoryService.getRepositoriesByUsername(ownerLogin))
                .thenReturn(mockResponseDtoList);

        mockMvc.perform(get("/api/repository/{owner-login}", ownerLogin))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(mockResponseDtoList.size()));
    }

    @Test
    public void testGetAllRepositoriesByUsername_EntityNotFoundException() throws Exception {
        String ownerLogin = "testuser";


        when(githubRepositoryService.getRepositoriesByUsername(ownerLogin))
                .thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(get("/api/repository/{owner-login}", ownerLogin))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    public void testGetAllRepositoriesByUsername_GithubException() throws Exception {
        String ownerLogin = "testuser";

        when(githubRepositoryService.getRepositoriesByUsername(ownerLogin))
                .thenThrow(new GithubException("Error while communicating with GitHub API"));

        mockMvc.perform(get("/api/repository/{owner-login}", ownerLogin))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("Error while communicating with GitHub API"));
    }

    @Test
    public void testHandleEntityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("User not found");

        ResponseEntity<ApiError> response = githubRepositoryController.handleEntityNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    public void testHandleGithubException() {
        GithubException exception = new GithubException("Error while communicating with GitHub API");

        ResponseEntity<ApiError> response = githubRepositoryController.handleGithubException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error while communicating with GitHub API", response.getBody().getMessage());
    }

}