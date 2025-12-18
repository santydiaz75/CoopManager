/*
 * FrmParamFacturas.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;

import java.util.Set;



import entitiesPackage.Entity;
import entitiesPackage.Message;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sessionPackage.MySession;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author  __USER__
 */
public class FrmGeneraLiquidacionRetornoPorCosechero extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MySession session;
	private java.awt.Frame parent;
	private Entity entity = new Entity();
	SwingWorker worker;
	
	boolean toModify = false;

	/** Creates new form FrmParamFacturas */
	public FrmGeneraLiquidacionRetornoPorCosechero(java.awt.Frame parent, MySession session,
			boolean modal) {
		super(parent, modal);
		setResizable(false);
		try {
			this.session = session;
			this.parent = parent;
			entity.setSession(session);
			initComponents();
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent, "FrmGeneraLiquidacionRetorno()", he);
		}
	}

	private void configureKeys() {
		try {
			Set<AWTKeyStroke> teclasTab = new HashSet<AWTKeyStroke>();
			teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
			teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
			teclasTab.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0));
			this.setFocusTraversalKeys(
					KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, teclasTab);

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmGeneraLiquidacionRetorno.configureKeys", he);
		}
	}


        private boolean validateData() {

		try {
			
			if (txtCosechero.getText().equals("")) {
				Message.ShowValidateMessage(parent, "Debe indicar un cosechero.");
				txtCosechero.requestFocus();
				return (false);
			} else {
				try {
					txtCosechero.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es válido.");
					txtCosechero.requestFocus();
					return (false);
				}
			}
			
			if (txtImporteKilo.getText().equals("")) {
				Message.ShowValidateMessage(parent, "Debe indicar el importe por kilo.");
				txtImporteKilo.requestFocus();
				return (false);
			} else {
				try {
					txtImporteKilo.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es válido.");
					txtImporteKilo.requestFocus();
					return (false);
				}
			}
                        
            if (txtNumeroKilos.getText().equals("")) {
				Message.ShowValidateMessage(parent, "Debe indicar el nï¿½mero de kilos a bonificar.");
				txtNumeroKilos.requestFocus();
				return (false);
			} else {
				try {
					txtNumeroKilos.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es válido.");
					txtNumeroKilos.requestFocus();
					return (false);
				}
			}
                        
            if (txtEjericioContable.getText().equals("")) {
				Message.ShowValidateMessage(parent, "Debe indicar un ejercicio contable.");
				txtEjericioContable.requestFocus();
				return (false);
			} else {
				try {
					txtEjericioContable.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es válido.");
					txtEjericioContable.requestFocus();
					return (false);
				}
			}
                        
                        if (txtNumeroBonificacion.getText().equals("")) {
				Message.ShowValidateMessage(parent, "Debe indicar un nï¿½mero de la bonificaciï¿½n en el año contable.");
				txtNumeroBonificacion.requestFocus();
				return (false);
			} else {
				try {
					txtNumeroBonificacion.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es válido.");
					txtNumeroBonificacion.requestFocus();
					return (false);
				}
			}
                        
                        if (txtFecha.getText().equals("")) {
				Message.ShowValidateMessage(parent, "Debe indicar una fecha.");
				txtFecha.requestFocus();
				return (false);
			} else {
				try {
					txtFecha.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es válido.");
					txtFecha.requestFocus();
					return (false);
				}
			}
                        
            if (txtTitulo.getText().equals("")) {
				Message.ShowValidateMessage(parent, "Debe indicar un tï¿½tulo.");
				txtTitulo.requestFocus();
				return (false);
			} 
            
            if (txtConcepto.getText().equals("")) {
				Message.ShowValidateMessage(parent, "Debe indicar un concepto.");
				txtConcepto.requestFocus();
				return (false);
			} 
                        
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmGeneraLiquidacionRetorno.validateData()", he);
			return (false);
		}
	}
        
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    private void initComponents() {

        btnOk = new javax.swing.JButton();
        lblCosechero = new javax.swing.JLabel();
        lblEjercicio = new javax.swing.JLabel();
        lblImporteKilo = new javax.swing.JLabel();
        txtImporteKilo = new javax.swing.JFormattedTextField();
        lblNumeroKilos = new javax.swing.JLabel();
        txtNumeroKilos = new javax.swing.JFormattedTextField();
        lblEjericioContable = new javax.swing.JLabel();
        txtCosechero = new javax.swing.JFormattedTextField();
        txtEjericioContable = new javax.swing.JFormattedTextField();
        lblNumeroBonificacion = new javax.swing.JLabel();
        txtNumeroBonificacion = new javax.swing.JFormattedTextField();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JFormattedTextField();
        lblTitulo = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        lblConcepto = new javax.swing.JLabel();
        txtConcepto = new javax.swing.JTextField();

        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generaciï¿½n de liquidaciones por cosechero");

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/ok.png")));
        btnOk.setToolTipText("Aceptar");
        btnOk.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnOkMousePressed(evt);
            }
        });

        lblCosechero.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblCosechero.setForeground(new java.awt.Color(255, 0, 0));
        lblCosechero.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCosechero.setText("Cosechero");
        lblCosechero.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCosechero.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCosechero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtCosechero.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCosechero.setFont(new java.awt.Font("Segoe UI", 0, 14));
        
        lblEjercicio.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblEjercicio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEjercicio.setText("Generar la liquidación de Retorno por Cosechero");
        lblEjercicio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblImporteKilo.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblImporteKilo.setForeground(new java.awt.Color(255, 0, 0));
        lblImporteKilo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblImporteKilo.setText("Importe kilo");
        lblImporteKilo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtImporteKilo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtImporteKilo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.0000"))));
        txtImporteKilo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtImporteKilo.setFont(new java.awt.Font("Segoe UI", 0, 14));

        lblNumeroKilos.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblNumeroKilos.setForeground(new java.awt.Color(255, 0, 0));
        lblNumeroKilos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumeroKilos.setText("Número kilos");
        lblNumeroKilos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNumeroKilos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumeroKilos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.0000"))));
        txtNumeroKilos.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNumeroKilos.setFont(new java.awt.Font("Segoe UI", 0, 14));

        lblEjericioContable.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblEjericioContable.setForeground(new java.awt.Color(255, 0, 0));
        lblEjericioContable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEjericioContable.setText("Ejercicio contable");
        lblEjericioContable.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtEjericioContable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtEjericioContable.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtEjericioContable.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEjericioContable.setFont(new java.awt.Font("Segoe UI", 0, 14));

        lblNumeroBonificacion.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblNumeroBonificacion.setForeground(new java.awt.Color(255, 0, 0));
        lblNumeroBonificacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumeroBonificacion.setText("Número bonificaciï¿½n");
        lblNumeroBonificacion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNumeroBonificacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumeroBonificacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtNumeroBonificacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNumeroBonificacion.setFont(new java.awt.Font("Segoe UI", 0, 14));

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblFecha.setForeground(new java.awt.Color(255, 0, 0));
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFecha.setText("Fecha de liquidación");

        txtFecha.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFecha.setFont(new java.awt.Font("Segoe UI", 0, 14));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblTitulo.setForeground(new java.awt.Color(255, 0, 0));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTitulo.setText("Titulo");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        
        lblConcepto.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblConcepto.setForeground(new java.awt.Color(255, 0, 0));
        lblConcepto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblConcepto.setText("Concepto");
        lblConcepto.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtTitulo.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtTitulo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTitulo.setAutoscrolls(false);
        txtTitulo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTitulo.setName("titulo");
        txtTitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTituloKeyTyped(evt);
            }
        });
        
        txtConcepto.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtConcepto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtConcepto.setAutoscrolls(false);
        txtConcepto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtConcepto.setName("concepto");
        txtConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConceptoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(lblCosechero, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(txtCosechero, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(lblImporteKilo, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(txtImporteKilo, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
        					.addContainerGap(572, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        							.addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(txtTitulo, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE))
        						.addGroup(Alignment.LEADING, layout.createSequentialGroup()
            							.addComponent(lblConcepto, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
            							.addPreferredGap(ComponentPlacement.RELATED)
            							.addComponent(txtConcepto, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE))
        						.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        							.addComponent(lblFecha, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(txtFecha, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
        						.addGroup(Alignment.LEADING, layout.createParallelGroup(Alignment.LEADING, false)
        							.addGroup(layout.createSequentialGroup()
        								.addComponent(lblNumeroKilos, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
        								.addPreferredGap(ComponentPlacement.UNRELATED)
        								.addComponent(txtNumeroKilos, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
        							.addGroup(layout.createSequentialGroup()
        								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        									.addGroup(layout.createSequentialGroup()
        										.addComponent(lblEjericioContable, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
        										.addPreferredGap(ComponentPlacement.UNRELATED))
        									.addGroup(layout.createSequentialGroup()
        										.addComponent(lblNumeroBonificacion, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
        										.addGap(10)))
        								.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        									.addComponent(txtNumeroBonificacion, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        									.addComponent(txtEjericioContable))))
        						.addComponent(lblEjercicio, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
        						.addComponent(btnOk))
        					.addGap(436))))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(28)
        			.addComponent(lblEjercicio)
        			.addGap(23)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblCosechero)
        				.addComponent(txtCosechero, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblImporteKilo)
        				.addComponent(txtImporteKilo, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNumeroKilos)
        				.addComponent(txtNumeroKilos, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblEjericioContable)
        				.addComponent(txtEjericioContable, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(txtNumeroBonificacion, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNumeroBonificacion))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(txtFecha, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblFecha))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblTitulo, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        				.addComponent(txtTitulo, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblConcepto, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        				.addComponent(txtConcepto, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        			.addGap(32)
        			.addComponent(btnOk)
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        lblEjercicio.getAccessibleContext().setAccessibleName("Ejercicio");

        pack();
    }

    private void txtTituloKeyTyped(java.awt.event.KeyEvent evt) {
    }

    private void txtConceptoKeyTyped(java.awt.event.KeyEvent evt) {
    }

	private Object DoLiquidacion() throws ParseException {
		try {
                    if (validateData()) {
                            Integer cont = 0;
                            int cosechero = ((Number) txtCosechero.getValue()).intValue();
                            Float importekilo = ((Number) txtImporteKilo.getValue()).floatValue();
                            Float numeroKilos = ((Number) txtNumeroKilos.getValue()).floatValue();
                            int ejercicioContable = ((Number) txtEjericioContable.getValue()).intValue();
                            int numeroBonificacion = ((Number) txtNumeroBonificacion.getValue()).intValue();
                            java.util.Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getValue().toString()); 
                            String titulo = txtTitulo.getText();
                            String concepto = txtConcepto.getText();
                            
                            try {
                                entity.LiquidacionRetornoGeneratePorCosechero(parent,cosechero, importekilo,numeroKilos, ejercicioContable, numeroBonificacion, fecha, titulo, concepto);                                  
                            } catch (SQLException ex) {
                                    Message.ShowErrorMessage(parent,
                                                    "FrmGeneraLiquidacionRetornoPorCosechero", ex
                                                                    .getMessage());
                                    return null;
                            }

                    }
                    return "Completado";
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmGeneraLiquidacionRetorno.DoLiquidacion()", he);
			return "Error";
		} 
	}

	private void btnOkMousePressed(java.awt.event.MouseEvent evt) {
		try {
                    btnOk.setEnabled(false);
                    worker = new SwingWorker() {
                                    public Object construct() {
                                        try {
                                            return DoLiquidacion();
                                        } catch (ParseException ex) {
                                            Logger.getLogger(FrmGeneraLiquidacionRetornoPorCosechero.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        return null;
                                    }

                                    public void finished() {
                                            btnOk.setEnabled(true);
                                    }
                            };
                            worker.start();
		}
		catch (RuntimeException he) {
			Message.ShowRuntimeError(this,
					"FrmGeneraLiquidacionRetorno.btnOkMousePressed()", he);
		}
	}


    private javax.swing.JButton btnOk;
    private javax.swing.JLabel lblEjercicio;
    private javax.swing.JLabel lblCosechero;
    private javax.swing.JLabel lblEjericioContable;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblImporteKilo;
    private javax.swing.JLabel lblNumeroBonificacion;
    private javax.swing.JLabel lblNumeroKilos;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblConcepto;
    private javax.swing.JFormattedTextField txtCosechero;
    private javax.swing.JFormattedTextField txtEjericioContable;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JFormattedTextField txtImporteKilo;
    private javax.swing.JFormattedTextField txtNumeroBonificacion;
    private javax.swing.JFormattedTextField txtNumeroKilos;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtConcepto;

}

