package springbook.user.dao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private ConnectionMaker connectionMaker;
    private DataSource dataSource;

    private static UserDao INSTANCE;
    private Connection c;
    private User user;

    public UserDao() {

    }

    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker=connectionMaker;
    }

    public static synchronized UserDao getInstance(){
        if(INSTANCE == null) INSTANCE = new UserDao();
        return INSTANCE;
    }


    public void setConnectionMaker(ConnectionMaker connectionMaker) { //수정자 주입(Setter)
        this.connectionMaker = connectionMaker;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws ClassNotFoundException, SQLException{

       Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException{

        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id= ?");
        ps.setString(1, id);;

        ResultSet rs = ps.executeQuery();
        rs.next();
        this.user = new User();
        this.user.setId((rs.getString("id")));
        this.user.setName((rs.getString("name")));
        this.user.setPassword((rs.getString("password")));

        rs.close();
        ps.close();
        c.close();

        return this.user;
    }

}