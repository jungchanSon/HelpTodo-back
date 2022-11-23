package HelpTodo.helptodoBackend.repository;

import HelpTodo.helptodoBackend.domain.JoinTeam;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JoinTeamRepository {

    private final EntityManager em;

    public void save(JoinTeam joinTeam){
        em.persist(joinTeam);
    }
}
