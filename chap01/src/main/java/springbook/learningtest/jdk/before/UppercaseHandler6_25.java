package springbook.learningtest.jdk.before;

import springbook.learningtest.jdk.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler6_25 implements InvocationHandler {

    Hello target;

    public UppercaseHandler6_25(Hello target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String ret = (String) method.invoke(target, args); // 타깃으로 위임, 인터페이스 메소드 호출에 모두 적용
        return ret.toUpperCase(); // 부가기능 제공
    }
}
