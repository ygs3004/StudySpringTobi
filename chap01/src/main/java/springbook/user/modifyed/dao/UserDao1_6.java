package springbook.user.modifyed.dao;

import springbook.user.domain.User;

import java.sql.*;

// 독립된 클래스로 Connection(SimpleConnectionMaker) 을 분리
public class UserDao1_6 {

    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDao1_6(){
        simpleConnectionMaker = new SimpleConnectionMaker();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException{

        UserDao1_6 dao = new UserDao1_6();

        User user = new User();
        user.setId("Buff");
        user.setName("윤건수");
        user.setPassword("124");

        dao.add(user);

        System.out.println(user.getId()+" 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user.getPassword());

        System.out.println(user2.getId() + " 조회 성공");

    }

    public void add(User user) throws ClassNotFoundException, SQLException{

        Connection c= simpleConnectionMaker.makeNewConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException{

        Connection c= simpleConnectionMaker.makeNewConnection();

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