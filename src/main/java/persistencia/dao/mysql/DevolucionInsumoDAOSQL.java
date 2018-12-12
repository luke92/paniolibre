package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.SQLException;

import domain.model.DevolucionInsumo;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.DevolucionInsumoDAO;

public class DevolucionInsumoDAOSQL implements DevolucionInsumoDAO {
	private static final String INSERT = "{CALL cargarDevolucionInsumo(?,?,?,?,?,?,?,?)}";

	@Override
	public boolean insert(DevolucionInsumo devolucionInsumo) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT);
			statement.setInt(1, devolucionInsumo.getRetiro().getIdRetiroInsumo());
			statement.setInt(2, devolucionInsumo.getDeposito().getIdDeposito());
			statement.setInt(3, devolucionInsumo.getInsumo().getIdInsumo());
			statement.setInt(4, devolucionInsumo.getUsuario().getIdUsuario());
			statement.setInt(5, devolucionInsumo.getTecnico().getIdTecnico());
			if (devolucionInsumo.getOrdenDeTrabajo() == null) {
				statement.setNull(6, java.sql.Types.INTEGER);
			} else {
				statement.setInt(6, devolucionInsumo.getOrdenDeTrabajo().getId());
			}
			statement.setInt(7, devolucionInsumo.getCantidadNueva());
			statement.setInt(8, devolucionInsumo.getCantidadUsado());

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
