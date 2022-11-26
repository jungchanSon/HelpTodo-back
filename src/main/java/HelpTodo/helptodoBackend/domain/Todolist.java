package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "todolist")
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Todolist {

    @Id @GeneratedValue
    @Column(name = "todolist_id")
    private Long id;


    @CreatedDate
    private LocalDateTime createDate;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_name")
    private Team team;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;


    @OneToMany(mappedBy = "todolist" , fetch = FetchType.LAZY)
    private List<Todo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "todolist", fetch = FetchType.LAZY)
    private List<Doing> doing = new ArrayList<>();

    @OneToMany(mappedBy = "todolist", fetch = FetchType.LAZY)
    private List<Done> done = new ArrayList<>();


}
