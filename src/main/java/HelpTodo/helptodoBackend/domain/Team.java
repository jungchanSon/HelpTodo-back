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
@Table(name = "team")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Team {

    @Id
    @Column(name = "team_name")
    private String name;
    private String teamCode;

    private String password;

    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<JoinTeam> joinTeams = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Todolist> todolists = new ArrayList<>();

//    public void addJoinTeam (JoinTeam joinTeam) {
//        joinTeams.add(joinTeam);
//        joinTeam.setTeam(this);
//    }
//
//    public static Team createTeam(String name, JoinTeam... joinTeams){
//        Team team = new Team();
//        team.setName(name) ;
//        for(JoinTeam jt : joinTeams){
//            team.addJoinTeam(jt);
//        }
//
//        return team;
//    }
}
