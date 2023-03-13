package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection;

    public UserDaoJDBCImpl() {
        connection = new Util().getConnection();
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user(id INT  PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45),age TINYINT)");
            System.out.println("Таблица создана");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Не удалось востановить данные");
            }
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            connection.commit();
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Не удалось востановить данные");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name , lastName,age) VALUES (? , ?,?)")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {

            System.out.println("Не удалось добавить запись");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Не удалось востановить данные");
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE  from user where id = ?")) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
            System.out.println("Пользователь удален");
        } catch (SQLException e) {
            System.out.println("Пользователь не удален");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Не удалось востановить данные");
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from user");
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }

            System.out.println("Лист создан");
        } catch (SQLException e) {
            System.out.println("Лист не создан");
        }

        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE  from user ");
            System.out.println("Пользователи удален");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Пользователи не удален");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Не удалось востановить данные");
            }
        }

    }
}
