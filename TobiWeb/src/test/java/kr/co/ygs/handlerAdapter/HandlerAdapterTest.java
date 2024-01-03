package kr.co.ygs.handlerAdapter;

import kr.co.ygs.config.AbstractDispatcherServletTest;
import kr.co.ygs.controller.SimpleHelloControllerImpl;
import kr.co.ygs.hanlderAdapter.SimpleHandlerAdapter;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

public class HandlerAdapterTest extends AbstractDispatcherServletTest {

    @Test
    public void SimpleHandlerAdapterTest() throws ServletException, IOException {
        setClasses(SimpleHandlerAdapter.class, SimpleHelloControllerImpl.class);
        initRequest("/hello").addParameter("name", "Spring").runService();

        assertViewName("/WEB-INF/view/hello.jsp");
        assertModel("message", "Hello Spring");
    }

}
