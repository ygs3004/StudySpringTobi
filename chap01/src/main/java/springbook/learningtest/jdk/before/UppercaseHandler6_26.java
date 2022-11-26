package springbook.learningtest.jdk.before;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler6_26 implements InvocationHandler {

    Object target;

    public UppercaseHandler6_26(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object ret = method.invoke(target, args); // 타깃으로 위임, 인터페이스 메소드 호출에 모두 적용
        if (ret instanceof String){
            return ((String)ret).toUpperCase();
        }else{
            return ret;
        }
    }
}
