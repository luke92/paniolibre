package persistencia.dao.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Herramienta;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroHerramienta;
import domain.model.Tecnico;
import domain.model.Usuario;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.RetiroHerramientaDAO;
import util.Fechas;

public class RetiroHerramientaDAOSQL implements RetiroHerramientaDAO {
	private static final String INSERT = "INSERT INTO PrestamosHerramientas(id,usuario_id,tecnico_id,herramienta_id) VALUES(?,?,?,?)";
	private static final String INSERT_CON_ORDEN_DE_TRABAJO = "INSERT INTO PrestamosHerramientas(id,usuario_id,tecnico_id,herramienta_id,ordenTrabajo_id,fechaPrestamo) VALUES(?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM PrestamosHerramientas WHERE id = ?";
	private static final String READ_ALL = "SELECT * FROM PrestamosHerramientas";
	private static final String EDIT = "UPDATE PrestamosHerramientas set usuario_id = ?,tecnico_id = ?,herramienta_id = ?,fechaPrestamo = ? WHERE PrestamosHerramientas.id = ? ";
	private static final String EDIT_CON_ORDEN = "UPDATE PrestamosHerramientas set usuario_id = ?,tecnico_id = ?,herramienta_id = ?,ordenTrabajo_id = ?,fechaPrestamo = ? WHERE PrestamosHerramientas.id = ? ";
	private static final String GET_ID = "SELECT id from PrestamosHerramientas where PrestamosHerramientas.usuario_id = ? AND PrestamosHerramientas.tecnico_id = ? AND PrestamosHerramientas.herramienta_id = ?";
	private static final String GET_RETIROS_POR_ORDEN = "select * from PrestamosHerramientas where ordenTrabajo_id = ? and devuelto = 0";
	private static final String GET_RETIROS_POR_TECNICOS = "select * from PrestamosHerramientas where tecnico_id = ? and devuelto = 0";
	private static final String CAMBIAR_ESTADO = "UPDATE PrestamosHerramientas set devuelto = 1 WHERE herramienta_id = ? ";

	public boolean insert(RetiroHerramienta retiroHerramienta) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (retiroHerramienta.getOrdenDeTrabajo() == null) {
				statement = conexion.getSQLConexion().prepareStatement(INSERT);
				statement.setInt(1, retiroHerramienta.getIdRetiroHerramienta());
				statement.setInt(2, retiroHerramienta.getUsuario().getIdUsuario());
				statement.setInt(3, retiroHerramienta.getTecnico().getIdTecnico());
				statement.setInt(4, retiroHerramienta.getHerramienta().getIdHerramienta());
			} else {
				statement = conexion.getSQLConexion().prepareStatement(INSERT_CON_ORDEN_DE_TRABAJO);
				statement.setInt(1, retiroHerramienta.getIdRetiroHerramienta());
				statement.setInt(2, retiroHerramienta.getUsuario().getIdUsuario());
				statement.setInt(3, retiroHerramienta.getTecnico().getIdTecnico());
				statement.setInt(4, retiroHerramienta.getHerramienta().getIdHerramienta());
				statement.setInt(5, retiroHerramienta.getOrdenDeTrabajo().getId());
				statement.setDate(6, (Date) Fechas.Calendar_a_DateSQL(retiroHerramienta.getFechaPrestamo()));

			}

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(RetiroHerramienta retiroHerramienta) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(retiroHerramienta.getIdRetiroHerramienta()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<RetiroHerramienta> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<RetiroHerramienta> retiroHerramientas = new ArrayList<RetiroHerramienta>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				RetiroHerramienta retiroHerramienta = new RetiroHerramienta();
				retiroHerramienta.setIdRetiroHerramienta(resultSet.getInt("id"));
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt("usuario_id"));
				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(resultSet.getInt("tecnico_id"));
				retiroHerramienta.setUsuario(usuario);
				retiroHerramienta.setTecnico(tecnico);
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("herramienta_id"));
				retiroHerramienta.setHerramienta(herramienta);
				OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
				ordenDeTrabajo.setId(resultSet.getInt("ordenTrabajo_id"));
				retiroHerramienta.setOrdenDeTrabajo(ordenDeTrabajo);
				retiroHerramienta.setFechaPrestamo(Fechas.Date_a_Calendar(resultSet.getDate("fechaPrestamo")));
				retiroHerramientas.add(retiroHerramienta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retiroHerramientas;
	}

	public List<RetiroHerramienta> obtenerRetiroPorTecnico(Tecnico tecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		List<RetiroHerramienta> retiros = new ArrayList<RetiroHerramienta>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_RETIROS_POR_TECNICOS);
			statement.setInt(1, tecnico.getIdTecnico());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				RetiroHerramienta retiroHerramienta = new RetiroHerramienta();
				retiroHerramienta.setIdRetiroHerramienta(resultSet.getInt("id"));
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt("usuario_id"));
				Tecnico tec = new Tecnico();
				tec.setIdTecnico(resultSet.getInt("tecnico_id"));
				retiroHerramienta.setUsuario(usuario);
				retiroHerramienta.setTecnico(tec);
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("herramienta_id"));
				retiroHerramienta.setHerramienta(herramienta);
				OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
				ordenDeTrabajo.setId(resultSet.getInt("ordenTrabajo_id"));
				retiroHerramienta.setOrdenDeTrabajo(ordenDeTrabajo);
				retiroHerramienta.setFechaPrestamo(Fechas.Date_a_Calendar(resultSet.getDate("fechaPrestamo")));
				retiros.add(retiroHerramienta);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retiros;
	}

	public List<RetiroHerramienta> obtenerRetiroPorOrden(OrdenDeTrabajo orden) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		List<RetiroHerramienta> retiros = new ArrayList<RetiroHerramienta>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_RETIROS_POR_ORDEN);
			statement.setInt(1,orden.getId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				RetiroHerramienta retiroHerramienta = new RetiroHerramienta();
				retiroHerramienta.setIdRetiroHerramienta(resultSet.getInt("id"));
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt("usuario_id"));
				Tecnico tec = new Tecnico();
				tec.setIdTecnico(resultSet.getInt("tecnico_id"));
				retiroHerramienta.setUsuario(usuario);
				retiroHerramienta.setTecnico(tec);
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("herramienta_id"));
				retiroHerramienta.setHerramienta(herramienta);
				OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
				ordenDeTrabajo.setId(resultSet.getInt("ordenTrabajo_id"));
				retiroHerramienta.setOrdenDeTrabajo(ordenDeTrabajo);
				retiroHerramienta.setFechaPrestamo(Fechas.Date_a_Calendar(resultSet.getDate("fechaPrestamo")));
				retiros.add(retiroHerramienta);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retiros;
	}

	public boolean edit(RetiroHerramienta retiroHerramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (retiroHerramientaAEditar.getOrdenDeTrabajo() == null) {
				statement = conexion.getSQLConexion().prepareStatement(EDIT);
				statement.setInt(1, retiroHerramientaAEditar.getUsuario().getIdUsuario());
				statement.setInt(2, retiroHerramientaAEditar.getTecnico().getIdTecnico());
				statement.setInt(3, retiroHerramientaAEditar.getHerramienta().getIdHerramienta());
				statement.setDate(4, (Date) Fechas.Calendar_a_DateSQL(retiroHerramientaAEditar.getFechaPrestamo()));
				statement.setInt(5, retiroHerramientaAEditar.getIdRetiroHerramienta());
			} else {
				statement = conexion.getSQLConexion().prepareStatement(EDIT_CON_ORDEN);
				statement.setInt(1, retiroHerramientaAEditar.getUsuario().getIdUsuario());
				statement.setInt(2, retiroHerramientaAEditar.getTecnico().getIdTecnico());
				statement.setInt(3, retiroHerramientaAEditar.getHerramienta().getIdHerramienta());
				statement.setInt(4, retiroHerramientaAEditar.getOrdenDeTrabajo().getId());
				statement.setDate(5, (Date) Fechas.Calendar_a_DateSQL(retiroHerramientaAEditar.getFechaPrestamo()));
				statement.setInt(6, retiroHerramientaAEditar.getIdRetiroHerramienta());
			}

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cambiarAEstadoDevuelto(Herramienta herramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(CAMBIAR_ESTADO);
			statement.setInt(1, herramientaAEditar.getIdHerramienta());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int obtenerIdRetiroHerramienta(RetiroHerramienta retiroHerramienta) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idRetiroHerramienta = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setInt(1, retiroHerramienta.getUsuario().getIdUsuario());
			statement.setInt(2, retiroHerramienta.getTecnico().getIdTecnico());
			statement.setInt(3, retiroHerramienta.getHerramienta().getIdHerramienta());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idRetiroHerramienta = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idRetiroHerramienta;
	}
}
