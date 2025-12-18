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

import entitiesPackage.Calendario;
import entitiesPackage.CalendarioId;
import entitiesPackage.Categorias;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;
import entitiesPackage.Precios;
import entitiesPackage.PreciosId;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmSemana extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	public class PreciosTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;;

		final public static int NormalType = 0;
		final public static int Float2Type = 1;
		final public static int Float4Type = 2;
		final public static int ComboType = 3;
		final public static int CheckType = 4;
		final public static int StateType = 5;

		final public static String NewLine = "[Adding]";
		final public static String EditLine = "[Editing]";
		final public static String DeleteLine = "[Deleting]";

		final public static int columnSelect = 0;
		final public static int columnState = 1;
		final public static int columnIdCategoria = 2;
		final public static int columnCategoriaDesc = 3;
		final public static int columnPrecio = 4;

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

		public PreciosTableModel() {

			super(null, new String[] { "Select", "State", "IdCategoria",
					"CategoriaDesc", "Precio" });

			columns = new Vector<ColumnData>();
			columns.add(new ColumnData("Select", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					CheckType, null, null));
			columns.add(new ColumnData("State", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					StateType, null, null));
			columns
					.add(new ColumnData("IdCategoria", "Id. categoria",
							EntityType.NumberWidth, SwingConstants.RIGHT, 0,
							null, null));
			columns.add(new ColumnData("CategoriaDesc", "descripci�n",
					EntityType.ComboWidth, SwingConstants.LEFT, ComboType,
					cboCategoriaDesc, null));
			columns.add(new ColumnData("Precio", "Precio",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtPrecio, "#,##0.00"));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (!changingState && !addLine)
						changeEditStatePrecios();
					if (e.getColumn() == columnIdCategoria) {
						changeCategoria();
					}
				}
			});
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			switch (columna) {
			case columnIdCategoria: {
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
			}
			return super.getValueAt(fila, columna);
		}

	}

	Calendario calendario;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private boolean OnNew = false;
	private Entity entity = new Entity();

	static boolean changingState = false;
	static boolean addLine = false;
	static boolean changingCategoria = false;

	public java.awt.Frame getParentFrame() {
		return FrmSemana.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmSemana.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmSemana() {
		initComponents();
	}

	public FrmSemana(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmSemana.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent, "FrmSemana()", he);
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
			Message
					.ShowRuntimeError(parentFrame, "FrmSemana.configureKeys",
							he);
		}
	}

	private void prepareTablePrecios() {
		try {
			loadCategorias();

			PreciosTableModel modelDetalle = new PreciosTableModel();
			tblPrecios.setModel(modelDetalle);
			tblPrecios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			for (int k = 0; k < modelDetalle.columns.size(); k++) {

				Object renderer = null;
				DefaultCellEditor editor = null;

				Integer editortype = modelDetalle.columns.get(k).m_editortype;
				switch (editortype) {

				case PreciosTableModel.NormalType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelDetalle.columns.get(k).m_alignment);

					final JTextField field = new JTextField();
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);
						}
					});
					break;
				}
				case PreciosTableModel.Float2Type: {
					renderer = new Float2Renderer();
					final JFormattedTextField field = (JFormattedTextField) modelDetalle.columns
							.get(k).m_editor;
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);
						}
					});
					break;
				}
				case PreciosTableModel.Float4Type: {
					renderer = new Float4Renderer();
					final JFormattedTextField field = (JFormattedTextField) modelDetalle.columns
							.get(k).m_editor;
					editor = new DefaultCellEditor(field);
					editor.setClickCountToStart(1);
					field.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							field.selectAll();
						}

						public void focusLost(FocusEvent e) {
							field.select(0, 0);
						}
					});
					break;
				}
				case PreciosTableModel.ComboType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelDetalle.columns.get(k).m_alignment);
					editor = new DefaultCellEditor(
							(JComboBox) modelDetalle.columns.get(k).m_editor);
					break;
				}
				case PreciosTableModel.CheckType: {
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
							.setHorizontalAlignment(modelDetalle.columns.get(k).m_alignment);
					editor = new DefaultCellEditor(check);
					break;
				}
				case PreciosTableModel.StateType: {

					final JLabel stateIcon = new JLabel();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JLabel getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							if (value.equals(PreciosTableModel.NewLine))
								stateIcon.setIcon(new ImageIcon(getClass()
										.getResource(
												"/imagesPackage/addline.png")));
							else if (value.equals(PreciosTableModel.EditLine))
								stateIcon
										.setIcon(new ImageIcon(
												getClass()
														.getResource(
																"/imagesPackage/editline.png")));

							return stateIcon;
						}
					};

					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelDetalle.columns.get(k).m_alignment);
					break;
				}
				}
				TableColumn column = tblPrecios.getColumn(modelDetalle.columns
						.get(k).m_name);
				column.setHeaderValue(modelDetalle.columns.get(k).m_title);
				column.setPreferredWidth(modelDetalle.columns.get(k).m_width);
				column.setCellRenderer((TableCellRenderer) renderer);
				if (editor != null) {
					column.setCellEditor(editor);
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.prepareTablePrecios()", he);
		}
	}

	public void newData() {
		try {
			this.calendario = new Calendario();
			prepareTablePrecios();
			txtAno.setValue(session.getEjercicio().getEjercicio());
			txtSemana.setText("");
			txtDesdeFecha.setText("");
			txtHastaFecha.setText("");
			txtPorcKilosInutilizados.setText("");
			txtRefAutoControl.setText("");
			Integer numRows = tblPrecios.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblPrecios.getModel()).removeRow(0);
			newLinePrecios();
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmSemana.newData()", he);
		}
	}

	private void newLinePrecios() {
		try {
			if (tblPrecios.getRowCount() > 0) {
				if (!(tblPrecios.getValueAt(tblPrecios.getSelectedRow(),
						PreciosTableModel.columnIdCategoria).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblPrecios.getModel())
							.addRow(new Object[] { false,
									PreciosTableModel.NewLine, "", "", "" });
					tblPrecios.changeSelection(tblPrecios.getSelectedRow() + 1,
							PreciosTableModel.columnIdCategoria, false, false);
					tblPrecios.editCellAt(tblPrecios.getSelectedRow() + 1,
							PreciosTableModel.columnIdCategoria);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblPrecios.getModel())
						.addRow(new Object[] { false,
								PreciosTableModel.NewLine, "", "", "" });
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmSemana.newLinePrecios()",
					he);
		}
	}

	private static void changeEditStatePrecios() {
		try {
			if (!changingState) {
				if (tblPrecios.getSelectedRow() != -1) {
					changingState = true;
					tblPrecios.setValueAt(PreciosTableModel.EditLine,
							tblPrecios.getSelectedRow(),
							PreciosTableModel.columnState);
					changingState = false;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.changeEditStatePrecios()", he);
		}
	}

	public void loadData(Calendario entity) {
		try {
			this.calendario = entity;
			prepareTablePrecios();
			txtAno.setValue(entity.getId().getEjercicios().getEjercicio());
			txtSemana.setValue(entity.getId().getSemana());
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
			if (entity.getDesdeFecha() != null) {
				String strFechaDesde = df.format(entity.getDesdeFecha());
				txtDesdeFecha.setValue(strFechaDesde);
			} else
				txtDesdeFecha.setValue(null);
			if (entity.getHastaFecha() != null) {
				String strFechaHasta = df.format(entity.getHastaFecha());
				txtHastaFecha.setValue(strFechaHasta);
			} else
				txtHastaFecha.setValue(null);
			txtPorcKilosInutilizados
					.setValue(entity.getPorcKilosInutilizados());
			txtRefAutoControl.setText(entity.getRefAutoControl());
			txtSemana.setEditable(false);
			loadDataPrecios();
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmSemana.loadData()", he);
		}
	}

	private void loadCategorias() {
		try {
			cboCategoriaDesc.removeAllItems();
			List<?> categoriasList = entity.CategoriaFindAll(this, true);

			for (Object o : categoriasList) {
				Categorias categoria = (Categorias) o;
				cboCategoriaDesc.addItem(categoria.getNombreCategoria());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmSemana.loadCategorias()",
					he);
		}
	}

	private void loadDataPrecios() {

		try {
			Integer numRows = tblPrecios.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblPrecios.getModel()).removeRow(0);

			List<?> lineasList = entity.executeQuery(this, "*", "Precios",
					"id.semana = " + calendario.getId().getSemana(),
					"id.idCategoria");

			if (lineasList.size() == 0)
				newLinePrecios();
			else {
				for (Object linea : lineasList) {
					Precios precio = (Precios) linea;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(PreciosTableModel.EditLine);
					oneRow.add(precio.getId().getIdCategoria());
					Categorias categoria = entity.CategoriaFindByIdCategoria(
							this, precio.getId().getIdCategoria());
					if (categoria != null)
						cboCategoriaDesc.setSelectedItem(categoria
								.getNombreCategoria());
					oneRow.add(cboCategoriaDesc.getSelectedItem());
					oneRow.add(precio.getPrecio());
					((DefaultTableModel) tblPrecios.getModel()).addRow(oneRow);
				}
			}

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.loadDataPrecios()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {
		try {
			if (validateData()) {
				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					CalendarioId calendarioId = new CalendarioId();
					calendarioId.setEmpresas(session.getEmpresa());
					calendarioId.setEjercicios(session.getEjercicio());
					calendarioId.setSemana(((Number) txtSemana.getValue())
							.intValue());
					calendario.setId(calendarioId);
				}
				if (!txtDesdeFecha.getText().equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtDesdeFecha.getText());
					calendario.setDesdeFecha(value);
				} else
					calendario.setDesdeFecha(null);
				if (!txtHastaFecha.getText().equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtHastaFecha.getText());
					calendario.setHastaFecha(value);
				} else
					calendario.setHastaFecha(null);
				if (!txtPorcKilosInutilizados.getText().equals(""))
					calendario
							.setPorcKilosInutilizados(((Number) txtPorcKilosInutilizados
									.getValue()).floatValue());
				else
					calendario.setPorcKilosInutilizados(((Number) 0)
							.floatValue());
				if (!txtRefAutoControl.getText().equals(""))
					calendario.setRefAutoControl(txtRefAutoControl.getText());
				else
					calendario.setRefAutoControl(null);
				calendario.setLmd(new Date());
				calendario.setSid("Santi");
				calendario.setVersion(1);
				session.getSession().replicate(calendario,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(calendario);
				session.getSession().flush();

				boolean hasValidPrecios = false;
				for (Integer k = 0; k < tblPrecios.getRowCount(); k++) {
					if (tblPrecios.getValueAt(k, PreciosTableModel.columnState)
							.equals(PreciosTableModel.EditLine)) {
						hasValidPrecios = true;
						break;
					}
				}

				if (hasValidPrecios) {
					String deletelinesquery = "Delete From Precios "
							+ "Where id.semana = "
							+ ((Number) txtSemana.getValue()).intValue()
							+ " and id.empresas.idEmpresa="
							+ session.getEmpresa().getIdEmpresa()
							+ " and id.ejercicios.ejercicio="
							+ session.getEjercicio().getEjercicio();

					Query q = getSession().getSession().createQuery(
							deletelinesquery);
					q.executeUpdate();
				}

				for (Integer k = 0; k < tblPrecios.getRowCount(); k++) {
					if (tblPrecios.getValueAt(k, PreciosTableModel.columnState)
							.equals(PreciosTableModel.EditLine)) {
						Precios precio = new Precios();
						PreciosId precioId = new PreciosId();
						precioId.setEjercicios(session.getEjercicio());
						precioId.setEmpresas(session.getEmpresa());
						precioId.setSemana(((Number) txtSemana.getValue())
								.intValue());
						precioId.setIdCategoria(((Number) tblPrecios
								.getValueAt(k,
										PreciosTableModel.columnIdCategoria))
								.intValue());
						precio.setId(precioId);
						precio.setPrecio(((Number) tblPrecios.getValueAt(k,
								PreciosTableModel.columnPrecio)).floatValue());
						precio.setLmd(new Date());
						precio.setSid("Santi");
						precio.setVersion(1);
						session.getSession().replicate(precio,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(precio);
						session.getSession().flush();
					}
				}
				if (transaction.isActive()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				OnNew = false;
				FrmCalendario.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmSemana.saveData()", he);
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean validateData() {
		try {
			if (txtSemana.getText().equals("")) {
				Message
						.ShowValidateMessage(pnlData,
								"Debe indicar una semana.");
				txtSemana.requestFocus();
				return (false);
			} else {
				try {
					txtSemana.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtSemana.requestFocus();
					return (false);
				}
			}
			if (!txtDesdeFecha.getText().equals(PreferencesUI.DateMask)) {
				try {
					txtDesdeFecha.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtDesdeFecha.requestFocus();
					return (false);
				}
			}

			if (!txtHastaFecha.getText().equals(PreferencesUI.DateMask)) {
				try {
					txtHastaFecha.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtHastaFecha.requestFocus();
					return (false);
				}
			}
			if (!txtPorcKilosInutilizados.getText().equals("")) {
				try {
					txtPorcKilosInutilizados.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtPorcKilosInutilizados.requestFocus();
					return (false);
				}
			}

			if (!validatePreciosData())
				return (false);

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmSemana.valiadateData()",
					he);
			return (false);
		}
	}

	private boolean validatePreciosData() {
		try {
			for (Integer actualrow = 0; actualrow < tblPrecios.getRowCount(); actualrow++) {
				if (tblPrecios.getValueAt(actualrow,
						PreciosTableModel.columnState).equals(
						PreciosTableModel.EditLine)) {
					if (tblPrecios.getValueAt(actualrow,
							PreciosTableModel.columnIdCategoria).equals("")) {
						Message.ShowValidateMessage(tblPrecios,
								"Debe indicar una categoría.");
						tblPrecios.changeSelection(tblPrecios.getSelectedRow(),
								PreciosTableModel.columnIdCategoria, false,
								false);
						tblPrecios.editCellAt(actualrow,
								PreciosTableModel.columnIdCategoria);
						tblPrecios.requestFocusInWindow();
						return (false);
					}
					if (tblPrecios.getValueAt(actualrow,
							PreciosTableModel.columnPrecio).equals("")) {
						Message.ShowValidateMessage(tblPrecios,
								"Debe indicar un precio.");
						tblPrecios.changeSelection(tblPrecios.getSelectedRow(),
								PreciosTableModel.columnPrecio, false, false);
						tblPrecios.editCellAt(actualrow,
								PreciosTableModel.columnPrecio);
						tblPrecios.requestFocusInWindow();
						return (false);
					}
				}
			}
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.valiadatePreciosData()", he);
			return (false);
		}
	}

	private void changeCategoria() {
		try {
			if (!changingCategoria) {
				if (!(tblPrecios.getValueAt(tblPrecios.getSelectedRow(),
						tblPrecios.getSelectedColumn()).equals(""))) {
					Integer value = (Integer) tblPrecios.getValueAt(tblPrecios
							.getSelectedRow(), tblPrecios.getSelectedColumn());
					changingCategoria = true;
					if (value.equals(""))
						tblPrecios.setValueAt(null,
								tblPrecios.getSelectedRow(),
								PreciosTableModel.columnCategoriaDesc);
					else {
						if (((DefaultTableModel) tblPrecios.getModel())
								.getRowCount() > 0) {
							Categorias categoria = entity
									.CategoriaFindByIdCategoria(this, value);
							if (categoria != null)
								tblPrecios.setValueAt(categoria
										.getNombreCategoria(), tblPrecios
										.getSelectedRow(),
										PreciosTableModel.columnCategoriaDesc);
							else {
								Message.ShowValidateMessage(tblPrecios,
										"La categoria indicada no existe.");
								tblPrecios.setValueAt(null, tblPrecios
										.getSelectedRow(),
										PreciosTableModel.columnCategoriaDesc);
								tblPrecios.requestFocusInWindow();
							}
						}
					}
				}
				changingCategoria = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.changeCategoria()", he);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents() {

		pnlData = new javax.swing.JPanel();
		lblAno = new javax.swing.JLabel();
		lblSemana = new javax.swing.JLabel();
		lblDesdeFecha = new javax.swing.JLabel();
		lblHastaFecha = new javax.swing.JLabel();
		txtHastaFecha = new javax.swing.JFormattedTextField();
		txtDesdeFecha = new javax.swing.JFormattedTextField();
		txtAno = new javax.swing.JFormattedTextField();
		txtSemana = new javax.swing.JFormattedTextField();
		txtPorcKilosInutilizados = new javax.swing.JFormattedTextField();
		lblKilosInutilizados = new javax.swing.JLabel();
		lbPrecios = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblPrecios = new javax.swing.JTable();
		btnOk = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		cboCategoriaDesc = new javax.swing.JComboBox();
		txtPrecio = new javax.swing.JFormattedTextField();
		cmdDeselectAll = new javax.swing.JButton();
		cmdDeleteLinea = new javax.swing.JButton();
		cmdSelectAll = new javax.swing.JButton();
		lblRefAutoControl = new javax.swing.JLabel();
		txtRefAutoControl = new javax.swing.JTextField();

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData.setName("pnlData");

		lblAno.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblAno.setForeground(new java.awt.Color(255, 0, 0));
		lblAno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblAno.setText("año");

		lblSemana.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemana.setForeground(new java.awt.Color(255, 0, 0));
		lblSemana.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemana.setText("Semana");

		lblDesdeFecha.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblDesdeFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblDesdeFecha.setText("Desde");

		lblHastaFecha.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblHastaFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblHastaFecha.setText("Hasta");

		txtHastaFecha.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtHastaFecha
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtHastaFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtHastaFecha.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtHastaFecha.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtHastaFechaFocusLost(evt);
			}
		});

		txtDesdeFecha.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtDesdeFecha
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtDesdeFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtDesdeFecha.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtDesdeFecha.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtDesdeFechaFocusLost(evt);
			}
		});

		txtAno.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtAno.setEditable(false);
		txtAno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtAno.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtAno.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtAnoFocusLost(evt);
			}
		});

		txtSemana.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemana
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemana.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtSemana.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtSemana.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtSemanaFocusLost(evt);
			}
		});

		txtPorcKilosInutilizados.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtPorcKilosInutilizados
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.0000"))));
		txtPorcKilosInutilizados
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtPorcKilosInutilizados.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtPorcKilosInutilizados
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						txtPorcKilosInutilizadosFocusLost(evt);
					}
				});

		lblKilosInutilizados.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblKilosInutilizados
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblKilosInutilizados.setText("% Kilos inutilizados");
		lblKilosInutilizados
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		lbPrecios.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lbPrecios.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		lbPrecios.setText("Precios");
		lbPrecios.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		jScrollPane1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		tblPrecios.setFont(new java.awt.Font("Segoe UI", 0, 14));
		tblPrecios.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] {

				}));
		tblPrecios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblPrecios.setCellSelectionEnabled(true);
		tblPrecios.setRowHeight(18);
		tblPrecios.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tblPreciosKeyPressed(evt);
			}
		});
		jScrollPane1.setViewportView(tblPrecios);

		btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/ok.png")));
		btnOk.setToolTipText("Aceptar");
		btnOk.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnOkMousePressed(evt);
			}
		});

		btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/cancel.png")));
		btnCancel.setToolTipText("Cancelar");
		btnCancel.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnCancelMousePressed(evt);
			}
		});

		cboCategoriaDesc.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "" }));
		cboCategoriaDesc.setPreferredSize(new java.awt.Dimension(0, 0));
		cboCategoriaDesc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboCategoriaDescActionPerformed(evt);
			}
		});

		txtPrecio.setPreferredSize(new java.awt.Dimension(0, 0));

		cmdDeselectAll.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdDeselectAll.setText("Quitar selección");
		cmdDeselectAll.setToolTipText("Quitar la seleccionar todas las filas");
		cmdDeselectAll.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdDeselectAll.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdDeselectAllMousePressed(evt);
			}
		});

		cmdDeleteLinea.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdDeleteLinea.setText("Eliminar línea");
		cmdDeleteLinea.setToolTipText("Eliminar las líneas seleccionados");
		cmdDeleteLinea.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdDeleteLinea.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdDeleteLineaMousePressed(evt);
			}
		});

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

		lblRefAutoControl.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblRefAutoControl
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblRefAutoControl.setText("Ref. auto control");
		lblRefAutoControl
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		txtRefAutoControl.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtRefAutoControl.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtRefAutoControl.setAutoscrolls(false);
		txtRefAutoControl.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtRefAutoControl.setName("nif");
		txtRefAutoControl.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtRefAutoControlKeyTyped(evt);
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
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																pnlDataLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																		.addGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				pnlDataLayout
																						.createSequentialGroup()
																						.addContainerGap()
																						.addComponent(
																								jScrollPane1,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								612,
																								Short.MAX_VALUE))
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
																														.addGap(
																																17,
																																17,
																																17)
																														.addComponent(
																																cmdSelectAll,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																157,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																cmdDeselectAll,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																157,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(
																																cmdDeleteLinea,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																157,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																																9,
																																Short.MAX_VALUE))
																										.addGroup(
																												pnlDataLayout
																														.createSequentialGroup()
																														.addGap(
																																484,
																																484,
																																484)
																														.addGroup(
																																pnlDataLayout
																																		.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.TRAILING)
																																		.addComponent(
																																				txtPrecio,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				0,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)
																																		.addComponent(
																																				cboCategoriaDesc,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				0,
																																				javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addPreferredGap(
																																javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
																						.addComponent(
																								btnCancel)
																						.addPreferredGap(
																								javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(
																								btnOk)))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				lbPrecios,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				36,
																				36,
																				36)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								pnlDataLayout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING,
																												false)
																										.addGroup(
																												pnlDataLayout
																														.createSequentialGroup()
																														.addComponent(
																																lblKilosInutilizados,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																150,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGap(
																																10,
																																10,
																																10)
																														.addComponent(
																																txtPorcKilosInutilizados,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																90,
																																javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addGroup(
																												pnlDataLayout
																														.createSequentialGroup()
																														.addComponent(
																																lblHastaFecha,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																150,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGap(
																																10,
																																10,
																																10)
																														.addComponent(
																																txtHastaFecha,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																90,
																																javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addGroup(
																												pnlDataLayout
																														.createSequentialGroup()
																														.addComponent(
																																lblDesdeFecha,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																150,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGap(
																																10,
																																10,
																																10)
																														.addComponent(
																																txtDesdeFecha,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																90,
																																javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addGroup(
																												pnlDataLayout
																														.createSequentialGroup()
																														.addGap(
																																10,
																																10,
																																10)
																														.addComponent(
																																lblRefAutoControl,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addGap(
																																10,
																																10,
																																10)
																														.addComponent(
																																txtRefAutoControl,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																90,
																																javax.swing.GroupLayout.PREFERRED_SIZE)))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblAno,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												150,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												txtAno,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblSemana,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												150,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												txtSemana,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))))
										.addContainerGap()));
		pnlDataLayout
				.setVerticalGroup(pnlDataLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlDataLayout
										.createSequentialGroup()
										.addGap(28, 28, 28)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(lblAno)
														.addComponent(
																txtAno,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(10, 10, 10)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(lblSemana)
														.addComponent(
																txtSemana,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblDesdeFecha)
														.addComponent(
																txtDesdeFecha,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblHastaFecha)
														.addComponent(
																txtHastaFecha,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblKilosInutilizados)
														.addComponent(
																txtPorcKilosInutilizados,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblRefAutoControl)
																		.addGap(
																				3,
																				3,
																				3)
																		.addComponent(
																				lbPrecios))
														.addComponent(
																txtRefAutoControl,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												140, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																pnlDataLayout
																		.createSequentialGroup()
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								cmdDeselectAll)
																						.addComponent(
																								cmdDeleteLinea)
																						.addComponent(
																								cmdSelectAll))
																		.addGap(
																				21,
																				21,
																				21)
																		.addComponent(
																				txtPrecio,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				0,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cboCategoriaDesc,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				0,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																btnCancel,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																btnOk,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	}

	private void txtRefAutoControlKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtRefAutoControl, evt, 45);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmReceptor.txtRefAutoControlKeyTyped()", he);
		}
	}

	private void txtPorcKilosInutilizadosFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtPorcKilosInutilizados);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.txtPorcKilosInutilizadosFocusLost()", he);
		}
	}

	private void cmdSelectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblPrecios.getRowCount(); actualrow++)
				tblPrecios.setValueAt(true, actualrow,
						PreciosTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.cmdSelectAllMousePressed()", he);
		}
	}

	private void cmdDeleteLineaMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblPrecios.getRowCount(); actualrow++) {
				if ((Boolean) tblPrecios.getValueAt(actualrow,
						PreciosTableModel.columnSelect) == true) {
					((DefaultTableModel) tblPrecios.getModel())
							.removeRow(actualrow);
					actualrow--;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.cmdDeleteLineaMousePressed()", he);
		}
	}

	private void cmdDeselectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblPrecios.getRowCount(); actualrow++)
				tblPrecios.setValueAt(false, actualrow,
						PreciosTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.cmdDeselectAllMousePressed()", he);
		}
	}

	private void cboCategoriaDescActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingCategoria) {
				changingCategoria = true;
				if (cboCategoriaDesc.getSelectedIndex() != -1) {
					if (tblPrecios.getRowCount() > 0
							&& tblPrecios.getSelectedRow() != -1) {
						List<?> categoriaslist = entity
								.CategoriaFindByNombreCategoria(this,
										(String) cboCategoriaDesc
												.getSelectedItem());
						if (categoriaslist.size() > 0) {
							Categorias categoriasItem = (Categorias) categoriaslist
									.get(0);
							tblPrecios.setValueAt(categoriasItem.getId()
									.getIdCategoria(), tblPrecios
									.getSelectedRow(),
									PreciosTableModel.columnIdCategoria);
						}
					}
				}
				changingCategoria = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.cboCategoriaDescActionPerformed()", he);
		}
	}

	private void tblPreciosKeyPressed(java.awt.event.KeyEvent evt) {
		try {
			if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				if ((tblPrecios.getSelectedRow() == ((DefaultTableModel) tblPrecios
						.getModel()).getRowCount() - 1)
						&& (tblPrecios.getValueAt(tblPrecios.getSelectedRow(),
								PreciosTableModel.columnState)
								.equals(PreciosTableModel.EditLine))) {
					if ((tblPrecios.getSelectedColumn() == tblPrecios
							.getColumnCount() - 1)) {
						newLinePrecios();
						evt.setKeyCode(0);
					} else
						evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
				} else
					evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
			} else {
				if ((evt.getKeyCode() == 32 || evt.getKeyCode() == 164)
						|| (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57)
						|| (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 122))
					tblPrecios.setValueAt("", tblPrecios.getSelectedRow(),
							tblPrecios.getSelectedColumn());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.tblPreciosKeyPressed()", he);
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
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.calendario);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.btnCancelMousePressed()", he);
		}
	}

	private void txtHastaFechaFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtHastaFecha);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.txtHastaFechaFocusLost()", he);
		}
	}

	private void txtDesdeFechaFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtDesdeFecha);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.txtDesdeFechaFocusLost()", he);
		}
	}

	private void txtSemanaFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtSemana);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.txtSemanaFocusLost()", he);
		}
	}

	private void txtAnoFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtAno);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmSemana.txtAnoFocusLost()", he);
		}
	}

	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private static javax.swing.JComboBox cboCategoriaDesc;
	private javax.swing.JButton cmdDeleteLinea;
	private javax.swing.JButton cmdDeselectAll;
	private javax.swing.JButton cmdSelectAll;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lbPrecios;
	private javax.swing.JLabel lblAno;
	private javax.swing.JLabel lblDesdeFecha;
	private javax.swing.JLabel lblHastaFecha;
	private javax.swing.JLabel lblKilosInutilizados;
	private javax.swing.JLabel lblRefAutoControl;
	private javax.swing.JLabel lblSemana;
	public javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblPrecios;
	private javax.swing.JFormattedTextField txtAno;
	private javax.swing.JFormattedTextField txtDesdeFecha;
	private javax.swing.JFormattedTextField txtHastaFecha;
	private javax.swing.JFormattedTextField txtPorcKilosInutilizados;
	private static javax.swing.JFormattedTextField txtPrecio;
	private javax.swing.JTextField txtRefAutoControl;
	private javax.swing.JFormattedTextField txtSemana;

}


