package by.bnd.hibernate.starter;

import by.bnd.hibernate.starter.entity.*;
import by.bnd.hibernate.starter.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {

        Company company = Company.builder()
                .name("GDN")
                .build();
        User user = User.builder()
                .username("ivan7@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("FName")
                        .lastname("LName")
                        .birthDate(new Birthday(LocalDate.of(1999, 02, 01)))
                        .build())
                .role(Role.ADMIN)
                .company(company)
                .build();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.persist(company);
                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }

        } catch (Exception e) {
            log.error("Exception occured", e);
        }
    }
}
