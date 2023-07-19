package jm.task.core.jdbc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import jm.task.core.jdbc.model.User;
import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private final Util connection = new Util();

    public UserDaoJDBCImpl() { }

    public void createUsersTable() {
        String sqlRequest = "CREATE TABLE IF NOT EXISTS `users` (`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
            + "`Name` VARCHAR(155) NULL,`LastName` VARCHAR(155) NULL,  `Age` INT NULL);";

        try (Connection connect = connection.getConnectionJDBC();
            PreparedStatement statement = connect.prepareStatement(sqlRequest)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sqlRequest = "DROP TABLE IF EXISTS Users;";

        try (Connection connect = connection.getConnectionJDBC();
            PreparedStatement statement = connect.prepareStatement(sqlRequest)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlRequest = "INSERT INTO users(`Name`, `LastName`, `Age`) Values (?, ?, ?);";

        try (Connection connect = connection.getConnectionJDBC();
            PreparedStatement statement = connect.prepareStatement(sqlRequest)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("User добавлен в БД!");
    }

    public void removeUserById(long id) {
        String sqlRequest = "DELETE FROM users WHERE id = ?;";

        try (Connection connect = connection.getConnectionJDBC();
            PreparedStatement statement = connect.prepareStatement(sqlRequest)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Пользователь удален!");
    }

    public List<User> getAllUsers() {
        String sqlRequest = "SELECT id, Name, LastName, Age FROM users;";
        List<User> users = new ArrayList<>();

        try (Connection connect = connection.getConnectionJDBC();
            Statement statement = connect.createStatement();) {
            ResultSet resultQuery = statement.executeQuery(sqlRequest);

            while (resultQuery.next()) {
                User user = new User();
                user.setId(resultQuery.getLong("id"));
                user.setName(resultQuery.getString("Name"));
                user.setLastName(resultQuery.getString("LastName"));
                user.setAge(resultQuery.getByte("Age"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        String sqlRequest = "DELETE FROM users;";

        try (Connection connect = connection.getConnectionJDBC();
            PreparedStatement statement = connect.prepareStatement(sqlRequest)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Таблица очищена!");
    }
}
