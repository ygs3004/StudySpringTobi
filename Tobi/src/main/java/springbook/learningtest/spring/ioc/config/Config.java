package springbook.learningtest.spring.ioc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.learningtest.spring.ioc.bean.Hello;
import springbook.learningtest.spring.ioc.bean.Printer;
import springbook.learningtest.spring.ioc.bean.StringPrinter;

@Configuration
public class Config {

    @Value("${database.username}")
    private String name;

    // @Bean
    // public Hello hello(){
    //     Hello hello = new Hello();
    //     hello.setName(this.name);
    //     return hello;
    // }
    // @Bean
    // public Hello hello(@Value("${database.username}") String name){
    //     Hello hello = new Hello();
    //     hello.setName(name);
    //     return hello;
    // }
    //
    // @Bean
    // public Hello hello(Printer printer){
    //     Hello hello = new Hello();
    //     hello.setPrinter(printer);
    //     return hello;
    // }

    @Bean
    public Printer printer(){
        return new StringPrinter();
    }
}
