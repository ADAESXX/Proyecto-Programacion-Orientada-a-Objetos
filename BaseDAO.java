import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {
    
    protected Connection getConnection() {
        return ConexionBD.getConnection();
    }
    
    protected void closeResources(ResultSet rs, PreparedStatement pstmt) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
    
    protected void closeResources(PreparedStatement pstmt) {
        closeResources(null, pstmt);
    }
    
    protected boolean executeUpdate(String sql, Object... params) {
        PreparedStatement pstmt = null;
        try {
            pstmt = getConnection().prepareStatement(sql);
            setParameters(pstmt, params);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en executeUpdate: " + e.getMessage());
            return false;
        } finally {
            closeResources(pstmt);
        }
    }
    
    protected void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }
}
