package kr.co.ygs.controller;

import kr.co.ygs.model.Info;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HelloMarshallerController implements Controller {

    // 같은 타입의 View 가 여러개 존재할 가능성이 있을때, 빈 이름으로 매핑하기 위해 Resource 사용
    @Resource
    MarshallingView helloMarshallingView;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("info", new Info("Hello " + request.getParameter("name")));

        return new ModelAndView(helloMarshallingView, model);
    }

}
