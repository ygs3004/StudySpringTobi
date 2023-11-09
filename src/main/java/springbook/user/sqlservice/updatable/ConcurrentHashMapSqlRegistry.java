package springbook.user.sqlservice.updatable;

import springbook.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;
import springbook.user.sqlservice.SqlNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapSqlRegistry implements UpdatableSqlRegistry {

    private Map<String, String> sqlMap = new ConcurrentHashMap<>() ; // 전체데이터, 데이터 조회의 경우 락을 걸지 않는 동기화 된 HashMap

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        String sql = sqlMap.get(key);
        if(sql==null) throw new SqlNotFoundException(key + "에 대한 SQL을 찾을 수 없습니다");
        else return sql;
    }

    @Override
    public void registerSql(String key, String sql) { // HashMap 이라는 구체적인 구현 방법에서 독립될 수 있도록 메소드로 접근
        sqlMap.put(key, sql);
    }


    @Override
    public void updateSql(String key, String sql) throws SqlUpdateFailureException {
        if(sqlMap.get(key)==null){
            throw new SqlUpdateFailureException(key + "에 해당하는 SQL을 찾을 수 없습니다.");
        }
        sqlMap.put(key, sql);
    }

    @Override
    public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException {
        for(Map.Entry<String, String> entry : sqlmap.entrySet()){
            updateSql(entry.getKey(), entry.getValue());
        }
    }

}
