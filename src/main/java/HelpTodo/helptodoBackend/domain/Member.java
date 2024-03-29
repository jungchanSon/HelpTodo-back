package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="members")
@Getter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Member {

    @Id
    private String loginId;
    private String loginPw;

    private String name;

    @CreatedDate
    private LocalDateTime createDate;


//    @Embedded
//    private LoginIdPw loginIdPw;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TodoList> todolist = new ArrayList<>();

//    memberTeam 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MemberTeam> memberTeam = new ArrayList<>();

//    todo, doing, done 매핑
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Todo> todos = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Doing> doings = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Done> dones = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Tdd> tdds = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TodoComment> todoComments = new ArrayList<>();

    //
//    private List<JoinTeam> addJoinTeam(JoinTeam joinTeam){
//        this.joinTeams.add
//    }
//
    public static Member createMember(String name, String id, String pw) {
        Member member = new Member();

        member.name = name;
        member.loginId =id;
        member.loginPw = pw;

        return member;
    }
    public void addMemberTeam(MemberTeam memberTeam){
        this.memberTeam.add(memberTeam);
    }
}
