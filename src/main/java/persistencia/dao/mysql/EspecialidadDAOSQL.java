package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Especialidad;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.EspecialidadesDAO;

public class EspecialidadDAOSQL implements EspecialidadesDAO {

	private static final String INSERT = "INSERT INTO Especialidades(id,nombre) VALUES(?,?)";
	private static final String DELETE = "DELETE FROM Especialidades WHERE id = ?";
	private static final String READ_ALL = "SELECT * FROM Especialidades";

	public boolean insert(Especialidad especialidad) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(INSERT);
			statement.setInt(1, especialidad.getIdEspecialidad());
			statement.setString(2, especialidad.getNombre());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Especialidad especialidad) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(especialidad.getIdEspecialidad()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Especialidad> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Especialidad> especialidades = new ArrayList<Especialidad>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Especialidad especialidad = new Especialidad(resultSet.getInt("id"), resultSet.getString("nombre"));
				especialidades.add(especialidad);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return especialidades;
	}

}
