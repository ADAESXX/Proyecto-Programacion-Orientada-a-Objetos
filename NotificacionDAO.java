import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAO extends BaseDAO {
    
    // Crear notificación
    public boolean crearNotificacion(Notificaciones notificacion) {
        int receptorId = obtenerIdUsuarioPorCorreo(notificacion.getReceptorN().getCorreo());
        
        if (receptorId == -1) {
            System.err.println("No se encontró el usuario receptor");
            return false;
        }
        
        String sql = "INSERT INTO notificaciones (receptor_id, mensaje, leido) VALUES (?, ?, ?)";
        
        return executeUpdate(sql, receptorId, notificacion.getMensaje(), notificacion.isLeido());
    }
    
    // Obtener notificaciones de un usuario
    public List<Notificaciones> obtenerNotificacionesUsuario(String correoReceptor) {
        List<Notificaciones> notificaciones = new ArrayList<>();
        String sql = "SELECT n.mensaje, n.leido, n.fecha_creacion, "
                   + "u.nombre, u.correo, u.contrasena, u.ubicacion, u.verificado "
                   + "FROM notificaciones n "
                   + "JOIN usuarios u ON n.receptor_id = u.id "
                   + "WHERE u.correo = ? "
                   + "ORDER BY n.fecha_creacion DESC";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, correoReceptor);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Usuario receptor = new Usuario(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getString("ubicacion")
                );
                receptor.setVerificado(rs.getBoolean("verificado"));
                
                Notificaciones notificacion = new Notificaciones(receptor, rs.getString("mensaje"));
                notificacion.marcarLeido(rs.getBoolean("leido"));
                notificaciones.add(notificacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener notificaciones: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return notificaciones;
    }
    
    // Obtener notificaciones no leídas
    public List<Notificaciones> obtenerNotificacionesNoLeidas(String correoReceptor) {
        List<Notificaciones> notificaciones = new ArrayList<>();
        String sql = "SELECT n.mensaje, n.leido, n.fecha_creacion, "
                   + "u.nombre, u.correo, u.contrasena, u.ubicacion, u.verificado "
                   + "FROM notificaciones n "
                   + "JOIN usuarios u ON n.receptor_id = u.id "
                   + "WHERE u.correo = ? AND n.leido = FALSE "
                   + "ORDER BY n.fecha_creacion DESC";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, correoReceptor);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Usuario receptor = new Usuario(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getString("ubicacion")
                );
                receptor.setVerificado(rs.getBoolean("verificado"));
                
                Notificaciones notificacion = new Notificaciones(receptor, rs.getString("mensaje"));
                notificacion.marcarLeido(rs.getBoolean("leido"));
                notificaciones.add(notificacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener notificaciones no leídas: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return notificaciones;
    }
    
    // Marcar notificación como leída
    public boolean marcarComoLeida(String correoReceptor, String mensaje) {
        String sql = "UPDATE notificaciones n "
                   + "JOIN usuarios u ON n.receptor_id = u.id "
                   + "SET n.leido = TRUE "
                   + "WHERE u.correo = ? AND n.mensaje = ?";
        
        return executeUpdate(sql, correoReceptor, mensaje);
    }
    
    // Marcar todas las notificaciones como leídas
    public boolean marcarTodasComoLeidas(String correoReceptor) {
        String sql = "UPDATE notificaciones n "
                   + "JOIN usuarios u ON n.receptor_id = u.id "
                   + "SET n.leido = TRUE "
                   + "WHERE u.correo = ?";
        
        return executeUpdate(sql, correoReceptor);
    }
    
    // Contar notificaciones no leídas
    public int contarNotificacionesNoLeidas(String correoReceptor) {
        String sql = "SELECT COUNT(*) as total "
                   + "FROM notificaciones n "
                   + "JOIN usuarios u ON n.receptor_id = u.id "
                   + "WHERE u.correo = ? AND n.leido = FALSE";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, correoReceptor);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al contar notificaciones: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return 0;
    }
    
    // Eliminar notificación
    public boolean eliminarNotificacion(String correoReceptor, String mensaje) {
        String sql = "DELETE n FROM notificaciones n "
                   + "JOIN usuarios u ON n.receptor_id = u.id "
                   + "WHERE u.correo = ? AND n.mensaje = ?";
        
        return executeUpdate(sql, correoReceptor, mensaje);
    }
    
    // Eliminar todas las notificaciones de un usuario
    public boolean eliminarTodasLasNotificaciones(String correoReceptor) {
        String sql = "DELETE n FROM notificaciones n "
                   + "JOIN usuarios u ON n.receptor_id = u.id "
                   + "WHERE u.correo = ?";
        
        return executeUpdate(sql, correoReceptor);
    }
    
    // Método auxiliar
    private int obtenerIdUsuarioPorCorreo(String correo) {
        String sql = "SELECT id FROM usuarios WHERE correo = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, correo);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID de usuario: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return -1;
    }
}
