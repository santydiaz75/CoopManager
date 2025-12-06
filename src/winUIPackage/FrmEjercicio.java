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

import entitiesPackage.Ejercicios;
import entitiesPackage.Message;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmEjercicio extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	Ejercicios ejercicio;

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
	public FrmEjercicio() {
		initComponents();
	}

	public FrmEjercicio(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			this.parentFrame = parent;
			this.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEjercicio()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmEjercicio.configureKeys",
					he);
		}
	}

	public void newData() {

		try {
			this.ejercicio = new Ejercicios();
			txtAno.setText("");
			txtAno.setEditable(true);
			txtDesdeFecha.setText("");
			txtHastaFecha.setText("");
			txtComentarios.setText("");
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmEjercicio.newData()", he);
		}

	}

	public void loadData(Ejercicios entity) {

		try {
			this.ejercicio = entity;
			txtAno.setValue(entity.getEjercicio());
			txtAno.setEditable(false);
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
			txtComentarios.setText(entity.getComentarios());
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmEjercicio.loadData()", he);
		}

	}

	public boolean saveData(Boolean showmessage) {

		try {
			if (validateData()) {
				Transaction transaction = session.getSession()
						.beginTransaction();

				ejercicio.setEjercicio(((Number) txtAno.getValue()).intValue());
				if (!txtDesdeFecha.getText().equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtDesdeFecha.getText());
					ejercicio.setDesdeFecha(value);
				} else
					ejercicio.setDesdeFecha(null);
				if (!txtHastaFecha.getText().equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtHastaFecha.getText());
					ejercicio.setHastaFecha(value);
				} else
					ejercicio.setHastaFecha(null);
				ejercicio.setLmd(new Date());
				ejercicio.setSid("Santi");
				ejercicio.setVersion(1);
				session.getSession().replicate(ejercicio,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(ejercicio);
				session.getSession().flush();
				if (transaction.isActive()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				FrmEjercicios.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmEjercicio.saveData()", he);
			return false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private boolean validateData() {

		try {
			if (txtAno.getText().equals("")) {
				Message.ShowValidateMessage(pnlData, "Debe indicar un a�o.");
				txtAno.requestFocus();
				return (false);
			} else {
				try {
					txtAno.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es v�lido.");
					txtAno.requestFocus();
					return (false);
				}
			}

			if (!txtDesdeFecha.getText().equals(PreferencesUI.DateMask)) {
				try {
					txtDesdeFecha.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es v�lido.");
					txtDesdeFecha.requestFocus();
					return (false);
				}
			}

			if (!txtHastaFecha.getText().equals(PreferencesUI.DateMask)) {
				try {
					txtHastaFecha.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es v�lido.");
					txtHastaFecha.requestFocus();
					return (false);
				}
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmEjercicio.validateData()", he);
			return (false);
		}

	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		pnlData = new javax.swing.JPanel();
		lblAno = new javax.swing.JLabel();
		btnOk = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		lblDesdeFecha = new javax.swing.JLabel();
		lblHastaFecha = new javax.swing.JLabel();
		txtHastaFecha = new javax.swing.JFormattedTextField();
		txtDesdeFecha = new javax.swing.JFormattedTextField();
		txtAno = new javax.swing.JFormattedTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		txtComentarios = new javax.swing.JTextArea();
		lblComentarios = new javax.swing.JLabel();

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData.setName("pnlData");

		lblAno.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblAno.setForeground(new java.awt.Color(255, 0, 0));
		lblAno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblAno.setText("A�o");

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
		txtAno
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtAno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtAno.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtAno.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtAnoFocusLost(evt);
			}
		});

		txtComentarios.setColumns(20);
		txtComentarios.setRows(5);
		txtComentarios.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		jScrollPane1.setViewportView(txtComentarios);

		lblComentarios.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblComentarios.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblComentarios.setText("Comentarios");

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
										.addGap(12, 12, 12)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblComentarios,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jScrollPane1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				0,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblDesdeFecha,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												txtDesdeFecha,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblAno,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												txtAno,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblHastaFecha,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												txtHastaFecha,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addGap(
																				484,
																				484,
																				484))))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlDataLayout
										.createSequentialGroup()
										.addContainerGap(591, Short.MAX_VALUE)
										.addComponent(btnCancel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnOk).addContainerGap()));
		pnlDataLayout
				.setVerticalGroup(pnlDataLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlDataLayout
										.createSequentialGroup()
										.addGap(77, 77, 77)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(lblAno)
														.addComponent(
																txtAno,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblDesdeFecha)
														.addComponent(
																txtDesdeFecha,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblHastaFecha)
														.addComponent(
																txtHastaFecha,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																121,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblComentarios))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																btnCancel,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																btnOk,
																javax.swing.GroupLayout.Alignment.TRAILING))
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

	private void btnOkMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			saveData(true);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmEjercicio.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.ejercicio);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmEjercicio.btnCancelMousePressed()", he);
		}
	}

	private void txtHastaFechaFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtHastaFecha);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmEjercicio.txtHastaFechaFocusLost()", he);
		}
	}

	private void txtDesdeFechaFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtDesdeFecha);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmEjercicio.txtDesdeFechaFocusLost()", he);
		}
	}

	private void txtAnoFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtAno);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmEjercicio.txtAnoFocusLost()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblAno;
	private javax.swing.JLabel lblComentarios;
	private javax.swing.JLabel lblDesdeFecha;
	private javax.swing.JLabel lblHastaFecha;
	public javax.swing.JPanel pnlData;
	private javax.swing.JFormattedTextField txtAno;
	private javax.swing.JTextArea txtComentarios;
	private javax.swing.JFormattedTextField txtDesdeFecha;
	private javax.swing.JFormattedTextField txtHastaFecha;
	// End of variables declaration//GEN-END:variables

}
