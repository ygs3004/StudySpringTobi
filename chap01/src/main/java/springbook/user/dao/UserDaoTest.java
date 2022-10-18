package springbook.user.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
@DirtiesContext
public class UserDaoTest {

    @Autowired
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;
    private JdbcContext jdbcContext;

    public static void main(String[] args) {
        JUnitCore.main("springbook.user.dao.UserDaoTest");
    }

    @Before
    public void setUp(){
        this.user1 = new User("ygs3004", "윤건수", "비밀!");
        this.user2 = new User("lol", "페이커", "1557");
        this.user3 = new User("Winter", "겨울", "추워");
        /*
        dao = new UserDao();
        DataSource dataSource = new SingleConnectionDataSource("jdbc:oracle:thin:@localhost:1521:XE", "tobiTest", "tobi", true);
        dao.setDataSource(dataSource);
        */
    }

    @Test
    public void addAndGet() throws SQLException{

        // 테스트의 효율성을 위한 deleteAll(). getCount()  테스트 추가
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));

    }

    @Test
    public void count() throws SQLException{

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

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknow_id"); // 예외 발생
    }

}