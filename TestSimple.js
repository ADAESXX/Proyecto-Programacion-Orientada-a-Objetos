import ConexionBD from './ConexionBD.js';
import DatabaseInitializer from './DatabaseInitializer.js';
import UsuarioDAO from './UsuarioDAO.js';
import Usuario from './models/Usuario.js';

class TestSimple {
    static async main() {
        console.log('=== PRUEBA SIMPLE DEL SISTEMA ===');
        
        try {
            // 1. Probar conexión
            console.log('\n1. PROBANDO CONEXIÓN...');
            const conexionExitosa = await ConexionBD.probarConexion();
            
            if (!conexionExitosa) {
                console.log('Error de conexión. Verifica:');
                console.log('1. MySQL está ejecutándose');
                console.log('2. La base de datos \'plataforma_apoyo\' existe');
                console.log('3. Las credenciales son correctas');
                return;
            }
            
            console.log('¡Conexión exitosa!');
            
            // 2. Inicializar base de datos (crear tablas)
            console.log('\n2. INICIALIZANDO BASE DE DATOS...');
            await DatabaseInitializer.initializeDatabase();
            
            // 3. Crear un usuario simple
            console.log('\n3. CREANDO USUARIO DE PRUEBA...');
            const usuarioDAO = new UsuarioDAO();
            
            // Crear usuario inversionista simple
            const usuario = new Usuario(
                'Test Usuario', 
                'test@email.com', 
                '123456', 
                'Guatemala'
            );
            
            const usuarioCreado = await usuarioDAO.crearUsuario(usuario, 'Inversionista');
            console.log('Usuario creado:', usuarioCreado ? 'SÍ' : ' NO');
            
            if (usuarioCreado) {
                // Verificar que se guardó
                const usuarioRecuperado = await usuarioDAO.obtenerUsuarioPorCorreo('test@email.com');
                if (usuarioRecuperado) {
                    console.log('✅ Usuario recuperado:', usuarioRecuperado.getNombre());
                    console.log('📧 Correo:', usuarioRecuperado.getCorreo());
                } else {
                    console.log('No se pudo recuperar el usuario');
                }
            }
            
        } catch (error) {
            console.error('Error durante las pruebas:', error.message);
            console.error(error.stack);
        } finally {
            // Cerrar conexión
            await ConexionBD.cerrarConexion();
            console.log('\n=== PRUEBA COMPLETADA ===');
        }
    }
}

// Ejecutar las pruebas si este archivo se ejecuta directamente
if (import.meta.url === `file://${process.argv[1]}`) {
    TestSimple.main();
}

export default TestSimple;
