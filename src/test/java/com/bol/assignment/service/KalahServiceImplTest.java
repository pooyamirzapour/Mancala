package com.bol.assignment.service;

import com.bol.assignment.AbstractTest;
import com.bol.assignment.exception.ServiceException;
import com.bol.assignment.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class KalahServiceImplTest extends AbstractTest {

    @Test
    void should_make_board_when_param_is_provided() {
        Board board = kalahService.newGame();
        Assertions.assertEquals(6, board.getPitsMap().get(1));
        Assertions.assertEquals(0, board.getPitsMap().get(7));
        Assertions.assertEquals(6, board.getPitsMap().get(8));
        Assertions.assertEquals(0, board.getPitsMap().get(14));
        Assertions.assertEquals(1, board.getId());
    }

    @Test
    void should_join_game_when_board_is_valid() {
        Board board = kalahService.newGame();
        SseEmitter sseEmitter = new SseEmitter();
        kalahService.joinToGame(board.getId(), sseEmitter);
        List<SseEmitter> sseEmitters = gameEmitterRepository.get(board.getId());
        Assertions.assertEquals(sseEmitter,sseEmitters.get(0));
        boardRepository.deleteAll();
    }

    @Test
    void should_throw_exception_when_gameId_is_invalid() {
        SseEmitter sseEmitter = new SseEmitter();
        Assertions.assertThrows(ServiceException.class,()->kalahService.joinToGame(2, sseEmitter));
    }


    @Test
    void move() {
    }
}