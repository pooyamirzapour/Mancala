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

/**
 * MoveRuleService interface provides and categorizes a move into some methods.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@Service
@Slf4j
public class MoveRuleServiceImpl implements MoveRuleService {
    @Override
    public void replace(Board board, int pitId) {
        log.info(String.format("Start seeds replacement. board: %s", board));

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
        log.info(String.format("Replaced seeds on pits. board: %s", board));

    }

    @Override
    public void collectFront(Board board) {
        if (canCatchFrontStones(board, board.getLastPlacedPit())) {
            int oppositeSidePitId = getFrontPitId(board.getLastPlacedPit());
            int ownKalahPitId = getCurrentKalahPitId(board.getCurrentPlayer());
            board.getPitsMap().put(ownKalahPitId, board.getPitsMap().get(ownKalahPitId) + board.getPitsMap().get(board.getLastPlacedPit()) + board.getPitsMap().get(oppositeSidePitId));
            board.getPitsMap().put(board.getLastPlacedPit(), 0);
            board.getPitsMap().put(oppositeSidePitId, 0);
            log.info(String.format("Player catches stones from pit %d and %d", board.getLastPlacedPit(), oppositeSidePitId));
        }
    }

    @Override
    public void collectAll(Board board) {
        if (gameIsOver(board.getPitsMap(), board.getCurrentPlayer())) {
            log.info(String.format("Game is over for Board: %s", board));
            board.setIsOver(true);
            gatherOpponentRemainingStone(board);
        }
    }

    @Override
    public void setTurn(Board board) {
        if (board.getLastPlacedPit() != getCurrentKalahPitId(board.getCurrentPlayer())) {
            if (board.getCurrentPlayer().getStartPitNumber() == GameUtil.STARTED_PIT_FOR_PLAYER_ONE) {
                board.getPlayerOne().setTurn(false);
                board.getPlayerTwo().setTurn(true);
            } else if (board.getCurrentPlayer().getStartPitNumber() == GameUtil.STARTED_PIT_FOR_PLAYER_TWO) {
                board.getPlayerOne().setTurn(true);
                board.getPlayerTwo().setTurn(false);
            }
            log.info(String.format("Set Turn. Board: %s", board));

        }
    }

    @Override
    public Board validate(Optional<Board> optionalBoard, String gameId, int pitId) {
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
        return (pitId % GameUtil.KALAH_ONE) == 0;
    }

    private int getNextPitId(int currentPitId, Player currentPlayer) {
        int nextPitId = (currentPitId % GameUtil.KALAH_TWO) + 1;
        if (nextPitId != getCurrentKalahPitId(currentPlayer)  && isKalah(nextPitId))
            nextPitId = (nextPitId % GameUtil.KALAH_TWO) + 1;
        return nextPitId;
    }

    private int getCurrentKalahPitId(Player currentPlayer) {
        return (currentPlayer.getStartPitNumber() + GameUtil.PIT_COUNT);
    }

    private boolean canCatchFrontStones(Board board, int currentPitId) {
        int oppositeSidePitId = getFrontPitId(currentPitId);
        return isOwnPit(currentPitId, board.getCurrentPlayer()) &&
                board.getPitsMap().get(board.getLastPlacedPit()) == 1 &&
                board.getPitsMap().get(oppositeSidePitId) > 0;
    }


    private int getFrontPitId(int pitId) {
        int oppositeSidePitId = GameUtil.KALAH_TWO - pitId;
        return oppositeSidePitId;
    }

    private boolean gameIsOver(Map<Integer, Integer> pitsMap, Player currentPlayer) {
        int startOwnPitId = currentPlayer.getStartPitNumber();
        int endOwnPitId = currentPlayer.getStartPitNumber() + GameUtil.PIT_COUNT - 1;
        return !pitsMap.entrySet().stream().
                filter(f -> f.getKey().compareTo(startOwnPitId) >= 0 && f.getKey().compareTo(endOwnPitId) <= 0).
                anyMatch(f -> f.getValue() > 0);
    }

    private void gatherOpponentRemainingStone(Board board) {
        int startOpponentPitId = board.getOpponentPlayer().getStartPitNumber();
        int endOpponentPitId = board.getOpponentPlayer().getStartPitNumber() + GameUtil.PIT_COUNT - 1;
        int opponentKalahPitId = endOpponentPitId + 1;

        int remainingStone = board.getPitsMap().entrySet().stream().
                filter(f -> f.getKey().compareTo(startOpponentPitId) >= 0 && f.getKey().compareTo(endOpponentPitId) <= 0).
                map((Map.Entry::getValue))
                .reduce(0, (a, b) -> a + b);

        board.getPitsMap().entrySet().stream().
                filter(f -> f.getKey().compareTo(startOpponentPitId) >= 0 && f.getKey().compareTo(endOpponentPitId) <= 0)
                .forEach(
                        f -> f.setValue(0)
                );
        board.getPitsMap().put(opponentKalahPitId, board.getPitsMap().get(opponentKalahPitId) + remainingStone);
    }
}
