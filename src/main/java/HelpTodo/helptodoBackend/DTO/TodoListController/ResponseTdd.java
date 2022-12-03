package HelpTodo.helptodoBackend.DTO.TodoListController;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseTdd {
    private Long tddId;
    private String content;
    private LocalDateTime createDate;

    public static ResponseTdd createResponseTdd(Long id,String content, LocalDateTime localDateTime){
        ResponseTdd responseTdd = new ResponseTdd();

        responseTdd.setTddId(id);
        responseTdd.setContent(content);
        responseTdd.setCreateDate(localDateTime);

        return responseTdd;
    }
}
