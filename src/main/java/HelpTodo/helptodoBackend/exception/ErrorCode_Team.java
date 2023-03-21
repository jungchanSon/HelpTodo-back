package HelpTodo.helptodoBackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode_Team {

    CREATE_TEAM_NAME_NULL (HttpStatus.BAD_REQUEST, ""),
    CREATE_TEAM_CREATOR_ID_NULL(HttpStatus.BAD_REQUEST, ""),
    CREATE_TEAM_DUPLICATED (HttpStatus.BAD_REQUEST, ""),
    CREATE_TEAM_CREATOR_NULL (HttpStatus.BAD_REQUEST, ""),
    VALIDATE_TEAM_PASSWORD_WRONG (HttpStatus.BAD_REQUEST, "");

    private HttpStatus httpStatus;
    private String message;
}
