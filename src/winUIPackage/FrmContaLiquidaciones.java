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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

import sessionPackage.MySession;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;
import entitiesPackage.Viewcontaliquidaciones;

/**
 *
 * @author  __USER__
 */
public class FrmContaLiquidaciones extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return FrmContaLiquidaciones.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmContaLiquidaciones.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmConta */
	public FrmContaLiquidaciones() {
		initComponents();
	}

	public FrmContaLiquidaciones(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmContaLiquidaciones.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message
					.ShowRuntimeError(parentFrame, "FrmContaLiquidaciones()",
							he);
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
					"FrmContaLiquidaciones.configureKeys", he);
		}
	}

	private void loadLiquidaciones() {

		try {
			Float totalHaber = ((Number) 0).floatValue();
			Float totalDebe = ((Number) 0).floatValue();
			Float totalCompras = ((Number) 0).floatValue();
			Float totalIGIC = ((Number) 0).floatValue();
			Float totalIRPF = ((Number) 0).floatValue();
			Float totalRappel = ((Number) 0).floatValue();
			Float importe = ((Number) 0).floatValue();

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

			txtMes.commitEdit();

			List<?> lineasList = entity.ContabilizaLiquidaciones(this,
					((Number) txtMes.getValue()).intValue());

			if (lineasList.size() > 0) {
				String concepto = "";
				String cadenaFecha = "";
				SimpleDateFormat formatFecha = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				NumberFormat formatDecimal = new DecimalFormat("#,##0.00");
				String simporte = "";

				for (Object linea : lineasList) {
					Vector<Object> oneRow = new Vector<Object>();
					if (((Viewcontaliquidaciones) linea).getId()
							.getCuentaContable() != null)
						oneRow.add(((Viewcontaliquidaciones) linea).getId()
								.getCuentaContable());
					else
						oneRow.add("");
					if (((Viewcontaliquidaciones) linea).getId().getFecha() != null)
						cadenaFecha = formatFecha
								.format(((Viewcontaliquidaciones) linea)
										.getId().getFecha());
					else
						cadenaFecha = "";
					oneRow.add(cadenaFecha);
					oneRow.add(txtAsiento.getText());
					concepto = ((Viewcontaliquidaciones) linea).getId()
							.getConcepto().toUpperCase();
					oneRow.add(concepto);
					oneRow.add(2);
					float baseImponible = ((Viewcontaliquidaciones) linea)
							.getId().getBaseImponible();
					float importeIgic = ((Viewcontaliquidaciones) linea)
							.getId().getImporteIgic();
					totalIGIC = totalIGIC + importeIgic;
					float importeIrpf = ((Viewcontaliquidaciones) linea)
							.getId().getImporteIrpf();
					float importeBonificacion = ((Viewcontaliquidaciones) linea)
					.getId().getImporteBonificacion();
					totalIRPF = totalIRPF + importeIrpf;
					totalRappel = totalRappel + importeBonificacion;
					importe = baseImponible + importeIgic - importeIrpf;
					simporte = formatDecimal.format(-importe);
					oneRow.add(simporte);
					oneRow.add(1);
					tableData.add(oneRow);

					totalHaber = totalHaber + importe;
				}

				Vector<Object> RowIGIC = new Vector<Object>();
				RowIGIC.add(txtCuentaIGIC.getText());
				RowIGIC.add(cadenaFecha);
				RowIGIC.add(txtAsiento.getText());
				RowIGIC.add(concepto);
				RowIGIC.add(1);
				simporte = formatDecimal.format(totalIGIC);
				RowIGIC.add(simporte);
				RowIGIC.add(1);
				tableData.add(RowIGIC);

				Vector<Object> RowIRPF = new Vector<Object>();
				RowIRPF.add(txtCuentaIRPF.getText());
				RowIRPF.add(cadenaFecha);
				RowIRPF.add(txtAsiento.getText());
				RowIRPF.add(concepto);
				RowIRPF.add(2);
				simporte = formatDecimal.format(-totalIRPF);
				RowIRPF.add(simporte);
				RowIRPF.add(1);
				tableData.add(RowIRPF);
				totalHaber = totalHaber + totalIRPF;

				totalCompras = totalHaber - totalRappel - totalIGIC;
				Vector<Object> RowCompras = new Vector<Object>();
				RowCompras.add(txtCuentaCompras.getText());
				RowCompras.add(cadenaFecha);
				RowCompras.add(txtAsiento.getText());
				RowCompras.add(concepto);
				RowCompras.add(1);
				simporte = formatDecimal.format(totalCompras);
				RowCompras.add(simporte);
				RowCompras.add(1);
				tableData.add(RowCompras);
				totalDebe = totalCompras + totalIGIC;
				
				Vector<Object> RowRappel = new Vector<Object>();
				RowRappel.add(txtCuentaRappel.getText());
				RowRappel.add(cadenaFecha);
				RowRappel.add(txtAsiento.getText());
				RowRappel.add(concepto);
				RowRappel.add(1);
				simporte = formatDecimal.format(totalRappel);
				RowRappel.add(simporte);
				RowRappel.add(1);
				tableData.add(RowRappel);
				totalDebe = totalDebe + totalRappel;

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
					"FrmConta.loadLiquidaciones()", he);
		} catch (ParseException e) {
			Message.ShowParseError(parentFrame, "FrmConta.loadLiquidaciones()",
					e);
		}
	}

	private boolean validateData() {
		try {

			if (txtMes.getText().equals("")) {
				Message.ShowValidateMessage(pnlData, "Debe indicar un mes.");
				txtMes.requestFocus();
				return (false);
			} else {
				try {
					txtMes.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no esválido.");
					txtMes.requestFocus();
					return (false);
				}
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFacturaPago.validateData()", he);
			return (false);
		}
	}

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
		lblMes = new javax.swing.JLabel();
		txtMes = new javax.swing.JFormattedTextField();
		cmdSearch = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblResult = new javax.swing.JTable();
		cmdCopy = new javax.swing.JButton();
		lblCuentaIGIC = new javax.swing.JLabel();
		txtCuentaIGIC = new javax.swing.JTextField();
		lblCuentaIRPF = new javax.swing.JLabel();
		txtCuentaIRPF = new javax.swing.JTextField();
		lblCuentaCompras = new javax.swing.JLabel();
		txtCuentaCompras = new javax.swing.JTextField();
		lblAsiento = new javax.swing.JLabel();
		txtAsiento = new javax.swing.JTextField();
		jSeparator1 = new javax.swing.JSeparator();
		lblCuentaRappel = new javax.swing.JLabel();
		txtCuentaRappel = new javax.swing.JTextField();

		setTitle("Contabilización de liquidaciones");

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData.setName("pnlData");
		pnlData.setPreferredSize(new java.awt.Dimension(1024, 768));

		btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/cerrar.png")));
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

		lblMes.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblMes.setForeground(new java.awt.Color(255, 0, 0));
		lblMes.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblMes.setText("Mes de liquidación");

		txtMes.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtMes
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtMes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtMes.setFont(new java.awt.Font("Segoe UI", 0, 14));

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
		cmdSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmdSearchActionPerformed(evt);
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

		lblCuentaIGIC.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaIGIC.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaIGIC.setText("Cuenta IGIC Soportado");
		lblCuentaIGIC
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaIGIC.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaIGIC.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaIGIC.setText("47200000");
		txtCuentaIGIC.setAutoscrolls(false);
		txtCuentaIGIC.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaIGIC.setName("telefono2");
		txtCuentaIGIC.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaIGICKeyTyped(evt);
			}
		});

		lblCuentaIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaIRPF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaIRPF.setText("Cuenta retenciones");
		lblCuentaIRPF
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaIRPF.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaIRPF.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaIRPF.setText("47510000");
		txtCuentaIRPF.setAutoscrolls(false);
		txtCuentaIRPF.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaIRPF.setName("telefono2");
		txtCuentaIRPF.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaIRPFKeyTyped(evt);
			}
		});

		lblCuentaCompras.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaCompras
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaCompras.setText("Cuenta compras");
		lblCuentaCompras
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaCompras.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaCompras.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaCompras.setText("48000001");
		txtCuentaCompras.setAutoscrolls(false);
		txtCuentaCompras.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaCompras.setName("telefono2");
		txtCuentaCompras.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaComprasKeyTyped(evt);
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

		lblCuentaRappel.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaRappel
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaRappel.setText("Cuenta rappel");
		lblCuentaRappel
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaRappel.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaRappel.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaRappel.setText("60900000");
		txtCuentaRappel.setAutoscrolls(false);
		txtCuentaRappel.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaRappel.setName("telefono2");
		txtCuentaRappel.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaRappelKeyTyped(evt);
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
										.addContainerGap()
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblMes,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				152,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtMes,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				50,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cmdSearch,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				104,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addContainerGap(
																				447,
																				Short.MAX_VALUE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				jSeparator1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				753,
																				Short.MAX_VALUE)
																		.addGap(
																				687,
																				687,
																				687))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																pnlDataLayout
																		.createSequentialGroup()
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGroup(
																												pnlDataLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																														.addGroup(
																																pnlDataLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblCuentaIRPF,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				137,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				txtCuentaIRPF,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				90,
																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																pnlDataLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblCuentaIGIC)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				txtCuentaIGIC,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				90,
																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																pnlDataLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblCuentaCompras,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				125,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				txtCuentaCompras,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				90,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)))
																										.addGap(
																												18,
																												18,
																												18)
																										.addComponent(
																												lblCuentaRappel,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												125,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtCuentaRappel,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												82,
																												Short.MAX_VALUE)
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
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								jScrollPane1,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								753,
																								Short.MAX_VALUE)
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
																												51,
																												Short.MAX_VALUE)
																										.addGroup(
																												pnlDataLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
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
																																		.addGap(
																																				328,
																																				328,
																																				328)
																																		.addComponent(
																																				btnClose))))
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
																		.addGap(
																				687,
																				687,
																				687)))));
		pnlDataLayout
				.setVerticalGroup(pnlDataLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlDataLayout
										.createSequentialGroup()
										.addGap(19, 19, 19)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				5,
																				5,
																				5)
																		.addComponent(
																				lblMes))
														.addComponent(
																txtMes,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(cmdSearch))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jSeparator1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(1, 1, 1)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txtCuentaIGIC,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCuentaIGIC))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txtCuentaIRPF,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCuentaIRPF))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblCuentaCompras)
														.addComponent(
																txtCuentaCompras,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblAsiento)
														.addComponent(
																txtAsiento,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCuentaRappel)
														.addComponent(
																txtCuentaRappel,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												211, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 781,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 504,
				Short.MAX_VALUE));

		pack();
	}

	private void cmdSearchActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private void txtCuentaRappelKeyTyped(java.awt.event.KeyEvent evt) {
	}

	private void txtAsientoKeyTyped(java.awt.event.KeyEvent evt) {

	}

	private void txtCuentaComprasKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaCompras, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaComprasKeyTyped()", he);
		}
	}

	private void txtCuentaIRPFKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaIRPF, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaIRPFKeyTyped()", he);
		}
	}

	private void txtCuentaIGICKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaIGIC, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaIGICKeyTyped()", he);
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
				loadLiquidaciones();
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

	private javax.swing.JButton btnClose;
	private javax.swing.JButton cmdCopy;
	private javax.swing.JButton cmdSearch;
	private javax.swing.JButton cmdSelectAll;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JLabel lblAsiento;
	private javax.swing.JLabel lblCuentaCompras;
	private javax.swing.JLabel lblCuentaIGIC;
	private javax.swing.JLabel lblCuentaIRPF;
	private javax.swing.JLabel lblCuentaRappel;
	private javax.swing.JLabel lblDebe;
	private javax.swing.JLabel lblDescuadre;
	private javax.swing.JLabel lblHaber;
	private javax.swing.JLabel lblMes;
	public static javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblResult;
	private javax.swing.JTextField txtAsiento;
	private javax.swing.JTextField txtCuentaCompras;
	private javax.swing.JTextField txtCuentaIGIC;
	private javax.swing.JTextField txtCuentaIRPF;
	private javax.swing.JTextField txtCuentaRappel;
	private javax.swing.JFormattedTextField txtDescuadre;
	private static javax.swing.JFormattedTextField txtImporte;
	private javax.swing.JFormattedTextField txtMes;
	private static javax.swing.JFormattedTextField txtPrecio;
	private javax.swing.JFormattedTextField txtTotalDebe;
	private javax.swing.JFormattedTextField txtTotalHaber;

}
