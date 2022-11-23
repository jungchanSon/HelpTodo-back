package HelpTodo.helptodoBackend.service;

import static org.junit.Assert.*;

import HelpTodo.helptodoBackend.domain.JoinTeam;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.repository.JoinTeamRepository;
import HelpTodo.helptodoBackend.repository.MemberRepository;
import HelpTodo.helptodoBackend.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JoinTeamServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    JoinTeamService joinTeamService;
    @Autowired
    JoinTeamRepository joinTeamRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamService teamService;

    @Test
    public void 팀참가(){
        Team team = new Team();
        team.setName("t");
        team.setPassword("p");
        String createTeam = teamService.createTeam(team);

        Member member = new Member();
        member.setLoginId("kim");
        member.setLoginPw("kim");
        member.setName("kim");
        String saveId = memberService.signup(member);

        Long join = joinTeamService.join(saveId, createTeam);
        JoinTeam one = joinTeamRepository.findOne(join);
        Team team1 = one.getTeam();
        Member member1 = one.getMember();


        assertEquals(team1, team);
        assertEquals(member1, member);
    }
}