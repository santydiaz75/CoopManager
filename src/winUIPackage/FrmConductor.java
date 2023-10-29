/*
 * FrmBanco.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.AWTKeyStroke;
import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.ReplicationMode;
import org.hibernate.Transaction;

import sessionPackage.MySession;

import entitiesPackage.Bancos;
import entitiesPackage.Conductores;
import entitiesPackage.ConductoresId;
import entitiesPackage.Entity;
import entitiesPackage.Message;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmConductor extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	Conductores conductor;

	private static boolean changingBanco = false;
	private static boolean changingSucursal = false;

	private java.awt.Frame parentFrame;
	private static MySession session;
	private boolean OnNew = false;
	private Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return this.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		FrmConductor.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmConductor() {
		initComponents();
	}

	public FrmConductor(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			this.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmConductor()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmConductor.configureKeys",
					he);
		}
	}

	public void newData() {

		try {
			this.conductor = new Conductores();
			loadBancos();
			loadSucursales("");
			txtIdConductor.setText("");
			txtNIF.setText("");
			txtNombre.setText("");
			txtApellidos.setText("");
			txtDireccion.setText("");
			txtPoblacion.setText("");
			txtCodigoPostal.setText("");
			txtTelefono.setText("");
			txtCuentaContable.setText("");
			txtIdBanco.setText("");
			cboBancos.setSelectedItem(null);
			txtIdSucursal.setText("");
			cboSucursales.setSelectedItem(null);
			txtDigitoControl.setText("");
			txtCuentaBancaria.setText("");
                        txtIBAN.setText("");
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.newData()", he);
		}
	}

	public void loadData(Conductores entity) {

		try {
			this.conductor = entity;
			loadBancos();
			loadSucursales(entity.getIdBanco());
			txtIdConductor.setValue(entity.getId().getIdConductor());
			txtNIF.setText(entity.getNif());
			txtNombre.setText(entity.getNombre());
			txtApellidos.setText(entity.getApellidos());
			txtDireccion.setText(entity.getDireccion());
			txtPoblacion.setText(entity.getPoblacion());
			txtCodigoPostal.setText(entity.getCodigoPostal());
			txtTelefono.setText(entity.getTelefono());
			txtCuentaContable.setText(entity.getCuentaContable());
			txtIdBanco.setText(entity.getIdBanco());
			changeBanco();
			txtIdSucursal.setText(entity.getIdSucursal());
			changeSucursal();
			txtDigitoControl.setValue(entity.getDigitoControl());
			txtCuentaBancaria.setText(entity.getCuentaBancaria());
                        txtIBAN.setText(entity.getIban());
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.loadData()", he);
		}
	}

	private void loadBancos() {

		try {
			cboBancos.removeAllItems();

			List<?> bancosList = entity.BancoFindDistinctAll(this);

			for (Object o : bancosList) {
				String banco = (String) o;
				cboBancos.addItem(banco);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.loadBancos()", he);
		}
	}

	private void loadSucursales(String bancoId) {

		try {
			cboSucursales.removeAllItems();

			List<?> bancosList = entity.SucursalFindByIdBanco(this, bancoId);

			for (Object o : bancosList) {
				Bancos banco = (Bancos) o;
				cboSucursales.addItem(banco.getNombreSucursal());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.loadSucursales()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {

		try {
			if (validateData()) {
				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					txtIdConductor.setValue(entity.newId(this, "Conductores",
							"id.idConductor"));
					ConductoresId conductorid = new ConductoresId();
					conductorid.setEmpresas(session.getEmpresa());
					conductorid.setEjercicios(session.getEjercicio());
					conductorid.setIdConductor((Integer) txtIdConductor
							.getValue());
					conductor.setId(conductorid);
				}
				if (!txtNIF.getText().equals(""))
					conductor.setNif(txtNIF.getText());
				else
					conductor.setNif(null);
				if (!txtNombre.getText().equals(""))
					conductor.setNombre(txtNombre.getText());
				else
					conductor.setNombre(null);
				if (!txtApellidos.getText().equals(""))
					conductor.setApellidos(txtApellidos.getText());
				else
					conductor.setApellidos(null);
				if (!txtDireccion.getText().equals(""))
					conductor.setDireccion(txtDireccion.getText());
				else
					conductor.setDireccion(null);
				if (!txtPoblacion.getText().equals(""))
					conductor.setPoblacion(txtPoblacion.getText());
				else
					conductor.setPoblacion(null);
				if (!txtCodigoPostal.getText().equals(""))
					conductor.setCodigoPostal(txtCodigoPostal.getText());
				else
					conductor.setCodigoPostal(null);
				if (!txtTelefono.getText().equals(""))
					conductor.setTelefono(txtTelefono.getText());
				else
					conductor.setTelefono(null);
				if (!txtCuentaContable.getText().equals(""))
					conductor.setCuentaContable(txtCuentaContable.getText());
				else
					conductor.setCuentaContable(null);
				if (!txtIdBanco.getText().equals(""))
					conductor.setIdBanco(txtIdBanco.getText());
				else
					conductor.setIdBanco(null);
				if (!txtIdSucursal.getText().equals(""))
					conductor.setIdSucursal(txtIdSucursal.getText());
				else
					conductor.setIdSucursal(null);
				if (!txtDigitoControl.getText().equals(""))
					conductor.setDigitoControl(txtDigitoControl.getText());
				else
					conductor.setDigitoControl(null);
				if (!txtCuentaBancaria.getText().equals(""))
					conductor.setCuentaBancaria(txtCuentaBancaria.getText());
				else
					conductor.setCuentaBancaria(null);
                                if (!txtIBAN.getText().equals(""))
					conductor.setIban(txtIBAN.getText());
				else
					conductor.setIban(null);
				conductor.setLmd(new Date());
				conductor.setSid("Santi");
				conductor.setVersion(1);
				session.getSession().replicate(conductor,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(conductor);
				session.getSession().flush();
				if (!transaction.wasCommitted()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				OnNew = false;
				TabCosechero.setSelectedIndex(0);
				FrmConductores.runSearchQuery();
				return true;
			}
			else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.saveData()", he);
			return false;
		}
	}

	private boolean validateData() {

		try {
			if (txtNombre.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un nombre para el conductor.");
				TabCosechero.setSelectedIndex(0);
				txtNombre.requestFocus();
				return (false);
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.validateData()", he);
		}
		return false;

	}

	private void changeBanco() {

		try {
			if (!changingBanco) {
				changingBanco = true;
				if (txtIdBanco.getText().equals(""))
					cboBancos.setSelectedItem(null);
				else {
					try {
						String value = txtIdBanco.getText();
						List<?> bancosList = entity.BancoFindByIdBanco(this,
								value);

						if (bancosList.size() > 0) {
							cboBancos.setSelectedItem(((Bancos) bancosList
									.get(0)).getNombreBanco());
							loadSucursales(txtIdBanco.getText());
						} else {
							cboBancos.setSelectedItem(null);
							loadSucursales("");
						}
					} catch (NumberFormatException nfe) {
						cboBancos.setSelectedItem(null);
						loadSucursales("");
					}
				}
				changingBanco = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.changeBanco()", he);
		}
	}

	private void changeSucursal() {

		try {
			if (!changingSucursal) {
				changingSucursal = true;
				if (txtIdSucursal.getText().equals(""))
					cboSucursales.setSelectedItem(null);
				else {
					try {
						String value = txtIdSucursal.getText();
						List<?> sucursaleslist = entity.BancoFindByIdSucursal(
								this, value);
						if (sucursaleslist.size() > 0)
							cboSucursales
									.setSelectedItem(((Bancos) sucursaleslist
											.get(0)).getNombreSucursal());
						else {
							cboSucursales.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboBancos.setSelectedItem(null);
					}
				}
				changingSucursal = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.changeSucursal()", he);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlData = new javax.swing.JPanel();
        TabCosechero = new javax.swing.JTabbedPane();
        pnlData1 = new javax.swing.JPanel();
        lblIdConductor = new javax.swing.JLabel();
        lblApellidos = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        lblPoblacion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblCodigoPostal = new javax.swing.JLabel();
        txtCodigoPostal = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblNIF = new javax.swing.JLabel();
        txtNIF = new javax.swing.JTextField();
        txtIdConductor = new javax.swing.JFormattedTextField();
        txtPoblacion = new javax.swing.JTextField();
        lblCuentaContable = new javax.swing.JLabel();
        txtCuentaContable = new javax.swing.JTextField();
        lblIdBanco = new javax.swing.JLabel();
        txtIdBanco = new javax.swing.JFormattedTextField();
        cboBancos = new javax.swing.JComboBox();
        lblIdSucursal = new javax.swing.JLabel();
        txtIdSucursal = new javax.swing.JFormattedTextField();
        cboSucursales = new javax.swing.JComboBox();
        lblDigitoControl = new javax.swing.JLabel();
        txtDigitoControl = new javax.swing.JFormattedTextField();
        lblCuentaBancaria = new javax.swing.JLabel();
        txtCuentaBancaria = new javax.swing.JTextField();
        lblIBAN = new javax.swing.JLabel();
        txtIBAN = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        pnlData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TabCosechero.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        pnlData1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlData1.setName("pnlData1"); // NOI18N

        lblIdConductor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdConductor.setForeground(new java.awt.Color(255, 0, 0));
        lblIdConductor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdConductor.setText("Id conductor");
        lblIdConductor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblApellidos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblApellidos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblApellidos.setText("Apellidos");
        lblApellidos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 0, 0));
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombre.setText("Nombre");

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDireccion.setText("Dirección");
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

        txtApellidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtApellidos.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtApellidos.setAutoscrolls(false);
        txtApellidos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtApellidos.setName("apellidos"); // NOI18N
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });

        lblPoblacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPoblacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPoblacion.setText("Población");
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

        lblCodigoPostal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodigoPostal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoPostal.setText("Código postal");
        lblCodigoPostal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCodigoPostal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodigoPostal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoPostal.setAutoscrolls(false);
        txtCodigoPostal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodigoPostal.setName("codigoPostal"); // NOI18N
        txtCodigoPostal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoPostalKeyTyped(evt);
            }
        });

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTelefono.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTelefono.setText("Teléfono");
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
        lblNIF.setText("NIF");
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

        txtIdConductor.setEditable(false);
        txtIdConductor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdConductor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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

        lblCuentaContable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCuentaContable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCuentaContable.setText("Cuenta contable");
        lblCuentaContable.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCuentaContable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCuentaContable.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCuentaContable.setAutoscrolls(false);
        txtCuentaContable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCuentaContable.setName("telefono2"); // NOI18N
        txtCuentaContable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCuentaContableKeyTyped(evt);
            }
        });

        lblIdBanco.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdBanco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdBanco.setText("Banco");

        txtIdBanco.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdBanco.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIdBanco.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        txtIdBanco.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdBanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdBancoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdBancoKeyTyped(evt);
            }
        });

        cboBancos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBancos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboBancos.setName("Id subcategoria"); // NOI18N
        cboBancos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBancosActionPerformed(evt);
            }
        });

        lblIdSucursal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdSucursal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdSucursal.setText("Sucursal");

        txtIdSucursal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdSucursal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIdSucursal.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        txtIdSucursal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdSucursalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdSucursalKeyTyped(evt);
            }
        });

        cboSucursales.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSucursales.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboSucursales.setName("Id subcategoria"); // NOI18N
        cboSucursales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSucursalesActionPerformed(evt);
            }
        });

        lblDigitoControl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDigitoControl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDigitoControl.setText("Dígito control");
        lblDigitoControl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtDigitoControl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDigitoControl.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDigitoControl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDigitoControl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDigitoControlKeyTyped(evt);
            }
        });

        lblCuentaBancaria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCuentaBancaria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCuentaBancaria.setText("Cuenta bancaria");
        lblCuentaBancaria.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCuentaBancaria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCuentaBancaria.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCuentaBancaria.setAutoscrolls(false);
        txtCuentaBancaria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCuentaBancaria.setName("telefono2"); // NOI18N
        txtCuentaBancaria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCuentaBancariaKeyTyped(evt);
            }
        });

        lblIBAN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIBAN.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIBAN.setText("IBAN");
        lblIBAN.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtIBAN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIBAN.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIBAN.setAutoscrolls(false);
        txtIBAN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIBAN.setName("iban"); // NOI18N
        txtIBAN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIBANKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlData1Layout = new javax.swing.GroupLayout(pnlData1);
        pnlData1.setLayout(pnlData1Layout);
        pnlData1Layout.setHorizontalGroup(
            pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblIdConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtIdConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(lblNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addGap(290, 290, 290)
                        .addComponent(lblCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblCuentaContable, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtCuentaContable, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(cboBancos, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlData1Layout.createSequentialGroup()
                        .addComponent(lblApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtApellidos))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlData1Layout.createSequentialGroup()
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtNombre))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlData1Layout.createSequentialGroup()
                        .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addComponent(lblDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58))
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addComponent(lblIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cboSucursales, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))))
        );
        pnlData1Layout.setVerticalGroup(
            pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdConductor)
                    .addComponent(txtIdConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNIF)
                    .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblApellidos)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccion)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPoblacion)
                    .addComponent(lblCodigoPostal)
                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCuentaContable)
                    .addComponent(txtCuentaContable, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdBanco)
                    .addComponent(txtIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboBancos, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdSucursal)
                    .addComponent(txtIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSucursales, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDigitoControl)
                    .addComponent(txtDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCuentaBancaria)
                    .addComponent(txtCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblIBAN)
                        .addComponent(txtIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        TabCosechero.addTab("1. Datos Generales", pnlData1);

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
            .addComponent(TabCosechero)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addGap(9, 9, 9)
                .addComponent(btnOk)
                .addContainerGap())
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addComponent(TabCosechero)
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
            .addGap(0, 659, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtIBANKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIBANKeyTyped
        try {
                Util.keyTyped(txtIBAN, evt, 4);
        } catch (RuntimeException he) {
                Message.ShowRuntimeError(this.parentFrame,
                                "FrmConductor.txtIBANKeyTyped()", he);
        }
    }//GEN-LAST:event_txtIBANKeyTyped

	private void cboSucursalesActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingSucursal) {
				changingSucursal = true;
				if (cboSucursales.getSelectedIndex() != -1) {
					List<?> sucursaleslist = entity.executeQuery(this, "*",
							"Bancos", "nombreSucursal = '"
									+ cboSucursales.getSelectedItem()
									+ "' and nombreBanco = '"
									+ cboBancos.getSelectedItem() + "'", "");
					if (sucursaleslist.size() > 0) {
						Bancos sucursalesItem = (Bancos) sucursaleslist.get(0);
						txtIdSucursal.setValue(sucursalesItem.getId()
								.getIdSucursal());
					}
				}
				changingSucursal = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.cboSucursalesActionPerformed()", he);
		}
	}

	private void cboBancosActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingBanco) {
				changingBanco = true;
				if (cboBancos.getSelectedIndex() != -1) {
					List<?> bancoslist = entity.BancoFindByNombreBanco(this,
							(String) cboBancos.getSelectedItem());
					if (bancoslist.size() > 0) {
						Bancos bancosItem = (Bancos) bancoslist.get(0);
						txtIdBanco.setValue(bancosItem.getId().getIdBanco());
						loadSucursales(txtIdBanco.getText());
					}
				}
				changingBanco = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.cboBancosActionPerformed()", he);
		}
	}

	private void txtIdSucursalKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeSucursal();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtIdSucursalKeyReleased()", he);
		}
	}

	private void txtIdBancoKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeBanco();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtIdBancoKeyReleased()", he);
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
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.conductor);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.btnCancelMousePressed()", he);
		}
	}

	private void txtCuentaBancariaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaBancaria, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtCuentaBancariaKeyTyped()", he);
		}
	}

	private void txtDigitoControlKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDigitoControl, evt, 2);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtDigitoControlKeyTyped()", he);
		}
	}

	private void txtIdSucursalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdSucursal, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtIdSucursalKeyTyped()", he);
		}
	}

	private void txtIdBancoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdBanco, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtIdBancoKeyTyped()", he);
		}
	}

	private void txtCuentaContableKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaContable, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtCuentaContableKeyTyped()", he);
		}
	}

	private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtTelefono, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtTelefonoKeyTyped()", he);
		}
	}

	private void txtCodigoPostalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCodigoPostal, evt, 6);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtCodigoPostalKeyTyped()", he);
		}
	}

	private void txtPoblacionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtPoblacion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtPoblacionKeyTyped()", he);
		}
	}

	private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDireccion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtDireccionKeyTyped()", he);
		}
	}

	private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtApellidos, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtApellidosKeyTyped()", he);
		}
	}

	private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNombre, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtNombreKeyTyped()", he);
		}
	}

	private void txtNIFKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNIF, evt, 12);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmConductor.txtNIFKeyTyped()", he);
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabCosechero;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private static javax.swing.JComboBox cboBancos;
    private static javax.swing.JComboBox cboSucursales;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblCuentaBancaria;
    private javax.swing.JLabel lblCuentaContable;
    private javax.swing.JLabel lblDigitoControl;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblIBAN;
    private javax.swing.JLabel lblIdBanco;
    private javax.swing.JLabel lblIdConductor;
    private javax.swing.JLabel lblIdSucursal;
    private javax.swing.JLabel lblNIF;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPoblacion;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JPanel pnlData;
    public javax.swing.JPanel pnlData1;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtCuentaBancaria;
    private javax.swing.JTextField txtCuentaContable;
    private javax.swing.JFormattedTextField txtDigitoControl;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtIBAN;
    private static javax.swing.JFormattedTextField txtIdBanco;
    private javax.swing.JFormattedTextField txtIdConductor;
    private static javax.swing.JFormattedTextField txtIdSucursal;
    private javax.swing.JTextField txtNIF;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPoblacion;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

}