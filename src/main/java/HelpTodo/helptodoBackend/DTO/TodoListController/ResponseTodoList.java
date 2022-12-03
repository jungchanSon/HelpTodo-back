package HelpTodo.helptodoBackend.DTO.TodoListController;

import HelpTodo.helptodoBackend.domain.Doing;
import HelpTodo.helptodoBackend.domain.Done;
import HelpTodo.helptodoBackend.domain.Todo;
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
                                                          @Nullable List<Todo> todos,
                                                          @Nullable List<Doing> doings,
                                                          @Nullable List<Done> dones){

        ResponseTodoList responseTodoList = new ResponseTodoList();

        responseTodoList.setId(id);
        responseTodoList.setTitle(title);
        responseTodoList.setCreator(creator);
        responseTodoList.setCreateDate(createDate);

        if(todos != null){
            for(Todo todo : todos){
                ResponseTdd responseTodo = ResponseTdd.createResponseTdd(todo.getContent(),
                                                                         todo.getCreateDate());

                responseTodoList.resTodos.add(responseTodo);
            }
        }

        if(doings != null){
            for(Doing doing : doings){
                ResponseTdd responseTodo = ResponseTdd.createResponseTdd(doing.getContent(),
                                                                         doing.getCreateDate());

                responseTodoList.resDoings.add(responseTodo);
            }
        }

        if(dones != null){
            for(Done done : dones){
                ResponseTdd responseTodo = ResponseTdd.createResponseTdd(done.getContent(),
                                                                         done.getCreateDate());

                responseTodoList.resDones.add(responseTodo);
            }
        }

        return responseTodoList;
    }
}
