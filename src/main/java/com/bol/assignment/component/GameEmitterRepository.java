package com.bol.assignment.component;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


// TODO: For achieving horizontally scaling, the Map must change with the Redis.

/**
 * GameEmitterRepository for holding and retrieving SseEmitters.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@Component
public class GameEmitterRepository {
    Map<String, List<SseEmitter>> players = new ConcurrentHashMap<>();

    /**
     * Keeps the SseEmitter in the map.
     *
     * @param gameId
     * @param sseEmitter
     */
    public void put(String gameId, SseEmitter sseEmitter) {
        List<SseEmitter> sseEmitters = players.get(gameId);

        if (Objects.isNull(sseEmitters)) {
            sseEmitters = new ArrayList<>();
        }
        sseEmitters.add(sseEmitter);
        players.put(gameId, sseEmitters);
    }

    /**
     * A list of SseEmitter based on gameId.
     *
     * @param gameId
     * @return a list of SseEmitter.
     */
    public List<SseEmitter> get(String gameId) {
        return players.get(gameId);
    }

    public void remove(String gameId) {
        players.remove(gameId);
    }

    public void clear() {
        players.clear();
    }


}
