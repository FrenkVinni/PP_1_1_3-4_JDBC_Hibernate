package jm.task.core.jdbc.service;


import java.sql.SQLException;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;


import java.util.List;

public class UserServiceImpl implements UserService {

    UserDao dao = new UserDaoHibernateImpl();

    public void createUsersTable() {
        dao.createUsersTable();
        System.out.println("Таблица создана!");
    }

    public void dropUsersTable() {
        dao.dropUsersTable();
        System.out.println("Таблица удалена!");
    }

    public void saveUser(String name, String lastName, byte age) {
        dao.saveUser(name, lastName, age);
        System.out.printf("User с именем – %s добавлен в базу данных%n", name);
    }

    public void removeUserById(long id) {
        dao.removeUserById(id);
        System.out.printf("User под id - %d удален из таблицы!%n", id);
    }

    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public void cleanUsersTable() {
        dao.cleanUsersTable();
        System.out.println("Таблица очищена!");
    }
}
