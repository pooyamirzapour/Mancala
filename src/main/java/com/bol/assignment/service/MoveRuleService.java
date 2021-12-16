package com.bol.assignment.service;

import com.bol.assignment.model.Board;

import java.util.Optional;

/**
 * MoveRuleService interface provides and categorizes a move into some methods.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

public interface MoveRuleService {

    /**
     * rearrange the board game based on a move
     *
     * @param board
     * @param pitId
     */
    void replace(Board board, int pitId);


    /**
     * Based on the rule it might collect seeds from the front pit.
     *
     * @param board
     */
    void collectFront(Board board);


    /**
     * If the game is over, the method will collect all seeds in favor of the side has seed.
     *
     * @param board
     */
    void collectAll(Board board);


    /**
     * The method set the turn of players.
     *
     * @param board
     */
    void setTurn(Board board);


    /**
     * It validates the move of a player.
     *
     * @param optionalBoard
     * @param gameId
     * @param pitId
     * @return an instance of Board
     */
    Board validate(Optional<Board> optionalBoard, int gameId, int pitId);

}
