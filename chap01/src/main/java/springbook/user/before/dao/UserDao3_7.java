package springbook.user.before.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.dao.ConnectionMaker;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao3_7 {

    private ConnectionMaker connectionMaker;
    private DataSource dataSource;

    private static UserDao3_7 INSTANCE;
    private Connection c;

    public UserDao3_7() {

    }

    public UserDao3_7(ConnectionMaker connectionMaker){
        this.connectionMaker=connectionMaker;
    }

    public static synchronized UserDao3_7 getInstance(){
        if(INSTANCE == null) INSTANCE = new UserDao3_7();
        return INSTANCE;
    }


    public void setConnectionMaker(ConnectionMaker connectionMaker) { //수정자 주입(Setter)
        this.connectionMaker = connectionMaker;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException{

       Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException{

        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id= ?");
        ps.setString(1, id);;

        ResultSet rs = ps.executeQuery();

        User user = null; // rs.next가 false면 EmptyResultDataAccessException
        if(rs.next()){
            user = new User();
            user.setId((rs.getString("id")));
            user.setName((rs.getString("name")));
            user.setPassword((rs.getString("password")));
        }

        rs.close();
        ps.close();
        c.close();

        if(user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }

    public void deleteAll() throws SQLException{

        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();
            ps = makeStatement(c);
            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            if( ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                }
            }
            if( c != null){
                try{
                    c.close();
                }catch (SQLException e){
                }
            }
        }
    }

    public int getCount() throws SQLException{

        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if( rs != null){
                try{
                   rs.close();
                }catch (SQLException e){
                }
            }
            if( ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                }
            }
            if( c != null){
                try{
                    c.close();
                }catch (SQLException e){
                }
            }
        }
    }

    private PreparedStatement makeStatement(Connection c) throws SQLException{
        PreparedStatement ps;
        ps = c.prepareStatement("delete from users");
        return ps;
    }

}