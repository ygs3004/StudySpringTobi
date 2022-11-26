package springbook.user.before.dao;

import springbook.user.dao.ConnectionMaker;
import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao1_26Before {

    private static UserDao1_26Before INSTANCE;
    private ConnectionMaker connectionMaker; // 초기 설정 후 변하지 않는 읽기전용 인스턴스 변수 (싱글톤역할)
    private Connection c;
    private User user; // c, user -> 매번 새로운 값으로 바뀌는 정보를 담은 인스턴스 변수

    private UserDao1_26Before() {

    }

    public UserDao1_26Before(ConnectionMaker connectionMaker){
        this.connectionMaker=connectionMaker;


    }

    public static synchronized UserDao1_26Before getInstance(){
        if(INSTANCE == null) INSTANCE = new UserDao1_26Before();
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

}