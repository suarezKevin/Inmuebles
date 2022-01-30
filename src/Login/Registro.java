package Login;

import Usuarios.Propietario;
import gui.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Jeni
 */
public class Registro extends javax.swing.JFrame {

    Propietario p = new Propietario();
    String sql = "";
    Conexion cnx = new Conexion();
    Connection cn = cnx.conectar();
    String cedulaProveedor;
    private int n = 0;

    public int getN() {
        return n;
    }

    /**
     * Creates new form Registro
     */
    public Registro() {
        initComponents();

        jLbProveedor.setVisible(false);
        jTxtCedProveedor.setVisible(false);
    }

    public String fechaActual() {
        //java.sql.Date fechaActual;
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/YYYY");
        //formato.format(fecha);
        //long f = fecha.getTime();
        //return fechaActual = new java.sql.Date(f);
        return formato.format(fecha);
    }

    public void agregarPropietarioBD() {
        try {
            agregarContraseniaUsuario();
            sql = "insert into propietarios(ced_pro,nom_pro,ape_pro,tel_pro,dir_pro)values(?,?,?,?,?)";
            PreparedStatement psd = cn.prepareStatement(sql);
            psd.setString(1, p.getCedula());
            psd.setString(2, p.getNombre());
            psd.setString(3, p.getApellido());
            psd.setString(4, p.getTelefono());
            psd.setString(5, p.getDireccion());
            n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "USUARIO CREADO CON EXITO !");
                this.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Faltan datos !");
        }
    }

    public void agregarInquilinoBD() {
        try {
            agregarContraseniaUsuario();
            sql = "insert into inquilinos(ced_inq,nom_inq,ape_inq,tel_inq,dir_inq)values(?,?,?,?,?)";
            PreparedStatement psd = cn.prepareStatement(sql);
            psd.setString(1, p.getCedula());
            psd.setString(2, p.getNombre());
            psd.setString(3, p.getApellido());
            psd.setString(4, p.getTelefono());
            psd.setString(5, p.getDireccion());
            n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "USUARIO CREADO CON EXITO !");
                this.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Faltan datos !");
        }
    }

    public void agregarSecretariaBD() {
        try {
            agregarDatosContrato();
            agregarContraseniaUsuario();
            sql = "insert into secretarias(cedula_sec,nombre_sec,apellido_sec,telefono_sec,direccion_sec)values(?,?,?,?,?)";
            PreparedStatement psd = cn.prepareStatement(sql);
            psd.setString(1, p.getCedula());
            psd.setString(2, p.getNombre());
            psd.setString(3, p.getApellido());
            psd.setString(4, p.getTelefono());
            psd.setString(5, p.getDireccion());
            n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "CUENTA CREADA CON EXITO !");
                this.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Faltan datos !");
        }
    }

    public void agregarDatosContrato() {
        try {
            String sql = "";
            sql = "insert into contrato_pro_sec(id_con,fec_ini_con,fec_fin_con,ced_pro_con,ced_sec_con,estado_contrato)values(?,?,?,?,?,?)";
            PreparedStatement psd = cn.prepareStatement(sql);
            psd.setInt(1, SQL.id_inrementable());
            psd.setString(2, fechaActual());
            psd.setString(3, "NULL");
            psd.setString(4, this.cedulaProveedor);
            psd.setString(5, p.getCedula());
            psd.setString(6, "YES");
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Contrato Creado !");

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public boolean buscarProveedor() {
        boolean encontrarCed = false;
        try {
            System.out.println(this.cedulaProveedor);
            String sql = "";
            sql = "select ced_pro, nom_pro, ape_pro from propietarios where ced_pro=" + this.cedulaProveedor;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (cedulaProveedor.equals(rs.getString("ced_pro"))) {
                    JOptionPane.showMessageDialog(null, "Su contrato es con: " + rs.getString("nom_pro") + rs.getString("ape_pro"));
                    encontrarCed = true;
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "La cedula del Proveedor no se encuentra !");
        }
        return encontrarCed;
    }

    public void agregarContraseniaUsuario() {
        try {
            String sql1 = "";
            sql1 = "insert into usuarios(usuario,fec_abre_cuen_usu,fec_cierra_cuen_usu,contrasenia,cargo_usu,estado_usu,ced_usu_per)values(?,?,?,?,?,?,?)";
            PreparedStatement psd = cn.prepareStatement(sql1);
            psd.setString(1, p.getUsuario());
            psd.setString(2, fechaActual());
            psd.setString(3, "NULL");
            psd.setString(4, p.getContrasenia());
            psd.setString(5, p.getCargo());
            psd.setString(6, "YES");
            psd.setString(7, p.getCedula());
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Usuario Creado!");
                this.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void crearCuenta() {
        p.setCedula(jTxtCedula.getText());
        p.setNombre(jTxtNombre.getText());
        p.setApellido(jTxtApellido.getText());
        p.setDireccion(jTxtDireccion.getText());
        p.setTelefono(jTxtTelefono.getText());
        p.setUsuario(jTxtUsuario.getText());
        p.setContrasenia(jPsdContrasenia.getText());
        p.setCargo(jCmbxCargo.getSelectedItem().toString());
        this.cedulaProveedor = jTxtCedProveedor.getText();
        if (p.getCargo().equals("Propietario")) {
            agregarPropietarioBD();
        } else if (p.getCargo().equals("Inquilino")) {
            agregarInquilinoBD();
        } else if (p.getCargo().equals("Secretaria")) {
            if (buscarProveedor()) {
                agregarSecretariaBD();
            }
        }
    }

    public void verificarContenidoTxt() {
        if (jTxtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la Cedula");
            jTxtCedula.requestFocus();
        } else if (jTxtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre");
            jTxtNombre.requestFocus();
        } else if (jTxtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Apellido");
            jTxtApellido.requestFocus();
        } else if (jTxtTelefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Telefono");
            jTxtTelefono.requestFocus();
        } else if (jTxtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la Direccion");
            jTxtDireccion.requestFocus();
        } else if (jTxtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre de Usuario");
            jTxtUsuario.requestFocus();
        } else if (jPsdContrasenia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una Contraseña");
            jPsdContrasenia.requestFocus();
        } else if (jCmbxCargo.getSelectedItem().toString().equals("Seleccione Uno")) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Cargo");
            jCmbxCargo.requestFocus();
        } else {
            crearCuenta();
        }
    }

    public void habilitarCampoCedulaProovedor() {
        String cargo = jCmbxCargo.getSelectedItem().toString();
        if (cargo.equals("Secretaria")) {
            jLbProveedor.setVisible(true);
            jTxtCedProveedor.setVisible(true);
        } else {
            jLbProveedor.setVisible(false);
            jTxtCedProveedor.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTxtCedula = new javax.swing.JTextField();
        jTxtNombre = new javax.swing.JTextField();
        jTxtApellido = new javax.swing.JTextField();
        jTxtTelefono = new javax.swing.JTextField();
        jTxtDireccion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTxtUsuario = new javax.swing.JTextField();
        jPsdContrasenia = new javax.swing.JPasswordField();
        jCmbxCargo = new javax.swing.JComboBox<>();
        jLbProveedor = new javax.swing.JLabel();
        jTxtCedProveedor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jBtnCrearCuenta = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(19, 56, 190));
        setForeground(java.awt.Color.darkGray);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(19, 56, 190));
        jLabel2.setText("Cédula:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(19, 56, 190));
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(19, 56, 190));
        jLabel4.setText("Apellido:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(19, 56, 190));
        jLabel5.setText("Direccion:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(19, 56, 190));
        jLabel6.setText("Telefono:");

        jTxtCedula.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N

        jTxtNombre.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N

        jTxtApellido.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N

        jTxtTelefono.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N

        jTxtDireccion.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jTxtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtDireccionActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(19, 56, 190));
        jLabel7.setText("Cargo:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(19, 56, 190));
        jLabel8.setText("Usuario:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(19, 56, 190));
        jLabel9.setText("Contraseña:");

        jTxtUsuario.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N

        jPsdContrasenia.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N

        jCmbxCargo.setEditable(true);
        jCmbxCargo.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jCmbxCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCmbxCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbxCargoActionPerformed(evt);
            }
        });

        jLbProveedor.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLbProveedor.setForeground(new java.awt.Color(19, 56, 190));
        jLbProveedor.setText("Cédula Proveedor: ");

        jTxtCedProveedor.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(19, 56, 190));
        jLabel1.setText("Resgistro");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/verificar.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxtDireccion)
                            .addComponent(jTxtTelefono)
                            .addComponent(jTxtApellido)
                            .addComponent(jTxtNombre)
                            .addComponent(jTxtCedula)
                            .addComponent(jTxtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPsdContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTxtCedProveedor)
                        .addComponent(jLbProveedor)
                        .addComponent(jLabel7)
                        .addComponent(jCmbxCargo, 0, 140, Short.MAX_VALUE))
                    .addComponent(jLabel11))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLbProveedor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jTxtCedProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCmbxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPsdContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel2.setBackground(new java.awt.Color(19, 56, 190));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jBtnCrearCuenta.setBackground(new java.awt.Color(255, 255, 255));
        jBtnCrearCuenta.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jBtnCrearCuenta.setText("Crear Cuenta");
        jBtnCrearCuenta.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnCrearCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCrearCuentaActionPerformed(evt);
            }
        });

        jBtnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnCancelar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnCrearCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jBtnCrearCuenta)
                .addGap(18, 18, 18)
                .addComponent(jBtnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnCrearCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCrearCuentaActionPerformed
        verificarContenidoTxt();
        if (this.n == 1) {
            Login l = new Login();
            l.setVisible(true);

    }//GEN-LAST:event_jBtnCrearCuentaActionPerformed
    }
    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        this.dispose();
        Login l = new Login();
        l.setVisible(true);

    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jCmbxCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbxCargoActionPerformed
        habilitarCampoCedulaProovedor();
    }//GEN-LAST:event_jCmbxCargoActionPerformed

    private void jTxtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDireccionActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnCrearCuenta;
    private javax.swing.JComboBox<String> jCmbxCargo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLbProveedor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPsdContrasenia;
    private javax.swing.JTextField jTxtApellido;
    private javax.swing.JTextField jTxtCedProveedor;
    private javax.swing.JTextField jTxtCedula;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtTelefono;
    private javax.swing.JTextField jTxtUsuario;
    // End of variables declaration//GEN-END:variables
}
