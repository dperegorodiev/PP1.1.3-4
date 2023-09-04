package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement())  {
            statement.execute("CREATE TABLE IF NOT EXISTS bddima" +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
            System.out.println("TABLE OK");


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("TABLE NO OK");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS bddima");
        } catch (SQLException e) {
            System.out.println("DROP OK");
            e.printStackTrace();
            System.out.println("DROP NO OK");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bddima (name, " +
                "last_name, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("saveUser OK");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("saveUser NO OK");

        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM bddima WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("REMOVE ON ID OK");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("REMOVE ON ID NO OK");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM bddima")) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
                System.out.println("getAllUsers OK");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAllUsers NO OK");
        }


        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE bddima");
            System.out.println("cleanUsersTable OK");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("cleanUsersTable NO OK");
        }
    }
}
