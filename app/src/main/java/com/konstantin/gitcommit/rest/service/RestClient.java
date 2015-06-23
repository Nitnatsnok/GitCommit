package com.konstantin.gitcommit.rest.service;

import retrofit.RestAdapter;

public class RestClient {
    private static final String BASE_URL = "https://api.github.com";
    private GitHubApiService gitHubApiService;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASE_URL).build();
        gitHubApiService = restAdapter.create(GitHubApiService.class);
    }

    public GitHubApiService getGitHubApiService() {
        return gitHubApiService;
    }
}
