## Test
In level of the microservice there are some tests, they are used for an automation test.
I have written some useful test classes under the “BolAssignment/src/test/java” folder which included service test, and api integration test.
I utilized spring-boot-test, JUnit and TestRestTemplate to cover unit testing and integration testing.
With the help of ***@TestPropertySource*** it is possibility of customize the application properties.

```
      mvn test
```