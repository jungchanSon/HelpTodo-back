package HelpTodo.helptodoBackend.DTO.TodoListController;

import HelpTodo.helptodoBackend.domain.Tdd;
import HelpTodo.helptodoBackend.domain.TddType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter @Setter
public class ResponseTodoList {
    private Long id;
    private String title;
    private String creator;
    private LocalDateTime createDate;

    private List<ResponseTdd> resTodos = new ArrayList<>();
    private List<ResponseTdd> resDoings = new ArrayList<>();
    private List<ResponseTdd> resDones = new ArrayList<>();

    public static ResponseTodoList createResponseTodolist(Long id,
                                                          String title,
                                                          String creator,
                                                          LocalDateTime createDate,
                                                          @Nullable List<Tdd> tdds){

        ResponseTodoList responseTodoList = new ResponseTodoList();

        responseTodoList.setId(id);
        responseTodoList.setTitle(title);
        responseTodoList.setCreator(creator);
        responseTodoList.setCreateDate(createDate);

        for(Tdd t : tdds){
            TddType type = t.getTddtype();
            ResponseTdd responseTodo = ResponseTdd.builder()
                    .tddId(t.getId())
                    .content(t.getContent())
                    .createDate(t.getCreateDate())
                    .important(t.getImportance())
                    .startDate(t.getStartDate())
                    .endDate(t.getEndDate())
                    .manager(t.getManager())
                    .build();

            if(type == TddType.TODO){
                responseTodoList.resTodos.add(responseTodo);
            } else if(type == TddType.DOING){

                responseTodoList.resDoings.add(responseTodo);
            } else if(type == TddType.DONE){

                responseTodoList.resDones.add(responseTodo);
            }

        }

//        if(todos != null){
//            for(Todo todo : todos){
//        ResponseTdd responseTodo = ResponseTdd.createResponseTdd(todo.getId(),
//                                                                 todo.getContent(),
//                                                                 todo.getCreateDate());
//
//        responseTodoList.resTodos.add(responseTodo);
//            }
//        }

//        if(doings != null){
//            for(Doing doing : doings){
//                ResponseTdd responseTodo = ResponseTdd.createResponseTdd(doing.getId(),
//                                                                         doing.getContent(),
//                                                                         doing.getCreateDate());
//
//                responseTodoList.resDoings.add(responseTodo);
//            }
//        }
//
//        if(dones != null){
//            for(Done done : dones){
//                ResponseTdd responseTodo = ResponseTdd.createResponseTdd(done.getId(),
//                                                                         done.getContent(),
//                                                                         done.getCreateDate());
//
//                responseTodoList.resDones.add(responseTodo);
//            }
//        }

        return responseTodoList;
    }
}
