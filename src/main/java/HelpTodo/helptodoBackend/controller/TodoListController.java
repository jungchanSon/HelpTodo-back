package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.DTO.TodoListController.ResponseTodoList;
import HelpTodo.helptodoBackend.Form.TodoListForm.AddTDDForm;
import HelpTodo.helptodoBackend.Form.TodoListForm.AllTodoListForm;
import HelpTodo.helptodoBackend.Form.TodoListForm.CreateTodoListForm;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Tdd;
import HelpTodo.helptodoBackend.domain.TddType;
import HelpTodo.helptodoBackend.domain.Team;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TodoListController {

    private final TodoListService todoListService;
    private final MemberService memberService;
    private final TeamService teamService;

    @RequestMapping("/todolist/create")
    public ResponseEntity createTodoList(@Valid CreateTodoListForm createTodolistForm, BindingResult result) {

        if (result.hasErrors()) {

            return ResponseEntity.ok().body("create todoList Fail");
        }

        // TODO: 2023-03-22 createTodoListFrom.toServiceDto 만들어서 보내주기..
        todoListService.createTodoList(createTodolistForm);

        return ResponseEntity.ok().body("create todolist ok");
    }

    @RequestMapping("/todolist/all")
    public ResponseEntity allTodoList(@Valid AllTodoListForm allTodoListForm, BindingResult result) {

        if (result.hasErrors()) {
            return null;
        }

        List<TodoList> allByTeamName = todoListService.findAllByTeamName(allTodoListForm);

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
    @RequestMapping("/todolist/addTDD/{type}")
    public ResponseEntity addTodo(@Valid AddTDDForm addTDDForm, @PathVariable String type, BindingResult result) {

        todoListService.createTDDEntity(addTDDForm, type);

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
    @RequestMapping("/todolist/delete")
    public ResponseEntity deleteTodoList(@RequestParam(name="todoListId") Long todoListId) {
        todoListService.deleteTodoList(todoListId);

        return ResponseEntity.ok().body("delete todoList ok");
    }

    @RequestMapping("/todolist/tdd/delete")
    public ResponseEntity deleteTdd(@RequestParam(name="tddId") Long tddId) {
        todoListService.deleteTdd(tddId);

        return ResponseEntity.ok().body("delete todo card Ok");

    }

    @RequestMapping("/todolist/change/tddType")
    public ResponseEntity changeTddType(@RequestParam(name="tddId") Long tddId, @RequestParam(name="tddType") TddType type){
        todoListService.changeTddType(tddId, type);

        return ResponseEntity.ok().body("change todocard Ok");
    }
}
