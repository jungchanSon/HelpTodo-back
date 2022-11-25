package HelpTodo.helptodoBackend.controller;


import HelpTodo.helptodoBackend.service.JoinTeamService;
import HelpTodo.helptodoBackend.service.MemberService;
import HelpTodo.helptodoBackend.service.TeamService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinTeamController {

    private final MemberService memberService;
    private final JoinTeamService joinTeamService;
    private final TeamService teamService;

    @RequestMapping("/jointeam/join")
    public String joinTeam(@RequestParam("userId") String userId,
                         @RequestParam("teamName") String teamName){

        joinTeamService.join(userId, teamName);

        return "succ";
    }
}
