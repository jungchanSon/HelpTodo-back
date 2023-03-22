package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.Form.TodoListForm.AddTDDForm;
import HelpTodo.helptodoBackend.Form.TodoListForm.AllTodoListForm;
import HelpTodo.helptodoBackend.Form.TodoListForm.CreateTodoListForm;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Tdd;
import HelpTodo.helptodoBackend.domain.TddType;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.domain.TodoList;
import HelpTodo.helptodoBackend.repository.TodoListRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoListService {

    private final TodoListRepository todoListRepository;
    private final MemberService memberService;
    private final TeamService teamService;

    @Transactional
    public Long createTodoList(CreateTodoListForm createTodoListForm){

        String listTitle = createTodoListForm.getTitle();
        String memberId = createTodoListForm.getMemberId();
        String teamName = createTodoListForm.getTeamName();

        Member findMember = memberService.findOne(memberId);
        Team findTeam = teamService.findOneByName(teamName);

        TodoList todoList = TodoList.createTodolist(listTitle, findTeam, findMember);
        todoListRepository.save(todoList);

        return todoList.getId();
    }

    public List<TodoList> findAllByTeamName(AllTodoListForm allTodoListForm){
        String teamName = allTodoListForm.getTeamName();
        String memberId = allTodoListForm.getMemberId();

        validateMeberBelongTeam(memberId, teamName);

        List<TodoList> allTeam = todoListRepository.findAllByTeamName(teamName);

        return allTeam;
    }

    private void validateMeberBelongTeam(String memberId, String teamName) {
        List<Member> membersInTeam = teamService.findMembers(teamName);
        boolean isBelong = false;

        for (Member member : membersInTeam) {
            if (member.getLoginId() == memberId) {
                isBelong = true;
                break;
            }
        }

        if (!isBelong) {
            throw new IllegalStateException();
        }
    }

    //    public void createTDDEntity(Long todoListId, Object TDDEntity){
//
//        TodoList findTodolist = todoListRepository.findOneById(todoListId);
//
//        if (TDDEntity.getClass() == Todo.class) {
//            findTodolist.addTodos((Todo) TDDEntity);
//            todoListRepository.save(findTodolist);
//
//        } else if(TDDEntity.getClass() == Doing.class){
//            findTodolist.addDoing((Doing)TDDEntity);
//            todoListRepository.save(findTodolist);
//
//        } else if(TDDEntity.getClass() == Done.class){
//            findTodolist.addDone((Done)TDDEntity);
//            todoListRepository.save(findTodolist);
//
//        }
//    }

    public void createTDDEntity(AddTDDForm addTDDForm, String type){

        Long todoListId = addTDDForm.getTodoListId();
        String memberId = addTDDForm.getMemberId();
        String content = addTDDForm.getContent();
        int importance = addTDDForm.getImportance();

        Member member = memberService.findOne(memberId);
        TddType TDDType = null;

        if (type.equals("todo")) {
            TDDType = TddType.TODO;
        } else if (type.equals("doing")) {
            TDDType = TddType.DOING;
        } else if (type.equals("done")) {
            TDDType = TddType.DONE;
        } else {
            throw new IllegalStateException();
        }

        Tdd newTDD = Tdd.builder()
            .tddtype(TDDType)
            .content(content)
            .importance(importance)
            .member(member)
            .build();

        TodoList findTodolist = todoListRepository.findOneById(todoListId);
        findTodolist.addTdd(newTDD);

        todoListRepository.save(findTodolist);
    }

    public void deleteTdd(Long tddId) {

        todoListRepository.deleteTdd(tddId);
    }

    public void deleteTodoList(Long todoListId) {

        todoListRepository.deleteTodoList(todoListId);
    }

    public void changeTddType(Long tddId, TddType tddType){
        todoListRepository.changeTddType(tddId, tddType);
    }
}
