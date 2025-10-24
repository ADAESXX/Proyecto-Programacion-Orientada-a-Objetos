import BaseDAO from './BaseDAO.js';
import Usuario from './models/Usuario.js';
import Mensaje from './models/Mensaje.js';

class MensajeDAO extends BaseDAO {
    
    // Enviar mensaje
    async enviarMensaje(mensaje) {
        const emisorId = await this.obtenerIdUsuarioPorCorreo(mensaje.getEmisor().getCorreo());
        const receptorId = await this.obtenerIdUsuarioPorCorreo(mensaje.getReceptor().getCorreo());
        
        if (emisorId === -1 || receptorId === -1) {
            console.error('No se encontraron los usuarios emisor o receptor');
            return false;
        }
        
        const sql = `INSERT INTO mensajes (emisor_id, receptor_id, contenido) VALUES (?, ?, ?)`;
        
        return await this.executeUpdate(sql, emisorId, receptorId, mensaje.getContenido());
    }
    
    // Obtener mensajes entre dos usuarios
    async obtenerMensajesEntreUsuarios(correoUsuario1, correoUsuario2) {
        const mensajes = [];
        const sql = `SELECT m.contenido, m.fecha_envio,
                           e.nombre as emisor_nombre, e.correo as emisor_correo,
                           e.contrasena as emisor_pass, e.ubicacion as emisor_ubicacion,
                           r.nombre as receptor_nombre, r.correo as receptor_correo,
                           r.contrasena as receptor_pass, r.ubicacion as receptor_ubicacion
                     FROM mensajes m
                     JOIN usuarios e ON m.emisor_id = e.id
                     JOIN usuarios r ON m.receptor_id = r.id
                     WHERE (e.correo = ? AND r.correo = ?) OR (e.correo = ? AND r.correo = ?)
                     ORDER BY m.fecha_envio ASC`;
        
        const rows = await this.executeQuery(sql, correoUsuario1, correoUsuario2, correoUsuario2, correoUsuario1);
        
        for (const row of rows) {
            const emisor = new Usuario(
                row.emisor_nombre,
                row.emisor_correo,
                row.emisor_pass,
                row.emisor_ubicacion
            );
            
            const receptor = new Usuario(
                row.receptor_nombre,
                row.receptor_correo,
                row.receptor_pass,
                row.receptor_ubicacion
            );
            
            const mensaje = new Mensaje(emisor, receptor, row.contenido);
            mensaje.setFechaEnvio(row.fecha_envio);
            mensajes.push(mensaje);
        }
        
        return mensajes;
    }
    
    // Obtener mensajes recibidos por un usuario
    async obtenerMensajesRecibidos(correoReceptor) {
        const mensajes = [];
        const sql = `SELECT m.contenido, m.fecha_envio,
                           e.nombre as emisor_nombre, e.correo as emisor_correo,
                           e.contrasena as emisor_pass, e.ubicacion as emisor_ubicacion,
                           r.nombre as receptor_nombre, r.correo as receptor_correo,
                           r.contrasena as receptor_pass, r.ubicacion as receptor_ubicacion
                     FROM mensajes m
                     JOIN usuarios e ON m.emisor_id = e.id
                     JOIN usuarios r ON m.receptor_id = r.id
                     WHERE r.correo = ?
                     ORDER BY m.fecha_envio DESC`;
        
        const rows = await this.executeQuery(sql, correoReceptor);
        
        for (const row of rows) {
            const emisor = new Usuario(
                row.emisor_nombre,
                row.emisor_correo,
                row.emisor_pass,
                row.emisor_ubicacion
            );
            
            const receptor = new Usuario(
                row.receptor_nombre,
                row.receptor_correo,
                row.receptor_pass,
                row.receptor_ubicacion
            );
            
            const mensaje = new Mensaje(emisor, receptor, row.contenido);
            mensaje.setFechaEnvio(row.fecha_envio);
            mensajes.push(mensaje);
        }
        
        return mensajes;
    }
    
    // Obtener mensajes enviados por un usuario
    async obtenerMensajesEnviados(correoEmisor) {
        const mensajes = [];
        const sql = `SELECT m.contenido, m.fecha_envio,
                           e.nombre as emisor_nombre, e.correo as emisor_correo,
                           e.contrasena as emisor_pass, e.ubicacion as emisor_ubicacion,
                           r.nombre as receptor_nombre, r.correo as receptor_correo,
                           r.contrasena as receptor_pass, r.ubicacion as receptor_ubicacion
                     FROM mensajes m
                     JOIN usuarios e ON m.emisor_id = e.id
                     JOIN usuarios r ON m.receptor_id = r.id
                     WHERE e.correo = ?
                     ORDER BY m.fecha_envio DESC`;
        
        const rows = await this.executeQuery(sql, correoEmisor);
        
        for (const row of rows) {
            const emisor = new Usuario(
                row.emisor_nombre,
                row.emisor_correo,
                row.emisor_pass,
                row.emisor_ubicacion
            );
            
            const receptor = new Usuario(
                row.receptor_nombre,
                row.receptor_correo,
                row.receptor_pass,
                row.receptor_ubicacion
            );
            
            const mensaje = new Mensaje(emisor, receptor, row.contenido);
            mensaje.setFechaEnvio(row.fecha_envio);
            mensajes.push(mensaje);
        }
        
        return mensajes;
    }
    
    // Método auxiliar
    async obtenerIdUsuarioPorCorreo(correo) {
        const sql = `SELECT id FROM usuarios WHERE correo = ?`;
        const row = await this.executeQuerySingle(sql, correo);
        
        return row ? row.id : -1;
    }
    
    // Eliminar mensajes entre usuarios
    async eliminarMensajesEntreUsuarios(correoUsuario1, correoUsuario2) {
        const sql = `DELETE m FROM mensajes m
                     JOIN usuarios e ON m.emisor_id = e.id
                     JOIN usuarios r ON m.receptor_id = r.id
                     WHERE (e.correo = ? AND r.correo = ?) OR (e.correo = ? AND r.correo = ?)`;
        
        return await this.executeUpdate(sql, correoUsuario1, correoUsuario2, correoUsuario2, correoUsuario1);
    }
}

export default MensajeDAO;
