import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcInsertExample {

    // Change these to match your MySQL setup
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        String createTableSql =
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL)";

        String insertSql = "INSERT INTO users (name, email) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {

            // Create the table if it does not exist
            try (PreparedStatement createStmt = connection.prepareStatement(createTableSql)) {
                createStmt.executeUpdate();
            }

            // Insert the two records using the same PreparedStatement
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                insertStmt.setString(1, "John Doe");
                insertStmt.setString(2, "john.doe@example.com");
                insertStmt.executeUpdate();

                insertStmt.setString(1, "Jane Doe");
                insertStmt.setString(2, "jane.doe@example.com");
                insertStmt.executeUpdate();
            }

            System.out.println("Data inserted successfully.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
