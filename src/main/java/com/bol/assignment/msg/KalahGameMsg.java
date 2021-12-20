package com.bol.assignment.msg;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Game class which hold id of kalah game
 * @author Pooya Mirzapour (pooyamirzaapour@gmail.com)
 */
@JsonPropertyOrder({"id", "url"})
@ApiModel(description = "This class is used to return a response message of KalahGame")
@Data
@Builder
public class KalahGameMsg implements Serializable {
    @ApiModelProperty(notes = "Unique identifier of the game.", example = "1")
    @JsonProperty("id")
    private String gameId;

    @ApiModelProperty(notes = "Main URL of the game.", example = "http://localhost:8087/games/1", position = 1)
    @JsonProperty("url")
    private String url;
}
