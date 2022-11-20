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
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="members")
@Getter @Setter
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

//    memberTeam 매핑
    @OneToMany(mappedBy = "member", cascade= CascadeType.ALL)
    private List<JoinTeam> joinTeam = new ArrayList<>();

//    todo, doing, done 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Doing> doings = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Done> dones = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TodoComment> todoComments = new ArrayList<>();

}
