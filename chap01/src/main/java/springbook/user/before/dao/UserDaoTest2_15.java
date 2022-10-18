package springbook.user.before.dao;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest2_15 {

    public static void main(String[] args) {
        JUnitCore.main("springbook.user.dao.UserDaoTest");
    }

    @Test
    public void addAndGet() throws SQLException{

        //ApplicationCaontext.xml 사용
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao3_22 dao = context.getBean("userDao", UserDao3_22.class);
        User user1 = new User("ygs3004", "윤건수", "비밀!");
        User user2 = new User("lol", "페이커", "1557");

        // 테스트의 효율성을 위한 deleteAll(). getCount()  테스트 추가
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));
        /*
        User user = new User();
        user.setId("ygs3004");
        user.setName("내아이디");
        user.setPassword("내비번");
        */

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));

    }

    @Test
    public void count() throws SQLException{

        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao3_22 dao = context.getBean("userDao", UserDao3_22.class);
        User user1 = new User("ygs3004","윤건수","myId");
        User user2 = new User("shj", "서현진", "wow");
        User user3 = new User("bts", "슈퍼스타", "dynamite");

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));

    }

    //null 값 등으로 인한 get() 메소드의 예외상황에 대한 테스트
    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException{
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao3_22 dao = context.getBean("userDao", UserDao3_22.class);
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknow_id"); // 예외 발생
    }

}