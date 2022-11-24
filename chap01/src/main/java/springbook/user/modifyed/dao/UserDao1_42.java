package springbook.user.modifyed.dao;

import springbook.user.dao.ConnectionMaker;
import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao1_42 {

    private static UserDao1_42 INSTANCE;
    private ConnectionMaker connectionMaker;
    private Connection c;
    private User user;

    public UserDao1_42() {
        /* 생성자 주입시
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
        */

    }

    public UserDao1_42(ConnectionMaker connectionMaker){
        this.connectionMaker=connectionMaker;
    }

    public static synchronized UserDao1_42 getInstance(){
        if(INSTANCE == null) INSTANCE = new UserDao1_42();
        return INSTANCE;
    }

    public void setConnectionMaker(ConnectionMaker connectionMaker) { //수정자 주입(Setter)
        this.connectionMaker = connectionMaker;
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

}