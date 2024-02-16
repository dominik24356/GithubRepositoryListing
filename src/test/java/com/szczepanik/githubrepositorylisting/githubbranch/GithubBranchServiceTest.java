package com.szczepanik.githubrepositorylisting.githubbranch;

import com.szczepanik.githubrepositorylisting.exception.GithubException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
class GithubBranchServiceTest {

    @Mock
    private RestTemplate restTemplate;


    private GithubBranchService githubBranchService;

    private static final String GITHUB_API_URL = "https://api.github.com";

    @BeforeEach
    public void setup() {
        githubBranchService = new GithubBranchService(restTemplate, GITHUB_API_URL);
    }

    private HttpHeaders createExpectedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return headers;
    }

    private String buildExpectedUrl(String username, String repo) {
        return GITHUB_API_URL + "/repos/" + username + "/" + repo + "/branches";
    }

    @Test
    public void testGetAllBranchesByUsernameAndRepositoryName_Success() {
        String username = "testuser";
        String repo = "testrepo";
        String expectedUrl = buildExpectedUrl(username, repo);
        HttpHeaders headers = createExpectedHeaders();

        GithubBranch[] mockBranches = {
                new GithubBranch("master", new GithubBranch.Commit("sha1")),
                new GithubBranch("develop", new GithubBranch.Commit("sha2"))
        };

        when(restTemplate.getForObject(expectedUrl, GithubBranch[].class, headers))
                .thenReturn(mockBranches);

        List<GithubBranch> branches = githubBranchService.getAllBranchesByUsernameAndRepositoryName(username, repo);

        assertEquals(Arrays.asList(mockBranches), branches);
    }

    @Test
    public void testGetAllBranchesByUsernameAndRepositoryName_UserOrRepositoryNotFound() {
        String username = "testuser";
        String repo = "testrepo";
        String expectedUrl = buildExpectedUrl(username, repo);
        HttpHeaders headers = createExpectedHeaders();

        when(restTemplate.getForObject(expectedUrl, GithubBranch[].class, headers))
                .thenThrow(HttpClientErrorException.NotFound.class);

        try {
            githubBranchService.getAllBranchesByUsernameAndRepositoryName(username, repo);
        } catch (EntityNotFoundException ex) {
            assertEquals("user or repository not found", ex.getMessage());
        }
    }

    @Test
    public void testGetAllBranchesByUsernameAndRepositoryName_GithubError() {
        String username = "testuser";
        String repo = "testrepo";
        String expectedUrl = buildExpectedUrl(username, repo);
        HttpHeaders headers = createExpectedHeaders();

        when(restTemplate.getForObject(expectedUrl, GithubBranch[].class, headers))
                .thenThrow(RestClientException.class);

        try {
            githubBranchService.getAllBranchesByUsernameAndRepositoryName(username, repo);
        } catch (GithubException ex) {
            assertEquals("Error while communicating with GitHub API", ex.getMessage());
        }
    }

}