package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.domain.JoinTeam;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.repository.MemberRepository;
import HelpTodo.helptodoBackend.repository.TeamRepository;
import java.util.ArrayList;
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
    public String createTeam(String memberId, Team team) {
        validateDuplicateTeam(team);
        validateEmpty(team);
        //TODO : 회원이 로그인한 상태인지 검증 추가하기

        Member member = memberRepository.findOne(memberId);
        JoinTeam joinTeam = JoinTeam.createJoinTeam(member, team);

        teamRepository.save(team);

        return team.getName();
    }

    @Transactional
    public Long join(String memberId, String teamName, String teamPassword){
        Member member = memberRepository.findOne(memberId);
        Team team = teamRepository.findOne(teamName);

        System.out.println("join ser : ");
        System.out.println(team.getPassword());
        System.out.println(teamPassword);
        validateTeamPassword(team, teamPassword);

        JoinTeam joinTeam = JoinTeam.createJoinTeam(member, team);

        teamRepository.save(team);
        return joinTeam.getId();
    }

    private void validateTeamPassword(Team team, String teamPassword) {
        String collectPassword = team.getPassword();

        if(!collectPassword.equals(teamPassword)){
            throw new IllegalStateException(collectPassword + "  " + teamPassword);
        }
    }

    @Transactional
    public Long join(String memberId, String teamName){
        Member member = memberRepository.findOne(memberId);
        Team team = teamRepository.findOne(teamName);

        JoinTeam joinTeam = JoinTeam.createJoinTeam(member, team);

        teamRepository.save(team);
        return joinTeam.getId();
    }
    public List<Team> findAllTeams(){

        List<Team> findAllTeams = teamRepository.findAll();

        return findAllTeams;
    }

    public List<Team> findMyTeams(String memberId){

        List<Team> teams = new ArrayList<>();

        Member member = memberRepository.findOne(memberId);
        List<JoinTeam> myJoinTeam = member.getJoinTeam();

        for(JoinTeam jt : myJoinTeam){
            Team myteam = jt.getTeam();

            teams.add(myteam);
        }
        return teams;
    }

    public List<Team> findOtherTeams(String memberId){

        List<Team> teams = new ArrayList<>();

        List<Team> teamWithoutUser = teamRepository.findTeamWithoutUser(memberId);
        return teamWithoutUser;
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

    private void validateEmpty(Team team) {
        if(team.getName() == null){
            throw new IllegalStateException("팀 이름 공백");
        }
    }

    private void validateDuplicateTeam(Team team) {
        List<Team> findTeams = teamRepository.findByTeamName(team.getName());
        if (!findTeams.isEmpty()) {
            throw new IllegalStateException("");
        }
    }


}
