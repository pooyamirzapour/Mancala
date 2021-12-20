package com.bol.assignment.service.single;

import com.bol.assignment.model.Board;
import com.bol.assignment.model.Player;
import com.bol.assignment.repository.BoardRepository;
import com.bol.assignment.service.MoveRuleService;
import com.bol.assignment.util.GameUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * An implementation for playing game on a single browser.
 * Including newGame, and move.
 */
@Service
@Slf4j
@AllArgsConstructor
public class SingleKalahServiceImpl implements SingleKalahService {

    private BoardRepository boardRepository;
    private MoveRuleService moveRuleService;

    @Override
    public Board newGame() {
        log.info("Start a new single browser game");

        Board board = new Board();
        board.setPlayerOne(new Player(GameUtil.PLAYER_ONE));
        board.setPlayerTwo(new Player(GameUtil.PLAYER_TWO));
        board.setIsOver(false);
        board = boardRepository.save(board);

        log.info("A new single browser kalah game is started. ");

        return board;
    }


    @Override
    public Board move(String gameId, int pitId) {
        log.info(String.format("Start moving single browser. gameId: %s , pitId: %d ", gameId, pitId));

        Optional<Board> optionalBoard = boardRepository.findById(gameId);
        Board board = moveRuleService.validate(optionalBoard, gameId, pitId);
        moveRuleService.replace(board, pitId);
        moveRuleService.collectFront(board);
        moveRuleService.collectAll(board);
        moveRuleService.setTurn(board);
        board = boardRepository.save(board);

        log.info(String.format("The move request successfully processed. gameId: %s , pitId: %d ", gameId, pitId));
        return board;
    }


}
