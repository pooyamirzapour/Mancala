package com.bol.assignment.model;

import com.bol.assignment.msg.GameStatusMsg;
import com.bol.assignment.util.GameUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Board is an entity class hold the game last status.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Data
@Entity
public class Board extends BaseEntity {

    @MapKeyColumn(name = "ID")

    @Column(name = "STONE")

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PIT", joinColumns = @JoinColumn(name = "BOARD_ID"))
    private Map<Integer, Integer> pitsMap;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PLAYER_ONE_ID")
    private Player playerOne;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PLAYER_TWO_ID")
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
            gameStatusMsg.setPlayerId(this.playerOne.getId());
        } else {
            gameStatusMsg.setPlayerId(this.playerTwo.getId());
        }
        return gameStatusMsg;
    }


}
