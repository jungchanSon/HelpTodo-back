package HelpTodo.helptodoBackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<?> memberExceptionHandler(MemberException e){

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(e.getErrorCode().name() + " " + e.getMessage());
    }
}
