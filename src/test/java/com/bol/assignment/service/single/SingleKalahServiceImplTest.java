package com.bol.assignment.service.single;

import com.bol.assignment.AbstractTest;
import com.bol.assignment.exception.ServiceException;
import com.bol.assignment.model.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.junit.jupiter.api.Assertions.*;

class SingleKalahServiceImplTest extends AbstractTest {

    @AfterEach
    void deleteBoard()
    {
        boardRepository.deleteAll();
    }

    @Test
    void should_make_board_when_param_is_provided() {
        Board board = singleKalahService.newGame();
        Assertions.assertEquals(6, board.getPitsMap().get(1));
        Assertions.assertEquals(0, board.getPitsMap().get(7));
        Assertions.assertEquals(6, board.getPitsMap().get(8));
        Assertions.assertEquals(0, board.getPitsMap().get(14));
    }

    @Test
    void should_throw_exception_when_gameId_is_invalid() {
        Assertions.assertThrows(ServiceException.class,()->singleKalahService.move("2", 1));
    }

    @Test
    void should_update_game_board_when_move_is_correct() {
        Board board = singleKalahService.newGame();
        board = singleKalahService.move(board.getId(), 1);
        Assertions.assertEquals(0,board.getPitsMap().get(1));
    }


}