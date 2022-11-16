package HelpTodo.helptodoBackend.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doing")
@Getter
@Setter
public class Doing {

    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    private String content;

    @Temporal(TemporalType.DATE)
    private Date createDate;

    private int  importance;

    @ManyToOne
    @JoinColumn(name = "todolist_id")
    private Todolist todolist;

}
