package kr.co.ygs.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSimpleController implements Controller {

    private String[] requiredParams;
    private String viewName;

    public void setRequiredParams(String[] requiredParams){
        this.requiredParams = requiredParams;
    }

    public void setViewName(String viewName){
        this.viewName = viewName;
    }

    final public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{

        if(viewName == null) throw new IllegalStateException();

        Map<String, String> params = new HashMap<String, String>();
        for(String param: requiredParams){
            String value = request.getParameter(param);
            if(value == null) throw new IllegalStateException();
            params.put(param, value);
        }

        Map<String, Object> model = new HashMap<String, Object>();

        this.control(params, model);

        return new ModelAndView(this.viewName, model);
    }

    public abstract void control(Map<String, String> params, Map<String, Object> model) throws Exception;

}
