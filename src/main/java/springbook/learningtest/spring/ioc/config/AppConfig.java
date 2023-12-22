package springbook.learningtest.spring.ioc.config;

import org.springframework.context.annotation.*;
import springbook.learningtest.spring.ioc.scanner.ServiceMaker;


@Configuration
// @ComponentScan("springbook.learningtest.spring.ioc.scanner")
// @ComponentScan(basePackages = "myproject", excludeFilters = @ComponentScan.Filter(Configuration.class))
// @ComponentScan(basePackages = "myproject", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AppConfig.class))
@ComponentScan(basePackageClasses = ServiceMaker.class)
public class AppConfig {

    @Configuration
    @Profile("spring-test")
    public static class SpringTestConfig{
    }

    @Configuration
    @Profile("dev")
    public static class DevConfig{
    }

    @Configuration
    @Profile("production")
    public static class ProductionConfig{
    }

}
