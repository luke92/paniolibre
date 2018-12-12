package domain.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DevolucionHerramienta {
	private int idDevolucionHerramienta;
	private Usuario usuario;
	private Tecnico tecnico;
	private Herramienta herramiento;
	private List<Herramienta> herramientas;
	private OrdenDeTrabajo ordenDeTrabajo;
	private Calendar fechaDevolucion;
	private EnumEstadoHerramienta estado;
	private String comentario;

	public DevolucionHerramienta() {

	}

	public DevolucionHerramienta(int idDevolucionHerramienta, Usuario usuario, Tecnico tecnico, Herramienta herramiento,
			OrdenDeTrabajo ordenDeTrabajo, Calendar fechaDevolucion, EnumEstadoHerramienta estado, String comentario) {
		this.idDevolucionHerramienta = idDevolucionHerramienta;
		this.usuario = usuario;
		this.tecnico = tecnico;
		this.herramiento = herramiento;
		this.ordenDeTrabajo = ordenDeTrabajo;
		this.fechaDevolucion = fechaDevolucion;
		this.estado = estado;
		this.herramientas = new ArrayList<Herramienta>();
		this.comentario = comentario;
	}

	public void agregarHerramientas(Herramienta herramienta) {
		this.herramientas.add(herramienta);
	}

	public int getIdDevolucionHerramienta() {
		return idDevolucionHerramienta;
	}

	public List<Herramienta> getHerramientas() {
		return herramientas;
	}

	public void setHerramientas(List<Herramienta> herramientas) {
		this.herramientas = herramientas;
	}

	public void setIdDevolucionHerramienta(int idDevolucionHerramienta) {
		this.idDevolucionHerramienta = idDevolucionHerramienta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public Herramienta getHerramienta() {
		return herramiento;
	}

	public void setHerramienta(Herramienta herramienta) {
		this.herramiento = herramienta;
	}

	public OrdenDeTrabajo getOrdenDeTrabajo() {
		return ordenDeTrabajo;
	}

	public void setOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	public Calendar getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Calendar fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public EnumEstadoHerramienta getEstado() {
		return estado;
	}

	public void setEstado(EnumEstadoHerramienta estado) {
		this.estado = estado;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
