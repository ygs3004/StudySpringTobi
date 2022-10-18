package springbook.learningtest.template.before;

import org.junit.Test;
import springbook.learningtest.template.Calculator;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest3_36 {

    @Test
    public void sumOfNumbers() throws IOException{
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(getClass().getResource("/numbers.txt").getPath());
        assertThat(sum, is(10));
    }

}