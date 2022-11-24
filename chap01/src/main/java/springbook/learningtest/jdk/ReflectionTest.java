package springbook.learningtest.jdk;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReflectionTest {

    @Test
    public void invokeMethod() throws Exception {
        String name = "Spring";

        // lenght() 메소드 테스트
        assertThat(name.length(), is(6));

        // chatAt()
        assertThat(name.charAt(0), is('S'));

        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertThat((Character)charAtMethod.invoke(name, 0), is('S'));
    }

    @Test
    public void simpleProxy(){
        Hello hello = new HelloTarget(); // 타깃은 인터페이스를 통해 접근
        assertThat(hello.sayHello("GunSoo"), is("Hello GunSoo") );
        assertThat(hello.sayHi("GunSoo"), is("Hi GunSoo") );
        assertThat(hello.sayThankYou("GunSoo"), is("Thank You GunSoo") );

        Hello proxiedHello = new HelloUppercase(hello);
        assertThat(proxiedHello.sayHello("GunSoo"), is("HELLO GUNSOO") );
        assertThat(proxiedHello.sayHi("GunSoo"), is("HI GUNSOO") );
        assertThat(proxiedHello.sayThankYou("GunSoo"), is("THANK YOU GUNSOO") );
    }

}
