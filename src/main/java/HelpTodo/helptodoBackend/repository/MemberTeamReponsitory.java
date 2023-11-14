package HelpTodo.helptodoBackend.repository;

import HelpTodo.helptodoBackend.domain.MemberTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class MemberTeamReponsitory{

    @PersistenceContext
    private EntityManager em;

    public void save(MemberTeam memberTeam){
        em.persist(memberTeam);
    }
    @Transactional
    public void deleteEntity(Long memberTeamId){
        MemberTeam memberTeam = em.find(MemberTeam.class, memberTeamId);

        em.remove(memberTeam);
    }
}
