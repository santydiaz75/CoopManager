/*
 * FrmMDI.java
 *
 * Created on __DATE__, __TIME__
 */

package winUIPackage;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.List;

import entitiesPackage.Ejercicios;
import entitiesPackage.Empresas;
import entitiesPackage.Entity;
import entitiesPackage.Message;

import reportsPackage.ListadoCosecheros;
import reportsPackage.ListadoPersonal;
import reportsPackage.ListadoPersonalSalarioMedio;
import sessionPackage.MySession;

/**
 *
 * @author  SANTI DIAZ
 */
public class FrmMDI extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	private FrmCalendario fCalendario;
	private FrmBimestres fBimestre;
	private FrmCategorias fCategoria;
	private FrmCosecheros fCosechero;
	private FrmEntradas fEntradas;
	private FrmVentas fVentas;
	private FrmEmpleados fEmpleado;
	private FrmTiposGasto fTipogasto;
	private FrmLiquidaciones fLiquidacion;
	private FrmDataQuery fDataQuery;
	private FrmGeneraLiquidacion fGeneraLiquidacion;
    private FrmGeneraLiquidacionRetorno fGeneraLiquidacionRetorno;
    private FrmGeneraLiquidacionRetornoPorCosechero fGeneraLiquidacionRetornoPorCosechero;
	private FrmInformeVale fInformeVale;
	private FrmInformePreLiquidacion fInformePreLiquidacion;
	private FrmInformeFacturasLiquidacion fInformeFacturaLiquidacion;
    private FrmInformeFacturasLiquidacionRetorno fInformeFacturaLiquidacionRetorno;
	private FrmInformeResumenLiquidacion fInformeResumenLiquidacion;
	private FrmInformeKilosInutilizados fInformeKilosInutilizados;
	private FrmInformeControlESAlmacen fControlESAlmacen;
	private FrmInformeControlProduccionZonas fControlProduccionZonas;
	private FrmInformeListadoPersonalBanco fListadoPersonalBanco;
	private FrmListadoPersonalSalarioMedio fListadoPersonalSalarioMedio;
	private FrmInformeListadoNominas fListadoNominas;
	private FrmListadoCosecherosKilos fListadoCosecherosKilos;
	private FrmListadoCategoriasKilosPorCosechero fListadoCategoriasKilosPorCosechero;
	private FrmInformeRentabilidad fInformeRentabilidad;
	private FrmBackup fBackup;
	private FrmContaLiquidaciones fContaLiquidaciones;
	private FrmContaNominas fContaNominas;
	private FrmContaPagos fContaPagos;
	private FrmContaCobros fContaCobros;
	private FrmContaLiquidacionesPagos fContaLiquidacionesPagos;
    private FrmCartaCosecheros fCartaCosecheros;
	private MySession session = null;
	private boolean loading = true;
	private Entity entity = new Entity();

	/** Creates new form FrmMDI */
	public FrmMDI() {
		try {
			initComponents();
			desktopPane.setBackground(PreferencesUI.DesktopBackgroundColor);
			session = new MySession(new Ejercicios(), new Empresas());
			FrmEntityView f = new FrmEntityView();
			f.dispose();
			entity.setSession(session);
			loadEmpresas();
			loadEjercicios();
			Ejercicios ejercicio = entity.EjercicioFindLast(this);
			Empresas empresa = entity.EmpresaFindActivada(this);
			cboEmpresas.setSelectedItem(empresa.getNombre());
			cboEjercicios.setSelectedItem(ejercicio.getEjercicio());
			session.setEjercicio(ejercicio);
			session.setEmpresa(empresa);
			this.setTitle("GestCoop v3.0 - Empresa: " + empresa.getNombre());
			loading = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(desktopPane, "FrmMDI()", he);
		}
	}

	private void reload() {
		try {
			loading = true;
			String empresa = entity.EmpresaFindActivada(this).getNombre();
			this.desktopPane.removeAll();
			this.desktopPane.repaint();
			this.setTitle(empresa);
			loading = false;
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(desktopPane, "FrmMDI.reload()", he);
		}
	}

	private void loadEmpresas() {
		try {
			cboEmpresas.removeAllItems();

			List<?> empresasList = entity.EmpresaFindAll(this);

			for (Object o : empresasList) {
				Empresas empresa = (Empresas) o;
				cboEmpresas.addItem(empresa.getNombre());
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(desktopPane, "FrmMDI.loadEmpresas()", he);
		}
	}

	private void loadEjercicios() {
		try {
			cboEjercicios.removeAllItems();

			List<?> ejerciciosList = entity.EjercicioFindAll(this);

			for (Object o : ejerciciosList) {
				Ejercicios ejercicio = (Ejercicios) o;
				cboEjercicios.addItem(ejercicio.getEjercicio());
			}
		} catch (RuntimeException he) {
			Message
					.ShowRuntimeError(desktopPane, "FrmMDI.loadejercicios()",
							he);
		}
	}

    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        cboEmpresas = new javax.swing.JComboBox();
        lnlEmpresa = new javax.swing.JLabel();
        lnlEmpresa1 = new javax.swing.JLabel();
        cboEjercicios = new javax.swing.JComboBox();
        menuBar = new javax.swing.JMenuBar();
        CalendarioMenuItem = new javax.swing.JMenu();
        BimestresMenuItem = new javax.swing.JMenu();
        CategoriasMenuItem = new javax.swing.JMenu();
        EntradasMenuItem = new javax.swing.JMenu();
        VentasMenuItem = new javax.swing.JMenu();
        LiquidacionesMenuItem = new javax.swing.JMenu();
        GeneraLiquidacionMenuiItem = new javax.swing.JMenuItem();
        ConsultarLiquidacionesMenuItem = new javax.swing.JMenuItem();
        GenerarLiquidacionRetornoMenuItem = new javax.swing.JMenuItem();
        GenerarLiquidacionRetornoPorCosecheroMenuItem = new javax.swing.JMenuItem();
        InformesMenu = new javax.swing.JMenu();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        PrintPreliquidacionItem = new javax.swing.JMenuItem();
        PrintFacturaLiquidacionItem = new javax.swing.JMenuItem();
        PrintResumenLiquidacion = new javax.swing.JMenuItem();
        PrintFacturaLiquidacionRetornoItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        PrintControlESAlmacenMenuItem = new javax.swing.JMenuItem();
        PrintControlProduccionZonasMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        PrintListadoPersonal = new javax.swing.JMenuItem();
        PrintListadoPersonalBanco = new javax.swing.JMenuItem();
        PrintListadoPersonalSalarioMedio = new javax.swing.JMenuItem();
        PrintListadoNominas = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        PrintRentabilidadItem = new javax.swing.JMenuItem();
        PrintCartaCosecherosItem = new javax.swing.JMenuItem();
        ToolsMenuItem = new javax.swing.JMenu();
        BackupManuItem = new javax.swing.JMenuItem();
        ContabilizarMenuItem = new javax.swing.JMenu();
        ContaLiquidacionesMenuItem = new javax.swing.JMenuItem();
        ContaLiquidacionesPagosItem = new javax.swing.JMenuItem();
        ContaNominasMenuItem = new javax.swing.JMenuItem();
        ContaPagosMenuItem = new javax.swing.JMenuItem();
        ContaCobrosMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        ExitMenuItem = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.setBackground(new java.awt.Color(255, 255, 204));
        desktopPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        desktopPane.setAutoscrolls(true);

        cboEmpresas.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cboEmpresas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboEmpresas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboEmpresas.setFocusable(false);
        cboEmpresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpresasActionPerformed(evt);
            }
        });

        lnlEmpresa.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lnlEmpresa.setText("Empresa");

        lnlEmpresa1.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lnlEmpresa1.setText("Ejercicio");

        cboEjercicios.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cboEjercicios.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboEjercicios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboEjercicios.setFocusable(false);
        cboEjercicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEjerciciosActionPerformed(evt);
            }
        });

        CalendarioMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CalendarioMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/calendario.png")));
        CalendarioMenuItem.setToolTipText("Calendario y precios");
        CalendarioMenuItem.setBorderPainted(true);
        CalendarioMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CalendarioMenuItemMousePressed(evt);
            }
        });
        menuBar.add(CalendarioMenuItem);

        BimestresMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BimestresMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/bimestre.png")));
        BimestresMenuItem.setToolTipText("Bimestres");
        BimestresMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BimestresMenuItemMousePressed(evt);
            }
        });
        menuBar.add(BimestresMenuItem);

        CategoriasMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CategoriasMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/categoria.png")));
        CategoriasMenuItem.setToolTipText("Categorías");
        CategoriasMenuItem.setBorderPainted(true);
        CategoriasMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CategoriasMenuItemMousePressed(evt);
            }
        });
        menuBar.add(CategoriasMenuItem);

        EntradasMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EntradasMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/entradas.png")));
        EntradasMenuItem.setToolTipText("Entradas");
        EntradasMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EntradasMenuItemMousePressed(evt);
            }
        });
        menuBar.add(EntradasMenuItem);

        VentasMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VentasMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/salidas.png")));
        VentasMenuItem.setToolTipText("Ventas");
        VentasMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                VentasMenuItemMousePressed(evt);
            }
        });
        menuBar.add(VentasMenuItem);

        LiquidacionesMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LiquidacionesMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/liquidacion.png")));
        LiquidacionesMenuItem.setToolTipText("Liquidaciones");

        GeneraLiquidacionMenuiItem.setText("Generar liquidación");
        GeneraLiquidacionMenuiItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GeneraLiquidacionMenuiItemActionPerformed(evt);
            }
        });
        LiquidacionesMenuItem.add(GeneraLiquidacionMenuiItem);

        ConsultarLiquidacionesMenuItem.setText("Consultar liquidaciones");
        ConsultarLiquidacionesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarLiquidacionesMenuItemActionPerformed(evt);
            }
        });
        LiquidacionesMenuItem.add(ConsultarLiquidacionesMenuItem);

        GenerarLiquidacionRetornoMenuItem.setText("Generar liquidación Retorno");
        GenerarLiquidacionRetornoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarLiquidacionRetornoMenuItemActionPerformed(evt);
            }
        });
        LiquidacionesMenuItem.add(GenerarLiquidacionRetornoMenuItem);

        GenerarLiquidacionRetornoPorCosecheroMenuItem.setText("Generar liquidación Retorno Por Cosechero");
        GenerarLiquidacionRetornoPorCosecheroMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarLiquidacionRetornoPorCosecheroMenuItemActionPerformed(evt);
            }
        });
        LiquidacionesMenuItem.add(GenerarLiquidacionRetornoPorCosecheroMenuItem);
        
        menuBar.add(LiquidacionesMenuItem);

        InformesMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        InformesMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Informes.png")));
        InformesMenu.setToolTipText("Informes");
        InformesMenu.setBorderPainted(true);


        InformesMenu.add(jSeparator1);

        PrintPreliquidacionItem.setText("Preliquidación");
        PrintPreliquidacionItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintPreliquidacionItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintPreliquidacionItem);

        PrintFacturaLiquidacionItem.setText("Facturas de liquidación");
        PrintFacturaLiquidacionItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintFacturaLiquidacionItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintFacturaLiquidacionItem);

        PrintResumenLiquidacion.setText("Resumen de liquidación");
        PrintResumenLiquidacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintResumenLiquidacionActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintResumenLiquidacion);

        PrintFacturaLiquidacionRetornoItem.setText("Facturas de liquidación Retorno");
        PrintFacturaLiquidacionRetornoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintFacturaLiquidacionRetornoItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintFacturaLiquidacionRetornoItem);
        InformesMenu.add(jSeparator2);
        InformesMenu.add(jSeparator3);

        PrintControlESAlmacenMenuItem.setText("Control de E/S del almacen");
        PrintControlESAlmacenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintControlESAlmacenMenuItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintControlESAlmacenMenuItem);

        PrintControlProduccionZonasMenuItem.setText("Control de producción por zonas");
        PrintControlProduccionZonasMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintControlProduccionZonasMenuItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintControlProduccionZonasMenuItem);
        InformesMenu.add(jSeparator4);

        PrintListadoPersonal.setText("Listado de personal");
        PrintListadoPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintListadoPersonalActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintListadoPersonal);

        PrintListadoPersonalBanco.setText("Listado de personal para el banco");
        PrintListadoPersonalBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintListadoPersonalBancoActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintListadoPersonalBanco);

        PrintListadoPersonalSalarioMedio.setText("Listado de personal con salario medio");
        PrintListadoPersonalSalarioMedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	PrintListadoPersonalSalarioMedioActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintListadoPersonalSalarioMedio);
        
        PrintListadoNominas.setText("Listado de nóminas");
        PrintListadoNominas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintListadoNominasActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintListadoNominas);
        InformesMenu.add(jSeparator5);

        PrintRentabilidadItem.setText("Rentabilidad");
        PrintRentabilidadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintRentabilidadItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintRentabilidadItem);

        PrintCartaCosecherosItem.setText("Carta a cosecheros");
        PrintCartaCosecherosItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintCartaCosecherosItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintCartaCosecherosItem);
        PrintCartaCosecherosItem.getAccessibleContext().setAccessibleName("Carta a cosecheros");
        PrintCartaCosecherosItem.getAccessibleContext().setAccessibleDescription("");

        menuBar.add(InformesMenu);

        ToolsMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ToolsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/tools.png")));
        ToolsMenuItem.setToolTipText("Herramientas");

        BackupManuItem.setText("Copia de seguridad");
        BackupManuItem.setToolTipText("Realiza la copia  de seguridad de la base de datos");
        BackupManuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackupManuItemActionPerformed(evt);
            }
        });
        ToolsMenuItem.add(BackupManuItem);

        ContabilizarMenuItem.setText("Contabilización");

        ContaLiquidacionesMenuItem.setText("Liquidaciones");
        ContaLiquidacionesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContaLiquidacionesMenuItemActionPerformed(evt);
            }
        });
        ContabilizarMenuItem.add(ContaLiquidacionesMenuItem);

        ContaLiquidacionesPagosItem.setText("Pagos de liquidaciones");
        ContaLiquidacionesPagosItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContaLiquidacionesPagosItemActionPerformed(evt);
            }
        });
        ContabilizarMenuItem.add(ContaLiquidacionesPagosItem);

        ContaNominasMenuItem.setText("Nominas");
        ContaNominasMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContaNominasMenuItemActionPerformed(evt);
            }
        });
        ContabilizarMenuItem.add(ContaNominasMenuItem);

        ContaPagosMenuItem.setText("Pagos");
        ContaPagosMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContaPagosMenuItemActionPerformed(evt);
            }
        });
        ContabilizarMenuItem.add(ContaPagosMenuItem);

        ContaCobrosMenuItem.setText("Cobros");
        ContaCobrosMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContaCobrosMenuItemActionPerformed(evt);
            }
        });
        ContabilizarMenuItem.add(ContaCobrosMenuItem);

        ToolsMenuItem.add(ContabilizarMenuItem);

        menuBar.add(ToolsMenuItem);

        helpMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        helpMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Ayuda.png")));
        helpMenu.setToolTipText("Ayuda");
        helpMenu.setBorderPainted(true);

        contentMenuItem.setText("Contenido");
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setText("Acerca de");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        ExitMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ExitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Salir.png")));
        ExitMenuItem.setToolTipText("Salir de la aplicación");
        ExitMenuItem.setBorderPainted(true);
        ExitMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ExitMenuItemMousePressed(evt);
            }
        });
        menuBar.add(ExitMenuItem);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lnlEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lnlEmpresa1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboEjercicios, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(259, Short.MAX_VALUE))
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboEjercicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(desktopPane))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lnlEmpresa1)
                    .addComponent(lnlEmpresa))
                .addGap(245, 245, 245))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboEjercicios, cboEmpresas});

        pack();
    }

    private void GenerarLiquidacionRetornoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {

		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormGeneraLiquidacionRetorno();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
    }

    private void GenerarLiquidacionRetornoPorCosecheroMenuItemActionPerformed(java.awt.event.ActionEvent evt) {

		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormGeneraLiquidacionRetornoPorCosechero();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
    }
    
    private void PrintFacturaLiquidacionRetornoItemActionPerformed(java.awt.event.ActionEvent evt) {
        Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeFacturaLiquidacionRetorno();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
    }

    private void PrintCartaCosecherosItemActionPerformed(java.awt.event.ActionEvent evt) {
        Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openCartaCosecheros();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
    }

	private void ContaLiquidacionesPagosItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormContaLiquidacionesPagos();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void ContaCobrosMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormContaCobros();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void ContaPagosMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormContaPagos();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void ContaNominasMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormContaNominas();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void ContaLiquidacionesMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormContaLiquidaciones();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void BackupManuItemActionPerformed(java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormBackup();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void cboEjerciciosActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())) {
				if (cboEjercicios.getSelectedIndex() != -1 && !loading) {
					Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
					setCursor(hourglassCursor);
					Ejercicios Ejercicio = entity.EjercicioFindById(this,
							((Number) cboEjercicios.getSelectedItem())
									.intValue());
					session.setEjercicio(Ejercicio);
					reload();
					Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
					setCursor(normalCursor);
					Message.ShowInformationMessage(desktopPane,
							"Ha activado el ejercicio "
									+ cboEjercicios.getSelectedItem() + ".");
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(desktopPane,
					"FrmMDI.cboEjerciciosActionPerformed()", he);
		}
	}

	private void cboEmpresasActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if ("comboBoxChanged".equals(evt.getActionCommand())) {
				if (cboEmpresas.getSelectedIndex() != -1 && !loading) {
					Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
					setCursor(hourglassCursor);
					Empresas empresa = entity.EmpresaFindByNombre(this,
							(String) cboEmpresas.getSelectedItem());
					session.setEmpresa(empresa);
					entity.EmpresaSetActivada(this, empresa);
					reload();
					Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
					setCursor(normalCursor);
					Message.ShowInformationMessage(desktopPane,
							"Ha activado la empresa "
									+ cboEmpresas.getSelectedItem() + ".");
				}
			}
		} catch (RuntimeException he) {
			Message.ShowRuntimeError(desktopPane,
					"FrmMDI.cboEmpresasActionPerformed()", he);
		}
	}

	private void ConsultarLiquidacionesMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormLiquidaciones();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void GeneraLiquidacionMenuiItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormGeneraLiquidacion();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}
        

	private void BimestresMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormBimestres();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void VentasMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormVentas();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void EntradasMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormEntradas();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void CalendarioMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormCalendario();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void CategoriasMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormCategorias();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}
	
	private void PrintListadoNominasActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openListadoNominas();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintPreliquidacionItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformePreLiquidacion();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintFacturaLiquidacionItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeFacturaLiquidacion();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintResumenLiquidacionActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeResumenLiquidacion();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}



	private void PrintControlESAlmacenMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeControlESAlmacen();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintControlProduccionZonasMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeControlProduccionZonas();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintListadoPersonalActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		ListadoPersonal report = new ListadoPersonal(this);
		report.runReporte(session.getEmpresa().getIdEmpresa(), session
				.getEjercicio().getEjercicio());
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintListadoPersonalBancoActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeListadoPersonalBanco();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}
	
	private void PrintListadoPersonalSalarioMedioActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		ListadoPersonalSalarioMedio report = new ListadoPersonalSalarioMedio(this);
		report.runReporte(session.getEmpresa().getIdEmpresa(), session
				.getEjercicio().getEjercicio());
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintRentabilidadItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeRentabilidad();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void ExitMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		session.getSession().close();
		this.dispose();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void openFormCalendario() {
		if (fCalendario != null) {
			fCalendario.dispose();
		}
		fCalendario = new FrmCalendario(this, session);
		desktopPane.removeAll();
		desktopPane.add(fCalendario, BorderLayout.CENTER);
		fCalendario.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fCalendario.setBackground(PreferencesUI.DesktopBackgroundColor);
		fCalendario.setVisible(true);
	}

	private void openFormBimestres() {
		if (fBimestre != null) {
			fBimestre.dispose();
		}
		fBimestre = new FrmBimestres(this, session);
		desktopPane.removeAll();
		desktopPane.add(fBimestre, BorderLayout.CENTER);
		fBimestre.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fBimestre.setBackground(PreferencesUI.DesktopBackgroundColor);
		fBimestre.setVisible(true);
	}

	private void openFormCategorias() {
		if (fCategoria != null) {
			fCategoria.dispose();
		}
		fCategoria = new FrmCategorias(this, session);
		desktopPane.removeAll();
		desktopPane.add(fCategoria, BorderLayout.CENTER);
		fCategoria.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fCategoria.setBackground(PreferencesUI.DesktopBackgroundColor);
		fCategoria.setVisible(true);
	}

	private void openFormEntradas() {
		if (fEntradas != null) {
			fEntradas.dispose();
		}
		fEntradas = new FrmEntradas(this, session);
		desktopPane.removeAll();
		desktopPane.add(fEntradas, BorderLayout.CENTER);
		fEntradas.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fEntradas.setBackground(PreferencesUI.DesktopBackgroundColor);
		fEntradas.setVisible(true);
	}

	private void openFormVentas() {
		if (fVentas != null) {
			fVentas.dispose();
		}
		fVentas = new FrmVentas(this, session);
		desktopPane.removeAll();
		desktopPane.add(fVentas, BorderLayout.CENTER);
		fVentas.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fVentas.setBackground(PreferencesUI.DesktopBackgroundColor);
		fVentas.setVisible(true);
	}

	private void openFormLiquidaciones() {
		if (fLiquidacion != null) {
			fLiquidacion.dispose();
		}
		fLiquidacion = new FrmLiquidaciones(this, session);
		desktopPane.removeAll();
		desktopPane.add(fLiquidacion, BorderLayout.CENTER);
		fLiquidacion.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fLiquidacion.setBackground(PreferencesUI.DesktopBackgroundColor);
		fLiquidacion.setVisible(true);
	}

	private void openFormDataQuery() {
		if (fDataQuery != null) {
			fDataQuery.dispose();
		}
		fDataQuery = new FrmDataQuery(this, session);
		desktopPane.removeAll();
		desktopPane.add(fDataQuery, BorderLayout.CENTER);
		fDataQuery.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fDataQuery.setBackground(PreferencesUI.DesktopBackgroundColor);
		fDataQuery.setVisible(true);
	}

	private void openFormGeneraLiquidacion() {
		if (fGeneraLiquidacion != null) {
			fGeneraLiquidacion.dispose();
		}
		fGeneraLiquidacion = new FrmGeneraLiquidacion(this, session, false);
		fGeneraLiquidacion.setLocationRelativeTo(null);
		fGeneraLiquidacion.setVisible(true);
	}
        
    private void openFormGeneraLiquidacionRetorno() {
		if (fGeneraLiquidacionRetorno != null) {
			fGeneraLiquidacionRetorno.dispose();
		}
		fGeneraLiquidacionRetorno = new FrmGeneraLiquidacionRetorno(this, session, false);
		fGeneraLiquidacionRetorno.setLocationRelativeTo(null);
		fGeneraLiquidacionRetorno.setVisible(true);
	}
    
    private void openFormGeneraLiquidacionRetornoPorCosechero() {
		if (fGeneraLiquidacionRetornoPorCosechero != null) {
			fGeneraLiquidacionRetornoPorCosechero.dispose();
		}
		fGeneraLiquidacionRetornoPorCosechero = new FrmGeneraLiquidacionRetornoPorCosechero(this, session, false);
		fGeneraLiquidacionRetornoPorCosechero.setLocationRelativeTo(null);
		fGeneraLiquidacionRetornoPorCosechero.setVisible(true);
	}



	private void openListadoNominas() {
		if (fListadoNominas != null) {
			fListadoNominas.dispose();
		}
		fListadoNominas = new FrmInformeListadoNominas(this, session, false);
		fListadoNominas.setLocationRelativeTo(null);
		fListadoNominas.setVisible(true);
	}

	private void openInformeVale() {
		if (fInformeVale != null) {
			fInformeVale.dispose();
		}
		fInformeVale = new FrmInformeVale(this, session, false);
		fInformeVale.setLocationRelativeTo(null);
		fInformeVale.setVisible(true);
	}

	private void openInformePreLiquidacion() {
		if (fInformePreLiquidacion != null) {
			fInformePreLiquidacion.dispose();
		}
		fInformePreLiquidacion = new FrmInformePreLiquidacion(this, session,
				false);
		fInformePreLiquidacion.setLocationRelativeTo(null);
		fInformePreLiquidacion.setVisible(true);
	}

	private void openInformeFacturaLiquidacion() {
		if (fInformeFacturaLiquidacion != null) {
			fInformeFacturaLiquidacion.dispose();
		}
		fInformeFacturaLiquidacion = new FrmInformeFacturasLiquidacion(this,
				session, false);
		fInformeFacturaLiquidacion.setLocationRelativeTo(null);
		fInformeFacturaLiquidacion.setVisible(true);
	}
        
        private void openInformeFacturaLiquidacionRetorno() {
		if (fInformeFacturaLiquidacionRetorno != null) {
			fInformeFacturaLiquidacionRetorno.dispose();
		}
		fInformeFacturaLiquidacionRetorno = new FrmInformeFacturasLiquidacionRetorno(this,
				session, false);
		fInformeFacturaLiquidacionRetorno.setLocationRelativeTo(null);
		fInformeFacturaLiquidacionRetorno.setVisible(true);
	}
        
        private void openCartaCosecheros() {
		if (fCartaCosecheros != null) {
			fCartaCosecheros.dispose();
		}
		fCartaCosecheros = new FrmCartaCosecheros(this,
				session, false);
		fCartaCosecheros.setLocationRelativeTo(null);
		fCartaCosecheros.setVisible(true);
	}
        
	private void openInformeResumenLiquidacion() {
		if (fInformeResumenLiquidacion != null) {
			fInformeResumenLiquidacion.dispose();
		}
		fInformeResumenLiquidacion = new FrmInformeResumenLiquidacion(this,
				session, false);
		fInformeResumenLiquidacion.setLocationRelativeTo(null);
		fInformeResumenLiquidacion.setVisible(true);
	}

	private void openInformeControlESAlmacen() {
		if (fControlESAlmacen != null) {
			fControlESAlmacen.dispose();
		}
		fControlESAlmacen = new FrmInformeControlESAlmacen(this, session, false);
		fControlESAlmacen.setLocationRelativeTo(null);
		fControlESAlmacen.setVisible(true);
	}

	private void openInformeControlProduccionZonas() {
		if (fControlProduccionZonas != null) {
			fControlProduccionZonas.dispose();
		}
		fControlProduccionZonas = new FrmInformeControlProduccionZonas(this,
				session, false);
		fControlProduccionZonas.setLocationRelativeTo(null);
		fControlProduccionZonas.setVisible(true);
	}

	private void openInformeListadoPersonalBanco() {
		if (fListadoPersonalBanco != null) {
			fListadoPersonalBanco.dispose();
		}
		fListadoPersonalBanco = new FrmInformeListadoPersonalBanco(this,
				session, false);
		fListadoPersonalBanco.setLocationRelativeTo(null);
		fListadoPersonalBanco.setVisible(true);
	}

	private void openInformeRentabilidad() {
		if (fInformeRentabilidad != null) {
			fInformeRentabilidad.dispose();
		}
		fInformeRentabilidad = new FrmInformeRentabilidad(this,
				session, false);
		fInformeRentabilidad.setLocationRelativeTo(null);
		fInformeRentabilidad.setVisible(true);
	}

	private void openFormBackup() {
		if (fBackup != null) {
			fBackup.dispose();
		}
		fBackup = new FrmBackup(this, true);
		fBackup.setLocationRelativeTo(null);
		fBackup.setVisible(true);
	}

	private void openFormContaLiquidaciones() {
		if (fContaLiquidaciones != null) {
			fContaLiquidaciones.dispose();
		}
		fContaLiquidaciones = new FrmContaLiquidaciones(this, session);
		desktopPane.removeAll();
		desktopPane.add(fContaLiquidaciones, BorderLayout.CENTER);
		fContaLiquidaciones.setSize(desktopPane.getWidth(), desktopPane
				.getHeight());
		fContaLiquidaciones.setBackground(PreferencesUI.DesktopBackgroundColor);
		fContaLiquidaciones.setVisible(true);
	}

	private void openFormContaNominas() {
		if (fContaNominas != null) {
			fContaNominas.dispose();
		}
		fContaNominas = new FrmContaNominas(this, session);
		desktopPane.removeAll();
		desktopPane.add(fContaNominas, BorderLayout.CENTER);
		fContaNominas.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fContaNominas.setBackground(PreferencesUI.DesktopBackgroundColor);
		fContaNominas.setVisible(true);
	}

	private void openFormContaPagos() {
		if (fContaPagos != null) {
			fContaPagos.dispose();
		}
		fContaPagos = new FrmContaPagos(this, session);
		desktopPane.removeAll();
		desktopPane.add(fContaPagos, BorderLayout.CENTER);
		fContaPagos.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fContaPagos.setBackground(PreferencesUI.DesktopBackgroundColor);
		fContaPagos.setVisible(true);
	}

	private void openFormContaCobros() {
		if (fContaCobros != null) {
			fContaCobros.dispose();
		}
		fContaCobros = new FrmContaCobros(this, session);
		desktopPane.removeAll();
		desktopPane.add(fContaCobros, BorderLayout.CENTER);
		fContaCobros.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fContaCobros.setBackground(PreferencesUI.DesktopBackgroundColor);
		fContaCobros.setVisible(true);
	}

	private void openFormContaLiquidacionesPagos() {
		if (fContaLiquidacionesPagos != null) {
			fContaLiquidacionesPagos.dispose();
		}
		fContaLiquidacionesPagos = new FrmContaLiquidacionesPagos(this, session);
		desktopPane.removeAll();
		desktopPane.add(fContaLiquidacionesPagos, BorderLayout.CENTER);
		fContaLiquidacionesPagos.setSize(desktopPane.getWidth(), desktopPane
				.getHeight());
		fContaLiquidacionesPagos
				.setBackground(PreferencesUI.DesktopBackgroundColor);
		fContaLiquidacionesPagos.setVisible(true);
	}

    private javax.swing.JMenuItem BackupManuItem;
    private javax.swing.JMenu BimestresMenuItem;
    private javax.swing.JMenu CalendarioMenuItem;
    private javax.swing.JMenu CategoriasMenuItem;
    private javax.swing.JMenuItem ConsultarLiquidacionesMenuItem;
    private javax.swing.JMenuItem ContaCobrosMenuItem;
    private javax.swing.JMenuItem ContaLiquidacionesMenuItem;
    private javax.swing.JMenuItem ContaLiquidacionesPagosItem;
    private javax.swing.JMenuItem ContaNominasMenuItem;
    private javax.swing.JMenuItem ContaPagosMenuItem;
    private javax.swing.JMenu ContabilizarMenuItem;
    private javax.swing.JMenu EntradasMenuItem;
    private javax.swing.JMenu ExitMenuItem;
    private javax.swing.JMenuItem GeneraLiquidacionMenuiItem;
    private javax.swing.JMenuItem GenerarLiquidacionRetornoMenuItem;
    private javax.swing.JMenuItem GenerarLiquidacionRetornoPorCosecheroMenuItem;
    private javax.swing.JMenu InformesMenu;
    private javax.swing.JMenu LiquidacionesMenuItem;
    private javax.swing.JMenuItem PrintCartaCosecherosItem;
    private javax.swing.JMenuItem PrintControlESAlmacenMenuItem;
    private javax.swing.JMenuItem PrintControlProduccionZonasMenuItem;
    private javax.swing.JMenuItem PrintFacturaLiquidacionItem;
    private javax.swing.JMenuItem PrintFacturaLiquidacionRetornoItem;
    private javax.swing.JMenuItem PrintListadoNominas;
    private javax.swing.JMenuItem PrintListadoPersonal;
    private javax.swing.JMenuItem PrintListadoPersonalBanco;
    private javax.swing.JMenuItem PrintListadoPersonalSalarioMedio;
    private javax.swing.JMenuItem PrintPreliquidacionItem;
    private javax.swing.JMenuItem PrintRentabilidadItem;
    private javax.swing.JMenuItem PrintResumenLiquidacion;
    private javax.swing.JMenu ToolsMenuItem;
    private javax.swing.JMenu VentasMenuItem;
    private javax.swing.JMenuItem aboutMenuItem;
    private static javax.swing.JComboBox cboEjercicios;
    private static javax.swing.JComboBox cboEmpresas;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lnlEmpresa;
    private javax.swing.JLabel lnlEmpresa1;
    private javax.swing.JMenuBar menuBar;

}
