package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Deposito;
import domain.model.Estado;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.DepositoDAO;

public class DepositoDAOSQL implements DepositoDAO {

	private static final String INSERT = "INSERT INTO Depositos(id,Nombre,Comentario) VALUES(?,?,?)";
	private static final String DELETE = "DELETE FROM Depositos WHERE id = ?";
	private static final String READ_ALL = "SELECT * FROM Depositos";
	private static final String EDIT = "UPDATE Depositos set Nombre = ?,Comentario = ?,Activo = ? WHERE Depositos.id = ? ";
	private static final String GET_ID = "SELECT id from Depositos where Depositos.Nombre = ?";
	private static final String GET_NOMBRE = "SELECT nombre from Depositos where Depositos.id = ? ";

	public boolean insert(Deposito deposito) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(INSERT);
			statement.setInt(1, deposito.getIdDeposito());
			statement.setString(2, deposito.getNombre());
			statement.setString(3, deposito.getComentario());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Deposito depositoAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(depositoAEliminar.getIdDeposito()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Deposito> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Deposito> depositos = new ArrayList<Deposito>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				depositos.add(new Deposito(resultSet.getInt("id"), resultSet.getString("Nombre"),
						resultSet.getString("Comentario"), Estado.getEstado(resultSet.getInt("Activo"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return depositos;
	}

	public boolean edit(Deposito depositoAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, depositoAEditar.getNombre());
			statement.setString(2, depositoAEditar.getComentario());
			statement.setInt(3, depositoAEditar.getActivo().getInt());
			statement.setInt(4, depositoAEditar.getIdDeposito());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int obtenerIdDeposito(Deposito deposito) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idDeposito = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setString(1, deposito.getNombre());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idDeposito = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idDeposito;
	}

	public String obtenerNombreDeposito(Deposito deposito) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		String nombreDeposito = "";
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_NOMBRE);
			statement.setInt(1, deposito.getIdDeposito());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				nombreDeposito = resultSet.getString("nombre");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombreDeposito;
	}
}