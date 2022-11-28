package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.Form.team.JoinTeamForm;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.form.team.CreateTeamForm;
import HelpTodo.helptodoBackend.service.MemberService;
import HelpTodo.helptodoBackend.service.TeamService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;
    private final MemberService memberService;

    //생성 후 -> 생성된 팀에 멤버 가입 시키기
    @RequestMapping("/team/create")
    public String createTeam(@Valid CreateTeamForm createTeamForm, BindingResult result) {

        if (result.hasErrors()) {
            return "fail";
        }

        String teamName = createTeamForm.getTeamName();
        String memberId = createTeamForm.getMemberId();
        String teamPassword = createTeamForm.getTeamPassword();

        Team team = new Team();
        team.setName(teamName);
        team.setPassword(teamPassword);

        String teamId = teamService.createTeam(memberId, team);

//        joinTeamService.join(createTeamForm.getMemberId(), teamId);

        return "succ";
    }

    @RequestMapping("/team/join")
    public String joinTeam(@Valid JoinTeamForm joinTeamForm, BindingResult result){

        if (result.hasErrors()) {
            return "fail";
        }

        Long join = teamService.join(joinTeamForm.getUserId(), joinTeamForm.getTeamName());

        return "succ";
    }

    @RequestMapping(value = "/team/findTeamList" )
    public Map findTeamList(){

        List<Team> allTeams = teamService.findAllTeams();
        if (!allTeams.isEmpty()) {

            Map findResult = new HashMap<String, Object>();
            for(Team t : allTeams) {
                System.out.println(t.getName());
                findResult.put(t.getName(), t);
            }
            return findResult;
        }
        return null;
    }


}
