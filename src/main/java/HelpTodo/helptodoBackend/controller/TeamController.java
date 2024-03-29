package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.DTO.teamContoller.ResponseTeam;
import HelpTodo.helptodoBackend.form.ExitTeamForm;
import HelpTodo.helptodoBackend.form.team.FindMyMembersForm;
import HelpTodo.helptodoBackend.form.team.JoinTeamForm;
import HelpTodo.helptodoBackend.domain.Team;
import HelpTodo.helptodoBackend.form.team.CreateTeamForm;
import HelpTodo.helptodoBackend.service.MemberService;
import HelpTodo.helptodoBackend.service.MemberTeamService;
import HelpTodo.helptodoBackend.service.TeamService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;
    private final MemberTeamService memberTeamService;
    private final MemberService memberService;

    @Value("${jwt.secretKey}")
    private String secretKey;

    //생성 후 -> 생성된 팀에 멤버 가입 시키기
    @RequestMapping(value = "/team/create", method = {RequestMethod.POST})
    public ResponseEntity createTeam(@RequestHeader(value = "Authorization") String token, @Valid CreateTeamForm createTeamForm, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("create team Fail");
        }
        teamService.createTeam(createTeamForm, token);

        return ResponseEntity.ok().body("create team OK");
    }

    @RequestMapping(value = "/team/join", method = {RequestMethod.POST})
    public ResponseEntity joinTeam(@RequestHeader(value = "Authorization") String token, @Valid JoinTeamForm joinTeamForm, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Fail join team");
        }

        teamService.join(joinTeamForm, token);

        // TODO:팀에 가입 -> 자신이 속하지 않은 팀 리스트 반환 -> 프론트 갱신
        return ResponseEntity.ok().body("팀 가입 완료");
    }

    @RequestMapping(value = "/team/exitTeam", method = {RequestMethod.POST})
    public ResponseEntity exitTeam(@RequestHeader(value = "Authorization") String token, @Valid ExitTeamForm exitTeamForm, BindingResult result) {

        teamService.exitTeam(exitTeamForm, token);

        return ResponseEntity.ok().body("");

    }
    @RequestMapping(value = "/team/findTeamList", method = {RequestMethod.POST})
    public ResponseEntity findAllTeamList(){

        List<Team> allTeams = teamService.findAllTeams();
        List<ResponseTeam> responseAllTeamList = new ArrayList<>();

        for(Team t : allTeams) {

            ResponseTeam responseTeam = ResponseTeam.builder()
                                                    .name(t.getName())
                                                    .creatorName(t.getCreatorId())
                                                    .createDate(t.getCreateDate())
                                                    .build();
            responseAllTeamList.add(responseTeam);
        }

        return ResponseEntity.ok().body(responseAllTeamList);
    }

    @RequestMapping(value = "/team/findOtherTeamList", method = {RequestMethod.POST} )
    public ResponseEntity findOtherTeamList(@RequestHeader(value = "Authorization") String token){


        HashSet<Team> allTeams = teamService.findOtherTeams(token);
        List<ResponseTeam> responseOtherTeams = new ArrayList<>();

        for(Team t : allTeams){
            log.info("team : {}", t);
            ResponseTeam responseTeam = ResponseTeam.builder()
                                                    .name(t.getName())
                                                    .creatorName(t.getCreatorName())
                                                    .createDate(t.getCreateDate())
                                                    .build();
            if(t.getPassword().isEmpty()){
                responseTeam.setHasPassword(false);
            }
            else {
                responseTeam.setHasPassword(true);
            }
            responseOtherTeams.add(responseTeam);
        }
        return ResponseEntity.ok().body(responseOtherTeams);
    }

    @RequestMapping(value = "/team/findMyTeam" , method = {RequestMethod.POST})
    public ResponseEntity findMyTeams(@RequestHeader(value = "Authorization") String token){

        List<Team> myTeams = teamService.findMyTeams(token);
        List<ResponseTeam> responseMyTeams = new ArrayList<>();

        for(Team t : myTeams){
            ResponseTeam responseTeam = ResponseTeam.builder()
                                                    .name(t.getName())
                                                    .creatorName(t.getCreatorName())
                                                    .createDate(t.getCreateDate())
                                                    .build();
            responseMyTeams.add(responseTeam);
        }
        return ResponseEntity.ok().body(responseMyTeams);
    }

    @RequestMapping(value = "/team/findMyMembers", method = {RequestMethod.POST})
    public ResponseEntity findMyMembers(@RequestHeader(value = "Authorization") String token, FindMyMembersForm requestForm){

        List<String> myMembers = teamService.findMyMembers(requestForm);

        return ResponseEntity.ok().body(myMembers);
    }
}
