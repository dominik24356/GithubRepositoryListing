package com.szczepanik.githubrepositorylisting.githubrepository;

import com.szczepanik.githubrepositorylisting.githubbranch.GithubBranch;

import java.util.List;
import java.util.stream.Collectors;

public class GithubRepositoryResponseDto {
    private String repositoryName;
    private String ownerLogin;

    private List<BranchDto> branches;

    public GithubRepositoryResponseDto(GithubRepository repository, List<GithubBranch> branches) {
        this.repositoryName = repository.getName();
        this.ownerLogin = repository.getOwner().getLogin();

        this.branches = branches.stream()
                .map(branch -> new BranchDto(branch.getName(), branch.getCommit().getSha()))
                .collect(Collectors.toList());
    }

    public GithubRepositoryResponseDto(String repositoryName, String ownerLogin, List<BranchDto> branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public GithubRepositoryResponseDto() {

    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<BranchDto> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchDto> branches) {
        this.branches = branches;
    }

    public static class BranchDto {
        private String branchName;
        private String lastCommitSha;

        public BranchDto(String branchName, String lastCommitSha) {
            this.branchName = branchName;
            this.lastCommitSha = lastCommitSha;
        }

        public BranchDto() {
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getLastCommitSha() {
            return lastCommitSha;
        }

        public void setLastCommitSha(String lastCommitSha) {
            this.lastCommitSha = lastCommitSha;
        }
    }


}
