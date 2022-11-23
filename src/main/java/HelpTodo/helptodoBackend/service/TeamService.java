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

}
