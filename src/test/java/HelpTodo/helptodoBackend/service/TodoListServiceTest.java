package HelpTodo.helptodoBackend.service;

import static org.junit.Assert.*;

import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.domain.Todolist;
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

        Todolist todolist = new Todolist();
        todolist.setTitle("todolist");
        todolist.setTeam(team1);
        todoListService.createTodoList(todolist);

        Todolist todolist2 = new Todolist();
        todolist.setTitle("todolist2");
        todolist.setTeam(team1);
        todoListService.createTodoList(todolist2);

        Todolist todolist3 = new Todolist();
        todolist.setTitle("todolist3");
        todolist.setTeam(team2);
        todoListService.createTodoList(todolist3);

        Todolist todolist4 = new Todolist();
        todolist.setTitle("todolist4");
        todolist.setTeam(team2);
        todoListService.createTodoList(todolist4);

        List<Todolist> findTodoL = todoListRepository.findAllByTeamName(team2.getName());
        List<Todolist> all = todoListRepository.findAll();
        System.out.println(all.size());

        for(Todolist item : all){
            System.out.println(item.getTeam().getName());
        }

        assertEquals(4, all.size());
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