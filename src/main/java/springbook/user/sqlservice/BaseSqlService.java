package springbook.user.sqlservice;

import javax.annotation.PostConstruct;

public class BaseSqlService implements SqlService{

    // BaseSqlService 는 상속을 통해 확장후 사용하기 적합한 클래스이다. 필요한 경우 접근할 수 있도록 protected 로 선언
    protected SqlReader sqlReader;
    protected SqlRegistry sqlRegistry;

    public void setSqlReader(SqlReader sqlReader) {
        this.sqlReader = sqlReader;
    }

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    @PostConstruct
    public void loadSql(){
        this.sqlReader.read(this.sqlRegistry);
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        try{
            return this.sqlRegistry.findSql(key);
        }catch (SqlNotFoundException e){
            throw new SqlRetrievalFailureException(e);
        }
    }

}
