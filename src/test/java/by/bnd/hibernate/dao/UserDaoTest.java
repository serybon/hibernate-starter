package by.bnd.hibernate.dao;

import by.bnd.hibernate.starter.dao.UserDao;
import by.bnd.hibernate.starter.entity.*;
import by.bnd.hibernate.starter.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {
    private static SessionFactory sessionFactory;
    private static UserDao userDao;

    @BeforeAll
    public static void start() {
        sessionFactory = HibernateUtil.buildSessionFactory();
        userDao = UserDao.getInstance();
    }

    @AfterAll
    public static void finish() {
        sessionFactory.close();
    }

    @Test
    void fillDB() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user1 = User.builder()
                .username("johndoe@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("John")
                        .lastname("Doe")
                        .birthDate(new Birthday(LocalDate.of(1970, 5, 12)))
                        .build())
                .role(Role.ADMIN)
                .company(session.get(Company.class, 2))
                .build();
        User user2 = User.builder()
                .username("jane@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Jane")
                        .lastname("Doe")
                        .birthDate(new Birthday(LocalDate.of(1988, 4, 22)))
                        .build())
                .role(Role.USER)
                .company(session.get(Company.class, 1))
                .build();
        User user3 = User.builder()
                .username("alice@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Alice")
                        .lastname("Smith")
                        .birthDate(new Birthday(LocalDate.of(1990, 7, 30)))
                        .build())
                .role(Role.USER)
                .company(session.get(Company.class, 3))
                .build();
        User user4 = User.builder()
                .username("bob@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Bob")
                        .lastname("Johnson")
                        .birthDate(new Birthday(LocalDate.of(1996, 10, 4)))
                        .build())
                .role(Role.USER)
                .company(session.get(Company.class, 2))
                .build();
        User user5 = User.builder()
                .username("charlie@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Charlie")
                        .lastname("Brown")
                        .birthDate(new Birthday(LocalDate.of(2002, 1, 6)))
                        .build())
                .role(Role.USER)
                .company(session.get(Company.class, 1))
                .build();
        session.save(user1);
        session.save(user2);
        session.save(user3);
        session.save(user4);
        session.save(user5);

        session.getTransaction().commit();
    }

    @Test
    void findAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> users = userDao.findAll(session);
        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(7); // Замените 5 на ожидаемое количество пользователей в вашей базе данных
        List<String> fullNames = users.stream()
                .map(User::fullName)
                .toList();
        assertThat(fullNames).containsExactlyInAnyOrder(
                "Alice Smith", // Замените на ожидаемые полные имена
                "Semen Dragva",
                "Bob Johnson",
                "John Doe",
                "Jane Doe",
                "Charlie Brown",
                "Pavel Bobrov"
        );
        session.getTransaction().commit();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    @Test
    void findAllByFirstName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        String searchedFirstName = "Jane";
        List<User> users = userDao.findAll(session);
        assertThat(users).isNotEmpty();
        List<User> usersWithThSameFirstName = users.stream()
                .filter(user -> user.getPersonalInfo().getFirstname().equals(searchedFirstName)).toList();
        System.out.println(usersWithThSameFirstName);
        assertThat(usersWithThSameFirstName).isNotEmpty();
        session.getTransaction().commit();
    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения
     */
    @Test
    void findLimitedUsersOrderedByBirthDate() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        int limit = 2;
        List<User> selectedUsers = userDao.findLimitedUsersOrderedByBirthDate(session, limit);
        selectedUsers.forEach(user -> System.out.println(user));
        assertThat(selectedUsers).isNotEmpty();
        session.getTransaction().commit();
    }
    /** Возвращает всех сотрудников указанной компании */
    @Test
    void findAllByCompanyName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        String searchedCompanyName = session.get(Company.class, 1).getName();
        List<User> selectedUsers = userDao.findAllByCompanyName(session, searchedCompanyName);
        selectedUsers.forEach(System.out::println);
        assertThat(selectedUsers).isNotEmpty();

        session.getTransaction().commit();
    }
}

