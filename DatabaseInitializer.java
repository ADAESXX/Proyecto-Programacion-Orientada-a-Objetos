import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        Connection conn = ConexionBD.getConnection();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                
                System.out.println("Creando tablas en la base de datos...");
                
                // Crear tabla usuarios
                String createUsuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "nombre VARCHAR(100) NOT NULL, "
                        + "correo VARCHAR(100) UNIQUE NOT NULL, "
                        + "contrasena VARCHAR(255) NOT NULL, "
                        + "ubicacion VARCHAR(100) NOT NULL, "
                        + "verificado BOOLEAN DEFAULT FALSE, "
                        + "tipo_usuario ENUM('Estudiante', 'Emprendedor', 'Inversionista', 'Donante', 'Voluntario') NOT NULL, "
                        + "fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                        + ")";
                stmt.executeUpdate(createUsuarios);
                System.out.println("✓ Tabla 'usuarios' creada");
                
                // Crear tabla estudiantes
                String createEstudiantes = "CREATE TABLE IF NOT EXISTS estudiantes ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "usuario_id INT NOT NULL, "
                        + "carrera VARCHAR(100) NOT NULL, "
                        + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(createEstudiantes);
                System.out.println("✓ Tabla 'estudiantes' creada");
                
                // Crear tabla emprendedores
                String createEmprendedores = "CREATE TABLE IF NOT EXISTS emprendedores ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "usuario_id INT NOT NULL, "
                        + "nombre_proyecto VARCHAR(200) NOT NULL, "
                        + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(createEmprendedores);
                System.out.println("✓ Tabla 'emprendedores' creada");
                
                // Crear tabla inversionistas
                String createInversionistas = "CREATE TABLE IF NOT EXISTS inversionistas ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "usuario_id INT NOT NULL, "
                        + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(createInversionistas);
                System.out.println("✓ Tabla 'inversionistas' creada");
                
                // Crear tabla donantes
                String createDonantes = "CREATE TABLE IF NOT EXISTS donantes ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "usuario_id INT NOT NULL, "
                        + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(createDonantes);
                System.out.println("✓ Tabla 'donantes' creada");
                
                // Crear tabla voluntarios
                String createVoluntarios = "CREATE TABLE IF NOT EXISTS voluntarios ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "usuario_id INT NOT NULL, "
                        + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(createVoluntarios);
                System.out.println("✓ Tabla 'voluntarios' creada");
                
                // Crear tabla propuestas
                String createPropuestas = "CREATE TABLE IF NOT EXISTS propuestas ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "titulo VARCHAR(200) NOT NULL, "
                        + "descripcion TEXT NOT NULL, "
                        + "creador_id INT NOT NULL, "
                        + "progreso FLOAT DEFAULT 0, "
                        + "meta DOUBLE DEFAULT 0, "
                        + "recaudado DOUBLE DEFAULT 0, "
                        + "fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                        + "FOREIGN KEY (creador_id) REFERENCES usuarios(id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(createPropuestas);
                System.out.println("✓ Tabla 'propuestas' creada");
                
                // Crear tabla mensajes
                String createMensajes = "CREATE TABLE IF NOT EXISTS mensajes ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "emisor_id INT NOT NULL, "
                        + "receptor_id INT NOT NULL, "
                        + "contenido TEXT NOT NULL, "
                        + "fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                        + "FOREIGN KEY (emisor_id) REFERENCES usuarios(id) ON DELETE CASCADE, "
                        + "FOREIGN KEY (receptor_id) REFERENCES usuarios(id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(createMensajes);
                System.out.println("✓ Tabla 'mensajes' creada");
                
                // Crear tabla notificaciones
                String createNotificaciones = "CREATE TABLE IF NOT EXISTS notificaciones ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "receptor_id INT NOT NULL, "
                        + "mensaje TEXT NOT NULL, "
                        + "leido BOOLEAN DEFAULT FALSE, "
                        + "fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                        + "FOREIGN KEY (receptor_id) REFERENCES usuarios(id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(createNotificaciones);
                System.out.println("✓ Tabla 'notificaciones' creada");

                // Agregar después de la línea 114 en DatabaseInitializer.java
                String createEstudiantePropuestas = "CREATE TABLE IF NOT EXISTS estudiante_propuestas ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "estudiante_id INT NOT NULL, "
                        + "titulo_propuesta VARCHAR(200) NOT NULL, "
                        + "FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE"
                        + ")";
                stmt.executeUpdate(createEstudiantePropuestas);
                System.out.println("✓ Tabla 'estudiante_propuestas' creada");

                
                // Aquí seguirías con las demás tablas de relaciones y listas usando la misma técnica
                // ... (concatenar cadenas normales en lugar de comillas triples)
                
                System.out.println("\n ¡Todas las tablas han sido creadas exitosamente!");
                
            } catch (SQLException e) {
                System.err.println("Error al crear las tablas: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("No se pudo obtener conexión a la base de datos");
        }
    }
}
