package springbook.user.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.context.AppContext;
import springbook.context.TestAppContext;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppContext.class, AppContext.class})
@DirtiesContext // 테스트 케이스마다 UserDaoTest객체를 새로 생성
public class UserDaoTest {

    @Autowired
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        JUnitCore.main(
                "springbook.user.dao.UserDaoTest",
//                "springbook.user.service.UserServiceTest",
                "springbook.user.dao.UserTest",
                "springbook.user.sqlservice.updatable.ConcurrentHashMapSqlRegistryTest",
                "springbook.learningtest.spring.embeddeddb.EmbeddedDbTest",
                "springbook.user.sqlservice.updatable.EmbeddedDbSqlRegistryTest"
        );
    }

    @Before
    public void setUp(){
        this.user1 = new User("ygs3004", "윤건수", "비밀!", Level.BASIC, 1, 0, "ygs3004@naver.com");
        this.user2 = new User("winter", "겨울", "추워", Level.SILVER, 55, 10, "abc2@nave2r.com");
        this.user3 = new User("faker", "페이커", "1557", Level.GOLD, 100, 44, "abc3@nave2r.com");
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
        checkSameUser(userget1, user1);

        User userget2 = dao.get(user2.getId());
        checkSameUser(userget2, user2);

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

    //null 값 등으로 인한 get() 메소드의 예외상황에 대한 테스트 query가 order by id
    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException{

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknow_id"); // 예외 발생
    }

    @Test
    public void getAll() throws SQLException {
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0));

        dao.add(user1); // Id:ygs3004
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2); // Id:winter
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user2, users2.get(0));
        checkSameUser(user1, users2.get(1));

        dao.add(user3); // Id:faker
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user3, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user1, users3.get(2));
    }

    private void checkSameUser(User user1, User user2){
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        // 새로운 필드(level, login, recommend)를 검증
        assertThat(user1.getLevel(), is(user2.getLevel()));
        assertThat(user1.getLogin(), is(user2.getLogin()));
        assertThat(user1.getRecommend(), is(user2.getRecommend()));
    }

    //@Test // (expected = DataAccessException.class)
    @Test(expected = DuplicateKeyException.class)
    public void duplicateKey(){

        dao.deleteAll();

        dao.add(user1);
        dao.add(user1); // 같은 사용자 두번 등록으로 DataAccessException 발생

    }

    @Test
    public void sqlExceptionTranslate(){
        dao.deleteAll();

        try{
            dao.add(user1);
            dao.add(user1);

        }catch (DuplicateKeyException ex){
            SQLException sqlEx = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set = // 코드를 이용한 SQLException
                    new SQLErrorCodeSQLExceptionTranslator(this.dataSource); // data 종류( Orcale, mysql, ....)

            assertThat(set.translate(null, null, sqlEx), // null => 에러 메시지를 만들때 사용하는 정보
                    is(DuplicateKeyException.class));
        }

    }

    @Test
    public void update() {
        dao.deleteAll();

        dao.add(user1); // 수정할 사용자
        dao.add(user2); // 수정하지 않을 사용자

        user1.setName("윤건수");
        user1.setPassword("비밀!");
        user1.setLevel(Level.BASIC);
        user1.setLogin(1);
        user1.setRecommend(0);

        dao.update(user1);

        User user1update = dao.get(user1.getId());
        checkSameUser(user1, user1update);

        User user2same = dao.get(user2.getId());
        checkSameUser(user2, user2same);
    }

}