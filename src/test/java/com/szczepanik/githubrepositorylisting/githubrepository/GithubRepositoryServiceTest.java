package com.szczepanik.githubrepositorylisting.githubrepository;

import com.szczepanik.githubrepositorylisting.exception.GithubException;
import com.szczepanik.githubrepositorylisting.githubbranch.GithubBranchService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class GithubRepositoryServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GithubBranchService githubBranchService;


    private GithubRepositoryService githubRepositoryService;

    private static final String GITHUB_API_URL = "https://api.github.com";

    @BeforeEach
    public void setup() {
        githubRepositoryService = new GithubRepositoryService(GITHUB_API_URL, restTemplate, githubBranchService);
    }

    private HttpHeaders createExpectedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return headers;
    }

    private String buildExpectedUrl(String username) {
        return GITHUB_API_URL + "/users/" + username + "/repos";
    }


    @Test
    public void testGetRepositoriesByUsername_Success() {
        String username = "testuser";
        String expectedUrl = buildExpectedUrl(username);
        HttpHeaders expectedHeaders = createExpectedHeaders();


        GithubRepository[] mockRepositories = {
                new GithubRepository("testrepo1", new GithubRepository.Owner("testuser"), false),
                new GithubRepository("testrepo2", new GithubRepository.Owner("testuser"), false)};


        // Mocking the repository service response
        when(restTemplate.getForObject(expectedUrl, GithubRepository[].class, expectedHeaders))
                .thenReturn(mockRepositories);

        // Mocking the branch service response
        when(githubBranchService.getAllBranchesByUsernameAndRepositoryName(anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        // testing the getRepositoriesByUsername method
        List<GithubRepositoryResponseDto> repositories = githubRepositoryService.getRepositoriesByUsername(username);

        // checking the response
        assertEquals(mockRepositories.length, repositories.size());
    }

    @Test
    public void testGetRepositoriesByUsername_UserNotFound() {
        String username = "testuser";
        String expectedUrl = buildExpectedUrl(username);
        HttpHeaders expectedHeaders = createExpectedHeaders();


        // simulating the HttpClientErrorException.NotFound
        when(restTemplate.getForObject(expectedUrl, GithubRepository[].class, expectedHeaders)).thenThrow(HttpClientErrorException.NotFound.class);

        // testing the getRepositoriesByUsername method
        try {
            githubRepositoryService.getRepositoriesByUsername("testuser");
        } catch (EntityNotFoundException ex) {
            assertEquals("User with username: testuser not found", ex.getMessage());
        }
    }

    @Test
    public void testGetRepositoriesByUsername_GithubError() {
        String username = "testuser";
        String expectedUrl = buildExpectedUrl(username);
        HttpHeaders expectedHeaders = createExpectedHeaders();

        // simulating the RestClientException
        when(restTemplate.getForObject(expectedUrl, GithubRepository[].class, expectedHeaders)).thenThrow(RestClientException.class);

        // testing the getRepositoriesByUsername method
        try {
            githubRepositoryService.getRepositoriesByUsername("testuser");
        } catch (GithubException ex) {
            assertEquals("Error while communicating with GitHub API", ex.getMessage());
        }
    }

}