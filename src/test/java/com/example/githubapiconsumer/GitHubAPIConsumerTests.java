package com.example.githubapiconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import java.util.List;

@WebFluxTest(GitHubUserController.class)
@Import(WebClientConfiguration.class)
@TestPropertySource(properties = { "github.Url = http://localhost:8088"})   
@WireMockTest(httpPort = 8088)
public class GitHubAPIConsumerTests {

    @Autowired
    private WebTestClient webTestClient;

	@Test
	void testUserNotFound() {
        stubFor(
            WireMock.get(urlMatching("/users/usernotexist/repos"))   //robie wiremock zeby nie odpytywac githuba
             .willReturn(aResponse().withStatus(404)));

        webTestClient.get().uri("/users/usernotexist")
             .exchange()
             .expectStatus().isNotFound()
             .expectBody(ErrorData.class);
	}


    @Test
	void testAllRepositoriesForked() {

        stubFor(
            WireMock.get(urlMatching("/users/testuser/repos"))
             .willReturn(okJson("""
                [
                    {"name": "repo1", "fork": true, "owner": {"login": "testuser"}},
                    {"name": "repo2", "fork": true, "owner": {"login": "testuser"}}
                ]
                """)));

        webTestClient.get().uri("/users/testuser")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RepositoryDTO.class)
                .hasSize(0);
	}


    @Test
	void testProperResponse() {

        stubFor(
            WireMock.get(urlMatching("/users/properuser/repos"))
             .willReturn(okJson("""
                [
                    {"name": "repo1", "owner": {"login": "properuser"},"fork": true},
                    {"name": "repo2", "owner": {"login": "properuser"}, "fork": false},
                    {"name": "repo3", "owner": {"login": "properuser"}, "fork": false},
                    {"name": "repo4", "owner": {"login": "properuser"}, "fork": true}
                ]
                """)));


        stubFor(
            WireMock.get(urlMatching("/repos/properuser/repo2/branches"))  
            //bo tu w tych body musi byc tak jak w githubie odpowiedz jest
             .willReturn(okJson("""
                [
                    {"name": "branch1", "commit": {"sha": "sha1"}},
                    {"name": "branch2", "commit": {"sha": "sha2"}}
                ]
                """)));        

        stubFor(
            WireMock.get(urlMatching("/repos/properuser/repo3/branches"))
                .willReturn(okJson("""
                [
                    {"name": "branch1", "commit": {"sha": "sha1"}},
                    {"name": "branch2", "commit": {"sha": "sha2"}}
                ]
                """)));     

        webTestClient.get().uri("/users/properuser")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RepositoryDTO.class)
                .hasSize(2)
                .contains(
                    new RepositoryDTO(
                   "repo2",
             "properuser", 
                        List.of(
                            new BranchDTO("branch1", "sha1"),
                            new BranchDTO("branch2", "sha2")
                            )
                    ),

                    new RepositoryDTO(
                   "repo3",
             "properuser", 
                        List.of(
                            new BranchDTO("branch1", "sha1"),
                            new BranchDTO("branch2", "sha2")
                            )
                    )
                );    
	}


    @Test
	void testPathNotFound   () {

        webTestClient.get().uri("/notexisting/path")
             .exchange()
             .expectStatus().isNotFound();
	}

}
