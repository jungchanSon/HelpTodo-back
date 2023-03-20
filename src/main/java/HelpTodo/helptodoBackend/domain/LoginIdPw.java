package HelpTodo.helptodoBackend.domain;

import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@Builder
public class LoginIdPw {

    private String Id;
    private String Pw;

    public LoginIdPw(String Id, String Pw){
        this.Id = Id;
        this.Pw = Pw;
    }
}
