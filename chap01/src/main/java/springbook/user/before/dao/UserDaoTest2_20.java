package springbook.user.before.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoTest2_20 {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserDao3_22 dao; // setUp 메소드에서 오브젝트를 만들어 테스트 메소드에서 사용
    private User user1;
    private User user2;
    private User user3;

    public static void main(String[] args) {
        JUnitCore.main("springbook.user.dao.UserDaoTest");
    }

    @Before // 픽스쳐(Fixture) 테스트를 수행하는데 필요한 정보나 오브젝트, 일반적으로 여러 테스트에서 반복적으로 사용되므로 @Before setUp()이용하면 좋다
    public void setUp(){
        // this.dao = context.getBean("userDao", UserDao.class); context에 bean으로 등록 되었기 때문에 @Autowired 로 자동 주입
        this.user1 = new User("ygs3004", "윤건수", "비밀!");
        this.user2 = new User("lol", "페이커", "1557");
        this.user3 = new User("Winter", "겨울", "추워");
        System.out.println(this.context); // 하나의 context로 관리, 리소스 속도 등의 이득
        System.out.println(this); // 매번 다름, TEST 메서드 마다 독립적으로 실행을 확인하기 위해 새로 만듬
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