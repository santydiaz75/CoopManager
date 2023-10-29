/*
 * prueba.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import reportsPackage.InformeVale;
import reportsPackage.InformeValeVentas;
import sessionPackage.MySession;

import entitiesPackage.Cosecheros;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Entradascabecera;
import entitiesPackage.EntradascabeceraId;
import entitiesPackage.Message;
import entitiesPackage.Receptores;
import entitiesPackage.Ventascabecera;
import entitiesPackage.VentascabeceraId;
import entitiesPackage.Viewentradascategoriascosecheroquery;
import entitiesPackage.Viewentradascategoriasquery;
import entitiesPackage.Viewentradasquery;
import entitiesPackage.Viewventascategoriasquery;
import entitiesPackage.Viewventascategoriasreceptorquery;
import entitiesPackage.Viewventasquery;

import winUIPackage.PreferencesUI;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmDataQuery extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static final String tmTodos = "[Todos]";
	private static final String tmMercadoLocal = "Mercado local";
	private static final String tmExportaciones = "Exportaciones";

	private static java.awt.Frame parentFrame;
	private static MySession session;
	private static Entity entity = new Entity();;
	
	private Object fDetail;

	private boolean changingCosechero;
	private boolean changingReceptor;

	public java.awt.Frame getparentFrame() {
		return FrmDataQuery.parentFrame;
	}

	public static void setparentFrame(java.awt.Frame parentFrame) {
		FrmDataQuery.parentFrame = parentFrame;
	}

	public static MySession getSession() {
		return FrmDataQuery.session;
	}

	public static void setSession(MySession session) {
		FrmDataQuery.session = session;
	}

	/** Creates new form FrmEntity */
	public FrmDataQuery() {
		initComponents();
	}

	public FrmDataQuery(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmDataQuery.parentFrame = parent;
			FrmDataQuery.setSession(session);
			entity.setSession(session);
			configureKeys();
			loadCosecheros();
			loadReceptores();
			loadTipoMercado();
			txtSemanaEntDesde.setText("");
			txtSemanaEntHasta.setText("");
			txtIdCosechero.setText("");
			cboCosecheros.setSelectedItem(null);
			txtSemanaVenDesde.setText("");
			txtSemanaVenHasta.setText("");
			txtIdReceptor.setText("");
			cboReceptores.setSelectedItem(null);
			txtSemanaEntDesde.requestFocusInWindow();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmDataQuery()", he);
		}
	}

	private void configureKeys() {
		try {
			Set<AWTKeyStroke> teclasTab = new HashSet<AWTKeyStroke>();
			teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
			teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
			teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0));
			pnlEntradas.setFocusTraversalKeys(
					KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, teclasTab);
			pnlVentas.setFocusTraversalKeys(
					KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, teclasTab);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmDataQuery.configureKeys",
					he);
		}
	}

	private void loadTipoMercado() {
		try {
			cboTiposMercado.removeAllItems();
			cboTiposMercado.addItem(tmTodos);
			cboTiposMercado.addItem(tmMercadoLocal);
			cboTiposMercado.addItem(tmExportaciones);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.loadTipoMercado()", he);
		}
	}

	private void loadCosecheros() {
		try {
			cboCosecheros.removeAllItems();

			List<?> cosecherosList = entity.CosecheroFindAll(this);

			for (Object o : cosecherosList) {
				Cosecheros cosechero = (Cosecheros) o;
				String nombre = cosechero.getNombre();
				String apellidos = cosechero.getApellidos();
				if (apellidos != null)
					nombre = nombre + " " + apellidos;
				cboCosecheros.addItem(nombre);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.loadCosecheros()", he);
		}
	}

	private void loadReceptores() {
		try {
			cboReceptores.removeAllItems();

			List<?> receptoresList = entity.ReceptorFindAll(this);

			for (Object o : receptoresList) {
				Receptores receptor = (Receptores) o;
				String nombre = receptor.getNombre();
				cboReceptores.addItem(nombre);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.loadReceptores()", he);
		}
	}

	private void changeCosechero() {
		try {
			if (!changingCosechero) {
				changingCosechero = true;
				if (txtIdCosechero.getText().equals(""))
					cboCosecheros.setSelectedItem(null);
				else {
					try {
						Integer value = Integer.parseInt(txtIdCosechero
								.getText());
						Cosecheros cosechero = entity
								.CosecheroFindByIdCosechero(this, value);
						if (cosechero != null) {
							String nombre = cosechero.getNombre();
							String apellidos = cosechero.getApellidos();
							if (apellidos != null)
								nombre = nombre + " " + apellidos;
							cboCosecheros.setSelectedItem(nombre);
						} else {
							cboCosecheros.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboCosecheros.setSelectedItem(null);
					}
				}
				changingCosechero = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.changeCosechero()", he);
		}
	}

	private void changeReceptor() {
		try {
			if (!changingReceptor) {
				changingReceptor = true;
				if (txtIdReceptor.getText().equals(""))
					cboReceptores.setSelectedItem(null);
				else {
					try {
						Integer value = Integer.parseInt(txtIdReceptor
								.getText());
						Receptores receptor = entity.ReceptorFindByIdReceptor(
								this, value);
						if (receptor != null) {
							String nombre = receptor.getNombre();
							cboReceptores.setSelectedItem(nombre);
						} else {
							cboReceptores.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboReceptores.setSelectedItem(null);
					}
				}
				changingReceptor = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.changeReceptor()", he);
		}
	}

	private void loadEntradas() {

		try {

			Float totalKilos = ((Number) 0).floatValue();
			Float numKilos = ((Number) 0).floatValue();
			Float totalPinas = ((Number) 0).floatValue();
			Float numPinas = ((Number) 0).floatValue();
			Float KilosvsPinas = ((Number) 0).floatValue();
			Integer Cosechero = 0;
			Integer SemanaDesde = 1;
			Integer SemanaHasta = 52;

			if (!txtSemanaEntDesde.getText().equals("")) {
				txtSemanaEntDesde.commitEdit();
				SemanaDesde = ((Number) txtSemanaEntDesde.getValue())
						.intValue();
			} else
				SemanaDesde = 1;
			if (!txtSemanaEntHasta.getText().equals("")) {
				txtSemanaEntHasta.commitEdit();
				SemanaHasta = ((Number) txtSemanaEntHasta.getValue())
						.intValue();
			} else
				SemanaHasta = 52;

			if (!txtIdCosechero.getText().equals("")) {
				txtIdCosechero.commitEdit();
				Cosechero = ((Number) txtIdCosechero.getValue()).intValue();
			} else
				Cosechero = 0;

			Vector<String> headerDataEntradas = new Vector<String>();
			headerDataEntradas.add("Vale");
			headerDataEntradas.add("Cosechero");
			headerDataEntradas.add("Semana");
			headerDataEntradas.add("Fecha");
			headerDataEntradas.add("Piñas");
			headerDataEntradas.add("Kilos");

			Vector<Vector<Object>> tableDataEntradas = new Vector<Vector<Object>>();

			Integer numRowsEntradas = tblEntradas.getRowCount();

			for (Integer k = 0; k < numRowsEntradas; k++)
				((DefaultTableModel) tblEntradas.getModel()).removeRow(0);

			List<?> EntradasList = entity.EntradasBySemanaCosechero(this,
					SemanaDesde, SemanaHasta, Cosechero);

			if (EntradasList.size() > 0) {
				String cadenaFecha = "";
				String snumPinas = "";
				String snumKilos = "";
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				NumberFormat formatter = new DecimalFormat("#,##0");

				for (Object linea : EntradasList) {
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(((Viewentradasquery) linea).getId()
							.getIdEntrada());
					String nombre = ((Viewentradasquery) linea).getId()
							.getNombre();
					String apellidos = ((Viewentradasquery) linea).getId()
							.getApellidos();
					if (apellidos != null)
						nombre = nombre + " " + apellidos;
					oneRow.add(nombre);
					oneRow.add(((Viewentradasquery) linea).getId().getSemana());
					if (((Viewentradasquery) linea).getId().getFecha() != null)
						cadenaFecha = formato
								.format(((Viewentradasquery) linea).getId()
										.getFecha());
					else
						cadenaFecha = "";
					oneRow.add(cadenaFecha);
					numPinas = ((Number) ((Viewentradasquery) linea).getId()
							.getNumPinas()).floatValue();
					snumPinas = formatter.format(numPinas);
					oneRow.add(snumPinas);
					numKilos = ((Number) ((Viewentradasquery) linea).getId()
							.getNumKilos()).floatValue();
					snumKilos = formatter.format(numKilos);
					oneRow.add(snumKilos);
					tableDataEntradas.add(oneRow);

					totalKilos = totalKilos + numKilos;
					totalPinas = totalPinas + numPinas;
				}

				tblEntradas.setModel(new MyViewTableModel(tableDataEntradas,
						headerDataEntradas));
				tblEntradas.getColumn("Vale").setPreferredWidth(
						EntityType.NumberWidth);
				tblEntradas.getColumn("Cosechero").setPreferredWidth(
						EntityType.TextWidth);
				tblEntradas.getColumn("Semana").setPreferredWidth(
						EntityType.NumberWidth);
				tblEntradas.getColumn("Fecha").setPreferredWidth(
						EntityType.DateWidth);
				tblEntradas.getColumn("Piñas").setPreferredWidth(
						EntityType.NumberWidth);
				tblEntradas.getColumn("Kilos").setPreferredWidth(
						EntityType.NumberWidth);
				tblEntradas.getColumn("Piñas").setPreferredWidth(
						EntityType.NumberWidth);
				tblEntradas.getColumn("Kilos").setPreferredWidth(
						EntityType.NumberWidth);
				DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
				rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
				tblEntradas.getColumn("Semana").setCellRenderer(rightRenderer);
				tblEntradas.getColumn("Piñas").setCellRenderer(rightRenderer);
				tblEntradas.getColumn("Kilos").setCellRenderer(rightRenderer);

				txtTotalKilosEntradas.setValue(totalKilos);
				txtTotalPinasEntradas.setValue(totalPinas);
				KilosvsPinas = totalKilos / totalPinas;
				txtKilosvsPinas.setValue(KilosvsPinas);
			}

			Vector<String> headerDataEntCategorias = new Vector<String>();
			headerDataEntCategorias.add("Categorías");
			headerDataEntCategorias.add("Kilos");
			headerDataEntCategorias.add("Porcentaje");

			Vector<Vector<Object>> tableDataEntCategorias = new Vector<Vector<Object>>();

			Integer numRowsCategorias = tblEntCategorias.getRowCount();

			for (Integer k = 0; k < numRowsCategorias; k++)
				((DefaultTableModel) tblEntCategorias.getModel()).removeRow(0);

			List<?> EntCategoriasList = entity
					.EntradasCategoriasBySemanaCosechero(this, SemanaDesde,
							SemanaHasta, Cosechero);

			if (EntCategoriasList.size() > 0) {
				String sporcentaje = "";
				String snumKilos = "";
				String nombreCategoria = "";
				NumberFormat formatter1 = new DecimalFormat("#,##0");
				NumberFormat formatter2 = new DecimalFormat("#,##0.000");
				numKilos = ((Number) 0).floatValue();
				Object linea = null;
				if (Cosechero == 0) {
					int index = 0;
					if (EntCategoriasList.size() > 0) {
						linea = EntCategoriasList.get(index);
						nombreCategoria = ((Viewentradascategoriasquery) linea)
								.getId().getNombreCategoria();
					}
					while (index < EntCategoriasList.size()) {
						if (!((Viewentradascategoriasquery) linea).getId()
								.getNombreCategoria().equals(nombreCategoria)) {
							Vector<Object> oneRow = new Vector<Object>();
							oneRow.add(nombreCategoria);
							snumKilos = formatter1.format(numKilos);
							oneRow.add(snumKilos);
							sporcentaje = formatter2.format(numKilos
									/ totalKilos * 100);
							oneRow.add(sporcentaje);
							tableDataEntCategorias.add(oneRow);

							numKilos = ((Number) ((Viewentradascategoriasquery) linea)
									.getId().getNumKilos()).floatValue();
							nombreCategoria = ((Viewentradascategoriasquery) linea)
									.getId().getNombreCategoria();
						} else {
							numKilos = numKilos
									+ ((Number) ((Viewentradascategoriasquery) linea)
											.getId().getNumKilos())
											.floatValue();
						}
						index++;
						if (index < EntCategoriasList.size())
							linea = EntCategoriasList.get(index);
						else {
							Vector<Object> oneRow = new Vector<Object>();
							oneRow.add(nombreCategoria);
							snumKilos = formatter1.format(numKilos);
							oneRow.add(snumKilos);
							sporcentaje = formatter2.format(numKilos
									/ totalKilos * 100);
							oneRow.add(sporcentaje);
							tableDataEntCategorias.add(oneRow);
						}
					}
				} else {
					int index = 0;
					if (EntCategoriasList.size() > 0) {
						linea = EntCategoriasList.get(index);
						nombreCategoria = ((Viewentradascategoriascosecheroquery) linea)
								.getId().getNombreCategoria();
					}
					while (index < EntCategoriasList.size()) {
						if (!((Viewentradascategoriascosecheroquery) linea)
								.getId().getNombreCategoria().equals(
										nombreCategoria)) {
							Vector<Object> oneRow = new Vector<Object>();
							oneRow.add(nombreCategoria);
							snumKilos = formatter1.format(numKilos);
							oneRow.add(snumKilos);
							sporcentaje = formatter2.format(numKilos
									/ totalKilos * 100);
							oneRow.add(sporcentaje);
							tableDataEntCategorias.add(oneRow);

							numKilos = ((Number) ((Viewentradascategoriascosecheroquery) linea)
									.getId().getNumKilos()).floatValue();
							nombreCategoria = ((Viewentradascategoriascosecheroquery) linea)
									.getId().getNombreCategoria();
						} else {
							numKilos = numKilos
									+ ((Number) ((Viewentradascategoriascosecheroquery) linea)
											.getId().getNumKilos())
											.floatValue();
						}
						index++;
						if (index < EntCategoriasList.size())
							linea = EntCategoriasList.get(index);
						else {
							Vector<Object> oneRow = new Vector<Object>();
							oneRow.add(nombreCategoria);
							snumKilos = formatter1.format(numKilos);
							oneRow.add(snumKilos);
							sporcentaje = formatter2.format(numKilos
									/ totalKilos * 100);
							oneRow.add(sporcentaje);
							tableDataEntCategorias.add(oneRow);
						}
					}
				}

				tblEntCategorias.setModel(new MyViewTableModel(
						tableDataEntCategorias, headerDataEntCategorias));
				tblEntCategorias.getColumn("Categorías").setPreferredWidth(
						EntityType.TextWidth);
				tblEntCategorias.getColumn("Kilos").setPreferredWidth(
						EntityType.NumberWidth);
				tblEntCategorias.getColumn("Porcentaje").setPreferredWidth(
						EntityType.NumberWidth);
				DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
				rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
				tblEntCategorias.getColumn("Kilos").setCellRenderer(
						rightRenderer);
				tblEntCategorias.getColumn("Porcentaje").setCellRenderer(
						rightRenderer);

			}

		} catch (RuntimeException he) {
			he.printStackTrace();
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.loadEntradas()", he);
		} catch (ParseException e) {
			Message.ShowParseError(parentFrame, "FrmDataQuery.loadEntradas()",
					e);
		}
	}

	private void loadVentas() {

		try {

			Float totalKilos = ((Number) 0).floatValue();
			Float numKilos = ((Number) 0).floatValue();
			Float totalCajas = ((Number) 0).floatValue();
			Float numCajas = ((Number) 0).floatValue();
			Float totalImporte = ((Number) 0).floatValue();
			Float importe = ((Number) 0).floatValue();
			Integer Receptor = 0;
			Integer SemanaDesde = 1;
			Integer SemanaHasta = 52;

			if (!txtSemanaVenDesde.getText().equals("")) {
				txtSemanaVenDesde.commitEdit();
				SemanaDesde = ((Number) txtSemanaVenDesde.getValue())
						.intValue();
			} else
				SemanaDesde = 1;
			if (!txtSemanaVenHasta.getText().equals("")) {
				txtSemanaVenHasta.commitEdit();
				SemanaHasta = ((Number) txtSemanaVenHasta.getValue())
						.intValue();
			} else
				SemanaHasta = 52;

			if (!txtIdReceptor.getText().equals("")) {
				txtIdReceptor.commitEdit();
				Receptor = ((Number) txtIdReceptor.getValue()).intValue();
			} else
				Receptor = 0;

			Vector<String> headerDataVentas = new Vector<String>();
			headerDataVentas.add("Venta");
			headerDataVentas.add("Receptor");
			headerDataVentas.add("Semana");
			headerDataVentas.add("Fecha");
			headerDataVentas.add("Cajas");
			headerDataVentas.add("Kilos");
			headerDataVentas.add("Importe");

			Vector<Vector<Object>> tableDataVentas = new Vector<Vector<Object>>();

			Integer numRowsVentas = tblVentas.getRowCount();

			for (Integer k = 0; k < numRowsVentas; k++)
				((DefaultTableModel) tblVentas.getModel()).removeRow(0);

			List<?> VentasList = entity.VentasBySemanaReceptor(this,
					SemanaDesde, SemanaHasta, Receptor, cboTiposMercado
							.getSelectedIndex());

			if (VentasList.size() > 0) {
				String cadenaFecha = "";
				String snumCajas = "";
				String snumKilos = "";
				String simporte = "";
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				NumberFormat formatter1 = new DecimalFormat("#,##0");
				NumberFormat formatter2 = new DecimalFormat("#,##0.00");

				for (Object linea : VentasList) {
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(((Viewventasquery) linea).getId().getIdVenta());
					String nombre = ((Viewventasquery) linea).getId()
							.getNombre();
					oneRow.add(nombre);
					oneRow.add(((Viewventasquery) linea).getId().getSemana());
					if (((Viewventasquery) linea).getId().getFecha() != null)
						cadenaFecha = formato.format(((Viewventasquery) linea)
								.getId().getFecha());
					else
						cadenaFecha = "";
					oneRow.add(cadenaFecha);
					numCajas = ((Number) ((Viewventasquery) linea).getId()
							.getNumCajas()).floatValue();
					snumCajas = formatter1.format(numCajas);
					oneRow.add(snumCajas);
					numKilos = ((Number) ((Viewventasquery) linea).getId()
							.getNumKilos()).floatValue();
					snumKilos = formatter1.format(numKilos);
					oneRow.add(snumKilos);
					importe = ((Number) ((Viewventasquery) linea).getId()
							.getImporte()).floatValue();
					simporte = formatter2.format(importe);
					oneRow.add(simporte);
					tableDataVentas.add(oneRow);

					totalKilos = totalKilos + numKilos;
					totalCajas = totalCajas + numCajas;
					totalImporte = totalImporte + importe;
				}

				tblVentas.setModel(new MyViewTableModel(tableDataVentas,
						headerDataVentas));
				tblVentas.getColumn("Venta").setPreferredWidth(
						EntityType.NumberWidth);
				tblVentas.getColumn("Receptor").setPreferredWidth(
						EntityType.TextWidth);
				tblVentas.getColumn("Semana").setPreferredWidth(
						EntityType.NumberWidth);
				tblVentas.getColumn("Fecha").setPreferredWidth(
						EntityType.DateWidth);
				tblVentas.getColumn("Kilos").setPreferredWidth(
						EntityType.NumberWidth);
				tblVentas.getColumn("Cajas").setPreferredWidth(
						EntityType.NumberWidth);
				tblVentas.getColumn("Importe").setPreferredWidth(
						EntityType.NumberWidth);
				DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
				rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
				tblVentas.getColumn("Semana").setCellRenderer(rightRenderer);
				tblVentas.getColumn("Kilos").setCellRenderer(rightRenderer);
				tblVentas.getColumn("Cajas").setCellRenderer(rightRenderer);
				tblVentas.getColumn("Importe").setCellRenderer(rightRenderer);

				txtTotalKilosVentas.setValue(totalKilos);
				txtTotalCajasVentas.setValue(totalCajas);
				txtImporteVentas.setValue(totalImporte);
			}

			Vector<String> headerDataVenCategorias = new Vector<String>();
			headerDataVenCategorias.add("Categorías");
			headerDataVenCategorias.add("Cajas");
			headerDataVenCategorias.add("Kilos");
			headerDataVenCategorias.add("Importe");
			headerDataVenCategorias.add("Porcentaje");

			Vector<Vector<Object>> tableDataVenCategorias = new Vector<Vector<Object>>();

			Integer numRowsCategorias = tblVenCategorias.getRowCount();

			for (Integer k = 0; k < numRowsCategorias; k++)
				((DefaultTableModel) tblVenCategorias.getModel()).removeRow(0);

			List<?> VenCategoriasList = entity
					.VentasCategoriasBySemanaReceptor(this, SemanaDesde,
							SemanaHasta, Receptor, cboTiposMercado
									.getSelectedIndex());

			if (VenCategoriasList.size() > 0) {
				String sporcentaje = "";
				String snumKilos = "";
				String snumCajas = "";
				String simporte = "";
				String nombreCategoria = "";
				NumberFormat formatter1 = new DecimalFormat("#,##0");
				NumberFormat formatter2 = new DecimalFormat("#,##0.00");
				NumberFormat formatter3 = new DecimalFormat("#,##0.000");
				numKilos = ((Number) 0).floatValue();
				numCajas = ((Number) 0).floatValue();
				importe = ((Number) 0).floatValue();
				Object linea = null;
				if ((Receptor == 0)
						&& (cboTiposMercado.getSelectedIndex() == 0)) {
					int index = 0;
					if (VenCategoriasList.size() > 0) {
						linea = VenCategoriasList.get(index);
						nombreCategoria = ((Viewventascategoriasquery) linea)
								.getId().getNombreCategoria();
					}
					while (index < VenCategoriasList.size()) {
						if (!((Viewventascategoriasquery) linea).getId()
								.getNombreCategoria().equals(nombreCategoria)) {
							Vector<Object> oneRow = new Vector<Object>();
							oneRow.add(nombreCategoria);
							snumCajas = formatter1.format(numCajas);
							oneRow.add(snumCajas);
							snumKilos = formatter1.format(numKilos);
							oneRow.add(snumKilos);
							simporte = formatter2.format(importe);
							oneRow.add(simporte);
							sporcentaje = formatter3.format(numKilos
									/ totalKilos * 100);
							oneRow.add(sporcentaje);
							tableDataVenCategorias.add(oneRow);

							numKilos = ((Number) ((Viewventascategoriasquery) linea)
									.getId().getNumKilos()).floatValue();
							numCajas = ((Number) ((Viewventascategoriasquery) linea)
									.getId().getNumCajas()).floatValue();
							importe = ((Number) ((Viewventascategoriasquery) linea)
									.getId().getImpore()).floatValue();
							nombreCategoria = ((Viewventascategoriasquery) linea)
									.getId().getNombreCategoria();
						} else {
							numKilos = numKilos
									+ ((Number) ((Viewventascategoriasquery) linea)
											.getId().getNumKilos())
											.floatValue();
							numCajas = numCajas
									+ ((Number) ((Viewventascategoriasquery) linea)
											.getId().getNumCajas())
											.floatValue();
							importe = importe
									+ ((Number) ((Viewventascategoriasquery) linea)
											.getId().getImpore()).floatValue();
						}
						index++;
						if (index < VenCategoriasList.size())
							linea = VenCategoriasList.get(index);
						else {
							Vector<Object> oneRow = new Vector<Object>();
							oneRow.add(nombreCategoria);
							snumCajas = formatter1.format(numCajas);
							oneRow.add(snumCajas);
							snumKilos = formatter1.format(numKilos);
							oneRow.add(snumKilos);
							simporte = formatter2.format(importe);
							oneRow.add(simporte);
							sporcentaje = formatter3.format(numKilos
									/ totalKilos * 100);
							oneRow.add(sporcentaje);
							tableDataVenCategorias.add(oneRow);
						}
					}

				} else {
					int index = 0;
					if (VenCategoriasList.size() > 0) {
						linea = VenCategoriasList.get(index);
						nombreCategoria = ((Viewventascategoriasreceptorquery) linea)
								.getId().getNombreCategoria();
					}
					while (index < VenCategoriasList.size()) {
						if (!((Viewventascategoriasreceptorquery) linea)
								.getId().getNombreCategoria().equals(
										nombreCategoria)) {
							Vector<Object> oneRow = new Vector<Object>();
							oneRow.add(nombreCategoria);
							snumCajas = formatter1.format(numCajas);
							oneRow.add(snumCajas);
							snumKilos = formatter1.format(numKilos);
							oneRow.add(snumKilos);
							simporte = formatter2.format(importe);
							oneRow.add(simporte);
							sporcentaje = formatter3.format(numKilos
									/ totalKilos * 100);
							oneRow.add(sporcentaje);
							tableDataVenCategorias.add(oneRow);

							numKilos = ((Number) ((Viewventascategoriasreceptorquery) linea)
									.getId().getNumKilos()).floatValue();
							numCajas = ((Number) ((Viewventascategoriasreceptorquery) linea)
									.getId().getNumCajas()).floatValue();
							importe = ((Number) ((Viewventascategoriasreceptorquery) linea)
									.getId().getImpore()).floatValue();
							nombreCategoria = ((Viewventascategoriasreceptorquery) linea)
									.getId().getNombreCategoria();
						} else {
							numKilos = numKilos
									+ ((Number) ((Viewventascategoriasreceptorquery) linea)
											.getId().getNumKilos())
											.floatValue();
							numCajas = numCajas
									+ ((Number) ((Viewventascategoriasreceptorquery) linea)
											.getId().getNumCajas())
											.floatValue();
							importe = importe
									+ ((Number) ((Viewventascategoriasreceptorquery) linea)
											.getId().getImpore()).floatValue();
						}
						index++;
						if (index < VenCategoriasList.size())
							linea = VenCategoriasList.get(index);
						else {
							Vector<Object> oneRow = new Vector<Object>();
							oneRow.add(nombreCategoria);
							snumCajas = formatter1.format(numCajas);
							oneRow.add(snumCajas);
							snumKilos = formatter1.format(numKilos);
							oneRow.add(snumKilos);
							simporte = formatter2.format(importe);
							oneRow.add(simporte);
							sporcentaje = formatter3.format(numKilos
									/ totalKilos * 100);
							oneRow.add(sporcentaje);
							tableDataVenCategorias.add(oneRow);
						}
					}
				}
				tblVenCategorias.setModel(new MyViewTableModel(
						tableDataVenCategorias, headerDataVenCategorias));
				tblVenCategorias.getColumn("Categorías").setPreferredWidth(
						EntityType.TextWidth);
				tblVenCategorias.getColumn("Kilos").setPreferredWidth(
						EntityType.NumberWidth);
				tblVenCategorias.getColumn("Cajas").setPreferredWidth(
						EntityType.NumberWidth);
				tblVenCategorias.getColumn("Importe").setPreferredWidth(
						EntityType.NumberWidth);
				tblVenCategorias.getColumn("Porcentaje").setPreferredWidth(
						EntityType.NumberWidth);
				DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
				rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
				tblVenCategorias.getColumn("Kilos").setCellRenderer(
						rightRenderer);
				tblVenCategorias.getColumn("Cajas").setCellRenderer(
						rightRenderer);
				tblVenCategorias.getColumn("Importe").setCellRenderer(
						rightRenderer);
				tblVenCategorias.getColumn("Porcentaje").setCellRenderer(
						rightRenderer);

			}

		} catch (RuntimeException he) {
			he.printStackTrace();
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.loadEntradas()", he);
		} catch (ParseException e) {
			Message.ShowParseError(parentFrame, "FrmDataQuery.loadEntradas()",
					e);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		lblTitle = new javax.swing.JLabel();
		tabQuery = new javax.swing.JTabbedPane();
		pnlEntradas = new javax.swing.JPanel();
		cmdEntSearch = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblEntradas = new javax.swing.JTable();
		lblSemanaEntDesde = new javax.swing.JLabel();
		txtSemanaEntDesde = new javax.swing.JFormattedTextField();
		lblSemanaEntHasta = new javax.swing.JLabel();
		txtSemanaEntHasta = new javax.swing.JFormattedTextField();
		lblCosechero = new javax.swing.JLabel();
		txtIdCosechero = new javax.swing.JFormattedTextField();
		cboCosecheros = new javax.swing.JComboBox();
		jScrollPane2 = new javax.swing.JScrollPane();
		tblEntCategorias = new javax.swing.JTable();
		lblTotalKilosEntradas = new javax.swing.JLabel();
		txtTotalKilosEntradas = new javax.swing.JFormattedTextField();
		lblTotalPinasEntradas = new javax.swing.JLabel();
		txtTotalPinasEntradas = new javax.swing.JFormattedTextField();
		lblKilosvsPinas = new javax.swing.JLabel();
		txtKilosvsPinas = new javax.swing.JFormattedTextField();
		btnPrintEntradas = new javax.swing.JButton();
		pnlVentas = new javax.swing.JPanel();
		lblSemanaVenDesde = new javax.swing.JLabel();
		txtSemanaVenDesde = new javax.swing.JFormattedTextField();
		lblSemanaVenHasta = new javax.swing.JLabel();
		txtSemanaVenHasta = new javax.swing.JFormattedTextField();
		lblReceptor = new javax.swing.JLabel();
		txtIdReceptor = new javax.swing.JFormattedTextField();
		cboReceptores = new javax.swing.JComboBox();
		cmdVenSearch = new javax.swing.JButton();
		jScrollPane3 = new javax.swing.JScrollPane();
		tblVentas = new javax.swing.JTable();
		jScrollPane4 = new javax.swing.JScrollPane();
		tblVenCategorias = new javax.swing.JTable();
		lblTotalKilosVentas = new javax.swing.JLabel();
		txtTotalKilosVentas = new javax.swing.JFormattedTextField();
		lblTotalCajasVentas = new javax.swing.JLabel();
		txtTotalCajasVentas = new javax.swing.JFormattedTextField();
		lblImporteVentas = new javax.swing.JLabel();
		txtImporteVentas = new javax.swing.JFormattedTextField();
		cboTiposMercado = new javax.swing.JComboBox();
		lblTipoMercado = new javax.swing.JLabel();
		btnPrintVentas = new javax.swing.JButton();
		cmdClose = new javax.swing.JButton();

		lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
		lblTitle.setForeground(new java.awt.Color(0, 153, 0));
		lblTitle.setText("Consulta de entradas y ventas");

		tabQuery.setFont(new java.awt.Font("Segoe UI", 0, 14));

		pnlEntradas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		cmdEntSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdEntSearch.setText("Buscar");
		cmdEntSearch.setToolTipText("Buscar");
		cmdEntSearch.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdEntSearch.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdEntSearchMousePressed(evt);
			}
		});

		jScrollPane1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		tblEntradas.setFont(new java.awt.Font("Segoe UI", 0, 14));
		tblEntradas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblEntradas.setRowHeight(18);
		tblEntradas.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblEntradasMousePressed(evt);
			}
		});
		jScrollPane1.setViewportView(tblEntradas);

		lblSemanaEntDesde.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemanaEntDesde.setForeground(new java.awt.Color(255, 0, 0));
		lblSemanaEntDesde
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemanaEntDesde.setText("Semana desde");
		lblSemanaEntDesde
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtSemanaEntDesde.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemanaEntDesde
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemanaEntDesde.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtSemanaEntDesde.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblSemanaEntHasta.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemanaEntHasta.setForeground(new java.awt.Color(255, 0, 0));
		lblSemanaEntHasta
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemanaEntHasta.setText("Semana hasta");
		lblSemanaEntHasta
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtSemanaEntHasta.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemanaEntHasta
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemanaEntHasta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtSemanaEntHasta.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblCosechero.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCosechero.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblCosechero.setText("Cosechero");

		txtIdCosechero.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdCosechero
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtIdCosechero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdCosechero
				.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
		txtIdCosechero.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtIdCosechero.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtIdCosecheroKeyReleased(evt);
			}
		});

		cboCosecheros.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		cboCosecheros.setName("Id subcategoria");
		cboCosecheros.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboCosecherosActionPerformed(evt);
			}
		});

		jScrollPane2.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		tblEntCategorias.setFont(new java.awt.Font("Segoe UI", 0, 14));
		tblEntCategorias.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblEntCategorias.setRowHeight(18);
		jScrollPane2.setViewportView(tblEntCategorias);

		lblTotalKilosEntradas.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblTotalKilosEntradas
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTotalKilosEntradas.setText("Total kilos");

		txtTotalKilosEntradas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalKilosEntradas.setEditable(false);
		txtTotalKilosEntradas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								java.text.NumberFormat.getIntegerInstance())));
		txtTotalKilosEntradas
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalKilosEntradas.setFont(new java.awt.Font("Segoe UI", 1, 14));

		lblTotalPinasEntradas.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblTotalPinasEntradas
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTotalPinasEntradas.setText("Total pi\u00f1as");

		txtTotalPinasEntradas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalPinasEntradas.setEditable(false);
		txtTotalPinasEntradas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								java.text.NumberFormat.getIntegerInstance())));
		txtTotalPinasEntradas
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalPinasEntradas.setFont(new java.awt.Font("Segoe UI", 1, 14));

		lblKilosvsPinas.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblKilosvsPinas
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblKilosvsPinas.setText("Kilos/Pi\u00f1as");

		txtKilosvsPinas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtKilosvsPinas.setEditable(false);
		txtKilosvsPinas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtKilosvsPinas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtKilosvsPinas.setFont(new java.awt.Font("Segoe UI", 1, 14));

		btnPrintEntradas.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/imagesPackage/Print.png"))); // NOI18N
		btnPrintEntradas.setToolTipText("Imprimir");
		btnPrintEntradas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnPrintEntradas.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnPrintEntradasMousePressed(evt);
			}
		});

		javax.swing.GroupLayout pnlEntradasLayout = new javax.swing.GroupLayout(
				pnlEntradas);
		pnlEntradas.setLayout(pnlEntradasLayout);
		pnlEntradasLayout
				.setHorizontalGroup(pnlEntradasLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlEntradasLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlEntradasLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																811,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane2,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																811,
																Short.MAX_VALUE)
														.addGroup(
																pnlEntradasLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblCosechero,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtIdCosechero,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				69,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				cboCosecheros,
																				0,
																				503,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				cmdEntSearch,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlEntradasLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblSemanaEntDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				119,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtSemanaEntDesde,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				72,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblSemanaEntHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				119,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtSemanaEntHasta,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				72,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlEntradasLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblTotalKilosEntradas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				77,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtTotalKilosEntradas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblTotalPinasEntradas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				77,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtTotalPinasEntradas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblKilosvsPinas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				77,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtKilosvsPinas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				142,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnPrintEntradas)))
										.addContainerGap()));
		pnlEntradasLayout
				.setVerticalGroup(pnlEntradasLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlEntradasLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlEntradasLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txtSemanaEntDesde,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblSemanaEntDesde)
														.addComponent(
																txtSemanaEntHasta,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblSemanaEntHasta))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlEntradasLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lblCosechero)
														.addComponent(
																txtIdCosechero,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cmdEntSearch)
														.addComponent(
																cboCosecheros,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												124, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jScrollPane2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												159, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlEntradasLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																pnlEntradasLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				lblTotalKilosEntradas)
																		.addComponent(
																				txtTotalKilosEntradas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblTotalPinasEntradas)
																		.addComponent(
																				txtTotalPinasEntradas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblKilosvsPinas)
																		.addComponent(
																				txtKilosvsPinas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																btnPrintEntradas))
										.addContainerGap()));

		tabQuery.addTab("Entradas", pnlEntradas);

		lblSemanaVenDesde.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemanaVenDesde.setForeground(new java.awt.Color(255, 0, 0));
		lblSemanaVenDesde
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemanaVenDesde.setText("Semana desde");
		lblSemanaVenDesde
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtSemanaVenDesde.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemanaVenDesde
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemanaVenDesde.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtSemanaVenDesde.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblSemanaVenHasta.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemanaVenHasta.setForeground(new java.awt.Color(255, 0, 0));
		lblSemanaVenHasta
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemanaVenHasta.setText("Semana hasta");
		lblSemanaVenHasta
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtSemanaVenHasta.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemanaVenHasta
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemanaVenHasta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtSemanaVenHasta.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblReceptor.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblReceptor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblReceptor.setText("Receptor");

		txtIdReceptor.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdReceptor
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtIdReceptor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdReceptor
				.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
		txtIdReceptor.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtIdReceptor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtIdReceptorKeyReleased(evt);
			}
		});

		cboReceptores.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		cboReceptores.setName("Id subcategoria");
		cboReceptores.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboReceptoresActionPerformed(evt);
			}
		});

		cmdVenSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdVenSearch.setText("Buscar");
		cmdVenSearch.setToolTipText("Buscar");
		cmdVenSearch.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdVenSearch.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdVenSearchMousePressed(evt);
			}
		});

		jScrollPane3.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		tblVentas.setFont(new java.awt.Font("Segoe UI", 0, 14));
		tblVentas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblVentas.setRowHeight(18);
		tblVentas.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblVentasMousePressed(evt);
			}
		});
		jScrollPane3.setViewportView(tblVentas);

		jScrollPane4.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		tblVenCategorias.setFont(new java.awt.Font("Segoe UI", 0, 14));
		tblVenCategorias.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblVenCategorias.setRowHeight(18);
		jScrollPane4.setViewportView(tblVenCategorias);

		lblTotalKilosVentas.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblTotalKilosVentas
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTotalKilosVentas.setText("Total kilos");

		txtTotalKilosVentas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalKilosVentas.setEditable(false);
		txtTotalKilosVentas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								java.text.NumberFormat.getIntegerInstance())));
		txtTotalKilosVentas
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalKilosVentas.setFont(new java.awt.Font("Segoe UI", 1, 14));

		lblTotalCajasVentas.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblTotalCajasVentas
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTotalCajasVentas.setText("Total cajas");

		txtTotalCajasVentas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalCajasVentas.setEditable(false);
		txtTotalCajasVentas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								java.text.NumberFormat.getIntegerInstance())));
		txtTotalCajasVentas
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalCajasVentas.setFont(new java.awt.Font("Segoe UI", 1, 14));

		lblImporteVentas.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblImporteVentas
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblImporteVentas.setText("Importe");

		txtImporteVentas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtImporteVentas.setEditable(false);
		txtImporteVentas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtImporteVentas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtImporteVentas.setFont(new java.awt.Font("Segoe UI", 1, 14));

		cboTiposMercado.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		cboTiposMercado.setName("Id subcategoria");

		lblTipoMercado.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblTipoMercado.setForeground(new java.awt.Color(255, 0, 0));
		lblTipoMercado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTipoMercado.setText("Tipo de mercado");
		lblTipoMercado
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		btnPrintVentas.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/imagesPackage/Print.png"))); // NOI18N
		btnPrintVentas.setToolTipText("Imprimir");
		btnPrintVentas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnPrintVentas.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnPrintVentasMousePressed(evt);
			}
		});

		javax.swing.GroupLayout pnlVentasLayout = new javax.swing.GroupLayout(
				pnlVentas);
		pnlVentas.setLayout(pnlVentasLayout);
		pnlVentasLayout
				.setHorizontalGroup(pnlVentasLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlVentasLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlVentasLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane4,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																815,
																Short.MAX_VALUE)
														.addGroup(
																pnlVentasLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblTotalKilosVentas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				77,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtTotalKilosVentas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblTotalCajasVentas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				77,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtTotalCajasVentas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblImporteVentas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				77,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtImporteVentas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				146,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnPrintVentas))
														.addComponent(
																jScrollPane3,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																815,
																Short.MAX_VALUE)
														.addGroup(
																pnlVentasLayout
																		.createSequentialGroup()
																		.addGroup(
																				pnlVentasLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								pnlVentasLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblSemanaVenDesde,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												119,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtSemanaVenDesde,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												72,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												lblSemanaVenHasta,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												119,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtSemanaVenHasta,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												72,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												60,
																												Short.MAX_VALUE)
																										.addComponent(
																												lblTipoMercado,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												119,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												cboTiposMercado,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												132,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								pnlVentasLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblReceptor,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtIdReceptor,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												69,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												cboReceptores,
																												0,
																												507,
																												Short.MAX_VALUE)))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				12,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				cmdVenSearch,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		pnlVentasLayout
				.setVerticalGroup(pnlVentasLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlVentasLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlVentasLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txtSemanaVenDesde,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblSemanaVenDesde)
														.addComponent(
																lblSemanaVenHasta)
														.addComponent(
																cboTiposMercado,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtSemanaVenHasta,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																26,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblTipoMercado))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlVentasLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lblReceptor)
														.addComponent(
																txtIdReceptor,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cmdVenSearch)
														.addComponent(
																cboReceptores,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jScrollPane3,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												127, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane4,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												159, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlVentasLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																pnlVentasLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				lblTotalKilosVentas)
																		.addComponent(
																				txtTotalKilosVentas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblTotalCajasVentas)
																		.addComponent(
																				txtTotalCajasVentas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblImporteVentas)
																		.addComponent(
																				txtImporteVentas,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																btnPrintVentas))
										.addContainerGap()));

		tabQuery.addTab("Ventas", pnlVentas);

		cmdClose.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdClose.setText("Cerrar");
		cmdClose.setToolTipText("Cerrar la ventana");
		cmdClose.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdClose.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdCloseMousePressed(evt);
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
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(tabQuery)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblTitle,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				531,
																				Short.MAX_VALUE)
																		.addGap(
																				223,
																				223,
																				223)
																		.addComponent(
																				cmdClose,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(17, 17, 17)));
		layout
				.setVerticalGroup(layout
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
														.addComponent(cmdClose)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				4,
																				4,
																				4)
																		.addComponent(
																				lblTitle)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												tabQuery,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												482, Short.MAX_VALUE)
										.addContainerGap()));

		tabQuery.getAccessibleContext().setAccessibleName("tabEntity");

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btnPrintEntradasMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			InformeVale report = new InformeVale(parentFrame);
			if (!(txtIdCosechero.getText().equals(""))) {
				report.runReporte(session.getEmpresa().getIdEmpresa(), session
						.getEjercicio().getEjercicio(), ((Number) txtIdCosechero
						.getValue()).intValue(), ((Number) txtSemanaEntDesde.getValue())
						.intValue(), ((Number) txtSemanaEntHasta.getValue())
						.intValue());
			}
			else {
				report.runReporte(session.getEmpresa().getIdEmpresa(), session
						.getEjercicio().getEjercicio(), ((Number) 0).intValue(), ((Number) txtSemanaEntDesde.getValue())
						.intValue(), ((Number) txtSemanaEntHasta.getValue())
						.intValue());	
			}		
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.btnPrintEntradasMousePressed()", he);
		}
	}
	
	private void btnPrintVentasMousePressed(java.awt.event.MouseEvent evt) {
		try {
			
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			InformeValeVentas report = new InformeValeVentas(parentFrame);
			if (!(txtIdReceptor.getText().equals(""))) {
				report.runReporte(session.getEmpresa().getIdEmpresa(), session
						.getEjercicio().getEjercicio(), ((Number) txtIdReceptor
						.getValue()).intValue(), ((Number) txtSemanaVenDesde.getValue())
						.intValue(), ((Number) txtSemanaVenHasta.getValue())
						.intValue(), cboTiposMercado
						.getSelectedIndex());
			}
			else {
				report.runReporte(session.getEmpresa().getIdEmpresa(), session
						.getEjercicio().getEjercicio(), ((Number) 0).intValue(), ((Number) txtSemanaVenDesde.getValue())
						.intValue(), ((Number) txtSemanaVenHasta.getValue())
						.intValue(), cboTiposMercado
						.getSelectedIndex());	
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.btnPrintVentasMousePressed()", he);
		}
	}
	
	private void tblVentasMousePressed(java.awt.event.MouseEvent evt) {
		try {
			if (evt.getClickCount() == 2) {
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				loadRecordVenta(tblVentas.getSelectedRow());
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.tblVentasMousePressed()", he);
		}
	}

	private void cmdVenSearchMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			loadVentas();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.cmdVenSearchMousePressed()", he);
		}
	}

	private void cboReceptoresActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingReceptor) {
				changingReceptor = true;
				if (cboReceptores.getSelectedIndex() != -1) {
					List<?> receptoreslist = entity.executeQuery(this, "*",
							"Receptores", "Nombre = '"
									+ (String) cboReceptores.getSelectedItem()
									+ "'", "");
					if (receptoreslist.size() > 0) {
						Receptores receptoresItem = (Receptores) receptoreslist
								.get(0);
						txtIdReceptor.setValue(receptoresItem.getId()
								.getIdReceptor());
					}
				}
				changingReceptor = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.cboReceptoresActionPerformed()", he);
		}
	}

	private void txtIdReceptorKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeReceptor();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.txtIdReceptorKeyReleased()", he);
		}
	}

	private void cboCosecherosActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingCosechero) {
				changingCosechero = true;
				if (cboCosecheros.getSelectedIndex() != -1) {
					List<?> cosecheroslist = entity.executeQuery(this, "*",
							"Cosecheros",
							"concat(concat(Nombre,' '), coalesce(Apellidos,'')) = '"
									+ (String) cboCosecheros.getSelectedItem()
									+ "'", "");
					if (cosecheroslist.size() > 0) {
						Cosecheros cosecherosItem = (Cosecheros) cosecheroslist
								.get(0);
						txtIdCosechero.setValue(cosecherosItem.getId()
								.getIdCosechero());
					}
				}
				changingCosechero = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.cboCosecherosActionPerformed()", he);
		}
	}

	private void txtIdCosecheroKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeCosechero();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.txtIdCosecheroKeyReleased()", he);
		}
	}

	private void cmdCloseMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			this.setClosed(true);
			this.dispose();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void cmdEntSearchMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			loadEntradas();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.cmdEntSearchMousePressed()", he);
		}
	}

	private void loadRecordEntrada(int fila) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			Entradascabecera entrada = new Entradascabecera();
			EntradascabeceraId entradascabeceraid = new EntradascabeceraId();
			entradascabeceraid.setEjercicios(session.getEjercicio());
			entradascabeceraid.setEmpresas(session.getEmpresa());
			entradascabeceraid.setIdEntrada((Integer) tblEntradas.getValueAt(
					fila, 0));
			entrada = entity.EntradascabeceraFindById(this,
					entradascabeceraid);
			fDetail = new FrmEntrada(parentFrame,
						getSession());
			if (fDetail != null) {
				if (tabQuery.getTabCount() > 2) {
					tabQuery.remove(2);
				}
				tabQuery
						.insertTab("Entrada", null, (Component) fDetail, "", 2);
			}
			((FrmEntrada) fDetail).loadData(entrada);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
			tabQuery.setSelectedIndex(2);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntityView.loadRecord()",
					he);
		}
	}
	
	private void loadRecordVenta(int fila) {
		try {
			Ventascabecera venta = new Ventascabecera();
			VentascabeceraId ventacabeceraid = new VentascabeceraId();
			ventacabeceraid.setEjercicios(session.getEjercicio());
			ventacabeceraid.setEmpresas(session.getEmpresa());
			ventacabeceraid.setIdVenta((Integer) tblVentas.getValueAt(fila,
					0));
			venta = entity.VentascabeceraFindById(fDetail, ventacabeceraid);
			fDetail = new FrmVenta(parentFrame,
						getSession());
			if (fDetail != null) {
				if (tabQuery.getTabCount() > 2) {
					tabQuery.remove(2);
				}
				tabQuery
						.insertTab("Venta", null, (Component) fDetail, "", 2);
			}
			((FrmVenta) fDetail).loadData(venta);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
			tabQuery.setSelectedIndex(2);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntityView.loadRecord()",
					he);
		}
	}
	
	private void tblEntradasMousePressed(java.awt.event.MouseEvent evt) {
		try {
			if (evt.getClickCount() == 2) {
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				loadRecordEntrada(tblEntradas.getSelectedRow());
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmDataQuery.tblEntradasMousePressed()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnPrintEntradas;
	private javax.swing.JButton btnPrintVentas;
	private static javax.swing.JComboBox cboCosecheros;
	private static javax.swing.JComboBox cboReceptores;
	private static javax.swing.JComboBox cboTiposMercado;
	private javax.swing.JButton cmdClose;
	private javax.swing.JButton cmdEntSearch;
	private javax.swing.JButton cmdVenSearch;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JLabel lblCosechero;
	private javax.swing.JLabel lblImporteVentas;
	private javax.swing.JLabel lblKilosvsPinas;
	private javax.swing.JLabel lblReceptor;
	private javax.swing.JLabel lblSemanaEntDesde;
	private javax.swing.JLabel lblSemanaEntHasta;
	private javax.swing.JLabel lblSemanaVenDesde;
	private javax.swing.JLabel lblSemanaVenHasta;
	private javax.swing.JLabel lblTipoMercado;
	private javax.swing.JLabel lblTitle;
	private javax.swing.JLabel lblTotalCajasVentas;
	private javax.swing.JLabel lblTotalKilosEntradas;
	private javax.swing.JLabel lblTotalKilosVentas;
	private javax.swing.JLabel lblTotalPinasEntradas;
	private javax.swing.JPanel pnlEntradas;
	private javax.swing.JPanel pnlVentas;
	private javax.swing.JTabbedPane tabQuery;
	private static javax.swing.JTable tblEntCategorias;
	private static javax.swing.JTable tblEntradas;
	private static javax.swing.JTable tblVenCategorias;
	private static javax.swing.JTable tblVentas;
	private static javax.swing.JFormattedTextField txtIdCosechero;
	private static javax.swing.JFormattedTextField txtIdReceptor;
	private javax.swing.JFormattedTextField txtImporteVentas;
	private javax.swing.JFormattedTextField txtKilosvsPinas;
	private javax.swing.JFormattedTextField txtSemanaEntDesde;
	private javax.swing.JFormattedTextField txtSemanaEntHasta;
	private javax.swing.JFormattedTextField txtSemanaVenDesde;
	private javax.swing.JFormattedTextField txtSemanaVenHasta;
	private javax.swing.JFormattedTextField txtTotalCajasVentas;
	private javax.swing.JFormattedTextField txtTotalKilosEntradas;
	private javax.swing.JFormattedTextField txtTotalKilosVentas;
	private javax.swing.JFormattedTextField txtTotalPinasEntradas;
	// End of variables declaration//GEN-END:variables

}