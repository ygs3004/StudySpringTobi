package springbook.user.service.before;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserLevelUpgradePolicy;

import java.util.List;

import static springbook.user.domain.Level.BASIC;

public class UserService5_56 implements UserLevelUpgradePolicy {

    public static final int MIN_LOGOUT_FOR_SILVER = 50;
    public static final int MIN_RECOMMED_FOR_GOLD = 30;

    UserDao userDao;

    private PlatformTransactionManager transactionManager;

    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void upgradeLevels() throws Exception {

        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
            List<User> users = userDao.getAll();

            for(User user : users){
                if(canUpgradeLevel(user)){
                    upgradeLevel(user);
                }
            }
            this.transactionManager.commit(status); // 트랜잭션 커밋
        }catch (Exception e) {
            this.transactionManager.rollback(status); // 트랜잭션 롤백
            throw e; // 예외 발생시 롤백
        }

    }

    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(BASIC);
        userDao.add(user);
    }

    @Override
    public boolean canUpgradeLevel(User user){
        Level currentLevel = user.getLevel();
        switch (currentLevel){
            case BASIC : return(user.getLogin() >= MIN_LOGOUT_FOR_SILVER);
            case SILVER : return(user.getRecommend() >= MIN_RECOMMED_FOR_GOLD);
            case GOLD : return false;
            default : throw new IllegalArgumentException("Unknown Level" + currentLevel);

        }
    }

    @Override
    public void upgradeLevel(User user){
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEMail(user);
    }

    private void sendUpgradeEMail(User user) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@ksug.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());

        mailSender.send(mailMessage);
    }

    public class DummyMailSender implements MailSender{

        @Override
        public void send(SimpleMailMessage mailMessage) throws MailException {
        }

        @Override
        public void send(SimpleMailMessage[] mailMessage) throws MailException {
        }
    }

}
