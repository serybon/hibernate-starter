package by.bnd.hibernate.starter.dao;

import by.bnd.hibernate.starter.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final SessionFactory sessionFactory;
    private final Class<E> clazz;

    @Override
    public E save(E entity) {

        Session session = sessionFactory.getCurrentSession();
        session.save(entity);
        return entity;
    }

    @Override
    public void delete(K id) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(session.find(clazz, id));
        session.flush();
    }

    @Override
    public void update(E entity) {

        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
    }

    @Override
    public Optional<E> findById(K id) {

        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public List<E> findAll() {

        Session session = sessionFactory.getCurrentSession();
        var criteria = session.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);

        return session.createQuery(criteria).getResultList();
    }
}
