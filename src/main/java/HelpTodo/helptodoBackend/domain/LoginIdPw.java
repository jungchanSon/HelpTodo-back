package HelpTodo.helptodoBackend.domain;

import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class LoginIdPw {

    private String Id;
    private String Pw;

    protected LoginIdPw(){

    }

    public LoginIdPw(String Id, String Pw){
        this.Id = Id;
        this.Pw = Pw;
    }
}
