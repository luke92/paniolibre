package domain.model;

import java.util.Calendar;

public class ReparacionHerramienta {
	private int idReparacionHerramienta;
	private Herramienta herramienta;
	private int reparacionInterna;
	private String comentarioEnvio;
	private String comentarioRecepcion;
	private Calendar fechaEnviada;
	private Calendar fechaRecibida;
	private Calendar fechaExpiracionGarantia;

	public ReparacionHerramienta() {

	}

	public ReparacionHerramienta(int idReparacionHerramienta, Herramienta herramienta, int reparacionInterna,
			String comentarioEnvio, String comentarioRecepcion, Calendar fechaEnviada, Calendar fechaRecibida,
			Calendar fechaExpiracionGarantia) {
		this.idReparacionHerramienta = idReparacionHerramienta;
		this.herramienta = herramienta;
		this.reparacionInterna = reparacionInterna;
		this.comentarioEnvio = comentarioEnvio;
		this.comentarioRecepcion = comentarioRecepcion;
		this.fechaEnviada = fechaEnviada;
		this.fechaRecibida = fechaRecibida;
		this.fechaExpiracionGarantia = fechaExpiracionGarantia;
	}

	public int getIdReparacionHerramienta() {
		return idReparacionHerramienta;
	}

	public void setIdReparacionHerramienta(int idReparacionHerramienta) {
		this.idReparacionHerramienta = idReparacionHerramienta;
	}

	public Herramienta getHerramienta() {
		return herramienta;
	}

	public void setHerramienta(Herramienta herramienta) {
		this.herramienta = herramienta;
	}

	public int getReparacionInterna() {
		return reparacionInterna;
	}

	public void setReparacionInterna(int reparacionInterna) {
		this.reparacionInterna = reparacionInterna;
	}

	public String getComentarioEnvio() {
		return comentarioEnvio;
	}

	public void setComentarioEnvio(String comentarioEnvio) {
		this.comentarioEnvio = comentarioEnvio;
	}

	public String getComentarioRecepcion() {
		return comentarioRecepcion;
	}

	public void setComentarioRecepcion(String comentarioRecepcion) {
		this.comentarioRecepcion = comentarioRecepcion;
	}

	public Calendar getFechaEnviada() {
		return fechaEnviada;
	}

	public void setFechaEnviada(Calendar fechaEnviada) {
		this.fechaEnviada = fechaEnviada;
	}

	public Calendar getFechaRecibida() {
		return fechaRecibida;
	}

	public void setFechaRecibida(Calendar fechaRecibida) {
		this.fechaRecibida = fechaRecibida;
	}

	public Calendar getFechaExpiracionGarantia() {
		return fechaExpiracionGarantia;
	}

	public void setFechaExpiracionGarantia(Calendar fechaExpiracionGarantia) {
		this.fechaExpiracionGarantia = fechaExpiracionGarantia;
	}

}
