package HelpTodo.helptodoBackend.DTO.TodoListController;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseTdd {
    private String content;
    private LocalDateTime createDate;

    public static ResponseTdd createResponseTdd(String content, LocalDateTime localDateTime){
        ResponseTdd responseTdd = new ResponseTdd();
        responseTdd.setContent(content);
        responseTdd.setCreateDate(localDateTime);

        return responseTdd;
    }
}
