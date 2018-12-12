package dto;

import java.sql.Date;
import java.util.Calendar;

public class OrdenesReporteDTO {
	private Calendar fechaInicio;
	private Calendar fechaFin;
	private String fechaInicioReporte;
	private String fechaFinReporte;
	private Date fechaInicioOrden;
	private String fechaInicioOrdenTrabajo;
	private Date fechaUltModificacion;
	private String fechaUltModfOrden;
	private String idMantis;
	private String resumen;
	private String nombreProyecto;
	private String estado;

	public OrdenesReporteDTO() {
		fechaInicioReporte = "";
		fechaFinReporte = "";
		fechaInicioOrdenTrabajo = "";
		fechaUltModfOrden = "";
		estado = "";
	}

	public OrdenesReporteDTO(Calendar fechaInicio, Calendar fechaFin, String fechaInicioReporte, String fechaFinReporte,
			Date fechaInicioOrden, String fechaInicioOrdenTrabajo, Date fechaUltModificacion, String fechaUltModfOrden,
			String idMantis, String resumen, String nombreProyecto) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaInicioReporte = fechaInicioReporte;
		this.fechaFinReporte = fechaFinReporte;
		this.fechaInicioOrden = fechaInicioOrden;
		this.fechaInicioOrdenTrabajo = fechaInicioOrdenTrabajo;
		this.fechaUltModificacion = fechaUltModificacion;
		this.fechaUltModfOrden = fechaUltModfOrden;
		this.idMantis = idMantis;
		this.resumen = resumen;
		this.nombreProyecto = nombreProyecto;
		estado = "";
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Calendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Calendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Calendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicioReporte() {
		return fechaInicioReporte;
	}

	public void setFechaInicioReporte(String fechaInicioReporte) {
		this.fechaInicioReporte = fechaInicioReporte;
	}

	public String getFechaFinReporte() {
		return fechaFinReporte;
	}

	public void setFechaFinReporte(String fechaFinReporte) {
		this.fechaFinReporte = fechaFinReporte;
	}

	public Date getFechaInicioOrden() {
		return fechaInicioOrden;
	}

	public void setFechaInicioOrden(Date fechaInicioOrden) {
		this.fechaInicioOrden = fechaInicioOrden;
	}

	public Date getFechaUltModificacion() {
		return fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public String getFechaInicioOrdenTrabajo() {
		return fechaInicioOrdenTrabajo;
	}

	public void setFechaInicioOrdenTrabajo(String fechaInicioOrdenTrabajo) {
		this.fechaInicioOrdenTrabajo = fechaInicioOrdenTrabajo;
	}

	public String getFechaUltModfOrden() {
		return fechaUltModfOrden;
	}

	public void setFechaUltModfOrden(String fechaUltModfOrden) {
		this.fechaUltModfOrden = fechaUltModfOrden;
	}

	public String getIdMantis() {
		return idMantis;
	}

	public void setIdMantis(String idMantis) {
		this.idMantis = idMantis;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

}
