package by.bnd.hibernate.starter.dao;

import by.bnd.hibernate.starter.entity.Payment;
import org.hibernate.SessionFactory;

public class PaymentRepository extends BaseRepository<Long, Payment> {
    public PaymentRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Payment.class);
    }
}
