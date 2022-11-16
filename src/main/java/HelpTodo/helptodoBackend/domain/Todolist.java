package HelpTodo.helptodoBackend.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todolist")
@Getter @Setter
public class Todolist {

    @Id @GeneratedValue
    @Column(name = "todolist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
