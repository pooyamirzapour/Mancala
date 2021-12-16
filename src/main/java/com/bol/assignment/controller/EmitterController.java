package com.bol.assignment.controller;

import com.bol.assignment.msg.GameStatusMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("")
public class EmitterController {

    @GetMapping("/sse")
    public ResponseEntity streamSseMvc() {
        SseEmitter emitter = new SseEmitter();
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; true; i++) {

                    emitter.send(new GameStatusMsg(i,"google.com"));
                    Thread.sleep(2000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
