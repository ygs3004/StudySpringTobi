package springbook.user.dao.Before;

import springbook.user.dao.ConnectionMaker;
import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao1_23Before {

    private ConnectionMaker connectionMaker; // 인터페이스를 통해 오브젝트 접근, 각각의 사용자마다 구현클래스를 사용하여 다형성을 이용 가능

/*
    public UserDao(){
        connectionMaker = new DConnectionMaker(); // DConnectionMaker의 클래스명을 써야하는 문제점(유저마다 변경되어야함)
    }
*/

    public UserDao1_23Before(ConnectionMaker connectionMaker){ // DconnectionMaker 의 책임을 클라이언트에게 넘김
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException{

        Connection c= connectionMaker.makeConnection(); // 인터페이스에 정의된 메소드로 사용자에 따라 클래스가 변경되어도 메소드명(코드)가 변경될 걱정이 없음

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException{

        Connection c= connectionMaker.makeConnection(); // 인터페이스에 정의된 메소드로 사용자에 따라 클래스가 변경되어도 메소드명(코드)가 변경될 걱정이 없음

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


}