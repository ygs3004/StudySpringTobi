package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {

    // Oracle을 사용하는 유저의 DConnectionMaker

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection c= DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","tobi","tobi");
        return c;
    }
}