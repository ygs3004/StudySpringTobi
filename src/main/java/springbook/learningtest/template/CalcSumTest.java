package springbook.learningtest.template;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest {

    Calculator calculator;
    String numFilepath;

    @Before public void setUp(){
        this.calculator = new Calculator();
        this.numFilepath = getClass().getResource("/numbers.txt").getPath();
        System.out.println("Test를 위해 생성된 Fixture 객체 calculator : " + calculator);
        System.out.println("Test를 위해 생성된 Fixture 경로 numFilepath : " + numFilepath);
    }

    @Test
    public void sumOfNumbers() throws IOException{
        assertThat(calculator.calcSum(this.numFilepath), is(10));
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(this.numFilepath), is(24));
    }

    @Test
    public void concatenateStrings() throws IOException {
        assertThat(calculator.concatenate(this.numFilepath), is("1234"));
    }
}