package HelpTodo.helptodoBackend.service;

import static org.junit.Assert.*;

import HelpTodo.helptodoBackend.domain.Team;
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
public class TeamServiceTest {

    @Autowired TeamService teamService;
    @Autowired
    TeamRepository teamRepository;

    @Test
    public void 팀생성(){
        Team team = new Team();
        team.setName("t");
        team.setPassword("p");

        String createTeam = teamService.createTeam(team);

        assertEquals(team, teamRepository.findOne(createTeam));
    }


}