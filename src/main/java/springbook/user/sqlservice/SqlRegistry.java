package springbook.user.sqlservice;

public interface SqlRegistry {

    void registerSql(String key, String sql); // SQL 과 Key를 등록한다.

    String findSql(String key) throws SqlNotFoundException; // Sql 을 키로 검색하여 실패하면 예외를 던진다.
}
