package springbook.learningtest.jdk.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.learningtest.jdk.UppercaseHandler;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springbook/learningtest/jdk/proxy/FactoryBeanTest-context.xml") // 설정파일 이름을 지정하지 않으면 클래스이름 + "-context.xml이 디폴트로 사용된다
public class DynamicProxyTest {

    @Test
    public void simpleProxy(){ //JDK 다이내믹 프록시 생성의 경우
        Hello proxiedHello = (Hello)Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
    }

    @Test
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget()); // 타깃설정

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*"); // 이름 비교조건 설정, sayH로 시작하는 메소드 선택
        
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice())); // 포인트컷과 어드바이스를 Advisor로 한번에 추가

        Hello proxiedHello = (Hello)pfBean.getObject(); // FactoryBean이므로 getObject로 생성된 프록시를 가져온다

        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("Toby"), is("Thank You Toby")); // 메소드 이름이 pointcut과 맞지 않으므로 대문자 변환 x
    }

    @Test
    public void classNamePointcutAdvisor(){
        //포인트컷 준비
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut(){

            @Override
            public ClassFilter getClassFilter(){
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return clazz.getSimpleName().startsWith("HelloT"); // 클래스 이름이 helloT로 시작하는것만 성정
                    }
                };
            }
        };
        
        classMethodPointcut.setMappedName("sayH*"); // sayH로 시작하는 메소드 선정
        
        //테스트
        checkAdviced(new HelloTarget(), classMethodPointcut, true); //적용 클래스

        class HelloWorld extends HelloTarget{};
        checkAdviced(new HelloWorld(), classMethodPointcut, false); // 미적용 클래스

        class HelloToby extends HelloTarget{};
        checkAdviced(new HelloToby(), classMethodPointcut, true); // 적용 클래스

    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced){  // adviced => 적용 대상인가?

        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        Hello proxiedHello = (Hello) pfBean.getObject();

        if(adviced){
            assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
            assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
            assertThat(proxiedHello.sayThankYou("Toby"), is("Thank You Toby"));
        }else{
            assertThat(proxiedHello.sayHello("Toby"), is("Hello Toby"));
            assertThat(proxiedHello.sayHi("Toby"), is("Hi Toby"));
            assertThat(proxiedHello.sayThankYou("Toby"), is("Thank You Toby"));
        }

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
