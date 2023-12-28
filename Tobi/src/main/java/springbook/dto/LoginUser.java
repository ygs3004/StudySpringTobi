package springbook.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.Date;

@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
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
