/*
 * prueba.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.Component;
import java.awt.Cursor;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import sessionPackage.MySession;

import entitiesPackage.Bancos;
import entitiesPackage.BancosId;
import entitiesPackage.Barcos;
import entitiesPackage.BarcosId;
import entitiesPackage.Bimestres;
import entitiesPackage.BimestresId;
import entitiesPackage.Calendario;
import entitiesPackage.CalendarioId;
import entitiesPackage.Categorias;
import entitiesPackage.CategoriasId;
import entitiesPackage.Conceptos;
import entitiesPackage.ConceptosId;
import entitiesPackage.Conductores;
import entitiesPackage.ConductoresId;
import entitiesPackage.Cosecheros;
import entitiesPackage.CosecherosId;
import entitiesPackage.Ejercicios;
import entitiesPackage.Empleados;
import entitiesPackage.EmpleadosId;
import entitiesPackage.Empleadosnominas;
import entitiesPackage.Empresas;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Entradascabecera;
import entitiesPackage.EntradascabeceraId;
import entitiesPackage.Facturascabecera;
import entitiesPackage.FacturascabeceraId;
import entitiesPackage.Facturaspagocabecera;
import entitiesPackage.FacturaspagocabeceraId;
import entitiesPackage.Identidades;
import entitiesPackage.IdentidadesId;
import entitiesPackage.Message;
import entitiesPackage.Receptores;
import entitiesPackage.ReceptoresId;
import entitiesPackage.Tiposgasto;
import entitiesPackage.TiposgastoId;
import entitiesPackage.Vehiculos;
import entitiesPackage.VehiculosId;
import entitiesPackage.Ventascabecera;
import entitiesPackage.VentascabeceraId;
import entitiesPackage.Viewentradasquery;
import entitiesPackage.Viewliquidaciones;
import entitiesPackage.Viewventasquery;
import entitiesPackage.Zonas;
import entitiesPackage.ZonasId;
import entitiesPackage.EntityType.EntitiesType;

import winUIPackage.PreferencesUI;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmEntityView extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static EntitiesType entitytype;
	private static String tablename;
	private static String[] filterfields;
	private static String[] fieldtypes;
	private static String[] headers;
	private static int[] columnswidth;
	private static int[] columnsalign;
	private static String title;
	private static boolean filterByEmpresa = false;
	private static boolean filterByEjercicio = false;
	private static String FieldEmpresa;
	private static String FieldEjercicio;
	private static Boolean editable;

	private Object fDetail;

	private static java.awt.Frame parentFrame;
	private static MySession session;
	private static Entity entity;

	public java.awt.Frame getparentFrame() {
		return FrmEntityView.parentFrame;
	}

	public static void setparentFrame(java.awt.Frame parentFrame) {
		FrmEntityView.parentFrame = parentFrame;
	}

	public static MySession getSession() {
		return FrmEntityView.session;
	}

	public static void setSession(MySession session) {
		FrmEntityView.session = session;
	}

	public static Entity getEntity() {
		return FrmEntityView.entity;
	}

	public static void setEntity(Entity entity) {
		FrmEntityView.entity = entity;
	}

	public static void setentitytype(EntityType.EntitiesType entitytype) {
		FrmEntityView.entitytype = entitytype;
	}

	public static EntityType.EntitiesType getentitytype() {
		return entitytype;
	}

	public static void settablename(String tablename) {
		FrmEntityView.tablename = tablename;
	}

	public static String gettablename() {
		return tablename;
	}

	public static void setfilterfields(String[] filterfields) {
		FrmEntityView.filterfields = filterfields;
	}

	public static String[] getfilterfields() {
		return filterfields;
	}

	public static void setfieldtypes(String[] fieldtypes) {
		FrmEntityView.fieldtypes = fieldtypes;
	}

	public static String[] getfieldtypes() {
		return fieldtypes;
	}

	public static void setcolumnswidth(int[] columnswidth) {
		FrmEntityView.columnswidth = columnswidth;
	}

	public static int[] getcolumnswidth() {
		return columnswidth;
	}
	
	public static void setcolumnsalign(int[] columnsalign) {
		FrmEntityView.columnsalign = columnsalign;
	}

	public static int[] getcolumnsalign() {
		return columnsalign;
	}

	public static void setheaders(String[] headers) {
		FrmEntityView.headers = headers;
	}

	public static String[] getheaders() {
		return headers;
	}

	public static void setFilterByEmpresa(boolean filterByEmpresa) {
		FrmEntityView.filterByEmpresa = filterByEmpresa;
	}

	public static boolean getFilterByEmpresa() {
		return filterByEmpresa;
	}

	public static void setFilterByEjercicio(boolean filterByEjercicio) {
		FrmEntityView.filterByEjercicio = filterByEjercicio;
	}

	public static boolean getFilterByEjercicio() {
		return filterByEjercicio;
	}

	/** Creates new form FrmEntity */
	public FrmEntityView() {
		initComponents();
	}

	public FrmEntityView(EntityType.EntitiesType entitytype,
			java.awt.Frame parentFrame, MySession session, Boolean editable,
			Boolean printable) {

		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);

			FrmEntityView.setEntity(new Entity(entitytype, session));
			FrmEntityView.setentitytype(entitytype);
			FrmEntityView.title = entitytype.gettitle();
			FrmEntityView.settablename(entitytype.getname());
			FrmEntityView.setfilterfields(entitytype.getfilterfields());
			FrmEntityView.setfieldtypes(entitytype.getfieldTypes());
			FrmEntityView.setheaders(entitytype.getheaders());
			FrmEntityView.setcolumnswidth(entitytype.getcolumnswidth());
			FrmEntityView.setcolumnsalign(entitytype.getcolumnsalign());
			FrmEntityView.setparentFrame(parentFrame);
			session.getSession().close();
			FrmEntityView.setSession(session);
			entity.setSession(session);
			initComponents();
			setInitialFiltersAndCache();
			preparetabRecord();
			pnlButtoms.setBackground(PreferencesUI.DesktopBackgroundColor);

			if (editable) {
				btnAdd.setVisible(true);
				btnEdit.setVisible(true);
				btnDelete.setVisible(true);
			} else {
				btnAdd.setVisible(false);
				btnEdit.setVisible(false);
				btnDelete.setVisible(false);
			}
			if (printable) {
				btnPrint.setVisible(true);
			} else {
				btnPrint.setVisible(false);
			}
			FrmEntityView.editable = editable;

			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntityView()", he);
		}
	}

	public void showEntity() {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);

			setTitle(title);
			lblTitle.setText(title);
			runSearchQuery();

			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntityView.showEntity()",
					he);
		}
	}

	private void setInitialFiltersAndCache() {

		try {
			int index = entitytype.getindex();

			switch (index) {

			case EntityType.indexBancos: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexBarcos: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexCalendario: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexBimestres: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexCategorias: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexConceptos: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexConductores: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexCosecheros: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexEmpresas: {
				setFilterByEmpresa(false);
				setFilterByEjercicio(false);
				break;
			}
			case EntityType.indexEjercicios: {
				setFilterByEmpresa(false);
				setFilterByEjercicio(false);
				break;
			}
			case EntityType.indexReceptores: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexZonas: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexVehiculos: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexEntradas: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicio";
				break;
			}
			case EntityType.indexVentas: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicio";
				break;
			}
			case EntityType.indexEmpleados: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexIdentidades: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexFacturas: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexFacturasPago: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			case EntityType.indexLiquidaciones: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicio";
				break;
			}
			case EntityType.indexTiposGasto: {
				setFilterByEmpresa(true);
				FieldEmpresa = "id.empresas.idEmpresa";
				setFilterByEjercicio(true);
				FieldEjercicio = "id.ejercicios.ejercicio";
				break;
			}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.setInitialFiltersAndCache()", he);
		}
	}

	private void preparetabRecord() {
		try {
			int index = entitytype.getindex();

			switch (index) {

			case EntityType.indexBancos: {
				if (fDetail == null) {
					fDetail = new FrmBanco(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexBarcos: {
				if (fDetail == null) {
					fDetail = new FrmBarco(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexCalendario: {
				if (fDetail == null) {
					fDetail = new FrmSemana(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexBimestres: {
				if (fDetail == null) {
					fDetail = new FrmBimestre(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexCategorias: {
				if (fDetail == null) {
					fDetail = new FrmCategoria(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexConceptos: {
				if (fDetail == null) {
					fDetail = new FrmConcepto(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexConductores: {
				if (fDetail == null) {
					fDetail = new FrmConductor(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexCosecheros: {
				if (fDetail == null) {
					fDetail = new FrmCosechero(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexEmpresas: {
				if (fDetail == null) {
					fDetail = new FrmEmpresa(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexEjercicios: {
				if (fDetail == null) {
					fDetail = new FrmEjercicio(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexReceptores: {
				if (fDetail == null) {
					fDetail = new FrmReceptor(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexZonas: {
				if (fDetail == null) {
					fDetail = new FrmZona(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexVehiculos: {
				if (fDetail == null) {
					fDetail = new FrmVehiculo(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexEntradas: {
				if (fDetail == null) {
					fDetail = new FrmEntrada(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexVentas: {
				if (fDetail == null) {
					fDetail = new FrmVenta(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexIdentidades: {
				if (fDetail == null) {
					fDetail = new FrmIdentidad(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexEmpleados: {
				if (fDetail == null) {
					fDetail = new FrmEmpleado(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexFacturas: {
				if (fDetail == null) {
					fDetail = new FrmFactura(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexFacturasPago: {
				if (fDetail == null) {
					fDetail = new FrmFacturaPago(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			case EntityType.indexTiposGasto: {
				if (fDetail == null) {
					fDetail = new FrmTipoGasto(FrmEntityView.parentFrame,
							getSession());
				}
				break;
			}
			}
			if (fDetail != null) {
				tabEntity
						.insertTab("Detalle", null, (Component) fDetail, "", 1);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.preparetabRecord()", he);
		}
	}

	private static String createSearchQueryWhere() {

		try {
			String resultwhere = "(1=1)";

			if (getFilterByEmpresa())
				resultwhere = resultwhere + " and (" + FieldEmpresa + " = "
						+ session.getEmpresa().getIdEmpresa() + ")";

			if (getFilterByEjercicio())
				resultwhere = resultwhere + " and (" + FieldEjercicio + " = "
						+ session.getEjercicio().getEjercicio() + ")";

			String mSearch = txtSearch.getText();

			if ((mSearch != null) && (!mSearch.equals(""))) {

				resultwhere = resultwhere + " and (";

				for (int i = 0; i < filterfields.length; i++) {

					if (fieldtypes[i] == EntityType.TextType)
						resultwhere = resultwhere + "(" + filterfields[i]
								+ " like '%" + mSearch + "%') or ";
					else {
						if ((fieldtypes[i] == EntityType.NumberType)
								&& isNumeric(mSearch))
							resultwhere = resultwhere + "(" + filterfields[i]
									+ " = " + mSearch + ") or ";
						else
							resultwhere = resultwhere + "(" + filterfields[i]
									+ " = '" + mSearch + "') or ";
					}
				}
				resultwhere = (String) resultwhere.subSequence(0, resultwhere
						.length() - 3);
				resultwhere = resultwhere + ")";
			}
			return resultwhere;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.createSearchQueryWhere()", he);
			return "";
		}
	}

	private static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static void runSearchQuery() {
		try {
			entity.getSession().getSession().close();
			List<?> resultList = entity.executeQueryView(
					FrmEntityView.parentFrame, "*", tablename,
					createSearchQueryWhere(), "");
			displayResult(resultList, headers, columnswidth, columnsalign);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.runSearchQuery()", he);
		}
	}

	public static void displayResult(List<?> resultList, String[] header,
			int[] columnswidth, int[] columnsalign) {
		try {
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
			DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
			leftRenderer.setHorizontalAlignment(JLabel.LEFT);
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			
			
			Vector<String> headerData = new Vector<String>();
			Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();

			int rowselected = tblResult.getSelectedRow();
			Integer numRows = tblResult.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblResult.getModel()).removeRow(0);

			for (String s : header) {
				headerData.add(s);
			}

			for (Object o : resultList) {
				tableData.add(FillData(o));
			}
			tblResult.setModel(new MyViewTableModel(tableData, headerData));
			for (int i = 0; i < headerData.size(); i++) {
				tblResult.getColumn(headerData.get(i)).setPreferredWidth(
						columnswidth[i]);
				
				switch (columnsalign[i]) {
				
				 	case EntityType.Left: {
				 		tblResult.getColumn(headerData.get(i)).setCellRenderer(leftRenderer);
				 		break;
				 	}
				 	case EntityType.Center: {
				 		tblResult.getColumn(headerData.get(i)).setCellRenderer(centerRenderer);
				 		break;
				 	}
				 	case EntityType.Right: {
				 		tblResult.getColumn(headerData.get(i)).setCellRenderer(rightRenderer);
				 		break;
				 	}
				}
			}
			
			ListSelectionModel selectionModel = tblResult.getSelectionModel();
			selectionModel.setSelectionInterval(rowselected, rowselected);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.displayResult()", he);
		}
	}

	private void newRecord() {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);

			int index = entitytype.getindex();

			switch (index) {

			case EntityType.indexBancos: {
				((FrmBanco) fDetail).newData();
				break;
			}

			case EntityType.indexBarcos: {
				((FrmBarco) fDetail).newData();
				break;
			}

			case EntityType.indexCalendario: {
				((FrmSemana) fDetail).newData();
				break;
			}

			case EntityType.indexBimestres: {
				((FrmBimestre) fDetail).newData();
				break;
			}

			case EntityType.indexCategorias: {
				((FrmCategoria) fDetail).newData();
				break;
			}
			
			case EntityType.indexConceptos: {
				((FrmConcepto) fDetail).newData();
				break;
			}

			case EntityType.indexConductores: {
				((FrmConductor) fDetail).newData();
				break;
			}

			case EntityType.indexCosecheros: {
				((FrmCosechero) fDetail).newData();
				break;
			}

			case EntityType.indexEmpresas: {
				((FrmEmpresa) fDetail).newData();
				break;
			}

			case EntityType.indexEjercicios: {
				((FrmEjercicio) fDetail).newData();
				break;
			}

			case EntityType.indexReceptores: {
				((FrmReceptor) fDetail).newData();
				break;
			}
			case EntityType.indexZonas: {
				((FrmZona) fDetail).newData();
				break;
			}
			case EntityType.indexVehiculos: {
				((FrmVehiculo) fDetail).newData();
				break;
			}
			case EntityType.indexEntradas: {
				((FrmEntrada) fDetail).newData();
				break;
			}
			case EntityType.indexVentas: {
				((FrmVenta) fDetail).newData();
				break;
			}
			case EntityType.indexIdentidades: {
				((FrmIdentidad) fDetail).newData();
				break;
			}
			case EntityType.indexEmpleados: {
				((FrmEmpleado) fDetail).newData();
				break;
			}
			case EntityType.indexFacturas: {
				((FrmFactura) fDetail).newData();
				break;
			}
			case EntityType.indexFacturasPago: {
				((FrmFacturaPago) fDetail).newData();
				break;
			}
			case EntityType.indexTiposGasto: {
				((FrmTipoGasto) fDetail).newData();
				break;
			}
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntityView.newRecord()",
					he);
		}
	}

	private static Vector<Object> FillData(Object o) {
		try {
			Vector<Object> oneRow = new Vector<Object>();

			int index = entitytype.getindex();

			switch (index) {

			case EntityType.indexBancos: {
				Bancos banco = (Bancos) o;
				BancosId bancoId = ((Bancos) banco).getId();
				oneRow.add(bancoId.getIdBanco());
				oneRow.add(bancoId.getIdSucursal());
				oneRow.add(((Bancos) banco).getNombreBanco());
				oneRow.add(((Bancos) banco).getNombreSucursal());
				break;
			}
			case EntityType.indexBarcos: {
				Barcos barco = (Barcos) o;
				oneRow.add(((Barcos) barco).getId().getIdBarco());
				oneRow.add(((Barcos) barco).getNombreBarco());
				break;
			}
			case EntityType.indexCalendario: {
				Calendario calendario = (Calendario) o;
				CalendarioId calendarioId = ((Calendario) calendario).getId();
				oneRow.add(calendarioId.getEjercicios().getEjercicio());
				oneRow.add(calendarioId.getSemana());
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				String cadenaFecha = "";
				if (((Calendario) calendario).getDesdeFecha() != null)
					cadenaFecha = formato.format(((Calendario) calendario)
							.getDesdeFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				if (((Calendario) calendario).getHastaFecha() != null)
					cadenaFecha = formato.format(((Calendario) calendario)
							.getHastaFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				break;
			}
			case EntityType.indexBimestres: {
				Bimestres bimestre = (Bimestres) o;
				BimestresId bimestreId = ((Bimestres) bimestre).getId();
				oneRow.add(bimestreId.getEjercicios().getEjercicio());
				oneRow.add(bimestreId.getBimestre());
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				String cadenaFecha = "";
				if (((Bimestres) bimestre).getDesdeFecha() != null)
					cadenaFecha = formato.format(((Bimestres) bimestre)
							.getDesdeFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				if (((Bimestres) bimestre).getHastaFecha() != null)
					cadenaFecha = formato.format(((Bimestres) bimestre)
							.getHastaFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				break;
			}
			case EntityType.indexCategorias: {
				Categorias categoria = (Categorias) o;
				oneRow.add(((Categorias) categoria).getId().getIdCategoria());
				oneRow.add(((Categorias) categoria).getNombreCategoria());
				oneRow.add(((Categorias) categoria).getNumKilosCaja());
				oneRow.add(((Categorias) categoria).getIdSubcategoria());
				oneRow.add(((Categorias) categoria).getPrivada());
				oneRow.add(((Categorias) categoria).getOrden());
				break;
			}
			case EntityType.indexConceptos: {
				Conceptos concepto = (Conceptos) o;
				oneRow.add(((Conceptos) concepto).getId().getConcepto());
				oneRow.add(((Conceptos) concepto).getConceptoDesc());
				break;
			}
			case EntityType.indexConductores: {
				Conductores Conductor = (Conductores) o;
				oneRow.add(((Conductores) Conductor).getId().getIdConductor());
				oneRow.add(((Conductores) Conductor).getNombre());
				oneRow.add(((Conductores) Conductor).getApellidos());
				oneRow.add(((Conductores) Conductor).getNif());
				oneRow.add(((Conductores) Conductor).getTelefono());
				oneRow.add(((Conductores) Conductor).getPoblacion());
				break;
			}
			case EntityType.indexCosecheros: {
				Cosecheros Cosechero = (Cosecheros) o;
				oneRow.add(((Cosecheros) Cosechero).getId().getIdCosechero());
				oneRow.add(((Cosecheros) Cosechero).getNombre());
				oneRow.add(((Cosecheros) Cosechero).getApellidos());
				oneRow.add(((Cosecheros) Cosechero).getNif());
				oneRow.add(((Cosecheros) Cosechero).getTelefono1());
				oneRow.add(((Cosecheros) Cosechero).getPoblacion());
				break;
			}
			case EntityType.indexEmpresas: {
				Empresas Empresa = (Empresas) o;
				oneRow.add(((Empresas) Empresa).getIdEmpresa());
				oneRow.add(((Empresas) Empresa).getNombre());
				oneRow.add(((Empresas) Empresa).getNif());
				oneRow.add(((Empresas) Empresa).getTelefono());
				oneRow.add(((Empresas) Empresa).getPoblacion());
				oneRow.add(((Empresas) Empresa).getProvincia());
				break;
			}
			case EntityType.indexReceptores: {
				Receptores Receptor = (Receptores) o;
				oneRow.add(((Receptores) Receptor).getId().getIdReceptor());
				oneRow.add(((Receptores) Receptor).getNombre());
				oneRow.add(((Receptores) Receptor).getNif());
				oneRow.add(((Receptores) Receptor).getTelefono());
				oneRow.add(((Receptores) Receptor).getPoblacion());
				break;
			}
			case EntityType.indexZonas: {
				Zonas zona = (Zonas) o;
				oneRow.add(((Zonas) zona).getId().getIdZona());
				oneRow.add(((Zonas) zona).getNombreZona());
				break;
			}
			case EntityType.indexVehiculos: {
				Vehiculos vehiculo = (Vehiculos) o;
				oneRow.add(((Vehiculos) vehiculo).getId().getIdVehiculo());
				oneRow.add(((Vehiculos) vehiculo).getMatricula());
				oneRow.add(((Vehiculos) vehiculo).getMarca());
				break;
			}
			case EntityType.indexEntradas: {
				Viewentradasquery entrada = (Viewentradasquery) o;
				oneRow.add(((Viewentradasquery) entrada).getId().getIdEntrada());
				oneRow.add(((Viewentradasquery) entrada).getId().getEjercicio());
				oneRow.add(((Viewentradasquery) entrada).getId().getSemana());
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				String cadenaFecha = "";
				if (((Viewentradasquery) entrada).getId().getFecha() != null)
					cadenaFecha = formato.format(((Viewentradasquery) entrada)
							.getId().getFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				oneRow.add(((Viewentradasquery) entrada).getId().getIdCosechero());
				oneRow.add(((Viewentradasquery) entrada).getId().getNombre());
				oneRow.add(((Viewentradasquery) entrada).getId().getApellidos());
				NumberFormat formatter = new DecimalFormat("#,##0");
				float numpinas = ((Viewentradasquery) entrada).getId().getNumPinas();
				String snumpinas = formatter.format(numpinas);
				oneRow.add(snumpinas);
				double numkilos = ((Viewentradasquery) entrada).getId().getNumKilos();
				String snumkilos = formatter.format(numkilos);
				oneRow.add(snumkilos);
				break;
			}
			case EntityType.indexVentas: {
				Viewventasquery venta = (Viewventasquery) o;
				oneRow.add(((Viewventasquery) venta).getId().getIdVenta());
				oneRow.add(((Viewventasquery) venta).getId().getEjercicio());
				oneRow.add(((Viewventasquery) venta).getId().getSemana());
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				String cadenaFecha = "";
				if (((Viewventasquery) venta).getId().getFecha() != null)
					cadenaFecha = formato.format(((Viewventasquery) venta).getId()
							.getFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				oneRow.add(((Viewventasquery) venta).getId().getNombre());
				NumberFormat formatter1 = new DecimalFormat("#,##0");
				NumberFormat formatter2 = new DecimalFormat("#,##0.00");
				BigDecimal numcajas = ((Viewventasquery) venta).getId().getNumCajas();
				String snumcajas = formatter1.format(numcajas);
				oneRow.add(snumcajas);
				double numkilos = ((Viewventasquery) venta).getId().getNumKilos();
				String snumkilos = formatter1.format(numkilos);
				oneRow.add(snumkilos);
				double importe = ((Viewventasquery) venta).getId().getImporte();
				String simporte = formatter2.format(importe);
				oneRow.add(simporte);
				break;
			}
			case EntityType.indexEjercicios: {
				Ejercicios ejercicio = (Ejercicios) o;
				oneRow.add(((Ejercicios) ejercicio).getEjercicio());
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				String cadenaFecha = "";
				if (((Ejercicios) ejercicio).getDesdeFecha() != null)
					cadenaFecha = formato.format(((Ejercicios) ejercicio)
							.getDesdeFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				if (((Ejercicios) ejercicio).getHastaFecha() != null)
					cadenaFecha = formato.format(((Ejercicios) ejercicio)
							.getHastaFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				break;
			}
			case EntityType.indexIdentidades: {
				Identidades Identidad = (Identidades) o;
				oneRow.add(((Identidades) Identidad).getId().getIdentidad());
				oneRow.add(((Identidades) Identidad).getNombreIdentidad());
				oneRow.add(((Identidades) Identidad).getNif());
				oneRow.add(((Identidades) Identidad).getTelefono());
				oneRow.add(((Identidades) Identidad).getPoblacion());
				break;
			}
			case EntityType.indexEmpleados: {
				Empleados Empleado = (Empleados) o;
				oneRow.add(((Empleados) Empleado).getId().getIdEmpleado());
				oneRow.add(((Empleados) Empleado).getNombre());
				oneRow.add(((Empleados) Empleado).getApellidos());
				oneRow.add(((Empleados) Empleado).getNif());
				oneRow.add(((Empleados) Empleado).getTelefono());
				oneRow.add(((Empleados) Empleado).getPoblacion());
				break;
			}
			case EntityType.indexFacturas: {
				Facturascabecera factura = (Facturascabecera) o;
				oneRow.add(((Facturascabecera) factura).getId().getIdFactura());
				oneRow.add(((Facturascabecera) factura).getId().getEjercicios()
						.getEjercicio());
				oneRow.add(((Facturascabecera) factura).getSemana());
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				String cadenaFecha = "";
				if (((Facturascabecera) factura).getFecha() != null)
					cadenaFecha = formato.format(((Facturascabecera) factura)
							.getFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				oneRow.add(((Facturascabecera) factura).getNombre());
				NumberFormat formatter = new DecimalFormat("#,##0.00");
				float importe = ((Facturascabecera) factura).getImporteFactura();
				String simporte = formatter.format(importe);
				oneRow.add(simporte);
				break;
			}
			case EntityType.indexFacturasPago: {
				Facturaspagocabecera facturapago = (Facturaspagocabecera) o;
				oneRow.add(((Facturaspagocabecera) facturapago).getId()
						.getIdFactura());
				oneRow.add(((Facturaspagocabecera) facturapago).getId()
						.getEjercicios().getEjercicio());
				oneRow
						.add(((Facturaspagocabecera) facturapago)
								.getReferencia());
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				String cadenaFecha = "";
				if (((Facturaspagocabecera) facturapago).getFecha() != null)
					cadenaFecha = formato
							.format(((Facturaspagocabecera) facturapago)
									.getFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				oneRow.add(((Facturaspagocabecera) facturapago).getNombre());
				NumberFormat formatter = new DecimalFormat("#,##0.00");
				float importe = ((Facturaspagocabecera) facturapago).getImporteFactura();
				String simporte = formatter.format(importe);
				oneRow.add(simporte);
				break;
			}
			case EntityType.indexLiquidaciones: {
				Viewliquidaciones liquidacion = (Viewliquidaciones) o;
				oneRow.add(((Viewliquidaciones) liquidacion).getId()
						.getNumerofactura());
				oneRow.add(((Viewliquidaciones) liquidacion).getId()
						.getEjercicio());
				oneRow.add(((Viewliquidaciones) liquidacion).getId().getMes());
				SimpleDateFormat formato = new SimpleDateFormat(
						PreferencesUI.DateFormat);
				String cadenaFecha = "";
				if (((Viewliquidaciones) liquidacion).getId().getFecha() != null)
					cadenaFecha = formato
							.format(((Viewliquidaciones) liquidacion).getId()
									.getFecha());
				else
					cadenaFecha = "";
				oneRow.add(cadenaFecha);
				oneRow.add(((Viewliquidaciones) liquidacion).getId()
						.getNombre());
				oneRow.add(((Viewliquidaciones) liquidacion).getId()
						.getApellidos());
				if (((Viewliquidaciones) liquidacion).getId().getTipoIrpf() != null)
					oneRow.add(((Viewliquidaciones) liquidacion).getId()
							.getTipoIrpf());
				else
					oneRow.add("");
				if (((Viewliquidaciones) liquidacion).getId().getTipoIgic() != null)
					oneRow.add(((Viewliquidaciones) liquidacion).getId()
							.getTipoIgic());
				else
					oneRow.add("");
				NumberFormat formatter = new DecimalFormat("#,##0.00");
				float baseImponible = ((Viewliquidaciones) liquidacion).getId()
						.getBaseImponible();
				String sbaseImponible = formatter.format(baseImponible);
				oneRow.add(sbaseImponible);
				break;
			}
			case EntityType.indexTiposGasto: {
				Tiposgasto tipogasto = (Tiposgasto) o;
				oneRow.add(((Tiposgasto) tipogasto).getId().getIdTipoGasto());
				oneRow.add(((Tiposgasto) tipogasto).getDescTipoGasto());
				break;
			}
			}
			return oneRow;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntityView.FillData()",
					he);
			return null;
		}
	}

	private void loadRecord(int fila) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);

			int index = entitytype.getindex();

			switch (index) {

			case EntityType.indexBancos: {
				Bancos banco = new Bancos();
				BancosId bancoId = new BancosId();
				bancoId.setEmpresas(session.getEmpresa());
				bancoId.setEjercicios(session.getEjercicio());
				bancoId.setIdBanco(tblResult.getValueAt(fila, 0).toString());
				bancoId.setIdSucursal(tblResult.getValueAt(fila, 1).toString());
				banco = entity.BancoFindById(fDetail, bancoId);
				((FrmBanco) fDetail).loadData(banco);
				break;
			}

			case EntityType.indexBarcos: {
				Barcos barco = new Barcos();
				BarcosId barcoId = new BarcosId();
				barcoId.setEmpresas(session.getEmpresa());
				barcoId.setEjercicios(session.getEjercicio());
				barcoId.setIdBarco((Integer) tblResult.getValueAt(fila, 0));
				barco = entity.BarcoFindById(fDetail, barcoId);
				((FrmBarco) fDetail).loadData(barco);
				break;
			}

			case EntityType.indexCalendario: {
				Calendario calendario = new Calendario();
				CalendarioId calendarioId = new CalendarioId();
				calendarioId.setEmpresas(session.getEmpresa());
				calendarioId.setEjercicios(session.getEjercicio());
				calendarioId.setSemana((Integer) tblResult.getValueAt(fila, 1));
				calendario = entity.CalendarioFindById(fDetail, calendarioId);
				((FrmSemana) fDetail).loadData(calendario);
				break;
			}

			case EntityType.indexBimestres: {
				Bimestres bimestre = new Bimestres();
				BimestresId bimestreId = new BimestresId();
				bimestreId.setEmpresas(session.getEmpresa());
				bimestreId.setEjercicios(session.getEjercicio());
				bimestreId.setBimestre((Integer) tblResult.getValueAt(fila, 1));
				bimestre = entity.BimestreFindById(fDetail, bimestreId);
				((FrmBimestre) fDetail).loadData(bimestre);
				break;
			}

			case EntityType.indexCategorias: {
				Categorias categoria = new Categorias();
				CategoriasId categoriaId = new CategoriasId();
				categoriaId.setEmpresas(session.getEmpresa());
				categoriaId.setEjercicios(session.getEjercicio());
				categoriaId.setIdCategoria((Integer) tblResult.getValueAt(fila,
						0));
				categoria = entity.CategoriaFindById(fDetail, categoriaId);
				((FrmCategoria) fDetail).loadData(categoria);
				break;
			}
			
			case EntityType.indexConceptos: {
				Conceptos concepto = new Conceptos();
				ConceptosId conceptoId = new ConceptosId();
				conceptoId.setEmpresas(session.getEmpresa());
				conceptoId.setEjercicios(session.getEjercicio());
				conceptoId.setConcepto((Integer) tblResult.getValueAt(fila,
						0));
				concepto = entity.ConceptoFindById(fDetail, conceptoId);
				((FrmConcepto) fDetail).loadData(concepto);
				break;
			}

			case EntityType.indexConductores: {
				Conductores conductor = new Conductores();
				ConductoresId conductorId = new ConductoresId();
				conductorId.setEmpresas(session.getEmpresa());
				conductorId.setEjercicios(session.getEjercicio());
				conductorId.setIdConductor((Integer) tblResult.getValueAt(fila,
						0));
				conductor = entity.ConductorFindById(fDetail, conductorId);
				((FrmConductor) fDetail).loadData(conductor);
				break;
			}

			case EntityType.indexCosecheros: {
				Cosecheros cosechero = new Cosecheros();
				CosecherosId cosecheroId = new CosecherosId();
				cosecheroId.setEmpresas(session.getEmpresa());
				cosecheroId.setEjercicios(session.getEjercicio());
				cosecheroId.setIdCosechero((Integer) tblResult.getValueAt(fila,
						0));
				cosechero = entity.CosecheroFindById(fDetail, cosecheroId);
				((FrmCosechero) fDetail).loadData(cosechero);
				break;
			}

			case EntityType.indexEmpresas: {
				Empresas empresa = new Empresas();
				Integer IdValue = (Integer) tblResult.getValueAt(fila, 0);
				empresa = entity.EmpresaFindById(fDetail, IdValue);
				((FrmEmpresa) fDetail).loadData(empresa);
				break;
			}

			case EntityType.indexEjercicios: {
				Ejercicios ejercicio = new Ejercicios();
				Integer IdValue = (Integer) tblResult.getValueAt(fila, 0);
				ejercicio = entity.EjercicioFindById(fDetail, IdValue);
				((FrmEjercicio) fDetail).loadData(ejercicio);
				break;
			}

			case EntityType.indexReceptores: {
				Receptores receptor = new Receptores();
				ReceptoresId receptorid = new ReceptoresId();
				receptorid.setEmpresas(session.getEmpresa());
				receptorid.setEjercicios(session.getEjercicio());
				receptorid.setIdReceptor((Integer) tblResult
						.getValueAt(fila, 0));
				receptor = entity.ReceptorFindById(fDetail, receptorid);
				((FrmReceptor) fDetail).loadData(receptor);
				break;
			}

			case EntityType.indexZonas: {
				Zonas zona = new Zonas();
				ZonasId zonaid = new ZonasId();
				zonaid.setEmpresas(session.getEmpresa());
				zonaid.setEjercicios(session.getEjercicio());
				zonaid.setIdZona((Integer) tblResult.getValueAt(fila, 0));
				zona = entity.ZonaFindById(fDetail, zonaid);
				((FrmZona) fDetail).loadData(zona);
				break;
			}

			case EntityType.indexVehiculos: {
				Vehiculos vehiculo = new Vehiculos();
				VehiculosId vehiculoid = new VehiculosId();
				vehiculoid.setEmpresas(session.getEmpresa());
				vehiculoid.setIdVehiculo((Integer) tblResult
						.getValueAt(fila, 0));
				vehiculo = entity.VehiculoFindById(fDetail, vehiculoid);
				((FrmVehiculo) fDetail).loadData(vehiculo);
				break;
			}
			case EntityType.indexEntradas: {
				Entradascabecera entrada = new Entradascabecera();
				EntradascabeceraId entradascabeceraid = new EntradascabeceraId();
				entradascabeceraid.setEjercicios(session.getEjercicio());
				entradascabeceraid.setEmpresas(session.getEmpresa());
				entradascabeceraid.setIdEntrada((Integer) tblResult.getValueAt(
						fila, 0));
				entrada = entity.EntradascabeceraFindById(this,
						entradascabeceraid);
				((FrmEntrada) fDetail).loadData(entrada);
				break;
			}

			case EntityType.indexVentas: {
				Ventascabecera venta = new Ventascabecera();
				VentascabeceraId ventacabeceraid = new VentascabeceraId();
				ventacabeceraid.setEjercicios(session.getEjercicio());
				ventacabeceraid.setEmpresas(session.getEmpresa());
				ventacabeceraid.setIdVenta((Integer) tblResult.getValueAt(fila,
						0));
				venta = entity.VentascabeceraFindById(fDetail, ventacabeceraid);
				((FrmVenta) fDetail).loadData(venta);
				break;
			}

			case EntityType.indexIdentidades: {
				Identidades identidad = new Identidades();
				IdentidadesId identidadid = new IdentidadesId();
				identidadid.setEmpresas(session.getEmpresa());
				identidadid.setEjercicios(session.getEjercicio());
				identidadid.setIdentidad((Integer) tblResult
						.getValueAt(fila, 0));
				identidad = entity.IdentidadFindById(fDetail, identidadid);
				((FrmIdentidad) fDetail).loadData(identidad);
				break;
			}
			case EntityType.indexEmpleados: {
				Empleados empleado = new Empleados();
				EmpleadosId empleadoId = new EmpleadosId();
				empleadoId.setEmpresas(session.getEmpresa());
				empleadoId.setEjercicios(session.getEjercicio());
				empleadoId.setIdEmpleado((Integer) tblResult
						.getValueAt(fila, 0));
				empleado = entity.EmpleadoFindById(fDetail, empleadoId);
				((FrmEmpleado) fDetail).loadData(empleado);
				break;
			}
			case EntityType.indexFacturas: {
				Facturascabecera factura = new Facturascabecera();
				FacturascabeceraId facturaid = new FacturascabeceraId();
				facturaid.setEjercicios(session.getEjercicio());
				facturaid.setEmpresas(session.getEmpresa());
				facturaid.setIdFactura((Integer) tblResult.getValueAt(fila, 0));
				factura = entity.FacturascabeceraFindById(fDetail, facturaid);
				((FrmFactura) fDetail).loadData(factura);
				break;
			}
			case EntityType.indexFacturasPago: {
				Facturaspagocabecera facturapago = new Facturaspagocabecera();
				FacturaspagocabeceraId facturapagoid = new FacturaspagocabeceraId();
				facturapagoid.setEjercicios(session.getEjercicio());
				facturapagoid.setEmpresas(session.getEmpresa());
				facturapagoid.setIdFactura((Integer) tblResult.getValueAt(fila,
						0));
				facturapago = entity.FacturaspagocabeceraFindById(fDetail,
						facturapagoid);
				((FrmFacturaPago) fDetail).loadData(facturapago);
				break;
			}
			case EntityType.indexTiposGasto: {
				Tiposgasto tipogasto = new Tiposgasto();
				TiposgastoId tipogastoId = new TiposgastoId();
				tipogastoId.setEmpresas(session.getEmpresa());
				tipogastoId.setEjercicios(session.getEjercicio());
				tipogastoId.setIdTipoGasto((Integer) tblResult.getValueAt(fila, 0));
				tipogasto = entity.TipoGastoFindById(fDetail, tipogastoId);
				((FrmTipoGasto) fDetail).loadData(tipogasto);
				break;
			}
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntityView.loadRecord()",
					he);
		}
	}

	private boolean saveRecord() {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);

			int index = entitytype.getindex();

			switch (index) {

			case EntityType.indexBancos: {
				return ((FrmBanco) fDetail).saveData(false);
			}

			case EntityType.indexBarcos: {
				return ((FrmBarco) fDetail).saveData(false);
			}

			case EntityType.indexCalendario: {
				return ((FrmSemana) fDetail).saveData(false);
			}

			case EntityType.indexBimestres: {
				return ((FrmBimestre) fDetail).saveData(false);
			}

			case EntityType.indexCategorias: {
				return ((FrmCategoria) fDetail).saveData(false);
			}
			
			case EntityType.indexConceptos: {
				return ((FrmConcepto) fDetail).saveData(false);
			}

			case EntityType.indexConductores: {
				return ((FrmConductor) fDetail).saveData(false);
			}

			case EntityType.indexCosecheros: {
				return ((FrmCosechero) fDetail).saveData(false);
			}

			case EntityType.indexEmpresas: {
				return ((FrmEmpresa) fDetail).saveData(false);
			}

			case EntityType.indexEjercicios: {
				return ((FrmEjercicio) fDetail).saveData(false);
			}

			case EntityType.indexReceptores: {
				return ((FrmReceptor) fDetail).saveData(false);
			}

			case EntityType.indexZonas: {
				return ((FrmZona) fDetail).saveData(false);
			}

			case EntityType.indexVehiculos: {
				return ((FrmVehiculo) fDetail).saveData(false);
			}
			case EntityType.indexEntradas: {
				return ((FrmEntrada) fDetail).saveData(false);
			}

			case EntityType.indexVentas: {
				return ((FrmVenta) fDetail).saveData(false);
			}

			case EntityType.indexIdentidades: {
				return ((FrmIdentidad) fDetail).saveData(false);
			}
			case EntityType.indexEmpleados: {
				return ((FrmEmpleado) fDetail).saveData(false);
			}
			case EntityType.indexFacturas: {
				return ((FrmFactura) fDetail).saveData(false);
			}
			case EntityType.indexFacturasPago: {
				return ((FrmFacturaPago) fDetail).saveData(false);
			}
			case EntityType.indexTiposGasto: {
				return ((FrmTipoGasto) fDetail).saveData(false);
			}
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
			return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntityView.saveRecord()",
					he);
			return false;
		} catch (ParseException e) {
			Message.ShowParseError(parentFrame,
					"FrmEntityView.saveRecord()", e);
			return false;
		}
	}

	private void printRecord(int fila) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);

			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.printRecord()", he);
		}
	}

	private void deleteRecord(int fila) {

		try {
			Object[] botones = { "Si", "No" };
			int respuestavalue = JOptionPane.showOptionDialog(null,
					"Se va a eliminar el registro seleccionado, ¿está seguro?",
					"", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE, null, botones, botones[0]);

			if (respuestavalue == 0) {

				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);

				int index = entitytype.getindex();

				switch (index) {

				case EntityType.indexBancos: {
					Bancos banco = new Bancos();
					BancosId bancoId = new BancosId();
					bancoId.setEmpresas(session.getEmpresa());
					bancoId.setEjercicios(session.getEjercicio());
					bancoId
							.setIdBanco(tblResult.getValueAt(fila, 0)
									.toString());
					bancoId.setIdSucursal(tblResult.getValueAt(fila, 1)
							.toString());
					banco = entity.BancoFindById(this, bancoId);
					entity.BancoDelete(this, banco);
					FrmBancos.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexBarcos: {
					Barcos barco = new Barcos();
					BarcosId barcoId = new BarcosId();
					barcoId.setEmpresas(session.getEmpresa());
					barcoId.setEjercicios(session.getEjercicio());
					barcoId.setIdBarco((Integer) tblResult.getValueAt(fila, 0));
					barco = entity.BarcoFindById(this, barcoId);
					entity.BarcoDelete(this, barco);
					FrmBarcos.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexCalendario: {
					Calendario calendario = new Calendario();
					CalendarioId calendarioId = new CalendarioId();
					calendarioId.setEmpresas(session.getEmpresa());
					calendarioId.setEjercicios(session.getEjercicio());
					calendarioId.setSemana((Integer) tblResult.getValueAt(fila,
							1));
					calendario = entity.CalendarioFindById(this, calendarioId);
					entity.CalendarioDelete(this, calendario);
					FrmCalendario.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexBimestres: {
					Bimestres bimestre = new Bimestres();
					BimestresId bimestreId = new BimestresId();
					bimestreId.setEmpresas(session.getEmpresa());
					bimestreId.setEjercicios(session.getEjercicio());
					bimestreId.setBimestre((Integer) tblResult.getValueAt(fila,
							1));
					bimestre = entity.BimestreFindById(this, bimestreId);
					entity.BimestreDelete(this, bimestre);
					FrmBimestres.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexCategorias: {
					Categorias categoria = new Categorias();
					CategoriasId categoriaId = new CategoriasId();
					categoriaId.setEmpresas(session.getEmpresa());
					categoriaId.setEjercicios(session.getEjercicio());
					categoriaId.setIdCategoria((Integer) tblResult.getValueAt(
							fila, 0));
					categoria = entity.CategoriaFindById(this, categoriaId);
					entity.CategoriaDelete(this, categoria);
					FrmCategorias.runSearchQuery();
					newRecord();
					break;
				}
				
				case EntityType.indexConceptos: {
					Conceptos concepto = new Conceptos();
					ConceptosId conceptoId = new ConceptosId();
					conceptoId.setEmpresas(session.getEmpresa());
					conceptoId.setEjercicios(session.getEjercicio());
					conceptoId.setConcepto((Integer) tblResult.getValueAt(
							fila, 0));
					concepto = entity.ConceptoFindById(this, conceptoId);
					entity.ConceptoDelete(this, concepto);
					FrmConceptos.runSearchQuery();
					newRecord();
					break;
				}


				case EntityType.indexConductores: {
					Conductores conductor = new Conductores();
					ConductoresId conductorId = new ConductoresId();
					conductorId.setEmpresas(session.getEmpresa());
					conductorId.setEjercicios(session.getEjercicio());
					conductorId.setIdConductor((Integer) tblResult.getValueAt(
							fila, 0));
					conductor = entity.ConductorFindById(this, conductorId);
					entity.ConductorDelete(this, conductor);
					FrmConductores.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexCosecheros: {
					Cosecheros cosechero = new Cosecheros();
					CosecherosId cosecheroId = new CosecherosId();
					cosecheroId.setEmpresas(session.getEmpresa());
					cosecheroId.setEjercicios(session.getEjercicio());
					cosecheroId.setIdCosechero((Integer) tblResult.getValueAt(
							fila, 0));
					cosechero = entity.CosecheroFindById(this, cosecheroId);
					entity.CosecheroDelete(this, cosechero);
					FrmCosecheros.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexEmpresas: {
					Empresas empresa = new Empresas();
					Integer IdValue = (Integer) tblResult.getValueAt(fila, 0);
					empresa = entity.EmpresaFindById(this, IdValue);
					entity.EmpresaDelete(this, empresa);
					FrmEmpresas.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexEjercicios: {
					Ejercicios ejercicio = new Ejercicios();
					Integer IdValue = (Integer) tblResult.getValueAt(fila, 0);
					ejercicio = entity.EjercicioFindById(this, IdValue);
					entity.EjercicioDelete(this, ejercicio);
					FrmEmpresas.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexReceptores: {
					Receptores receptor = new Receptores();
					ReceptoresId receptorid = new ReceptoresId();
					receptorid.setEmpresas(session.getEmpresa());
					receptorid.setEjercicios(session.getEjercicio());
					receptorid.setIdReceptor((Integer) tblResult.getValueAt(
							fila, 0));
					receptor = entity.ReceptorFindById(this, receptorid);
					entity.ReceptorDelete(this, receptor);
					FrmReceptores.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexZonas: {
					Zonas zona = new Zonas();
					ZonasId zonaid = new ZonasId();
					zonaid.setEmpresas(session.getEmpresa());
					zonaid.setEjercicios(session.getEjercicio());
					zonaid.setIdZona((Integer) tblResult.getValueAt(fila, 0));
					zona = entity.ZonaFindById(this, zonaid);
					entity.ZonaDelete(this, zona);
					FrmZonas.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexVehiculos: {
					Vehiculos vehiculo = new Vehiculos();
					VehiculosId vehiculoid = new VehiculosId();
					vehiculoid.setEmpresas(session.getEmpresa());
					vehiculoid.setEjercicios(session.getEjercicio());
					vehiculoid.setIdVehiculo((Integer) tblResult.getValueAt(
							fila, 0));
					vehiculo = entity.VehiculoFindById(this, vehiculoid);
					entity.VehiculoDelete(this, vehiculo);
					FrmVehiculos.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexEntradas: {
					Entradascabecera entrada = new Entradascabecera();
					EntradascabeceraId entradascabeceraid = new EntradascabeceraId();
					entradascabeceraid.setEjercicios(session.getEjercicio());
					entradascabeceraid.setEmpresas(session.getEmpresa());
					entradascabeceraid.setIdEntrada((Integer) tblResult
							.getValueAt(fila, 0));
					entrada = entity.EntradascabeceraFindById(this,
							entradascabeceraid);
					entity.EntradaDelete(this, entrada);
					FrmEntradas.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexVentas: {
					Ventascabecera venta = new Ventascabecera();
					VentascabeceraId ventacabeceraid = new VentascabeceraId();
					ventacabeceraid.setEjercicios(session.getEjercicio());
					ventacabeceraid.setEmpresas(session.getEmpresa());
					ventacabeceraid.setIdVenta((Integer) tblResult.getValueAt(
							fila, 0));
					venta = entity
							.VentascabeceraFindById(this, ventacabeceraid);
					entity.VentaDelete(this, venta);
					FrmVentas.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexIdentidades: {
					Identidades identidad = new Identidades();
					IdentidadesId identidadid = new IdentidadesId();
					identidadid.setEmpresas(session.getEmpresa());
					identidadid.setEjercicios(session.getEjercicio());
					identidadid.setIdentidad((Integer) tblResult.getValueAt(
							fila, 0));
					identidad = entity.IdentidadFindById(this, identidadid);
					entity.IdentidadDelete(this, identidad);
					FrmIdentidades.runSearchQuery();
					newRecord();
					break;
				}

				case EntityType.indexEmpleados: {
					Empleados empleado = new Empleados();
                                        List<?> empleadosnomina;
					EmpleadosId empleadoId = new EmpleadosId();
					empleadoId.setEmpresas(session.getEmpresa());
					empleadoId.setEjercicios(session.getEjercicio());
					empleadoId.setIdEmpleado((Integer) tblResult.getValueAt(
							fila, 0));
					empleado = entity.EmpleadoFindById(this, empleadoId);
                                        empleadosnomina = entity.EmpleadosNominasFindByEmpleado(this, empleado.getId().getIdEmpleado());
                                        if (empleadosnomina.isEmpty()) {
                                            entity.EmpleadoDelete(this, empleado);
                                            FrmEmpleados.runSearchQuery();
                                            newRecord();
                                        }
                                        else {
                                            Message.ShowValidateMessage(fDetail, "No se puede eliminar el empleado porque tiene nóminas");
                                        }
					break;
				}
				case EntityType.indexFacturas: {
					Facturascabecera factura = new Facturascabecera();
					FacturascabeceraId facturaid = new FacturascabeceraId();
					facturaid.setEjercicios(session.getEjercicio());
					facturaid.setEmpresas(session.getEmpresa());
					facturaid.setIdFactura((Integer) tblResult.getValueAt(fila,
							0));
					factura = entity.FacturascabeceraFindById(this, facturaid);
					entity.FacturaDelete(this, factura);
					FrmFacturas.runSearchQuery();
					newRecord();
					break;
				}
				case EntityType.indexFacturasPago: {
					Facturaspagocabecera facturapago = new Facturaspagocabecera();
					FacturaspagocabeceraId facturapagoid = new FacturaspagocabeceraId();
					facturapagoid.setEjercicios(session.getEjercicio());
					facturapagoid.setEmpresas(session.getEmpresa());
					facturapagoid.setIdFactura((Integer) tblResult.getValueAt(
							fila, 0));
					facturapago = entity.FacturaspagocabeceraFindById(this,
							facturapagoid);
					entity.FacturapagoDelete(this, facturapago);
					FrmFacturasPago.runSearchQuery();
					newRecord();
					break;
				}
				case EntityType.indexTiposGasto: {
					Tiposgasto tipogasto = new Tiposgasto();
					TiposgastoId tipogastoId = new TiposgastoId();
					tipogastoId.setEmpresas(session.getEmpresa());
					tipogastoId.setEjercicios(session.getEjercicio());
					tipogastoId.setIdTipoGasto((Integer) tblResult.getValueAt(fila, 0));
					tipogasto = entity.TipoGastoFindById(this, tipogastoId);
					entity.TipoGastoDelete(this, tipogasto);
					FrmTiposGasto.runSearchQuery();
					newRecord();
					break;
				}
				}
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.deleteRecord()", he);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		lblTitle = new javax.swing.JLabel();
		pnlButtoms = new javax.swing.JPanel();
		btnAdd = new javax.swing.JButton();
		btnEdit = new javax.swing.JButton();
		btnDelete = new javax.swing.JButton();
		btnPrint = new javax.swing.JButton();
		tabEntity = new javax.swing.JTabbedPane();
		pnlView = new javax.swing.JPanel();
		txtSearch = new javax.swing.JTextField();
		cmdSearch = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblResult = new javax.swing.JTable();
		cmdAnterior = new javax.swing.JButton();
		cmdSiguiente = new javax.swing.JButton();
		cmdClose = new javax.swing.JButton();

		lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
		lblTitle.setForeground(new java.awt.Color(0, 153, 0));
		lblTitle.setText("Title");

		pnlButtoms.setBorder(new javax.swing.border.SoftBevelBorder(
				javax.swing.border.BevelBorder.RAISED));

		btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/New.png"))); // NOI18N
		btnAdd.setToolTipText("A\u00f1adir");
		btnAdd.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnAddMousePressed(evt);
			}
		});

		btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/Edit.png"))); // NOI18N
		btnEdit.setToolTipText("Editar");
		btnEdit.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnEditMousePressed(evt);
			}
		});

		btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/Delete.png"))); // NOI18N
		btnDelete.setToolTipText("Borrar");
		btnDelete.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnDeleteMousePressed(evt);
			}
		});

		btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/Print.png"))); // NOI18N
		btnPrint.setToolTipText("Imprimir");
		btnPrint.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnPrintMousePressed(evt);
			}
		});

		javax.swing.GroupLayout pnlButtomsLayout = new javax.swing.GroupLayout(
				pnlButtoms);
		pnlButtoms.setLayout(pnlButtomsLayout);
		pnlButtomsLayout
				.setHorizontalGroup(pnlButtomsLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlButtomsLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlButtomsLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																btnDelete,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																81,
																Short.MAX_VALUE)
														.addComponent(
																btnEdit,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																81,
																Short.MAX_VALUE)
														.addComponent(
																btnAdd,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																81,
																Short.MAX_VALUE)
														.addComponent(
																btnPrint,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																81,
																Short.MAX_VALUE))
										.addContainerGap()));
		pnlButtomsLayout
				.setVerticalGroup(pnlButtomsLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlButtomsLayout
										.createSequentialGroup()
										.addGap(31, 31, 31)
										.addComponent(
												btnAdd,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												66,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btnEdit,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												68,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btnDelete,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												69,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												268, Short.MAX_VALUE)
										.addComponent(
												btnPrint,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												69,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		tabEntity.setFont(new java.awt.Font("Segoe UI", 0, 14));

		pnlView.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txtSearchKeyPressed(evt);
			}
		});

		cmdSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdSearch.setText("Buscar");
		cmdSearch.setToolTipText("Buscar");
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
		tblResult.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblResultMousePressed(evt);
			}
		});
		jScrollPane1.setViewportView(tblResult);

		javax.swing.GroupLayout pnlViewLayout = new javax.swing.GroupLayout(
				pnlView);
		pnlView.setLayout(pnlViewLayout);
		pnlViewLayout
				.setHorizontalGroup(pnlViewLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlViewLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlViewLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																489,
																Short.MAX_VALUE)
														.addGroup(
																pnlViewLayout
																		.createSequentialGroup()
																		.addComponent(
																				txtSearch,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				387,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				cmdSearch,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		pnlViewLayout
				.setVerticalGroup(pnlViewLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlViewLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlViewLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(cmdSearch)
														.addComponent(
																txtSearch,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												474, Short.MAX_VALUE)
										.addContainerGap()));

		tabEntity.addTab("Vista", pnlView);

		cmdAnterior.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdAnterior.setText("<< Anterior");
		cmdAnterior.setToolTipText("Navega al registro anterior");
		cmdAnterior.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdAnterior.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		cmdAnterior.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdAnteriorMousePressed(evt);
			}
		});

		cmdSiguiente.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdSiguiente.setText("Siguiente >>");
		cmdSiguiente.setToolTipText("Navega al siguiente registro");
		cmdSiguiente.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdSiguiente.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
		cmdSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdSiguienteMousePressed(evt);
			}
		});

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
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												pnlButtoms,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																layout
																		.createSequentialGroup()
																		.addComponent(
																				lblTitle,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				204,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cmdAnterior,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				98,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cmdSiguiente,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				95,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				cmdClose,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				17,
																				17,
																				17))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				tabEntity,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				522,
																				Short.MAX_VALUE)
																		.addContainerGap()))));
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
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																pnlButtoms,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																layout
																		.createSequentialGroup()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								cmdAnterior)
																						.addComponent(
																								cmdSiguiente)
																						.addComponent(
																								cmdClose))
																		.addGap(
																				7,
																				7,
																				7)
																		.addComponent(
																				tabEntity,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				571,
																				Short.MAX_VALUE)
																		.addGap(
																				1,
																				1,
																				1))
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																layout
																		.createSequentialGroup()
																		.addGap(
																				4,
																				4,
																				4)
																		.addComponent(
																				lblTitle)
																		.addGap(
																				517,
																				517,
																				517)))
										.addGap(17, 17, 17)));

		tabEntity.getAccessibleContext().setAccessibleName("tabEntity");

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btnPrintMousePressed(java.awt.event.MouseEvent evt) {
		try {
			if (tblResult.getSelectedRow() != -1) {
				printRecord(tblResult.getSelectedRow());
			} else {
				Object[] options = { "Aceptar" };
				JOptionPane.showOptionDialog(SwingUtilities
						.getWindowAncestor(this),
						"Debe seleccionar un registro.", "",
						JOptionPane.INFORMATION_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE, null, options,
						options[0]);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.btnPrintMousePressed()", he);
		}
	}

	private void cmdSiguienteMousePressed(java.awt.event.MouseEvent evt) {
		try {
			cmdAnterior.setEnabled(false);
			cmdSiguiente.setEnabled(false);
			ListSelectionModel selectionModel = tblResult.getSelectionModel();

			if (tblResult.getSelectedRow() == -1) {
				selectionModel.setSelectionInterval(0, 0);
			} else {
				if (tblResult.getSelectedRow() != tblResult.getRowCount() - 1) {
					if (tabEntity.getSelectedIndex() == 1) {
						if (saveRecord()) {
							selectionModel.setSelectionInterval(tblResult
									.getSelectedRow() + 1, tblResult
									.getSelectedRow() + 1);
							Cursor hourglassCursor = new Cursor(
									Cursor.WAIT_CURSOR);
							setCursor(hourglassCursor);
							loadRecord(tblResult.getSelectedRow());
							Cursor normalCursor = new Cursor(
									Cursor.DEFAULT_CURSOR);
							setCursor(normalCursor);
						}
					} else
						selectionModel.setSelectionInterval(tblResult
								.getSelectedRow() + 1, tblResult
								.getSelectedRow() + 1);
				}
			}
			cmdAnterior.setEnabled(true);
			cmdSiguiente.setEnabled(true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.cmdSiguienteMousePressed()", he);
			cmdAnterior.setEnabled(true);
			cmdSiguiente.setEnabled(true);
		}
	}

	private void cmdAnteriorMousePressed(java.awt.event.MouseEvent evt) {
		try {
			cmdAnterior.setEnabled(false);
			cmdSiguiente.setEnabled(false);
			ListSelectionModel selectionModel = tblResult.getSelectionModel();

			if (tblResult.getSelectedRow() == -1) {
				selectionModel.setSelectionInterval(0, 0);
			} else {
				if (tblResult.getSelectedRow() != 0) {
					if (tabEntity.getSelectedIndex() == 1) {
						if (saveRecord()) {
							selectionModel.setSelectionInterval(tblResult
									.getSelectedRow() - 1, tblResult
									.getSelectedRow() - 1);
							Cursor hourglassCursor = new Cursor(
									Cursor.WAIT_CURSOR);
							setCursor(hourglassCursor);
							loadRecord(tblResult.getSelectedRow());
							Cursor normalCursor = new Cursor(
									Cursor.DEFAULT_CURSOR);
							setCursor(normalCursor);
						}
					} else
						selectionModel.setSelectionInterval(tblResult
								.getSelectedRow() - 1, tblResult
								.getSelectedRow() - 1);
				}
			}
			cmdAnterior.setEnabled(true);
			cmdSiguiente.setEnabled(true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.cmdAnteriorMousePressed()", he);
			cmdAnterior.setEnabled(true);
			cmdSiguiente.setEnabled(true);
		}
	}

	private void btnDeleteMousePressed(java.awt.event.MouseEvent evt) {
		try {

			if (tblResult.getSelectedRow() != -1) {
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				deleteRecord(tblResult.getSelectedRow());
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			} else {
				Object[] options = { "Aceptar" };
				JOptionPane.showOptionDialog(SwingUtilities
						.getWindowAncestor(this),
						"Debe seleccionar un registro.", "",
						JOptionPane.INFORMATION_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE, null, options,
						options[0]);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.btnDeleteMousePressed()", he);
		}
	}

	private void btnEditMousePressed(java.awt.event.MouseEvent evt) {
		try {
			if (tblResult.getSelectedRow() != -1) {
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				loadRecord(tblResult.getSelectedRow());
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
				tabEntity.setSelectedIndex(1);
			} else {
				Object[] options = { "Aceptar" };
				JOptionPane.showOptionDialog(SwingUtilities
						.getWindowAncestor(this),
						"Debe seleccionar un registro.", "",
						JOptionPane.INFORMATION_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE, null, options,
						options[0]);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.btnEditMousePressed()", he);
		}
	}

	private void btnAddMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			newRecord();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
			tabEntity.setSelectedIndex(1);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.btnAddMousePressed()", he);
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

	private void cmdSearchMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			runSearchQuery();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.cmdSearchMousePressed()", he);
		}
	}

	private void tblResultMousePressed(java.awt.event.MouseEvent evt) {
		try {
			if (evt.getClickCount() == 2 && editable) {
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				loadRecord(tblResult.getSelectedRow());
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
				tabEntity.setSelectedIndex(1);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityView.tblResultMousePressed()", he);
		}
	}

	private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {
		try {
			if (evt.getKeyCode() == PreferencesUI.KeyReturn) {
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				runSearchQuery();
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntityViewtxtSearchKeyPressed()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnAdd;
	private javax.swing.JButton btnDelete;
	private javax.swing.JButton btnEdit;
	private javax.swing.JButton btnPrint;
	private javax.swing.JButton cmdAnterior;
	private javax.swing.JButton cmdClose;
	private javax.swing.JButton cmdSearch;
	private javax.swing.JButton cmdSiguiente;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblTitle;
	private javax.swing.JPanel pnlButtoms;
	private javax.swing.JPanel pnlView;
	private javax.swing.JTabbedPane tabEntity;
	private static javax.swing.JTable tblResult;
	private static javax.swing.JTextField txtSearch;
	// End of variables declaration//GEN-END:variables

}