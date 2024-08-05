package com.example.githubapiconsumer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
     
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryDTO that = (RepositoryDTO) o;
        return repositoryName.equals(that.repositoryName) &&
               ownerLogin.equals(that.ownerLogin) &&
               branches.equals(that.branches);
    }

}
