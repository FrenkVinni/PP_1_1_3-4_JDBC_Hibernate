package jm.task.core.jdbc.dao;

import java.util.ArrayList;
import jm.task.core.jdbc.model.User;
import java.util.List;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util = new Util();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sqlRequest = "CREATE TABLE IF NOT EXISTS `users` (`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
            + "`Name` VARCHAR(155) NULL,`LastName` VARCHAR(155) NULL,  `Age` INT NULL);";

        try(Session session = util.getConnectionHibernate()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sqlRequest).executeUpdate();
            transaction.commit();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlRequest = "DROP TABLE IF EXISTS Users;";

        try(Session session = util.getConnectionHibernate()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sqlRequest).executeUpdate();
            transaction.commit();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = util.getConnectionHibernate()) {
            User user = new User();

            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = util.getConnectionHibernate()) {
            User user = new User();

            user.setId(id);

            session.delete(user);
            session.beginTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try(Session session = util.getConnectionHibernate()) {
            users = session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String hqlRequest = "DELETE FROM users;";

        try(Session session = util.getConnectionHibernate()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(hqlRequest).executeUpdate();
            transaction.commit();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}
