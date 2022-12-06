package springbook.user.sqlservice.before;

import springbook.user.dao.UserDao;
import springbook.user.sqlservice.SqlRetrievalFailureException;
import springbook.user.sqlservice.SqlService;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlSqlService7_21 implements SqlService {

    private Map<String, String> sqlMap = new HashMap<>(); // 읽어온 SQL 을 저장해둘 맵

    public XmlSqlService7_21() { // JAXB API 를 이용해 XML 문서를 오브젝트 트리로 읽어온다
        
        String contextPath = Sqlmap.class.getPackage().getName();

        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream is = UserDao.class.getResourceAsStream("sqlmap.xml"); // UserDao 와 같은 클래스패스의 sqlmap.xml 파일 변환
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);

            for(SqlType sql : sqlmap.getSql()){
                sqlMap.put(sql.getKey(), sql.getValue()); // 읽어온 내용 Map으로 저장
            }
            
        } catch (JAXBException e) {
            throw new RuntimeException(e); // JAXBException 은 복구 불가능한 예외, 불필요한 throw 를 피하도록 RuntimeException 으로 포장하여 던진다.
        }

    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {

        String sql = sqlMap.get(key);

        if(sql == null)
            throw new SqlRetrievalFailureException(key + "를 이용해서 SQL을 찾을 수 없습니다.");
        else
            return sql;
    }

}
