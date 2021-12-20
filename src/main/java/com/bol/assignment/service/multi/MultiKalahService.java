package com.bol.assignment.service.multi;

import com.bol.assignment.model.Board;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * KalahService interface provides some methods to play the game.
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
public interface MultiKalahService {

    /**
     * Creates a new Kalah game.
     *
     * @return an instance of Board
     */
    Board newGame();


    /**
     * Join to a game
     *
     * @param gameId
     * @return an existing instance of Board
     */
    void joinToGame(String gameId,SseEmitter sseEmitter) ;


    /**
     * Performs a move from selected pit. It orchestrate the methods from MoveRuleService.
     *
     * @param gameId identifier of the game
     * @param pitId  identifier of the pit
     */
    void move(String gameId, int pitId);


}
