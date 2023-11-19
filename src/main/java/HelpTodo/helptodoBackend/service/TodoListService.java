package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.form.TodoListForm.AddTDDForm;
import HelpTodo.helptodoBackend.form.TodoListForm.AllTodoListForm;
import HelpTodo.helptodoBackend.form.TodoListForm.CreateTodoListForm;
import HelpTodo.helptodoBackend.form.TodoListForm.DeleteTddForm;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    public Long createTodoList(CreateTodoListForm createTodoListForm, String token) {

        String tokenValue = JwtUtil.getTokenValue(token);
        String memberId = JwtUtil.getMemberId(tokenValue, secretKey);

        String listTitle = createTodoListForm.getTitle();
        String teamName = createTodoListForm.getTeamName();

        Member findMember = memberService.findOne(memberId);
        Team findTeam = teamService.findOneByName(teamName);

        TodoList todoList = TodoList.createTodolist(listTitle, findTeam, findMember);
        todoListRepository.save(todoList);

        return todoList.getId();
    }

    public List<TodoList> findAllByTeamName(String teamName, String token) {
        String tokenValue = JwtUtil.getTokenValue(token);
        String memberId = JwtUtil.getMemberId(tokenValue, secretKey);

        List<TodoList> allTeam = todoListRepository.findAllByTeamName(teamName);

        return allTeam;
    }

    private void validateMeberBelongTeam(String memberId, String teamName) {
        List<Member> membersInTeam = teamService.findMembers(teamName);
        boolean isBelong = false;
        log.info("memberId : {}", memberId);
        log.info("teamName : {}", teamName);
        for (Member memberInTeam : membersInTeam) {
            log.info("members in Team: : {} ", memberInTeam.getLoginId());
            if (Objects.equals(memberInTeam.getLoginId(), memberId)) {
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
        LocalDate startDate = LocalDate.parse(addTDDForm.getStartDate());
        LocalDate endDate = LocalDate.parse(addTDDForm.getEndDate());
        String manager = addTDDForm.getManager();
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
                .startDate(startDate)
                .endDate(endDate)
                .manager(manager)
                .build();

        TodoList findTodolist = todoListRepository.findOneById(todoListId);
        findTodolist.addTdd(newTDD);

        todoListRepository.save(findTodolist);
    }

    public  void deleteTdd(DeleteTddForm deleteTddForm) {
        Long tddId = deleteTddForm.getTddId();

        todoListRepository.deleteTdd(tddId);
    }

    public void deleteTodoList(Long todoListId) {

        todoListRepository.deleteTodoList(todoListId);
    }

    public void changeTddType(Long tddId, TddType tddType) {
        todoListRepository.changeTddType(tddId, tddType);
    }

    public void changeTodoDate(Long id, String startDate, String endDate) {
        todoListRepository.changeTodoDate(id, startDate, endDate);
    }
}
