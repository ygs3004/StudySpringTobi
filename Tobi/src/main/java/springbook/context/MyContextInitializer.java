package springbook.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

// Web 컨텍스트 초기화시 프로퍼티 등록 클래스
// @Configuration
// public class MyContextInitializer implements ApplicationContextInitializer<AnnotationConfigWebApplicationContext>{
//
//     @Override
//     public void initialize(AnnotationConfigWebApplicationContext ac) {
//         ConfigurableEnvironment ce = ac.getEnvironment();
//
//         Map<String, Object> m = new HashMap<>();
//         m.put("db.username", "spring");
//
//         ce.getPropertySources().addFirst(new MapPropertySource("myPs", m));
//     }
//
// }
