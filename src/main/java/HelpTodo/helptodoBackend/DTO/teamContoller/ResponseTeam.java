package HelpTodo.helptodoBackend.DTO.teamContoller;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ResponseTeam {
    private String name;
    private String creatorId;
    private LocalDateTime createDate;


    public static ResponseTeam createResponseTeam(String name, String creator, LocalDateTime createDate) {
        ResponseTeam responseTeam = new ResponseTeam();
        responseTeam.name = name;
        responseTeam.creatorId = creator;
        responseTeam.createDate = createDate;

        return responseTeam;
    }

}
