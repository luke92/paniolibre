package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.OrdenesTrabajoTecnicos;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.OrdenesTrabajoTecnicosDAO;

public class OrdenesTrabajoTecnicosDAOSQL implements OrdenesTrabajoTecnicosDAO {
	private static final String INSERT = "{CALL CargarOrdenTrabajoTecnico (?,?)}";
	private static final String DELETE = "DELETE FROM ordenestrabajotecnicos WHERE OrdenTrabajo_id= ? AND Tecnico_id= ?";
	private static final String READ_ALL = "SELECT * FROM ordenestrabajotecnicos";
	private static final String EDIT = "UPDATE ordenestrabajotecnicos SET OrdenTrabajo_id = ?,Tecnico_id = ? WHERE OrdenTrabajo_id= ? AND Tecnico_id= ? ";

	@Override
	public boolean insert(OrdenesTrabajoTecnicos ordenesTrabajoTecnicos) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT);
			statement.setInt(1, ordenesTrabajoTecnicos.getOrdenTrabajoId());
			statement.setInt(2, ordenesTrabajoTecnicos.getTecnicoId());

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean edit(OrdenesTrabajoTecnicos ordenesTrabajoTecnicosAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setInt(1, ordenesTrabajoTecnicosAEditar.getOrdenTrabajoId());
			statement.setInt(2, ordenesTrabajoTecnicosAEditar.getTecnicoId());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(OrdenesTrabajoTecnicos ordenesTrabajoTecnicosAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(ordenesTrabajoTecnicosAEliminar.getOrdenTrabajoId()));
			statement.setString(2, Integer.toString(ordenesTrabajoTecnicosAEliminar.getTecnicoId()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<OrdenesTrabajoTecnicos> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<OrdenesTrabajoTecnicos> ordenesDeTrabajo = new ArrayList<OrdenesTrabajoTecnicos>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ordenesDeTrabajo.add(new OrdenesTrabajoTecnicos(resultSet.getInt("getOrdenTrabajo_id"),
						resultSet.getInt("getTecnico_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordenesDeTrabajo;
	}
}