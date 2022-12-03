package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "todo_comment")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class TodoComment {

    @Id
    @GeneratedValue
    @Column(name = "todo_comment_id")
    private Long id;

    private String content;

    @CreatedDate
    private LocalDateTime createDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="todo_id")
//    private Todo todo;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="doing_id")
//    private Doing doing;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="done_id")
//    private Done done;
    @ManyToOne(fetch = FetchType.LAZY)
    private Tdd tdd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
}
