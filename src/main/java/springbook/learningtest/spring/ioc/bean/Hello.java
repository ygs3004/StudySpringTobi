package springbook.learningtest.spring.ioc.bean;

import org.springframework.beans.factory.annotation.Value;

public class Hello {
    String name = "Everyone";
    Printer printer;

    public String sayHello(){
        return "Hello " + name;
    }

    public void print(){
        this.printer.print(sayHello());
    }

    @Value("Everyone ")
    public void setName(String name){
        this.name = name;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

}
