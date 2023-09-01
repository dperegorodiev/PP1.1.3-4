package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

        private static final String URL = "jdbc:mysql://localhost/bddima";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "root";

        public static Connection getConnection() {
            Connection connection = null;
            try {
                Driver driver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("connection ok");
            } catch (SQLException e) {
                System.out.println("ne ok");
            }
            return connection;
        }
}
