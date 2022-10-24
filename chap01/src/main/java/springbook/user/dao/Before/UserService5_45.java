package springbook.user.dao.Before;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import springbook.user.dao.UserDao;
import springbook.user.dao.UserLevelUpgradePolicy;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

import static springbook.user.domain.Level.BASIC;

public class UserService5_45 implements UserLevelUpgradePolicy {

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
        
        TransactionSynchronizationManager.initSynchronization(); // Connection 동기화 시작
        Connection c = DataSourceUtils.getConnection(dataSource);
        c.setAutoCommit(false); // 자동커밋 false

        try{
            List<User> users = userDao.getAll();

            for(User user : users){
                if(canUpgradeLevel(user)){
                    upgradeLevel(user);
                }
            }
            c.commit(); // 정상 진행시 commit
        }catch (Exception e){
            c.rollback();
            throw e; // 예외 발생시 롤백
        } finally {
            // 동기화 작업 종료 및 정리
            DataSourceUtils.releaseConnection(c, dataSource); // DB 커넥션 닫기
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
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
