package dataAccess.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import dataAccess.dto.Member;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class MemberDao_ibatis {

    private SqlMapClientTemplate sqlMapClientTemplate;

    public void setSqlMapClient(SqlMapClient sqlMapClient){
        // SqlMapClient 가 아닌 SqlMapClientTemplate 사용시 Spring DA 기술의 다양한 혜택을 받을 수 있음
        sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
    }

    public void insert(Member member){
        sqlMapClientTemplate.insert("insertMember", member);
    }

    public void deleteAll(){
        sqlMapClientTemplate.delete("deleteMemberAll");
    }

}
