package springbook.learningtest.spring.factorybean;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration // 설정파일 이름을 지정하지 않으면 클래스이름 + "-context.xml이 디폴트로 사용된다
public class FactoryBeanTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean(){
        Object message = context.getBean("message"); // 등록되어있는 factory 가아니라 getObject 메소드를 통해 얻어옴
        assertThat(message, is(Message.class)); //타입체크
        assertThat(((Message)message).getText(), is("Factory Bean"));
    }
    
    @Test
    public void getFactoryBean() throws Exception{
        Object factory = context.getBean("&message"); // 팩토리 자체를 리턴
        assertThat(factory, is(MessageFactoryBean.class));
    }

}
