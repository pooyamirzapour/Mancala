## Mancala Assignment
This project implements some APIs, and a simple clients for Mancala game.

## Approach
There are two different approaches: Firstly, running on a single browser, and Running on the multiple
browsers. The services are exposed based on REST. In the multiple browsers approach, after any movement, client will be
notified with the help of ***SseEmitter***. SseEmitter sends unidirectional asynchronous events
to any web app using Spring.
For more details please see the below links:

[SseEmitter]https://dzone.com/articles/server-sent-events-using-spring


[SseEmitter]https://www.baeldung.com/spring-mvc-sse-streams

## Implementation
The move rules of the game have been implemented in ***MoveRuleServiceImpl*** class with different methods,
and in the ***SingleKalahServiceImpl*** and ***MultiKalahServiceImpl*** classes these methods and rules have been 
ordered and orchestrated. (That is based on ***Method Abstraction Levels*** in ***Clean Code*** book by ***Robert Martin***).
If you wish change one of the rules or some, you can define your implementation 
of ***MoveRuleService*** and with the help of ***@Qualifier*** or ***@Primary*** use your own rules.
(That is the ***Open Close Principle of SOLID***)



## Technologies, Framework and Tools
-	Java 11
-	Spring Boot
-	Maven
-   Lombok
-   Spring fox
-	JUnit 5
-   Map Structs
-   SseEmitter
-   Docker
-	MongoDB
-   TestContainer
