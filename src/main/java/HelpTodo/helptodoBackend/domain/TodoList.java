package HelpTodo.helptodoBackend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "todolist")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Builder
public class TodoList {

    @Id @GeneratedValue
    @Column(name = "todolist_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "team_name")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "memberId")
    private Member member;


//    @OneToMany(mappedBy = "todolist" , fetch = FetchType.LAZY)
//    private List<Todo> todos = new ArrayList<>();
//
//    @OneToMany(mappedBy = "todolist", fetch = FetchType.LAZY)
//    private List<Doing> doings = new ArrayList<>();
//
//    @OneToMany(mappedBy = "todolist", fetch = FetchType.LAZY)
//    private List<Done> dones = new ArrayList<>();

    @OneToMany(mappedBy = "todolist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tdd> tdds = new ArrayList<>();

    // TODO: 2022-11-27 테스트 코드 에러 고치고, private으로
    public void setTeam(Team team) {
        this.team = team;
        team.getTodolists().add(this);
    }

    private void setMember(Member member) {
        this.member = member;
        member.getTodolist().add(this);
    }

    private void setTitle(String title){
        this.title = title;
    }
    // TODO: 2022-11-27 요부분 다시 생각해보기
    // public / private 고민해보기
//    public void addTodos(Todo todo) {
//        this.todos.add(todo);
//        todo.setTodolist(this);
//    }
//
//    public void addDoing(Doing doing) {
//        this.doings.add(doing);
//        doing.setTodolist(this);
//    }
//
//    public void addDone(Done done) {
//        this.dones.add(done);
//        done.setTodolist(this);
//    }
    public void addTdd(Tdd tdd){
        this.tdds.add(tdd);
        tdd.setTodolist(this);
    }

//    public static TodoList createTodolist(
//        String title,
//        Team team,
//        Member member
//    ) {
//        TodoList todoList = new TodoList();
//        todoList.setTitle(title);
//        todoList.setTeam(team);
//        todoList.setMember(member);
//
//        return todoList;
//    }
}
