package springbook.user.sqlservice.updatable;

import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import springbook.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {

    EmbeddedDatabase db;

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {

        db = new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .addScript("classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql")
                .build();

        EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlRegistry.setDataSource(db);

        return embeddedDbSqlRegistry;
    }

    @Test
    public void transactionUpdate(){
        checkFind("SQL1", "SQL2", "SQL3"); // 트랜잭션 롤백 후 상태가 처음과 같은지 체크

        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY9999!@#$", "Modified9999"); // 두 번째 SQL 의 키를 존재하지 않는 것으로 지정, 이 때문에 테스트는 실패하고 롤백 될 것이다.

        try{
            sqlRegistry.updateSql(sqlmap);
            fail(); // 예외가 발생해서 catch 로 넘어가지 않으면 잘못되었으므로 테스트는 fail
        }catch (SqlUpdateFailureException e){

        }

        checkFind("SQL1", "SQL2", "SQL3"); // KEY1은 수정되었지만 트랜잭션이 적용 되었다면 수정되지 않았을 것
    }


    @After
    public void tearDown(){
        db.shutdown();
    }

}
