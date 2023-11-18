package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.form.Member.LoginForm;
import HelpTodo.helptodoBackend.form.Member.SignupForm;
import HelpTodo.helptodoBackend.service.MemberService;

import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원 가입 컨트롤러
    @RequestMapping( value = "/members/signup", method = {RequestMethod.POST})
    public ResponseEntity signup(@Valid SignupForm signupForm, BindingResult result){

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("signup Fail");
        }

        memberService.signup(signupForm);
        return ResponseEntity.ok().body("signup OK");
    }

    @RequestMapping( value = "/members/login", method = {RequestMethod.POST})
    public ResponseEntity login(@Valid LoginForm form, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("login Fail");
        }

        Map responseLoginResult = memberService.login(form);

        return ResponseEntity.ok().body(responseLoginResult);
    }
}
