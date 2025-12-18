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
import java.util.Set;

import org.hibernate.ReplicationMode;
import org.hibernate.Transaction;

import sessionPackage.MySession;

import entitiesPackage.Entity;
import entitiesPackage.Message;
import entitiesPackage.Receptores;
import entitiesPackage.ReceptoresId;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmReceptor extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	Receptores receptor;

	private java.awt.Frame parentFrame;
	private MySession session;
	private boolean OnNew = false;
	private Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return this.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmReceptor() {
		initComponents();
	}

	public FrmReceptor(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			this.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent, "FrmReceptor()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmReceptor.configureKeys",
					he);
		}
	}

	public void newData() {
		try {
			this.receptor = new Receptores();
			txtIdReceptor.setText("");
			txtNIF.setText("");
			txtNombre.setText("");
			txtDireccion.setText("");
			txtPoblacion.setText("");
			txtCodigoPostal.setText("");
			txtTelefono.setText("");
			txtFax.setText("");
			txtCuentaContable.setText("");
			chkMercadoLocal.setText("");
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmReceptor.newData()", he);
		}
	}

	public void loadData(Receptores entity) {
		try {
			this.receptor = entity;
			txtIdReceptor.setValue(entity.getId().getIdReceptor());
			txtNIF.setText(entity.getNif());
			txtNombre.setText(entity.getNombre());
			txtDireccion.setText(entity.getDireccion());
			txtPoblacion.setText(entity.getPoblacion());
			txtCodigoPostal.setText(entity.getCodigoPostal());
			txtTelefono.setText(entity.getTelefono());
			txtFax.setText(entity.getFax());
			txtCuentaContable.setText(entity.getCuentaContable());
			chkMercadoLocal.setSelected(entity.getMercadoLocal() != 0);
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmReceptor.loadData()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {
		try {
			if (validateData()) {

				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					txtIdReceptor.setValue(entity.newId(this, "Receptores",
							"id.idReceptor"));
					ReceptoresId receptorid = new ReceptoresId();
					receptorid.setEmpresas(session.getEmpresa());
					receptorid.setEjercicios(session.getEjercicio());
					receptorid
							.setIdReceptor((Integer) txtIdReceptor.getValue());
					receptor.setId(receptorid);
				}
				if (!txtNIF.getText().equals(""))
					receptor.setNif(txtNIF.getText());
				else
					receptor.setNif(null);
				if (!txtNombre.getText().equals(""))
					receptor.setNombre(txtNombre.getText());
				else
					receptor.setNombre(null);
				if (!txtDireccion.getText().equals(""))
					receptor.setDireccion(txtDireccion.getText());
				else
					receptor.setDireccion(null);
				if (!txtPoblacion.getText().equals(""))
					receptor.setPoblacion(txtPoblacion.getText());
				else
					receptor.setPoblacion(null);
				if (!txtCodigoPostal.getText().equals(""))
					receptor.setCodigoPostal(txtCodigoPostal.getText());
				else
					receptor.setCodigoPostal(null);
				if (!txtFax.getText().equals(""))
					receptor.setFax(txtFax.getText());
				else
					receptor.setFax(null);
				if (!txtTelefono.getText().equals(""))
					receptor.setTelefono(txtTelefono.getText());
				else
					receptor.setTelefono(null);
				if (!txtCuentaContable.getText().equals(""))
					receptor.setCuentaContable(txtCuentaContable.getText());
				else
					receptor.setCuentaContable(null);
				if (chkMercadoLocal.isSelected())
					receptor.setMercadoLocal(((Number) 1).shortValue());
				else
					receptor.setMercadoLocal(((Number) 0).shortValue());
				receptor.setLmd(new Date());
				receptor.setSid("Santi");
				receptor.setVersion(1);
				session.getSession().replicate(receptor,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(receptor);
				session.getSession().flush();
				if (transaction.isActive()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				OnNew = false;
				FrmReceptores.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmReceptor.saveData()", he);
			return false;
		}
	}

	private boolean validateData() {
		try {
			if (txtNombre.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un nombre para el receptor.");
				TabReceptor.setSelectedIndex(0);
				txtNombre.requestFocus();
				return (false);
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmReceptor.validateData()",
					he);
			return (false);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents() {

		pnlData = new javax.swing.JPanel();
		TabReceptor = new javax.swing.JTabbedPane();
		pnlData1 = new javax.swing.JPanel();
		lblIdReceptor = new javax.swing.JLabel();
		lblNombre = new javax.swing.JLabel();
		lblDireccion = new javax.swing.JLabel();
		txtNombre = new javax.swing.JTextField();
		lblPoblacion = new javax.swing.JLabel();
		txtDireccion = new javax.swing.JTextField();
		lblCodigoPostal = new javax.swing.JLabel();
		txtCodigoPostal = new javax.swing.JTextField();
		lblTelefono = new javax.swing.JLabel();
		txtTelefono = new javax.swing.JTextField();
		lblNIF = new javax.swing.JLabel();
		txtNIF = new javax.swing.JTextField();
		txtIdReceptor = new javax.swing.JFormattedTextField();
		txtPoblacion = new javax.swing.JTextField();
		lblCuentaContable = new javax.swing.JLabel();
		txtCuentaContable = new javax.swing.JTextField();
		lblFax = new javax.swing.JLabel();
		txtFax = new javax.swing.JTextField();
		lblMercadoLocal = new javax.swing.JLabel();
		chkMercadoLocal = new javax.swing.JCheckBox();
		btnCancel = new javax.swing.JButton();
		btnOk = new javax.swing.JButton();

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		TabReceptor.setFont(new java.awt.Font("Segoe UI", 0, 14));

		pnlData1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData1.setForeground(new java.awt.Color(255, 0, 0));
		pnlData1.setName("pnlData1");

		lblIdReceptor.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblIdReceptor.setForeground(new java.awt.Color(255, 0, 0));
		lblIdReceptor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblIdReceptor.setText("Id receptor");
		lblIdReceptor
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblNombre.setForeground(new java.awt.Color(255, 0, 0));
		lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblNombre.setText("Nombre");

		lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblDireccion.setText("Dirección");
		lblDireccion
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtNombre.setAutoscrolls(false);
		txtNombre.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtNombre.setName("nombre");
		txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtNombreKeyTyped(evt);
			}
		});

		lblPoblacion.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblPoblacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblPoblacion.setText("Población");
		lblPoblacion
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtDireccion.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtDireccion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtDireccion.setAutoscrolls(false);
		txtDireccion.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtDireccion.setName("poblacion");
		txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtDireccionKeyTyped(evt);
			}
		});

		lblCodigoPostal.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCodigoPostal
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCodigoPostal.setText("Código postal");
		lblCodigoPostal
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCodigoPostal.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCodigoPostal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCodigoPostal.setAutoscrolls(false);
		txtCodigoPostal.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCodigoPostal.setName("codigoPostal");
		txtCodigoPostal.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCodigoPostalKeyTyped(evt);
			}
		});

		lblTelefono.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblTelefono.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTelefono.setText("TelÃ©fono");
		lblTelefono.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtTelefono.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtTelefono.setAutoscrolls(false);
		txtTelefono.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTelefono.setName("telefono1");
		txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtTelefonoKeyTyped(evt);
			}
		});

		lblNIF.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblNIF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblNIF.setText("NIF");
		lblNIF.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtNIF.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtNIF.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtNIF.setAutoscrolls(false);
		txtNIF.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtNIF.setName("nif");
		txtNIF.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtNIFKeyTyped(evt);
			}
		});

		txtIdReceptor.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdReceptor.setEditable(false);
		txtIdReceptor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdReceptor.setFont(new java.awt.Font("Segoe UI", 0, 14));

		txtPoblacion.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtPoblacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtPoblacion.setAutoscrolls(false);
		txtPoblacion.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtPoblacion.setName("poblacion");
		txtPoblacion.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtPoblacionKeyTyped(evt);
			}
		});

		lblCuentaContable.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaContable
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaContable.setText("Cuenta contable");
		lblCuentaContable
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaContable.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaContable.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaContable.setAutoscrolls(false);
		txtCuentaContable.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaContable.setName("telefono2");
		txtCuentaContable.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaContableKeyTyped(evt);
			}
		});

		lblFax.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFax.setText("Fax");
		lblFax.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtFax.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtFax.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtFax.setAutoscrolls(false);
		txtFax.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtFax.setName("telefono1");
		txtFax.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtFaxKeyTyped(evt);
			}
		});

		lblMercadoLocal.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblMercadoLocal
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblMercadoLocal.setText("Mercado local");

		chkMercadoLocal.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		chkMercadoLocal.setMargin(new java.awt.Insets(0, 0, 2, 2));
		chkMercadoLocal.setName("privada");

		javax.swing.GroupLayout pnlData1Layout = new javax.swing.GroupLayout(
				pnlData1);
		pnlData1.setLayout(pnlData1Layout);
		pnlData1Layout
				.setHorizontalGroup(pnlData1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlData1Layout
										.createSequentialGroup()
										.addGroup(
												pnlData1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlData1Layout
																		.createSequentialGroup()
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				lblIdReceptor,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtIdReceptor,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				60,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				110,
																				110,
																				110)
																		.addComponent(
																				lblNIF,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				30,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtNIF,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlData1Layout
																		.createSequentialGroup()
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				lblPoblacion,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addGroup(
																				pnlData1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								txtPoblacion,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								300,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								pnlData1Layout
																										.createSequentialGroup()
																										.addGap(
																												290,
																												290,
																												290)
																										.addComponent(
																												lblCodigoPostal,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtCodigoPostal,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				60,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlData1Layout
																		.createSequentialGroup()
																		.addGap(
																				48,
																				48,
																				48)
																		.addComponent(
																				lblTelefono,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtTelefono,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				20,
																				20,
																				20)
																		.addComponent(
																				lblFax,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtFax,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlData1Layout
																		.createSequentialGroup()
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				lblCuentaContable,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtCuentaContable,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				lblMercadoLocal,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				96,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				chkMercadoLocal))
														.addGroup(
																pnlData1Layout
																		.createSequentialGroup()
																		.addGap(
																				18,
																				18,
																				18)
																		.addGroup(
																				pnlData1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addGroup(
																								pnlData1Layout
																										.createSequentialGroup()
																										.addComponent(
																												lblNombre,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												txtNombre))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								pnlData1Layout
																										.createSequentialGroup()
																										.addComponent(
																												lblDireccion,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												txtDireccion,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												480,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))))
										.addContainerGap(18, Short.MAX_VALUE)));
		pnlData1Layout
				.setVerticalGroup(pnlData1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlData1Layout
										.createSequentialGroup()
										.addGap(18, 18, 18)
										.addGroup(
												pnlData1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblIdReceptor)
														.addComponent(
																txtIdReceptor,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lblNIF)
														.addComponent(
																txtNIF,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlData1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(lblNombre)
														.addComponent(
																txtNombre,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlData1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblDireccion)
														.addComponent(
																txtDireccion,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlData1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblPoblacion)
														.addComponent(
																txtPoblacion,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCodigoPostal)
														.addComponent(
																txtCodigoPostal,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlData1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblTelefono)
														.addComponent(
																txtTelefono,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lblFax)
														.addComponent(
																txtFax,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlData1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblCuentaContable)
														.addComponent(
																txtCuentaContable,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																pnlData1Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(
																				chkMercadoLocal)
																		.addComponent(
																				lblMercadoLocal)))
										.addGap(13, 13, 13)));

		TabReceptor.addTab("1. Datos Generales", pnlData1);

		btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/cancel.png")));
		btnCancel.setToolTipText("Cancelar");
		btnCancel.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnCancelMousePressed(evt);
			}
		});

		btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/ok.png")));
		btnOk.setToolTipText("Aceptar");
		btnOk.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnOkMousePressed(evt);
			}
		});

		javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(
				pnlData);
		pnlData.setLayout(pnlDataLayout);
		pnlDataLayout
				.setHorizontalGroup(pnlDataLayout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(TabReceptor,
								javax.swing.GroupLayout.DEFAULT_SIZE, 655,
								Short.MAX_VALUE).addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlDataLayout.createSequentialGroup()
										.addContainerGap(528, Short.MAX_VALUE)
										.addComponent(btnCancel)
										.addGap(9, 9, 9).addComponent(btnOk)
										.addContainerGap()));
		pnlDataLayout.setVerticalGroup(pnlDataLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				pnlDataLayout.createSequentialGroup().addComponent(TabReceptor,
						javax.swing.GroupLayout.DEFAULT_SIZE, 262,
						Short.MAX_VALUE).addGap(18, 18, 18).addGroup(
						pnlDataLayout.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(btnCancel).addComponent(btnOk))
						.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 659,
				Short.MAX_VALUE).addGroup(
				layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						layout.createSequentialGroup().addGap(0, 0, 0)
								.addComponent(pnlData,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addGap(0, 0, 0))));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 350,
				Short.MAX_VALUE).addGroup(
				layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						layout.createSequentialGroup().addGap(0, 0, 0)
								.addComponent(pnlData,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addGap(0, 0, 0))));
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
					"FrmReceptor.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.receptor);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.btnCancelMousePressed()", he);
		}
	}

	private void txtCuentaContableKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaContable, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.txtCuentaContableKeyTyped()", he);
		}
	}

	private void txtFaxKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtFax, evt, 12);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.txtFaxKeyTyped()", he);
		}
	}

	private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtTelefono, evt, 12);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.txtTelefonoKeyTyped()", he);
		}
	}

	private void txtCodigoPostalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCodigoPostal, evt, 6);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.txtCodigoPostalKeyTyped()", he);
		}
	}

	private void txtPoblacionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtPoblacion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.txtPoblacionKeyTyped()", he);
		}
	}

	private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDireccion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.txtDireccionKeyTyped()", he);
		}
	}

	private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNombre, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.txtNombreKeyTyped()", he);
		}
	}

	private void txtNIFKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNIF, evt, 12);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.txtNIFKeyTyped()", he);
		}
	}

	private javax.swing.JTabbedPane TabReceptor;
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private javax.swing.JCheckBox chkMercadoLocal;
	private javax.swing.JLabel lblCodigoPostal;
	private javax.swing.JLabel lblCuentaContable;
	private javax.swing.JLabel lblDireccion;
	private javax.swing.JLabel lblFax;
	private javax.swing.JLabel lblIdReceptor;
	private javax.swing.JLabel lblMercadoLocal;
	private javax.swing.JLabel lblNIF;
	private javax.swing.JLabel lblNombre;
	private javax.swing.JLabel lblPoblacion;
	private javax.swing.JLabel lblTelefono;
	private javax.swing.JPanel pnlData;
	public javax.swing.JPanel pnlData1;
	private javax.swing.JTextField txtCodigoPostal;
	private javax.swing.JTextField txtCuentaContable;
	private javax.swing.JTextField txtDireccion;
	private javax.swing.JTextField txtFax;
	private javax.swing.JFormattedTextField txtIdReceptor;
	private javax.swing.JTextField txtNIF;
	private javax.swing.JTextField txtNombre;
	private javax.swing.JTextField txtPoblacion;
	private javax.swing.JTextField txtTelefono;

}
