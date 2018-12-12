package dto;

import java.time.LocalDate;

public class AlertaReparacionDTO {
	private int idAlerta;
	private LocalDate fecha;
	private boolean enviadoPorMail;
	private String codigoHerramienta;
	private String nombreHerramienta;
	private String comentarioEnvioReparacion;
	private LocalDate fechaEnviada;
	private LocalDate fechaProbableRecepcion;

	public AlertaReparacionDTO() {
	}

	public AlertaReparacionDTO(int idAlerta, LocalDate fecha, boolean enviadoPorMail, String codigoHerramienta,
			String nombreHerramienta, String comentarioEnvioReparacion, LocalDate fechaEnviada,
			LocalDate fechaProbableRecepcion) {
		this.idAlerta = idAlerta;
		this.fecha = fecha;
		this.enviadoPorMail = enviadoPorMail;
		this.codigoHerramienta = codigoHerramienta;
		this.nombreHerramienta = nombreHerramienta;
		this.comentarioEnvioReparacion = comentarioEnvioReparacion;
		this.fechaEnviada = fechaEnviada;
		this.fechaProbableRecepcion = fechaProbableRecepcion;
	}

	public int getIdAlerta() {
		return this.idAlerta;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isEnviadoPorMail() {
		return enviadoPorMail;
	}

	public void setEnviadoPorMail(boolean enviadoPorMail) {
		this.enviadoPorMail = enviadoPorMail;
	}

	public String getCodigoHerramienta() {
		return codigoHerramienta;
	}

	public void setCodigoHerramienta(String codigoHerramienta) {
		this.codigoHerramienta = codigoHerramienta;
	}

	public String getNombreHerramienta() {
		return nombreHerramienta;
	}

	public void setNombreHerramienta(String nombreHerramienta) {
		this.nombreHerramienta = nombreHerramienta;
	}

	public String getComentarioEnvioReparacion() {
		return comentarioEnvioReparacion;
	}

	public void setComentarioEnvioReparacion(String comentarioEnvioReparacion) {
		this.comentarioEnvioReparacion = comentarioEnvioReparacion;
	}

	public LocalDate getFechaEnviada() {
		return fechaEnviada;
	}

	public void setFechaEnviada(LocalDate fechaEnviada) {
		this.fechaEnviada = fechaEnviada;
	}

	public LocalDate getFechaProbableRecepcion() {
		return fechaProbableRecepcion;
	}

	public void setFechaProbableRecepcion(LocalDate fechaProbableRecepcion) {
		this.fechaProbableRecepcion = fechaProbableRecepcion;
	}

	@Override
	public String toString() {

		return "Herramienta " + codigoHerramienta + " " + nombreHerramienta + " se envio a reparar el dia "
				+ fechaEnviada + " con el siguiente detalle: " + comentarioEnvioReparacion
				+ ". Debio estar lista desde la fecha " + fechaProbableRecepcion + ". Alerta existente desde la fecha "
				+ fecha;
	}

}
