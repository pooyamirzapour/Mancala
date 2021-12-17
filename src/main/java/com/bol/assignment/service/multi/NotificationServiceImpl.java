package com.bol.assignment.service.multi;

import com.bol.assignment.component.GameEmitterRepository;
import com.bol.assignment.model.Board;
import com.bol.assignment.msg.GameStatusMsg;
import com.bol.assignment.util.GameUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private GameEmitterRepository gameEmitterRepository;
    private GameUtil gameUtil;

    public SseEmitter sendToClients(Board board) {
        if (Objects.nonNull(gameEmitterRepository.get(board.getId()))) {
            gameEmitterRepository.get(board.getId())
                    .forEach(mySseEmitter -> {
                        try {
                            GameStatusMsg gameResponse = board.getGameStatusMsg();
                            gameResponse.setUrl(gameUtil.getGameUrl(gameResponse.getGameId()));

                            log.info(gameResponse.toString());
                            mySseEmitter.send(gameResponse);
                        } catch (Throwable e) {
                            log.info("IO exception while emitting the board.");
                            mySseEmitter.completeWithError(e);
                        }
                    });
            return gameEmitterRepository.get(board.getId()).get(0);
        }
        return null;
    }
}
