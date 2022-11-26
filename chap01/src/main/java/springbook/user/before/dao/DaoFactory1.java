package springbook.user.before.dao;

import springbook.user.dao.*;

public class DaoFactory1 {

    ConnectionMaker connectionMaker = new DConnectionMaker();

    public UserDao3_22 userDao(){
        return new UserDao3_22(connectionMaker());
    }

    public AccountDao accountDao(){
        return new AccountDao(connectionMaker());
    }

    public MessageDao messageDao(){
        return new MessageDao(connectionMaker());
    }

    public ConnectionMaker connectionMaker(){
        return new DConnectionMaker();
    }
}