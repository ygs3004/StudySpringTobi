package dataAccess.jdbc;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;

public class JNDIBind {

    public void JNDIBindForTest() throws NamingException {
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        builder.bind("jdbc/Default", ds);
        builder.activate();
    }

}
