package com.szczepanik.githubrepositorylisting.githubbranch;

import com.szczepanik.githubrepositorylisting.exception.GithubException;
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
public class GithubBranchService {

    private final RestTemplate restTemplate;

    private final String githubApiUrl;

    public static final String GITHUB_API_BRANCHES = "/repos/{username}/{repo}/branches";

    public GithubBranchService(RestTemplate restTemplate,@Value("${github.api.url}") String githubApiUrl) {
        this.restTemplate = restTemplate;
        this.githubApiUrl = githubApiUrl;
    }

    public List<GithubBranch> getAllBranchesByUsernameAndRepositoryName(String username, String repo) {

        String url = UriComponentsBuilder.fromHttpUrl(githubApiUrl)
                .path(GITHUB_API_BRANCHES)
                .buildAndExpand(username, repo)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        try {

            return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url, GithubBranch[].class, headers)));

        } catch (HttpClientErrorException.NotFound e) {
            throw new EntityNotFoundException("user or repository not found");
        } catch (RestClientException e) {
            throw new GithubException("Error while communicating with GitHub API");
        }

    }

}
