/*
 * FrmBanco.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.AWTKeyStroke;
import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Transaction;

import sessionPackage.MySession;

import entitiesPackage.Bancos;
import entitiesPackage.Empleados;
import entitiesPackage.EmpleadosId;
import entitiesPackage.Empleadoscontratos;
import entitiesPackage.EmpleadoscontratosId;
import entitiesPackage.Empleadoshorasextras;
import entitiesPackage.EmpleadoshorasextrasId;
import entitiesPackage.Empleadosnominas;
import entitiesPackage.EmpleadosnominasId;
import entitiesPackage.Empleadosvacaciones;
import entitiesPackage.EmpleadosvacacionesId;
import entitiesPackage.Empresascuentas;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmEmpleado extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	public static class ContratosTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		static ImageIcon Adding = null;
		static ImageIcon Editing = null;

		final public static int NormalType = 0;
		final public static int Float2Type = 1;
		final public static int Float4Type = 2;
		final public static int ComboType = 3;
		final public static int CheckType = 4;
		final public static int StateType = 5;
		final public static int DateType = 6;

		final public static String NewLine = "[Adding]";
		final public static String EditLine = "[Editing]";
		final public static String DeleteLine = "[Deleting]";

		final public static int columnSelect = 0;
		final public static int columnState = 1;
		final public static int columnFechaAlta = 2;
		final public static int columnFechaBaja = 3;
		final public static int columnDuracion = 4;
		final public static int columnImporteLiquidacion = 5;
		final public static int columnImporteIrpf = 6;
		final public static int columnCuentaBancariaPago = 7;
		final public static int columnComentarios = 8;

		static String[] columnNames = { "Select", "State", "FechaAlta",
				"FechaBaja", "Duracion", "ImporteLiquidacion", "ImporteIrpf",
				"CuentaBancariaPago", "Comentarios" };

		class ColumnData {
			public String m_name;
			public String m_title;
			public int m_width;
			public int m_alignment;
			public int m_editortype;
			public Object m_editor;
			public String m_format;

			public ColumnData(String name, String title, int width,
					int alignment, int editortype, Object editor, String format) {
				m_name = name;
				m_title = title;
				m_width = width;
				m_alignment = alignment;
				m_editortype = editortype;
				m_editor = editor;
				m_format = format;
			}
		}

		private Vector<ColumnData> columns;

		public ContratosTableModel() {

			super(null, columnNames);
			Adding = new ImageIcon(getClass().getResource(
					"/imagesPackage/addline.png"));
			Editing = new ImageIcon(getClass().getResource(
					"/imagesPackage/editline.png"));

			columns = new Vector<ColumnData>();
			columns.add(new ColumnData("Select", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					CheckType, null, null));
			columns.add(new ColumnData("State", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					StateType, null, null));
			columns.add(new ColumnData("FechaAlta", "Fecha de alta",
					EntityType.DateWidth, SwingConstants.RIGHT, DateType,
					txtFechaAlta, null));
			columns.add(new ColumnData("FechaBaja", "Fecha de baja",
					EntityType.DateWidth, SwingConstants.RIGHT, DateType,
					txtFechaBaja, null));
			columns.add(new ColumnData("Duracion", "Duraciï¿½n",
					EntityType.TextWidth, SwingConstants.LEFT, NormalType,
					null, null));
			columns.add(new ColumnData("ImporteLiquidacion", "liquidación",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImporteLiquidacion, "#,##0.00"));
			columns.add(new ColumnData("ImporteIrpf", "Irpf",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImporteLiquidacionIrpf, "#,##0.00"));
			columns.add(new ColumnData("CuentaBancariaPago", "Cuenta bancaria",
					EntityType.ComboWidth, SwingConstants.LEFT, ComboType,
					cboCuentaBancariaLiquidacion, null));
			columns.add(new ColumnData("Comentarios", "Comentarios",
					EntityType.TextWidth, SwingConstants.LEFT, NormalType,
					null, null));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (!changingContratoState && !addLine)
						changeContratosEditState();
				}
			});
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			switch (columna) {
			case columnFechaAlta: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals("")) {
						String value = super.getValueAt(fila, columna)
								.toString();
						return value;
					} else
						return "";
				} else
					return "";
			}
			case columnFechaBaja: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals("")) {
						String value = super.getValueAt(fila, columna)
								.toString();
						return value;
					} else
						return "";
				} else
					return "";
			}
			case columnDuracion: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals("")) {
						String value = super.getValueAt(fila, columna)
								.toString();
						return value;
					} else
						return "";
				}
				return "";
			}
			case columnImporteLiquidacion: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnImporteIrpf: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnCuentaBancariaPago: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals("")) {
						String value = super.getValueAt(fila, columna)
								.toString();
						return value;
					} else
						return "";
				}
				return "";
			}
			case columnComentarios: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals("")) {
						String value = super.getValueAt(fila, columna)
								.toString();
						return value;
					} else
						return "";
				}
				return "";
			}
			}
			return super.getValueAt(fila, columna);
		}
	}

	public static class NominasTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		static ImageIcon Adding = null;
		static ImageIcon Editing = null;

		final public static int NormalType = 0;
		final public static int Float2Type = 1;
		final public static int Float4Type = 2;
		final public static int ComboType = 3;
		final public static int CheckType = 4;
		final public static int StateType = 5;
		final public static int DateType = 6;

		final public static String NewLine = "[Adding]";
		final public static String EditLine = "[Editing]";
		final public static String DeleteLine = "[Deleting]";

		final public static int columnSelect = 0;
		final public static int columnState = 1;
		final public static int columnEjercicio = 2;
		final public static int columnMes = 3;
		final public static int columnTotalDevengado = 4;
		final public static int columnImporteIrpf = 5;
		final public static int columnImporteSegSoc = 6;
		final public static int columnImporteSegAutonomo = 7;
		final public static int columnImporteEmbargo = 8;
		final public static int columnTotalLiquido = 9;
		final public static int columnImporteBonificacion = 10;
		final public static int columnCuentaBancariaPago = 11;
		final public static int columnComentarios = 12;

		static String[] columnNames = { "Select", "State", "Ejercicio", "Mes",
				"TotalDevengado", "ImporteIrpf", "ImporteSegSoc",
				"ImporteSegAutonomo", "ImporteEmbargo", "TotalLiquido",
				"ImporteBonificacion", "CuentaBancariaPago", "Comentarios" };

		class ColumnData {
			public String m_name;
			public String m_title;
			public int m_width;
			public int m_alignment;
			public int m_editortype;
			public Object m_editor;
			public String m_format;

			public ColumnData(String name, String title, int width,
					int alignment, int editortype, Object editor, String format) {
				m_name = name;
				m_title = title;
				m_width = width;
				m_alignment = alignment;
				m_editortype = editortype;
				m_editor = editor;
				m_format = format;
			}
		}

		private Vector<ColumnData> columns;

		public NominasTableModel() {

			super(null, columnNames);
			Adding = new ImageIcon(getClass().getResource(
					"/imagesPackage/addline.png"));
			Editing = new ImageIcon(getClass().getResource(
					"/imagesPackage/editline.png"));

			columns = new Vector<ColumnData>();
			columns.add(new ColumnData("Select", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					CheckType, null, null));
			columns.add(new ColumnData("State", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					StateType, null, null));
			columns
					.add(new ColumnData("Ejercicio", "Aï¿½o",
							EntityType.NumberWidth, SwingConstants.RIGHT, 0,
							null, null));
			columns.add(new ColumnData("Mes", "Mes", EntityType.NumberWidth,
					SwingConstants.RIGHT, 0, null, null));
			columns.add(new ColumnData("TotalDevengado", "Total devengado",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtTotalDevengado, "#,##0.00"));
			columns.add(new ColumnData("ImporteIrpf", "IRPF",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImporteIrpf, "#,##0.00"));
			columns.add(new ColumnData("ImporteSegSoc", "Seguridad social",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImporteSegSoc, "#,##0.00"));
			columns.add(new ColumnData("ImporteSegAutonomo", "Autï¿½nomo",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImporteSegAutonomo, "#,##0.00"));
			columns.add(new ColumnData("ImporteEmbargo", "Embargo",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImporteEmbargo, "#,##0.00"));
			columns.add(new ColumnData("TotalLiquido", "Total lï¿½quido",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtTotalLiquido, "#,##0.00"));
			columns.add(new ColumnData("ImporteBonificacion", "Bonificaciï¿½n",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtBonificacion, "#,##0.00"));
			columns.add(new ColumnData("CuentaBancariaPago", "Cuenta bancaria",
					EntityType.ComboWidth, SwingConstants.LEFT, ComboType,
					cboCuentaBancaria, null));
			columns.add(new ColumnData("Comentarios", "Comentarios",
					EntityType.TextWidth, SwingConstants.LEFT, NormalType,
					null, null));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (!changingNominaState && !addLine)
						changeNominasEditState();
					if (e.getColumn() == columnTotalDevengado) {
						calculaTotalLiquido();
					}
					if (e.getColumn() == columnImporteSegSoc) {
						calculaTotalLiquido();
					}
					if (e.getColumn() == columnImporteSegAutonomo) {
						calculaTotalLiquido();
					}
					if (e.getColumn() == columnImporteEmbargo) {
						calculaTotalLiquido();
					}
					if (e.getColumn() == columnImporteIrpf) {
						calculaTotalLiquido();
					}
				}
			});
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			switch (columna) {

			case columnEjercicio: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString();
							return Integer.parseInt(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnMes: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString();
							return Integer.parseInt(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnTotalDevengado: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnImporteIrpf: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnImporteSegSoc: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnImporteSegAutonomo: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnImporteEmbargo: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnTotalLiquido: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnImporteBonificacion: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnCuentaBancariaPago: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals("")) {
						String value = super.getValueAt(fila, columna)
								.toString();
						return value;
					} else
						return "";
				}
				return "";
			}
			case columnComentarios: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals("")) {
						String value = super.getValueAt(fila, columna)
								.toString();
						return value;
					} else
						return "";
				}
				return "";
			}
			}
			return super.getValueAt(fila, columna);
		}

	}

	public static class HorasExtrasTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		static ImageIcon Adding = null;
		static ImageIcon Editing = null;

		final public static int NormalType = 0;
		final public static int Float2Type = 1;
		final public static int Float4Type = 2;
		final public static int ComboType = 3;
		final public static int CheckType = 4;
		final public static int StateType = 5;
		final public static int DateType = 6;

		final public static String NewLine = "[Adding]";
		final public static String EditLine = "[Editing]";
		final public static String DeleteLine = "[Deleting]";

		final public static int columnSelect = 0;
		final public static int columnState = 1;
		final public static int columnEjercicio = 2;
		final public static int columnSemana = 3;
		final public static int columnHorasExtras = 4;
		final public static int columnHorasDescontadas = 5;
		final public static int columnPrecio = 6;
		final public static int columnImporte = 7;
		final public static int columnSabado = 8;
		final public static int columnComentarios = 9;

		static String[] columnNames = { "Select", "State", "Ejercicio",
				"Semana", "HorasExtras", "HorasDescontadas", "Precio",
				"Importe", "Sabado", "Comentarios" };

		class ColumnData {
			public String m_name;
			public String m_title;
			public int m_width;
			public int m_alignment;
			public int m_editortype;
			public Object m_editor;
			public String m_format;

			public ColumnData(String name, String title, int width,
					int alignment, int editortype, Object editor, String format) {
				m_name = name;
				m_title = title;
				m_width = width;
				m_alignment = alignment;
				m_editortype = editortype;
				m_editor = editor;
				m_format = format;
			}
		}

		private Vector<ColumnData> columns;

		public HorasExtrasTableModel() {

			super(null, columnNames);
			Adding = new ImageIcon(getClass().getResource(
					"/imagesPackage/addline.png"));
			Editing = new ImageIcon(getClass().getResource(
					"/imagesPackage/editline.png"));

			columns = new Vector<ColumnData>();
			columns.add(new ColumnData("Select", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					CheckType, null, null));
			columns.add(new ColumnData("State", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					StateType, null, null));
			columns
					.add(new ColumnData("Ejercicio", "Aï¿½o",
							EntityType.NumberWidth, SwingConstants.RIGHT, 0,
							null, null));
			columns
					.add(new ColumnData("Semana", "Semana",
							EntityType.NumberWidth, SwingConstants.RIGHT, 0,
							null, null));
			columns.add(new ColumnData("HorasExtras", "Horas extras",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtHorasExtras, "#,##0.00"));
			columns.add(new ColumnData("HorasDescontadas", "Horas descontadas",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtHorasDescontadas, "#,##0.00"));
			columns.add(new ColumnData("Precio", "Precio",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtPreciohe, "#,##0.00"));
			columns.add(new ColumnData("Importe", "Importe",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImportehe, "#,##0.00"));
			columns.add(new ColumnData("Sabado", "Sï¿½bado",
					EntityType.NumberWidth, SwingConstants.CENTER, CheckType,
					null, null));
			columns.add(new ColumnData("Comentarios", "Comentarios",
					EntityType.TextWidth, SwingConstants.LEFT, NormalType,
					null, null));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (!changingHoraExtraState && !addLine)
						changeHorasExtrasEditState();
					if (e.getColumn() == columnHorasExtras) {
						calculaImporteHoraExtra();
					}
					if (e.getColumn() == columnHorasDescontadas) {
						calculaImporteHoraExtra();
					}
					if (e.getColumn() == columnPrecio) {
						calculaImporteHoraExtra();
					}
				}
			});
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			switch (columna) {

			case columnEjercicio: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString();
							return Integer.parseInt(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnSemana: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString();
							return Integer.parseInt(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnHorasExtras: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnHorasDescontadas: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnPrecio: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnImporte: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnComentarios: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals("")) {
						String value = super.getValueAt(fila, columna)
								.toString();
						return value;
					} else
						return "";
				}
				return "";
			}
			}
			return super.getValueAt(fila, columna);
		}

	}

	public static class VacacionesTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		static ImageIcon Adding = null;
		static ImageIcon Editing = null;

		final public static int NormalType = 0;
		final public static int Float2Type = 1;
		final public static int Float4Type = 2;
		final public static int ComboType = 3;
		final public static int CheckType = 4;
		final public static int StateType = 5;
		final public static int DateType = 6;

		final public static String NewLine = "[Adding]";
		final public static String EditLine = "[Editing]";
		final public static String DeleteLine = "[Deleting]";

		final public static int columnSelect = 0;
		final public static int columnState = 1;
		final public static int columnEjercicio = 2;
		final public static int columnNumDias = 3;
		final public static int columnComentarios = 4;

		static String[] columnNames = { "Select", "State", "Ejercicio",
				"NumDias", "Comentarios" };

		class ColumnData {
			public String m_name;
			public String m_title;
			public int m_width;
			public int m_alignment;
			public int m_editortype;
			public Object m_editor;
			public String m_format;

			public ColumnData(String name, String title, int width,
					int alignment, int editortype, Object editor, String format) {
				m_name = name;
				m_title = title;
				m_width = width;
				m_alignment = alignment;
				m_editortype = editortype;
				m_editor = editor;
				m_format = format;
			}
		}

		private Vector<ColumnData> columns;

		public VacacionesTableModel() {

			super(null, columnNames);
			Adding = new ImageIcon(getClass().getResource(
					"/imagesPackage/addline.png"));
			Editing = new ImageIcon(getClass().getResource(
					"/imagesPackage/editline.png"));

			columns = new Vector<ColumnData>();
			columns.add(new ColumnData("Select", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					CheckType, null, null));
			columns.add(new ColumnData("State", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					StateType, null, null));
			columns
					.add(new ColumnData("Ejercicio", "Aï¿½o",
							EntityType.NumberWidth, SwingConstants.RIGHT, 0,
							null, null));
			columns.add(new ColumnData("NumDias", "Nï¿½mero de dï¿½as",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtNumDias, "#,##0.00"));
			columns.add(new ColumnData("Comentarios", "Comentarios",
					EntityType.TextWidth, SwingConstants.LEFT, NormalType,
					null, null));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (!changingVacacionesState && !addLine)
						changeVacacionesEditState();
				}
			});
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			switch (columna) {

			case columnEjercicio: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString();
							return Integer.parseInt(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnNumDias: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals(""))
						try {
							String value = super.getValueAt(fila, columna)
									.toString().replace(",", ".");
							return Float.parseFloat(value);
						} catch (NumberFormatException nfe) {
							return "";
						}
					else
						return "";
				} else
					return "";
			}
			case columnComentarios: {
				if (super.getValueAt(fila, columna) != null) {
					if (!super.getValueAt(fila, columna).equals("")) {
						String value = super.getValueAt(fila, columna)
								.toString();
						return value;
					} else
						return "";
				}
				return "";
			}
			}
			return super.getValueAt(fila, columna);
		}

	}

	Empleados empleado;

	static boolean changingContratoState = false;
	static boolean addLine = false;
	static boolean changingNominaState = false;
	static boolean changingHoraExtraState = false;
	static boolean changingVacacionesState = false;

	private static boolean changingBanco = false;
	private static boolean changingSucursal = false;

	private static java.awt.Frame parentFrame;
	private static MySession session;
	private boolean OnNew = false;
	private Entity entity = new Entity();

	public static java.awt.Frame getParentFrame() {
		return FrmEmpleado.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmEmpleado.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		FrmEmpleado.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmEmpleado() {
		initComponents();
	}

	public FrmEmpleado(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			this.setParentFrame(parent);
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEmpleado()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmEmpleado.configureKeys",
					he);
		}
	}

	private void prepareTableContratos() {

		try {
			ContratosTableModel modelContratos = new ContratosTableModel();
			tblContratos.setModel(modelContratos);
			tblContratos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			for (int k = 0; k < modelContratos.columns.size(); k++) {

				Object renderer = null;
				DefaultCellEditor editor = null;

				Integer editortype = modelContratos.columns.get(k).m_editortype;
				switch (editortype) {
				case ContratosTableModel.NormalType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelContratos.columns
									.get(k).m_alignment);

					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case ContratosTableModel.DateType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelContratos.columns
									.get(k).m_alignment);
					final JFormattedTextField field = (JFormattedTextField) modelContratos.columns
							.get(k).m_editor;
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case ContratosTableModel.Float2Type: {
					renderer = new Float2Renderer();
					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case ContratosTableModel.Float4Type: {
					renderer = new Float4Renderer();
					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case ContratosTableModel.ComboType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelContratos.columns
									.get(k).m_alignment);
					editor = new DefaultCellEditor(
							(JComboBox) modelContratos.columns.get(k).m_editor);
					break;
				}
				case ContratosTableModel.CheckType: {
					final JCheckBox check = new JCheckBox();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JCheckBox getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							check.setSelected(((Boolean) value).booleanValue());
							return check;
						}
					};
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelContratos.columns
									.get(k).m_alignment);
					editor = new DefaultCellEditor(check);
					break;
				}
				case ContratosTableModel.StateType: {

					final JLabel stateIcon = new JLabel();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JLabel getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							if (value.equals(ContratosTableModel.NewLine))
								stateIcon.setIcon(ContratosTableModel.Adding);
							else if (value.equals(ContratosTableModel.EditLine))
								stateIcon.setIcon(ContratosTableModel.Editing);

							return stateIcon;
						}

					};
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelContratos.columns
									.get(k).m_alignment);
					break;
				}
				}

				TableColumn column = tblContratos
						.getColumn(modelContratos.columns.get(k).m_name);
				column.setHeaderValue(modelContratos.columns.get(k).m_title);
				column.setPreferredWidth(modelContratos.columns.get(k).m_width);
				column.setCellRenderer((TableCellRenderer) renderer);
				if (editor != null) {
					column.setCellEditor(editor);
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmEmpleado.parentFrame,
					"FrmEmpleado.prepareTableContratos()", he);
		}
	}

	private void prepareTableNominas() {

		try {
			NominasTableModel modelNominas = new NominasTableModel();
			tblNominas.setModel(modelNominas);
			tblNominas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			for (int k = 0; k < modelNominas.columns.size(); k++) {

				Object renderer = null;
				DefaultCellEditor editor = null;

				Integer editortype = modelNominas.columns.get(k).m_editortype;
				switch (editortype) {
				case NominasTableModel.NormalType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelNominas.columns.get(k).m_alignment);

					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case NominasTableModel.DateType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelNominas.columns.get(k).m_alignment);
					final JFormattedTextField field = (JFormattedTextField) modelNominas.columns
							.get(k).m_editor;
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case NominasTableModel.Float2Type: {
					renderer = new Float2Renderer();
					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case NominasTableModel.Float4Type: {
					renderer = new Float4Renderer();
					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case NominasTableModel.ComboType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelNominas.columns.get(k).m_alignment);
					editor = new DefaultCellEditor(
							(JComboBox) modelNominas.columns.get(k).m_editor);
					break;
				}
				case NominasTableModel.CheckType: {
					final JCheckBox check = new JCheckBox();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JCheckBox getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							check.setSelected(((Boolean) value).booleanValue());
							return check;
						}
					};
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelNominas.columns.get(k).m_alignment);
					editor = new DefaultCellEditor(check);
					break;
				}
				case NominasTableModel.StateType: {

					final JLabel stateIcon = new JLabel();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JLabel getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							if (value.equals(NominasTableModel.NewLine))
								stateIcon.setIcon(NominasTableModel.Adding);
							else if (value.equals(NominasTableModel.EditLine))
								stateIcon.setIcon(NominasTableModel.Editing);

							return stateIcon;
						}

					};
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelNominas.columns.get(k).m_alignment);
					break;
				}
				}

				TableColumn column = tblNominas.getColumn(modelNominas.columns
						.get(k).m_name);
				column.setHeaderValue(modelNominas.columns.get(k).m_title);
				column.setPreferredWidth(modelNominas.columns.get(k).m_width);
				column.setCellRenderer((TableCellRenderer) renderer);
				if (editor != null) {
					column.setCellEditor(editor);
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmEmpleado.parentFrame,
					"FrmEmpleado.prepareTableNominas()", he);
		}
	}

	private void prepareTableHorasExtras() {

		try {
			HorasExtrasTableModel modelHorasExtras = new HorasExtrasTableModel();
			tblHorasExtras.setModel(modelHorasExtras);
			tblHorasExtras
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			for (int k = 0; k < modelHorasExtras.columns.size(); k++) {

				Object renderer = null;
				DefaultCellEditor editor = null;

				Integer editortype = modelHorasExtras.columns.get(k).m_editortype;
				switch (editortype) {
				case HorasExtrasTableModel.NormalType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelHorasExtras.columns
									.get(k).m_alignment);

					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case HorasExtrasTableModel.DateType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelHorasExtras.columns
									.get(k).m_alignment);
					final JFormattedTextField field = (JFormattedTextField) modelHorasExtras.columns
							.get(k).m_editor;
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case HorasExtrasTableModel.Float2Type: {
					renderer = new Float2Renderer();
					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case HorasExtrasTableModel.Float4Type: {
					renderer = new Float4Renderer();
					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case HorasExtrasTableModel.ComboType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelHorasExtras.columns
									.get(k).m_alignment);
					editor = new DefaultCellEditor(
							(JComboBox) modelHorasExtras.columns.get(k).m_editor);
					break;
				}
				case HorasExtrasTableModel.CheckType: {
					final JCheckBox check = new JCheckBox();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JCheckBox getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							check.setSelected(((Boolean) value).booleanValue());
							return check;
						}
					};
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelHorasExtras.columns
									.get(k).m_alignment);
					editor = new DefaultCellEditor(check);
					break;
				}
				case HorasExtrasTableModel.StateType: {

					final JLabel stateIcon = new JLabel();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JLabel getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							if (value.equals(HorasExtrasTableModel.NewLine))
								stateIcon.setIcon(HorasExtrasTableModel.Adding);
							else if (value
									.equals(HorasExtrasTableModel.EditLine))
								stateIcon
										.setIcon(HorasExtrasTableModel.Editing);

							return stateIcon;
						}

					};
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelHorasExtras.columns
									.get(k).m_alignment);
					break;
				}
				}

				TableColumn column = tblHorasExtras
						.getColumn(modelHorasExtras.columns.get(k).m_name);
				column.setHeaderValue(modelHorasExtras.columns.get(k).m_title);
				column
						.setPreferredWidth(modelHorasExtras.columns.get(k).m_width);
				column.setCellRenderer((TableCellRenderer) renderer);
				if (editor != null) {
					column.setCellEditor(editor);
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmEmpleado.parentFrame,
					"FrmEmpleado.prepareTableHorasExtras()", he);
		}
	}

	private void prepareTableVacaciones() {

		try {
			VacacionesTableModel modelVacaciones = new VacacionesTableModel();
			tblVacaciones.setModel(modelVacaciones);
			tblVacaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			for (int k = 0; k < modelVacaciones.columns.size(); k++) {

				Object renderer = null;
				DefaultCellEditor editor = null;

				Integer editortype = modelVacaciones.columns.get(k).m_editortype;
				switch (editortype) {
				case VacacionesTableModel.NormalType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelVacaciones.columns
									.get(k).m_alignment);

					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case VacacionesTableModel.DateType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelVacaciones.columns
									.get(k).m_alignment);
					final JFormattedTextField field = (JFormattedTextField) modelVacaciones.columns
							.get(k).m_editor;
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case VacacionesTableModel.Float2Type: {
					renderer = new Float2Renderer();
					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case VacacionesTableModel.Float4Type: {
					renderer = new Float4Renderer();
					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();//Con esto al solicitar el editor, el texto queda seleccionado
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);//De-selecciono el texto al perder el foco.
						}
					});
					break;
				}
				case VacacionesTableModel.ComboType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelVacaciones.columns
									.get(k).m_alignment);
					editor = new DefaultCellEditor(
							(JComboBox) modelVacaciones.columns.get(k).m_editor);
					break;
				}
				case VacacionesTableModel.CheckType: {
					final JCheckBox check = new JCheckBox();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JCheckBox getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							check.setSelected(((Boolean) value).booleanValue());
							return check;
						}
					};
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelVacaciones.columns
									.get(k).m_alignment);
					editor = new DefaultCellEditor(check);
					break;
				}
				case VacacionesTableModel.StateType: {

					final JLabel stateIcon = new JLabel();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JLabel getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							if (value.equals(VacacionesTableModel.NewLine))
								stateIcon.setIcon(VacacionesTableModel.Adding);
							else if (value
									.equals(VacacionesTableModel.EditLine))
								stateIcon.setIcon(VacacionesTableModel.Editing);

							return stateIcon;
						}

					};
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelVacaciones.columns
									.get(k).m_alignment);
					break;
				}
				}

				TableColumn column = tblVacaciones
						.getColumn(modelVacaciones.columns.get(k).m_name);
				column.setHeaderValue(modelVacaciones.columns.get(k).m_title);
				column
						.setPreferredWidth(modelVacaciones.columns.get(k).m_width);
				column.setCellRenderer((TableCellRenderer) renderer);
				if (editor != null) {
					column.setCellEditor(editor);
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmEmpleado.parentFrame,
					"FrmEmpleado.prepareTableVacaciones()", he);
		}
	}

	private void newLineContratos() {

		try {
			if (tblContratos.getRowCount() > 0) {
				if (!(tblContratos.getValueAt(tblContratos.getSelectedRow(),
						ContratosTableModel.columnFechaAlta).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblContratos.getModel())
							.addRow(new Object[] { false,
									ContratosTableModel.NewLine, "", "", "",
									"", "", "", "" });
					tblContratos.changeSelection(
							tblContratos.getSelectedRow() + 1,
							ContratosTableModel.columnFechaAlta, false, false);
					tblContratos.editCellAt(tblContratos.getSelectedRow() + 1,
							ContratosTableModel.columnFechaAlta);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblContratos.getModel())
						.addRow(new Object[] { false,
								ContratosTableModel.NewLine, "", "", "", "",
								"", "", "" });
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmEmpleado.parentFrame,
					"FrmEmpleado.newLineContratos()", he);
		}

	}

	private void newLineNominas() {

		try {
			if (tblNominas.getRowCount() > 0) {
				if (!(tblNominas.getValueAt(tblNominas.getSelectedRow(),
						NominasTableModel.columnMes).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblNominas.getModel())
							.addRow(new Object[] { false,
									NominasTableModel.NewLine,
									session.getEjercicio().getEjercicio(), "",
									"", "", "", "", "", "", "", "" });
					tblNominas.changeSelection(tblNominas.getSelectedRow() + 1,
							NominasTableModel.columnMes, false, false);
					tblNominas.editCellAt(tblNominas.getSelectedRow() + 1,
							NominasTableModel.columnMes);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblNominas.getModel())
						.addRow(new Object[] { false,
								NominasTableModel.NewLine,
								session.getEjercicio().getEjercicio(), "", "",
								"", "", "", "", "", "", "" });

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmEmpleado.parentFrame,
					"FrmEmpleado.newLineNominas()", he);
		}

	}

	private void newLineHorasExtras() {

		try {
			if (tblHorasExtras.getRowCount() > 0) {
				if (!(tblHorasExtras.getValueAt(
						tblHorasExtras.getSelectedRow(),
						HorasExtrasTableModel.columnSemana).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblHorasExtras.getModel())
							.addRow(new Object[] { false,
									HorasExtrasTableModel.NewLine,
									session.getEjercicio().getEjercicio(), "",
									"", "", "", "", false, "" });
					tblHorasExtras.changeSelection(tblHorasExtras
							.getSelectedRow() + 1,
							HorasExtrasTableModel.columnSemana, false, false);
					tblHorasExtras.editCellAt(
							tblHorasExtras.getSelectedRow() + 1,
							HorasExtrasTableModel.columnSemana);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblHorasExtras.getModel())
						.addRow(new Object[] { false,
								HorasExtrasTableModel.NewLine,
								session.getEjercicio().getEjercicio(), "", "",
								"", "", "", false, "" });
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmEmpleado.parentFrame,
					"FrmEmpleado.newLineHorasExtras()", he);
		}

	}

	private void newLineVacaciones() {

		try {
			if (tblVacaciones.getRowCount() > 0) {
				if (!(tblVacaciones.getValueAt(tblVacaciones.getSelectedRow(),
						VacacionesTableModel.columnNumDias).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblVacaciones.getModel())
							.addRow(new Object[] { false,
									VacacionesTableModel.NewLine,
									session.getEjercicio().getEjercicio(), "",
									"" });
					tblVacaciones.changeSelection(tblVacaciones
							.getSelectedRow() + 1,
							VacacionesTableModel.columnNumDias, false, false);
					tblVacaciones.editCellAt(
							tblVacaciones.getSelectedRow() + 1,
							VacacionesTableModel.columnNumDias);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblVacaciones.getModel())
						.addRow(new Object[] { false,
								VacacionesTableModel.NewLine,
								session.getEjercicio().getEjercicio(), "", "" });
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmEmpleado.parentFrame,
					"FrmEmpleado.newLineVacaciones()", he);
		}

	}

	private static void changeContratosEditState() {
		try {
			if (!changingContratoState) {
				if (tblContratos.getSelectedRow() != -1) {
					changingContratoState = true;
					tblContratos.setValueAt(ContratosTableModel.EditLine,
							tblContratos.getSelectedRow(),
							ContratosTableModel.columnState);
					changingContratoState = false;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.changeContratosEditState()", he);
		}
	}

	private static void changeNominasEditState() {
		try {
			if (!changingNominaState) {
				if (tblNominas.getSelectedRow() != -1) {
					changingNominaState = true;
					tblNominas.setValueAt(NominasTableModel.EditLine,
							tblNominas.getSelectedRow(),
							NominasTableModel.columnState);
					changingNominaState = false;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.changeNominasEditState()", he);
		}
	}

	private static void changeHorasExtrasEditState() {
		try {
			if (!changingHoraExtraState) {
				if (tblHorasExtras.getSelectedRow() != -1) {
					changingHoraExtraState = true;
					tblHorasExtras.setValueAt(HorasExtrasTableModel.EditLine,
							tblHorasExtras.getSelectedRow(),
							HorasExtrasTableModel.columnState);
					changingHoraExtraState = false;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.changeHorasExtrasEditState()", he);
		}
	}

	private static void changeVacacionesEditState() {
		try {
			if (!changingVacacionesState) {
				if (tblVacaciones.getSelectedRow() != -1) {
					changingVacacionesState = true;
					tblVacaciones.setValueAt(VacacionesTableModel.EditLine,
							tblVacaciones.getSelectedRow(),
							VacacionesTableModel.columnState);
					changingVacacionesState = false;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.changeVacacionesEditState()", he);
		}
	}

	public void newData() {

		try {
			this.empleado = new Empleados();
			loadCuentasBancarias();
			prepareTableContratos();
			prepareTableNominas();
			prepareTableHorasExtras();
			prepareTableVacaciones();
			loadBancos();
			loadSucursales("");
			txtIdEmpleado.setText("");
			txtNIF.setText("");
			txtNombre.setText("");
			txtApellidos.setText("");
			txtFechaNacimiento.setText("");
			txtDireccion.setText("");
			txtPoblacion.setText("");
			txtCodigoPostal.setText("");
			txtTelefono.setText("");
			chkFijo.setText("");
			txtCategoria.setText("");
			txtCorreoElectronico.setText("");
			txtFechaAntiguedad.setText("");
			txtComentarios.setText("");
			txtIdBanco.setText("");
			cboBancos.setSelectedItem(null);
			txtIdSucursal.setText("");
			cboSucursales.setSelectedItem(null);
			txtDigitoControl.setText("");
			txtCuentaBancaria.setText("");
                        txtIBAN.setText("");
			Integer numRows = tblContratos.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblContratos.getModel()).removeRow(0);
			newLineContratos();
			numRows = tblNominas.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblNominas.getModel()).removeRow(0);
			newLineNominas();
			numRows = tblHorasExtras.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblHorasExtras.getModel()).removeRow(0);
			newLineHorasExtras();
			numRows = tblVacaciones.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblVacaciones.getModel()).removeRow(0);
			newLineVacaciones();
			calculaTotalLiquidado();
			calculaTotalesNominas();
			calculaTotalesHorasExtras();
			calculaTotalDiasVacaciones();
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(), "FrmEmpleado.newData()",
					he);
		}
	}

	public void loadData(Empleados entity) {

		try {
			this.empleado = entity;
			loadCuentasBancarias();
			prepareTableContratos();
			prepareTableNominas();
			prepareTableHorasExtras();
			prepareTableVacaciones();
			loadBancos();
			loadSucursales(entity.getIdBanco());
			txtIdEmpleado.setValue(entity.getId().getIdEmpleado());
			txtNIF.setText(entity.getNif());
			txtNombre.setText(entity.getNombre());
			txtApellidos.setText(entity.getApellidos());
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
			if (entity.getFechaNacimiento() != null) {
				String strFechaNacimiento = df.format(entity
						.getFechaNacimiento());
				txtFechaNacimiento.setValue(strFechaNacimiento);
			} else
				txtFechaNacimiento.setValue(null);
			txtDireccion.setText(entity.getDireccion());
			txtPoblacion.setText(entity.getPoblacion());
			txtCodigoPostal.setText(entity.getCodigoPostal());
			txtTelefono.setText(entity.getTelefono());
			txtCategoria.setText(entity.getCategoria());
			txtCorreoElectronico.setText(entity.getCorreoElectronico());
			if (entity.getFechaAntiguedad() != null) {
				String strFechaAntiguedad = df.format(entity
						.getFechaAntiguedad());
				txtFechaAntiguedad.setValue(strFechaAntiguedad);
			} else
				txtFechaAntiguedad.setValue(null);
			chkFijo.setSelected(entity.getFijo() != 0);
			txtComentarios.setText(entity.getObservaciones());
			txtIdBanco.setText(entity.getIdBanco());
			changeBanco();
			txtIdSucursal.setText(entity.getIdSucursal());
			changeSucursal();
			txtDigitoControl.setValue(entity.getDigitoControl());
			txtCuentaBancaria.setText(entity.getCuentaBancaria());
                        txtIBAN.setText(entity.getIban());
			loadDataContratos();
			loadDataNominas();
			loadDataHorasExtras();
			loadDataVacaciones();
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.loadData()", he);
		}
	}

	private void loadDataContratos() {

		try {
			Integer numRows = ((DefaultTableModel) tblContratos.getModel())
					.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblContratos.getModel()).removeRow(0);

			List<?> contratosList = entity.executeQuery(this, "*",
					"Empleadoscontratos", "id.idEmpleado = "
							+ empleado.getId().getIdEmpleado(), "id.fechaAlta");

			if (contratosList.size() == 0)
				newLineContratos();
			else {
				for (Object contrato : contratosList) {
					Empleadoscontratos empleadocontrato = (Empleadoscontratos) contrato;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(ContratosTableModel.EditLine);
					SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					String cadenaFecha = formatoDeFecha.format(empleadocontrato
							.getId().getFechaAlta());
					oneRow.add(cadenaFecha);
					cadenaFecha = formatoDeFecha.format(empleadocontrato
							.getFechaBaja());
					oneRow.add(cadenaFecha);
					oneRow.add(empleadocontrato.getDuracion());
					oneRow.add(empleadocontrato.getImporteLiquidacion());
					oneRow.add(empleadocontrato.getImporteIrpf());
					oneRow.add(empleadocontrato.getCuentaBancariaPago());
					oneRow.add(empleadocontrato.getComentarios());
					((DefaultTableModel) tblContratos.getModel())
							.addRow(oneRow);
				}
			}
			calculaTotalLiquidado();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.loadDataContratos()", he);
		}
	}

	private void loadDataNominas() {

		try {
			Integer numRows = ((DefaultTableModel) tblNominas.getModel())
					.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblNominas.getModel()).removeRow(0);

			List<?> nominasList = entity.executeQuery(this, "*",
					"Empleadosnominas", "id.idEmpleado = "
							+ empleado.getId().getIdEmpleado(),
					"id.ejercicios.ejercicio, id.mes");

			if (nominasList.size() == 0)
				newLineNominas();
			else {
				for (Object nomina : nominasList) {
					Empleadosnominas empleadonomina = (Empleadosnominas) nomina;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(NominasTableModel.EditLine);
					oneRow.add(empleadonomina.getId().getEjercicios()
							.getEjercicio());
					oneRow.add(empleadonomina.getId().getMes());
					oneRow.add(empleadonomina.getTotalDevengado());
					oneRow.add(empleadonomina.getImporteIrpf());
					oneRow.add(empleadonomina.getImporteSegSoc());
					oneRow.add(empleadonomina.getImporteSegAutonomo());
					oneRow.add(empleadonomina.getImporteEmbargo());
					oneRow.add(empleadonomina.getTotalLiquido());
					oneRow.add(empleadonomina.getImporteBonificacion());
					oneRow.add(empleadonomina.getCuentaBancariaPago());
					oneRow.add(empleadonomina.getComentarios());
					((DefaultTableModel) tblNominas.getModel()).addRow(oneRow);
				}
			}
			calculaTotalesNominas();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.loadDataNominas()", he);
		}
	}

	private void loadDataHorasExtras() {

		try {
			Integer numRows = ((DefaultTableModel) tblHorasExtras.getModel())
					.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblHorasExtras.getModel()).removeRow(0);

			List<?> horasextrasList = entity.executeQuery(this, "*",
					"Empleadoshorasextras", "id.idEmpleado = "
							+ empleado.getId().getIdEmpleado(),
					"id.ejercicios.ejercicio, id.semana");

			if (horasextrasList.size() == 0)
				newLineHorasExtras();
			else {
				for (Object horaextra : horasextrasList) {
					Empleadoshorasextras empleadohoraextra = (Empleadoshorasextras) horaextra;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(HorasExtrasTableModel.EditLine);
					oneRow.add(empleadohoraextra.getId().getEjercicios()
							.getEjercicio());
					oneRow.add(empleadohoraextra.getId().getSemana());
					oneRow.add(empleadohoraextra.getHorasExtras());
					oneRow.add(empleadohoraextra.getHorasDescontadas());
					oneRow.add(empleadohoraextra.getPrecio());
					oneRow.add(empleadohoraextra.getImporte());
					if (empleadohoraextra.getSabado() == 0)
						oneRow.add(false);
					else
						oneRow.add(true);
					oneRow.add(empleadohoraextra.getComentarios());
					((DefaultTableModel) tblHorasExtras.getModel())
							.addRow(oneRow);
				}
			}
			calculaTotalesHorasExtras();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.loadDataHorasExtras()", he);
		}
	}

	private void loadDataVacaciones() {

		try {
			Integer numRows = ((DefaultTableModel) tblVacaciones.getModel())
					.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblVacaciones.getModel()).removeRow(0);

			List<?> vacacionesList = entity.executeQuery(this, "*",
					"Empleadosvacaciones", "id.idEmpleado = "
							+ empleado.getId().getIdEmpleado(),
					"id.ejercicios.ejercicio, id.linea");

			if (vacacionesList.size() == 0)
				newLineVacaciones();
			else {
				for (Object vacaciones : vacacionesList) {
					Empleadosvacaciones empleadovacaciones = (Empleadosvacaciones) vacaciones;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(VacacionesTableModel.EditLine);
					oneRow.add(empleadovacaciones.getId().getEjercicios()
							.getEjercicio());
					oneRow.add(empleadovacaciones.getNumDias());
					oneRow.add(empleadovacaciones.getComentarios());
					((DefaultTableModel) tblVacaciones.getModel())
							.addRow(oneRow);
				}
			}
			calculaTotalDiasVacaciones();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.loadDataVacaciones()", he);
		}
	}

	private void loadBancos() {

		try {
			cboBancos.removeAllItems();

			List<?> bancosList = entity.BancoFindDistinctAll(this);

			for (Object o : bancosList) {
				String banco = (String) o;
				cboBancos.addItem(banco);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.loadBancos()", he);
		}
	}

	private void loadCuentasBancarias() {

		try {
			cboCuentaBancaria.removeAllItems();
			cboCuentaBancariaLiquidacion.removeAllItems();

			List<?> cuentasbancarias = entity
					.EmpresaGetCuentasBancarias(getParentFrame());

			for (Object o : cuentasbancarias) {
				Empresascuentas empresacuenta = (Empresascuentas) o;
				cboCuentaBancaria.addItem(empresacuenta.getId()
						.getCuentaBancaria());
				cboCuentaBancariaLiquidacion.addItem(empresacuenta.getId()
						.getCuentaBancaria());
			}

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.loadCuentasBancarias()", he);
		}
	}

	private void loadSucursales(String bancoId) {

		try {
			cboSucursales.removeAllItems();

			List<?> bancosList = entity.SucursalFindByIdBanco(this, bancoId);

			for (Object o : bancosList) {
				Bancos banco = (Bancos) o;
				cboSucursales.addItem(banco.getNombreSucursal());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.loadSucursales()", he);
		}
	}

	public boolean saveData(Boolean showmessage) throws ParseException {

		try {
			if (validateData()) {
				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					txtIdEmpleado.setValue(entity.newId(this, "Empleados",
							"id.idEmpleado"));
					EmpleadosId empleadoid = new EmpleadosId();
					empleadoid.setEmpresas(session.getEmpresa());
					empleadoid.setEjercicios(session.getEjercicio());
					empleadoid
							.setIdEmpleado((Integer) txtIdEmpleado.getValue());
					empleado.setId(empleadoid);
				}
				if (!txtNIF.getText().equals(""))
					empleado.setNif(txtNIF.getText());
				else
					empleado.setNif(null);
				if (!txtNombre.getText().equals(""))
					empleado.setNombre(txtNombre.getText());
				else
					empleado.setNombre(null);
				if (!txtApellidos.getText().equals(""))
					empleado.setApellidos(txtApellidos.getText());
				else
					empleado.setApellidos(null);
				if (!txtFechaNacimiento.getText()
						.equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtFechaNacimiento.getText());
					empleado.setFechaNacimiento(value);
				} else
					empleado.setFechaNacimiento(null);
				if (!txtDireccion.getText().equals(""))
					empleado.setDireccion(txtDireccion.getText());
				else
					empleado.setDireccion(null);
				if (!txtPoblacion.getText().equals(""))
					empleado.setPoblacion(txtPoblacion.getText());
				else
					empleado.setPoblacion(null);
				if (!txtCodigoPostal.getText().equals(""))
					empleado.setCodigoPostal(txtCodigoPostal.getText());
				else
					empleado.setCodigoPostal(null);
				if (!txtTelefono.getText().equals(""))
					empleado.setTelefono(txtTelefono.getText());
				else
					empleado.setTelefono(null);
				if (!txtCategoria.getText().equals(""))
					empleado.setCategoria(txtCategoria.getText());
				else
					empleado.setCategoria(null);
				if (!txtCorreoElectronico.getText().equals(""))
					empleado.setCorreoElectronico(txtCorreoElectronico.getText());
				else
					empleado.setCorreoElectronico(null);
				if (!txtFechaAntiguedad.getText()
						.equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtFechaAntiguedad.getText());
					empleado.setFechaAntiguedad(value);
				} else
					empleado.setFechaAntiguedad(null);
				if (chkFijo.isSelected())
					empleado.setFijo(((Number) 1).shortValue());
				else
					empleado.setFijo(((Number) 0).shortValue());
				if (!txtComentarios.getText().equals(""))
					empleado.setObservaciones(txtComentarios.getText());
				else
					empleado.setObservaciones(null);
				if (!txtIdBanco.getText().equals(""))
					empleado.setIdBanco(txtIdBanco.getText());
				else
					empleado.setIdBanco(null);
				if (!txtIdSucursal.getText().equals(""))
					empleado.setIdSucursal(txtIdSucursal.getText());
				else
					empleado.setIdSucursal(null);
				if (!txtDigitoControl.getText().equals(""))
					empleado.setDigitoControl(txtDigitoControl.getText());
				else
					empleado.setDigitoControl(null);
				if (!txtCuentaBancaria.getText().equals(""))
					empleado.setCuentaBancaria(txtCuentaBancaria.getText());
				else
					empleado.setCuentaBancaria(null);
                                if (!txtIBAN.getText().equals(""))
					empleado.setIban(txtIBAN.getText());
				else
					empleado.setIban(null);
				empleado.setLmd(new Date());
				empleado.setSid("Santi");
				empleado.setVersion(1);
				session.getSession().replicate(empleado,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(empleado);
				session.getSession().flush();

				String deletelinesquery = "Delete From Empleadoscontratos "
						+ "Where id.idEmpleado = "
						+ ((Number) txtIdEmpleado.getValue()).intValue()
						+ " and id.empresas.idEmpresa="
						+ session.getEmpresa().getIdEmpresa()
						+ " and id.ejercicios.ejercicio="
						+ session.getEjercicio().getEjercicio();

				Query q = getSession().getSession().createQuery(
						deletelinesquery);
				q.executeUpdate();

				for (Integer k = 0; k < tblContratos.getRowCount(); k++) {
					if (tblContratos.getValueAt(k,
							ContratosTableModel.columnState).equals(
							ContratosTableModel.EditLine)) {
						Empleadoscontratos empleadocontrato = new Empleadoscontratos();
						EmpleadoscontratosId empleadocontratoId = new EmpleadoscontratosId();
						empleadocontratoId
								.setIdEmpleado((Integer) txtIdEmpleado
										.getValue());
						empleadocontratoId.setEmpresas(session.getEmpresa());
						empleadocontratoId
								.setEjercicios(session.getEjercicio());
						SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
								PreferencesUI.DateFormat);
						Date value = formatoDeFecha.parse(tblContratos
								.getValueAt(k,
										ContratosTableModel.columnFechaAlta)
								.toString());
						empleadocontratoId.setFechaAlta(value);
						empleadocontrato.setId(empleadocontratoId);
						if (!tblContratos.getValueAt(k,
								ContratosTableModel.columnFechaBaja).equals("")) {
							value = formatoDeFecha
									.parse(tblContratos
											.getValueAt(
													k,
													ContratosTableModel.columnFechaBaja)
											.toString());
							empleadocontrato.setFechaBaja(value);
						} else
							empleadocontrato.setFechaBaja(null);
						if (!tblContratos.getValueAt(k,
								ContratosTableModel.columnDuracion).equals(""))
							empleadocontrato
									.setDuracion((String) tblContratos
											.getValueAt(
													k,
													ContratosTableModel.columnDuracion));
						else
							empleadocontrato.setDuracion(null);
						if (!tblContratos.getValueAt(k,
								ContratosTableModel.columnImporteLiquidacion)
								.equals(""))
							empleadocontrato
									.setImporteLiquidacion((Float) tblContratos
											.getValueAt(
													k,
													ContratosTableModel.columnImporteLiquidacion));
						else
							empleadocontrato.setImporteLiquidacion(((Number) 0)
									.floatValue());
						if (!tblContratos.getValueAt(k,
								ContratosTableModel.columnImporteIrpf).equals(
								""))
							empleadocontrato
									.setImporteIrpf((Float) tblContratos
											.getValueAt(
													k,
													ContratosTableModel.columnImporteIrpf));
						else
							empleadocontrato.setImporteIrpf(((Number) 0)
									.floatValue());
						if (!(tblContratos.getValueAt(k,
								ContratosTableModel.columnCuentaBancariaPago) == null)) {
							if (!tblContratos
									.getValueAt(
											k,
											ContratosTableModel.columnCuentaBancariaPago)
									.equals(""))
								empleadocontrato
										.setCuentaBancariaPago((String) tblContratos
												.getValueAt(
														k,
														ContratosTableModel.columnCuentaBancariaPago));
							else
								empleadocontrato.setCuentaBancariaPago(null);
						} else
							empleadocontrato.setCuentaBancariaPago(null);
						if (!tblContratos.getValueAt(k,
								ContratosTableModel.columnComentarios).equals(
								""))
							empleadocontrato
									.setComentarios((String) tblContratos
											.getValueAt(
													k,
													ContratosTableModel.columnComentarios));
						else
							empleadocontrato.setComentarios(null);
						empleadocontrato.setLmd(new Date());
						empleadocontrato.setSid("Santi");
						empleadocontrato.setVersion(1);
						session.getSession().replicate(empleadocontrato,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(empleadocontrato);
						session.getSession().flush();
					}
				}

				deletelinesquery = "Delete From Empleadosnominas "
						+ "Where id.idEmpleado = "
						+ ((Number) txtIdEmpleado.getValue()).intValue()
						+ " and id.empresas.idEmpresa="
						+ session.getEmpresa().getIdEmpresa()
						+ " and id.ejercicios.ejercicio="
						+ session.getEjercicio().getEjercicio();

				q = getSession().getSession().createQuery(deletelinesquery);
				q.executeUpdate();

				for (Integer k = 0; k < tblNominas.getRowCount(); k++) {
					if (tblNominas.getValueAt(k, NominasTableModel.columnState)
							.equals(NominasTableModel.EditLine)) {
						Empleadosnominas empleadonomina = new Empleadosnominas();
						EmpleadosnominasId empleadonominaId = new EmpleadosnominasId();
						empleadonominaId.setIdEmpleado((Integer) txtIdEmpleado
								.getValue());
						empleadonominaId.setEmpresas(session.getEmpresa());
						empleadonominaId
								.setEjercicios(entity
										.EjercicioFindById(
												this,
												(Integer) tblNominas
														.getValueAt(
																k,
																NominasTableModel.columnEjercicio)));
						if (!tblNominas.getValueAt(k,
								NominasTableModel.columnMes).equals(""))
							empleadonominaId
									.setMes((Integer) tblNominas.getValueAt(k,
											NominasTableModel.columnMes));
						else
							empleadonominaId.setMes(null);
						empleadonomina.setId(empleadonominaId);
						if (!tblNominas.getValueAt(k,
								NominasTableModel.columnTotalDevengado).equals(
								""))
							empleadonomina
									.setTotalDevengado((Float) tblNominas
											.getValueAt(
													k,
													NominasTableModel.columnTotalDevengado));
						else
							empleadonomina.setTotalDevengado(((Number) 0)
									.floatValue());
						if (!tblNominas.getValueAt(k,
								NominasTableModel.columnImporteIrpf).equals(""))
							empleadonomina
									.setImporteIrpf((Float) tblNominas
											.getValueAt(
													k,
													NominasTableModel.columnImporteIrpf));
						else
							empleadonomina.setImporteIrpf(((Number) 0)
									.floatValue());
						if (!tblNominas.getValueAt(k,
								NominasTableModel.columnImporteSegSoc).equals(
								""))
							empleadonomina
									.setImporteSegSoc((Float) tblNominas
											.getValueAt(
													k,
													NominasTableModel.columnImporteSegSoc));
						else
							empleadonomina.setImporteSegSoc(((Number) 0)
									.floatValue());
						if (!tblNominas.getValueAt(k,
								NominasTableModel.columnImporteSegAutonomo)
								.equals(""))
							empleadonomina
									.setImporteSegAutonomo((Float) tblNominas
											.getValueAt(
													k,
													NominasTableModel.columnImporteSegAutonomo));
						else
							empleadonomina.setImporteSegAutonomo(((Number) 0)
									.floatValue());
						if (!tblNominas.getValueAt(k,
								NominasTableModel.columnImporteEmbargo).equals(
								""))
							empleadonomina
									.setImporteEmbargo((Float) tblNominas
											.getValueAt(
													k,
													NominasTableModel.columnImporteEmbargo));
						else
							empleadonomina.setImporteEmbargo(((Number) 0)
									.floatValue());
						if (!tblNominas.getValueAt(k,
								NominasTableModel.columnTotalLiquido)
								.equals(""))
							empleadonomina
									.setTotalLiquido((Float) tblNominas
											.getValueAt(
													k,
													NominasTableModel.columnTotalLiquido));
						else
							empleadonomina.setTotalLiquido(((Number) 0)
									.floatValue());
						if (!tblNominas.getValueAt(k,
								NominasTableModel.columnImporteBonificacion)
								.equals(""))
							empleadonomina
									.setImporteBonificacion((Float) tblNominas
											.getValueAt(
													k,
													NominasTableModel.columnImporteBonificacion));
						else
							empleadonomina.setImporteBonificacion(((Number) 0)
									.floatValue());
						if (!(tblNominas.getValueAt(k,
								NominasTableModel.columnCuentaBancariaPago) == null)) {
							if (!tblNominas.getValueAt(k,
									NominasTableModel.columnCuentaBancariaPago)
									.equals(""))
								empleadonomina
										.setCuentaBancariaPago((String) tblNominas
												.getValueAt(
														k,
														NominasTableModel.columnCuentaBancariaPago));
							else
								empleadonomina.setCuentaBancariaPago(null);
						} else
							empleadonomina.setCuentaBancariaPago(null);
						if (!tblNominas.getValueAt(k,
								NominasTableModel.columnComentarios).equals(""))
							empleadonomina
									.setComentarios((String) tblNominas
											.getValueAt(
													k,
													NominasTableModel.columnComentarios));
						else
							empleadonomina.setComentarios(null);
						empleadonomina.setLmd(new Date());
						empleadonomina.setSid("Santi");
						empleadonomina.setVersion(1);
						session.getSession().replicate(empleadonomina,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(empleadonomina);
						session.getSession().flush();
					}
				}

				deletelinesquery = "Delete From Empleadoshorasextras "
						+ "Where id.idEmpleado = "
						+ ((Number) txtIdEmpleado.getValue()).intValue()
						+ " and id.empresas.idEmpresa="
						+ session.getEmpresa().getIdEmpresa()
						+ " and id.ejercicios.ejercicio="
						+ session.getEjercicio().getEjercicio();

				q = getSession().getSession().createQuery(deletelinesquery);
				q.executeUpdate();

				for (Integer k = 0; k < tblHorasExtras.getRowCount(); k++) {
					if (tblHorasExtras.getValueAt(k,
							HorasExtrasTableModel.columnState).equals(
							HorasExtrasTableModel.EditLine)) {
						Empleadoshorasextras empleadohoraextra = new Empleadoshorasextras();
						EmpleadoshorasextrasId empleadohoraextraId = new EmpleadoshorasextrasId();
						empleadohoraextraId
								.setIdEmpleado((Integer) txtIdEmpleado
										.getValue());
						empleadohoraextraId.setEmpresas(session.getEmpresa());
						empleadohoraextraId
								.setEjercicios(entity
										.EjercicioFindById(
												this,
												(Integer) tblHorasExtras
														.getValueAt(
																k,
																HorasExtrasTableModel.columnEjercicio)));
						if (!tblHorasExtras.getValueAt(k,
								HorasExtrasTableModel.columnSemana).equals(""))
							empleadohoraextraId
									.setSemana((Integer) tblHorasExtras
											.getValueAt(
													k,
													HorasExtrasTableModel.columnSemana));
						else
							empleadohoraextraId.setSemana(null);
						empleadohoraextra.setId(empleadohoraextraId);
						if (!tblHorasExtras.getValueAt(k,
								HorasExtrasTableModel.columnHorasExtras)
								.equals(""))
							empleadohoraextra
									.setHorasExtras(((Number) tblHorasExtras
											.getValueAt(
													k,
													HorasExtrasTableModel.columnHorasExtras))
											.floatValue());
						else
							empleadohoraextra.setHorasExtras(((Number) 0)
									.floatValue());
						if (!tblHorasExtras.getValueAt(k,
								HorasExtrasTableModel.columnHorasDescontadas)
								.equals(""))
							empleadohoraextra
									.setHorasDescontadas(((Number) tblHorasExtras
											.getValueAt(
													k,
													HorasExtrasTableModel.columnHorasDescontadas))
											.floatValue());
						else
							empleadohoraextra.setHorasDescontadas(((Number) 0)
									.floatValue());
						if (!tblHorasExtras.getValueAt(k,
								HorasExtrasTableModel.columnPrecio).equals(""))
							empleadohoraextra
									.setPrecio((Float) tblHorasExtras
											.getValueAt(
													k,
													HorasExtrasTableModel.columnPrecio));
						else
							empleadohoraextra.setPrecio(((Number) 0)
									.floatValue());
						if (!tblHorasExtras.getValueAt(k,
								HorasExtrasTableModel.columnImporte).equals(""))
							empleadohoraextra
									.setImporte((Float) tblHorasExtras
											.getValueAt(
													k,
													HorasExtrasTableModel.columnImporte));
						else
							empleadohoraextra.setImporte(((Number) 0)
									.floatValue());
						if ((Boolean) tblHorasExtras.getValueAt(k,
								HorasExtrasTableModel.columnSabado) == true)
							empleadohoraextra.setSabado(((Number) 1)
									.shortValue());
						else
							empleadohoraextra.setSabado(((Number) 0)
									.shortValue());
						if (!tblHorasExtras.getValueAt(k,
								HorasExtrasTableModel.columnComentarios)
								.equals(""))
							empleadohoraextra
									.setComentarios((String) tblHorasExtras
											.getValueAt(
													k,
													HorasExtrasTableModel.columnComentarios));
						else
							empleadohoraextra.setComentarios(null);
						empleadohoraextra.setLmd(new Date());
						empleadohoraextra.setSid("Santi");
						empleadohoraextra.setVersion(1);
						session.getSession().replicate(empleadohoraextra,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(empleadohoraextra);
						session.getSession().flush();
					}
				}

				deletelinesquery = "Delete From Empleadosvacaciones "
						+ "Where id.idEmpleado = "
						+ ((Number) txtIdEmpleado.getValue()).intValue()
						+ " and id.empresas.idEmpresa="
						+ session.getEmpresa().getIdEmpresa()
						+ " and id.ejercicios.ejercicio="
						+ session.getEjercicio().getEjercicio();

				q = getSession().getSession().createQuery(deletelinesquery);
				q.executeUpdate();

				for (Integer k = 0; k < tblVacaciones.getRowCount(); k++) {
					if (tblVacaciones.getValueAt(k,
							VacacionesTableModel.columnState).equals(
							VacacionesTableModel.EditLine)) {
						Empleadosvacaciones empleadovacaciones = new Empleadosvacaciones();
						EmpleadosvacacionesId empleadovacacionesId = new EmpleadosvacacionesId();
						empleadovacacionesId
								.setIdEmpleado((Integer) txtIdEmpleado
										.getValue());
						empleadovacacionesId.setEmpresas(session.getEmpresa());
						empleadovacacionesId
								.setEjercicios(entity
										.EjercicioFindById(
												this,
												(Integer) tblVacaciones
														.getValueAt(
																k,
																VacacionesTableModel.columnEjercicio)));
						empleadovacacionesId.setLinea(k + 1);
						empleadovacaciones.setId(empleadovacacionesId);
						if (!tblVacaciones.getValueAt(k,
								VacacionesTableModel.columnNumDias).equals(""))
							empleadovacaciones
									.setNumDias((Float) tblVacaciones
											.getValueAt(
													k,
													VacacionesTableModel.columnNumDias));
						else
							empleadovacaciones.setNumDias(((Number) 0)
									.floatValue());
						if (!tblVacaciones.getValueAt(k,
								VacacionesTableModel.columnComentarios).equals(
								""))
							empleadovacaciones
									.setComentarios((String) tblVacaciones
											.getValueAt(
													k,
													VacacionesTableModel.columnComentarios));
						else
							empleadovacaciones.setComentarios(null);
						empleadovacaciones.setLmd(new Date());
						empleadovacaciones.setSid("Santi");
						empleadovacaciones.setVersion(1);
						session.getSession().replicate(empleadovacaciones,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(empleadovacaciones);
						session.getSession().flush();
					}
				}

				if (transaction.isActive()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage) {
					TabEmpleado.setSelectedIndex(0);
					Message.ShowSaveSuccesfull(pnlData);
				}
				OnNew = false;
				FrmEmpleados.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.saveData()", he);
			return false;
		}
	}

	private boolean validateData() {

		try {
			if (txtNombre.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un nombre para el empleado.");
				TabEmpleado.setSelectedIndex(0);
				txtNombre.requestFocus();
				return (false);
			}
			if (!txtFechaNacimiento.getText().equals(PreferencesUI.DateMask)) {
				try {
					txtFechaNacimiento.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtFechaNacimiento.requestFocus();
					return (false);
				}
			}
			if (!txtFechaAntiguedad.getText().equals(PreferencesUI.DateMask)) {
				try {
					txtFechaAntiguedad.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtFechaAntiguedad.requestFocus();
					return (false);
				}
			}

			if (!validateContratosData())
				return (false);

			if (!validateNominasData())
				return (false);

			if (!validateHorasExtrasData())
				return (false);

			if (!validateVacacionesData())
				return (false);

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.validateData()", he);
			return (false);
		}
	}

	private boolean validateContratosData() {

		try {
			for (Integer actualrow = 0; actualrow < tblContratos.getRowCount(); actualrow++) {
				if (tblContratos.getValueAt(actualrow,
						ContratosTableModel.columnState).equals(
						ContratosTableModel.EditLine)) {
					if (tblContratos.getValueAt(actualrow,
							ContratosTableModel.columnFechaAlta).equals("")) {
						Message.ShowValidateMessage(tblContratos,
								"Debe indicar una fecha de alta.");
						tblContratos.changeSelection(tblContratos
								.getSelectedRow(),
								ContratosTableModel.columnFechaAlta, false,
								false);
						tblContratos.editCellAt(actualrow,
								ContratosTableModel.columnFechaAlta);
						tblContratos.requestFocusInWindow();
						return (false);
					}
					try {
						SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
								PreferencesUI.DateFormat);
						@SuppressWarnings("unused")
						Date value = formatoDeFecha.parse(tblContratos
								.getValueAt(actualrow,
										ContratosTableModel.columnFechaAlta)
								.toString());
					} catch (ParseException nfe) {
						Message.ShowValidateMessage(tblContratos,
								"La fecha de alta indicada no es vï¿½lida.");
						tblContratos.changeSelection(tblContratos
								.getSelectedRow(),
								ContratosTableModel.columnFechaAlta, false,
								false);
						tblContratos.editCellAt(actualrow,
								ContratosTableModel.columnFechaAlta);
						tblContratos.requestFocusInWindow();
						return (false);
					}
					if (!(tblContratos.getValueAt(actualrow,
							ContratosTableModel.columnFechaBaja).equals(""))) {
						try {
							SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
									PreferencesUI.DateFormat);
							@SuppressWarnings("unused")
							Date value = formatoDeFecha
									.parse(tblContratos
											.getValueAt(
													actualrow,
													ContratosTableModel.columnFechaBaja)
											.toString());
						} catch (ParseException nfe) {
							Message.ShowValidateMessage(tblContratos,
									"La fecha de baja indicada no es vï¿½lida.");
							tblContratos.changeSelection(tblContratos
									.getSelectedRow(),
									ContratosTableModel.columnFechaBaja, false,
									false);
							tblContratos.editCellAt(actualrow,
									ContratosTableModel.columnFechaBaja);
							tblContratos.requestFocusInWindow();
							return (false);
						}
					}
				}
			}
			calculaTotalLiquidado();
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.validateDataContratos()", he);
			return (false);
		}
	}

	private boolean validateNominasData() {

		try {
			for (Integer actualrow = 0; actualrow < tblNominas.getRowCount(); actualrow++) {
				if (tblNominas.getValueAt(actualrow,
						NominasTableModel.columnState).equals(
						NominasTableModel.EditLine)) {
					if (tblNominas.getValueAt(actualrow,
							NominasTableModel.columnEjercicio).equals("")) {
						Message.ShowValidateMessage(tblNominas,
								"Debe indicar un aï¿½o.");
						tblNominas
								.changeSelection(tblNominas.getSelectedRow(),
										NominasTableModel.columnEjercicio,
										false, false);
						tblNominas.editCellAt(actualrow,
								NominasTableModel.columnEjercicio);
						tblNominas.requestFocusInWindow();
						return (false);
					}
					if (tblNominas.getValueAt(actualrow,
							NominasTableModel.columnMes).equals("")) {
						Message.ShowValidateMessage(tblNominas,
								"Debe indicar un mes.");
						tblNominas.changeSelection(tblNominas.getSelectedRow(),
								NominasTableModel.columnMes, false, false);
						tblNominas.editCellAt(actualrow,
								NominasTableModel.columnMes);
						tblNominas.requestFocusInWindow();
						return (false);
					}
				}
			}
			calculaTotalesNominas();
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.validateDataNominas()", he);
			return (false);
		}
	}

	private boolean validateHorasExtrasData() {

		try {
			for (Integer actualrow = 0; actualrow < tblHorasExtras
					.getRowCount(); actualrow++) {
				if (tblHorasExtras.getValueAt(actualrow,
						HorasExtrasTableModel.columnState).equals(
						HorasExtrasTableModel.EditLine)) {
					if (tblHorasExtras.getValueAt(actualrow,
							HorasExtrasTableModel.columnEjercicio).equals("")) {
						Message.ShowValidateMessage(tblHorasExtras,
								"Debe indicar un aï¿½o.");
						tblHorasExtras.changeSelection(tblHorasExtras
								.getSelectedRow(),
								HorasExtrasTableModel.columnEjercicio, false,
								false);
						tblHorasExtras.editCellAt(actualrow,
								HorasExtrasTableModel.columnEjercicio);
						tblHorasExtras.requestFocusInWindow();
						return (false);
					}
					if (tblHorasExtras.getValueAt(actualrow,
							HorasExtrasTableModel.columnSemana).equals("")) {
						Message.ShowValidateMessage(tblHorasExtras,
								"Debe indicar una semana.");
						tblHorasExtras.changeSelection(tblHorasExtras
								.getSelectedRow(),
								HorasExtrasTableModel.columnSemana, false,
								false);
						tblHorasExtras.editCellAt(actualrow,
								HorasExtrasTableModel.columnSemana);
						tblHorasExtras.requestFocusInWindow();
						return (false);
					}
				}
			}
			calculaTotalesHorasExtras();
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.validateHorasExtras()", he);
			return (false);
		}
	}

	private boolean validateVacacionesData() {

		try {
			for (Integer actualrow = 0; actualrow < tblVacaciones.getRowCount(); actualrow++) {
				if (tblVacaciones.getValueAt(actualrow,
						VacacionesTableModel.columnState).equals(
						VacacionesTableModel.EditLine)) {
					if (tblVacaciones.getValueAt(actualrow,
							VacacionesTableModel.columnEjercicio).equals("")) {
						Message.ShowValidateMessage(tblVacaciones,
								"Debe indicar un aï¿½o.");
						tblVacaciones.changeSelection(tblVacaciones
								.getSelectedRow(),
								VacacionesTableModel.columnEjercicio, false,
								false);
						tblVacaciones.editCellAt(actualrow,
								VacacionesTableModel.columnEjercicio);
						tblVacaciones.requestFocusInWindow();
						return (false);
					}
					if (tblVacaciones.getValueAt(actualrow,
							VacacionesTableModel.columnNumDias).equals("")) {
						Message.ShowValidateMessage(tblVacaciones,
								"Debe indicar un Nï¿½mero de dï¿½as.");
						tblVacaciones.changeSelection(tblVacaciones
								.getSelectedRow(),
								VacacionesTableModel.columnNumDias, false,
								false);
						tblVacaciones.editCellAt(actualrow,
								VacacionesTableModel.columnNumDias);
						tblVacaciones.requestFocusInWindow();
						return (false);
					}
				}
			}
			calculaTotalDiasVacaciones();
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.validateDataHorasExtras()", he);
			return (false);
		}
	}

	private void changeBanco() {

		try {
			if (!changingBanco) {
				changingBanco = true;
				if (txtIdBanco.getText().equals(""))
					cboBancos.setSelectedItem(null);
				else {
					try {
						String value = txtIdBanco.getText();
						List<?> bancosList = entity.BancoFindByIdBanco(this,
								value);

						if (bancosList.size() > 0) {
							cboBancos.setSelectedItem(((Bancos) bancosList
									.get(0)).getNombreBanco());
							loadSucursales(txtIdBanco.getText());
						} else {
							cboBancos.setSelectedItem(null);
							loadSucursales("");
						}
					} catch (NumberFormatException nfe) {
						cboBancos.setSelectedItem(null);
						loadSucursales("");
					}
				}
				changingBanco = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.changeBanco()", he);
		}
	}

	private void changeSucursal() {

		try {
			if (!changingSucursal) {
				changingSucursal = true;
				if (txtIdSucursal.getText().equals(""))
					cboSucursales.setSelectedItem(null);
				else {
					try {
						String value = txtIdSucursal.getText();
						List<?> sucursaleslist = entity.BancoFindByIdSucursal(
								this, value);
						if (sucursaleslist.size() > 0)
							cboSucursales
									.setSelectedItem(((Bancos) sucursaleslist
											.get(0)).getNombreSucursal());
						else {
							cboSucursales.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboBancos.setSelectedItem(null);
					}
				}
				changingSucursal = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.changesucursal()", he);
		}
	}

	private static void calculaTotalLiquido() {
		try {
			Float totalDevengado = ((Number) 0).floatValue();
			if (!(tblNominas.getValueAt(tblNominas.getSelectedRow(),
					NominasTableModel.columnTotalDevengado).equals("")))
				totalDevengado = (((Number) tblNominas.getValueAt(tblNominas
						.getSelectedRow(),
						NominasTableModel.columnTotalDevengado)).floatValue());

			Float importeIRPF = ((Number) 0).floatValue();
			if (!(tblNominas.getValueAt(tblNominas.getSelectedRow(),
					NominasTableModel.columnImporteIrpf).equals("")))
				importeIRPF = (((Number) tblNominas.getValueAt(tblNominas
						.getSelectedRow(), NominasTableModel.columnImporteIrpf))
						.floatValue());

			Float importeSegSoc = ((Number) 0).floatValue();
			if (!(tblNominas.getValueAt(tblNominas.getSelectedRow(),
					NominasTableModel.columnImporteSegSoc).equals("")))
				importeSegSoc = (((Number) tblNominas.getValueAt(tblNominas
						.getSelectedRow(),
						NominasTableModel.columnImporteSegSoc)).floatValue());

			Float importeOtros = ((Number) 0).floatValue();
			if (!(tblNominas.getValueAt(tblNominas.getSelectedRow(),
					NominasTableModel.columnImporteSegAutonomo).equals("")))
				importeOtros = (((Number) tblNominas.getValueAt(tblNominas
						.getSelectedRow(),
						NominasTableModel.columnImporteSegAutonomo))
						.floatValue());
			if (!(tblNominas.getValueAt(tblNominas.getSelectedRow(),
					NominasTableModel.columnImporteEmbargo).equals("")))
				importeOtros = importeOtros
						+ (((Number) tblNominas.getValueAt(tblNominas
								.getSelectedRow(),
								NominasTableModel.columnImporteEmbargo))
								.floatValue());

			Float totalLiquido = totalDevengado - importeIRPF - importeSegSoc
					- importeOtros;
			tblNominas.setValueAt(totalLiquido, tblNominas.getSelectedRow(),
					NominasTableModel.columnTotalLiquido);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEmpleado.calculaTotalLiquido()", he);
		}
	}

	private void calculaTotalesNominas() {
		try {
			Float totalDevengado = ((Number) 0).floatValue();
			Float totalSegSoc = ((Number) 0).floatValue();
			Float totalIrpf = ((Number) 0).floatValue();
			Float totalOtros = ((Number) 0).floatValue();
			Float totalLiquido = ((Number) 0).floatValue();
                        Float totalBonificaciones = ((Number) 0).floatValue();
			Integer numNominas = tblNominas.getRowCount();
			for (Integer actualRow = 0; actualRow < numNominas; actualRow++) {
				if (tblNominas.getValueAt(actualRow,
						NominasTableModel.columnState).equals(
						NominasTableModel.EditLine)) {
					if (!(tblNominas.getModel().getValueAt(actualRow,
							NominasTableModel.columnTotalDevengado).equals("")))
						totalDevengado = totalDevengado
								+ ((Number) (((DefaultTableModel) tblNominas
										.getModel()).getValueAt(actualRow,
										NominasTableModel.columnTotalDevengado)))
										.floatValue();
					if (!(tblNominas.getModel().getValueAt(actualRow,
							NominasTableModel.columnImporteSegSoc).equals("")))
						totalSegSoc = totalSegSoc
								+ ((Number) (((DefaultTableModel) tblNominas
										.getModel()).getValueAt(actualRow,
										NominasTableModel.columnImporteSegSoc)))
										.floatValue();
					if (!(tblNominas.getModel().getValueAt(actualRow,
							NominasTableModel.columnImporteIrpf).equals("")))
						totalIrpf = totalIrpf
								+ ((Number) (((DefaultTableModel) tblNominas
										.getModel()).getValueAt(actualRow,
										NominasTableModel.columnImporteIrpf)))
										.floatValue();
					if (!(tblNominas.getModel().getValueAt(actualRow,
							NominasTableModel.columnImporteSegAutonomo)
							.equals("")))
						totalOtros = totalOtros
								+ ((Number) (((DefaultTableModel) tblNominas
										.getModel())
										.getValueAt(
												actualRow,
												NominasTableModel.columnImporteSegAutonomo)))
										.floatValue();
					if (!(tblNominas.getModel().getValueAt(actualRow,
							NominasTableModel.columnImporteEmbargo).equals("")))
						totalOtros = totalOtros
								+ ((Number) (((DefaultTableModel) tblNominas
										.getModel()).getValueAt(actualRow,
										NominasTableModel.columnImporteEmbargo)))
										.floatValue();
					if (!(tblNominas.getModel().getValueAt(actualRow,
							NominasTableModel.columnTotalLiquido).equals("")))
						totalLiquido = totalLiquido
								+ ((Number) (((DefaultTableModel) tblNominas
										.getModel()).getValueAt(actualRow,
										NominasTableModel.columnTotalLiquido)))
										.floatValue();
                                        if (!(tblNominas.getModel().getValueAt(actualRow,
							NominasTableModel.columnImporteBonificacion).equals("")))
						totalBonificaciones = totalBonificaciones
								+ ((Number) (((DefaultTableModel) tblNominas
										.getModel()).getValueAt(actualRow,
										NominasTableModel.columnImporteBonificacion)))
										.floatValue();
				}
			}
			txtSumaTotalDevengado.setValue(totalDevengado);
			txtSumaIRPF.setValue(totalIrpf);
			txtSumaSegSoc.setValue(totalSegSoc);
			txtSumaOtros.setValue(totalOtros);
			txtSumaTotalLiquido.setValue(totalLiquido);
                        txtSalarioMedio.setValue((totalLiquido + totalBonificaciones)/numNominas);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEmpleado.calculaTotalesNominas()", he);
		}
	}

	private void calculaTotalLiquidado() {
		try {
			Float totalLiquidado = ((Number) 0).floatValue();
			Integer numRows = tblContratos.getRowCount();
			for (Integer actualRow = 0; actualRow < numRows; actualRow++) {
				if (tblContratos.getValueAt(actualRow,
						ContratosTableModel.columnState).equals(
						ContratosTableModel.EditLine)) {
					if (!(tblContratos.getModel().getValueAt(actualRow,
							ContratosTableModel.columnImporteLiquidacion)
							.equals("")))
						totalLiquidado = totalLiquidado
								+ ((Number) (((DefaultTableModel) tblContratos
										.getModel())
										.getValueAt(
												actualRow,
												ContratosTableModel.columnImporteLiquidacion)))
										.floatValue();
					if (!(tblContratos.getModel().getValueAt(actualRow,
							ContratosTableModel.columnImporteIrpf).equals("")))
						totalLiquidado = totalLiquidado
								- ((Number) (((DefaultTableModel) tblContratos
										.getModel()).getValueAt(actualRow,
										ContratosTableModel.columnImporteIrpf)))
										.floatValue();
				}
			}
			txtSumaTotalLiquidado.setValue(totalLiquidado);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEmpleado.calculaTotalLiquidado()", he);
		}
	}

	private static void calculaImporteHoraExtra() {
		try {
			Float PrecioHoraExtra = ((Number) 0).floatValue();
			Float HorasExtras = ((Number) 0).floatValue();
			Float HorasDescontadas = ((Number) 0).floatValue();
			if (!(tblHorasExtras.getValueAt(tblHorasExtras.getSelectedRow(),
					HorasExtrasTableModel.columnHorasExtras).equals("")))
				HorasExtras = (((Number) tblHorasExtras.getValueAt(
						tblHorasExtras.getSelectedRow(),
						HorasExtrasTableModel.columnHorasExtras)).floatValue());

			if (!(tblHorasExtras.getValueAt(tblHorasExtras.getSelectedRow(),
					HorasExtrasTableModel.columnHorasDescontadas).equals("")))
				HorasDescontadas = (((Number) tblHorasExtras.getValueAt(
						tblHorasExtras.getSelectedRow(),
						HorasExtrasTableModel.columnHorasDescontadas))
						.floatValue());

			if (!(tblHorasExtras.getValueAt(tblHorasExtras.getSelectedRow(),
					HorasExtrasTableModel.columnPrecio).equals("")))
				PrecioHoraExtra = (((Number) tblHorasExtras.getValueAt(
						tblHorasExtras.getSelectedRow(),
						HorasExtrasTableModel.columnPrecio)).floatValue());

			Float ImporteHoraExtra = (HorasExtras - HorasDescontadas)
					* PrecioHoraExtra;

			tblHorasExtras.setValueAt(ImporteHoraExtra, tblHorasExtras
					.getSelectedRow(), HorasExtrasTableModel.columnImporte);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEmpleado.calculaImporteHoraExtra()", he);
		}
	}

	private void calculaTotalesHorasExtras() {
		try {
			Float totalHorasExtras = ((Number) 0).floatValue();
			Float totalHorasExtrasDescontadas = ((Number) 0).floatValue();
			Float totalImporte = ((Number) 0).floatValue();

			Integer numRows = tblHorasExtras.getRowCount();
			for (Integer actualRow = 0; actualRow < numRows; actualRow++) {
				if (tblHorasExtras.getValueAt(actualRow,
						HorasExtrasTableModel.columnState).equals(
						HorasExtrasTableModel.EditLine)) {
					if (!(tblHorasExtras.getValueAt(actualRow,
							HorasExtrasTableModel.columnHorasExtras).equals("")))
						totalHorasExtras = totalHorasExtras
								+ ((Number) (((DefaultTableModel) tblHorasExtras
										.getModel())
										.getValueAt(
												actualRow,
												HorasExtrasTableModel.columnHorasExtras)))
										.floatValue();
					if (!(tblHorasExtras.getValueAt(actualRow,
							HorasExtrasTableModel.columnHorasDescontadas)
							.equals("")))
						totalHorasExtrasDescontadas = totalHorasExtrasDescontadas
								+ ((Number) (((DefaultTableModel) tblHorasExtras
										.getModel())
										.getValueAt(
												actualRow,
												HorasExtrasTableModel.columnHorasDescontadas)))
										.floatValue();
					if (!(tblHorasExtras.getValueAt(actualRow,
							HorasExtrasTableModel.columnImporte).equals("")))
						totalImporte = totalImporte
								+ ((Number) (((DefaultTableModel) tblHorasExtras
										.getModel()).getValueAt(actualRow,
										HorasExtrasTableModel.columnImporte)))
										.floatValue();
				}
			}
			txtSumaTotalHorasExtras.setValue(totalHorasExtras
					- totalHorasExtrasDescontadas);
			txtSumaImporteHorasExtras.setValue(totalImporte);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEmpleado.calculaTotalesHorasExtras()", he);
		}
	}

	private void calculaTotalDiasVacaciones() {
		try {
			Float totalDiasVacaciones = ((Number) 0).floatValue();

			Integer numRows = tblVacaciones.getRowCount();
			for (Integer actualRow = 0; actualRow < numRows; actualRow++) {
				if (tblVacaciones.getValueAt(actualRow,
						VacacionesTableModel.columnState).equals(
						VacacionesTableModel.EditLine)) {
					if (!(tblVacaciones.getValueAt(actualRow,
							VacacionesTableModel.columnNumDias).equals("")))
						totalDiasVacaciones = totalDiasVacaciones
								+ ((Number) (((DefaultTableModel) tblVacaciones
										.getModel()).getValueAt(actualRow,
										VacacionesTableModel.columnNumDias)))
										.floatValue();
				}
			}
			txtTotalDiasVacaciones.setValue(totalDiasVacaciones);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEmpleado.calculaTotalDiasVacaciones()", he);
		}
	}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlData = new javax.swing.JPanel();
        TabEmpleado = new javax.swing.JTabbedPane();
        pnlData1 = new javax.swing.JPanel();
        lblIdEmpleado = new javax.swing.JLabel();
        lblApellidos = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        lblPoblacion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblCodigoPostal = new javax.swing.JLabel();
        txtCodigoPostal = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblNIF = new javax.swing.JLabel();
        txtNIF = new javax.swing.JTextField();
        txtIdEmpleado = new javax.swing.JFormattedTextField();
        txtPoblacion = new javax.swing.JTextField();
        lblCategoria = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        lblCorreoElectronico = new javax.swing.JLabel();
        txtCorreoElectronico = new javax.swing.JTextField();
        lblIdBanco = new javax.swing.JLabel();
        txtIdBanco = new javax.swing.JFormattedTextField();
        cboBancos = new javax.swing.JComboBox();
        lblIdSucursal = new javax.swing.JLabel();
        txtIdSucursal = new javax.swing.JFormattedTextField();
        cboSucursales = new javax.swing.JComboBox();
        lblDigitoControl = new javax.swing.JLabel();
        txtDigitoControl = new javax.swing.JFormattedTextField();
        lblCuentaBancaria = new javax.swing.JLabel();
        txtCuentaBancaria = new javax.swing.JTextField();
        lblFijo = new javax.swing.JLabel();
        chkFijo = new javax.swing.JCheckBox();
        lblFechaNacimiento = new javax.swing.JLabel();
        txtFechaNacimiento = new javax.swing.JFormattedTextField();
        lblFechaAntiguedad = new javax.swing.JLabel();
        txtFechaAntiguedad = new javax.swing.JFormattedTextField();
        lblIBAN = new javax.swing.JLabel();
        txtIBAN = new javax.swing.JTextField();
        pnlData2 = new javax.swing.JPanel();
        lblComentarios = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentarios = new javax.swing.JTextArea();
        pnlData3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblContratos = new javax.swing.JTable();
        txtImporteLiquidacion = new javax.swing.JFormattedTextField();
        cmdSelectContratosAll = new javax.swing.JButton();
        cmdDeselectContratosAll = new javax.swing.JButton();
        cmdDeleteContratos = new javax.swing.JButton();
        txtSumaTotalLiquidado = new javax.swing.JFormattedTextField();
        lblTotalImporteLiquidado = new javax.swing.JLabel();
        cboCuentaBancariaLiquidacion = new javax.swing.JComboBox();
        txtImporteLiquidacionIrpf = new javax.swing.JFormattedTextField();
        txtFechaAlta = new javax.swing.JFormattedTextField();
        txtFechaBaja = new javax.swing.JFormattedTextField();
        pnlData4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNominas = new javax.swing.JTable();
        txtTotalDevengado = new javax.swing.JFormattedTextField();
        txtImporteIrpf = new javax.swing.JFormattedTextField();
        txtImporteSegSoc = new javax.swing.JFormattedTextField();
        txtImporteSegAutonomo = new javax.swing.JFormattedTextField();
        txtTotalLiquido = new javax.swing.JFormattedTextField();
        cmdSelectNominasAll = new javax.swing.JButton();
        cmdDeselectNominasAll = new javax.swing.JButton();
        cmdDeleteNominas = new javax.swing.JButton();
        lblTotalDevengado = new javax.swing.JLabel();
        txtSumaTotalDevengado = new javax.swing.JFormattedTextField();
        lblIRPF = new javax.swing.JLabel();
        txtSumaIRPF = new javax.swing.JFormattedTextField();
        lblSegSoc = new javax.swing.JLabel();
        txtSumaSegSoc = new javax.swing.JFormattedTextField();
        lblTipoIRPF = new javax.swing.JLabel();
        txtSumaOtros = new javax.swing.JFormattedTextField();
        lblImporteIRPF = new javax.swing.JLabel();
        txtSumaTotalLiquido = new javax.swing.JFormattedTextField();
        txtBonificacion = new javax.swing.JFormattedTextField();
        cboCuentaBancaria = new javax.swing.JComboBox();
        txtImporteEmbargo = new javax.swing.JFormattedTextField();
        lblImporteIRPF1 = new javax.swing.JLabel();
        txtSalarioMedio = new javax.swing.JFormattedTextField();
        pnlData5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHorasExtras = new javax.swing.JTable();
        cmdSelectHorasExtrasAll = new javax.swing.JButton();
        cmdDeselectHorasExtrasAll = new javax.swing.JButton();
        cmdDeleteHorasExtras = new javax.swing.JButton();
        txtHorasExtras = new javax.swing.JFormattedTextField();
        txtHorasDescontadas = new javax.swing.JFormattedTextField();
        txtPreciohe = new javax.swing.JFormattedTextField();
        txtImportehe = new javax.swing.JFormattedTextField();
        lblTotalHorasExtras = new javax.swing.JLabel();
        txtSumaTotalHorasExtras = new javax.swing.JFormattedTextField();
        lblTotalImporteHorasExtras = new javax.swing.JLabel();
        txtSumaImporteHorasExtras = new javax.swing.JFormattedTextField();
        pnlData6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblVacaciones = new javax.swing.JTable();
        cmdSelectVacacionesAll = new javax.swing.JButton();
        cmdDeselectVacacionesAll = new javax.swing.JButton();
        cmdDeleteVacaciones = new javax.swing.JButton();
        txtNumDias = new javax.swing.JFormattedTextField();
        lblTotalDias = new javax.swing.JLabel();
        txtTotalDiasVacaciones = new javax.swing.JFormattedTextField();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        pnlData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TabEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        pnlData1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlData1.setName("pnlData1"); // NOI18N

        lblIdEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdEmpleado.setForeground(new java.awt.Color(255, 0, 0));
        lblIdEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdEmpleado.setText("Id empleado");
        lblIdEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblApellidos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblApellidos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblApellidos.setText("Apellidos");
        lblApellidos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 0, 0));
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombre.setText("Nombre");

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDireccion.setText("Dirección");
        lblDireccion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombre.setAutoscrolls(false);
        txtNombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombre.setName("nombre"); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtApellidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtApellidos.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtApellidos.setAutoscrolls(false);
        txtApellidos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtApellidos.setName("apellidos"); // NOI18N
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });

        lblPoblacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPoblacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPoblacion.setText("Población");
        lblPoblacion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtDireccion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDireccion.setAutoscrolls(false);
        txtDireccion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDireccion.setName("poblacion"); // NOI18N
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        lblCodigoPostal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodigoPostal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoPostal.setText("Código postal");
        lblCodigoPostal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCodigoPostal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodigoPostal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoPostal.setAutoscrolls(false);
        txtCodigoPostal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodigoPostal.setName("codigoPostal"); // NOI18N
        txtCodigoPostal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoPostalKeyTyped(evt);
            }
        });

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTelefono.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTelefono.setText("Teléfono");
        lblTelefono.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTelefono.setAutoscrolls(false);
        txtTelefono.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTelefono.setName("telefono1"); // NOI18N
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        lblNIF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNIF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNIF.setText("NIF");
        lblNIF.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNIF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNIF.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNIF.setAutoscrolls(false);
        txtNIF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNIF.setName("nif"); // NOI18N
        txtNIF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNIFKeyTyped(evt);
            }
        });

        txtIdEmpleado.setEditable(false);
        txtIdEmpleado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdEmpleado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtPoblacion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPoblacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPoblacion.setAutoscrolls(false);
        txtPoblacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPoblacion.setName("poblacion"); // NOI18N
        txtPoblacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPoblacionKeyTyped(evt);
            }
        });

        lblCorreoElectronico.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCorreoElectronico.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCorreoElectronico.setText("E-mail");
        lblCorreoElectronico.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCorreoElectronico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCorreoElectronico.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCorreoElectronico.setAutoscrolls(false);
        txtCorreoElectronico.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCorreoElectronico.setName("correoElectronico"); // NOI18N
        txtCorreoElectronico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
            	txtCorreoElectronicoKeyTyped(evt);
            }
        });
        
        lblCategoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCategoria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCategoria.setText("Categoria");
        lblCategoria.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCategoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCategoria.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCategoria.setAutoscrolls(false);
        txtCategoria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCategoria.setName("categoria"); // NOI18N
        txtCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCategoriaKeyTyped(evt);
            }
        });

        lblIdBanco.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdBanco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdBanco.setText("Banco");

        txtIdBanco.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdBanco.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIdBanco.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdBanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdBancoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdBancoKeyTyped(evt);
            }
        });

        cboBancos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBancos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboBancos.setName("Id subcategoria"); // NOI18N
        cboBancos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBancosActionPerformed(evt);
            }
        });

        lblIdSucursal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdSucursal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdSucursal.setText("Sucursal");

        txtIdSucursal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdSucursal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIdSucursal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdSucursalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdSucursalKeyTyped(evt);
            }
        });

        cboSucursales.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSucursales.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboSucursales.setName("Id subcategoria"); // NOI18N
        cboSucursales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSucursalesActionPerformed(evt);
            }
        });

        lblDigitoControl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDigitoControl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDigitoControl.setText("Dï¿½gito control");
        lblDigitoControl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtDigitoControl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDigitoControl.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDigitoControl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDigitoControl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDigitoControlKeyTyped(evt);
            }
        });

        lblCuentaBancaria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCuentaBancaria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCuentaBancaria.setText("Cuenta bancaria");
        lblCuentaBancaria.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCuentaBancaria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCuentaBancaria.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCuentaBancaria.setAutoscrolls(false);
        txtCuentaBancaria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCuentaBancaria.setName("telefono2"); // NOI18N
        txtCuentaBancaria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCuentaBancariaKeyTyped(evt);
            }
        });

        lblFijo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFijo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFijo.setText("Fijo");

        chkFijo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        chkFijo.setMargin(new java.awt.Insets(0, 0, 2, 2));
        chkFijo.setName("privada"); // NOI18N

        lblFechaNacimiento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaNacimiento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaNacimiento.setText("F. nacimiento");

        txtFechaNacimiento.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtFechaNacimiento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFechaNacimiento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFechaNacimiento.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFechaNacimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaNacimientoFocusLost(evt);
            }
        });

        lblFechaAntiguedad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaAntiguedad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaAntiguedad.setText("F. antigï¿½edad");

        txtFechaAntiguedad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtFechaAntiguedad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFechaAntiguedad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFechaAntiguedad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFechaAntiguedad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaAntiguedadFocusLost(evt);
            }
        });

        lblIBAN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIBAN.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIBAN.setText("IBAN");
        lblIBAN.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtIBAN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIBAN.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIBAN.setAutoscrolls(false);
        txtIBAN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIBAN.setName("iban"); // NOI18N
        txtIBAN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIBANKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlData1Layout = new javax.swing.GroupLayout(pnlData1);
        pnlData1.setLayout(pnlData1Layout);
        pnlData1Layout.setHorizontalGroup(
            pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlData1Layout.createSequentialGroup()
                                        .addGap(290, 290, 290)
                                        .addComponent(lblCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(lblFechaAntiguedad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaAntiguedad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkFijo))
                            .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(cboSucursales, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(lblCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        		.addGroup(pnlData1Layout.createSequentialGroup()
                                        .addComponent(lblCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtCorreoElectronico))
                        		.addGroup(pnlData1Layout.createSequentialGroup()
                                    .addComponent(lblCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtCategoria))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlData1Layout.createSequentialGroup()
                                    .addComponent(lblIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(cboBancos, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlData1Layout.createSequentialGroup()
                                    .addComponent(lblApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtApellidos))
                                .addGroup(pnlData1Layout.createSequentialGroup()
                                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtNombre))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlData1Layout.createSequentialGroup()
                                    .addComponent(lblIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(70, 70, 70)
                                    .addComponent(lblNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        pnlData1Layout.setVerticalGroup(
            pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdEmpleado)
                    .addComponent(txtIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNIF)
                    .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblFechaNacimiento)
                        .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblApellidos)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccion)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPoblacion)
                    .addComponent(lblCodigoPostal)
                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFechaAntiguedad)
                    .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtFechaAntiguedad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkFijo)
                            .addComponent(lblFijo))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCorreoElectronico)
                    .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategoria)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdBanco)
                    .addComponent(txtIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboBancos, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdSucursal)
                    .addComponent(txtIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSucursales, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDigitoControl)
                    .addComponent(txtDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCuentaBancaria)
                    .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblIBAN)
                        .addComponent(txtIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        TabEmpleado.addTab("1. Datos Generales", pnlData1);

        lblComentarios.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblComentarios.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblComentarios.setText("Comentarios");

        txtComentarios.setColumns(20);
        txtComentarios.setRows(5);
        txtComentarios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane1.setViewportView(txtComentarios);

        javax.swing.GroupLayout pnlData2Layout = new javax.swing.GroupLayout(pnlData2);
        pnlData2.setLayout(pnlData2Layout);
        pnlData2Layout.setHorizontalGroup(
            pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblComentarios, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlData2Layout.setVerticalGroup(
            pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData2Layout.createSequentialGroup()
                .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblComentarios))
                    .addGroup(pnlData2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)))
                .addContainerGap())
        );

        TabEmpleado.addTab("2. Observaciones", null, pnlData2, "null");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblContratos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblContratos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblContratos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblContratos.setCellSelectionEnabled(true);
        tblContratos.setRowHeight(18);
        tblContratos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblContratosKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblContratos);

        txtImporteLiquidacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImporteLiquidacion.setPreferredSize(new java.awt.Dimension(0, 0));

        cmdSelectContratosAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdSelectContratosAll.setText("Seleccionar todo");
        cmdSelectContratosAll.setToolTipText("Seleccionar todas las filas");
        cmdSelectContratosAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdSelectContratosAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdSelectContratosAllMousePressed(evt);
            }
        });

        cmdDeselectContratosAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeselectContratosAll.setText("Quitar selecciï¿½n");
        cmdDeselectContratosAll.setToolTipText("Quitar la seleccionar todas las filas");
        cmdDeselectContratosAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeselectContratosAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeselectContratosAllMousePressed(evt);
            }
        });

        cmdDeleteContratos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeleteContratos.setText("Eliminar contratos");
        cmdDeleteContratos.setToolTipText("Eliminar contratos seleccionados");
        cmdDeleteContratos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeleteContratos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeleteContratosMousePressed(evt);
            }
        });

        txtSumaTotalLiquidado.setEditable(false);
        txtSumaTotalLiquidado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSumaTotalLiquidado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSumaTotalLiquidado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaTotalLiquidado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblTotalImporteLiquidado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalImporteLiquidado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalImporteLiquidado.setText("Tot. importe liquidado");

        cboCuentaBancariaLiquidacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboCuentaBancariaLiquidacion.setName("Id subcategoria"); // NOI18N

        txtImporteLiquidacionIrpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImporteLiquidacionIrpf.setPreferredSize(new java.awt.Dimension(0, 0));

        txtFechaAlta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtFechaAlta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFechaAlta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFechaAlta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtFechaBaja.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtFechaBaja.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFechaBaja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFechaBaja.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout pnlData3Layout = new javax.swing.GroupLayout(pnlData3);
        pnlData3.setLayout(pnlData3Layout);
        pnlData3Layout.setHorizontalGroup(
            pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .addComponent(txtImporteLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlData3Layout.createSequentialGroup()
                        .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlData3Layout.createSequentialGroup()
                                .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSumaTotalLiquidado)
                                    .addComponent(lblTotalImporteLiquidado))
                                .addGap(65, 65, 65)
                                .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cboCuentaBancariaLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFechaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlData3Layout.createSequentialGroup()
                                .addComponent(cmdSelectContratosAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdDeselectContratosAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlData3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdDeleteContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData3Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(txtFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData3Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(txtImporteLiquidacionIrpf, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(622, Short.MAX_VALUE)))
        );
        pnlData3Layout.setVerticalGroup(
            pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlData3Layout.createSequentialGroup()
                        .addComponent(lblTotalImporteLiquidado)
                        .addGap(2, 2, 2)
                        .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSumaTotalLiquidado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cboCuentaBancariaLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdDeleteContratos)
                    .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmdSelectContratosAll)
                        .addComponent(cmdDeselectContratosAll)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtImporteLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
            .addGroup(pnlData3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData3Layout.createSequentialGroup()
                    .addContainerGap(352, Short.MAX_VALUE)
                    .addComponent(txtImporteLiquidacionIrpf, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        TabEmpleado.addTab("3. Contratos", pnlData3);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblNominas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblNominas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblNominas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblNominas.setCellSelectionEnabled(true);
        tblNominas.setRowHeight(18);
        tblNominas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblNominasKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblNominas);

        txtTotalDevengado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTotalDevengado.setPreferredSize(new java.awt.Dimension(0, 0));

        txtImporteIrpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImporteIrpf.setPreferredSize(new java.awt.Dimension(0, 0));

        txtImporteSegSoc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImporteSegSoc.setPreferredSize(new java.awt.Dimension(0, 0));

        txtImporteSegAutonomo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImporteSegAutonomo.setPreferredSize(new java.awt.Dimension(0, 0));

        txtTotalLiquido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTotalLiquido.setPreferredSize(new java.awt.Dimension(0, 0));

        cmdSelectNominasAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdSelectNominasAll.setText("Seleccionar todo");
        cmdSelectNominasAll.setToolTipText("Seleccionar todas las filas");
        cmdSelectNominasAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdSelectNominasAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdSelectNominasAllMousePressed(evt);
            }
        });

        cmdDeselectNominasAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeselectNominasAll.setText("Quitar selecciï¿½n");
        cmdDeselectNominasAll.setToolTipText("Quitar la seleccionar todas las filas");
        cmdDeselectNominasAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeselectNominasAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeselectNominasAllMousePressed(evt);
            }
        });

        cmdDeleteNominas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeleteNominas.setText("Eliminar nominas");
        cmdDeleteNominas.setToolTipText("Eliminar nominas seleccionadas");
        cmdDeleteNominas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeleteNominas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeleteNominasMousePressed(evt);
            }
        });

        lblTotalDevengado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalDevengado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalDevengado.setText("Tot. devengado");

        txtSumaTotalDevengado.setEditable(false);
        txtSumaTotalDevengado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSumaTotalDevengado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSumaTotalDevengado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaTotalDevengado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIRPF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIRPF.setText("I.R.P.F.");

        txtSumaIRPF.setEditable(false);
        txtSumaIRPF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSumaIRPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSumaIRPF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblSegSoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSegSoc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSegSoc.setText("S. Social");

        txtSumaSegSoc.setEditable(false);
        txtSumaSegSoc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSumaSegSoc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSumaSegSoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaSegSoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblTipoIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoIRPF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoIRPF.setText("Otros");

        txtSumaOtros.setEditable(false);
        txtSumaOtros.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSumaOtros.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSumaOtros.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaOtros.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblImporteIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblImporteIRPF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblImporteIRPF.setText("Tot. liquido");

        txtSumaTotalLiquido.setEditable(false);
        txtSumaTotalLiquido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSumaTotalLiquido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSumaTotalLiquido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaTotalLiquido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtBonificacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtBonificacion.setPreferredSize(new java.awt.Dimension(0, 0));

        cboCuentaBancaria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboCuentaBancaria.setName("Id subcategoria"); // NOI18N

        txtImporteEmbargo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImporteEmbargo.setPreferredSize(new java.awt.Dimension(0, 0));

        lblImporteIRPF1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblImporteIRPF1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblImporteIRPF1.setText("Sueldo medio");

        txtSalarioMedio.setEditable(false);
        txtSalarioMedio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSalarioMedio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSalarioMedio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSalarioMedio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout pnlData4Layout = new javax.swing.GroupLayout(pnlData4);
        pnlData4.setLayout(pnlData4Layout);
        pnlData4Layout.setHorizontalGroup(
            pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData4Layout.createSequentialGroup()
                        .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlData4Layout.createSequentialGroup()
                                .addComponent(txtTotalDevengado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE))
                            .addGroup(pnlData4Layout.createSequentialGroup()
                                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSumaTotalDevengado)
                                    .addComponent(lblTotalDevengado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblIRPF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSumaIRPF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblSegSoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSumaSegSoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlData4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSumaOtros, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(lblTipoIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblImporteIRPF, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSumaTotalLiquido, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblImporteIRPF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSalarioMedio))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(pnlData4Layout.createSequentialGroup()
                        .addComponent(cmdSelectNominasAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDeselectNominasAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDeleteNominas, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                        .addComponent(cboCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData4Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(txtImporteIrpf, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(622, Short.MAX_VALUE)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData4Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(txtImporteSegSoc, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(612, Short.MAX_VALUE)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData4Layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(txtImporteSegAutonomo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(602, Short.MAX_VALUE)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData4Layout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addComponent(txtTotalLiquido, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(592, Short.MAX_VALUE)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData4Layout.createSequentialGroup()
                    .addGap(62, 62, 62)
                    .addComponent(txtBonificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(582, Short.MAX_VALUE)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData4Layout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addComponent(txtImporteEmbargo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(592, Short.MAX_VALUE)))
        );

        pnlData4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtSumaIRPF, txtSumaOtros, txtSumaSegSoc, txtSumaTotalLiquido});

        pnlData4Layout.setVerticalGroup(
            pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData4Layout.createSequentialGroup()
                        .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                                .addComponent(txtTotalDevengado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
                            .addGroup(pnlData4Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlData4Layout.createSequentialGroup()
                                .addComponent(lblTotalDevengado)
                                .addGap(2, 2, 2)
                                .addComponent(txtSumaTotalDevengado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblIRPF)
                                .addComponent(lblSegSoc))
                            .addGroup(pnlData4Layout.createSequentialGroup()
                                .addComponent(lblTipoIRPF)
                                .addGap(2, 2, 2)
                                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSumaSegSoc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSumaIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSumaOtros, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSumaTotalLiquido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblImporteIRPF)
                                .addComponent(lblImporteIRPF1))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtSalarioMedio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSelectNominasAll)
                    .addComponent(cmdDeselectNominasAll)
                    .addComponent(cmdDeleteNominas)
                    .addComponent(cboCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                    .addContainerGap(315, Short.MAX_VALUE)
                    .addComponent(txtImporteIrpf, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(47, 47, 47)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                    .addContainerGap(325, Short.MAX_VALUE)
                    .addComponent(txtImporteSegSoc, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(37, 37, 37)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                    .addContainerGap(335, Short.MAX_VALUE)
                    .addComponent(txtImporteSegAutonomo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(27, 27, 27)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                    .addContainerGap(345, Short.MAX_VALUE)
                    .addComponent(txtTotalLiquido, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(17, 17, 17)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                    .addContainerGap(355, Short.MAX_VALUE)
                    .addComponent(txtBonificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(7, 7, 7)))
            .addGroup(pnlData4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData4Layout.createSequentialGroup()
                    .addContainerGap(345, Short.MAX_VALUE)
                    .addComponent(txtImporteEmbargo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(17, 17, 17)))
        );

        TabEmpleado.addTab("4. nóminas", pnlData4);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblHorasExtras.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHorasExtras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHorasExtras.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblHorasExtras.setCellSelectionEnabled(true);
        tblHorasExtras.setRowHeight(18);
        tblHorasExtras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblHorasExtrasKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblHorasExtras);

        cmdSelectHorasExtrasAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdSelectHorasExtrasAll.setText("Seleccionar todo");
        cmdSelectHorasExtrasAll.setToolTipText("Seleccionar todas las filas");
        cmdSelectHorasExtrasAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdSelectHorasExtrasAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdSelectHorasExtrasAllMousePressed(evt);
            }
        });

        cmdDeselectHorasExtrasAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeselectHorasExtrasAll.setText("Quitar selecciï¿½n");
        cmdDeselectHorasExtrasAll.setToolTipText("Quitar la seleccionar todas las filas");
        cmdDeselectHorasExtrasAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeselectHorasExtrasAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeselectHorasExtrasAllMousePressed(evt);
            }
        });

        cmdDeleteHorasExtras.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeleteHorasExtras.setText("Eliminar horas extras");
        cmdDeleteHorasExtras.setToolTipText("Eliminar horas extras seleccionadas");
        cmdDeleteHorasExtras.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeleteHorasExtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeleteHorasExtrasMousePressed(evt);
            }
        });

        txtHorasExtras.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtHorasExtras.setPreferredSize(new java.awt.Dimension(0, 0));

        txtHorasDescontadas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtHorasDescontadas.setPreferredSize(new java.awt.Dimension(0, 0));

        txtPreciohe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtPreciohe.setPreferredSize(new java.awt.Dimension(0, 0));

        txtImportehe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImportehe.setPreferredSize(new java.awt.Dimension(0, 0));

        lblTotalHorasExtras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalHorasExtras.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalHorasExtras.setText("Tot. horas extras");

        txtSumaTotalHorasExtras.setEditable(false);
        txtSumaTotalHorasExtras.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSumaTotalHorasExtras.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSumaTotalHorasExtras.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaTotalHorasExtras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblTotalImporteHorasExtras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalImporteHorasExtras.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalImporteHorasExtras.setText("Importe total");

        txtSumaImporteHorasExtras.setEditable(false);
        txtSumaImporteHorasExtras.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSumaImporteHorasExtras.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSumaImporteHorasExtras.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaImporteHorasExtras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout pnlData5Layout = new javax.swing.GroupLayout(pnlData5);
        pnlData5.setLayout(pnlData5Layout);
        pnlData5Layout.setHorizontalGroup(
            pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .addGroup(pnlData5Layout.createSequentialGroup()
                        .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSumaTotalHorasExtras)
                            .addComponent(lblTotalHorasExtras))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotalImporteHorasExtras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSumaImporteHorasExtras, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                        .addGap(207, 207, 207)
                        .addComponent(txtPreciohe, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtImportehe, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlData5Layout.createSequentialGroup()
                        .addComponent(cmdSelectHorasExtrasAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDeselectHorasExtrasAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDeleteHorasExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData5Layout.createSequentialGroup()
                    .addGap(322, 322, 322)
                    .addComponent(txtHorasExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(322, Short.MAX_VALUE)))
            .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData5Layout.createSequentialGroup()
                    .addContainerGap(332, Short.MAX_VALUE)
                    .addComponent(txtHorasDescontadas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(312, 312, 312)))
        );
        pnlData5Layout.setVerticalGroup(
            pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPreciohe, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtImportehe, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlData5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlData5Layout.createSequentialGroup()
                                .addComponent(lblTotalImporteHorasExtras)
                                .addGap(2, 2, 2)
                                .addComponent(txtSumaImporteHorasExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData5Layout.createSequentialGroup()
                                .addComponent(lblTotalHorasExtras)
                                .addGap(2, 2, 2)
                                .addComponent(txtSumaTotalHorasExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSelectHorasExtrasAll)
                    .addComponent(cmdDeselectHorasExtrasAll)
                    .addComponent(cmdDeleteHorasExtras))
                .addGap(28, 28, 28))
            .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData5Layout.createSequentialGroup()
                    .addGap(181, 181, 181)
                    .addComponent(txtHorasExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(181, Short.MAX_VALUE)))
            .addGroup(pnlData5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData5Layout.createSequentialGroup()
                    .addContainerGap(191, Short.MAX_VALUE)
                    .addComponent(txtHorasDescontadas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(171, 171, 171)))
        );

        TabEmpleado.addTab("5. Horas extras", pnlData5);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblVacaciones.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblVacaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblVacaciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblVacaciones.setCellSelectionEnabled(true);
        tblVacaciones.setRowHeight(18);
        tblVacaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblVacacionesKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tblVacaciones);

        cmdSelectVacacionesAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdSelectVacacionesAll.setText("Seleccionar todo");
        cmdSelectVacacionesAll.setToolTipText("Seleccionar todas las filas");
        cmdSelectVacacionesAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdSelectVacacionesAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdSelectVacacionesAllMousePressed(evt);
            }
        });

        cmdDeselectVacacionesAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeselectVacacionesAll.setText("Quitar selecciï¿½n");
        cmdDeselectVacacionesAll.setToolTipText("Quitar la seleccionar todas las filas");
        cmdDeselectVacacionesAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeselectVacacionesAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeselectVacacionesAllMousePressed(evt);
            }
        });

        cmdDeleteVacaciones.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeleteVacaciones.setText("Eliminar vacaciones");
        cmdDeleteVacaciones.setToolTipText("Eliminar vacaciones seleccionadas");
        cmdDeleteVacaciones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeleteVacaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeleteVacacionesMousePressed(evt);
            }
        });

        txtNumDias.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtNumDias.setPreferredSize(new java.awt.Dimension(0, 0));

        lblTotalDias.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalDias.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalDias.setText("Total dï¿½as");

        txtTotalDiasVacaciones.setEditable(false);
        txtTotalDiasVacaciones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotalDiasVacaciones.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtTotalDiasVacaciones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalDiasVacaciones.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout pnlData6Layout = new javax.swing.GroupLayout(pnlData6);
        pnlData6.setLayout(pnlData6Layout);
        pnlData6Layout.setHorizontalGroup(
            pnlData6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlData6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .addGroup(pnlData6Layout.createSequentialGroup()
                        .addComponent(cmdSelectVacacionesAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDeselectVacacionesAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDeleteVacaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlData6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtTotalDiasVacaciones)
                        .addComponent(lblTotalDias)))
                .addContainerGap())
            .addGroup(pnlData6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData6Layout.createSequentialGroup()
                    .addGap(322, 322, 322)
                    .addComponent(txtNumDias, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(322, Short.MAX_VALUE)))
        );
        pnlData6Layout.setVerticalGroup(
            pnlData6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalDias)
                .addGap(2, 2, 2)
                .addComponent(txtTotalDiasVacaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSelectVacacionesAll)
                    .addComponent(cmdDeselectVacacionesAll)
                    .addComponent(cmdDeleteVacaciones))
                .addContainerGap())
            .addGroup(pnlData6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData6Layout.createSequentialGroup()
                    .addGap(181, 181, 181)
                    .addComponent(txtNumDias, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(181, Short.MAX_VALUE)))
        );

        TabEmpleado.addTab("6. Vacaciones", pnlData6);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/cancel.png"))); // NOI18N
        btnCancel.setToolTipText("Cancelar");
        btnCancel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCancelMousePressed(evt);
            }
        });

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/ok.png"))); // NOI18N
        btnOk.setToolTipText("Aceptar");
        btnOk.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnOkMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addGap(9, 9, 9)
                .addComponent(btnOk)
                .addContainerGap())
            .addComponent(TabEmpleado, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                .addComponent(TabEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel)
                    .addComponent(btnOk))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 653, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtIBANKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIBANKeyTyped
        try {
                Util.keyTyped(txtIBAN, evt, 4);
        } catch (RuntimeException he) {
                Message.ShowRuntimeError(getParentFrame(),
                                "FrmEmpleado.txtIBANKeyTyped()", he);
        }
    }//GEN-LAST:event_txtIBANKeyTyped

	private void txtFechaAntiguedadFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtFechaAntiguedad);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtFechaAntiguedadFocusLost()", he);
		}

	}

	private void txtFechaNacimientoFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtFechaNacimiento);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtFechaNacimientoFocusLost()", he);
		}
	}

	void cmdDeleteVacacionesMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblVacaciones.getRowCount(); actualrow++) {
				if ((Boolean) tblVacaciones.getValueAt(actualrow,
						VacacionesTableModel.columnSelect) == true) {
					((DefaultTableModel) tblVacaciones.getModel())
							.removeRow(actualrow);
					actualrow--;
				}
			}
			calculaTotalDiasVacaciones();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdDeleteVacacionesMousePressed()", he);
		}
	}

	private void cmdSelectVacacionesAllMousePressed(
			java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblVacaciones.getRowCount(); actualrow++)
				tblVacaciones.setValueAt(true, actualrow,
						VacacionesTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdSelectVacacionesAllMousePressed()", he);
		}
	}

	private void cmdDeselectVacacionesAllMousePressed(
			java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblVacaciones.getRowCount(); actualrow++)
				tblVacaciones.setValueAt(false, actualrow,
						VacacionesTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdDeselectVacacionesAllMousePressed()", he);
		}
	}

	private void tblVacacionesKeyPressed(java.awt.event.KeyEvent evt) {
		try {
			if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				if ((tblVacaciones.getSelectedRow() == ((DefaultTableModel) tblVacaciones
						.getModel()).getRowCount() - 1)
						&& (tblVacaciones.getValueAt(tblVacaciones
								.getSelectedRow(),
								VacacionesTableModel.columnState)
								.equals(VacacionesTableModel.EditLine))) {
					if ((tblVacaciones.getSelectedColumn() == tblVacaciones
							.getColumnCount() - 1)) {
						newLineVacaciones();
						evt.setKeyCode(0);
					} else
						evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
				} else
					evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
			} else {
				if ((evt.getKeyCode() == 32 || evt.getKeyCode() == 164)
						|| (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57)
						|| (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 122))
					tblVacaciones.setValueAt("",
							tblVacaciones.getSelectedRow(), tblVacaciones
									.getSelectedColumn());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.tblVacacionesKeyPressed()", he);
		}
	}

	void cmdDeleteHorasExtrasMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblHorasExtras
					.getRowCount(); actualrow++) {
				if ((Boolean) tblHorasExtras.getValueAt(actualrow,
						HorasExtrasTableModel.columnSelect) == true) {
					((DefaultTableModel) tblHorasExtras.getModel())
							.removeRow(actualrow);
					actualrow--;
				}
			}
			calculaTotalesHorasExtras();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdDeleteHorasExtrasMousePressed()", he);
		}
	}

	private void cmdSelectHorasExtrasAllMousePressed(
			java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblHorasExtras
					.getRowCount(); actualrow++)
				tblHorasExtras.setValueAt(true, actualrow,
						HorasExtrasTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdSelectHorasExtrasAllMousePressed()", he);
		}
	}

	private void cmdDeselectHorasExtrasAllMousePressed(
			java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblHorasExtras
					.getRowCount(); actualrow++)
				tblHorasExtras.setValueAt(false, actualrow,
						HorasExtrasTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdDeselectHorasExtrasAllMousePressed()", he);
		}
	}

	private void tblHorasExtrasKeyPressed(java.awt.event.KeyEvent evt) {
		try {
			if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				if ((tblHorasExtras.getSelectedRow() == ((DefaultTableModel) tblHorasExtras
						.getModel()).getRowCount() - 1)
						&& (tblHorasExtras.getValueAt(tblHorasExtras
								.getSelectedRow(),
								HorasExtrasTableModel.columnState)
								.equals(HorasExtrasTableModel.EditLine))) {
					if ((tblHorasExtras.getSelectedColumn() == tblHorasExtras
							.getColumnCount() - 1)) {
						newLineHorasExtras();
						evt.setKeyCode(0);
					} else
						evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
				} else
					evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
			} else {
				if ((evt.getKeyCode() == 32 || evt.getKeyCode() == 164)
						|| (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57)
						|| (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 122))
					tblHorasExtras.setValueAt("", tblHorasExtras
							.getSelectedRow(), tblHorasExtras
							.getSelectedColumn());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.tblHorasExtrasKeyPressed()", he);
		}
	}

	void cmdDeleteNominasMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblNominas.getRowCount(); actualrow++) {
				if ((Boolean) tblNominas.getValueAt(actualrow,
						NominasTableModel.columnSelect) == true) {
					((DefaultTableModel) tblNominas.getModel())
							.removeRow(actualrow);
					actualrow--;
				}
			}
			calculaTotalesNominas();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdDeleteNominasMousePressed()", he);
		}
	}

	private void cmdSelectNominasAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblNominas.getRowCount(); actualrow++)
				tblNominas.setValueAt(true, actualrow,
						NominasTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdSelectNominasAllMousePressed()", he);
		}
	}

	private void cmdDeselectNominasAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblNominas.getRowCount(); actualrow++)
				tblNominas.setValueAt(false, actualrow,
						NominasTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdDeselectNominasAllMousePressed()", he);
		}
	}

	private void tblNominasKeyPressed(java.awt.event.KeyEvent evt) {
		try {
			if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				if ((tblNominas.getSelectedRow() == ((DefaultTableModel) tblNominas
						.getModel()).getRowCount() - 1)
						&& (tblNominas.getValueAt(tblNominas.getSelectedRow(),
								NominasTableModel.columnState)
								.equals(NominasTableModel.EditLine))) {
					if ((tblNominas.getSelectedColumn() == tblNominas
							.getColumnCount() - 1)) {
						newLineNominas();
						evt.setKeyCode(0);
					} else
						evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
				} else
					evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
			} else {
				if ((evt.getKeyCode() == 32 || evt.getKeyCode() == 164)
						|| (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57)
						|| (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 122))
					tblNominas.setValueAt("", tblNominas.getSelectedRow(),
							tblNominas.getSelectedColumn());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.tblNominasKeyPressed()", he);
		}
	}

	void cmdDeleteContratosMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblContratos.getRowCount(); actualrow++) {
				if ((Boolean) tblContratos.getValueAt(actualrow,
						ContratosTableModel.columnSelect) == true) {
					((DefaultTableModel) tblContratos.getModel())
							.removeRow(actualrow);
					actualrow--;
				}
			}
			calculaTotalLiquidado();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdDeleteContratosMousePressed()", he);
		}
	}

	private void cmdSelectContratosAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblContratos.getRowCount(); actualrow++)
				tblContratos.setValueAt(true, actualrow,
						ContratosTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdSelectContratosAllMousePressed()", he);
		}
	}

	private void cmdDeselectContratosAllMousePressed(
			java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblContratos.getRowCount(); actualrow++)
				tblContratos.setValueAt(false, actualrow,
						ContratosTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cmdDeselectContratosAllMousePressed()", he);
		}
	}

	private void tblContratosKeyPressed(java.awt.event.KeyEvent evt) {
		try {
			if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				if ((tblContratos.getSelectedRow() == ((DefaultTableModel) tblContratos
						.getModel()).getRowCount() - 1)
						&& (tblContratos.getValueAt(tblContratos
								.getSelectedRow(),
								ContratosTableModel.columnState)
								.equals(ContratosTableModel.EditLine))) {
					if ((tblContratos.getSelectedColumn() == tblContratos
							.getColumnCount() - 1)) {
						newLineContratos();
						evt.setKeyCode(0);
					} else
						evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
				} else
					evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
			} else {
				if ((evt.getKeyCode() == 32 || evt.getKeyCode() == 164)
						|| (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57)
						|| (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 122))
					tblContratos.setValueAt("", tblContratos.getSelectedRow(),
							tblContratos.getSelectedColumn());
			}

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEmpleado.tblContratosKeyPressed()", he);
		}
	}

	private void cboSucursalesActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingSucursal) {
				changingSucursal = true;
				if (cboSucursales.getSelectedIndex() != -1) {
					List<?> sucursaleslist = entity.executeQuery(this, "*",
							"Bancos", "nombreSucursal = '"
									+ cboSucursales.getSelectedItem()
									+ "' and nombreBanco = '"
									+ cboBancos.getSelectedItem() + "'", "");
					if (sucursaleslist.size() > 0) {
						Bancos sucursalesItem = (Bancos) sucursaleslist.get(0);
						txtIdSucursal.setValue(sucursalesItem.getId()
								.getIdSucursal());
					}
				}
				changingSucursal = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cboSucursalesActionPerformed()", he);
		}
	}

	private void cboBancosActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingBanco) {
				changingBanco = true;
				if (cboBancos.getSelectedIndex() != -1) {
					List<?> bancoslist = entity.BancoFindByNombreBanco(this,
							(String) cboBancos.getSelectedItem());
					if (bancoslist.size() > 0) {
						Bancos bancosItem = (Bancos) bancoslist.get(0);
						txtIdBanco.setValue(bancosItem.getId().getIdBanco());
						loadSucursales(txtIdBanco.getText());
					}
				}
				changingBanco = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.cboBancosActionPerformed()", he);
		}
	}

	private void txtIdSucursalKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeSucursal();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtIdSucursalKeyReleased()", he);
		}
	}

	private void txtIdBancoKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeBanco();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtIdBancoKeyReleased()", he);
		}
	}

	private void btnOkMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			saveData(true);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.btnOkMousePressed()", he);
		} catch (ParseException e) {
			Message.ShowParseError(getParentFrame(),
					"FrmEmpleado.btnOkMousePressed()", e);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.empleado);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.btnCancelMousePressed()", he);
		}
	}

	private void txtCuentaBancariaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaBancaria, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtCuentaBancariaKeyTyped()", he);
		}
	}

	private void txtDigitoControlKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDigitoControl, evt, 2);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtDigitoControlKeyTyped()", he);
		}
	}

	private void txtIdSucursalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdSucursal, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtIdSucursalKeyTyped()", he);
		}
	}

	private void txtIdBancoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdBanco, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtIdBancoKeyTyped()", he);
		}
	}

	private void txtCategoriaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCategoria, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtCategoriaKeyTyped()", he);
		}
	}
	
	private void txtCorreoElectronicoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCorreoElectronico, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtCorreoElectronicoKeyTyped()", he);
		}
	}

	private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtTelefono, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtTelefonoKeyTyped()", he);
		}
	}

	private void txtCodigoPostalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCodigoPostal, evt, 6);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtCodigoPostalKeyTyped()", he);
		}
	}

	private void txtPoblacionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtPoblacion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtPoblacionKeyTyped()", he);
		}
	}

	private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDireccion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtDireccionKeyTyped()", he);
		}
	}

	private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtApellidos, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtApellidosKeyTyped()", he);
		}
	}

	private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNombre, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtNombreKeyTyped()", he);
		}
	}

	private void txtNIFKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNIF, evt, 12);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEmpleado.txtNIFKeyTyped()", he);
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabEmpleado;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private static javax.swing.JComboBox cboBancos;
    private static javax.swing.JComboBox cboCuentaBancaria;
    private static javax.swing.JComboBox cboCuentaBancariaLiquidacion;
    private static javax.swing.JComboBox cboSucursales;
    private javax.swing.JCheckBox chkFijo;
    private javax.swing.JButton cmdDeleteContratos;
    private javax.swing.JButton cmdDeleteHorasExtras;
    private javax.swing.JButton cmdDeleteNominas;
    private javax.swing.JButton cmdDeleteVacaciones;
    private javax.swing.JButton cmdDeselectContratosAll;
    private javax.swing.JButton cmdDeselectHorasExtrasAll;
    private javax.swing.JButton cmdDeselectNominasAll;
    private javax.swing.JButton cmdDeselectVacacionesAll;
    private javax.swing.JButton cmdSelectContratosAll;
    private javax.swing.JButton cmdSelectHorasExtrasAll;
    private javax.swing.JButton cmdSelectNominasAll;
    private javax.swing.JButton cmdSelectVacacionesAll;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCorreoElectronico;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblComentarios;
    private javax.swing.JLabel lblCuentaBancaria;
    private javax.swing.JLabel lblDigitoControl;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFechaAntiguedad;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblFijo;
    private javax.swing.JLabel lblIBAN;
    private javax.swing.JLabel lblIRPF;
    private javax.swing.JLabel lblIdBanco;
    private javax.swing.JLabel lblIdEmpleado;
    private javax.swing.JLabel lblIdSucursal;
    private javax.swing.JLabel lblImporteIRPF;
    private javax.swing.JLabel lblImporteIRPF1;
    private javax.swing.JLabel lblNIF;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPoblacion;
    private javax.swing.JLabel lblSegSoc;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTipoIRPF;
    private javax.swing.JLabel lblTotalDevengado;
    private javax.swing.JLabel lblTotalDias;
    private javax.swing.JLabel lblTotalHorasExtras;
    private javax.swing.JLabel lblTotalImporteHorasExtras;
    private javax.swing.JLabel lblTotalImporteLiquidado;
    private javax.swing.JPanel pnlData;
    public javax.swing.JPanel pnlData1;
    private javax.swing.JPanel pnlData2;
    private javax.swing.JPanel pnlData3;
    private javax.swing.JPanel pnlData4;
    private javax.swing.JPanel pnlData5;
    private javax.swing.JPanel pnlData6;
    private static javax.swing.JTable tblContratos;
    private static javax.swing.JTable tblHorasExtras;
    private static javax.swing.JTable tblNominas;
    private static javax.swing.JTable tblVacaciones;
    private javax.swing.JTextField txtApellidos;
    private static javax.swing.JFormattedTextField txtBonificacion;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextArea txtComentarios;
    private javax.swing.JTextField txtCuentaBancaria;
    private javax.swing.JFormattedTextField txtDigitoControl;
    private javax.swing.JTextField txtDireccion;
    private static javax.swing.JFormattedTextField txtFechaAlta;
    private javax.swing.JFormattedTextField txtFechaAntiguedad;
    private static javax.swing.JFormattedTextField txtFechaBaja;
    private javax.swing.JFormattedTextField txtFechaNacimiento;
    private static javax.swing.JFormattedTextField txtHorasDescontadas;
    private static javax.swing.JFormattedTextField txtHorasExtras;
    private javax.swing.JTextField txtIBAN;
    private static javax.swing.JFormattedTextField txtIdBanco;
    private javax.swing.JFormattedTextField txtIdEmpleado;
    private static javax.swing.JFormattedTextField txtIdSucursal;
    private static javax.swing.JFormattedTextField txtImporteEmbargo;
    private static javax.swing.JFormattedTextField txtImporteIrpf;
    private static javax.swing.JFormattedTextField txtImporteLiquidacion;
    private static javax.swing.JFormattedTextField txtImporteLiquidacionIrpf;
    private static javax.swing.JFormattedTextField txtImporteSegAutonomo;
    private static javax.swing.JFormattedTextField txtImporteSegSoc;
    private static javax.swing.JFormattedTextField txtImportehe;
    private javax.swing.JTextField txtNIF;
    private javax.swing.JTextField txtNombre;
    private static javax.swing.JFormattedTextField txtNumDias;
    private javax.swing.JTextField txtPoblacion;
    private static javax.swing.JFormattedTextField txtPreciohe;
    private javax.swing.JFormattedTextField txtSalarioMedio;
    private javax.swing.JFormattedTextField txtSumaIRPF;
    private javax.swing.JFormattedTextField txtSumaImporteHorasExtras;
    private javax.swing.JFormattedTextField txtSumaOtros;
    private javax.swing.JFormattedTextField txtSumaSegSoc;
    private javax.swing.JFormattedTextField txtSumaTotalDevengado;
    private javax.swing.JFormattedTextField txtSumaTotalHorasExtras;
    private javax.swing.JFormattedTextField txtSumaTotalLiquidado;
    private javax.swing.JFormattedTextField txtSumaTotalLiquido;
    private javax.swing.JTextField txtTelefono;
    private static javax.swing.JFormattedTextField txtTotalDevengado;
    private javax.swing.JFormattedTextField txtTotalDiasVacaciones;
    private static javax.swing.JFormattedTextField txtTotalLiquido;
    // End of variables declaration//GEN-END:variables

}


