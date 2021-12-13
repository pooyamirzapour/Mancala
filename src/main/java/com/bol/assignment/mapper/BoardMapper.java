package com.bol.assignment.mapper;

import com.bol.assignment.model.Board;
import com.bol.assignment.msg.GameStatusMsg;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface BoardMapper {
    @Mappings({
     @Mapping(source = "id", target = "gameId")
    })
    GameStatusMsg toGameStatusMsg(Board board);
}
