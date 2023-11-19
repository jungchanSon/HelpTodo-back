package HelpTodo.helptodoBackend.Components;

import HelpTodo.helptodoBackend.DTO.TodoListController.ResponseTodoList;
import HelpTodo.helptodoBackend.controller.TodoListController;
import HelpTodo.helptodoBackend.domain.TodoList;
import HelpTodo.helptodoBackend.service.TodoListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class TeamSseEmitters {
    private final ConcurrentHashMap<String, List<SseEmitter>> teamEmitterHashMap = new ConcurrentHashMap<>();
    private final TodoListService todoListService;

    public void addEmitter(String teamName, SseEmitter memberSseEmitter){
        List memberEmitterList = teamEmitterHashMap.get(teamName);
        if (memberEmitterList == null){
            List<SseEmitter> emitterList = new CopyOnWriteArrayList();
            emitterList.add(memberSseEmitter);
            teamEmitterHashMap.put(teamName, emitterList);
        } else {
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

    public void updateTodoList(String teamName, String token){
        List<SseEmitter> sseEmitters = teamEmitterHashMap.get(teamName);
        List<TodoList> todolist = todoListService.findAllByTeamName(teamName, token);

        List<ResponseTodoList> responseTodoLists = new ArrayList<>();
        for(TodoList todoList : todolist) {
            ResponseTodoList responseTodolist = ResponseTodoList.createResponseTodolist(todoList.getId(),
                    todoList.getTitle(),
                    todoList.getMember().getName(),
                    todoList.getCreateDate(),
                    todoList.getTdds());
            responseTodoLists.add(responseTodolist);
        }

        sseEmitters.forEach((emitter) -> {
            try{
                log.info("send emitter : {}", emitter);
                emitter.send(SseEmitter.event().name("updateTodoList").data(responseTodoLists).reconnectTime(500));
            } catch (IOException e) {
                sseEmitters.remove(emitter);
            }
        });
    }
}