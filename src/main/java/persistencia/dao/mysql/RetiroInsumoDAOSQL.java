package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Deposito;
import domain.model.Insumo;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroInsumo;
import domain.model.Tecnico;
import domain.model.Usuario;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.RetiroInsumoDAO;
import util.Fechas;

public class RetiroInsumoDAOSQL implements RetiroInsumoDAO {
	private static final String INSERT = "{CALL cargarRetiroInsumo(?,?,?,?,?,?)}";
	private static final String INSERT_RESERVADO = "{CALL cargarRetiroInsumoReservado(?,?,?,?,?,?,?)}";
	private static final String DELETE = "DELETE FROM retirosinsumos WHERE id= ?";
	private static final String READ_ALL = "SELECT * FROM retirosinsumos where devuelto = 0";
	private static final String EDIT = "UPDATE retirosinsumos set Usuario_id = ?,Tecnico_id = ?, CantidadNuevo = ?, CantidadUsado = ?,Insumo_id = ?,Deposito_id = ?,Fecha = ? WHERE id = ? ";
	private static final String GET_RETIROS_POR_ORDEN = "{CALL obtenerRetirosInsumosPorOrdenTrabajo(?)}";
	private static final String GET_RETIROS_POR_TECNICOS = "{CALL obtenerRetirosInsumosPorTecnico(?)}";
	private static final String CERRAR_RETIROS_INSUMOS_PENDIENTES_POR_ORDEN_DE_TRABAJO = "{CALL cerrarRetirosInsumosPendientesPorOrdenTrabajo(?)}";

	public boolean insert(RetiroInsumo retiroInsumo) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (retiroInsumo.getOrdenDeTrabajo() == null) {
				statement = conexion.getSQLConexion().prepareCall(INSERT);
				statement.setInt(1, retiroInsumo.getUsuario().getIdUsuario());
				statement.setInt(2, retiroInsumo.getTecnico().getIdTecnico());
				statement.setInt(3, retiroInsumo.getCantidadNueva());
				statement.setInt(4, retiroInsumo.getCantidadUsado());
				statement.setInt(5, retiroInsumo.getInsumo().getIdInsumo());
				statement.setInt(6, retiroInsumo.getDeposito().getIdDeposito());
			} else {
				statement = conexion.getSQLConexion().prepareCall(INSERT_RESERVADO);
				statement.setInt(1, retiroInsumo.getUsuario().getIdUsuario());
				statement.setInt(2, retiroInsumo.getTecnico().getIdTecnico());
				statement.setInt(3, retiroInsumo.getCantidadNueva());
				statement.setInt(4, retiroInsumo.getCantidadUsado());
				statement.setInt(5, retiroInsumo.getInsumo().getIdInsumo());
				statement.setInt(6, retiroInsumo.getDeposito().getIdDeposito());
				statement.setInt(7, retiroInsumo.getOrdenDeTrabajo().getId());
			}
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(RetiroInsumo retiroInsumoAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(retiroInsumoAEliminar.getIdRetiroInsumo()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<RetiroInsumo> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<RetiroInsumo> retiroInsumos = new ArrayList<RetiroInsumo>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt("Usuario_id"));
				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(resultSet.getInt("Tecnico_id"));
				Insumo insumo = new Insumo();
				insumo.setIdInsumo(resultSet.getInt("Insumo_id"));
				Deposito deposito = new Deposito();
				deposito.setIdDeposito(resultSet.getInt("Deposito_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retiroInsumos;
	}

	public boolean edit(RetiroInsumo retiroInsumoAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setInt(1, retiroInsumoAEditar.getUsuario().getIdUsuario());
			statement.setInt(2, retiroInsumoAEditar.getTecnico().getIdTecnico());
			statement.setInt(3, retiroInsumoAEditar.getCantidadNueva());
			statement.setInt(4, retiroInsumoAEditar.getCantidadUsado());
			statement.setInt(5, retiroInsumoAEditar.getInsumo().getIdInsumo());
			statement.setInt(6, retiroInsumoAEditar.getDeposito().getIdDeposito());
			statement.setDate(7, Fechas.Calendar_a_DateSQL(retiroInsumoAEditar.getFecha()));

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<RetiroInsumo> obtenerRetiroPorTecnico(Tecnico tecnico) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		List<RetiroInsumo> retiros = new ArrayList<RetiroInsumo>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_RETIROS_POR_TECNICOS);
			statement.setInt(1, tecnico.getIdTecnico());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				RetiroInsumo retiroInsumo = new RetiroInsumo();
				retiroInsumo.setIdRetiroInsumo(resultSet.getInt("idRetiro"));

				Usuario usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt("idUsuario"));
				usuario.setNombre(resultSet.getString("nombreUsuario"));
				usuario.setApellido(resultSet.getString("apellidoUsuario"));
				usuario.setUserName(resultSet.getString("Username"));
				retiroInsumo.setUsuario(usuario);

				Deposito deposito = new Deposito();
				deposito.setIdDeposito(resultSet.getInt("idDeposito"));
				deposito.setNombre(resultSet.getString("nombreDeposito"));
				retiroInsumo.setDeposito(deposito);

				Insumo insumo = new Insumo();
				insumo.setIdInsumo(resultSet.getInt("idInsumo"));
				insumo.setCodigoInsumo(resultSet.getString("codigoInsumo"));
				insumo.setNombre(resultSet.getString("nombreInsumo"));
				retiroInsumo.setInsumo(insumo);

				tecnico = new Tecnico();
				tecnico.setIdTecnico(resultSet.getInt("idTecnico"));
				tecnico.setDni(resultSet.getString("dniTecnico"));
				tecnico.setNombre(resultSet.getString("nombreTecnico"));
				tecnico.setApellido(resultSet.getString("apellidoTecnico"));
				retiroInsumo.setTecnico(tecnico);

				OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
				ordenDeTrabajo.setId(resultSet.getInt("idOrdenTrabajo"));
				ordenDeTrabajo.setIdOrdenDeTrabajo(resultSet.getString("idOrdenTrabajoMantis"));
				if (ordenDeTrabajo.getId() != 0) {
					retiroInsumo.setOrdenDeTrabajo(ordenDeTrabajo);
				}

				retiroInsumo.setFecha(Fechas.Date_a_Calendar(resultSet.getDate("fecha")));
				retiroInsumo.setCantidadNueva(resultSet.getInt("cantidadNuevo"));
				retiroInsumo.setCantidadReservada(resultSet.getInt("insumoReservado"));
				retiroInsumo.setCantidadUsado(resultSet.getInt("cantidadUsado"));
				retiroInsumo.setDevuelto(resultSet.getInt("devuelto"));
				retiros.add(retiroInsumo);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retiros;
	}

	public List<RetiroInsumo> obtenerRetiroPorOrden(OrdenDeTrabajo orden) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		List<RetiroInsumo> retiros = new ArrayList<RetiroInsumo>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_RETIROS_POR_ORDEN);
			statement.setInt(1,orden.getId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				RetiroInsumo retiroInsumo = new RetiroInsumo();
				retiroInsumo.setIdRetiroInsumo(resultSet.getInt("idRetiro"));

				Usuario usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt("idUsuario"));
				usuario.setNombre(resultSet.getString("nombreUsuario"));
				usuario.setApellido(resultSet.getString("apellidoUsuario"));
				usuario.setUserName(resultSet.getString("Username"));
				retiroInsumo.setUsuario(usuario);

				Deposito deposito = new Deposito();
				deposito.setIdDeposito(resultSet.getInt("idDeposito"));
				deposito.setNombre(resultSet.getString("nombreDeposito"));
				retiroInsumo.setDeposito(deposito);

				Insumo insumo = new Insumo();
				insumo.setIdInsumo(resultSet.getInt("idInsumo"));
				insumo.setCodigoInsumo(resultSet.getString("codigoInsumo"));
				insumo.setNombre(resultSet.getString("nombreInsumo"));
				retiroInsumo.setInsumo(insumo);

				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(resultSet.getInt("idTecnico"));
				tecnico.setDni(resultSet.getString("dniTecnico"));
				tecnico.setNombre(resultSet.getString("nombreTecnico"));
				tecnico.setApellido(resultSet.getString("apellidoTecnico"));
				retiroInsumo.setTecnico(tecnico);

				OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
				ordenDeTrabajo.setId(resultSet.getInt("idOrdenTrabajo"));
				ordenDeTrabajo.setIdOrdenDeTrabajo(resultSet.getString("idOrdenTrabajoMantis"));
				if (ordenDeTrabajo.getId() != 0) {
					retiroInsumo.setOrdenDeTrabajo(ordenDeTrabajo);
				}

				retiroInsumo.setFecha(Fechas.Date_a_Calendar(resultSet.getDate("fecha")));
				retiroInsumo.setCantidadNueva(resultSet.getInt("cantidadNuevo"));
				retiroInsumo.setCantidadReservada(resultSet.getInt("insumoReservado"));
				retiroInsumo.setCantidadUsado(resultSet.getInt("cantidadUsado"));
				retiroInsumo.setDevuelto(resultSet.getInt("devuelto"));
				retiros.add(retiroInsumo);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retiros;
	}

	@Override
	public boolean cerrarRetirosInsumosPendientes(OrdenDeTrabajo orden) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(CERRAR_RETIROS_INSUMOS_PENDIENTES_POR_ORDEN_DE_TRABAJO);
			statement.setInt(1, orden.getId());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}