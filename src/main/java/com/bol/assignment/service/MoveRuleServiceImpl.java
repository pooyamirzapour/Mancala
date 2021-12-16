package com.bol.assignment.service;

import com.bol.assignment.exception.ErrorCode;
import com.bol.assignment.exception.ServiceException;
import com.bol.assignment.model.Board;
import com.bol.assignment.model.Player;
import com.bol.assignment.util.GameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class MoveRuleServiceImpl implements MoveRuleService {
    @Override
    public Board replace(Board board, int pitId) {
        Player currentPlayer = board.getCurrentPlayer();
        Map<Integer, Integer> pitsMap = board.getPitsMap();
        int currentPitId = pitId;
        int stones = pitsMap.get(pitId);
        pitsMap.put(pitId, 0);

        while (stones > 0) {
            currentPitId = getNextPitId(currentPitId, currentPlayer);
            pitsMap.put(currentPitId, pitsMap.get(currentPitId) + 1);
            stones--;
        }
        board.setLastPlacedPit(currentPitId);

        return board;
    }

    @Override
    public Board collectFront(Board board) {
        if (canCatchOpponentSideStones(board, board.getLastPlacedPit())) {
            int oppositeSidePitId = getOppositeSidePitId(board.getLastPlacedPit());
            int ownKalahPitId = getCurrentKalahPitId(board.getCurrentPlayer());
            board.getPitsMap().put(ownKalahPitId, board.getPitsMap().get(ownKalahPitId) + board.getPitsMap().get(board.getLastPlacedPit()) + board.getPitsMap().get(oppositeSidePitId));
            board.getPitsMap().put(board.getLastPlacedPit(), 0);
            board.getPitsMap().put(oppositeSidePitId, 0);
            log.info(String.format("Player catches stones from pit %d and %d", board.getLastPlacedPit(), oppositeSidePitId));
        }
        return board;
    }

    @Override
    public Board collectAll(Board board) {
        if (gameIsOver(board.getPitsMap(), board.getCurrentPlayer())) {
            log.info(String.format("Game is over with gameId: %d", board));
            board.setIsOver(true);
            gatherOpponentRemainingStone(board);
        }
        return board;
    }

    @Override
    public Board setTurn(Board board) {
        if (board.getLastPlacedPit() != getCurrentKalahPitId(board.getCurrentPlayer())) {
            if (board.getCurrentPlayer().getStartPitNumber() == GameUtil.STARTED_PIT_FOR_PLAYER_ONE) {
                board.getPlayerOne().setTurn(false);
                board.getPlayerTow().setTurn(true);
            }
            else if (board.getCurrentPlayer().getStartPitNumber() == GameUtil.STARTED_PIT_FOR_PLAYER_TWO) {
                board.getPlayerOne().setTurn(true);
                board.getPlayerTow().setTurn(false);
            }
        }
        return board;
    }

    @Override
    public Board validate(Optional<Board> optionalBoard, int gameId, int pitId) {
        if (!optionalBoard.isPresent())
            throw new ServiceException(ErrorCode.GAME_NOT_FOUND, String.valueOf(gameId));

        Board game = optionalBoard.get();
        if (game.getIsOver())
            throw new ServiceException(ErrorCode.GAME_IS_OVER, String.valueOf(gameId));

        if (isKalah(pitId))
            throw new ServiceException(ErrorCode.PIT_IS_KALAH, String.valueOf(gameId));

        if (!isOwnPit(pitId, game.getCurrentPlayer()))
            throw new ServiceException(ErrorCode.PIT_IS_NOT_YOURS, String.valueOf(pitId));

        Map<Integer, Integer> pitsMap = game.getPitsMap();
        if (Objects.isNull(pitsMap) || Objects.isNull(pitsMap.get(pitId)))
            throw new ServiceException(ErrorCode.INVALID_PIT, String.valueOf(pitId));

        if (pitsMap.get(pitId) == 0)
            throw new ServiceException(ErrorCode.PIT_IS_EMPTY, String.valueOf(pitId));

        return optionalBoard.get();
    }

    private boolean isOwnPit(int currentPitId, Player currentPlayer) {

        return (currentPitId >= currentPlayer.getStartPitNumber()
                && currentPitId < currentPlayer.getStartPitNumber() + GameUtil.PIT_COUNT);
    }

    private boolean isKalah(int pitId) {
        return (pitId % GameUtil.PIT_FOR_PLAYER) == 0;
    }

    private int getNextPitId(int currentPitId, Player currentPlayer) {
        // moving in counter clockwise direction
        int nextPitId = (currentPitId % GameUtil.TOTAL_PIT_COUNT) + 1;
        if (nextPitId != getCurrentKalahPitId(currentPlayer)
                && isKalah(nextPitId)) // is opponent Kalah position
            nextPitId = (nextPitId % GameUtil.TOTAL_PIT_COUNT) + 1;
        return nextPitId;
    }

    private int getCurrentKalahPitId(Player currentPlayer) {
        return (currentPlayer.getStartPitNumber() + GameUtil.PIT_COUNT);
    }

    private boolean canCatchOpponentSideStones(Board board, int currentPitId) {

        int oppositeSidePitId = getOppositeSidePitId(currentPitId);
        return isOwnPit(currentPitId, board.getCurrentPlayer()) &&
                board.getPitsMap().get(board.getLastPlacedPit()) == 1 && board.getPitsMap().get(oppositeSidePitId) > 0;
    }

    private int getOppositeSidePitId(int pitId) {
        int oppositeSidePitId = GameUtil.TOTAL_PIT_COUNT - pitId;
        return oppositeSidePitId;
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
}
