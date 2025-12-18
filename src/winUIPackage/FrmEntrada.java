/*
 * FrmBanco.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.AWTKeyStroke;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

import java.util.Vector;

import entitiesPackage.Cosecheros;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Categorias;
import entitiesPackage.Entradascabecera;
import entitiesPackage.EntradascabeceraId;
import entitiesPackage.Entradaslineas;
import entitiesPackage.EntradaslineasId;
import entitiesPackage.Message;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmEntrada extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	public class DetalleTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

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
		final public static int columnNumKilos = 4;

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

		public DetalleTableModel() {

			super(null, new String[] { "Select", "State", "IdCategoria",
					"CategoriaDesc", "NumKilos" });

			columns = new Vector<ColumnData>();
			columns.add(new ColumnData("Select", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					CheckType, null, null));
			columns.add(new ColumnData("State", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					StateType, null, null));
			columns.add(new ColumnData("IdCategoria", "Id. categorï¿½a",
					EntityType.NumberWidth, SwingConstants.RIGHT, NormalType,
					null, null));
			columns.add(new ColumnData("CategoriaDesc", "Descripciï¿½n",
					EntityType.ComboWidth, SwingConstants.LEFT, ComboType,
					cboCategoriaDesc, null));
			columns.add(new ColumnData("NumKilos", "Nï¿½mero de Kilos",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtNumKilos, "#0"));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (!changingState && !addLine)
						changeEditState();
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
			case columnNumKilos: {
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

	Entradascabecera entrada;

	static boolean changingState = false;
	static boolean addLine = false;
	static boolean changingCategoria = false;
	static boolean changingCosechero = false;
	private boolean OnNew = false;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private Entity entity = new Entity();

	public static java.awt.Frame getParentFrame() {
		return FrmEntrada.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmEntrada.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmEntrada() {
		initComponents();
	}

	public FrmEntrada(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmEntrada.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntrada()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmEntrada.configureKeys",
					he);
		}
	}

	private void prepareTable() {
		try {
			loadCategorias();

			DetalleTableModel modelDetalle = new DetalleTableModel();
			tblDetalle.setModel(modelDetalle);
			tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			for (int k = 0; k < modelDetalle.columns.size(); k++) {

				Object renderer = null;
				DefaultCellEditor editor = null;

				Integer editortype = modelDetalle.columns.get(k).m_editortype;
				switch (editortype) {

				case DetalleTableModel.NormalType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelDetalle.columns.get(k).m_alignment);

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
				case DetalleTableModel.Float2Type: {
					renderer = new Float2Renderer();
					final JFormattedTextField field = (JFormattedTextField) modelDetalle.columns
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
				case DetalleTableModel.Float4Type: {
					renderer = new Float4Renderer();
					final JFormattedTextField field = (JFormattedTextField) modelDetalle.columns
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
				case DetalleTableModel.ComboType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelDetalle.columns.get(k).m_alignment);
					editor = new DefaultCellEditor(
							(JComboBox) modelDetalle.columns.get(k).m_editor);
					editor.setClickCountToStart(1);
					break;
				}
				case DetalleTableModel.CheckType: {
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
				case DetalleTableModel.StateType: {

					final JLabel stateIcon = new JLabel();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JLabel getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							if (value.equals(DetalleTableModel.NewLine))
								stateIcon.setIcon(new ImageIcon(getClass()
										.getResource(
												"/imagesPackage/addline.png")));
							else if (value.equals(DetalleTableModel.EditLine))
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
				TableColumn column = tblDetalle.getColumn(modelDetalle.columns
						.get(k).m_name);
				column.setHeaderValue(modelDetalle.columns.get(k).m_title);
				column.setPreferredWidth(modelDetalle.columns.get(k).m_width);
				column.setCellRenderer((TableCellRenderer) renderer);
				if (editor != null) {
					column.setCellEditor(editor);
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntrada.prepareTable()",
					he);
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
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.loadCategorias()", he);
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
					"FrmEntrada.loadCosecheros()", he);
		}
	}

	public void newData() {
		try {
			this.entrada = new Entradascabecera();
			prepareTable();
			loadCosecheros();
			txtIdEntrada.setValue(null);
			txtAno.setValue(session.getEjercicio().getEjercicio());
			/* txtFecha.setText("");
			txtSemana.setText(""); */
			txtIdCosechero.setText("");
			cboCosecheros.setSelectedItem(null);
			txtNumPinas.setText("");
			chkRecogidaPropia.setSelected(false);
			txtImporteBonificacion.setValue(null);
			txtKilosBonificacion.setValue(null);
			txtNumPinasBonificacion.setValue(null);
			Integer numRows = ((DefaultTableModel) tblDetalle.getModel())
					.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblDetalle.getModel()).removeRow(0);
			newLineDetalle();
			OnNew = true;
			txtIdCosechero.requestFocusInWindow();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntrada.newData()", he);
		}
	}

	private void newLineDetalle() {

		try {
			if (tblDetalle.getRowCount() > 0) {
				if (!(tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
						DetalleTableModel.columnIdCategoria).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblDetalle.getModel())
							.addRow(new Object[] { false,
									DetalleTableModel.NewLine, "", "", "" });
					tblDetalle.changeSelection(tblDetalle.getSelectedRow() + 1,
							DetalleTableModel.columnIdCategoria, false, false);
					tblDetalle.editCellAt(tblDetalle.getSelectedRow() + 1,
							DetalleTableModel.columnIdCategoria);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblDetalle.getModel())
						.addRow(new Object[] { false,
								DetalleTableModel.NewLine, "", "", "" });
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.newLineDetalle()", he);
		}
	}

	private static void changeEditState() {
		try {
			if (!changingState) {
				if (tblDetalle.getSelectedRow() != -1) {
					changingState = true;
					tblDetalle.setValueAt(DetalleTableModel.EditLine,
							tblDetalle.getSelectedRow(),
							DetalleTableModel.columnState);
					changingState = false;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(getParentFrame(),
					"FrmEntrada.changeEditState()", he);
		}
	}

	public void loadData(Entradascabecera entity) {
		try {
			this.entrada = entity;
			prepareTable();
			loadCosecheros();
			txtIdEntrada.setValue(entity.getId().getIdEntrada());
			txtAno.setValue(entity.getId().getEjercicios().getEjercicio());
			txtSemana.setValue(entity.getSemana());
			txtSemanaEntrada.setValue(entity.getSemanaEntrada());
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
			String strFecha = df.format(entity.getFecha());
			txtFecha.setValue(strFecha);
			txtIdCosechero.setValue(entity.getIdCosechero());
			txtNumPinas.setValue(entity.getNumPinas());
			chkRecogidaPropia.setSelected(entity.getRecogidaPropia() != 0);
			txtImporteBonificacion.setValue(entity.getImporteBonificacion());
			txtKilosBonificacion.setValue(entity.getKilosBonificacion());
			txtNumPinasBonificacion.setValue(entity.getNumPinasBonificacion());
			changeCosechero();
			loadDataDetail();
			OnNew = false;
			txtSemana.requestFocusInWindow();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntrada.loadData()", he);
		}
	}

	private void loadDataDetail() {

		try {
			Integer numRows = ((DefaultTableModel) tblDetalle.getModel())
					.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblDetalle.getModel()).removeRow(0);

			List<?> lineasList = entity.executeQuery(this, "*",
					"Entradaslineas", "id.idEntrada="
							+ entrada.getId().getIdEntrada(), "id.idCategoria");

			if (lineasList.size() == 0)
				newLineDetalle();
			else {
				for (Object linea : lineasList) {
					Entradaslineas entradaslinea = (Entradaslineas) linea;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(DetalleTableModel.EditLine);
					oneRow.add(entradaslinea.getId().getIdCategoria());
					Categorias categoria = entity.CategoriaFindByIdCategoria(
							this, entradaslinea.getId().getIdCategoria());
					if (categoria != null)
						oneRow.add(categoria.getNombreCategoria());
					else
						oneRow.add("");
					cboCategoriaDesc.setSelectedItem(categoria
							.getNombreCategoria());
					oneRow.add(entradaslinea.getNumKilos());
					((DefaultTableModel) tblDetalle.getModel()).addRow(oneRow);
				}
			}
			calculaTotales();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.loadDataDetail()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {

		Transaction transaction = null;
		boolean weStartedTransaction = false;
		
		try {
			if (validateData()) {
				
				// Primero generar el ID si es un nuevo registro, antes de la transacción
				if (OnNew) {
					txtIdEntrada.setValue(entity.newId(this,
							"Entradascabecera", "id.idEntrada"));
					EntradascabeceraId entradaid = new EntradascabeceraId();
					entradaid.setIdEntrada(((Number) txtIdEntrada.getValue())
							.intValue());
					entradaid.setEjercicios(session.getEjercicio());
					entradaid.setEmpresas(session.getEmpresa());
					entrada.setId(entradaid);
				}
				
				// Manejo minimalista de transacciones para Hibernate 3.x
				// Usar lo que esté disponible sin manipulaciones complejas
				transaction = session.getSession().getTransaction();
				if (transaction == null || !transaction.isActive()) {
					// Solo crear nueva transacción si realmente no hay ninguna
					transaction = session.getSession().beginTransaction();
					weStartedTransaction = true;
				} else {
					// Usar transacción existente
					weStartedTransaction = false;
				}
				if (!txtSemana.getText().equals(""))
					entrada.setSemana(((Number) txtSemana.getValue())
							.intValue());
				else
					entrada.setSemana(null);
				if (!txtSemanaEntrada.getText().equals(""))
					entrada.setSemanaEntrada(((Number) txtSemanaEntrada.getValue())
							.intValue());
				else
					entrada.setSemanaEntrada(null);
				if (!txtFecha.getText().equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtFecha.getText());
					entrada.setFecha(value);
				} else
					entrada.setFecha(null);
				if (!txtIdCosechero.getText().equals(""))
					entrada.setIdCosechero(((Number) txtIdCosechero.getValue())
							.intValue());
				else
					entrada.setIdCosechero(null);
				entrada.setIdZona(entity.CosecheroGetZona(this,
						((Number) txtIdCosechero.getValue()).intValue()));
				if (!txtNumPinas.getText().equals(""))
					entrada.setNumPinas(((Number) txtNumPinas.getValue())
							.floatValue());
				else
					entrada.setNumPinas(null);
				if (chkRecogidaPropia.isSelected())
					entrada.setRecogidaPropia(((Number) 1).shortValue());
				else
					entrada.setRecogidaPropia(((Number) 0).shortValue());
				if (!txtImporteBonificacion.getText().equals(""))
					entrada
							.setImporteBonificacion(((Number) txtImporteBonificacion
									.getValue()).floatValue());
				else
					entrada.setImporteBonificacion(null);
				if (!txtKilosBonificacion.getText().equals(""))
					entrada.setKilosBonificacion(((Number) txtKilosBonificacion
							.getValue()).floatValue());
				else
					entrada.setKilosBonificacion(null);
				if (!txtNumPinasBonificacion.getText().equals(""))
					entrada
							.setNumPinasBonificacion(((Number) txtNumPinasBonificacion
									.getValue()).floatValue());
				else
					entrada.setNumPinasBonificacion(null);
				entrada.setLmd(new Date());
				entrada.setSid("Santi");
				entrada.setVersion(1);
				session.getSession().replicate(entrada,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(entrada);
				session.getSession().flush();

				// Verificar que hay datos válidos en la tabla antes de borrar
				boolean hasValidLines = false;
				for (Integer k = 0; k < ((DefaultTableModel) tblDetalle
						.getModel()).getRowCount(); k++) {
					if (tblDetalle.getValueAt(k, DetalleTableModel.columnState)
							.equals(DetalleTableModel.EditLine)) {
						hasValidLines = true;
						break;
					}
				}

				// Solo borrar y reinsertar líneas si hay datos válidos para reemplazar
				if (hasValidLines) {
					String deletelinesquery = "Delete From Entradaslineas "
							+ "Where id.idEntrada = "
							+ ((Number) txtIdEntrada.getValue()).intValue()
							+ " and id.empresas.idEmpresa="
							+ session.getEmpresa().getIdEmpresa()
							+ " and id.ejercicios.ejercicio="
							+ session.getEjercicio().getEjercicio();

					Query q = getSession().getSession().createQuery(
							deletelinesquery);
					q.executeUpdate();
				}

				for (Integer k = 0; k < ((DefaultTableModel) tblDetalle
						.getModel()).getRowCount(); k++) {
					if (tblDetalle.getValueAt(k, DetalleTableModel.columnState)
							.equals(DetalleTableModel.EditLine)) {
						Entradaslineas entradalinea = new Entradaslineas();
						EntradaslineasId entradalineaId = new EntradaslineasId();
						entradalineaId.setEjercicios(session.getEjercicio());
						entradalineaId.setEmpresas(session.getEmpresa());
						entradalineaId.setIdEntrada(((Number) txtIdEntrada
								.getValue()).intValue());
						entradalineaId.setIdCategoria((Integer) tblDetalle
								.getValueAt(k,
										DetalleTableModel.columnIdCategoria));
						entradalinea.setId(entradalineaId);
						entradalinea.setNumKilos((Float) tblDetalle.getValueAt(
								k, DetalleTableModel.columnNumKilos));
						entradalinea.setLmd(new Date());
						entradalinea.setSid("Santi");
						entradalinea.setVersion(1);
						session.getSession().replicate(entradalinea,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(entradalinea);
						session.getSession().flush();
					}
				}
				// Hacer commit solo si nosotros iniciamos la transacción
				if (weStartedTransaction && transaction != null && transaction.isActive()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				OnNew = false;
				FrmEntradas.runSearchQuery();
				txtIdCosechero.requestFocusInWindow();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			// En caso de error, hacer rollback solo si nosotros iniciamos la transacción
			try {
				if (weStartedTransaction && transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
			} catch (Exception rollbackError) {
				// Log rollback error but don't mask original error
				System.err.println("Error during rollback: " + rollbackError.getMessage());
			}
			Message.ShowRuntimeError(parentFrame, "FrmEntrada.saveData()", he);
			return false;
		} catch (ParseException e) {
			// En caso de error de parseo, hacer rollback solo si nosotros iniciamos la transacción
			try {
				if (weStartedTransaction && transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
			} catch (Exception rollbackError) {
				// Log rollback error but don't mask original error
				System.err.println("Error during rollback: " + rollbackError.getMessage());
			}
			// TODO Auto-generated catch block
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
			if (txtSemanaEntrada.getText().equals("")) {
				Message
						.ShowValidateMessage(pnlData,
								"Debe indicar una semana de entrada.");
				txtSemanaEntrada.requestFocus();
				return (false);
			} else {
				try {
					txtSemanaEntrada.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtSemanaEntrada.requestFocus();
					return (false);
				}
			}
			if (txtFecha.getText().equals(PreferencesUI.DateMask)) {
				Message.ShowValidateMessage(pnlData, "Debe indicar una fecha.");
				txtFecha.requestFocus();
				return (false);
			} else {
				try {
					txtFecha.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtFecha.requestFocus();
					return (false);
				}
			}
			if (txtIdCosechero.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un cosechero.");
				txtIdCosechero.requestFocus();
				return (false);
			} else {
				try {
					txtIdCosechero.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdCosechero.requestFocus();
					return (false);
				}
			}
			if (txtNumPinas.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un Nï¿½mero de racimos.");
				txtNumPinas.requestFocus();
				return (false);
			} else {
				try {
					txtNumPinas.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtNumPinas.requestFocus();
					return (false);
				}
			}
			if (!txtImporteBonificacion.getText().equals("")) {
				try {
					txtImporteBonificacion.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtImporteBonificacion.requestFocus();
					return (false);
				}
			}
			if (!txtKilosBonificacion.getText().equals("")) {
				try {
					txtKilosBonificacion.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtKilosBonificacion.requestFocus();
					return (false);
				}
			}
			if (!txtNumPinasBonificacion.getText().equals("")) {
				try {
					txtNumPinasBonificacion.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtNumPinasBonificacion.requestFocus();
					return (false);
				}
			}
			if (!txtIdCosechero.getText().equals("")) {
				try {
					txtIdCosechero.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdCosechero.requestFocus();
					return (false);
				}
			}
			if (OnNew) {
				Integer idCosechero = ((Number) txtIdCosechero.getValue())
						.intValue();
				Integer semana = ((Number) txtSemana.getValue()).intValue();
				if (entity.EntradaFindByCosecheroSemana(pnlData, idCosechero,
						semana) != null) {
					Object[] botones = { "Si", "No" };
					int respuestavalue = JOptionPane
							.showOptionDialog(
									null,
									"Ya hay una entrada para el cosechero y semana seleccionados, ¿Desea crear otra?",
									"", JOptionPane.DEFAULT_OPTION,
									JOptionPane.WARNING_MESSAGE, null, botones,
									botones[0]);
					if (respuestavalue != 0) {
						return (false);
					}
				}
			}

			if (!validateDetalleData())
				return (false);

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntrada.validateData()",
					he);
			return (false);
		}
	}

	private boolean validateDetalleData() {
		try {
			for (Integer actualrow = 0; actualrow < ((DefaultTableModel) tblDetalle
					.getModel()).getRowCount(); actualrow++) {
				if (tblDetalle.getValueAt(actualrow,
						DetalleTableModel.columnState).equals(
						DetalleTableModel.EditLine)) {
					if (tblDetalle.getValueAt(actualrow,
							DetalleTableModel.columnIdCategoria).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar un categorï¿½a.");
						tblDetalle.changeSelection(actualrow,
								DetalleTableModel.columnIdCategoria, false,
								false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnIdCategoria);
						tblDetalle.requestFocusInWindow();
						return (false);

					}
					if (tblDetalle.getValueAt(actualrow,
							DetalleTableModel.columnNumKilos).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar un Nï¿½mero de kilos.");
						tblDetalle.changeSelection(actualrow,
								DetalleTableModel.columnNumKilos, false, false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnNumKilos);
						tblDetalle.requestFocusInWindow();
						return (false);
					}
				}
			}
			calculaTotales();
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.validateDetalleData()", he);
			return (false);
		}
	}

	private void changeCategoria() {
		try {
			if (!changingCategoria) {
				if (!(tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
						tblDetalle.getSelectedColumn()).equals(""))) {
					Integer value = (Integer) tblDetalle.getValueAt(tblDetalle
							.getSelectedRow(), tblDetalle.getSelectedColumn());
					changingCategoria = true;
					if (value.equals(""))
						tblDetalle.setValueAt(null,
								tblDetalle.getSelectedRow(),
								DetalleTableModel.columnCategoriaDesc);
					else {
						if (((DefaultTableModel) tblDetalle.getModel())
								.getRowCount() > 0) {
							Categorias categoria = entity
									.CategoriaFindByIdCategoria(this, value);
							if (categoria != null)
								tblDetalle.setValueAt(categoria
										.getNombreCategoria(), tblDetalle
										.getSelectedRow(),
										DetalleTableModel.columnCategoriaDesc);
							else {
								Message.ShowValidateMessage(tblDetalle,
										"La categoria indicada no existe.");
								tblDetalle.setValueAt(null, tblDetalle
										.getSelectedRow(),
										DetalleTableModel.columnCategoriaDesc);
								tblDetalle.requestFocusInWindow();
							}
						}
					}
				}
				changingCategoria = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.changeCategoria()", he);
		}
	}

	private void changeSemana() {
		try {
			if (!(txtFecha.getText().equals(""))) {
				try {
					txtFecha.commitEdit();

					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date fecha = df.parse(txtFecha.getText());

					txtSemana
							.setValue(entity.SemanaByFecha(parentFrame, fecha));
					txtSemanaEntrada
					.setValue(entity.SemanaByFecha(parentFrame, fecha));
				} catch (ParseException e) {

				}
			}

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmEntrada.changeSemana()",
					he);
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
					"FrmEntrada.changeCosechero()", he);
		}
	}

	private void calculaTotales() {
		try {
			Float totalNumKilos = ((Number) 0).floatValue();
			Integer numRows = ((DefaultTableModel) tblDetalle.getModel())
					.getRowCount();
			for (Integer actualRow = 0; actualRow < numRows; actualRow++) {
				if (tblDetalle.getValueAt(actualRow,
						DetalleTableModel.columnState).equals(
						DetalleTableModel.EditLine)) {
					totalNumKilos = totalNumKilos
							+ (Float) (((DefaultTableModel) tblDetalle
									.getModel()).getValueAt(actualRow,
									DetalleTableModel.columnNumKilos));
				}
			}

			txtTotalNumKilos.setValue(totalNumKilos.floatValue());
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.calculaTotales()", he);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		pnlData = new javax.swing.JPanel();
		btnOk = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblDetalle = new javax.swing.JTable();
		cmdSelectAll = new javax.swing.JButton();
		cmdDeselectAll = new javax.swing.JButton();
		cmdDeleteLinea = new javax.swing.JButton();
		txtNumCajas = new javax.swing.JFormattedTextField();
		txtPrecio = new javax.swing.JFormattedTextField();
		txtNumKilos = new javax.swing.JFormattedTextField();
		cboCategoriaDesc = new javax.swing.JComboBox();
		lblIdEntrada = new javax.swing.JLabel();
		txtIdEntrada = new javax.swing.JFormattedTextField();
		lblAno = new javax.swing.JLabel();
		txtAno = new javax.swing.JFormattedTextField();
		lblSemana = new javax.swing.JLabel();
		txtSemana = new javax.swing.JFormattedTextField();
		lblSemanaEntrada = new javax.swing.JLabel();
		txtSemanaEntrada = new javax.swing.JFormattedTextField();
		lblFecha = new javax.swing.JLabel();
		txtFecha = new javax.swing.JFormattedTextField();
		lblCosechero = new javax.swing.JLabel();
		txtIdCosechero = new javax.swing.JFormattedTextField();
		cboCosecheros = new javax.swing.JComboBox();
		lblNumPinas = new javax.swing.JLabel();
		txtNumPinas = new javax.swing.JFormattedTextField();
		lblNumKilos = new javax.swing.JLabel();
		txtTotalNumKilos = new javax.swing.JFormattedTextField();
		chkRecogidaPropia = new javax.swing.JCheckBox();
		txtImporteBonificacion = new javax.swing.JFormattedTextField();
		lblImporteRPropia = new javax.swing.JLabel();
		lblRecogidaropia = new javax.swing.JLabel();
		txtNumPinasBonificacion = new javax.swing.JFormattedTextField();
		jSeparator1 = new javax.swing.JSeparator();
		lblRacimosRPropia = new javax.swing.JLabel();
		lblKilosRPropia = new javax.swing.JLabel();
		txtKilosBonificacion = new javax.swing.JFormattedTextField();
		jSeparator2 = new javax.swing.JSeparator();

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData.setName("pnlData");
		pnlData.setPreferredSize(new java.awt.Dimension(1024, 768));

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

		btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/cancel.png"))); // NOI18N
		btnCancel.setToolTipText("Cancelar");
		btnCancel.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnCancelMousePressed(evt);
			}
		});

		jScrollPane1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		tblDetalle.setFont(new java.awt.Font("Segoe UI", 0, 14));
		tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] {

				}));
		tblDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblDetalle.setCellSelectionEnabled(true);
		tblDetalle.setRowHeight(18);
		tblDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tblDetalleKeyPressed(evt);
			}
		});
		jScrollPane1.setViewportView(tblDetalle);

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

		cmdDeselectAll.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdDeselectAll.setText("Quitar selecciï¿½n");
		cmdDeselectAll.setToolTipText("Quitar la seleccionar todas las filas");
		cmdDeselectAll.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdDeselectAll.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdDeselectAllMousePressed(evt);
			}
		});

		cmdDeleteLinea.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdDeleteLinea.setText("Eliminar lï¿½nea");
		cmdDeleteLinea.setToolTipText("Eliminar las lï¿½neas seleccionados");
		cmdDeleteLinea.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdDeleteLinea.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdDeleteLineaMousePressed(evt);
			}
		});

		txtNumCajas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtNumCajas.setPreferredSize(new java.awt.Dimension(0, 0));

		txtPrecio
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtPrecio.setPreferredSize(new java.awt.Dimension(0, 0));

		txtNumKilos
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtNumKilos.setPreferredSize(new java.awt.Dimension(0, 0));

		cboCategoriaDesc.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "" }));
		cboCategoriaDesc.setPreferredSize(new java.awt.Dimension(0, 0));
		cboCategoriaDesc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboCategoriaDescActionPerformed(evt);
			}
		});

		lblIdEntrada.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblIdEntrada.setForeground(new java.awt.Color(255, 0, 0));
		lblIdEntrada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblIdEntrada.setText("Vale");

		txtIdEntrada.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdEntrada.setEditable(false);
		txtIdEntrada
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtIdEntrada.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdEntrada.setFocusable(false);
		txtIdEntrada.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblAno.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblAno.setForeground(new java.awt.Color(255, 0, 0));
		lblAno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblAno.setText("Aï¿½o");

		txtAno.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtAno.setEditable(false);
		txtAno
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtAno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtAno.setFocusable(false);
		txtAno.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblSemana.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemana.setForeground(new java.awt.Color(255, 0, 0));
		lblSemana.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemana.setText("Semana");

		txtSemana.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemana.setEditable(true);
		txtSemana
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemana.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtSemana.setFocusable(true);
		txtSemana.setFont(new java.awt.Font("Segoe UI", 0, 14));
		
		lblSemanaEntrada.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemanaEntrada.setForeground(new java.awt.Color(255, 0, 0));
		lblSemanaEntrada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemanaEntrada.setText("Semana entrada");

		txtSemanaEntrada.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemanaEntrada.setEditable(false);
		txtSemanaEntrada
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemanaEntrada.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtSemanaEntrada.setFocusable(false);
		txtSemanaEntrada.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFecha.setForeground(new java.awt.Color(255, 0, 0));
		lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFecha.setText("Fecha");

		txtFecha.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtFecha
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtFecha.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtFechaKeyReleased(evt);
			}
		});

		lblCosechero.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblCosechero.setForeground(new java.awt.Color(255, 0, 0));
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

		lblNumPinas.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblNumPinas.setForeground(new java.awt.Color(255, 0, 0));
		lblNumPinas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblNumPinas.setText("Nï¿½mero de racimos");

		txtNumPinas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtNumPinas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtNumPinas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtNumPinas.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtNumPinas.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txtNumPinasFocusLost(evt);
			}
		});

		lblNumKilos.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblNumKilos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblNumKilos.setText("Total de kilos");

		txtTotalNumKilos.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalNumKilos.setEditable(false);
		txtTotalNumKilos
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								java.text.NumberFormat.getIntegerInstance())));
		txtTotalNumKilos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalNumKilos.setFocusable(false);
		txtTotalNumKilos.setFont(new java.awt.Font("Segoe UI", 1, 14));

		chkRecogidaPropia.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		chkRecogidaPropia.setMargin(new java.awt.Insets(0, 0, 2, 2));
		chkRecogidaPropia.setName("privada");

		txtImporteBonificacion.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtImporteBonificacion
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtImporteBonificacion
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtImporteBonificacion.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtImporteBonificacion
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						txtImporteBonificacionFocusLost(evt);
					}
				});

		lblImporteRPropia.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblImporteRPropia
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblImporteRPropia.setText("Importe");

		lblRecogidaropia.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblRecogidaropia.setForeground(new java.awt.Color(51, 102, 255));
		lblRecogidaropia
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblRecogidaropia.setText("Recogida propia");

		txtNumPinasBonificacion.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtNumPinasBonificacion
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtNumPinasBonificacion
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtNumPinasBonificacion.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtNumPinasBonificacion
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						txtNumPinasBonificacionFocusLost(evt);
					}
				});

		lblRacimosRPropia.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblRacimosRPropia
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblRacimosRPropia.setText("Racimos");

		lblKilosRPropia.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblKilosRPropia
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblKilosRPropia.setText("Kilos");

		txtKilosBonificacion.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtKilosBonificacion
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtKilosBonificacion
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtKilosBonificacion.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtKilosBonificacion
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						txtKilosBonificacionFocusLost(evt);
					}
				});
		txtKilosBonificacion.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtKilosBonificacionKeyTyped(evt);
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
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlDataLayout
										.createSequentialGroup()
										.addGap(22, 22, 22)
										.addComponent(
												lblRecogidaropia,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												133,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(12, 12, 12)
										.addComponent(chkRecogidaPropia)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lblKilosRPropia,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				133,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtKilosBonificacion,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				61,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				126,
																				Short.MAX_VALUE)
																		.addComponent(
																				lblRacimosRPropia,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				133,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtNumPinasBonificacion,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				61,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(57, 57, 57)
										.addComponent(
												lblImporteRPropia,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												txtImporteBonificacion,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												61,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlDataLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jSeparator2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												679, Short.MAX_VALUE)
										.addContainerGap())
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
																				jScrollPane1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				679,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				jSeparator1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				679,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																pnlDataLayout
																		.createSequentialGroup()
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
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
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblNumKilos)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												txtTotalNumKilos,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addGap(
																				28,
																				28,
																				28)
																		.addComponent(
																				cboCategoriaDesc,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				19,
																				Short.MAX_VALUE)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								pnlDataLayout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																										.addGroup(
																												pnlDataLayout
																														.createSequentialGroup()
																														.addGap(
																																60,
																																60,
																																60)
																														.addComponent(
																																txtNumCajas,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																0,
																																javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING,
																												pnlDataLayout
																														.createSequentialGroup()
																														.addGap(
																																84,
																																84,
																																84)
																														.addComponent(
																																txtPrecio,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGap(
																																63,
																																63,
																																63)))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												btnCancel)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												btnOk)))
																		.addGap(
																				12,
																				12,
																				12))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				13,
																				13,
																				13)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lblAno,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								120,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblIdEntrada,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								120,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblCosechero,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								120,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblNumPinas))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												txtIdCosechero,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												cboCosecheros,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												461,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								txtIdEntrada,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								60,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												txtAno,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												76,
																												76,
																												76)
																										.addComponent(
																												lblFecha,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												59,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtFecha,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												18,
																												18,
																												18)
																										.addComponent(
																												lblSemana,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												69,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtSemana,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												18,
																												18,
																												18)
																										.addComponent(
																												lblSemanaEntrada,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtSemanaEntrada,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								txtNumPinas,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								90,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addContainerGap())
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				txtNumKilos,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
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
																txtIdEntrada,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblIdEntrada))
										.addGap(8, 8, 8)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txtAno,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lblAno)
														.addComponent(lblFecha)
														.addComponent(
																txtFecha,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtSemana,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lblSemana)
														.addComponent(
																txtSemanaEntrada,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lblSemanaEntrada))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txtIdCosechero,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblCosechero)
														.addComponent(
																cboCosecheros,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txtNumPinas,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblNumPinas))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jSeparator1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
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
																				lblRecogidaropia)
																		.addComponent(
																				txtImporteBonificacion,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblRacimosRPropia)
																		.addComponent(
																				txtNumPinasBonificacion,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblKilosRPropia)
																		.addComponent(
																				txtKilosBonificacion,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																lblImporteRPropia)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				4,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				chkRecogidaPropia)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jSeparator2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												228,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				4,
																				4,
																				4)
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
																														.addComponent(
																																txtNumCajas,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																0,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGap(
																																12,
																																12,
																																12)
																														.addComponent(
																																txtPrecio,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGap(
																																10,
																																10,
																																10)
																														.addComponent(
																																txtNumKilos,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																0,
																																javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addGroup(
																												pnlDataLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																														.addComponent(
																																btnCancel)
																														.addComponent(
																																btnOk)))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGroup(
																												pnlDataLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																lblNumKilos)
																														.addComponent(
																																txtTotalNumKilos,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																25,
																																javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												pnlDataLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																cmdSelectAll)
																														.addComponent(
																																cmdDeselectAll)
																														.addComponent(
																																cmdDeleteLinea)))))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cboCategoriaDesc,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 707,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 533,
				Short.MAX_VALUE));
	}// </editor-fold>
	//GEN-END:initComponents

	private void txtImporteBonificacionFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtImporteBonificacion);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.txtImporteBonificacionFocusLost()", he);
		}
	}

	private void txtNumPinasBonificacionFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtNumPinasBonificacion);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.txtNumPinasBonificacionFocusLost()", he);
		}
	}

	private void txtKilosBonificacionFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtKilosBonificacion);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.txtKilosBonificacionFocusLost()", he);
		}
	}

	private void txtNumPinasFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtNumPinas);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.txtNumPinasFocusLost()", he);
		}
	}

	private void txtFechaKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeSemana();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.txtFechaKeyReleased()", he);
		}
	}

	private void txtKilosBonificacionKeyTyped(java.awt.event.KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void txtIdCosecheroKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeCosechero();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.txtIdCosecheroKeyReleased()", he);
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
					"FrmEntrada.cboCosecherosActionPerformed()", he);
		}
	}

	private void cmdDeleteLineaMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < ((DefaultTableModel) tblDetalle
					.getModel()).getRowCount(); actualrow++) {
				if ((Boolean) tblDetalle.getValueAt(actualrow,
						DetalleTableModel.columnSelect) == true) {
					((DefaultTableModel) tblDetalle.getModel())
							.removeRow(actualrow);
					actualrow--;
				}
			}
			calculaTotales();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.cmdDeleteLineaMousePressed()", he);
		}
	}

	private void cmdSelectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < ((DefaultTableModel) tblDetalle
					.getModel()).getRowCount(); actualrow++)
				tblDetalle.setValueAt(true, actualrow,
						DetalleTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.cmdSelectAllMousePressed()", he);
		}
	}

	private void cmdDeselectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < ((DefaultTableModel) tblDetalle
					.getModel()).getRowCount(); actualrow++)
				tblDetalle.setValueAt(false, actualrow,
						DetalleTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.cmdDeselectAllMousePressed()", he);
		}
	}

	private void tblDetalleKeyPressed(java.awt.event.KeyEvent evt) {
		try {
			if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				if ((tblDetalle.getSelectedRow() == ((DefaultTableModel) tblDetalle
						.getModel()).getRowCount() - 1)
						&& (tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
								DetalleTableModel.columnState)
								.equals(DetalleTableModel.EditLine))) {
					if ((tblDetalle.getSelectedColumn() == tblDetalle
							.getColumnCount() - 1)) {
						newLineDetalle();
						evt.setKeyCode(0);
					} else
						evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
				} else
					evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
			} else {
				if ((evt.getKeyCode() == 32 || evt.getKeyCode() == 164)
						|| (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57)
						|| (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 122))
					tblDetalle.setValueAt("", tblDetalle.getSelectedRow(),
							tblDetalle.getSelectedColumn());
			}

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.tblDetalleKeyPressed()", he);
		}
	}

	private void cboCategoriaDescActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingCategoria) {
				changingCategoria = true;
				if (cboCategoriaDesc.getSelectedIndex() != -1) {
					if (((DefaultTableModel) tblDetalle.getModel())
							.getRowCount() > 0
							&& tblDetalle.getSelectedRow() != -1) {
						List<?> categoriaslist = entity
								.CategoriaFindByNombreCategoria(this,
										(String) cboCategoriaDesc
												.getSelectedItem());

						if (categoriaslist.size() > 0) {
							Categorias categoriasItem = (Categorias) categoriaslist
									.get(0);
							tblDetalle.setValueAt(categoriasItem.getId()
									.getIdCategoria(), tblDetalle
									.getSelectedRow(),
									DetalleTableModel.columnIdCategoria);
						}
					}
				}
				changingCategoria = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.cboCategoriaDescActionPerformed()", he);
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
					"FrmEntrada.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.entrada);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmEntrada.btnCancelMousePressed()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private static javax.swing.JComboBox cboCategoriaDesc;
	private static javax.swing.JComboBox cboCosecheros;
	private javax.swing.JCheckBox chkRecogidaPropia;
	private javax.swing.JButton cmdDeleteLinea;
	private javax.swing.JButton cmdDeselectAll;
	private javax.swing.JButton cmdSelectAll;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JLabel lblAno;
	private javax.swing.JLabel lblCosechero;
	private javax.swing.JLabel lblFecha;
	private javax.swing.JLabel lblIdEntrada;
	private javax.swing.JLabel lblImporteRPropia;
	private javax.swing.JLabel lblKilosRPropia;
	private javax.swing.JLabel lblNumKilos;
	private javax.swing.JLabel lblNumPinas;
	private javax.swing.JLabel lblRacimosRPropia;
	private javax.swing.JLabel lblRecogidaropia;
	private javax.swing.JLabel lblSemana;
	private javax.swing.JLabel lblSemanaEntrada;
	public static javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblDetalle;
	private javax.swing.JFormattedTextField txtAno;
	private javax.swing.JFormattedTextField txtFecha;
	private static javax.swing.JFormattedTextField txtIdCosechero;
	private javax.swing.JFormattedTextField txtIdEntrada;
	private javax.swing.JFormattedTextField txtImporteBonificacion;
	private javax.swing.JFormattedTextField txtKilosBonificacion;
	private static javax.swing.JFormattedTextField txtNumCajas;
	private static javax.swing.JFormattedTextField txtNumKilos;
	private javax.swing.JFormattedTextField txtNumPinas;
	private javax.swing.JFormattedTextField txtNumPinasBonificacion;
	private static javax.swing.JFormattedTextField txtPrecio;
	private javax.swing.JFormattedTextField txtSemana;
	private javax.swing.JFormattedTextField txtSemanaEntrada;
	private javax.swing.JFormattedTextField txtTotalNumKilos;
	// End of variables declaration//GEN-END:variables

}


