package springbook.context;

import org.springframework.context.ConfigurableApplicationContext;

public interface ApplicationContextInitializer<C extends ConfigurableApplicationContext> {
    void initialize(C applicationContext);
}
