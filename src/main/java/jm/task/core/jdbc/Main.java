package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Dadashev", "Sabir", (byte) 29);
        userService.saveUser("Dadashev", "Sabir", (byte) 29);
        userService.saveUser("Dadashev", "Sabir", (byte) 29);
        userService.saveUser("Dadashev", "Sabir", (byte) 29);
        System.out.println( userService.getAllUsers());
        userService.removeUserById(2);
        System.out.println( userService.getAllUsers());
        userService.cleanUsersTable();




    }
}
