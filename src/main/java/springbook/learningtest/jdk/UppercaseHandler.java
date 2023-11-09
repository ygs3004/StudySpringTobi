package springbook.learningtest.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    Object target;

    public UppercaseHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object ret = method.invoke(target, args); // 타깃으로 위임, 인터페이스 메소드 호출에 모두 적용
        if (ret instanceof String && method.getName().startsWith("say")){
            return ((String)ret).toUpperCase();
        }else{
            return ret;
        }
    }
}
