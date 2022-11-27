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

    public void save(Team team){
        em.persist(team);
    }

    public Team findOne(String name){
        return em.find(Team.class, name);
    }


//    public List<Team> findByName(String name){
//        return em.createQuery("select t from Team t where t.name = :name", Team.class)
//            .setParameter("name", name)
//            .getResultList();
//    }

    public List<Team> findAll(){
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }
}
