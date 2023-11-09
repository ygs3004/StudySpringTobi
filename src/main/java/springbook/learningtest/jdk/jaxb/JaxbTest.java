package springbook.learningtest.jdk.jaxb;

import org.junit.Test;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JaxbTest {

    @Test
    public void readSqlmap() throws JAXBException, IOException{
        String contextPath = Sqlmap.class.getPackage().getName();
        JAXBContext context = JAXBContext.newInstance(contextPath); // 바인딩용 클래스 위치로 JAXB 컨텍스트를 만든다.

        Unmarshaller unmarshaller = context.createUnmarshaller(); // 언마샬러 생성 // 언마샬링(Unmarshalling) :  xml 문서로 자바 오브젝트 변환, 마샬링(marshalling): 바인딩 오브젝트를 xml로
        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal( // 언마샬을 할 경우 매핑된 오브젝트 트리의 루트인 Sqlmap 을 돌려준다.
            getClass().getResourceAsStream("sqlmap.xml"));

            List<SqlType> sqlList = sqlmap.getSql();

            assertThat(sqlList.size(), is(3));
            assertThat(sqlList.get(0).getKey(), is("add"));
            assertThat(sqlList.get(0).getValue(), is("insert"));
            assertThat(sqlList.get(1).getKey(), is("get"));
            assertThat(sqlList.get(1).getValue(), is("select"));
            assertThat(sqlList.get(2).getKey(), is("delete"));
            assertThat(sqlList.get(2).getValue(), is("delete"));

    }
}
