import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Initialize/verify database tables in the background
        new Thread(DBConnection::initializeDatabase).start();

        SwingUtilities.invokeLater(() -> {
            new TicTacToe().setVisible(true);
        });
    }
}