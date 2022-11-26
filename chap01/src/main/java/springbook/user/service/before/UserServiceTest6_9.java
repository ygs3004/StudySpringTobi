package springbook.user.service.before;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static springbook.user.service.UserServiceImpl.MIN_LOGOUT_FOR_SILVER;
import static springbook.user.service.UserServiceImpl.MIN_RECOMMED_FOR_GOLD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
@DirtiesContext
public class UserServiceTest6_9 {

    @Autowired
    UserService userService;
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserDao userDao;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    MailSender mailSender;

    List<User> users;

    @Before
    public void setUp(){
        users = Arrays.asList(
                new User("afaker", "고전파", "p1", Level.BASIC, MIN_LOGOUT_FOR_SILVER-1 , 0, "ygs3004@naver.com"),
                new User("bkeria", "케리아", "p2", Level.BASIC, MIN_LOGOUT_FOR_SILVER, 0, "abc2@nav2er.com"),
                new User("coner", "오너", "p3", Level.SILVER, 60, MIN_RECOMMED_FOR_GOLD-1, "abc3@nav2er.com"),
                new User("dgumayusi", "구마", "p4", Level.SILVER, 60, MIN_RECOMMED_FOR_GOLD, "abc4@nav2er.com"),
                new User("ezeus", "제우스", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "abc5@nav2er.com")
        );
    }

    @Test
    public void upgradeLevels() throws Exception{

        userDao.deleteAll();
        for(User user:users) {
            userDao.add(user);
        }

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);

        List<String> request = mockMailSender.getRequest();
        assertThat(request.size(), is(2));
        assertThat(request.get(0), is(users.get(1).getEmail()));
        assertThat(request.get(1), is(users.get(3).getEmail()));
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

    public void checkLevel(User user, Level expectedLevel){
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }

    private void checkLevelUpgraded(User user, boolean upgraded){ // 다음레벨로 업그레이드 되는지에 대한 테스트
        User userUpdate = userDao.get(user.getId());
        if(upgraded){
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        }
        else{
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

    static class TestUserServiceImpl extends UserServiceImpl {

        private String id;

        private TestUserServiceImpl(String id){
            this.id = id;
        }

        @Override
        public void upgradeLevel(User user){
            if(user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }

    @Test
    public void upgradeAllOrNothing() throws Exception{

        UserServiceImpl testUserServiceImpl = new TestUserServiceImpl(users.get(3).getId());
        testUserServiceImpl.setUserDao(this.userDao);
        testUserServiceImpl.setMailSender(mailSender);

        userDao.deleteAll();

        for(User user:users) {
            userDao.add(user);
        }

        try{
            testUserServiceImpl.upgradeLevels(); // 실행중에 Exception 이 발생해야함
            fail("TestUserServiceException expected");
        }catch (TestUserServiceException e){
            // Exception 이 발생했을떄 테스트가 정상 진행
        }

        checkLevelUpgraded(users.get(1), false); // upgrade 안되어있는지 Test -> assertThat(userUpdate.getLevel(), is(user.getLevel()));

    }

    static class MockMailSender implements MailSender {

        private List<String> request = new ArrayList<>();

        public List<String> getRequest() {
            return request;
        }

        @Override
        public void send(SimpleMailMessage mailMessage) throws MailException {
            request.add(mailMessage.getTo()[0]);
        }

        @Override
        public void send(SimpleMailMessage[] mailMessage) throws MailException {

        }
    }
}
