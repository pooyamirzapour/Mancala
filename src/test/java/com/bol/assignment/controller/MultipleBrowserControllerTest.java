package com.bol.assignment.controller;

import com.bol.assignment.AbstractTest;
import com.bol.assignment.msg.KalahGameMsg;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

class MultipleBrowserControllerTest extends AbstractTest {

    @LocalServerPort
    private int localPort;

    @AfterEach
    void deleteBoard() {
        boardRepository.deleteAll();
        gameEmitterRepository.clear();
    }

    @Test
    void should_create_new_game_when_method_is_called() {
        ResponseEntity<KalahGameMsg> response = newGame();
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private ResponseEntity<KalahGameMsg> newGame() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        String uri = String.format("http://localhost:%s/api/v1/games", localPort);
        return testRestTemplate.exchange(uri, HttpMethod.POST, entity, KalahGameMsg.class);
    }

    @Test
    void should_join_game_when_method_is_called() {
        ResponseEntity<KalahGameMsg> kalahGameMsgResponseEntity = newGame();
        ResponseEntity<Resource> exchange = joinToGame(kalahGameMsgResponseEntity);
        Assertions.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        Assertions.assertEquals(1, gameEmitterRepository.get(kalahGameMsgResponseEntity.getBody().getGameId()).size());
    }

    private ResponseEntity<Resource> joinToGame(ResponseEntity<KalahGameMsg> kalahGameMsgResponseEntity) {
        String uri = String.format("http://localhost:%s/api/v1/games/join/%d", localPort, kalahGameMsgResponseEntity.getBody().getGameId());
        return restTemplate.getForEntity(uri, Resource.class);
    }

    @Test
    void should_update_game_boad_when_player_move() {
        ResponseEntity<KalahGameMsg> kalahGameMsgResponseEntity = newGame();

        String uri = String.format("http://localhost:%s/api/v1/games/%d/pits/%d", localPort,
                kalahGameMsgResponseEntity.getBody().getGameId(), 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Resource> exchange = testRestTemplate.exchange(uri, HttpMethod.POST, entity, Resource.class);

        Assertions.assertEquals(HttpStatus.OK, exchange.getStatusCode());


    }
}