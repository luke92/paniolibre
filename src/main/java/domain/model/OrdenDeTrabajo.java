package domain.model;

import java.util.Calendar;

import dto.OrdenTrabajoDTO;
import dto.ProyectoDTO;
import util.Fechas;

public class OrdenDeTrabajo {
	private Integer id;
	private String idOrdenDeTrabajo;
	private Proyecto proyecto;
	private Calendar fechaInicio;
	private String area;
	private String prioridad;
	private String resumen;
	private String descripcion;
	private TipoActividad tipoActividad;
	private String moduloSede;
	private String aulaOficina;
	private String telefonoContacto;
	private String disponibilidad;
	private Calendar fechaUltimaModificacion;
	private EnumEstadoOrdenTrabajo estadoOrdenTrabajo;
	private Estado activo;

	public OrdenDeTrabajo() {
	}

	public OrdenDeTrabajo(String idOrdenDeTrabajo, Proyecto proyecto, Calendar fechaInicio, String resumen,
			String descripcion, TipoActividad tipoActividad, String moduloSede, Calendar fechaUltimaModificacion,
			EnumEstadoOrdenTrabajo estadoOrdenTrabajo, Estado activo) {
		super();
		this.idOrdenDeTrabajo = idOrdenDeTrabajo;
		this.proyecto = proyecto;
		this.fechaInicio = fechaInicio;
		this.resumen = resumen;
		this.descripcion = descripcion;
		this.tipoActividad = tipoActividad;
		this.moduloSede = moduloSede;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.estadoOrdenTrabajo = estadoOrdenTrabajo;
		this.activo = activo;
	}

	public OrdenDeTrabajo(Integer id, String idOrdenDeTrabajo, Proyecto proyecto, Calendar fechaInicio, String area,
			String prioridad, String resumen, String descripcion, TipoActividad tipoActividad, String moduloSede,
			String aulaOficina, String telefonoContacto, String disponibilidad, Calendar fechaUltimaModificacion,
			EnumEstadoOrdenTrabajo estadoOrdenTrabajo, Estado activo) {
		super();
		this.id = id;
		this.idOrdenDeTrabajo = idOrdenDeTrabajo;
		this.proyecto = proyecto;
		this.fechaInicio = fechaInicio;
		this.area = area;
		this.prioridad = prioridad;
		this.resumen = resumen;
		this.descripcion = descripcion;
		this.tipoActividad = tipoActividad;
		this.moduloSede = moduloSede;
		this.aulaOficina = aulaOficina;
		this.telefonoContacto = telefonoContacto;
		this.disponibilidad = disponibilidad;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.estadoOrdenTrabajo = estadoOrdenTrabajo;
		this.activo = activo;
	}

	public String getIdOrdenDeTrabajo() {
		return idOrdenDeTrabajo;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Calendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoActividad getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public String getModuloSede() {
		return moduloSede;
	}

	public void setModuloSede(String moduloSede) {
		this.moduloSede = moduloSede;
	}

	public EnumEstadoOrdenTrabajo getEstadoOrdenTrabajo() {
		return estadoOrdenTrabajo;
	}

	public void setEstadoOrdenTrabajo(EnumEstadoOrdenTrabajo estadoOrdenTrabajo) {
		this.estadoOrdenTrabajo = estadoOrdenTrabajo;
	}

	public Estado getActivo() {
		return activo;
	}

	public void setActivo(Estado activo) {
		this.activo = activo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getAulaOficina() {
		return aulaOficina;
	}

	public void setAulaOficina(String aulaOficina) {
		this.aulaOficina = aulaOficina;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Calendar getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Calendar fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public void setIdOrdenDeTrabajo(String idOrdenDeTrabajo) {
		this.idOrdenDeTrabajo = idOrdenDeTrabajo;
	}

	public OrdenTrabajoDTO getDTO() {
		OrdenTrabajoDTO orden = new OrdenTrabajoDTO();
		orden.getId().set(this.getId());
		orden.getIdOrdenTrabajo().set(this.getIdOrdenDeTrabajo());
		orden.setFechaInicio(Fechas.CalendarTolocalDate(this.getFechaInicio()));
		orden.getResumen().set(this.getResumen());
		orden.getDescripcion().set(this.getDescripcion());
		orden.setFechaUltimaModificacion(Fechas.CalendarTolocalDate(this.getFechaUltimaModificacion()));
		orden.setEstadoOrdenTrabajo(this.getEstadoOrdenTrabajo().getDTO());
		return orden;

	}

}