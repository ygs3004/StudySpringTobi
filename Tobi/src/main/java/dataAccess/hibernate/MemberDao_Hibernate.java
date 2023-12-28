package dataAccess.hibernate;

import dataAccess.jpa.Member;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.sql.SQLException;

public class MemberDao_Hibernate {

    // HibernateTemplate 이용하는 경우
    private HibernateTemplate hibernateTemplate;

    // SessionFactory 이용하는경우
    public SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        // HibernateTemplate 이용하는 경우
        hibernateTemplate = new HibernateTemplate(sessionFactory);

        // SessionFactory 이용하는경우
        this.sessionFactory = sessionFactory;
    }

    public void addMember(Member member){
        // HibernateTemplate 이용하는 경우
        hibernateTemplate.save(member);

        // SessionFactory 이용하는경우
        sessionFactory.getCurrentSession().save(member);
    }

    public void hibernateCallbackExample(){
        long count = hibernateTemplate.execute(new HibernateCallback<Long>(){
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                return (Long) session.createQuery("select count(m) from Member m").uniqueResult();
            }
        });
    }

}
