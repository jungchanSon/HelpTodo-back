package HelpTodo.helptodoBackend.DTO.TodoListController;

import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTdd {
    private Long tddId;
    private String content;
    private LocalDateTime createDate;
    private int important;

    public static ResponseTdd createResponseTdd(Long id,String content, LocalDateTime localDateTime, int important){
        ResponseTdd responseTdd = ResponseTdd.builder()
                        .tddId(id)
                .content(content)
                .createDate(localDateTime)
                .important(important)
                .build();
        return responseTdd;
    }
}
