//package HelpTodo.helptodoBackend.domain;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Inheritance;
//import javax.persistence.InheritanceType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//// TODO: 2022-11-27 조인테이블 전략 고민해보기
//@Entity
//@Table(name = "todo")
//@Getter
//@Setter
//@EntityListeners(AuditingEntityListener.class)
//public class Todo {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "todo_id")
//    private Long id;
//
//    private String content;
//
//    @CreatedDate
//    private LocalDateTime createDate;
//
//    private int  importance;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "todolist_id")
//    private TodoList todolist;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @OneToMany(mappedBy = "todo")
//    private List<TodoComment> todoComments = new ArrayList<>();
//
//    public void setMember(Member member) {
//        this.member = member;
//        member.getTodos().add(this);
//    }
//
//    public static Todo createTodo(String content, int importance, Member member){
//        Todo todo = new Todo();
//        todo.setContent(content);
//        todo.setImportance(importance);
//
//        todo.setMember(member);
//
//        return todo;
//    }
//}
