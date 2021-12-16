package com.bol.assignment.service;

import com.bol.assignment.component.GameEmitterRepository;
import com.bol.assignment.exception.ErrorCode;
import com.bol.assignment.exception.ServiceException;
import com.bol.assignment.model.Board;
import com.bol.assignment.model.Player;
import com.bol.assignment.msg.GameStatusMsg;
import com.bol.assignment.repository.BoardRepository;
import com.bol.assignment.util.GameUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class KalahServiceImpl implements KalahService {

    private BoardRepository boardRepository;
    private MoveRuleService moveRuleService;
    private GameEmitterRepository gameEmitterRepository;
    private NotificationService notificationService;

    @Override
    public Board newGame() {
        log.info("Start a New game");

        Board board = new Board();
        Player player_one = new Player(GameUtil.PLAYER_ONE);
        Player player_two = new Player(GameUtil.PLAYER_TWO);
        player_one.setTurn(true);
        player_two.setTurn(false);
        board.setPlayerOne(player_one);
        board.setPlayerTow(player_two);
        board.setIsOver(false);
        board = boardRepository.save(board);

        log.info("A new kalah game is started. ");

        return board;
    }

    @Override
    public void joinToGame(int gameId, SseEmitter sseEmitter) {
        Optional<Board> optionalBoard = boardRepository.findById(gameId);
        if (optionalBoard.isEmpty())
            throw new ServiceException(ErrorCode.GAME_NOT_FOUND, String.valueOf(gameId));

        if (Objects.nonNull(gameEmitterRepository.get(gameId)) && gameEmitterRepository.get(gameId).size() >= GameUtil.SUPPORTED_PLAYER_NUMBER) {
            throw new ServiceException(ErrorCode.PLAYERS_ARE_FULL, String.valueOf(gameId));
        }

        gameEmitterRepository.put(gameId, sseEmitter);
        notificationService.sendToClients(optionalBoard.get());

    }


    @Override
    public Board move(int gameId, int pitId) {
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
        return board;
    }


}
