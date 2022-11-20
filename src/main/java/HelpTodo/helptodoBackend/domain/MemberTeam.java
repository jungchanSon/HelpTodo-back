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
public class MemberTeam {

    @Id
    @GeneratedValue
    @Column(name = "member_team_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member
        member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
