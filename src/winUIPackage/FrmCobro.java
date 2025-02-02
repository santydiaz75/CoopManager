/*
 * FrmParamFacturas.java
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

import entitiesPackage.Cobros;
import entitiesPackage.CobrosId;
import entitiesPackage.Empresascuentas;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;
import sessionPackage.MySession;

/**
 *
 * @author  __USER__
 */
public class FrmCobro extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;

	public static class DetalleTableModel extends DefaultTableModel {

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
		final public static int columnFecha = 2;
		final public static int columnConcepto = 3;
		final public static int columnCuentaBancaria = 4;
		final public static int columnImporte = 5;

		static String[] columnNames = { "Select", "State", "Fecha", "Concepto",
				"CuentaBancaria", "Importe" };

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
			columns.add(new ColumnData("Fecha", "Fecha", EntityType.DateWidth,
					SwingConstants.RIGHT, DateType, txtFechaCobro, null));
			columns.add(new ColumnData("Concepto", "Concepto",
					EntityType.MediumTextWidth, SwingConstants.LEFT,
					NormalType, null, null));
			columns.add(new ColumnData("CuentaBancaria", "Cuenta bancaria",
					EntityType.ComboWidth, SwingConstants.LEFT, ComboType,
					cboCuentaBancaria, null));
			columns.add(new ColumnData("Importe", "Importe",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImporte, "#,##0.00"));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					if (!changingState && !addLine)
						changeEditState();
				}
			});
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			switch (columna) {
			case columnFecha: {
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
			case columnConcepto: {
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
			case columnCuentaBancaria: {
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

	static boolean changingState = false;
	static boolean addLine = false;

	private MySession session;
	private Integer idCliente;
	private static Entity entity = new Entity();

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	public void setIdCliente(Integer idFactura) {
		this.idCliente = idFactura;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	/** Creates new form FrmBanco */
	public FrmCobro() {
		initComponents();
	}

	public FrmCobro(MySession session, Integer idCliente) {
		try {
			initComponents();
			this.setSession(session);
			entity.setSession(session);
			this.idCliente = idCliente;
			configureKeys();
			loadData();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData, "FrmCobro()", he);
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
			Message.ShowRuntimeError(pnlData, "FrmCobro.configureKeys", he);
		}
	}

	private void prepareTable() {
		try {
			loadCuentasBancarias();
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
				case DetalleTableModel.DateType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelDetalle.columns.get(k).m_alignment);
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
								stateIcon.setIcon(DetalleTableModel.Adding);
							else if (value.equals(DetalleTableModel.EditLine))
								stateIcon.setIcon(DetalleTableModel.Editing);

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
			Message.ShowRuntimeError(pnlData, "FrmCobro.prepareTable()", he);
		}
	}

	private void newLineDetalle() {
		try {
			if (tblDetalle.getRowCount() > 0) {
				if (!(tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
						DetalleTableModel.columnFecha).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblDetalle.getModel())
							.addRow(new Object[] { false,
									DetalleTableModel.NewLine, "", "", "", "" });
					tblDetalle.changeSelection(tblDetalle.getSelectedRow() + 1,
							DetalleTableModel.columnFecha, false, false);
					tblDetalle.editCellAt(tblDetalle.getSelectedRow() + 1,
							DetalleTableModel.columnFecha);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblDetalle.getModel())
						.addRow(new Object[] { false,
								DetalleTableModel.NewLine, "", "", "", "" });
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData, "FrmCobro.newLineDetalle()", he);
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
			Message.ShowRuntimeError(pnlData, "FrmCobro.changeEditState()", he);
		}
	}

	private void loadCuentasBancarias() {

		try {
			cboCuentaBancaria.removeAllItems();

			List<?> cuentasbancarias = entity
					.EmpresaGetCuentasBancarias(pnlData);

			for (Object o : cuentasbancarias) {
				Empresascuentas empresacuenta = (Empresascuentas) o;
				cboCuentaBancaria.addItem(empresacuenta.getId()
						.getCuentaBancaria());
			}

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmCobro.loadCuentasBancarias()", he);
		}
	}

	public void loadData() {
		try {
			prepareTable();
			loadDataDetail();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData, "FrmCobro.loadData()", he);
		}
	}

	private void loadDataDetail() {

		try {
			Integer numRows = tblDetalle.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblDetalle.getModel()).removeRow(0);

			List<?> lineasList = entity.executeQuery(this, "*", "Cobros",
					"id.idCliente=" + idCliente, "id.cobro");

			if (lineasList.size() == 0)
				newLineDetalle();
			else {
				for (Object linea : lineasList) {
					Cobros cobro = (Cobros) linea;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(DetalleTableModel.EditLine);
					if (cobro.getFechaCobro() != null) {
						SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
								PreferencesUI.DateFormat);
						String cadenaFecha = formatoDeFecha.format(cobro
								.getFechaCobro());
						oneRow.add(cadenaFecha);
					} else
						oneRow.add("");
					if (cobro.getConcepto() != null)
						oneRow.add(cobro.getConcepto());
					else
						oneRow.add("");
					if (cobro.getCuentaBancaria() != null)
						oneRow.add(cobro.getCuentaBancaria());
					else
						oneRow.add("");
					if (cobro.getImporte() != null)
						oneRow.add(cobro.getImporte());
					else
						oneRow.add("");
					((DefaultTableModel) tblDetalle.getModel()).addRow(oneRow);
				}
			}
			calculaTotales();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData, "FrmCobro.loadDataDetail()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {
		try {
			if (validateDetalleData()) {

				Transaction transaction = session.getSession()
						.beginTransaction();

				String deletelinesquery = "Delete From Cobros "
						+ "Where id.idCliente = " + idCliente
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
						Cobros cobro = new Cobros();
						CobrosId cobroId = new CobrosId();
						cobroId.setEjercicios(session.getEjercicio());
						cobroId.setEmpresas(session.getEmpresa());
						cobroId.setIdCliente(idCliente);
						cobroId.setCobro(k + 1);
						cobro.setId(cobroId);
						if (!tblDetalle.getValueAt(k,
								DetalleTableModel.columnFecha).equals("")) {
							SimpleDateFormat formatoDeFecha = new SimpleDateFormat(
									PreferencesUI.DateFormat);
							Date value = formatoDeFecha.parse(tblDetalle
									.getValueAt(k,
											DetalleTableModel.columnFecha)
									.toString());
							cobro.setFechaCobro(value);
						} else
							cobro.setFechaCobro(null);
						if (!tblDetalle.getValueAt(k,
								DetalleTableModel.columnConcepto).equals(""))
							cobro.setConcepto((String) tblDetalle.getValueAt(k,
									DetalleTableModel.columnConcepto));
						else
							cobro.setConcepto(null);
						if (!tblDetalle.getValueAt(k,
								DetalleTableModel.columnCuentaBancaria).equals(
								""))
							cobro
									.setCuentaBancaria((String) tblDetalle
											.getValueAt(
													k,
													DetalleTableModel.columnCuentaBancaria));
						else
							cobro.setCuentaBancaria(null);
						if (!tblDetalle.getValueAt(k,
								DetalleTableModel.columnImporte).equals(""))
							cobro.setImporte((Float) tblDetalle.getValueAt(k,
									DetalleTableModel.columnImporte));
						else
							cobro.setImporte(((Number) 0).floatValue());
						cobro.setLmd(new Date());
						cobro.setSid("Santi");
						cobro.setVersion(1);
						session.getSession().replicate(cobro,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(cobro);
						session.getSession().flush();
					}
				}
				if (!transaction.wasCommitted()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				FrmFacturas.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData, "FrmCobro.saveData()", he);
			return false;
		} catch (ParseException e) {
			Message.ShowParseError(pnlData, "FrmCobro.saveData()", e);
			return false;
		}
	}

	private boolean validateDetalleData() {
		try {
			for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++) {
				if (tblDetalle.getValueAt(actualrow,
						DetalleTableModel.columnState).equals(
						DetalleTableModel.EditLine)) {
					if (tblDetalle.getValueAt(actualrow,
							DetalleTableModel.columnFecha).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar la fecha.");
						tblDetalle.changeSelection(actualrow,
								DetalleTableModel.columnFecha, false, false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnFecha);
						tblDetalle.requestFocusInWindow();
						return (false);
					}
					if (tblDetalle.getValueAt(actualrow,
							DetalleTableModel.columnImporte).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar el importe.");
						tblDetalle.changeSelection(actualrow,
								DetalleTableModel.columnImporte, false, false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnImporte);
						tblDetalle.requestFocusInWindow();
						return (false);
					}
				}
			}
			calculaTotales();

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData, "FrmCobro.validateDetalleData()",
					he);
			return (false);
		}
	}

	private void calculaTotales() {
		try {
			Float totalCobro = ((Number) 0).floatValue();

			Integer numRows = tblDetalle.getRowCount();
			for (Integer actualRow = 0; actualRow < numRows; actualRow++) {
				if (tblDetalle.getValueAt(actualRow,
						DetalleTableModel.columnState).equals(
						DetalleTableModel.EditLine)) {
					float importe = ((Number) (Util.round(
							((Number) (((DefaultTableModel) tblDetalle
									.getModel()).getValueAt(actualRow,
									DetalleTableModel.columnImporte)))
									.floatValue(), 2))).floatValue();
					totalCobro = totalCobro + importe;
				}
			}
			txtTotalCobro.setValue(totalCobro);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData, "FrmCobro.calculaTotales()", he);
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
		lblTotalCobro = new javax.swing.JLabel();
		txtTotalCobro = new javax.swing.JFormattedTextField();
		txtImporte = new javax.swing.JFormattedTextField();
		cboCuentaBancaria = new javax.swing.JComboBox();
		btnClose = new javax.swing.JButton();
		txtFechaCobro = new javax.swing.JFormattedTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Cobros de la factura");
		setResizable(false);

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

		lblTotalCobro.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblTotalCobro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTotalCobro.setText("Total cobro");

		txtTotalCobro.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtTotalCobro.setEditable(false);
		txtTotalCobro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtTotalCobro.setFont(new java.awt.Font("Segoe UI", 1, 14));

		txtImporte
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0.00"))));
		txtImporte.setPreferredSize(new java.awt.Dimension(0, 0));

		cboCuentaBancaria.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		cboCuentaBancaria.setName("Id subcategoria");

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

		txtFechaCobro.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		try {
			txtFechaCobro
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		txtFechaCobro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		txtFechaCobro.setFont(new java.awt.Font("Segoe UI", 0, 14));

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
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																729,
																Short.MAX_VALUE)
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
																												lblTotalCobro,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												124,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txtTotalCobro,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												90,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								pnlDataLayout
																										.createSequentialGroup()
																										.addComponent(
																												cmdSelectAll,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												135,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												cmdDeselectAll,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												135,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												cmdDeleteLinea,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												135,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												41,
																												41,
																												41)
																										.addComponent(
																												txtImporte,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												0,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(
																												18,
																												18,
																												18)
																										.addComponent(
																												cboCuentaBancaria,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												0,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				78,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnClose)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				btnCancel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				btnOk)))
										.addContainerGap())
						.addGroup(
								pnlDataLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												pnlDataLayout
														.createSequentialGroup()
														.addGap(376, 376, 376)
														.addComponent(
																txtFechaCobro,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																0,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addContainerGap(377,
																Short.MAX_VALUE))));
		pnlDataLayout
				.setVerticalGroup(pnlDataLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlDataLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												101,
												javax.swing.GroupLayout.PREFERRED_SIZE)
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
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								txtTotalCobro,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblTotalCobro))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
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
																								txtImporte,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								0,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								cboCuentaBancaria,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								0,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGap(
																				248,
																				248,
																				248))
														.addGroup(
																pnlDataLayout
																		.createSequentialGroup()
																		.addGap(
																				23,
																				23,
																				23)
																		.addGroup(
																				pnlDataLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								btnCancel)
																						.addComponent(
																								btnOk)
																						.addComponent(
																								btnClose))
																		.addContainerGap())))
						.addGroup(
								pnlDataLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												pnlDataLayout
														.createSequentialGroup()
														.addGap(213, 213, 213)
														.addComponent(
																txtFechaCobro,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																0,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addContainerGap(213,
																Short.MAX_VALUE))));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.PREFERRED_SIZE, 757,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				pnlData, javax.swing.GroupLayout.PREFERRED_SIZE, 220,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btnCloseMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			this.dispose();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmCobros.btnCancelMousePressed()", he);
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
			Message.ShowRuntimeError(pnlData,
					"FrmCobro.cmdDeleteLineaMousePressed()", he);
		}
	}

	private void cmdDeselectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++)
				tblDetalle.setValueAt(false, actualrow,
						DetalleTableModel.columnSelect);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmCobro.cmdDeselectAllMousePressed()", he);
		}
	}

	private void cmdSelectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++)
				tblDetalle.setValueAt(true, actualrow,
						DetalleTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmCobro.cmdSelectAllMousePressed()", he);
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
			Message.ShowRuntimeError(pnlData,
					"FrmCobro.tblDetalleKeyPressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			loadData();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(pnlData,
					"FrmCobro.btnCancelMousePressed()", he);
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
			Message.ShowRuntimeError(pnlData, "FrmCobro.btnOkMousePressed()",
					he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnClose;
	private javax.swing.JButton btnOk;
	private static javax.swing.JComboBox cboCuentaBancaria;
	private javax.swing.JButton cmdDeleteLinea;
	private javax.swing.JButton cmdDeselectAll;
	private javax.swing.JButton cmdSelectAll;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblTotalCobro;
	public static javax.swing.JPanel pnlData;
	private static javax.swing.JTable tblDetalle;
	private static javax.swing.JFormattedTextField txtFechaCobro;
	private static javax.swing.JFormattedTextField txtImporte;
	private javax.swing.JFormattedTextField txtTotalCobro;
	// End of variables declaration//GEN-END:variables

}