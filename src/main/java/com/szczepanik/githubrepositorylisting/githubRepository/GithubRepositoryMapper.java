package com.szczepanik.githubrepositorylisting.githubRepository;

public class GithubRepositoryMapper {

    public static GithubRepositoryResponseDto mapToDto(GithubRepository repository) {
        return new GithubRepositoryResponseDto(repository.getName(), repository.getOwner().getLogin());
    }


}
