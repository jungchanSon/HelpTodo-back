package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "member_team")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class JoinTeam {

    @Id
    @GeneratedValue
    @Column(name = "member_team_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public void setMember(Member member) {
        this.member = member;
        member.getJoinTeam().add(this);
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getJoinTeams().add(this);
    }
    public static JoinTeam createJoinTeam(Member member, Team team){
        JoinTeam joinTeam = new JoinTeam();
        joinTeam.setMember(member);
        joinTeam.setTeam(team);

        return joinTeam;
    }
}
