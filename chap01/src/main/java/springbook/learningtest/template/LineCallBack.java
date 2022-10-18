package springbook.learningtest.template;

public interface LineCallBack<T> {

    T doSomethingWithLine(String line, T value);

}
