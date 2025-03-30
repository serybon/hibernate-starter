package by.bnd.hibernate.starter;

import by.bnd.hibernate.starter.entity.*;
import by.bnd.hibernate.starter.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {

        Company company = Company.builder()
                .name("Google")
                .build();
        User user = User.builder()
                .username("ivan10@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Vasil")
                        .lastname("Ivanov")
                        .birthDate(new Birthday(LocalDate.of(1999, 02, 01)))
                        .build())
                .role(Role.ADMIN)
                .company(company)
                .build();
        log.info("User object in transient state {} , {}", user, user.getPersonalInfo().getBirthDate());
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();
                session1.saveOrUpdate(user);


//                user.setFirstname("Ivan");
//                log.warn("User's field \"firstname\" was changed to {}", user.getFirstname());
//                System.out.println(session1.isDirty());
//                session1.get(User.class, "jdoe@gmail.com");
//                System.out.println(user);
//                log.warn("User:  {} Session: {}", user, session1);
                session1.getTransaction().commit();
            }

        } catch (Exception e) {
            log.error("Exception occured", e);
        }
    }
}
