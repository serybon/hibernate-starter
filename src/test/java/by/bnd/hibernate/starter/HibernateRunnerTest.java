package by.bnd.hibernate.starter;

import by.bnd.hibernate.starter.entity.*;
import by.bnd.hibernate.starter.util.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

class HibernateRunnerTest {
    @Test
    public void checkManyToMany(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();
        Chat chat = session.get(Chat.class,3L);
        User user1 = session.get(User.class,12L);
        UserChat userChat = UserChat.builder()
                .createdAt(Instant.now())
                .createdBy("John Doe")
                .build();

        userChat.setUser(user1);
        userChat.setChat(chat);

        session.save(userChat);

        session.getTransaction().commit();
    }
    @Test
    public void checkOneToOne() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();
        User user = User.builder()
                .username("ivan75@gmail.com")
                .build();
        Profile profile = Profile.builder()
                .language("ru")
                .street("Pobedy 1")
                .build();
        session.save(user);
        profile.setUser(user);
        session.save(profile);




        session.getTransaction().commit();
    }



    @Test
    public void checkOrphanRemoval() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
            Company company = session.get(Company.class, 1);
            company.getUsers().removeIf(user -> user.getId().equals(10));
        session.getTransaction().commit();
    }
    @Test
    public void addNewUserAndCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Random random = new Random();
        Company company = Company.builder()
                .name("Google" + random.nextInt(100))
                .build();
        User user = User.builder()
                .username("ivan" + random.nextInt(100) + "@gmail.com")
                .build();
        company.addUser(user);
        session.save(company);


        session.getTransaction().commit();
    }

    @Test
    public void checkOneToMany() {

        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var company = session.get(Company.class, 1);
        company.getUsers().forEach(System.out::println);


        session.getTransaction().commit();
    }


    @Test
    public void testHibernateApi() throws SQLException, IllegalAccessException {
        Company company = Company.builder()
                .name("SAP2")
                .build();
        User user = User.builder()
                .username("ivan6@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("FName")
                        .lastname("LName")
                        .birthDate(new Birthday(LocalDate.of(1999, 02, 01)))
                        .build())
                .role(Role.ADMIN)
                .company(company)
                .build();

        var sql = """
                Insert into 
                %s
                (%s)
                values (%s)
                """;

        var tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(table -> table.schema() + "." + table.name())
                .orElse(user.getClass().getName());
        Field[] fields = user.getClass().getDeclaredFields();
        var columnNames = Arrays.stream(fields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(column -> column.name())
                        .orElse(field.getName())).collect(Collectors.joining(", "));

        var columnValues = Arrays.stream(fields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            preparedStatement.setObject(i + 1, fields[i].get(user));
        }
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
}


