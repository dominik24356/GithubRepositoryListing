package com.szczepanik.githubrepositorylisting.githubbranch;

public class GithubBranch {
    private String name;
    private Commit commit;

    public GithubBranch(String name, Commit commit){
        this.name = name;
        this.commit = commit;
    }

    public GithubBranch() {
    }


    public static class Commit {
        private String sha;

        public Commit(String sha){
            this.sha = sha;
        }

        public Commit() {
        }

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }



}