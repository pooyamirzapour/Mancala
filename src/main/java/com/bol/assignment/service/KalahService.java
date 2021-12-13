package com.bol.assignment.service;

import com.bol.assignment.model.Board;

public interface     KalahService {

    /**
     * Creates a new Kalah game.
     * @return an instance of Board
     */
    Board newGame();

    /**
     * Performs a move from selected pit.
     * @param gameId identifier of the game
     * @param pitId identifier of the pit
     * @return an instance of KalahGameBoardDto as the latest status of the game
     */
    Board move(int gameId, int pitId);


}
