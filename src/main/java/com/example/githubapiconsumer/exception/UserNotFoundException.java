package com.example.githubapiconsumer.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User %s not found".formatted(username));
    }
}