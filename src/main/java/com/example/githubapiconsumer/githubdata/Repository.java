package com.example.githubapiconsumer.githubdata;

import com.example.githubapiconsumer.response.BranchResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Repository(String name, Owner owner, List<BranchResponse> branches, boolean fork) {

    public String ownerLogin() {
        return owner != null ? owner.login() : null;
    }
}
