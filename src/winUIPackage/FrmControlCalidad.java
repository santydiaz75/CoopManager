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
import java.util.HashSet;
import java.util.Set;

import entitiesPackage.Message;
import reportsPackage.ControlCalidad;
import sessionPackage.MySession;

/**
 *
 * @author  __USER__
 */
public class FrmControlCalidad extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MySession session;
	private java.awt.Frame parent;

	/** Creates new form FrmParamFacturas */
	public FrmControlCalidad(java.awt.Frame parent, MySession session,
			boolean modal) {
		super(parent, modal);
		try {
			this.session = session;
			this.parent = parent;
			initComponents();
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent, "FrmControlCalidad()", he);
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
			Message.ShowRuntimeError(parent, "FrmControlCalidad.configureKeys",
					he);
		}
	}

	private boolean validateData() {

		try {
			if (txtSemanaDesde.getText().equals("")) {
				Message.ShowValidateMessage(parent,
						"Debe indicar la semana de incio.");
				txtSemanaDesde.requestFocus();
				return (false);
			} else {
				try {
					txtSemanaDesde.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtSemanaDesde.requestFocus();
					return (false);
				}
			}
			if (txtSemanaHasta.getText().equals("")) {
				Message.ShowValidateMessage(parent,
						"Debe indicar la semana final.");
				txtSemanaHasta.requestFocus();
				return (false);
			} else {
				try {
					txtSemanaHasta.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtSemanaHasta.requestFocus();
					return (false);
				}
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmControlCalidad.validateData()", he);
			return (false);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		lblSemanaDesde = new javax.swing.JLabel();
		txtSemanaDesde = new javax.swing.JFormattedTextField();
		lblSemanaHasta = new javax.swing.JLabel();
		txtSemanaHasta = new javax.swing.JFormattedTextField();
		btnCancel = new javax.swing.JButton();
		btnOk = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Informe de control de calidad");
		setResizable(false);

		lblSemanaDesde.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemanaDesde.setForeground(new java.awt.Color(255, 0, 0));
		lblSemanaDesde.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemanaDesde.setText("Semana desde");
		lblSemanaDesde
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtSemanaDesde.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemanaDesde
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemanaDesde.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtSemanaDesde.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblSemanaHasta.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemanaHasta.setForeground(new java.awt.Color(255, 0, 0));
		lblSemanaHasta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemanaHasta.setText("Semana hasta");
		lblSemanaHasta
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtSemanaHasta.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemanaHasta
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemanaHasta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtSemanaHasta.setFont(new java.awt.Font("Segoe UI", 0, 14));

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
										.addContainerGap(275, Short.MAX_VALUE)
										.addComponent(btnCancel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnOk).addContainerGap())
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblSemanaHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				179,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtSemanaHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				72,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblSemanaDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				179,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtSemanaDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				72,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(132, Short.MAX_VALUE)));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addGap(35, 35, 35)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lblSemanaDesde)
														.addComponent(
																txtSemanaDesde,
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
																txtSemanaHasta,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblSemanaHasta))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												72, Short.MAX_VALUE)
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
				ControlCalidad report = new ControlCalidad(parent);
				report.runReporte(session.getEmpresa().getIdEmpresa(), session
						.getEjercicio().getEjercicio(),
						((Number) txtSemanaDesde.getValue()).intValue(),
						((Number) txtSemanaHasta.getValue()).intValue());
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		} catch (Exception e) {
			Message.ShowErrorMessage(parent,
					"FrmControlCalidad.btnOkMousePressed()", e);
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
					"FrmControlCalidad.btnCancelMousePressed()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private javax.swing.JLabel lblSemanaDesde;
	private javax.swing.JLabel lblSemanaHasta;
	private javax.swing.JFormattedTextField txtSemanaDesde;
	private javax.swing.JFormattedTextField txtSemanaHasta;
	// End of variables declaration//GEN-END:variables

}