package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.Form.TodoListForm.CreateTodoListForm;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.domain.TodoList;
import HelpTodo.helptodoBackend.service.MemberService;
import HelpTodo.helptodoBackend.service.TeamService;
import HelpTodo.helptodoBackend.service.TodoListService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
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
        String title = createTodolistForm.getTitle();
        String userId = createTodolistForm.getUserId();
        String teamName = createTodolistForm.getTeamName();

        Member findMember = memberService.findOne(userId);
        Team findTeam = teamService.findOneByName(teamName);

        TodoList todolist = TodoList.createTodolist(title, findTeam, findMember);

        todoListService.createTodoList(todolist);
    }


    // TODO: 2022-11-27
//    @RequestMapping("/todolist/addtodo")
//    public String addTodo(@Valid AddTodoForm addTodoForm, BindingResult result) {
//
//    }
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
