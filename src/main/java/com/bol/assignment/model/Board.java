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
    private Player playerTow;

    private Boolean isOver;

    @Transient
    private int lastPlacedPit;


    /**
     * Constructs a new board and initiating pits.
     */
    public Board() {
        pitsMap = new HashMap<>();
        // initialing pits with total pit count
        for (int pitId = 1; pitId < GameUtil.TOTAL_PIT_COUNT; pitId++) {
            if (pitId != GameUtil.PIT_FOR_PLAYER)
                pitsMap.put(pitId, GameUtil.INITIAL_STONE);
        }
        pitsMap.put(GameUtil.PIT_FOR_PLAYER, 0);  // kala for palayer one
        pitsMap.put(GameUtil.TOTAL_PIT_COUNT, 0); // kala for palayer two
    }

    public Player getCurrentPlayer() {
        return playerOne.getTurn() ? playerOne : playerTow;
    }

    public Player getOpponentPlayer() {
        return playerOne.getTurn() ? playerTow : playerOne;
    }

    public GameStatusMsg getGameStatusMsg() {
        GameStatusMsg gameStatusMsg = new GameStatusMsg(this.getId());
        gameStatusMsg.setPitsMap_playerOne(this.getPitsMap());

        if (playerOne.getTurn()) {
            gameStatusMsg.setPlayerId(this.playerOne.getId());
        } else {
            gameStatusMsg.setPlayerId(this.playerTow.getId());
        }
        return gameStatusMsg;
    }


}
