package springbook.learningtest.spring.ioc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class SystemBean {

    @Autowired
    ApplicationContext context;

    public void specialJobWithContext(){
        // this.context.getBean();
    }

}
