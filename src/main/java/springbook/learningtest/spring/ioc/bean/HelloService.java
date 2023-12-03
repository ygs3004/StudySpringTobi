package springbook.learningtest.spring.ioc.bean;

import org.springframework.context.annotation.Bean;

public class HelloService {

    private Printer printer;

    public void setPrinter(Printer printer){
        this.printer = printer;
    }

    @Bean
    public Hello hello(){
        Hello hello = new Hello();
        hello.setName("Spring");
        hello.setPrinter(this.printer);
        return hello;
    }

    @Bean
    public Hello hello2(){
        Hello hello = new Hello();
        hello.setName("Spring2");
        hello.setPrinter(this.printer);
        return hello;
    }

    @Bean
    public Printer printer(){
        // @Configuration 내부가 아닌경우 매번 새로운 인스턴스 반환
        return new StringPrinter();
    }
    
}
