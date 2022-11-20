package HelpTodo.helptodoBackend.service;

import static org.junit.Assert.*;

import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setLoginId("kim");
        member.setLoginPw("kim");
        member.setName("kim");

        String saveId = memberService.signup(member);

        assertEquals(member, memberRepository.findOne((saveId)));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member1.setName("kim");

        memberService.signup(member1);
        memberService.signup(member2);

        fail("예외가 발생해야 함 ");

    }

    @Test
    public void 로그인(){
        //given
        Member member = new Member();
        member.setName("jungchan");
        member.setLoginId("son");
        member.setLoginPw("1234");

        memberService.signup(member);

        //when
//        Member loginMember = new Member();
//        loginMember.setLoginId("son");
//        loginMember.setLoginPw("1324");
//
//        String userName = memberService.login(loginMember);
//
//        //then
//        assertEquals(userName, memberService.findOne("son").getName());


    }

}