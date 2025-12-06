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
import entitiesPackage.Cosecheros;
import entitiesPackage.CosecherosId;
import entitiesPackage.Cosecherosparcelas;
import entitiesPackage.CosecherosparcelasId;
import entitiesPackage.Entity;
import entitiesPackage.EntityType;
import entitiesPackage.Message;
import entitiesPackage.Zonas;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmCosechero extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	public static class ParcelasTableModel extends DefaultTableModel {

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
		final public static int columnMunicipio = 2;
		final public static int columnParaje = 3;
		final public static int columnPoligono = 4;
		final public static int columnParcela = 5;
		final public static int columnRecinto = 6;
		final public static int columnSuperficie = 7;
		final public static int columnSuperficieCultivada = 8;

		static String[] columnNames = { "Select", "State", "Municipio",
				"Paraje", "Poligono", "Parcela", "Recinto", "Superficie",
				"SuperficieCultivada" };

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

		public ParcelasTableModel() {

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
			columns.add(new ColumnData("Municipio", "Municipio",
					EntityType.MediumTextWidth, SwingConstants.LEFT,
					NormalType, null, null));
			columns.add(new ColumnData("Paraje", "Paraje",
					EntityType.MediumTextWidth, SwingConstants.LEFT,
					NormalType, null, null));
			columns.add(new ColumnData("Poligono", "Pol�gono",
					EntityType.ShortTextWidth, SwingConstants.LEFT, NormalType,
					null, null));
			columns.add(new ColumnData("Parcela", "Parcela",
					EntityType.ShortTextWidth, SwingConstants.LEFT, NormalType,
					null, null));
			columns.add(new ColumnData("Recinto", "Recinto",
					EntityType.ShortTextWidth, SwingConstants.LEFT, NormalType,
					null, null));
			columns.add(new ColumnData("Superficie", "Superficie",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float4Type,
					txtSuperficie, "#,##0.00"));
			columns.add(new ColumnData("SuperficieCultivada", "Sup. cultivada",
					EntityType.NumberWidth, SwingConstants.RIGHT, Float4Type,
					txtSuperficieCultivada, "#,##0.00"));

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
			case columnSuperficie: {
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
			case columnSuperficieCultivada: {
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

	Cosecheros cosechero;

	private FrmPagoLiquidacion frmPago;

	static boolean changingState = false;
	static boolean addLine = false;
	static boolean changingCategoria = false;

	private static java.awt.Frame parentFrame;
	private MySession session;
	private boolean OnNew = false;
	private Entity entity = new Entity();

	private static boolean changingBanco = false;
	private static boolean changingSucursal = false;
	private static boolean changingZona = false;

	public java.awt.Frame getParentFrame() {
		return FrmCosechero.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		FrmCosechero.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmCosechero() {
		initComponents();
	}

	public FrmCosechero(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			FrmCosechero.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmCosechero()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmCosechero.configureKeys",
					he);
		}
	}

	private void prepareTable() {
		try {
			ParcelasTableModel modelDetalle = new ParcelasTableModel();
			tblParcelas.setModel(modelDetalle);
			tblParcelas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			for (int k = 0; k < modelDetalle.columns.size(); k++) {

				Object renderer = null;
				DefaultCellEditor editor = null;

				Integer editortype = modelDetalle.columns.get(k).m_editortype;
				switch (editortype) {

				case ParcelasTableModel.NormalType: {
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
				case ParcelasTableModel.Float2Type: {
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
				case ParcelasTableModel.Float4Type: {
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
				case ParcelasTableModel.ComboType: {
					renderer = new DefaultTableCellRenderer();
					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelDetalle.columns.get(k).m_alignment);
					editor = new DefaultCellEditor(
							(JComboBox) modelDetalle.columns.get(k).m_editor);
					break;
				}
				case ParcelasTableModel.CheckType: {
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
				case ParcelasTableModel.StateType: {

					final JLabel stateIcon = new JLabel();
					renderer = new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public JLabel getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							if (value.equals(ParcelasTableModel.NewLine))
								stateIcon.setIcon(ParcelasTableModel.Adding);
							else if (value.equals(ParcelasTableModel.EditLine))
								stateIcon.setIcon(ParcelasTableModel.Editing);

							return stateIcon;
						}
					};

					((DefaultTableCellRenderer) renderer)
							.setHorizontalAlignment(modelDetalle.columns.get(k).m_alignment);
					break;
				}
				}
				TableColumn column = tblParcelas.getColumn(modelDetalle.columns
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
					"FrmCosecheros.prepareTable()", he);
		}
	}

	public void newData() {

		try {
			this.cosechero = new Cosecheros();
			prepareTable();
			loadBancos();
			loadSucursales("");
			loadZonas();
			txtIdCosechero.setText("");
			txtIdCosechero.setEditable(true);
			txtNIF.setText("");
			txtNombre.setText("");
			txtApellidos.setText("");
			txtDireccion.setText("");
			txtPoblacion.setText("");
			txtCodigoPostal.setText("");
			txtFAX.setText("");
			txtTelefono1.setText("");
			txtTelefono2.setText("");
			txtEmail.setText("");
			txtIdZona.setText("");
			cboZonas.setSelectedItem(null);
			chkGrupoPladimisa.setText("");
			txtIdGrupo.setText("");
			txtCuentaContable.setText("");
			txtTipoIGIC.setText("");
			txtTipoIRPF.setText("");
			txtIdBanco.setText("");
			cboBancos.setSelectedItem(null);
			txtIdSucursal.setText("");
			cboSucursales.setSelectedItem(null);
			txtDigitoControl.setText("");
			txtCuentaBancaria.setText("");
                        txtIBAN.setText("");
			txtCodigoINE.setText("");
			txtCodigoAsesoria.setText("");
			chkOCM.setText("");
			txtNumKilosReferencia.setText("");
			Integer numRows = tblParcelas.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblParcelas.getModel()).removeRow(0);
			newLineDetalle();
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.newData()", he);
		}
	}

	private void newLineDetalle() {
		try {
			if (tblParcelas.getRowCount() > 0) {
				if (!(tblParcelas.getValueAt(tblParcelas.getSelectedRow(),
						ParcelasTableModel.columnMunicipio).equals(""))) {
					addLine = true;
					((DefaultTableModel) tblParcelas.getModel())
							.addRow(new Object[] { false,
									ParcelasTableModel.NewLine, "", "", "", "",
									"", 0, 0 });
					tblParcelas.changeSelection(
							tblParcelas.getSelectedRow() + 1,
							ParcelasTableModel.columnMunicipio, false, false);
					tblParcelas.editCellAt(tblParcelas.getSelectedRow() + 1,
							ParcelasTableModel.columnMunicipio);
					addLine = false;
				}
			} else
				((DefaultTableModel) tblParcelas.getModel())
						.addRow(new Object[] { false,
								ParcelasTableModel.NewLine, "", "", "", "", "",
								0, 0 });
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmCosecheros.newLineDetalle()", he);
		}

	}

	private static void changeEditState() {
		try {
			if (!changingState) {
				if (tblParcelas.getSelectedRow() != -1) {
					changingState = true;
					tblParcelas.setValueAt(ParcelasTableModel.EditLine,
							tblParcelas.getSelectedRow(),
							ParcelasTableModel.columnState);
					changingState = false;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmCosecheros.changeEditState()", he);
		}
	}

	public void loadData(Cosecheros entity) {

		try {
			this.cosechero = entity;
			prepareTable();
			loadBancos();
			loadSucursales(entity.getIdBanco());
			loadZonas();
			txtIdCosechero.setValue(entity.getId().getIdCosechero());
			txtIdCosechero.setEditable(false);
			txtNIF.setText(entity.getNif());
			txtNombre.setText(entity.getNombre());
			txtApellidos.setText(entity.getApellidos());
			txtDireccion.setText(entity.getDireccion());
			txtPoblacion.setText(entity.getPoblacion());
			txtCodigoPostal.setText(entity.getCodigoPostal());
			txtFAX.setText(entity.getFax());
			txtTelefono1.setText(entity.getTelefono1());
			txtTelefono2.setText(entity.getTelefono2());
			txtEmail.setText(entity.getEmail());
			txtIdZona.setValue(entity.getIdZona());
			chkGrupoPladimisa.setSelected(entity.getGrupoPladimisa() != 0);
			txtIdGrupo.setValue(entity.getIdGrupo());
			txtCuentaContable.setText(entity.getCuentaContable());
			txtTipoIGIC.setValue(entity.getTipoIgic());
			txtTipoIRPF.setValue(entity.getTipoIrpf());
			txtIdBanco.setText(entity.getIdBanco());
			changeBanco();
			txtIdSucursal.setText(entity.getIdSucursal());
			changeSucursal();
			txtDigitoControl.setValue(entity.getDigitoControl());
			txtCuentaBancaria.setText(entity.getCuentaBancaria());
                        txtIBAN.setText(entity.getIban());
			txtCodigoINE.setText(entity.getCodigoIne());
			txtCodigoAsesoria.setValue(entity.getCodigoAsesoria());
			chkOCM.setSelected(entity.getOcm() != 0);
			txtNumKilosReferencia.setValue(entity.getNumKilosReferencia());
			changeZona();
			loadDataDetail();
			OnNew = false;
		} catch (RuntimeException he) {
			Message
					.ShowRuntimeError(parentFrame, "FrmCosechero.loadData()",
							he);
		}
	}

	private void loadDataDetail() {

		try {
			Integer numRows = tblParcelas.getRowCount();
			for (Integer k = 0; k < numRows; k++)
				((DefaultTableModel) tblParcelas.getModel()).removeRow(0);

			List<?> lineasList = entity.executeQuery(this, "*",
					"Cosecherosparcelas", "id.idCosechero="
							+ cosechero.getId().getIdCosechero(),
					"id.idParcela");

			if (lineasList.size() == 0)
				newLineDetalle();
			else {
				for (Object linea : lineasList) {
					Cosecherosparcelas Cosecheroparcela = (Cosecherosparcelas) linea;
					Vector<Object> oneRow = new Vector<Object>();
					oneRow.add(false);
					oneRow.add(ParcelasTableModel.EditLine);
					oneRow.add(Cosecheroparcela.getMunicipio());
					oneRow.add(Cosecheroparcela.getParaje());
					oneRow.add(Cosecheroparcela.getPoligono());
					oneRow.add(Cosecheroparcela.getParcela());
					oneRow.add(Cosecheroparcela.getRecinto());
					oneRow.add(Cosecheroparcela.getSuperficie());
					oneRow.add(Cosecheroparcela.getSuperficieCultivada());
					((DefaultTableModel) tblParcelas.getModel()).addRow(oneRow);
				}
			}

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmCosechero.loadDataDetail()", he);
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
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.loadBancos()", he);
		}
	}

	private void loadSucursales(String bancoId) {
		try {
			cboSucursales.removeAllItems();

			List<?> bancosList = entity.BancoFindByIdBanco(this, bancoId);

			for (Object o : bancosList) {
				Bancos banco = (Bancos) o;
				cboSucursales.addItem(banco.getNombreSucursal());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.loadSucursales()", he);
		}
	}

	private void loadZonas() {

		try {
			cboZonas.removeAllItems();

			List<?> zonasList = entity.ZonaFindAll(this);

			for (Object o : zonasList) {
				Zonas zona = (Zonas) o;
				cboZonas.addItem(zona.getNombreZona());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.loadZonas()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {

		try {
			if (validateData()) {
				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					if (txtIdCosechero.getText().equals("")) {
						txtIdCosechero.setValue(entity.newId(this,
								"Cosecheros", "id.idCosechero"));
					}
					CosecherosId cosecheroid = new CosecherosId();
					cosecheroid.setEmpresas(session.getEmpresa());
					cosecheroid.setEjercicios(session.getEjercicio());
					cosecheroid.setIdCosechero(((Number) txtIdCosechero
							.getValue()).intValue());
					cosechero.setId(cosecheroid);
				}
				if (!txtNIF.getText().equals(""))
					cosechero.setNif(txtNIF.getText());
				else
					cosechero.setNif(null);
				if (!txtNombre.getText().equals(""))
					cosechero.setNombre(txtNombre.getText());
				else
					cosechero.setNombre(null);
				if (!txtApellidos.getText().equals(""))
					cosechero.setApellidos(txtApellidos.getText());
				else
					cosechero.setApellidos(null);
				if (!txtDireccion.getText().equals(""))
					cosechero.setDireccion(txtDireccion.getText());
				else
					cosechero.setDireccion(null);
				if (!txtPoblacion.getText().equals(""))
					cosechero.setPoblacion(txtPoblacion.getText());
				else
					cosechero.setPoblacion(null);
				if (!txtCodigoPostal.getText().equals(""))
					cosechero.setCodigoPostal(txtCodigoPostal.getText());
				else
					cosechero.setCodigoPostal(null);
				if (!txtFAX.getText().equals(""))
					cosechero.setFax(txtFAX.getText());
				else
					cosechero.setFax(null);
				if (!txtTelefono1.getText().equals(""))
					cosechero.setTelefono1(txtTelefono1.getText());
				else
					cosechero.setTelefono1(null);
				if (!txtTelefono2.getText().equals(""))
					cosechero.setTelefono2(txtTelefono2.getText());
				else
					cosechero.setTelefono2(null);
				if (!txtEmail.getText().equals(""))
					cosechero.setEmail(txtEmail.getText());
				else
					cosechero.setEmail(null);
				if (!txtIdZona.getText().equals("")) {
					cosechero.setIdZona(((Number) txtIdZona.getValue())
							.intValue());
				} else
					cosechero.setIdZona(null);
				if (chkGrupoPladimisa.isSelected())
					cosechero.setGrupoPladimisa(((Number) 1).shortValue());
				else
					cosechero.setGrupoPladimisa(((Number) 0).shortValue());
				if (txtIdGrupo.getText().equals(""))
					txtIdGrupo.setValue(txtIdCosechero.getValue());
				cosechero.setIdGrupo(((Number) txtIdGrupo.getValue())
						.intValue());
				if (!txtCuentaContable.getText().equals(""))
					cosechero.setCuentaContable(txtCuentaContable.getText());
				else
					cosechero.setCuentaContable(null);
				if (!txtTipoIGIC.getText().equals(""))
					cosechero.setTipoIgic(((Number) txtTipoIGIC.getValue())
							.intValue());
				else
					cosechero.setTipoIgic(0);
				if (!txtTipoIRPF.getText().equals(""))
					cosechero.setTipoIrpf(((Number) txtTipoIRPF.getValue())
							.intValue());
				else
					cosechero.setTipoIrpf(0);
				if (!txtIdBanco.getText().equals(""))
					cosechero.setIdBanco(txtIdBanco.getText());
				else
					cosechero.setIdBanco(null);
				if (!txtIdSucursal.getText().equals(""))
					cosechero.setIdSucursal(txtIdSucursal.getText());
				else
					cosechero.setIdSucursal(null);
				if (!txtDigitoControl.getText().equals(""))
					cosechero.setDigitoControl(txtDigitoControl.getText());
				else
					cosechero.setDigitoControl(null);
				if (!txtCuentaBancaria.getText().equals(""))
					cosechero.setCuentaBancaria(txtCuentaBancaria.getText());
				else
					cosechero.setCuentaBancaria(null);
                                if (!txtIBAN.getText().equals(""))
					cosechero.setIban(txtIBAN.getText());
				else
					cosechero.setIban(null);
				if (!txtCodigoINE.getText().equals(""))
					cosechero.setCodigoIne(txtCodigoINE.getText());
				else
					cosechero.setCodigoIne(null);
				if (!txtCodigoAsesoria.getText().equals(""))
					cosechero.setCodigoAsesoria(((Number) txtCodigoAsesoria
							.getValue()).intValue());
				else
					cosechero.setCodigoAsesoria(null);
				if (chkOCM.isSelected())
					cosechero.setOcm(((Number) 1).shortValue());
				else
					cosechero.setOcm(((Number) 0).shortValue());
				if (!txtNumKilosReferencia.getText().equals(""))
					cosechero
							.setNumKilosReferencia(((Number) txtNumKilosReferencia
									.getValue()).floatValue());
				else
					cosechero.setNumKilosReferencia(((Number) 0).floatValue());
				cosechero.setLmd(new Date());
				cosechero.setSid("Santi");
				cosechero.setVersion(1);
				session.getSession().replicate(cosechero,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(cosechero);
				session.getSession().flush();

				String deletelinesquery = "Delete From Cosecherosparcelas "
						+ "Where id.idCosechero = "
						+ ((Number) txtIdCosechero.getValue()).intValue()
						+ " and id.empresas.idEmpresa="
						+ session.getEmpresa().getIdEmpresa()
						+ " and id.ejercicios.ejercicio="
						+ session.getEjercicio().getEjercicio();

				Query q = getSession().getSession().createQuery(
						deletelinesquery);
				q.executeUpdate();

				for (Integer k = 0; k < tblParcelas.getRowCount(); k++) {
					if (tblParcelas.getValueAt(k,
							ParcelasTableModel.columnState).equals(
							ParcelasTableModel.EditLine)) {
						Cosecherosparcelas cosecheroparcela = new Cosecherosparcelas();
						CosecherosparcelasId cosecheroparcelaId = new CosecherosparcelasId();
						cosecheroparcelaId
								.setEjercicios(session.getEjercicio());
						cosecheroparcelaId.setEmpresas(session.getEmpresa());
						cosecheroparcelaId
								.setIdCosechero((Integer) txtIdCosechero
										.getValue());
						cosecheroparcelaId.setIdParcela(k + 1);
						cosecheroparcela.setId(cosecheroparcelaId);
						if (tblParcelas.getValueAt(k,
								ParcelasTableModel.columnMunicipio).equals(""))
							cosecheroparcela.setMunicipio(null);
						else
							cosecheroparcela
									.setMunicipio((String) tblParcelas
											.getValueAt(
													k,
													ParcelasTableModel.columnMunicipio));
						if (tblParcelas.getValueAt(k,
								ParcelasTableModel.columnParaje).equals(""))
							cosecheroparcela.setParaje("");
						else
							cosecheroparcela.setParaje((String) tblParcelas
									.getValueAt(k,
											ParcelasTableModel.columnParaje));
						if (tblParcelas.getValueAt(k,
								ParcelasTableModel.columnPoligono).equals(""))
							cosecheroparcela.setPoligono(null);
						else
							cosecheroparcela.setPoligono((String) tblParcelas
									.getValueAt(k,
											ParcelasTableModel.columnPoligono));
						if (tblParcelas.getValueAt(k,
								ParcelasTableModel.columnParcela).equals(""))
							cosecheroparcela.setParcela(null);
						else
							cosecheroparcela.setParcela((String) tblParcelas
									.getValueAt(k,
											ParcelasTableModel.columnParcela));
						if (tblParcelas.getValueAt(k,
								ParcelasTableModel.columnRecinto).equals(""))
							cosecheroparcela.setRecinto(null);
						else
							cosecheroparcela.setRecinto((String) tblParcelas
									.getValueAt(k,
											ParcelasTableModel.columnRecinto));
						if (tblParcelas.getValueAt(k,
								ParcelasTableModel.columnSuperficie).equals(""))
							cosecheroparcela.setSuperficie(null);
						else
							cosecheroparcela
									.setSuperficie(((Number) tblParcelas
											.getValueAt(
													k,
													ParcelasTableModel.columnSuperficie))
											.floatValue());
						if (tblParcelas.getValueAt(k,
								ParcelasTableModel.columnSuperficieCultivada)
								.equals(""))
							cosecheroparcela.setSuperficieCultivada(null);
						else
							cosecheroparcela
									.setSuperficieCultivada(((Number) tblParcelas
											.getValueAt(
													k,
													ParcelasTableModel.columnSuperficieCultivada))
											.floatValue());
						cosecheroparcela.setLmd(new Date());
						cosecheroparcela.setSid("Santi");
						cosecheroparcela.setVersion(1);
						session.getSession().replicate(cosecheroparcela,
								ReplicationMode.OVERWRITE);
						session.getSession().saveOrUpdate(cosecheroparcela);
						session.getSession().flush();
					}
				}
				if (transaction.isActive()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage) {
					TabCosechero.setSelectedIndex(0);
					Message.ShowSaveSuccesfull(pnlData);
				}
				OnNew = false;
				FrmCosecheros.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.saveData()", he);
			return false;
		}
	}

	private boolean validateData() {

		try {
			if (!(txtIdCosechero.getText().equals("")) && OnNew) {
				try {
					txtIdCosechero.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es v�lido.");
					txtIdCosechero.requestFocus();
					return (false);
				}
				if (entity.CosecheroFindByIdCosechero(this,
						((Number) txtIdCosechero.getValue()).intValue()) != null) {
					Message.ShowValidateMessage(pnlData,
							"El c�digo de cosechero especificado ya existe.");
					TabCosechero.setSelectedIndex(0);
					txtIdCosechero.requestFocus();
					return (false);
				}
			}
			if (txtNombre.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un nombre para el cosechero.");
				TabCosechero.setSelectedIndex(0);
				txtNombre.requestFocus();
				return (false);
			}
			if (!txtIdZona.getText().equals("")) {
				try {
					txtIdZona.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es v�lido.");
					txtIdZona.requestFocus();
					return (false);
				}
			}
			if (!txtTipoIGIC.getText().equals("")) {
				try {
					txtTipoIGIC.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es v�lido.");
					txtTipoIGIC.requestFocus();
					return (false);
				}
			}
			if (!txtTipoIRPF.getText().equals("")) {
				try {
					txtTipoIRPF.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es v�lido.");
					txtTipoIRPF.requestFocus();
					return (false);
				}
			}
			if (!txtIdGrupo.getText().equals("")) {
				try {
					txtIdGrupo.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es v�lido.");
					txtIdGrupo.requestFocus();
					return (false);
				}
			}
			if (!txtCodigoAsesoria.getText().equals("")) {
				try {
					txtCodigoAsesoria.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es v�lido.");
					txtCodigoAsesoria.requestFocus();
					return (false);
				}
			}
			if (!validateDetalleData())
				return (false);

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmCosechero.validateData()", he);
			return (false);
		}
	}

	private boolean validateDetalleData() {
		try {
			for (Integer actualrow = 0; actualrow < tblParcelas.getRowCount(); actualrow++) {
				if (tblParcelas.getValueAt(actualrow,
						ParcelasTableModel.columnState).equals(
						ParcelasTableModel.EditLine)) {
					if (tblParcelas.getValueAt(actualrow,
							ParcelasTableModel.columnPoligono).equals("")) {
						Message.ShowValidateMessage(tblParcelas,
								"Debe indicar el pol�gono.");
						tblParcelas
								.changeSelection(tblParcelas.getSelectedRow(),
										ParcelasTableModel.columnPoligono,
										false, false);
						tblParcelas.editCellAt(actualrow,
								ParcelasTableModel.columnPoligono);
						tblParcelas.requestFocusInWindow();
						return (false);
					}
					if (tblParcelas.getValueAt(actualrow,
							ParcelasTableModel.columnParcela).equals("")) {
						Message.ShowValidateMessage(tblParcelas,
								"Debe indicar la parcela.");
						tblParcelas.changeSelection(tblParcelas
								.getSelectedRow(),
								ParcelasTableModel.columnParcela, false, false);
						tblParcelas.editCellAt(actualrow,
								ParcelasTableModel.columnParcela);
						tblParcelas.requestFocusInWindow();
						return (false);
					}
					if (tblParcelas.getValueAt(actualrow,
							ParcelasTableModel.columnRecinto).equals("")) {
						Message.ShowValidateMessage(tblParcelas,
								"Debe indicar el recinto.");
						tblParcelas.changeSelection(tblParcelas
								.getSelectedRow(),
								ParcelasTableModel.columnRecinto, false, false);
						tblParcelas.editCellAt(actualrow,
								ParcelasTableModel.columnRecinto);
						tblParcelas.requestFocusInWindow();
						return (false);
					}
				}
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmFactura.validateDetalleData()", he);
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
						List<?> bancoslist = entity.BancoFindByIdBanco(this,
								value);
						if (bancoslist.size() > 0) {
							cboBancos.setSelectedItem(((Bancos) bancoslist
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
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.changeBanco()", he);
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
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.changeSucursal()", he);
		}
	}

	private void changeZona() {

		try {
			if (!changingZona) {
				changingZona = true;
				if (txtIdZona.getText().equals(""))
					cboZonas.setSelectedItem(null);
				else {
					try {
						Integer value = Integer.parseInt(txtIdZona.getText());
						Zonas zona = entity.ZonaFindByIdZona(this, value);
						if (zona != null)
							cboZonas.setSelectedItem(zona.getNombreZona());
						else {
							cboZonas.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboZonas.setSelectedItem(null);
					}
				}
				changingZona = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.changeZona()", he);
		}
	}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlData = new javax.swing.JPanel();
        TabCosechero = new javax.swing.JTabbedPane();
        pnlData1 = new javax.swing.JPanel();
        lblIdCosechero = new javax.swing.JLabel();
        lblApellidos = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        lblPoblacion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblCodigoPostal = new javax.swing.JLabel();
        txtCodigoPostal = new javax.swing.JTextField();
        lblFAX = new javax.swing.JLabel();
        txtFAX = new javax.swing.JTextField();
        lblTelefono1 = new javax.swing.JLabel();
        txtTelefono1 = new javax.swing.JTextField();
        lblTelefono2 = new javax.swing.JLabel();
        txtTelefono2 = new javax.swing.JTextField();
        lblNIF = new javax.swing.JLabel();
        txtNIF = new javax.swing.JTextField();
        txtIdCosechero = new javax.swing.JFormattedTextField();
        txtPoblacion = new javax.swing.JTextField();
        txtIdZona = new javax.swing.JFormattedTextField();
        cboZonas = new javax.swing.JComboBox();
        lblIdZona = new javax.swing.JLabel();
        lblGrupoPladimisa = new javax.swing.JLabel();
        chkGrupoPladimisa = new javax.swing.JCheckBox();
        lblIdGrupo = new javax.swing.JLabel();
        txtIdGrupo = new javax.swing.JFormattedTextField();
        lblNumKilosRef = new javax.swing.JLabel();
        txtNumKilosReferencia = new javax.swing.JFormattedTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        pnlData2 = new javax.swing.JPanel();
        lblCuentaContable = new javax.swing.JLabel();
        txtCuentaContable = new javax.swing.JTextField();
        lblTipoIGIC = new javax.swing.JLabel();
        txtTipoIGIC = new javax.swing.JFormattedTextField();
        lblTipoIRPF = new javax.swing.JLabel();
        txtTipoIRPF = new javax.swing.JFormattedTextField();
        lblIdBanco = new javax.swing.JLabel();
        txtIdBanco = new javax.swing.JFormattedTextField();
        cboBancos = new javax.swing.JComboBox();
        lblIdSucursal = new javax.swing.JLabel();
        txtIdSucursal = new javax.swing.JFormattedTextField();
        cboSucursales = new javax.swing.JComboBox();
        lblDigitoControl = new javax.swing.JLabel();
        lblCuentaBancaria = new javax.swing.JLabel();
        lblCodigoINE = new javax.swing.JLabel();
        txtCodigoINE = new javax.swing.JTextField();
        lblOCM = new javax.swing.JLabel();
        chkOCM = new javax.swing.JCheckBox();
        txtCuentaBancaria = new javax.swing.JTextField();
        txtDigitoControl = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblParcelas = new javax.swing.JTable();
        txtSuperficieCultivada = new javax.swing.JFormattedTextField();
        txtSuperficie = new javax.swing.JFormattedTextField();
        cmdSelectAll = new javax.swing.JButton();
        cmdDeselectAll = new javax.swing.JButton();
        cmdDeleteLinea = new javax.swing.JButton();
        lblCodigoAsesoria = new javax.swing.JLabel();
        txtCodigoAsesoria = new javax.swing.JFormattedTextField();
        lblIBAN = new javax.swing.JLabel();
        txtIBAN = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnPago = new javax.swing.JButton();

        pnlData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TabCosechero.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        pnlData1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlData1.setName("pnlData1"); // NOI18N

        lblIdCosechero.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdCosechero.setForeground(new java.awt.Color(255, 0, 0));
        lblIdCosechero.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdCosechero.setText("Id cosechero");
        lblIdCosechero.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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
        lblDireccion.setText("Direcci�n");
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
        lblPoblacion.setText("Poblaci�n");
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
        lblCodigoPostal.setText("C�digo postal");
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

        lblFAX.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFAX.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFAX.setText("Fax");
        lblFAX.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtFAX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFAX.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFAX.setAutoscrolls(false);
        txtFAX.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtFAX.setName("fax"); // NOI18N
        txtFAX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFAXKeyTyped(evt);
            }
        });

        lblTelefono1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTelefono1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTelefono1.setText("Tel�fono 1");
        lblTelefono1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtTelefono1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTelefono1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTelefono1.setAutoscrolls(false);
        txtTelefono1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTelefono1.setName("telefono1"); // NOI18N
        txtTelefono1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono1KeyTyped(evt);
            }
        });

        lblTelefono2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTelefono2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTelefono2.setText("Tel�fono 2");
        lblTelefono2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtTelefono2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTelefono2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTelefono2.setAutoscrolls(false);
        txtTelefono2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTelefono2.setName("telefono2"); // NOI18N
        txtTelefono2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono2KeyTyped(evt);
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

        txtIdCosechero.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdCosechero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdCosechero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdCosechero.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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

        txtIdZona.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdZona.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdZona.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdZona.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        txtIdZona.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdZona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdZonaKeyReleased(evt);
            }
        });

        cboZonas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboZonas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboZonas.setName("Id subcategoria"); // NOI18N
        cboZonas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboZonasActionPerformed(evt);
            }
        });

        lblIdZona.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdZona.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdZona.setText("Zona");

        lblGrupoPladimisa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGrupoPladimisa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblGrupoPladimisa.setText("Grupo Pladimisa");

        chkGrupoPladimisa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        chkGrupoPladimisa.setMargin(new java.awt.Insets(0, 0, 2, 2));
        chkGrupoPladimisa.setName("privada"); // NOI18N

        lblIdGrupo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdGrupo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdGrupo.setText("Grupo");

        txtIdGrupo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdGrupo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdGrupo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdGrupo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdGrupo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdGrupoFocusLost(evt);
            }
        });

        lblNumKilosRef.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNumKilosRef.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumKilosRef.setText("Kilos de referencia");

        txtNumKilosReferencia.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumKilosReferencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtNumKilosReferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumKilosReferencia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEmail.setText("E-mail");
        lblEmail.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmail.setAutoscrolls(false);
        txtEmail.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtEmail.setName("telefono1"); // NOI18N
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlData1Layout = new javax.swing.GroupLayout(pnlData1);
        pnlData1.setLayout(pnlData1Layout);
        pnlData1Layout.setHorizontalGroup(
            pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblIdCosechero, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtIdCosechero, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(lblNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtNombre))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtApellidos))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlData1Layout.createSequentialGroup()
                                        .addGap(290, 290, 290)
                                        .addComponent(lblCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblFAX, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtFAX, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(lblTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlData1Layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(lblTelefono2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10)
                                .addComponent(txtTelefono2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblIdGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtIdGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlData1Layout.createSequentialGroup()
                                        .addComponent(lblIdZona, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtIdZona, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlData1Layout.createSequentialGroup()
                                        .addComponent(lblGrupoPladimisa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(chkGrupoPladimisa)))
                                .addGap(10, 10, 10)
                                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlData1Layout.createSequentialGroup()
                                        .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtEmail))
                                    .addComponent(cboZonas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblNumKilosRef, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumKilosReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        pnlData1Layout.setVerticalGroup(
            pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdCosechero)
                    .addComponent(txtIdCosechero, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNIF)
                    .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblApellidos)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccion)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPoblacion)
                    .addComponent(lblCodigoPostal)
                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFAX)
                    .addComponent(txtFAX, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefono1)
                    .addComponent(txtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefono2)
                    .addComponent(txtTelefono2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIdZona)
                            .addComponent(txtIdZona, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGrupoPladimisa)
                            .addComponent(chkGrupoPladimisa))
                        .addGap(11, 11, 11)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIdGrupo)
                            .addComponent(txtIdGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addComponent(cboZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumKilosReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumKilosRef))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        TabCosechero.addTab("1. Datos Generales", pnlData1);

        pnlData2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblCuentaContable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCuentaContable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCuentaContable.setText("Cuenta contable");
        lblCuentaContable.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCuentaContable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCuentaContable.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCuentaContable.setAutoscrolls(false);
        txtCuentaContable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCuentaContable.setName("telefono2"); // NOI18N
        txtCuentaContable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCuentaContableKeyTyped(evt);
            }
        });

        lblTipoIGIC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoIGIC.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoIGIC.setText("Tipo de IGIC");
        lblTipoIGIC.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtTipoIGIC.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTipoIGIC.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtTipoIGIC.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTipoIGIC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTipoIGIC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipoIGICFocusLost(evt);
            }
        });

        lblTipoIRPF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoIRPF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipoIRPF.setText("Tipo de IRPF");
        lblTipoIRPF.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtTipoIRPF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTipoIRPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtTipoIRPF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTipoIRPF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTipoIRPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipoIRPFFocusLost(evt);
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

        cboBancos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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

        cboSucursales.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        lblDigitoControl.setText("D�gito control");
        lblDigitoControl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblCuentaBancaria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCuentaBancaria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCuentaBancaria.setText("Cuenta bancaria");
        lblCuentaBancaria.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblCodigoINE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodigoINE.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoINE.setText("C�digo INE");
        lblCodigoINE.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCodigoINE.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodigoINE.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigoINE.setAutoscrolls(false);
        txtCodigoINE.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodigoINE.setName("codigoPostal"); // NOI18N
        txtCodigoINE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoINEKeyTyped(evt);
            }
        });

        lblOCM.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblOCM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblOCM.setText("O.C.M.");

        chkOCM.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        chkOCM.setMargin(new java.awt.Insets(0, 0, 2, 2));
        chkOCM.setName("privada"); // NOI18N

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

        txtDigitoControl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDigitoControl.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDigitoControl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDigitoControl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDigitoControlKeyTyped(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblParcelas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblParcelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblParcelas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblParcelas.setCellSelectionEnabled(true);
        tblParcelas.setRowHeight(18);
        tblParcelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblParcelasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblParcelas);

        txtSuperficieCultivada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.0000"))));
        txtSuperficieCultivada.setPreferredSize(new java.awt.Dimension(0, 0));

        txtSuperficie.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.0000"))));
        txtSuperficie.setPreferredSize(new java.awt.Dimension(0, 0));

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
        cmdDeselectAll.setText("Quitar selecci�n");
        cmdDeselectAll.setToolTipText("Quitar la seleccionar todas las filas");
        cmdDeselectAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeselectAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeselectAllMousePressed(evt);
            }
        });

        cmdDeleteLinea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdDeleteLinea.setText("Eliminar l�nea");
        cmdDeleteLinea.setToolTipText("Eliminar las l�neas seleccionados");
        cmdDeleteLinea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmdDeleteLinea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cmdDeleteLineaMousePressed(evt);
            }
        });

        lblCodigoAsesoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodigoAsesoria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigoAsesoria.setText("Cod. asesoria");
        lblCodigoAsesoria.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCodigoAsesoria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodigoAsesoria.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtCodigoAsesoria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigoAsesoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodigoAsesoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoAsesoriaFocusLost(evt);
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

        javax.swing.GroupLayout pnlData2Layout = new javax.swing.GroupLayout(pnlData2);
        pnlData2.setLayout(pnlData2Layout);
        pnlData2Layout.setHorizontalGroup(
            pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData2Layout.createSequentialGroup()
                .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlData2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE))
                    .addGroup(pnlData2Layout.createSequentialGroup()
                        .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlData2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlData2Layout.createSequentialGroup()
                                        .addComponent(lblCuentaContable, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtCuentaContable, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(lblTipoIGIC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtTipoIGIC, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(lblTipoIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtTipoIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlData2Layout.createSequentialGroup()
                                        .addComponent(lblIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(cboBancos, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlData2Layout.createSequentialGroup()
                                        .addComponent(lblIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(cboSucursales, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlData2Layout.createSequentialGroup()
                                        .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlData2Layout.createSequentialGroup()
                                                .addComponent(lblDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(txtDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(pnlData2Layout.createSequentialGroup()
                                                .addComponent(lblCodigoINE, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(txtCodigoINE, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlData2Layout.createSequentialGroup()
                                                .addComponent(lblCodigoAsesoria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(txtCodigoAsesoria, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(130, 130, 130)
                                                .addComponent(lblOCM, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(chkOCM))
                                            .addGroup(pnlData2Layout.createSequentialGroup()
                                                .addComponent(lblCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(txtCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(pnlData2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cmdSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdDeselectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdDeleteLinea, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 56, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData2Layout.createSequentialGroup()
                    .addGap(342, 342, 342)
                    .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlData2Layout.createSequentialGroup()
                            .addComponent(txtSuperficieCultivada, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData2Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(txtSuperficie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(343, Short.MAX_VALUE)))
        );
        pnlData2Layout.setVerticalGroup(
            pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCuentaContable)
                    .addComponent(txtCuentaContable, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipoIGIC)
                    .addComponent(txtTipoIGIC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipoIRPF)
                    .addComponent(txtTipoIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdBanco)
                    .addComponent(txtIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboBancos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdSucursal)
                    .addComponent(txtIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSucursales, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDigitoControl)
                    .addComponent(txtDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCuentaBancaria)
                    .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIBAN)
                            .addComponent(txtIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(8, 8, 8)
                .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOCM)
                    .addComponent(chkOCM)
                    .addComponent(lblCodigoINE)
                    .addComponent(txtCodigoINE, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigoAsesoria)
                    .addComponent(txtCodigoAsesoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSelectAll)
                    .addComponent(cmdDeselectAll)
                    .addComponent(cmdDeleteLinea))
                .addContainerGap())
            .addGroup(pnlData2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlData2Layout.createSequentialGroup()
                    .addGap(168, 168, 168)
                    .addComponent(txtSuperficieCultivada, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtSuperficie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(169, Short.MAX_VALUE)))
        );

        TabCosechero.addTab("2. M�s datos", pnlData2);

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

        btnPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/pagos.png"))); // NOI18N
        btnPago.setToolTipText("Pagar");
        btnPago.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPagoMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabCosechero)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addGap(9, 9, 9)
                .addComponent(btnOk)
                .addContainerGap())
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addComponent(TabCosechero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel)
                    .addComponent(btnOk)
                    .addComponent(btnPago))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
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
                Message.ShowRuntimeError(FrmCosechero.parentFrame,
                                "FrmCosechero.txtIBANKeyTyped()", he);
        }
    }//GEN-LAST:event_txtIBANKeyTyped

	private void btnPagoMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		if (frmPago != null) {
			frmPago.dispose();
		}
		frmPago = new FrmPagoLiquidacion(session, cosechero.getId()
				.getIdCosechero());
		frmPago.setLocationRelativeTo(null);
		frmPago.setVisible(true);
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void cmdDeleteLineaMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblParcelas.getRowCount(); actualrow++) {
				if ((Boolean) tblParcelas.getValueAt(actualrow,
						ParcelasTableModel.columnSelect) == true) {
					((DefaultTableModel) tblParcelas.getModel())
							.removeRow(actualrow);
					actualrow--;
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmCosechero.cmdDeleteLineaMousePressed()", he);
		}
	}

	private void cmdDeselectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblParcelas.getRowCount(); actualrow++)
				tblParcelas.setValueAt(false, actualrow,
						ParcelasTableModel.columnSelect);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmCosechero.cmdDeselectAllMousePressed()", he);
		}
	}

	private void cmdSelectAllMousePressed(java.awt.event.MouseEvent evt) {
		try {
			for (Integer actualrow = 0; actualrow < tblParcelas.getRowCount(); actualrow++)
				tblParcelas.setValueAt(true, actualrow,
						ParcelasTableModel.columnSelect);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmCosechero.cmdSelectAllMousePressed()", he);
		}
	}

	private void tblParcelasKeyPressed(java.awt.event.KeyEvent evt) {
		try {
			if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
				if ((tblParcelas.getSelectedRow() == ((DefaultTableModel) tblParcelas
						.getModel()).getRowCount() - 1)
						&& (tblParcelas.getValueAt(
								tblParcelas.getSelectedRow(),
								ParcelasTableModel.columnState)
								.equals(ParcelasTableModel.EditLine))) {
					if ((tblParcelas.getSelectedColumn() == tblParcelas
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
					tblParcelas.setValueAt("", tblParcelas.getSelectedRow(),
							tblParcelas.getSelectedColumn());
			}

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame,
					"FrmCosechero.tblParcelasKeyPressed()", he);
		}
	}

	private void cboZonasActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingZona) {
				changingZona = true;
				if (cboZonas.getSelectedIndex() != -1) {
					List<?> zonaslist = entity.ZonaFindByNombreZona(this,
							(String) cboZonas.getSelectedItem());

					if (zonaslist.size() > 0) {
						Zonas zonasItem = (Zonas) zonaslist.get(0);
						txtIdZona.setValue(zonasItem.getId().getIdZona());
					}
				}
				changingZona = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.cboZonasActionPerformed()", he);
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
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.cboSucursalesActionPerformed()", he);
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
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.cboBancosActionPerformed()", he);
		}
	}

	private void txtIdZonaKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeZona();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtIdZonaKeyReleased()", he);
		}
	}

	private void txtIdSucursalKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeSucursal();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtIdSucursalKeyReleased()", he);
		}
	}

	private void txtIdBancoKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeBanco();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtIdBancoKeyReleased()", he);
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
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.cosechero);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.btnCancelMousePressed()", he);
		}
	}

	private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtEmail, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtEmailKeyTypedKeyTyped()", he);
		}
	}

	private void txtCodigoAsesoriaFocusLost(java.awt.event.FocusEvent evt) {

	}

	private void txtIdGrupoFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtIdGrupo);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtIdGrupoFocusLost()", he);
		}
	}

	private void txtCodigoINEKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCodigoINE, evt, 7);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtCodigoINEKeyTyped()", he);
		}
	}

	private void txtCuentaBancariaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaBancaria, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtCuentaBancariaKeyTyped()", he);
		}
	}

	private void txtDigitoControlKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDigitoControl, evt, 2);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtDigitoControlKeyTyped()", he);
		}
	}

	private void txtIdSucursalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdSucursal, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtIdSucursalKeyTyped()", he);
		}
	}

	private void txtIdBancoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdBanco, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtIdBancoKeyTyped()", he);
		}
	}

	private void txtTipoIRPFFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtTipoIRPF);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtTipoIRPFFocusLost()", he);
		}
	}

	private void txtTipoIGICFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtTipoIGIC);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtTipoIGICFocusLost()", he);
		}
	}

	private void txtCuentaContableKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaContable, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtCuentaContableKeyTyped()", he);
		}
	}

	private void txtTelefono2KeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtTelefono2, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtTelefono2KeyTyped()", he);
		}
	}

	private void txtTelefono1KeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtTelefono1, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtTelefono1KeyTyped()", he);
		}
	}

	private void txtFAXKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtFAX, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtFAXKeyTyped()", he);
		}
	}

	private void txtCodigoPostalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCodigoPostal, evt, 6);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtCodigoPostalKeyTyped()", he);
		}
	}

	private void txtPoblacionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtPoblacion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtPoblacionKeyTyped()", he);
		}
	}

	private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDireccion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtDireccionKeyTyped()", he);
		}
	}

	private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtApellidos, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtApellidosKeyTyped()", he);
		}
	}

	private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNombre, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtNombreKeyTyped()", he);
		}
	}

	private void txtNIFKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNIF, evt, 12);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(FrmCosechero.parentFrame,
					"FrmCosechero.txtNIFKeyTyped()", he);
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabCosechero;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnPago;
    private static javax.swing.JComboBox cboBancos;
    private static javax.swing.JComboBox cboSucursales;
    private static javax.swing.JComboBox cboZonas;
    private javax.swing.JCheckBox chkGrupoPladimisa;
    private javax.swing.JCheckBox chkOCM;
    private javax.swing.JButton cmdDeleteLinea;
    private javax.swing.JButton cmdDeselectAll;
    private javax.swing.JButton cmdSelectAll;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblCodigoAsesoria;
    private javax.swing.JLabel lblCodigoINE;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblCuentaBancaria;
    private javax.swing.JLabel lblCuentaContable;
    private javax.swing.JLabel lblDigitoControl;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFAX;
    private javax.swing.JLabel lblGrupoPladimisa;
    private javax.swing.JLabel lblIBAN;
    private javax.swing.JLabel lblIdBanco;
    private javax.swing.JLabel lblIdCosechero;
    private javax.swing.JLabel lblIdGrupo;
    private javax.swing.JLabel lblIdSucursal;
    private javax.swing.JLabel lblIdZona;
    private javax.swing.JLabel lblNIF;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumKilosRef;
    private javax.swing.JLabel lblOCM;
    private javax.swing.JLabel lblPoblacion;
    private javax.swing.JLabel lblTelefono1;
    private javax.swing.JLabel lblTelefono2;
    private javax.swing.JLabel lblTipoIGIC;
    private javax.swing.JLabel lblTipoIRPF;
    private javax.swing.JPanel pnlData;
    public javax.swing.JPanel pnlData1;
    private javax.swing.JPanel pnlData2;
    private static javax.swing.JTable tblParcelas;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JFormattedTextField txtCodigoAsesoria;
    private javax.swing.JTextField txtCodigoINE;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtCuentaBancaria;
    private javax.swing.JTextField txtCuentaContable;
    private javax.swing.JFormattedTextField txtDigitoControl;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFAX;
    private javax.swing.JTextField txtIBAN;
    private static javax.swing.JFormattedTextField txtIdBanco;
    private javax.swing.JFormattedTextField txtIdCosechero;
    private static javax.swing.JFormattedTextField txtIdGrupo;
    private static javax.swing.JFormattedTextField txtIdSucursal;
    private static javax.swing.JFormattedTextField txtIdZona;
    private javax.swing.JTextField txtNIF;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JFormattedTextField txtNumKilosReferencia;
    private javax.swing.JTextField txtPoblacion;
    private static javax.swing.JFormattedTextField txtSuperficie;
    private static javax.swing.JFormattedTextField txtSuperficieCultivada;
    private javax.swing.JTextField txtTelefono1;
    private javax.swing.JTextField txtTelefono2;
    private javax.swing.JFormattedTextField txtTipoIGIC;
    private javax.swing.JFormattedTextField txtTipoIRPF;
    // End of variables declaration//GEN-END:variables

}
