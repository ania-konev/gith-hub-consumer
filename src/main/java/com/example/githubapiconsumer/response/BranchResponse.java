package com.example.githubapiconsumer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchResponse(String name, String lastCommitSha) {
}
