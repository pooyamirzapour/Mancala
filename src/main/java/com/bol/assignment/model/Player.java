package com.bol.assignment.model;

import com.bol.assignment.util.GameUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Player is an entity class hold players.
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Data
@Entity
@AllArgsConstructor
public class Player extends BaseEntity {
    private Boolean turn=false;
    private int startPitNumber;

    /**
     * Constructs a new player.
     * @param start the player index
     */
    public Player(int start) {
        startPitNumber = start * GameUtil.PIT_FOR_PLAYER + 1;
        if (start==0)
            turn=true;
    }

    public Player() {

    }
}
