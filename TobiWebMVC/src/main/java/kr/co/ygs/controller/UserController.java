package kr.co.ygs.controller;

import kr.co.ygs.dto.User;
import kr.co.ygs.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;

public class UserController extends GenericController<User, Integer, UserService>{

    @RequestMapping("/login")
    public String login(String userId, String userPwd){
        return "ok";
    }
}
