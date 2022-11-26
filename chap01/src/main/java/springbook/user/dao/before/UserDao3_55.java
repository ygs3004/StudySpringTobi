package springbook.user.dao.before;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.dao.ConnectionMaker;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao3_55 {

    private ConnectionMaker connectionMaker;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private static UserDao3_55 INSTANCE;
    private Connection c;

    public UserDao3_55() {

    }

    public UserDao3_55(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public static synchronized UserDao3_55 getInstance() {
        if (INSTANCE == null) INSTANCE = new UserDao3_55();
        return INSTANCE;
    }

    public void setConnectionMaker(ConnectionMaker connectionMaker) { //수정자 주입(Setter)
        this.connectionMaker = connectionMaker;
    }

    public void setDataSource(DataSource dataSource) { // 수정자 메소드이면서 JdbcTemplate에 대한 생성, DI 작업을 동시에 수행한다.
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource; // JdbcContext를 적용하지 않은 메소드를 위해 저장
    }


    public void add(final User user) throws SQLException {

        this.jdbcTemplate.update("insert into users(id, name, password)values(?,?,?)", user.getId(), user.getName(), user.getPassword() );
    }

    public User get(String id) throws SQLException {

        return this.jdbcTemplate.queryForObject("select * from users where id=?",
                new Object[]{id},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));

                        return user;
                    }
                });
    }

    public void deleteAll() throws SQLException {

        this.jdbcTemplate.update("delete from users");
    }

    public List<User> getAll(){ // 리턴되는 리스트는 id의 알바벳값 순서로 리턴됨

        return this.jdbcTemplate.query("select * from users order by id",
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {

                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));

                        return user;
                    } 
                }
        );
    }

    public int getCount() throws SQLException {

        return this.jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement("select count(*) from users");
            }
        }, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                return rs.getInt(1);
            }
        });

    }
}