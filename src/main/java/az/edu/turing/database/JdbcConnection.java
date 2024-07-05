package az.edu.turing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Aybeniz2021";
    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
