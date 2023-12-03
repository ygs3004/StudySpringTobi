package springbook.learningtest.spring.ioc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.learningtest.spring.ioc.bean.Hello;
import springbook.learningtest.spring.ioc.bean.Printer;
import springbook.learningtest.spring.ioc.bean.StringPrinter;

@Configuration
public class Config {

    // @Bean
    // @Configuration 에서만 사용 가능한 방법
    // 다른 곳에서 사용시 singleton 주입이 안됨
    // public Hello hell(){
    //     Hello hello = new Hello();
    //     hello.setPrinter(printer());
    //     return hello;
    // }

    @Bean
    public Hello hell(Printer printer){
        Hello hello = new Hello();
        hello.setPrinter(printer);
        return hello;
    }

    @Bean
    public Printer printer(){
        return new StringPrinter();
    }
}
