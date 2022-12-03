package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.domain.Tdd;
import HelpTodo.helptodoBackend.domain.TodoList;
import HelpTodo.helptodoBackend.repository.TodoListRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoListService {

    private final TodoListRepository todoListRepository;

    @Transactional
    public Long createTodoList(TodoList todolist){

        todoListRepository.save(todolist);
        return todolist.getId();
    }

    public List<TodoList> findAllByTeamName(String teamName){
        List<TodoList> allTeam = todoListRepository.findAllByTeamName(teamName);
        return allTeam;
    }

//    public void createTDDEntity(Long todoListId, Object TDDEntity){
//
//        TodoList findTodolist = todoListRepository.findOneById(todoListId);
//
//        if (TDDEntity.getClass() == Todo.class) {
//            findTodolist.addTodos((Todo) TDDEntity);
//            todoListRepository.save(findTodolist);
//
//        } else if(TDDEntity.getClass() == Doing.class){
//            findTodolist.addDoing((Doing)TDDEntity);
//            todoListRepository.save(findTodolist);
//
//        } else if(TDDEntity.getClass() == Done.class){
//            findTodolist.addDone((Done)TDDEntity);
//            todoListRepository.save(findTodolist);
//
//        }
//    }

    public void createTDDEntity(Long todoListId, Tdd TDDEntity){

        TodoList findTodolist = todoListRepository.findOneById(todoListId);
        findTodolist.addTdd(TDDEntity);

        todoListRepository.save(findTodolist);

    }
}
