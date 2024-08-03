package com.example.githubapiconsumer;  

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepositoryInfo(String name, OwnerInfo owner, List<BranchDTO> branches, boolean fork) {

    public String ownerLogin() {
        return owner != null ? owner.login() : null;
    }
 }

