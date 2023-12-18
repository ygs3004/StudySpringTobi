package springbook.learningtest.spring.ioc.bean;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class Hello {
    String name = "Everyone";
    Printer printer;

    public String sayHello(){
        return "Hello " + name;
    }

    public void print(){
        this.printer.print(sayHello());
    }

    @Value("Everyone")
    public void setName(String name){
        this.name = name;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    @PostConstruct
    public void init(){
        System.out.println("Init");
    }
}
