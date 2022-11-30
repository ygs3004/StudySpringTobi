package springbook.learningtest.jdk.before;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.learningtest.jdk.UppercaseHandler;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springbook/learningtest/jdk/proxy/FactoryBeanTest-context.xml") // 설정파일 이름을 지정하지 않으면 클래스이름 + "-context.xml이 디폴트로 사용된다
public class DynamicProxyTest6_42 {

    @Test
    public void simpleProxy(){ //JDK 다이내믹 프록시 생성의 경우
        Hello proxiedHello = (Hello)Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
    }

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget()); // 타깃설정
        pfBean.addAdvice(new UppercaseAdvice()); // 부가기능을 담은 어드바이스를 추가, 여러개도 추가 가능

        Hello proxiedHello = (Hello)pfBean.getObject(); // FactoryBean이므로 getObject로 생성된 프록시를 가져온다

        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
    }

    static class UppercaseAdvice implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String)invocation.proceed(); // 리플렉션의 Method와 달리 메소드 실행시 타깃 오브젝트를 전달필요X, MethodInvocation에 타깃정보, 메소드정보를 포함
            return ret.toUpperCase(); // 부가기능 적용
        }
    }

    static interface Hello{
        String sayHello(String name);
        String sayHi(String name);
        String sayThankYou(String name);
    }

    static class HelloTarget implements Hello {

        @Override
        public String sayHello(String name) {
            return "Hello "+name;
        }

        @Override
        public String sayHi(String name) {
            return "Hi "+name;
        }

        @Override
        public String sayThankYou(String name) {
            return "Thank You "+name;
        }
    }

}
