package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.context.support.StaticApplicationContext;

import static org.hamcrest.core.Is.is;
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

}
