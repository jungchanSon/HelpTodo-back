package HelpTodo.helptodoBackend.service;

import HelpTodo.helptodoBackend.domain.Todolist;
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
    public String createTodoList(Todolist todolist){

        todoListRepository.save(todolist);
        return todolist.getTitle();
    }

    public List<Todolist> findAllByTeamName(String teamName){
        List<Todolist> allTeam = todoListRepository.findAllByTeamName(teamName);

        return allTeam;
    }
}
