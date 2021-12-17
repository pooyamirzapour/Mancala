## Mancala Assignment
This project implements some APIs, and a simple clients for Mancala game.

### Approach
There are two different approaches: Firstly, running on a single browser, and Running on multiple
browsers. The services are exposed based on REST. In the multiple browsers approach, after any movement, client will be
notified with the help of ***SseEmitter***.

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