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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TodoListController {

    private final TodoListService todoListService;
    private final MemberService memberService;
    private final TeamService teamService;

    @RequestMapping("/todolist/create")
    public String createTodoList(@Valid CreateTodoListForm createTodolistForm, BindingResult result) {

        if (result.hasErrors()) {

            return "fail";
        }

        String title = createTodolistForm.getTitle();
        String userId = createTodolistForm.getUserId();
        String teamName = createTodolistForm.getTeamName();

        Member findMember = memberService.findOne(userId);
        Team findTeam = teamService.findOneByName(teamName);

        TodoList todolist = TodoList.createTodolist(title, findTeam, findMember);

        todoListService.createTodoList(todolist);

        return "Create Todolist";
    }

    @RequestMapping("/todolist/all")
    public List<ResponseTodoList> allTodoList(@Valid AllTodoListForm allTodoListForm, BindingResult result) {

        if (result.hasErrors()) {
            return null;
        }

        String teamName = allTodoListForm.getTeamName();

        List<ResponseTodoList> responseTodoLists = new ArrayList<>();

        // TODO: 2022-11-30 이부분 최적화 생각해보기
        List<TodoList> allByTeamName = todoListService.findAllByTeamName(teamName);

        for(TodoList todoList : allByTeamName) {
            ResponseTodoList responseTodolist = ResponseTodoList.createResponseTodolist(todoList.getId(),
                                                                                        todoList.getTitle(),
                                                                                        todoList.getMember().getName(),
                                                                                        todoList.getCreateDate(),
                                                                                        todoList.getTdds());
            responseTodoLists.add(responseTodolist);
        }

        return responseTodoLists;
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
    public String addTodo(@Valid AddTDDForm addTDDForm, @PathVariable String type, BindingResult result) {

        if (result.hasErrors()) {
            return "hasErrors";
        }

        Long todoListId = addTDDForm.getTodoListId();
        String memberId = addTDDForm.getMemberId();
        String content = addTDDForm.getContent();
        int importance = addTDDForm.getImportance();

        Member member = memberService.findOne(memberId);

        System.out.println("todoListId : "+ todoListId);
        System.out.println("memberId : "+ memberId);
        System.out.println("content : "+ content);
        System.out.println("importance : " + importance);
        System.out.println("type : " + type.toString());
        System.out.println(type.toString()=="todo");
        System.out.println(type.equals("todo"));


        if (type.equals("todo")){
            Tdd newTodo = Tdd.createTdd(TddType.TODO, content, importance, member);
            todoListService.createTDDEntity(todoListId, newTodo);

            return "createTodoEntity";

        } else if (type.equals("doing")) {
            Tdd newDoing = Tdd.createTdd(TddType.DOING, content, importance, member);
            todoListService.createTDDEntity(todoListId, newDoing);

            return "createDoingEntity";

        } else if (type.equals("done")) {
            Tdd newDone = Tdd.createTdd(TddType.DONE, content, importance, member);
            todoListService.createTDDEntity(todoListId, newDone);

            return "createDoneEntity";
        }
        return "fail";
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

}
