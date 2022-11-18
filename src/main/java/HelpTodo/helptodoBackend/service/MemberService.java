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
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public String signup(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getLoginId();
    }

    public String login(Member member){
        String userName = validateLogin(member);

        return userName;
    }

    //TODO: 추후에 프론트에 넘겨줄 데이터가 널어나면, 따로 클래스 만들어서 전달할 것.
    private String validateLogin(Member member) {
        Member findMember = memberRepository.findOne(member.getLoginId());

        //id 잘못 입력
        if(findMember == null){
            throw new IllegalStateException("아이디 존재 하지 않음");
        }

        //Pw 통과-> 로그인
        if (findMember.getLoginPw().equals(member.getLoginPw())) {
            return findMember.getName();
        }

        //id , pw 불합격
        throw new IllegalStateException("비번 틀림");

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