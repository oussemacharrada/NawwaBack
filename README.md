# Nawwa Backend

This project serves as a service providing backend designed to work with IOS android or web applications .

The server itself is implemented in [Java](https://www.java.com/en/) using [Spring Boot]([http://www.sinatrarb.com/](https://spring.io/projects/spring-boot)).
## Features:
  - user authentication and autherisation
  - Dao CRUD operations for a service providing website 
  - data handling 
   
## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use 

1. Build the project using
```shell
  `mvn clean install`
```
2. Run using 
```shell
  `mvn spring-boot:run`
```
3. The web application is accessible via localhost:8080
