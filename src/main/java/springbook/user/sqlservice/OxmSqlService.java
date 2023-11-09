package springbook.user.sqlservice;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import springbook.user.dao.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class OxmSqlService implements SqlService {

    private final BaseSqlService baseSqlService = new BaseSqlService();
    private final OxmSqlReader oxmSqlReader = new OxmSqlReader(); // final로 변경 불가능, 강하게 결합
    private SqlRegistry sqlRegistry = new HashMapSqlRegistry();

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller){
        this.oxmSqlReader.setUnmarshaller(unmarshaller);
    }

    public void setSqlmap(Resource sqlmap){
        this.oxmSqlReader.setSqlmap(sqlmap);
    }

    @PostConstruct
    public void loadSql(){
        this.baseSqlService.setSqlReader(this.oxmSqlReader);
        this.baseSqlService.setSqlRegistry(this.sqlRegistry);

        this.baseSqlService.loadSql();
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        return this.baseSqlService.getSql(key);
    }

    private static class OxmSqlReader implements SqlReader { // 멤버클래스, 탑레벨 클래스인 OxmSqlService만이 사용 가능

        private Resource sqlmap = new ClassPathResource("sqlmap.xml", UserDao.class);
        private Unmarshaller unmarshaller;

        public void setUnmarshaller(Unmarshaller unmarshaller) {
            this.unmarshaller = unmarshaller;
        }

        public void setSqlmap(Resource sqlmap) {
            this.sqlmap = sqlmap;
        }


        @Override
        public void read(SqlRegistry sqlRegistry) {
            try{
                Source source = new StreamSource(sqlmap.getInputStream());
                Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source); // OxmSqlService 를 통해 전달받은 OXM 인터페이스 구현 오브젝트를 가지고 언마샬링 수행
                for(SqlType sql : sqlmap.getSql()){
                    sqlRegistry.registerSql(sql.getKey(), sql.getValue());
                }

            }catch (IOException e) {
                throw new IllegalArgumentException(this.sqlmap.getFilename()+"을 가져올 수 없습니다.");
            }
        }

    }

}
