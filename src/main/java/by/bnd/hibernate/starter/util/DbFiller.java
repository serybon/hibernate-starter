//package by.bnd.hibernate.starter.util;
//
//import by.bnd.hibernate.starter.entity.*;
//import org.hibernate.Session;
//
//import java.time.LocalDate;
//
//
//public class DbFiller {
//
//
//    void fillDB() {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        User user1 = User.builder()
//                .username("johndoe@gmail.com")
//                .personalInfo(PersonalInfo.builder()
//                        .firstname("John")
//                        .lastname("Doe")
//                        .birthDate(new Birthday(LocalDate.of(1970, 5, 12)))
//                        .build())
//                .role(Role.ADMIN)
//                .company(session.get(Company.class, 2))
//                .build();
//        User user2 = User.builder()
//                .username("jane@gmail.com")
//                .personalInfo(PersonalInfo.builder()
//                        .firstname("Jane")
//                        .lastname("Doe")
//                        .birthDate(new Birthday(LocalDate.of(1988, 4, 22)))
//                        .build())
//                .role(Role.USER)
//                .company(session.get(Company.class, 1))
//                .build();
//        User user3 = User.builder()
//                .username("alice@gmail.com")
//                .personalInfo(PersonalInfo.builder()
//                        .firstname("Alice")
//                        .lastname("Smith")
//                        .birthDate(new Birthday(LocalDate.of(1990, 7, 30)))
//                        .build())
//                .role(Role.USER)
//                .company(session.get(Company.class, 3))
//                .build();
//        User user4 = User.builder()
//                .username("bob@gmail.com")
//                .personalInfo(PersonalInfo.builder()
//                        .firstname("Bob")
//                        .lastname("Johnson")
//                        .birthDate(new Birthday(LocalDate.of(1996, 10, 4)))
//                        .build())
//                .role(Role.USER)
//                .company(session.get(Company.class, 2))
//                .build();
//        User user5 = User.builder()
//                .username("charlie@gmail.com")
//                .personalInfo(PersonalInfo.builder()
//                        .firstname("Charlie")
//                        .lastname("Brown")
//                        .birthDate(new Birthday(LocalDate.of(2002, 1, 6)))
//                        .build())
//                .role(Role.USER)
//                .company(session.get(Company.class, 1))
//                .build();
//        session.save(user1);
//        session.save(user2);
//        session.save(user3);
//        session.save(user4);
//        session.save(user5);
//
//        session.getTransaction().commit();
//    }
//}
