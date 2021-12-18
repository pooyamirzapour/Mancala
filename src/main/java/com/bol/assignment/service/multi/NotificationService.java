package com.bol.assignment.service.multi;

import com.bol.assignment.model.Board;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

    /**
     * It notifies clients with an updated game board after move.
     *
     * @param board
     * @return an instance of SseEmitter for updating game board of clients.
     */
    SseEmitter sendToClients(Board board);
}
