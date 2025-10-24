import ConexionBD from './ConexionBD.js';

class BaseDAO {
    
    async getConnection() {
        return await ConexionBD.getConnection();
    }
    
    // Ejecutar consulta de actualización (INSERT, UPDATE, DELETE)
    async executeUpdate(sql, ...params) {
        let connection = null;
        try {
            connection = await this.getConnection();
            if (!connection) {
                throw new Error('No se pudo obtener conexión a la base de datos');
            }
            
            const [result] = await connection.execute(sql, params);
            return result.affectedRows > 0;
        } catch (error) {
            console.error('Error en executeUpdate:', error.message);
            return false;
        }
    }
    
    // Ejecutar consulta de selección
    async executeQuery(sql, ...params) {
        let connection = null;
        try {
            connection = await this.getConnection();
            if (!connection) {
                throw new Error('No se pudo obtener conexión a la base de datos');
            }
            
            const [rows] = await connection.execute(sql, params);
            return rows;
        } catch (error) {
            console.error('Error en executeQuery:', error.message);
            return [];
        }
    }
    
    // Ejecutar consulta que devuelve un solo resultado
    async executeQuerySingle(sql, ...params) {
        const rows = await this.executeQuery(sql, ...params);
        return rows.length > 0 ? rows[0] : null;
    }
}

export default BaseDAO;
