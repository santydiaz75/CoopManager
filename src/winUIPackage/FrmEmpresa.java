/*
 * FrmBanco.java
 *
 * Created on __DATE__, __TIME__
 */
package winUIPackage;

import java.awt.AWTKeyStroke;
import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Transaction;

import sessionPackage.MySession;
import entitiesPackage.Empresas;
import entitiesPackage.Empresascuentas;
import entitiesPackage.EmpresascuentasId;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;

/**
 *
 * @author SANTI DIAZ
 */
public class FrmEmpresa extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;

    public class CuentasTableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        final public static int NormalType = 0;
        final public static int Float2Type = 1;
        final public static int Float4Type = 2;
        final public static int ComboType = 3;
        final public static int CheckType = 4;
        final public static int StateType = 5;

        final public static String NewLine = "[Adding]";
        final public static String EditLine = "[Editing]";
        final public static String DeleteLine = "[Deleting]";

        final public static int columnSelect = 0;
        final public static int columnState = 1;
        final public static int columnCuentaBancaria = 2;
        final public static int columnNombreBanco = 3;
        final public static int columnCuentaContable = 4;

        class ColumnData {

            public String m_name;
            public String m_title;
            public int m_width;
            public int m_alignment;
            public int m_editortype;
            public Object m_editor;
            public String m_format;

            public ColumnData(String name, String title, int width,
                    int alignment, int editortype, Object editor, String format) {
                m_name = name;
                m_title = title;
                m_width = width;
                m_alignment = alignment;
                m_editortype = editortype;
                m_editor = editor;
                m_format = format;
            }
        }

        private Vector<ColumnData> columns;

        public CuentasTableModel() {

            super(null, new String[]{"Select", "State", "CuentaBancaria",
                "NombreBanco", "CuentaContable"});

            columns = new Vector<ColumnData>();
            columns.add(new ColumnData("Select", "",
                    EntityType.SelectStateWidth, SwingConstants.CENTER,
                    CheckType, null, null));
            columns.add(new ColumnData("State", "",
                    EntityType.SelectStateWidth, SwingConstants.CENTER,
                    StateType, null, null));
            columns.add(new ColumnData("CuentaBancaria", "Cuenta bancaria",
                    EntityType.MediumTextWidth, SwingConstants.LEFT,
                    NormalType, null, null));
            columns.add(new ColumnData("NombreBanco", "Nombre banco",
                    EntityType.MediumTextWidth, SwingConstants.LEFT,
                    NormalType, null, null));
            columns.add(new ColumnData("CuentaContable", "Cuenta contable",
                    EntityType.ShortTextWidth, SwingConstants.LEFT, NormalType,
                    null, null));

            this.addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    if (!changingState && !addLine) {
                        changeEditState();
                    }
                }
            });
        }

        @Override
        public Object getValueAt(int fila, int columna) {
            switch (columna) {
                case columnCuentaBancaria: {
                    if (super.getValueAt(fila, columna) != null) {
                        if (!super.getValueAt(fila, columna).equals("")) {
                            String value = super.getValueAt(fila, columna)
                                    .toString();
                            return value;
                        } else {
                            return "";
                        }
                    }
                    return "";
                }
                case columnNombreBanco: {
                    if (super.getValueAt(fila, columna) != null) {
                        if (!super.getValueAt(fila, columna).equals("")) {
                            String value = super.getValueAt(fila, columna)
                                    .toString();
                            return value;
                        } else {
                            return "";
                        }
                    }
                    return "";
                }
                case columnCuentaContable: {
                    if (super.getValueAt(fila, columna) != null) {
                        if (!super.getValueAt(fila, columna).equals("")) {
                            String value = super.getValueAt(fila, columna)
                                    .toString();
                            return value;
                        } else {
                            return "";
                        }
                    }
                    return "";
                }
            }
            return super.getValueAt(fila, columna);
        }

    }

    Empresas empresa;

    static boolean changingState = false;
    static boolean addLine = false;

    private static java.awt.Frame parentFrame;
    private MySession session;
    private boolean OnNew = false;
    private Entity entity = new Entity();

    public java.awt.Frame getParentFrame() {
        return FrmEmpresa.parentFrame;
    }

    public void setParentFrame(java.awt.Frame parentFrame) {
        FrmEmpresa.parentFrame = parentFrame;
    }

    public void setSession(MySession session) {
        this.session = session;
    }

    public MySession getSession() {
        return session;
    }

    /**
     * Creates new form FrmBanco
     */
    public FrmEmpresa() {
        initComponents();
    }

    public FrmEmpresa(java.awt.Frame parent, MySession session) {
        try {
            initComponents();
            FrmEmpresa.parentFrame = parent;
            this.setSession(session);
            entity.setSession(session);
            configureKeys();

        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame, "FrmEmpresa()", he);
        }
    }

    private void configureKeys() {
        try {
            Set<AWTKeyStroke> teclasTab = new HashSet<AWTKeyStroke>();
            teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
            teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
            teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0));
            pnlData.setFocusTraversalKeys(
                    KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, teclasTab);

        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame, "FrmEmpresa.configureKeys",
                    he);
        }
    }

    private void prepareTable() {
        try {
            CuentasTableModel modelCuentas = new CuentasTableModel();
            tblCuentas.setModel(modelCuentas);
            tblCuentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            for (int k = 0; k < modelCuentas.columns.size(); k++) {

                Object renderer = null;
                DefaultCellEditor editor = null;

                Integer editortype = modelCuentas.columns.get(k).m_editortype;
                switch (editortype) {
                    case CuentasTableModel.NormalType: {
                        renderer = new DefaultTableCellRenderer();
                        ((DefaultTableCellRenderer) renderer)
                                .setHorizontalAlignment(modelCuentas.columns.get(k).m_alignment);

                        final JTextField field = new JTextField();
                        editor = new DefaultCellEditor(field);
                        editor.setClickCountToStart(1);
                        field.addFocusListener(new FocusAdapter() {
                            public void focusGained(FocusEvent e) {
                                field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
                            }

                            public void focusLost(FocusEvent e) {
                                field.select(0, 0);//De-selecciono el texto al perder el foco.
                            }
                        });
                        break;
                    }
                    case CuentasTableModel.Float2Type: {
                        renderer = new Float2Renderer();
                        final JFormattedTextField field = (JFormattedTextField) modelCuentas.columns
                                .get(k).m_editor;
                        editor = new DefaultCellEditor(field);
                        editor.setClickCountToStart(1);
                        field.addFocusListener(new FocusAdapter() {
                            public void focusGained(FocusEvent e) {
                                field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
                            }

                            public void focusLost(FocusEvent e) {
                                field.select(0, 0);//De-selecciono el texto al perder el foco.
                            }
                        });
                        break;
                    }
                    case CuentasTableModel.Float4Type: {
                        renderer = new Float4Renderer();
                        final JFormattedTextField field = (JFormattedTextField) modelCuentas.columns
                                .get(k).m_editor;
                        editor = new DefaultCellEditor(field);
                        editor.setClickCountToStart(1);
                        field.addFocusListener(new FocusAdapter() {
                            public void focusGained(FocusEvent e) {
                                field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
                            }

                            public void focusLost(FocusEvent e) {
                                field.select(0, 0);//De-selecciono el texto al perder el foco.
                            }
                        });
                        break;
                    }
                    case CuentasTableModel.ComboType: {
                        renderer = new DefaultTableCellRenderer();
                        ((DefaultTableCellRenderer) renderer)
                                .setHorizontalAlignment(modelCuentas.columns.get(k).m_alignment);
                        editor = new DefaultCellEditor(
                                (JComboBox) modelCuentas.columns.get(k).m_editor);
                        break;
                    }
                    case CuentasTableModel.CheckType: {
                        final JCheckBox check = new JCheckBox();
                        renderer = new DefaultTableCellRenderer() {
                            /**
                             *
                             */
                            private static final long serialVersionUID = 1L;

                            public JCheckBox getTableCellRendererComponent(
                                    JTable table, Object value, boolean isSelected,
                                    boolean hasFocus, int row, int column) {
                                check.setSelected(((Boolean) value).booleanValue());
                                return check;
                            }
                        };
                        ((DefaultTableCellRenderer) renderer)
                                .setHorizontalAlignment(modelCuentas.columns.get(k).m_alignment);
                        editor = new DefaultCellEditor(check);
                        break;
                    }
                    case CuentasTableModel.StateType: {

                        final JLabel stateIcon = new JLabel();
                        renderer = new DefaultTableCellRenderer() {
                            /**
                             *
                             */
                            private static final long serialVersionUID = 1L;

                            public JLabel getTableCellRendererComponent(
                                    JTable table, Object value, boolean isSelected,
                                    boolean hasFocus, int row, int column) {
                                if (value.equals(CuentasTableModel.NewLine)) {
                                    stateIcon.setIcon(new ImageIcon(getClass()
                                            .getResource(
                                                    "/imagesPackage/addline.png")));
                                } else if (value.equals(CuentasTableModel.EditLine)) {
                                    stateIcon
                                            .setIcon(new ImageIcon(
                                                            getClass()
                                                            .getResource(
                                                                    "/imagesPackage/editline.png")));
                                }

                                return stateIcon;
                            }

                        };
                        ((DefaultTableCellRenderer) renderer)
                                .setHorizontalAlignment(modelCuentas.columns.get(k).m_alignment);
                        break;
                    }
                }

                TableColumn column = tblCuentas.getColumn(modelCuentas.columns
                        .get(k).m_name);
                column.setHeaderValue(modelCuentas.columns.get(k).m_title);
                column.setPreferredWidth(modelCuentas.columns.get(k).m_width);
                column.setCellRenderer((TableCellRenderer) renderer);
                if (editor != null) {
                    column.setCellEditor(editor);
                }
            }
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame, "FrmEntrada.prepareTable()",
                    he);
        }
    }

    public void newData() {
        try {
            this.empresa = new Empresas();
            prepareTable();
            txtIdEmpresa.setText("");
            txtNIF.setText("");
            txtNombre.setText("");
            txtProvincia.setText("");
            txtDireccion.setText("");
            txtPoblacion.setText("");
            txtCodigoPostal.setText("");
            txtTelefono.setText("");
            txtFAX.setText("");
            txtCorreoElectronico.setText("");
            txtLopd.setText("");
            Integer numRows = ((DefaultTableModel) tblCuentas.getModel())
                    .getRowCount();
            for (Integer k = 0; k < numRows; k++) {
                ((DefaultTableModel) tblCuentas.getModel()).removeRow(0);
            }
            newLineCuentas();
            OnNew = true;
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame, "FrmEmpresa.newData()", he);
        }

    }

    private void newLineCuentas() {
        try {
            if (tblCuentas.getRowCount() > 0) {
                if (!(tblCuentas.getValueAt(tblCuentas.getSelectedRow(),
                        CuentasTableModel.columnCuentaBancaria).equals(""))) {
                    addLine = true;
                    ((DefaultTableModel) tblCuentas.getModel())
                            .addRow(new Object[]{false,
                                CuentasTableModel.NewLine,
                                "", "", ""});
                    tblCuentas.changeSelection(tblCuentas.getSelectedRow() + 1,
                            CuentasTableModel.columnCuentaBancaria, false,
                            false);
                    tblCuentas.editCellAt(tblCuentas.getSelectedRow() + 1,
                            CuentasTableModel.columnCuentaBancaria);
                    addLine = false;
                }
            } else {
                ((DefaultTableModel) tblCuentas.getModel())
                        .addRow(new Object[]{false,
                            CuentasTableModel.NewLine,
                            "", "", ""});
            }
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame, "FrmEmpresa.newLineGastos()",
                    he);
        }

    }

    private static void changeEditState() {
        try {
            if (!changingState) {
                if (tblCuentas.getSelectedRow() != -1) {
                    changingState = true;
                    tblCuentas.setValueAt(CuentasTableModel.EditLine,
                            tblCuentas.getSelectedRow(),
                            CuentasTableModel.columnState);
                    changingState = false;
                }
            }
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.changeEditState()", he);
        }
    }

    public void loadData(Empresas entity) {
        try {
            this.empresa = entity;
            prepareTable();
            txtIdEmpresa.setValue(entity.getIdEmpresa());
            txtNIF.setText(entity.getNif());
            txtNombre.setText(entity.getNombre());
            txtProvincia.setText(entity.getProvincia());
            txtDireccion.setText(entity.getDireccion());
            txtPoblacion.setText(entity.getPoblacion());
            txtCodigoPostal.setValue(entity.getCodigoPostal());
            txtTelefono.setText(entity.getTelefono());
            txtFAX.setText(entity.getFax());
            txtCorreoElectronico.setText(entity.getCorreoElectronico());
            txtLopd.setText(entity.getLopd());
            loadDataDetail();
            OnNew = false;
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame, "FrmEmpresa.loadData()", he);
        }
    }

    private void loadDataDetail() {

        try {
            Integer numRows = ((DefaultTableModel) tblCuentas.getModel())
                    .getRowCount();
            for (Integer k = 0; k < numRows; k++) {
                ((DefaultTableModel) tblCuentas.getModel()).removeRow(0);
            }

            List<?> cuentasList = entity.EmpresaFindCuentas(parentFrame, empresa.getIdEmpresa());

            if (cuentasList.size() == 0) {
                newLineCuentas();
            } else {
                for (Object cuenta : cuentasList) {
                    Empresascuentas empresacuenta = (Empresascuentas) cuenta;
                    Vector<Object> oneRow = new Vector<Object>();
                    oneRow.add(false);
                    oneRow.add(CuentasTableModel.EditLine);
                    oneRow.add(empresacuenta.getId().getCuentaBancaria());
                    oneRow.add(empresacuenta.getNombreBanco());
                    oneRow.add(empresacuenta.getCuentaContable());
                    ((DefaultTableModel) tblCuentas.getModel()).addRow(oneRow);
                }
            }

        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.loadDataDetail()", he);
        }
    }

    public Boolean saveData(Boolean showmessage) {
        try {
            if (validateData()) {

                Transaction transaction = session.getSession()
                        .beginTransaction();
                if (OnNew) {
                    txtIdEmpresa.setValue(entity.newIdEmpresa(this));
                    empresa.setIdEmpresa((Integer) txtIdEmpresa.getValue());
                    empresa.setActivada(((Number) 0).shortValue());
                }
                if (!txtNIF.getText().equals("")) {
                    empresa.setNif(txtNIF.getText());
                } else {
                    empresa.setNif(null);
                }
                if (!txtNombre.getText().equals("")) {
                    empresa.setNombre(txtNombre.getText());
                } else {
                    empresa.setNombre(null);
                }
                if (!txtProvincia.getText().equals("")) {
                    empresa.setProvincia(txtProvincia.getText());
                } else {
                    empresa.setProvincia(null);
                }
                if (!txtDireccion.getText().equals("")) {
                    empresa.setDireccion(txtDireccion.getText());
                } else {
                    empresa.setDireccion(null);
                }
                if (!txtPoblacion.getText().equals("")) {
                    empresa.setPoblacion(txtPoblacion.getText());
                } else {
                    empresa.setPoblacion(null);
                }
                if (!txtCodigoPostal.getText().equals("")) {
                    empresa.setCodigoPostal(txtCodigoPostal.getText());
                } else {
                    empresa.setCodigoPostal(null);
                }
                if (!txtTelefono.getText().equals("")) {
                    empresa.setTelefono(txtTelefono.getText());
                } else {
                    empresa.setTelefono(null);
                }
                if (!txtFAX.getText().equals("")) {
                    empresa.setFax(txtFAX.getText());
                } else {
                    empresa.setFax(null);
                }
                if (!txtCorreoElectronico.getText().equals("")) {
                    empresa
                            .setCorreoElectronico(txtCorreoElectronico
                                    .getText());
                } else {
                    empresa.setCorreoElectronico(null);
                }
                if (!txtLopd.getText().equals("")) {
                    empresa
                            .setLopd(txtLopd.getText());
                } else {
                    empresa.setLopd(null);
                }
                empresa.setLmd(new Date());
                empresa.setSid("Santi");
                empresa.setVersion(1);
                session.getSession().replicate(empresa,
                        ReplicationMode.OVERWRITE);
                session.getSession().saveOrUpdate(empresa);
                session.getSession().flush();

                String deletelinesquery = "Delete From Empresascuentas "
                        + "Where id.empresas.idEmpresa = " + ((Number) txtIdEmpresa
                        .getValue()).intValue();

                Query q = getSession().getSession().createQuery(deletelinesquery);
                q.executeUpdate();

                for (Integer k = 0; k < tblCuentas.getRowCount(); k++) {
                    if (tblCuentas.getValueAt(k, CuentasTableModel.columnState)
                            .equals(CuentasTableModel.EditLine)) {
                        Empresascuentas empresacuenta = new Empresascuentas();
                        EmpresascuentasId empresacuentaId = new EmpresascuentasId();
                        empresacuentaId.setEmpresas(empresa);
                        empresacuentaId
                                .setCuentaBancaria((String) tblCuentas
                                        .getValueAt(
                                                k,
                                                CuentasTableModel.columnCuentaBancaria));
                        empresacuenta.setId(empresacuentaId);
                        empresacuenta.setNombreBanco((String) tblCuentas
                                .getValueAt(k,
                                        CuentasTableModel.columnNombreBanco));
                        if (!tblCuentas.getValueAt(k,
                                CuentasTableModel.columnCuentaContable).equals(
                                        "")) {
                            empresacuenta
                                    .setCuentaContable((String) tblCuentas
                                            .getValueAt(
                                                    k,
                                                    CuentasTableModel.columnCuentaContable));
                        } else {
                            empresacuenta.setCuentaContable(null);
                        }
                        empresacuenta.setLmd(new Date());
                        empresacuenta.setSid("Santi");
                        empresacuenta.setVersion(1);
                        session.getSession().replicate(empresacuenta,
                                ReplicationMode.OVERWRITE);
                        session.getSession().saveOrUpdate(empresacuenta);
                        session.getSession().flush();
                    }
                }

                if (transaction.isActive()) {
                    transaction.commit();
                }
                session.getSession().close();
                if (showmessage) {
                    Message.ShowSaveSuccesfull(pnlData);
                }
                OnNew = false;
                TabEmpresa.setSelectedIndex(0);
                FrmEmpresas.runSearchQuery();
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame, "FrmEmpresa.saveData()", he);
            return false;
        }
    }

    private boolean validateData() {
        try {
            if (txtNombre.getText().equals("")) {
                Message.ShowValidateMessage(pnlData,
                        "Debe indicar un nombre para la empresa.");
                TabEmpresa.setSelectedIndex(0);
                txtNombre.requestFocus();
                return (false);
            }

            if (!validateCuentasData()) {
                return (false);
            }

            return (true);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame, "FrmEmpresa.validateData()",
                    he);
            return (false);
        }
    }

    private boolean validateCuentasData() {
        try {
            for (Integer actualrow = 0; actualrow < tblCuentas.getRowCount(); actualrow++) {
                if (tblCuentas.getValueAt(actualrow,
                        CuentasTableModel.columnState).equals(
                                CuentasTableModel.EditLine)) {
                    if (tblCuentas.getValueAt(actualrow,
                            CuentasTableModel.columnCuentaBancaria).equals("")) {
                        Message.ShowValidateMessage(tblCuentas,
                                "Debe indicar una cuenta bancaria.");
                        tblCuentas.changeSelection(actualrow,
                                CuentasTableModel.columnCuentaBancaria, false,
                                false);
                        tblCuentas.editCellAt(actualrow,
                                CuentasTableModel.columnCuentaBancaria);
                        tblCuentas.requestFocusInWindow();
                        return (false);
                    }
                    if (tblCuentas.getValueAt(actualrow,
                            CuentasTableModel.columnNombreBanco).equals("")) {
                        Message.ShowValidateMessage(tblCuentas,
                                "Debe indicar un nombre de banco.");
                        tblCuentas.changeSelection(actualrow,
                                CuentasTableModel.columnNombreBanco, false,
                                false);
                        tblCuentas.editCellAt(actualrow,
                                CuentasTableModel.columnNombreBanco);
                        tblCuentas.requestFocusInWindow();
                        return (false);
                    }
                }
            }
            return (true);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.validateCuentasData()", he);
            return (false);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlData = new javax.swing.JPanel();
        TabEmpresa = new javax.swing.JTabbedPane();
        pnlData1 = new javax.swing.JPanel();
        lblCodigoPostal = new javax.swing.JLabel();
        lblProvincia = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtProvincia = new javax.swing.JTextField();
        lblPoblacion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblNIF = new javax.swing.JLabel();
        txtNIF = new javax.swing.JTextField();
        txtCodigoPostal = new javax.swing.JFormattedTextField();
        txtPoblacion = new javax.swing.JTextField();
        lblFax = new javax.swing.JLabel();
        txtFAX = new javax.swing.JTextField();
        lblIdEmpresa = new javax.swing.JLabel();
        txtIdEmpresa = new javax.swing.JFormattedTextField();
        lblCorreoElectronico = new javax.swing.JLabel();
        txtCorreoElectronico = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCuentas = new javax.swing.JTable();
        cmdSelectAll = new javax.swing.JButton();
        cmdDeselectAll = new javax.swing.JButton();
        cmdDeleteCuenta = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblLopd = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtLopd = new javax.swing.JTextArea();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        pnlData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TabEmpresa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        pnlData1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlData1.setName("pnlData1"); // NOI18N

        lblCodigoPostal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodigoPostal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoPostal.setText("C�digo postal");
        lblCodigoPostal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblProvincia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProvincia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProvincia.setText("Provincia");
        lblProvincia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 0, 0));
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombre.setText("Nombre");

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDireccion.setText("Direcci�n");
        lblDireccion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombre.setAutoscrolls(false);
        txtNombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombre.setName("nombre"); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtProvincia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtProvincia.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtProvincia.setAutoscrolls(false);
        txtProvincia.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtProvincia.setName("apellidos"); // NOI18N
        txtProvincia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProvinciaKeyTyped(evt);
            }
        });

        lblPoblacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPoblacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPoblacion.setText("Poblaci�n");
        lblPoblacion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtDireccion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDireccion.setAutoscrolls(false);
        txtDireccion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDireccion.setName("poblacion"); // NOI18N
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTelefono.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTelefono.setText("Tel�fono");
        lblTelefono.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTelefono.setAutoscrolls(false);
        txtTelefono.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTelefono.setName("telefono1"); // NOI18N
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        lblNIF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNIF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNIF.setText("CIF");
        lblNIF.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNIF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNIF.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNIF.setAutoscrolls(false);
        txtNIF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNIF.setName("nif"); // NOI18N
        txtNIF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNIFKeyTyped(evt);
            }
        });

        txtCodigoPostal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodigoPostal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoPostal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodigoPostal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoPostalKeyTyped(evt);
            }
        });

        txtPoblacion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPoblacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPoblacion.setAutoscrolls(false);
        txtPoblacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPoblacion.setName("poblacion"); // NOI18N
        txtPoblacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPoblacionKeyTyped(evt);
            }
        });

        lblFax.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFax.setText("Fax");
        lblFax.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtFAX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFAX.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFAX.setAutoscrolls(false);
        txtFAX.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtFAX.setName("telefono1"); // NOI18N
        txtFAX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFAXKeyTyped(evt);
            }
        });

        lblIdEmpresa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdEmpresa.setForeground(new java.awt.Color(255, 0, 0));
        lblIdEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdEmpresa.setText("Id empresa");
        lblIdEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtIdEmpresa.setEditable(false);
        txtIdEmpresa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdEmpresa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdEmpresa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblCorreoElectronico.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCorreoElectronico.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCorreoElectronico.setText("E-mail");
        lblCorreoElectronico.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCorreoElectronico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCorreoElectronico.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCorreoElectronico.setAutoscrolls(false);
        txtCorreoElectronico.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCorreoElectronico.setName("apellidos"); // NOI18N
        txtCorreoElectronico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoElectronicoKeyTyped(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblCuentas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblCuentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblCuentas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblCuentas.setCellSelectionEnabled(true);
        tblCuentas.setRowHeight(18);
        tblCuentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblCuentasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblCuentas);

        cmdSelectAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdSelectAll.setText("Seleccionar todo");
        cmdSelectAll.setToolTipText("Seleccionar todas las filas");
        cmdSelectAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdSelectAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdSelectAllMousePressed(evt);
            }
        });

        cmdDeselectAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeselectAll.setText("Quitar selecci�n");
        cmdDeselectAll.setToolTipText("Quitar la seleccionar todas las filas");
        cmdDeselectAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeselectAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeselectAllMousePressed(evt);
            }
        });

        cmdDeleteCuenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeleteCuenta.setText("Eliminar cuentas");
        cmdDeleteCuenta.setToolTipText("Eliminar cuentas seleccionados");
        cmdDeleteCuenta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeleteCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeleteCuentaMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnlData1Layout = new javax.swing.GroupLayout(pnlData1);
        pnlData1.setLayout(pnlData1Layout);
        pnlData1Layout.setHorizontalGroup(
            pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCorreoElectronico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTelefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblFax, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtFAX, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(txtIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(lblNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDireccion)))
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(cmdSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdDeselectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdDeleteCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnlData1Layout.setVerticalGroup(
            pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdEmpresa)
                    .addComponent(txtIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNIF)
                    .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccion)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPoblacion)
                            .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProvincia)
                            .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefono)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFax)
                            .addComponent(txtFAX, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCorreoElectronico)
                            .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblCodigoPostal)
                    .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdDeleteCuenta)
                    .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmdSelectAll)
                        .addComponent(cmdDeselectAll)))
                .addGap(27, 27, 27))
        );

        TabEmpresa.addTab("1. Datos Generales", pnlData1);

        lblLopd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLopd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLopd.setText("Texto LOPD");
        lblLopd.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtLopd.setColumns(20);
        txtLopd.setRows(5);
        txtLopd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane2.setViewportView(txtLopd);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblLopd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblLopd)
                        .addGap(0, 372, Short.MAX_VALUE)))
                .addContainerGap())
        );

        TabEmpresa.addTab("2. Ley Org�nica de Protecci�n de Datos", jPanel1);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/cancel.png"))); // NOI18N
        btnCancel.setToolTipText("Cancelar");
        btnCancel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCancelMousePressed(evt);
            }
        });

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/ok.png"))); // NOI18N
        btnOk.setToolTipText("Aceptar");
        btnOk.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnOkMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addGap(9, 9, 9)
                .addComponent(btnOk)
                .addContainerGap())
            .addComponent(TabEmpresa)
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                .addComponent(TabEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel)
                    .addComponent(btnOk))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 671, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(8, 8, 8)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdDeleteCuentaMousePressed(java.awt.event.MouseEvent evt) {
        try {
            for (Integer actualrow = 0; actualrow < tblCuentas.getRowCount(); actualrow++) {
                if ((Boolean) tblCuentas.getValueAt(actualrow,
                        CuentasTableModel.columnSelect) == true) {
                    ((DefaultTableModel) tblCuentas.getModel())
                            .removeRow(actualrow);
                    actualrow--;
                }
            }
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.cmdDeleteCuentaMousePressed()", he);
        }
    }

    private void cmdDeselectAllMousePressed(java.awt.event.MouseEvent evt) {
        try {
            for (Integer actualrow = 0; actualrow < tblCuentas.getRowCount(); actualrow++) {
                tblCuentas.setValueAt(false, actualrow,
                        CuentasTableModel.columnSelect);
            }
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.cmdDeselectAllMousePressed()", he);
        }
    }

    private void cmdSelectAllMousePressed(java.awt.event.MouseEvent evt) {
        try {
            for (Integer actualrow = 0; actualrow < tblCuentas.getRowCount(); actualrow++) {
                tblCuentas.setValueAt(true, actualrow,
                        CuentasTableModel.columnSelect);
            }
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.cmdSelectAllMousePressed()", he);
        }
    }

    private void tblCuentasKeyPressed(java.awt.event.KeyEvent evt) {
        try {
            if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                if ((tblCuentas.getSelectedRow() == ((DefaultTableModel) tblCuentas
                        .getModel()).getRowCount() - 1)
                        && (tblCuentas.getValueAt(tblCuentas.getSelectedRow(),
                                CuentasTableModel.columnState)
                        .equals(CuentasTableModel.EditLine))) {
                    if ((tblCuentas.getSelectedColumn() == tblCuentas
                            .getColumnCount() - 1)) {
                        newLineCuentas();
                        evt.setKeyCode(0);
                    } else {
                        evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
                    }
                } else {
                    evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
                }
            } else {
                if ((evt.getKeyCode() == 32 || evt.getKeyCode() == 164)
                        || (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57)
                        || (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 122)) {
                    tblCuentas.setValueAt("", tblCuentas.getSelectedRow(),
                            tblCuentas.getSelectedColumn());
                }
            }
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.tblCuentasKeyPressed()", he);
        }
    }

    private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
        try {
            Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
            setCursor(hourglassCursor);
            if (OnNew) {
                newData();
            } else {
                loadData(this.empresa);
            }
            Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
            setCursor(normalCursor);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.btnCancelMousePressed()", he);
        }
    }

    private void btnOkMousePressed(java.awt.event.MouseEvent evt) {
        try {
            Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
            setCursor(hourglassCursor);
            saveData(true);
            Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
            setCursor(normalCursor);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.btnOkMousePressed()", he);
        }
    }

    private void txtFAXKeyTyped(java.awt.event.KeyEvent evt) {
        try {
            Util.keyTyped(txtFAX, evt, 12);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.txtFAXKeyTyped()", he);
        }
    }

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {
        try {
            Util.keyTyped(txtTelefono, evt, 12);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.txtTelefonoKeyTyped()", he);
        }
    }

    private void txtProvinciaKeyTyped(java.awt.event.KeyEvent evt) {
        try {
            Util.keyTyped(txtProvincia, evt, 100);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.txtProvinciaKeyTyped()", he);
        }
    }

    private void txtCodigoPostalKeyTyped(java.awt.event.KeyEvent evt) {
        try {
            Util.keyTyped(txtCodigoPostal, evt, 6);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.txtCodigoPostalKeyTyped()", he);
        }
    }

    private void txtPoblacionKeyTyped(java.awt.event.KeyEvent evt) {
        try {
            Util.keyTyped(txtPoblacion, evt, 100);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.txtPoblacionKeyTyped()", he);
        }
    }

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {
        try {
            Util.keyTyped(txtDireccion, evt, 100);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.txtDireccionKeyTyped()", he);
        }
    }

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {
        try {
            Util.keyTyped(txtNombre, evt, 100);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.txtNombreKeyTyped()", he);
        }
    }

    private void txtCorreoElectronicoKeyTyped(java.awt.event.KeyEvent evt) {
        try {
            Util.keyTyped(txtCorreoElectronico, evt, 100);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.txtCorreoElectronicoKeyTyped()", he);
        }
    }

    private void txtNIFKeyTyped(java.awt.event.KeyEvent evt) {
        try {
            Util.keyTyped(txtNIF, evt, 12);
        } catch (RuntimeException he) {
            Message.ShowRuntimeError(parentFrame,
                    "FrmEmpresa.txtNIFKeyTyped()", he);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabEmpresa;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton cmdDeleteCuenta;
    private javax.swing.JButton cmdDeselectAll;
    private javax.swing.JButton cmdSelectAll;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblCorreoElectronico;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFax;
    private javax.swing.JLabel lblIdEmpresa;
    private javax.swing.JLabel lblLopd;
    private javax.swing.JLabel lblNIF;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPoblacion;
    private javax.swing.JLabel lblProvincia;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JPanel pnlData;
    public javax.swing.JPanel pnlData1;
    private static javax.swing.JTable tblCuentas;
    private javax.swing.JFormattedTextField txtCodigoPostal;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFAX;
    private javax.swing.JFormattedTextField txtIdEmpresa;
    private javax.swing.JTextArea txtLopd;
    private javax.swing.JTextField txtNIF;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPoblacion;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

}
