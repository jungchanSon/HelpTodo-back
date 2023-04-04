package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.Components.TeamSseEmitters;
import HelpTodo.helptodoBackend.DTO.TodoListController.ResponseTodoList;
import HelpTodo.helptodoBackend.Form.TodoListForm.*;
import HelpTodo.helptodoBackend.domain.TddType;
import HelpTodo.helptodoBackend.domain.TodoList;
import HelpTodo.helptodoBackend.service.MemberService;
import HelpTodo.helptodoBackend.service.TeamService;
import HelpTodo.helptodoBackend.service.TodoListService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TodoListController {

    private final TodoListService todoListService;
    private final MemberService memberService;
    private final TeamService teamService;
    private final TeamSseEmitters teamSseEmitters;

    // 투두리스트 생성
    @RequestMapping("/todolist/create")
    public ResponseEntity createTodoList(@RequestHeader(value = "Authorization") String token, @Valid CreateTodoListForm createTodolistForm, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.ok().body("create todoList Fail");
        }

        // TODO: 2023-03-22 createTodoListFrom.toServiceDto 만들어서 보내주기..
        todoListService.createTodoList(createTodolistForm, token);
        teamSseEmitters.updateTodoList(createTodolistForm.getTeamName());
        return ResponseEntity.ok().body("create todolist ok");
    }

    //투두리스트 조회
    @RequestMapping("/todolist/all")
    public ResponseEntity allTodoList(@RequestHeader(value = "Authorization") String token, @Valid AllTodoListForm allTodoListForm, BindingResult result) {

        if (result.hasErrors()) {
            return null;
        }

        List<TodoList> allByTeamName = todoListService.findAllByTeamName(allTodoListForm, token);

        List<ResponseTodoList> responseTodoLists = new ArrayList<>();
        for(TodoList todoList : allByTeamName) {
            ResponseTodoList responseTodolist = ResponseTodoList.createResponseTodolist(todoList.getId(),
                                                                                        todoList.getTitle(),
                                                                                        todoList.getMember().getName(),
                                                                                        todoList.getCreateDate(),
                                                                                        todoList.getTdds());
            responseTodoLists.add(responseTodolist);
        }

        return ResponseEntity.ok().body(responseTodoLists);
    }

//    @RequestMapping("/todolist/addTodo")
//    public String addTodo(@Valid AllTodoListForm allTodoListForm, BindingResult result) {
//
//        if (result.hasErrors()) {
//            return null;
//        }
//
//        return "add Todo";
//    }
//
//    @RequestMapping("/todolist/addDoing")
//    public String addDoing(@Valid AllTodoListForm allTodoListForm, BindingResult result) {
//
//        if (result.hasErrors()) {
//            return null;
//        }
//
//        return "add Doing";
//    }
//
//    @RequestMapping("/todolist/addDone")
//    public String addDone(@Valid AllTodoListForm allTodoListForm, BindingResult result) {
//
//        if (result.hasErrors()) {
//            return null;
//        }
//
//        return "add Done";
//    }

    // TODO: 2022-11-27
    //투두리스트 카드 추가
    @RequestMapping("/todolist/addTDD/{type}")
    public ResponseEntity addTodo(@RequestHeader(value = "Authorization") String token, @Valid AddTDDForm addTDDForm, @PathVariable String type, BindingResult result) {

        todoListService.createTDDEntity(addTDDForm, type, token);
        teamSseEmitters.updateTodoList(addTDDForm.getTeamName());
        return ResponseEntity.ok().body("create todo card OK");
    }
//
//    @RequestMapping("/todolist/adddoing")
//    public String addDoing(@Valid AddDoingForm addDoingForm, BindingResult result) {
//
//    }
//
//    @RequestMapping("/todolist/adddone")
//    public String addDone(@Valid AddDoneForm addDoneForm, BindingResult result) {
//
//    }

    // TODO: 2023-03-29 올바른 사용자인지 체크해야하나 ?
    @RequestMapping("/todolist/delete")
    public ResponseEntity deleteTodoList(@RequestHeader(value = "Authorization") String token, @Valid DeleteTodoListForm deleteTodoListForm, BindingResult result) {
        todoListService.deleteTodoList(deleteTodoListForm.getTodoListId());
        teamSseEmitters.updateTodoList(deleteTodoListForm.getTeamName());
        return ResponseEntity.ok().body("delete todoList ok");
    }

    @RequestMapping("/todolist/tdd/delete")
    public ResponseEntity deleteTdd(@RequestHeader(value = "Authorization") String token, @Valid DeleteTddForm deleteTddForm, BindingResult result) {
        log.info("deleteTddForm getTddId {}", deleteTddForm.getTddId());
        log.info("deleteTddForm getTeamName {}", deleteTddForm.getTeamName());
        log.info("deleteTddForm token {}", token);
        todoListService.deleteTdd(deleteTddForm);

        List<TodoList> allByTeamName = todoListService.findAllByTeamName(new AllTodoListForm(deleteTddForm.getTeamName()), token);

        List<ResponseTodoList> responseTodoLists = new ArrayList<>();
        for(TodoList todoList : allByTeamName) {
            ResponseTodoList responseTodolist = ResponseTodoList.createResponseTodolist(todoList.getId(),
                    todoList.getTitle(),
                    todoList.getMember().getName(),
                    todoList.getCreateDate(),
                    todoList.getTdds());
            responseTodoLists.add(responseTodolist);
        }

        teamSseEmitters.updateTodoList(deleteTddForm.getTeamName());
        return ResponseEntity.ok().body(responseTodoLists);
    }

    @RequestMapping("/todolist/change/tddType")
    public ResponseEntity changeTddType(@RequestHeader(value = "Authorization") String token, @Valid ChangeTddTypeForm changeTddTypeForm, BindingResult result){
        todoListService.changeTddType(changeTddTypeForm.getTddId(), changeTddTypeForm.getTddType());

        teamSseEmitters.updateTodoList(changeTddTypeForm.getTeamName());
        return ResponseEntity.ok().body("change todocard Ok");
    }
}
