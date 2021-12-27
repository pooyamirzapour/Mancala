package com.bol.assignment.service;

import com.bol.assignment.AbstractTest;
import com.bol.assignment.exception.ServiceException;
import com.bol.assignment.model.Board;
import com.bol.assignment.util.GameUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class MoveRuleServiceImplTest extends AbstractTest {

    private Board board;

    @BeforeEach
    private void makeBoard() {
        board = kalahService.newGame();

    }

    @AfterEach
    private void deleteBoard() {
        boardRepository.deleteAll();

    }

    @Test
    void should_rearrange_game_when_pit_one_moved() {
        ruleService.replace(board, 1);
        Assertions.assertEquals(0, board.getPitsMap().get(1));
        Assertions.assertEquals(7, board.getPitsMap().get(2));
        Assertions.assertEquals(1, board.getPitsMap().get(7));
        Assertions.assertEquals(6, board.getPitsMap().get(8));
    }

    @Test
    void should_throw_exception_when_pit_is_kalah() {
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.of(board), board.getId(), 7));
    }

    @Test
    void should_throw_exception_when_game_not_found() {
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.empty(), "2000", 1));
    }

    @Test
    void should_throw_exception_when_game_is_over() {
        board.setIsOver(true);
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.of(board), board.getId(), 1));
    }

    @Test
    void should_throw_exception_when_pit_is_not_yours() {
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.of(board), board.getId(), 8));
    }

    @Test
    void should_throw_exception_when_pit_is_empty() {
        ruleService.replace(board, 1);
        Assertions.assertEquals(0, board.getPitsMap().get(1));
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.of(board), board.getId(), 1));
    }

    @Test
    void should_throw_exception_when_pit_is_invalid() {
        board.setPitsMap(null);
        Assertions.assertThrows(ServiceException.class, () -> ruleService.validate(Optional.of(board), "1", 1));
    }

    @Test
    void should_collect_from_front_when_pit_move_to_empty_pit() {
        ruleService.replace(board,1);
        ruleService.setTurn(board);
        ruleService.replace(board,2);
        ruleService.setTurn(board);
        ruleService.replace(board,8);
        ruleService.setTurn(board);
        ruleService.replace(board,1);
        ruleService.collectFront(board);
        Assertions.assertEquals(0,board.getPitsMap().get(2));
        Assertions.assertEquals(0,board.getPitsMap().get(12));

    }

    @Test
    void collectAll() {
    }

    @Test
    void should_set_players_turn_when_pit_is_moved() {
        ruleService.replace(board,1);
        ruleService.setTurn(board);
        Assertions.assertEquals(GameUtil.STARTED_PIT_FOR_PLAYER_ONE,board.getCurrentPlayer().getStartPitNumber());

        ruleService.replace(board,2);
        ruleService.setTurn(board);
        Assertions.assertEquals(GameUtil.STARTED_PIT_FOR_PLAYER_TWO,board.getCurrentPlayer().getStartPitNumber());
    }


}