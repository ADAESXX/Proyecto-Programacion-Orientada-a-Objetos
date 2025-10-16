import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropuestaDAO extends BaseDAO {
    
    // Crear propuesta
    public boolean crearPropuesta(Propuesta propuesta) {
        // Primero necesitamos obtener el ID del creador
        int creadorId = obtenerIdUsuarioPorCorreo(propuesta.getCreador().getCorreo());
        if (creadorId == -1) {
            System.err.println("No se encontró el usuario creador");
            return false;
        }
        
        String sql = "INSERT INTO propuestas (titulo, descripcion, creador_id, progreso, meta, recaudado) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        return executeUpdate(sql,
            propuesta.getTitulo(),
            propuesta.getDescripcion(),
            creadorId,
            propuesta.getProgreso(),
            propuesta.getMeta(),
            propuesta.getRecaudado()
        );
    }
    
    // Obtener todas las propuestas
    public List<Propuesta> obtenerTodasLasPropuestas() {
        List<Propuesta> propuestas = new ArrayList<>();
        String sql = "SELECT p.id, p.titulo, p.descripcion, p.progreso, p.meta, p.recaudado, p.fecha_creacion, "
                   + "u.nombre, u.correo, u.contrasena, u.ubicacion, u.verificado "
                   + "FROM propuestas p "
                   + "JOIN usuarios u ON p.creador_id = u.id "
                   + "ORDER BY p.fecha_creacion DESC";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                // Crear usuario creador
                Usuario creador = new Usuario(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getString("ubicacion")
                );
                creador.setVerificado(rs.getBoolean("verificado"));
                
                // Crear propuesta
                Propuesta propuesta = new Propuesta(
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    creador
                );
                propuesta.setProgreso(rs.getFloat("progreso"));
                propuesta.setMeta(rs.getDouble("meta"));
                propuesta.agregarRecaudacion(rs.getDouble("recaudado"));
                
                propuestas.add(propuesta);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener propuestas: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return propuestas;
    }
    
    // Obtener propuesta por título
    public Propuesta obtenerPropuestaPorTitulo(String titulo) {
        String sql = "SELECT p.id, p.titulo, p.descripcion, p.progreso, p.meta, p.recaudado, "
                   + "u.nombre, u.correo, u.contrasena, u.ubicacion, u.verificado "
                   + "FROM propuestas p "
                   + "JOIN usuarios u ON p.creador_id = u.id "
                   + "WHERE p.titulo = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, titulo);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Usuario creador = new Usuario(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getString("ubicacion")
                );
                creador.setVerificado(rs.getBoolean("verificado"));
                
                Propuesta propuesta = new Propuesta(
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    creador
                );
                propuesta.setProgreso(rs.getFloat("progreso"));
                propuesta.setMeta(rs.getDouble("meta"));
                propuesta.agregarRecaudacion(rs.getDouble("recaudado"));
                
                return propuesta;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener propuesta: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return null;
    }
    
    // Actualizar progreso de propuesta
    public boolean actualizarProgreso(String titulo, float nuevoProgreso) {
        String sql = "UPDATE propuestas SET progreso = ? WHERE titulo = ?";
        return executeUpdate(sql, nuevoProgreso, titulo);
    }
    
    // Actualizar meta de propuesta
    public boolean actualizarMeta(String titulo, double nuevaMeta) {
        String sql = "UPDATE propuestas SET meta = ? WHERE titulo = ?";
        return executeUpdate(sql, nuevaMeta, titulo);
    }
    
    // Agregar recaudación a propuesta
    public boolean agregarRecaudacion(String titulo, double cantidad) {
        String sql = "UPDATE propuestas SET recaudado = recaudado + ? WHERE titulo = ?";
        return executeUpdate(sql, cantidad, titulo);
    }
    
    // Agregar inversionista a propuesta
    public boolean agregarInversionista(String tituloPropuesta, String correoInversionista, double montoInversion) {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        
        try {
            // Obtener IDs
            int propuestaId = obtenerIdPropuestaPorTitulo(tituloPropuesta);
            int inversionistaId = obtenerIdInversionistaPorCorreo(correoInversionista);
            
            if (propuestaId == -1 || inversionistaId == -1) {
                return false;
            }
            
            String sql = "INSERT INTO propuesta_inversionistas (propuesta_id, inversionista_id, monto_invertido) "
                       + "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE monto_invertido = monto_invertido + ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, propuestaId);
            pstmt.setInt(2, inversionistaId);
            pstmt.setDouble(3, montoInversion);
            pstmt.setDouble(4, montoInversion);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al agregar inversionista: " + e.getMessage());
            return false;
        } finally {
            closeResources(pstmt);
        }
    }
    
    // Métodos auxiliares
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
    
    private int obtenerIdPropuestaPorTitulo(String titulo) {
        String sql = "SELECT id FROM propuestas WHERE titulo = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, titulo);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID de propuesta: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return -1;
    }
    
    private int obtenerIdInversionistaPorCorreo(String correo) {
        String sql = "SELECT i.id FROM inversionistas i "
                   + "JOIN usuarios u ON i.usuario_id = u.id "
                   + "WHERE u.correo = ?";
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
            System.err.println("Error al obtener ID de inversionista: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
        
        return -1;
    }
    
    // Eliminar propuesta
    public boolean eliminarPropuesta(String titulo) {
        String sql = "DELETE FROM propuestas WHERE titulo = ?";
        return executeUpdate(sql, titulo);
    }
}
