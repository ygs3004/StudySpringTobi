package kr.co.ygs.learningtest;

import kr.co.ygs.HelloSpring;
import kr.co.ygs.config.AbstractDispatcherServletTest;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

// Test용 DispatcherServlet 을 이용
public class SimpleHelloControllerTest extends AbstractDispatcherServletTest {

    @Test
    public void helloController() throws ServletException, IOException {
        setRelativeLocations("spring-servlet-test.xml")
                .setClasses(HelloSpring.class)
                .initRequest("/hello", "GET")
                .addParameter("name", "Spring")
                .runService() // DispatcherServlet 실행
                .assertModel("message", "Hello Spring")
                .assertViewName("/WEB-INF/view/hello.jsp");
    }
}
