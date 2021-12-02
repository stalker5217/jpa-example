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
            Order order = new Order();
            order.setStatus(OrderStatus.ORDER);
            entityManager.persist(order);

            Delivery delivery = new Delivery();
            entityManager.persist(delivery);

            order.setDelivery(delivery);

            entityManager.flush();
            entityManager.clear();

            Order order2 = entityManager.find(Order.class, order.getId());
            order2.setDelivery(null);

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
