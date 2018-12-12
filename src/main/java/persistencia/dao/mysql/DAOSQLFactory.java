/**
 * 
 */
package persistencia.dao.mysql;

import persistencia.dao.interfaz.AlertaDAO;
import persistencia.dao.interfaz.ArbolCategoriaDAO;
import persistencia.dao.interfaz.CategoriaHerramientaDAO;
import persistencia.dao.interfaz.CategoriaInsumoDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.DepositoDAO;
import persistencia.dao.interfaz.DevolucionHerramientaDAO;
import persistencia.dao.interfaz.DevolucionInsumoDAO;
import persistencia.dao.interfaz.EspecialidadesDAO;
import persistencia.dao.interfaz.EstadosOrdenTrabajoDAO;
import persistencia.dao.interfaz.HerramientaDAO;
import persistencia.dao.interfaz.IngresoInsumoDAO;
import persistencia.dao.interfaz.InsumoDAO;
import persistencia.dao.interfaz.InsumoDepositoDAO;
import persistencia.dao.interfaz.MailDAO;
import persistencia.dao.interfaz.MantisDAO;
import persistencia.dao.interfaz.OrdenDeTrabajoDAO;
import persistencia.dao.interfaz.OrdenesTrabajoTecnicosDAO;
import persistencia.dao.interfaz.PedidoInsumoDAO;
import persistencia.dao.interfaz.PedidoInsumoDetalleDAO;
import persistencia.dao.interfaz.ReparacionHerramientaDAO;
import persistencia.dao.interfaz.RetiroHerramientaDAO;
import persistencia.dao.interfaz.RetiroInsumoDAO;
import persistencia.dao.interfaz.TecnicoDAO;
import persistencia.dao.interfaz.UbicacionDepositoDAO;
import persistencia.dao.interfaz.UsuarioDAO;

public class DAOSQLFactory implements DAOAbstractFactory {

	@Override
	public InsumoDAO createInsumoDAO() {
		return new InsumoDAOSQL();
	}

	@Override
	public HerramientaDAO createHerramientaDAO() {
		return new HerramientaDAOSQL();
	}

	@Override
	public CategoriaHerramientaDAO createCategoriaHerramientaDAO() {
		return new CategoriaHerramientaDAOSQL();
	}

	@Override
	public CategoriaInsumoDAO createCategoriaInsumoDAO() {
		return new CategoriaInsumoDAOSQL();
	}

	@Override
	public OrdenDeTrabajoDAO createOrdenDeTrabajoDAO() {
		return new OrdenDeTrabajoDAOSQL();
	}

	public RetiroInsumoDAO createRetiroInsumoDAO() {
		return new RetiroInsumoDAOSQL();
	}

	@Override
	public InsumoDepositoDAO createInsumoDepositoDAO() {
		return new InsumoDepositoDAOSQL();
	}

	@Override
	public DepositoDAO createDepositoDAO() {
		return new DepositoDAOSQL();
	}

	@Override
	public UbicacionDepositoDAO createUbicacionDepositoDAO() {
		return new UbicacionDepositoDAOSQL();
	}

	@Override
	public PedidoInsumoDAO createPedidoInsumoDAO() {
		return new PedidoInsumoDAOSQL();
	}

	@Override
	public PedidoInsumoDetalleDAO createPedidoInsumoDetalleDAO() {
		return new PedidoInsumoDetalleDAOSQL();
	}

	@Override
	public EstadosOrdenTrabajoDAO createEstadosOrdenTrabajoDAO() {
		return new EstadosOrdenTrabajoDAOSQL();
	}

	@Override
	public TecnicoDAO createTecnicoDAO() {
		return new TecnicoDAOSQL();
	}

	@Override
	public ReparacionHerramientaDAO createReparacionHerramientaDAO() {
		return new ReparacionHerramientaDAOSQL();
	}

	@Override
	public OrdenesTrabajoTecnicosDAO createOrdenesTrabajoTecnicosDAO() {
		return new OrdenesTrabajoTecnicosDAOSQL();
	}

	@Override
	public RetiroHerramientaDAO createRetiroHerramientaDAO() {
		return new RetiroHerramientaDAOSQL();
	}

	@Override
	public DevolucionHerramientaDAO createDevolucionHerramientaDAO() {
		return new DevolucionHerramientaDAOSQL();
	}

	@Override
	public EspecialidadesDAO createEspecialidadDAO() {
		return new EspecialidadDAOSQL();
	}

	@Override
	public DevolucionInsumoDAO createDevolucionInsumoDAO() {
		return new DevolucionInsumoDAOSQL();
	}

	@Override
	public ArbolCategoriaDAO createArbolCategoriaDAO() {
		return new ArbolCategoriaDAOSQL();
	}

	@Override
	public IngresoInsumoDAO createIngresoInsumo() {
		return new IngresoInsumoDAOSQL();
	}

	@Override
	public MailDAO createMailDAO() {
		return new MailDAOSQL();
	}

	public UsuarioDAO createUsuarioDAO() {
		return new UsuarioDAOSQL();
	}

	@Override
	public AlertaDAO createAlertaDAO() {
		return new AlertaDAOSQL();
	}

	@Override
	public MantisDAO createMantisDAO() {
		return new MantisDAOSQL();
	}
}