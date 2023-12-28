package springbook.learningtest.jdk;

public class HelloUppercase implements Hello{

    Hello hello; // 타깃 오브젝트, 다른프록시 추가 가능성을 염두해두고 인터페이스로 접근한다

    public HelloUppercase(Hello hello) {
        this.hello = hello;
    }

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(String name) {
        return hello.sayThankYou(name).toUpperCase();
    }
}
