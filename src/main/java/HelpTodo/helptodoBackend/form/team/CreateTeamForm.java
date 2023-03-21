package HelpTodo.helptodoBackend.Form.team;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateTeamForm {
    String memberId;
    String teamName;
    String teamPassword;
}

