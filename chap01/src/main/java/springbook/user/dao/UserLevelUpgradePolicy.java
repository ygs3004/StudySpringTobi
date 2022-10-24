package springbook.user.dao;

import springbook.user.domain.User;

public interface UserLevelUpgradePolicy {

    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);

}
