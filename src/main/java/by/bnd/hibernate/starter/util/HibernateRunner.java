package by.bnd.hibernate.starter.util;

import by.bnd.hibernate.starter.dao.CompanyRepository;
import by.bnd.hibernate.starter.dao.UserRepository;
import by.bnd.hibernate.starter.dto.UserCreateDto;
import by.bnd.hibernate.starter.entity.PersonalInfo;
import by.bnd.hibernate.starter.entity.Role;
import by.bnd.hibernate.starter.mapper.CompanyReadMapper;
import by.bnd.hibernate.starter.mapper.UserCreateMapper;
import by.bnd.hibernate.starter.mapper.UserReadMapper;
import by.bnd.hibernate.starter.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

public class HibernateRunner {
    public static void main(String[] args) {


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                     ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)))) {
            session.beginTransaction();

            var companyReadMapper = new CompanyReadMapper();
            var userReadMapper = new UserReadMapper(companyReadMapper);
            var userRepository = new UserRepository(session);
            var companyRepository = new CompanyRepository(session);
            var userCreateMapper = new UserCreateMapper(companyRepository);
            var userService = new UserService(userRepository, userReadMapper, userCreateMapper);
            //userService.findUserById(1L).ifPresent(System.out::println);

            UserCreateDto userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Svetlana")
                            .lastname("Svetova")
                            //.birthDate(new Birthday(LocalDate.now()))
                            .build(),
                    "svetsvet@gmail.com",
                    Role.USER,
                    3);
            System.out.println(userService.create(userCreateDto));


            session.getTransaction().commit();


        }
    }
}
