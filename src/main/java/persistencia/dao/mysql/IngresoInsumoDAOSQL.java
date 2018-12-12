package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.SQLException;

import domain.model.IngresoInsumo;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.IngresoInsumoDAO;

public class IngresoInsumoDAOSQL implements IngresoInsumoDAO {
	private static final String INSERT = "{CALL cargarIngresoInsumo(?,?,?,?)}";
	private static final String INSERT_CON_ORDEN = "{CALL cargarIngresoInsumoReservado(?,?,?,?,?)}";

	@Override
	public boolean insert(IngresoInsumo ingresoInsumo) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (ingresoInsumo.getPedidoInsumo() == null) {
				statement = conexion.getSQLConexion().prepareCall(INSERT);
				statement.setInt(1, ingresoInsumo.getInsumo().getIdInsumo());
				statement.setInt(2, ingresoInsumo.getDeposito().getIdDeposito());
				statement.setInt(3, ingresoInsumo.getCantidadIngreso());
				statement.setNull(4, java.sql.Types.INTEGER);

				if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
					return true;
			} else {
				statement = conexion.getSQLConexion().prepareCall(INSERT);
				statement.setInt(1, ingresoInsumo.getInsumo().getIdInsumo());
				statement.setInt(2, ingresoInsumo.getDeposito().getIdDeposito());
				statement.setInt(3, ingresoInsumo.getCantidadIngreso());
				statement.setInt(4, ingresoInsumo.getPedidoInsumo().getIdPedidoInsumo());
				if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
					return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insertConOrdenDeTrabajo(IngresoInsumo ingresoInsumo) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT_CON_ORDEN);
			statement.setInt(1, ingresoInsumo.getInsumo().getIdInsumo());
			statement.setInt(2, ingresoInsumo.getDeposito().getIdDeposito());
			statement.setInt(3, ingresoInsumo.getCantidadIngreso());
			statement.setString(4, ingresoInsumo.getOrdenDeTrabajo().getIdOrdenDeTrabajo());
			statement.setNull(5, java.sql.Types.INTEGER);
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
