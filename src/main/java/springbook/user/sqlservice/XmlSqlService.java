package springbook.user.sqlservice;


import springbook.user.dao.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlSqlService implements SqlService, SqlRegistry, SqlReader{

    private SqlReader sqlReader;
    private SqlRegistry sqlRegistry;

    private Map<String, String> sqlMap = new HashMap<>(); //sqlMap 은 SqlRegistry 구현의 일부, 따라서 외부에서 직접 접근할 수 없다

    private String sqlmapFile;

    public void setSqlReader(SqlReader sqlReader) {
        this.sqlReader = sqlReader;
    }

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    public void setSqlmapFile(String sqlmapFile) {
        this.sqlmapFile = sqlmapFile;
    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        String sql = sqlMap.get(key);
        if(sql==null) throw new SqlNotFoundException(key + "에 대한 SQL을 찾을 수 없습니다");
        else return sql;
    }

    @Override
    public void registerSql(String key, String sql) { // HashMap 이라는 구체적인 구현 방법에서 독립될 수 있도록 메소드로 접근
        sqlMap.put(key, sql);
    }

    @Override
    public void read(SqlRegistry sqlRegistry) {
        String contextPath = Sqlmap.class.getPackage().getName();

        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream is = UserDao.class.getResourceAsStream(sqlmapFile);
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);

            for(SqlType sql : sqlmap.getSql()){
                sqlRegistry.registerSql(sql.getKey(), sql.getValue());
            }

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
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
