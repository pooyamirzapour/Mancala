package com.bol.assignment.model;

import com.bol.assignment.util.GameUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor
public class Player extends BaseEntity {
    private Boolean turn;
    private int startPitNumber;

    /**
     * Constructs a new exception with the specified error code and detail message.
     * @param start the player index
     */
    public Player(int start) {
        startPitNumber = start * GameUtil.PIT_FOR_PLAYER + 1;
    }

    public Player() {

    }
}
