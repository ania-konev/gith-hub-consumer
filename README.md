# GitHub API Consumer

This Spring Boot application retrieves and displays a list of GitHub repositories for a specified user with header “Accept: application/json”, excluding forked repositories. Additionally, it provides branch details for each repository with its name and last commit sha.

- Backing API: https://developer.github.com/v3

## Quickstart

### Requirements

- Java 22
- Spring Boot 3
- Gradle 8

> [!NOTE]  
> Gradle and Spring Boot are automatically installed in the installation step through `gradlew` command.

### Installation

To install and run the application first make sure you have Java 22, it can be installed following instructions from [here](https://jdk.java.net/22/).

To install dependencies and build the application through Gradle you have run:

```
./gradlew build
```

> [!NOTE]  
> `gradlew` is present as `gradlew.bat` command on Windows.

### To start the application

```
gradle bootRun
```

## API Endpoints

- GET: http://localhost:8080/{username}

## Internals Insight

The application works in the following way:

- WebClient from Spring WebFlux is used to perform asynchronous HTTP requests to provide a reactive API for fetching GitHub repository and branch information.
- Exception handling is used to manage the errors.
