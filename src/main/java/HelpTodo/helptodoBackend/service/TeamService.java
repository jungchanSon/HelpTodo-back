package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    //Todo: 추후에 teamCode 중복 생성될 수있는 상황 체크하는것도 좋을 거같다.
    @Transactional
    public String createTeam(Team team){

        TeamRepository.save(team);

        return team.getName();
    }
}
