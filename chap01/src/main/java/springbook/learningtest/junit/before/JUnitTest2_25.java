package springbook.learningtest.junit.before;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


public class JUnitTest2_25 {

    static JUnitTest2_25 testObject;

    @Test public void test1(){
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

    @Test public void test2(){
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

    @Test public void test3(){
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

}