package springbook.user.service.before;

import springbook.user.domain.User;

import java.util.List;

public interface UserService6_87 {

    void add(User user);

    User get(String id);
    List<User> getAll();
    void deleteAll();
    void update(User user);

    void upgradeLevels();

}
