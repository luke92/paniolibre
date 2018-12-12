package dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import domain.model.OrdenDeTrabajo;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import util.Fechas;

public class DevolucionHerramientaDTO {
	private IntegerProperty idDevolucionHerramienta;
	private UsuarioDTO usuario;
	private TecnicoDTO tecnico;
	private HerramientaDTO herramiento;
	private List<HerramientaDTO> herramientas;
	private OrdenDeTrabajo ordenDeTrabajo;
	private LocalDate fechaDevolucion;
	private EnumEstadoHerramientaDTO estado;

	public DevolucionHerramientaDTO() {
		this.idDevolucionHerramienta = new SimpleIntegerProperty();
		this.usuario = null;
		this.tecnico = null;
		this.herramiento = null;
		this.ordenDeTrabajo = null;
		this.fechaDevolucion = null;
		this.estado = null;
		this.herramientas = new ArrayList<HerramientaDTO>();
	}

	public DevolucionHerramientaDTO(int idDevolucionHerramienta, UsuarioDTO usuario, TecnicoDTO tecnico,
			HerramientaDTO herramiento, OrdenDeTrabajo ordenDeTrabajo, Calendar fechaDevolucion,
			EnumEstadoHerramientaDTO estado) {
		this.idDevolucionHerramienta = new SimpleIntegerProperty(idDevolucionHerramienta);
		this.usuario = usuario;
		this.tecnico = tecnico;
		this.herramiento = herramiento;
		this.ordenDeTrabajo = ordenDeTrabajo;
		this.fechaDevolucion = Fechas.CalendarTolocalDate(fechaDevolucion);
		this.estado = estado;
		this.herramientas = new ArrayList<HerramientaDTO>();
	}

	public void agregarHerramienta(HerramientaDTO herramientaDTO) {
		this.herramientas.add(herramientaDTO);
	}

	public List<HerramientaDTO> getHerramientas() {
		return herramientas;
	}

	public void setHerramientas(List<HerramientaDTO> herramientas) {
		this.herramientas = herramientas;
	}

	public IntegerProperty getIdDevolucionHerramienta() {
		return idDevolucionHerramienta;
	}

	public void setIdDevolucionHerramienta(int idDevolucionHerramienta) {
		this.idDevolucionHerramienta.set(idDevolucionHerramienta);
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

	public HerramientaDTO getHerramiento() {
		return herramiento;
	}

	public void setHerramiento(HerramientaDTO herramiento) {
		this.herramiento = herramiento;
	}

	public OrdenDeTrabajo getOrdenDeTrabajo() {
		return ordenDeTrabajo;
	}

	public void setOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public EnumEstadoHerramientaDTO getEstado() {
		return estado;
	}

	public void setEstado(EnumEstadoHerramientaDTO estado) {
		this.estado = estado;
	}

}
