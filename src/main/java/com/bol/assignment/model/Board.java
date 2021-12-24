package com.bol.assignment.model;

import com.bol.assignment.msg.GameStatusMsg;
import com.bol.assignment.util.GameUtil;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Board is an entity class hold the game last status.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Data
@Document
public class Board {

    @Id
    private String id=UUID.randomUUID().toString();
    private Map<Integer, Integer> pitsMap;
    private Player playerOne;
    private Player playerTwo;
    private Boolean isOver;
    @Transient
    private int lastPlacedPit;


    /**
     * Constructs a new board and initiating pits.
     */
    public Board() {
        pitsMap = new HashMap<>();
        for (int pitId = 1; pitId < GameUtil.KALAH_TWO; pitId++) {
            pitsMap.put(pitId, GameUtil.INITIAL_STONE);
        }
        pitsMap.put(GameUtil.KALAH_ONE, 0);
        pitsMap.put(GameUtil.KALAH_TWO, 0);
    }

    public Player getCurrentPlayer() {
        return playerOne.getTurn() ? playerOne : playerTwo;
    }

    public Player getOpponentPlayer() {
        return playerOne.getTurn() ? playerTwo : playerOne;
    }

    public GameStatusMsg getGameStatusMsg() {
        GameStatusMsg gameStatusMsg = new GameStatusMsg(this.getId());
        gameStatusMsg.setPitsMap_playerOne(this.getPitsMap());

        if (playerOne.getTurn()) {
            gameStatusMsg.setPlayerId(1);
        } else {
            gameStatusMsg.setPlayerId(2);
        }
        return gameStatusMsg;
    }


}
