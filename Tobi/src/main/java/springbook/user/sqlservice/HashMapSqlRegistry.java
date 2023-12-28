package springbook.user.sqlservice;

import java.util.HashMap;
import java.util.Map;

public class HashMapSqlRegistry implements SqlRegistry{

    private Map<String, String> sqlMap = new HashMap<>();

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

}
