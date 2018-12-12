package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Deposito;
import domain.model.Estado;
import domain.model.UbicacionDeposito;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.UbicacionDepositoDAO;

public class UbicacionDepositoDAOSQL implements UbicacionDepositoDAO {
	private static final String READ_ALL = "{ CALL obtenerUbicaciones() }";
	private static final String GET_UBICACION = "SELECT nombre, deposito_id from Ubicaciones where id = ?";
	private static final String INSERT = "{call cargarUbicacion(?,?)}";
	private static final String EDIT = "UPDATE ubicaciones set nombre = ? WHERE id = ? ";
	private static final String DELETE = "UPDATE ubicaciones set activo = 0 WHERE id = ?  ";
	private static final String EXISTE_NOMBRE = "select ubicaciones.nombre from ubicaciones where ubicaciones.deposito_id = ? ";

	public boolean insert(UbicacionDeposito ubicacionDeposito) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT);
			statement.setString(1, ubicacionDeposito.getNombre());
			statement.setInt(2, ubicacionDeposito.getDeposito().getIdDeposito());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(UbicacionDeposito ubicacionDeposito) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setInt(1, ubicacionDeposito.getIdUbicacionDeposito());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public List<UbicacionDeposito> readAll() {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<UbicacionDeposito> ubicacionDepositos = new ArrayList<UbicacionDeposito>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Deposito deposito = new Deposito();
				deposito.setIdDeposito(resultSet.getInt("idDeposito"));
				deposito.setNombre(resultSet.getString("nombreDeposito"));
				Estado estado = Estado.ALTA;
				ubicacionDepositos.add(
						new UbicacionDeposito(resultSet.getInt("idUbicacion"), resultSet.getString("nombreUbicacion"), deposito, estado));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ubicacionDepositos;

	}

	public boolean edit(UbicacionDeposito ubicacionDeposito) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, ubicacionDeposito.getNombre());
			statement.setInt(2, ubicacionDeposito.getIdUbicacionDeposito());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public UbicacionDeposito obtenerUbicacionDeposito(UbicacionDeposito ubicacion) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		UbicacionDeposito ubicacionDeposito = new UbicacionDeposito();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_UBICACION);
			statement.setInt(1, ubicacion.getIdUbicacionDeposito());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ubicacionDeposito.setNombre(resultSet.getString("nombre"));
				Deposito deposito = new Deposito();
				deposito.setIdDeposito(resultSet.getInt("deposito_id"));
				ubicacionDeposito.setDeposito(deposito);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ubicacionDeposito;
	}

	@Override
	public int obtenerIdUbicacionDeposito(UbicacionDeposito ubicacionDeposito) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean existeNombre(UbicacionDeposito ubicacionDeposito) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EXISTE_NOMBRE);
			statement.setInt(1, ubicacionDeposito.getDeposito().getIdDeposito());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString("nombre").equals(ubicacionDeposito.getNombre())) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}