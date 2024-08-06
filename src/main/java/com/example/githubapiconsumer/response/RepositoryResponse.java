package com.example.githubapiconsumer.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepositoryResponse(String repositoryName, String ownerLogin, List<BranchResponse> branches) {
}
