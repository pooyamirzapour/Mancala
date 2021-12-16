package com.bol.assignment.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * This class holds all variables to initiate a new kala game .
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
public class GameUtil {
    public final static int INITIAL_STONE = 6;
    public final static int PIT_COUNT = 6;
    public final static int PLAYER_ONE = 0;
    public final static int PLAYER_TWO = 1;
    public final static int PIT_FOR_PLAYER = PIT_COUNT + 1;
    public final static int TOTAL_PIT_COUNT = PIT_FOR_PLAYER * 2;
    public final static int SUPPORTED_PLAYER_NUMBER=2;
    public final static int STARTED_PIT_FOR_PLAYER_ONE=1;
    public final static int STARTED_PIT_FOR_PLAYER_TWO=8;

    public static String getGameUrl(int gameId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/games/{gameId}").buildAndExpand(gameId).toUriString();
    }
}
