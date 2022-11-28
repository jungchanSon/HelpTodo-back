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

        Team team = new Team();
        team.setName("team");

        Team team2 = new Team();
        team2.setName("team2");

        teamService.createTeam(member.getName(), team);
        teamService.createTeam(member1.getName(), team2);
        teamService.join(member1.getLoginId(), team.getName());

        Team findOne = teamRepository.findOne(team.getName());
        Team findOne2 = teamRepository.findOne(team2.getName());

        List<JoinTeam> joinTeams1 = findOne.getJoinTeams();
        List<JoinTeam> joinTeams2 = findOne2.getJoinTeams();

        for(JoinTeam joinTeam : joinTeams1) {
            System.out.println(joinTeam.getMember().getName());
        }
        for(JoinTeam joinTeam : joinTeams2) {
            System.out.println(joinTeam.getMember().getName());
        }
        assertEquals(2, joinTeams1.size());
        assertEquals(1, joinTeams2.size());
    }

    @Test
    public void 모든팀조회(){
        Team team1 = createTeam("team1");
        Team team2 = createTeam("team2");

        List<Team> allTeams = teamService.findAllTeams();

        for(Team team : allTeams){
            System.out.println(team.getName());
        }

        assertEquals(2, allTeams.size());
    }

    private Member createMember(String name, String Id, String Pw){
        Member member = new Member();
        member.setName(name);
        member.setLoginId(Id);
        member.setLoginPw(Pw);

        em.persist(member);

        return member;
    }

    private Team createTeam(String name){
        Team team = new Team();
        team.setName(name);

        em.persist(team);
        
        return team;
    }

}