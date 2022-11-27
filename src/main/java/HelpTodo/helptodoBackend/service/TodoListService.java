package HelpTodo.helptodoBackend.service;

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
    public String createTodoList(TodoList todolist){

        todoListRepository.save(todolist);
        return todolist.getTitle();
    }

    public List<TodoList> findAllByTeamName(String teamName){
        List<TodoList> allTeam = todoListRepository.findAllByTeamName(teamName);

        return allTeam;
    }
}
