package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@Table(name = "team")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Team {

    @Id
    @Column(name = "team_name")
    private String name;

    private String teamCode;

    private String password;

    private String creator;

    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @Builder.Default
    private List<JoinTeam> joinTeams = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TodoList> todolists = new ArrayList<>();

    public void setCreator(String memberId){
        this.creator = memberId;
    }

    public void add(JoinTeam joinTeam){
        joinTeam.setTeam(this);
        this.joinTeams.add(joinTeam);

    }

    public void addJoinTeam (JoinTeam joinTeam) {
        joinTeams.add(joinTeam);
        joinTeam.setTeam(this);
    }

    public void setName(String name) {
        this.name = name;
    }
    public static Team createTeam(String name, JoinTeam... joinTeams){
        Team team = new Team();
        team.setName(name);
        for(JoinTeam jointTeam : joinTeams){
            team.addJoinTeam(jointTeam);
        }

        return team;
    }
}
