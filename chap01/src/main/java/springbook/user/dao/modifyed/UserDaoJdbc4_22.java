/*
package springbook.user.dao.Before;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.dao.Before.DuplicateUserIdException;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc4_22 implements UserDao {

    public void setDataSource(DataSource dataSource) { // 수정자 메소드이면서 JdbcTemplate에 대한 생성, DI 작업을 동시에 수행한다.
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper =
        new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        };

    @Override
    public void add(final User user) {

        try{
            // JDBC 이용해 user 정보를 DB에 추가하는 코드
            int a = this.jdbcTemplate.update("insert into users(id, name, password)values(?,?,?)", user.getId(), user.getName(), user.getPassword() );

        }catch (DuplicateKeyException e){
            // 로그남기기
            throw new DuplicateUserIdException(e); // 예외를 전환할때는 원인의 예외를 중첩하는것이 추적에 좋다
        }

    }

    @Override
    public User get(String id)  {
        return this.jdbcTemplate.queryForObject("select * from users where id=?", new Object[]{id}, userMapper);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

    @Override
    public List<User> getAll(){
        return this.jdbcTemplate.query("select * from users order by id", userMapper);
    }

}*/
