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

import entitiesPackage.Barcos;
import entitiesPackage.Categorias;
import entitiesPackage.Conductores;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;
import entitiesPackage.Puertos;
import entitiesPackage.Receptores;
import entitiesPackage.Vehiculos;
import entitiesPackage.Ventascabecera;
import entitiesPackage.VentascabeceraId;
import entitiesPackage.Ventaslineas;
import entitiesPackage.VentaslineasId;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmVenta extends javax.swing.JPanel {

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
		final public static int columnNumCajas = 4;
		final public static int columnNumKilos = 5;
		final public static int columnPrecio = 6;
		final public static int columnImporte = 7;

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
					"CategoriaDesc", "NumCajas", "NumKilos", "Precio",
					"Importe" });

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
			columns.add(new ColumnData("CategoriaDesc", "Descripción",
					EntityType.ComboWidth, SwingConstants.LEFT, ComboType,
					cboCategoriaDesc, null));
			columns
					.add(new ColumnData("NumCajas", "Número de cajas",
							EntityType.NumberWidth, SwingConstants.RIGHT, 0,
							null, null));
			columns.add(new ColumnData("NumKilos", "Número de Kilos",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtNumKilos, "#,##0.00"));
			columns.add(new ColumnData("Precio", "Precio",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float4Type,
					txtPrecio, "#,##0.0000"));
			columns.add(new ColumnData("Importe", "Importe",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float4Type,
					txtImporte, "#,##0.0000"));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (!changingState && !addLine)
						changeEditState();
					if (e.getColumn() == columnIdCategoria) {
						changeCategoria();
					}
					if (e.getColumn() == columnPrecio) {
						calculaImporte();
					}
					if (e.getColumn() == columnNumCajas) {
						calculaNumKilos();
					}
					if (e.getColumn() == columnNumKilos) {
						calculaImporte();
					}
				}
			});
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
		 */
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
			case columnNumCajas: {
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
			}
			return super.getValueAt(fila, columna);
		}

	}

	Ventascabecera venta;

	static boolean changingState = false;
	static boolean addLine = false;
	static boolean changingCategoria = false;
	static boolean changingReceptor = false;
	static boolean changingBarco = false;
	static boolean changingVehiculo = false;
	static boolean changingConductor = false;
	static boolean changingPuerto = false;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private boolean OnNew = false;
	private static Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return FrmVenta.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmVenta.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmVenta() {
		initComponents();
	}

	public FrmVenta(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmVenta.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent, "FrmVenta()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmVenta.configureKeys", he);
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
			Message
					.ShowRuntimeError(parentFrame, "FrmVenta.prepareTable()",
							he);
		}
	}

	private void loadBarcos() {
		try {
			cboBarcos.removeAllItems();
			List<?> barcosList = entity.BarcoFindAll(this);

			for (Object o : barcosList) {
				Barcos barco = (Barcos) o;
				cboBarcos.addItem(barco.getNombreBarco());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.loadBarcos()", he);
		}
	}

	private void loadConductores() {
		try {
			cboConductores.removeAllItems();
			List<?> conductoresList = entity.ConductorFindAll(this);

			for (Object o : conductoresList) {
				Conductores conductor = (Conductores) o;
				String nombre = conductor.getNombre();
				String apellidos = conductor.getApellidos();
				if (apellidos != null)
					nombre = nombre + " " + apellidos;
				cboConductores.addItem(nombre);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.loadConductores()",
					he);
		}
	}

	private void loadVehiculos() {
		try {
			cboVehiculos.removeAllItems();
			List<?> vehiculosList = entity.VehiculoFindAll(this);

			for (Object o : vehiculosList) {
				Vehiculos vehiculo = (Vehiculos) o;
				String marca = vehiculo.getMarca();
				String matricula = vehiculo.getMatricula();
				if (matricula != null)
					marca = marca + " " + matricula;
				cboVehiculos.addItem(marca);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.loadVehiculos()",
					he);
		}
	}

	private void loadPuertos() {
		try {

			cboPuertos.removeAllItems();
			List<?> puertosList = entity.PuertoFindAll(this);

			for (Object o : puertosList) {
				Puertos puerto = (Puertos) o;
				cboPuertos.addItem(puerto.getNombrePuerto());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.loadPuertos()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmVenta.loadReceptores()",
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
			Message.ShowRuntimeError(parentFrame, "FrmVenta.loadCategorias()",
					he);
		}
	}

	public void newData() {
		try {
			this.venta = new Ventascabecera();
			prepareTable();
			loadReceptores();
			loadBarcos();
			loadVehiculos();
			loadConductores();
			loadPuertos();
			txtIdVenta.setValue(null);
			txtAno.setValue(session.getEjercicio().getEjercicio());
			txtFecha.setText("");
			txtSemana.setText("");
			txtIdReceptor.setText("");
			cboReceptores.setSelectedItem(null);
			txtIdBarco.setText("");
			cboBarcos.setSelectedItem(null);
			txtIdVehiculo.setText("");
			cboVehiculos.setSelectedItem(null);
			txtIdConductor.setText("");
			cboConductores.setSelectedItem(null);
			txtIdPuerto.setText("");
			cboPuertos.setSelectedItem(null);
			txtPlataforma.setText("");
			Integer numRows = tblDetalle.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblDetalle.getModel()).removeRow(0);
			newLineDetalle();
			OnNew = true;
			txtSemana.requestFocusInWindow();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.newData()", he);
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
									DetalleTableModel.NewLine, "", "", "", "",
									0, 0 });
					tblDetalle.changeSelection(tblDetalle.getSelectedRow() + 1,
							DetalleTableModel.columnIdCategoria, false, false);
					tblDetalle.editCellAt(tblDetalle.getSelectedRow() + 1,
							DetalleTableModel.columnIdCategoria);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblDetalle.getModel())
						.addRow(new Object[] { false,
								DetalleTableModel.NewLine, "", "", "", "", 0, 0 });

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.newLineDetalle()",
					he);
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
			Message.ShowRuntimeError(parentFrame, "FrmVenta.changeEdiState()",
					he);
		}
	}

	public void loadData(Ventascabecera entity) {
		try {
			this.venta = entity;
			prepareTable();
			loadReceptores();
			loadBarcos();
			loadVehiculos();
			loadConductores();
			loadPuertos();
			txtIdVenta.setValue(entity.getId().getIdVenta());
			txtAno.setValue(entity.getId().getEjercicios().getEjercicio());
			txtSemana.setValue(entity.getSemana());
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
			String strFecha = df.format(entity.getFecha());
			txtFecha.setValue(strFecha);
			txtIdReceptor.setValue(entity.getIdReceptor());
			txtIdBarco.setValue(entity.getIdBarco());
			txtIdVehiculo.setValue(entity.getIdVehiculo());
			txtIdConductor.setValue(entity.getIdConductor());
			txtIdPuerto.setValue(entity.getIdPuerto());
			txtPlataforma.setText(entity.getPlataforma());
			changeReceptor();
			changeBarco();
			changeVehiculo();
			changeConductor();
			changePuerto();
			loadDataDetail();
			OnNew = false;
			txtSemana.requestFocusInWindow();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.loadData()", he);
		}
	}

	private void loadDataDetail() {

		try {
			Integer numRows = tblDetalle.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblDetalle.getModel()).removeRow(0);

			List<?> lineasList = entity.executeQuery(this, "*", "Ventaslineas",
					"id.idVenta = " + venta.getId().getIdVenta(),
					"id.idCategoria");

			if (lineasList.size() == 0)
				newLineDetalle();
			else {
				for (Object linea : lineasList) {
					Ventaslineas Ventaslinea = (Ventaslineas) linea;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(DetalleTableModel.EditLine);
					oneRow.add(Ventaslinea.getId().getIdCategoria());
					Categorias categoria = entity.CategoriaFindByIdCategoria(
							this, Ventaslinea.getId().getIdCategoria());
					if (categoria != null)
						cboCategoriaDesc.setSelectedItem(categoria
								.getNombreCategoria());
					oneRow.add(cboCategoriaDesc.getSelectedItem());
					oneRow.add(Ventaslinea.getNumCajas());
					oneRow.add(Ventaslinea.getNumKilos());
					oneRow.add(Ventaslinea.getPrecio());
					oneRow.add(Ventaslinea.getImporte());
					((DefaultTableModel) tblDetalle.getModel()).addRow(oneRow);
				}
			}
			calculaTotales();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.loadDataDetail()",
					he);
		}
	}

	public boolean saveData(Boolean showmessage) {
		try {
			if (validateData()) {

				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					txtIdVenta.setValue(entity.newId(this, "Ventascabecera",
							"id.idVenta"));
					VentascabeceraId ventaid = new VentascabeceraId();
					ventaid.setIdVenta(((Number) txtIdVenta.getValue())
							.intValue());
					ventaid.setEjercicios(session.getEjercicio());
					ventaid.setEmpresas(session.getEmpresa());
					venta.setId(ventaid);
				}
				if (!txtSemana.getText().equals(""))
					venta.setSemana(((Number) txtSemana.getValue()).intValue());
				else
					venta.setSemana(null);
				if (!txtFecha.getText().equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtFecha.getText());
					venta.setFecha(value);
				} else
					venta.setFecha(null);
				if (!txtIdReceptor.getText().equals("")) {
					int idReceptor = ((Number) txtIdReceptor.getValue())
							.intValue();
					venta.setIdReceptor(idReceptor);
					venta.setIdZona(entity.ReceptorGetZona(parentFrame,
							idReceptor));
				} else {
					venta.setIdReceptor(null);
					venta.setIdZona(null);
				}
				if (!txtIdBarco.getText().equals(""))
					venta.setIdBarco(((Number) txtIdBarco.getValue())
							.intValue());
				else
					venta.setIdBarco(null);
				if (!txtIdVehiculo.getText().equals(""))
					venta.setIdVehiculo(((Number) txtIdVehiculo.getValue())
							.intValue());
				else
					venta.setIdVehiculo(null);
				if (!txtIdConductor.getText().equals(""))
					venta.setIdConductor(((Number) txtIdConductor.getValue())
							.intValue());
				else
					venta.setIdConductor(null);
				if (!txtIdPuerto.getText().equals("")) {
					venta.setIdPuerto(((Number) txtIdPuerto.getValue())
							.intValue());
				} else
					venta.setIdPuerto(null);
				if (!txtPlataforma.getText().equals(""))
					venta.setPlataforma((String) txtPlataforma.getText());
				else
					venta.setPlataforma(null);
				venta.setLmd(new Date());
				venta.setSid("Santi");
				venta.setVersion(1);
				session.getSession()
						.replicate(venta, ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(venta);
				session.getSession().flush();

				String deletelinesquery = "Delete From Ventaslineas "
						+ "Where id.idVenta = "
						+ ((Number) txtIdVenta.getValue()).intValue()
						+ " and id.empresas.idEmpresa="
						+ session.getEmpresa().getIdEmpresa()
						+ " and id.ejercicios.ejercicio="
						+ session.getEjercicio().getEjercicio();

				Query q = getSession().getSession().createQuery(
						deletelinesquery);
				q.executeUpdate();

				for (Integer k = 0; k < tblDetalle.getRowCount(); k++) {
					if (tblDetalle.getValueAt(k, DetalleTableModel.columnState)
							.equals(DetalleTableModel.EditLine)) {
						transaction = session.getSession().beginTransaction();
						Ventaslineas ventalinea = new Ventaslineas();
						VentaslineasId ventalineaId = new VentaslineasId();
						ventalineaId.setEjercicios(session.getEjercicio());
						ventalineaId.setEmpresas(session.getEmpresa());
						ventalineaId
								.setIdVenta(((Number) txtIdVenta.getValue())
										.intValue());
						ventalineaId.setIdCategoria((Integer) tblDetalle
								.getValueAt(k,
										DetalleTableModel.columnIdCategoria));
						ventalinea.setId(ventalineaId);
						ventalinea.setNumCajas((Integer) tblDetalle.getValueAt(
								k, DetalleTableModel.columnNumCajas));
						ventalinea.setNumKilos((Float) tblDetalle.getValueAt(k,
								DetalleTableModel.columnNumKilos));
						if (!(tblDetalle.getValueAt(k,
								DetalleTableModel.columnPrecio).equals("")))
							ventalinea.setPrecio((Float) tblDetalle.getValueAt(
									k, DetalleTableModel.columnPrecio));
						else
							ventalinea.setPrecio(((Number) 0).floatValue());
						if (!(tblDetalle.getValueAt(k,
								DetalleTableModel.columnImporte).equals("")))
							ventalinea.setImporte((Float) tblDetalle
									.getValueAt(k,
											DetalleTableModel.columnImporte));
						else
							ventalinea.setImporte(((Number) 0).floatValue());
						ventalinea.setLmd(new Date());
						ventalinea.setSid("Santi");
						ventalinea.setVersion(1);
						session.getSession().replicate(ventalinea,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(ventalinea);
						session.getSession().flush();
					}
				}
				if (!transaction.wasCommitted()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				OnNew = false;
				FrmVentas.runSearchQuery();
				txtSemana.requestFocusInWindow();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.saveData()", he);
			return false;
		} catch (ParseException e) {
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
			if (txtIdReceptor.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un receptor.");
				txtIdReceptor.requestFocus();
				return (false);
			} else {
				try {
					txtIdReceptor.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdReceptor.requestFocus();
					return (false);
				}
			}
			if (!txtIdBarco.getText().equals("")) {
				try {
					txtIdBarco.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdBarco.requestFocus();
					return (false);
				}
			}
			if (!txtIdVehiculo.getText().equals("")) {
				try {
					txtIdVehiculo.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdVehiculo.requestFocus();
					return (false);
				}
			}
			if (!txtIdConductor.getText().equals("")) {
				try {
					txtIdConductor.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdConductor.requestFocus();
					return (false);
				}
			}
			if (!txtIdPuerto.getText().equals("")) {
				try {
					txtIdPuerto.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdPuerto.requestFocus();
					return (false);
				}
			}
			if (!validateDetalleData())
				return (false);

			return (true);
		} catch (RuntimeException he) {
			Message
					.ShowRuntimeError(parentFrame, "FrmVenta.validateData()",
							he);
			return (false);
		}
	}

	private boolean validateDetalleData() {
		try {
			for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++) {
				if (tblDetalle.getValueAt(actualrow,
						DetalleTableModel.columnState).equals(
						DetalleTableModel.EditLine)) {
					if (tblDetalle.getValueAt(actualrow,
							DetalleTableModel.columnIdCategoria).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar un categoría.");
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
								"Debe indicar un número de kilos.");
						tblDetalle.changeSelection(tblDetalle.getSelectedRow(),
								DetalleTableModel.columnNumKilos, false, false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnNumKilos);
						tblDetalle.requestFocusInWindow();
						return (false);
					}
					if (tblDetalle.getValueAt(actualrow,
							DetalleTableModel.columnNumCajas).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar un número de cajas.");
						tblDetalle.changeSelection(tblDetalle.getSelectedRow(),
								DetalleTableModel.columnNumCajas, false, false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnNumCajas);
						tblDetalle.requestFocusInWindow();
						return (false);
					}
				}
			}
			calculaTotales();
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.validateDetalleData()", he);
			return (false);
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
			Message.ShowRuntimeError(parentFrame, "FrmVenta.changeCategoria()",
					he);
		}
	}

	private void changeVehiculo() {
		try {
			if (!changingVehiculo) {
				changingVehiculo = true;
				if (txtIdVehiculo.getText().equals(""))
					cboVehiculos.setSelectedItem(null);
				else {
					try {
						Integer value = Integer.parseInt(txtIdVehiculo
								.getText());
						Vehiculos vehiculo = entity.VehiculoFindByIdVehiculo(
								this, value);
						if (vehiculo != null) {
							String marca = vehiculo.getMarca();
							String matricula = vehiculo.getMatricula();
							if (matricula != null)
								marca = marca + " " + matricula;
							cboVehiculos.setSelectedItem(marca);
						} else {
							cboVehiculos.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboVehiculos.setSelectedItem(null);
					}
				}
				changingVehiculo = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.changeVehiculo()",
					he);
		}
	}

	private void changeConductor() {
		try {
			if (!changingConductor) {
				changingConductor = true;
				if (txtIdConductor.getText().equals(""))
					cboConductores.setSelectedItem(null);
				else {
					try {
						Integer value = Integer.parseInt(txtIdConductor
								.getText());
						Conductores conductor = entity
								.ConductorFindByIdConductor(this, value);
						if (conductor != null) {
							String nombre = conductor.getNombre();
							String apellidos = conductor.getApellidos();
							if (apellidos != null)
								nombre = nombre + " " + apellidos;
							cboConductores.setSelectedItem(nombre);
						} else {
							cboConductores.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboConductores.setSelectedItem(null);
					}
				}
				changingConductor = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.changeConductor()",
					he);
		}
	}

	private void changePuerto() {
		try {
			if (!changingPuerto) {
				changingPuerto = true;
				if (txtIdPuerto.getText().equals(""))
					cboPuertos.setSelectedItem(null);
				else {
					try {
						Integer value = Integer.parseInt(txtIdPuerto.getText());
						Puertos puerto = entity.PuertoFindByIdPuerto(this,
								value);
						if (puerto != null)
							cboPuertos
									.setSelectedItem(puerto.getNombrePuerto());
						else {
							cboPuertos.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboPuertos.setSelectedItem(null);
					}
				}
				changingPuerto = false;
			}
		} catch (RuntimeException he) {
			Message
					.ShowRuntimeError(parentFrame, "FrmVenta.changePuerto()",
							he);
		}
	}

	private void changeBarco() {
		try {
			if (!changingBarco) {
				changingBarco = true;
				if (txtIdBarco.getText().equals(""))
					cboBarcos.setSelectedItem(null);
				else {
					try {
						Integer value = Integer.parseInt(txtIdBarco.getText());
						Barcos barco = entity.BarcoFindByIdBarco(this, value);
						if (barco != null)
							cboBarcos.setSelectedItem(barco.getNombreBarco());
						else {
							cboBarcos.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboBarcos.setSelectedItem(null);
					}
				}
				changingBarco = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.changeBarco()", he);
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
				} catch (ParseException e) {

				}
			}

		} catch (RuntimeException he) {
			Message
					.ShowRuntimeError(parentFrame, "FrmVenta.changeSemana()",
							he);
		}
	}

	private void calculaTotales() {
		try {
			Integer totalNumCajas = ((Number) 0).intValue();
			Float totalNumKilos = ((Number) 0).floatValue();
			Float totalImporte = ((Number) 0).floatValue();

			Integer numRows = tblDetalle.getRowCount();
			for (Integer actualRow = 0; actualRow < numRows; actualRow++) {
				if (tblDetalle.getValueAt(actualRow,
						DetalleTableModel.columnState).equals(
						DetalleTableModel.EditLine)
						&& !(tblDetalle.getValueAt(actualRow,
								DetalleTableModel.columnImporte).equals(""))) {
					totalNumCajas = totalNumCajas
							+ (Integer) (((DefaultTableModel) tblDetalle
									.getModel()).getValueAt(actualRow,
									DetalleTableModel.columnNumCajas));
					totalNumKilos = totalNumKilos
							+ (Float) (((DefaultTableModel) tblDetalle
									.getModel()).getValueAt(actualRow,
									DetalleTableModel.columnNumKilos));
					totalImporte = totalImporte
							+ (Float) (((DefaultTableModel) tblDetalle
									.getModel()).getValueAt(actualRow,
									DetalleTableModel.columnImporte));
				}
			}

			txtTotalNumCajas.setValue(totalNumCajas.intValue());
			txtTotalNumKilos.setValue(totalNumKilos.floatValue());
			txtTotalImporte.setValue(totalImporte.floatValue());
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.calculaTotales()",
					he);
		}
	}

	private static void calculaNumKilos() {
		try {
			if (tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
					DetalleTableModel.columnIdCategoria).equals("")
					|| tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
							DetalleTableModel.columnNumCajas).equals("")) {

			} else {
				int categoria = (((Number) tblDetalle.getValueAt(tblDetalle
						.getSelectedRow(), DetalleTableModel.columnIdCategoria))
						.intValue());
				float KilosCaja = entity.CategoriaGetKilosCaja(parentFrame,
						categoria);
				int NumCajas = (((Number) tblDetalle.getValueAt(tblDetalle
						.getSelectedRow(), DetalleTableModel.columnNumCajas))
						.intValue());

				tblDetalle.setValueAt(KilosCaja * NumCajas, tblDetalle
						.getSelectedRow(), DetalleTableModel.columnNumKilos);
				calculaImporte();
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.calculaNumKilos()",
					he);
		}
	}

	private static void calculaImporte() {
		try {
			if (tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
					DetalleTableModel.columnNumKilos).equals("")
					|| tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
							DetalleTableModel.columnPrecio).equals("")) {

			} else {
				Float NumKilos = (((Number) tblDetalle.getValueAt(tblDetalle
						.getSelectedRow(), DetalleTableModel.columnNumKilos))
						.floatValue());
				Float Precio = (((Number) tblDetalle.getValueAt(tblDetalle
						.getSelectedRow(), DetalleTableModel.columnPrecio))
						.floatValue());

				tblDetalle.setValueAt(Util.round(Precio * NumKilos, 2),
						tblDetalle.getSelectedRow(),
						DetalleTableModel.columnImporte);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmVenta.calculaImporte()",
					he);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		pnlData = new javax.swing.JPanel();
		txtIdVenta = new javax.swing.JFormattedTextField();
		btnOk = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		lblIdVenta = new javax.swing.JLabel();
		lblVehiculo = new javax.swing.JLabel();
		lblFecha = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblDetalle = new javax.swing.JTable();
		txtImporte = new javax.swing.JFormattedTextField();
		cmdSelectAll = new javax.swing.JButton();
		cmdDeselectAll = new javax.swing.JButton();
		cmdDeleteLinea = new javax.swing.JButton();
		lblSemana = new javax.swing.JLabel();
		txtSemana = new javax.swing.JFormattedTextField();
		lblBarco = new javax.swing.JLabel();
		lblAno = new javax.swing.JLabel();
		txtAno = new javax.swing.JFormattedTextField();
		cboBarcos = new javax.swing.JComboBox();
		cboVehiculos = new javax.swing.JComboBox();
		txtFecha = new javax.swing.JFormattedTextField();
		txtIdBarco = new javax.swing.JFormattedTextField();
		txtIdVehiculo = new javax.swing.JFormattedTextField();
		lblPlataforma = new javax.swing.JLabel();
		lblConductor = new javax.swing.JLabel();
		txtIdConductor = new javax.swing.JFormattedTextField();
		cboConductores = new javax.swing.JComboBox();
		lblPuerto = new javax.swing.JLabel();
		txtIdPuerto = new javax.swing.JFormattedTextField();
		cboPuertos = new javax.swing.JComboBox();
		txtPlataforma = new javax.swing.JTextField();
		txtNumCajas = new javax.swing.JFormattedTextField();
		txtPrecio = new javax.swing.JFormattedTextField();
		cboCategoriaDesc = new javax.swing.JComboBox();
		lblNumcajas = new javax.swing.JLabel();
		txtTotalNumCajas = new javax.swing.JFormattedTextField();
		txtTotalNumKilos = new javax.swing.JFormattedTextField();
		lblImporte = new javax.swing.JLabel();
		txtTotalImporte = new javax.swing.JFormattedTextField();
		txtNumKilos = new javax.swing.JFormattedTextField();
		lblTotalKilos = new javax.swing.JLabel();
		lblReceptor = new javax.swing.JLabel();
		txtIdReceptor = new javax.swing.JFormattedTextField();
		cboReceptores = new javax.swing.JComboBox();

		pnlData.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		pnlData.setName("pnlData");
		pnlData.setPreferredSize(new java.awt.Dimension(1024, 768));

		txtIdVenta.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdVenta.setEditable(false);
		txtIdVenta
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtIdVenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdVenta.setFocusable(false);
		txtIdVenta.setFont(new java.awt.Font("Segoe UI", 0, 14));

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

		lblIdVenta.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblIdVenta.setForeground(new java.awt.Color(255, 0, 0));
		lblIdVenta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblIdVenta.setText("Id venta");

		lblVehiculo.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblVehiculo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblVehiculo.setText("Vehiculo");

		lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblFecha.setForeground(new java.awt.Color(255, 0, 0));
		lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblFecha.setText("Fecha");

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

		txtImporte
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.0000"))));
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
		cmdDeselectAll.setText("Quitar selecci\u00f3n");
		cmdDeselectAll.setToolTipText("Quitar la seleccionar todas las filas");
		cmdDeselectAll.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdDeselectAll.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdDeselectAllMousePressed(evt);
			}
		});

		cmdDeleteLinea.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdDeleteLinea.setText("Eliminar l\u00ednea");
		cmdDeleteLinea.setToolTipText("Eliminar las l\u00edneas seleccionados");
		cmdDeleteLinea.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdDeleteLinea.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdDeleteLineaMousePressed(evt);
			}
		});

		lblSemana.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblSemana.setForeground(new java.awt.Color(255, 0, 0));
		lblSemana.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSemana.setText("Semana");

		txtSemana.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtSemana.setEditable(false);
		txtSemana
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtSemana.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtSemana.setFocusable(false);
		txtSemana.setFont(new java.awt.Font("Segoe UI", 0, 14));

		lblBarco.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblBarco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblBarco.setText("Barco");

		lblAno.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblAno.setForeground(new java.awt.Color(255, 0, 0));
		lblAno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblAno.setText("A\u00f1o");

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

		cboBarcos.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		cboBarcos.setName("Id subcategoria");
		cboBarcos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboBarcosActionPerformed(evt);
			}
		});

		cboVehiculos.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		cboVehiculos.setName("Id subcategoria");
		cboVehiculos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboVehiculosActionPerformed(evt);
			}
		});

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

		txtIdBarco.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdBarco
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtIdBarco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdBarco
				.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
		txtIdBarco.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtIdBarco.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtIdBarcoKeyReleased(evt);
			}
		});

		txtIdVehiculo.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdVehiculo
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtIdVehiculo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdVehiculo
				.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
		txtIdVehiculo.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtIdVehiculo.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtIdVehiculoKeyReleased(evt);
			}
		});

		lblPlataforma.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblPlataforma.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblPlataforma.setText("Plataforma");

		lblConductor.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblConductor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblConductor.setText("Conductor");

		txtIdConductor.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdConductor
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtIdConductor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdConductor
				.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
		txtIdConductor.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtIdConductor.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtIdConductorKeyReleased(evt);
			}
		});

		cboConductores.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		cboConductores.setName("Id subcategoria");
		cboConductores.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboConductoresActionPerformed(evt);
			}
		});

		lblPuerto.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblPuerto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblPuerto.setText("Puerto");

		txtIdPuerto.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtIdPuerto
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtIdPuerto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtIdPuerto
				.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
		txtIdPuerto.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtIdPuerto.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtIdPuertoKeyReleased(evt);
			}
		});

		cboPuertos.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		cboPuertos.setName("Id subcategoria");
		cboPuertos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboPuertosActionPerformed(evt);
			}
		});

		txtPlataforma.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

		txtNumCajas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtNumCajas.setPreferredSize(new java.awt.Dimension(0, 0));

		txtPrecio
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.0000"))));
		txtPrecio.setPreferredSize(new java.awt.Dimension(0, 0));
		txtPrecio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtPrecioActionPerformed(evt);
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

		lblNumcajas.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblNumcajas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblNumcajas.setText("Total de cajas");

		txtTotalNumCajas.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalNumCajas.setEditable(false);
		txtTotalNumCajas
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								java.text.NumberFormat.getIntegerInstance())));
		txtTotalNumCajas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalNumCajas.setFont(new java.awt.Font("Segoe UI", 1, 14));

		txtTotalNumKilos.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalNumKilos.setEditable(false);
		txtTotalNumKilos
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								java.text.NumberFormat.getIntegerInstance())));
		txtTotalNumKilos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalNumKilos.setFont(new java.awt.Font("Segoe UI", 1, 14));

		lblImporte.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblImporte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblImporte.setText("Importe total");

		txtTotalImporte.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalImporte.setEditable(false);
		txtTotalImporte
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtTotalImporte.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalImporte.setFont(new java.awt.Font("Segoe UI", 1, 14));

		txtNumKilos
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		txtNumKilos.setPreferredSize(new java.awt.Dimension(0, 0));

		lblTotalKilos.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblTotalKilos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTotalKilos.setText("Total de kilos");

		lblReceptor.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblReceptor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblReceptor.setText("Receptor");

		txtIdReceptor.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
																				jScrollPane1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				776,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGroup(
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
																												lblAno,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtAno,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGap(
																												60,
																												60,
																												60)
																										.addComponent(
																												lblIdVenta,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtIdVenta,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGap(
																												60,
																												60,
																												60)
																										.addComponent(
																												lblBarco,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtIdBarco,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGap(
																												60,
																												60,
																												60)
																										.addComponent(
																												lblVehiculo,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												120,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtIdVehiculo,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGap(
																												73,
																												73,
																												73)
																										.addComponent(
																												lblConductor,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												107,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtIdConductor,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGap(
																												73,
																												73,
																												73)
																										.addComponent(
																												lblPuerto,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												107,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtIdPuerto,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												60,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												pnlDataLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING,
																																false)
																														.addComponent(
																																cboPuertos,
																																javax.swing.GroupLayout.Alignment.LEADING,
																																0,
																																524,
																																Short.MAX_VALUE)
																														.addComponent(
																																cboConductores,
																																javax.swing.GroupLayout.Alignment.LEADING,
																																0,
																																524,
																																Short.MAX_VALUE)
																														.addComponent(
																																cboVehiculos,
																																javax.swing.GroupLayout.Alignment.LEADING,
																																0,
																																524,
																																Short.MAX_VALUE)
																														.addGroup(
																																pnlDataLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				cboBarcos,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				338,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				lblPlataforma,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				86,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				txtPlataforma,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				90,
																																				javax.swing.GroupLayout.PREFERRED_SIZE))))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addGap(
																												56,
																												56,
																												56)
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
																												27,
																												27,
																												27)
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
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addContainerGap())
														.addGroup(
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
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												18,
																												18,
																												18)
																										.addComponent(
																												txtImporte,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												0,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												48,
																												48,
																												48)
																										.addComponent(
																												txtNumCajas,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												0,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												lblNumcajas)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtTotalNumCajas,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												14,
																												14,
																												14)
																										.addComponent(
																												lblTotalKilos)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtTotalNumKilos,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												lblImporte,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												98,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtTotalImporte,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				79,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnCancel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				btnOk)
																		.addContainerGap())
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				656,
																				656,
																				656)
																		.addComponent(
																				txtPrecio,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				132,
																				132,
																				132))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				61,
																				61,
																				61)
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
																				58,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cboReceptores,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				525,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addContainerGap(
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))))
						.addGroup(
								pnlDataLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												pnlDataLayout
														.createSequentialGroup()
														.addGap(525, 525, 525)
														.addComponent(
																cboCategoriaDesc,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addContainerGap(703,
																Short.MAX_VALUE)))
						.addGroup(
								pnlDataLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												pnlDataLayout
														.createSequentialGroup()
														.addContainerGap(525,
																Short.MAX_VALUE)
														.addComponent(
																txtNumKilos,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																0,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(275, 275, 275))));
		pnlDataLayout
				.setVerticalGroup(pnlDataLayout
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
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lblIdVenta)
														.addComponent(
																txtIdVenta,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(8, 8, 8)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pnlDataLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				lblAno)
																		.addComponent(
																				txtAno,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				25,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lblFecha)
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
																						.addComponent(
																								lblSemana))))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
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
																cboReceptores,
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
																txtIdBarco,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lblBarco)
														.addComponent(
																cboBarcos,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblPlataforma)
														.addComponent(
																txtPlataforma,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																23,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txtIdVehiculo,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblVehiculo)
														.addComponent(
																cboVehiculos,
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
																txtIdConductor,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblConductor)
														.addComponent(
																cboConductores,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pnlDataLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lblPuerto)
														.addComponent(
																txtIdPuerto,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cboPuertos,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																25,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												194, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												txtPrecio,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(14, 14, 14)
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
																								lblNumcajas)
																						.addComponent(
																								txtTotalNumCajas,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								txtTotalNumKilos,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblImporte)
																						.addComponent(
																								txtTotalImporte,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblTotalKilos))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								pnlDataLayout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.BASELINE)
																										.addComponent(
																												cmdSelectAll)
																										.addComponent(
																												cmdDeselectAll)
																										.addComponent(
																												cmdDeleteLinea)
																										.addComponent(
																												txtNumCajas,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												0,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								txtImporte,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								0,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addComponent(
																btnCancel,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																btnOk,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addContainerGap())
						.addGroup(
								pnlDataLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												pnlDataLayout
														.createSequentialGroup()
														.addContainerGap(436,
																Short.MAX_VALUE)
														.addComponent(
																cboCategoriaDesc,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(36, 36, 36)))
						.addGroup(
								pnlDataLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												pnlDataLayout
														.createSequentialGroup()
														.addContainerGap(517,
																Short.MAX_VALUE)
														.addComponent(
																txtNumKilos,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																0,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(23, 23, 23))));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 804,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 544,
				Short.MAX_VALUE));
	}// </editor-fold>
	//GEN-END:initComponents

	private void txtFechaKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeSemana();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.txtFechaKeyReleased()", he);
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
					"FrmVenta.cboReceptoresActionPerformed()", he);
		}
	}

	private void txtIdReceptorKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeReceptor();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.txtIdReceptorKeyReleased()", he);
		}
	}

	private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {

	}

	private void txtIdPuertoKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changePuerto();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.txtIdPuertoKeyReleased()", he);
		}
	}

	private void txtIdConductorKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeConductor();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.txtIdConductorKeyReleased()", he);
		}
	}

	private void txtIdVehiculoKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeVehiculo();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.txtIdVehiculoKeyReleased()", he);
		}
	}

	private void txtIdBarcoKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeBarco();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.txtIdBarcoKeyReleased()", he);
		}
	}

	private void cboPuertosActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingPuerto) {
				changingPuerto = true;
				if (cboPuertos.getSelectedIndex() != -1) {
					List<?> puertoslist = entity.PuertoFindByNombrePuerto(this,
							(String) cboPuertos.getSelectedItem());
					if (puertoslist.size() > 0) {
						Puertos puertosItem = (Puertos) puertoslist.get(0);
						txtIdPuerto.setValue(puertosItem.getId().getIdPuerto());
					}
				}
				changingPuerto = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.cboPuertosActionPerformed()", he);
		}
	}

	private void cboConductoresActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingConductor) {
				changingConductor = true;
				if (cboConductores.getSelectedIndex() != -1) {
					List<?> conductoreslist = entity.executeQuery(this, "*",
							"Conductores",
							"concat(concat(Nombre,' '), coalesce(Apellidos,'')) = '"
									+ (String) cboConductores.getSelectedItem()
									+ "'", "");
					if (conductoreslist.size() > 0) {
						Conductores conductoresItem = (Conductores) conductoreslist
								.get(0);
						txtIdConductor.setValue(conductoresItem.getId()
								.getIdConductor());
					}
				}
				changingConductor = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.cboConductoresActionPerformed()", he);
		}
	}

	private void cboBarcosActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingBarco) {
				changingBarco = true;
				if (cboBarcos.getSelectedIndex() != -1) {
					List<?> barcoslist = entity.BarcoFindByNombreBarco(this,
							(String) cboBarcos.getSelectedItem());
					if (barcoslist.size() > 0) {
						Barcos barcosItem = (Barcos) barcoslist.get(0);
						txtIdBarco.setValue(barcosItem.getId().getIdBarco());
					}
				}
				changingBarco = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.cboBarcosActionPerformed()", he);
		}
	}

	private void cboVehiculosActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingVehiculo) {
				changingVehiculo = true;
				if (cboVehiculos.getSelectedIndex() != -1) {
					List<?> vehiculoslist = entity.executeQuery(this, "*",
							"Vehiculos",
							"concat(concat(Marca,' '), coalesce(Matricula,'')) = '"
									+ (String) cboVehiculos.getSelectedItem()
									+ "'", "");
					if (vehiculoslist.size() > 0) {
						Vehiculos vehiculosItem = (Vehiculos) vehiculoslist
								.get(0);
						txtIdVehiculo.setValue(vehiculosItem.getId()
								.getIdVehiculo());
					}
				}
				changingVehiculo = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.cboVehiculosActionPerformed()", he);
		}
	}

	private void cmdDeleteLineaMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++) {
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
					"FrmVenta.cmdDeleteLineaMousePressed()", he);
		}
	}

	private void cmdSelectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++)
				tblDetalle.setValueAt(true, actualrow,
						DetalleTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.cmdSelectAllMousePressed()", he);
		}
	}

	private void cmdDeselectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++)
				tblDetalle.setValueAt(false, actualrow,
						DetalleTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.cmdDeselectAllMousePressed()", he);
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
					"FrmVenta.tblDetalleKeyPressed()", he);
		}
	}

	private void cboCategoriaDescActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingCategoria) {
				changingCategoria = true;
				if (cboCategoriaDesc.getSelectedIndex() != -1) {
					if (tblDetalle.getRowCount() > 0
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
							calculaNumKilos();
						}
					}
				}
				changingCategoria = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.cboCategoriaDescActionPerformed()", he);
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
					"FrmVenta.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.venta);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmVenta.btnCancelMousePressed()", he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private static javax.swing.JComboBox cboBarcos;
	private static javax.swing.JComboBox cboCategoriaDesc;
	private static javax.swing.JComboBox cboConductores;
	private static javax.swing.JComboBox cboPuertos;
	private static javax.swing.JComboBox cboReceptores;
	private static javax.swing.JComboBox cboVehiculos;
	private javax.swing.JButton cmdDeleteLinea;
	private javax.swing.JButton cmdDeselectAll;
	private javax.swing.JButton cmdSelectAll;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblAno;
	private javax.swing.JLabel lblBarco;
	private javax.swing.JLabel lblConductor;
	private javax.swing.JLabel lblFecha;
	private javax.swing.JLabel lblIdVenta;
	private javax.swing.JLabel lblImporte;
	private javax.swing.JLabel lblNumcajas;
	private javax.swing.JLabel lblPlataforma;
	private javax.swing.JLabel lblPuerto;
	private javax.swing.JLabel lblReceptor;
	private javax.swing.JLabel lblSemana;
	private javax.swing.JLabel lblTotalKilos;
	private javax.swing.JLabel lblVehiculo;
	public static javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblDetalle;
	private javax.swing.JFormattedTextField txtAno;
	private javax.swing.JFormattedTextField txtFecha;
	private static javax.swing.JFormattedTextField txtIdBarco;
	private static javax.swing.JFormattedTextField txtIdConductor;
	private static javax.swing.JFormattedTextField txtIdPuerto;
	private static javax.swing.JFormattedTextField txtIdReceptor;
	private static javax.swing.JFormattedTextField txtIdVehiculo;
	private javax.swing.JFormattedTextField txtIdVenta;
	private static javax.swing.JFormattedTextField txtImporte;
	private static javax.swing.JFormattedTextField txtNumCajas;
	private static javax.swing.JFormattedTextField txtNumKilos;
	private javax.swing.JTextField txtPlataforma;
	private static javax.swing.JFormattedTextField txtPrecio;
	private javax.swing.JFormattedTextField txtSemana;
	private javax.swing.JFormattedTextField txtTotalImporte;
	private javax.swing.JFormattedTextField txtTotalNumCajas;
	private javax.swing.JFormattedTextField txtTotalNumKilos;
	// End of variables declaration//GEN-END:variables

}