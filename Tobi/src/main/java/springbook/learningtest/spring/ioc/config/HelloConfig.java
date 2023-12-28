package springbook.learningtest.spring.ioc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.learningtest.spring.ioc.bean.Hello;
import springbook.learningtest.spring.ioc.bean.Printer;
import springbook.learningtest.spring.ioc.bean.StringPrinter;

@Configuration
public class HelloConfig {

    // @Bean
    // public Hello hello(){
    //     Hello hello = new Hello();
    //     hello.setName("Spring");
    //     hello.setPrinter(printer());
    //     return hello;
    // }

    @Bean
    public Hello hello2(){
        Hello hello = new Hello();
        hello.setName("Spring2");
        hello.setPrinter(printer());
        return hello;
    }

    @Bean
    public Printer printer(){
        // @Configuration 내부에서만 사용된 @Bean
        // 디폴트 싱글톤, 매번 동일한 인스턴스를 return
        return new StringPrinter();
    }

}
