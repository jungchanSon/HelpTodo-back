package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.Form.Member.SignupForm;
import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.exception.ErrorCode_Member;
import HelpTodo.helptodoBackend.exception.MemberException;
import HelpTodo.helptodoBackend.repository.MemberRepository;
import HelpTodo.helptodoBackend.util.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j

// TODO: 2023-03-21 패스워드 인코딩 디코딩 추가하기
public class MemberService {

    private final MemberRepository memberRepository;

    @Value("${jwt.secretKey}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60 * 10l;

    @Transactional
    public String signup(SignupForm requestSignUpForm){

        Member member = new Member().builder()
                                    .name(requestSignUpForm.getName())
                                    .loginId(requestSignUpForm.getId())
                                    .loginPw(requestSignUpForm.getPw())
                                    .build();

        validateSignup(member);

        memberRepository.save(member);

        return member.getLoginId();
    }

    private void validateSignup(Member member) {

        if (member.getLoginId() == null || member.getLoginId() == "") {
            throw new MemberException(ErrorCode_Member.SIGNUP_MEMBER_ID_NULL, "ID가 비어있음");
        }
        if (member.getLoginPw() == null || member.getLoginPw() == ""){
            throw new MemberException(ErrorCode_Member.SIGNUP_MEMBER_PW_NULL, "PW가 비어있음");
        }
        if (member.getName() == null || member.getName() == ""){

            throw new MemberException(ErrorCode_Member.SIGNUP_MEMBER_NAME_NULL, "NAME이 비어있음");
        }

        List<Member> findMembers = memberRepository.findByMemberId(member.getLoginId());
        if (!findMembers.isEmpty()) {
            throw new MemberException(ErrorCode_Member.SIGNUP_DUPLICATED_MEMBER, "중복 회원");
        }
    }

    public String login(Member member){

        Member findMember = memberRepository.findOne(member.getLoginId());
        validateLogin(findMember, member);

        return JwtUtil.createJwt(member.getLoginId(), secretKey, expiredMs);
    }

    private void validateLogin(Member findMember, Member savedMember) {
        // 존재하지 않는 회원
        if(findMember == null){
            throw new MemberException(ErrorCode_Member.LOGIN_MEMBER_NULL, "존재하지 않는 회원");
        }
        //패스워드 틀림
        if (!findMember.getLoginPw().equals(savedMember.getLoginPw())) {
            throw new MemberException(ErrorCode_Member.LOGIN_MEMBER_PW_WRONG, "PW 틀림");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(String memberId){
        return memberRepository.findOne(memberId);
    }
}