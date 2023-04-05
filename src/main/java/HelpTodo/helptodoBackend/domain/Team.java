package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@Table(name = "team")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Team {

    @Id
    @Column(name = "team_name")
    private String name;

    private String teamCode;

    private String password;

    private String creatorId;

    private String creatorName;

    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MemberTeam> memberTeams = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TodoList> todolists = new ArrayList<>();

    public void setCreatorId(String memberId){
        this.creatorId = memberId;
    }

    public void add(MemberTeam memberTeam){
        memberTeam.setTeam(this);
        this.memberTeams.add(memberTeam);

    }

    public void addJoinTeam (MemberTeam memberTeam) {
        memberTeams.add(memberTeam);
        memberTeam.setTeam(this);
    }

    public void setName(String name) {
        this.name = name;
    }
    public static Team createTeam(String name, MemberTeam... memberTeams){
        Team team = new Team();
        team.setName(name);
        for(MemberTeam jointTeam : memberTeams){
            team.addJoinTeam(jointTeam);
        }

        return team;
    }
}
