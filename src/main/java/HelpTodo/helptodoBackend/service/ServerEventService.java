package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.Components.TeamSseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerEventService {

    private final TeamSseEmitters teamSseEmitters;

    public SseEmitter addEmitter(String teamName){

        SseEmitter sseEmitter = new SseEmitter();

        teamSseEmitters.addEmitter(teamName, sseEmitter);

        SseEmitter.SseEventBuilder firstConnectData = SseEmitter.event().name("connect").data("success");
        try{
            sseEmitter.send(SseEmitter.event().name("connect").data("data!"));
        } catch (IOException e) {
            log.error("addEmitter 실패");
        }
        return sseEmitter;
    }
}
