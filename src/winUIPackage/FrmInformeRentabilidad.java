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

import entitiesPackage.Message;
import reportsPackage.Rentabilidad;
import sessionPackage.MySession;

/**
 *
 * @author  __USER__
 */
public class FrmInformeRentabilidad extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MySession session;
	private java.awt.Frame parent;

	/** Creates new form FrmParamFacturas */
	public FrmInformeRentabilidad(java.awt.Frame parent, MySession session,
			boolean modal) {
		super(parent, modal);
		try {
			this.session = session;
			this.parent = parent;
			initComponents();
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent, "FrmInformeRentabilidad()", he);
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
					"FrmInformeRentabilidad.configureKeys", he);
		}
	}

	private boolean validateData() {

		try {
			if (!(txtFechaDesde.getText().equals(""))) {
				try {
					txtFechaDesde.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtFechaDesde.requestFocus();
					return (false);
				}
			} else {
				Message.ShowValidateMessage(parent,
				"Debe indicar la fecha de inicio.");
				txtFechaDesde.requestFocus();
				return (false);
			}
			if (!(txtFechaHasta.getText().equals(""))) {
				try {
					txtFechaHasta.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtFechaHasta.requestFocus();
					return (false);
				}
			} else {
				Message.ShowValidateMessage(parent,
				"Debe indicar la fecha final.");
				txtFechaHasta.requestFocus();
				return (false);
			}
			if (!(txtCuotaAgriten.getText().equals(""))) {
				try {
					txtCuotaAgriten.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtCuotaAgriten.requestFocus();
					return (false);
				}
			} else {
				txtCuotaAgriten.setValue(0);
			}
			if (!(txtComisiones.getText().equals(""))) {
				try {
					txtComisiones.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtComisiones.requestFocus();
					return (false);
				}
			} else {
				txtComisiones.setValue(0);
			}
			if (!(txtSubvenciones.getText().equals(""))) {
				try {
					txtSubvenciones.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtSubvenciones.requestFocus();
					return (false);
				}
			} else {
				txtSubvenciones.setValue(0);
			}
			if (!(txtAmortizaciones.getText().equals(""))) {
				try {
					txtAmortizaciones.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtAmortizaciones.requestFocus();
					return (false);
				}
			} else {
				txtAmortizaciones.setValue(0);
			}
			if (!(txtGastosFinancieros.getText().equals(""))) {
				try {
					txtGastosFinancieros.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtGastosFinancieros.requestFocus();
					return (false);
				}
			} else {
				txtGastosFinancieros.setValue(0);
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmInformeRentabilidad.validateData()", he);
			return (false);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		lblComisiones = new javax.swing.JLabel();
		txtComisiones = new javax.swing.JFormattedTextField();
		btnCancel = new javax.swing.JButton();
		btnOk = new javax.swing.JButton();
		lblSubvenciones = new javax.swing.JLabel();
		txtSubvenciones = new javax.swing.JFormattedTextField();
		lblAmortizaciones = new javax.swing.JLabel();
		txtAmortizaciones = new javax.swing.JFormattedTextField();
		lblGastosFinancieros = new javax.swing.JLabel();
		txtGastosFinancieros = new javax.swing.JFormattedTextField();
		lblCuotaAgriten = new javax.swing.JLabel();
		txtCuotaAgriten = new javax.swing.JFormattedTextField();
		lblFechaDesde = new javax.swing.JLabel();
		txtFechaDesde = new javax.swing.JFormattedTextField();
		lblFechaHasta = new javax.swing.JLabel();
		txtFechaHasta = new javax.swing.JFormattedTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Informe de Rentabilidad");
		setResizable(false);

		lblComisiones.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblComisiones.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblComisiones.setText("Comisiones");
		lblComisiones
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtComisiones.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtComisiones
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtComisiones.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtComisiones.setFont(new java.awt.Font("Segoe UI", 0, 14));

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

		lblSubvenciones.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSubvenciones
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSubvenciones.setText("Subvenciones");
		lblSubvenciones
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtSubvenciones.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSubvenciones
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtSubvenciones.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtSubvenciones.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblAmortizaciones.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblAmortizaciones
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblAmortizaciones.setText("Amortizaciones");
		lblAmortizaciones
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtAmortizaciones.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtAmortizaciones
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtAmortizaciones.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtAmortizaciones.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblGastosFinancieros.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblGastosFinancieros
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblGastosFinancieros.setText("Gastos financieros");
		lblGastosFinancieros
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtGastosFinancieros.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtGastosFinancieros
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtGastosFinancieros
				.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtGastosFinancieros.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblCuotaAgriten.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuotaAgriten
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuotaAgriten.setText("Cuota de Agriten");
		lblCuotaAgriten
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuotaAgriten.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuotaAgriten
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtCuotaAgriten.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuotaAgriten.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblFechaDesde.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFechaDesde.setForeground(new java.awt.Color(255, 0, 0));
		lblFechaDesde.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFechaDesde.setText("Fecha de inicio");

		txtFechaDesde.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtFechaDesde.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtFechaDesde.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblFechaHasta.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFechaHasta.setForeground(new java.awt.Color(255, 0, 0));
		lblFechaHasta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFechaHasta.setText("Fecha final");

		txtFechaHasta.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblCuotaAgriten,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																179,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblFechaHasta,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																158,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblFechaDesde,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																158,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				txtCuotaAgriten,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				99,
																				Short.MAX_VALUE)
																		.addGap(
																				105,
																				105,
																				105))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				txtFechaHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addContainerGap(
																				114,
																				Short.MAX_VALUE))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				txtFechaDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addContainerGap(
																				114,
																				Short.MAX_VALUE))))
						.addGroup(
								layout
										.createSequentialGroup()
										.addGap(12, 12, 12)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblComisiones,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				179,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtComisiones,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				101,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblSubvenciones,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				179,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtSubvenciones))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblAmortizaciones,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				179,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtAmortizaciones))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblGastosFinancieros,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				179,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtGastosFinancieros)))
										.addContainerGap(103, Short.MAX_VALUE))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap(275, Short.MAX_VALUE)
										.addComponent(btnCancel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnOk).addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap(46, Short.MAX_VALUE)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txtFechaDesde,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblFechaDesde))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txtFechaHasta,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblFechaHasta))
										.addGap(6, 6, 6)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lblCuotaAgriten)
														.addComponent(
																txtCuotaAgriten,
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
																lblComisiones)
														.addComponent(
																txtComisiones,
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
																lblSubvenciones)
														.addComponent(
																txtSubvenciones,
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
																lblAmortizaciones)
														.addComponent(
																txtAmortizaciones,
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
																lblGastosFinancieros)
														.addComponent(
																txtGastosFinancieros,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(btnCancel)
														.addComponent(btnOk))
										.addContainerGap()));

		lblFechaDesde.getAccessibleContext()
				.setAccessibleName("Fecha de incio");

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btnOkMousePressed(java.awt.event.MouseEvent evt) {
		try {
			if (validateData()) {
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
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
				
				Rentabilidad report = new Rentabilidad(parent);
				report
						.runReporte(session.getEmpresa().getIdEmpresa(),
								session.getEjercicio().getEjercicio(), fechadesde, fechahasta, 
								((Number) txtComisiones.getValue())
										.floatValue(),
								((Number) txtSubvenciones.getValue())
										.floatValue(),
								((Number) txtAmortizaciones.getValue())
										.floatValue(),
								((Number) txtGastosFinancieros.getValue())
										.floatValue(),
								((Number) txtCuotaAgriten.getValue())
										.floatValue());
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		} catch (Exception e) {
			Message.ShowErrorMessage(parent,
					"FrmInformeRentabilidad.btnOkMousePressed()", e);
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
					"FrmInformeRentabilidad.btnCancelMousePressed()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private javax.swing.JLabel lblAmortizaciones;
	private javax.swing.JLabel lblComisiones;
	private javax.swing.JLabel lblCuotaAgriten;
	private javax.swing.JLabel lblFechaDesde;
	private javax.swing.JLabel lblFechaHasta;
	private javax.swing.JLabel lblGastosFinancieros;
	private javax.swing.JLabel lblSubvenciones;
	private javax.swing.JFormattedTextField txtAmortizaciones;
	private javax.swing.JFormattedTextField txtComisiones;
	private javax.swing.JFormattedTextField txtCuotaAgriten;
	private javax.swing.JFormattedTextField txtFechaDesde;
	private javax.swing.JFormattedTextField txtFechaHasta;
	private javax.swing.JFormattedTextField txtGastosFinancieros;
	private javax.swing.JFormattedTextField txtSubvenciones;
	// End of variables declaration//GEN-END:variables

}