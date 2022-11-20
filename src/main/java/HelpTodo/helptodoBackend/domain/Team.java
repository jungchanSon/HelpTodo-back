package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "team")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private long id;

    private String name;
    private String teamCode;

    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<MemberTeam> memberTeams = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Todolist> todolists = new ArrayList<>();
}
