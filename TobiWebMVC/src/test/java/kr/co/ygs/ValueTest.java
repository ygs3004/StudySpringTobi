package kr.co.ygs;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

public class ValueTest {

    @Value("#{systemProperties['os.name']}")
    String osName;

    @Test
    public void valueTest(){
        String osName = this.osName;
        System.out.println(osName);
    }
}
