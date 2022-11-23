package HelpTodo.helptodoBackend.form.team;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateTeamForm {
    private String memberId;
    private String teamName;
    private String teamPassword;
}

