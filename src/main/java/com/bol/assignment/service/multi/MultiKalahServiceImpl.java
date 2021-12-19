package com.bol.assignment.service.multi;

import com.bol.assignment.component.GameEmitterRepository;
import com.bol.assignment.exception.ErrorCode;
import com.bol.assignment.exception.ServiceException;
import com.bol.assignment.model.Board;
import com.bol.assignment.model.Player;
import com.bol.assignment.repository.BoardRepository;
import com.bol.assignment.service.MoveRuleService;
import com.bol.assignment.util.GameUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;
import java.util.Optional;

/**
 * An implementation for playing game on a multiple browser.
 * Including newGame, join, and move.
 */
@Service
@Slf4j
@AllArgsConstructor
public class MultiKalahServiceImpl implements MultiKalahService {

    private BoardRepository boardRepository;
    private MoveRuleService moveRuleService;
    private GameEmitterRepository gameEmitterRepository;
    private NotificationService notificationService;

    @Override
    public Board newGame() {
        log.info("Start a New game");

        Board board = new Board();
        board.setPlayerOne(new Player(GameUtil.PLAYER_ONE));
        board.setPlayerTwo(new Player(GameUtil.PLAYER_TWO));
        board.setIsOver(false);
        board = boardRepository.save(board);

        log.info("A new game is started.");

        return board;
    }

    @Override
    public void joinToGame(int gameId, SseEmitter sseEmitter) {
        log.info("Join to a game");

        Optional<Board> optionalBoard = boardRepository.findById(gameId);
        if (optionalBoard.isEmpty())
            throw new ServiceException(ErrorCode.GAME_NOT_FOUND, String.valueOf(gameId));

        if (Objects.nonNull(gameEmitterRepository.get(gameId)) && gameEmitterRepository.get(gameId).size() >= GameUtil.SUPPORTED_PLAYER_NUMBER) {
            throw new ServiceException(ErrorCode.PLAYERS_ARE_FULL, String.valueOf(gameId));
        }

        gameEmitterRepository.put(gameId, sseEmitter);
        notificationService.sendToClients(optionalBoard.get());

        log.info("Join to a game Started");


    }


    @Override
    public void move(int gameId, int pitId) {
        log.info(String.format("Start moving. gameId: %d , pitId: %d ", gameId, pitId));

        Optional<Board> optionalBoard = boardRepository.findById(gameId);
        Board board = moveRuleService.validate(optionalBoard, gameId, pitId);
        moveRuleService.replace(board, pitId);
        moveRuleService.collectFront(board);
        moveRuleService.collectAll(board);
        moveRuleService.setTurn(board);
        board = boardRepository.save(board);

        notificationService.sendToClients(board);

        log.info(String.format("The move request successfully processed. gameId: %d , pitId: %d ", gameId, pitId));
    }


}
