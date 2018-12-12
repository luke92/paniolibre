package persistencia.dao.interfaz;

public interface DAOAbstractFactory {
	public MantisDAO createMantisDAO();
	
	public AlertaDAO createAlertaDAO();
	
	public ArbolCategoriaDAO createArbolCategoriaDAO();

	public InsumoDAO createInsumoDAO();

	public CategoriaInsumoDAO createCategoriaInsumoDAO();

	public OrdenDeTrabajoDAO createOrdenDeTrabajoDAO();

	public RetiroInsumoDAO createRetiroInsumoDAO();

	public InsumoDepositoDAO createInsumoDepositoDAO();

	public DepositoDAO createDepositoDAO();

	public UbicacionDepositoDAO createUbicacionDepositoDAO();

	public PedidoInsumoDAO createPedidoInsumoDAO();

	public PedidoInsumoDetalleDAO createPedidoInsumoDetalleDAO();

	public EstadosOrdenTrabajoDAO createEstadosOrdenTrabajoDAO();

	public TecnicoDAO createTecnicoDAO();

	public OrdenesTrabajoTecnicosDAO createOrdenesTrabajoTecnicosDAO();

	public HerramientaDAO createHerramientaDAO();

	public CategoriaHerramientaDAO createCategoriaHerramientaDAO();

	public RetiroHerramientaDAO createRetiroHerramientaDAO();

	public ReparacionHerramientaDAO createReparacionHerramientaDAO();

	public DevolucionHerramientaDAO createDevolucionHerramientaDAO();

	public EspecialidadesDAO createEspecialidadDAO();

	public DevolucionInsumoDAO createDevolucionInsumoDAO();

	public IngresoInsumoDAO createIngresoInsumo();

	public MailDAO createMailDAO();

	public UsuarioDAO createUsuarioDAO();
}