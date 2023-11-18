package HelpTodo.helptodoBackend.form.TodoListForm;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AddTDDForm {

    String teamName;
    String Content;
    Long TodoListId;
    String startDate;
    String endDate;
    String manager;

    int importance;
}
