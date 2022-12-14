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
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//@Entity
//@Table(name = "done")
//@Getter
//@Setter
//@EntityListeners(AuditingEntityListener.class)
//public class Done {
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
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @OneToMany(mappedBy = "done")
//    private List<TodoComment> todoComments = new ArrayList<>();
//
//
//    public void setMember(Member member) {
//        this.member = member;
//        member.getDones().add(this);
//    }
//
//    public static Done createDone(String content, int importance, Member member){
//        Done done = new Done();
//        done.setContent(content);
//        done.setImportance(importance);
//
//        done.setMember(member);
//
//        return done;
//    }
//}
