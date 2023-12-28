package dataAccess.ibatis;

import dataAccess.dto.Member;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.sql.SQLException;

public class MemberDao2_ibatis extends SqlMapClientDaoSupport {

    public void insert(Member member) throws SQLException {
        getSqlMapClient().insert("insertMember", member);
    }

}
