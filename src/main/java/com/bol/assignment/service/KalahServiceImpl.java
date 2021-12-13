package com.bol.assignment.service;

import com.bol.assignment.exception.ErrorCode;
import com.bol.assignment.exception.ServiceException;
import com.bol.assignment.model.Board;
import com.bol.assignment.model.Player;
import com.bol.assignment.repository.BoardRepository;
import com.bol.assignment.util.GameUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class KalahServiceImpl implements KalahService {

    private BoardRepository boardRepository;

    @Override
    public Board newGame() {
        log.info("Start a New game");
        Board board = new Board();
        Player player_one = new Player(GameUtil.PLAYER_ONE);
        Player player_two = new Player(GameUtil.PLAYER_TWO);
        log.info("turn is for player one");
        player_one.setTurn(true);
        player_two.setTurn(false);
        board.setPlayerOne(player_one);
        board.setPlayerTow(player_two);
        board.setIsOver(false);
        board = boardRepository.save(board);
        log.info("A new kalah game is started. ");
        return board;
    }

    // continuing kala game by moving a pit
    @Override
    public Board move(int gameId, int pitId) {
        log.info(String.format("Start moving. gameId: %d , pitId: %d ", gameId, pitId));
        Optional<Board> gameOptional = boardRepository.findById(gameId);
        validateNewMoveRequest(gameOptional, gameId, pitId);

        Board game = gameOptional.get();
        Player currentPlayer = game.getCurrentPlayer();
        Map<Integer, Integer> pitsMap = game.getPitsMap();
        int currentPitId = pitId;
        int stones = pitsMap.get(pitId);
        pitsMap.put(pitId, 0);

        while (stones > 0) {

            currentPitId = getNextPitId(currentPitId, currentPlayer);
            pitsMap.put(currentPitId, pitsMap.get(currentPitId) + 1);
            stones--;
        }

        if (canCatchOpponentSideStones(game, currentPitId)) {
            int oppositeSidePitId = getOppositeSidePitId(currentPitId);
            int ownKalahPitId = getCurrentKalahPitId(currentPlayer);
            pitsMap.put(ownKalahPitId, pitsMap.get(ownKalahPitId) + pitsMap.get(currentPitId) + pitsMap.get(oppositeSidePitId));
            pitsMap.put(currentPitId, 0);
            pitsMap.put(oppositeSidePitId, 0);
            log.info(String.format("Player catches stones from pit %d and %d", currentPitId, oppositeSidePitId));
        }

        if (gameIsOver(pitsMap, currentPlayer)) {
            log.info(String.format("Game is over with gameId: %d", gameId));
            game.setIsOver(true);
            gatherOpponentRemainingStone(game);
        }
        // if last stone in in own kalah current player gets another turn
        if (currentPitId == getCurrentKalahPitId(currentPlayer)) {
            currentPlayer.setTurn(true);
            game.getOpponentPlayer().setTurn(false);
        } else {
            game.getOpponentPlayer().setTurn(true);
            currentPlayer.setTurn(false);
        }

        game = boardRepository.save(game);
        log.info(String.format("The move request successfully processed. gameId: %d , pitId: %d ", gameId, pitId));
        return game;
    }

    private void validateNewMoveRequest(Optional<Board> gameOptional, int gameId, int pitId) {
            if (!gameOptional.isPresent()) {
            throw new ServiceException(ErrorCode.GAME_NOT_FOUND, String.valueOf(gameId));
        }

        Board game = gameOptional.get();
        if (game.getIsOver()) {
            throw new ServiceException(ErrorCode.GAME_IS_OVER, String.valueOf(gameId));
        }

        if (isKalah(pitId)) {
            throw new ServiceException(ErrorCode.PIT_IS_KALAH, String.valueOf(gameId));
        }

        if (!isOwnPit(pitId, game.getCurrentPlayer())) {
            throw new ServiceException(ErrorCode.PIT_IS_NOT_YOURS, String.valueOf(pitId));
        }

        Map<Integer, Integer> pitsMap = game.getPitsMap();
        if (Objects.isNull(pitsMap) || Objects.isNull(pitsMap.get(pitId))) {
            throw new ServiceException(ErrorCode.INVALID_PIT, String.valueOf(pitId));
        }

        if (pitsMap.get(pitId) == 0) {
            throw new ServiceException(ErrorCode.PIT_IS_EMPTY, String.valueOf(pitId));
        }
    }

    private int getNextPitId(int currentPitId, Player currentPlayer) {
        // moving in counter clockwise direction
        int nextPitId = (currentPitId % GameUtil.TOTAL_PIT_COUNT) + 1;
        if (nextPitId != getCurrentKalahPitId(currentPlayer)
                && isKalah(nextPitId)) // is opponent Kalah position
            nextPitId = (nextPitId % GameUtil.TOTAL_PIT_COUNT) + 1;
        return nextPitId;
    }

    private boolean gameIsOver(Map<Integer, Integer> pitsMap, Player currentPlayer) {
        boolean gameIsOver = true;
        int startOwnPitId = currentPlayer.getStartPitNumber();
        int endOwnPitId = currentPlayer.getStartPitNumber() + GameUtil.PIT_COUNT - 1;

        for (int pitId = startOwnPitId; pitId <= endOwnPitId; pitId++) {
            if (pitsMap.get(pitId) > 0) {
                gameIsOver = false;
            }
        }
        return gameIsOver;
    }

    private void gatherOpponentRemainingStone(Board game) {
        int startOpponentPitId = game.getOpponentPlayer().getStartPitNumber();
        int endOpponentPitId = game.getOpponentPlayer().getStartPitNumber() + GameUtil.PIT_COUNT - 1;
        int opponentKalahPitId = endOpponentPitId + 1;
        int remainingStone = 0;
        for (int pitId = startOpponentPitId; pitId <= endOpponentPitId; pitId++) {
            remainingStone += game.getPitsMap().get(pitId);
            game.getPitsMap().put(pitId, 0);
        }

        game.getPitsMap().put(opponentKalahPitId, game.getPitsMap().get(opponentKalahPitId) + remainingStone);
    }

    private int getCurrentKalahPitId(Player currentPlayer) {
        return (currentPlayer.getStartPitNumber() + GameUtil.PIT_COUNT);
    }

    private boolean isOwnPit(int currentPitId, Player currentPlayer) {

        return (currentPitId >= currentPlayer.getStartPitNumber()
                && currentPitId < currentPlayer.getStartPitNumber() + GameUtil.PIT_COUNT);
    }

    private boolean canCatchOpponentSideStones(Board game, int currentPitId) {

        int oppositeSidePitId = getOppositeSidePitId(currentPitId);
        return isOwnPit(currentPitId, game.getCurrentPlayer()) &&
                game.getPitsMap().get(currentPitId) == 1 && game.getPitsMap().get(oppositeSidePitId) > 0;
    }

    private boolean isKalah(int pitId) {
        return (pitId % GameUtil.PIT_FOR_PLAYER) == 0;
    }

    private int getOppositeSidePitId(int pitId) {
        int oppositeSidePitId = GameUtil.TOTAL_PIT_COUNT - pitId;
        return oppositeSidePitId;
    }

}
