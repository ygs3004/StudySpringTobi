package springbook.service;

import jakarta.inject.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import springbook.dto.LoginUser;

import java.util.Date;

public class LoginService {

    @Autowired
    Provider<LoginUser> loginUserProvider;

    public void login(Login login){
        LoginUser loginUser = loginUserProvider.get();
        loginUser.setLoginId("ygs");
        loginUser.setName("윤건수");
        loginUser.setLongTime(new Date());
    }

}
