package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.DTO.teamContoller.FindTeam;
import HelpTodo.helptodoBackend.Form.team.JoinTeamForm;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.form.team.CreateTeamForm;
import HelpTodo.helptodoBackend.service.MemberService;
import HelpTodo.helptodoBackend.service.TeamService;
import com.fasterxml.jackson.core.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.JSONParser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        team.setCreator(memberId);

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

    @GetMapping(value = "/team/findTeamList" )
    public List<FindTeam> findTeamList(){

        List<Team> allTeams = teamService.findAllTeams();
        List<FindTeam> list = new ArrayList<>();

        if (!allTeams.isEmpty()) {
            for(Team t : allTeams) {

                FindTeam findTeam = FindTeam.responseFindTeam(t.getName(), t.getCreator(), t.getCreateDate());

                list.add(findTeam);
            }
            return list;
        }
        return null;
    }
    @GetMapping(value = "/team/findMyTeam" )
    public List<FindTeam> findMyTeams(@RequestParam(name="userId") String userId){

        List<Team> myTeams = teamService.findMyTeams(userId);

        List<FindTeam> resultTeams = new ArrayList<>();
        for(Team t : myTeams){
            FindTeam findTeam = FindTeam.responseFindTeam(t.getName(),
                                                          t.getCreator(),
                                                          t.getCreateDate());

            resultTeams.add(findTeam);
        }
        return resultTeams;
    }

}
