/*
package springbook.user.dao.Before;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.UserDao;
import springbook.user.dao.UserService;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest5_30 {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    List<User> users;

    @Before
    public void setUp(){
        users = Arrays.asList(
                new User("faker", "고전파", "p1", Level.BASIC, 49, 0),
                new User("keria", "케리아", "p2", Level.BASIC, 50, 0),
                new User("oner", "오너", "p3", Level.SILVER, 60, 29),
                new User("gumayusi", "구마", "p4", Level.SILVER, 60, 30),
                new User("zeus", "제우스", "p5", Level.GOLD, 100, 100)
        );
    }

    @Test
    public void upgradeLevels() throws Exception{

        userDao.deleteAll();

        for(User user:users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);

    }

    public void checkLevel(User user, Level expectedLevel){
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }

    @Test
    public void add(){

        userDao.deleteAll();

        User userWithLevel = users.get(4); // Level을 골드로 등록한 경우 GOLD
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null); // Level이 비어있는 등록

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        //DB값
        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));


    }

}
*/
