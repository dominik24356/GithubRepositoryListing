package com.szczepanik.githubrepositorylisting.githubrepository;

public class GithubRepository {
    private String name;
    private Owner owner;
    private boolean fork;

    GithubRepository(String name, Owner owner, boolean fork){
        this.name = name;
        this.owner = owner;
        this.fork = fork;
    }

    public GithubRepository() {
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


    public static class Owner {
        private String login;

        public Owner(String login){
            this.login = login;
        }

        public Owner() {
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }



}
