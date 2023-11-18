package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.Components.TeamSseEmitters;
import HelpTodo.helptodoBackend.form.ServerEvent.AddEmitterForm;
import HelpTodo.helptodoBackend.service.ServerEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ServerEventContoller {

    private final TeamSseEmitters teamSseEmitters;
    private final ServerEventService serverEventService;
    @GetMapping(value="/event/addEmitter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> join(@RequestHeader(value = "teamName") String teamName, AddEmitterForm addEmitterForm) {

        SseEmitter emitter = serverEventService.addEmitter(teamName);

        log.info("suc addEmitter");
        try{
            log.info("send Test");
            emitter.send(SseEmitter.event().name("test").data("testData"));
        }catch (IOException e ){
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);
    }

    @RequestMapping("/update")
    public ResponseEntity update() {

        return ResponseEntity.ok().body("update)");
    }

}
