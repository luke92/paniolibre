package persistencia.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.model.EnumEstadoOrdenTrabajo;
import domain.model.Estado;
import domain.model.OrdenDeTrabajo;
import domain.model.Proyecto;
import domain.model.TipoActividad;
import dto.OrdenDeTrabajoPorTecnicoDTO;
import dto.OrdenesReporteDTO;
import dto.TecnicoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.OrdenDeTrabajoDAO;
import util.Fechas;

public class OrdenDeTrabajoDAOSQL implements OrdenDeTrabajoDAO {
	private static final String OBTENER_ORDEN_SIN_DEVOLUCIONES_PENDIENTES = "{CALL obtenerOrdenDeTrabajoSinDevolucionesPendientes(?)}";
	private static final String INSERT = "{call cargarOrdenTrabajo(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String DELETE = "DELETE FROM OrdenesTrabajo WHERE id= ?";
	private static final String READ_ALL = "select * from ordenestrabajo where estadoOrdenTrabajo_id = 1 or estadoOrdenTrabajo_id = 2 ";
	private static final String GET_ORDENES_POR_TECNICOS = "{call obtenerTecnicosAsociadosAOrdenTrabajoAsignadaPorIdMantis(?)}";
	private static final String EDIT = "{call editarOrdenTrabajo(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String REALIZADA = "UPDATE OrdenesTrabajo SET EstadoOrdenTrabajo_id = 3 WHERE id = ? ";
	private static final String SUSPENDIDA = "UPDATE OrdenesTrabajo SET EstadoOrdenTrabajo_id = 5 WHERE id = ? ";
	private static final String ASIGNADA = "UPDATE OrdenesTrabajo SET EstadoOrdenTrabajo_id = 2 WHERE id = ? ";
	private static final String CERRADA = "UPDATE OrdenesTrabajo SET EstadoOrdenTrabajo_id = 4 WHERE id = ? ";
	private static final String GET_BY_ID = "{call obtenerOrdenTrabajoxId(?,?)}";
	private static final String READ_ALL_PROYECTOS = "SELECT * FROM Proyectos";
	private static final String READ_ALL_TIPOS_ACTIVIDAD = "SELECT * FROM TiposActividad";
	private static final String GET_ID_ORDEN = "select id from ordenestrabajo where idOrdenTrabajo = ?";
	private static final String OBTENER_ORDENES_REPORTES = "{CALL obtenerOrdenesDeTrabajoReporte(?,?,?,?,?,?,?)}";
	private static final String OBTENER_PRIMER_ORDEN = "{CALL obtenerPrimerOrdenDeTrabajo()}";

	@Override
	public boolean insert(OrdenDeTrabajo ordenDeTrabajo) {
		CallableStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(INSERT);
			statement.setString(1, ordenDeTrabajo.getIdOrdenDeTrabajo());
			statement.setInt(2, ordenDeTrabajo.getProyecto().getIdProyecto());
			statement.setDate(3, Fechas.Calendar_a_DateSQL(ordenDeTrabajo.getFechaInicio()));
			statement.setString(4, ordenDeTrabajo.getArea());
			statement.setString(4, ordenDeTrabajo.getArea());
			statement.setString(5, ordenDeTrabajo.getPrioridad());
			statement.setString(6, ordenDeTrabajo.getResumen());
			statement.setString(7, ordenDeTrabajo.getDescripcion());
			statement.setNull(8, java.sql.Types.INTEGER);
			statement.setNull(9, java.sql.Types.VARCHAR);
			statement.setNull(10, java.sql.Types.VARCHAR);
			statement.setString(11, ordenDeTrabajo.getTelefonoContacto());
			statement.setString(12, ordenDeTrabajo.getDisponibilidad());

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean edit(OrdenDeTrabajo ordenDeTrabajo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(EDIT);
			statement.setString(1, ordenDeTrabajo.getIdOrdenDeTrabajo());
			statement.setInt(2, ordenDeTrabajo.getProyecto().getIdProyecto());
			statement.setDate(3, Fechas.Calendar_a_DateSQL(ordenDeTrabajo.getFechaInicio()));
			statement.setString(4, ordenDeTrabajo.getArea());
			statement.setString(4, ordenDeTrabajo.getArea());
			statement.setString(5, ordenDeTrabajo.getPrioridad());
			statement.setString(6, ordenDeTrabajo.getResumen());
			statement.setString(7, ordenDeTrabajo.getDescripcion());
			statement.setInt(8, ordenDeTrabajo.getTipoActividad().getIdTipoActividad());
			statement.setString(9, ordenDeTrabajo.getModuloSede());
			statement.setString(10, ordenDeTrabajo.getAulaOficina());
			statement.setString(11, ordenDeTrabajo.getTelefonoContacto());
			statement.setString(12, ordenDeTrabajo.getDisponibilidad());
			statement.setInt(13, ordenDeTrabajo.getId());
			statement.setInt(14, ordenDeTrabajo.getEstadoOrdenTrabajo().getValor());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(OrdenDeTrabajo ordenDeTrabajoAEliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(DELETE);
			statement.setInt(1, ordenDeTrabajoAEliminar.getId());
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<OrdenDeTrabajo> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<OrdenDeTrabajo> ordenesDeTrabajo = new ArrayList<OrdenDeTrabajo>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				OrdenDeTrabajo ot = new OrdenDeTrabajo();
				ot.setId(resultSet.getInt("id"));
				ot.setActivo(Estado.getEstado(resultSet.getInt("activo")));
				ot.setEstadoOrdenTrabajo(EnumEstadoOrdenTrabajo.getEstado(resultSet.getInt("estadoOrdenTrabajo_id")));
				ot.setFechaInicio(Fechas.Date_a_Calendar(resultSet.getDate("fechaInicio")));
				ot.setFechaUltimaModificacion(Fechas.Date_a_Calendar(resultSet.getDate("fechaUltimaModificacion")));
				ot.setIdOrdenDeTrabajo(resultSet.getString("idOrdenTrabajo"));
				ot.setResumen(resultSet.getString("resumen"));
				ordenesDeTrabajo.add(ot);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordenesDeTrabajo;
	}

	@Override
	public boolean finalizarOrden(OrdenDeTrabajo ordenTrabajoAFinalizar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(REALIZADA);
			statement.setInt(1, ordenTrabajoAFinalizar.getId());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public OrdenDeTrabajo getById(OrdenDeTrabajo ordenDeTrabajoABuscar) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		OrdenDeTrabajo ot = null;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_BY_ID);
			statement.setInt(1, ordenDeTrabajoABuscar.getId());
			statement.setString(2, ordenDeTrabajoABuscar.getIdOrdenDeTrabajo());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ot = new OrdenDeTrabajo();
				ot.setId(resultSet.getInt("id"));
				ot.setActivo(Estado.getEstado(resultSet.getInt("activo")));
				ot.setArea(resultSet.getString("area"));
				ot.setAulaOficina(resultSet.getString("aulaOficina"));
				ot.setDescripcion(resultSet.getString("descripcion"));
				ot.setDisponibilidad(resultSet.getString("disponibilidad"));
				ot.setEstadoOrdenTrabajo(EnumEstadoOrdenTrabajo.getEstado(resultSet.getInt("estadoOrdenTrabajo_id")));
				ot.setFechaInicio(Fechas.Date_a_Calendar(resultSet.getDate("fechaInicio")));
				ot.setFechaUltimaModificacion(Fechas.Date_a_Calendar(resultSet.getDate("fechaUltimaModificacion")));
				ot.setIdOrdenDeTrabajo(resultSet.getString("idOrdenTrabajo"));
				ot.setModuloSede(resultSet.getString("moduloSede"));
				ot.setPrioridad(resultSet.getString("prioridad"));
				Proyecto proyecto = new Proyecto();
				proyecto.setIdProyecto(resultSet.getInt("proyecto_id"));
				proyecto.setNombre(resultSet.getString("nombreProyecto"));
				ot.setProyecto(proyecto);
				ot.setResumen(resultSet.getString("resumen"));
				ot.setTelefonoContacto(resultSet.getString("telefonoContacto"));
				TipoActividad tipoActividad = new TipoActividad();
				tipoActividad.setIdTipoActividad(resultSet.getInt("tipoActividad_id"));
				tipoActividad.setNombre(resultSet.getString("nombreTipoActividad"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ot;
	}

	public int obtenerId(OrdenDeTrabajo ordenDeTrabajoABuscar) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int id = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_ID_ORDEN);
			statement.setString(1, ordenDeTrabajoABuscar.getIdOrdenDeTrabajo());

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public List<TipoActividad> readAllTiposActividad() {
		PreparedStatement statement;
		ResultSet resultSet;
		List<TipoActividad> tiposActividad = new ArrayList<TipoActividad>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL_TIPOS_ACTIVIDAD);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				TipoActividad tipo = new TipoActividad();
				tipo.setIdTipoActividad(resultSet.getInt("id"));
				tipo.setNombre(resultSet.getString("nombre"));
				tiposActividad.add(tipo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tiposActividad;
	}

	@Override
	public List<Proyecto> readAllProyectos() {
		PreparedStatement statement;
		ResultSet resultSet;
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL_PROYECTOS);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Proyecto proyecto = new Proyecto();
				proyecto.setIdProyecto(resultSet.getInt("id"));
				proyecto.setNombre(resultSet.getString("nombre"));
				proyectos.add(proyecto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return proyectos;
	}

	@Override
	public boolean cambiarEstadoAsignado(OrdenDeTrabajo ordenAModificar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(ASIGNADA);
			statement.setInt(1, ordenAModificar.getId());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<OrdenDeTrabajoPorTecnicoDTO> obtenerOrdenPorTecnicos(OrdenDeTrabajo ordenDeTrabajo) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<OrdenDeTrabajoPorTecnicoDTO> ordenesDeTrabajo = new ArrayList<OrdenDeTrabajoPorTecnicoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_ORDENES_POR_TECNICOS);
			statement.setString(1, ordenDeTrabajo.getIdOrdenDeTrabajo());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				List<TecnicoDTO> tecnicos = new ArrayList<>();
				TecnicoDTO tecnicoDTO = new TecnicoDTO();
				tecnicoDTO.setNombre(resultSet.getString("nombreTecnico"));
				tecnicoDTO.setApellido(resultSet.getString("apellidoTecnico"));
				tecnicos.add(tecnicoDTO);
				OrdenDeTrabajoPorTecnicoDTO dto = new OrdenDeTrabajoPorTecnicoDTO(resultSet.getString("idOrdenTrabajo"),
						"", tecnicos);
				ordenesDeTrabajo.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordenesDeTrabajo;
	}

	public List<TecnicoDTO> obtenerTecnicos(OrdenDeTrabajo ordenDeTrabajo) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		List<TecnicoDTO> tecnicos = new ArrayList<>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareCall(GET_ORDENES_POR_TECNICOS);
			statement.setString(1, ordenDeTrabajo.getIdOrdenDeTrabajo());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				TecnicoDTO tecnicoDTO = new TecnicoDTO();
				tecnicoDTO.setIdTecnico(resultSet.getInt("idTecnico"));
				tecnicoDTO.setNombre(resultSet.getString("nombreTecnico"));
				tecnicos.add(tecnicoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tecnicos;
	}

	@Override
	public List<OrdenesReporteDTO> obtenerOrdenesDeTrabajoPorReporte(Date dateInicio, Date dateFin, int nuevo,
			int asignada, int realizada, int cerrada, int suspendida) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		List<OrdenesReporteDTO> lista = new ArrayList<>();
		try {
			statement = conexion.getSQLConexion().prepareCall(OBTENER_ORDENES_REPORTES);
			statement.setDate(1, dateInicio);
			statement.setDate(2, dateFin);
			statement.setInt(3, nuevo);
			statement.setInt(4, asignada);
			statement.setInt(5, realizada);
			statement.setInt(6, cerrada);
			statement.setInt(7, suspendida);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				OrdenesReporteDTO reporte = new OrdenesReporteDTO();
				reporte.setFechaUltModificacion(resultSet.getDate("fechaUltimaModificacion"));
				reporte.setFechaUltModfOrden(reporte.getFechaUltModificacion().toString());
				reporte.setFechaInicioOrden(resultSet.getDate("fechaInicio"));
				reporte.setFechaInicioOrdenTrabajo(reporte.getFechaInicioOrden().toString());
				reporte.setFechaInicio(Fechas.Date_a_Calendar(dateInicio));
				reporte.setFechaFin(Fechas.Date_a_Calendar(dateFin));
				reporte.setFechaFinReporte(dateFin.toString());
				reporte.setFechaInicioReporte(dateInicio.toString());
				reporte.setIdMantis(resultSet.getString("idOrdenTrabajo"));
				reporte.setNombreProyecto(resultSet.getString("nombre"));
				reporte.setResumen(resultSet.getString("resumen"));
				reporte.setEstado(resultSet.getString("estado"));
				lista.add(reporte);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	@Override
	public OrdenDeTrabajo obtenerPrimerOrden() {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		OrdenDeTrabajo orden = null;
		try {
			statement = conexion.getSQLConexion().prepareCall(OBTENER_PRIMER_ORDEN);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				orden = new OrdenDeTrabajo();
				orden.setFechaInicio(Fechas.Date_a_Calendar(resultSet.getDate("fechaInicio")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orden;
	}

	@Override
	public OrdenDeTrabajo getOrdenSinDevolucionesPendientes(String idMantis) {
		CallableStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		OrdenDeTrabajo ot = null;
		try {
			statement = conexion.getSQLConexion().prepareCall(OBTENER_ORDEN_SIN_DEVOLUCIONES_PENDIENTES);
			statement.setString(1, idMantis);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ot = new OrdenDeTrabajo();
				ot.setId(resultSet.getInt("id"));
				ot.setActivo(Estado.getEstado(resultSet.getInt("activo")));
				ot.setArea(resultSet.getString("area"));
				ot.setAulaOficina(resultSet.getString("aulaOficina"));
				ot.setDescripcion(resultSet.getString("descripcion"));
				ot.setDisponibilidad(resultSet.getString("disponibilidad"));
				ot.setEstadoOrdenTrabajo(EnumEstadoOrdenTrabajo.getEstado(resultSet.getInt("estadoOrdenTrabajo_id")));
				ot.setFechaInicio(Fechas.Date_a_Calendar(resultSet.getDate("fechaInicio")));
				ot.setFechaUltimaModificacion(Fechas.Date_a_Calendar(resultSet.getDate("fechaUltimaModificacion")));
				ot.setIdOrdenDeTrabajo(resultSet.getString("idOrdenTrabajo"));
				ot.setModuloSede(resultSet.getString("moduloSede"));
				ot.setPrioridad(resultSet.getString("prioridad"));
				Proyecto proyecto = new Proyecto();
				proyecto.setIdProyecto(resultSet.getInt("proyecto_id"));
				ot.setProyecto(proyecto);
				ot.setResumen(resultSet.getString("resumen"));
				ot.setTelefonoContacto(resultSet.getString("telefonoContacto"));
				TipoActividad tipoActividad = new TipoActividad();
				tipoActividad.setIdTipoActividad(resultSet.getInt("tipoActividad_id"));
				ot.setTipoActividad(tipoActividad);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ot;
	}

	@Override
	public boolean cambiarEstadoResuelto(OrdenDeTrabajo ot) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(REALIZADA);
			statement.setInt(1, ot.getId());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean cambiarEstadoCerrada(OrdenDeTrabajo ot) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(CERRADA);
			statement.setInt(1, ot.getId());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean cambiarEstadoSuspendida(OrdenDeTrabajo ot) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(SUSPENDIDA);
			statement.setInt(1, ot.getId());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}