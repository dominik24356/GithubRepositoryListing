package com.szczepanik.githubrepositorylisting.githubRepository;

import java.util.List;

public class GithubRepository {
    private String name;
    private Owner owner;

    private boolean fork;

    private List<GithubBranch> branches;

    public void setBranches(List<GithubBranch> branches) {
        this.branches = branches;
    }


    public static class Owner {
        private String login;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public List<GithubBranch> getBranches() {
        return branches;
    }

    public static class GithubBranch {
        private String name;
        private Commit commit;

        public static class Commit {
            private String sha;

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

        public Commit getCommit() {
            return commit;
        }
    }

}
