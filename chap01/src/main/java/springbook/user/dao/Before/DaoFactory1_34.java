package springbook.user.dao.Before;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.user.dao.*;

@Configuration
public class DaoFactory1_34 {

    ConnectionMaker connectionMaker = new DConnectionMaker();

    @Bean
    public UserDao userDao(){
        return new UserDao(connectionMaker());
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
}