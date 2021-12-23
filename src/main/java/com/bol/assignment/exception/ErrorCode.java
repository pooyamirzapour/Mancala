package com.bol.assignment.exception;

import org.springframework.http.HttpStatus;

/**
 * Enumeration of error codes.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

public enum ErrorCode {
    GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "Game not found"),
    PLAYERS_ARE_FULL(HttpStatus.BAD_REQUEST, "Can not accept more players"),
    PIT_IS_KALAH(HttpStatus.BAD_REQUEST, "The pit is a Kalah"),
    PIT_IS_NOT_YOURS(HttpStatus.BAD_REQUEST, "The pit is not yours"),
    INVALID_PIT(HttpStatus.BAD_REQUEST, "The pit is invalid"),
    PIT_IS_EMPTY(HttpStatus.BAD_REQUEST, "Empty pit could not be chosen"),
    GAME_IS_OVER(HttpStatus.BAD_REQUEST, "The game is over");

    private final HttpStatus value;
    private final String message;

    ErrorCode(HttpStatus value, String message) {
        this.value = value;
        this.message = message;
    }

    public HttpStatus getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }


}