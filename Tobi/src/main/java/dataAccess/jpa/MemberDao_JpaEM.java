package dataAccess.jpa;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class MemberDao_JpaEM {

    // @Autowired
    // EntityManagerFactory emf;

    @PersistenceContext
    EntityManager em;

    public void TransactionExample(){
        // EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Member m = new Member(1, "Spring", 7);
        em.persist(m);
        Long count = em.createQuery("select count(m) from Member m", Long.class).getSingleResult();

        em.getTransaction().commit();
    }

}
