package kr.co.ygs.controller;

import java.util.Map;

public class AbstractSimpleHelloControllerImpl extends AbstractSimpleController {

    public AbstractSimpleHelloControllerImpl(){
        this.setRequiredParams(new String[] {"name"});
        this.setViewName("/WEB-INF/view/hello.jsp");
    }

    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Hello " + params.get("name"));
    }
}
