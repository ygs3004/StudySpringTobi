package dataAccess.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcDao {

    SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Autowired
    public void setDataSource(){
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    // 메서드 주입의 경우
    // @Autowired
    // public void init(DataSource dataSource){
    //     this.simpleJdbcTemplate - new SimpleJdbcTemplate(dataSource);
    // }

    // 하나의 값을 가져는 경우
    public Object queryForObjectExample(String id){
        try{
            return simpleJdbcTemplate.queryForObject("select name from users where id = ?", String.class, id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

}
