package springbook.user.modifyed.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.dao.ConnectionMaker;
import springbook.user.dao.DeleteAllStatement;
import springbook.user.dao.StatementStrategy;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao3_18 {

    private ConnectionMaker connectionMaker;
    private DataSource dataSource;
    private static UserDao3_18 INSTANCE;
    private Connection c;

    public UserDao3_18() {

    }

    public UserDao3_18(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public static synchronized UserDao3_18 getInstance() {
        if (INSTANCE == null) INSTANCE = new UserDao3_18();
        return INSTANCE;
    }


    public void setConnectionMaker(ConnectionMaker connectionMaker) { //수정자 주입(Setter)
        this.connectionMaker = connectionMaker;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void jdbcContextWithStatemnetStrategy (StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();

        }catch (Exception e) {
            throw e;

        } finally {
            if(ps != null) { try { ps.close();} catch (SQLException e){}}
            if(c != null) { try { c.close();} catch (SQLException e){}}
        }

    }

    public void add(final User user) throws SQLException {

        class AddStatement implements StatementStrategy {

            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
                ps.setString(1, user.getId());
                ps.setString(1, user.getName());
                ps.setString(1, user.getPassword());
                return ps;
            }
        }

        StatementStrategy st = new AddStatement();
        jdbcContextWithStatemnetStrategy(st);

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

        StatementStrategy st = new DeleteAllStatement(); // 선정한 전략 클래스 오브젝트 생성
        jdbcContextWithStatemnetStrategy(st); // 컨텐스트 호출, 전략 오브젝트 전달

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