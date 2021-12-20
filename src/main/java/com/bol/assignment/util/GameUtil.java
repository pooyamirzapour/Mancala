package com.bol.assignment.util;

import com.bol.assignment.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * This class holds all variables to initiate a new kala game .
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@Service
public class GameUtil {

    private AppConfig appConfig;

    @Autowired
    public GameUtil(AppConfig appConfig)
    {
        this.appConfig=appConfig;
    }

    public final static int INITIAL_STONE = 6;
    public final static int PIT_COUNT = 6;
    public final static int PLAYER_ONE = 0;
    public final static int PLAYER_TWO = 1;
    public final static int KALAH_ONE = PIT_COUNT + 1;
    public final static int KALAH_TWO = KALAH_ONE * 2;
    public final static int SUPPORTED_PLAYER_NUMBER=2;
    public final static int STARTED_PIT_FOR_PLAYER_ONE=1;
    public final static int STARTED_PIT_FOR_PLAYER_TWO=8;

    public String getSingleGameUrl(String gameId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(appConfig.getGameSinglePath()).buildAndExpand(gameId).toUriString();
    }

    public String getGameUrl(String gameId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(appConfig.getGamePath()).buildAndExpand(gameId).toUriString();
    }
}
