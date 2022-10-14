package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    ConnectionMaker connectionMaker = new DConnectionMaker();

    @Bean
    public UserDao userDao(){
        UserDao userDao = new UserDao();
        //userDao.setConnectionMaker(connectionMaker());
        userDao.setDataSource(dataSource());
        return userDao;
    }

    public AccountDao accountDao(){
        return new AccountDao(connectionMaker());
    }

    public MessageDao messageDao(){
        return new MessageDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker(){
        return new DConnectionMaker();
    }

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
        dataSource.setUsername("tobi");
        dataSource.setPassword("tobi");
        return dataSource;
    }
}