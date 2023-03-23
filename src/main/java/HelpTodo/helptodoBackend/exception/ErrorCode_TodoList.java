package HelpTodo.helptodoBackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode_TodoList {

    CREATE_TDD_ENTITY_WRONG_TYPE (HttpStatus.BAD_REQUEST, ""),
    MEMBER_NOT_BELONG_TEAM (HttpStatus.BAD_REQUEST, "");

    private HttpStatus httpStatus;
    private String message;
}
