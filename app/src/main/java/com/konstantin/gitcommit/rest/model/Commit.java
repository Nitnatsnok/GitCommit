package com.konstantin.gitcommit.rest.model;

public class Commit {
    private Author author;
    private Committer committer;
    private String message;

    public Author getAuthor() {
        return author;
    }

    public Committer getCommitter() {
        return committer;
    }

    public String getMessage() {
        return message;
    }
}
