package kr.co.ygs.controller;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelloControllerTest {

    @Test
    public void helloControllerTest() throws Exception {
        ApplicationContext ac = new GenericXmlApplicationContext("kr/co/ygs/learningtest/spring-servlet-test.xml");
        HelloController helloController = ac.getBean(HelloController.class);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Spring");;
        MockHttpServletResponse response = new MockHttpServletResponse();

        ModelAndView modelAndView = helloController.handleRequest(request, response);
        assertThat(modelAndView.getViewName(), is("/WEB-INF/view/hello.jsp"));
        assertThat((String)modelAndView.getModel().get("message"), is("Hello Spring"));
    }
}
