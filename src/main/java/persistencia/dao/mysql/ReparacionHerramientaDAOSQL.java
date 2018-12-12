package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Herramienta;
import domain.model.ReparacionHerramienta;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ReparacionHerramientaDAO;
import util.Fechas;

public class ReparacionHerramientaDAOSQL implements ReparacionHerramientaDAO {
	private static final String INSERT = "INSERT INTO reparacionesherramientas(id,herramienta_id,reparacionInterna,comentarioEnvio,comentarioRecepcion,fechaEnviada,fechaRecibida,fechaExpiracionGarantia) VALUES(?,?,?,?,?,?,?,?)";
	private static final String INSERT_CON_FECHAS_NULL = "INSERT INTO reparacionesherramientas(id,herramienta_id,reparacionInterna,comentarioEnvio,comentarioRecepcion,fechaEnviada) VALUES(?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM reparacionesherramientas WHERE id= ?";
	private static final String READ_ALL = "SELECT * FROM reparacionesherramientas";
	private static final String EDIT = "UPDATE reparacionesherramientas set comentarioRecepcion = ?, fechaRecibida = ?,fechaExpiracionGarantia = ? WHERE id = ? ";
	private static final String GET_ID = "{ CALL obtenerIdReparacionHerramienta(?)}";

	public boolean insert(ReparacionHerramienta reparacionHerramienta) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (reparacionHerramienta.getFechaRecibida() == null
					&& reparacionHerramienta.getFechaExpiracionGarantia() == null) {
				statement = conexion.getSQLConexion().prepareStatement(INSERT_CON_FECHAS_NULL);
				statement.setInt(1, reparacionHerramienta.getIdReparacionHerramienta());
				statement.setInt(2, reparacionHerramienta.getHerramienta().getIdHerramienta());
				statement.setInt(3, reparacionHerramienta.getReparacionInterna());
				statement.setString(4, reparacionHerramienta.getComentarioEnvio());
				statement.setString(5, reparacionHerramienta.getComentarioRecepcion());
				statement.setDate(6, (Date) Fechas.Calendar_a_DateSQL(reparacionHerramienta.getFechaEnviada()));
			} else {
				statement = conexion.getSQLConexion().prepareStatement(INSERT);
				statement.setInt(1, reparacionHerramienta.getIdReparacionHerramienta());
				statement.setInt(2, reparacionHerramienta.getHerramienta().getIdHerramienta());
				statement.setInt(3, reparacionHerramienta.getReparacionInterna());
				statement.setString(4, reparacionHerramienta.getComentarioEnvio());
				statement.setString(5, reparacionHerramienta.getComentarioRecepcion());
				statement.setDate(6, (Date) Fechas.Calendar_a_DateSQL(reparacionHerramienta.getFechaEnviada()));
				statement.setDate(7, (Date) Fechas.Calendar_a_DateSQL(reparacionHerramienta.getFechaRecibida()));
				statement.setDate(8,
						(Date) Fechas.Calendar_a_DateSQL(reparacionHerramienta.getFechaExpiracionGarantia()));
			}
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(ReparacionHerramienta reparacionHerramientaAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(reparacionHerramientaAEliminar.getIdReparacionHerramienta()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ReparacionHerramienta> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReparacionHerramienta> reparacionHerramientas = new ArrayList<ReparacionHerramienta>();
		Conexion conexion = Conexion.getConexion();

		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ReparacionHerramienta reparacionHerramienta = new ReparacionHerramienta();
				reparacionHerramienta.setIdReparacionHerramienta(resultSet.getInt("id"));
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("herramienta_id"));
				reparacionHerramienta.setHerramienta(herramienta);
				reparacionHerramienta.setReparacionInterna(resultSet.getInt("reparacionInterna"));
				reparacionHerramienta.setComentarioEnvio(resultSet.getString("comentarioEnvio"));
				reparacionHerramienta.setComentarioRecepcion(resultSet.getString("comentarioRecepcion"));
				reparacionHerramienta.setFechaEnviada(Fechas.Date_a_Calendar(resultSet.getDate("fechaEnviada")));
				reparacionHerramienta.setFechaRecibida(Fechas.Date_a_Calendar(resultSet.getDate("fechaRecibida")));
				reparacionHerramienta.setFechaExpiracionGarantia(
						Fechas.Date_a_Calendar(resultSet.getDate("fechaExpiracionGarantia")));
				reparacionHerramientas.add(reparacionHerramienta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reparacionHerramientas;
	}

	public boolean edit(ReparacionHerramienta reparacionHerramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();

		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, reparacionHerramientaAEditar.getComentarioRecepcion());
			statement.setDate(2, (Date) Fechas.Calendar_a_DateSQL(reparacionHerramientaAEditar.getFechaRecibida()));
			statement.setDate(3,
					(Date) Fechas.Calendar_a_DateSQL(reparacionHerramientaAEditar.getFechaExpiracionGarantia()));
			statement.setInt(4, reparacionHerramientaAEditar.getIdReparacionHerramienta());

			if (statement.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public int obtenerIdReparacionHerramienta(Herramienta herramienta) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		int id = 0;
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_ID);
			statement.setInt(1, herramienta.getIdHerramienta());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}
}
