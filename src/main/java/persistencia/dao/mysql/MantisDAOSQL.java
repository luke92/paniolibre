package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import dto.MantisDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.MantisDAO;

public class MantisDAOSQL implements MantisDAO {

	private static final String EDIT = "{CALL cargarConfiguracionMantis(?,?,?)}";
	private static final String READ = "SELECT * FROM Mantis";

	@Override
	public MantisDTO obtenerConfiguracionMantis() {
		CallableStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();
		MantisDTO mantisDTO = null;
		try {
			statement = conexion.getSQLConexion().prepareCall(READ);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				mantisDTO = new MantisDTO(resultSet.getString("filtroNuevasId"), resultSet.getString("filtroTodasId"),
						resultSet.getString("filtroResueltasId"), resultSet.getString("filtroCerradasId"),
						resultSet.getString("filtroAsignadasId"), resultSet.getString("idCustomFieldTecnicos"),
						resultSet.getString("apiUrl"), resultSet.getString("mantisPuerto"),
						resultSet.getString("mantisIP"), resultSet.getString("mantisNombreApp"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mantisDTO;
	}

	@Override
	public boolean actualizarConfiguracionMantis(MantisDTO mantisDTO) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(EDIT);
			statement.setString(1, mantisDTO.getMantisPuerto());
			statement.setString(2, mantisDTO.getMantisIP());
			statement.setString(3, mantisDTO.getMantisNombreApp());
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			Logger.getLogger(e.getMessage());
		} finally {
			Logger.getLogger("Exito");
		}
		return false;
	}
}
