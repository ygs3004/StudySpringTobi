package springbook.user.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

// ConnectionMaker 관계설정의 책임이 있는 UserDao의 클라이언트
public class UserDaoTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /* DaoFactory에서 책임
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao dao = new UserDao(connectionMaker);
        */
        // UserDao dao = new DaoFactoryProb().userDao();
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("Buff");
        user.setName("윤건수");
        user.setPassword("124");

        dao.add(user);

        System.out.println(user.getId()+" 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user.getPassword());

        System.out.println(user2.getId() + " 조회 성공");

    }
}