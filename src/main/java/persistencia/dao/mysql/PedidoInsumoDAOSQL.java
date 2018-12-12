package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.EnumRecibido;
import domain.model.Insumo;
import domain.model.OrdenDeTrabajo;
import domain.model.PedidoInsumo;
import domain.model.PedidoInsumoDetalle;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PedidoInsumoDAO;
import util.Fechas;

public class PedidoInsumoDAOSQL implements PedidoInsumoDAO {
	private static final String INSERT = "{call CargarPedidoInsumo(?,?,?,?,?)}";
	private static final String GET_PEDIDO_DETALLE = "{call obtenerPedidosInsumosDetalleXIdPedido(?)}";
	private static final String DELETE = "DELETE FROM PedidosInsumos WHERE id = ?";
	private static final String READ_ALL = "SELECT * FROM PedidosInsumos where PedidosInsumos.estadoRecibido_id = 1 OR PedidosInsumos.estadoRecibido_id = 2 ";
	private static final String GET_ID = "SELECT PedidosInsumos.id from PedidosInsumos where nroOrdenCompra = ?";
	private static final String GET_PEDIDO_INSUMO = "SELECT * from PedidosInsumos where PedidosInsumos.id = ? ";
	private static final String EDIT = "UPDATE PedidosInsumos SET FechaSolicitud = ?,FechaProbableRecepcion = ?,Comentario = ?,Recibido = ?,NroOrdenCompra = ?,FechaRealRecepcion = ? WHERE PedidosInsumos.id = ? ";
	private static final String CAMBIAR_ESTADO_RECIBIDO = "UPDATE PedidosInsumos SET estadoRecibido_id = 3, fechaRealRecepcion = ? WHERE PedidosInsumos.id = ? ";
	private static final String CAMBIAR_ESTADO_INCOMPLETO = "UPDATE PedidosInsumos SET estadoRecibido_id = 4 WHERE PedidosInsumos.id = ? ";
	private static final String CAMBIAR_PEDIDO_PROCESADO = "{call procesarPedidoInsumoDetalle(?,?)}";
	private static final String CAMBIAR_ESTADO_PARCIAL = " UPDATE PedidosInsumos SET estadoRecibido_id = 2  WHERE PedidosInsumos.id = ? ";
	private static final String GET_ID_MANTIS = "select idOrdenTrabajo from ordenestrabajo where id = ?";

	public boolean insert(PedidoInsumo pedidoInsumo) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (pedidoInsumo.getOrdenDeTrabajo() == null) {
				statement = conexion.getSQLConexion().prepareCall(INSERT);
				statement.setDate(4, Fechas.Calendar_a_DateSQL(pedidoInsumo.getFechaProbableRecepcion()));
				statement.setString(3, pedidoInsumo.getComentario());
				statement.setInt(1, pedidoInsumo.getNumeroOrdenCompra());
				statement.setString(2, pedidoInsumo.getProveedor());
				statement.setNull(5, java.sql.Types.INTEGER);
				if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
					return true;
			} else {
				statement = conexion.getSQLConexion().prepareCall(INSERT);
				statement.setDate(4, Fechas.Calendar_a_DateSQL(pedidoInsumo.getFechaProbableRecepcion()));
				statement.setString(3, pedidoInsumo.getComentario());
				statement.setInt(1, pedidoInsumo.getNumeroOrdenCompra());
				statement.setString(2, pedidoInsumo.getProveedor());
				statement.setInt(5, pedidoInsumo.getOrdenDeTrabajo().getId());
				if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<PedidoInsumoDetalle> obtenerPedidosDetalle(PedidoInsumo pedido) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<PedidoInsumoDetalle> pedidoDetalles = new ArrayList<PedidoInsumoDetalle>();
		Conexion conexion = Conexion.getConexion();

		try {
			statement = conexion.getSQLConexion().prepareCall(GET_PEDIDO_DETALLE);
			statement.setInt(1, pedido.getIdPedidoInsumo());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				PedidoInsumoDetalle detalle = new PedidoInsumoDetalle();
				detalle.setCantidad(resultSet.getInt("cantidad"));
				Insumo insumo = new Insumo();
				insumo.setIdInsumo(resultSet.getInt("insumo_id"));
				detalle.setInsumo(insumo);
				pedidoDetalles.add(detalle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pedidoDetalles;
	}

	public boolean delete(PedidoInsumo pedidoInsumoAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(pedidoInsumoAEliminar.getIdPedidoInsumo()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<PedidoInsumo> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<PedidoInsumo> pedidosInsumos = new ArrayList<PedidoInsumo>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				PedidoInsumo nuevoPedidoInsumo = new PedidoInsumo();
				nuevoPedidoInsumo.setIdPedidoInsumo(resultSet.getInt("Id"));
				nuevoPedidoInsumo.setFechaSolicitud(Fechas.Date_a_Calendar(resultSet.getDate("FechaSolicitud")));
				nuevoPedidoInsumo
						.setFechaProbableRecepcion(Fechas.Date_a_Calendar(resultSet.getDate("FechaProbableRecepcion")));
				nuevoPedidoInsumo.setComentario(resultSet.getString("Comentario"));
				nuevoPedidoInsumo.setRecibido(obtenerEstado(resultSet.getInt("estadoRecibido_id")));
				nuevoPedidoInsumo.setNumeroOrdenCompra(resultSet.getInt("NroOrdenCompra"));
				nuevoPedidoInsumo.setProveedor(resultSet.getString("proveedor"));
				OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
				ordenDeTrabajo.setId(resultSet.getInt("ordenTrabajo_id"));
				nuevoPedidoInsumo.setOrdenDeTrabajo(ordenDeTrabajo);
				if (resultSet.getDate("fechaRealRecepcion") != null) {
					nuevoPedidoInsumo
							.setFechaRealRecepcion(Fechas.Date_a_Calendar(resultSet.getDate("fechaRealRecepcion")));
				}
				nuevoPedidoInsumo.setProveedor(resultSet.getString("Proveedor"));
				pedidosInsumos.add(nuevoPedidoInsumo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pedidosInsumos;
	}

	private EnumRecibido obtenerEstado(int valor) {
		if (valor == 1)
			return EnumRecibido.PENDIENTE;
		if (valor == 2)
			return EnumRecibido.PARCIAL;
		if (valor == 3)
			return EnumRecibido.COMPLETO;
		if (valor == 4)
			return EnumRecibido.INCOMPLETO;
		return null;
	}

	public boolean edit(PedidoInsumo pedidoInsumoAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setDate(1, Fechas.Calendar_a_DateSQL(pedidoInsumoAEditar.getFechaSolicitud()));
			statement.setDate(2, Fechas.Calendar_a_DateSQL(pedidoInsumoAEditar.getFechaProbableRecepcion()));
			statement.setString(3, pedidoInsumoAEditar.getComentario());
			statement.setInt(4, pedidoInsumoAEditar.getRecibido().getValor());
			statement.setInt(5, pedidoInsumoAEditar.getNumeroOrdenCompra());
			statement.setDate(6, Fechas.Calendar_a_DateSQL(pedidoInsumoAEditar.getFechaRealRecepcion()));
			statement.setInt(7, pedidoInsumoAEditar.getIdPedidoInsumo());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int obtenerId(PedidoInsumo pedidoInsumo) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idPedido = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setInt(1, pedidoInsumo.getNumeroOrdenCompra());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idPedido = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idPedido;
	}

	@Override
	public boolean cambiarEstadoRecibido(PedidoInsumo pedidoInsumo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(CAMBIAR_ESTADO_RECIBIDO);
			statement.setDate(1, Fechas.Calendar_a_DateSQL(pedidoInsumo.getFechaRealRecepcion()));
			statement.setInt(2, pedidoInsumo.getIdPedidoInsumo());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PedidoInsumo obtenerPedidoInsumo(PedidoInsumo pedidoInsumo) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		PedidoInsumo pedidoInsumoMaestro = new PedidoInsumo();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_PEDIDO_INSUMO);
			statement.setInt(1, pedidoInsumo.getIdPedidoInsumo());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pedidoInsumoMaestro.setIdPedidoInsumo(pedidoInsumo.getIdPedidoInsumo());
				pedidoInsumoMaestro.setComentario(resultSet.getString("comentario"));
				pedidoInsumoMaestro.setNumeroOrdenCompra(resultSet.getInt("nroOrdenCompra"));
				pedidoInsumoMaestro.setProveedor(resultSet.getString("proveedor"));
				pedidoInsumoMaestro.setFechaSolicitud(Fechas.Date_a_Calendar(resultSet.getDate("fechaSolicitud")));
				pedidoInsumoMaestro
						.setFechaProbableRecepcion(Fechas.Date_a_Calendar(resultSet.getDate("fechaProbableRecepcion")));

				pedidoInsumoMaestro.setRecibido(EnumRecibido.PENDIENTE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pedidoInsumoMaestro;
	}

	@Override
	public String obtenerIdMantis(PedidoInsumo pedidoInsumo) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		String id = "";
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID_MANTIS);
			statement.setInt(1, pedidoInsumo.getOrdenDeTrabajo().getId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getString("idOrdenTrabajo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public boolean cambiarEstadoProcesado(PedidoInsumoDetalle pedidoInsumo) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(CAMBIAR_PEDIDO_PROCESADO);
			statement.setInt(1, pedidoInsumo.getInsumo().getIdInsumo());
			statement.setInt(2, pedidoInsumo.getPedidoInsumo().getIdPedidoInsumo());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean cambiarEstadoIncompleto(PedidoInsumo pedidoInsumo2) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(CAMBIAR_ESTADO_INCOMPLETO);
			statement.setInt(1, pedidoInsumo2.getIdPedidoInsumo());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cambiarEstadoParcial(PedidoInsumo pedidoInsumo) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(CAMBIAR_ESTADO_PARCIAL);
			statement.setInt(1, pedidoInsumo.getIdPedidoInsumo());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}