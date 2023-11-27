package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import springbook.learningtest.spring.ioc.bean.Hello;
import springbook.learningtest.spring.ioc.bean.StringPrinter;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class IocTest {

    @Test
    public void singletonBeanRegisterTest(){
        // ioc 컨테이너 생성과 동시에 컨테이너로 동작
        StaticApplicationContext ac = new StaticApplicationContext();
        // 싱글톤 bean 으로 컨테이너 등록
        ac.registerSingleton("hello1", Hello.class);

        Hello hello1 = ac.getBean("hello1", Hello.class);
        assertThat(hello1, is(notNullValue()));
    }

    @Test
    public void beanDefinitionRegisterTest(){
        StaticApplicationContext ac = new StaticApplicationContext();
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        ac.registerBeanDefinition("hello2", helloDef);
    }

    @Test
    public void beanGenerateTest(){
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerSingleton("hello1", Hello.class);

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        ac.registerBeanDefinition("hello2", helloDef);

        Hello hello2 = ac.getBean("hello2", Hello.class);
        assertThat(hello2.sayHello(), is("Hello Spring"));

        Hello hello1 = ac.getBean("hello1", Hello.class);
        assertThat(hello1, is(not(hello2)));

        assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
    }

    @Test
    public void registerBeanWithDependency(){
        StaticApplicationContext ac = new StaticApplicationContext();

        ac.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));

        ac.registerBeanDefinition("hello", helloDef);

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();
        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    public void genericApplicationContext(){
        // XML 과 같은 외부 빈설정 메타정보를 읽는 context
        // GenericApplicationContext ac = new GenericApplicationContext();
        // XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
        // reader.loadBeanDefinitions("springbook/learningtest/spring/ioc/genericApplicationContext.xml");

        //  properties 파일을 이용할 경우
        // PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(ac);
        // reader.loadBeanDefinitions("springbook/learningtest/spring/ioc/genericApplicationContext.properties");

        // ac.refresh();

        // 메타정보 등록이후 컨테이너 초기화
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("springbook/learningtest/spring/ioc/genericApplicationContext.xml");

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }

}
