package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domain.model.Deposito;
import domain.model.Insumo;
import domain.model.InsumoDeposito;
import domain.model.UbicacionDeposito;
import dto.AlertaInsumoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.InsumoDepositoDAO;

public class InsumoDepositoDAOSQL implements InsumoDepositoDAO {
	private static final String INSERT = "{CALL cargarInsumoDeposito(?,?,?)}";
	private static final String DELETE = "DELETE FROM insumosdepositos WHERE id= ?";
	private static final String READ_ALL = "{CALL obtenerStockInsumos()}";
	private static final String EDIT = "{ CALL intercambiarUbicacionInsumo(?,?,?) }";
	private static final String GET_BY_ID = "{CALL obtenerInsumoDepositoxIds(?,?)}";
	private static final String CARGAR_AJUSTE = "{ CALL cargarAjusteStock(?,?,?,?,?,?)}";
	private static final String GET_ALERTA_UMBRAL = "{CALL obtenerInsumosConUmbralMinimo()}";
	private static final String EXISTE_INSUMO_EN_DEPOSITOS = "SELECT * FROM InsumosDepositos WHERE insumo_id = ?";
	private static final String READ_ALL_ORDEN = "{CALL obtenerStockInsumosPorOrdenTrabajo(?)}";

	public boolean insert(InsumoDeposito insumoDeposito) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT);
			statement.setInt(1, insumoDeposito.getUbicacion().getDeposito().getIdDeposito());
			statement.setInt(2, insumoDeposito.getInsumo().getIdInsumo());
			statement.setInt(3, insumoDeposito.getUbicacion().getIdUbicacionDeposito());
			if (statement.execute()) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(InsumoDeposito insumoDepositoAEliminar) {
		CallableStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(DELETE);
			statement.setString(1, Integer.toString(insumoDepositoAEliminar.getIdDeposito()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<InsumoDeposito> readAll() {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<InsumoDeposito> insumoDepositos = new ArrayList<InsumoDeposito>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				InsumoDeposito insumoDeposito = new InsumoDeposito();
				Insumo insumo = new Insumo();
				insumo.setCodigoInsumo(resultSet.getString("codigo"));
				insumo.setIdInsumo(resultSet.getInt("insumo_id"));
				insumo.setNombre(resultSet.getString("Insumo"));
				insumo.setUmbralMinimo(resultSet.getInt("umbralMinimo"));
				UbicacionDeposito ubicacion = new UbicacionDeposito();
				ubicacion.setIdUbicacionDeposito(resultSet.getInt("ubicacion_id"));
				ubicacion.setNombre(resultSet.getString("Ubicacion"));
				Deposito deposito = new Deposito();
				deposito.setIdDeposito(resultSet.getInt("deposito_id"));
				deposito.setNombre(resultSet.getString("Deposito"));
				ubicacion.setDeposito(deposito);
				insumoDeposito.setInsumo(insumo);
				insumoDeposito.setUbicacion(ubicacion);
				insumoDeposito.setStockNuevo(resultSet.getInt("stockNuevo"));
				insumoDeposito.setStockUsado(resultSet.getInt("stockUsado"));
				insumoDeposito.setStockReservado(resultSet.getInt("stockReservado"));
				insumoDepositos.add(insumoDeposito);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insumoDepositos;
	}

	public List<AlertaInsumoDTO> obtenerAlertaUmbralMinimo() {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<AlertaInsumoDTO> alerta = new ArrayList<AlertaInsumoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_ALERTA_UMBRAL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				AlertaInsumoDTO alertaInsumo = new AlertaInsumoDTO(resultSet.getInt("idAlerta"), resultSet.getInt("idDeposito"),
						resultSet.getString("Deposito"), resultSet.getInt("idInsumo"), resultSet.getString("Insumo"),
						resultSet.getInt("umbralMinimo"), resultSet.getInt("StockNoReservado"), false, LocalDate.now(),resultSet.getString("codigoInsumo"));
				alerta.add(alertaInsumo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alerta;
	}

	public boolean edit(InsumoDeposito insumoDepositoAEditar, UbicacionDeposito ubicacionNueva) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(EDIT);
			statement.setInt(1, insumoDepositoAEditar.getUbicacion().getDeposito().getIdDeposito());
			statement.setInt(2, insumoDepositoAEditar.getInsumo().getIdInsumo());
			statement.setInt(3, ubicacionNueva.getIdUbicacionDeposito());
			if (statement.execute())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public InsumoDeposito getById(InsumoDeposito insumoDepositoABuscar) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		InsumoDeposito insumoDeposito = null;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_BY_ID);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				insumoDeposito = new InsumoDeposito();
				Insumo insumo = new Insumo();
				insumo.setIdInsumo(resultSet.getInt("insumo_id"));
				insumo.setNombre(resultSet.getString("Insumo"));
				UbicacionDeposito ubicacion = new UbicacionDeposito();
				ubicacion.setIdUbicacionDeposito(resultSet.getInt("ubicacion_id"));
				ubicacion.setNombre(resultSet.getString("Ubicacion"));
				Deposito deposito = new Deposito();
				deposito.setIdDeposito(resultSet.getInt("deposito_id"));
				deposito.setNombre(resultSet.getString("Deposito"));
				ubicacion.setDeposito(deposito);
				insumoDeposito.setInsumo(insumo);
				insumoDeposito.setUbicacion(ubicacion);
				insumoDeposito.setStockNuevo(resultSet.getInt("stockNuevo"));
				insumoDeposito.setStockUsado(resultSet.getInt("stockUsado"));
				insumoDeposito.setStockReservado(resultSet.getInt("stockReservado"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insumoDeposito;
	}

	@Override
	public boolean edit(InsumoDeposito insumoDepositoAEditar) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(EDIT);
			statement.setInt(1, insumoDepositoAEditar.getUbicacion().getIdUbicacionDeposito());
			statement.setInt(2, insumoDepositoAEditar.getInsumo().getIdInsumo());
			statement.setInt(3, insumoDepositoAEditar.getUbicacion().getIdUbicacionDeposito());
			if (statement.execute())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean ajustarStock(InsumoDeposito insumoDeposito) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(CARGAR_AJUSTE);

			statement.setInt(1, 1);
			statement.setInt(2, insumoDeposito.getUbicacion().getDeposito().getIdDeposito());
			statement.setInt(3, insumoDeposito.getInsumo().getIdInsumo());
			statement.setInt(4, insumoDeposito.getStockNuevo());
			statement.setInt(5, insumoDeposito.getStockUsado());
			statement.setInt(6, insumoDeposito.getStockReservado());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean existeInsumoEnDepositos(InsumoDeposito insumo) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		boolean existe = false;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EXISTE_INSUMO_EN_DEPOSITOS);
			statement.setInt(1, insumo.getInsumo().getIdInsumo());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				existe = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;

	}

	public List<InsumoDeposito> readAllPorNumeroDeOrden(int id) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<InsumoDeposito> insumoDepositos = new ArrayList<InsumoDeposito>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(READ_ALL_ORDEN);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				InsumoDeposito insumoDeposito = new InsumoDeposito();
				Insumo insumo = new Insumo();
				insumo.setIdInsumo(resultSet.getInt("insumo_id"));
				insumo.setNombre(resultSet.getString("Insumo"));
				insumo.setUmbralMinimo(resultSet.getInt("umbralMinimo"));
				UbicacionDeposito ubicacion = new UbicacionDeposito();
				ubicacion.setIdUbicacionDeposito(resultSet.getInt("ubicacion_id"));
				ubicacion.setNombre(resultSet.getString("Ubicacion"));
				Deposito deposito = new Deposito();
				deposito.setIdDeposito(resultSet.getInt("deposito_id"));
				deposito.setNombre(resultSet.getString("Deposito"));
				ubicacion.setDeposito(deposito);
				insumoDeposito.setInsumo(insumo);
				insumoDeposito.setUbicacion(ubicacion);
				insumoDeposito.setStockNuevo(resultSet.getInt("stockNuevo"));
				insumoDeposito.setStockUsado(resultSet.getInt("stockUsado"));
				insumoDeposito.setStockReservado(resultSet.getInt("stockReservado"));
				insumoDepositos.add(insumoDeposito);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insumoDepositos;
	}

}