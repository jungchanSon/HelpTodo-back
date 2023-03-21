package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.Form.team.JoinTeamForm;
import HelpTodo.helptodoBackend.domain.MemberTeam;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.exception.ErrorCode_Team;
import HelpTodo.helptodoBackend.exception.TeamException;
import HelpTodo.helptodoBackend.repository.MemberRepository;
import HelpTodo.helptodoBackend.repository.TeamRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public String createTeam(Team team, String memberId) {
//        validateDuplicateTeam(team);
//        validateEmpty(team);
        validateCreateTeam(team);

        Member member = memberRepository.findOne(memberId);
        MemberTeam.createMemberTeam(member, team);

        teamRepository.save(team);

        return team.getName();
    }

    private void validateCreateTeam(Team team) {
        // validate empty name
        if(team.getName() == null || team.getName() == ""){
            throw new TeamException(ErrorCode_Team.CREATE_TEAM_NAME_NULL, "팀 이름이 비어 있음");
        }

        // validate empty creatorid
        if(team.getCreatorId() == null || team.getCreatorId() == ""){
            throw new TeamException(ErrorCode_Team.CREATE_TEAM_CREATOR_ID_NULL, "방장 이름이 공백");
        }

        // validate Duplicate (중복 검사)
        List<Team> findTeams = teamRepository.findByTeamName(team.getName());
        if (!findTeams.isEmpty()) {
            throw new TeamException(ErrorCode_Team.CREATE_TEAM_DUPLICATED, "중복된 팀");
        }

        // 올바르지 않은 CreateId
        List<Member> creators = memberRepository.findByMemberId(team.getCreatorId());
        if (creators.isEmpty()){
            throw new TeamException(ErrorCode_Team.CREATE_TEAM_CREATOR_NULL, "존재 하지 않는 회원이 방 개설");
        }
    }


    @Transactional
    public Long join(JoinTeamForm joinTeamForm){
        String teamPassword = joinTeamForm.getTeamPassword();
        Member member = memberRepository.findOne(joinTeamForm.getUserId());
        Team team = teamRepository.findOne(joinTeamForm.getTeamName());

        if (!teamPassword.isEmpty()){ //팀 비번 있으면 확인.
            validateTeamPassword(team, teamPassword);
        }

        MemberTeam memberTeam = MemberTeam.createMemberTeam(member, team);
        teamRepository.save(team);

        return memberTeam.getId();
    }

    private void validateTeamPassword(Team team, String teamPassword) {
        String collectPassword = team.getPassword();

        if(!collectPassword.equals(teamPassword)){
            throw new TeamException(ErrorCode_Team.VALIDATE_TEAM_PASSWORD_WRONG, "");
        }
    }
    public List<Team> findAllTeams(){

        List<Team> allTeams = teamRepository.findAll();

        return allTeams;
    }

    public List<Team> findMyTeams(String memberId){

        Member member = memberRepository.findOne(memberId);
        List<MemberTeam> myMemberTeam = member.getMemberTeam();

        List<Team> myTeamList = new ArrayList<>();
        for(MemberTeam jt : myMemberTeam){
            Team myTeam = jt.getTeam();

            myTeamList.add(myTeam);
        }

        return myTeamList;
    }

    public HashSet<Team> findOtherTeams(String memberId){

        List<Team> teamWithoutUser = teamRepository.findTeamWithoutUser(memberId);
        HashSet<Team> result = new HashSet<>(teamWithoutUser);

        return result;
    }

    public Team findTeamsByName(String name){
        List<Team> findTeams = teamRepository.findByTeamName(name);
        Team team = findTeams.get(0);

        return team;
    }

    public Team findOneByName(String name){

        Team findTeam = teamRepository.findOne(name);

        return findTeam;
    }
}
