package springbook.user.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import springbook.context.AppContext;
import springbook.context.TestAppContext;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static springbook.user.service.UserServiceImpl.MIN_LOGOUT_FOR_SILVER;
import static springbook.user.service.UserServiceImpl.MIN_RECOMMED_FOR_GOLD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppContext.class, AppContext.class})
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserService testUserService;

    @Autowired
    UserDao userDao;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    MailSender mailSender;

    List<User> users;

    @Autowired
    ApplicationContext context;

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

        // 고립된 테스트 대상 오브젝트 직접 생성
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao); // 목 오브젝트로 만든 userDao 직접 DI

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated(); // MockUserDao로 업데이트 결과 가져오기
        assertThat(updated.size(), is(2));
        checkUserAndLevel(updated.get(0), "bkeria", Level.SILVER);
        checkUserAndLevel(updated.get(1), "dgumayusi", Level.GOLD);

        List<String> request = mockMailSender.getRequest();
        assertThat(request.size(), is(2));
        assertThat(request.get(0), is(users.get(1).getEmail()));
        assertThat(request.get(1), is(users.get(3).getEmail()));
    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel){
        assertThat(updated.getId(), is(expectedId));
        assertThat(updated.getLevel(), is(expectedLevel));
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

    public static class TestUserService extends UserServiceImpl {

        private String id = "dgumayusi";

        @Override
        public void upgradeLevel(User user){
            if(user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }

        public List<User> getAll(){ // get으로 시작하는 메소드 -> 읽기 전용 트랜잭션 대상
            for(User user : super.getAll()){
                super.update(user); // 쓰기 시도로 인한 예외 발생
            }
            return null; // 메소드가 끝나기 전 예외가 발생함, 컴파일을 위해 무의미한 값 적용
        }

    }

    @Test (expected = TransientDataAccessResourceException.class) //오라클에선 미적용
    public void readOnlyTransactionAttribute(){
        testUserService.getAll();
        // 트랜잭션 읽기전용 속성 위반으로 예외발생, Tobi 의 스프링 책과 다르게 예외가 발생하지 않음 -> 검색결과 : readOnly 옵션은 JDBC 에 힌트를 주는 형식으로 예외가 발생하게 되어있는데 오라클에선 안되었음
    }

    static class TestUserServiceException extends RuntimeException {
    }

    @Test
    public void upgradeAllOrNothing() throws Exception{

        userDao.deleteAll();

        for(User user:users) {
            userDao.add(user);
        }

        try{
            this.testUserService.upgradeLevels(); // 실행중에 Exception 이 발생해야함
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

    static class MockUserDao implements UserDao {

        private List<User> users;
        private List<User> updated = new ArrayList<>();

        public MockUserDao(List<User> users) {
            this.users = users;
        }

        public List<User> getUpdated() {
            return this.updated;
        }

        @Override
        public void update(User user) {
            updated.add(user);
        }

        // 스텁 기능 제공
        @Override
        public List<User> getAll() {
            return this.users;
        }

        // 목 오브젝트 기능
        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }

    }

    @Test
    public void movckUpgradelevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
    }

    @Test
    public void advisorAutoProxyuCreator(){
        assertThat(testUserService, is(java.lang.reflect.Proxy.class));
    }

    @Test
    @Rollback // 메소드에서 롤백방법 재설정
    public void transactionSync(){
        userDao.deleteAll();
        userService.add(users.get(0));
        userService.add(users.get(1));
    }

}
