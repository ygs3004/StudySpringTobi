package springbook.learningtest.spring.ioc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.learningtest.spring.ioc.bean.Hello;

@Configuration
public class SimpleConfig {

    @Autowired
    public Hello hello;

    @Bean
    Hello hello(){
        return new Hello();
    }

}
