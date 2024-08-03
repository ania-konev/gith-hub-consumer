package com.example.githubapiconsumer;

import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RestController
public class GitHubUserController {

    private static final Logger log = LoggerFactory.getLogger(GitHubUserController.class);

    private final WebClient client =  WebClient.create("https://api.github.com");

    @GetMapping(value = "/users/{username}", headers = "Accept=application/json")
    public Flux<RepositoryDTO> repositoryInfo(@PathVariable String username ) {

        return client.get()
            .uri("/users/{username}/repos", username)
            .retrieve()
            .bodyToFlux(RepositoryInfo.class)
            .onErrorResume(WebClientResponseException.NotFound.class, e -> {
                throw new UserNotFoundException(username);
            })
            .filter(info -> !info.fork())
            .doOnNext(info -> log.warn(info.toString())) 
            .flatMap(info -> getBranches(username, info));
        } 

    private Mono<RepositoryDTO> getBranches(String username, RepositoryInfo info) {
        return client.get()
            .uri("/repos/{username}/{repoName}/branches", username, info.name())
            .retrieve()
            .bodyToFlux(BranchInfo.class) //
            .collectList()
            .map(branchInfos -> {
                List<BranchDTO> branches = new ArrayList<>(branchInfos.size());
                for (BranchInfo branchInfo : branchInfos) {
                    branches.add(new BranchDTO(branchInfo.name(), branchInfo.lastCommitSha()));
                }
                return new RepositoryDTO(info.name(), info.ownerLogin(), branches);
            });
    }
}
