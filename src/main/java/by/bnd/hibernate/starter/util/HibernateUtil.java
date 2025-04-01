package by.bnd.hibernate.starter.util;

import by.bnd.hibernate.starter.converter.BirthdayConverter;
import org.hibernate.SessionFactory;
//import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAttributeConverter(new BirthdayConverter());
        //configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        return configuration.buildSessionFactory();
    }
}
