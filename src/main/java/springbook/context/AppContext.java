package springbook.context;

import com.mysql.jdbc.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springbook.user.dao.UserDao;
import springbook.user.service.DummyMailSender;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceImpl;
import springbook.user.service.UserServiceTest;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "springbook.user")
@Import({SqlServiceContext.class, TestAppContext.class, ProductionAppContext.class})
public class AppContext {

    @Autowired
    UserDao userDao;

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/tobi?characterEncoding=UTF-8");
        dataSource.setUsername("tobitest");
        dataSource.setPassword("tobitest");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    @Bean
    public UserService userService(){
        UserServiceImpl service = new UserServiceImpl();
        service.setUserDao(this.userDao);
        service.setMailSender(mailSender());
        return service;
    }

    @Bean
    public UserService testUserService(){
        UserServiceTest.TestUserService testService = new UserServiceTest.TestUserService();
        testService.setUserDao(this.userDao);
        testService.setMailSender(mailSender());
        return testService;
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

}
