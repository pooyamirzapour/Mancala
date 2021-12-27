# Build and Run
Build and Run DockerCompose in root folder, it runs an instance of mongo:
```
docker-compose up 
```
## to see images:
```
docker image ls
```

## to see all container:
```
docker container ls
```
## to stop a container
```
docker container stop ${container ID}
```
## to remove the container
```
docker container rm ${container ID}
```


## Run
This project can be run by two ways, please note that first, the MongoDB should be run on your Docker:
1.	Run com.bol.assignment.AssignmentApplication class from your IDE.
2.	Run this command in "BolAssignment" folder by:
      ```
      mvn package
      ```
      and then run:
      
      `
      java -jar /target/Mancala-0.0.1-SNAPSHOT.jar
      `
      


Web service URL to start a new game on a single browser **http://localhost:8087/api/v1/games/single**

Web service URL to start a new game on a multiple browser **http://localhost:8087/api/v1/games**

Web service URL to join the existing game **http://localhost:8087/api/v1/games/join/{gameId}**

Web service URL to move from a pit on single browser **http://localhost:8087/api/v1/games/single/{gameId}/pits/{pitId}**

Web service URL to move from a pit on multiple browser **http://localhost:8087/api/v1/games/{gameId}/pits/{pitId}**


After running the project it can be used by any rest client or swagger.
You can open Swagger by this link: **http://localhost:8087/swagger-ui.html**