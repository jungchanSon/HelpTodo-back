package HelpTodo.helptodoBackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode_Member {
    SIGNUP_MEMBER_ID_NULL(HttpStatus.BAD_REQUEST, ""),
    SIGNUP_MEMBER_PW_NULL(HttpStatus.BAD_REQUEST, ""),
    SIGNUP_MEMBER_NAME_NULL(HttpStatus.BAD_REQUEST, ""),
    SIGNUP_DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, ""),

    LOGIN_MEMBER_NULL(HttpStatus.BAD_REQUEST, ""),
    LOGIN_MEMBER_PW_WRONG(HttpStatus.BAD_REQUEST, "");

    private HttpStatus httpStatus;
    private String message;
}
