package dataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;

public class MemberDao {

    private SimpleJdbcTemplate simpleJdbcTemplate;
    private SimpleJdbcInsert memberInsert;
    private SimpleJdbcCall memberFindCall;

    @Autowired
    public void init(DataSource dataSource){
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
        this.memberInsert = new SimpleJdbcInsert(dataSource).withTableName("member");
        this.memberFindCall = new SimpleJdbcCall(dataSource).withFunctionName("find_member");
    }
}
