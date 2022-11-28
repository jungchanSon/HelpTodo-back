package HelpTodo.helptodoBackend.DTO.teamContoller;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class FindTeam {
    private String name;
    private String creator;
    private LocalDateTime createDate;


    public static FindTeam responseFindTeam (String name, String creator, LocalDateTime createDate) {
        FindTeam findTeam= new FindTeam();
        findTeam.name = name;
        findTeam.creator = creator;
        findTeam.createDate = createDate;

        return findTeam;
    }

}
