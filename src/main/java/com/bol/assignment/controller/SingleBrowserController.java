package com.bol.assignment.controller;

import com.bol.assignment.mapper.BoardMapper;
import com.bol.assignment.model.Board;
import com.bol.assignment.msg.GameStatusMsg;
import com.bol.assignment.msg.KalahGameMsg;
import com.bol.assignment.service.single.SingleKalahService;
import com.bol.assignment.util.GameUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Rest controller class to start a new game and move on the single browser.*
 *
 * @author Pooya Mirzapour (Pooyamirzapour@gmail.com)
 */

@RestController
@Api("Provides set of endpoints to create, and play a Kalah game.")
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/games/single")
public class SingleBrowserController {

    private SingleKalahService kalahService;
    private GameUtil gameUtil;
    private final BoardMapper boardMapper;

    /**
     * It starts a new game
     * @return a KalahGameMsg to the client
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    @ApiOperation("Staring a new Kalah game.")
    @CrossOrigin
    public KalahGameMsg newGame() {
        log.info("A new single browser game request received");
        Board board = kalahService.newGame();
        KalahGameMsg kalahGameMsg = KalahGameMsg.builder().gameId(board.getId()).build();
        kalahGameMsg.setUrl(gameUtil.getGameUrl(kalahGameMsg.getGameId()));
        log.info("A new single browser game successfully started. gameId: %d , url: %s", kalahGameMsg.getGameId(), kalahGameMsg.getUrl());
        return kalahGameMsg;
    }


    /**
     * It makes an updated game board for client.
     * @param gameId
     * @param pitId
     * @return an updated GameStatusMsg after the move to the client.
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{gameId}/pits/{pitId}")
    @ApiOperation("Performs a move from a specific pit and returns the latest status of the game.")
    public GameStatusMsg move(
            @ApiParam("Identifier of the game.It Cannot be empty.") @NotNull @PathVariable("gameId") int gameId,
            @ApiParam("Identifier of the selected pit.It Cannot be empty or be a kalah") @NotNull @PathVariable("pitId") int pitId) throws IOException {
        log.info(String.format("A move is requested with these parameters: gameId: %d , pitId: %d ", gameId, pitId));

        GameStatusMsg gameStatusMsg = boardMapper.toGameStatusMsg(kalahService.move(gameId, pitId));
        gameStatusMsg.setUrl(gameUtil.getGameUrl(gameId));
        return gameStatusMsg;

    }


}
