package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dto.AlertaInsumoDTO;
import dto.AlertaPedidoDTO;
import dto.AlertaReparacionDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.AlertaDAO;

public class AlertaDAOSQL implements AlertaDAO {

	private String obtenerAlertasInsumos = "{CALL obtenerAlertasInsumos()}";
	private String obtenerAlertasPedidos = "{CALL obtenerAlertasPedidos()}";
	private String obtenerAlertasReparaciones = "{CALL obtenerAlertasReparaciones()}";
	private String enviarAlertaInsumo = "{CALL enviarAlertaInsumo(?)}";
	private String enviarAlertaPedido = "{CALL enviarAlertaPedido(?)}";
	private String enviarAlertaReparacion = "{CALL enviarAlertaReparacion(?)}";

	@Override
	public List<AlertaInsumoDTO> obtenerAlertasInsumos() {
		CallableStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();
		List<AlertaInsumoDTO> alertas = new ArrayList<>();
		try {
			statement = conexion.getSQLConexion().prepareCall(obtenerAlertasInsumos);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				AlertaInsumoDTO alerta = new AlertaInsumoDTO(resultSet.getInt("idAlerta"),
						resultSet.getInt("idDeposito"), resultSet.getString("nombreDeposito"),
						resultSet.getInt("idInsumo"), resultSet.getString("nombreInsumo"),
						resultSet.getInt("umbralMinimo"), resultSet.getInt("cantidad"),
						resultSet.getBoolean("enviadoPorMail"), resultSet.getDate("fecha").toLocalDate(),
						resultSet.getString("codigoInsumo"));
				alertas.add(alerta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alertas;
	}

	@Override
	public List<AlertaPedidoDTO> obtenerAlertasPedidos() {
		CallableStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();
		List<AlertaPedidoDTO> alertas = new ArrayList<>();
		try {
			statement = conexion.getSQLConexion().prepareCall(obtenerAlertasPedidos);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				AlertaPedidoDTO alerta = new AlertaPedidoDTO(resultSet.getInt("idAlerta"),
						resultSet.getDate("fecha").toLocalDate(), resultSet.getBoolean("enviadoPorMail"),
						resultSet.getInt("nroOrdenCompra"), resultSet.getString("proveedor"),
						resultSet.getDate("fechaSolicitud").toLocalDate(), resultSet.getString("comentario"),
						resultSet.getDate("fechaProbableRecepcion").toLocalDate());
				alertas.add(alerta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alertas;
	}

	@Override
	public List<AlertaReparacionDTO> obtenerAlertasReparaciones() {
		CallableStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();
		List<AlertaReparacionDTO> alertas = new ArrayList<>();
		try {
			statement = conexion.getSQLConexion().prepareCall(obtenerAlertasReparaciones);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				AlertaReparacionDTO alerta = new AlertaReparacionDTO(resultSet.getInt("idAlerta"),
						resultSet.getDate("fecha").toLocalDate(), resultSet.getBoolean("enviadoPorMail"),
						resultSet.getString("codigoHerramienta"), resultSet.getString("nombreHerramienta"),
						resultSet.getString("comentarioEnvioReparacion"),
						resultSet.getDate("fechaEnviada").toLocalDate(),
						resultSet.getDate("fechaProbableRecepcion").toLocalDate());
				alertas.add(alerta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alertas;
	}

	@Override
	public boolean enviarPorMailAlertaInsumo(int idAlerta) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(enviarAlertaInsumo);
			statement.setInt(1, idAlerta);
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			Logger.getLogger(e.getMessage());
		} finally {
			Logger.getLogger("Exito");
		}
		return false;
	}

	@Override
	public boolean enviarPorMailAlertaPedido(int idAlerta) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(enviarAlertaPedido);
			statement.setInt(1, idAlerta);
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			Logger.getLogger(e.getMessage());
		} finally {
			Logger.getLogger("Exito");
		}
		return false;
	}

	@Override
	public boolean enviarPorMailAlertaReparacion(int idAlerta) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(enviarAlertaReparacion);
			statement.setInt(1, idAlerta);
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
