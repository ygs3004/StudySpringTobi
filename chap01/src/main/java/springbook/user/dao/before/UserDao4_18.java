package springbook.user.dao.before;

import oracle.jdbc.xa.OracleXAException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao4_18 {

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

    public void add(final User user) throws DuplicateUserIdException, SQLException{

        try{
            // JDBC 이용해 user 정보를 DB에 추가하는 코드
            int a = this.jdbcTemplate.update("insert into users(id, name, password)values(?,?,?)", user.getId(), user.getName(), user.getPassword() );
            if( a == 2 )
                throw new SQLException();

        }catch (SQLException e){
            //ErrorCode가 Duplicate Entry 이면 예외 전환
            if(e.getErrorCode() == OracleXAException.XAER_DUPID)
                throw new DuplicateUserIdException(e);
            else 
                throw new RuntimeException(e);
        }

    }

    public User get(String id)  {
        return this.jdbcTemplate.queryForObject("select * from users where id=?", new Object[]{id}, userMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

    public List<User> getAll(){
        return this.jdbcTemplate.query("select * from users order by id", userMapper);
    }

}