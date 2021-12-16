package com.bol.assignment.service;

import com.bol.assignment.model.Board;

import java.util.Optional;

public interface MoveRuleService {

    Board replace(Board board,int pitId);

    Board collectFront(Board board);

    Board collectAll(Board board);

    Board setTurn(Board board);

    Board validate(Optional<Board> optionalBoard, int gameId, int pitId);

}
