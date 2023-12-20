package springbook.learningtest.spring.ioc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import springbook.learningtest.spring.ioc.scanner.ServiceMaker;


@Configuration
// @ComponentScan("springbook.learningtest.spring.ioc.scanner")
// @ComponentScan(basePackages = "myproject", excludeFilters = @ComponentScan.Filter(Configuration.class))
// @ComponentScan(basePackages = "myproject", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AppConfig.class))
@ComponentScan(basePackageClasses = ServiceMaker.class)
public class AppConfig {
}
