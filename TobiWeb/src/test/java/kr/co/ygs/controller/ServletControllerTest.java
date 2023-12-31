package kr.co.ygs.controller;

import kr.co.ygs.config.AbstractDispatcherServletTest;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServletControllerTest extends AbstractDispatcherServletTest {

    @Test
    public void helloServletController() throws ServletException, IOException {
        setClasses(SimpleServletHandlerAdapter.class, HelloServlet.class);
        initRequest("/hello").addParameter("name", "Spring");

        assertThat(runService().getContentAsString(), is("Hello Spring"));
    }

    @Component("/hello")
    static class HelloServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String name = request.getParameter("name");
            response.getWriter().print("Hello " + name);
        }
    }
}
