package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "member_team")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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
        member.addMemberTeam(this);
    }
    public void setTeam(Team team) {
        this.team = team;
        team.addMemberTeam(this);
    }

    public void removeM(){
        this.member = null;
    }
    public void removeT(){
        this.team = null;
    }
    public static MemberTeam createMemberTeam(Member member, Team team){
        MemberTeam memberTeam = new MemberTeam();
        memberTeam.setMember(member);
        memberTeam.setTeam(team);

        return memberTeam;
    }
}
