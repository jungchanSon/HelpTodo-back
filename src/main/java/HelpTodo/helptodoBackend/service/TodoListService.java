package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.Form.TodoListForm.AddTDDForm;
import HelpTodo.helptodoBackend.Form.TodoListForm.AllTodoListForm;
import HelpTodo.helptodoBackend.Form.TodoListForm.CreateTodoListForm;
import HelpTodo.helptodoBackend.domain.*;
import HelpTodo.helptodoBackend.exception.ErrorCode_TodoList;
import HelpTodo.helptodoBackend.exception.TodoListException;
import HelpTodo.helptodoBackend.repository.TodoListRepository;
import HelpTodo.helptodoBackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TodoListService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final TodoListRepository todoListRepository;
    private final MemberService memberService;
    private final TeamService teamService;

    @Transactional
    public Long createTodoList(CreateTodoListForm createTodoListForm) {

        String listTitle = createTodoListForm.getTitle();
        String memberId = createTodoListForm.getMemberId();
        String teamName = createTodoListForm.getTeamName();

        Member findMember = memberService.findOne(memberId);
        Team findTeam = teamService.findOneByName(teamName);

        TodoList todoList = TodoList.createTodolist(listTitle, findTeam, findMember);
        todoListRepository.save(todoList);

        return todoList.getId();
    }

    public List<TodoList> findAllByTeamName(AllTodoListForm allTodoListForm, String token) {
        String teamName = allTodoListForm.getTeamName();
        String tokenValue = JwtUtil.getTokenValue(token);
        String memberId = JwtUtil.getMemberId(tokenValue, secretKey);

        validateMeberBelongTeam(memberId, teamName);

        List<TodoList> allTeam = todoListRepository.findAllByTeamName(teamName);

        return allTeam;
    }

    private void validateMeberBelongTeam(String memberId, String teamName) {
        List<Member> membersInTeam = teamService.findMembers(teamName);
        boolean isBelong = false;
        log.info("memberId : {}", memberId);
        log.info("teamName : {}", teamName);
        for (Member member : membersInTeam) {
            log.info("members in Team: : {} ", member.getLoginId());
            if (member.getLoginId() == memberId) {
                isBelong = true;
                break;
            }
        }

        if (!isBelong) {
            throw new TodoListException(ErrorCode_TodoList.MEMBER_NOT_BELONG_TEAM, "팀에 소속되지 않는 멤버");
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

    public void createTDDEntity(AddTDDForm addTDDForm, String type, String token) {

        Long todoListId = addTDDForm.getTodoListId();
        String tokenValue = JwtUtil.getTokenValue(token);
        String memberId = JwtUtil.getMemberId(tokenValue, secretKey);
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
            throw new TodoListException(ErrorCode_TodoList.CREATE_TDD_ENTITY_WRONG_TYPE, "올바르지 않은 TDD타입");
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

    public void changeTddType(Long tddId, TddType tddType) {
        todoListRepository.changeTddType(tddId, tddType);
    }
}
