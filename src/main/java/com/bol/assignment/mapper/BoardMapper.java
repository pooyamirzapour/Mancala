package com.bol.assignment.mapper;

import com.bol.assignment.model.Board;
import com.bol.assignment.msg.GameStatusMsg;
import com.bol.assignment.util.GameUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper {


    @BeforeMapping
    default void beforeMapping(@MappingTarget GameStatusMsg target, Board source) {
        if (source.getPlayerOne().getTurn().equals(true)) {
            target.setPlayerId(1);
        } else
            target.setPlayerId(2);
    }


    @Mappings({
            @Mapping(source = "id", target = "gameId"),
            @Mapping(source = "pitsMap", target = "pitsMap_playerOne")
    })
    GameStatusMsg toGameStatusMsg(Board board);
}
