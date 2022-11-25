package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.repository.TeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public String createTeam(Team team) {
        validateDuplicateTeam(team);

        teamRepository.save(team);

        return team.getName();
    }

    public Team findTeamByName(String name){

        List<Team> findTeams = teamRepository.findByName(name);
        Team team = findTeams.get(0);

        return team;
    }


    private void validateDuplicateTeam(Team team) {
        List<Team> findTeams = teamRepository.findByName(team.getName());
        if (!findTeams.isEmpty()) {
            throw new IllegalStateException("중복 회원");
        }
    }
}
