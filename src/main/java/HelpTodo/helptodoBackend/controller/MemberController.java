package HelpTodo.helptodoBackend.controller;

import HelpTodo.helptodoBackend.Form.MemberForm;
import HelpTodo.helptodoBackend.domain.LoginIdPw;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String signup(@Valid MemberForm memberForm, BindingResult result){
        Environment env = context.getEnvironment();
        
        if (result.hasErrors()) {
            return env.getProperty("localurl.front");
        }

        Member member = new Member();

        member.setName(memberForm.getName());

        LoginIdPw loginIdPw = new LoginIdPw(memberForm.getId(), memberForm.getPw());
        member.setLoginIdPw(loginIdPw);

        System.out.println("loginIdPw.getId() = " + loginIdPw.getId());
        System.out.println("loginIdPw.getPw() = " + loginIdPw.getPw());
        System.out.println("member.getName() = " + member.getName());
        
        memberService.signup(member);
        return "succ";
    }
}
