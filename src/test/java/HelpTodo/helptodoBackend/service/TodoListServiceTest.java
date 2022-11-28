package HelpTodo.helptodoBackend.service;

import static org.junit.Assert.*;

import HelpTodo.helptodoBackend.domain.Doing;
import HelpTodo.helptodoBackend.domain.Done;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.domain.Todo;
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
    public void 투두리스트여러사람생성(){

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

    @Test
    public void 투두리스트생성(){
        //투두리스트 생성
        Member member = createMember("member");
        Team team = createTeam("team");

        TodoList todolist = TodoList.createTodolist("todolist", team, member);
        Long todoListId = todoListService.createTodoList(todolist);

        TodoList findTodoList = todoListRepository.findOneById(todoListId);
        printTodo(findTodoList);
        assertEquals(todolist, findTodoList);
    }

    @Test
    public void 투두리스트TDD추가(){
        Member member = createMember("member");
        Team team = createTeam("team");

        TodoList todolist = TodoList.createTodolist("todolist", team, member);
        Long todoListId = todoListService.createTodoList(todolist);

        //저장된 투두리스트
        Todo todo = new Todo();
        todo.setContent("todo1");

        Doing doing = new Doing();
        doing.setContent("doing1");

        Done done = new Done();
        done.setContent("Done1");

        todoListService.createTDDEntity(todoListId, todo);
        todoListService.createTDDEntity(todoListId, doing);
        todoListService.createTDDEntity(todoListId, done);

        Todo todo2 = new Todo();
        todo2.setContent("todo2");

        Done done2 = new Done();
        done2.setContent("Done2");

        todoListService.createTDDEntity(todoListId, todo2);
        todoListService.createTDDEntity(todoListId, done2);

        TodoList findtodolist = todoListRepository.findOneById(todoListId);

        printTodo(findtodolist);

        int sumSize = findtodolist.getTodos().size() + findtodolist.getDoings().size()
            + findtodolist.getDones().size();

        assertEquals(5, sumSize);
    }

    @Test
    public void 투두리스트조회(){

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



    private void printTodo(TodoList findTodoList) {
        System.out.println("id : " + findTodoList.getId());
        System.out.println("title : " + findTodoList.getTitle());
        System.out.println("mem : " + findTodoList.getMember());
        System.out.println("team : " + findTodoList.getTeam());
        System.out.println();
        System.out.println("TODOS");
        for (Todo t : findTodoList.getTodos()){
            System.out.println(t.getContent());
        }
        System.out.println("DOINGS");
        for (Doing d : findTodoList.getDoings()){
            System.out.println(d.getContent());
        }
        System.out.println("DONES");
        for (Done d : findTodoList.getDones()){
            System.out.println(d.getContent());
        }
    }

}