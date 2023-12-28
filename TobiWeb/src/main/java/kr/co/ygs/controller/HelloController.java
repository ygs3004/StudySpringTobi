package kr.co.ygs.controller;

import kr.co.ygs.HelloSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HelloController implements Controller {

    @Autowired
    HelloSpring helloSpring;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        String message = this.helloSpring.sayHello(name);
        System.out.println(message);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", message);

        return new ModelAndView("/WEB-INF/view/hello.jsp", model);
    }

}
