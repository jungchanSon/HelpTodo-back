package HelpTodo.helptodoBackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TodoListException extends RuntimeException {

    private ErrorCode_TodoList errorCode;
    private String message;

}
