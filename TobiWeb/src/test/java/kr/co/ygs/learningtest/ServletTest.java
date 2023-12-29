package kr.co.ygs.learningtest;

import kr.co.ygs.HelloSpring;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServletTest {

    @Test
    public void servletTest() throws ServletException, IOException {

        ConfigurableDispatcherServlet servlet = new ConfigurableDispatcherServlet();
        servlet.setRelativeLocations(getClass(), "spring-servlet.xml");
        servlet.setClasses(HelloSpring.class);

        servlet.init(new MockServletConfig("spring"));

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Spring");
        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.service(request, response);

        ModelAndView modelAndView = servlet.getModelAndView();
        assertThat(modelAndView.getViewName(), is("/WEB-INF/view/hello.jsp"));
        assertThat((String)modelAndView.getModel().get("message"), is("Hello Spring"));
    }

}
