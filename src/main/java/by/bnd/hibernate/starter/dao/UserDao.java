package by.bnd.hibernate.starter.dao;

import by.bnd.hibernate.starter.entity.User;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static by.bnd.hibernate.starter.entity.QCompany.*;
import static by.bnd.hibernate.starter.entity.QUser.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    /**Возвращает всех сотрудников*/
    public List<User> findAll(Session session) {

        return new JPAQuery<User>(session).select(user).from(user).fetch();
    }

    private static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    /**Возвращает всех сотрудников c заданным именем*/
    public List<User> findAllByFirstName(Session session,String firstName) {
        return new JPAQuery<User>(session).select(user).from(user)
                .where(user.personalInfo().firstname.eq(firstName)).fetch();
    }

    /**Возвращает первые {limit} сотрудников, упорядоченных по дате рождения*/
    public List<User> findLimitedUsersOrderedByBirthDate(Session session,int limit) {
        return new JPAQuery<User>(session).select(user).from(user)
                .orderBy(new OrderSpecifier(Order.ASC, user.personalInfo().birthDate))
                .limit(limit).fetch();
    }
    /** Возвращает всех сотрудников указанной компании */
    public List<User> findAllByCompanyName(Session session,String companyName) {
        return new JPAQuery<User>(session).select(user).from(company)
                .join(company.users,user)
                .where(company.name.eq(companyName))
                .orderBy(new OrderSpecifier(Order.ASC, user.personalInfo().lastname))
                .fetch();
    }
    /**Возвращает средний возраст всех сотрудников каждой компании*/
    public List<Tuple> findCompanyNamesWithAgvUserAgeOrderedByCompanyName(Session session) {
        return new JPAQuery<Tuple>(session)
                .select(company.name,user.personalInfo().birthDate.)
    }
}
