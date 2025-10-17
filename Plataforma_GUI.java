import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Plataforma_GUI extends JFrame {
    private Usuario usuarioActual;
    private PropuestaController propuestaController;
    private MensajeController mensajeController;
    private NotificacionController notificacionController;
    private UsuarioDAO usuarioDAO;
    private PropuestaDAO propuestaDAO;

    private JPanel panelPrincipal;
    private CardLayout cardLayout;

    // Paneles principales
    private JPanel panelLogin;
    private JPanel panelRegistro;
    private JPanel panelDashboard;
    private JPanel panelPropuestas;
    private JPanel panelCrearPropuesta;
    private JPanel panelDetallePropuesta;
    private JPanel panelPerfil;

    // Datos temporales para imágenes
    private Map<String, ImageIcon> imagenesPropuestas;

    public Plataforma_GUI() {
        // Inicializar controladores y DAOs
        propuestaController = new PropuestaController();
        mensajeController = new MensajeController();
        notificacionController = new NotificacionController();
        usuarioDAO = new UsuarioDAO();
        propuestaDAO = new PropuestaDAO();
        imagenesPropuestas = new HashMap<>();

        initComponents();
        crearDatosDePrueba();
    }

    private void initComponents() {
        setTitle("Plataforma de Crowdfunding - Guatemala");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);

        // Layout principal
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        // Crear paneles
        crearPanelLogin();
        crearPanelRegistro();
        crearPanelDashboard();
        crearPanelPropuestas();
        crearPanelCrearPropuesta();
        crearPanelDetallePropuesta();
        crearPanelPerfil();

        add(panelPrincipal);

        // Mostrar login inicialmente
        cardLayout.show(panelPrincipal, "login");
    }

    private void crearPanelLogin() {
        panelLogin = new JPanel(new BorderLayout());
        panelLogin.setBackground(new Color(240, 248, 255));

        // Panel central
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel titulo = new JLabel("Plataforma de Crowdfunding");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(new Color(25, 118, 210));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 30, 0);
        centerPanel.add(titulo, gbc);

        // Subtítulo
        JLabel subtitulo = new JLabel("Conectando ideas con oportunidades");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(100, 100, 100));
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 40, 0);
        centerPanel.add(subtitulo, gbc);

        // Campos de login
        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 2;
        centerPanel.add(new JLabel("Correo:"), gbc);

        JTextField correoField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        centerPanel.add(correoField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        centerPanel.add(new JLabel("Contraseña:"), gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 3;
        centerPanel.add(passwordField, gbc);

        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(76, 175, 80));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnRegistro = new JButton("Crear Cuenta");
        btnRegistro.setBackground(new Color(33, 150, 243));
        btnRegistro.setForeground(Color.WHITE);

        btnLogin.addActionListener(e -> {
            String correo = correoField.getText();
            String password = new String(passwordField.getPassword());

            try {
                if (usuarioDAO.validarLogin(correo, password)) {
                    usuarioActual = usuarioDAO.obtenerUsuarioPorCorreo(correo);
                    actualizarDashboard();
                    cardLayout.show(panelPrincipal, "dashboard");
                    JOptionPane.showMessageDialog(this, "¡Bienvenido, " + usuarioActual.getNombre() + "!");
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegistro.addActionListener(e -> cardLayout.show(panelPrincipal, "registro"));

        botonesPanel.add(btnLogin);
        botonesPanel.add(btnRegistro);
        gbc.gridx = 1; gbc.gridy = 4;
        centerPanel.add(botonesPanel, gbc);

        panelLogin.add(centerPanel, BorderLayout.CENTER);
        panelPrincipal.add(panelLogin, "login");
    }

    private void crearPanelRegistro() {
        panelRegistro = new JPanel(new BorderLayout());
        panelRegistro.setBackground(new Color(240, 248, 255));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título con botón volver
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton btnVolver = new JButton("← Volver");
        btnVolver.setBackground(new Color(200, 200, 200));
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "login"));
        
        JLabel titulo = new JLabel("Crear Nueva Cuenta");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(25, 118, 210));
        
        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centerPanel.add(topPanel, gbc);

        // Tipo de usuario
        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 1;
        centerPanel.add(new JLabel("Tipo de Usuario:"), gbc);

        JComboBox<String> tipoUsuario = new JComboBox<>(new String[]{
            "Estudiante", "Emprendedor", "Inversionista", "Donante", "Voluntario"
        });
        gbc.gridx = 1; gbc.gridy = 1;
        centerPanel.add(tipoUsuario, gbc);

        // Campos comunes
        JTextField nombreField = new JTextField(20);
        JTextField correoField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField ubicacionField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 2; centerPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; centerPanel.add(nombreField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; centerPanel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; centerPanel.add(correoField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; centerPanel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; centerPanel.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; centerPanel.add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; centerPanel.add(ubicacionField, gbc);

        // Campo adicional según tipo de usuario
        JTextField campoAdicional = new JTextField(20);
        JLabel labelAdicional = new JLabel("Carrera:");
        gbc.gridx = 0; gbc.gridy = 6; centerPanel.add(labelAdicional, gbc);
        gbc.gridx = 1; gbc.gridy = 6; centerPanel.add(campoAdicional, gbc);

        // Actualizar campo adicional según tipo
        tipoUsuario.addActionListener(e -> {
            String tipo = (String) tipoUsuario.getSelectedItem();
            switch (tipo) {
                case "Estudiante":
                    labelAdicional.setText("Carrera:");
                    break;
                case "Emprendedor":
                    labelAdicional.setText("Proyecto Principal:");
                    break;
                case "Voluntario":
                    labelAdicional.setText("Habilidades:");
                    break;
                default:
                    labelAdicional.setText("Información adicional:");
                    break;
            }
        });

        // Botones
        JPanel botonesPanel = new JPanel();
        JButton btnRegistrar = new JButton("Registrarse");
        JButton btnCancelar = new JButton("Cancelar");

        btnRegistrar.setBackground(new Color(76, 175, 80));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));

        btnRegistrar.addActionListener(e -> {
            try {
                String tipo = (String) tipoUsuario.getSelectedItem();
                String nombre = nombreField.getText();
                String correo = correoField.getText();
                String password = new String(passwordField.getPassword());
                String ubicacion = ubicacionField.getText();
                String adicional = campoAdicional.getText();

                // Crear usuario según tipo usando DAO
                boolean exito = false;
                switch (tipo) {
                    case "Estudiante":
                        Estudiante estudiante = new Estudiante(nombre, correo, password, ubicacion, adicional);
                        exito = usuarioDAO.crearEstudiante(estudiante);
                        break;
                    case "Emprendedor":
                        Usuario emprendedor = new Usuario(nombre, correo, password, ubicacion);
                        exito = usuarioDAO.crearUsuario(emprendedor, "Emprendedor");
                        break;
                    case "Inversionista":
                        Usuario inversionista = new Usuario(nombre, correo, password, ubicacion);
                        exito = usuarioDAO.crearUsuario(inversionista, "Inversionista");
                        break;
                    case "Donante":
                        Usuario donante = new Usuario(nombre, correo, password, ubicacion);
                        exito = usuarioDAO.crearUsuario(donante, "Donante");
                        break;
                    case "Voluntario":
                        Usuario voluntario = new Usuario(nombre, correo, password, ubicacion);
                        exito = usuarioDAO.crearUsuario(voluntario, "Voluntario");
                        break;
                }

                if (exito) {
                    JOptionPane.showMessageDialog(this, "¡Cuenta creada exitosamente!");
                    cardLayout.show(panelPrincipal, "login");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al crear la cuenta", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> cardLayout.show(panelPrincipal, "login"));

        botonesPanel.add(btnRegistrar);
        botonesPanel.add(btnCancelar);
        gbc.gridx = 1; gbc.gridy = 7;
        centerPanel.add(botonesPanel, gbc);

        panelRegistro.add(centerPanel, BorderLayout.CENTER);
        panelPrincipal.add(panelRegistro, "registro");
    }

    private void crearPanelDashboard() {
        panelDashboard = new JPanel(new BorderLayout());

        // Header
        JPanel header = crearHeader();
        panelDashboard.add(header, BorderLayout.NORTH);

        // Contenido principal
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Bienvenida personalizada
        JLabel bienvenida = new JLabel("Bienvenido a tu Dashboard");
        bienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenida.setForeground(new Color(25, 118, 210));
        contenido.add(bienvenida, BorderLayout.NORTH);

        // Estadísticas rápidas
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Obtener estadísticas reales de la BD
        int totalPropuestas = propuestaDAO.obtenerTodasLasPropuestas().size();
        
        JPanel stat1 = crearPanelEstadistica("Proyectos Activos", String.valueOf(totalPropuestas), new Color(76, 175, 80));
        JPanel stat2 = crearPanelEstadistica("Donaciones", "$4,250", new Color(255, 152, 0));
        JPanel stat3 = crearPanelEstadistica("Progreso General", "68%", new Color(33, 150, 243));

        statsPanel.add(stat1);
        statsPanel.add(stat2);
        statsPanel.add(stat3);

        contenido.add(statsPanel, BorderLayout.CENTER);

        // Acciones rápidas
        JPanel accionesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        accionesPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton btnVerProyectos = crearBotonAccion("📋 Ver Proyectos", new Color(33, 150, 243));
        JButton btnCrearProyecto = crearBotonAccion("➕ Crear Proyecto", new Color(76, 175, 80));
        JButton btnMiPerfil = crearBotonAccion("👤 Mi Perfil", new Color(156, 39, 176));
        JButton btnMensajes = crearBotonAccion("💬 Mensajes", new Color(255, 152, 0));

        btnVerProyectos.addActionListener(e -> {
            actualizarPropuestas();
            cardLayout.show(panelPrincipal, "propuestas");
        });

        btnCrearProyecto.addActionListener(e -> cardLayout.show(panelPrincipal, "crearPropuesta"));
        btnMiPerfil.addActionListener(e -> actualizarPerfil());
        btnMensajes.addActionListener(e -> mostrarMensajes());

        accionesPanel.add(btnVerProyectos);
        accionesPanel.add(btnCrearProyecto);
        accionesPanel.add(btnMiPerfil);
        accionesPanel.add(btnMensajes);

        contenido.add(accionesPanel, BorderLayout.SOUTH);

        panelDashboard.add(contenido, BorderLayout.CENTER);
        panelPrincipal.add(panelDashboard, "dashboard");
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("Plataforma de Crowdfunding");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(titulo, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        if (usuarioActual != null) {
            JLabel userLabel = new JLabel("Hola, " + usuarioActual.getNombre());
            userLabel.setForeground(Color.WHITE);
            userLabel.setFont(new Font("Arial", Font.BOLD, 14));
            userPanel.add(userLabel);
        }

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(Color.WHITE);
        btnCerrarSesion.addActionListener(e -> {
            usuarioActual = null;
            cardLayout.show(panelPrincipal, "login");
        });
        userPanel.add(btnCerrarSesion);

        header.add(userPanel, BorderLayout.EAST);
        return header;
    }

    private JPanel crearPanelEstadistica(String titulo, String valor, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 24));
        lblValor.setForeground(color);
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblValor);

        return panel;
    }

    private JButton crearBotonAccion(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        return boton;
    }

    private void crearPanelPropuestas() {
        panelPropuestas = new JPanel(new BorderLayout());

        // Header con botón volver
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JButton btnVolver = new JButton("← Volver al Dashboard");
        btnVolver.setBackground(Color.WHITE);
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "dashboard"));
        header.add(btnVolver, BorderLayout.WEST);

        JLabel tituloHeader = new JLabel("Proyectos Disponibles");
        tituloHeader.setForeground(Color.WHITE);
        tituloHeader.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(tituloHeader, BorderLayout.CENTER);

        panelPropuestas.add(header, BorderLayout.NORTH);
        panelPrincipal.add(panelPropuestas, "propuestas");
    }

    private void crearPanelCrearPropuesta() {
        panelCrearPropuesta = new JPanel(new BorderLayout());

        // Header con botón volver
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JButton btnVolver = new JButton("← Volver al Dashboard");
        btnVolver.setBackground(Color.WHITE);
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "dashboard"));
        header.add(btnVolver, BorderLayout.WEST);

        JLabel tituloHeader = new JLabel("Crear Nueva Propuesta");
        tituloHeader.setForeground(Color.WHITE);
        tituloHeader.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(tituloHeader, BorderLayout.CENTER);

        panelCrearPropuesta.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Crear Nueva Propuesta");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(25, 118, 210));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centerPanel.add(titulo, gbc);

        // Campos del formulario
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; centerPanel.add(new JLabel("Título del Proyecto:"), gbc);
        JTextField tituloField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 1; centerPanel.add(tituloField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; centerPanel.add(new JLabel("Descripción:"), gbc);
        JTextArea descripcionArea = new JTextArea(5, 30);
        descripcionArea.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(descripcionArea);
        gbc.gridx = 1; gbc.gridy = 2; centerPanel.add(scrollDesc, gbc);

        gbc.gridx = 0; gbc.gridy = 3; centerPanel.add(new JLabel("Meta de Financiamiento ($):"), gbc);
        JTextField metaField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 3; centerPanel.add(metaField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; centerPanel.add(new JLabel("Imagen del Proyecto:"), gbc);
        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblImagen = new JLabel("No se ha seleccionado imagen");
        lblImagen.setForeground(Color.GRAY);
        lblImagen.setPreferredSize(new Dimension(200, 150));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setOpaque(true);
        lblImagen.setBackground(Color.WHITE);
        
        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen(lblImagen, tituloField.getText()));
        panelImagen.add(btnSeleccionarImagen);
        panelImagen.add(Box.createRigidArea(new Dimension(10, 0)));
        panelImagen.add(lblImagen);
        gbc.gridx = 1; gbc.gridy = 4; centerPanel.add(panelImagen, gbc);

        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCrear = new JButton("Crear Proyecto");
        JButton btnCancelar = new JButton("Cancelar");

        btnCrear.setBackground(new Color(76, 175, 80));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFont(new Font("Arial", Font.BOLD, 14));

        btnCrear.addActionListener(e -> {
            try {
                String tituloTexto = tituloField.getText();
                String descripcion = descripcionArea.getText();
                double meta = Double.parseDouble(metaField.getText());

                if (tituloTexto.isEmpty() || descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear propuesta usando DAO de BD
                Propuesta nuevaPropuesta = new Propuesta(tituloTexto, descripcion, usuarioActual);
                nuevaPropuesta.setMeta(meta);
                
                // Guardar en BD
                boolean exitoBD = propuestaDAO.crearPropuesta(nuevaPropuesta);
                
                if (exitoBD) {
                    // También agregar al controller para mostrar en el GUI
                    String resultado = propuestaController.crearPropuesta(nuevaPropuesta);
                    
                    JOptionPane.showMessageDialog(this, "¡Propuesta creada exitosamente en la base de datos!");

                    // Limpiar campos
                    tituloField.setText("");
                    descripcionArea.setText("");
                    metaField.setText("");
                    lblImagen.setIcon(null);
                    lblImagen.setText("No se ha seleccionado imagen");

                    cardLayout.show(panelPrincipal, "dashboard");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> cardLayout.show(panelPrincipal, "dashboard"));

        botonesPanel.add(btnCrear);
        botonesPanel.add(btnCancelar);
        gbc.gridx = 1; gbc.gridy = 5;
        centerPanel.add(botonesPanel, gbc);

        panelCrearPropuesta.add(centerPanel, BorderLayout.CENTER);
        panelPrincipal.add(panelCrearPropuesta, "crearPropuesta");
    }

    private void crearPanelDetallePropuesta() {
        panelDetallePropuesta = new JPanel(new BorderLayout());
        panelPrincipal.add(panelDetallePropuesta, "detallePropuesta");
    }

    private void crearPanelPerfil() {
        panelPerfil = new JPanel(new BorderLayout());
        panelPrincipal.add(panelPerfil, "perfil");
    }

    private void seleccionarImagen(JLabel lblImagen, String tituloPropuesta) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Imágenes", "jpg", "jpeg", "png", "gif"));

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIcon imagenOriginal = new ImageIcon(fileChooser.getSelectedFile().getPath());
                Image imagenRedimensionada = imagenOriginal.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                ImageIcon imagenFinal = new ImageIcon(imagenRedimensionada);
                
                imagenesPropuestas.put(tituloPropuesta, imagenFinal);
                lblImagen.setIcon(imagenFinal);
                lblImagen.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarDashboard() {
        // Actualizar información del dashboard según el usuario
        if (usuarioActual != null) {
            Component[] components = panelDashboard.getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    if (panel.getComponentCount() > 0) {
                        Component firstComp = panel.getComponent(0);
                        if (firstComp instanceof JLabel) {
                            ((JLabel) firstComp).setText("Bienvenido, " + usuarioActual.getNombre());
                        }
                    }
                }
            }
        }
    }

    private void actualizarPropuestas() {
        // Limpiar panel anterior
        panelPropuestas.removeAll();

        // Header con botón volver
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JButton btnVolver = new JButton("← Volver al Dashboard");
        btnVolver.setBackground(Color.WHITE);
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "dashboard"));
        header.add(btnVolver, BorderLayout.WEST);

        JLabel tituloHeader = new JLabel("Proyectos Disponibles");
        tituloHeader.setForeground(Color.WHITE);
        tituloHeader.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(tituloHeader, BorderLayout.CENTER);

        panelPropuestas.add(header, BorderLayout.NORTH);

        // Contenido
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Obtener propuestas de la BD
        java.util.List<Propuesta> propuestasBD = propuestaDAO.obtenerTodasLasPropuestas();
        
        JLabel titulo = new JLabel("Proyectos Disponibles (" + propuestasBD.size() + " proyectos)");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(25, 118, 210));
        contenido.add(titulo, BorderLayout.NORTH);

        // Lista de propuestas
        JPanel listaPropuestas = new JPanel();
        listaPropuestas.setLayout(new BoxLayout(listaPropuestas, BoxLayout.Y_AXIS));

        if (propuestasBD.isEmpty()) {
            JLabel lblNoPropuestas = new JLabel("No hay proyectos disponibles. ¡Sé el primero en crear uno!");
            lblNoPropuestas.setFont(new Font("Arial", Font.ITALIC, 16));
            lblNoPropuestas.setForeground(Color.GRAY);
            lblNoPropuestas.setHorizontalAlignment(SwingConstants.CENTER);
            listaPropuestas.add(lblNoPropuestas);
        } else {
            for (Propuesta propuesta : propuestasBD) {
                JPanel cardPropuesta = crearCardPropuesta(propuesta);
                listaPropuestas.add(cardPropuesta);
                listaPropuestas.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(listaPropuestas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        contenido.add(scrollPane, BorderLayout.CENTER);
        panelPropuestas.add(contenido, BorderLayout.CENTER);

        panelPropuestas.revalidate();
        panelPropuestas.repaint();
    }

    private JPanel crearCardPropuesta(Propuesta propuesta) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // Imagen de la propuesta
        JLabel lblImagen = new JLabel();
        ImageIcon imagen = imagenesPropuestas.get(propuesta.getTitulo());
        if (imagen == null) {
            // Imagen por defecto
            lblImagen.setText("📷 Sin imagen");
            lblImagen.setFont(new Font("Arial", Font.PLAIN, 12));
            lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
            lblImagen.setVerticalAlignment(SwingConstants.CENTER);
            lblImagen.setPreferredSize(new Dimension(150, 150));
            lblImagen.setOpaque(true);
            lblImagen.setBackground(new Color(240, 240, 240));
            lblImagen.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        } else {
            lblImagen.setIcon(imagen);
            lblImagen.setText("");
        }
        lblImagen.setPreferredSize(new Dimension(150, 150));
        card.add(lblImagen, BorderLayout.WEST);

        // Información de la propuesta
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel(propuesta.getTitulo());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(25, 118, 210));

        JLabel lblCreador = new JLabel("Creador: " + propuesta.getCreador().getNombre());
        lblCreador.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCreador.setForeground(Color.GRAY);

        JLabel lblDescripcion = new JLabel(
            propuesta.getDescripcion().length() > 100 ?
            propuesta.getDescripcion().substring(0, 100) + "..." :
            propuesta.getDescripcion()
        );
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));

        // Barra de progreso
        double progreso = propuesta.getMeta() > 0 ? (propuesta.getRecaudado() / propuesta.getMeta()) * 100 : 0;
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) progreso);
        progressBar.setString(String.format("$%.2f / $%.2f (%.1f%%)",
            propuesta.getRecaudado(), propuesta.getMeta(), progreso));
        progressBar.setStringPainted(true);
        progressBar.setForeground(getColorProgreso(progreso));

        infoPanel.add(lblTitulo);
        infoPanel.add(lblCreador);
        infoPanel.add(lblDescripcion);
        infoPanel.add(progressBar);

        card.add(infoPanel, BorderLayout.CENTER);

        // Botones de acción
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
        botonesPanel.setBackground(Color.WHITE);

        if (usuarioActual != null && (usuarioActual instanceof Inversionista || usuarioActual instanceof Donante)) {
            JButton btnApoyo = new JButton("💵 Apoyar Económicamente");
            btnApoyo.setBackground(new Color(76, 175, 80));
            btnApoyo.setForeground(Color.WHITE);
            btnApoyo.addActionListener(e -> mostrarDialogoApoyo(propuesta, "economico"));
            botonesPanel.add(btnApoyo);
            botonesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        if (usuarioActual != null && usuarioActual instanceof Voluntario) {
            JButton btnVoluntario = new JButton("🤝 Ofrecer Ayuda");
            btnVoluntario.setBackground(new Color(255, 152, 0));
            btnVoluntario.setForeground(Color.WHITE);
            btnVoluntario.addActionListener(e -> mostrarDialogoApoyo(propuesta, "voluntario"));
            botonesPanel.add(btnVoluntario);
            botonesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        JButton btnDetalle = new JButton("👁️ Ver Detalle");
        btnDetalle.setBackground(new Color(33, 150, 243));
        btnDetalle.setForeground(Color.WHITE);
        btnDetalle.addActionListener(e -> mostrarDetallePropuesta(propuesta));
        botonesPanel.add(btnDetalle);

        card.add(botonesPanel, BorderLayout.EAST);

        return card;
    }

    private Color getColorProgreso(double progreso) {
        if (progreso < 30) return Color.RED;
        if (progreso < 70) return Color.ORANGE;
        return Color.GREEN;
    }

    private void mostrarDialogoApoyo(Propuesta propuesta, String tipo) {
        if (tipo.equals("economico")) {
            String input = JOptionPane.showInputDialog(this,
                "Ingrese la cantidad a aportar a '" + propuesta.getTitulo() + "':",
                "Apoyo Económico",
                JOptionPane.QUESTION_MESSAGE);

            if (input != null && !input.trim().isEmpty()) {
                try {
                    double cantidad = Double.parseDouble(input);
                    if (cantidad > 0) {
                        // Actualizar en BD
                        boolean exitoBD = propuestaDAO.agregarRecaudacion(propuesta.getTitulo(), cantidad);
                        
                        if (exitoBD) {
                            // Actualizar en memoria
                            propuesta.agregarRecaudacion(cantidad);

                            // Usar TUS métodos originales
                            String resultado = "";
                            if (usuarioActual instanceof Inversionista) {
                                resultado = propuestaController.registrarInversion(propuesta, (Inversionista) usuarioActual);
                            } else if (usuarioActual instanceof Donante) {
                                resultado = propuestaController.registrarDonacion(propuesta, (Donante) usuarioActual);
                            }

                            JOptionPane.showMessageDialog(this,
                                "¡Gracias por tu apoyo de $" + cantidad + "!\n" +
                                "El proyecto '" + propuesta.getTitulo() + "' ha recibido tu contribución.\n" +
                                resultado);

                            actualizarPropuestas();
                        } else {
                            JOptionPane.showMessageDialog(this, "Error al registrar en la base de datos");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Cantidad inválida");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        } else if (tipo.equals("voluntario")) {
            String[] opciones = {"Habilidades técnicas", "Trabajo gratuito", "Mentoría", "Consultoría"};
            String seleccion = (String) JOptionPane.showInputDialog(this,
                "Seleccione el tipo de apoyo que desea ofrecer para '" + propuesta.getTitulo() + "':",
                "Apoyo Voluntario",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

            if (seleccion != null) {
                try {
                    // Usar TU método original
                    String resultado = propuestaController.registrarApoyo(propuesta, (Voluntario) usuarioActual);
                    JOptionPane.showMessageDialog(this,
                        "¡Gracias por ofrecer " + seleccion + "!\n" +
                        "El creador del proyecto será notificado de tu disponibilidad.\n" +
                        resultado);
                    actualizarPropuestas();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        }
    }

    private void mostrarDetallePropuesta(Propuesta propuesta) {
        JDialog dialog = new JDialog(this, "Detalle del Proyecto", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel(propuesta.getTitulo());
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(25, 118, 210));
        panel.add(titulo, BorderLayout.NORTH);

        // Información detallada
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        infoPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        infoPanel.add(new JLabel("Creador: " + propuesta.getCreador().getNombre()));
        infoPanel.add(new JLabel("Ubicación: " + propuesta.getCreador().getUbicacion()));
        infoPanel.add(new JLabel("Descripción: " + propuesta.getDescripcion()));

        // Barra de progreso detallada
        double progreso = propuesta.getMeta() > 0 ? (propuesta.getRecaudado() / propuesta.getMeta()) * 100 : 0;
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) progreso);
        progressBar.setString(String.format("$%.2f / $%.2f (%.1f%%)",
            propuesta.getRecaudado(), propuesta.getMeta(), progreso));
        progressBar.setStringPainted(true);
        progressBar.setForeground(getColorProgreso(progreso));
        progressBar.setPreferredSize(new Dimension(0, 30));

        infoPanel.add(progressBar);
        panel.add(infoPanel, BorderLayout.CENTER);

        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());

        if (usuarioActual != null && !usuarioActual.equals(propuesta.getCreador())) {
            if (usuarioActual instanceof Inversionista || usuarioActual instanceof Donante) {
                JButton btnApoyar = new JButton("Apoyar Económicamente");
                btnApoyar.setBackground(new Color(76, 175, 80));
                btnApoyar.setForeground(Color.WHITE);
                btnApoyar.addActionListener(e -> {
                    dialog.dispose();
                    mostrarDialogoApoyo(propuesta, "economico");
                });
                botonesPanel.add(btnApoyar);
            }

            if (usuarioActual instanceof Voluntario) {
                JButton btnVoluntario = new JButton("Ofrecer Ayuda");
                btnVoluntario.setBackground(new Color(255, 152, 0));
                btnVoluntario.setForeground(Color.WHITE);
                btnVoluntario.addActionListener(e -> {
                    dialog.dispose();
                    mostrarDialogoApoyo(propuesta, "voluntario");
                });
                botonesPanel.add(btnVoluntario);
            }
        }

        botonesPanel.add(btnCerrar);
        panel.add(botonesPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void actualizarPerfil() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de perfil en desarrollo");
        cardLayout.show(panelPrincipal, "dashboard");
    }

    private void mostrarMensajes() {
        JOptionPane.showMessageDialog(this, "Sistema de mensajes en desarrollo");
    }

    private void crearDatosDePrueba() {
        // Crear algunos usuarios de prueba en la BD
        try {
            // Verificar si ya existen usuarios para no duplicar
            if (usuarioDAO.obtenerUsuarioPorCorreo("carlos@mail.com") == null) {
                Usuario emprendedor = new Usuario("Carlos López", "carlos@mail.com", "123", "Guatemala");
                usuarioDAO.crearUsuario(emprendedor, "Emprendedor");
            }

            if (usuarioDAO.obtenerUsuarioPorCorreo("maria@mail.com") == null) {
                Estudiante estudiante = new Estudiante("María García", "maria@mail.com", "123", "Antigua", "Ingeniería");
                usuarioDAO.crearEstudiante(estudiante);
            }

            // Crear propuestas de prueba en la BD si no existen
            if (propuestaDAO.obtenerPropuestaPorTitulo("App Móvil Educativa") == null) {
                Usuario creador = usuarioDAO.obtenerUsuarioPorCorreo("carlos@mail.com");
                if (creador != null) {
                    Propuesta propuesta1 = new Propuesta("App Móvil Educativa", 
                        "Desarrollo de una aplicación móvil para educación primaria con contenido interactivo y gamificación.",
                        creador);
                    propuesta1.setMeta(5000);
                    propuesta1.agregarRecaudacion(1200);
                    propuestaDAO.crearPropuesta(propuesta1);
                }
            }

        } catch (Exception e) {
            System.err.println("Error al crear datos de prueba: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Inicializar la base de datos primero
        DatabaseInitializer.initializeDatabase();
        
        SwingUtilities.invokeLater(() -> {
            new Plataforma_GUI().setVisible(true);
        });
    }
}