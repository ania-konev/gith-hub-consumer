package com.example.githubapiconsumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@PropertySource("classpath:/config.properties")
public class WebClientConfiguration {

    @Value("${github.Url}")
    private String githubUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(githubUrl).build();
    }
}
