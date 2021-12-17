package com.bol.assignment.msg;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Game Status class which hold latest status of kalah game
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@JsonPropertyOrder({"id", "url", "status"})
@ApiModel(description = "This class is used to show the latest status of a Kalah game.")
@Data
public class JoinGameStatusMsg implements Serializable {

    @ApiModelProperty(notes = "Unique identifier of the game.", example = "1")
    @JsonProperty("id")
    private int gameId;

    @ApiModelProperty(notes = "Unique identifier of the player who turns.", example = "1")
    private int playerId;

    @ApiModelProperty(notes = "Main URL of the Game.", example = "http://localhost:8087/games/1", position = 1)
    @JsonProperty("url")
    private String url;

    @ApiModelProperty(notes = "Current state of the Game, depend on Kalah game rules",
            example = "{\"1\":6,\"2\":6,\"3\":6,\"4\":6,\"5\":6,\"6\":6,\"7\":0,\"8\":6,\"9\":6,\"10\":6,\"11\":6,\"12\":6,\"13\":6,\"14\":0}", position = 2)
    @JsonProperty("status")
    private Map<Integer, Integer> pitsMap_playerOne;

    public JoinGameStatusMsg(int gameId) {
        this.gameId = gameId;
    }

}