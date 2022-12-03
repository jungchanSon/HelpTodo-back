package HelpTodo.helptodoBackend.DTO.TodoListController;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseDone {
    private String content;
    private LocalDateTime createDate;

    public static ResponseDone createResponseTodo(String content, LocalDateTime localDateTime){
        ResponseDone responseDone = new ResponseDone();
        responseDone.setContent(content);
        responseDone.setCreateDate(localDateTime);

        return responseDone;
    }
}
