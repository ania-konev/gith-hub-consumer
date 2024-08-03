package com.example.githubapiconsumer;  

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ErrorData(int status, String message) {}
