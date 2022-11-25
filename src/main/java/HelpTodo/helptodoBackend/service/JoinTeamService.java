package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.domain.JoinTeam;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.repository.JoinTeamRepository;
import HelpTodo.helptodoBackend.repository.MemberRepository;
import HelpTodo.helptodoBackend.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JoinTeamService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final JoinTeamRepository joinTeamRepository;

    @Transactional
    public Long join(String memberId, String teamName){
        Member member = memberRepository.findOne(memberId);
        Team team = teamRepository.findOne(teamName);

        JoinTeam joinTeam = JoinTeam.createJoinTeam(member, team);

        joinTeamRepository.save(joinTeam);

        return joinTeam.getId();
    }
}
