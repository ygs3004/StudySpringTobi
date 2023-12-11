package springbook.dto;

import org.springframework.context.annotation.Scope;

import java.util.Date;

@Scope("session")
public class LoginUser {

    String longinId;
    String name;
    Date loginTime;

    public void setLongTime(Date date) {
    }

    public void setLoginId(String longinId) {
    }

    public void setName(String name) {
    }
}
