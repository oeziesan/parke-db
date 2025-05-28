import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
        //login
        private static final String DB_URL = "jdbc:mysql://localhost:3306/parkingsys";
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "255.255.255.0Fa";

        private static Connection connection;

        public static Connection getConnection () throws SQLException {
            if (connection == null || connection.isClosed()) {
                try {
                    connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    System.out.println("✅ Database connected successfully.");
                } catch (SQLException e) {
                    System.err.println("❌ Database connection failed: " + e.getMessage());
                    throw e;
                }
            }
            return connection;
        }

        public static void closeConnection () {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("🔒 Database connection closed.");
                }
            } catch (SQLException e) {
                System.err.println("⚠️ Failed to close connection: " + e.getMessage());
            }
        }
    }
