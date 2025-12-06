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

import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;
import entitiesPackage.Tiposgasto;
import entitiesPackage.Vehiculos;
import entitiesPackage.VehiculosId;
import entitiesPackage.Vehiculosgastos;
import entitiesPackage.VehiculosgastosId;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmVehiculo extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	public class GastosTableModel extends DefaultTableModel {

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
		final public static int columnAno = 2;
		final public static int columnMes = 3;
		final public static int columnIdGasto = 4;
		final public static int columnIdGastoDesc = 5;
		final public static int columnImporte = 6;
		final public static int columnComentarios = 7;

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

		public GastosTableModel() {

			super(null, new String[] { "Select", "State", "Ano", "Mes",
					"IdGasto", "IdGastoDesc", "Importe", "Comentarios" });

			columns = new Vector<ColumnData>();
			columns.add(new ColumnData("Select", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					CheckType, null, null));
			columns.add(new ColumnData("State", "",
					EntityType.SelectStateWidth, SwingConstants.CENTER,
					StateType, null, null));
			columns.add(new ColumnData("Ano", "A�o", EntityType.NumberWidth,
					SwingConstants.RIGHT, NormalType, null, null));
			columns.add(new ColumnData("Mes", "Mes", EntityType.NumberWidth,
					SwingConstants.RIGHT, NormalType, null, null));
			columns
					.add(new ColumnData("IdGasto", "Id. gasto",
							EntityType.NumberWidth, SwingConstants.RIGHT, 0,
							null, null));
			columns.add(new ColumnData("IdGastoDesc", "Descripci�n",
					EntityType.ComboWidth, SwingConstants.LEFT, ComboType,
					cboTipoGastoDesc, null));
			columns.add(new ColumnData("Importe", "Importe",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImporte, "#,##0.00"));
			columns.add(new ColumnData("Comentarios", "Comentarios",
					EntityType.TextWidth, SwingConstants.LEFT, NormalType,
					null, null));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (!changingState && !addLine)
						changeEditState();
					if (e.getColumn() == columnIdGasto) {
						changeTipoGasto();
					}
				}
			});
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			switch (columna) {
			case columnAno: {
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
			case columnIdGasto: {
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

	Vehiculos vehiculo;

	static boolean changingState = false;
	static boolean addLine = false;
	static boolean changingTipoGasto = false;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private boolean OnNew = false;
	private Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return FrmVehiculo.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmVehiculo.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmVehiculo() {
		initComponents();
	}

	public FrmVehiculo(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmVehiculo.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent, "FrmVehiculo()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmVehiculo.configureKeys",
					he);
		}
	}

	private void prepareTable() {
		try {
			GastosTableModel modelGastos = new GastosTableModel();
			tblGastos.setModel(modelGastos);
			tblGastos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			cboTipoGastoDesc.removeAllItems();
			List<?> tiposgastosList = entity.TipoGastoFindAll(this);
			for (Object o : tiposgastosList) {
				Tiposgasto tipogasto = (Tiposgasto) o;
				cboTipoGastoDesc.addItem(tipogasto.getDescTipoGasto());
			}

			for (int k = 0; k < modelGastos.columns.size(); k++) {

				Object renderer = null;
				DefaultCellEditor editor = null;

				Integer editortype = modelGastos.columns.get(k).m_editortype;
				switch (editortype) {
				case GastosTableModel.NormalType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelGastos.columns.get(k).m_alignment);

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
				case GastosTableModel.Float2Type: {
					renderer = new Float2Renderer();
					final JFormattedTextField field = (JFormattedTextField) modelGastos.columns
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
				case GastosTableModel.Float4Type: {
					renderer = new Float4Renderer();
					final JFormattedTextField field = (JFormattedTextField) modelGastos.columns
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
				case GastosTableModel.ComboType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelGastos.columns.get(k).m_alignment);
					editor = new DefaultCellEditor(
							(JComboBox) modelGastos.columns.get(k).m_editor);
					break;
				}
				case GastosTableModel.CheckType: {
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
							.setHorizontalAlignment(modelGastos.columns.get(k).m_alignment);
					editor = new DefaultCellEditor(check);
					break;
				}
				case GastosTableModel.StateType: {

					final JLabel stateIcon = new JLabel();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JLabel getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							if (value.equals(GastosTableModel.NewLine))
								stateIcon.setIcon(new ImageIcon(getClass()
										.getResource(
												"/imagesPackage/addline.png")));
							else if (value.equals(GastosTableModel.EditLine))
								stateIcon
										.setIcon(new ImageIcon(
												getClass()
														.getResource(
																"/imagesPackage/editline.png")));

							return stateIcon;
						}

					};
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelGastos.columns.get(k).m_alignment);
					break;
				}
				}

				TableColumn column = tblGastos.getColumn(modelGastos.columns
						.get(k).m_name);
				column.setHeaderValue(modelGastos.columns.get(k).m_title);
				column.setPreferredWidth(modelGastos.columns.get(k).m_width);
				column.setCellRenderer((TableCellRenderer) renderer);
				if (editor != null) {
					column.setCellEditor(editor);
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVehiculo.prepareTable()",
					he);
		}
	}

	public void newData() {
		try {
			this.vehiculo = new Vehiculos();
			prepareTable();
			txtIdVehiculo.setText("");
			txtMatricula.setText("");
			txtMarca.setText("");
			Integer numRows = ((DefaultTableModel) tblGastos.getModel())
					.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblGastos.getModel()).removeRow(0);
			newLineGastos();
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVehiculo.newData()", he);
		}
	}

	private void newLineGastos() {
		try {
			if (tblGastos.getRowCount() > 0) {
				if (!(tblGastos.getValueAt(tblGastos.getSelectedRow(),
						GastosTableModel.columnMes).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblGastos.getModel())
							.addRow(new Object[] { false,
									GastosTableModel.NewLine,
									session.getEjercicio().getEjercicio(), "",
									"", "", "", "" });
					tblGastos.changeSelection(tblGastos.getSelectedRow() + 1,
							GastosTableModel.columnMes, false, false);
					tblGastos.editCellAt(tblGastos.getSelectedRow() + 1,
							GastosTableModel.columnMes);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblGastos.getModel()).addRow(new Object[] {
						false, GastosTableModel.NewLine,
						session.getEjercicio().getEjercicio(), "", "", "", "",
						"" });
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.newLineGastos()", he);
		}

	}

	private static void changeEditState() {
		try {
			if (!changingState) {
				if (tblGastos.getSelectedRow() != -1) {
					changingState = true;
					tblGastos.setValueAt(GastosTableModel.EditLine, tblGastos
							.getSelectedRow(), GastosTableModel.columnState);
					changingState = false;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.changeEditState()", he);
		}
	}

	public void loadData(Vehiculos entity) {
		try {
			this.vehiculo = entity;
			prepareTable();
			txtIdVehiculo.setValue(entity.getId().getIdVehiculo());
			txtMatricula.setText(entity.getMatricula());
			txtMarca.setText(entity.getMarca());
			loadDataDetail();
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVehiculo.loadData()", he);
		}
	}

	private void loadDataDetail() {

		try {
			Integer numRows = ((DefaultTableModel) tblGastos.getModel())
					.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblGastos.getModel()).removeRow(0);

			List<?> gastosList = entity.executeQuery(this, "*",
					"Vehiculosgastos", "id.idVehiculo = "
							+ vehiculo.getId().getIdVehiculo(),
					"ejercicio, mes, idTipoGasto");

			if (gastosList.size() == 0)
				newLineGastos();
			else {
				for (Object gasto : gastosList) {
					Vehiculosgastos vehiculogasto = (Vehiculosgastos) gasto;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(GastosTableModel.EditLine);
					oneRow.add(vehiculogasto.getId().getEjercicios()
							.getEjercicio());
					oneRow.add(vehiculogasto.getMes());
					oneRow.add(vehiculogasto.getIdTipoGasto());
					Tiposgasto tipogasto = entity.TipoGastoFindByIdTipoGasto(
							this, vehiculogasto.getIdTipoGasto());
					if (tipogasto != null)
						oneRow.add(tipogasto.getDescTipoGasto());
					else
						oneRow.add("");
					cboTipoGastoDesc.setSelectedItem(tipogasto
							.getDescTipoGasto());
					oneRow.add(vehiculogasto.getImporte());
					oneRow.add(vehiculogasto.getComentarios());
					((DefaultTableModel) tblGastos.getModel()).addRow(oneRow);
				}
			}
			calculaTotales();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.loadDataDetail()", he);
		}
	}

	/**
	 * @param showmessage
	 * @return
	 */
	public boolean saveData(Boolean showmessage) {
		try {
			if (validateData()) {

				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					txtIdVehiculo.setValue(entity.newId(this, "Vehiculos",
							"id.idVehiculo"));
					VehiculosId vehiculoid = new VehiculosId();
					vehiculoid.setEmpresas(session.getEmpresa());
					vehiculoid.setEjercicios(session.getEjercicio());
					vehiculoid
							.setIdVehiculo((Integer) txtIdVehiculo.getValue());
					vehiculo.setId(vehiculoid);
				}
				if (!txtMatricula.getText().equals(""))
					vehiculo.setMatricula(txtMatricula.getText());
				else
					vehiculo.setMatricula(null);
				if (!txtMarca.getText().equals(""))
					vehiculo.setMarca(txtMarca.getText());
				else
					vehiculo.setMarca(null);
				vehiculo.setLmd(new Date());
				vehiculo.setSid("Santi");
				vehiculo.setVersion(1);
				session.getSession().replicate(vehiculo,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(vehiculo);
				session.getSession().flush();

				String deletelinesquery = "Delete From Vehiculosgastos "
						+ "Where id.idVehiculo = "
						+ ((Number) txtIdVehiculo.getValue()).intValue()
						+ " and id.empresas.idEmpresa="
						+ session.getEmpresa().getIdEmpresa()
						+ " and id.ejercicios.ejercicio="
						+ session.getEjercicio().getEjercicio();

				Query q = getSession().getSession().createQuery(
						deletelinesquery);
				q.executeUpdate();

				for (Integer k = 0; k < tblGastos.getRowCount(); k++) {
					if (tblGastos.getValueAt(k, GastosTableModel.columnState)
							.equals(GastosTableModel.EditLine)) {
						Vehiculosgastos vehiculogasto = new Vehiculosgastos();
						VehiculosgastosId vehiculogastoId = new VehiculosgastosId();
						vehiculogastoId.setIdVehiculo((Integer) txtIdVehiculo
								.getValue());
						vehiculogastoId.setIdGasto(k + 1);
						vehiculogastoId.setEmpresas(session.getEmpresa());
						vehiculogastoId.setEjercicios(session.getEjercicio());
						vehiculogasto.setId(vehiculogastoId);
						vehiculogasto.setMes((Integer) tblGastos.getValueAt(k,
								GastosTableModel.columnMes));
						vehiculogasto.setIdTipoGasto((Integer) tblGastos
								.getValueAt(k, GastosTableModel.columnIdGasto));
						vehiculogasto.setImporte((Float) tblGastos.getValueAt(
								k, GastosTableModel.columnImporte));
						if (!tblGastos.getValueAt(k,
								GastosTableModel.columnComentarios).equals(""))
							vehiculogasto
									.setComentarios((String) tblGastos
											.getValueAt(
													k,
													GastosTableModel.columnComentarios));
						else
							vehiculogasto.setComentarios(null);
						vehiculogasto.setLmd(new Date());
						vehiculogasto.setSid("Santi");
						vehiculogasto.setVersion(1);
						session.getSession().replicate(vehiculogasto,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(vehiculogasto);
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
				FrmVehiculos.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVehiculo.saveData()", he);
			return false;
		}
	}

	private boolean validateData() {
		try {
			if (txtMarca.getText().equals("")) {
				Message.ShowValidateMessage(pnlData, "Debe indicar una marca.");
				txtMarca.requestFocus();
				return (false);
			}
			if (!validateGastosData())
				return (false);

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVehiculo.validateData()",
					he);
			return (false);
		}
	}

	private boolean validateGastosData() {
		try {
			for (Integer actualrow = 0; actualrow < tblGastos.getRowCount(); actualrow++) {
				if (tblGastos.getValueAt(actualrow,
						GastosTableModel.columnState).equals(
						GastosTableModel.EditLine)) {
					if (tblGastos.getValueAt(actualrow,
							GastosTableModel.columnAno).equals("")) {
						Message.ShowValidateMessage(tblGastos,
								"Debe indicar un a�o.");
						tblGastos.changeSelection(actualrow,
								GastosTableModel.columnAno, false, false);
						tblGastos.editCellAt(actualrow,
								GastosTableModel.columnAno);
						tblGastos.requestFocusInWindow();
						return (false);
					}
					if (tblGastos.getValueAt(actualrow,
							GastosTableModel.columnMes).equals("")) {
						Message.ShowValidateMessage(tblGastos,
								"Debe indicar un mes.");
						tblGastos.changeSelection(actualrow,
								GastosTableModel.columnMes, false, false);
						tblGastos.editCellAt(actualrow,
								GastosTableModel.columnMes);
						tblGastos.requestFocusInWindow();
						return (false);
					}
					if (tblGastos.getValueAt(actualrow,
							GastosTableModel.columnIdGasto).equals("")) {
						Message.ShowValidateMessage(tblGastos,
								"Debe indicar un tipo de gasto.");
						tblGastos.changeSelection(actualrow,
								GastosTableModel.columnIdGasto, false, false);
						tblGastos.editCellAt(actualrow,
								GastosTableModel.columnIdGasto);
						tblGastos.requestFocusInWindow();
						return (false);
					}
					if (tblGastos.getValueAt(actualrow,
							GastosTableModel.columnImporte).equals("")) {
						Message.ShowValidateMessage(tblGastos,
								"Debe indicar un importe.");
						tblGastos.changeSelection(actualrow,
								GastosTableModel.columnImporte, false, false);
						tblGastos.editCellAt(actualrow,
								GastosTableModel.columnImporte);
						tblGastos.requestFocusInWindow();
						return (false);
					}
				}
			}
			calculaTotales();
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.validateGastosData()", he);
			return (false);
		}
	}

	private void changeTipoGasto() {
		try {
			if (!changingTipoGasto) {
				if (!(tblGastos.getValueAt(tblGastos.getSelectedRow(),
						tblGastos.getSelectedColumn()).equals(""))) {
					Integer value = (Integer) tblGastos.getValueAt(tblGastos
							.getSelectedRow(), tblGastos.getSelectedColumn());
					changingTipoGasto = true;
					if (value.equals(""))
						tblGastos.setValueAt(null, tblGastos.getSelectedRow(),
								GastosTableModel.columnIdGastoDesc);
					else {
						if (((DefaultTableModel) tblGastos.getModel())
								.getRowCount() > 0) {
							Tiposgasto tipogasto = entity
									.TipoGastoFindByIdTipoGasto(this, value);
							if (tipogasto != null)
								tblGastos.setValueAt(tipogasto
										.getDescTipoGasto(), tblGastos
										.getSelectedRow(),
										GastosTableModel.columnIdGastoDesc);
							else {
								Message.ShowValidateMessage(tblGastos,
										"El tipo de gasto indicado no existe.");
								tblGastos.setValueAt(null, tblGastos
										.getSelectedRow(),
										GastosTableModel.columnIdGastoDesc);
								tblGastos.requestFocusInWindow();
							}
						}
					}
				}
				changingTipoGasto = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.changeTipoGasto()", he);
		}
	}

	private void calculaTotales() {
		try {
			Float totalGastos = ((Number) 0).floatValue();

			Integer numRows = tblGastos.getRowCount();
			for (Integer actualRow = 0; actualRow < numRows; actualRow++) {
				if (tblGastos.getValueAt(actualRow,
						GastosTableModel.columnState).equals(
						GastosTableModel.EditLine)) {
					if (!(tblGastos.getValueAt(actualRow,
							GastosTableModel.columnImporte).equals(""))) {
						totalGastos = totalGastos
								+ (Float) (((DefaultTableModel) tblGastos
										.getModel()).getValueAt(actualRow,
										GastosTableModel.columnImporte));
					}
				}
			}

			txtTotalGastos.setValue(totalGastos.floatValue());
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.calculaTotales()", he);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		pnlData = new javax.swing.JPanel();
		btnOk = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		lblIdVehiculo = new javax.swing.JLabel();
		lblMarca = new javax.swing.JLabel();
		txtMarca = new javax.swing.JTextField();
		txtIdVehiculo = new javax.swing.JFormattedTextField();
		lblMatricula = new javax.swing.JLabel();
		txtMatricula = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblGastos = new javax.swing.JTable();
		cboTipoGastoDesc = new javax.swing.JComboBox();
		txtImporte = new javax.swing.JFormattedTextField();
		cmdSelectAll = new javax.swing.JButton();
		cmdDeselectAll = new javax.swing.JButton();
		cmdDeleteGastos = new javax.swing.JButton();
		lblTotalGastos = new javax.swing.JLabel();
		txtTotalGastos = new javax.swing.JFormattedTextField();

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData.setName("pnlData");

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
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});

		lblIdVehiculo.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblIdVehiculo.setForeground(new java.awt.Color(255, 0, 0));
		lblIdVehiculo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblIdVehiculo.setText("Id veh�culo");

		lblMarca.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblMarca.setForeground(new java.awt.Color(255, 0, 0));
		lblMarca.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblMarca.setText("Marca");

		txtMarca.setFont(new java.awt.Font("Tahoma", 0, 14));
		txtMarca.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtMarca.setAutoscrolls(false);
		txtMarca.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtMarca.setName("nombreCategoria");
		txtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtMarcaKeyTyped(evt);
			}
		});

		txtIdVehiculo.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdVehiculo.setEditable(false);
		txtIdVehiculo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdVehiculo.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblMatricula.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblMatricula.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblMatricula.setText("Matr�cula");

		txtMatricula.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtMatricula.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtMatricula.setAutoscrolls(false);
		txtMatricula.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtMatricula.setName("nombreCategoria");
		txtMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtMatriculaKeyTyped(evt);
			}
		});

		jScrollPane1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		tblGastos.setFont(new java.awt.Font("Segoe UI", 0, 14));
		tblGastos.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] {

				}));
		tblGastos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblGastos.setCellSelectionEnabled(true);
		tblGastos.setRowHeight(18);
		tblGastos.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tblGastosKeyPressed(evt);
			}
		});
		jScrollPane1.setViewportView(tblGastos);

		cboTipoGastoDesc.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "" }));
		cboTipoGastoDesc.setPreferredSize(new java.awt.Dimension(0, 0));
		cboTipoGastoDesc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboTipoGastoDescActionPerformed(evt);
			}
		});

		txtImporte
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtImporte.setPreferredSize(new java.awt.Dimension(0, 0));

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
		cmdDeselectAll.setText("Quitar selecci�n");
		cmdDeselectAll.setToolTipText("Quitar la seleccionar todas las filas");
		cmdDeselectAll.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdDeselectAll.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdDeselectAllMousePressed(evt);
			}
		});

		cmdDeleteGastos.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdDeleteGastos.setText("Eliminar gastos");
		cmdDeleteGastos.setToolTipText("Eliminar gastos seleccionados");
		cmdDeleteGastos.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdDeleteGastos.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdDeleteGastosMousePressed(evt);
			}
		});

		lblTotalGastos.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblTotalGastos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTotalGastos.setText("Total gastos");

		txtTotalGastos.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalGastos.setEditable(false);
		txtTotalGastos
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtTotalGastos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalGastos.setFont(new java.awt.Font("Segoe UI", 1, 14));

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
										.addContainerGap()
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																654,
																Short.MAX_VALUE)
														.addGroup(
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
																										.addComponent(
																												lblIdVehiculo,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												txtIdVehiculo,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblMatricula,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												txtMatricula,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
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
																												cmdDeleteGastos,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												157,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addGap(
																				56,
																				56,
																				56)
																		.addComponent(
																				btnCancel)
																		.addGap(
																				7,
																				7,
																				7)
																		.addComponent(
																				btnOk))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblTotalGastos,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				124,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txtTotalGastos,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				90,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblMarca,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				txtMarca,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				524,
																				Short.MAX_VALUE)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												txtImporte,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(316, 316, 316)
										.addComponent(
												cboTipoGastoDesc,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		pnlDataLayout
				.setVerticalGroup(pnlDataLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlDataLayout
										.createSequentialGroup()
										.addGap(55, 55, 55)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblIdVehiculo)
														.addComponent(
																txtIdVehiculo,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(10, 10, 10)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblMatricula)
														.addComponent(
																txtMatricula,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(lblMarca)
														.addComponent(
																txtMarca,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												165, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
																								cmdSelectAll)
																						.addComponent(
																								cmdDeselectAll)
																						.addComponent(
																								cmdDeleteGastos))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								cboTipoGastoDesc,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								8,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								txtImporte,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																pnlDataLayout
																		.createSequentialGroup()
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								txtTotalGastos,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblTotalGastos))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								btnOk)
																						.addComponent(
																								btnCancel))))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 684,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	}// </editor-fold>
	//GEN-END:initComponents

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	void cmdDeleteGastosMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblGastos.getRowCount(); actualrow++) {
				if ((Boolean) tblGastos.getValueAt(actualrow,
						GastosTableModel.columnSelect) == true) {
					((DefaultTableModel) tblGastos.getModel())
							.removeRow(actualrow);
					actualrow--;
				}
			}
			calculaTotales();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.cmdDeleteGastosMousePressed()", he);
		}
	}

	private void cmdSelectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblGastos.getRowCount(); actualrow++)
				tblGastos.setValueAt(true, actualrow,
						GastosTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.cmdSelectAllMousePressed()", he);
		}
	}

	private void cmdDeselectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblGastos.getRowCount(); actualrow++)
				tblGastos.setValueAt(false, actualrow,
						GastosTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.cmdDeselectAllMousePressed()", he);
		}
	}

	private void tblGastosKeyPressed(java.awt.event.KeyEvent evt) {
		try {
			if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				if ((tblGastos.getSelectedRow() == ((DefaultTableModel) tblGastos
						.getModel()).getRowCount() - 1)
						&& (tblGastos.getValueAt(tblGastos.getSelectedRow(),
								GastosTableModel.columnState)
								.equals(GastosTableModel.EditLine))) {
					if ((tblGastos.getSelectedColumn() == tblGastos
							.getColumnCount() - 1)) {
						newLineGastos();
						evt.setKeyCode(0);
					} else
						evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
				} else
					evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
			} else {
				if ((evt.getKeyCode() == 32 || evt.getKeyCode() == 164)
						|| (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57)
						|| (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 122))
					tblGastos.setValueAt("", tblGastos.getSelectedRow(),
							tblGastos.getSelectedColumn());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.tblGastosKeyPressed()", he);
		}
	}

	private void cboTipoGastoDescActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingTipoGasto) {
				changingTipoGasto = true;
				if (cboTipoGastoDesc.getSelectedIndex() != -1) {
					if (tblGastos.getRowCount() > 0
							&& tblGastos.getSelectedRow() != -1) {
						List<?> tiposgastoslist = entity
								.TipoGastoFindByDescTipoGasto(this,
										(String) cboTipoGastoDesc
												.getSelectedItem());
						if (tiposgastoslist.size() > 0) {
							Tiposgasto tiposgastosItem = (Tiposgasto) tiposgastoslist
									.get(0);
							tblGastos.setValueAt(tiposgastosItem.getId()
									.getIdTipoGasto(), tblGastos
									.getSelectedRow(),
									GastosTableModel.columnIdGasto);
						}
					}
				}
				changingTipoGasto = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.cboTipoGastoDescActionPerformed()", he);
		}
	}

	private void txtMarcaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtMarca, evt, 20);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.txtMarcaKeyTyped()", he);
		}
	}

	private void txtMatriculaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtMatricula, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.txtMatriculaKeyTyped()", he);
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
					"FrmVehiculo.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.vehiculo);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVehiculo.btnCancelMousePressed()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private static javax.swing.JComboBox cboTipoGastoDesc;
	private javax.swing.JButton cmdDeleteGastos;
	private javax.swing.JButton cmdDeselectAll;
	private javax.swing.JButton cmdSelectAll;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblIdVehiculo;
	private javax.swing.JLabel lblMarca;
	private javax.swing.JLabel lblMatricula;
	private javax.swing.JLabel lblTotalGastos;
	public javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblGastos;
	private javax.swing.JFormattedTextField txtIdVehiculo;
	private static javax.swing.JFormattedTextField txtImporte;
	private javax.swing.JTextField txtMarca;
	private javax.swing.JTextField txtMatricula;
	private javax.swing.JFormattedTextField txtTotalGastos;
	// End of variables declaration//GEN-END:variables

}
