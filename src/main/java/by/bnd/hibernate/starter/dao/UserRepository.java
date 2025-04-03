package by.bnd.hibernate.starter.dao;

import by.bnd.hibernate.starter.entity.User;

import javax.persistence.EntityManager;

public class UserRepository extends BaseRepository<Long, User>{

    public UserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }
}
