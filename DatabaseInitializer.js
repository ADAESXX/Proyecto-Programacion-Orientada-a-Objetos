import ConexionBD from './ConexionBD.js';

class DatabaseInitializer {
    
    static async initializeDatabase() {
        const conn = await ConexionBD.getConnection();
        if (conn) {
            try {
                console.log('Creando tablas en la base de datos...');
                
                // Crear tabla usuarios
                const createUsuarios = `CREATE TABLE IF NOT EXISTS usuarios (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    correo VARCHAR(100) UNIQUE NOT NULL,
                    contrasena VARCHAR(255) NOT NULL,
                    ubicacion VARCHAR(100) NOT NULL,
                    verificado BOOLEAN DEFAULT FALSE,
                    tipo_usuario ENUM('Estudiante', 'Emprendedor', 'Inversionista', 'Donante', 'Voluntario') NOT NULL,
                    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )`;
                await conn.execute(createUsuarios);
                console.log('✓ Tabla \'usuarios\' creada');
                
                // Crear tabla estudiantes
                const createEstudiantes = `CREATE TABLE IF NOT EXISTS estudiantes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    usuario_id INT NOT NULL,
                    carrera VARCHAR(100) NOT NULL,
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
                )`;
                await conn.execute(createEstudiantes);
                console.log('✓ Tabla \'estudiantes\' creada');
                
                // Crear tabla emprendedores
                const createEmprendedores = `CREATE TABLE IF NOT EXISTS emprendedores (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    usuario_id INT NOT NULL,
                    nombre_proyecto VARCHAR(200) NOT NULL,
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
                )`;
                await conn.execute(createEmprendedores);
                console.log('✓ Tabla \'emprendedores\' creada');
                
                // Crear tabla inversionistas
                const createInversionistas = `CREATE TABLE IF NOT EXISTS inversionistas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    usuario_id INT NOT NULL,
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
                )`;
                await conn.execute(createInversionistas);
                console.log('✓ Tabla \'inversionistas\' creada');
                
                // Crear tabla donantes
                const createDonantes = `CREATE TABLE IF NOT EXISTS donantes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    usuario_id INT NOT NULL,
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
                )`;
                await conn.execute(createDonantes);
                console.log('✓ Tabla \'donantes\' creada');
                
                // Crear tabla voluntarios
                const createVoluntarios = `CREATE TABLE IF NOT EXISTS voluntarios (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    usuario_id INT NOT NULL,
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
                )`;
                await conn.execute(createVoluntarios);
                console.log('✓ Tabla \'voluntarios\' creada');
                
                // Crear tabla propuestas
                const createPropuestas = `CREATE TABLE IF NOT EXISTS propuestas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    titulo VARCHAR(200) NOT NULL,
                    descripcion TEXT NOT NULL,
                    creador_id INT NOT NULL,
                    progreso FLOAT DEFAULT 0,
                    meta DOUBLE DEFAULT 0,
                    recaudado DOUBLE DEFAULT 0,
                    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (creador_id) REFERENCES usuarios(id) ON DELETE CASCADE
                )`;
                await conn.execute(createPropuestas);
                console.log('✓ Tabla \'propuestas\' creada');
                
                // Crear tabla mensajes
                const createMensajes = `CREATE TABLE IF NOT EXISTS mensajes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    emisor_id INT NOT NULL,
                    receptor_id INT NOT NULL,
                    contenido TEXT NOT NULL,
                    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (emisor_id) REFERENCES usuarios(id) ON DELETE CASCADE,
                    FOREIGN KEY (receptor_id) REFERENCES usuarios(id) ON DELETE CASCADE
                )`;
                await conn.execute(createMensajes);
                console.log('✓ Tabla \'mensajes\' creada');
                
                // Crear tabla notificaciones
                const createNotificaciones = `CREATE TABLE IF NOT EXISTS notificaciones (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    receptor_id INT NOT NULL,
                    mensaje TEXT NOT NULL,
                    leido BOOLEAN DEFAULT FALSE,
                    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (receptor_id) REFERENCES usuarios(id) ON DELETE CASCADE
                )`;
                await conn.execute(createNotificaciones);
                console.log('✓ Tabla \'notificaciones\' creada');

                // Crear tabla estudiante_propuestas
                const createEstudiantePropuestas = `CREATE TABLE IF NOT EXISTS estudiante_propuestas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    estudiante_id INT NOT NULL,
                    titulo_propuesta VARCHAR(200) NOT NULL,
                    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE
                )`;
                await conn.execute(createEstudiantePropuestas);
                console.log('✓ Tabla \'estudiante_propuestas\' creada');

                // Crear tabla propuesta_inversionistas
                const createPropuestaInversionistas = `CREATE TABLE IF NOT EXISTS propuesta_inversionistas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    propuesta_id INT NOT NULL,
                    inversionista_id INT NOT NULL,
                    monto_invertido DOUBLE NOT NULL,
                    fecha_inversion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (propuesta_id) REFERENCES propuestas(id) ON DELETE CASCADE,
                    FOREIGN KEY (inversionista_id) REFERENCES inversionistas(id) ON DELETE CASCADE,
                    UNIQUE KEY unique_propuesta_inversionista (propuesta_id, inversionista_id)
                )`;
                await conn.execute(createPropuestaInversionistas);
                console.log('✓ Tabla \'propuesta_inversionistas\' creada');
                
                console.log('\n¡Todas las tablas han sido creadas exitosamente!');
                
            } catch (error) {
                console.error('Error al crear las tablas:', error.message);
                throw error;
            }
        } else {
            console.error('No se pudo obtener conexión a la base de datos');
        }
    }
}

export default DatabaseInitializer;
