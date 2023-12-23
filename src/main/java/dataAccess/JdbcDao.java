package dataAccess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.config.ServiceConfig;
import springbook.user.domain.User;

import javax.sql.DataSource;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
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

    @Test
    public void SimpleJdbcInsertTest(){
        SimpleJdbcInsert insert =
                new SimpleJdbcInsert(dataSource)
                .withTableName("users");
        User user = new User("ygs5", "윤", "123", null, 0, 0, "ygs@a.com");
        insert.execute(new BeanPropertySqlParameterSource(user));

        // insert key 값을 가져오는경우
        // SimpleJdbcInsert registerInsert =
        //         new SimpleJdbcInsert(dataSource)
        //                 .withTableName("users")
        //                 .usingGeneratedKeyColumns("id");
        // int key = (int) registerInsert.executeAndReturnKey(new MapSqlParameterSource("name", "윤"));

    }

}
