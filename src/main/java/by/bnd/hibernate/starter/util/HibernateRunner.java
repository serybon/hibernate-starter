package by.bnd.hibernate.starter.util;

import by.bnd.hibernate.starter.dao.PaymentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

public class HibernateRunner {
    public static void main(String[] args) {


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                     ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)))) {
            session.beginTransaction();
            var paymentRepository = new PaymentRepository(session);
            paymentRepository.findById(2L).ifPresent(System.out::println);

            session.getTransaction().commit();


        }
    }
}
