package com.example.githubapiconsumer.githubdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Branch(String name, Commit commit) {

    public String lastCommitSha() {
        return commit != null ? commit.sha() : null;
    }
}
