package entitiesPackage;

public class EntityType {
	
	final public static int indexBancos = 1;
	final public static int indexBarcos = 2;
	final public static int indexCalendario = 3;
	final public static int indexCategorias = 4;
	final public static int indexConductores = 5;
	final public static int indexCosecheros = 6;
	final public static int indexEmpresas = 7;
	final public static int indexReceptores = 8;
	final public static int indexEjercicios = 9;
	final public static int indexZonas = 10;
	final public static int indexVehiculos = 11;
	final public static int indexEntradas = 12;
	final public static int indexVentas = 13;
	final public static int indexIdentidades = 14;
	final public static int indexBimestres = 15;
	final public static int indexEmpleados = 16;
	final public static int indexFacturas = 17;
	final public static int indexLiquidaciones = 18;
	final public static int indexFacturasPago = 19;
	final public static int indexTiposGasto = 20;
	final public static int indexConceptos = 21;
	
	
	final public static int SelectStateWidth = 25;
	final public static int DateWidth = 100;
	final public static int IdWidth = 50;
	final public static int NumberWidth = 100;
	final public static int ComboWidth = 200;
	final public static int ShortTextWidth = 100;
	final public static int MediumTextWidth = 250;
	final public static int TextWidth = 400;
	
	final public static int Left = 1;
	final public static int Center = 2;
	final public static int Right = 3;
	
	final public static String DateType = "Date";
	final public static String NumberType = "Number";
	final public static String TextType = "Text";
	
	public enum EntitiesType 
	{
		Bancos(indexBancos,"Bancos", "Ficha de bancos", 
				new String[] {"Id banco", "Id sucursal", "Nombre banco", "Nombre sucursal"}, 
				new String[] {"id.idBanco", "id.idSucursal", "nombreBanco", "nombreSucursal"},
				new String[] {TextType, TextType, TextType, TextType},
				new int[] {ShortTextWidth, ShortTextWidth, MediumTextWidth, MediumTextWidth},
				new int[] {Left, Left, Left, Left}), 
		Barcos(indexBarcos,"Barcos", "Ficha de barcos", 
				new String[] {"Id", "Nombre barco"}, 
				new String[] {"id.idBarco", "nombreBarco"},
				new String[] {NumberType, TextType},
				new int[] {IdWidth, TextWidth},
				new int[] {Left, Left}), 
		Calendario(indexCalendario,"Calendario", "Calendario", 
				new String[] {"A�o", "Semana", "Desde", "Hasta"}, 
				new String[] {"id.ejercicios.ejercicio", "id.semana", "desdeFecha", "hastaFecha"},
				new String[] {NumberType, NumberType, DateType, DateType},
				new int[] {NumberWidth, NumberWidth, DateWidth, DateWidth},
				new int[] {Left, Left, Right, Right}), 
		Categorias(indexCategorias,"Categorias", "Ficha de categor�as", 
				new String[] {"Id", "Nombre categoria", "N\u00ba kilos caja", "Id subcategoria", "Privada", "Orden"}, 
				new String[] {"id.idCategoria", "nombreCategoria", "numKilosCaja", "idSubcategoria", "privada", "orden"},
				new String[] {NumberType, TextType, NumberType, NumberType, NumberType, NumberType},
				new int[] {IdWidth, MediumTextWidth, NumberWidth, IdWidth, NumberWidth, NumberWidth},
				new int[] {Left, Left, Right, Left, Left, Left}), 
		Conceptos(indexConceptos,"Conceptos", "Ficha de conceptos", 
				new String[] {"Id", "Descripci�n"}, 
				new String[] {"id.idConcepto", "conceptoDesc"},
				new String[] {NumberType, TextType},
				new int[] {IdWidth, MediumTextWidth},
				new int[] {Left, Left}), 
		Cosecheros(indexCosecheros,"Cosecheros", "Ficha de cosecheros", 
				new String[] {"Id", "Nombre", "Apellidos", "NIF", "Tel�fono", "Poblaci�n"}, 
				new String[] {"id.idCosechero", "nombre", "apellidos", "nif", "telefono1", "poblacion"},
				new String[] {NumberType, TextType, TextType, TextType, TextType, TextType},
				new int[] {IdWidth, MediumTextWidth, MediumTextWidth, ShortTextWidth, ShortTextWidth, MediumTextWidth},
				new int[] {Left, Left, Left, Left, Left, Left}), 
		Conductores(indexConductores,"Conductores", "Ficha de conductores", 
				new String[] {"Id", "Nombre", "Apellidos", "NIF", "Tel�fono", "Poblaci�n"}, 
				new String[] {"id.idConductor", "nombre", "apellidos", "nif", "telefono", "poblacion"},
				new String[] {NumberType, TextType, TextType, TextType, TextType, TextType},
				new int[] {IdWidth, MediumTextWidth, MediumTextWidth, ShortTextWidth, ShortTextWidth, MediumTextWidth},
				new int[] {Left, Left, Left, Left, Left, Left}), 
		Empresas(indexEmpresas,"Empresas", "Ficha de empresas", 
				new String[] {"Id", "Nombre", "CIF", "Tel�fono", "Poblaci�n", "Provincia"}, 
				new String[] {"idEmpresa", "nombre", "nif", "telefono", "poblacion", "provincia"},
				new String[] {NumberType, TextType, TextType, TextType, TextType, TextType},
				new int[] {IdWidth, MediumTextWidth, ShortTextWidth, ShortTextWidth, MediumTextWidth, MediumTextWidth},
				new int[] {Left, Left, Left, Left, Left, Left}),
		Receptores(indexReceptores,"Receptores", "Ficha de receptores", 
				new String[] {"Id", "Nombre", "NIF", "Tel�fono", "Poblaci�n"}, 
				new String[] {"id.idReceptor", "nombre", "nif", "telefono", "poblacion"},
				new String[] {NumberType, TextType, TextType, TextType, TextType},
				new int[] {IdWidth, MediumTextWidth, ShortTextWidth, ShortTextWidth, MediumTextWidth},
				new int[] {Left, Left, Left, Left, Left}),
		Zonas(indexZonas,"Zonas", "Ficha de zonas", 
				new String[] {"Id", "Nombre zona"}, 
				new String[] {"id.idZona", "nombreZona"},
				new String[] {NumberType, TextType},
				new int[] {IdWidth, TextWidth},
				new int[] {Left, Left}),
		Vehiculos(indexVehiculos, "Vehiculos", "Ficha de veh�culos", 
				new String[] {"Id", "Matr�cula", "Marca"}, 
				new String[] {"id.idVehiculo", "matricula", "marca"},
				new String[] {NumberType, TextType, TextType},
				new int[] {IdWidth, ShortTextWidth, MediumTextWidth},
				new int[] {Left, Left, Left}),
		Entradas(indexEntradas, "Viewentradasquery", "Entradas", 
				new String[] {"Vale", "A�o", "Semana", "Fecha", "Id. Cosechero", "Nombre", "Apellidos", "Pi�as", "Kilos"}, 
				new String[] {"id.idEntrada", "id.ejercicio", "id.semana", "id.fecha", "id.idCosechero", "id.nombre", "id.apellidos", "id.numPinas", "id.numKilos"},
				new String[] {NumberType, NumberType, NumberType, DateType, NumberType, TextType, TextType, NumberType, NumberType},
				new int[] {NumberWidth, NumberWidth, NumberWidth, DateWidth, NumberWidth, MediumTextWidth, MediumTextWidth, NumberWidth, NumberWidth},
				new int[] {Left, Right, Right, Right, Right, Left, Left, Right, Right}),
		Ventas(indexVentas, "Viewventasquery", "Ventas", 
				new String[] {"Id", "A�o", "Semana", "Fecha", "Receptor", "N�Cajas", "N�Kilos", "Importe"}, 
				new String[] {"id.idVenta", "id.ejercicio", "id.semana", "id.fecha", "id.receptor", "id.numCajas", "id.numKilos", "id.importe"},
				new String[] {NumberType, NumberType, NumberType, DateType, TextType, NumberType, NumberType, NumberType},
				new int[] {IdWidth, NumberWidth, NumberWidth, DateWidth, MediumTextWidth, NumberWidth, NumberWidth, NumberWidth},
				new int[] {Left, Right, Right, Right, Left, Right, Right, Right}),
		Identidades(indexIdentidades, "Identidades", "Ficha de identidades", 
				new String[] {"Id", "Nombre", "NIF/CIF", "Tel�fono", "Poblaci�n"}, 
				new String[] {"id.identidad", "nombreIdentidad", "nif", "telefono", "poblacion"},
				new String[] {NumberType, TextType,  TextType, TextType, TextType},
				new int[] {IdWidth, MediumTextWidth,  ShortTextWidth, ShortTextWidth, MediumTextWidth},
				new int[] {Left, Left, Left, Left, Left}),
		Ejercicios(indexEjercicios,"Ejercicios", "Ejercicios", 
				new String[] {"A�o", "Desde", "Hasta"}, 
				new String[] {"ejercicio", "desdeFecha", "hastaFecha"},
				new String[] {NumberType, DateType, DateType},
				new int[] {NumberWidth, DateWidth, DateWidth},
				new int[] {Left, Right, Right}),
		Bimestres(indexBimestres,"Bimestres", "Bimestres", 
				new String[] {"A�o", "Bimestre", "Desde", "Hasta"}, 
				new String[] {"id.ejercicios.ejercicio", "id.bimestre", "desdeFecha", "hastaFecha"},
				new String[] {NumberType, NumberType, DateType, DateType},
				new int[] {NumberWidth, NumberWidth, DateWidth, DateWidth},
				new int[] {Left, Left, Right, Right}),
		Empleados(indexEmpleados,"Empleados", "Ficha de empleados", 
				new String[] {"Id", "Nombre", "Apellidos", "NIF", "Tel�fono", "Poblaci�n"}, 
				new String[] {"id.idEmpleado", "nombre", "apellidos", "nif", "telefono", "poblacion"},
				new String[] {NumberType, TextType, TextType, TextType, TextType, TextType},
				new int[] {IdWidth, MediumTextWidth, MediumTextWidth, ShortTextWidth, ShortTextWidth, MediumTextWidth},
				new int[] {Left, Left, Left, Left, Left, Left}),
		Facturas(indexFacturas, "Facturascabecera", "Facturas de ventas y servicios", 
				new String[] {"Id", "A�o", "Semana", "Fecha", "Cliente", "Importe"}, 
				new String[] {"id.idFactura", "id.ejercicio", "semana", "fecha", "nombre", "importeFactura"},
				new String[] {NumberType, NumberType, NumberType, DateType, TextType, NumberType},
				new int[] {NumberWidth, NumberWidth, NumberWidth, DateWidth, MediumTextWidth, NumberWidth},
				new int[] {Left, Right, Right, Right, Left, Right}),
		Liquidaciones(indexLiquidaciones, "Viewliquidaciones", "Liquidaciones", 
				new String[] {"Factura", "A�o", "Mes", "Fecha", "Nombre", "Apellidos", "Tipo IRPF", "Tipo IGIC", "Base imponible"}, 
				new String[] {"id.numerofactura", "id.ejercicio", "id.mes", "id.fecha", "id.nombre", "id.apellidos", "id.tipoIrpf", "id.tipoIgic", "id.baseImponible"},
				new String[] {NumberType, NumberType, NumberType, DateType, TextType, TextType, NumberType, NumberType, NumberType},
				new int[] {NumberWidth, NumberWidth, NumberWidth, DateWidth, MediumTextWidth, MediumTextWidth, NumberWidth, NumberWidth, NumberWidth},
				new int[] {Left, Right, Right, Right, Left, Left, Right, Right, Right}),
		FacturasPago(indexFacturasPago, "Facturaspagocabecera", "Facturas de compras y gastos", 
				new String[] {"Id", "A�o", "Referencia", "Fecha", "Proveedor", "Importe"}, 
				new String[] {"id.idFactura", "id.ejercicio", "referencia", "fecha", "nombre", "importeFactura"},
				new String[] {NumberType, NumberType, TextType, DateType, TextType, NumberType},
				new int[] {NumberWidth, NumberWidth, ShortTextWidth, DateWidth, MediumTextWidth, NumberWidth},
				new int[] {Left, Right, Left, Right, Left, Right}),
		TiposGasto(indexTiposGasto, "Tiposgasto", "Tipos de gastos", 
				new String[] {"Id", "Tipo de gasto"}, 
				new String[] {"id.idTipoGasto", "descTipoGasto"},
				new String[] {NumberType, TextType},
				new int[] {IdWidth, TextWidth},
				new int[] {Left, Left});

		
		private final int index;
		private final String name;
		private final String title;
		private final String[] headers;
		private final String[] filterfields;
		private final String[] fieldTypes;
		private final int[] columnswidth;
		private final int[] columnsalign;
		
		EntitiesType(int paramindex, String paramname, 
				String paramtitle, String[] paramheaders, String[] paramfilterfields, String[] paramfieldTypes, int[] paramcolumnswidth,  int[] paramcolumnsalign) {
			index = paramindex;
			name = paramname;
			title = paramtitle;
			headers = paramheaders;
			filterfields = paramfilterfields;
			fieldTypes = paramfieldTypes;
			columnswidth = paramcolumnswidth;
			columnsalign = paramcolumnsalign;
		}
		
		public int getindex() {
			return index;
		}
		
		public String getname() {
			return name;
		}
		
		public String gettitle() {
			return title;
		}
		
		public String[] getheaders() {
			return headers;
		}
		
		public String[] getfilterfields() {
			return filterfields;
		}
		
		public String[] getfieldTypes() {
			return fieldTypes;
		}
		
		public int[] getcolumnswidth() {
			return columnswidth;
		}
		public int[] getcolumnsalign() {
			return columnsalign;
		}
	}
}
