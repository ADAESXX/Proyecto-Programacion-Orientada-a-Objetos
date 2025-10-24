import BaseDAO from './BaseDAO.js';
import Usuario from './models/Usuario.js';
import Propuesta from './models/Propuesta.js';

class PropuestaDAO extends BaseDAO {
    
    // Crear propuesta
    async crearPropuesta(propuesta) {
        // Primero necesitamos obtener el ID del creador
        const creadorId = await this.obtenerIdUsuarioPorCorreo(propuesta.getCreador().getCorreo());
        if (creadorId === -1) {
            console.error('No se encontró el usuario creador');
            return false;
        }
        
        const sql = `INSERT INTO propuestas (titulo, descripcion, creador_id, progreso, meta, recaudado) 
                     VALUES (?, ?, ?, ?, ?, ?)`;
        
        return await this.executeUpdate(sql,
            propuesta.getTitulo(),
            propuesta.getDescripcion(),
            creadorId,
            propuesta.getProgreso(),
            propuesta.getMeta(),
            propuesta.getRecaudado()
        );
    }
    
    // Obtener todas las propuestas
    async obtenerTodasLasPropuestas() {
        const propuestas = [];
        const sql = `SELECT p.id, p.titulo, p.descripcion, p.progreso, p.meta, p.recaudado, p.fecha_creacion,
                           u.nombre, u.correo, u.contrasena, u.ubicacion, u.verificado
                     FROM propuestas p
                     JOIN usuarios u ON p.creador_id = u.id
                     ORDER BY p.fecha_creacion DESC`;
        
        const rows = await this.executeQuery(sql);
        
        for (const row of rows) {
            // Crear usuario creador
            const creador = new Usuario(
                row.nombre,
                row.correo,
                row.contrasena,
                row.ubicacion
            );
            creador.setVerificado(row.verificado);
            
            // Crear propuesta
            const propuesta = new Propuesta(
                row.titulo,
                row.descripcion,
                creador
            );
            propuesta.setProgreso(row.progreso);
            propuesta.setMeta(row.meta);
            propuesta.agregarRecaudacion(row.recaudado);
            propuesta.setFechaCreacion(row.fecha_creacion);
            
            propuestas.push(propuesta);
        }
        
        return propuestas;
    }
    
    // Obtener propuesta por título
    async obtenerPropuestaPorTitulo(titulo) {
        const sql = `SELECT p.id, p.titulo, p.descripcion, p.progreso, p.meta, p.recaudado,
                           u.nombre, u.correo, u.contrasena, u.ubicacion, u.verificado
                     FROM propuestas p
                     JOIN usuarios u ON p.creador_id = u.id
                     WHERE p.titulo = ?`;
        
        const row = await this.executeQuerySingle(sql, titulo);
        
        if (row) {
            const creador = new Usuario(
                row.nombre,
                row.correo,
                row.contrasena,
                row.ubicacion
            );
            creador.setVerificado(row.verificado);
            
            const propuesta = new Propuesta(
                row.titulo,
                row.descripcion,
                creador
            );
            propuesta.setProgreso(row.progreso);
            propuesta.setMeta(row.meta);
            propuesta.agregarRecaudacion(row.recaudado);
            
            return propuesta;
        }
        
        return null;
    }
    
    // Actualizar progreso de propuesta
    async actualizarProgreso(titulo, nuevoProgreso) {
        const sql = `UPDATE propuestas SET progreso = ? WHERE titulo = ?`;
        return await this.executeUpdate(sql, nuevoProgreso, titulo);
    }
    
    // Actualizar meta de propuesta
    async actualizarMeta(titulo, nuevaMeta) {
        const sql = `UPDATE propuestas SET meta = ? WHERE titulo = ?`;
        return await this.executeUpdate(sql, nuevaMeta, titulo);
    }
    
    // Agregar recaudación a propuesta
    async agregarRecaudacion(titulo, cantidad) {
        const sql = `UPDATE propuestas SET recaudado = recaudado + ? WHERE titulo = ?`;
        return await this.executeUpdate(sql, cantidad, titulo);
    }
    
    // Agregar inversionista a propuesta
    async agregarInversionista(tituloPropuesta, correoInversionista, montoInversion) {
        let connection = null;
        try {
            connection = await this.getConnection();
            if (!connection) {
                throw new Error('No se pudo obtener conexión a la base de datos');
            }
            
            // Obtener IDs
            const propuestaId = await this.obtenerIdPropuestaPorTitulo(tituloPropuesta);
            const inversionistaId = await this.obtenerIdInversionistaPorCorreo(correoInversionista);
            
            if (propuestaId === -1 || inversionistaId === -1) {
                return false;
            }
            
            const sql = `INSERT INTO propuesta_inversionistas (propuesta_id, inversionista_id, monto_invertido) 
                         VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE monto_invertido = monto_invertido + ?`;
            
            const [result] = await connection.execute(sql, [propuestaId, inversionistaId, montoInversion, montoInversion]);
            
            return result.affectedRows > 0;
            
        } catch (error) {
            console.error('Error al agregar inversionista:', error.message);
            return false;
        }
    }
    
    // Métodos auxiliares
    async obtenerIdUsuarioPorCorreo(correo) {
        const sql = `SELECT id FROM usuarios WHERE correo = ?`;
        const row = await this.executeQuerySingle(sql, correo);
        
        return row ? row.id : -1;
    }
    
    async obtenerIdPropuestaPorTitulo(titulo) {
        const sql = `SELECT id FROM propuestas WHERE titulo = ?`;
        const row = await this.executeQuerySingle(sql, titulo);
        
        return row ? row.id : -1;
    }
    
    async obtenerIdInversionistaPorCorreo(correo) {
        const sql = `SELECT i.id FROM inversionistas i
                     JOIN usuarios u ON i.usuario_id = u.id
                     WHERE u.correo = ?`;
        const row = await this.executeQuerySingle(sql, correo);
        
        return row ? row.id : -1;
    }
    
    // Eliminar propuesta
    async eliminarPropuesta(titulo) {
        const sql = `DELETE FROM propuestas WHERE titulo = ?`;
        return await this.executeUpdate(sql, titulo);
    }
}

export default PropuestaDAO;
