## Test
In level of the microservice there are some tests, they are used for an automation test.
I have written some useful test classes under the ***“BolAssignment/src/test/java”*** folder which included service test,
and api integration test.

I utilized ***spring-boot-test***, ***JUnit5*** and ***TestRestTemplate*** to cover unit testing and integration testing.
With the help of ***@TestPropertySource*** it is possible to customize the application properties.
With the help of ***TestContainer***, the ***MongoDB*** will be added to the 
***Spring Application Context*** in the test time. For testing the application, being ***Docker*** up is necessary.
If there is not an image of ***Mongo*** on your ***Docker***, it takes time to pull the image and run for the first time.

```
      mvn test
```