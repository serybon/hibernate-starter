package by.bnd.hibernate.starter.util;

import by.bnd.hibernate.starter.dao.PaymentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunner {
    public static void main(String[] args) {


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            var paymentRepository = new PaymentRepository(sessionFactory);
            paymentRepository.findById(2L).ifPresent(System.out::println);

            session.getTransaction().commit();


        }
    }
}
