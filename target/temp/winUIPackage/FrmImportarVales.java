/*
 * FrmParamFacturas.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.Cursor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import org.hibernate.ReplicationMode;
import org.hibernate.Transaction;

import sessionPackage.MySession;

import entitiesPackage.Categorias;

import entitiesPackage.Cosecheros;
import entitiesPackage.Entity;
import entitiesPackage.Entradascabecera;
import entitiesPackage.EntradascabeceraId;
import entitiesPackage.Entradasimportacion;
import entitiesPackage.EntradasimportacionId;
import entitiesPackage.Entradaslineas;
import entitiesPackage.EntradaslineasId;
import entitiesPackage.Message;
import java.io.InputStreamReader;
/**
 *
 * @author  __USER__
 */
public class FrmImportarVales extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.awt.Frame parent;
	SwingWorker worker;
	private Entity entity = new Entity();
	private MySession session;


	/** Creates new form FrmParamFacturas */
	public FrmImportarVales(java.awt.Frame parent, MySession session, boolean modal) {
		super(parent, modal);
		try {
			this.parent = parent;
			initComponents();
			progressbar.setVisible(false);
			this.session = session;
			entity.setSession(session);
			
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(parent, "FrmImportarVales()", he);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		btnOk = new javax.swing.JButton();
		progressbar = new javax.swing.JProgressBar();
		lblApellidos = new javax.swing.JLabel();
		cmdFile = new javax.swing.JButton();
		txtFile = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Importación de vales");
		setResizable(false);

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

		progressbar.setMaximum(200);
		progressbar.setStringPainted(true);

		lblApellidos.setFont(new java.awt.Font("Segoe UI", 1, 14));
		lblApellidos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		lblApellidos
				.setText("A continuación se importarán los vales del fichero especificado");
		lblApellidos
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		cmdFile.setFont(new java.awt.Font("Segoe UI", 0, 14));
		cmdFile.setText("Seleccionar fichero");
		cmdFile.setToolTipText("Cerrar la ventana");
		cmdFile.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		cmdFile.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				cmdFileMousePressed(evt);
			}
		});

		txtFile.setEditable(false);
		txtFile.setFont(new java.awt.Font("Segoe UI", 0, 14));
		txtFile.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txtFile.setAutoscrolls(false);
		txtFile.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtFile.setName("nif");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addContainerGap(
																				439,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnOk))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				progressbar,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				480,
																				Short.MAX_VALUE))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								lblApellidos,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								480,
																								Short.MAX_VALUE)
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addGap(
																												149,
																												149,
																												149)
																										.addComponent(
																												cmdFile,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												197,
																												javax.swing.GroupLayout.PREFERRED_SIZE))))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				txtFile,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				480,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addGap(47, 47, 47)
										.addComponent(lblApellidos)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(cmdFile)
										.addGap(18, 18, 18)
										.addComponent(
												txtFile,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												25,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												31, Short.MAX_VALUE)
										.addComponent(
												progressbar,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												32,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnOk).addContainerGap()));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void cmdFileMousePressed(java.awt.event.MouseEvent evt) {
		try {
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			JFileChooser FileChooser = new JFileChooser();
			FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = FileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION)
				txtFile.setText(String.valueOf(FileChooser.getSelectedFile()));
			else
				txtFile.setText("");
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this,
					"FrmImportarVales.btnOkMousePressed()", he);
		}

	}

	private Object DoImport() {
		//Le pasamos la URL del archivo CSV a leer.
		try {
			String sCadena;
			String sAnio;
			String sMes;
			String sDia;
			String sVale;
			String sNumPinas;
			String sIdCosechero;
			String sIdCategoria;
			String sNumKilos;
			Integer idEntrada = 0;
			Integer numregistros = 0;
			String charset = "UTF-8";
			
			BufferedReader bf = new BufferedReader(
			        new InputStreamReader(
			                new FileInputStream(txtFile.getText()), charset));
			Integer cont = 0;
			Transaction transaction = session.getSession()
			.beginTransaction();
			
			entity.EntradaaimportacionDeleteAll(parent);
			
			boolean finalizado = false;
			
			sCadena = bf.readLine();
			
			if (sCadena != null) {
				
				sAnio = sCadena.substring(6, 10);
				sMes = sCadena.substring(10, 12);
				sDia = sCadena.substring(12, 14);
				sVale = sCadena.substring(35, 50);
				sNumPinas = sCadena.substring(51, 55);
				sIdCosechero = sCadena.substring(75, 84);
				sIdCategoria = sCadena.substring(226, 237);
				sNumKilos = sCadena.substring(244, 251);
				
				while (!finalizado) {
					numregistros++;
					Integer idCosechero = Integer.parseInt(sIdCosechero.trim());
					SimpleDateFormat formatoDelTexto = new SimpleDateFormat(PreferencesUI.MySQLDateFormat);
					String strFecha = sAnio.trim() + "/" + sMes.trim() + "/" + sDia.trim();
					Date fechaEntrada = null;
					try {

						fechaEntrada = formatoDelTexto.parse(strFecha);

					} catch (ParseException ex) {

						ex.printStackTrace();

					}
					Integer semana = entity.SemanaByFecha(parent, fechaEntrada);
					String vale = sVale.trim();
					Float numPinas = Float.parseFloat(sNumPinas.trim().replace(',', '.'));
					Float numKilos = Float.parseFloat(sNumKilos.trim().replace(',', '.'));
				
					List<?> categoriaslist = entity.CategoriaFindByCodAgriten(parent, sIdCategoria.trim());
					Integer idCategoria = 0;
					
					
					if (categoriaslist.size() > 0) 
						idCategoria = ((Categorias) categoriaslist.get(0)).getId().getIdCategoria();
					
					Entradasimportacion entradaimport = new Entradasimportacion();
					EntradasimportacionId entradaimportid = new EntradasimportacionId();
					entradaimportid.setEmpresas(session.getEmpresa());
					entradaimportid.setEjercicios(session.getEjercicio());
					entradaimportid.setSemana(semana);
					entradaimportid.setFecha(fechaEntrada);
					entradaimportid.setIdCosechero(idCosechero);
					entradaimportid.setNumPinas(numPinas);
					entradaimportid.setIdCategoria(idCategoria);
					entradaimportid.setNumKilos(numKilos);
					entradaimportid.setVale(vale);
					entradaimport.setId(entradaimportid);
					session.getSession().replicate(entradaimport,
							ReplicationMode.OVERWRITE);
					session.getSession().saveOrUpdate(entradaimport);
					session.getSession().flush();
					
					sCadena = bf.readLine();
					if (sCadena == null)
						finalizado = true;
					else {
						sAnio = sCadena.substring(6, 10);
						sMes = sCadena.substring(10, 12);
						sDia = sCadena.substring(12, 14);
						sVale = sCadena.substring(35, 50);
						sNumPinas = sCadena.substring(51, 55);
						sIdCosechero = sCadena.substring(75, 84);
						sIdCategoria = sCadena.substring(226, 237);
						sNumKilos = sCadena.substring(244, 251);
					}
				}
				progressbar.setMaximum(numregistros);
				
				String valeActual = "";
				
				List<?> importList = entity.executeQuery(this, "*",
						"Entradasimportacion", "1=1", "id.fecha, id.idCosechero, id.vale, id.idCategoria");

				if (importList.size() > 0) {
					for (Object linea : importList) {
						Entradasimportacion importlinea = (Entradasimportacion) linea;
						Cosecheros cosechero = entity.CosecheroFindByIdCosechero(parent, importlinea.getId().getIdCosechero().intValue());
						if (cosechero != null) {
							if (!(importlinea.getId().getVale().equals(valeActual))) {
								Entradascabecera entrada = new Entradascabecera();
								idEntrada = entity.newId(this,
									"Entradascabecera", "id.idEntrada");
								EntradascabeceraId entradaid = new EntradascabeceraId();
								entradaid.setIdEntrada(idEntrada);
								entradaid.setEjercicios(session.getEjercicio());
								entradaid.setEmpresas(session.getEmpresa());
								entrada.setId(entradaid);
								entrada.setSemana(importlinea.getId().getSemana());
								entrada.setSemanaEntrada(importlinea.getId().getSemana());
								entrada.setFecha(importlinea.getId().getFecha());			
								entrada.setIdCosechero(importlinea.getId().getIdCosechero());
								Integer idZona = entity.CosecheroGetZona(parent, importlinea.getId().getIdCosechero());
								entrada.setIdZona(idZona);					
								entrada.setNumPinas(importlinea.getId().getNumPinas());	
								entrada.setRecogidaPropia(((Number) 0).shortValue());
								entrada.setLmd(new Date());
								entrada.setSid("Santi");
								entrada.setVersion(1);
								session.getSession().replicate(entrada,
										ReplicationMode.OVERWRITE);
								session.getSession().saveOrUpdate(entrada);
								session.getSession().flush();
							}
						
							Entradaslineas entradalinea = new Entradaslineas();
							EntradaslineasId entradalineaId = new EntradaslineasId();
							entradalineaId.setEjercicios(session.getEjercicio());
							entradalineaId.setEmpresas(session.getEmpresa());
							entradalineaId.setIdEntrada(idEntrada);
							entradalineaId.setIdCategoria(importlinea.getId().getIdCategoria());
							entradalinea.setId(entradalineaId);
							entradalinea.setNumKilos(importlinea.getId().getNumKilos());
							entradalinea.setLmd(new Date());
							entradalinea.setSid("Santi");
							entradalinea.setVersion(1);
							session.getSession().replicate(entradalinea,
									ReplicationMode.OVERWRITE);
							session.getSession().saveOrUpdate(entradalinea);
							session.getSession().flush();
						}
						valeActual = importlinea.getId().getVale();
						updateStatus(cont++);
					}
				}
			}
			if (transaction.isActive()) {
				transaction.commit();
			}
			session.getSession().close();
			bf.close();
			Message.ShowInformationMessage(this,
					"Importación realizada con éxito");
			return "Completado";

		} catch (RuntimeException he) {
			he.printStackTrace();
			Message.ShowRuntimeError(parent,
					"Error en el proceso de importaciï¿½n", he);
			return "Error";
		} catch (IOException e) {
			e.printStackTrace();
			Message
					.ShowIOError(parent, "Error en el proceso de importaciï¿½n",
							e);
			return "Error";
		}
	}

	private void btnOkMousePressed(java.awt.event.MouseEvent evt) {
		try {
			if (!txtFile.getText().equals("")) {
				progressbar.setVisible(true);
				btnOk.setEnabled(false);

				worker = new SwingWorker() {
					public Object construct() {
						return DoImport();
					}

					public void finished() {
						btnOk.setEnabled(true);
						dispose();
					}
				};
				worker.start();
			} else {
				Message.ShowValidateMessage(parent, "Debe indicar un fichero.");
				txtFile.requestFocus();
			}

		} catch (RuntimeException he) {
			Message.ShowRuntimeError(this,
					"FrmImportarVales.btnOkMousePressed()", he);
		}
	}

	void updateStatus(final int i) {
		Runnable doSetProgressBarValue = new Runnable() {
			public void run() {
				progressbar.setValue(i);
			}
		};
		SwingUtilities.invokeLater(doSetProgressBarValue);
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnOk;
	private javax.swing.JButton cmdFile;
	private javax.swing.JLabel lblApellidos;
	private javax.swing.JProgressBar progressbar;
	private javax.swing.JTextField txtFile;
	// End of variables declaration//GEN-END:variables

}
