package springbook.user.sqlservice.updatable;

import org.junit.Before;
import org.junit.Test;
import springbook.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;
import springbook.user.sqlservice.SqlNotFoundException;
import springbook.user.sqlservice.updatable.ConcurrentHashMapSqlRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public abstract class AbstractUpdatableSqlRegistryTest {

    UpdatableSqlRegistry sqlRegistry;

    @Before
    public void setUp(){
        sqlRegistry = createUpdatableSqlRegistry();
        sqlRegistry.registerSql("KEY1", "SQL1");
        sqlRegistry.registerSql("KEY2", "SQL2");
        sqlRegistry.registerSql("KEY3", "SQL3");
    }

    abstract protected UpdatableSqlRegistry createUpdatableSqlRegistry(); // 서ㅏ브클래스에서 구현하도록 만든다

    @Test
    public void find(){
        checkFind("SQL1", "SQL2", "SQL3");
    }

    protected void checkFind(String expected1, String expected2, String expected3){ // 서브클래스에서 접근 가능하도록 protected
        assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
        assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
        assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
    }

    @Test(expected = SqlNotFoundException.class) // 키에 해당하는 SQL 을 찾을 수 없을 때 예외상횡에 대한 테스트, 빼먹기 쉬우므로 항상 의식적으로 넣으려고 노력해야함
    public void unknownKey(){
        sqlRegistry.findSql("SQL9999!@#$");
    }

    @Test
    public void undateSingle() throws SqlUpdateFailureException { // 변경기능에 대한 테스트, 변경된 이외의 것도 확인하는게 좋음
        sqlRegistry.updateSql("KEY2", "Modified2");
        checkFind("SQL1", "Modified2", "SQL3");
    }

    @Test
    public void updateMulti() throws SqlUpdateFailureException {
        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY3", "Modified3");

        sqlRegistry.updateSql(sqlmap);
        checkFind("Modified1", "SQL2", "Modified3");
    }

    @Test(expected = SqlUpdateFailureException.class) // 존재재하지 않는 키의 SQL 변경 시도 테스트
    public void updateWithNotExistingKey() throws SqlUpdateFailureException {
        sqlRegistry.updateSql("SQL9999!@#$", "Modified2");
    }

}
