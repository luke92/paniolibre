package dto;

import java.time.LocalDate;
import java.util.Calendar;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.Fechas;

public class ReparacionHerramientaDTO {

	private IntegerProperty idReparacionHerramienta;
	private HerramientaDTO herramienta;
	private EnumEstadoReparacionDTO estadoReparacion;
	private StringProperty comentarioEnvio;
	private StringProperty comentarioRecepcion;
	private LocalDate fechaEnviada;
	private LocalDate fechaRecibida;
	private LocalDate fechaExpiracionGarantia;

	public ReparacionHerramientaDTO() {
		this.idReparacionHerramienta = new SimpleIntegerProperty();
		this.herramienta = null;
		this.estadoReparacion = null;
		this.comentarioEnvio = new SimpleStringProperty();
		this.comentarioRecepcion = new SimpleStringProperty();
		this.fechaEnviada = null;
		this.fechaRecibida = null;
		this.fechaExpiracionGarantia = null;
	}

	public ReparacionHerramientaDTO(int idReparacionHerramienta, HerramientaDTO herramienta, int estadoReparacion,
			String comentarioEnvio, String comentarioRecepcion, Calendar fechaEnviada, Calendar fechaRecibida,
			Calendar fechaExpiracionGarantia) {
		this.idReparacionHerramienta = new SimpleIntegerProperty(idReparacionHerramienta);
		this.herramienta = herramienta;
		this.estadoReparacion = (estadoReparacion == 1) ? EnumEstadoReparacionDTO.REPARACION_EXTERNA
				: EnumEstadoReparacionDTO.REPARACION_INTRERNA;
		this.comentarioEnvio = new SimpleStringProperty(comentarioEnvio);
		this.comentarioRecepcion = new SimpleStringProperty(comentarioRecepcion);
		this.fechaEnviada = Fechas.CalendarTolocalDate(fechaEnviada);
		this.fechaRecibida = Fechas.CalendarTolocalDate(fechaRecibida);
		this.fechaExpiracionGarantia = Fechas.CalendarTolocalDate(fechaExpiracionGarantia);
	}

	public IntegerProperty getIdReparacionHerramienta() {
		return idReparacionHerramienta;
	}

	public void setIdHerramienta(int idReparacionHerramienta) {
		this.idReparacionHerramienta.set(idReparacionHerramienta);
	}

	public StringProperty getComentarioEnvio() {
		return comentarioEnvio;
	}

	public void setComentarioEnvio(String comentarioEnvio) {
		this.comentarioEnvio.set(comentarioEnvio);
	}

	public StringProperty getComentarioRecepcion() {
		return comentarioRecepcion;
	}

	public void setComentarioRecepcion(String comentarioRecepcion) {
		this.comentarioRecepcion.set(comentarioRecepcion);
	}

	public HerramientaDTO getHerramienta() {
		return herramienta;
	}

	public void setHerramienta(HerramientaDTO herramienta) {
		this.herramienta = herramienta;
	}

	public EnumEstadoReparacionDTO getEstadoReparacion() {
		return estadoReparacion;
	}

	public void setEstadoReparacion(EnumEstadoReparacionDTO estadoReparacion) {
		this.estadoReparacion = estadoReparacion;
	}

	public LocalDate getFechaEnviada() {
		return fechaEnviada;
	}

	public void setFechaEnviada(LocalDate fechaEnviada) {
		this.fechaEnviada = fechaEnviada;
	}

	public LocalDate getFechaRecibida() {
		return fechaRecibida;
	}

	public void setFechaRecibida(LocalDate fechaRecibida) {
		this.fechaRecibida = fechaRecibida;
	}

	public LocalDate getFechaExpiracionGarantia() {
		return fechaExpiracionGarantia;
	}

	public void setFechaExpiracionGarantia(LocalDate fechaExpiracionGarantia) {
		this.fechaExpiracionGarantia = fechaExpiracionGarantia;
	}

}
