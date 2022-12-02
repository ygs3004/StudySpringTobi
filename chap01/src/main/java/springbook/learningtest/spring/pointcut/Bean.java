package springbook.learningtest.spring.pointcut;

public class Bean {

    public void method() throws RuntimeException{

    }

    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println(Target.class.getMethod("minus", int.class, int.class));
    }
}
