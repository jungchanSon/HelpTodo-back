package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.DTO.teamContoller.FindTeam;
import HelpTodo.helptodoBackend.Form.team.JoinTeamForm;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.Form.team.CreateTeamForm;
import HelpTodo.helptodoBackend.service.TeamService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;

    //생성 후 -> 생성된 팀에 멤버 가입 시키기
    @RequestMapping("/team/create")
    public ResponseEntity createTeam(@Valid CreateTeamForm createTeamForm, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("create team Fail");
        }

        String teamName = createTeamForm.getTeamName();
        String memberId = createTeamForm.getMemberId();
        String teamPassword = createTeamForm.getTeamPassword();

        Team team = new Team();
        team.setName(teamName);
        team.setPassword(teamPassword);
        team.setCreator(memberId);

        teamService.createTeam(memberId, team);

//        joinTeamService.join(createTeamForm.getMemberId(), teamId);

        return ResponseEntity.ok().body("create team OK");
    }

    @RequestMapping("/team/join")
    public ResponseEntity joinTeam(@Valid JoinTeamForm joinTeamForm, BindingResult result){
        System.out.println(joinTeamForm.getUserId());
        System.out.println(joinTeamForm.getTeamName());
        System.out.println(joinTeamForm.getTeamPassword());
        if (result.hasErrors()) {
            return null;
        }

        if(joinTeamForm.getTeamPassword() != null){
            System.out.println("join 3 param");
            teamService.join(joinTeamForm.getUserId(),
                             joinTeamForm.getTeamName(),
                             joinTeamForm.getTeamPassword());
        } else{
            Long join = teamService.join(joinTeamForm.getUserId(), joinTeamForm.getTeamName());
        }


        // 팀에 가입 -> 자신이 속하지 않은 팀 리스트 반환 -> 프론트 갱신
        return ResponseEntity.ok().body(findOtherTeamList(joinTeamForm.getUserId()));
    }

    @RequestMapping(value = "/team/findTeamList")
    public ResponseEntity findTeamList(){

        List<Team> allTeams = teamService.findAllTeams();
        List<FindTeam> list = new ArrayList<>();

        if (!allTeams.isEmpty()) {
            for(Team t : allTeams) {

                FindTeam findTeam = FindTeam.responseFindTeam(t.getName(), t.getCreator(), t.getCreateDate());

                list.add(findTeam);
            }
            return ResponseEntity.ok().body(list);

        }
        return null;
    }

    @RequestMapping(value = "/team/findOtherTeamList" )
    public ResponseEntity findOtherTeamList(@RequestParam(name="userId") String userId){

        HashSet<Team> allTeams = teamService.findOtherTeams(userId);
        List<FindTeam> resultTeams = new ArrayList<>();
        for(Team t : allTeams){
            FindTeam findTeam = FindTeam.responseFindTeam(t.getName(),
                                                          t.getCreator(),
                                                          t.getCreateDate());

            resultTeams.add(findTeam);
        }
        return ResponseEntity.ok().body(resultTeams);

    }

    @RequestMapping(value = "/team/findMyTeam" )
    public ResponseEntity findMyTeams(@RequestParam(name="userId") String userId){

        List<Team> myTeams = teamService.findMyTeams(userId);

        List<FindTeam> resultTeams = new ArrayList<>();
        for(Team t : myTeams){
            FindTeam findTeam = FindTeam.responseFindTeam(t.getName(),
                                                          t.getCreator(),
                                                          t.getCreateDate());

            resultTeams.add(findTeam);
        }
        return ResponseEntity.ok().body(resultTeams);

    }

}
