/*
package springbook.user.before.dao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.ConnectionMaker;
import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao1_33Before {

    private static UserDao1_33Before INSTANCE;
    private ConnectionMaker connectionMaker;
    private Connection c;
    private User user;

    private UserDao1_33Before() {
        */
/*
        DaoFactory daoFactory = new DaoFactory();
        this.connectionMaker = daoFactory.connectionMaker(); // 주입이 아닌 요청, 의존관계 검색
        *//*


        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);

    }

    public UserDao1_33Before(ConnectionMaker connectionMaker){
        this.connectionMaker=connectionMaker;
    }

    public static synchronized UserDao1_33Before getInstance(){
        if(INSTANCE == null) INSTANCE = new UserDao1_33Before();
        return INSTANCE;
    }

    public void add(User user) throws ClassNotFoundException, SQLException{

       this.c= connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException{

        this.c= connectionMaker.makeConnection();

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

}*/
