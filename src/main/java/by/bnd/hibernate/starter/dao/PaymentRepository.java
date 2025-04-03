package by.bnd.hibernate.starter.dao;

import by.bnd.hibernate.starter.entity.Payment;

import javax.persistence.EntityManager;

public class PaymentRepository extends BaseRepository<Long, Payment> {
    public PaymentRepository(EntityManager entityManager) {
        super(entityManager, Payment.class);
    }
}
