package springbook.user.problemdao;

import springbook.user.domain.User;

import java.sql.*;

// 다른 DB를 사용할 경우 : UserDao를 추상 클래스 , getConnection을 추상 메소드로 선언 -> 사용자에 따라 구현
// NUser Oracle DUser Mysql
public abstract class UserDao1_5 {

    public void add(User user) throws ClassNotFoundException, SQLException{

        Connection c= getConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException{

        Connection c= getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id= ?");
        ps.setString(1, id);;

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId((rs.getString("id")));
        user.setName((rs.getString("name")));
        user.setPassword((rs.getString("password")));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;

    // 오라클일 경우
    public class NUserDao extends UserDao{

        @Override
        public Connection getConnection() throws ClassNotFoundException, SQLException{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection c= DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE","tobi","tobi");
            return c;
        }

    }

    public class DUserDao extends UserDao{

        @Override
        public Connection getConnection() throws ClassNotFoundException, SQLException{
            Class.forName("oracle.mysql.jdbc.Driver");
            Connection c= DriverManager.getConnection(
                    "jdbc:mysql://localhost/springbook","tobi","tobi");
            return c;
        }

    }

}