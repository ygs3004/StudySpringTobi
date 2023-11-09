package springbook.user.dao;

import org.junit.Before;
import org.junit.Test;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    User user;

    @Before
    public void setUp(){
        user = new User(); // 스프링에서 관리하지 않음
    }

    @Test
    public void upgradeLevel(){
        Level[] levels = Level.values();

        for(Level level : levels){
            if (level.nextLevel() == null) continue;
            user.setLevel(level);;
            user.upgradeLevel();
            assertThat(user.getLevel(), is(level.nextLevel()));
        }

    }

    @Test(expected = IllegalStateException.class)
    public void connotUpgradeLevel(){
        Level[] levels = Level.values();

        for(Level level : levels){
            if(level.nextLevel() != null) continue;
            user.setLevel(level);
            user.upgradeLevel();
        }

    }


}
