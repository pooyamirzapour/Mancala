package com.bol.assignment.service;

import com.bol.assignment.AbstractTest;
import com.bol.assignment.exception.ServiceException;
import com.bol.assignment.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class MoveRuleServiceImplTest extends AbstractTest {


    @Test
    void should_rearrange_game_when_pit_one_moved() {
        Board board = kalahService.newGame();
        ruleService.replace(board, 1);
        Assertions.assertEquals(0, board.getPitsMap().get(1));
        Assertions.assertEquals(7, board.getPitsMap().get(2));
        Assertions.assertEquals(1, board.getPitsMap().get(7));
        Assertions.assertEquals(6, board.getPitsMap().get(8));
        boardRepository.deleteAll();
    }

    @Test
    void should_throw_exception_when_pit_is_kalah() {
        Board board = kalahService.newGame();
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.of(board), 1, 0));
        boardRepository.deleteAll();
    }

    @Test
    void should_throw_exception_when_game_not_found() {
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.empty(), 2000, 1));
        boardRepository.deleteAll();
    }

    @Test
    void should_throw_exception_when_game_is_over() {
        Board board = kalahService.newGame();
        board.setIsOver(true);
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.of(board), 1, 1));
        boardRepository.deleteAll();
    }

    @Test
    void should_throw_exception_when_pit_is_not_yours() {
        Board board = kalahService.newGame();
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.of(board), 1, 8));
        boardRepository.deleteAll();
    }

    @Test
    void should_throw_exception_when_pit_is_empty() {
        Board board = kalahService.newGame();
        ruleService.replace(board, 1);
        Assertions.assertEquals(0, board.getPitsMap().get(1));
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.of(board), 1, 1));
        boardRepository.deleteAll();
    }

    @Test
    void collectFront() {
    }

    @Test
    void collectAll() {
    }

    @Test
    void setTurn() {
    }

    @Test
    void validate() {
    }
}