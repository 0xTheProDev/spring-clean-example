# spring-clean-example

[![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)
[![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)](https://tomcat.apache.org/)
[![OpenAPI](https://img.shields.io/badge/openapi-6BA539?style=for-the-badge&logo=openapi-initiative&logoColor=fff)](https://www.openapis.org/)
[![GraphQL](https://img.shields.io/badge/-GraphQL-E10098?style=for-the-badge&logo=graphql&logoColor=white)](https://graphql.org/)
[![Open Issues](https://img.shields.io/github/issues-raw/Progyan1997/spring-clean-example?style=for-the-badge)](https://github.com/Progyan1997/spring-clean-example/issues)
[![Closed Issues](https://img.shields.io/github/issues-closed-raw/Progyan1997/spring-clean-example?style=for-the-badge)](https://github.com/Progyan1997/spring-clean-example/issues?q=is%3Aissue+is%3Aclosed)
[![Open Pulls](https://img.shields.io/github/issues-pr-raw/Progyan1997/spring-clean-example?style=for-the-badge)](https://github.com/Progyan1997/spring-clean-example/pulls)
[![Closed Pulls](https://img.shields.io/github/issues-pr-closed-raw/Progyan1997/spring-clean-example?style=for-the-badge)](https://github.com/Progyan1997/spring-clean-example/pulls?q=is%3Apr+is%3Aclosed)
[![Contributors](https://img.shields.io/github/contributors/Progyan1997/spring-clean-example?style=for-the-badge)](https://github.com/Progyan1997/spring-clean-example/graphs/contributors)
[![Activity](https://img.shields.io/github/last-commit/Progyan1997/spring-clean-example?style=for-the-badge&label=most%20recent%20activity)](https://github.com/Progyan1997/spring-clean-example/pulse)

## Description

_Example Application Interface using Spring framework in Java_

This example showcases Repository Pattern in Hexagonal Architecture _(also known as Clean Architecture)_. Here we have two Entities - Books and Authors, whose relationships have been exploited to create CRUD endpoint in REST under OpenAPI standard.

## Installation

- Run the application using [Maven](https://maven.apache.org/):

  ```sh
  $ ./mvnw spring-boot:run
  ```

- For Windows users:

  ```powershell
  $ mvnw spring-boot:run
  ```

## Testing

- Run test suite along with Coverage reporting:

  ```sh
  $ ./mvnw jacoco:prepare-agent test install jacoco:report
  ```

  or for Windows

  ```powershell
  $ mvnw jacoco:prepare-agent test install jacoco:report
  ```

## Swagger UI

- Open Swagger UI at `localhost:8080/swagger-ui` after running the application.

## License

&copy; MIT License
