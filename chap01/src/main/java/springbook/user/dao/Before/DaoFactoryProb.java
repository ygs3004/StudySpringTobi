package springbook.user.dao.Before;

import springbook.user.dao.ConnectionMaker;
import springbook.user.dao.DConnectionMaker;
import springbook.user.dao.UserDao;

public class DaoFactoryProb {

    ConnectionMaker connectionMaker = new DConnectionMaker();

    public UserDao userDao(){
        //팩토리 메소드는 UserDao 타입의 오브젝트를 만들고 준비시킨다.
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
    /* 중복되는 코드, 매개변수(enw DConnectionMaker())로 인해 수정사항이 생길시 문제 발생
    public AccountDao accountDao(){
        return new AccountDao(new DConnectionMaker());
    }

    public MessageDao messageDao(){
        return new MessageDao(new DConnectionMaker());
    }
    */
}