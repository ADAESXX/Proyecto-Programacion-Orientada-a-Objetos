 import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/plataforma_apoyo";
    private static final String USER = "plataforma_user";
    private static final String PASSWORD = "miclave123";

    private static Connection conn = null;

    // Conectar
    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conectado a la base de datos.");
            } catch (SQLException e) {
                System.out.println("❌ Error de conexión: " + e.getMessage());
            }
        }
        return conn;
    }
}
