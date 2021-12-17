package com.bol.assignment.service;

import com.bol.assignment.AbstractTest;
import com.bol.assignment.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void joinToGame() {
    }

    @Test
    void move() {
    }
}