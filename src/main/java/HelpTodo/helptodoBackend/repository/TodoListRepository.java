package HelpTodo.helptodoBackend.repository;

import HelpTodo.helptodoBackend.domain.TodoList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoListRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(TodoList todolist){
        em.persist(todolist);
    }

    public List<TodoList> findAllByTeamName(String teamName){
        List<TodoList> todoLists = em.createQuery("select tl from TodoList tl where tl.team.name = :teamName", TodoList.class)
            .setParameter("teamName", teamName)
            .getResultList();

        return todoLists;
    }

    public TodoList findOneById(Long todolistId){
        TodoList todoList = em.find(TodoList.class, todolistId);

        return todoList;
    }
}
