package kr.co.ygs.controller;

import kr.co.ygs.annotation.RequiredParams;
import kr.co.ygs.annotation.ViewName;

import java.util.Map;

public class SimpleHelloControllerImpl implements SimpleController{

    @ViewName("/WEB-INF/view/hello.jsp")
    @RequiredParams({"name"})
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Hello " + params.get("name"));
    }
}
