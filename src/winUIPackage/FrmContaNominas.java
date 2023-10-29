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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

import sessionPackage.MySession;
import entitiesPackage.Empleados;
import entitiesPackage.EmpleadosId;
import entitiesPackage.Empleadosnominas;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;
import entitiesPackage.Viewcontabonificaciones;
import entitiesPackage.Viewcontaindemnizaciones;
import entitiesPackage.Viewcontanominas;

/**
 *
 * @author  __USER__
 */
public class FrmContaNominas extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return FrmContaNominas.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmContaNominas.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmConta */
	public FrmContaNominas() {
		initComponents();
	}

	public FrmContaNominas(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmContaNominas.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmContaNominas()", he);
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
					"FrmContaNominas.configureKeys", he);
		}
	}

	private Double CalculaTotalDevengado() {
		try {
			Double totalDevengado = ((Number) 0).doubleValue();
			List<?> lineasList = entity.ContabilizaNominas(this,
					((Number) txtMes.getValue()).intValue());
			if (lineasList.size() > 0) {
				for (Object linea : lineasList) {
					totalDevengado = totalDevengado
							+ ((Viewcontanominas) linea).getId()
									.getTotalDevengado();
				}

			}
			return totalDevengado;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmContaNominas.CalculaTotalDevengado", he);
		}
		return ((Number) 0).doubleValue();
	}

	private Double CalculaTotalIRPF() {
		try {
			Double totalIRPF = ((Number) 0).doubleValue();
			List<?> lineasList = entity.ContabilizaNominas(this,
					((Number) txtMes.getValue()).intValue());
			if (lineasList.size() > 0) {
				for (Object linea : lineasList) {
					totalIRPF = totalIRPF
							+ ((Viewcontanominas) linea).getId()
									.getImporteIrpf();
				}

			}
			return totalIRPF;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmContaNominas.CalculaTotalIrpf", he);
		}
		return ((Number) 0).doubleValue();
	}

	private Double CalculaTotalSegSoc() {
		try {
			Double totalSegSoc = ((Number) 0).doubleValue();
			List<?> lineasList = entity.ContabilizaNominas(this,
					((Number) txtMes.getValue()).intValue());
			if (lineasList.size() > 0) {
				for (Object linea : lineasList) {
					totalSegSoc = totalSegSoc
							+ ((Viewcontanominas) linea).getId()
									.getImporteSegSoc();
				}

			}
			return totalSegSoc;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmContaNominas.CalculaTotalSegSoc", he);
		}
		return ((Number) 0).doubleValue();
	}

	private void loadNominas() {

		try {
			Double totalHaber = ((Number) 0).doubleValue();
			Double totalDebe = ((Number) 0).doubleValue();
			Double totalLiquido = ((Number) 0).doubleValue();
			Double totalSegSoc = ((Number) 0).doubleValue();
			Double totalIRPF = ((Number) 0).doubleValue();
			Double importeBanco = ((Number) 0).doubleValue();
			Float importeLiquidacion = ((Number) 0).floatValue();
			Float importeLiquidacionIrpf = ((Number) 0).floatValue();
			Float importeBonificacion = ((Number) 0).floatValue();

			String concepto = "";
			NumberFormat formatdecimal = new DecimalFormat("#,##0.00");

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

			List<?> lineasList = entity.ContabilizaNominas(this,
					((Number) txtMes.getValue()).intValue());

			if (lineasList.size() > 0) {

				Vector<Object> RowSalario = new Vector<Object>();
				RowSalario.add(txtCuentaSalarios.getText());
				RowSalario.add(txtFechaAsiento.getText());
				RowSalario.add(txtAsiento.getText());
				concepto = "SUELDOS Y SALARIOS";
				RowSalario.add(concepto);
				RowSalario.add(1);
				totalLiquido = CalculaTotalDevengado();
				String stotalLiquido = formatdecimal.format(totalLiquido);
				RowSalario.add(stotalLiquido);
				RowSalario.add(1);
				tableData.add(RowSalario);
				totalDebe = totalDebe + totalLiquido;

				Vector<Object> RowIRPF = new Vector<Object>();
				RowIRPF.add(txtCuentaIRPF.getText());
				RowIRPF.add(txtFechaAsiento.getText());
				RowIRPF.add(txtAsiento.getText());
				concepto = "HACIENDA PUB. RETENCIONES";
				RowIRPF.add(concepto);
				RowIRPF.add(2);
				totalIRPF = CalculaTotalIRPF();
				String stotalIRPF = formatdecimal.format(-totalIRPF);
				RowIRPF.add(stotalIRPF);
				RowIRPF.add(1);
				tableData.add(RowIRPF);
				totalHaber = totalHaber + totalIRPF;

				Vector<Object> RowSegSoc = new Vector<Object>();
				RowSegSoc.add(txtCuentaSegSoc.getText());
				RowSegSoc.add(txtFechaAsiento.getText());
				RowSegSoc.add(txtAsiento.getText());
				concepto = "SEGURIDAD SOCIAL";
				RowSegSoc.add(concepto);
				RowSegSoc.add(2);
				totalSegSoc = CalculaTotalSegSoc();
				String stotalSegSoc = formatdecimal.format(-totalSegSoc);
				RowSegSoc.add(stotalSegSoc);
				RowSegSoc.add(1);
				tableData.add(RowSegSoc);
				totalHaber = totalHaber + totalSegSoc;
				
				for (Object linea : lineasList) {

					Vector<Object> RowBanco = new Vector<Object>();
					RowBanco.add(((Viewcontanominas) linea).getId()
							.getCuentaContable());
					RowBanco.add(txtFechaAsiento.getText());
					RowBanco.add(txtAsiento.getText());
					concepto = ((Viewcontanominas) linea).getId()
							.getNombreBanco()
							+ " "
							+ ((Viewcontanominas) linea).getId()
									.getCuentaBancaria();
					RowBanco.add(concepto);
					RowBanco.add(2);
					importeBanco = ((Viewcontanominas) linea).getId()
							.getTotalLiquido();
					String simporteBanco = formatdecimal.format(-importeBanco);
					RowBanco.add(simporteBanco);
					RowBanco.add(1);
					tableData.add(RowBanco);
					totalHaber = totalHaber + importeBanco;

				}
			}
			
			lineasList = entity.EmpleadoGetAutonomosMes(this, ((Number) txtMes
					.getValue()).intValue());

			if (lineasList.size() > 0) {
				for (Object linea : lineasList) {

					Vector<Object> RowIndem = new Vector<Object>();
					if (!(((Empleadosnominas) linea).getCuentaBancariaPago().equals("")))
						RowIndem.add(entity.EmpresaGetCuentaContableOfCuentaBanco(this, ((Empleadosnominas) linea).getCuentaBancariaPago()));
					else
						RowIndem.add("");	
					RowIndem.add(txtFechaAsiento.getText());
					RowIndem.add(txtAsiento.getText());
					EmpleadosId empid = new EmpleadosId();
					empid.setEjercicios(session.getEjercicio());
					empid.setEmpresas(session.getEmpresa());
					empid.setIdEmpleado(((Empleadosnominas) linea).getId()
							.getIdEmpleado());
					Empleados emp = entity.EmpleadoFindById(this, empid);
					if (emp != null)
						concepto = "AUTONOMO " + emp.getNombre().toUpperCase();
					else
						concepto = "AUTONOMO";
					RowIndem.add(concepto);
					RowIndem.add(2);
					Float importeAutonomo = ((Empleadosnominas) linea)
							.getImporteSegAutonomo();
					String simporteAutonomo = formatdecimal
							.format(-importeAutonomo);
					RowIndem.add(simporteAutonomo);
					RowIndem.add(1);
					tableData.add(RowIndem);
					totalHaber = totalHaber + importeAutonomo;
				}
			}

			lineasList = entity.EmpleadoGetEmbargosMes(this, ((Number) txtMes
					.getValue()).intValue());

			if (lineasList.size() > 0) {
				for (Object linea : lineasList) {

					Vector<Object> RowIndem = new Vector<Object>();
					RowIndem.add(txtCuentaEmbargos.getText());
					RowIndem.add(txtFechaAsiento.getText());
					RowIndem.add(txtAsiento.getText());
					EmpleadosId empid = new EmpleadosId();
					empid.setEjercicios(session.getEjercicio());
					empid.setEmpresas(session.getEmpresa());
					empid.setIdEmpleado(((Empleadosnominas) linea).getId()
							.getIdEmpleado());
					Empleados emp = entity.EmpleadoFindById(this, empid);
					if (emp != null)
						concepto = "EMBARGO " + emp.getNombre().toUpperCase();
					else
						concepto = "EMBARGO";
					RowIndem.add(concepto);
					RowIndem.add(2);
					Float importeEmbargo = ((Empleadosnominas) linea)
							.getImporteEmbargo();
					String simporteEmbargo = formatdecimal
							.format(-importeEmbargo);
					RowIndem.add(simporteEmbargo);
					RowIndem.add(1);
					tableData.add(RowIndem);
					totalHaber = totalHaber + importeEmbargo;
				}
			}

			lineasList = entity.ContabilizaIndemnizaciones(this,
					((Number) txtMes.getValue()).intValue());

			if (lineasList.size() > 0) {
				for (Object linea : lineasList) {

					Vector<Object> RowIndem = new Vector<Object>();
					RowIndem.add(txtCuentaIndemnizaciones.getText());
					RowIndem.add(txtFechaAsiento.getText());
					RowIndem.add(txtAsiento.getText());
					concepto = "INDEMNIZACION "
							+ ((Viewcontaindemnizaciones) linea).getId()
									.getNombre().toUpperCase();
					RowIndem.add(concepto);
					RowIndem.add(1);
					importeLiquidacion = ((Viewcontaindemnizaciones) linea)
							.getId().getImporteLiquidacion();
					String simporteLiquidacion = formatdecimal
							.format(importeLiquidacion);
					RowIndem.add(simporteLiquidacion);
					RowIndem.add(1);
					tableData.add(RowIndem);
					totalDebe = totalDebe + importeLiquidacion;

					Vector<Object> RowIndemIrpf = new Vector<Object>();
					RowIndemIrpf.add(txtCuentaIRPF.getText());
					RowIndemIrpf.add(txtFechaAsiento.getText());
					RowIndemIrpf.add(txtAsiento.getText());
					concepto = "INDEMNIZACION "
							+ ((Viewcontaindemnizaciones) linea).getId()
									.getNombre().toUpperCase();
					RowIndemIrpf.add(concepto);
					RowIndemIrpf.add(2);
					importeLiquidacionIrpf = ((Viewcontaindemnizaciones) linea)
							.getId().getImporteIrpf();
					String simporteLiquidacionIrpf = formatdecimal
							.format(-importeLiquidacionIrpf);
					RowIndemIrpf.add(simporteLiquidacionIrpf);
					RowIndemIrpf.add(1);
					tableData.add(RowIndemIrpf);
					totalHaber = totalHaber + importeLiquidacionIrpf;

					Vector<Object> RowBanco = new Vector<Object>();
					RowBanco.add(((Viewcontaindemnizaciones) linea).getId()
							.getCuentaContable());
					RowBanco.add(txtFechaAsiento.getText());
					RowBanco.add(txtAsiento.getText());
					concepto = "INDEMNIZACION "
							+ ((Viewcontaindemnizaciones) linea).getId()
									.getNombre().toUpperCase();
					RowBanco.add(concepto);
					RowBanco.add(2);
					importeBanco = ((Number) (importeLiquidacion - importeLiquidacionIrpf))
							.doubleValue();
					String simporteBanco = formatdecimal.format(-importeBanco);
					RowBanco.add(simporteBanco);
					RowBanco.add(1);
					tableData.add(RowBanco);
					totalHaber = totalHaber + importeBanco;

				}
			}

			lineasList = entity.ContabilizaBonificaciones(this,
					((Number) txtMes.getValue()).intValue());

			if (lineasList.size() > 0) {
				for (Object linea : lineasList) {

					Vector<Object> RowIndem = new Vector<Object>();
					RowIndem.add(txtCuentaGastos.getText());
					RowIndem.add(txtFechaAsiento.getText());
					RowIndem.add(txtAsiento.getText());
					concepto = "EXTRA "
							+ ((Viewcontabonificaciones) linea).getId()
									.getNombre().toUpperCase();
					RowIndem.add(concepto);
					RowIndem.add(1);
					importeBonificacion = ((Viewcontabonificaciones) linea)
							.getId().getImporteBonificacion();
					String simporteBonificacion = formatdecimal
							.format(importeBonificacion);
					RowIndem.add(simporteBonificacion);
					RowIndem.add(1);
					tableData.add(RowIndem);
					totalDebe = totalDebe + importeBonificacion;

					Vector<Object> RowBanco = new Vector<Object>();
					RowBanco.add(((Viewcontabonificaciones) linea).getId()
							.getCuentaContable());
					RowBanco.add(txtFechaAsiento.getText());
					RowBanco.add(txtAsiento.getText());
					concepto = "EXTRA "
							+ ((Viewcontabonificaciones) linea).getId()
									.getNombre().toUpperCase();
					RowBanco.add(concepto);
					RowBanco.add(2);
					importeBanco = ((Number) (importeBonificacion))
							.doubleValue();
					String simporteBanco = formatdecimal.format(-importeBanco);
					RowBanco.add(simporteBanco);
					RowBanco.add(1);
					tableData.add(RowBanco);
					totalHaber = totalHaber + importeBonificacion;

				}
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

		} catch (RuntimeException he) {
			he.printStackTrace();
			Message.ShowRuntimeError(parentFrame, "FrmConta.loadNominas()", he);
		} catch (ParseException e) {
			Message.ShowParseError(parentFrame, "FrmConta.loadNominas()", e);
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
							"El tipo de datos indicado no es válido.");
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
		lblMes = new javax.swing.JLabel();
		txtMes = new javax.swing.JFormattedTextField();
		cmdSearch = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblResult = new javax.swing.JTable();
		cmdCopy = new javax.swing.JButton();
		lblCuentaIGIC = new javax.swing.JLabel();
		txtCuentaSalarios = new javax.swing.JTextField();
		lblCuentaIRPF = new javax.swing.JLabel();
		txtCuentaIRPF = new javax.swing.JTextField();
		lblCuentaCompras = new javax.swing.JLabel();
		txtCuentaSegSoc = new javax.swing.JTextField();
		lblFechaAsiento = new javax.swing.JLabel();
		txtFechaAsiento = new javax.swing.JFormattedTextField();
		lblAsiento = new javax.swing.JLabel();
		txtAsiento = new javax.swing.JTextField();
		lblCuentaGastos = new javax.swing.JLabel();
		txtCuentaGastos = new javax.swing.JTextField();
		lblCuentaIndemnizaciones = new javax.swing.JLabel();
		txtCuentaIndemnizaciones = new javax.swing.JTextField();
		lblCuentaEmbargos = new javax.swing.JLabel();
		txtCuentaEmbargos = new javax.swing.JTextField();

		setTitle("Contabilizaci\u00f3n de nominas");

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

		lblMes.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblMes.setForeground(new java.awt.Color(255, 0, 0));
		lblMes.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblMes.setText("Mes");

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
		lblCuentaIGIC.setText("Cuenta sueldos y salarios");
		lblCuentaIGIC
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaSalarios.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaSalarios.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaSalarios.setText("64000000");
		txtCuentaSalarios.setAutoscrolls(false);
		txtCuentaSalarios.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaSalarios.setName("telefono2");
		txtCuentaSalarios.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaSalariosKeyTyped(evt);
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
		lblCuentaCompras.setText("Cuenta Seguridad Social");
		lblCuentaCompras
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaSegSoc.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaSegSoc.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaSegSoc.setText("47600000");
		txtCuentaSegSoc.setAutoscrolls(false);
		txtCuentaSegSoc.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaSegSoc.setName("telefono2");
		txtCuentaSegSoc.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaSegSocKeyTyped(evt);
			}
		});

		lblFechaAsiento.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFechaAsiento
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFechaAsiento.setText("Fecha asiento");

		txtFechaAsiento.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtFechaAsiento
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtFechaAsiento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtFechaAsiento.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtFechaAsiento.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtFechaAsientoFocusLost(evt);
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

		lblCuentaGastos.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaGastos
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaGastos.setText("Cuenta gastos varios");
		lblCuentaGastos
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaGastos.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaGastos.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaGastos.setText("62900000");
		txtCuentaGastos.setAutoscrolls(false);
		txtCuentaGastos.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaGastos.setName("telefono2");
		txtCuentaGastos.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaGastosKeyTyped(evt);
			}
		});

		lblCuentaIndemnizaciones.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaIndemnizaciones
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaIndemnizaciones.setText("Cta. indemnizaciones");
		lblCuentaIndemnizaciones
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaIndemnizaciones.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaIndemnizaciones
				.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaIndemnizaciones.setText("64100000");
		txtCuentaIndemnizaciones.setAutoscrolls(false);
		txtCuentaIndemnizaciones.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaIndemnizaciones.setName("telefono2");
		txtCuentaIndemnizaciones
				.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyTyped(java.awt.event.KeyEvent evt) {
						txtCuentaIndemnizacionesKeyTyped(evt);
					}
				});

		lblCuentaEmbargos.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCuentaEmbargos
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCuentaEmbargos.setText("Cuenta embargos");
		lblCuentaEmbargos
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtCuentaEmbargos.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtCuentaEmbargos.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtCuentaEmbargos.setText("46500000");
		txtCuentaEmbargos.setAutoscrolls(false);
		txtCuentaEmbargos.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtCuentaEmbargos.setName("telefono2");
		txtCuentaEmbargos.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCuentaEmbargosKeyTyped(evt);
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
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGap(
																												13,
																												13,
																												13)
																										.addComponent(
																												lblMes,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												152,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtMes))
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
																												txtCuentaSalarios,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblCuentaCompras,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtCuentaSegSoc,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblFechaAsiento,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtFechaAsiento,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								cmdSearch,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								104,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGap(
																												13,
																												13,
																												13)
																										.addComponent(
																												lblAsiento,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												125,
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
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING,
																												false)
																										.addGroup(
																												pnlDataLayout
																														.createSequentialGroup()
																														.addComponent(
																																lblCuentaEmbargos,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																txtCuentaEmbargos,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																90,
																																javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addGroup(
																												javax.swing.GroupLayout.Alignment.LEADING,
																												pnlDataLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																														.addGroup(
																																pnlDataLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblCuentaIndemnizaciones)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				txtCuentaIndemnizaciones,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				90,
																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																pnlDataLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblCuentaGastos)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				txtCuentaGastos,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				90,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)))))
																		.addGap(
																				276,
																				276,
																				276))
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
										.addGap(13, 13, 13)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				txtMes,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblMes))
														.addComponent(cmdSearch))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txtCuentaSalarios,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCuentaIGIC)
														.addComponent(
																txtCuentaGastos,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCuentaGastos))
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
																lblCuentaIRPF)
														.addComponent(
																txtCuentaIndemnizaciones,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCuentaIndemnizaciones))
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
																				txtCuentaEmbargos,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblCuentaEmbargos))
														.addComponent(
																txtCuentaSegSoc,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCuentaCompras))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txtFechaAsiento,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				5,
																				5,
																				5)
																		.addComponent(
																				lblFechaAsiento))
														.addGroup(
																pnlDataLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				txtAsiento,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblAsiento)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												202, Short.MAX_VALUE)
										.addGap(13, 13, 13)
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
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 797,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 516,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void txtCuentaEmbargosKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaEmbargos, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaSalariosKeyTyped()", he);
		}
	}

	private void txtFechaAsientoFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtFechaAsiento);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtFechaAsientoFocusLost()", he);
		}
	}

	private void txtCuentaSalariosKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaSalarios, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaSalariosKeyTyped()", he);
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

	private void txtCuentaSegSocKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaSegSoc, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaIGICKeyTyped()", he);
		}
	}

	private void txtCuentaIndemnizacionesKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaIndemnizaciones, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaIndemnizacionesKeyTyped()", he);
		}
	}

	private void txtCuentaGastosKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaGastos, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmConta.txtCuentaGastosKeyTyped()", he);
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
				loadNominas();
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
	private javax.swing.JLabel lblAsiento;
	private javax.swing.JLabel lblCuentaCompras;
	private javax.swing.JLabel lblCuentaEmbargos;
	private javax.swing.JLabel lblCuentaGastos;
	private javax.swing.JLabel lblCuentaIGIC;
	private javax.swing.JLabel lblCuentaIRPF;
	private javax.swing.JLabel lblCuentaIndemnizaciones;
	private javax.swing.JLabel lblDebe;
	private javax.swing.JLabel lblDescuadre;
	private javax.swing.JLabel lblFechaAsiento;
	private javax.swing.JLabel lblHaber;
	private javax.swing.JLabel lblMes;
	public static javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblResult;
	private javax.swing.JTextField txtAsiento;
	private javax.swing.JTextField txtCuentaEmbargos;
	private javax.swing.JTextField txtCuentaGastos;
	private javax.swing.JTextField txtCuentaIRPF;
	private javax.swing.JTextField txtCuentaIndemnizaciones;
	private javax.swing.JTextField txtCuentaSalarios;
	private javax.swing.JTextField txtCuentaSegSoc;
	private javax.swing.JFormattedTextField txtDescuadre;
	private javax.swing.JFormattedTextField txtFechaAsiento;
	private static javax.swing.JFormattedTextField txtImporte;
	private javax.swing.JFormattedTextField txtMes;
	private static javax.swing.JFormattedTextField txtPrecio;
	private javax.swing.JFormattedTextField txtTotalDebe;
	private javax.swing.JFormattedTextField txtTotalHaber;
	// End of variables declaration//GEN-END:variables

}