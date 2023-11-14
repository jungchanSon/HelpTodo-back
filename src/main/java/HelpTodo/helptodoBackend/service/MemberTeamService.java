package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.MemberTeam;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.repository.MemberTeamReponsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberTeamService {

    private final MemberTeamReponsitory memberTeamReponsitory;

    public void remove(MemberTeam mt, Team team, Member member){
        memberTeamReponsitory.deleteEntity(mt.getId());
    }
}
