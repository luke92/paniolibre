package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Insumo;
import domain.model.PedidoInsumo;
import domain.model.PedidoInsumoDetalle;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PedidoInsumoDetalleDAO;

public class PedidoInsumoDetalleDAOSQL implements PedidoInsumoDetalleDAO {

	private static final String INSERT = "INSERT INTO PedidosInsumosDetalle(pedidoInsumos_id,insumo_id,cantidad) VALUES(?,?,?)";
	private static final String DELETE = "DELETE FROM PedidosInsumosDetalle WHERE PedidoInsumos_id = ? AND Insumo_id = ? ";
	private static final String READ_ALL = "SELECT * FROM PedidosInsumosDetalle where PedidosInsumosDetalle.procesado = 0";
	private static final String EDIT = "UPDATE PedidosInsumosDetalle set cantidad = ? WHERE pedidoInsumos_id = ? AND insumo_id = ?  ";
	private static final String CAMBIAR_ESTADO_PROCESADO = "UPDATE PedidosInsumosDetalle set procesado = 1 WHERE pedidoInsumos_id = ? AND insumo_id = ?";
	private static final String CAMBIAR_ESTADO_SIN_PROCESAR = "UPDATE PedidosInsumosDetalle set procesado = 0 WHERE pedidoInsumos_id = ? AND insumo_id = ?";

	public boolean insert(PedidoInsumoDetalle pedidoInsumoDetalle) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(INSERT);
			statement.setInt(1, pedidoInsumoDetalle.getPedidoInsumo().getIdPedidoInsumo());
			statement.setInt(2, pedidoInsumoDetalle.getInsumo().getIdInsumo());
			statement.setInt(3, pedidoInsumoDetalle.getCantidad());

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(PedidoInsumoDetalle pedidoInsumoDetalleAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1,
					Integer.toString(pedidoInsumoDetalleAEliminar.getPedidoInsumo().getIdPedidoInsumo()));
			statement.setString(2, Integer.toString(pedidoInsumoDetalleAEliminar.getInsumo().getIdInsumo()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<PedidoInsumoDetalle> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<PedidoInsumoDetalle> pedidosInsumoDetalles = new ArrayList<PedidoInsumoDetalle>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				PedidoInsumo pedidoInsumo = new PedidoInsumo();
				pedidoInsumo.setIdPedidoInsumo(resultSet.getInt("PedidoInsumos_id"));
				Insumo insumo = new Insumo();
				insumo.setIdInsumo(resultSet.getInt("Insumo_id"));
				pedidosInsumoDetalles.add(new PedidoInsumoDetalle(pedidoInsumo, insumo, resultSet.getInt("Cantidad")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pedidosInsumoDetalles;
	}

	public boolean edit(PedidoInsumoDetalle pedidoInsumoDetalleAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setInt(1, pedidoInsumoDetalleAEditar.getCantidad());
			statement.setInt(2, pedidoInsumoDetalleAEditar.getPedidoInsumo().getIdPedidoInsumo());
			statement.setInt(3, pedidoInsumoDetalleAEditar.getInsumo().getIdInsumo());
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cambiarEstadoProcesado(PedidoInsumoDetalle pedidoInsumoDetalle_a_editar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(CAMBIAR_ESTADO_PROCESADO);
			statement.setInt(1, pedidoInsumoDetalle_a_editar.getPedidoInsumo().getIdPedidoInsumo());
			statement.setInt(2, pedidoInsumoDetalle_a_editar.getInsumo().getIdInsumo());
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cambiarEstadoSinProcesar(PedidoInsumoDetalle pedidoInsumoDetalle_a_editar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(CAMBIAR_ESTADO_SIN_PROCESAR);
			statement.setInt(1, pedidoInsumoDetalle_a_editar.getPedidoInsumo().getIdPedidoInsumo());
			statement.setInt(2, pedidoInsumoDetalle_a_editar.getInsumo().getIdInsumo());
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}