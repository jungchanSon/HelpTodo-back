package HelpTodo.helptodoBackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TeamException extends RuntimeException{

    private ErrorCode_Team errorCode;
    private String message;
}
