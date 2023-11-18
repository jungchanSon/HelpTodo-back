package HelpTodo.helptodoBackend.repository;

import HelpTodo.helptodoBackend.domain.Tdd;
import HelpTodo.helptodoBackend.domain.TddType;
import HelpTodo.helptodoBackend.domain.TodoList;

import java.time.LocalDate;
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

    public void deleteTdd(Long tddId) {
        Tdd tdd = em.find(Tdd.class, tddId);

        em.remove(tdd);

    }

    public void deleteTodoList(Long todoListId) {
        TodoList todoList = em.find(TodoList.class, todoListId);

        em.remove(todoList);
    }

    public void changeTddType(Long tddId, TddType tddtype){
        Tdd tdd = em.find(Tdd.class, tddId);

        tdd.setTddtype(tddtype);

        em.persist(tdd);
    }

    public void changeTodoDate(Long id, String startDate, String endDate) {
        Tdd tdd = em.find(Tdd.class, id);

        tdd.setStartDate(LocalDate.parse(startDate));
        tdd.setEndDate(LocalDate.parse(endDate));

        em.persist(tdd);
    }
}
