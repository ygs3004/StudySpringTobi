package springbook.user.dao;

public class DaoFactory {

    public UserDao userDao(){
        //팩토리 메소드는 UserDao 타입의 오브젝트를 만들고 준비시킨다.
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);

        return userDao;
    }

}