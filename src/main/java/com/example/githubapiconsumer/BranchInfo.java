package com.example.githubapiconsumer;  

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchInfo(String name, Commit commit) { 

    public String lastCommitSha() {
        return commit != null ? commit.sha() : null;
    }
}
