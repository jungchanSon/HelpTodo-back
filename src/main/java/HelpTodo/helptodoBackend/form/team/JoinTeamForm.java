package HelpTodo.helptodoBackend.Form.team;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinTeamForm {
    String userId;
    String teamName;
    String teamPassword;
}
