package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class AnnotationTest {

    @Test
    public void simpleAtAutowired(){
        AbstractApplicationContext ac = new AnnotationConfigApplicationContext(BeanA.class, BeanB.class);
        BeanA beanA = ac.getBean(BeanA.class);
        assertThat(beanA.beanB, is(notNullValue()));
    }

    private static class BeanA{
        @Autowired
        BeanB beanB;
    }

    private static class BeanB{
    }
}
