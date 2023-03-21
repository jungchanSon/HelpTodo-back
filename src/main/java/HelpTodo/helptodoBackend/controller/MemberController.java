package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.Form.Member.LoginForm;
import HelpTodo.helptodoBackend.Form.Member.SignupForm;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.service.MemberService;
import java.util.Date;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원 가입 컨트롤러
    @RequestMapping(value = "/members/signup")
    public ResponseEntity signup(@Valid SignupForm signupForm, BindingResult result){

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("signup Fail");
        }

        Member member = new Member().builder()
                                    .name(signupForm.getName())
                                    .loginId(signupForm.getId())
                                    .loginPw(signupForm.getPw())
                                    .build();

        memberService.signup(member);
        return ResponseEntity.ok().body("signup OK");
    }

    @RequestMapping("/members/login")
    public ResponseEntity login(@Valid LoginForm form, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("login Fail");
        }

        Member member = new Member()
                            .builder()
                            .loginId(form.getId())
                            .loginPw(form.getPw())
                            .build();

        String token = memberService.login(member);

        return ResponseEntity.ok().body(token);
    }
}
