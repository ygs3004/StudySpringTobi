package kr.co.ygs.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @Value("#{systemProperties['os.name']}")
    String osName;

    @RequestMapping("/hello")
    public String hello(ModelMap map){
        String osName = this.osName;
        map.put("osName", osName);
        System.out.println(osName);
        return "/WEB-INF/view/hello.jsp";
    }

}
