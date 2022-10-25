package springbook.user.dao.modifyed;

import springbook.user.dao.UserDao;
import springbook.user.service.UserLevelUpgradePolicy;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.List;

import static springbook.user.domain.Level.BASIC;

public class UserService5_41 implements UserLevelUpgradePolicy {

    public static final int MIN_LOGOUT_FOR_SILVER = 50;
    public static final int MIN_RECOMMED_FOR_GOLD = 30;

    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels(){
        List<User> users = userDao.getAll();

        for(User user : users){
            if(canUpgradeLevel(user)){
                upgradeLevel(user);
            }
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
