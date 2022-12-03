package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="members")
@Getter @Setter // TODO: 2022-11-27 Setter 지우기.
@EntityListeners(AuditingEntityListener.class)
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
    private List<TodoList> todolist = new ArrayList<>();

//    memberTeam 매핑
    @OneToMany(mappedBy = "member", cascade= CascadeType.ALL)
    private List<JoinTeam> joinTeam = new ArrayList<>();

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
    private List<Tdd> tdds = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TodoComment> todoComments = new ArrayList<>();

//
//    private List<JoinTeam> addJoinTeam(JoinTeam joinTeam){
//        this.joinTeams.add
//    }
//
//    private void createMember(String name, String id, String pw, JoinTeam joinTeam) {
//        Member member = new Member();
//
//        member.name = name;
//        member.loginId =id;
//        member.loginPw = pw;
//
//    }
}
