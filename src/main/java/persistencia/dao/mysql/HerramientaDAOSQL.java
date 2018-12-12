package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import domain.model.CategoriaHerramienta;
import domain.model.DevolucionHerramienta;
import domain.model.EnumEstadoHerramienta;
import domain.model.Herramienta;
import domain.model.RetiroHerramienta;
import domain.model.UbicacionDeposito;
import dto.ArbolCategoriaDTO;
import dto.HerramientaReporteDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.HerramientaDAO;
import util.Fechas;

public class HerramientaDAOSQL implements HerramientaDAO {
	private String INSERT = "INSERT INTO Herramientas(id,codigo,marca,nombre,categoria_id,ubicacion_id,numeroActivo,mecanismoAdquisicion,fechaAdquisicion,proveedor,factura,fechaGarantiaExpiracion,comentario,estadoHerramienta_id,Activo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String DELETE = "DELETE FROM Herramientas WHERE id = ?";
	private String READ_ALL = "SELECT * FROM Herramientas";
	private String EDIT = "UPDATE Herramientas set  codigo = ? ,marca = ?,nombre = ?,categoria_id = ?,ubicacion_id = ?,numeroActivo = ?,mecanismoAdquisicion = ?,fechaAdquisicion = ?,proveedor = ?,factura = ?,fechaGarantiaExpiracion = ?,comentario = ?,estadoHerramienta_id = ? ,activo = ?, fechaUltimaModificacion = ? WHERE Herramientas.id = ? ";
	private String CAMBIAR_ESTADO_EN_REPARACION = "UPDATE Herramientas set estadoHerramienta_id = 4, fechaUltimaModificacion = ? WHERE Herramientas.id = ? ";
	private String CAMBIAR_ESTADO_PRESTADA = "UPDATE Herramientas set estadoHerramienta_id = 2, fechaUltimaModificacion = ? WHERE Herramientas.id = ? ";
	private String CAMBIAR_ESTADO_DISPONIBLE = "UPDATE Herramientas set estadoHerramienta_id = 1, fechaUltimaModificacion = ? WHERE Herramientas.id = ?";
	private String CAMBIAR_ESTADO_AVERIADA = "UPDATE Herramientas set estadoHerramienta_id = 3, fechaUltimaModificacion = ? WHERE Herramientas.id = ?";
	private String GET_ID = "SELECT Herramientas.id from Herramientas where Herramientas.nombre = ?";
	private String GET_HERRAMIENTA = "SELECT codigo,marca,nombre,categoria_id,ubicacion_id,numeroActivo,mecanismoAdquisicion,fechaAdquisicion,proveedor,factura,fechaGarantiaExpiracion,comentario,estadoHerramienta_id,fechaUltimaModificacion,Activo from Herramientas where Herramientas.id = ?";
	private String GET_HERRAMIENTAS_AVERIADAS = "SELECT * from Herramientas where Herramientas.estadoHerramienta_id = 3";
	private String GET_HERRAMIENTAS_EN_REPARACION = "SELECT * from Herramientas where Herramientas.estadoHerramienta_id = 4";
	private String GET_HERRAMIENTAS_DISPONIBLES = "SELECT * from Herramientas where Herramientas.estadoHerramienta_id = 1";
	private String GET_HERRAMIENTAS_PRESTADAS = "SELECT * from Herramientas where Herramientas.estadoHerramienta_id = 2";
	private String GET_ULTIMA_DEVOLUCION_HERRAMIENTAS = "{CALL obtenerUltimoDevolucionHerramientaPorIdHerramienta(?)}";
	private String OBTENER_HERRAMIENTAS_MAS_PRESTADAS = "{CALL obtenerHerramientasMasPrestadas(?,?,?)}";
	private String OBTENER_PRIMER_HERRAMIENTA_PRESTADA = "{CALL obtenerPrimerPrestamoHerramienta()}";
	public boolean insert(Herramienta herramienta) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(INSERT);
			statement.setInt(1, herramienta.getIdHerramienta());
			statement.setString(2, herramienta.getCodigo());
			statement.setString(3, herramienta.getMarca());
			statement.setString(4, herramienta.getNombre());
			statement.setInt(5, herramienta.getCategoria().getIdCategoria());
			statement.setInt(6, herramienta.getUbicacion().getIdUbicacionDeposito());
			statement.setString(7, herramienta.getNumeroActivo());
			statement.setString(8, herramienta.getMecanismoAdquisicion());
			statement.setDate(9, (Date) Fechas.Calendar_a_DateSQL(herramienta.getFechaAdquisicion()));
			statement.setString(10, herramienta.getProveedor());
			statement.setString(11, herramienta.getFactura());
			if (herramienta.getFechaGarantiaExpiracion() != null) {
				statement.setDate(12, (Date) Fechas.Calendar_a_DateSQL(herramienta.getFechaGarantiaExpiracion()));
			} else
				statement.setNull(12, java.sql.Types.DATE);
			statement.setString(13, herramienta.getComentario());
			statement.setInt(14, obtenerIDEstado(herramienta.getEstado()));
			statement.setInt(15, herramienta.getActivo());
			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Herramienta herramientaAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(herramientaAEliminar.getIdHerramienta()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Herramienta> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Herramienta> herramientas = new ArrayList<Herramienta>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				UbicacionDeposito ubicacion = new UbicacionDeposito();
				ubicacion.setIdUbicacionDeposito(resultSet.getInt("ubicacion_id"));
				CategoriaHerramienta categoria = new CategoriaHerramienta();
				categoria.setIdCategoria(resultSet.getInt("categoria_id"));
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("id"));
				herramienta.setCodigo(resultSet.getString("codigo"));
				herramienta.setMarca(resultSet.getString("marca"));
				herramienta.setNombre(resultSet.getString("nombre"));
				herramienta.setProveedor(resultSet.getString("proveedor"));
				herramienta.setUbicacion(ubicacion);
				herramienta.setCategoria(categoria);
				herramienta.setFactura(resultSet.getString("factura"));
				herramienta.setNumeroActivo(resultSet.getString("numeroActivo"));
				herramienta.setComentario(resultSet.getString("comentario"));
				herramienta.setMecanismoAdquisicion(resultSet.getString("mecanismoAdquisicion"));
				herramienta.setFechaAdquisicion(Fechas.Date_a_Calendar(resultSet.getDate("fechaAdquisicion")));
				if (resultSet.getDate("fechaGarantiaExpiracion") != null)
					herramienta.setFechaGarantiaExpiracion(
							Fechas.Date_a_Calendar(resultSet.getDate("fechaGarantiaExpiracion")));
				else
					herramienta.setFechaGarantiaExpiracion(herramienta.getFechaAdquisicion());
				herramienta.setFechaUltimaModificacion(
						Fechas.Date_a_Calendar(resultSet.getDate("fechaUltimaModificacion")));
				herramienta.setEstado(obtenerEstado(resultSet.getInt("estadoHerramienta_id")));
				herramienta.setActivo(resultSet.getInt("activo"));
				herramientas.add(herramienta);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return herramientas;
	}

	public List<Herramienta> obtenerHerramientasAveriadas() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Herramienta> herramientas = new ArrayList<Herramienta>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_HERRAMIENTAS_AVERIADAS);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				UbicacionDeposito ubicacion = new UbicacionDeposito();
				ubicacion.setIdUbicacionDeposito(resultSet.getInt("ubicacion_id"));
				CategoriaHerramienta categoria = new CategoriaHerramienta();
				categoria.setIdCategoria(resultSet.getInt("categoria_id"));
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("id"));
				herramienta.setCodigo(resultSet.getString("codigo"));
				herramienta.setMarca(resultSet.getString("marca"));
				herramienta.setNombre(resultSet.getString("nombre"));
				herramienta.setProveedor(resultSet.getString("proveedor"));
				herramienta.setUbicacion(ubicacion);
				herramienta.setCategoria(categoria);
				herramienta.setFactura(resultSet.getString("factura"));
				herramienta.setNumeroActivo(resultSet.getString("numeroActivo"));
				herramienta.setComentario(resultSet.getString("comentario"));
				herramienta.setMecanismoAdquisicion(resultSet.getString("mecanismoAdquisicion"));
				herramienta.setFechaAdquisicion(Fechas.Date_a_Calendar(resultSet.getDate("fechaAdquisicion")));
				if (resultSet.getDate("fechaGarantiaExpiracion") != null)
					herramienta.setFechaGarantiaExpiracion(
							Fechas.Date_a_Calendar(resultSet.getDate("fechaGarantiaExpiracion")));
				else
					herramienta.setFechaGarantiaExpiracion(herramienta.getFechaAdquisicion());
				herramienta.setFechaUltimaModificacion(
						Fechas.Date_a_Calendar(resultSet.getDate("fechaUltimaModificacion")));
				herramienta.setEstado(obtenerEstado(resultSet.getInt("estadoHerramienta_id")));
				herramienta.setActivo(resultSet.getInt("activo"));
				herramientas.add(herramienta);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return herramientas;
	}

	public List<Herramienta> obtenerHerramientasPrestadas() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Herramienta> herramientas = new ArrayList<Herramienta>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_HERRAMIENTAS_PRESTADAS);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				UbicacionDeposito ubicacion = new UbicacionDeposito();
				ubicacion.setIdUbicacionDeposito(resultSet.getInt("ubicacion_id"));
				CategoriaHerramienta categoria = new CategoriaHerramienta();
				categoria.setIdCategoria(resultSet.getInt("categoria_id"));
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("id"));
				herramienta.setCodigo(resultSet.getString("codigo"));
				herramienta.setMarca(resultSet.getString("marca"));
				herramienta.setNombre(resultSet.getString("nombre"));
				herramienta.setProveedor(resultSet.getString("proveedor"));
				herramienta.setUbicacion(ubicacion);
				herramienta.setCategoria(categoria);
				herramienta.setFactura(resultSet.getString("factura"));
				herramienta.setNumeroActivo(resultSet.getString("numeroActivo"));
				herramienta.setComentario(resultSet.getString("comentario"));
				herramienta.setMecanismoAdquisicion(resultSet.getString("mecanismoAdquisicion"));
				herramienta.setFechaAdquisicion(Fechas.Date_a_Calendar(resultSet.getDate("fechaAdquisicion")));
				if (resultSet.getDate("fechaGarantiaExpiracion") != null)
					herramienta.setFechaGarantiaExpiracion(
							Fechas.Date_a_Calendar(resultSet.getDate("fechaGarantiaExpiracion")));
				else
					herramienta.setFechaGarantiaExpiracion(herramienta.getFechaAdquisicion());
				herramienta.setFechaUltimaModificacion(
						Fechas.Date_a_Calendar(resultSet.getDate("fechaUltimaModificacion")));
				herramienta.setEstado(obtenerEstado(resultSet.getInt("estadoHerramienta_id")));
				herramienta.setActivo(resultSet.getInt("activo"));
				herramientas.add(herramienta);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return herramientas;
	}

	public List<Herramienta> obtenerHerramientasDisponibles() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Herramienta> herramientas = new ArrayList<Herramienta>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_HERRAMIENTAS_DISPONIBLES);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				UbicacionDeposito ubicacion = new UbicacionDeposito();
				ubicacion.setIdUbicacionDeposito(resultSet.getInt("ubicacion_id"));
				CategoriaHerramienta categoria = new CategoriaHerramienta();
				categoria.setIdCategoria(resultSet.getInt("categoria_id"));
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("id"));
				herramienta.setCodigo(resultSet.getString("codigo"));
				herramienta.setMarca(resultSet.getString("marca"));
				herramienta.setNombre(resultSet.getString("nombre"));
				herramienta.setProveedor(resultSet.getString("proveedor"));
				herramienta.setUbicacion(ubicacion);
				herramienta.setCategoria(categoria);
				herramienta.setFactura(resultSet.getString("factura"));
				herramienta.setNumeroActivo(resultSet.getString("numeroActivo"));
				herramienta.setComentario(resultSet.getString("comentario"));
				herramienta.setMecanismoAdquisicion(resultSet.getString("mecanismoAdquisicion"));
				herramienta.setFechaAdquisicion(Fechas.Date_a_Calendar(resultSet.getDate("fechaAdquisicion")));
				if (resultSet.getDate("fechaGarantiaExpiracion") != null)
					herramienta.setFechaGarantiaExpiracion(
							Fechas.Date_a_Calendar(resultSet.getDate("fechaGarantiaExpiracion")));
				else
					herramienta.setFechaGarantiaExpiracion(herramienta.getFechaAdquisicion());
				herramienta.setFechaUltimaModificacion(
						Fechas.Date_a_Calendar(resultSet.getDate("fechaUltimaModificacion")));
				herramienta.setEstado(obtenerEstado(resultSet.getInt("estadoHerramienta_id")));
				herramienta.setActivo(resultSet.getInt("activo"));
				herramientas.add(herramienta);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return herramientas;
	}

	public List<Herramienta> obtenerHerramientasEnReparacion() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Herramienta> herramientas = new ArrayList<Herramienta>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_HERRAMIENTAS_EN_REPARACION);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				UbicacionDeposito ubicacion = new UbicacionDeposito();
				ubicacion.setIdUbicacionDeposito(resultSet.getInt("ubicacion_id"));
				CategoriaHerramienta categoria = new CategoriaHerramienta();
				categoria.setIdCategoria(resultSet.getInt("categoria_id"));
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("id"));
				herramienta.setCodigo(resultSet.getString("codigo"));
				herramienta.setMarca(resultSet.getString("marca"));
				herramienta.setNombre(resultSet.getString("nombre"));
				herramienta.setProveedor(resultSet.getString("proveedor"));
				herramienta.setUbicacion(ubicacion);
				herramienta.setCategoria(categoria);
				herramienta.setFactura(resultSet.getString("factura"));
				herramienta.setNumeroActivo(resultSet.getString("numeroActivo"));
				herramienta.setComentario(resultSet.getString("comentario"));
				herramienta.setMecanismoAdquisicion(resultSet.getString("mecanismoAdquisicion"));
				herramienta.setFechaAdquisicion(Fechas.Date_a_Calendar(resultSet.getDate("fechaAdquisicion")));
				if (resultSet.getDate("fechaGarantiaExpiracion") != null)
					herramienta.setFechaGarantiaExpiracion(
							Fechas.Date_a_Calendar(resultSet.getDate("fechaGarantiaExpiracion")));
				else
					herramienta.setFechaGarantiaExpiracion(herramienta.getFechaAdquisicion());
				herramienta.setFechaUltimaModificacion(
						Fechas.Date_a_Calendar(resultSet.getDate("fechaUltimaModificacion")));
				herramienta.setEstado(obtenerEstado(resultSet.getInt("estadoHerramienta_id")));
				herramienta.setActivo(resultSet.getInt("activo"));
				herramientas.add(herramienta);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return herramientas;
	}

	public boolean edit(Herramienta herramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, herramientaAEditar.getCodigo());
			statement.setString(2, herramientaAEditar.getMarca());
			statement.setString(3, herramientaAEditar.getNombre());
			statement.setInt(4, herramientaAEditar.getCategoria().getIdCategoria());
			statement.setInt(5, herramientaAEditar.getUbicacion().getIdUbicacionDeposito());
			statement.setString(6, herramientaAEditar.getNumeroActivo());
			statement.setString(7, herramientaAEditar.getMecanismoAdquisicion());
			statement.setDate(8, (Date) Fechas.Calendar_a_DateSQL(herramientaAEditar.getFechaAdquisicion()));
			statement.setString(9, herramientaAEditar.getProveedor());
			statement.setString(10, herramientaAEditar.getFactura());
			if (herramientaAEditar.getFechaGarantiaExpiracion() != null) {
				statement.setDate(11, Fechas.Calendar_a_DateSQL(herramientaAEditar.getFechaGarantiaExpiracion()));
			} else
				statement.setNull(11, java.sql.Types.DATE);
			statement.setString(12, herramientaAEditar.getComentario());
			statement.setInt(13, obtenerIDEstado(herramientaAEditar.getEstado()));
			statement.setInt(14, herramientaAEditar.getActivo());
			statement.setDate(15, Fechas.Calendar_a_DateSQL(Calendar.getInstance()));
			statement.setInt(16, herramientaAEditar.getIdHerramienta());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cambiarEstadoEnReparacion(Herramienta herramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(CAMBIAR_ESTADO_EN_REPARACION);
			statement.setDate(1, Fechas.Calendar_a_DateSQL(Calendar.getInstance()));
			statement.setInt(2, herramientaAEditar.getIdHerramienta());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cambiarEstadoPrestada(Herramienta herramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(CAMBIAR_ESTADO_PRESTADA);
			statement.setDate(1, Fechas.Calendar_a_DateSQL(Calendar.getInstance()));
			statement.setInt(2, herramientaAEditar.getIdHerramienta());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cambiarEstadoAveriada(Herramienta herramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(CAMBIAR_ESTADO_AVERIADA);
			statement.setDate(1, Fechas.Calendar_a_DateSQL(Calendar.getInstance()));
			statement.setInt(2, herramientaAEditar.getIdHerramienta());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cambiarEstadoDisponible(Herramienta herramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(CAMBIAR_ESTADO_DISPONIBLE);
			statement.setDate(1, Fechas.Calendar_a_DateSQL(Calendar.getInstance()));
			statement.setInt(2, herramientaAEditar.getIdHerramienta());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int obtenerIdHerramienta(Herramienta herramienta) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idHerramienta = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setString(1, herramienta.getNombre());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idHerramienta = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idHerramienta;
	}

	public Herramienta obtenerHerramientaMaestro(Herramienta herramienta) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Herramienta herramientaM = new Herramienta();
		herramientaM.setIdHerramienta(herramienta.getIdHerramienta());
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_HERRAMIENTA);
			statement.setInt(1, herramienta.getIdHerramienta());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				UbicacionDeposito ubicacion = new UbicacionDeposito();
				ubicacion.setIdUbicacionDeposito(resultSet.getInt("ubicacion_id"));
				CategoriaHerramienta categoria = new CategoriaHerramienta();
				categoria.setIdCategoria(resultSet.getInt("categoria_id"));
				herramientaM.setCodigo(resultSet.getString("codigo"));
				herramientaM.setMarca(resultSet.getString("marca"));
				herramientaM.setNombre(resultSet.getString("nombre"));
				herramientaM.setUbicacion(ubicacion);
				herramientaM.setCategoria(categoria);
				herramientaM.setFactura(resultSet.getString("factura"));
				herramientaM.setNumeroActivo(resultSet.getString("numeroActivo"));
				herramientaM.setComentario(resultSet.getString("comentario"));
				herramientaM.setMecanismoAdquisicion(resultSet.getString("mecanismoAdquisicion"));
				herramientaM.setFechaAdquisicion(Fechas.Date_a_Calendar(resultSet.getDate("fechaAdquisicion")));
				if (resultSet.getDate("fechaGarantiaExpiracion") != null)
					herramientaM.setFechaGarantiaExpiracion(
							Fechas.Date_a_Calendar(resultSet.getDate("fechaGarantiaExpiracion")));
				else
					herramientaM.setFechaGarantiaExpiracion(herramientaM.getFechaAdquisicion());
				herramientaM.setFechaUltimaModificacion(
						Fechas.Date_a_Calendar(resultSet.getDate("fechaUltimaModificacion")));
				herramientaM.setEstado(obtenerEstado(resultSet.getInt("estadoHerramienta_id")));
				herramientaM.setActivo(resultSet.getInt("activo"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return herramientaM;
	}

	public EnumEstadoHerramienta obtenerEstado(int estado) {
		if (estado == 1)
			return EnumEstadoHerramienta.DISPONIBLE;
		if (estado == 2)
			return EnumEstadoHerramienta.PRESTADO;
		if (estado == 3)
			return EnumEstadoHerramienta.AVERIADO;
		if (estado == 4)
			return EnumEstadoHerramienta.EN_REPARACION;
		return null;
	}

	public int obtenerIDEstado(EnumEstadoHerramienta estado) {
		if (EnumEstadoHerramienta.DISPONIBLE.equals(estado))
			return 1;
		if (EnumEstadoHerramienta.PRESTADO.equals(estado))
			return 2;
		if (EnumEstadoHerramienta.AVERIADO.equals(estado))
			return 3;
		if (EnumEstadoHerramienta.EN_REPARACION.equals(estado))
			return 4;
		return 0;
	}

	@Override
	public DevolucionHerramienta obtenerComentarioAveriado(Herramienta herramienta) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		DevolucionHerramienta devolucion = null;
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_ULTIMA_DEVOLUCION_HERRAMIENTAS);
			statement.setInt(1, herramienta.getIdHerramienta());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				devolucion = new DevolucionHerramienta();
				devolucion.setComentario(resultSet.getString("comentario"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return devolucion;
	}

	@Override
	public List<HerramientaReporteDTO> obtenerHerramientasMasPrestadas(ArbolCategoriaDTO categoriaDTO, Date fechaInicio,
			Date fechaFin) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		List<HerramientaReporteDTO> lista = new ArrayList<>();
		try {
			statement = conexion.getSQLConexion().prepareCall(OBTENER_HERRAMIENTAS_MAS_PRESTADAS);
			statement.setInt(1, categoriaDTO.getId());
			statement.setDate(2, fechaInicio);
			statement.setDate(3, fechaFin);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				HerramientaReporteDTO reporte = new HerramientaReporteDTO();
				reporte.setCategoriaSeleccionada(categoriaDTO.getNombre());
				reporte.setCodigo(resultSet.getString("codigo"));
				reporte.setNombreHerramienta(resultSet.getString("nombre_Herramienta"));
				reporte.setNombreTecnico(resultSet.getString("nombre_Tecnico"));
				reporte.setCantidadPrestada(resultSet.getInt("cantidad"));
				if(resultSet.getString("nombreCategoriaHijo") != null)
					reporte.setCategoria(resultSet.getString("nombreCategoriaHijo"));
					else reporte.setCategoria("No tiene");
				reporte.setNombreCategoriaPadre(resultSet.getString("nombreCategoriaPadre"));
				reporte.setFechaInicio(Fechas.Date_a_Calendar(fechaInicio));
				reporte.setFechaFin(Fechas.Date_a_Calendar(fechaFin));
				reporte.setFechaFinHerramienta(fechaFin.toString());
				reporte.setFechaInicioHerramienta(fechaInicio.toString());

				lista.add(reporte);

			}
			if(categoriaDTO.tieneHijas())
			{
				for(int i = 0; i < categoriaDTO.getHijas().size(); i ++)
				{
					List<HerramientaReporteDTO> listaHijos = obtenerHerramientasMasPrestadas(categoriaDTO.getHijas().get(i), fechaInicio, fechaFin);
					if(!listaHijos.isEmpty()) lista.addAll(listaHijos);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
	@Override
	public RetiroHerramienta obtenerPrimerPrestamoHerramienta() {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		RetiroHerramienta retiro = null;
		try {
			statement = conexion.getSQLConexion().prepareCall(OBTENER_PRIMER_HERRAMIENTA_PRESTADA);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				retiro = new RetiroHerramienta();
				retiro.setFechaPrestamo(Fechas.Date_a_Calendar(resultSet.getDate("fechaPrestamo")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retiro;
	}

}
