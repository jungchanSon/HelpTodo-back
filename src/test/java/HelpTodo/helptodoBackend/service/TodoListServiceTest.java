package HelpTodo.helptodoBackend.service;

import static org.junit.Assert.*;

import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.domain.TodoList;
import HelpTodo.helptodoBackend.repository.TodoListRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TodoListServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TodoListService todoListService;
    @Autowired
    TodoListRepository todoListRepository;



    @Test
    public void 투두리스트생성(){

        Team team1 = createTeam("team1");
        Team team2 = createTeam("team2");

        TodoList todolist = new TodoList();
        todolist.setTitle("todolist");
        todolist.setTeam(team1);
        todoListService.createTodoList(todolist);

        TodoList todolist2 = new TodoList();
        todolist2.setTitle("todolist2");
        todolist2.setTeam(team1);
        todoListService.createTodoList(todolist2);

        TodoList todolist3 = new TodoList();
        todolist3.setTitle("todolist3");
        todolist3.setTeam(team2);
        todoListService.createTodoList(todolist3);



        List<TodoList> findTodoL = todoListRepository.findAllByTeamName(team1.getName());

        assertEquals(2, findTodoL.size());
    }

    private Member createMember(String name){

        Member member = new Member();
        member.setName(name);
        member.setLoginId(name);
        member.setLoginPw(name);

        em.persist(member);

        return member;
    }

    private Team createTeam(String name){
        Team team = new Team();
        team.setName(name);

        em.persist(team);

        return team;
    }

    @Test
    public void 투두리스트조회(){

    }
}