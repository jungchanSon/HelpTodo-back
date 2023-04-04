package HelpTodo.helptodoBackend.Form.TodoListForm;

import HelpTodo.helptodoBackend.domain.TddType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeTddTypeForm {
    String teamName;
    Long tddId;
    TddType tddType;
}
