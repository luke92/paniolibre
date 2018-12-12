package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Especialidad;
import domain.model.Tecnico;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TecnicoDAO;

public class TecnicoDAOSQL implements TecnicoDAO {
	private static final String INSERT = "{ CALL cargarTecnico(?,?,?,?,?)}";
	private static final String INSERT_ESPECIALIDAD = "INSERT INTO TecnicosxEspecialidades (tecnico_id, especialidad_id) VALUES (?,?)";
	private static final String DELETE = "DELETE FROM Tecnicos WHERE id= ?";
	private static final String DELETE_ESPECIALIDAD = "DELETE FROM TecnicosxEspecialidades where especialidad_id = ? and tecnico_id = ? ";
	private static final String READ_ALL = "SELECT * FROM Tecnicos";
	private static final String READ_ALL_ESPECIALIDADES_X_ID_TECNICO = "SELECT te.especialidad_id, e.nombre as nombreEspecialidad FROM Tecnicos as t INNER JOIN TecnicosxEspecialidades as te ON t.id = te.tecnico_id INNER JOIN Especialidades as e ON te.especialidad_id = e.id WHERE t.id = ?";
	private static final String EDIT = "UPDATE Tecnicos set dni = ?,legajo = ?,nombre = ?, apellido = ?, activo = ?, etiquetaMantis = ? WHERE id = ? ";
	private static final String GET_ID = "SELECT id from Tecnicos where Tecnicos.dni = ?";
	private static final String EDIT_ESPECIALIDAD = "UPDATE TecnicosxEspecialidades set especialidad_id = ? WHERE tecnico_id = ? ";
	private static final String READ_ALL_MANTIS = "{call obtenerTecnicosAsociadosAOrdenTrabajoAsignadaPorIdMantis(?)}";

	public boolean insert(Tecnico tecnico) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT);
			statement.setString(1, tecnico.getEtiquetaMantis());
			statement.setString(2, tecnico.getDni());
			statement.setString(3, tecnico.getNombre());
			statement.setString(4, tecnico.getApellido());
			statement.setString(5, tecnico.getLegajo());
			
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertEspecialidad(Tecnico tecnico, Especialidad especialidad) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT_ESPECIALIDAD);
			statement.setInt(1, tecnico.getIdTecnico());
			statement.setInt(2, especialidad.getIdEspecialidad());

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Tecnico tecnico) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(tecnico.getIdTecnico()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteEspecialidad(Tecnico tecnico, Especialidad especialidad) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE_ESPECIALIDAD);
			statement.setString(1, Integer.toString(especialidad.getIdEspecialidad()));
			statement.setString(2, Integer.toString(tecnico.getIdTecnico()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Tecnico> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Tecnico> tecnicos = new ArrayList<Tecnico>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(resultSet.getInt("id"));
				tecnico.setDni(resultSet.getString("dni"));
				tecnico.setLegajo(resultSet.getString("legajo"));
				tecnico.setNombre(resultSet.getString("nombre"));
				tecnico.setApellido(resultSet.getString("apellido"));
				tecnico.setEspecialidades(this.readAllEspecialidadesxIdTecnico(tecnico));
				tecnico.setActivo(resultSet.getInt("activo"));
				tecnico.setEtiquetaMantis(resultSet.getString("etiquetaMantis"));
				tecnicos.add(tecnico);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tecnicos;
	}

	public List<Especialidad> readAllEspecialidadesxIdTecnico(Tecnico tecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Especialidad> especialidades = new ArrayList<Especialidad>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL_ESPECIALIDADES_X_ID_TECNICO);
			statement.setInt(1, tecnico.getIdTecnico());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Especialidad especialidad = new Especialidad();
				especialidad.setIdEspecialidad(resultSet.getInt("especialidad_id"));
				especialidad.setNombre(resultSet.getString("nombreEspecialidad"));
				especialidades.add(especialidad);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return especialidades;
	}

	public boolean edit(Tecnico tecnico) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, tecnico.getDni());
			statement.setString(2, tecnico.getLegajo());
			statement.setString(3, tecnico.getNombre());
			statement.setString(4, tecnico.getApellido());
			statement.setInt(5, tecnico.getActivo());
			statement.setString(6, tecnico.getEtiquetaMantis());
			statement.setInt(7, tecnico.getIdTecnico());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean editEspecialidad(Tecnico tecnico, Especialidad especialidad) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT_ESPECIALIDAD);
			statement.setInt(1, especialidad.getIdEspecialidad());
			statement.setInt(2, tecnico.getIdTecnico());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int obtenerIdTecnico(Tecnico tecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idTecnico = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setString(1, tecnico.getDni());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idTecnico = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idTecnico;
	}

	public List<Tecnico> readAllMantis(String idMantis) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Tecnico> tecnicos = new ArrayList<Tecnico>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL_MANTIS);
			statement.setString(1, idMantis);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(resultSet.getInt("idTecnico"));
				tecnico.setNombre(resultSet.getString("nombreTecnico"));
				tecnico.setApellido(resultSet.getString("apellidoTecnico"));
				tecnico.setEspecialidades(this.readAllEspecialidadesxIdTecnico(tecnico));
				tecnico.setActivo(resultSet.getInt("activo"));
				tecnicos.add(tecnico);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tecnicos;
	}
}