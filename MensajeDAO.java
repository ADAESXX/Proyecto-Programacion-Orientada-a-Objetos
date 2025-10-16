import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MensajeDAO extends BaseDAO {
    
    // Enviar mensaje
    public boolean enviarMensaje(Mensaje mensaje) {
        int emisorId = obtenerIdUsuarioPorCorreo(mensaje.getEmisor().getCorreo());
        int receptorId = obtenerIdUsuarioPorCorreo(mensaje.getReceptor().getCorreo());
        
        if (emisorId == -1 || receptorId == -1) {
            System.err.println("No se encontraron los usuarios emisor o receptor");
            return false;
        }
        
        String sql = "INSERT INTO mensajes (emisor_id, receptor_id, contenido) VALUES (?, ?, ?)";
        
        return executeUpdate(sql, emisorId, receptorId, mensaje.getContenido());
    }
    
    // Obtener mensajes entre dos usuarios
    public List<Mensaje> obtenerMensajesEntreUsuarios(String correoUsuario1, String correoUsuario2) {
        List<Mensaje> mensajes = new ArrayList<>();
        String sql = "SELECT m.contenido, m.fecha_envio, "
                   + "e.nombre as emisor_nombre, e.correo as emisor_correo, "
                   + "e.contrasena as emisor_pass, e.ubicacion as emisor_ubicacion, "
                   + "r.nombre as receptor_nombre, r.correo as receptor_correo, "
                   + "r.contrasena as receptor_pass, r.ubicacion as receptor_ubicacion "
                   + "FROM mensajes m "
                   + "JOIN usuarios e ON m.emisor_id = e.id "
                   + "JOIN usuarios r ON m.receptor_id = r.id "
                   + "WHERE (e.correo = ? AND r.correo = ?) OR (e.correo = ? AND r.correo = ?) "
                   + "ORDER BY m.fecha_envio ASC";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, correoUsuario1);
            pstmt.setString(2, correoUsuario2);
            pstmt.setString(3, correoUsuario2);
            pstmt.setString(4, correoUsuario1);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Usuario emisor = new Usuario(
                    rs.getString("emisor_nombre"),
                    rs.getString("emisor_correo"),
                    rs.getString("emisor_pass"),
                    rs.getString("emisor_ubicacion")
                );
                
                Usuario receptor = new Usuario(
                    rs.getString("receptor_nombre"),
                    rs.getString("receptor_correo"),
                    rs.getString("receptor_pass"),
                    rs.getString("receptor_ubicacion")
                );
                
                Mensaje mensaje = new Mensaje(emisor, receptor, rs.getString("contenido"));
                mensajes.add(mensaje);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mensajes: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return mensajes;
    }
    
    // Obtener mensajes recibidos por un usuario
    public List<Mensaje> obtenerMensajesRecibidos(String correoReceptor) {
        List<Mensaje> mensajes = new ArrayList<>();
        String sql = "SELECT m.contenido, m.fecha_envio, "
                   + "e.nombre as emisor_nombre, e.correo as emisor_correo, "
                   + "e.contrasena as emisor_pass, e.ubicacion as emisor_ubicacion, "
                   + "r.nombre as receptor_nombre, r.correo as receptor_correo, "
                   + "r.contrasena as receptor_pass, r.ubicacion as receptor_ubicacion "
                   + "FROM mensajes m "
                   + "JOIN usuarios e ON m.emisor_id = e.id "
                   + "JOIN usuarios r ON m.receptor_id = r.id "
                   + "WHERE r.correo = ? "
                   + "ORDER BY m.fecha_envio DESC";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, correoReceptor);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Usuario emisor = new Usuario(
                    rs.getString("emisor_nombre"),
                    rs.getString("emisor_correo"),
                    rs.getString("emisor_pass"),
                    rs.getString("emisor_ubicacion")
                );
                
                Usuario receptor = new Usuario(
                    rs.getString("receptor_nombre"),
                    rs.getString("receptor_correo"),
                    rs.getString("receptor_pass"),
                    rs.getString("receptor_ubicacion")
                );
                
                Mensaje mensaje = new Mensaje(emisor, receptor, rs.getString("contenido"));
                mensajes.add(mensaje);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mensajes recibidos: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return mensajes;
    }
    
    // Obtener mensajes enviados por un usuario
    public List<Mensaje> obtenerMensajesEnviados(String correoEmisor) {
        List<Mensaje> mensajes = new ArrayList<>();
        String sql = "SELECT m.contenido, m.fecha_envio, "
                   + "e.nombre as emisor_nombre, e.correo as emisor_correo, "
                   + "e.contrasena as emisor_pass, e.ubicacion as emisor_ubicacion, "
                   + "r.nombre as receptor_nombre, r.correo as receptor_correo, "
                   + "r.contrasena as receptor_pass, r.ubicacion as receptor_ubicacion "
                   + "FROM mensajes m "
                   + "JOIN usuarios e ON m.emisor_id = e.id "
                   + "JOIN usuarios r ON m.receptor_id = r.id "
                   + "WHERE e.correo = ? "
                   + "ORDER BY m.fecha_envio DESC";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, correoEmisor);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Usuario emisor = new Usuario(
                    rs.getString("emisor_nombre"),
                    rs.getString("emisor_correo"),
                    rs.getString("emisor_pass"),
                    rs.getString("emisor_ubicacion")
                );
                
                Usuario receptor = new Usuario(
                    rs.getString("receptor_nombre"),
                    rs.getString("receptor_correo"),
                    rs.getString("receptor_pass"),
                    rs.getString("receptor_ubicacion")
                );
                
                Mensaje mensaje = new Mensaje(emisor, receptor, rs.getString("contenido"));
                mensajes.add(mensaje);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mensajes enviados: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return mensajes;
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
    
    // Eliminar mensajes entre usuarios
    public boolean eliminarMensajesEntreUsuarios(String correoUsuario1, String correoUsuario2) {
        String sql = "DELETE m FROM mensajes m "
                   + "JOIN usuarios e ON m.emisor_id = e.id "
                   + "JOIN usuarios r ON m.receptor_id = r.id "
                   + "WHERE (e.correo = ? AND r.correo = ?) OR (e.correo = ? AND r.correo = ?)";
        
        return executeUpdate(sql, correoUsuario1, correoUsuario2, correoUsuario2, correoUsuario1);
    }
}
