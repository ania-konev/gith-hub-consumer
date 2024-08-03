package com.example.githubapiconsumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class RepositoryDTO {
    
    private String repositoryName;
    private String ownerLogin;
    private List<BranchDTO> branches;

    public RepositoryDTO(String name, String ownerLogin, List<BranchDTO> branches) {
        this.repositoryName = name;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<BranchDTO> getBranches() {
        return branches;
    }
}
