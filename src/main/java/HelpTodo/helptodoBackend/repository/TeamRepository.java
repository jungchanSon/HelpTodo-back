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


    public List<Team> findByTeamName(String name){
        return em.createQuery("select t from Team t where t.name = :name", Team.class)
            .setParameter("name", name)
            .getResultList();
    }

    public List<Team> findTeamWithoutUser(String userId){
//        return em.createQuery("select jt.team from JoinTeam jt where jt.team.name <> (select jt.team.name from JoinTeam jt where jt.member.loginId = :userId)", Team.class)
        return em.createQuery("select jt.team from JoinTeam jt where jt.team "
                                  + "not in (select t.team from JoinTeam t where t.member.loginId = :userId)", Team.class)
            .setParameter("userId", userId)
            .getResultList();
//        (select team.* from team, member_team  where member_team.member_id <> :userId, Team.class)
//        .setParameter("userId", userId)
//            .getResultList();
//        순수 SQL은 위처럼...
    }

    public List<Team> findAll(){
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }

}
