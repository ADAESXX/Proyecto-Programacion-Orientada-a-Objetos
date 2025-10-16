import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends BaseDAO {

    // Crear usuario base - CORREGIDO para aceptar solo 2 parámetros
    public boolean crearUsuario(Usuario usuario, String tipoUsuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contrasena, ubicacion, verificado, tipo_usuario) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        return executeUpdate(sql, 
            usuario.getNombre(), 
            usuario.getCorreo(), 
            usuario.getContrasena(), 
            usuario.getUbicacion(), 
            usuario.getVerificado(), 
            tipoUsuario
        );
    }

    // Crear estudiante completo (usuario + estudiante + propuestas)
    public boolean crearEstudiante(Estudiante estudiante) {
        Connection conn = getConnection();
        PreparedStatement pstmtUsuario = null;
        PreparedStatement pstmtEstudiante = null;
        PreparedStatement pstmtPropuestas = null;

        try {
            conn.setAutoCommit(false);

            // Insertar usuario
            String sqlUsuario = "INSERT INTO usuarios (nombre, correo, contrasena, ubicacion, verificado, tipo_usuario) "
                              + "VALUES (?, ?, ?, ?, ?, 'Estudiante')";
            pstmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            pstmtUsuario.setString(1, estudiante.getNombre());
            pstmtUsuario.setString(2, estudiante.getCorreo());
            pstmtUsuario.setString(3, estudiante.getContrasena());
            pstmtUsuario.setString(4, estudiante.getUbicacion());
            pstmtUsuario.setBoolean(5, estudiante.getVerificado());
            pstmtUsuario.executeUpdate();

            // Obtener ID generado
            ResultSet rs = pstmtUsuario.getGeneratedKeys();
            int usuarioId;
            if (rs.next()) {
                usuarioId = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener ID del usuario insertado");
            }
            rs.close();

            // Insertar estudiante
            String sqlEstudiante = "INSERT INTO estudiantes (usuario_id, carrera) VALUES (?, ?)";
            pstmtEstudiante = conn.prepareStatement(sqlEstudiante, Statement.RETURN_GENERATED_KEYS);
            pstmtEstudiante.setInt(1, usuarioId);
            pstmtEstudiante.setString(2, estudiante.getCarrera());
            pstmtEstudiante.executeUpdate();

            // Obtener ID del estudiante
            ResultSet rsEst = pstmtEstudiante.getGeneratedKeys();
            int estudianteId;
            if (rsEst.next()) {
                estudianteId = rsEst.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener ID del estudiante insertado");
            }
            rsEst.close();

            // Insertar propuestas del estudiante
            if (!estudiante.getPropuestas().isEmpty()) {
                String sqlPropuestas = "INSERT INTO estudiante_propuestas (estudiante_id, titulo_propuesta) VALUES (?, ?)";
                pstmtPropuestas = conn.prepareStatement(sqlPropuestas);
                
                for (String propuesta : estudiante.getPropuestas()) {
                    pstmtPropuestas.setInt(1, estudianteId);
                    pstmtPropuestas.setString(2, propuesta);
                    pstmtPropuestas.addBatch();
                }
                pstmtPropuestas.executeBatch();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex.getMessage());
            }
            System.err.println("Error al crear estudiante: " + e.getMessage());
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autocommit: " + e.getMessage());
            }
            closeResources(pstmtPropuestas);
            closeResources(pstmtEstudiante);
            closeResources(pstmtUsuario);
        }
    }

    // Obtener usuario por correo
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String sql = "SELECT id, nombre, correo, contrasena, ubicacion, verificado, tipo_usuario "
                   + "FROM usuarios WHERE correo = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, correo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getString("ubicacion")
                );
                usuario.setVerificado(rs.getBoolean("verificado"));
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        String sql = "SELECT nombre, correo, contrasena, ubicacion, verificado, tipo_usuario "
                   + "FROM usuarios ORDER BY nombre";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getString("ubicacion")
                );
                usuario.setVerificado(rs.getBoolean("verificado"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }

        return usuarios;
    }

    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, ubicacion = ?, contrasena = ? WHERE correo = ?";
        return executeUpdate(sql, 
            usuario.getNombre(), 
            usuario.getUbicacion(), 
            usuario.getContrasena(), 
            usuario.getCorreo()
        );
    }

    // Verificar usuario
    public boolean verificarUsuario(String correo) {
        String sql = "UPDATE usuarios SET verificado = TRUE WHERE correo = ?";
        return executeUpdate(sql, correo);
    }

    // Eliminar usuario
    public boolean eliminarUsuario(String correo) {
        String sql = "DELETE FROM usuarios WHERE correo = ?";
        return executeUpdate(sql, correo);
    }

    // Validar login
    public boolean validarLogin(String correo, String contrasena) {
        String sql = "SELECT 1 FROM usuarios WHERE correo = ? AND contrasena = ? LIMIT 1";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);
            rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al validar login: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }

        return false;
    }
}