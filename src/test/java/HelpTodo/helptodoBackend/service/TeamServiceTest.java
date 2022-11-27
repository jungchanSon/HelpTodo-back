package HelpTodo.helptodoBackend.service;

import static org.junit.Assert.*;

import HelpTodo.helptodoBackend.domain.JoinTeam;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.repository.TeamRepository;
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
public class TeamServiceTest {

    @Autowired TeamService teamService;
    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 팀생성(){
        Member member = createMember("son", "son", "son");

        Team team = new Team();
        team.setName("t");
        team.setPassword("p");

        String createTeamName = teamService.createTeam(member.getLoginId(), team);
        Team findTeam = teamRepository.findOne(createTeamName);

        List<JoinTeam> joinTeams = findTeam.getJoinTeams();

        for(JoinTeam joinTeam : joinTeams )
            System.out.println(joinTeam.getMember());

        JoinTeam joinTeam = joinTeams.get(0);
        Member member1 = joinTeam.getMember();
        assertEquals(member, member1);
        assertEquals(team, teamRepository.findOne(createTeamName));
    }


    @Test
    public void 팀참가(){
        Member member = createMember("son", "son", "son");
        Member member1 = createMember("jung", "jung", "jung");

        JoinTeam joinTeam = new JoinTeam();
        joinTeam.setMember(member);
//        Team t1 = Team.createTeam("t", joinTeam);

//        String createTeam = teamService.createTeam(t1);

        Team t = teamService.findTeamByName("t");
        List<JoinTeam> joinTeams = t.getJoinTeams();

        for(JoinTeam jt : joinTeams){
            System.out.println(jt.getMember().getName()+"--"+jt.getTeam().getName());
        }
        System.out.println(teamRepository.findAll());
    }

    private Member createMember(String name, String Id, String Pw){
        Member member = new Member();
        member.setName(name);
        member.setLoginId(Id);
        member.setLoginPw(Pw);

        em.persist(member);

        return member;
    }


}