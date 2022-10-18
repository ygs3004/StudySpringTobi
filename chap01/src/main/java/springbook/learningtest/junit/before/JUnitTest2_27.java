package springbook.learningtest.junit.before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JUnitTest2_27 {

    // static JUnitTest testObject; test1 과 test3의 JUnitTest 겍체가 다를수 있음

    static Set<JUnitTest2_27> testObjects = new HashSet<>();

    @Test public void test1(){
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }

    @Test public void test2(){
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }

    @Test public void test3(){
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }

}