package HelpTodo.helptodoBackend.DTO.TodoListController;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

// TODO: 2022-12-01 TDD 인터페이스 추가하기
@Getter @Setter
public class ResponseDoing {
    private String content;
    private LocalDateTime createDate;

    public static ResponseDoing createResponseTodo(String content, LocalDateTime localDateTime){
        ResponseDoing resonseDoing = new ResponseDoing();
        resonseDoing.setContent(content);
        resonseDoing.setCreateDate(localDateTime);

        return resonseDoing;
    }
}
