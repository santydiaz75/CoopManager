/*
 * FrmConta.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.AWTKeyStroke;
import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

import sessionPackage.MySession;
import entitiesPackage.Cosecheros;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;
import entitiesPackage.Liquidacionespagos;

/**
 *
 * @author  __USER__
 */
public class FrmContaLiquidacionesPagos extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return FrmContaLiquidacionesPagos.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmContaLiquidacionesPagos.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmConta */
	public FrmContaLiquidacionesPagos() {
		initComponents();
	}

	public FrmContaLiquidacionesPagos(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmContaLiquidacionesPagos.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmContaPagos()", he);
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
			Message.ShowRuntimeError(parentFrame,
					"FrmContaPagos.configureKeys", he);
		}
	}

	private void loadLiquidacionesPagos() {

		try {
			Double totalHaber = ((Number) 0).doubleValue();
			Double totalDebe = ((Number) 0).doubleValue();

			Vector<String> headerData = new Vector<String>();
			headerData.add("Cuenta contable");
			headerData.add("Fecha apunte");
			headerData.add("Asiento");
			headerData.add("Concepto");
			headerData.add("Debe=1;Haber=2");
			headerData.add("Importe");
			headerData.add("Número diario");

			Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();

			Integer numRows = tblResult.getRowCount();

			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblResult.getModel()).removeRow(0);

			txtFechaPagoDesde.commitEdit();
			txtFechaPagoHasta.commitEdit();

			Date fechadesde = null;
			Date fechahasta = null;

			if (!txtFechaPagoDesde.getText().equals("")) {
				SimpleDateFormat df = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				fechadesde = df.parse(txtFechaPagoDesde.getText());
			}
			if (!txtFechaPagoHasta.getText().equals("")) {
				SimpleDateFormat df = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				fechahasta = df.parse(txtFechaPagoHasta.getText());
			}

			List<?> lineasList = entity.ContabilizaLiquidacionesPagos(this,
					fechadesde, fechahasta);

			if (lineasList.size() > 0) {
				String concepto = "";
				String cadenaFecha = "";
				SimpleDateFormat formatFecha = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				NumberFormat formatDecimal = new DecimalFormat("#,##0.00");

				for (Object linea : lineasList) {
					Cosecheros cosechero = entity.CosecheroFindByIdCosechero(
							this, ((Liquidacionespagos) linea).getId()
									.getIdCosechero());
					concepto = "";
					if (((Liquidacionespagos) linea).getConcepto().equals(""))
						concepto = "SALDO A FAVOR "
								+ cosechero.getNombre().toUpperCase()
								+ cosechero.getApellidos().toUpperCase();
					else
						concepto = ((Liquidacionespagos) linea).getConcepto()
								.toUpperCase();
					Vector<Object> RowCosechero = new Vector<Object>();
					RowCosechero.add(cosechero.getCuentaContable());
					if (((Liquidacionespagos) linea).getFechaPago() != null)
						cadenaFecha = formatFecha
								.format(((Liquidacionespagos) linea)
										.getFechaPago());
					else
						cadenaFecha = "";
					RowCosechero.add(cadenaFecha);
					RowCosechero.add(txtAsiento.getText());
					RowCosechero.add(concepto);
					RowCosechero.add(1);
					Float importePago = ((Liquidacionespagos) linea)
							.getImporte();
					String simportePago = formatDecimal.format(importePago);
					RowCosechero.add(simportePago);
					RowCosechero.add(1);
					tableData.add(RowCosechero);

					totalDebe = totalDebe + importePago;

					String CuentaBanco = entity
							.EmpresaGetCuentaContableOfCuentaBanco(this,
									((Liquidacionespagos) linea)
											.getCuentaBancaria());
					Vector<Object> RowBanco = new Vector<Object>();
					RowBanco.add(CuentaBanco);
					RowBanco.add(cadenaFecha);
					RowBanco.add(txtAsiento.getText());
					RowBanco.add(concepto);
					RowBanco.add(2);
					Float importeBanco = ((Liquidacionespagos) linea)
							.getImporte();
					String simporteBanco = formatDecimal.format(-importeBanco);
					RowBanco.add(simporteBanco);
					RowBanco.add(1);
					tableData.add(RowBanco);
					if (((Liquidacionespagos) linea).getComision() != ((Number) 0)
							.floatValue()) {
						String CuentaComison = txtCuentaComisiones.getText();
						Vector<Object> RowComision = new Vector<Object>();
						RowComision.add(CuentaComison);
						RowComision.add(cadenaFecha);
						RowComision.add(txtAsiento.getText());
						RowComision.add("COMIS. " + concepto);
						RowComision.add(1);
						Float importeComision = ((Liquidacionespagos) linea).getComision();
						String simporteComision = formatDecimal
								.format(importeComision);
						RowComision.add(simporteComision);
						RowComision.add(1);
						tableData.add(RowComision);

						totalDebe = totalDebe + importeComision;
						
						Vector<Object> RowComisionBanco = new Vector<Object>();
						RowComisionBanco.add(CuentaBanco);
						RowComisionBanco.add(cadenaFecha);
						RowComisionBanco.add(txtAsiento.getText());
						RowComisionBanco.add("COMIS. " + concepto);
						RowComisionBanco.add(2);
						String simporteComisionBanco = formatDecimal
						.format(-importeComision);
						RowComisionBanco.add(simporteComisionBanco);
						RowComisionBanco.add(1);
						tableData.add(RowComisionBanco);
						
						importeBanco = importeBanco + importeComision;  
						
					}

					totalHaber = totalHaber + importeBanco;

				}

				tblResult.setModel(new MyViewTableModel(tableData, headerData));
				tblResult.getColumn("Cuenta contable").setPreferredWidth(
						EntityType.ShortTextWidth);
				tblResult.getColumn("Fecha apunte").setPreferredWidth(
						EntityType.DateWidth);
				tblResult.getColumn("Asiento").setPreferredWidth(
						EntityType.NumberWidth);
				tblResult.getColumn("Concepto").setPreferredWidth(
						EntityType.MediumTextWidth);
				tblResult.getColumn("Debe=1;Haber=2").setPreferredWidth(
						EntityType.ShortTextWidth);
				tblResult.getColumn("Importe").setPreferredWidth(
						EntityType.NumberWidth);
				tblResult.getColumn("Número diario").setPreferredWidth(
						EntityType.ShortTextWidth);

				txtTotalDebe.setValue(totalDebe);
				txtTotalHaber.setValue(totalHaber);
				txtDescuadre.setValue(totalDebe - totalHaber);
			}

		} catch (RuntimeException he) {
			he.printStackTrace();
			Message.ShowRuntimeError(parentFrame,
					"FrmConta.loadLiquidacionesPagos()", he);
		} catch (ParseException e) {
			Message.ShowParseError(parentFrame,
					"FrmConta.loadLiquidacionesPagos()", e);
		}
	}

	private boolean validateData() {
		try {

			if (txtFechaPagoDesde.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar una fecha de pago desde.");
				txtFechaPagoDesde.requestFocus();
				return (false);
			} else {
				try {
					txtFechaPagoDesde.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtFechaPagoDesde.requestFocus();
					return (false);
				}
			}

			if (txtFechaPagoHasta.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar una fecha de pago hasta.");
				txtFechaPagoHasta.requestFocus();
				return (false);
			} else {
				try {
					txtFechaPagoHasta.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtFechaPagoHasta.requestFocus();
					return (false);
				}
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmContaPagos.validateData()", he);
			return (false);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		pnlData = new javax.swing.JPanel();
		btnClose = new javax.swing.JButton();
		txtImporte = new javax.swing.JFormattedTextField();
		txtPrecio = new javax.swing.JFormattedTextField();
		lblDebe = new javax.swing.JLabel();
		txtTotalDebe = new javax.swing.JFormattedTextField();
		lblHaber = new javax.swing.JLabel();
		txtTotalHaber = new javax.swing.JFormattedTextField();
		cmdSelectAll = new javax.swing.JButton();
		lblDescuadre = new javax.swing.JLabel();
		txtDescuadre = new javax.swing.JFormattedTextField();
		cmdSearch = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblResult = new javax.swing.JTable();
		cmdCopy = new javax.swing.JButton();
		lblAsiento = new javax.swing.JLabel();
		txtAsiento = new javax.swing.JTextField();
		lblFechaPagoDesde = new javax.swing.JLabel();
		txtFechaPagoDesde = new javax.swing.JFormattedTextField();
		lblFechaPagoHasta = new javax.swing.JLabel();
		txtFechaPagoHasta = new javax.swing.JFormattedTextField();
		jSeparator1 = new javax.swing.JSeparator();
		lblCuentaComisiones = new javax.swing.JLabel();
		txtCuentaComisiones = new javax.swing.JTextField();

		setTitle("Contabilizaci\u00f3n de pagos de liquidaciones");

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData.setName("pnlData");
		pnlData.setPreferredSize(new java.awt.Dimension(1024, 768));

		btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/cerrar.png"))); // NOI18N
		btnClose.setToolTipText("Cancelar");
		btnClose.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnCloseMousePressed(evt);
			}
		});

		txtImporte
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtImporte.setPreferredSize(new java.awt.Dimension(0, 0));

		txtPrecio.setPreferredSize(new java.awt.Dimension(0, 0));

		lblDebe.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblDebe.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblDebe.setText("Total Debe");

		txtTotalDebe.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalDebe.setEditable(false);
		txtTotalDebe
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtTotalDebe.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalDebe.setFont(new java.awt.Font("Segoe UI", 1, 14));

		lblHaber.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblHaber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblHaber.setText("Total Haber");

		txtTotalHaber.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalHaber.setEditable(false);
		txtTotalHaber
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtTotalHaber.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalHaber.setFont(new java.awt.Font("Segoe UI", 1, 14));

		cmdSelectAll.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdSelectAll.setText("Seleccionar todo");
		cmdSelectAll.setToolTipText("Seleccionar todas las filas");
		cmdSelectAll.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdSelectAll.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdSelectAllMousePressed(evt);
			}
		});

		lblDescuadre.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblDescuadre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblDescuadre.setText("Descuadre");

		txtDescuadre.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtDescuadre.setEditable(false);
		txtDescuadre
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtDescuadre.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtDescuadre.setFont(new java.awt.Font("Segoe UI", 1, 14));

		cmdSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdSearch.setText("Consultar");
		cmdSearch.setToolTipText("Consultar");
		cmdSearch.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdSearch.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdSearchMousePressed(evt);
			}
		});

		jScrollPane1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		tblResult.setFont(new java.awt.Font("Segoe UI", 0, 14));
		tblResult.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblResult.setRowHeight(18);
		jScrollPane1.setViewportView(tblResult);

		cmdCopy.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdCopy.setText("Copiar al portapapeles");
		cmdCopy.setToolTipText("Copiar al portapapeles todas las filas");
		cmdCopy.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdCopy.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdCopyMousePressed(evt);
			}
		});

		lblAsiento.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblAsiento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblAsiento.setText("Asiento");
		lblAsiento.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtAsiento.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtAsiento.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtAsiento.setText("000000");
		txtAsiento.setAutoscrolls(false);
		txtAsiento.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtAsiento.setName("telefono2");
		txtAsiento.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtAsientoKeyTyped(evt);
			}
		});

		lblFechaPagoDesde.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFechaPagoDesde.setForeground(new java.awt.Color(255, 0, 0));
		lblFechaPagoDesde
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFechaPagoDesde.setText("Fec. pago desde");
		lblFechaPagoDesde
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtFechaPagoDesde.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtFechaPagoDesde
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtFechaPagoDesde.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtFechaPagoDesde.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblFechaPagoHasta.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFechaPagoHasta.setForeground(new java.awt.Color(255, 0, 0));
		lblFechaPagoHasta
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFechaPagoHasta.setText("Fec. pago hasta");
		lblFechaPagoHasta
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtFechaPagoHasta.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtFechaPagoHasta
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtFechaPagoHasta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtFechaPagoHasta.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblCuentaComisiones.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaComisiones
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaComisiones.setText("Cuenta comisiones");
		lblCuentaComisiones
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaComisiones.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaComisiones.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaComisiones.setText("62600000");
		txtCuentaComisiones.setAutoscrolls(false);
		txtCuentaComisiones.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaComisiones.setName("telefono2");
		txtCuentaComisiones.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaComisionesKeyTyped(evt);
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
										.addGap(92, 92, 92)
										.addComponent(
												lblAsiento,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												90,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												txtAsiento,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												90,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(lblCuentaComisiones)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												txtCuentaComisiones,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												90,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(270, Short.MAX_VALUE))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlDataLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																753,
																Short.MAX_VALUE)
														.addComponent(
																jSeparator1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																753,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblFechaPagoDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				170,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtFechaPagoDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				89,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblFechaPagoHasta)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtFechaPagoHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				8,
																				8,
																				8)
																		.addComponent(
																				cmdSearch,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				104,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				173,
																				Short.MAX_VALUE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				cmdSelectAll,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				157,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cmdCopy,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				157,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				312,
																				Short.MAX_VALUE)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								pnlDataLayout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																										.addGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING,
																												pnlDataLayout
																														.createSequentialGroup()
																														.addGap(
																																57,
																																57,
																																57)
																														.addComponent(
																																txtPrecio,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGap(
																																63,
																																63,
																																63))
																										.addGroup(
																												pnlDataLayout
																														.createSequentialGroup()
																														.addGap(
																																40,
																																40,
																																40)
																														.addComponent(
																																txtImporte,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																0,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGap(
																																73,
																																73,
																																73)))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												btnClose)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblDebe,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				197,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtTotalDebe,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				26,
																				26,
																				26)
																		.addComponent(
																				lblHaber)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtTotalHaber,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblDescuadre)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtDescuadre,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(687, 687, 687)));
		pnlDataLayout
				.setVerticalGroup(pnlDataLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlDataLayout
										.createSequentialGroup()
										.addGap(24, 24, 24)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(cmdSearch)
														.addComponent(
																lblFechaPagoDesde)
														.addComponent(
																lblFechaPagoHasta)
														.addComponent(
																txtFechaPagoDesde,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtFechaPagoHasta,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jSeparator1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												12,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				lblAsiento)
																		.addComponent(
																				txtAsiento,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																lblCuentaComisiones)
														.addComponent(
																txtCuentaComisiones,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												37, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(lblDebe)
														.addComponent(
																txtTotalDebe,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblDescuadre)
														.addComponent(
																txtDescuadre,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lblHaber)
														.addComponent(
																txtTotalHaber,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				7,
																				7,
																				7)
																		.addComponent(
																				btnClose)
																		.addGap(
																				19,
																				19,
																				19)
																		.addComponent(
																				txtImporte,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				0,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtPrecio,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								cmdSelectAll)
																						.addComponent(
																								cmdCopy))))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 782,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 292,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void txtCuentaComisionesKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaComisiones, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaPrestacionServiciosKeyTyped()", he);
		}
	}

	private void txtAsientoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtAsiento, evt, 6);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData, "FrmConta.txtAsientoKeyTyped()",
					he);
		}
	}

	private void cmdCopyMousePressed(java.awt.event.MouseEvent evt) {
		if (!(tblResult.getSelectedRows().length > 0))
			Message
					.ShowValidateMessage(pnlData,
							"Debe seleccionar los apuntes a copiar en el portapaleles.");
		else {
			TransferHandler th = tblResult.getTransferHandler();
			if (th != null) {
				Toolkit tk = Toolkit.getDefaultToolkit();
				th.exportToClipboard(tblResult, tk.getSystemClipboard(),
						TransferHandler.COPY);
				Message.ShowInformationMessage(pnlData, "Se han copiado "
						+ tblResult.getSelectedRows().length
						+ " apuntes en el portapapeles.");
			}
		}
	}

	private void cmdSearchMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (validateData())
				loadLiquidacionesPagos();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmConta.cmdSearchMousePressed()", he);
		}
	}

	private void cmdSelectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblResult.getRowCount(); actualrow++)
				tblResult.changeSelection(actualrow, 0, true, false);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmConta.cmdSelectAllMousePressed()", he);
		}
	}

	void btnCloseMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			this.dispose();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmConta.btnCancelMousePressed()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnClose;
	private javax.swing.JButton cmdCopy;
	private javax.swing.JButton cmdSearch;
	private javax.swing.JButton cmdSelectAll;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JLabel lblAsiento;
	private javax.swing.JLabel lblCuentaComisiones;
	private javax.swing.JLabel lblDebe;
	private javax.swing.JLabel lblDescuadre;
	private javax.swing.JLabel lblFechaPagoDesde;
	private javax.swing.JLabel lblFechaPagoHasta;
	private javax.swing.JLabel lblHaber;
	public static javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblResult;
	private javax.swing.JTextField txtAsiento;
	private javax.swing.JTextField txtCuentaComisiones;
	private javax.swing.JFormattedTextField txtDescuadre;
	private javax.swing.JFormattedTextField txtFechaPagoDesde;
	private javax.swing.JFormattedTextField txtFechaPagoHasta;
	private static javax.swing.JFormattedTextField txtImporte;
	private static javax.swing.JFormattedTextField txtPrecio;
	private javax.swing.JFormattedTextField txtTotalDebe;
	private javax.swing.JFormattedTextField txtTotalHaber;
	// End of variables declaration//GEN-END:variables

}