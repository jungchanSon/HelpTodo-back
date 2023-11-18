package HelpTodo.helptodoBackend.Components;

import HelpTodo.helptodoBackend.controller.TodoListController;
import HelpTodo.helptodoBackend.service.TodoListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class TeamSseEmitters {
    private final ConcurrentHashMap<String, List<SseEmitter>> teamEmitterHashMap = new ConcurrentHashMap<>();

    public void addEmitter(String teamName, SseEmitter memberSseEmitter){
        List memberEmitterList = teamEmitterHashMap.get(teamName);
        if (memberEmitterList == null){
            List<SseEmitter> emitterList = new CopyOnWriteArrayList();
            emitterList.add(memberSseEmitter);
            teamEmitterHashMap.put(teamName, emitterList);
        } else if (!memberEmitterList.contains(memberSseEmitter)){
            memberEmitterList.add(memberSseEmitter);
            teamEmitterHashMap.put(teamName, memberEmitterList);
        }

        memberSseEmitter.onCompletion(() -> {
            teamEmitterHashMap.get(teamName).remove(memberSseEmitter);
        });
        memberSseEmitter.onTimeout(() -> {
            memberSseEmitter.complete();
        });

        log.info("emmitters : {}", teamEmitterHashMap);
    }

    public void updateTodoList(String teamName){
        log.info("Update TodoList STRAT");
        List<SseEmitter> sseEmitters = teamEmitterHashMap.get(teamName);
        sseEmitters.forEach((emitter) -> {
            try{
                log.info("send emitter : {}", emitter);
                emitter.send(SseEmitter.event().name("updateTodoList").data("updateTodoList").reconnectTime(500));
            } catch (IOException e) {
                sseEmitters.remove(emitter);
            }
        });
        log.info("Update TodoList END");

    }
}
/*
    Emitter = {team1 : [mem1, mem2],
               team2 : [mem3, mem4],
                ...
               }
 */