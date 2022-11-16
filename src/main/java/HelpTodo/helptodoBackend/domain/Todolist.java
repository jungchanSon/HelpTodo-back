package HelpTodo.helptodoBackend.domain;

import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "todolist" , cascade= CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "todolist", cascade= CascadeType.ALL)
    private List<Doing> doing = new ArrayList<>();

    @OneToMany(mappedBy = "todolist", cascade= CascadeType.ALL)
    private List<Done> done = new ArrayList<>();
}
