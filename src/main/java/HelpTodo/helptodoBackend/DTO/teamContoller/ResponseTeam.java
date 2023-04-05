package HelpTodo.helptodoBackend.DTO.teamContoller;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTeam {
    private String name;
    private String creatorName;
    private LocalDateTime createDate;
    private boolean hasPassword;

    public static ResponseTeam createResponseTeam(String name, String creator, LocalDateTime createDate, boolean hasPassword) {
        ResponseTeam responseTeam = new ResponseTeam();
        responseTeam.name = name;
        responseTeam.creatorName = creator;
        responseTeam.createDate = createDate;
        responseTeam.hasPassword = hasPassword;
        return responseTeam;
    }

}
