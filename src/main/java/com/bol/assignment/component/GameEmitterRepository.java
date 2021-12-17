package com.bol.assignment.component;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameEmitterRepository {
	Map<Integer, List<SseEmitter>> players = new ConcurrentHashMap<>();

	public void put(Integer gameId, SseEmitter sseEmitter) {
		List<SseEmitter> sseEmitters = players.get(gameId);

		if (Objects.isNull(sseEmitters)) {
			sseEmitters = new ArrayList<>();
		}
		sseEmitters.add(sseEmitter);
		players.put(gameId, sseEmitters);
	}

	public List<SseEmitter> get(Integer gameId) {
		return players.get(gameId);
	}

	public void clear() {
		 players.clear();
	}


}
