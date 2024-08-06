package com.example.githubapiconsumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.example.githubapiconsumer.exception.UserNotFoundException;
import com.example.githubapiconsumer.githubdata.Branch;
import com.example.githubapiconsumer.githubdata.Repository;
import com.example.githubapiconsumer.response.BranchResponse;
import com.example.githubapiconsumer.response.RepositoryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GitHubUserController {

    private static final Logger log = LoggerFactory.getLogger(GitHubUserController.class);

    private final WebClient client;

    public GitHubUserController(WebClient client) {
        this.client = client;
    }

    @GetMapping(value = "/users/{username}", headers = "Accept=application/json")
    public Flux<RepositoryResponse> repositoryInfo(@PathVariable String username) {

        return client.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .bodyToFlux(Repository.class)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> {
                    log.error(e.getMessage());
                    throw new UserNotFoundException(username);
                })
                .filter(repo -> !repo.fork())
                .doOnNext(repo -> log.info(repo.toString()))
                .flatMap(repo -> getBranches(username, repo));
    }

    private Mono<RepositoryResponse> getBranches(String username, Repository info) {
        return client.get()
                .uri("/repos/{username}/{repoName}/branches", username, info.name())
                .retrieve()
                .bodyToFlux(Branch.class) //
                .collectList()
                .map(branchInfos -> {
                    List<BranchResponse> branches = new ArrayList<>(branchInfos.size());
                    for (Branch branchInfo : branchInfos) {
                        branches.add(new BranchResponse(branchInfo.name(), branchInfo.lastCommitSha()));
                    }
                    return new RepositoryResponse(info.name(), info.ownerLogin(), branches);
                });
    }
}
