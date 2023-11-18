package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.Form.ExitTeamForm;
import HelpTodo.helptodoBackend.Form.team.CreateTeamForm;
import HelpTodo.helptodoBackend.Form.team.FindMyMembersForm;
import HelpTodo.helptodoBackend.Form.team.JoinTeamForm;
import HelpTodo.helptodoBackend.domain.MemberTeam;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.exception.ErrorCode_Team;
import HelpTodo.helptodoBackend.exception.TeamException;
import HelpTodo.helptodoBackend.repository.MemberRepository;
import HelpTodo.helptodoBackend.repository.MemberTeamReponsitory;
import HelpTodo.helptodoBackend.repository.TeamRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import HelpTodo.helptodoBackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final MemberTeamReponsitory memberTeamReponsitory;

    @Transactional
    public String createTeam(CreateTeamForm createTeamForm, String token) {
//        validateDuplicateTeam(team);
//        validateEmpty(team);
        String tokenValue = JwtUtil.getTokenValue(token);
        String creatorId = JwtUtil.getMemberId(tokenValue, secretKey);
        Member member = memberRepository.findOne(creatorId);
        String memberName = member.getName();
        Team team = new Team().builder()
                .name(createTeamForm.getTeamName())
                .password(createTeamForm.getTeamPassword())
                .creatorId(creatorId)
                .creatorName(memberName)
                .build();

        validateCreateTeam(team);

        MemberTeam memberTeam = MemberTeam.createMemberTeam(member, team);
//        team.addMemberTeam(memberTeam);
        teamRepository.save(team);
        memberTeamReponsitory.save(memberTeam);
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
    public Long join(JoinTeamForm joinTeamForm, String token){
        String teamPassword = joinTeamForm.getTeamPassword();

        String tokenValue = JwtUtil.getTokenValue(token);
        String memberId = JwtUtil.getMemberId(tokenValue, secretKey);

        Member member = memberRepository.findOne(memberId);
        log.info("teamname : {}", joinTeamForm.getTeamName());
        log.info("memberPW : {}", joinTeamForm.getTeamPassword());
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

    public List<Team> findMyTeams(String token){

        String tokenValue = JwtUtil.getTokenValue(token);
        String memberId = JwtUtil.getMemberId(tokenValue, secretKey);
        Member member = memberRepository.findOne(memberId);
        List<MemberTeam> myMemberTeam = member.getMemberTeam();

        List<Team> myTeamList = new ArrayList<>();
        for(MemberTeam jt : myMemberTeam){
            Team myTeam = jt.getTeam();

            myTeamList.add(myTeam);
        }

        return myTeamList;
    }

    public HashSet<Team> findOtherTeams(String token){

        String tokenValue = JwtUtil.getTokenValue(token);
        String memberId = JwtUtil.getMemberId(tokenValue, secretKey);
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

    public List<Member> findMembers(String teamName) {
        Team findTeam = teamRepository.findOne(teamName);
        List<MemberTeam> memberTeams = findTeam.getMemberTeams();

        List<Member> membersInTeam = new ArrayList();
        for(MemberTeam memberTeam : memberTeams){
            membersInTeam.add(memberTeam.getMember());
        }

        return membersInTeam;
    }

//    마저 완성하기
    @Transactional
    public void exitTeam(ExitTeamForm exitTeamForm, String token) {
        String tokenValue = JwtUtil.getTokenValue(token);
        String memberId = JwtUtil.getMemberId(tokenValue, secretKey);
        Member member = memberRepository.findByMemberId(memberId).get(0);
        log.info("Mem : {}", member);

        Team findTeam = teamRepository.findOne(exitTeamForm.getTeamName());
        log.info("team : {}", findTeam);

        List<MemberTeam> memberTeams = findTeam.getMemberTeams();

        for (MemberTeam memberTeam : memberTeams) {
            if (Objects.equals(memberTeam.getMember().getLoginId(), memberId)){
                findTeam.getMemberTeams().remove(memberTeam);
                teamRepository.removeMemberTeam(memberTeam);
                break;
            }
        }
        if (memberTeams.size() == 0){
            teamRepository.removeTeam(findTeam);
        }
    }

    public List<String> findMyMembers(FindMyMembersForm requestForm) {
        String teamName = requestForm.getTeamName();
        Team team = teamRepository.findOne(teamName);
        List<MemberTeam> memberTeams = team.getMemberTeams();

        List<String> responseList = new ArrayList();

        for (MemberTeam memberTeam : memberTeams) {
            responseList.add(memberTeam.getMember().getName());
        }
        return responseList;
    }
}
