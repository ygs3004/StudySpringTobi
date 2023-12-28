package dataAccess.jdbc;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class AbstractSimpleJdbcDaoSupport extends JdbcDaoSupport {

    protected SimpleJdbcTemplate simpleJdbcTemplate;

    protected void initTemplateConfig(){
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(getDataSource());
        initJdbcObjects();
    }

    // AbstractSimpleJdbcDaoSupport 를 사용받는 경우 오버라이드해서 사용하는 초기화 메서드
    private void initJdbcObjects() {
    }

}
