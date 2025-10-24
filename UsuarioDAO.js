import BaseDAO from './BaseDAO.js';
import Usuario from './models/Usuario.js';

class UsuarioDAO extends BaseDAO {

    // Crear usuario base
    async crearUsuario(usuario, tipoUsuario) {
        const sql = `INSERT INTO usuarios (nombre, correo, contrasena, ubicacion, verificado, tipo_usuario) 
                     VALUES (?, ?, ?, ?, ?, ?)`;
        
        return await this.executeUpdate(sql, 
            usuario.getNombre(), 
            usuario.getCorreo(), 
            usuario.getContrasena(), 
            usuario.getUbicacion(), 
            usuario.getVerificado(), 
            tipoUsuario
        );
    }

    // Crear estudiante completo (usuario + estudiante + propuestas)
    async crearEstudiante(estudiante) {
        let connection = null;
        try {
            connection = await this.getConnection();
            if (!connection) {
                throw new Error('No se pudo obtener conexión a la base de datos');
            }

            await connection.beginTransaction();

            // Insertar usuario
            const sqlUsuario = `INSERT INTO usuarios (nombre, correo, contrasena, ubicacion, verificado, tipo_usuario) 
                               VALUES (?, ?, ?, ?, ?, 'Estudiante')`;
            const [resultUsuario] = await connection.execute(sqlUsuario, [
                estudiante.getNombre(),
                estudiante.getCorreo(),
                estudiante.getContrasena(),
                estudiante.getUbicacion(),
                estudiante.getVerificado()
            ]);

            const usuarioId = resultUsuario.insertId;

            // Insertar estudiante
            const sqlEstudiante = `INSERT INTO estudiantes (usuario_id, carrera) VALUES (?, ?)`;
            const [resultEstudiante] = await connection.execute(sqlEstudiante, [
                usuarioId,
                estudiante.getCarrera()
            ]);

            const estudianteId = resultEstudiante.insertId;

            // Insertar propuestas del estudiante
            if (estudiante.getPropuestas().length > 0) {
                const sqlPropuestas = `INSERT INTO estudiante_propuestas (estudiante_id, titulo_propuesta) VALUES (?, ?)`;
                
                for (const propuesta of estudiante.getPropuestas()) {
                    await connection.execute(sqlPropuestas, [estudianteId, propuesta]);
                }
            }

            await connection.commit();
            return true;

        } catch (error) {
            if (connection) {
                await connection.rollback();
            }
            console.error('Error al crear estudiante:', error.message);
            return false;
        }
    }

    // Obtener usuario por correo
    async obtenerUsuarioPorCorreo(correo) {
        const sql = `SELECT id, nombre, correo, contrasena, ubicacion, verificado, tipo_usuario 
                     FROM usuarios WHERE correo = ?`;

        const row = await this.executeQuerySingle(sql, correo);
        
        if (row) {
            const usuario = new Usuario(
                row.nombre,
                row.correo,
                row.contrasena,
                row.ubicacion
            );
            usuario.setVerificado(row.verificado);
            return usuario;
        }

        return null;
    }

    // Obtener todos los usuarios
    async obtenerTodosLosUsuarios() {
        const usuarios = [];
        const sql = `SELECT nombre, correo, contrasena, ubicacion, verificado, tipo_usuario 
                     FROM usuarios ORDER BY nombre`;

        const rows = await this.executeQuery(sql);
        
        for (const row of rows) {
            const usuario = new Usuario(
                row.nombre,
                row.correo,
                row.contrasena,
                row.ubicacion
            );
            usuario.setVerificado(row.verificado);
            usuarios.push(usuario);
        }

        return usuarios;
    }

    // Actualizar usuario
    async actualizarUsuario(usuario) {
        const sql = `UPDATE usuarios SET nombre = ?, ubicacion = ?, contrasena = ? WHERE correo = ?`;
        return await this.executeUpdate(sql, 
            usuario.getNombre(), 
            usuario.getUbicacion(), 
            usuario.getContrasena(), 
            usuario.getCorreo()
        );
    }

    // Verificar usuario
    async verificarUsuario(correo) {
        const sql = `UPDATE usuarios SET verificado = TRUE WHERE correo = ?`;
        return await this.executeUpdate(sql, correo);
    }

    // Eliminar usuario
    async eliminarUsuario(correo) {
        const sql = `DELETE FROM usuarios WHERE correo = ?`;
        return await this.executeUpdate(sql, correo);
    }

    // Validar login
    async validarLogin(correo, contrasena) {
        const sql = `SELECT 1 FROM usuarios WHERE correo = ? AND contrasena = ? LIMIT 1`;
        
        const row = await this.executeQuerySingle(sql, correo, contrasena);
        return row !== null;
    }
}

export default UsuarioDAO;
