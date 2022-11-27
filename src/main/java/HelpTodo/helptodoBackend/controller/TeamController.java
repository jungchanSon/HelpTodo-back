package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.form.team.CreateTeamForm;
import HelpTodo.helptodoBackend.service.MemberService;
import HelpTodo.helptodoBackend.service.TeamService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;
    private final MemberService memberService;
    private final JoinTeamService joinTeamService;

    //생성 후 -> 생성된 팀에 멤버 가입 시키기
    @RequestMapping("/team/create")
    public String createTeam(@Valid CreateTeamForm createTeamForm, BindingResult result) {

        if (result.hasErrors()) {
            return "fail";
        }

        Team team = new Team();

        team.setName(createTeamForm.getTeamName());
        team.setPassword(createTeamForm.getTeamPassword());

//        String teamId = teamService.createTeam(team);

//        joinTeamService.join(createTeamForm.getMemberId(), teamId);

        return "succ";
    }
}
