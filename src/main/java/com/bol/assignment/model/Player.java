package com.bol.assignment.model;

import com.bol.assignment.util.GameUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;


/**
 * Player is an entity class hold players.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Data
@AllArgsConstructor
public class Player {

    @Id
    private int id;

    private Boolean turn = false;
    private int startPitNumber;

    /**
     * Constructs a new player.
     *
     * @param start the player index
     */
    public Player(int start) {
        startPitNumber = start * GameUtil.KALAH_ONE + 1;
        if (start == 0) {
            turn = true;
            id = 1;
        } else
            id = 2;
    }

    public Player() {

    }
}
