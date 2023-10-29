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
import entitiesPackage.Facturascabecera;
import entitiesPackage.Message;

/**
 *
 * @author  __USER__
 */
public class FrmContaFacturas extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return FrmContaFacturas.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmContaFacturas.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmConta */
	public FrmContaFacturas() {
		initComponents();
	}

	public FrmContaFacturas(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmContaFacturas.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmContaFacturas()", he);
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
					"FrmContaFacturas.configureKeys", he);
		}
	}

	private void loadFacturas() {

		try {
			Float totalHaber = ((Number) 0).floatValue();
			Float totalDebe = ((Number) 0).floatValue();

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

			txtFacturaDesde.commitEdit();
			txtFacturaHasta.commitEdit();

			List<?> lineasList = entity.ContabilizaFacturas(this,
					((Number) txtFacturaDesde.getValue()).intValue(),
					((Number) txtFacturaHasta.getValue()).intValue());

			if (lineasList.size() > 0) {
				String concepto = "";
				String cadenaFecha = "";
				SimpleDateFormat formatFecha = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				NumberFormat formatDecimal = new DecimalFormat("#,##0.00");

				for (Object linea : lineasList) {
					concepto = "FRA Nº "
							+ ((Facturascabecera) linea).getId().getIdFactura()
							+ " "
							+ ((Facturascabecera) linea).getNombre()
									.toUpperCase();
					Vector<Object> RowCliente = new Vector<Object>();
					RowCliente.add(((Facturascabecera) linea)
							.getCuentaCliente());
					if (((Facturascabecera) linea).getFecha() != null)
						cadenaFecha = formatFecha
								.format(((Facturascabecera) linea).getFecha());
					else
						cadenaFecha = "";
					RowCliente.add(cadenaFecha);
					RowCliente.add(txtAsiento.getText());
					RowCliente.add(concepto);
					RowCliente.add(1);
					float totalFactura = ((Facturascabecera) linea)
							.getImporteFactura();
					String stotalFactura = formatDecimal.format(totalFactura);
					RowCliente.add(stotalFactura);
					RowCliente.add(1);
					tableData.add(RowCliente);

					totalDebe = totalDebe + totalFactura;

					String CuentaIGIC = "";
					if (((Facturascabecera) linea).getTipoImpuesto() == ((Number) 3)
							.floatValue()) {
						CuentaIGIC = txtCuentaIGIC3.getText();
					}
					if (((Facturascabecera) linea).getTipoImpuesto() == ((Number) 7)
							.floatValue()) {
						CuentaIGIC = txtCuentaIGIC7.getText();
					}

					if (!CuentaIGIC.equals("")) {
						Vector<Object> RowIGIC = new Vector<Object>();
						RowIGIC.add(CuentaIGIC);
						RowIGIC.add(cadenaFecha);
						RowIGIC.add(txtAsiento.getText());
						RowIGIC.add(concepto);
						RowIGIC.add(2);
						float importeIGIC = ((Facturascabecera) linea)
								.getImporteImpuesto();
						String simporteIGIC = formatDecimal
								.format(-importeIGIC);
						RowIGIC.add(simporteIGIC);
						RowIGIC.add(1);
						tableData.add(RowIGIC);

						totalHaber = totalHaber + importeIGIC;
					}

					Vector<Object> RowPrestServ = new Vector<Object>();
					RowPrestServ.add(txtCuentaPrestacionServicios.getText());
					RowPrestServ.add(cadenaFecha);
					RowPrestServ.add(txtAsiento.getText());
					RowPrestServ.add(concepto);
					RowPrestServ.add(2);
					float importe = ((Facturascabecera) linea)
							.getImporteFactura()
							- ((Facturascabecera) linea).getImporteImpuesto();
					String simporte = formatDecimal.format(-importe);
					RowPrestServ.add(simporte);
					RowPrestServ.add(1);
					tableData.add(RowPrestServ);

					totalHaber = totalHaber + importe;
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
			Message
					.ShowRuntimeError(parentFrame, "FrmConta.loadFacturas()",
							he);
		} catch (ParseException e) {
			Message.ShowParseError(parentFrame, "FrmConta.loadFacturas()", e);
		}
	}

	private boolean validateData() {
		try {

			if (txtFacturaDesde.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar una factura desde.");
				txtFacturaDesde.requestFocus();
				return (false);
			} else {
				try {
					txtFacturaDesde.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtFacturaDesde.requestFocus();
					return (false);
				}
			}

			if (txtFacturaHasta.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar una factura hasta.");
				txtFacturaHasta.requestFocus();
				return (false);
			} else {
				try {
					txtFacturaHasta.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtFacturaHasta.requestFocus();
					return (false);
				}
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmContaFacturas.validateData()", he);
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
		lblCuentaIGIC3 = new javax.swing.JLabel();
		txtCuentaIGIC3 = new javax.swing.JTextField();
		lblCuentaPrestacionServicios = new javax.swing.JLabel();
		txtCuentaPrestacionServicios = new javax.swing.JTextField();
		lblAsiento = new javax.swing.JLabel();
		txtAsiento = new javax.swing.JTextField();
		lblFacturaDesde = new javax.swing.JLabel();
		txtFacturaDesde = new javax.swing.JFormattedTextField();
		lblFacturaHasta = new javax.swing.JLabel();
		txtFacturaHasta = new javax.swing.JFormattedTextField();
		lblCuentaIGIC7 = new javax.swing.JLabel();
		txtCuentaIGIC7 = new javax.swing.JTextField();
		jSeparator1 = new javax.swing.JSeparator();

		setTitle("Contabilizaci\u00f3n de facturas");

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

		lblCuentaIGIC3.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaIGIC3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaIGIC3.setText("Cuenta IGIC 3%");
		lblCuentaIGIC3
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaIGIC3.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaIGIC3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaIGIC3.setText("47700003");
		txtCuentaIGIC3.setAutoscrolls(false);
		txtCuentaIGIC3.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaIGIC3.setName("igic3");
		txtCuentaIGIC3.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaIGIC3KeyTyped(evt);
			}
		});

		lblCuentaPrestacionServicios.setFont(new java.awt.Font("Segoe UI", 1,
				14));
		lblCuentaPrestacionServicios
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaPrestacionServicios.setText("Cuenta prestaci\u00f3n serv.");
		lblCuentaPrestacionServicios
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaPrestacionServicios.setFont(new java.awt.Font("Segoe UI", 0,
				14));
		txtCuentaPrestacionServicios
				.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaPrestacionServicios.setText("70500000");
		txtCuentaPrestacionServicios.setAutoscrolls(false);
		txtCuentaPrestacionServicios.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaPrestacionServicios.setName("telefono2");
		txtCuentaPrestacionServicios
				.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyTyped(java.awt.event.KeyEvent evt) {
						txtCuentaPrestacionServiciosKeyTyped(evt);
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

		lblFacturaDesde.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFacturaDesde.setForeground(new java.awt.Color(255, 0, 0));
		lblFacturaDesde
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFacturaDesde.setText("Factura desde");
		lblFacturaDesde
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtFacturaDesde.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtFacturaDesde
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtFacturaDesde.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtFacturaDesde.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblFacturaHasta.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFacturaHasta.setForeground(new java.awt.Color(255, 0, 0));
		lblFacturaHasta
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFacturaHasta.setText("Factura hasta");
		lblFacturaHasta
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtFacturaHasta.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtFacturaHasta
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtFacturaHasta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtFacturaHasta.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblCuentaIGIC7.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaIGIC7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaIGIC7.setText("Cuenta IGIC 7%");
		lblCuentaIGIC7
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaIGIC7.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaIGIC7.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaIGIC7.setText("47700007");
		txtCuentaIGIC7.setAutoscrolls(false);
		txtCuentaIGIC7.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaIGIC7.setName("igic7");
		txtCuentaIGIC7.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaIGIC7KeyTyped(evt);
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
																				jSeparator1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				750,
																				Short.MAX_VALUE)
																		.addGap(
																				690,
																				690,
																				690))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																pnlDataLayout
																		.createSequentialGroup()
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
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGap(
																												14,
																												14,
																												14)
																										.addGroup(
																												pnlDataLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																														.addGroup(
																																pnlDataLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblCuentaPrestacionServicios)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				txtCuentaPrestacionServicios,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				90,
																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																pnlDataLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblCuentaIGIC7)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				txtCuentaIGIC7,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				90,
																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																pnlDataLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblCuentaIGIC3)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				txtCuentaIGIC3,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				90,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)))
																										.addGap(
																												20,
																												20,
																												20)
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
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												283,
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
																		.addGap(
																				687,
																				687,
																				687))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblFacturaDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				170,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtFacturaDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				72,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				11,
																				11,
																				11)
																		.addComponent(
																				lblFacturaHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				97,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtFacturaHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				72,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				26,
																				26,
																				26)
																		.addComponent(
																				cmdSearch,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				104,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addContainerGap()))));
		pnlDataLayout
				.setVerticalGroup(pnlDataLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlDataLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lblFacturaDesde)
														.addComponent(
																txtFacturaDesde,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblFacturaHasta)
														.addComponent(
																txtFacturaHasta,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(cmdSearch))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jSeparator1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												6,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txtCuentaIGIC3,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCuentaIGIC3))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lblCuentaIGIC7)
														.addComponent(
																txtCuentaIGIC7,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(6, 6, 6)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txtAsiento,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																pnlDataLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(
																				lblAsiento)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								lblCuentaPrestacionServicios)
																						.addComponent(
																								txtCuentaPrestacionServicios,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGap(10, 10, 10)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												225, Short.MAX_VALUE)
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
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 780,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 523,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void txtAsientoKeyTyped(java.awt.event.KeyEvent evt) {

	}

	private void txtCuentaIGIC3KeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaIGIC3, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaIGIC2KeyTyped()", he);
		}
	}

	private void txtCuentaIGIC7KeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaIGIC7, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaIGIC5KeyTyped()", he);
		}
	}

	private void txtCuentaPrestacionServiciosKeyTyped(
			java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaPrestacionServicios, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaPrestacionServiciosKeyTyped()", he);
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
				loadFacturas();
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
	private javax.swing.JLabel lblCuentaIGIC3;
	private javax.swing.JLabel lblCuentaIGIC7;
	private javax.swing.JLabel lblCuentaPrestacionServicios;
	private javax.swing.JLabel lblDebe;
	private javax.swing.JLabel lblDescuadre;
	private javax.swing.JLabel lblFacturaDesde;
	private javax.swing.JLabel lblFacturaHasta;
	private javax.swing.JLabel lblHaber;
	public static javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblResult;
	private javax.swing.JTextField txtAsiento;
	private javax.swing.JTextField txtCuentaIGIC3;
	private javax.swing.JTextField txtCuentaIGIC7;
	private javax.swing.JTextField txtCuentaPrestacionServicios;
	private javax.swing.JFormattedTextField txtDescuadre;
	private javax.swing.JFormattedTextField txtFacturaDesde;
	private javax.swing.JFormattedTextField txtFacturaHasta;
	private static javax.swing.JFormattedTextField txtImporte;
	private static javax.swing.JFormattedTextField txtPrecio;
	private javax.swing.JFormattedTextField txtTotalDebe;
	private javax.swing.JFormattedTextField txtTotalHaber;
	// End of variables declaration//GEN-END:variables

}