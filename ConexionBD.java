import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Datos de conexión (ajusta según tu configuración)
    private static final String URL = "jdbc:mysql://localhost:3306/plataforma_apoyo";
    private static final String USER = "root";
    private static final String PASSWORD = "MyNewPass1";

    // Objeto conexión
    private static Connection conn = null;

    // Obtener conexión
    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conectado a la base de datos.");
            } catch (SQLException e) {
                System.out.println("Error de conexión: " + e.getMessage());
            }
        }
        return conn;
    }

    // Probar conexión
    public static boolean probarConexion() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                return !connection.isClosed();
            } catch (SQLException e) {
                System.out.println("Error al verificar el estado de la conexión: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("No se pudo establecer la conexión.");
            return false;
        }
    }

    // Cerrar conexión
    public static void cerrarConexion() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null; // importante para futuras conexiones
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

