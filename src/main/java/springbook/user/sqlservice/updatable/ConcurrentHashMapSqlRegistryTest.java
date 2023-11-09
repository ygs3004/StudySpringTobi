package springbook.user.sqlservice.updatable;

import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;
import springbook.user.sqlservice.updatable.AbstractUpdatableSqlRegistryTest;
import springbook.user.sqlservice.updatable.ConcurrentHashMapSqlRegistry;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();
    }

}
