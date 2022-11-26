package springbook.user.dao.before;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.user.dao.UserDao;
import springbook.user.service.UserLevelUpgradePolicy;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.util.List;

import static springbook.user.domain.Level.BASIC;

public class UserService5_46 implements UserLevelUpgradePolicy {

    public static final int MIN_LOGOUT_FOR_SILVER = 50;
    public static final int MIN_RECOMMED_FOR_GOLD = 30;

    UserDao userDao;

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() throws Exception {

        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
            List<User> users = userDao.getAll();

            for(User user : users){
                if(canUpgradeLevel(user)){
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status); // 트랜잭션 커밋
        }catch (Exception e) {
            transactionManager.rollback(status); // 트랜잭션 롤백
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
        /*
        if (user.getLevel() == Level.BASIC)
            user.setLevel(Level.SILVER);
        else if(user.getLevel() == Level.SILVER)
            user.setLevel(Level.GOLD);
        */
        user.upgradeLevel();
        userDao.update(user);
    }

}
