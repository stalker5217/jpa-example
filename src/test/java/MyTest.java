import hellojpa.Member;
import hellojpa.Team;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MyTest {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction entityTransaction;

    @BeforeEach
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-example");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
    }

    @AfterEach
    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void saveTest() {
        Member m = new Member();
        entityManager.persist(m);

        Team t = new Team();
        entityManager.persist(t);

        m.setTeam(t);

        entityManager.flush();
        entityManager.clear();

        System.out.println("SIZE >> " + t.getMembers().size());

        entityTransaction.commit();
    }
}
