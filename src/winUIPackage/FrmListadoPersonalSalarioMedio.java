/*
 * FrmParamFacturas.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.AWTKeyStroke;
import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import entitiesPackage.Entity;
import entitiesPackage.Message;

import reportsPackage.ListadoNominas;
import reportsPackage.ListadoPersonalSalarioMedio;
import sessionPackage.MySession;

/**
 *
 * @author  __USER__
 */
public class FrmListadoPersonalSalarioMedio extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MySession session;
	private java.awt.Frame parent;
	private Entity entity = new Entity();

	/** Creates new form FrmInformeListadoNominas */
	public FrmListadoPersonalSalarioMedio(java.awt.Frame parent, MySession session,
			boolean modal) {
		super(parent, modal);
		try {
			this.session = session;
			this.parent = parent;
			entity.setSession(session);
			initComponents();
			configureKeys();

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmInformeListadoNominas()", he);
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
					"FrmInformeListadoNominas.configureKeys", he);
		}
	}

	private boolean validateData() {

		try {
			if (txtMes.getText().equals("")) {
				Message.ShowValidateMessage(parent, "Debe indicar un mes.");
				txtMes.requestFocus();
				return (false);
			} else {
				try {
					txtMes.commitEdit();
				} catch (ParseException e) {
					Message.ShowValidateMessage(parent,
							"El tipo de datos indicado no es v�lido.");
					txtMes.requestFocus();
					return (false);
				}
			}
			return (true);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmInformeListadoNominas.validateData()", he);
			return (false);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		btnCancel = new javax.swing.JButton();
		btnOk = new javax.swing.JButton();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Informe de listado de personal con salario medio anual");
		setResizable(false);

		btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/imagesPackage/cerrar.png"))); // NOI18N
		btnCancel.setToolTipText("Cancelar");
		btnCancel.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnCancelMousePressed(evt);
			}
		});

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

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap(275, Short.MAX_VALUE)
										.addComponent(btnCancel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnOk).addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGap(36, 36, 36)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(btnCancel)
														.addComponent(btnOk))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btnOkMousePressed(java.awt.event.MouseEvent evt) {
		try {
			if (validateData()) {
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				ListadoPersonalSalarioMedio report = new ListadoPersonalSalarioMedio(parent);
				report.runReporte(session.getEmpresa().getIdEmpresa(), session
						.getEjercicio().getEjercicio());
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmInformePersonalSalarioMedio.btnOkMousePressed()", he);
		}
	}

	private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			this.dispose();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent,
					"FrmInformeListadoNominas.btnCancelMousePressed()",
					he);
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOk;
	private javax.swing.JLabel lblSemanaDesde1;
	private javax.swing.JFormattedTextField txtMes;
	// End of variables declaration//GEN-END:variables

}