package springbook.user.dao.Before;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.List;

public class UserService5_23 {

    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void upgradeLevels(){
        List<User> users = userDao.getAll();

        for(User user : users){
            Boolean changed = null; //레벨의 변화가 있는지 체크
            if(user.getLevel() == Level.BASIC && user.getLogin()>=50){
                user.setLevel(Level.SILVER);
                changed = true;
            }
            else if(user.getLevel() == Level.SILVER && user.getRecommend()>=30){
                user.setLevel(Level.GOLD);
                changed = true;
            }
            else if (user.getLevel() == Level.GOLD) {
                changed = false;
            }
            else {
                changed = false;
            }

            if(changed) {
                userDao.update(user);
            }
        }
    }

    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);

    }
}
