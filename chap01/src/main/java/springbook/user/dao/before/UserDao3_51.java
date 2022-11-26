package springbook.user.dao.before;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import springbook.user.dao.ConnectionMaker;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao3_51 {

    private ConnectionMaker connectionMaker;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private static UserDao3_51 INSTANCE;
    private Connection c;

    public UserDao3_51() {

    }

    public UserDao3_51(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public static synchronized UserDao3_51 getInstance() {
        if (INSTANCE == null) INSTANCE = new UserDao3_51();
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

        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id= ?");
        ps.setString(1, id);
        ;

        ResultSet rs = ps.executeQuery();

        User user = null; // rs.next가 false면 EmptyResultDataAccessException
        if (rs.next()) {
            user = new User();
            user.setId((rs.getString("id")));
            user.setName((rs.getString("name")));
            user.setPassword((rs.getString("password")));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }

    public void deleteAll() throws SQLException {

        this.jdbcTemplate.update("delete from users");

        /*
        this.jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement("delete from users");
                    }
                }
        );
        */
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