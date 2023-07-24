package jm.task.core.jdbc;

import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        service.createUsersTable();

        service.saveUser("Анатолий", "Логов", (byte) 26);
        service.saveUser("Вячеслав", "Ладо", (byte) 22);
        service.saveUser("Нина", "Файва", (byte) 51);
        service.saveUser("Игорь", "Вата", (byte) 45);

        List<User> userList = service.getAllUsers();
        userList.stream().forEach(System.out::println);

        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
