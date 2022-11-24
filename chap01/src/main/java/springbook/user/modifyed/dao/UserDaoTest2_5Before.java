package springbook.user.modifyed.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

// ConnectionMaker 관계설정의 책임이 있는 UserDao의 클라이언트
public class UserDaoTest2_5Before {


        //public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /* DaoFactory에서 책임
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao dao = new UserDao(connectionMaker);
        */
        // UserDao dao = new DaoFactoryProb().userDao();

        // 자바 클래스 사용
        // ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);


        @Test //junit사용
        public void addAndGet() throws SQLException, ClassNotFoundException{
        //ApplicationCaontext.xml 사용
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao3_22 dao = context.getBean("userDao", UserDao3_22.class);

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

        if(!user.getName().equals(user2.getName())){
            System.out.println("테스트 실패(name)");
        }
        else if(!user.getPassword().equals(user2.getPassword())){
            System.out.println("테스트 실패(password");
        }
        else{
            System.out.println("조회 테스트 성공");
        }

    }
}