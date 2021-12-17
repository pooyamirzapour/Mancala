package com.bol.assignment.service.single;

import com.bol.assignment.model.Board;

public interface SingleKalahService {

    /**
     * Creates a new single browser Kalah game.
     *
     * @return an instance of Board
     */
    Board newGame();


    /**
     * Performs a move from selected pit. It orchestrate the methods from MoveRuleService.
     *
     * @param gameId identifier of the game
     * @param pitId  identifier of the pit
     * @return an updated game board after move
     */
    Board move(int gameId, int pitId);


}
