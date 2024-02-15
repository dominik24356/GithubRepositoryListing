package com.szczepanik.githubrepositorylisting.githubRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class GithubRepositoryService {

    @Value("${github.api.url}")
    private String githubApiUrl;

    private RestTemplate restTemplate;

    public GithubRepositoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public List<GithubRepository> getRepositoriesByUsername(String username){

        String  url = UriComponentsBuilder.fromHttpUrl(githubApiUrl)
                .path("/users/{username}/repos")
                .buildAndExpand(username)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        try {

            GithubRepository[] repositories = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(url, GithubRepository[].class, headers)))
                    .filter(repository -> !repository.isFork())
                    .toArray(GithubRepository[]::new);

            for (GithubRepository repository : repositories) {
                repository.setBranches(getBranches(username, repository.getName()));
            }

            return List.of(repositories);

        } catch (Exception e) {
            throw new EntityNotFoundException("User with username: " + username + " not found");
        }

    }


    private List<GithubRepository.GithubBranch> getBranches(String username, String repo) {
        String url = UriComponentsBuilder.fromHttpUrl(githubApiUrl)
                .path("/repos/{username}/{repo}/branches")
                .buildAndExpand(username, repo)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url, GithubRepository.GithubBranch[].class, headers)));
    }


}
