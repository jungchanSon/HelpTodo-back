package HelpTodo.helptodoBackend.repository;

import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TeamRepository {
    @PersistenceContext
    private EntityManager em;


    public Team findOne(Long id){
        return em.find(Team.class, id);
    }

    public List<Team> findAll(){
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }
}
