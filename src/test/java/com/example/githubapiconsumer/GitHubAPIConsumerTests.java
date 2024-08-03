// package com.example.githubapiconsumer;


// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
// import org.springframework.boot.test.mock.mockito.MockBean;


// import org.springframework.http.HttpStatusCode;
// import org.springframework.test.web.reactive.server.WebTestClient;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.reactive.function.client.WebClientResponseException;


// @WebFluxTest(GitHubUserController.class)
// class GitHubAPIConsumerTests {

// 	@Autowired
// 	private WebTestClient webClient;

// 	@MockBean
//     private WebClient mockedClient;

// 	@Test
// 	void testUserNotFound() {
// 		String username = "testUser";

// 		Mockito.when(mockedClient.get()
// 		// .uri(Mockito.anyString(), Mockito.anyString()))
// 		// .retrieve()
//             // .bodyToFlux(RepositoryInfo.class))
// 			.thenThrow(new WebClientResponseException(HttpStatusCode.valueOf(404), 
// 				"User not found", null, null, null, null));

// 		this.webClient.get().uri("/{username}", username).exchange().expectStatus().isNotFound()
// 		.expectBody(String.class).isEqualTo("Hello World");

// 	}


// 	@Test
// 	void testAllRepositoriesForked() {
// 	}


// }
