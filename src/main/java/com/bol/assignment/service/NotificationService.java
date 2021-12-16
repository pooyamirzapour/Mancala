package com.bol.assignment.service;

import com.bol.assignment.model.Board;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

    SseEmitter sendToClients(Board board);
}
