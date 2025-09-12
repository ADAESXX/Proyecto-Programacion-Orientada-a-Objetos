//Mejore la distribución del GUI de igual forma le agregue bloques TryCatch para que no tenga errores

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;



public class GUI_Plataforma {
    private JFrame frame;

    // Controladores / Listas simples para la demo
    private PropuestaController propuestaController = new PropuestaController();
    private MensajeController mensajeController = new MensajeController();
    private NotificacionController notificacionController = new NotificacionController();

    // Modelos para JList
    private DefaultListModel<Usuario> usuariosModel = new DefaultListModel<>();
    private DefaultListModel<Propuesta> propuestasModel = new DefaultListModel<>();
    private DefaultListModel<Mensaje> mensajesModel = new DefaultListModel<>();
    private DefaultListModel<Notificaciones> notifsModel = new DefaultListModel<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI_Plataforma().initialize());
    }

    private void initialize() {
        frame = new JFrame("Plataforma - GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Usuarios", usuariosPanel());
        tabs.addTab("Propuestas", propuestasPanel());
        tabs.addTab("Mensajes", mensajesPanel());
        tabs.addTab("Notificaciones", notificacionesPanel());

        frame.getContentPane().add(tabs);
        frame.setVisible(true);
    }

    // Panel para gestionar usuarios
    private JPanel usuariosPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Formulario para crear usuario
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Tipo:"), c);
        String[] tipos = {"Usuario base", "Donante", "Inversionista", "Emprendedor", "Estudiante", "Voluntario"};
        JComboBox<String> tipoBox = new JComboBox<>(tipos);
        c.gridx = 1; form.add(tipoBox, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Nombre:"), c);
        JTextField nombreF = new JTextField(); c.gridx = 1; form.add(nombreF, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Correo:"), c);
        JTextField correoF = new JTextField(); c.gridx = 1; form.add(correoF, c);

        c.gridx = 0; c.gridy = 3; form.add(new JLabel("Contraseña:"), c);
        JPasswordField passF = new JPasswordField(); c.gridx = 1; form.add(passF, c);

        c.gridx = 0; c.gridy = 4; form.add(new JLabel("Ubicación:"), c);
        JTextField ubicF = new JTextField(); c.gridx = 1; form.add(ubicF, c);

        c.gridx = 0; c.gridy = 5; form.add(new JLabel("Proyecto (opt):"), c);
        JTextField proyectoF = new JTextField(); c.gridx = 1; form.add(proyectoF, c);

        JButton crearBtn = new JButton("Crear usuario");
        c.gridx = 0; c.gridy = 6; c.gridwidth = 2; form.add(crearBtn, c);

        panel.add(form, BorderLayout.NORTH);

        // Lista de usuarios
        JList<Usuario> usuariosList = new JList<>(usuariosModel);
        usuariosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(usuariosList), BorderLayout.CENTER);

        crearBtn.addActionListener(e -> {
            try {
                String tipo = (String) tipoBox.getSelectedItem();
                String nombre = nombreF.getText();
                String correo = correoF.getText();
                String pass = new String(passF.getPassword());
                String ubic = ubicF.getText();

                Usuario u = null;
                switch (tipo) {
                    case "Donante":
                        u = new Donante(nombre, correo, pass, ubic);
                        break;
                    case "Inversionista":
                        u = new Inversionista(nombre, correo, pass, ubic);
                        break;
                    case "Emprendedor":
                        String nomProyecto = proyectoF.getText().isBlank() ? "Proyecto inicial" : proyectoF.getText();
                        u = new Emprendedor(nombre, correo, pass, ubic, nomProyecto);
                        break;
                    case "Estudiante":
                        u = new Estudiante(nombre, correo, pass, ubic, "Sin especificar");
                        break;
                    case "Voluntario":
                        u = new Voluntario(nombre, correo, pass, ubic);
                        break;
                    default:
                        u = new Usuario(nombre, correo, pass, ubic);
                }

                usuariosModel.addElement(u);
                JOptionPane.showMessageDialog(frame, "Usuario creado: " + u.getNombre());

                // limpiar
                nombreF.setText(""); correoF.setText(""); passF.setText(""); ubicF.setText(""); proyectoF.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Right-click menu para ver detalles y eliminar
        JPopupMenu menu = new JPopupMenu();
        JMenuItem ver = new JMenuItem("Ver detalles");
        JMenuItem eliminar = new JMenuItem("Eliminar usuario");
        menu.add(ver); menu.add(eliminar);

        usuariosList.setComponentPopupMenu(menu);
        usuariosList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int idx = usuariosList.locationToIndex(e.getPoint());
                    usuariosList.setSelectedIndex(idx);
                }
            }
        });

        ver.addActionListener(e -> {
            Usuario sel = usuariosList.getSelectedValue();
            if (sel != null) {
                JOptionPane.showMessageDialog(frame, sel.toString(), "Detalles usuario", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        eliminar.addActionListener(e -> {
            Usuario sel = usuariosList.getSelectedValue();
            if (sel != null) usuariosModel.removeElement(sel);
        });

        return panel;
    }

    // Panel para propuestas
    private JPanel propuestasPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));

        JPanel top = new JPanel(new GridLayout(2, 1, 4, 4));

        JPanel crear = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4); c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx=0; c.gridy=0; crear.add(new JLabel("Título:"), c);
        JTextField tituloF = new JTextField(); c.gridx=1; crear.add(tituloF, c);

        c.gridx=0; c.gridy=1; crear.add(new JLabel("Descripción:"), c);
        JTextField descF = new JTextField(); c.gridx=1; crear.add(descF, c);

        JButton crearP = new JButton("Crear propuesta"); c.gridx=0; c.gridy=2; c.gridwidth=2; crear.add(crearP,c);

        top.add(crear);

        // Panel para relacionar usuarios y propuestas (donar, invertir, apoyar)
        JPanel acciones = new JPanel(new GridBagLayout());
        GridBagConstraints a = new GridBagConstraints(); a.insets=new Insets(4,4,4,4); a.fill=GridBagConstraints.HORIZONTAL;

        a.gridx=0; a.gridy=0; acciones.add(new JLabel("Propuesta:"), a);
        JList<Propuesta> propuestasList = new JList<>(propuestasModel); propuestasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        a.gridx=1; acciones.add(new JScrollPane(propuestasList), a);

        a.gridx=0; a.gridy=1; acciones.add(new JLabel("Usuario (seleccione):"), a);
        JList<Usuario> usuariosList = new JList<>(usuariosModel); usuariosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        a.gridx=1; acciones.add(new JScrollPane(usuariosList), a);

        JPanel btns = new JPanel(new FlowLayout());
        JButton btnDonar = new JButton("Registrar donación");
        JButton btnInvertir = new JButton("Registrar inversión");
        JButton btnApoyar = new JButton("Registrar apoyo(vol.)");
        btns.add(btnDonar); btns.add(btnInvertir); btns.add(btnApoyar);
        a.gridx=0; a.gridy=2; a.gridwidth=2; acciones.add(btns, a);

        top.add(acciones);

        panel.add(top, BorderLayout.NORTH);

        panel.add(new JScrollPane(new JList<>(propuestasModel)), BorderLayout.CENTER);

        crearP.addActionListener(e -> {
            try {
                String tit = tituloF.getText();
                String des = descF.getText();
                // usar un creador genérico: si hay usuarios, tomar el primero como creador; sino crear usuario base temporal
                Usuario creador = usuariosModel.isEmpty() ? new Usuario("anonimo","anon@local","123456","-") : usuariosModel.get(0);
                Propuesta p = new Propuesta(tit, des, creador);
                propuestaController.crearPropuesta(p);
                propuestasModel.addElement(p);
                JOptionPane.showMessageDialog(frame, "Propuesta creada: " + tit);
                tituloF.setText(""); descF.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDonar.addActionListener(e -> {
            try {
                Propuesta p = propuestasList.getSelectedValue();
                Usuario u = usuariosList.getSelectedValue();
                if (p==null || u==null) throw new IllegalArgumentException("Seleccione propuesta y usuario");
                if (!(u instanceof Donante)) throw new IllegalArgumentException("El usuario seleccionado no es un Donante");
                propuestaController.registrarDonacion(p, (Donante)u);
                notificacionController.generarNotificacion(p.getCreador(), "Tu proyecto '" + p.getTitulo() + "' recibió una donación de " + u.getNombre());
                refreshNotifsModel();
                JOptionPane.showMessageDialog(frame, "Donación registrada");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnInvertir.addActionListener(e -> {
            try {
                Propuesta p = propuestasList.getSelectedValue();
                Usuario u = usuariosList.getSelectedValue();
                if (p==null || u==null) throw new IllegalArgumentException("Seleccione propuesta y usuario");
                if (!(u instanceof Inversionista)) throw new IllegalArgumentException("El usuario seleccionado no es un Inversionista");
                propuestaController.registrarInversion(p, (Inversionista)u);
                notificacionController.generarNotificacion(p.getCreador(), "Tu proyecto '" + p.getTitulo() + "' recibió inversión de " + u.getNombre());
                refreshNotifsModel();
                JOptionPane.showMessageDialog(frame, "Inversión registrada");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnApoyar.addActionListener(e -> {
            try {
                Propuesta p = propuestasList.getSelectedValue();
                Usuario u = usuariosList.getSelectedValue();
                if (p==null || u==null) throw new IllegalArgumentException("Seleccione propuesta y usuario");
                if (!(u instanceof Voluntario)) throw new IllegalArgumentException("El usuario seleccionado no es un Voluntario");
                propuestaController.registrarApoyo(p, (Voluntario)u);
                notificacionController.generarNotificacion(p.getCreador(), "Tu proyecto '" + p.getTitulo() + "' recibió apoyo de voluntario " + u.getNombre());
                refreshNotifsModel();
                JOptionPane.showMessageDialog(frame, "Apoyo registrado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    // Panel para mensajes
    private JPanel mensajesPanel() {
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(new EmptyBorder(8,8,8,8));

        JPanel top = new JPanel(new GridLayout(1,2,8,8));

        // Envío de mensaje
        JPanel send = new JPanel(new GridBagLayout());
        GridBagConstraints s = new GridBagConstraints(); s.insets=new Insets(4,4,4,4); s.fill=GridBagConstraints.HORIZONTAL;
        s.gridx=0; s.gridy=0; send.add(new JLabel("Emisor:"), s);
        JList<Usuario> usuariosListEm = new JList<>(usuariosModel); usuariosListEm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); s.gridx=1; send.add(new JScrollPane(usuariosListEm), s);

        s.gridx=0; s.gridy=1; send.add(new JLabel("Receptor:"), s);
        JList<Usuario> usuariosListRec = new JList<>(usuariosModel); usuariosListRec.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); s.gridx=1; send.add(new JScrollPane(usuariosListRec), s);

        s.gridx=0; s.gridy=2; send.add(new JLabel("Contenido:"), s);
        JTextField contenF = new JTextField(); s.gridx=1; send.add(contenF, s);

        JButton enviar = new JButton("Enviar mensaje"); s.gridx=0; s.gridy=3; s.gridwidth=2; send.add(enviar, s);

        top.add(send);

        // Lista de mensajes
        JList<Mensaje> mensajesList = new JList<>(mensajesModel);
        top.add(new JScrollPane(mensajesList));

        panel.add(top, BorderLayout.CENTER);

        enviar.addActionListener(e -> {
            try {
                Usuario em = usuariosListEm.getSelectedValue();
                Usuario rec = usuariosListRec.getSelectedValue();
                String cont = contenF.getText();
                if (em==null || rec==null) throw new IllegalArgumentException("Seleccione emisor y receptor");
                Mensaje m = new Mensaje(em, rec, cont);
                mensajeController.enviarMensaje(m);
                mensajesModel.addElement(m);
                notificacionController.generarNotificacion(rec, "Nuevo mensaje de " + em.getNombre());
                refreshNotifsModel();
                contenF.setText("");
                JOptionPane.showMessageDialog(frame, "Mensaje enviado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel notificacionesPanel() {
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(new EmptyBorder(8,8,8,8));

        JList<Notificaciones> list = new JList<>(notifsModel);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout());
        JButton verTodas = new JButton("Marcar todas leídas (usuario seleccionado)");
        JButton refrescar = new JButton("Refrescar");
        bottom.add(verTodas); bottom.add(refrescar);
        panel.add(bottom, BorderLayout.SOUTH);

        verTodas.addActionListener(e -> {
            try {
                // Pedir correo del usuario para marcar
                String correo = JOptionPane.showInputDialog(frame, "Ingrese correo del usuario a marcar como leído:");
                if (correo == null) return;
                // buscar usuario
                Usuario sel = null;
                for (int i=0;i<usuariosModel.size();i++) {
                    Usuario u = usuariosModel.get(i);
                    if (u.getCorreo().equalsIgnoreCase(correo.trim())) { sel = u; break; }
                }
                if (sel == null) throw new IllegalArgumentException("Usuario no encontrado");
                notificacionController.marcarTodasLeidas(sel);
                refreshNotifsModel();
                JOptionPane.showMessageDialog(frame, "Notificaciones marcadas leídas para " + sel.getNombre());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        refrescar.addActionListener(e -> refreshNotifsModel());

        return panel;
    }

    // Actualiza el modelo de notificaciones desde el controller
    private void refreshNotifsModel() {
        notifsModel.clear();
        // Si se tuviera una forma de obtener todas las notificaciones desde NotificacionController
        // (en el código de ejemplo no hay getter), hacemos una aproximación si existe.
        try {
            // intento por reflejar notificaciones internas si existe getTodosNotificaciones()
            java.lang.reflect.Method m = notificacionController.getClass().getMethod("obtenerNotificaciones", Usuario.class);
            // como no conocemos el usuario objetivo, agregamos todas las notificaciones que ya generamos
            // aquí iteramos usuarios y pedimos notifs por usuario
            for (int i=0;i<usuariosModel.size();i++){
                Usuario u = usuariosModel.get(i);
                java.util.ArrayList lista = (ArrayList) m.invoke(notificacionController, u);
                for (Object o : lista) {
                    if (o instanceof Notificaciones) notifsModel.addElement((Notificaciones)o);
                }
            }
        } catch (NoSuchMethodException ns) {
            // si no existe el método, no hacemos nada
        } catch (Exception ex) {
            // ignorar errores de reflexión en la demo
        }
    }
}
