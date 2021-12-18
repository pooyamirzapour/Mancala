package com.bol.assignment.controller;

import com.bol.assignment.model.Board;
import com.bol.assignment.msg.KalahGameMsg;
import com.bol.assignment.service.multi.MultiKalahService;
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
 * Rest controller class to start a new game, join, and move on the multiple browser.*
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@RestController
@Api("Provides set of endpoints to create, and play a Kalah game.")
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/games")
public class MultipleBrowserController {

    private MultiKalahService multiKalahService;
    private GameUtil gameUtil;

    /**
     * It starts a new game
     * @return a KalahGameMsg to the client
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Staring a new Kalah game.")
    @CrossOrigin
    public KalahGameMsg newGame() {
        log.info("A new game request received");
        Board board = multiKalahService.newGame();
        KalahGameMsg kalahGameMsg = KalahGameMsg.builder().gameId(board.getId()).build();
        kalahGameMsg.setUrl(gameUtil.getGameUrl(kalahGameMsg.getGameId()));
        log.info("A new game successfully started. gameId: %d , url: %s", kalahGameMsg.getGameId(), kalahGameMsg.getUrl());
        return kalahGameMsg;
    }

    /**
     * It joins to an existing game.
     * @param gameId
     * @return SseEmitter
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/join/{gameId}")
    @ApiOperation("Join to a game Kalah game.")
    @CrossOrigin
    public SseEmitter joinToGame(@NotNull @PathVariable("gameId") int gameId) throws IOException {
        log.info("Join to a new game request received");
        SseEmitter sseEmitter = new SseEmitter();
        multiKalahService.joinToGame(gameId, sseEmitter);
        log.info("Join to a game successfully done. gameId: %d ", gameId);
        return sseEmitter;
    }

    /**
     * It makes an updated game board for client and send with the help of SseEmitter.
     * @param gameId
     * @param pitId
     * @return ResponseEntity.
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{gameId}/pits/{pitId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Performs a move from a specific pit and returns the latest status of the game.")
    public ResponseEntity move(
            @ApiParam("Identifier of the game.It Cannot be empty.") @NotNull @PathVariable("gameId") int gameId,
            @ApiParam("Identifier of the selected pit.It Cannot be empty or be a kalah") @NotNull @PathVariable("pitId") int pitId) throws IOException {
        log.info(String.format("A move is requested with these parameters: gameId: %d , pitId: %d ", gameId, pitId));
        multiKalahService.move(gameId, pitId);
        log.info(String.format("The move request successfully processed. gameId: %d , pitId: %d ", gameId, pitId));
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
