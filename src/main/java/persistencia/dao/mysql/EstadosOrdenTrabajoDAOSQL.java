package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.EstadosOrdenTrabajo;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.EstadosOrdenTrabajoDAO;

public class EstadosOrdenTrabajoDAOSQL implements EstadosOrdenTrabajoDAO {

	private static final String INSERT = "INSERT INTO estadosordentrabajo(id, Estado) VALUES(?,?)";
	private static final String DELETE = "DELETE FROM estadosordentrabajo WHERE id= ?";
	private static final String READ_ALL = "SELECT * FROM estadosordentrabajo";
	private static final String EDIT = "UPDATE estadosordentrabajo SET Estado = ? WHERE id = ? ";

	@Override
	public boolean insert(EstadosOrdenTrabajo estadosOrdenDeTrabajo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(INSERT);
			statement.setInt(1, estadosOrdenDeTrabajo.getIdEstadosOrdenTrabajo());
			statement.setInt(2, estadosOrdenDeTrabajo.getEstado());

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(EstadosOrdenTrabajo estadosOrdenTrabajoAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(estadosOrdenTrabajoAEliminar.getIdEstadosOrdenTrabajo()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean edit(EstadosOrdenTrabajo estadosOrdenDeTrabajo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setInt(1, estadosOrdenDeTrabajo.getIdEstadosOrdenTrabajo());
			statement.setInt(2, estadosOrdenDeTrabajo.getEstado());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<EstadosOrdenTrabajo> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<EstadosOrdenTrabajo> estadosOrdenDeTrabajo = new ArrayList<EstadosOrdenTrabajo>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				estadosOrdenDeTrabajo.add(new EstadosOrdenTrabajo(resultSet.getInt("id"), resultSet.getInt("Estado")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return estadosOrdenDeTrabajo;
	}
}