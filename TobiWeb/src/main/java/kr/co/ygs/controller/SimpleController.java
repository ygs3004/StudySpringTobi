package kr.co.ygs.controller;

import java.util.Map;

public interface SimpleController {
    void control(Map<String, String> params, Map<String, Object> model);
}
