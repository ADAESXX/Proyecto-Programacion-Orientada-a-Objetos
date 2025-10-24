import BaseDAO from './BaseDAO.js';
import Usuario from './models/Usuario.js';
import Notificaciones from './models/Notificaciones.js';

class NotificacionDAO extends BaseDAO {
    
    // Crear notificación
    async crearNotificacion(notificacion) {
        const receptorId = await this.obtenerIdUsuarioPorCorreo(notificacion.getReceptorN().getCorreo());
        
        if (receptorId === -1) {
            console.error('No se encontró el usuario receptor');
            return false;
        }
        
        const sql = `INSERT INTO notificaciones (receptor_id, mensaje, leido) VALUES (?, ?, ?)`;
        
        return await this.executeUpdate(sql, receptorId, notificacion.getMensaje(), notificacion.isLeido());
    }
    
    // Obtener notificaciones de un usuario
    async obtenerNotificacionesUsuario(correoReceptor) {
        const notificaciones = [];
        const sql = `SELECT n.mensaje, n.leido, n.fecha_creacion,
                           u.nombre, u.correo, u.contrasena, u.ubicacion, u.verificado
                     FROM notificaciones n
                     JOIN usuarios u ON n.receptor_id = u.id
                     WHERE u.correo = ?
                     ORDER BY n.fecha_creacion DESC`;
        
        const rows = await this.executeQuery(sql, correoReceptor);
        
        for (const row of rows) {
            const receptor = new Usuario(
                row.nombre,
                row.correo,
                row.contrasena,
                row.ubicacion
            );
            receptor.setVerificado(row.verificado);
            
            const notificacion = new Notificaciones(receptor, row.mensaje);
            notificacion.marcarLeido(row.leido);
            notificacion.setFechaCreacion(row.fecha_creacion);
            notificaciones.push(notificacion);
        }
        
        return notificaciones;
    }
    
    // Obtener notificaciones no leídas
    async obtenerNotificacionesNoLeidas(correoReceptor) {
        const notificaciones = [];
        const sql = `SELECT n.mensaje, n.leido, n.fecha_creacion,
                           u.nombre, u.correo, u.contrasena, u.ubicacion, u.verificado
                     FROM notificaciones n
                     JOIN usuarios u ON n.receptor_id = u.id
                     WHERE u.correo = ? AND n.leido = FALSE
                     ORDER BY n.fecha_creacion DESC`;
        
        const rows = await this.executeQuery(sql, correoReceptor);
        
        for (const row of rows) {
            const receptor = new Usuario(
                row.nombre,
                row.correo,
                row.contrasena,
                row.ubicacion
            );
            receptor.setVerificado(row.verificado);
            
            const notificacion = new Notificaciones(receptor, row.mensaje);
            notificacion.marcarLeido(row.leido);
            notificacion.setFechaCreacion(row.fecha_creacion);
            notificaciones.push(notificacion);
        }
        
        return notificaciones;
    }
    
    // Marcar notificación como leída
    async marcarComoLeida(correoReceptor, mensaje) {
        const sql = `UPDATE notificaciones n
                     JOIN usuarios u ON n.receptor_id = u.id
                     SET n.leido = TRUE
                     WHERE u.correo = ? AND n.mensaje = ?`;
        
        return await this.executeUpdate(sql, correoReceptor, mensaje);
    }
    
    // Marcar todas las notificaciones como leídas
    async marcarTodasComoLeidas(correoReceptor) {
        const sql = `UPDATE notificaciones n
                     JOIN usuarios u ON n.receptor_id = u.id
                     SET n.leido = TRUE
                     WHERE u.correo = ?`;
        
        return await this.executeUpdate(sql, correoReceptor);
    }
    
    // Contar notificaciones no leídas
    async contarNotificacionesNoLeidas(correoReceptor) {
        const sql = `SELECT COUNT(*) as total
                     FROM notificaciones n
                     JOIN usuarios u ON n.receptor_id = u.id
                     WHERE u.correo = ? AND n.leido = FALSE`;
        
        const row = await this.executeQuerySingle(sql, correoReceptor);
        return row ? row.total : 0;
    }
    
    // Eliminar notificación
    async eliminarNotificacion(correoReceptor, mensaje) {
        const sql = `DELETE n FROM notificaciones n
                     JOIN usuarios u ON n.receptor_id = u.id
                     WHERE u.correo = ? AND n.mensaje = ?`;
        
        return await this.executeUpdate(sql, correoReceptor, mensaje);
    }
    
    // Eliminar todas las notificaciones de un usuario
    async eliminarTodasLasNotificaciones(correoReceptor) {
        const sql = `DELETE n FROM notificaciones n
                     JOIN usuarios u ON n.receptor_id = u.id
                     WHERE u.correo = ?`;
        
        return await this.executeUpdate(sql, correoReceptor);
    }
    
    // Método auxiliar
    async obtenerIdUsuarioPorCorreo(correo) {
        const sql = `SELECT id FROM usuarios WHERE correo = ?`;
        const row = await this.executeQuerySingle(sql, correo);
        
        return row ? row.id : -1;
    }
}

export default NotificacionDAO;
