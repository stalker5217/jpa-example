package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        // 어플리케이션에서 하나만 생성
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-example");
        
        //스레드 간 공유 또는 재사용 금지
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            // CREATE
            Member member = new Member();
            member.setName("Hello");
            entityManager.persist(member);

            // READ
            Member findMember = entityManager.find(Member.class, 3L);
            System.out.println("ID >> " + findMember.getId());
            System.out.println("ID >> " + findMember.getName());

            // UPDATE
            findMember.setName("Hello JPA");

            // DELETE
            entityManager.remove(findMember);

            entityTransaction.commit();
        }
        catch(Exception e) {
            entityTransaction.rollback();
        }
        finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
