package springbook.user.sqlservice;

public interface SqlReader {

    void read(SqlRegistry sqlRegistry); // SQL 을 외부에서 가져와 SqlRegistry 에 등록
    
}
