package HelpTodo.helptodoBackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberException extends RuntimeException {

    private ErrorCode_Member errorCode;
    private String message;

}
