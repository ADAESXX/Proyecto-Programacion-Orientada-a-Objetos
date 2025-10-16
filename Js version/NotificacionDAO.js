// models/dao/NotificacionDAO.js
import BaseDAO from "./BaseDAO.js";
import Notificacion from "../Notificacion.js";
import Usuario from "../Usuario.js";

export default class NotificacionDAO extends BaseDAO {

  async crearNotificacion(notificacion) {
    const receptor = notificacion.usuario;
    const receptorId = await this.obtenerIdUsuarioPorCorreo(receptor.correo);

    if (!receptorId) {
      console.error("No se encontró el usuario receptor");
      return false;
    }

    const sql = `
      INSERT INTO notificaciones (receptor_id, mensaje, leido)
      VALUES (?, ?, ?)
    `;
    return this.executeUpdate(sql, [receptorId, notificacion.mensaje, notificacion.leida]);
  }

  async obtenerNotificacionesUsuario(correoReceptor) {
    const sql = `
      SELECT n.id, n.mensaje, n.leido, n.fecha_creacion,
             u.id as user_id, u.nombre, u.correo, u.contrasena, u.tipoUsuario
      FROM notificaciones n
      JOIN usuarios u ON n.receptor_id = u.id
      WHERE u.correo = ?
      ORDER BY n.fecha_creacion DESC
    `;
    const rows = await this.query(sql, [correoReceptor]);
    return rows.map(r => {
      const usuario = new Usuario(r.user_id, r.nombre, r.correo, r.contrasena, r.tipoUsuario);
      return new Notificacion(r.id, usuario, r.mensaje, r.fecha_creacion, r.leido);
    });
  }

  async obtenerNotificacionesNoLeidas(correoReceptor) {
    const sql = `
      SELECT n.id, n.mensaje, n.leido, n.fecha_creacion,
             u.id as user_id, u.nombre, u.correo, u.contrasena, u.tipoUsuario
      FROM notificaciones n
      JOIN usuarios u ON n.receptor_id = u.id
      WHERE u.correo = ? AND n.leido = FALSE
      ORDER BY n.fecha_creacion DESC
    `;
    const rows = await this.query(sql, [correoReceptor]);
    return rows.map(r => {
      const usuario = new Usuario(r.user_id, r.nombre, r.correo, r.contrasena, r.tipoUsuario);
      return new Notificacion(r.id, usuario, r.mensaje, r.fecha_creacion, r.leido);
    });
  }

  async marcarComoLeida(correoReceptor, mensaje) {
    const sql = `
      UPDATE notificaciones n
      JOIN usuarios u ON n.receptor_id = u.id
      SET n.leido = TRUE
      WHERE u.correo = ? AND n.mensaje = ?
    `;
    return this.executeUpdate(sql, [correoReceptor, mensaje]);
  }

  async marcarTodasComoLeidas(correoReceptor) {
    const sql = `
      UPDATE notificaciones n
      JOIN usuarios u ON n.receptor_id = u.id
      SET n.leido = TRUE
      WHERE u.correo = ?
    `;
    return this.executeUpdate(sql, [correoReceptor]);
  }

  async contarNotificacionesNoLeidas(correoReceptor) {
    const sql = `
      SELECT COUNT(*) AS total
      FROM notificaciones n
      JOIN usuarios u ON n.receptor_id = u.id
      WHERE u.correo = ? AND n.leido = FALSE
    `;
    const rows = await this.query(sql, [correoReceptor]);
    return rows.length ? rows[0].total : 0;
  }

  async eliminarNotificacion(correoReceptor, mensaje) {
    const sql = `
      DELETE n FROM notificaciones n
      JOIN usuarios u ON n.receptor_id = u.id
      WHERE u.correo = ? AND n.mensaje = ?
    `;
    return this.executeUpdate(sql, [correoReceptor, mensaje]);
  }

  async eliminarTodasLasNotificaciones(correoReceptor) {
    const sql = `
      DELETE n FROM notificaciones n
      JOIN usuarios u ON n.receptor_id = u.id
      WHERE u.correo = ?
    `;
    return this.executeUpdate(sql, [correoReceptor]);
  }

  async obtenerIdUsuarioPorCorreo(correo) {
    const sql = SELECT id FROM usuarios WHERE correo = ?;
    const rows = await this.query(sql, [correo]);
    return rows.length ? rows[0].id : null;
  }
}
