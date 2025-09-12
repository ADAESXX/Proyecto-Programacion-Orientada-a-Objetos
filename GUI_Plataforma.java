import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI_Plataforma extends JFrame {
    private Usuario usuarioActual;
    private PropuestaController propuestaController;
    private MensajeController mensajeController;
    private NotificacionController notificacionController;

    private JPanel panelPrincipal;
    private CardLayout cardLayout;

    // Paneles principales
    private JPanel panelLogin;
    private JPanel panelDashboard;
    private JPanel panelPropuestas;
    private JPanel panelCrearPropuesta;
    private JPanel panelDetallePropuesta;

    public GUI_Plataforma() {
        // Inicializar controladores
        propuestaController = new PropuestaController();
        mensajeController = new MensajeController();
        notificacionController = new NotificacionController();

        initComponents();
        //crearDatosDePrueba();
    }

    private void initComponents() {
        setTitle("Plataforma de Apoyo Guatemala");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Layout principal
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        // Crear paneles
        crearPanelLogin();
        crearPanelDashboard();
        crearPanelPropuestas();
        crearPanelCrearPropuesta();
        crearPanelDetallePropuesta();

        add(panelPrincipal);

        // Mostrar login inicialmente
        cardLayout.show(panelPrincipal, "login");
    }

    private void crearPanelLogin() {
        panelLogin = new JPanel(new BorderLayout());
        panelLogin.setBackground(new Color(240, 248, 255));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Título
        JLabel titulo = new JLabel("Plataforma de Apoyo Guatemala");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(25, 118, 210));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 30, 0);
        centerPanel.add(titulo, gbc);

        // Tipo de usuario
        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 1;
        centerPanel.add(new JLabel("Tipo de Usuario:"), gbc);

        JComboBox<String> tipoUsuario = new JComboBox<>(new String[]{
                "Estudiante", "Emprendedor", "Inversionista", "Donante", "Voluntario"
        });
        gbc.gridx = 1; gbc.gridy = 1;
        centerPanel.add(tipoUsuario, gbc);

        // Campos de entrada
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
        JLabel labelAdicional = new JLabel("Campo adicional:");
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
                default:
                    labelAdicional.setText("Campo adicional:");
                    break;
            }
        });

        // Botón de login
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(76, 175, 80));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1; gbc.gridy = 7; gbc.insets = new Insets(20, 5, 5, 5);
        centerPanel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            try {
                String tipo = (String) tipoUsuario.getSelectedItem();
                String nombre = nombreField.getText();
                String correo = correoField.getText();
                String password = new String(passwordField.getPassword());
                String ubicacion = ubicacionField.getText();
                String adicional = campoAdicional.getText();

                // Crear usuario según tipo
                switch (tipo) {
                    case "Estudiante":
                        usuarioActual = new Estudiante(nombre, correo, password, ubicacion, adicional);
                        break;
                    case "Emprendedor":
                        usuarioActual = new Emprendedor(nombre, correo, password, ubicacion, adicional);
                        break;
                    case "Inversionista":
                        usuarioActual = new Inversionista(nombre, correo, password, ubicacion);
                        break;
                    case "Donante":
                        usuarioActual = new Donante(nombre, correo, password, ubicacion);
                        break;
                    case "Voluntario":
                        usuarioActual = new Voluntario(nombre, correo, password, ubicacion);
                        break;
                }

                actualizarDashboard();
                cardLayout.show(panelPrincipal, "dashboard");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        panelLogin.add(centerPanel, BorderLayout.CENTER);
        panelPrincipal.add(panelLogin, "login");
    }

    private void crearPanelDashboard() {
        panelDashboard = new JPanel(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel bienvenida = new JLabel("Bienvenido a la Plataforma de Apoyo");
        bienvenida.setForeground(Color.WHITE);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(bienvenida, BorderLayout.WEST);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(Color.WHITE);
        btnCerrarSesion.addActionListener(e -> {
            usuarioActual = null;
            cardLayout.show(panelPrincipal, "login");
        });
        header.add(btnCerrarSesion, BorderLayout.EAST);

        // Menú lateral
        JPanel menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setBackground(new Color(250, 250, 250));
        menuLateral.setBorder(new EmptyBorder(20, 20, 20, 20));
        menuLateral.setPreferredSize(new Dimension(200, 0));

        JButton btnVerPropuestas = crearBotonMenu("Ver Propuestas");
        btnVerPropuestas.addActionListener(e -> {
            actualizarPropuestas();
            cardLayout.show(panelPrincipal, "propuestas");
        });
        menuLateral.add(btnVerPropuestas);

        JButton btnCrearPropuesta = crearBotonMenu("Crear Propuesta");
        btnCrearPropuesta.addActionListener(e -> cardLayout.show(panelPrincipal, "crearPropuesta"));
        menuLateral.add(btnCrearPropuesta);

        panelDashboard.add(header, BorderLayout.NORTH);
        panelDashboard.add(menuLateral, BorderLayout.WEST);

        panelPrincipal.add(panelDashboard, "dashboard");
    }

    private void crearPanelPropuestas() {
        panelPropuestas = new JPanel(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Propuestas Disponibles");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(titulo, BorderLayout.WEST);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "dashboard"));
        header.add(btnVolver, BorderLayout.EAST);

        panelPropuestas.add(header, BorderLayout.NORTH);
        panelPrincipal.add(panelPropuestas, "propuestas");
    }

    private void crearPanelCrearPropuesta() {
        panelCrearPropuesta = new JPanel(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel titulo = new JLabel("Crear Nueva Propuesta");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centerPanel.add(titulo, gbc);

        // Campos
        JTextField tituloField = new JTextField(30);
        JTextArea descripcionArea = new JTextArea(5, 30);
        descripcionArea.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(descripcionArea);
        JTextField metaField = new JTextField(30);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; centerPanel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; centerPanel.add(tituloField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; centerPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; centerPanel.add(scrollDesc, gbc);
        gbc.gridx = 0; gbc.gridy = 3; centerPanel.add(new JLabel("Meta ($):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; centerPanel.add(metaField, gbc);

        // Botones
        JPanel botones = new JPanel();
        JButton btnCrear = new JButton("Crear Propuesta");
        JButton btnCancelar = new JButton("Cancelar");

        btnCrear.setBackground(new Color(76, 175, 80));
        btnCrear.setForeground(Color.WHITE);

        btnCrear.addActionListener(e -> {
            try {
                String tituloTexto = tituloField.getText();
                String descripcion = descripcionArea.getText();
                double meta = Double.parseDouble(metaField.getText());

                Propuesta nuevaPropuesta = new Propuesta(tituloTexto, descripcion, usuarioActual);
                nuevaPropuesta.setMeta(meta);
                propuestaController.crearPropuesta(nuevaPropuesta);

                JOptionPane.showMessageDialog(this, "Propuesta creada exitosamente");

                // Limpiar campos
                tituloField.setText("");
                descripcionArea.setText("");
                metaField.setText("");

                cardLayout.show(panelPrincipal, "dashboard");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> cardLayout.show(panelPrincipal, "dashboard"));

        botones.add(btnCrear);
        botones.add(btnCancelar);

        gbc.gridx = 1; gbc.gridy = 4;
        centerPanel.add(botones, gbc);

        panelCrearPropuesta.add(centerPanel, BorderLayout.CENTER);
        panelPrincipal.add(panelCrearPropuesta, "crearPropuesta");
    }

    private void crearPanelDetallePropuesta() {
        panelDetallePropuesta = new JPanel(new BorderLayout());
        panelPrincipal.add(panelDetallePropuesta, "detallePropuesta");
    }

    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(160, 40));
        boton.setBackground(Color.WHITE);
        // FIX: reemplazar por un borde válido
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        return boton;
    }

    private void actualizarDashboard() {
        if (usuarioActual != null) {
            // Actualizar información del usuario en el dashboard
            Component[] components = ((JPanel) panelDashboard.getComponent(0)).getComponents();
            if (components.length > 0 && components[0] instanceof JLabel) {
                ((JLabel) components[0]).setText("Bienvenido, " + usuarioActual.getNombre());
            }
        }
    }

    private void actualizarPropuestas() {
        // Limpiar panel anterior
        panelPropuestas.removeAll();

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Propuestas Disponibles");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(titulo, BorderLayout.WEST);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "dashboard"));
        header.add(btnVolver, BorderLayout.EAST);

        // Lista de propuestas
        JPanel listaPropuestas = new JPanel();
        listaPropuestas.setLayout(new BoxLayout(listaPropuestas, BoxLayout.Y_AXIS));

        for (Propuesta propuesta : propuestaController.getPropuestas()) {
            JPanel cardPropuesta = crearCardPropuesta(propuesta);
            listaPropuestas.add(cardPropuesta);
            listaPropuestas.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(listaPropuestas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        panelPropuestas.add(header, BorderLayout.NORTH);
        panelPropuestas.add(scrollPane, BorderLayout.CENTER);

        panelPropuestas.revalidate();
        panelPropuestas.repaint();
    }

    private JPanel crearCardPropuesta(Propuesta propuesta) {
        JPanel card = new JPanel(new BorderLayout());
        // FIX: borde válido
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                new EmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Información de la propuesta
        JPanel info = new JPanel(new GridLayout(4, 1));
        info.add(new JLabel("Título: " + propuesta.getTitulo()));
        info.add(new JLabel("Creador: " + propuesta.getCreador().getNombre()));
        info.add(new JLabel("Descripción: " +
                (propuesta.getDescripcion().length() > 100 ?
                        propuesta.getDescripcion().substring(0, 100) + "..." :
                        propuesta.getDescripcion())));

        // Barra de progreso
        double progreso = (propuesta.getRecaudado() / propuesta.getMeta()) * 100;
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) progreso);
        progressBar.setString(String.format("$%.2f / $%.2f (%.1f%%)",
                propuesta.getRecaudado(), propuesta.getMeta(), progreso));
        progressBar.setStringPainted(true);
        info.add(progressBar);

        // Botones de acción
        JPanel botones = new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));

        if (usuarioActual instanceof Inversionista || usuarioActual instanceof Donante) {
            JButton btnApoyo = new JButton("Apoyar Económicamente");
            btnApoyo.setBackground(new Color(76, 175, 80));
            btnApoyo.setForeground(Color.WHITE);
            btnApoyo.addActionListener(e -> mostrarDialogoApoyo(propuesta, "economico"));
            botones.add(btnApoyo);
        }

        if (usuarioActual instanceof Voluntario) {
            JButton btnVoluntario = new JButton("Ofrecer Ayuda");
            btnVoluntario.setBackground(new Color(255, 152, 0));
            btnVoluntario.setForeground(Color.WHITE);
            btnVoluntario.addActionListener(e -> mostrarDialogoApoyo(propuesta, "voluntario"));
            botones.add(btnVoluntario);
        }

        JButton btnDetalle = new JButton("Ver Detalle");
        btnDetalle.addActionListener(e -> mostrarDetallePropuesta(propuesta));
        botones.add(btnDetalle);

        card.add(info, BorderLayout.CENTER);
        card.add(botones, BorderLayout.EAST);

        return card;
    }

    private void mostrarDialogoApoyo(Propuesta propuesta, String tipo) {
        if (tipo.equals("economico")) {
            String input = JOptionPane.showInputDialog(this,
                    "Ingrese la cantidad a aportar:",
                    "Apoyo Económico",
                    JOptionPane.QUESTION_MESSAGE);

            if (input != null && !input.trim().isEmpty()) {
                try {
                    double cantidad = Double.parseDouble(input);
                    if (cantidad > 0) {
                        propuesta.agregarRecaudacion(cantidad);

                        if (usuarioActual instanceof Inversionista) {
                            propuestaController.registrarInversion(propuesta, (Inversionista) usuarioActual);
                        } else if (usuarioActual instanceof Donante) {
                            propuestaController.registrarDonacion(propuesta, (Donante) usuarioActual);
                        }

                        JOptionPane.showMessageDialog(this,
                                "¡Gracias por tu apoyo de $" + cantidad + "!");
                        actualizarPropuestas();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Cantidad inválida");
                }
            }
        } else if (tipo.equals("voluntario")) {
            String[] opciones = {"Habilidades técnicas", "Trabajo gratuito", "Mentoría", "Consultoría"};
            String seleccion = (String) JOptionPane.showInputDialog(this,
                    "Seleccione el tipo de apoyo que desea ofrecer:",
                    "Apoyo Voluntario",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (seleccion != null) {
                propuestaController.registrarApoyo(propuesta, (Voluntario) usuarioActual);
                JOptionPane.showMessageDialog(this,
                        "¡Gracias por ofrecer " + seleccion + "!");
                actualizarPropuestas();
            }
        }
    }

    private void mostrarDetallePropuesta(Propuesta propuesta) {
        // Crear ventana de detalle
        JFrame ventanaDetalle = new JFrame("Detalle de Propuesta");
        ventanaDetalle.setSize(600, 400);
        ventanaDetalle.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Título: " + propuesta.getTitulo()));
        panel.add(new JLabel("Creador: " + propuesta.getCreador().getNombre()));
        panel.add(new JLabel("Descripción: " + propuesta.getDescripcion()));
        panel.add(new JLabel("Meta: $" + propuesta.getMeta()));
        panel.add(new JLabel("Recaudado: $" + propuesta.getRecaudado()));

        ventanaDetalle.add(panel);
        ventanaDetalle.setVisible(true);
    }

    private void crearDatosDePrueba() {
        Emprendedor e1 = new Emprendedor("Carlos López", "carlos@mail.com", "123", "Guatemala", "Reciclaje de Plástico");
        Emprendedor e2 = new Emprendedor("María Pérez", "maria@mail.com", "123", "Antigua", "Energía Solar");

        Propuesta p1 = new Propuesta("Proyecto Reciclaje", "Convertir plástico en materiales de construcción.", e1);
        p1.setMeta(5000);
        p1.agregarRecaudacion(1200);

        Propuesta p2 = new Propuesta("Energía Solar Rural", "Instalación de paneles solares en áreas rurales.", e2);
        p2.setMeta(10000);
        p2.agregarRecaudacion(3000);

        propuestaController.crearPropuesta(p1);
        propuestaController.crearPropuesta(p2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI_Plataforma ventana = new GUI_Plataforma();
            ventana.setVisible(true);
        });
    }
}
