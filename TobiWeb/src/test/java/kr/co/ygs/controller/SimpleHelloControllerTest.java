package kr.co.ygs.controller;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleHelloControllerTest {

    @Test
    public void SimpleHelloControllerTest(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "Spring");
        Map<String, Object> model = new HashMap<String, Object>();

        new SimpleHelloController().control(params, model);

        assertThat((String)model.get("message"), is("Hello Spring"));
    }

}
