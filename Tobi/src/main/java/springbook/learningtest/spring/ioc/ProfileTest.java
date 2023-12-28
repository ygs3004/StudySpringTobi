package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.sql.DataSource;

public class ProfileTest {

    @Test
    public void dbProfileTest(){
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext();
        ac.getEnvironment().setActiveProfiles("dev");
        ac.load(getClass(), "/springbook/context/dbProfileContext.xml");
        DataSource dataSource = ac.getBean("dataSource", DataSource.class);
        System.out.println(dataSource);
        ac.refresh();
    }

}
