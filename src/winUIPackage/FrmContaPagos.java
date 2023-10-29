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
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;
import entitiesPackage.Viewcontapagos;

/**
 *
 * @author  __USER__
 */
public class FrmContaPagos extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return FrmContaPagos.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmContaPagos.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmConta */
	public FrmContaPagos() {
		initComponents();
	}

	public FrmContaPagos(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmContaPagos.parentFrame = parent;
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

	private void loadFacturas_y_Pagos() {

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
				SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
				fechadesde = df.parse(txtFechaPagoDesde.getText());
			}
			if (!txtFechaPagoHasta.getText().equals("")) {
				SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
				fechahasta = df.parse(txtFechaPagoHasta.getText());
			}

			List<?> lineasList = entity.ContabilizaPagos(this, fechadesde,
					fechahasta);

			if (lineasList.size() > 0) {
				String concepto = "";
				String cadenaFecha = "";
				SimpleDateFormat formatFecha = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				NumberFormat formatDecimal = new DecimalFormat("#,##0.00");

				for (Object linea : lineasList) {
					concepto = "";
					if (((Viewcontapagos) linea).getId().getConcepto().equals(
							""))
						concepto = "FRA Nº "
								+ ((Viewcontapagos) linea).getId()
										.getIdFactura()
								+ " "
								+ ((Viewcontapagos) linea).getId().getNombre()
										.toUpperCase();
					else
						concepto = ((Viewcontapagos) linea).getId()
								.getConcepto().toUpperCase();
					Vector<Object> RowProveedor = new Vector<Object>();
					RowProveedor.add(((Viewcontapagos) linea).getId()
							.getCuentaContableProveedor());
					if (((Viewcontapagos) linea).getId().getFechaPago() != null)
						cadenaFecha = formatFecha
								.format(((Viewcontapagos) linea).getId()
										.getFechaPago());
					else
						cadenaFecha = "";
					RowProveedor.add(cadenaFecha);
					RowProveedor.add(txtAsiento.getText());
					RowProveedor.add(concepto);
					RowProveedor.add(2);
					Double importePago = ((Viewcontapagos) linea).getId()
							.getImportePago();
					String simportePago = formatDecimal.format(-importePago);
					RowProveedor.add(simportePago);
					RowProveedor.add(1);
					tableData.add(RowProveedor);

					totalHaber = totalHaber + importePago;

					if (((Viewcontapagos) linea).getId().getTipoIrpf() != ((Number) 0)
							.floatValue()) {
						String CuentaIRPF = txtCuentaIRPF.getText();
						Vector<Object> RowIRPF = new Vector<Object>();
						RowIRPF.add(CuentaIRPF);
						RowIRPF.add(cadenaFecha);
						RowIRPF.add(txtAsiento.getText());
						RowIRPF.add(concepto);
						RowIRPF.add(2);
						Double importeIRPF = ((Viewcontapagos) linea).getId()
								.getImporteIrpf();
						String simporteIRPF = formatDecimal
								.format(-importeIRPF);
						RowIRPF.add(simporteIRPF);
						RowIRPF.add(1);
						tableData.add(RowIRPF);

						totalHaber = totalHaber + importeIRPF;
					}

					String CuentaIGIC = "";
					if (((Viewcontapagos) linea).getId().getTipoImpuesto() == ((Number) 2)
							.floatValue()) {
						CuentaIGIC = txtCuentaIGIC2.getText();
					}
					if (((Viewcontapagos) linea).getId().getTipoImpuesto() == ((Number) 5)
							.floatValue()) {
						CuentaIGIC = txtCuentaIGIC5.getText();
					}

					if (!CuentaIGIC.equals("")) {
						Vector<Object> RowIGIC = new Vector<Object>();
						RowIGIC.add(CuentaIGIC);
						RowIGIC.add(cadenaFecha);
						RowIGIC.add(txtAsiento.getText());
						RowIGIC.add(concepto);
						RowIGIC.add(1);
						Double importeIGIC = ((Viewcontapagos) linea).getId()
								.getImporteImpuesto();
						String simporteIGIC = formatDecimal.format(importeIGIC);
						RowIGIC.add(simporteIGIC);
						RowIGIC.add(1);
						tableData.add(RowIGIC);

						totalDebe = totalDebe + importeIGIC;
					}

					Vector<Object> RowGasto = new Vector<Object>();
					RowGasto.add(((Viewcontapagos) linea).getId()
							.getCuentaContablePago());
					RowGasto.add(cadenaFecha);
					RowGasto.add(txtAsiento.getText());
					RowGasto.add(concepto);
					RowGasto.add(1);
					Double importeGasto = ((Viewcontapagos) linea).getId()
							.getBaseImponible();
					String simporteGasto = formatDecimal.format(importeGasto);
					RowGasto.add(simporteGasto);
					RowGasto.add(1);
					tableData.add(RowGasto);

					totalDebe = totalDebe + importeGasto;

					if (((Viewcontapagos) linea).getId().getComision() != ((Number) 0)
							.floatValue()) {
						String CuentaComison = txtCuentaComisiones.getText();
						Vector<Object> RowComision = new Vector<Object>();
						RowComision.add(CuentaComison);
						RowComision.add(cadenaFecha);
						RowComision.add(txtAsiento.getText());
						RowComision.add("COMIS. " + concepto);
						RowComision.add(1);
						Double importeComision = ((Viewcontapagos) linea)
								.getId().getComision();
						String simporteComision = formatDecimal
								.format(importeComision);
						RowComision.add(simporteComision);
						RowComision.add(1);
						tableData.add(RowComision);

						totalDebe = totalDebe + importeComision;
					}

					String CuentaProveedor = ((Viewcontapagos) linea).getId()
							.getCuentaContableProveedor();
					;
					Vector<Object> RowPago = new Vector<Object>();
					RowPago.add(CuentaProveedor);
					RowPago.add(cadenaFecha);
					RowPago.add(txtAsiento.getText());
					RowPago.add("PAGO " + concepto);
					RowPago.add(1);
					Double importePagoBanco = ((Viewcontapagos) linea).getId()
							.getImportePago();
					String simportePagoBanco = formatDecimal
							.format(importePago);
					RowPago.add(simportePagoBanco);
					RowPago.add(1);
					tableData.add(RowPago);

					totalDebe = totalDebe + importePagoBanco;

					String CuentaBanco = ((Viewcontapagos) linea).getId()
							.getCuentaContableBanco();
					Vector<Object> RowBanco = new Vector<Object>();
					RowBanco.add(CuentaBanco);
					RowBanco.add(cadenaFecha);
					RowBanco.add(txtAsiento.getText());
					if (((Viewcontapagos) linea).getId().getTalon() != ((Number) 0)
							.intValue())
						RowBanco.add("T/"
								+ ((Viewcontapagos) linea).getId().getTalon()
								+ " " + concepto);
					else
						RowBanco.add("PAGO " + concepto);
					RowBanco.add(2);
					Double importeBanco = ((Viewcontapagos) linea).getId()
							.getComision()
							+ ((Viewcontapagos) linea).getId().getImportePago();
					String simporteBanco = formatDecimal.format(-importeBanco);
					RowBanco.add(simporteBanco);
					RowBanco.add(1);
					tableData.add(RowBanco);

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
			Message
					.ShowRuntimeError(parentFrame, "FrmConta.loadFacturas()",
							he);
		} catch (ParseException e) {
			Message.ShowParseError(parentFrame, "FrmConta.loadFacturas()", e);
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
		lblCuentaIGIC2 = new javax.swing.JLabel();
		txtCuentaIGIC2 = new javax.swing.JTextField();
		lblCuentaComisiones = new javax.swing.JLabel();
		txtCuentaComisiones = new javax.swing.JTextField();
		lblAsiento = new javax.swing.JLabel();
		txtAsiento = new javax.swing.JTextField();
		lblCuentaIGIC5 = new javax.swing.JLabel();
		txtCuentaIGIC5 = new javax.swing.JTextField();
		lblCuentaIRPF = new javax.swing.JLabel();
		txtCuentaIRPF = new javax.swing.JTextField();
		lblFechaPagoDesde = new javax.swing.JLabel();
		txtFechaPagoDesde = new javax.swing.JFormattedTextField();
		lblFechaPagoHasta = new javax.swing.JLabel();
		txtFechaPagoHasta = new javax.swing.JFormattedTextField();
		jSeparator1 = new javax.swing.JSeparator();

		setTitle("Contabilizaci\u00f3n de facturas de compras y gastos con sus pagos");

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

		lblCuentaIGIC2.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaIGIC2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaIGIC2.setText("Cuenta IGIC 2%");
		lblCuentaIGIC2
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaIGIC2.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaIGIC2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaIGIC2.setText("47270002");
		txtCuentaIGIC2.setAutoscrolls(false);
		txtCuentaIGIC2.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaIGIC2.setName("telefono2");
		txtCuentaIGIC2.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaIGIC2KeyTyped(evt);
			}
		});

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

		lblCuentaIGIC5.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaIGIC5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaIGIC5.setText("Cuenta IGIC 5%");
		lblCuentaIGIC5
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaIGIC5.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaIGIC5.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaIGIC5.setText("47270005");
		txtCuentaIGIC5.setAutoscrolls(false);
		txtCuentaIGIC5.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaIGIC5.setName("telefono2");
		txtCuentaIGIC5.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaIGIC5KeyTyped(evt);
			}
		});

		lblCuentaIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaIRPF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaIRPF.setText("Cuenta IRPF");
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
										.addGap(57, 57, 57)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblCuentaComisiones)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtCuentaComisiones,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblCuentaIGIC5)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtCuentaIGIC5,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblCuentaIGIC2)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtCuentaIGIC2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
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
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				30,
																				30,
																				30)
																		.addComponent(
																				lblCuentaIRPF)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtCuentaIRPF,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(297, Short.MAX_VALUE))
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
																jSeparator1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																753,
																Short.MAX_VALUE)
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
										.addGap(18, 18, 18)
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
										.addGap(34, 34, 34)
										.addGroup(
												pnlDataLayout
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
																								txtCuentaIGIC2,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblCuentaIGIC2))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lblCuentaIGIC5)
																						.addComponent(
																								txtCuentaIGIC5,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addComponent(
																txtCuentaIRPF,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCuentaIRPF))
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
																								lblCuentaComisiones)
																						.addComponent(
																								txtCuentaComisiones,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												191, Short.MAX_VALUE)
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
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 530,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void txtCuentaIRPFKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaIRPF, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaIRPFKeyTyped()", he);
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

	private void txtCuentaIGIC2KeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaIGIC2, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaIGIC2KeyTyped()", he);
		}
	}

	private void txtCuentaIGIC5KeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaIGIC5, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaIGIC5KeyTyped()", he);
		}
	}

	private void txtCuentaComisionesKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaComisiones, evt, 10);
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
				loadFacturas_y_Pagos();
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
	private javax.swing.JLabel lblCuentaIGIC2;
	private javax.swing.JLabel lblCuentaIGIC5;
	private javax.swing.JLabel lblCuentaIRPF;
	private javax.swing.JLabel lblDebe;
	private javax.swing.JLabel lblDescuadre;
	private javax.swing.JLabel lblFechaPagoDesde;
	private javax.swing.JLabel lblFechaPagoHasta;
	private javax.swing.JLabel lblHaber;
	public static javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblResult;
	private javax.swing.JTextField txtAsiento;
	private javax.swing.JTextField txtCuentaComisiones;
	private javax.swing.JTextField txtCuentaIGIC2;
	private javax.swing.JTextField txtCuentaIGIC5;
	private javax.swing.JTextField txtCuentaIRPF;
	private javax.swing.JFormattedTextField txtDescuadre;
	private javax.swing.JFormattedTextField txtFechaPagoDesde;
	private javax.swing.JFormattedTextField txtFechaPagoHasta;
	private static javax.swing.JFormattedTextField txtImporte;
	private static javax.swing.JFormattedTextField txtPrecio;
	private javax.swing.JFormattedTextField txtTotalDebe;
	private javax.swing.JFormattedTextField txtTotalHaber;
	// End of variables declaration//GEN-END:variables

}