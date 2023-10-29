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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.ReplicationMode;
import org.hibernate.Transaction;

import sessionPackage.MySession;

import entitiesPackage.Bimestres;
import entitiesPackage.BimestresId;
import entitiesPackage.Message;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmBimestre extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	Bimestres bimestre;

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

	/** Creates new form FrmBimestre */
	public FrmBimestre() {
		initComponents();
	}

	public FrmBimestre(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			this.parentFrame = parent;
			this.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmBimestre()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmBimestre.configureKeys",
					he);
		}
	}

	public void newData() {

		try {
			this.bimestre = new Bimestres();
			txtAno.setValue(session.getEjercicio().getEjercicio());
			txtBimestre.setText("");
			txtDescripcion.setText("");
			txtDesdeFecha.setText("");
			txtHastaFecha.setText("");
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame, "FrmBimestre.newdata()",
					he);
		}
	}

	public void loadData(Bimestres entity) {

		try {
			this.bimestre = entity;
			txtAno.setValue(entity.getId().getEjercicios().getEjercicio());
			txtBimestre.setValue(entity.getId().getBimestre());
			txtDescripcion.setText(entity.getDescripcion());
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
			if (entity.getDesdeFecha() != null) {
				String strFechaDesde = df.format(entity.getDesdeFecha());
				txtDesdeFecha.setValue(strFechaDesde);
			} else
				txtDesdeFecha.setValue(null);
			if (entity.getHastaFecha() != null) {
				String strFechaHasta = df.format(entity.getHastaFecha());
				txtHastaFecha.setValue(strFechaHasta);
			} else
				txtHastaFecha.setValue(null);
			txtBimestre.setEditable(false);
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBimestre.loadData()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {

		try {
			if (validateData()) {
				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					BimestresId bimestreId = new BimestresId();
					bimestreId.setEmpresas(session.getEmpresa());
					bimestreId.setEjercicios(session.getEjercicio());
					bimestreId.setBimestre(((Number) txtBimestre.getValue())
							.intValue());
					bimestre.setId(bimestreId);
				}
				if (!txtDescripcion.getText().equals(""))
					bimestre.setDescripcion((String) txtDescripcion.getText());
				else
					bimestre.setDescripcion(null);
				if (!txtDesdeFecha.getText().equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtDesdeFecha.getText());
					bimestre.setDesdeFecha(value);
				} else
					bimestre.setDesdeFecha(null);
				if (!txtHastaFecha.getText().equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtHastaFecha.getText());
					bimestre.setHastaFecha(value);
				} else
					bimestre.setHastaFecha(null);
				bimestre.setLmd(new Date());
				bimestre.setSid("Santi");
				bimestre.setVersion(1);
				session.getSession().replicate(bimestre,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(bimestre);
				session.getSession().flush();
				if (!transaction.wasCommitted()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				OnNew = false;
				FrmBimestres.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBimestre.saveData()", he);
			return false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private boolean validateData() {

		try {
			if (txtBimestre.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un bimestre.");
				txtBimestre.requestFocus();
				return (false);
			} else {
				try {
					txtBimestre.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtBimestre.requestFocus();
					return (false);
				}
			}

			if (txtDescripcion.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar una descripción.");
				txtDescripcion.requestFocus();
				return (false);
			}

			if (!txtDesdeFecha.getText().equals(PreferencesUI.DateMask)) {
				try {
					txtDesdeFecha.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtDesdeFecha.requestFocus();
					return (false);
				}
			}

			if (!txtHastaFecha.getText().equals(PreferencesUI.DateMask)) {
				try {
					txtHastaFecha.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtHastaFecha.requestFocus();
					return (false);
				}
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBimestre.validateData()", he);
			return (false);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		pnlData = new javax.swing.JPanel();
		lblAno = new javax.swing.JLabel();
		lblBimestre = new javax.swing.JLabel();
		btnOk = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		lblDesdeFecha = new javax.swing.JLabel();
		lblHastaFecha = new javax.swing.JLabel();
		txtHastaFecha = new javax.swing.JFormattedTextField();
		txtDesdeFecha = new javax.swing.JFormattedTextField();
		txtAno = new javax.swing.JFormattedTextField();
		txtBimestre = new javax.swing.JFormattedTextField();
		lblDescripcion = new javax.swing.JLabel();
		txtDescripcion = new javax.swing.JTextField();

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData.setName("pnlData");

		lblAno.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblAno.setForeground(new java.awt.Color(255, 0, 0));
		lblAno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblAno.setText("A\u00f1o");

		lblBimestre.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblBimestre.setForeground(new java.awt.Color(255, 0, 0));
		lblBimestre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblBimestre.setText("Bimestre");

		btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/ok.png"))); // NOI18N
		btnOk.setToolTipText("Aceptar");
		btnOk.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnOkMousePressed(evt);
			}
		});

		btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/cancel.png"))); // NOI18N
		btnCancel.setToolTipText("Cancelar");
		btnCancel.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnCancelMousePressed(evt);
			}
		});

		lblDesdeFecha.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblDesdeFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblDesdeFecha.setText("Desde");

		lblHastaFecha.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblHastaFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblHastaFecha.setText("Hasta");

		txtHastaFecha.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtHastaFecha
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtHastaFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtHastaFecha.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtHastaFecha.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtHastaFechaFocusLost(evt);
			}
		});

		txtDesdeFecha.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtDesdeFecha
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtDesdeFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtDesdeFecha.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtDesdeFecha.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtDesdeFechaFocusLost(evt);
			}
		});

		txtAno.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtAno.setEditable(false);
		txtAno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtAno.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtAno.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtAnoFocusLost(evt);
			}
		});

		txtBimestre.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtBimestre
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtBimestre.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtBimestre.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtBimestre.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtBimestreFocusLost(evt);
			}
		});

		lblDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblDescripcion.setForeground(new java.awt.Color(255, 0, 0));
		lblDescripcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblDescripcion.setText("Descripci\u00f3n");

		txtDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtDescripcion.setAutoscrolls(false);
		txtDescripcion.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtDescripcion.setName("");
		txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtDescripcionKeyTyped(evt);
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
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblAno,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																120,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblBimestre,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																120,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblDescripcion,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																120,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblHastaFecha,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																120,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblDesdeFecha,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																120,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txtAno,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																60,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtBimestre,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																60,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtDesdeFecha,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																90,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtHastaFecha,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																90,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtDescripcion,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																248,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(291, 291, 291))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlDataLayout
										.createSequentialGroup()
										.addContainerGap(546, Short.MAX_VALUE)
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
										.addGap(60, 60, 60)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				txtAno,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtBimestre,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				44,
																				44,
																				44)
																		.addComponent(
																				txtDesdeFecha,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtHastaFecha,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblAno)
																		.addGap(
																				15,
																				15,
																				15)
																		.addComponent(
																				lblBimestre)
																		.addGap(
																				14,
																				14,
																				14)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lblDescripcion)
																						.addComponent(
																								txtDescripcion,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGap(
																				15,
																				15,
																				15)
																		.addComponent(
																				lblDesdeFecha)
																		.addGap(
																				12,
																				12,
																				12)
																		.addComponent(
																				lblHastaFecha)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												16, Short.MAX_VALUE)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
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
	}// </editor-fold>
	//GEN-END:initComponents

	private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {

		try {
			Util.keyTyped(txtDescripcion, evt, 25);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBimestre.txtDescripcionKeyTyped()", he);
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
					"FrmBimestre.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.bimestre);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBimestre.btnCancelMousePressed()", he);
		}
	}

	private void txtHastaFechaFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtHastaFecha);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBimestre.txtHastaFechaFocusLost()", he);
		}
	}

	private void txtDesdeFechaFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtDesdeFecha);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBimestre.txtDesdeFechaFocusLost()", he);
		}
	}

	private void txtBimestreFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtBimestre);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBimestre.txtBimestreFocusLost()", he);
		}
	}

	private void txtAnoFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtAno);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmBimestre.txtAnoFocusLost()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private javax.swing.JLabel lblAno;
	private javax.swing.JLabel lblBimestre;
	private javax.swing.JLabel lblDescripcion;
	private javax.swing.JLabel lblDesdeFecha;
	private javax.swing.JLabel lblHastaFecha;
	public javax.swing.JPanel pnlData;
	private javax.swing.JFormattedTextField txtAno;
	private javax.swing.JFormattedTextField txtBimestre;
	private javax.swing.JTextField txtDescripcion;
	private javax.swing.JFormattedTextField txtDesdeFecha;
	private javax.swing.JFormattedTextField txtHastaFecha;
	// End of variables declaration//GEN-END:variables

}