package com.bol.assignment.controller;

import com.bol.assignment.model.Board;
import com.bol.assignment.msg.GameStatusMsg;
import com.bol.assignment.msg.KalahGameMsg;
import com.bol.assignment.service.KalahService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Rest controller class to start a new game and move on the game.
 *
 * @author Pooya Mirzapour (Pooyamirzapour@gmail.com)
 */

@RestController
@Api("Provides set of endpoints to create, and play a Kalah game.")
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/games")

public class KalahController {

    private KalahService kalahService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Staring a new Kalah game.")
    public KalahGameMsg newGame() {
        log.info("A new game request received");
        Board board = kalahService.newGame();
        KalahGameMsg kalahGameMsg = KalahGameMsg.builder().gameId(board.getId()).build();
        kalahGameMsg.setUrl(getGameUrl(kalahGameMsg.getGameId()));
        log.info("A new game successfully started. gameId: %d , url: %s", kalahGameMsg.getGameId(), kalahGameMsg.getUrl());
        return kalahGameMsg;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{gameId}/pits/{pitId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Performs a move from a specific pit and returns the latest status of the game.")
    @CrossOrigin
    public GameStatusMsg move(
            @ApiParam("Identifier of the game.It Cannot be empty.") @PathVariable("gameId") int gameId,
            @ApiParam("Identifier of the selected pit.It Cannot be empty or be a kalah") @PathVariable("pitId") int pitId) {
        log.info(String.format("A move is requested with these parameters: gameId: %d , pitId: %d ", gameId, pitId));
        Board board = kalahService.move(gameId, pitId);
        GameStatusMsg gameStatusResponse = board.getGameStatusMsg();
        gameStatusResponse.setUrl(getGameUrl(gameStatusResponse.getGameId()));
        log.info(String.format("The move request successfully processed. gameId: %d , pitId: %d ", gameId, pitId));
        return gameStatusResponse;
    }

    private String getGameUrl(int gameId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/games/{gameId}").buildAndExpand(gameId).toUriString();
    }
}
