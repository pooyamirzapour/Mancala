package com.bol.assignment.service;

import com.bol.assignment.model.Board;

public interface RuleService {

    Board replace(Board board);

    Board collectFront(Board board);

    Board collectAll(Board board);

}
