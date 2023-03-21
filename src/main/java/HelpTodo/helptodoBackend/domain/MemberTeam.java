package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "member_team")
@Getter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberTeam {

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
    @JoinColumn(name = "team_name")
    private Team team;

    public void setMember(Member member) {
        this.member = member;
        member.getMemberTeam().add(this);
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMemberTeams().add(this);
    }
    public static MemberTeam createMemberTeam(Member member, Team team){
        MemberTeam memberTeam = new MemberTeam();
        memberTeam.setMember(member);
        memberTeam.setTeam(team);

        return memberTeam;
    }
}
