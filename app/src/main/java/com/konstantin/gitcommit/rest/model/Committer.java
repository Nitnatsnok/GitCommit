package com.konstantin.gitcommit.rest.model;

import java.util.Date;

public class Committer {
    private String name;
    private String email;
    private Date date;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getDate() {
        return date;
    }
}
