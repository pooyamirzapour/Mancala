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
 *
 * @author Pooya Mirzapour (pooyamirzaapour@gmail.com)
 */
@JsonPropertyOrder({"id", "url"})
@ApiModel(description = "This class is used to return a response message of KalahGame")
@Data
@Builder
public class KalahGameMsg implements Serializable {
    @ApiModelProperty(notes = "Unique identifier of the game.", example = "6bbac0ee-715d-44df-8353-3fdd6dca18bc")
    @JsonProperty("id")
    private String gameId;

    @ApiModelProperty(notes = "Main URL of the game.", example = "http://127.0.0.1:8087/api/v1/games/single/6bbac0ee-715d-44df-8353-3fdd6dca18bc", position = 1)
    @JsonProperty("url")
    private String url;
}
