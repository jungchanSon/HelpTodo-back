package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.Form.Member.LoginForm;
import HelpTodo.helptodoBackend.Form.Member.SignupForm;
import HelpTodo.helptodoBackend.domain.LoginIdPw;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final ApplicationContext context;


    //회원 가입 컨트롤러
    @RequestMapping("/members/signup")
    public String signup(@Valid SignupForm signupForm, BindingResult result){
//        Environment env = context.getEnvironment();
        
        if (result.hasErrors()) {
            return "fail";
        }

        Member member = new Member();

        member.setName(signupForm.getName());

        member.setLoginId(signupForm.getId());
        member.setLoginId(signupForm.getPw());

        log.info("loginIdPw.getId() = " + member.getLoginId());
        log.info("loginIdPw.getPw() = " + member.getLoginPw());
        log.info("member.getName() = " + member.getName());
        
        memberService.signup(member);
        return "succ";
    }
    @RequestMapping("/members/login")
    public String login(@Valid LoginForm form, BindingResult result){
        if (result.hasErrors()) {
            return null;
        }

        Member member = new Member();

        member.setLoginId(form.getId());
        member.setLoginPw(form.getPw());

        String userName = memberService.login(member);

        log.info("Login UserName : " + userName);
        return userName;
    }
}
