/*
 * FrmBanco.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.AWTKeyStroke;
import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.ReplicationMode;
import org.hibernate.Transaction;

import sessionPackage.MySession;

import entitiesPackage.Bancos;
import entitiesPackage.Identidades;
import entitiesPackage.IdentidadesId;
import entitiesPackage.Entity;
import entitiesPackage.Message;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmIdentidad extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	Identidades identidad;

	private static boolean changingBanco = false;
	private static boolean changingSucursal = false;

	private java.awt.Frame parentFrame;
	private MySession session;
	private boolean OnNew = false;
	private Entity entity = new Entity();
	
	private FrmCobro frmCobro;
	private FrmPago frmPago;

	public java.awt.Frame getParentFrame() {
		return this.parentFrame;
	}

	public void setParentFrame(java.awt.Frame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public void setSession(MySession session) {
		this.session = session;
	}

	public MySession getSession() {
		return session;
	}

	/** Creates new form FrmBanco */
	public FrmIdentidad() {
		initComponents();
	}

	public FrmIdentidad(java.awt.Frame parent, MySession session) {
		try {
			initComponents();
			this.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent, "FrmIdentidad()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmIdentidad.configureKeys",
					he);
		}
	}

	public void newData() {
		try {
			this.identidad = new Identidades();
			loadBancos();
			loadSucursales("");
			txtIdentidad.setText("");
			txtNIF.setText("");
			txtNombreIdentidad.setText("");
			txtDireccion.setText("");
			txtPoblacion.setText("");
			txtCodigoPostal.setText("");
			txtProvincia.setText("");
			txtTelefono.setText("");
			txtCuentaContable.setText("");
			txtIdBanco.setText("");
			cboBancos.setSelectedItem(null);
			txtIdSucursal.setText("");
			cboSucursales.setSelectedItem(null);
			txtDigitoControl.setText("");
			txtCuentaBancaria.setText("");
                        txtIBAN.setText("");
			chkCliente.setText("");
			chkProveedor.setText("");
			txtTipoIRPF.setText("");
			txtTipoIGIC.setText("");
			btnPago.setEnabled(false);
			btnCobro.setEnabled(false);
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.newData()", he);
		}
	}

	public void loadData(Identidades entity) {
		try {
			this.identidad = entity;
			loadBancos();
			loadSucursales(entity.getIdBanco());
			txtIdentidad.setValue(entity.getId().getIdentidad());
			txtNIF.setText(entity.getNif());
			txtNombreIdentidad.setText(entity.getNombreIdentidad());
			txtDireccion.setText(entity.getDireccion());
			txtPoblacion.setText(entity.getPoblacion());
			txtProvincia.setText(entity.getProvincia());
			txtCodigoPostal.setText(entity.getCodigoPostal());
			txtTelefono.setText(entity.getTelefono());
			txtCuentaContable.setText(entity.getCuentaContable());
			txtIdBanco.setText(entity.getIdBanco());
			changeBanco();
			txtIdSucursal.setText(entity.getIdSucursal());
			changeSucursal();
			txtDigitoControl.setValue(entity.getDigitoControl());
			txtCuentaBancaria.setText(entity.getCuentaBancaria());
                        txtIBAN.setText(entity.getIban());
			chkCliente.setSelected(entity.getCliente() != 0);
			chkProveedor.setSelected(entity.getProveedor() != 0);
			txtTipoIRPF.setValue(entity.getTipoIrpf());
			txtTipoIGIC.setValue(entity.getTipoImpuesto());
			btnCobro.setEnabled(entity.getCliente() != 0);
			btnPago.setEnabled(entity.getProveedor() != 0);
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.loadData()", he);
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
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.loadBancos()", he);
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
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.loadSucursales()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {
		try {
			if (validateData()) {

				Transaction transaction = session.getSession()
						.beginTransaction();

				if (OnNew) {
					txtIdentidad.setValue(entity.newId(this, "Identidades",
							"id.identidad"));
					IdentidadesId identidadid = new IdentidadesId();
					identidadid.setEmpresas(session.getEmpresa());
					identidadid.setEjercicios(session.getEjercicio());
					identidadid.setIdentidad((Integer) txtIdentidad.getValue());
					identidad.setId(identidadid);
				}
				if (!txtNIF.getText().equals(""))
					identidad.setNif(txtNIF.getText());
				else
					identidad.setNif(null);
				if (!txtNombreIdentidad.getText().equals(""))
					identidad.setNombreIdentidad(txtNombreIdentidad.getText());
				else
					identidad.setNombreIdentidad(null);
				if (!txtDireccion.getText().equals(""))
					identidad.setDireccion(txtDireccion.getText());
				else
					identidad.setDireccion(null);
				if (!txtPoblacion.getText().equals(""))
					identidad.setPoblacion(txtPoblacion.getText());
				else
					identidad.setPoblacion(null);
				if (!txtProvincia.getText().equals(""))
					identidad.setProvincia(txtProvincia.getText());
				else
					identidad.setProvincia(null);
				if (!txtCodigoPostal.getText().equals(""))
					identidad.setCodigoPostal(txtCodigoPostal.getText());
				else
					identidad.setCodigoPostal(null);
				if (!txtTelefono.getText().equals(""))
					identidad.setTelefono(txtTelefono.getText());
				else
					identidad.setTelefono(null);
				if (!txtCuentaContable.getText().equals(""))
					identidad.setCuentaContable(txtCuentaContable.getText());
				else
					identidad.setCuentaContable(null);
				if (!txtIdBanco.getText().equals(""))
					identidad.setIdBanco(txtIdBanco.getText());
				else
					identidad.setIdBanco(null);
				if (!txtIdSucursal.getText().equals(""))
					identidad.setIdSucursal(txtIdSucursal.getText());
				else
					identidad.setIdSucursal(null);
				if (!txtDigitoControl.getText().equals(""))
					identidad.setDigitoControl(txtDigitoControl.getText());
				else
					identidad.setDigitoControl(null);
				if (!txtCuentaBancaria.getText().equals(""))
					identidad.setCuentaBancaria(txtCuentaBancaria.getText());
				else
					identidad.setCuentaBancaria(null);
                                if (!txtIBAN.getText().equals(""))
					identidad.setIban(txtIBAN.getText());
				else
					identidad.setIban(null);
				if (chkCliente.isSelected())
					identidad.setCliente(((Number) 1).shortValue());
				else
					identidad.setCliente(((Number) 0).shortValue());
				if (chkProveedor.isSelected())
					identidad.setProveedor(((Number) 1).shortValue());
				else
					identidad.setProveedor(((Number) 0).shortValue());
				if (!txtTipoIGIC.getText().equals(""))
					identidad.setTipoImpuesto(((Number) txtTipoIGIC.getValue())
							.intValue());
				else
					identidad.setTipoImpuesto(0);
				if (!txtTipoIRPF.getText().equals(""))
					identidad.setTipoIrpf(((Number) txtTipoIRPF.getValue())
							.intValue());
				else
					identidad.setTipoIrpf(0);
				identidad.setLmd(new Date());
				identidad.setSid("Santi");
				identidad.setVersion(1);
				session.getSession().replicate(identidad,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(identidad);
				session.getSession().flush();
				if (transaction.isActive()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				OnNew = false;
				TabCosechero.setSelectedIndex(0);
				FrmIdentidades.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.saveData()", he);
			return false;
		}
	}

	private boolean validateData() {

		try {
			if (txtNombreIdentidad.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un nombre para el acreedor o proveedor.");
				TabCosechero.setSelectedIndex(0);
				txtNombreIdentidad.requestFocus();
				return (false);
			}

			if (!txtTipoIGIC.getText().equals("")) {
				try {
					txtTipoIGIC.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtTipoIGIC.requestFocus();
					return (false);
				}
			}
			if (!txtTipoIRPF.getText().equals("")) {
				try {
					txtTipoIRPF.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtTipoIRPF.requestFocus();
					return (false);
				}
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.valiadateData()", he);
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
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.changeBanco()", he);
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
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.changeSucursal()", he);
		}
	}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlData = new javax.swing.JPanel();
        TabCosechero = new javax.swing.JTabbedPane();
        pnlData1 = new javax.swing.JPanel();
        lblIdentidad = new javax.swing.JLabel();
        lblNombreIdentidad = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtNombreIdentidad = new javax.swing.JTextField();
        lblPoblacion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblCodigoPostal = new javax.swing.JLabel();
        txtCodigoPostal = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblNIF = new javax.swing.JLabel();
        txtNIF = new javax.swing.JTextField();
        txtIdentidad = new javax.swing.JFormattedTextField();
        txtPoblacion = new javax.swing.JTextField();
        lblCuentaContable = new javax.swing.JLabel();
        txtCuentaContable = new javax.swing.JTextField();
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
        lblCliente = new javax.swing.JLabel();
        chkCliente = new javax.swing.JCheckBox();
        lblProveedor = new javax.swing.JLabel();
        chkProveedor = new javax.swing.JCheckBox();
        lblProvincia = new javax.swing.JLabel();
        txtProvincia = new javax.swing.JTextField();
        lblTipoIGIC = new javax.swing.JLabel();
        txtTipoIGIC = new javax.swing.JFormattedTextField();
        lblTipoIRPF = new javax.swing.JLabel();
        txtTipoIRPF = new javax.swing.JFormattedTextField();
        lblIBAN = new javax.swing.JLabel();
        txtIBAN = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnCobro = new javax.swing.JButton();
        btnPago = new javax.swing.JButton();

        pnlData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TabCosechero.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        pnlData1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlData1.setName("pnlData1"); // NOI18N

        lblIdentidad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdentidad.setForeground(new java.awt.Color(255, 0, 0));
        lblIdentidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdentidad.setText("Id");
        lblIdentidad.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblNombreIdentidad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombreIdentidad.setForeground(new java.awt.Color(255, 0, 0));
        lblNombreIdentidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreIdentidad.setText("Nombre");

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDireccion.setText("Dirección");
        lblDireccion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNombreIdentidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNombreIdentidad.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreIdentidad.setAutoscrolls(false);
        txtNombreIdentidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombreIdentidad.setName("nombre"); // NOI18N
        txtNombreIdentidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreIdentidadKeyTyped(evt);
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

        txtIdentidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdentidad.setEditable(false);
        txtIdentidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdentidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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

        lblCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCliente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCliente.setText("Cliente");

        chkCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        chkCliente.setMargin(new java.awt.Insets(0, 0, 2, 2));
        chkCliente.setName("privada"); // NOI18N

        lblProveedor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProveedor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblProveedor.setText("Proveedor");

        chkProveedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        chkProveedor.setMargin(new java.awt.Insets(0, 0, 2, 2));
        chkProveedor.setName("privada"); // NOI18N

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
                .addGap(18, 18, 18)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addComponent(lblIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtIdBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cboBancos, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addComponent(lblIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cboSucursales, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addComponent(lblProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addComponent(lblNombreIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtNombreIdentidad))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlData1Layout.createSequentialGroup()
                        .addComponent(lblIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkCliente)
                        .addGap(37, 37, 37)
                        .addComponent(lblProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkProveedor)
                        .addGap(5, 5, 5))
                    .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                            .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlData1Layout.createSequentialGroup()
                            .addComponent(lblCuentaContable, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(txtCuentaContable, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlData1Layout.createSequentialGroup()
                            .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlData1Layout.createSequentialGroup()
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblTipoIGIC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtTipoIGIC, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(lblTipoIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtTipoIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlData1Layout.createSequentialGroup()
                                .addComponent(lblDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtDigitoControl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(lblCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        pnlData1Layout.setVerticalGroup(
            pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlData1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdentidad)
                    .addComponent(txtIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNIF)
                    .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(chkCliente)
                        .addComponent(lblCliente))
                    .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(chkProveedor)
                        .addComponent(lblProveedor)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreIdentidad)
                    .addComponent(txtNombreIdentidad, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccion)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPoblacion)
                    .addComponent(lblCodigoPostal)
                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProvincia)
                    .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCuentaContable)
                    .addComponent(txtCuentaContable, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
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
                        .addComponent(txtCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIBAN)
                            .addComponent(txtIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlData1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTipoIGIC)
                    .addComponent(txtTipoIGIC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipoIRPF)
                    .addComponent(txtTipoIRPF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );

        TabCosechero.addTab("1. Datos Generales", pnlData1);

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

        btnCobro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/cobros.png"))); // NOI18N
        btnCobro.setToolTipText("Cobrar");
        btnCobro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCobro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCobroMousePressed(evt);
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
                .addComponent(btnCobro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(btnPago)
                    .addComponent(btnCobro))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 659, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
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
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidadtxtIBANKeyTyped()", he);
		}
    }//GEN-LAST:event_txtIBANKeyTyped

	private void btnPagoMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		if (frmPago != null) {
			frmPago.dispose();
		}
		frmPago = new FrmPago(session, identidad.getId().getIdentidad());
		frmPago.setLocationRelativeTo(null);
		frmPago.setVisible(true);
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void btnCobroMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		if (frmCobro != null) {
			frmCobro.dispose();
		}
		frmCobro = new FrmCobro(session, identidad.getId().getIdentidad());
		frmCobro.setLocationRelativeTo(null);
		frmCobro.setVisible(true);
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void txtTipoIRPFFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtTipoIRPF);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCosechero.txtTipoIRPFFocusLost()", he);
		}
	}

	private void txtTipoIGICFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtTipoIGIC);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCosechero.txtTipoIGICFocusLost()", he);
		}
	}

	private void txtProvinciaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtProvincia, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtProvinciaKeyTyped()", he);
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
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.cboSucursalesActionPerformed()", he);
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
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.cboBancosActionPerformed()", he);
		}
	}

	private void txtIdSucursalKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeSucursal();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtIdSucursalKeyReleased()", he);
		}
	}

	private void txtIdBancoKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeBanco();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtIdBancoKeyReleased()", he);
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
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.identidad);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.btnCancelMousePressed()", he);
		}
	}

	private void txtCuentaBancariaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaBancaria, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidadtxtCuentaBancariaKeyTyped()", he);
		}
	}

	private void txtDigitoControlKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDigitoControl, evt, 2);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtDigitoControlKeyTyped()", he);
		}
	}

	private void txtIdSucursalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdSucursal, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtIdSucursalKeyTyped()", he);
		}
	}

	private void txtIdBancoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtIdBanco, evt, 4);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtIdBancoKeyTyped()", he);
		}
	}

	private void txtCuentaContableKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCuentaContable, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtCuentaContableKeyTyped()", he);
		}
	}

	private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtTelefono, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtTelefonoKeyTyped()", he);
		}
	}

	private void txtCodigoPostalKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCodigoPostal, evt, 6);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtCodigoPostalKeyTyped()", he);
		}
	}

	private void txtPoblacionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtPoblacion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtPoblacionKeyTyped()", he);
		}
	}

	private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtDireccion, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtDireccionKeyTyped()", he);
		}
	}

	private void txtNombreIdentidadKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNombreIdentidad, evt, 100);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtNombreIdentidadKeyTyped()", he);
		}
	}

	private void txtNIFKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNIF, evt, 12);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmIdentidad.txtNIFKeyTyped()", he);
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabCosechero;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCobro;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnPago;
    private static javax.swing.JComboBox cboBancos;
    private static javax.swing.JComboBox cboSucursales;
    private javax.swing.JCheckBox chkCliente;
    private javax.swing.JCheckBox chkProveedor;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblCuentaBancaria;
    private javax.swing.JLabel lblCuentaContable;
    private javax.swing.JLabel lblDigitoControl;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblIBAN;
    private javax.swing.JLabel lblIdBanco;
    private javax.swing.JLabel lblIdSucursal;
    private javax.swing.JLabel lblIdentidad;
    private javax.swing.JLabel lblNIF;
    private javax.swing.JLabel lblNombreIdentidad;
    private javax.swing.JLabel lblPoblacion;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblProvincia;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTipoIGIC;
    private javax.swing.JLabel lblTipoIRPF;
    private javax.swing.JPanel pnlData;
    public javax.swing.JPanel pnlData1;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtCuentaBancaria;
    private javax.swing.JTextField txtCuentaContable;
    private javax.swing.JFormattedTextField txtDigitoControl;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtIBAN;
    private static javax.swing.JFormattedTextField txtIdBanco;
    private static javax.swing.JFormattedTextField txtIdSucursal;
    private javax.swing.JFormattedTextField txtIdentidad;
    private javax.swing.JTextField txtNIF;
    private javax.swing.JTextField txtNombreIdentidad;
    private javax.swing.JTextField txtPoblacion;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JFormattedTextField txtTipoIGIC;
    private javax.swing.JFormattedTextField txtTipoIRPF;
    // End of variables declaration//GEN-END:variables

}

