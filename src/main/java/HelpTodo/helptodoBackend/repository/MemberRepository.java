package HelpTodo.helptodoBackend.repository;

import HelpTodo.helptodoBackend.domain.Member;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * save 저장
 * findOne id->Member
 * findALl ->MemberList
 * findByName name-> memberList
 */

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(String id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }

    public List<Member> findByMemberId(String memberId){
        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
            .setParameter("loginId", memberId)
            .getResultList();
    }
}
