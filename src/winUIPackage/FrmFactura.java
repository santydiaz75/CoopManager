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

import reportsPackage.FacturaServicios;
import sessionPackage.MySession;

import java.util.Vector;

import entitiesPackage.Conceptos;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Facturascabecera;
import entitiesPackage.FacturascabeceraId;
import entitiesPackage.Facturaslineas;
import entitiesPackage.FacturaslineasId;
import entitiesPackage.Identidades;
import entitiesPackage.Message;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmFactura extends javax.swing.JPanel {

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

		final public static String NewLine = "[Adding]";
		final public static String EditLine = "[Editing]";
		final public static String DeleteLine = "[Deleting]";

		final public static int columnSelect = 0;
		final public static int columnState = 1;
		final public static int columnUnidades = 2;
		final public static int columnIdConcepto = 3;
		final public static int columnConcepto = 4;
		final public static int columnPrecio = 5;
		final public static int columnPctImpuesto = 6;
		final public static int columnImporte = 7;

		static String[] columnNames = { "Select", "State", "Unidades",
				"IdConcepto", "Concepto", "Precio", "Impuesto", "Importe" };

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
			columns.add(new ColumnData("Unidades", "Unidades",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtUnidades, "#,##0.00"));
			columns
					.add(new ColumnData("IdConcepto", "Id",
							EntityType.NumberWidth, SwingConstants.RIGHT, 0,
							null, null));
			columns.add(new ColumnData("Concepto", "Concepto",
					EntityType.ComboWidth, SwingConstants.LEFT, ComboType,
					cboConceptos, null));
			columns.add(new ColumnData("Precio", "Precio",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float4Type,
					txtPrecio, "#,##0.0000"));
			columns.add(new ColumnData("Impuesto", "Impuesto",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtPctImpuesto, "#,##0.00"));
			columns.add(new ColumnData("Importe", "Importe",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float2Type,
					txtImporte, "#,##0.00"));

			this.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent e) {
					try {
						if (!changingState && !addLine)
							changeEditState();
						if (e.getColumn() == columnPrecio) {
							calculaImporte();
						}
						if (e.getColumn() == columnUnidades) {
							calculaImporte();
						}
						if (e.getColumn() == columnPctImpuesto) {
							calculaImporte();
						}
						if (e.getColumn() == columnIdConcepto) {
							changeConcepto();
						}
					} catch (NumberFormatException nfe) {
						
					}
				}
			});
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			switch (columna) {
				case columnIdConcepto: {
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
				case columnUnidades: {
					if (super.getValueAt(fila, columna) != null) {
						if (!super.getValueAt(fila, columna).equals(""))
							try {
								String value = super.getValueAt(fila, columna)
										.toString().replace(",", ".");
								return Float.parseFloat(value);
							} catch (NumberFormatException nfe) {
								return "0";
							}
						else
							return "0";
					} else
						return "0";
				}
				case columnPrecio: {
					if (super.getValueAt(fila, columna) != null) {
						if (!super.getValueAt(fila, columna).equals(""))
							try {
								String value = super.getValueAt(fila, columna)
										.toString().replace(",", ".");
								return Float.parseFloat(value);
							} catch (NumberFormatException nfe) {
								return "0.00";
							}
						else
							return "0.00";
					} else
						return "0.00";
				}
				case columnImporte: {
					if (super.getValueAt(fila, columna) != null) {
						if (!super.getValueAt(fila, columna).equals(""))
							try {
								String value = super.getValueAt(fila, columna)
										.toString().replace(",", ".");
								return Float.parseFloat(value);
							} catch (NumberFormatException nfe) {
								return "0.00";
							}
						else
							return "0.00";
					} else
						return "0.00";
				}
				case columnPctImpuesto: {
					if (super.getValueAt(fila, columna) != null) {
						if (!super.getValueAt(fila, columna).equals(""))
							try {
								String value = super.getValueAt(fila, columna)
										.toString().replace(",", ".");
								return Float.parseFloat(value);
							} catch (NumberFormatException nfe) {
								return "0.00";
							}
						else
							return "0.00";
					} else
						return "0.00";
				}
			}
			return super.getValueAt(fila, columna);
		}

	}

	Facturascabecera factura;

	static boolean changingState = false;
	static boolean addLine = false;
	static boolean changingConcepto = false;
	private boolean OnNew = false;
	private boolean changingIdentidad = false;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private static Entity entity = new Entity();

	public java.awt.Frame getParentFrame() {
		return FrmFactura.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmFactura.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmFactura() {
		initComponents();
	}

	public FrmFactura(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmFactura.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmFactura()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmFactura.configureKeys",
					he);
		}
	}

	private void prepareTable() {
		try {
			DetalleTableModel modelDetalle = new DetalleTableModel();
			tblDetalle.setModel(modelDetalle);
			tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			List<?> conceptosList = entity.ConceptoFindAll(this);
			for (Object o : conceptosList) {
				Conceptos concepto = (Conceptos) o;
				cboConceptos.addItem(concepto.getConceptoDesc());
			}

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
			Message.ShowRuntimeError(parentFrame, "FrmFactura.prepareTable()",
					he);
		}
	}

	public void newData() {
		try {
			this.factura = new Facturascabecera();
			prepareTable();
			loadIdentidades();
			txtIdFactura.setText("");
			txtAno.setValue(session.getEjercicio().getEjercicio());
			txtSemana.setText("");
			txtFecha.setText("");
			txtIdentidad.setText("");
			cboIdentidades.setSelectedItem(null);
			txtNIF.setText("");
			txtCuenta.setText("");
			txtNombre.setText("");
			txtDireccion.setText("");
			txtPoblacion.setText("");
			txtProvincia.setText("");
			txtCodigoPostal.setText("");
			txtBaseImponible.setValue(0);
			txtTipoImpuesto.setValue(0);
			txtPctImpuesto.setValue(0);
			txtImporteImpuesto.setValue(0);
			txtTipoIRPF.setValue(0);
			txtImporteIRPF.setValue(0);
			txtTotalFactura.setValue(0);
			Integer numRows = tblDetalle.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblDetalle.getModel()).removeRow(0);
			newLineDetalle();
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmFactura.newData()", he);
		}
	}

	private void newLineDetalle() {
		try {
			if (tblDetalle.getRowCount() > 0) {
				if (!(tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
						DetalleTableModel.columnUnidades).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblDetalle.getModel())
							.addRow(new Object[] { false,
									DetalleTableModel.NewLine, "1", "", "", "0.00",
									"0.00", "0.00" });
					tblDetalle.changeSelection(tblDetalle.getSelectedRow() + 1,
							DetalleTableModel.columnUnidades, false, false);
					tblDetalle.editCellAt(tblDetalle.getSelectedRow() + 1,
							DetalleTableModel.columnUnidades);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblDetalle.getModel())
						.addRow(new Object[] { false,
								DetalleTableModel.NewLine, "1", "", "", "0.00",
								"0.00", "0.00" });
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.newLineDetalle()", he);
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
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.changeEditState()", he);
		}
	}

	private void loadIdentidades() {
		try {
			cboIdentidades.removeAllItems();
			List<?> receptoresList = entity.IdentidadFindClientes(this);

			for (Object o : receptoresList) {
				Identidades identidad = (Identidades) o;
				cboIdentidades.addItem(identidad.getNombreIdentidad());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.loadIdentidades()", he);
		}
	}

	public void loadData(Facturascabecera entity) {
		try {
			this.factura = entity;
			prepareTable();
			loadIdentidades();
			txtIdFactura.setValue(entity.getId().getIdFactura());
			txtAno.setValue(entity.getId().getEjercicios().getEjercicio());
			txtSemana.setValue(entity.getSemana());
			SimpleDateFormat df = new SimpleDateFormat(PreferencesUI.DateFormat);
			String strFecha = df.format(entity.getFecha());
			txtFecha.setValue(strFecha);
			txtIdentidad.setValue(entity.getIdentidad());
			txtNIF.setText(entity.getNif());
			txtCuenta.setText(entity.getCuentaCliente());
			txtNombre.setText(entity.getNombre());
			txtDireccion.setText(entity.getDireccion());
			txtPoblacion.setText(entity.getPoblacion());
			txtProvincia.setText(entity.getProvincia());
			txtCodigoPostal.setText(entity.getCodigoPostal());
			txtBaseImponible.setValue(entity.getBaseImponible());
			txtTipoImpuesto.setValue(entity.getTipoImpuesto());
			txtImporteImpuesto.setValue(entity.getImporteImpuesto());
			txtTipoIRPF.setValue(entity.getTipoIrpf());
			txtImporteIRPF.setValue(entity.getImporteIrpf());
			txtTotalFactura.setValue(entity.getImporteFactura());
			changeIdentidad();
			loadDataDetail();
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmFactura.loadData()", he);
		}
	}

	private void loadDataDetail() {

		try {
			Integer numRows = tblDetalle.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblDetalle.getModel()).removeRow(0);

			List<?> lineasList = entity.executeQuery(this, "*",
					"Facturaslineas", "id.idFactura="
							+ factura.getId().getIdFactura(), "id.linea");

			if (lineasList.size() == 0)
				newLineDetalle();
			else {
				for (Object linea : lineasList) {
					Facturaslineas facturaslinea = (Facturaslineas) linea;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(DetalleTableModel.EditLine);
					oneRow.add(facturaslinea.getUnidades());
					if (facturaslinea.getIdConcepto() != null)
						oneRow.add(facturaslinea.getIdConcepto());
					else
						oneRow.add("");
					oneRow.add(facturaslinea.getConcepto());
					oneRow.add(facturaslinea.getPrecio());
					oneRow.add(facturaslinea.getPctImpuesto());
					oneRow.add(facturaslinea.getImporte());
					((DefaultTableModel) tblDetalle.getModel()).addRow(oneRow);
				}
			}
			calculaTotales();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.loadDataDetail()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {
		try {
			if (validateData()) {

				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					txtIdFactura.setValue(entity.newId(this,
							"Facturascabecera", "id.idFactura"));
					FacturascabeceraId facturaid = new FacturascabeceraId();
					facturaid.setIdFactura((Integer) txtIdFactura.getValue());
					facturaid.setEjercicios(session.getEjercicio());
					facturaid.setEmpresas(session.getEmpresa());
					factura.setId(facturaid);
				}
				if (!txtSemana.getText().equals(""))
					factura.setSemana(((Number) txtSemana.getValue())
							.intValue());
				else
					factura.setSemana(null);
				if (!txtFecha.getText().equals(PreferencesUI.DateMask)) {
					SimpleDateFormat df = new SimpleDateFormat(
							PreferencesUI.DateFormat);
					Date value = df.parse(txtFecha.getText());
					factura.setFecha(value);
				} else
					factura.setFecha(null);
				if (!txtIdentidad.getText().equals(""))
					factura.setIdentidad((Integer) txtIdentidad.getValue());
				else
					factura.setIdentidad(null);
				if (!txtNIF.getText().equals(""))
					factura.setNif((String) txtNIF.getText());
				else
					factura.setNif(null);
				if (!txtCuenta.getText().equals(""))
					factura.setCuentaCliente((String) txtCuenta.getText());
				else
					factura.setCuentaCliente(null);
				if (!txtNombre.getText().equals(""))
					factura.setNombre((String) txtNombre.getText());
				else
					factura.setNombre(null);
				if (!txtDireccion.getText().equals(""))
					factura.setDireccion((String) txtDireccion.getText());
				else
					factura.setDireccion(null);
				if (!txtPoblacion.getText().equals(""))
					factura.setPoblacion((String) txtPoblacion.getText());
				else
					factura.setPoblacion(null);
				if (!txtProvincia.getText().equals(""))
					factura.setProvincia((String) txtProvincia.getText());
				else
					factura.setProvincia(null);
				if (!txtCodigoPostal.getText().equals(""))
					factura.setCodigoPostal((String) txtCodigoPostal.getText());
				else
					factura.setCodigoPostal(null);
				if (!txtBaseImponible.getText().equals(""))
					factura.setBaseImponible(((Number) txtBaseImponible
							.getValue()).floatValue());
				else
					factura.setBaseImponible(((Number) 0).floatValue());
				if (!txtTipoImpuesto.getText().equals(""))
					factura.setTipoImpuesto(((Number) txtTipoImpuesto
							.getValue()).floatValue());
				else
					factura.setTipoImpuesto(((Number) 0).floatValue());
				if (!txtImporteImpuesto.getText().equals(""))
					factura.setImporteImpuesto(((Number) txtImporteImpuesto
							.getValue()).floatValue());
				else
					factura.setImporteImpuesto(((Number) 0).floatValue());
				if (!txtTipoIRPF.getText().equals(""))
					factura.setTipoIrpf(((Number) txtTipoIRPF.getValue())
							.floatValue());
				else
					factura.setTipoIrpf(((Number) 0).floatValue());
				if (!txtImporteIRPF.getText().equals(""))
					factura.setImporteIrpf(((Number) txtImporteIRPF.getValue())
							.floatValue());
				else
					factura.setImporteIrpf(((Number) 0).floatValue());
				if (!txtTotalFactura.getText().equals(""))
					factura.setImporteFactura(((Number) txtTotalFactura
							.getValue()).floatValue());
				else
					factura.setImporteFactura(((Number) 0).floatValue());
				factura.setLmd(new Date());
				factura.setSid("Santi");
				factura.setVersion(1);
				session.getSession().replicate(factura,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(factura);
				session.getSession().flush();

				String deletelinesquery = "Delete From Facturaslineas "
						+ "Where id.idFactura = "
						+ ((Number) txtIdFactura.getValue()).intValue()
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
						Facturaslineas facturalinea = new Facturaslineas();
						FacturaslineasId facturalineaId = new FacturaslineasId();
						facturalineaId.setEjercicios(session.getEjercicio());
						facturalineaId.setEmpresas(session.getEmpresa());
						facturalineaId.setIdFactura((Integer) txtIdFactura
								.getValue());
						facturalineaId.setLinea(k + 1);
						facturalinea.setId(facturalineaId);
						facturalinea
								.setUnidades(((Number) tblDetalle.getValueAt(k,
										DetalleTableModel.columnUnidades))
										.floatValue());
						if (tblDetalle.getValueAt(k,
								DetalleTableModel.columnIdConcepto).equals(""))
							facturalinea.setIdConcepto(null);
						else
							facturalinea
									.setIdConcepto(((Number) tblDetalle
											.getValueAt(
													k,
													DetalleTableModel.columnIdConcepto))
											.intValue());
						facturalinea
								.setConcepto((String) tblDetalle.getValueAt(k,
										DetalleTableModel.columnConcepto));
						facturalinea.setPrecio(((Number) tblDetalle.getValueAt(
								k, DetalleTableModel.columnPrecio))
								.floatValue());
						facturalinea
								.setPctImpuesto(((Number) tblDetalle.getValueAt(k,
										DetalleTableModel.columnPctImpuesto))
										.floatValue());
						facturalinea
								.setImporte(((Number) tblDetalle.getValueAt(k,
										DetalleTableModel.columnImporte))
										.floatValue());
						facturalinea.setLmd(new Date());
						facturalinea.setSid("Santi");
						facturalinea.setVersion(1);
						session.getSession().replicate(facturalinea,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(facturalinea);
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
				FrmFacturas.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmFactura.saveData()", he);
			return false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private boolean validateData() {
		try {
			if (txtIdentidad.getText().equals("")) {
				Message
						.ShowValidateMessage(pnlData,
								"Debe indicar un cliente.");
				txtIdentidad.requestFocus();
				return (false);
			} else {
				try {
					txtIdentidad.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdentidad.requestFocus();
					return (false);
				}
			}
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
			if (!txtTipoImpuesto.getText().equals("")) {
				try {
					txtTipoImpuesto.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtTipoImpuesto.requestFocus();
					return (false);
				}
			}
			if (!validateDetalleData())
				return (false);

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmFactura.validateData()",
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
							DetalleTableModel.columnUnidades).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar las unidades.");
						tblDetalle.changeSelection(actualrow,
								DetalleTableModel.columnUnidades, false, false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnUnidades);
						tblDetalle.requestFocusInWindow();
						return (false);
					}
					if (tblDetalle.getValueAt(actualrow,
							DetalleTableModel.columnConcepto).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar un concepto.");
						tblDetalle.changeSelection(actualrow,
								DetalleTableModel.columnConcepto, false, false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnConcepto);
						tblDetalle.requestFocusInWindow();
						return (false);
					}
					if (tblDetalle.getValueAt(actualrow,
							DetalleTableModel.columnPrecio).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar el precio.");
						tblDetalle.changeSelection(actualrow,
								DetalleTableModel.columnPrecio, false, false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnPrecio);
						tblDetalle.requestFocusInWindow();
						return (false);
					}
					if (tblDetalle.getValueAt(actualrow,
							DetalleTableModel.columnPctImpuesto).equals("")) {
						Message.ShowValidateMessage(tblDetalle,
								"Debe indicar el impuesto.");
						tblDetalle.changeSelection(actualrow,
								DetalleTableModel.columnPctImpuesto, false, false);
						tblDetalle.editCellAt(actualrow,
								DetalleTableModel.columnPctImpuesto);
						tblDetalle.requestFocusInWindow();
						return (false);
					}
				}
			}
			calculaTotales();

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.validateDetalleData()", he);
			return (false);
		}
	}

	private static void changeConcepto() {
		try {
			if (!changingConcepto) {
				if (!(tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
						tblDetalle.getSelectedColumn()).equals(""))) {
					Integer value = (Integer) tblDetalle.getValueAt(tblDetalle
							.getSelectedRow(), tblDetalle.getSelectedColumn());
					changingConcepto = true;
					if (value.equals(""))
						tblDetalle.setValueAt(null,
								tblDetalle.getSelectedRow(),
								DetalleTableModel.columnConcepto);
					else {
						if (((DefaultTableModel) tblDetalle.getModel())
								.getRowCount() > 0) {
							Conceptos concepto = entity
									.ConceptoFindByIdConcepto(parentFrame,
											value);
							if (concepto != null)
								tblDetalle.setValueAt(concepto
										.getConceptoDesc(), tblDetalle
										.getSelectedRow(),
										DetalleTableModel.columnConcepto);
							else {
								Message.ShowValidateMessage(tblDetalle,
										"El concepto indicado no existe.");
								tblDetalle.setValueAt(null, tblDetalle
										.getSelectedRow(),
										DetalleTableModel.columnConcepto);
								tblDetalle.requestFocusInWindow();
							}
						}
					}
				}
				changingConcepto = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.changeConcepto()", he);
		}
	}

	private void changeIdentidad() {
		try {
			if (!changingIdentidad) {
				changingIdentidad = true;
				if (txtIdentidad.getText().equals("")) {
					cboIdentidades.setSelectedItem(null);
					txtNIF.setText("");
					txtCuenta.setText("");
					txtNombre.setText("");
					txtDireccion.setText("");
					txtPoblacion.setText("");
					txtCodigoPostal.setText("");
					txtProvincia.setText("");
				} else {
					try {
						Integer value = Integer
								.parseInt(txtIdentidad.getText());
						Identidades identidad = entity
								.IdentidadFindByIdentidad(this, value);
						if (identidad != null) {
							cboIdentidades.setSelectedItem(identidad
									.getNombreIdentidad());
							txtNIF.setText((String) identidad.getNif());
							txtCuenta.setText((String) identidad
									.getCuentaContable());
							txtNombre.setText((String) identidad
									.getNombreIdentidad());
							txtDireccion.setText((String) identidad
									.getDireccion());
							txtPoblacion.setText((String) identidad
									.getPoblacion());
							txtCodigoPostal.setText((String) identidad
									.getCodigoPostal());
							txtProvincia.setText((String) identidad
									.getProvincia());
						} else {
							cboIdentidades.setSelectedItem(null);
							txtNIF.setText("");
							txtCuenta.setText("");
							txtNombre.setText("");
							txtDireccion.setText("");
							txtPoblacion.setText("");
							txtCodigoPostal.setText("");
							txtProvincia.setText("");
						}
					} catch (NumberFormatException nfe) {
						cboIdentidades.setSelectedItem(null);
					}
				}
				changingIdentidad = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.changeIdentidad()", he);
		}
	}

	private float calculaImpuestos() {
		try {
			Number TipoImpuesto = (Number) txtTipoImpuesto.getValue();
			if (TipoImpuesto.floatValue() == 0) {
				Float Impuesto = (float) 0;
				for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++) {
					Float unidades = (((Number) tblDetalle.getValueAt(actualrow, DetalleTableModel.columnUnidades))
							.floatValue());
					Float precio = (((Number) tblDetalle.getValueAt(actualrow, DetalleTableModel.columnPrecio))
							.floatValue());
					Float baseImponible = unidades * precio;
					Float PctImpuesto = (((Number) tblDetalle.getValueAt(actualrow, DetalleTableModel.columnPctImpuesto))
						.floatValue());
					
					Impuesto = Impuesto + (baseImponible * PctImpuesto / 100);
				}
				return (float) (Util.round(Impuesto, 2));
			} else {
				float Impuesto = 0;
				float baseImponible = ((Number) txtBaseImponible.getValue())
						.floatValue();
				float tipoImpuesto = ((Number) txtTipoImpuesto.getValue())
						.floatValue();
				Impuesto = baseImponible * tipoImpuesto / 100;
				return (float) (Util.round(Impuesto, 2));
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.calculaImpuestos()", he);
			return (float) 0;
		}
	}

	private float calculaRetencion() {
		try {
			if (txtTipoIRPF.getText().equals("")) {
				return 0;
			} else {
				float importe = 0;
				float baseImponible = ((Number) txtBaseImponible.getValue())
						.floatValue();
				float tipoIrpf = ((Number) txtTipoIRPF.getValue()).floatValue();
				importe = (baseImponible) * tipoIrpf / 100;
				return (float) Util.round(importe, 2);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.calculaRetencion()", he);
			return 0;
		}
	}

	private void calculaTotales() {
		try {
			Float totalBaseImponible = ((Number) 0).floatValue();
			Float totalExcento = ((Number) 0).floatValue();

			Integer numRows = tblDetalle.getRowCount();
			for (Integer actualRow = 0; actualRow < numRows; actualRow++) {
				if (tblDetalle.getValueAt(actualRow,
						DetalleTableModel.columnState).equals(
						DetalleTableModel.EditLine)) {
					Short ConIGIC = ((Number) 1).shortValue();
					if (!(tblDetalle.getValueAt(actualRow,
							DetalleTableModel.columnIdConcepto).equals(""))) {
						ConIGIC = entity.ConceptoConIGIC(parentFrame,
								((Number) tblDetalle.getValueAt(actualRow,
										DetalleTableModel.columnIdConcepto))
										.intValue());
					}
					else {
						Integer unidades = ((Number) tblDetalle.getValueAt(actualRow,
								DetalleTableModel.columnUnidades)).intValue();
						if (unidades == 0) {
							ConIGIC = ((Number) 0).shortValue();
						}
					}
					float importe = ((Number) (Util.round(
							((Number) (((DefaultTableModel) tblDetalle
									.getModel()).getValueAt(actualRow,
									DetalleTableModel.columnImporte)))
									.floatValue(), 2))).floatValue();

					if (ConIGIC == 1)
						totalBaseImponible = totalBaseImponible + importe;
					else
						totalExcento = totalExcento + importe;
				}
			}
			txtTipoImpuesto.commitEdit();
			txtTipoIRPF.commitEdit();
			txtBaseImponible.setValue(totalBaseImponible);
			txtImporteImpuesto.setValue(calculaImpuestos());
			txtImporteIRPF.setValue(calculaRetencion());
			txtTotalFactura.setValue(((Number) txtBaseImponible.getValue())
					.floatValue()
					+ ((Number) txtImporteImpuesto.getValue()).floatValue()
					- ((Number) txtImporteIRPF.getValue()).floatValue()
					+ totalExcento);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFacturaPago.calculaTotales()", he);
		} catch (ParseException e) {
			Message.ShowParseError(parentFrame,
					"FrmFacturaPago.calculaTotales()", e);
		}
	}

	private static void calculaImporte() {
		try {
			if (tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
					DetalleTableModel.columnUnidades).equals("")
					|| tblDetalle.getValueAt(tblDetalle.getSelectedRow(),
							DetalleTableModel.columnPrecio).equals("")) {

			} else {
				Float Unidades = (float) 0;
				Float Precio = (float) 0;

				Float Importe = (float) 0;
				Unidades = (((Number) tblDetalle.getValueAt(tblDetalle
					.getSelectedRow(), DetalleTableModel.columnUnidades))
					.floatValue());
				Precio = (((Number) tblDetalle.getValueAt(tblDetalle
					.getSelectedRow(), DetalleTableModel.columnPrecio))
					.floatValue());

				if (Unidades == 0) {
					Unidades = (float) 1;
				}
				Importe = (float) Util.round(Precio * Unidades, 2);

				tblDetalle.setValueAt(Importe, tblDetalle.getSelectedRow(),
						DetalleTableModel.columnImporte);
			}
		} catch (RuntimeException he) {
		
		}
	}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlData = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        txtImporte = new javax.swing.JFormattedTextField();
        txtPctImpuesto = new javax.swing.JFormattedTextField();
        txtPrecio = new javax.swing.JFormattedTextField();
        lblIdFactura = new javax.swing.JLabel();
        txtIdFactura = new javax.swing.JFormattedTextField();
        lblAno = new javax.swing.JLabel();
        txtAno = new javax.swing.JFormattedTextField();
        lblSemana = new javax.swing.JLabel();
        txtSemana = new javax.swing.JFormattedTextField();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JFormattedTextField();
        lblTotalFactura = new javax.swing.JLabel();
        txtTotalFactura = new javax.swing.JFormattedTextField();
        lblNIF = new javax.swing.JLabel();
        txtNIF = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblPoblacion = new javax.swing.JLabel();
        txtPoblacion = new javax.swing.JTextField();
        lblCodigoPostal = new javax.swing.JLabel();
        txtCodigoPostal = new javax.swing.JTextField();
        lblProvincia = new javax.swing.JLabel();
        txtProvincia = new javax.swing.JTextField();
        lblCuenta = new javax.swing.JLabel();
        txtCuenta = new javax.swing.JTextField();
        txtBaseImponible = new javax.swing.JFormattedTextField();
        lblBaseImponible = new javax.swing.JLabel();
        lblTipoImpuesto = new javax.swing.JLabel();
        txtTipoImpuesto = new javax.swing.JFormattedTextField();
        lblImporteImpuesto = new javax.swing.JLabel();
        txtImporteImpuesto = new javax.swing.JFormattedTextField();
        lblTipoIRPF = new javax.swing.JLabel();
        txtTipoIRPF = new javax.swing.JFormattedTextField();
        lblImporteIRPF = new javax.swing.JLabel();
        txtImporteIRPF = new javax.swing.JFormattedTextField();
        cmdSelectAll = new javax.swing.JButton();
        cmdDeselectAll = new javax.swing.JButton();
        cmdDeleteLinea = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        lblIdentidad = new javax.swing.JLabel();
        txtIdentidad = new javax.swing.JFormattedTextField();
        cboIdentidades = new javax.swing.JComboBox();
        txtUnidades = new javax.swing.JFormattedTextField();
        cboConceptos = new javax.swing.JComboBox();

        pnlData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlData.setName("pnlData"); // NOI18N
        pnlData.setPreferredSize(new java.awt.Dimension(1024, 768));

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/ok.png"))); // NOI18N
        btnOk.setToolTipText("Aceptar");
        btnOk.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnOkMousePressed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/cancel.png"))); // NOI18N
        btnCancel.setToolTipText("Cancelar");
        btnCancel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCancelMousePressed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblDetalle.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDetalle.setCellSelectionEnabled(true);
        tblDetalle.setRowHeight(18);
        tblDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDetalleKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblDetalle);

        txtImporte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImporte.setPreferredSize(new java.awt.Dimension(0, 0));
        
        txtPctImpuesto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtPctImpuesto.setPreferredSize(new java.awt.Dimension(0, 0));

        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.0000"))));
        txtPrecio.setPreferredSize(new java.awt.Dimension(0, 0));

        lblIdFactura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdFactura.setForeground(new java.awt.Color(255, 0, 0));
        lblIdFactura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdFactura.setText("Id factura");

        txtIdFactura.setEditable(false);
        txtIdFactura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdFactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdFactura.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblAno.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAno.setForeground(new java.awt.Color(255, 0, 0));
        lblAno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAno.setText("Aï¿½o");

        txtAno.setEditable(false);
        txtAno.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtAno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblSemana.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSemana.setForeground(new java.awt.Color(255, 0, 0));
        lblSemana.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSemana.setText("Semana");

        txtSemana.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSemana.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtSemana.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSemana.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 0, 0));
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFecha.setText("Fecha");

        txtFecha.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFecha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblTotalFactura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalFactura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalFactura.setText("Total factura");

        txtTotalFactura.setEditable(false);
        txtTotalFactura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTotalFactura.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTotalFactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalFactura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

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

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombre.setText("Nombre");

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

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDireccion.setText("Dirección");
        lblDireccion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        lblPoblacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPoblacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPoblacion.setText("Población");
        lblPoblacion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        lblProvincia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProvincia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProvincia.setText("Provincia");
        lblProvincia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtProvincia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtProvincia.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtProvincia.setAutoscrolls(false);
        txtProvincia.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtProvincia.setName("poblacion"); // NOI18N
        txtProvincia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProvinciaKeyTyped(evt);
            }
        });

        lblCuenta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCuenta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCuenta.setText("Cuenta");
        lblCuenta.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCuenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCuenta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCuenta.setAutoscrolls(false);
        txtCuenta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCuenta.setName("nif"); // NOI18N
        txtCuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCuentaKeyTyped(evt);
            }
        });

        txtBaseImponible.setEditable(false);
        txtBaseImponible.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtBaseImponible.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtBaseImponible.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtBaseImponible.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblBaseImponible.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBaseImponible.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBaseImponible.setText("Base imponible");

        lblTipoImpuesto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoImpuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoImpuesto.setText("% I.G.I.C.");

        txtTipoImpuesto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTipoImpuesto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTipoImpuesto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTipoImpuesto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTipoImpuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTipoImpuestoKeyReleased(evt);
            }
        });

        lblImporteImpuesto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblImporteImpuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblImporteImpuesto.setText("Impuestos");

        txtImporteImpuesto.setEditable(false);
        txtImporteImpuesto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtImporteImpuesto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImporteImpuesto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImporteImpuesto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblTipoIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoIRPF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoIRPF.setText("% I.R.P.F.");

        txtTipoIRPF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTipoIRPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTipoIRPF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTipoIRPF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTipoIRPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTipoIRPFKeyReleased(evt);
            }
        });

        lblImporteIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblImporteIRPF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblImporteIRPF.setText("Retenciï¿½n");

        txtImporteIRPF.setEditable(false);
        txtImporteIRPF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtImporteIRPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImporteIRPF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImporteIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        cmdSelectAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdSelectAll.setText("Seleccionar todo");
        cmdSelectAll.setToolTipText("Seleccionar todas las filas");
        cmdSelectAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdSelectAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdSelectAllMousePressed(evt);
            }
        });

        cmdDeselectAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeselectAll.setText("Quitar selecciï¿½n");
        cmdDeselectAll.setToolTipText("Quitar la seleccionar todas las filas");
        cmdDeselectAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeselectAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeselectAllMousePressed(evt);
            }
        });

        cmdDeleteLinea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeleteLinea.setText("Eliminar lï¿½nea");
        cmdDeleteLinea.setToolTipText("Eliminar las lï¿½neas seleccionados");
        cmdDeleteLinea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeleteLinea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeleteLineaMousePressed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Print.png"))); // NOI18N
        btnPrint.setToolTipText("Imprimir");
        btnPrint.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPrintMousePressed(evt);
            }
        });

        lblIdentidad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdentidad.setForeground(new java.awt.Color(255, 0, 0));
        lblIdentidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdentidad.setText("Cliente");

        txtIdentidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdentidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdentidad.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        txtIdentidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdentidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdentidadKeyReleased(evt);
            }
        });

        cboIdentidades.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboIdentidades.setName("Id subcategoria"); // NOI18N
        cboIdentidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboIdentidadesActionPerformed(evt);
            }
        });

        txtUnidades.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtUnidades.setPreferredSize(new java.awt.Dimension(0, 0));

        cboConceptos.setEditable(true);
        cboConceptos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        cboConceptos.setPreferredSize(new java.awt.Dimension(0, 0));
        cboConceptos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboConceptosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDataLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDataLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDataLayout.createSequentialGroup()
                                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtBaseImponible)
                                            .addComponent(lblBaseImponible))
                                        .addGap(18, 18, 18)
                                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(pnlDataLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(txtTipoImpuesto))
                                            .addComponent(lblTipoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblImporteImpuesto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtImporteImpuesto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(pnlDataLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(txtTipoIRPF))
                                            .addComponent(lblTipoIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pnlDataLayout.createSequentialGroup()
                                        .addComponent(cmdSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmdDeselectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDataLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmdDeleteLinea, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlDataLayout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtImporteIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblImporteIRPF))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlDataLayout.createSequentialGroup()
                                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlDataLayout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                                                .addGap(32, 32, 32)
                                                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(cboConceptos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(2, 2, 2)))
                                        .addGap(61, 61, 61)
                                        .addComponent(btnPrint)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnOk))
                                    .addGroup(pnlDataLayout.createSequentialGroup()
                                        .addComponent(lblTotalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDataLayout.createSequentialGroup()
                                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblAno, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblIdFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtIdFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pnlDataLayout.createSequentialGroup()
                                                .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(92, 92, 92)
                                                .addComponent(lblSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                                                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(pnlDataLayout.createSequentialGroup()
                                        .addComponent(lblIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboIdentidades, 0, 635, Short.MAX_VALUE))))))
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(lblNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(lblCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE))
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDataLayout.createSequentialGroup()
                                        .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
                                        .addComponent(lblCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE))))))
                .addContainerGap())
            .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                    .addContainerGap(639, Short.MAX_VALUE)
                    .addComponent(txtUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(238, 238, 238)))
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIdFactura))
                .addGap(8, 8, 8)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAno))
                    .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSemana)
                        .addComponent(lblFecha)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIdentidad)
                    .addComponent(cboIdentidades, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNIF)
                    .addComponent(lblCuenta)
                    .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDireccion)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPoblacion)
                    .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigoPostal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProvincia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotalFactura))
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmdSelectAll)
                                    .addComponent(cmdDeselectAll)
                                    .addComponent(cmdDeleteLinea)
                                    .addComponent(cboConceptos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnCancel)
                                .addComponent(btnOk)
                                .addComponent(btnPrint)))
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBaseImponible)
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(txtBaseImponible, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblImporteImpuesto)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtImporteImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblTipoImpuesto)
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addComponent(txtTipoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblTipoIRPF)
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addComponent(txtTipoIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblImporteIRPF)
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addComponent(txtImporteIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(48, 48, 48)))
                .addContainerGap())
            .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                    .addContainerGap(515, Short.MAX_VALUE)
                    .addComponent(txtUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	void cboConceptosActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingConcepto) {
				changingConcepto = true;
				if (cboConceptos.getSelectedIndex() != -1) {
					if (tblDetalle.getRowCount() > 0
							&& tblDetalle.getSelectedRow() != -1) {
						List<?> conceptoslist = entity
								.ConceptoFindByConceptoDesc(this,
										(String) cboConceptos.getSelectedItem());
						if (conceptoslist.size() > 0) {
							Conceptos conceptosItem = (Conceptos) conceptoslist
									.get(0);
							tblDetalle.setValueAt(conceptosItem.getId()
									.getConcepto(),
									tblDetalle.getSelectedRow(),
									DetalleTableModel.columnIdConcepto);
						}
					}
				}
				changingConcepto = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.cboConceptosActionPerformed()", he);
		}
	}

	private void cboIdentidadesActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingIdentidad) {
				changingIdentidad = true;
				if (cboIdentidades.getSelectedIndex() != -1) {
					List<?> Identidadeslist = entity.executeQuery(this, "*",
							"Identidades", "NombreIdentidad = '"
									+ (String) cboIdentidades.getSelectedItem()
									+ "'", "");
					if (Identidadeslist.size() > 0) {
						Identidades IdentidadesItem = (Identidades) Identidadeslist
								.get(0);
						txtIdentidad.setValue(IdentidadesItem.getId()
								.getIdentidad());
						txtNIF.setText((String) IdentidadesItem.getNif());
						txtCuenta.setText((String) IdentidadesItem
								.getCuentaContable());
						txtNombre.setText((String) IdentidadesItem
								.getNombreIdentidad());
						txtDireccion.setText((String) IdentidadesItem
								.getDireccion());
						txtPoblacion.setText((String) IdentidadesItem
								.getPoblacion());
						txtCodigoPostal.setText((String) IdentidadesItem
								.getCodigoPostal());
						txtProvincia.setText((String) IdentidadesItem
								.getProvincia());
					}
				}
				changingIdentidad = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.cboIdentidadesActionPerformed", he);
		}
	}

	private void txtIdentidadKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeIdentidad();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtIdentidadKeyReleased()", he);
		}
	}

	private void btnPrintMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			/* FacturaServicios report = new FacturaServicios(parentFrame);
			report.runReporte(factura.getId().getEmpresas().getIdEmpresa(),
					factura.getId().getEjercicios().getEjercicio(), factura
							.getId().getIdFactura(), factura.getId()
							.getIdFactura()); */
			FrmInformeFacturas frm = new FrmInformeFacturas(parentFrame, session, false, factura
					.getId().getIdFactura());
			frm.setLocationRelativeTo(null);
			frm.setVisible(true);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.btnPrintMousePressed()", he);
		}
	}

	private void txtTipoImpuestoKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			calculaTotales();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtTipoImpuestoKeyReleased()", he);
		}
	}

	private void txtTipoIRPFKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			calculaTotales();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtTipoImpuestoKeyReleased()", he);
		}
	}

	private void txtCuentaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuenta, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtCuentaKeyTyped()", he);
		}
	}

	private void txtProvinciaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtProvincia, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtProvinciaKeyTyped()", he);
		}
	}

	private void txtCodigoPostalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCodigoPostal, evt, 6);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtCodigoPostalKeyTyped()", he);
		}
	}

	private void txtPoblacionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtPoblacion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtPoblacionKeyTyped()", he);
		}
	}

	private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDireccion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtDireccionKeyTyped()", he);
		}
	}

	private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNombre, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtNombreKeyTyped()", he);
		}
	}

	private void txtNIFKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNIF, evt, 12);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.txtNIFKeyTyped()", he);
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
					"FrmFactura.cmdDeleteLineaMousePressed()", he);
		}
	}

	private void cmdSelectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++)
				tblDetalle.setValueAt(true, actualrow,
						DetalleTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.cmdSelectAllMousePressed()", he);
		}
	}

	private void cmdDeselectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblDetalle.getRowCount(); actualrow++)
				tblDetalle.setValueAt(false, actualrow,
						DetalleTableModel.columnSelect);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.cmdDeselectAllMousePressed()", he);
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
						|| (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 122)) {
					tblDetalle.setValueAt("", tblDetalle.getSelectedRow(),
							tblDetalle.getSelectedColumn());
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.tblDetalleKeyPressed()", he);
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
					"FrmFactura.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.factura);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.btnCancelMousePressed()", he);
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnPrint;
    private static javax.swing.JComboBox cboConceptos;
    private static javax.swing.JComboBox cboIdentidades;
    private javax.swing.JButton cmdDeleteLinea;
    private javax.swing.JButton cmdDeselectAll;
    private javax.swing.JButton cmdSelectAll;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAno;
    private javax.swing.JLabel lblBaseImponible;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblCuenta;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblIdFactura;
    private javax.swing.JLabel lblIdentidad;
    private javax.swing.JLabel lblImporteIRPF;
    private javax.swing.JLabel lblImporteImpuesto;
    private javax.swing.JLabel lblNIF;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPoblacion;
    private javax.swing.JLabel lblProvincia;
    private javax.swing.JLabel lblSemana;
    private javax.swing.JLabel lblTipoIRPF;
    private javax.swing.JLabel lblTipoImpuesto;
    private javax.swing.JLabel lblTotalFactura;
    public static javax.swing.JPanel pnlData;
    private static javax.swing.JTable tblDetalle;
    private javax.swing.JFormattedTextField txtAno;
    private javax.swing.JFormattedTextField txtBaseImponible;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtCuenta;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JFormattedTextField txtIdFactura;
    private static javax.swing.JFormattedTextField txtIdentidad;
    private static javax.swing.JFormattedTextField txtImporte;
    private static javax.swing.JFormattedTextField txtPctImpuesto;
    private javax.swing.JFormattedTextField txtImporteIRPF;
    private javax.swing.JFormattedTextField txtImporteImpuesto;
    private javax.swing.JTextField txtNIF;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPoblacion;
    private static javax.swing.JFormattedTextField txtPrecio;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JFormattedTextField txtSemana;
    private javax.swing.JFormattedTextField txtTipoIRPF;
    private javax.swing.JFormattedTextField txtTipoImpuesto;
    private javax.swing.JFormattedTextField txtTotalFactura;
    private static javax.swing.JFormattedTextField txtUnidades;
    // End of variables declaration//GEN-END:variables

}

