package HelpTodo.helptodoBackend.service;

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
    public String signup(Member member){
//        validateDuplicateMember(member);
//        validateEmpty(member);
        validateSignup(member);

        memberRepository.save(member);

        return member.getLoginId();
    }

    private void validateSignup(Member member) {
        log.info("member.id : {}", member.getLoginId());
        log.info("member.pw : {}", member.getLoginPw());
        log.info("member.name : {}", member.getName());
        if (member.getLoginId() == null || member.getLoginId() == "") {
            throw new MemberException(ErrorCode_Member.SIGNUP_MEMBER_ID_NULL, "ID가 비어있음");
        }
        if (member.getLoginPw() == null || member.getLoginPw() == ""){
            throw new MemberException(ErrorCode_Member.SIGNUP_MEMBER_PW_NULL, "PW가 비어있음");
        }
        if (member.getName() == null || member.getName() == ""){

            throw new MemberException(ErrorCode_Member.SIGNUP_MEMBER_NAME_NULL, "NAME이 비어있음");
        }

        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new MemberException(ErrorCode_Member.SIGNUP_DUPLICATED_MEMBER, "중복 회원");
        }
    }


    public String login(Member member){

        Member findMember = memberRepository.findOne(member.getLoginId());

        validateLogin(findMember, member);

//        //id 잘못 입력
//        if(findMember == null){
//            throw new IllegalStateException("아이디 존재 하지 않음");
//        }
//
//        //pw 불합격
//        if (!findMember.getLoginPw().equals(member.getLoginPw())) {
//            throw new IllegalStateException("비번 틀림");
//        }

        return JwtUtil.createJwt(member.getLoginId(), secretKey, expiredMs);
    }

    private void validateLogin(Member findMember, Member savedMember) {
        if(findMember == null){
            throw new MemberException(ErrorCode_Member.LOGIN_MEMBER_NULL, "존재하지 않는 회원");

        }

        //pw 불합격
        if (!findMember.getLoginPw().equals(savedMember.getLoginPw())) {
            throw new MemberException(ErrorCode_Member.LOGIN_MEMBER_PW_WRONG, "PW 틀림");
        }
    }

    //    private void validateEmpty(Member member) {
//
//        if (member.getLoginId() == null) {
//            throw new IllegalStateException("id가 비어 있음");
//        }
//        if (member.getLoginPw() == null){
//            throw new IllegalStateException("pw가 비어 있음");
//        }
//        if (member.getName() == null){
//            throw new IllegalStateException("name이 비어 있음");
//        }
//    }
//
//    private void validateDuplicateMember(Member member) {
//        List<Member> findMembers = memberRepository.findByName(member.getName());
//        if (!findMembers.isEmpty()) {
//            throw new IllegalStateException("중복 회원");
//        }
//    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(String memberId){
        return memberRepository.findOne(memberId);
    }



}