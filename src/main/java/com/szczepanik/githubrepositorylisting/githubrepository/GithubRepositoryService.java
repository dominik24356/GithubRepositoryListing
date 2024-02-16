package com.szczepanik.githubrepositorylisting.githubrepository;

import com.szczepanik.githubrepositorylisting.exception.GithubException;
import com.szczepanik.githubrepositorylisting.githubbranch.GithubBranch;
import com.szczepanik.githubrepositorylisting.githubbranch.GithubBranchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class GithubRepositoryService {


    private final String githubApiUrl;

    private final RestTemplate restTemplate;

    private final GithubBranchService githubBranchService;


    public static final String GITHUB_API_REPOS = "/users/{username}/repos";

    public GithubRepositoryService(@Value("${github.api.url}")String githubApiUrl, RestTemplate restTemplate, GithubBranchService githubBranchService) {
        this.githubApiUrl = githubApiUrl;
        this.restTemplate = restTemplate;
        this.githubBranchService = githubBranchService;
    }

    public List<GithubRepositoryResponseDto> getRepositoriesByUsername(String username){

        String  url = UriComponentsBuilder.fromHttpUrl(githubApiUrl)
                .path(GITHUB_API_REPOS)
                .buildAndExpand(username)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        try {

            GithubRepository[] repositories = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(url, GithubRepository[].class, headers)))
                    .filter(repository -> !repository.isFork())
                    .toArray(GithubRepository[]::new);


            return Arrays.stream(repositories)
                    .map(repository -> {
                        List<GithubBranch> branches = githubBranchService
                                .getAllBranchesByUsernameAndRepositoryName(username, repository.getName());
                        return new GithubRepositoryResponseDto(repository, branches);
                    })
                    .toList();

        } catch (HttpClientErrorException.NotFound e) {
            throw new EntityNotFoundException("User with username: " + username + " not found");
        } catch (RestClientException e) {
            throw new GithubException("Error while communicating with GitHub API");
        }

    }
}
