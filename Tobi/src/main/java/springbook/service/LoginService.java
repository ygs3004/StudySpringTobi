package springbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import springbook.dto.Login;
import springbook.dto.LoginUser;

import java.util.Date;

public class LoginService {

    // @Autowired
    // Provider<LoginUser> loginUserProvider;

    @Autowired
    LoginUser loginUser;


    public void login(Login login){
        loginUser.setLoginId("ygs");
        loginUser.setName("윤건수");
        loginUser.setLongTime(new Date());
    }

}
