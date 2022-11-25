package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.domain.Member;
import HelpTodo.helptodoBackend.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

//    레포지토리 생성자 주입 방식 적용
    private final MemberRepository memberRepository;


    @Transactional
    public String signup(Member member){
        validateDuplicateMember(member);
        validateEmpty(member);
        memberRepository.save(member);

        return member.getLoginId();
    }


    public String login(Member member){
        validateLogin(member);
        Member one = memberRepository.findOne(member.getLoginId());
        return one.getName();
    }

    private void validateEmpty(Member member) {

        if (member.getLoginId() == null) {
            throw new IllegalStateException("id가 비어 있음");
        }
        if (member.getLoginPw() == null){
            throw new IllegalStateException("pw가 비어 있음");
        }
        if (member.getName() == null){
            throw new IllegalStateException("name이 비어 있음");
        }
    }

    private void validateLogin(Member member) {
        Member findMember = memberRepository.findOne(member.getLoginId());

        //id 잘못 입력
        if(findMember == null){
            throw new IllegalStateException("아이디 존재 하지 않음");
        }
        //pw 불합격
        if (!findMember.getLoginPw().equals(member.getLoginPw())) {
            throw new IllegalStateException("비번 틀림");
        }


    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("중복 회원");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(String memberId){
        return memberRepository.findOne(memberId);
    }



}