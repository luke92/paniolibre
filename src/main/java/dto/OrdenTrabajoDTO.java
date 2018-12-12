package dto;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrdenTrabajoDTO {

	private IntegerProperty id;
	private StringProperty idOrdenTrabajo;
	private ProyectoDTO proyecto;
	private LocalDate fechaInicio;
	private StringProperty area;
	private StringProperty prioridad;
	private StringProperty resumen;
	private StringProperty descripcion;
	private TipoActividadDTO tipoActividad;
	private StringProperty moduloSede;
	private StringProperty aulaOficina;
	private StringProperty telefonoContacto;
	private StringProperty disponibilidad;
	private LocalDate fechaUltimaModificacion;
	private EstadoOrdenTrabajoDTO estadoOrdenTrabajo;
	private EstadoDTO activo;

	public OrdenTrabajoDTO() {
		this.id = new SimpleIntegerProperty();
		this.idOrdenTrabajo = new SimpleStringProperty();
		this.proyecto = new ProyectoDTO();
		this.fechaInicio = LocalDate.now();
		this.area = new SimpleStringProperty();
		this.prioridad = new SimpleStringProperty();
		this.resumen = new SimpleStringProperty();
		this.descripcion = new SimpleStringProperty();
		this.tipoActividad = new TipoActividadDTO();
		this.moduloSede = new SimpleStringProperty();
		this.aulaOficina = new SimpleStringProperty();
		this.telefonoContacto = new SimpleStringProperty();
		this.disponibilidad = new SimpleStringProperty();
		this.fechaUltimaModificacion = LocalDate.now();
		this.estadoOrdenTrabajo = new EstadoOrdenTrabajoDTO();
	}

	public OrdenTrabajoDTO(IntegerProperty id, StringProperty idOrdenTrabajo, ProyectoDTO proyecto,
			LocalDate fechaInicio, StringProperty area, StringProperty prioridad, StringProperty resumen,
			StringProperty descripcion, TipoActividadDTO tipoActividad, StringProperty moduloSede,
			StringProperty aulaOficina, StringProperty telefonoContacto, StringProperty disponibilidad,
			LocalDate fechaUltimaModificacion, EstadoOrdenTrabajoDTO estadoOrdenTrabajo, EstadoDTO activo) {
		super();
		this.id = id;
		this.idOrdenTrabajo = idOrdenTrabajo;
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

	public OrdenTrabajoDTO(IntegerProperty id, StringProperty idOrdenTrabajo, ProyectoDTO proyecto,
			LocalDate fechaInicio, StringProperty resumen, StringProperty descripcion, TipoActividadDTO tipoActividad,
			LocalDate fechaUltimaModificacion, EstadoOrdenTrabajoDTO estadoOrdenTrabajo, EstadoDTO activo) {
		super();
		this.id = id;
		this.idOrdenTrabajo = idOrdenTrabajo;
		this.proyecto = proyecto;
		this.fechaInicio = fechaInicio;
		this.resumen = resumen;
		this.descripcion = descripcion;
		this.tipoActividad = tipoActividad;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.estadoOrdenTrabajo = estadoOrdenTrabajo;
		this.activo = activo;
	}

	public IntegerProperty getId() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public StringProperty getIdOrdenTrabajo() {
		return idOrdenTrabajo;
	}

	public void setIdOrdenTrabajo(String idOrdenTrabajo) {
		this.idOrdenTrabajo.set(idOrdenTrabajo);
	}

	public ProyectoDTO getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoDTO proyecto) {
		this.proyecto = proyecto;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public StringProperty getArea() {
		return area;
	}

	public void setArea(StringProperty area) {
		this.area = area;
	}

	public StringProperty getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(StringProperty prioridad) {
		this.prioridad = prioridad;
	}

	public StringProperty getResumen() {
		return resumen;
	}

	public void setResumen(StringProperty resumen) {
		this.resumen = resumen;
	}

	public StringProperty getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(StringProperty descripcion) {
		this.descripcion = descripcion;
	}

	public TipoActividadDTO getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividadDTO tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public StringProperty getModuloSede() {
		return moduloSede;
	}

	public void setModuloSede(StringProperty moduloSede) {
		this.moduloSede = moduloSede;
	}

	public StringProperty getAulaOficina() {
		return aulaOficina;
	}

	public void setAulaOficina(StringProperty aulaOficina) {
		this.aulaOficina = aulaOficina;
	}

	public StringProperty getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(StringProperty telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public StringProperty getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(StringProperty disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public LocalDate getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(LocalDate fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public EstadoOrdenTrabajoDTO getEstadoOrdenTrabajo() {
		return estadoOrdenTrabajo;
	}

	public void setEstadoOrdenTrabajo(EstadoOrdenTrabajoDTO estadoOrdenTrabajo) {
		this.estadoOrdenTrabajo = estadoOrdenTrabajo;
	}

	public EstadoDTO getActivo() {
		return activo;
	}

	public void setActivo(EstadoDTO activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Orden de Trabajo [ Id Mantis: " + idOrdenTrabajo.get() + ", Proyecto: " + proyecto.getNombre().get()
				+ ", Fecha de Inicio: " + fechaInicio.toString() + ", Resumen: " + resumen.get() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activo == null) ? 0 : activo.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((aulaOficina == null) ? 0 : aulaOficina.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((disponibilidad == null) ? 0 : disponibilidad.hashCode());
		result = prime * result + ((estadoOrdenTrabajo == null) ? 0 : estadoOrdenTrabajo.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((fechaUltimaModificacion == null) ? 0 : fechaUltimaModificacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idOrdenTrabajo == null) ? 0 : idOrdenTrabajo.hashCode());
		result = prime * result + ((moduloSede == null) ? 0 : moduloSede.hashCode());
		result = prime * result + ((prioridad == null) ? 0 : prioridad.hashCode());
		result = prime * result + ((proyecto == null) ? 0 : proyecto.hashCode());
		result = prime * result + ((resumen == null) ? 0 : resumen.hashCode());
		result = prime * result + ((telefonoContacto == null) ? 0 : telefonoContacto.hashCode());
		result = prime * result + ((tipoActividad == null) ? 0 : tipoActividad.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdenTrabajoDTO other = (OrdenTrabajoDTO) obj;
		if (activo != other.activo)
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (aulaOficina == null) {
			if (other.aulaOficina != null)
				return false;
		} else if (!aulaOficina.equals(other.aulaOficina))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (disponibilidad == null) {
			if (other.disponibilidad != null)
				return false;
		} else if (!disponibilidad.equals(other.disponibilidad))
			return false;
		if (estadoOrdenTrabajo == null) {
			if (other.estadoOrdenTrabajo != null)
				return false;
		} else if (!estadoOrdenTrabajo.equals(other.estadoOrdenTrabajo))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (fechaUltimaModificacion == null) {
			if (other.fechaUltimaModificacion != null)
				return false;
		} else if (!fechaUltimaModificacion.equals(other.fechaUltimaModificacion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idOrdenTrabajo == null) {
			if (other.idOrdenTrabajo != null)
				return false;
		} else if (!idOrdenTrabajo.equals(other.idOrdenTrabajo))
			return false;
		if (moduloSede == null) {
			if (other.moduloSede != null)
				return false;
		} else if (!moduloSede.equals(other.moduloSede))
			return false;
		if (prioridad == null) {
			if (other.prioridad != null)
				return false;
		} else if (!prioridad.equals(other.prioridad))
			return false;
		if (proyecto == null) {
			if (other.proyecto != null)
				return false;
		} else if (!proyecto.equals(other.proyecto))
			return false;
		if (resumen == null) {
			if (other.resumen != null)
				return false;
		} else if (!resumen.equals(other.resumen))
			return false;
		if (telefonoContacto == null) {
			if (other.telefonoContacto != null)
				return false;
		} else if (!telefonoContacto.equals(other.telefonoContacto))
			return false;
		if (tipoActividad == null) {
			if (other.tipoActividad != null)
				return false;
		} else if (!tipoActividad.equals(other.tipoActividad))
			return false;
		return true;
	}
	
	
}