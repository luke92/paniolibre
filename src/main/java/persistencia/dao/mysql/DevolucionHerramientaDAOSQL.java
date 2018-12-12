package persistencia.dao.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.DevolucionHerramienta;
import domain.model.EnumEstadoHerramienta;
import domain.model.Herramienta;
import domain.model.OrdenDeTrabajo;
import domain.model.Tecnico;
import domain.model.Usuario;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.DevolucionHerramientaDAO;
import util.Fechas;

public class DevolucionHerramientaDAOSQL implements DevolucionHerramientaDAO {

	private static final String INSERT = "INSERT INTO DevolucionesHerramientas(id,usuario_id,tecnico_id,herramienta_id,fechaDevolucion,estadoHerramienta_id,comentario) VALUES(?,?,?,?,?,?,?)";
	private static final String INSERT_CON_ORDEN_DE_TRABAJO = "INSERT INTO DevolucionesHerramientas(id,usuario_id,tecnico_id,herramienta_id,ordenTrabajo_id,fechaDevolucion,estadoHerramienta_id,comentario) VALUES(?,?,?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM DevolucionesHerramientas WHERE id = ?";
	private static final String READ_ALL = "SELECT * FROM DevolucionesHerramientas";
	private static final String EDIT = "UPDATE DevolucionesHerramientas set usuario_id = ?,tecnico_id = ?,herramienta_id = ?,fechaDevolucion = ? ,estadoHerramienta_id = ? WHERE DevolucionesHerramientas.id = ? ";
	private static final String EDIT_CON_ORDEN_DE_TRABAJO = "UPDATE DevolucionesHerramientas set usuario_id = ?,tecnico_id = ?,herramienta_id = ?,ordenTrabajo_id = ?,fechaDevolucion = ?,estadoHerramienta_id = ? WHERE DevolucionesHerramientas.id = ? ";
	private static final String GET_ID = "SELECT id from DevolucionesHerramientas where DevolucionesHerramientas.usuario_id = ? AND DevolucionesHerramientas.tecnico_id = ? AND DevolucionesHerramientas.herramienta_id = ?";

	public boolean insert(DevolucionHerramienta devolucionHerramienta) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (devolucionHerramienta.getOrdenDeTrabajo() == null) {
				statement = conexion.getSQLConexion().prepareStatement(INSERT);
				statement.setInt(1, devolucionHerramienta.getIdDevolucionHerramienta());
				statement.setInt(2, devolucionHerramienta.getUsuario().getIdUsuario());
				statement.setInt(3, devolucionHerramienta.getTecnico().getIdTecnico());
				statement.setInt(4, devolucionHerramienta.getHerramienta().getIdHerramienta());
				statement.setDate(5, (Date) Fechas.Calendar_a_DateSQL(devolucionHerramienta.getFechaDevolucion()));
				statement.setInt(6, devolucionHerramienta.getEstado().getValor());
				statement.setString(7, devolucionHerramienta.getComentario());
			} else {
				statement = conexion.getSQLConexion().prepareStatement(INSERT_CON_ORDEN_DE_TRABAJO);
				statement.setInt(1, devolucionHerramienta.getIdDevolucionHerramienta());
				statement.setInt(2, devolucionHerramienta.getUsuario().getIdUsuario());
				statement.setInt(3, devolucionHerramienta.getTecnico().getIdTecnico());
				statement.setInt(4, devolucionHerramienta.getHerramienta().getIdHerramienta());
				statement.setInt(5, devolucionHerramienta.getOrdenDeTrabajo().getId());
				statement.setDate(6, (Date) Fechas.Calendar_a_DateSQL(devolucionHerramienta.getFechaDevolucion()));
				statement.setInt(7, devolucionHerramienta.getEstado().getValor());
				statement.setString(8, devolucionHerramienta.getComentario());
			}

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(DevolucionHerramienta devolucionHerramienta) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setString(1, Integer.toString(devolucionHerramienta.getIdDevolucionHerramienta()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<DevolucionHerramienta> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<DevolucionHerramienta> devolucionHerramientas = new ArrayList<DevolucionHerramienta>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				DevolucionHerramienta devolucionHerramienta = new DevolucionHerramienta();
				devolucionHerramienta.setIdDevolucionHerramienta(resultSet.getInt("id"));
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt("usuario_id"));
				devolucionHerramienta.setUsuario(usuario);
				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(resultSet.getInt("tecnico_id"));
				devolucionHerramienta.setTecnico(tecnico);
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(resultSet.getInt("herramienta_id"));
				OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
				ordenDeTrabajo.setId(resultSet.getInt("ordenTrabajo_id"));
				devolucionHerramienta.setFechaDevolucion(Fechas.Date_a_Calendar(resultSet.getDate("fechaDevolucion")));
				devolucionHerramienta.setEstado(obtenerEstado(resultSet.getInt("estadoHerramienta_id")));
				devolucionHerramientas.add(devolucionHerramienta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return devolucionHerramientas;
	}

	public boolean edit(DevolucionHerramienta devolucionHerramientaAEditar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			if (devolucionHerramientaAEditar.getOrdenDeTrabajo() == null) {
				statement = conexion.getSQLConexion().prepareStatement(EDIT);
				statement.setInt(1, devolucionHerramientaAEditar.getUsuario().getIdUsuario());
				statement.setInt(2, devolucionHerramientaAEditar.getTecnico().getIdTecnico());
				statement.setInt(3, devolucionHerramientaAEditar.getHerramienta().getIdHerramienta());
				statement.setDate(4,
						(Date) Fechas.Calendar_a_DateSQL(devolucionHerramientaAEditar.getFechaDevolucion()));
				statement.setInt(5, devolucionHerramientaAEditar.getEstado().getValor());
				statement.setInt(6, devolucionHerramientaAEditar.getIdDevolucionHerramienta());
			} else {
				statement = conexion.getSQLConexion().prepareStatement(EDIT_CON_ORDEN_DE_TRABAJO);
				statement.setInt(1, devolucionHerramientaAEditar.getUsuario().getIdUsuario());
				statement.setInt(2, devolucionHerramientaAEditar.getTecnico().getIdTecnico());
				statement.setInt(3, devolucionHerramientaAEditar.getHerramienta().getIdHerramienta());
				statement.setInt(4, devolucionHerramientaAEditar.getOrdenDeTrabajo().getId());
				statement.setDate(5,
						(Date) Fechas.Calendar_a_DateSQL(devolucionHerramientaAEditar.getFechaDevolucion()));
				statement.setInt(6, devolucionHerramientaAEditar.getEstado().getValor());
				statement.setInt(7, devolucionHerramientaAEditar.getIdDevolucionHerramienta());
			}

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int obtenerIdDevolucionHerramienta(DevolucionHerramienta devolucionHerramienta) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idRetiroHerramienta = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(GET_ID);
			statement.setInt(1, devolucionHerramienta.getUsuario().getIdUsuario());
			statement.setInt(2, devolucionHerramienta.getTecnico().getIdTecnico());
			statement.setInt(3, devolucionHerramienta.getHerramienta().getIdHerramienta());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idRetiroHerramienta = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idRetiroHerramienta;
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

}
