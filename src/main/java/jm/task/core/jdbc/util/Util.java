package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class Util {

    /** Поле необходимого драйвера */
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    /** Поле для настройки подключения с использованием Hibernate */
    private static final String DB_DIALECT = "org.hibernate.dialect.MySQLDialect";

    /** Поле url "сервера/имя базыданных" для подлючения к базе данных */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";

    /** Пользователь для подключения к базе */
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    /**
     * Метод для получения подключения к БД с использованием JDBC
     * @return возвращает обьект класса Connection
     */
    public Connection getConnectionJDBC() {
        Connection connect = null;

        try {
          Class.forName(DB_DRIVER);
          connect = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
          throw new RuntimeException(e);
        }

        return connect;
    }

    /**
     * Метод для получения подключения к БД с использованием Hibernate
     * @return возвращает обьект класса Session
     */
    public Session getConnectionHibernate() {
        Properties prop = new Properties();

        prop.put(AvailableSettings.DRIVER, DB_DRIVER);
        prop.put(AvailableSettings.DIALECT, DB_DIALECT);
        prop.put(AvailableSettings.URL, DB_URL);
        prop.put(AvailableSettings.USER, DB_USERNAME);
        prop.put(AvailableSettings.PASS, DB_PASSWORD);

        Configuration config = new Configuration().setProperties(prop)
            .addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(config.getProperties()).build();

        return config.buildSessionFactory(serviceRegistry)
            .openSession();
    }
}
