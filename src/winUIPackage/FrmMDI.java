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

	private FrmBancos fBancos;
	private FrmBarcos fBarcos;
	private FrmCalendario fCalendario;
	private FrmBimestres fBimestre;
	private FrmCategorias fCategoria;
	private FrmConceptos fConcepto;
	private FrmCosecheros fCosechero;
	private FrmIdentidades fIdentidad;
	private FrmConductores fConductor;
	private FrmEmpresas fEmpresa;
	private FrmEjercicios fEjercicio;
	private FrmReceptores fReceptor;
	private FrmZonas fZonas;
	private FrmVehiculos fVehiculos;
	private FrmEntradas fEntradas;
	private FrmVentas fVentas;
	private FrmEmpleados fEmpleado;
	private FrmFacturas fFactura;
	private FrmTiposGasto fTipogasto;
	private FrmFacturasPago fFacturaPago;
	private FrmLiquidaciones fLiquidacion;
	private FrmDataQuery fDataQuery;
	private FrmGeneraLiquidacion fGeneraLiquidacion;
    private FrmGeneraLiquidacionRetorno fGeneraLiquidacionRetorno;
    private FrmGeneraLiquidacionRetornoPorCosechero fGeneraLiquidacionRetornoPorCosechero;
	private FrmInformeVale fInformeVale;
	private FrmInformeFacturas fInformeFactura;
	private FrmInformePreLiquidacion fInformePreLiquidacion;
	private FrmInformeFacturasLiquidacion fInformeFacturaLiquidacion;
    private FrmInformeFacturasLiquidacionRetorno fInformeFacturaLiquidacionRetorno;
	private FrmInformeResumenLiquidacion fInformeResumenLiquidacion;
	private FrmInformeKilosInutilizados fInformeKilosInutilizados;
	private FrmInformeIGIC fInformeIGIC;
	private FrmInformeIRPF fInformeIRPF;
	private FrmInformeAyudasOCM fInformeAyudasOCM;
	private FrmControlCalidad fControlCalidad;
	private FrmInformeControlESAlmacen fControlESAlmacen;
	private FrmInformeControlProduccionZonas fControlProduccionZonas;
	private FrmInformeListadoPersonalBanco fListadoPersonalBanco;
	private FrmListadoPersonalSalarioMedio fListadoPersonalSalarioMedio;
	private FrmInformeListadoNominas fListadoNominas;
	private FrmListadoCosecherosKilos fListadoCosecherosKilos;
	private FrmListadoCategoriasKilosPorCosechero fListadoCategoriasKilosPorCosechero;
	private FrmInformeRentabilidad fInformeRentabilidad;
	private FrmBackup fBackup;
	private FrmImportarVales fImportarVales;
	private FrmContaLiquidaciones fContaLiquidaciones;
	private FrmContaNominas fContaNominas;
	private FrmContaFacturas fContaFacturas;
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
			//inicializamos el objeto para agilizar la carga
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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        cboEmpresas = new javax.swing.JComboBox();
        lnlEmpresa = new javax.swing.JLabel();
        lnlEmpresa1 = new javax.swing.JLabel();
        cboEjercicios = new javax.swing.JComboBox();
        menuBar = new javax.swing.JMenuBar();
        EmpresasMenuItem = new javax.swing.JMenu();
        EjercicioMenuItem = new javax.swing.JMenu();
        CalendarioMenuItem = new javax.swing.JMenu();
        BimestresMenuItem = new javax.swing.JMenu();
        CategoriasMenuItem = new javax.swing.JMenu();
        ConceptosMenuItem = new javax.swing.JMenu();
        CosecherosMenuItem = new javax.swing.JMenu();
        ReceptoresMenuItem = new javax.swing.JMenu();
        bancosMenuItem = new javax.swing.JMenu();
        barcosMenuItem = new javax.swing.JMenu();
        ConductoresMenuItem = new javax.swing.JMenu();
        VehiculosMenu = new javax.swing.JMenu();
        VehiculosMenuItem = new javax.swing.JMenuItem();
        TipoGastoMenuItem = new javax.swing.JMenuItem();
        IdentidadesMenuItem = new javax.swing.JMenu();
        ZonasMenuItem = new javax.swing.JMenu();
        EmpleadosMenuItem = new javax.swing.JMenu();
        EntradasMenuItem = new javax.swing.JMenu();
        VentasMenuItem = new javax.swing.JMenu();
        QueryMenuItem = new javax.swing.JMenu();
        LiquidacionesMenuItem = new javax.swing.JMenu();
        GeneraLiquidacionMenuiItem = new javax.swing.JMenuItem();
        ConsultarLiquidacionesMenuItem = new javax.swing.JMenuItem();
        GenerarLiquidacionRetornoMenuItem = new javax.swing.JMenuItem();
        GenerarLiquidacionRetornoPorCosecheroMenuItem = new javax.swing.JMenuItem();
        FacturasMenuItem = new javax.swing.JMenu();
        FacturasPagoMenuItem = new javax.swing.JMenu();
        InformesMenu = new javax.swing.JMenu();
        PrintListadoCosecheros = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        PrintValeEntradaItem = new javax.swing.JMenuItem();
        PrintRelacionCosecherosKilos = new javax.swing.JMenuItem();
        PrintListadoCategoriasKilosPorCosechero = new javax.swing.JMenuItem();
        PrintKilosInutilizadosItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        PrintPreliquidacionItem = new javax.swing.JMenuItem();
        PrintFacturaLiquidacionItem = new javax.swing.JMenuItem();
        PrintResumenLiquidacion = new javax.swing.JMenuItem();
        PrintIGICItem = new javax.swing.JMenuItem();
        PrintIRPFItem = new javax.swing.JMenuItem();
        PrintFacturaLiquidacionRetornoItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        PrintFacturaItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        PrintControlESAlmacenMenuItem = new javax.swing.JMenuItem();
        PrintControlProduccionZonasMenuItem = new javax.swing.JMenuItem();
        PrintControlCalidadItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        PrintListadoPersonal = new javax.swing.JMenuItem();
        PrintListadoPersonalBanco = new javax.swing.JMenuItem();
        PrintListadoPersonalSalarioMedio = new javax.swing.JMenuItem();
        PrintListadoNominas = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        PrintAyudasOCMItem = new javax.swing.JMenuItem();
        PrintRentabilidadItem = new javax.swing.JMenuItem();
        PrintCartaCosecherosItem = new javax.swing.JMenuItem();
        ToolsMenuItem = new javax.swing.JMenu();
        BackupManuItem = new javax.swing.JMenuItem();
        ImportarValesMenuItem = new javax.swing.JMenuItem();
        ContabilizarMenuItem = new javax.swing.JMenu();
        ContaLiquidacionesMenuItem = new javax.swing.JMenuItem();
        ContaLiquidacionesPagosItem = new javax.swing.JMenuItem();
        ContaNominasMenuItem = new javax.swing.JMenuItem();
        ContaFacturasMenuItem = new javax.swing.JMenuItem();
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

        cboEmpresas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboEmpresas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboEmpresas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboEmpresas.setFocusable(false);
        cboEmpresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpresasActionPerformed(evt);
            }
        });

        lnlEmpresa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lnlEmpresa.setText("Empresa");

        lnlEmpresa1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lnlEmpresa1.setText("Ejercicio");

        cboEjercicios.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboEjercicios.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboEjercicios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cboEjercicios.setFocusable(false);
        cboEjercicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEjerciciosActionPerformed(evt);
            }
        });

        EmpresasMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EmpresasMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Empresas.png"))); // NOI18N
        EmpresasMenuItem.setToolTipText("Empresas");
        EmpresasMenuItem.setBorderPainted(true);
        EmpresasMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EmpresasMenuItemMousePressed(evt);
            }
        });
        menuBar.add(EmpresasMenuItem);

        EjercicioMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EjercicioMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/ejercicio.png"))); // NOI18N
        EjercicioMenuItem.setToolTipText("Ejercicios");
        EjercicioMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EjercicioMenuItemMousePressed(evt);
            }
        });
        menuBar.add(EjercicioMenuItem);

        CalendarioMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CalendarioMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/calendario.png"))); // NOI18N
        CalendarioMenuItem.setToolTipText("Calendario y precios");
        CalendarioMenuItem.setBorderPainted(true);
        CalendarioMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CalendarioMenuItemMousePressed(evt);
            }
        });
        menuBar.add(CalendarioMenuItem);

        BimestresMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BimestresMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/bimestre.png"))); // NOI18N
        BimestresMenuItem.setToolTipText("Bimestres");
        BimestresMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BimestresMenuItemMousePressed(evt);
            }
        });
        menuBar.add(BimestresMenuItem);

        CategoriasMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CategoriasMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/categoria.png"))); // NOI18N
        CategoriasMenuItem.setToolTipText("Categorï¿½as");
        CategoriasMenuItem.setBorderPainted(true);
        CategoriasMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CategoriasMenuItemMousePressed(evt);
            }
        });
        menuBar.add(CategoriasMenuItem);
        
        ConceptosMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ConceptosMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/concepto.png"))); // NOI18N
        ConceptosMenuItem.setToolTipText("Conceptos");
        ConceptosMenuItem.setBorderPainted(true);
        ConceptosMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ConceptosMenuItemMousePressed(evt);
            }
        });
        menuBar.add(ConceptosMenuItem);

        CosecherosMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CosecherosMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Cosecheros.png"))); // NOI18N
        CosecherosMenuItem.setToolTipText("Cosecheros");
        CosecherosMenuItem.setBorderPainted(true);
        CosecherosMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CosecherosMenuItemMousePressed(evt);
            }
        });
        menuBar.add(CosecherosMenuItem);

        ReceptoresMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ReceptoresMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Receptores.png"))); // NOI18N
        ReceptoresMenuItem.setToolTipText("Receptores");
        ReceptoresMenuItem.setBorderPainted(true);
        ReceptoresMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ReceptoresMenuItemMousePressed(evt);
            }
        });
        menuBar.add(ReceptoresMenuItem);

        bancosMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bancosMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Banco.png"))); // NOI18N
        bancosMenuItem.setToolTipText("Bancos");
        bancosMenuItem.setBorderPainted(true);
        bancosMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bancosMenuItemMousePressed(evt);
            }
        });
        menuBar.add(bancosMenuItem);

        barcosMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        barcosMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Barco.png"))); // NOI18N
        barcosMenuItem.setToolTipText("Barcos");
        barcosMenuItem.setBorderPainted(true);
        barcosMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                barcosMenuItemMousePressed(evt);
            }
        });
        menuBar.add(barcosMenuItem);

        ConductoresMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ConductoresMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Conductores.png"))); // NOI18N
        ConductoresMenuItem.setToolTipText("Conductores");
        ConductoresMenuItem.setBorderPainted(true);
        ConductoresMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ConductoresMenuItemMousePressed(evt);
            }
        });
        menuBar.add(ConductoresMenuItem);

        VehiculosMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VehiculosMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Vehiculos.png"))); // NOI18N
        VehiculosMenu.setToolTipText("Vehï¿½culos");
        VehiculosMenu.setBorderPainted(true);

        VehiculosMenuItem.setText("Vehï¿½culos");
        VehiculosMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                VehiculosMenuItemMousePressed(evt);
            }
        });
        VehiculosMenu.add(VehiculosMenuItem);

        TipoGastoMenuItem.setText("Tipos de gastos");
        TipoGastoMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TipoGastoMenuItemMousePressed(evt);
            }
        });
        VehiculosMenu.add(TipoGastoMenuItem);

        menuBar.add(VehiculosMenu);

        IdentidadesMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        IdentidadesMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/identity.png"))); // NOI18N
        IdentidadesMenuItem.setToolTipText("Identidades");
        IdentidadesMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IdentidadesMenuItemMousePressed(evt);
            }
        });
        menuBar.add(IdentidadesMenuItem);

        ZonasMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ZonasMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Zonas.png"))); // NOI18N
        ZonasMenuItem.setToolTipText("Zonas");
        ZonasMenuItem.setBorderPainted(true);
        ZonasMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ZonasMenuItemMousePressed(evt);
            }
        });
        menuBar.add(ZonasMenuItem);

        EmpleadosMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EmpleadosMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/empleado.png"))); // NOI18N
        EmpleadosMenuItem.setToolTipText("Empleados");
        EmpleadosMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EmpleadosMenuItemMousePressed(evt);
            }
        });
        menuBar.add(EmpleadosMenuItem);

        EntradasMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EntradasMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/entradas.png"))); // NOI18N
        EntradasMenuItem.setToolTipText("Entradas");
        EntradasMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EntradasMenuItemMousePressed(evt);
            }
        });
        menuBar.add(EntradasMenuItem);

        VentasMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VentasMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/salidas.png"))); // NOI18N
        VentasMenuItem.setToolTipText("Ventas");
        VentasMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                VentasMenuItemMousePressed(evt);
            }
        });
        menuBar.add(VentasMenuItem);

        QueryMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        QueryMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Consulta.png"))); // NOI18N
        QueryMenuItem.setToolTipText("Consultas");
        QueryMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                QueryMenuItemMousePressed(evt);
            }
        });
        menuBar.add(QueryMenuItem);

        LiquidacionesMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LiquidacionesMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/liquidacion.png"))); // NOI18N
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

        FacturasMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        FacturasMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Factura.png"))); // NOI18N
        FacturasMenuItem.setToolTipText("Facturas de ventas y servicios");
        FacturasMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                FacturasMenuItemMousePressed(evt);
            }
        });
        menuBar.add(FacturasMenuItem);

        FacturasPagoMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        FacturasPagoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/FacturaPago.png"))); // NOI18N
        FacturasPagoMenuItem.setToolTipText("Facturas de compras y gastos");
        FacturasPagoMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                FacturasPagoMenuItemMousePressed(evt);
            }
        });
        menuBar.add(FacturasPagoMenuItem);

        InformesMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        InformesMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Informes.png"))); // NOI18N
        InformesMenu.setToolTipText("Informes");
        InformesMenu.setBorderPainted(true);

        PrintListadoCosecheros.setText("Listado de cosecheros");
        PrintListadoCosecheros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintListadoCosecherosActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintListadoCosecheros);
        InformesMenu.add(jSeparator6);

        PrintValeEntradaItem.setText("Vale de entrada");
        PrintValeEntradaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintValeEntradaItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintValeEntradaItem);

        PrintRelacionCosecherosKilos.setText("Relaciï¿½n de cosecheros y kilos");
        PrintRelacionCosecherosKilos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintRelacionCosecherosKilosActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintRelacionCosecherosKilos);
        
        PrintListadoCategoriasKilosPorCosechero.setText("Relaciï¿½n de kilos por campaï¿½a");
        PrintListadoCategoriasKilosPorCosechero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	PrintListadoCategoriasKilosPorCosecheroActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintListadoCategoriasKilosPorCosechero);

        PrintKilosInutilizadosItem.setText("Relaciï¿½n de kilos inutilizados");
        PrintKilosInutilizadosItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintKilosInutilizadosItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintKilosInutilizadosItem);
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

        PrintIGICItem.setText("Relaciï¿½n de I.G.I.C.");
        PrintIGICItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintIGICItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintIGICItem);

        PrintIRPFItem.setText("Relaciï¿½n de I.R.P.F.");
        PrintIRPFItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintIRPFItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintIRPFItem);

        PrintFacturaLiquidacionRetornoItem.setText("Facturas de liquidación Retorno");
        PrintFacturaLiquidacionRetornoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintFacturaLiquidacionRetornoItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintFacturaLiquidacionRetornoItem);
        InformesMenu.add(jSeparator2);

        PrintFacturaItem.setText("Facturas de servicios");
        PrintFacturaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintFacturaItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintFacturaItem);
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

        PrintControlCalidadItem.setText("Control de calidad");
        PrintControlCalidadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintControlCalidadItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintControlCalidadItem);
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
        
        PrintListadoNominas.setText("Listado de nï¿½minas");
        PrintListadoNominas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintListadoNominasActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintListadoNominas);
        InformesMenu.add(jSeparator5);

        PrintAyudasOCMItem.setText("Ayudas O.C.M.");
        PrintAyudasOCMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintAyudasOCMItemActionPerformed(evt);
            }
        });
        InformesMenu.add(PrintAyudasOCMItem);

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
        ToolsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/tools.png"))); // NOI18N
        ToolsMenuItem.setToolTipText("Herramientas");

        BackupManuItem.setText("Copia de seguridad");
        BackupManuItem.setToolTipText("Realiza la copia  de seguridad de la base de datos");
        BackupManuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackupManuItemActionPerformed(evt);
            }
        });
        ToolsMenuItem.add(BackupManuItem);

        ImportarValesMenuItem.setText("Importaciï¿½n de vales");
        ImportarValesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportarValesMenuItemActionPerformed(evt);
            }
        });
        ToolsMenuItem.add(ImportarValesMenuItem);

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

        ContaFacturasMenuItem.setText("Facturas de ventas y servicios");
        ContaFacturasMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContaFacturasMenuItemActionPerformed(evt);
            }
        });
        ContabilizarMenuItem.add(ContaFacturasMenuItem);

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
        helpMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Ayuda.png"))); // NOI18N
        helpMenu.setToolTipText("Ayuda");
        helpMenu.setBorderPainted(true);

        contentMenuItem.setText("Contenido");
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setText("Acerca de");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        ExitMenuItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ExitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesPackage/Salir.png"))); // NOI18N
        ExitMenuItem.setToolTipText("Salir de la aplicaciï¿½n");
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
    }// </editor-fold>//GEN-END:initComponents

    private void GenerarLiquidacionRetornoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarLiquidacionRetornoMenuItemActionPerformed
        // TODO add your handling code here:

		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormGeneraLiquidacionRetorno();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
    }//GEN-LAST:event_GenerarLiquidacionRetornoMenuItemActionPerformed

    private void GenerarLiquidacionRetornoPorCosecheroMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarLiquidacionRetornoPorCosecheroMenuItemActionPerformed
        // TODO add your handling code here:

		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormGeneraLiquidacionRetornoPorCosechero();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
    }//GEN-LAST:event_GenerarLiquidacionRetornoPorCosecheroMenuItemActionPerformed
    
    private void PrintFacturaLiquidacionRetornoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintFacturaLiquidacionRetornoItemActionPerformed
        // TODO add your handling code here:
        Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeFacturaLiquidacionRetorno();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
    }//GEN-LAST:event_PrintFacturaLiquidacionRetornoItemActionPerformed

    private void PrintCartaCosecherosItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintCartaCosecherosItemActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openCartaCosecheros();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
    }//GEN-LAST:event_PrintCartaCosecherosItemActionPerformed

	private void VehiculosMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		//Create a panel and add components to it.
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormVehiculos();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void TipoGastoMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		//Create a panel and add components to it.
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormTiposGastoVehiculo();
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

	private void ContaFacturasMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormContaFacturas();
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

	private void ImportarValesMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormImportarVales();
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

	private void QueryMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormDataQuery();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
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
        

	private void FacturasMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		//Create a panel and add components to it.
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormFacturas();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void FacturasPagoMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		//Create a panel and add components to it.
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormFacturasPago();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void EmpleadosMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		//Create a panel and add components to it.
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormEmpleados();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void BimestresMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		//Create a panel and add components to it.
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormBimestres();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void EjercicioMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormEjercicios();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void IdentidadesMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormIdentidades();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void EmpresasMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormEmpresas();
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

	private void CosecherosMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormCosecheros();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void ReceptoresMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormReceptores();
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

	private void bancosMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormBancos();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void barcosMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormBarcos();
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
	
	private void ConceptosMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormConceptos();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void ConductoresMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormConductores();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void ZonasMenuItemMousePressed(java.awt.event.MouseEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openFormZonas();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintControlCalidadItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openControlCalidad();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintRelacionCosecherosKilosActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openListadoCosecherosKilos();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}
	
	private void PrintListadoCategoriasKilosPorCosecheroActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openListadoCategoriasKilosPorCosechero();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintListadoCosecherosActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		ListadoCosecheros report = new ListadoCosecheros(this);
		report.runReporte(session.getEmpresa().getIdEmpresa(), session
				.getEjercicio().getEjercicio());
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

	private void PrintValeEntradaItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeVale();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintFacturaItemActionPerformed(java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeFactura();
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

	private void PrintKilosInutilizadosItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeKilosInutilizados();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintIRPFItemActionPerformed(java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeIRPF();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintIGICItemActionPerformed(java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeIGIC();
		Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(normalCursor);
	}

	private void PrintAyudasOCMItemActionPerformed(
			java.awt.event.ActionEvent evt) {
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		setCursor(hourglassCursor);
		openInformeAyudasOCM();
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

	private void openFormBancos() {
		//Create a panel and add components to it.
		if (fBancos != null) {
			fBancos.dispose();
		}
		fBancos = new FrmBancos(this, session);
		desktopPane.removeAll();
		desktopPane.add(fBancos, BorderLayout.CENTER);
		fBancos.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fBancos.setBackground(PreferencesUI.DesktopBackgroundColor);
		fBancos.setVisible(true);
	}

	private void openFormBarcos() {
		//Create a panel and add components to it.
		if (fBarcos != null) {
			fBarcos.dispose();
		}
		fBarcos = new FrmBarcos(this, session);
		desktopPane.removeAll();
		desktopPane.add(fBarcos, BorderLayout.CENTER);
		fBarcos.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fBarcos.setBackground(PreferencesUI.DesktopBackgroundColor);
		fBarcos.setVisible(true);
	}

	private void openFormCalendario() {
		//Create a panel and add components to it.
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
		//Create a panel and add components to it.
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
		//Create a panel and add components to it.
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
	
	private void openFormConceptos() {
		//Create a panel and add components to it.
		if (fConcepto != null) {
			fConcepto.dispose();
		}
		fConcepto = new FrmConceptos(this, session);
		desktopPane.removeAll();
		desktopPane.add(fConcepto, BorderLayout.CENTER);
		fConcepto.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fConcepto.setBackground(PreferencesUI.DesktopBackgroundColor);
		fConcepto.setVisible(true);
	}

	private void openFormConductores() {
		//Create a panel and add components to it.
		if (fConductor != null) {
			fConductor.dispose();
		}
		fConductor = new FrmConductores(this, session);
		desktopPane.removeAll();
		desktopPane.add(fConductor, BorderLayout.CENTER);
		fConductor.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fConductor.setBackground(PreferencesUI.DesktopBackgroundColor);
		fConductor.setVisible(true);
	}

	private void openFormCosecheros() {
		//Create a panel and add components to it.
		if (fCosechero != null) {
			fCosechero.dispose();
		}
		fCosechero = new FrmCosecheros(this, session);
		desktopPane.removeAll();
		desktopPane.add(fCosechero, BorderLayout.CENTER);
		fCosechero.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fCosechero.setBackground(PreferencesUI.DesktopBackgroundColor);
		fCosechero.setVisible(true);
	}

	private void openFormIdentidades() {
		//Create a panel and add components to it.
		if (fIdentidad != null) {
			fIdentidad.dispose();
		}
		fIdentidad = new FrmIdentidades(this, session);
		desktopPane.removeAll();
		desktopPane.add(fIdentidad, BorderLayout.CENTER);
		fIdentidad.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fIdentidad.setBackground(PreferencesUI.DesktopBackgroundColor);
		fIdentidad.setVisible(true);
	}

	private void openFormEmpresas() {
		//Create a panel and add components to it.
		if (fEmpresa != null) {
			fEmpresa.dispose();
		}
		fEmpresa = new FrmEmpresas(this, session);
		desktopPane.removeAll();
		desktopPane.add(fEmpresa, BorderLayout.CENTER);
		fEmpresa.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fEmpresa.setBackground(PreferencesUI.DesktopBackgroundColor);
		fEmpresa.setVisible(true);
	}

	private void openFormEjercicios() {
		//Create a panel and add components to it.
		if (fEjercicio != null) {
			fEjercicio.dispose();
		}
		fEjercicio = new FrmEjercicios(this, session);
		desktopPane.removeAll();
		desktopPane.add(fEjercicio, BorderLayout.CENTER);
		fEjercicio.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fEjercicio.setBackground(PreferencesUI.DesktopBackgroundColor);
		fEjercicio.setVisible(true);
	}

	private void openFormReceptores() {
		//Create a panel and add components to it.
		if (fReceptor != null) {
			fReceptor.dispose();
		}
		fReceptor = new FrmReceptores(this, session);
		desktopPane.removeAll();
		desktopPane.add(fReceptor, BorderLayout.CENTER);
		fReceptor.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fReceptor.setBackground(PreferencesUI.DesktopBackgroundColor);
		fReceptor.setVisible(true);
	}

	private void openFormZonas() {
		//Create a panel and add components to it.
		if (fZonas != null) {
			fZonas.dispose();
		}
		fZonas = new FrmZonas(this, session);
		desktopPane.removeAll();
		desktopPane.add(fZonas, BorderLayout.CENTER);
		fZonas.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fZonas.setBackground(PreferencesUI.DesktopBackgroundColor);
		fZonas.setVisible(true);
	}

	private void openFormVehiculos() {
		//Create a panel and add components to it.
		if (fVehiculos != null) {
			fVehiculos.dispose();
		}
		fVehiculos = new FrmVehiculos(this, session);
		desktopPane.removeAll();
		desktopPane.add(fVehiculos, BorderLayout.CENTER);
		fVehiculos.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fVehiculos.setBackground(PreferencesUI.DesktopBackgroundColor);
		fVehiculos.setVisible(true);
	}

	private void openFormEntradas() {
		//Create a panel and add components to it.
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
		//Create a panel and add components to it.
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

	private void openFormEmpleados() {
		//Create a panel and add components to it.
		if (fEmpleado != null) {
			fEmpleado.dispose();
		}
		fEmpleado = new FrmEmpleados(this, session);
		desktopPane.removeAll();
		desktopPane.add(fEmpleado, BorderLayout.CENTER);
		fEmpleado.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fEmpleado.setBackground(PreferencesUI.DesktopBackgroundColor);
		fEmpleado.setVisible(true);
	}

	private void openFormFacturas() {
		//Create a panel and add components to it.
		if (fFactura != null) {
			fFactura.dispose();
		}
		fFactura = new FrmFacturas(this, session);
		desktopPane.removeAll();
		desktopPane.add(fFactura, BorderLayout.CENTER);
		fFactura.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fFactura.setBackground(PreferencesUI.DesktopBackgroundColor);
		fFactura.setVisible(true);
	}

	private void openFormTiposGastoVehiculo() {
		//Create a panel and add components to it.
		if (fTipogasto != null) {
			fTipogasto.dispose();
		}
		fTipogasto = new FrmTiposGasto(this, session);
		desktopPane.removeAll();
		desktopPane.add(fTipogasto, BorderLayout.CENTER);
		fTipogasto.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fTipogasto.setBackground(PreferencesUI.DesktopBackgroundColor);
		fTipogasto.setVisible(true);
	}

	private void openFormFacturasPago() {
		//Create a panel and add components to it.
		if (fFacturaPago != null) {
			fFacturaPago.dispose();
		}
		fFacturaPago = new FrmFacturasPago(this, session);
		desktopPane.removeAll();
		desktopPane.add(fFacturaPago, BorderLayout.CENTER);
		fFacturaPago.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fFacturaPago.setBackground(PreferencesUI.DesktopBackgroundColor);
		fFacturaPago.setVisible(true);
	}

	private void openFormLiquidaciones() {
		//Create a panel and add components to it.
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
		//Create a panel and add components to it.
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
		//Create a panel and add components to it.
		if (fGeneraLiquidacion != null) {
			fGeneraLiquidacion.dispose();
		}
		fGeneraLiquidacion = new FrmGeneraLiquidacion(this, session, false);
		fGeneraLiquidacion.setLocationRelativeTo(null);
		fGeneraLiquidacion.setVisible(true);
	}
        
    private void openFormGeneraLiquidacionRetorno() {
		//Create a panel and add components to it.
		if (fGeneraLiquidacionRetorno != null) {
			fGeneraLiquidacionRetorno.dispose();
		}
		fGeneraLiquidacionRetorno = new FrmGeneraLiquidacionRetorno(this, session, false);
		fGeneraLiquidacionRetorno.setLocationRelativeTo(null);
		fGeneraLiquidacionRetorno.setVisible(true);
	}
    
    private void openFormGeneraLiquidacionRetornoPorCosechero() {
		//Create a panel and add components to it.
		if (fGeneraLiquidacionRetornoPorCosechero != null) {
			fGeneraLiquidacionRetornoPorCosechero.dispose();
		}
		fGeneraLiquidacionRetornoPorCosechero = new FrmGeneraLiquidacionRetornoPorCosechero(this, session, false);
		fGeneraLiquidacionRetornoPorCosechero.setLocationRelativeTo(null);
		fGeneraLiquidacionRetornoPorCosechero.setVisible(true);
	}

	private void openListadoCosecherosKilos() {
		//Create a panel and add components to it.
		if (fListadoCosecherosKilos != null) {
			fListadoCosecherosKilos.dispose();
		}
		fListadoCosecherosKilos = new FrmListadoCosecherosKilos(this, session,
				false);
		fListadoCosecherosKilos.setLocationRelativeTo(null);
		fListadoCosecherosKilos.setVisible(true);
	}
	
	private void openListadoCategoriasKilosPorCosechero() {
		//Create a panel and add components to it.
		if (fListadoCategoriasKilosPorCosechero != null) {
			fListadoCategoriasKilosPorCosechero.dispose();
		}
		fListadoCategoriasKilosPorCosechero = new FrmListadoCategoriasKilosPorCosechero(this, session,
				false);
		fListadoCategoriasKilosPorCosechero.setLocationRelativeTo(null);
		fListadoCategoriasKilosPorCosechero.setVisible(true);
	}

	private void openListadoNominas() {
		//Create a panel and add components to it.
		if (fListadoNominas != null) {
			fListadoNominas.dispose();
		}
		fListadoNominas = new FrmInformeListadoNominas(this, session, false);
		fListadoNominas.setLocationRelativeTo(null);
		fListadoNominas.setVisible(true);
	}

	private void openInformeVale() {
		//Create a panel and add components to it.
		if (fInformeVale != null) {
			fInformeVale.dispose();
		}
		fInformeVale = new FrmInformeVale(this, session, false);
		fInformeVale.setLocationRelativeTo(null);
		fInformeVale.setVisible(true);
	}

	private void openInformeFactura() {
		//Create a panel and add components to it.
		if (fInformeFactura != null) {
			fInformeFactura.dispose();
		}
		fInformeFactura = new FrmInformeFacturas(this, session, false);
		fInformeFactura.setLocationRelativeTo(null);
		fInformeFactura.setVisible(true);
	}

	private void openInformePreLiquidacion() {
		//Create a panel and add components to it.
		if (fInformePreLiquidacion != null) {
			fInformePreLiquidacion.dispose();
		}
		fInformePreLiquidacion = new FrmInformePreLiquidacion(this, session,
				false);
		fInformePreLiquidacion.setLocationRelativeTo(null);
		fInformePreLiquidacion.setVisible(true);
	}

	private void openInformeFacturaLiquidacion() {
		//Create a panel and add components to it.
		if (fInformeFacturaLiquidacion != null) {
			fInformeFacturaLiquidacion.dispose();
		}
		fInformeFacturaLiquidacion = new FrmInformeFacturasLiquidacion(this,
				session, false);
		fInformeFacturaLiquidacion.setLocationRelativeTo(null);
		fInformeFacturaLiquidacion.setVisible(true);
	}
        
        private void openInformeFacturaLiquidacionRetorno() {
		//Create a panel and add components to it.
		if (fInformeFacturaLiquidacionRetorno != null) {
			fInformeFacturaLiquidacionRetorno.dispose();
		}
		fInformeFacturaLiquidacionRetorno = new FrmInformeFacturasLiquidacionRetorno(this,
				session, false);
		fInformeFacturaLiquidacionRetorno.setLocationRelativeTo(null);
		fInformeFacturaLiquidacionRetorno.setVisible(true);
	}
        
        private void openCartaCosecheros() {
		//Create a panel and add components to it.
		if (fCartaCosecheros != null) {
			fCartaCosecheros.dispose();
		}
		fCartaCosecheros = new FrmCartaCosecheros(this,
				session, false);
		fCartaCosecheros.setLocationRelativeTo(null);
		fCartaCosecheros.setVisible(true);
	}
        
	private void openInformeResumenLiquidacion() {
		//Create a panel and add components to it.
		if (fInformeResumenLiquidacion != null) {
			fInformeResumenLiquidacion.dispose();
		}
		fInformeResumenLiquidacion = new FrmInformeResumenLiquidacion(this,
				session, false);
		fInformeResumenLiquidacion.setLocationRelativeTo(null);
		fInformeResumenLiquidacion.setVisible(true);
	}

	private void openInformeKilosInutilizados() {
		//Create a panel and add components to it.
		if (fInformeKilosInutilizados != null) {
			fInformeKilosInutilizados.dispose();
		}
		fInformeKilosInutilizados = new FrmInformeKilosInutilizados(this,
				session, false);
		fInformeKilosInutilizados.setLocationRelativeTo(null);
		fInformeKilosInutilizados.setVisible(true);
	}

	private void openInformeIGIC() {
		//Create a panel and add components to it.
		if (fInformeIGIC != null) {
			fInformeIGIC.dispose();
		}
		fInformeIGIC = new FrmInformeIGIC(this, session, false);
		fInformeIGIC.setLocationRelativeTo(null);
		fInformeIGIC.setVisible(true);
	}

	private void openInformeIRPF() {
		//Create a panel and add components to it.
		if (fInformeIRPF != null) {
			fInformeIRPF.dispose();
		}
		fInformeIRPF = new FrmInformeIRPF(this, session, false);
		fInformeIRPF.setLocationRelativeTo(null);
		fInformeIRPF.setVisible(true);
	}

	private void openInformeAyudasOCM() {
		//Create a panel and add components to it.
		if (fInformeAyudasOCM != null) {
			fInformeAyudasOCM.dispose();
		}
		fInformeAyudasOCM = new FrmInformeAyudasOCM(this, session, false);
		fInformeAyudasOCM.setLocationRelativeTo(null);
		fInformeAyudasOCM.setVisible(true);
	}

	private void openControlCalidad() {
		//Create a panel and add components to it.
		if (fControlCalidad != null) {
			fControlCalidad.dispose();
		}
		fControlCalidad = new FrmControlCalidad(this, session, false);
		fControlCalidad.setLocationRelativeTo(null);
		fControlCalidad.setVisible(true);
	}

	private void openInformeControlESAlmacen() {
		//Create a panel and add components to it.
		if (fControlESAlmacen != null) {
			fControlESAlmacen.dispose();
		}
		fControlESAlmacen = new FrmInformeControlESAlmacen(this, session, false);
		fControlESAlmacen.setLocationRelativeTo(null);
		fControlESAlmacen.setVisible(true);
	}

	private void openInformeControlProduccionZonas() {
		//Create a panel and add components to it.
		if (fControlProduccionZonas != null) {
			fControlProduccionZonas.dispose();
		}
		fControlProduccionZonas = new FrmInformeControlProduccionZonas(this,
				session, false);
		fControlProduccionZonas.setLocationRelativeTo(null);
		fControlProduccionZonas.setVisible(true);
	}

	private void openInformeListadoPersonalBanco() {
		//Create a panel and add components to it.
		if (fListadoPersonalBanco != null) {
			fListadoPersonalBanco.dispose();
		}
		fListadoPersonalBanco = new FrmInformeListadoPersonalBanco(this,
				session, false);
		fListadoPersonalBanco.setLocationRelativeTo(null);
		fListadoPersonalBanco.setVisible(true);
	}

	private void openInformeRentabilidad() {
		//Create a panel and add components to it.
		if (fInformeRentabilidad != null) {
			fInformeRentabilidad.dispose();
		}
		fInformeRentabilidad = new FrmInformeRentabilidad(this,
				session, false);
		fInformeRentabilidad.setLocationRelativeTo(null);
		fInformeRentabilidad.setVisible(true);
	}

	private void openFormBackup() {
		//Create a panel and add components to it.
		if (fBackup != null) {
			fBackup.dispose();
		}
		fBackup = new FrmBackup(this, true);
		fBackup.setLocationRelativeTo(null);
		fBackup.setVisible(true);
	}

	private void openFormImportarVales() {
		//Create a panel and add components to it.
		if (fImportarVales != null) {
			fImportarVales.dispose();
		}
		fImportarVales = new FrmImportarVales(this, session, true);
		fImportarVales.setLocationRelativeTo(null);
		fImportarVales.setVisible(true);
	}

	private void openFormContaLiquidaciones() {
		//Create a panel and add components to it.
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
		//Create a panel and add components to it.
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

	private void openFormContaFacturas() {
		//Create a panel and add components to it.
		if (fContaFacturas != null) {
			fContaFacturas.dispose();
		}
		fContaFacturas = new FrmContaFacturas(this, session);
		desktopPane.removeAll();
		desktopPane.add(fContaFacturas, BorderLayout.CENTER);
		fContaFacturas.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		fContaFacturas.setBackground(PreferencesUI.DesktopBackgroundColor);
		fContaFacturas.setVisible(true);
	}

	private void openFormContaPagos() {
		//Create a panel and add components to it.
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
		//Create a panel and add components to it.
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
		//Create a panel and add components to it.
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem BackupManuItem;
    private javax.swing.JMenu BimestresMenuItem;
    private javax.swing.JMenu CalendarioMenuItem;
    private javax.swing.JMenu CategoriasMenuItem;
    private javax.swing.JMenu ConceptosMenuItem;
    private javax.swing.JMenu ConductoresMenuItem;
    private javax.swing.JMenuItem ConsultarLiquidacionesMenuItem;
    private javax.swing.JMenuItem ContaCobrosMenuItem;
    private javax.swing.JMenuItem ContaFacturasMenuItem;
    private javax.swing.JMenuItem ContaLiquidacionesMenuItem;
    private javax.swing.JMenuItem ContaLiquidacionesPagosItem;
    private javax.swing.JMenuItem ContaNominasMenuItem;
    private javax.swing.JMenuItem ContaPagosMenuItem;
    private javax.swing.JMenu ContabilizarMenuItem;
    private javax.swing.JMenu CosecherosMenuItem;
    private javax.swing.JMenu EjercicioMenuItem;
    private javax.swing.JMenu EmpleadosMenuItem;
    private javax.swing.JMenu EmpresasMenuItem;
    private javax.swing.JMenu EntradasMenuItem;
    private javax.swing.JMenu ExitMenuItem;
    private javax.swing.JMenu FacturasMenuItem;
    private javax.swing.JMenu FacturasPagoMenuItem;
    private javax.swing.JMenuItem GeneraLiquidacionMenuiItem;
    private javax.swing.JMenuItem GenerarLiquidacionRetornoMenuItem;
    private javax.swing.JMenuItem GenerarLiquidacionRetornoPorCosecheroMenuItem;
    private javax.swing.JMenu IdentidadesMenuItem;
    private javax.swing.JMenuItem ImportarValesMenuItem;
    private javax.swing.JMenu InformesMenu;
    private javax.swing.JMenu LiquidacionesMenuItem;
    private javax.swing.JMenuItem PrintAyudasOCMItem;
    private javax.swing.JMenuItem PrintCartaCosecherosItem;
    private javax.swing.JMenuItem PrintControlCalidadItem;
    private javax.swing.JMenuItem PrintControlESAlmacenMenuItem;
    private javax.swing.JMenuItem PrintControlProduccionZonasMenuItem;
    private javax.swing.JMenuItem PrintFacturaItem;
    private javax.swing.JMenuItem PrintFacturaLiquidacionItem;
    private javax.swing.JMenuItem PrintFacturaLiquidacionRetornoItem;
    private javax.swing.JMenuItem PrintIGICItem;
    private javax.swing.JMenuItem PrintIRPFItem;
    private javax.swing.JMenuItem PrintKilosInutilizadosItem;
    private javax.swing.JMenuItem PrintListadoCosecheros;
    private javax.swing.JMenuItem PrintListadoNominas;
    private javax.swing.JMenuItem PrintListadoPersonal;
    private javax.swing.JMenuItem PrintListadoPersonalBanco;
    private javax.swing.JMenuItem PrintListadoPersonalSalarioMedio;
    private javax.swing.JMenuItem PrintPreliquidacionItem;
    private javax.swing.JMenuItem PrintRelacionCosecherosKilos;
    private javax.swing.JMenuItem PrintListadoCategoriasKilosPorCosechero;
    private javax.swing.JMenuItem PrintRentabilidadItem;
    private javax.swing.JMenuItem PrintResumenLiquidacion;
    private javax.swing.JMenuItem PrintValeEntradaItem;
    private javax.swing.JMenu QueryMenuItem;
    private javax.swing.JMenu ReceptoresMenuItem;
    private javax.swing.JMenuItem TipoGastoMenuItem;
    private javax.swing.JMenu ToolsMenuItem;
    private javax.swing.JMenu VehiculosMenu;
    private javax.swing.JMenuItem VehiculosMenuItem;
    private javax.swing.JMenu VentasMenuItem;
    private javax.swing.JMenu ZonasMenuItem;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu bancosMenuItem;
    private javax.swing.JMenu barcosMenuItem;
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
    // End of variables declaration//GEN-END:variables

}