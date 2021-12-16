package com.bol.assignment.controller;

import com.bol.assignment.model.Board;
import com.bol.assignment.msg.KalahGameMsg;
import com.bol.assignment.service.KalahService;
import com.bol.assignment.util.GameUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Rest controller class to start a new game and move on the game.*
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
    private GameUtil gameUtil;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Staring a new Kalah game.")
    @CrossOrigin
    public KalahGameMsg newGame() {
        log.info("A new game request received");
        Board board = kalahService.newGame();
        KalahGameMsg kalahGameMsg = KalahGameMsg.builder().gameId(board.getId()).build();
        kalahGameMsg.setUrl(gameUtil.getGameUrl(kalahGameMsg.getGameId()));
        log.info("A new game successfully started. gameId: %d , url: %s", kalahGameMsg.getGameId(), kalahGameMsg.getUrl());
        return kalahGameMsg;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/join/{gameId}")
    @ApiOperation("Join to a game Kalah game.")
    @CrossOrigin
    public SseEmitter joinToGame(@NotNull @PathVariable("gameId") int gameId) throws IOException {
        log.info("Join to a new game request received");
        SseEmitter sseEmitter = new SseEmitter();
        kalahService.joinToGame(gameId, sseEmitter);
        log.info("Join to a game successfully done. gameId: %d ", gameId);
        return sseEmitter;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{gameId}/pits/{pitId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Performs a move from a specific pit and returns the latest status of the game.")
    public ResponseEntity move(
            @ApiParam("Identifier of the game.It Cannot be empty.") @NotNull @PathVariable("gameId") int gameId,
            @ApiParam("Identifier of the selected pit.It Cannot be empty or be a kalah") @NotNull @PathVariable("pitId") int pitId) throws IOException {
        log.info(String.format("A move is requested with these parameters: gameId: %d , pitId: %d ", gameId, pitId));
        Board board = kalahService.move(gameId, pitId);
        log.info(String.format("The move request successfully processed. gameId: %d , pitId: %d ", gameId, pitId));
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
