package com.szczepanik.githubrepositorylisting.githubRepository;

import java.util.List;

public class GithubRepositoryResponseDto {
    private String repositoryName;
    private String ownerLogin;

    private List<BranchDto> branches;

    public GithubRepositoryResponseDto(String repositoryName, String ownerLogin) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
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

    public static class BranchDto {
        private String branchName;
        private String lastCommitSha;

        public BranchDto(String branchName, String lastCommitSha) {
            this.branchName = branchName;
            this.lastCommitSha = lastCommitSha;
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
