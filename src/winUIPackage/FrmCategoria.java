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

import entitiesPackage.Categorias;
import entitiesPackage.CategoriasId;
import entitiesPackage.Entity;
import entitiesPackage.Message;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmCategoria extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	Categorias categoria;

	static boolean changingSubcategoria = false;

	private java.awt.Frame parentFrame;
	private MySession session;
	private boolean OnNew = false;
	private Entity entity = new Entity();

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
	public FrmCategoria() {
		initComponents();
	}

	public FrmCategoria(java.awt.Frame parent, MySession session) {

		try {
			initComponents();
			this.parentFrame = parent;
			this.setSession(session);
			entity.setSession(session);
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parentFrame, "FrmCategoria()", he);
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
			Message.ShowRuntimeError(parentFrame, "FrmCategoria.configureKeys",
					he);
		}
	}

	public void newData() {

		try {
			loadSubcategoria();
			this.categoria = new Categorias();
			txtIdCategoria.setText("");
			txtIdCategoria.setEditable(true);
			txtNombreCategoria.setText("");
			txtNumKilosCaja.setText("");
			txtIdSubcategoria.setText("");
			cboSubcategoria.setSelectedItem(null);
			chkPrivada.setSelected(false);
			chkActiva.setSelected(true);
			txtOrden.setText("");
			txtCodCategoriaAgriten.setText("");
			txtIdGrupo.setText("");
            chkRetorno.setSelected(false);
			OnNew = true;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.newData()", he);
		}

	}

	public void loadData(Categorias entity) {

		try {
			loadSubcategoria();
			this.categoria = entity;
			txtIdCategoria.setValue(entity.getId().getIdCategoria());
			txtIdCategoria.setEditable(false);
			txtNombreCategoria.setText(entity.getNombreCategoria());
			txtNumKilosCaja.setValue(entity.getNumKilosCaja());
			txtIdSubcategoria.setValue(entity.getIdSubcategoria());
			chkPrivada.setSelected(entity.getPrivada() != 0);
			chkActiva.setSelected(entity.getActiva() != 0);
			txtOrden.setValue(entity.getOrden());
			txtCodCategoriaAgriten.setText(entity.getCodCategoriaAgriten());
            chkRetorno.setSelected(entity.getRetorno() != 0);
            txtIdGrupo.setValue(entity.getIdGrupo());
			changeSubcategoria();
			OnNew = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.loadData()", he);
		}

	}

	private void loadSubcategoria() {

		try {
			cboSubcategoria.removeAllItems();
			List<?> categoriasList = entity.CategoriaFindAll(getParentFrame(),
					false);

			for (Object o : categoriasList) {
				Categorias categoria = (Categorias) o;
				cboSubcategoria.addItem(categoria.getNombreCategoria());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.loadSubcategoria()", he);
		}
	}

	public boolean saveData(Boolean showmessage) {

		try {
			if (validateData()) {

				Transaction transaction = session.getSession()
						.beginTransaction();
				if (OnNew) {
					if (txtIdCategoria.getText().equals("")) {
						txtIdCategoria.setValue(entity.newId(this,
								"Categorias", "id.idCategoria"));
					}
					CategoriasId categoriaid = new CategoriasId();
					categoriaid.setIdCategoria(((Number) txtIdCategoria
							.getValue()).intValue());
					categoriaid.setEmpresas(session.getEmpresa());
					categoriaid.setEjercicios(session.getEjercicio());
					categoria.setId(categoriaid);
				}
				if (!txtNombreCategoria.getText().equals(""))
					categoria.setNombreCategoria(txtNombreCategoria.getText());
				else
					categoria.setNombreCategoria(null);
				if (!txtNumKilosCaja.getText().equals(""))
					categoria.setNumKilosCaja(((Number) txtNumKilosCaja
							.getValue()).floatValue());
				else
					categoria.setNumKilosCaja(null);
				if (!txtIdSubcategoria.getText().equals(""))
					categoria.setIdSubcategoria(((Number) txtIdSubcategoria
							.getValue()).intValue());
				else
					categoria.setIdSubcategoria(null);
				if (chkPrivada.isSelected())
					categoria.setPrivada(((Number) 1).shortValue());
				else
					categoria.setPrivada(((Number) 0).shortValue());
				if (!txtOrden.getText().equals(""))
					categoria.setOrden(((Number) txtOrden.getValue())
							.intValue());
				else
					categoria.setOrden(null);
				if (!txtCodCategoriaAgriten.getText().equals(""))
					categoria.setCodCategoriaAgriten(txtCodCategoriaAgriten
							.getText());
				else
					categoria.setCodCategoriaAgriten(null);
				if (chkActiva.isSelected())
					categoria.setActiva(((Number) 1).shortValue());
				else
					categoria.setActiva(((Number) 0).shortValue());
				categoria.setLmd(new Date());
				categoria.setSid("Santi");
				categoria.setVersion(1);
                                if (chkRetorno.isSelected())
					categoria.setRetorno(((Number) 1).shortValue());
				else
					categoria.setRetorno(((Number) 0).shortValue());
                if (!txtIdGrupo.getText().equals(""))
					categoria.setIdGrupo(((Number) txtIdGrupo.getValue())
							.intValue());
				else
					categoria.setIdGrupo(null);
				session.getSession().replicate(categoria,
						ReplicationMode.OVERWRITE);
				session.getSession().saveOrUpdate(categoria);
				session.getSession().flush();
				if (!transaction.wasCommitted()) {
					transaction.commit();
				}
				session.getSession().close();
				if (showmessage)
					Message.ShowSaveSuccesfull(pnlData);
				OnNew = false;
				FrmCategorias.runSearchQuery();
				return true;
			} else
				return false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.saveData()", he);
			return false;
		}
	}

	private boolean validateData() {

		try {
			if (!(txtIdCategoria.getText().equals("")) && OnNew) {
				try {
					txtIdCategoria.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdCategoria.requestFocus();
					return (false);
				}
				if (entity.CategoriaFindByIdCategoria(this,
						((Number) txtIdCategoria.getValue()).intValue()) != null) {
					Message.ShowValidateMessage(pnlData,
							"El código de categoría especificado ya existe.");
					txtIdCategoria.requestFocus();
					return (false);
				}
			}
			if (txtNombreCategoria.getText().equals("")) {
				Message.ShowValidateMessage(pnlData,
						"Debe indicar un nombre para la categoría.");
				txtNombreCategoria.requestFocus();
				return (false);
			}

			if (!txtNumKilosCaja.getText().equals("")) {
				try {
					txtNumKilosCaja.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtNumKilosCaja.requestFocus();
					return (false);
				}
			}
			if (!txtIdSubcategoria.getText().equals("")) {
				try {
					txtIdSubcategoria.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdSubcategoria.requestFocus();
					return (false);
				}
			}
			if (!txtOrden.getText().equals("")) {
				try {
					txtOrden.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtOrden.requestFocus();
					return (false);
				}
			}
			if (!txtIdGrupo.getText().equals("")) {
				try {
					txtIdGrupo.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(pnlData,
							"El tipo de datos indicado no es válido.");
					txtIdGrupo.requestFocus();
					return (false);
				}
			}

			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.validateData()", he);
			return (false);
		}

	}

	private void changeSubcategoria() {

		try {
			if (!changingSubcategoria) {
				changingSubcategoria = true;
				if (txtIdSubcategoria.getText().equals(""))
					cboSubcategoria.setSelectedItem(null);
				else {
					try {
						Integer value = Integer.parseInt(txtIdSubcategoria
								.getText());
						Categorias categoria = entity
								.CategoriaFindByIdCategoria(this, value);
						if (categoria != null)
							cboSubcategoria.setSelectedItem(categoria
									.getNombreCategoria());
						else {
							cboSubcategoria.setSelectedItem(null);
						}
					} catch (NumberFormatException nfe) {
						cboSubcategoria.setSelectedItem(null);
					}
				}
				changingSubcategoria = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.changeSubcategoria()", he);
		}
	}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlData = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblIdCategoria = new javax.swing.JLabel();
        lblNombreCategoria = new javax.swing.JLabel();
        txtNombreCategoria = new javax.swing.JTextField();
        lblNumKilosCaja = new javax.swing.JLabel();
        lblIdSubcategoria = new javax.swing.JLabel();
        cboSubcategoria = new javax.swing.JComboBox();
        lblPrivada = new javax.swing.JLabel();
        chkPrivada = new javax.swing.JCheckBox();
        lblOrden = new javax.swing.JLabel();
        txtOrden = new javax.swing.JFormattedTextField();
        txtNumKilosCaja = new javax.swing.JFormattedTextField();
        txtIdSubcategoria = new javax.swing.JFormattedTextField();
        txtIdCategoria = new javax.swing.JFormattedTextField();
        lblCodAgriten = new javax.swing.JLabel();
        txtCodCategoriaAgriten = new javax.swing.JTextField();
        lblActiva = new javax.swing.JLabel();
        chkActiva = new javax.swing.JCheckBox();
        lblRetorno = new javax.swing.JLabel();
        chkRetorno = new javax.swing.JCheckBox();
        lblIdGrupo = new javax.swing.JLabel();
        txtIdGrupo = new javax.swing.JFormattedTextField();

        pnlData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlData.setName("pnlData"); // NOI18N

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

        lblIdCategoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdCategoria.setForeground(new java.awt.Color(255, 0, 0));
        lblIdCategoria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdCategoria.setText("Id categoría");

        lblNombreCategoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombreCategoria.setForeground(new java.awt.Color(255, 0, 0));
        lblNombreCategoria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreCategoria.setText("Descripción");

        txtNombreCategoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNombreCategoria.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreCategoria.setAutoscrolls(false);
        txtNombreCategoria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombreCategoria.setName("nombreCategoria"); // NOI18N
        txtNombreCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCategoriaKeyTyped(evt);
            }
        });

        lblNumKilosCaja.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNumKilosCaja.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumKilosCaja.setText("Nº kilos caja");

        lblIdSubcategoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdSubcategoria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdSubcategoria.setText("Subcategoría");

        cboSubcategoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboSubcategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSubcategoria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboSubcategoria.setName("Id subcategoria"); // NOI18N
        cboSubcategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSubcategoriaActionPerformed(evt);
            }
        });

        lblPrivada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPrivada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPrivada.setText("Privada");

        chkPrivada.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        chkPrivada.setMargin(new java.awt.Insets(0, 0, 2, 2));
        chkPrivada.setName("privada"); // NOI18N

        lblOrden.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblOrden.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblOrden.setText("Orden");

        txtOrden.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtOrden.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtOrden.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtOrden.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtOrden.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOrdenFocusLost(evt);
            }
        });

        txtNumKilosCaja.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumKilosCaja.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtNumKilosCaja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumKilosCaja.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNumKilosCaja.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumKilosCajaFocusLost(evt);
            }
        });

        txtIdSubcategoria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdSubcategoria.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdSubcategoria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdSubcategoria.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        txtIdSubcategoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdSubcategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdSubcategoriaKeyReleased(evt);
            }
        });

        txtIdCategoria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdCategoria.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdCategoria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdCategoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblCodAgriten.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodAgriten.setForeground(new java.awt.Color(255, 0, 0));
        lblCodAgriten.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodAgriten.setText("Cod. Agriten");

        lblIdGrupo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdGrupo.setForeground(new java.awt.Color(255, 0, 0));
        lblIdGrupo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIdGrupo.setText("Id. Grupo");

        txtCodCategoriaAgriten.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodCategoriaAgriten.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodCategoriaAgriten.setAutoscrolls(false);
        txtCodCategoriaAgriten.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodCategoriaAgriten.setName("nombreCategoria"); // NOI18N
        txtCodCategoriaAgriten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodCategoriaAgritenKeyTyped(evt);
            }
        });
        
        txtIdGrupo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdGrupo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdGrupo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdGrupo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdGrupo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdGrupoFocusLost(evt);
            }
        });


        lblActiva.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblActiva.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblActiva.setText("Activa");

        chkActiva.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        chkActiva.setMargin(new java.awt.Insets(0, 0, 2, 2));
        chkActiva.setName("privada"); // NOI18N

        lblRetorno.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRetorno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRetorno.setText("Retorno");

        chkRetorno.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        chkRetorno.setMargin(new java.awt.Insets(0, 0, 2, 2));
        chkRetorno.setName("privada"); // NOI18N

        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addComponent(lblRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(chkRetorno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOk))
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDataLayout.createSequentialGroup()
                                .addComponent(lblActiva, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(chkActiva))
                            .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDataLayout.createSequentialGroup()
                                    .addComponent(lblCodAgriten, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtCodCategoriaAgriten, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(368, 368, 368))
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addComponent(lblIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addComponent(lblNombreCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtNombreCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addComponent(lblNumKilosCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtNumKilosCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addComponent(lblIdSubcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtIdSubcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cboSubcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addComponent(lblPrivada, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(chkPrivada))
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addComponent(lblOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDataLayout.createSequentialGroup()
                                    .addComponent(lblIdGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(txtIdGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 112, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlDataLayout.setVerticalGroup(
            pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdCategoria)
                    .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreCategoria)
                    .addComponent(txtNombreCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumKilosCaja)
                    .addComponent(txtNumKilosCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIdSubcategoria)
                            .addComponent(txtIdSubcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPrivada)
                            .addComponent(chkPrivada))
                        .addGap(11, 11, 11)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOrden)
                            .addComponent(txtOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cboSubcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodAgriten)
                    .addComponent(txtCodCategoriaAgriten, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdGrupo)
                    .addComponent(txtIdGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGap(0, 22, Short.MAX_VALUE)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnOk)
                            .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(pnlDataLayout.createSequentialGroup()
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblActiva)
                            .addComponent(chkActiva))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRetorno)
                            .addComponent(chkRetorno))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	private void cboSubcategoriaActionPerformed(java.awt.event.ActionEvent evt) {

		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())
					&& !changingSubcategoria) {
				changingSubcategoria = true;
				if (cboSubcategoria.getSelectedIndex() != -1) {
					;
					List<?> categoriaslist = entity
							.CategoriaFindByNombreCategoria(this,
									(String) cboSubcategoria.getSelectedItem());
					if (categoriaslist.size() > 0) {
						Categorias categoriasItem = (Categorias) categoriaslist
								.get(0);
						txtIdSubcategoria.setValue(categoriasItem.getId()
								.getIdCategoria());
					}
				}
				changingSubcategoria = false;
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.cboSubcategoriaActionPerformed()", he);
		}
	}

	private void txtIdSubcategoriaKeyReleased(java.awt.event.KeyEvent evt) {
		try {
			changeSubcategoria();
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.txtIdSubcategoriaKeyReleased()", he);
		}
	}

	private void txtOrdenFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtOrden);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.txtOrdenFocusLost()", he);
		}
	}
	
	private void txtIdGrupoFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtIdGrupo);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.txtIdGrupoFocusLost()", he);
		}
	}

	private void txtNumKilosCajaFocusLost(java.awt.event.FocusEvent evt) {
		try {
			Util.validateNull(txtNumKilosCaja);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.txtNumKilosCajaFocusLost()", he);
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
					"FrmCategoria.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			if (OnNew)
				newData();
			else
				loadData(this.categoria);
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.btnCancelMousePressed()", he);
		}
	}

	private void txtNombreCategoriaKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtNombreCategoria, evt, 30);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.txtNombreCategoriaKeyTyped()", he);
		}
	}

	private void txtCodCategoriaAgritenKeyTyped(java.awt.event.KeyEvent evt) {
		try {
			Util.keyTyped(txtCodCategoriaAgriten, evt, 10);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this.parentFrame,
					"FrmCategoria.txtNombreCategoriaKeyTyped()", he);
		}
	}
	

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private static javax.swing.JComboBox cboSubcategoria;
    private javax.swing.JCheckBox chkActiva;
    private javax.swing.JCheckBox chkPrivada;
    private javax.swing.JCheckBox chkRetorno;
    private javax.swing.JLabel lblActiva;
    private javax.swing.JLabel lblCodAgriten;
    private javax.swing.JLabel lblIdGrupo;
    private javax.swing.JLabel lblIdCategoria;
    private javax.swing.JLabel lblIdSubcategoria;
    private javax.swing.JLabel lblNombreCategoria;
    private javax.swing.JLabel lblNumKilosCaja;
    private javax.swing.JLabel lblOrden;
    private javax.swing.JLabel lblPrivada;
    private javax.swing.JLabel lblRetorno;
    public javax.swing.JPanel pnlData;
    private javax.swing.JTextField txtCodCategoriaAgriten;
    private javax.swing.JFormattedTextField txtIdGrupo;
    private javax.swing.JFormattedTextField txtIdCategoria;
    private static javax.swing.JFormattedTextField txtIdSubcategoria;
    private javax.swing.JTextField txtNombreCategoria;
    private javax.swing.JFormattedTextField txtNumKilosCaja;
    private javax.swing.JFormattedTextField txtOrden;
    // End of variables declaration//GEN-END:variables

}