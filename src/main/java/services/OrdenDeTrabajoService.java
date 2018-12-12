package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import domain.model.EnumEstadoOrdenTrabajo;
import domain.model.OrdenDeTrabajo;
import domain.model.Proyecto;
import domain.model.TipoActividad;
import domain.model.Usuario;
import domain.model.UsuarioLogueado;
import dto.EstadoOrdenTrabajoDTO;
import dto.OrdenDeTrabajoPorTecnicoDTO;
import dto.OrdenTrabajoDTO;
import dto.OrdenesReporteDTO;
import dto.ProyectoDTO;
import dto.TecnicoDTO;
import dto.TipoActividadDTO;
import javafx.beans.property.SimpleStringProperty;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.OrdenDeTrabajoDAO;
import ungs.org.service.mantis.access.ConectorMantisServiceImpl;
import ungs.org.service.mantis.exceptions.ConectorCambioEstadoException;
import ungs.org.service.mantis.exceptions.ConectorDatosIncorrectosException;
import ungs.org.service.mantis.exceptions.ConectorErrorMantisException;
import ungs.org.service.mantis.exceptions.ConectorLoginException;
import ungs.org.service.mantis.exceptions.ConectorPropertiesException;
import ungs.org.service.mantis.interfaz.ConectorMantisService;
import util.ConfigProperties;
import util.Dialogos;
import util.Fechas;

public class OrdenDeTrabajoService {
	private static OrdenDeTrabajoDAO ordenDeTrabajoDAO;
	private ConectorMantisService conectorMantis;

	public OrdenDeTrabajoService(DAOAbstractFactory metodoPersistencia) {
		OrdenDeTrabajoService.ordenDeTrabajoDAO = metodoPersistencia.createOrdenDeTrabajoDAO();
		try {
			conectorMantis = new ConectorMantisServiceImpl(ConfigProperties.configurationMantis());
		} catch (ConectorPropertiesException e) {
			Dialogos.error("Error con Ordenes de Trabajo","Error con el archivo de configuracion", e.getMessage());
		} catch (ConectorDatosIncorrectosException e) {
			Dialogos.error("Error con Ordenes de Trabajo","Error con los datos", e.getMessage());
		}
		try {
			UsuarioLogueado userLogueado = UsuarioLogueado.getInstancia();
			Usuario user = userLogueado.getUsuarioLogueado();
			conectorMantis.login(user.getUserMantis(),user.getClaveMantis());
		} catch (ConectorLoginException e) {
			Dialogos.error("Error con Ordenes de Trabajo","Error con el login", e.getMessage());
		}

	}

	public void agregarOrdenDeTrabajo(OrdenDeTrabajo ot) {
		ordenDeTrabajoDAO.insert(ot);
	}

	public void asignarTecnicos(Integer idMantis, List<String> listaTecnicos) {
		try {
			this.conectorMantis.pasarAsignada(idMantis, listaTecnicos);
		} catch (ConectorCambioEstadoException e) {
			Dialogos.error("Error con Orden de Trabajo","Error con la asignación de tecnicos", e.getMessage());
		}

	}

	public void cambiarEstadoOrdenResuelta(Integer idMantis) {
		try {
			this.conectorMantis.pasarResuelta(idMantis);
		} catch (ConectorCambioEstadoException e) {
			Dialogos.error("Error con Orden de Trabajo","Error con la resolución de Orden de trabajo", e.getMessage());
		}
	}

	public List<OrdenTrabajoDTO> obtenerOrdenDeTrabajosMantis() {
		List<OrdenTrabajoDTO> ordenes = new ArrayList<>();
		List<ungs.org.service.mantis.dto.OrdenDeTrabajo> ordenesMantisDTO = null;
		try {
			ordenesMantisDTO = conectorMantis.obtenerIssues();
		} catch (ConectorErrorMantisException e) {
			Dialogos.error("Error con Orden de Trabajo","Error con la obtención de Ordenes de Trabajo", e.getMessage());
		}
		// Quedan pendientes campos actividad y modulo/sede. No existen getters
		// apropiados a dichos campos en la libreria
		for (ungs.org.service.mantis.dto.OrdenDeTrabajo ordenMantisDTO : ordenesMantisDTO) {
			ProyectoDTO proyectoDTO = new ProyectoDTO();
			proyectoDTO.setNombre(new SimpleStringProperty(ordenMantisDTO.getProjecto().getNombre()));
			EstadoOrdenTrabajoDTO estadoOrdenTrabajoDTO = new EstadoOrdenTrabajoDTO();
			estadoOrdenTrabajoDTO.getEstado().set(ordenMantisDTO.getEstado().getName());
			OrdenTrabajoDTO ordenTrabajoDTO = new OrdenTrabajoDTO();
			ordenTrabajoDTO.getId().set(ordenMantisDTO.getId());
			ordenTrabajoDTO.getIdOrdenTrabajo().set(ordenMantisDTO.getId().toString());
			ordenTrabajoDTO.setProyecto(proyectoDTO);
			ordenTrabajoDTO.setFechaInicio(Fechas.CalendarTolocalDate(ordenMantisDTO.getFecha_creacion()));
			ordenTrabajoDTO.getResumen().set(ordenMantisDTO.getTitulo());
			ordenTrabajoDTO.getDescripcion().set(ordenMantisDTO.getDescripcion());
			ordenTrabajoDTO
					.setFechaUltimaModificacion(Fechas.CalendarTolocalDate(ordenMantisDTO.getFecha_actualizacion()));
			ordenTrabajoDTO.setEstadoOrdenTrabajo(estadoOrdenTrabajoDTO);
			OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
			ordenDeTrabajo.setId(0);
			ordenDeTrabajo.setIdOrdenDeTrabajo(ordenMantisDTO.getId().toString());
			Proyecto proyecto = new Proyecto();
			proyecto.setIdProyecto(ordenMantisDTO.getProjecto().getId());
			proyecto.setNombre(ordenMantisDTO.getProjecto().getNombre());
			ordenDeTrabajo.setProyecto(proyecto);
			ordenDeTrabajo.setFechaInicio(ordenMantisDTO.getFecha_creacion());
			ordenDeTrabajo.setResumen(ordenMantisDTO.getTitulo());
			ordenDeTrabajo.setDescripcion(ordenMantisDTO.getDescripcion());
			ordenDeTrabajo.setFechaUltimaModificacion(ordenMantisDTO.getFecha_actualizacion());
			ordenDeTrabajo.setEstadoOrdenTrabajo(obtenerEstadoOrden(ordenTrabajoDTO.getEstadoOrdenTrabajo()));
			if (!this.existe(ordenDeTrabajo)) {
				this.agregarOrdenDeTrabajo(ordenDeTrabajo);
			}
			ordenes.add(ordenTrabajoDTO);
		}

		return ordenes;
	}

	public int obtenerIdOrden(OrdenDeTrabajo ot) {
		return this.ordenDeTrabajoDAO.obtenerId(ot);
	}

	public void editarOrdenDeTrabajo(OrdenDeTrabajo ordenTrabajo) {
		ordenDeTrabajoDAO.edit(ordenTrabajo);
	}

	public void eliminarOrdenDeTrabajo(OrdenDeTrabajo ot) {
		OrdenDeTrabajoService.ordenDeTrabajoDAO.delete(ot);
	}

	public static List<OrdenDeTrabajo> obtenerOrdenDeTrabajos() {
		return OrdenDeTrabajoService.ordenDeTrabajoDAO.readAll();
	}

	public static List<OrdenTrabajoDTO> obtenerOrdenDeTrabajosDTO() {
		List<OrdenTrabajoDTO> ordenes = new ArrayList<OrdenTrabajoDTO>();
		for (OrdenDeTrabajo ot : OrdenDeTrabajoService.obtenerOrdenDeTrabajos()) {
			OrdenTrabajoDTO orden = ot.getDTO();
			ordenes.add(orden);
		}
		return ordenes;
	}

	public List<OrdenDeTrabajoPorTecnicoDTO> obtenerOrdenPorTecnicos(OrdenDeTrabajo ordenDeTrabajo) {
		return this.ordenDeTrabajoDAO.obtenerOrdenPorTecnicos(ordenDeTrabajo);
	}

	public List<TecnicoDTO> obtenerTecnicos(OrdenDeTrabajo ordenDeTrabajo) {
		return this.ordenDeTrabajoDAO.obtenerTecnicos(ordenDeTrabajo);
	}

	public static boolean existe(OrdenDeTrabajo ordenDeTrabajo) {
		if (ordenDeTrabajoDAO.obtenerId(ordenDeTrabajo) == 0) {
			return false;
		}
		return true;
	}

	public OrdenDeTrabajo obtenerOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		return ordenDeTrabajoDAO.getById(ordenDeTrabajo);
	}

	private static OrdenDeTrabajo buscarOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		for (OrdenDeTrabajo o : obtenerOrdenDeTrabajos())
			if (o.toString().equals(ordenDeTrabajo.toString()))
				return o;
		return null;
	}

	@Override
	public String toString() {
		return "OrdenDeTrabajoService [ordenDeTrabajo=" + ordenDeTrabajoDAO + "]";
	}

	public static int obtenerId(OrdenDeTrabajo ordenDeTrabajo) {
		return ordenDeTrabajo.getId();
	}

	public void finalizarOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		ordenDeTrabajoDAO.finalizarOrden(ordenDeTrabajo);
	}

	public List<ProyectoDTO> obtenerProyectosDTO() {
		List<ProyectoDTO> proyectos = new ArrayList<ProyectoDTO>();
		for (Proyecto proyecto : ordenDeTrabajoDAO.readAllProyectos()) {
			ProyectoDTO proyectoDTO = proyecto.getDTO();
			proyectos.add(proyectoDTO);
		}
		return proyectos;
	}

	public List<TipoActividadDTO> obtenerTiposActividadDTO() {
		List<TipoActividadDTO> tipos = new ArrayList<TipoActividadDTO>();
		for (TipoActividad tipo : ordenDeTrabajoDAO.readAllTiposActividad()) {
			TipoActividadDTO tipoDTO = tipo.getDTO();
			tipos.add(tipoDTO);
		}
		return tipos;
	}

	public void cambiarEstadoAsignada(OrdenDeTrabajo ordenDeTrabajo) {
		this.ordenDeTrabajoDAO.cambiarEstadoAsignado(ordenDeTrabajo);

	}

	public List<OrdenesReporteDTO> obtenerOrdenDeTrabajoReporte(Date dateInicio, Date dateFin, int nuevo, int asignada,
			int realizada, int cerrada, int suspendida) {
		return ordenDeTrabajoDAO.obtenerOrdenesDeTrabajoPorReporte(dateInicio, dateFin, nuevo, asignada, realizada,
				cerrada, suspendida);
	}

	public OrdenDeTrabajo obtenerPrimerOrden() {
		return ordenDeTrabajoDAO.obtenerPrimerOrden();
	}

	public EnumEstadoOrdenTrabajo obtenerEstadoOrden(EstadoOrdenTrabajoDTO estado) {
		if (estado.getEstado().get().equals("NUEVA")) {
			return EnumEstadoOrdenTrabajo.NUEVA;
		}
		if (estado.getEstado().get().equals("CERRADA")) {
			return EnumEstadoOrdenTrabajo.CERRADA;
		}
		if (estado.getEstado().get().equals("ASIGNADA")) {
			return EnumEstadoOrdenTrabajo.ASIGNADA;
		}
		if (estado.getEstado().get().equals("RESUELTA")) {
			return EnumEstadoOrdenTrabajo.REALIZADA;
		}
		if (estado.getEstado().get().equals("SUSPENDIDA")) {
			return EnumEstadoOrdenTrabajo.SUSPENDIDA;
		}
		return null;
	}

	public OrdenDeTrabajo obtenerOrdenTrabajoSinDevolucionesPendientes(String idMantis) {
		return ordenDeTrabajoDAO.getOrdenSinDevolucionesPendientes(idMantis);
	}

	public boolean ordenTrabajoSinDevolucionesPendientes(String idMantis) {
		if (obtenerOrdenTrabajoSinDevolucionesPendientes(idMantis) != null)
			return true;
		return false;
	}

	public void cambiarEstadoResuelta(OrdenDeTrabajo ot) {
		this.ordenDeTrabajoDAO.cambiarEstadoResuelto(ot);
	}

	public void cambiarEstadoCerrada(OrdenDeTrabajo ot) {
		this.ordenDeTrabajoDAO.cambiarEstadoCerrada(ot);
	}

	public void cambiarEstadoSuspendida(OrdenDeTrabajo ot) {
		this.ordenDeTrabajoDAO.cambiarEstadoSuspendida(ot);
	}

	public void cambiarOrdenACerrada(int idMantis) {
		try {
			conectorMantis.pasarCerrada(idMantis);
		} catch (ConectorCambioEstadoException e) {
			Dialogos.error("Error con Orden de Trabajo","Error cambiando a Cerrada la Orden", e.getMessage());
		} catch (ConectorDatosIncorrectosException e) {
			Dialogos.error("Error con Orden de Trabajo","Error con los datos", e.getMessage());
		}
	}

	public void cambiarOrdenSuspendida(int idMantis) {
		try {
			conectorMantis.pasarSuspendida(idMantis);
		} catch (ConectorCambioEstadoException e) {
			Dialogos.error("Error con Orden de Trabajo","Error cambiando a Suspendida la Orden", e.getMessage());
		}
	}
}