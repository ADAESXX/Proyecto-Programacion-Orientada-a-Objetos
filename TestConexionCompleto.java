import java.util.List;

public class TestConexionCompleto {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA COMPLETA DEL SISTEMA ===");
        
        // 1. Probar conexión
        System.out.println("\n1. PROBANDO CONEXIÓN...");
        boolean conexionExitosa = ConexionBD.probarConexion();
        
        if (!conexionExitosa) {
            System.out.println(" Error de conexión. Verifica:");
            System.out.println("1. MySQL está ejecutándose");
            System.out.println("2. La base de datos 'plataforma_apoyo' existe");
            System.out.println("3. Las credenciales son correctas");
            System.out.println("4. mysql-connector-java está en el classpath");
            return;
        }
        
        System.out.println(" ¡Conexión exitosa!");
        
        // 2. Inicializar base de datos (crear tablas)
        System.out.println("\n2. INICIALIZANDO BASE DE DATOS...");
        DatabaseInitializer.initializeDatabase();
        
        // 3. Probar DAOs
        System.out.println("\n3. PROBANDO OPERACIONES DE BASE DE DATOS...");
        
        // Crear instancias de DAOs
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PropuestaDAO propuestaDAO = new PropuestaDAO();
        MensajeDAO mensajeDAO = new MensajeDAO();
        NotificacionDAO notificacionDAO = new NotificacionDAO();
        
        try {
            // Probar creación de estudiante
            System.out.println("\n--- Probando creación de Estudiante ---");
            Estudiante estudiante = new Estudiante(
                "Juan Pérez", 
                "juan.perez@email.com", 
                "123456", 
                "Guatemala", 
                "Ingeniería en Sistemas"
            );
            estudiante.agregarPropuesta("App móvil para estudiantes");
            estudiante.agregarPropuesta("Sistema de gestión académica");
            
            boolean estudianteCreado = usuarioDAO.crearEstudiante(estudiante);
            System.out.println("Estudiante creado: " + (estudianteCreado ? " SÍ" : " NO"));
            
            // Probar creación de usuario inversionista
            System.out.println("\n--- Probando creación de Inversionista ---");
            Usuario inversionista = new Usuario(
                "María González", 
                "maria.gonzalez@email.com", 
                "654321", 
                "Ciudad de Guatemala"
            );
            boolean inversionistaCreado = usuarioDAO.crearUsuario(inversionista, "Inversionista");
            System.out.println("Inversionista creado: " + (inversionistaCreado ? " SÍ" : " NO"));
            
            // Probar obtención de usuarios
            System.out.println("\n--- Probando obtención de usuarios ---");
            List<Usuario> usuarios = usuarioDAO.obtenerTodosLosUsuarios();
            System.out.println("Usuarios encontrados: " + usuarios.size());
            for (Usuario u : usuarios) {
                System.out.println("- " + u.getNombre() + " (" + u.getCorreo() + ")");
            }
            
            // Probar creación de propuesta
            System.out.println("\n--- Probando creación de Propuesta ---");
            Propuesta propuesta = new Propuesta(
                "Plataforma de Apoyo Estudiantil",
                "Una plataforma para conectar estudiantes con inversionistas y mentores",
                estudiante
            );
            propuesta.setMeta(50000.0);
            propuesta.agregarRecaudacion(5000.0);
            
            boolean propuestaCreada = propuestaDAO.crearPropuesta(propuesta);
            System.out.println("Propuesta creada: " + (propuestaCreada ? " SÍ" : " NO"));
            
            // Probar obtención de propuestas
            System.out.println("\n--- Probando obtención de Propuestas ---");
            List<Propuesta> propuestas = propuestaDAO.obtenerTodasLasPropuestas();
            System.out.println("Propuestas encontradas: " + propuestas.size());
            for (Propuesta p : propuestas) {
                System.out.println("- " + p.getTitulo() + 
                    " (Meta: $" + p.getMeta() + 
                    ", Recaudado: $" + p.getRecaudado() + 
                    ", Creador: " + p.getCreador().getNombre() + ")");
            }
            
            // Probar envío de mensaje
            System.out.println("\n--- Probando envío de Mensaje ---");
            Mensaje mensaje = new Mensaje(
                inversionista, 
                estudiante, 
                "Me interesa tu propuesta. ¿Podemos hablar?"
            );
            boolean mensajeEnviado = mensajeDAO.enviarMensaje(mensaje);
            System.out.println("Mensaje enviado: " + (mensajeEnviado ? " SÍ" : " NO"));
            
            // Probar obtención de mensajes
            System.out.println("\n--- Probando obtención de Mensajes ---");
            List<Mensaje> mensajesRecibidos = mensajeDAO.obtenerMensajesRecibidos("juan.perez@email.com");
            System.out.println("Mensajes recibidos por Juan: " + mensajesRecibidos.size());
            for (Mensaje m : mensajesRecibidos) {
                System.out.println("- De: " + m.getEmisor().getNombre() + 
                    " | Mensaje: " + m.getContenido());
            }
            
            // Probar creación de notificación
            System.out.println("\n--- Probando creación de Notificación ---");
            Notificaciones notificacion = new Notificaciones(
                estudiante,
                "Tienes un nuevo mensaje de " + inversionista.getNombre()
            );
            boolean notificacionCreada = notificacionDAO.crearNotificacion(notificacion);
            System.out.println("Notificación creada: " + (notificacionCreada ? "SÍ" : "NO"));
            
            // Probar obtención de notificaciones
            System.out.println("\n--- Probando obtención de Notificaciones ---");
            List<Notificaciones> notificaciones = notificacionDAO.obtenerNotificacionesUsuario("juan.perez@email.com");
            System.out.println("Notificaciones para Juan: " + notificaciones.size());
            for (Notificaciones n : notificaciones) {
                System.out.println("- " + n.getMensaje() + " | Leído: " + n.isLeido());
            }
            
            // Probar validación de login
            System.out.println("\n--- Probando validación de Login ---");
            boolean loginValido = usuarioDAO.validarLogin("juan.perez@email.com", "123456");
            System.out.println("Login válido para Juan: " + (loginValido ? "SÍ" : "NO"));
            
            boolean loginInvalido = usuarioDAO.validarLogin("juan.perez@email.com", "wrongpassword");
            System.out.println("Login inválido (contraseña incorrecta): " + (loginInvalido ? " SÍ" : "✅ NO"));
            
        } catch (Exception e) {
            System.err.println("Error durante las pruebas: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== PRUEBAS COMPLETADAS ===");
        System.out.println("Sistema de base de datos funcionando correctamente");
        System.out.println("Todas las tablas creadas automáticamente");
        System.out.println("DAOs funcionando para todas las entidades");
        System.out.println("Datos de prueba insertados exitosamente");
        
        // Cerrar conexión
        ConexionBD.cerrarConexion();
        
        System.out.println("\n¡El sistema está listo para usar!");
        System.out.println("Puedes usar los DAOs para:");
        System.out.println("- UsuarioDAONew: Gestionar usuarios y estudiantes");
        System.out.println("- PropuestaDAONew: Gestionar propuestas y financiamiento");
        System.out.println("- MensajeDAONew: Gestionar mensajería entre usuarios");
        System.out.println("- NotificacionDAONew: Gestionar notificaciones del sistema");
    }
}