package com.konstantin.gitcommit.rest.service;

import com.konstantin.gitcommit.rest.model.CommitContainer;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubApiService {
    @GET("/repos/{owner}/{repo}/commits")
    ArrayList<CommitContainer> getListCommits(@Path("owner") String owner, @Path("repo") String repo);
}
