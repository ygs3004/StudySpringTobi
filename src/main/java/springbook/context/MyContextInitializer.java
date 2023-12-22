package springbook.context;

import org.junit.Test;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.HashMap;
import java.util.Map;

// 컨텍스트 초기화시 프로퍼티 등록 클래스
public class MyContextInitializer implements ApplicationContextInitializer<AnnotationConfigWebApplicationContext>{

    @Override
    public void initialize(AnnotationConfigWebApplicationContext ac) {
        ConfigurableEnvironment ce = ac.getEnvironment();

        Map<String, Object> m = new HashMap<>();
        m.put("db.username", "spring");

        ce.getPropertySources().addFirst(new MapPropertySource("myPs", m));
    }

}
