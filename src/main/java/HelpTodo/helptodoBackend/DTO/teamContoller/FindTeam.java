package HelpTodo.helptodoBackend.DTO.teamContoller;

import java.time.LocalDateTime;

public class FindTeam {
    private String name;
    private String creator;
    private LocalDateTime createDate;


    public FindTeam(String name, String creator, LocalDateTime createDate) {
        this.name = name;
        this.creator = creator;
        this.createDate = createDate;
    }

}
