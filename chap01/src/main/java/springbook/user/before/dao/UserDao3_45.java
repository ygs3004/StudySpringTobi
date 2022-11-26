package springbook.user.before.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.dao.ConnectionMaker;
import springbook.user.dao.JdbcContext;
import springbook.user.dao.StatementStrategy;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao3_45 {

    private ConnectionMaker connectionMaker;
    private JdbcContext jdbcContext;
    private DataSource dataSource;
    private static UserDao3_45 INSTANCE;
    private Connection c;

    public UserDao3_45() {

    }

    public UserDao3_45(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public static synchronized UserDao3_45 getInstance() {
        if (INSTANCE == null) INSTANCE = new UserDao3_45();
        return INSTANCE;
    }

    public void setConnectionMaker(ConnectionMaker connectionMaker) { //수정자 주입(Setter)
        this.connectionMaker = connectionMaker;
    }

    public void setDataSource(DataSource dataSource) { // 수정자 메소드이면서 JdbcContext에 대한 생성, DI 작업을 동시에 수행한다.
        this.jdbcContext = new JdbcContext(); // JdbcContext 생성(IoC
        this.jdbcContext.setDataSource(dataSource); // 의존 오브젝트 주입
        this.dataSource = dataSource; // JdbcContext를 적용하지 않은 메소드를 위해 저장
    }


    public void add(final User user) throws SQLException {

        this.jdbcContext.workWithStatementStrategy(
            new StatementStrategy() {
                @Override
                public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                    PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
                    ps.setString(1, user.getId());
                    ps.setString(2, user.getName());
                    ps.setString(3, user.getPassword());
                    return ps;
                }
            }
        );

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

        this.jdbcContext.executeSql("delete from users");

    }

    public int getCount() throws SQLException {

        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}