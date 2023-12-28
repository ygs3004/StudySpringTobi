package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import springbook.learningtest.spring.ioc.bean.*;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsSame.sameInstance;
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

    @Test
    public void hierarchyContextTest(){
        String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()))+ "/";
        ApplicationContext parent = new GenericXmlApplicationContext(basePath + "parentContext.xml");

        GenericApplicationContext child = new GenericApplicationContext(parent);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
        reader.loadBeanDefinitions(basePath + "childContext.xml");
        // reader 를 사용하여 설정을 읽은경우 refresh() 를 이용하여 초기화
        child.refresh();

        Printer printer = child.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));

        Hello hello = child.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));

        hello.print();
        assertThat(printer.toString(), is("Hello Child"));
    }

    @Test
    public void simpleBeanScanning(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext("springbook.learningtest.spring.ioc.bean");
        AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);

        assertThat(hello, is(notNullValue()));
    }

    @Test
    public void configurationTest(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
        AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
        assertThat(hello, is(notNullValue()));

        AnnotatedHelloConfig config = ctx.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);
        assertThat(config, is(notNullValue()));

        assertThat(config.annotatedHello(), is(sameInstance(hello)));
    }

    @Test
    public void singletonScope(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(
                SingletonBean.class, SingletonClientBean.class
        );

        // Set: 중복값 제거
        Set<SingletonBean> beans = new HashSet<>();
        beans.add(ac.getBean(SingletonBean.class));
        beans.add(ac.getBean(SingletonBean.class));

        assertThat(beans.size(), is(1));

        beans.add(ac.getBean(SingletonClientBean.class).bean1);
        beans.add(ac.getBean(SingletonClientBean.class).bean2);

        assertThat(beans.size(), is(1));

    }

    static class SingletonBean{}
    static class SingletonClientBean{
        @Autowired
        SingletonBean bean1;
        @Autowired
        SingletonBean bean2;
    }

    @Test
    public void prototypeScope(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(
                PrototypeBean.class, PrototypeClientBean.class
        );

        Set<PrototypeBean> beans = new HashSet<>();

        beans.add(ac.getBean(PrototypeBean.class));
        assertThat(beans.size(), is(1));
        beans.add(ac.getBean(PrototypeBean.class));
        assertThat(beans.size(), is(2));
        beans.add(ac.getBean(PrototypeClientBean.class).bean1);
        assertThat(beans.size(), is(3));
        beans.add(ac.getBean(PrototypeClientBean.class).bean2);
        assertThat(beans.size(), is(4));
    }

    @Scope("prototype")
    static class PrototypeBean{}

    static class PrototypeClientBean{
        @Autowired
        PrototypeBean bean1;
        @Autowired
        PrototypeBean bean2;
    }
}
