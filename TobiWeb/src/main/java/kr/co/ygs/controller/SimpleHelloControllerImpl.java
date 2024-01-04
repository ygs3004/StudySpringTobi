package kr.co.ygs.controller;

import kr.co.ygs.annotation.RequiredParams;
import kr.co.ygs.annotation.ViewName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class SimpleHelloControllerImpl implements SimpleController{

    @RequestMapping("/hello")
    @ViewName("/WEB-INF/view/hello.jsp")
    @RequiredParams({"name"})
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Hello " + params.get("name"));
    }

}
