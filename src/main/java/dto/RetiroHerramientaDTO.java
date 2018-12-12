package dto;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import domain.model.OrdenDeTrabajo;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import util.Fechas;

public class RetiroHerramientaDTO {
	private IntegerProperty idRetiroHerramienta;
	private UsuarioDTO usuario;
	private TecnicoDTO tecnico;
	private List<HerramientaDTO> herramientas;
	private OrdenDeTrabajo ordenDeTrabajo;
	private LocalDate fechaPrestamo;

	public RetiroHerramientaDTO() {
		this.idRetiroHerramienta = new SimpleIntegerProperty();
		this.usuario = null;
		this.tecnico = null;
		this.herramientas = null;
		this.ordenDeTrabajo = null;
		this.fechaPrestamo = null;
	}

	public RetiroHerramientaDTO(int idRetiroHerramienta, UsuarioDTO usuario, TecnicoDTO tecnico,
			List<HerramientaDTO> herramientas, OrdenDeTrabajo ordenDeTrabajo, Calendar fechaPrestamo) {
		this.idRetiroHerramienta = new SimpleIntegerProperty(idRetiroHerramienta);
		this.usuario = usuario;
		this.tecnico = tecnico;
		this.herramientas = herramientas;
		this.ordenDeTrabajo = ordenDeTrabajo;
		this.fechaPrestamo = Fechas.CalendarTolocalDate(fechaPrestamo);
	}

	public IntegerProperty getIdRetiroHerramienta() {
		return idRetiroHerramienta;
	}

	public void setIdRetiroHerramienta(int idRetiroHerramienta) {
		this.idRetiroHerramienta.set(idRetiroHerramienta);
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public TecnicoDTO getTecnico() {
		return tecnico;
	}

	public void setTecnico(TecnicoDTO tecnico) {
		this.tecnico = tecnico;
	}

	public List<HerramientaDTO> getHerramientas() {
		return herramientas;
	}

	public void setHerramientas(List<HerramientaDTO> herramientas) {
		this.herramientas = herramientas;
	}

	public void agregarHerramientaDTO(HerramientaDTO herramientaDTO) {
		this.herramientas.add(herramientaDTO);
	}

	public OrdenDeTrabajo getOrdenDeTrabajo() {
		return ordenDeTrabajo;
	}

	public void setOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

}
