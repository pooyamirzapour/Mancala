## Mancala Assignment
This project implements some APIs, and a simple clients for Mancala game.

### Approach
There are two different approaches: Firstly, running on a single browser, and Running on the multiple
browsers. The services are exposed based on REST. In the multiple browsers approach, after any movement, client will be
notified with the help of ***SseEmitter***. SseEmitter sends unidirectional asynchronous events
to any web app using Spring.
For more details please see the below links:

[SseEmitter]https://dzone.com/articles/server-sent-events-using-spring


[SseEmitter]https://www.baeldung.com/spring-mvc-sse-streams


## Technologies, Framework and Tools
-	Java 11
-	Spring Boot
-   Spring fox
-	H2 (in memory)
-	JUnit 5
-	Maven
-   Lombok
-   Docker
-   Map Structs
-   SseEmitter