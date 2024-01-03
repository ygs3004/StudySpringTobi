package kr.co.ygs.hanlderAdapter;

import kr.co.ygs.annotation.RequiredParams;
import kr.co.ygs.annotation.ViewName;
import kr.co.ygs.controller.SimpleController;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SimpleHandlerAdapter implements HandlerAdapter {

    // 핸들러가 지원하는 타입 체크
    public boolean supports(Object handler) {
        return (handler instanceof SimpleController);
    }

    // 캐싱을 적용하지 않는 케이스
    public long getLastModified(HttpServletRequest httpServletRequest, Object o) {
        return -1;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method method = ReflectionUtils.findMethod(handler.getClass(), "control", Map.class, Map.class);

        // Spring AnnotationUtils 이용하여 어노테이션 정보 가져오기
        ViewName viewName = AnnotationUtils.getAnnotation(method, ViewName.class);
        RequiredParams requiredParams = AnnotationUtils.getAnnotation(method, RequiredParams.class);

        Map<String, String> params = new HashMap<String, String>();
        for(String param : requiredParams.value()){
            String value = request.getParameter(param);
            if(value == null) throw new IllegalStateException();
            params.put(param, value);
        }

        Map<String, Object> model = new HashMap<String, Object>();

        // Dispatcher Servlet 은 컨트롤러 타입을 모르기 때문에 Object 타입으로 넘겨준다.
        ((SimpleController)handler).control(params, model);

        return new ModelAndView(viewName.value(), model);
    }

}
