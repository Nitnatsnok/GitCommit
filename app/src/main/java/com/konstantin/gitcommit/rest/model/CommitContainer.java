package com.konstantin.gitcommit.rest.model;

public class CommitContainer {
    private String sha;
    private Commit commit;
    private String html_url;

    public String getSha() {
        return sha;
    }

    public Commit getCommit() {
        return commit;
    }

    public String getHtmlUrl() {
        return html_url;
    }
}
