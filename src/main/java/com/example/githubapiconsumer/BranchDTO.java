package com.example.githubapiconsumer;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class BranchDTO {
    
    private String name;
    private String lastCommitSha;

    public BranchDTO(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }

    public String getName() {
        return name;
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchDTO that = (BranchDTO) o;

        return name.equals(that.name) &&
            lastCommitSha.equals(that.lastCommitSha);
    }

}
