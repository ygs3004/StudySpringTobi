package springbook.user.modifyed.dao;

import springbook.user.dao.ConnectionMaker;
import springbook.user.dao.DConnectionMaker;

public class DaoFactoryProb {

    ConnectionMaker connectionMaker = new DConnectionMaker();

    public UserDao3_22 userDao(){
        //팩토리 메소드는 UserDao 타입의 오브젝트를 만들고 준비시킨다.
        UserDao3_22 userDao = new UserDao3_22(connectionMaker);
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