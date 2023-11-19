package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.Components.TeamSseEmitters;
import HelpTodo.helptodoBackend.Form.ServerEvent.AddEmitterForm;
import HelpTodo.helptodoBackend.service.ServerEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ServerEventContoller {

    private final TeamSseEmitters teamSseEmitters;
    private final ServerEventService serverEventService;
    @GetMapping(value="/event/addEmitter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> join(@RequestParam(value = "teamName") String teamName) {

        SseEmitter emitter = serverEventService.addEmitter(teamName);

        try{
            emitter.send(SseEmitter.event().name("connect").data("success"));
        }catch (IOException e ){
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);
    }

    @RequestMapping("/update")
    public ResponseEntity update() {
        return ResponseEntity.ok().body("update");
    }

}
