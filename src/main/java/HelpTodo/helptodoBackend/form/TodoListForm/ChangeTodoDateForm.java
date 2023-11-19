package HelpTodo.helptodoBackend.form.TodoListForm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeTodoDateForm {
    Long id;
    String teamName;
    String startDate;
    String endDate;
}
