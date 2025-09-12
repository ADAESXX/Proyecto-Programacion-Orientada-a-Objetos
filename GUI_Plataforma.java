// ===== Versión SIN try-catch =====
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI_Plataforma {
    private JFrame frame;
    private JTabbedPane tabbedPane;

    private java.util.List<Usuario> usuarios;
    private PropuestaController propuestaController;
    private MensajeController mensajeController;
    private NotificacionController notificacionController;

    public GUI_Plataforma() {
        usuarios = new ArrayList<>();
        propuestaController = new PropuestaController();
        mensajeController = new MensajeController();
        notificacionController = new NotificacionController();
        initComponents();
    }

    private void initComponents() {
        frame = new JFrame("Plataforma Colaborativa - Sin TryCatch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Usuarios", crearPanelUsuarios());
        tabbedPane.addTab("Propuestas", crearPanelPropuestas());
        tabbedPane.addTab("Mensajes", crearPanelMensajes());
        tabbedPane.addTab("Notificaciones", crearPanelNotificaciones());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new GridLayout(0,2));

        JTextField nombreField = new JTextField();
        JTextField correoField = new JTextField();
        JTextField contrasenaField = new JTextField();
        JTextField ubicacionField = new JTextField();
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Donante","Inversionista","Emprendedor","Estudiante","Voluntario"});
        JTextField extraField = new JTextField();
        JLabel extraLabel = new JLabel("Dato extra (carrera/nombre proyecto):");

        JButton addButton = new JButton("Agregar Usuario");
        addButton.addActionListener(e -> {
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String contrasena = contrasenaField.getText();
            String ubicacion = ubicacionField.getText();
            String tipo = (String) tipoCombo.getSelectedItem();

            Usuario u;
            switch(tipo){
                case "Donante" -> u = new Donante(nombre, correo, contrasena, ubicacion);
                case "Inversionista" -> u = new Inversionista(nombre, correo, contrasena, ubicacion);
                case "Emprendedor" -> u = new Emprendedor(nombre, correo, contrasena, ubicacion, extraField.getText());
                case "Estudiante" -> u = new Estudiante(nombre, correo, contrasena, ubicacion, extraField.getText());
                case "Voluntario" -> u = new Voluntario(nombre, correo, contrasena, ubicacion);
                default -> throw new IllegalArgumentException("Tipo inválido");
            }
            usuarios.add(u);
            JOptionPane.showMessageDialog(frame, "Usuario agregado: " + u.getNombre());
        });

        panel.add(new JLabel("Nombre:")); panel.add(nombreField);
        panel.add(new JLabel("Correo:")); panel.add(correoField);
        panel.add(new JLabel("Contraseña:")); panel.add(contrasenaField);
        panel.add(new JLabel("Ubicación:")); panel.add(ubicacionField);
        panel.add(new JLabel("Tipo:")); panel.add(tipoCombo);
        panel.add(extraLabel); panel.add(extraField);
        panel.add(addButton);

        return panel;
    }

    private JPanel crearPanelPropuestas() {
        JPanel panel = new JPanel(new GridLayout(0,2));
        JTextField tituloField = new JTextField();
        JTextField descField = new JTextField();
        JComboBox<Usuario> creadorCombo = new JComboBox<>();
        JButton addButton = new JButton("Crear Propuesta");

        addButton.addActionListener(e -> {
            Propuesta p = new Propuesta(tituloField.getText(), descField.getText(), (Usuario) creadorCombo.getSelectedItem());
            propuestaController.crearPropuesta(p);
            JOptionPane.showMessageDialog(frame, "Propuesta creada: " + p.getTitulo());
        });

        panel.add(new JLabel("Título:")); panel.add(tituloField);
        panel.add(new JLabel("Descripción:")); panel.add(descField);
        panel.add(new JLabel("Creador:")); panel.add(creadorCombo);
        panel.add(addButton);

        JButton refreshButton = new JButton("Actualizar Lista Usuarios");
        refreshButton.addActionListener(e -> {
            creadorCombo.removeAllItems();
            for (Usuario u : usuarios) creadorCombo.addItem(u);
        });
        panel.add(refreshButton);

        return panel;
    }

    private JPanel crearPanelMensajes() {
        JPanel panel = new JPanel(new GridLayout(0,2));
        JComboBox<Usuario> emisorCombo = new JComboBox<>();
        JComboBox<Usuario> receptorCombo = new JComboBox<>();
        JTextField contenidoField = new JTextField();
        JButton enviarButton = new JButton("Enviar Mensaje");

        enviarButton.addActionListener(e -> {
            Mensaje m = new Mensaje((Usuario) emisorCombo.getSelectedItem(), (Usuario) receptorCombo.getSelectedItem(), contenidoField.getText());
            mensajeController.enviarMensaje(m);
            JOptionPane.showMessageDialog(frame, "Mensaje enviado");
        });

        panel.add(new JLabel("Emisor:")); panel.add(emisorCombo);
        panel.add(new JLabel("Receptor:")); panel.add(receptorCombo);
        panel.add(new JLabel("Contenido:")); panel.add(contenidoField);
        panel.add(enviarButton);

        JButton refreshButton = new JButton("Actualizar Listas");
        refreshButton.addActionListener(e -> {
            emisorCombo.removeAllItems();
            receptorCombo.removeAllItems();
            for (Usuario u : usuarios) {
                emisorCombo.addItem(u);
                receptorCombo.addItem(u);
            }
        });
        panel.add(refreshButton);
        return panel;
    }

    private JPanel crearPanelNotificaciones() {
        JPanel panel = new JPanel(new GridLayout(0,2));
        JComboBox<Usuario> receptorCombo = new JComboBox<>();
        JTextField mensajeField = new JTextField();
        JButton generarButton = new JButton("Generar Notificación");

        generarButton.addActionListener(e -> {
            String msg = notificacionController.generarNotificacion((Usuario) receptorCombo.getSelectedItem(), mensajeField.getText());
            JOptionPane.showMessageDialog(frame, msg);
        });

        panel.add(new JLabel("Receptor:")); panel.add(receptorCombo);
        panel.add(new JLabel("Mensaje:")); panel.add(mensajeField);
        panel.add(generarButton);

        JButton refreshButton = new JButton("Actualizar Usuarios");
        refreshButton.addActionListener(e -> {
            receptorCombo.removeAllItems();
            for (Usuario u : usuarios) receptorCombo.addItem(u);
        });
        panel.add(refreshButton);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI_Plataforma::new);
    }
}

