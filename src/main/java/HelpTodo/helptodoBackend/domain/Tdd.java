package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Tdd")
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tdd {

    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private TddType tddtype; //todo doing done
    @CreatedDate
    private LocalDateTime createDate;

    private int  importance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todolist_id")
    private TodoList todolist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "tdd")
    @Builder.Default
    private List<TodoComment> todoComments = new ArrayList<>();


    public void setMember(Member member) {
        this.member = member;
        member.getTdds().add(this);
    }

    public static Tdd createTdd(TddType type, String content,  int importance, Member member){
        Tdd tdd = new Tdd().builder()
            .content(content)
            .importance(importance)
            .tddtype(type)
            .member(member)
            .build();

        return tdd;
    }
    public void setTodolist(TodoList todolist){
        this.todolist = todolist;
    }
}
