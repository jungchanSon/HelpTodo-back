package HelpTodo.helptodoBackend.repository;

import HelpTodo.helptodoBackend.domain.Todolist;
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

    public void save(Todolist todolist){
        em.persist(todolist);
    }

    public List<Todolist> findAllByTeamName(String teamName){
        List<Todolist> todoLists = em.createQuery("select tl from Todolist tl where tl.team.name = :teamName", Todolist.class)
            .setParameter("teamName", teamName)
            .getResultList();

        return todoLists;
    }
}
