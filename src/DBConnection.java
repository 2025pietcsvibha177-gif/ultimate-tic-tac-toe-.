import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    // Database credentials and configuration
    private static final String URL = "jdbc:mysql://localhost:3306/tictactoe_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Vibha@11";

    /**
     * Establishes and returns a connection to the MySQL database.
     * @return Connection object if successful, null otherwise.
     */
    public static Connection getConnection() {
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found! Make sure mysql-connector-j dependency is in pom.xml.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("❌ Database Connection Failed! Please check MySQL service, URL, username, and password.");
            System.err.println("Error Code: " + e.getErrorCode() + " | SQLState: " + e.getSQLState() + " | Message: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error while connecting to database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Automatically ensures required tables (players, games) exist in the database.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    // Create players table
                    stmt.execute(
                        "CREATE TABLE IF NOT EXISTS players (" +
                        "  player_id INT NOT NULL AUTO_INCREMENT," +
                        "  player_name VARCHAR(100) NOT NULL," +
                        "  symbol VARCHAR(10) NOT NULL," +
                        "  score INT DEFAULT 0," +
                        "  PRIMARY KEY (player_id)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
                    );

                    // Create games table
                    stmt.execute(
                        "CREATE TABLE IF NOT EXISTS games (" +
                        "  game_id INT NOT NULL AUTO_INCREMENT," +
                        "  player1_id INT NOT NULL," +
                        "  player2_id INT NOT NULL," +
                        "  winner_id INT DEFAULT NULL," +
                        "  game_status VARCHAR(20) NOT NULL," +
                        "  played_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP," +
                        "  PRIMARY KEY (game_id)," +
                        "  CONSTRAINT fk_games_player1 FOREIGN KEY (player1_id) REFERENCES players (player_id) ON DELETE CASCADE," +
                        "  CONSTRAINT fk_games_player2 FOREIGN KEY (player2_id) REFERENCES players (player_id) ON DELETE CASCADE," +
                        "  CONSTRAINT fk_games_winner FOREIGN KEY (winner_id) REFERENCES players (player_id) ON DELETE SET NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
                    );
                    System.out.println("✅ Database tables checked/initialized successfully.");
                }
            }
        } catch (Exception e) {
            System.err.println("Notice: Could not automatically verify tables (MySQL may not be running or credentials differ): " + e.getMessage());
        }
    }

    /**
     * Test utility to verify JDBC connection directly from terminal.
     */
    public static void main(String[] args) {
        System.out.println("Testing MySQL JDBC Connection...");
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("✅ Successfully connected to MySQL database: tictactoe_db!");
            initializeDatabase();
            try {
                conn.close();
                System.out.println("Connection closed safely.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("❌ Connection test failed.");
        }
        // Gracefully shut down MySQL driver cleanup thread for instant CLI exit
        com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
