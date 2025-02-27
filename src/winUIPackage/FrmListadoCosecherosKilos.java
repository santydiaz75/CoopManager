/*
 * FrmParamFacturas.java
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

import entitiesPackage.Entity;
import entitiesPackage.Message;

import reportsPackage.ListadoCosecherosKilos;
import sessionPackage.MySession;

/**
 *
 * @author  __USER__
 */
public class FrmListadoCosecherosKilos extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MySession session;
	private java.awt.Frame parent;
	private Entity entity = new Entity();

	/** Creates new form FrmRelacionCosecherosKilos */
	public FrmListadoCosecherosKilos(java.awt.Frame parent, MySession session,
			boolean modal) {
		super(parent, modal);
		try {
			this.session = session;
			this.parent = parent;
			entity.setSession(session);
			initComponents();
			configureKeys();

		} catch (RuntimeException he) {
			Message
					.ShowRuntimeError(parent, "FrmRelacionCosecherosKilos()",
							he);
		}
	}

	private void configureKeys() {
		try {
			Set<AWTKeyStroke> teclasTab = new HashSet<AWTKeyStroke>();
			teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
			teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
			teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0));
			this.setFocusTraversalKeys(
					KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, teclasTab);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmRelacionCosecherosKilos.configureKeys", he);
		}
	}

	private boolean validateData() {

		try {
			if (txtFechaDesde.getText().equals("")) {
				Message.ShowValidateMessage(parent,
						"Debe indicar la fecha de inicio.");
				txtFechaDesde.requestFocus();
				return (false);
			} else {
				try {
					txtFechaDesde.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtFechaDesde.requestFocus();
					return (false);
				}
			}
			if (txtFechaHasta.getText().equals("")) {
				Message.ShowValidateMessage(parent,
						"Debe indicar la fecha final.");
				txtFechaHasta.requestFocus();
				return (false);
			} else {
				try {
					txtFechaHasta.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtFechaHasta.requestFocus();
					return (false);
				}
			}
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmRelacionCosecherosKilos.validateData()", he);
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

		btnCancel = new javax.swing.JButton();
		btnOk = new javax.swing.JButton();
		lblFechaDesde = new javax.swing.JLabel();
		txtFechaDesde = new javax.swing.JFormattedTextField();
		lblFechaHasta = new javax.swing.JLabel();
		txtFechaHasta = new javax.swing.JFormattedTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Informe de relaci\u00f3n de cosecheros y kilos");
		setResizable(false);

		btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/cerrar.png"))); // NOI18N
		btnCancel.setToolTipText("Cancelar");
		btnCancel.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnCancelMousePressed(evt);
			}
		});

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

		lblFechaDesde.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFechaDesde.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFechaDesde.setText("Fecha desde");
		lblFechaDesde
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtFechaDesde.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtFechaDesde
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtFechaDesde.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtFechaDesde.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblFechaHasta.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFechaHasta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFechaHasta.setText("Fecha hasta");
		lblFechaHasta
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtFechaHasta.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtFechaHasta
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtFechaHasta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtFechaHasta.setFont(new java.awt.Font("Segoe UI", 0, 14));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap(198, Short.MAX_VALUE)
										.addComponent(btnCancel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnOk).addContainerGap())
						.addGroup(
								layout
										.createSequentialGroup()
										.addGap(37, 37, 37)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblFechaDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				179,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				txtFechaDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblFechaHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				179,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				txtFechaHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lblFechaDesde)
														.addComponent(
																txtFechaDesde,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txtFechaHasta,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblFechaHasta))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(btnCancel)
														.addComponent(btnOk))
										.addContainerGap()));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btnOkMousePressed(java.awt.event.MouseEvent evt) {
		try {
			if (validateData()) {
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				ListadoCosecherosKilos report = new ListadoCosecherosKilos(
						parent);
				Date fechadesde = null;
				Date fechahasta = null;

				if (!txtFechaDesde.getText().equals("")) {
					SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
					fechadesde = df.parse(txtFechaDesde.getText());
				}
				if (!txtFechaHasta.getText().equals("")) {
					SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
					fechahasta = df.parse(txtFechaHasta.getText());
				}

				report.runReporte(session.getEmpresa().getIdEmpresa(),
						fechadesde, fechahasta);
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmRelacionCosecherosKilos.btnOkMousePressed()", he);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			this.dispose();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmRelacionCosecherosKilos.btnCancelMousePressed()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private javax.swing.JLabel lblFechaDesde;
	private javax.swing.JLabel lblFechaHasta;
	private javax.swing.JFormattedTextField txtFechaDesde;
	private javax.swing.JFormattedTextField txtFechaHasta;
	// End of variables declaration//GEN-END:variables

}