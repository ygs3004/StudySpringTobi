package springbook.user.modifyed.dao;

import springbook.user.dao.StatementStrategy;
import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement3_16 implements StatementStrategy {

    User user;

    public AddStatement3_16(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(1, user.getName());
        ps.setString(1, user.getPassword());
        return ps;
    }
}