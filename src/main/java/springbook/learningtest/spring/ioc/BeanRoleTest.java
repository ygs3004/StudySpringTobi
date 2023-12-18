package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.learningtest.spring.ioc.config.SimpleConfig;

public class BeanRoleTest {

    @Test
    public void testSimpleConfig(){
        ApplicationContext context = new GenericXmlApplicationContext(BeanRoleTest.class, "beanrole.xml");

        SimpleConfig sc = context.getBean(SimpleConfig.class);
        sc.hello.sayHello();
    }

    @Test
    public void printBeanDefinitionsTest(){
        GenericApplicationContext context = new GenericXmlApplicationContext(BeanRoleTest.class, "beanrole.xml");
        BeanDefinitionUtils.printBeanDefinitions(context);
    }

}
