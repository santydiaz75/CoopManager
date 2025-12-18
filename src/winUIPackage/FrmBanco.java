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

import entitiesPackage.Bancos;
import entitiesPackage.BancosId;
import entitiesPackage.Message;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmBanco extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	Bancos banco;

	private java.awt.Frame parentFrame;
	private MySession session;
	private boolean OnNew = false;

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
	public FrmBanco() {
		initComponents();
	}

	public FrmBanco(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			this.parentFrame = parent;
			this.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmBanco()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmBanco.configureKeys",
					he);
		}
	}

	public void newData() {
		try {
			this.banco = new Bancos();
			txtIdBanco.setEditable(true);
			txtIdSucursal.setEditable(true);
			txtIdBanco.setText("");
			txtIdSucursal.setText("");
			txtNombreBanco.setText("");
			txtNombreSucursal.setText("");
			txtCuentaContable.setText("");
			OnNew = true;
		} catch (RuntimeException he) {
			Message
					.ShowRuntimeError(this.parentFrame, "FrmBanco.newData()",
							he);
		}
	}

	public void loadData(Bancos entity) {

		try {
			this.banco = entity;
			txtIdBanco.setEditable(false);
			txtIdSucursal.setEditable(false);
			txtIdBanco.setText(entity.getId().getIdBanco());
			txtIdSucursal.setText(entity.getId().getIdSucursal());
			txtNombreBanco.setText(entity.getNombreBanco());
			txtNombreSucursal.setText(entity.getNombreSucursal());
			txtCuentaContable.setText(entity.getCuentaContable());
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame, "FrmBanco.loadData()",
					he);
		}
	}

	public boolean saveData(Boolean showmessage) {

		try {
			if (validateData()) {
				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					BancosId bancoId = new BancosId();
					bancoId.setEmpresas(session.getEmpresa());
					bancoId.setEjercicios(session.getEjercicio());
					bancoId.setIdBanco(txtIdBanco.getText());
					bancoId.setIdSucursal(txtIdSucursal.getText());
					banco.setId(bancoId);
				}
				if (!txtNombreBanco.getText().equals(""))
					banco.setNombreBanco(txtNombreBanco.getText());
				else
					banco.setNombreBanco(null);
				if (!txtNombreSucursal.getText().equals(""))
					banco.setNombreSucursal(txtNombreSucursal.getText());
				else
					banco.setNombreSucursal(null);
				if (!txtCuentaContable.getText().equals(""))
					banco.setCuentaContable(txtCuentaContable.getText());
				else
					banco.setCuentaContable(null);
				banco.setLmd(new Date());
				banco.setSid("Santi");
				banco.setVersion(1);
				session.getSession()
						.replicate(banco, ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(banco);

				session.getSession().flush();
				if (!transaction.isActive()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				OnNew = false;
				FrmBancos.runSearchQuery();
				return true;
			}
			else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame, "FrmBanco.saveData()",
					he);
			return false;
		}
	}

	private boolean validateData() {

		try {
			if (txtIdBanco.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un código para el banco.");
				txtIdBanco.requestFocus();
				return (false);
			}
			if (txtIdSucursal.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un código para la sucursal.");
				txtIdSucursal.requestFocus();
				return (false);
			}
			if (txtNombreBanco.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un nombre para el banco.");
				txtNombreBanco.requestFocus();
				return (false);
			}
			if (txtNombreSucursal.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un nombre para la sucursal.");
				txtNombreSucursal.requestFocus();
				return (false);
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBanco.validateData()", he);
			return (false);
		}

	}

	private void initComponents() {

		pnlData = new javax.swing.JPanel();
		lblIdBanco = new javax.swing.JLabel();
		lblIdSucursal = new javax.swing.JLabel();
		lblNombreBanco = new javax.swing.JLabel();
		lblNombreSucursal = new javax.swing.JLabel();
		txtIdBanco = new javax.swing.JTextField();
		txtIdSucursal = new javax.swing.JTextField();
		txtNombreBanco = new javax.swing.JTextField();
		txtNombreSucursal = new javax.swing.JTextField();
		btnOk = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		lblCuentaContable = new javax.swing.JLabel();
		txtCuentaContable = new javax.swing.JTextField();

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData.setName("pnlData");

		lblIdBanco.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblIdBanco.setForeground(new java.awt.Color(255, 0, 0));
		lblIdBanco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblIdBanco.setText("Id banco");
		lblIdBanco.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		lblIdSucursal.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblIdSucursal.setForeground(new java.awt.Color(255, 0, 0));
		lblIdSucursal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblIdSucursal.setText("Id sucursal");
		lblIdSucursal
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		lblNombreBanco.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblNombreBanco.setForeground(new java.awt.Color(255, 0, 0));
		lblNombreBanco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblNombreBanco.setText("Nombre banco");

		lblNombreSucursal.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblNombreSucursal.setForeground(new java.awt.Color(255, 0, 0));
		lblNombreSucursal
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblNombreSucursal.setText("Nombre sucursal");
		lblNombreSucursal
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtIdBanco.setEditable(false);
		txtIdBanco.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtIdBanco.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtIdBanco.setAutoscrolls(false);
		txtIdBanco.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdBanco.setName("");
		txtIdBanco.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtIdBancoKeyTyped(evt);
			}
		});

		txtIdSucursal.setEditable(false);
		txtIdSucursal.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtIdSucursal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtIdSucursal.setAutoscrolls(false);
		txtIdSucursal.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdSucursal.setName("");
		txtIdSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtIdSucursalKeyTyped(evt);
			}
		});

		txtNombreBanco.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtNombreBanco.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtNombreBanco.setAutoscrolls(false);
		txtNombreBanco.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtNombreBanco.setName("");
		txtNombreBanco.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtNombreBancoKeyTyped(evt);
			}
		});

		txtNombreSucursal.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtNombreSucursal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtNombreSucursal.setAutoscrolls(false);
		txtNombreSucursal.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtNombreSucursal.setName("");
		txtNombreSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtNombreSucursalKeyTyped(evt);
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
		txtCuentaContable.setName("");
		txtCuentaContable.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaContableKeyTyped(evt);
			}
		});

		javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(
				pnlData);
		pnlData.setLayout(pnlDataLayout);
		pnlDataLayout
				.setHorizontalGroup(pnlDataLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlDataLayout
										.createSequentialGroup()
										.addGap(18, 18, 18)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblIdBanco,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtIdBanco,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				60,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblIdSucursal,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtIdSucursal,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				60,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblNombreBanco,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtNombreBanco,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				480,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblNombreSucursal,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtNombreSucursal,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				480,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
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
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(21, 21, 21))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlDataLayout
										.createSequentialGroup()
										.addContainerGap(524, Short.MAX_VALUE)
										.addComponent(btnCancel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnOk).addContainerGap()));
		pnlDataLayout
				.setVerticalGroup(pnlDataLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlDataLayout
										.createSequentialGroup()
										.addGap(62, 62, 62)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblIdBanco)
														.addComponent(
																txtIdBanco,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblIdSucursal)
														.addComponent(
																txtIdSucursal,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblNombreBanco)
														.addComponent(
																txtNombreBanco,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblNombreSucursal)
														.addComponent(
																txtNombreSucursal,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblCuentaContable)
														.addComponent(
																txtCuentaContable,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												38, Short.MAX_VALUE)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(btnCancel)
														.addComponent(btnOk))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	}

	private void txtCuentaContableKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaContable, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBanco.txtCuentaContableKeyTyped()", he);
		}
	}

	private void txtNombreSucursalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNombreSucursal, evt, 25);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBanco.txtNombreSucursalKeyTyped()", he);
		}
	}

	private void txtNombreBancoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNombreBanco, evt, 25);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBanco.txtNombreBancoKeyTyped()", he);
		}
	}

	private void txtIdSucursalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdSucursal, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBanco.txtIdSucursalKeyTyped()", he);
		}
	}

	private void txtIdBancoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdBanco, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBanco.txtIdBancoKeyTyped()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew) 
				newData();
			else
				loadData(this.banco);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBanco.btnCancelMousePressed()", he);
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
					"FrmBanco.btnOkMousePressed()", he);
		}
	}

	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private javax.swing.JLabel lblCuentaContable;
	private javax.swing.JLabel lblIdBanco;
	private javax.swing.JLabel lblIdSucursal;
	private javax.swing.JLabel lblNombreBanco;
	private javax.swing.JLabel lblNombreSucursal;
	public javax.swing.JPanel pnlData;
	private javax.swing.JTextField txtCuentaContable;
	private javax.swing.JTextField txtIdBanco;
	private javax.swing.JTextField txtIdSucursal;
	private javax.swing.JTextField txtNombreBanco;
	private javax.swing.JTextField txtNombreSucursal;

}
