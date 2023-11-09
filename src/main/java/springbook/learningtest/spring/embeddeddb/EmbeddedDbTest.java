package springbook.learningtest.spring.embeddeddb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDbTest {

    EmbeddedDatabase db;
    SimpleJdbcTemplate template; //JdbcTemplate를 더 편리하게 사용할 수 있게 확장한 템플릿

    @Before
    public void setUp(){
        db = new EmbeddedDatabaseBuilder().setType(HSQL)
                .addScript("classpath:/springbook/learningtest/spring/embeddeddb/schema.sql")
                .addScript("classpath:/springbook/learningtest/spring/embeddeddb/data.sql")
                .build();

        template = new SimpleJdbcTemplate(db);
    }

    @After
    public void tearDown() {
        db.shutdown(); // 매테스트 뒤에 DB 종료, 내장형 메모리 DB 는 따로 저장하지 않는한 애플리캐이션과 함께 매번 새롭게 생성되고 제거되는 생명주기를 갖는다
    }

    @Test
    public void initData(){
        assertThat(template.queryForInt("select count(*) from sqlmap"), is(2));

        List<Map<String, Object>> list = template.queryForList("select * from sqlmap order by key_");
        assertThat((String)list.get(0).get("key_"), is("KEY1"));
        assertThat((String)list.get(0).get("sql_"), is("SQL1"));
        assertThat((String)list.get(1).get("key_"), is("KEY2"));
        assertThat((String)list.get(1).get("sql_"), is("SQL2"));
    }

    @Test
    public void insert(){
        template.update("insert into sqlmap(key_, sql_) values(?,?)", "KEY3", "SQL3");

        assertThat(template.queryForInt("select count(*) from sqlmap"), is(3));
    }

}