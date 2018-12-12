package domain.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RetiroHerramienta {
	private int idRetiroHerramienta;
	private Usuario usuario;
	private Tecnico tecnico;
	private Herramienta herramienta;
	private List<Herramienta> herramientas;
	private OrdenDeTrabajo ordenDeTrabajo;
	private Calendar fechaPrestamo;

	public RetiroHerramienta() {

	}

	public RetiroHerramienta(int idRetiroHerramienta, Usuario usuario, Tecnico tecnico, Herramienta herramienta,
			OrdenDeTrabajo orden, Calendar fechaPrestamo) {
		this.idRetiroHerramienta = idRetiroHerramienta;
		this.usuario = usuario;
		this.tecnico = tecnico;
		this.herramienta = herramienta;
		this.fechaPrestamo = fechaPrestamo;
		this.ordenDeTrabajo = orden;
		this.herramientas = new ArrayList<Herramienta>();

	}

	public Herramienta getHerramienta() {
		return herramienta;
	}

	public void setHerramienta(Herramienta herramienta) {
		this.herramienta = herramienta;
	}

	public int getIdRetiroHerramienta() {
		return idRetiroHerramienta;
	}

	public void setIdRetiroHerramienta(int idRetiroHerramienta) {
		this.idRetiroHerramienta = idRetiroHerramienta;
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

	public void agregarHerramientas(Herramienta herramienta) {
		this.herramientas.add(herramienta);
	}

	public List<Herramienta> getHerramientas() {
		return herramientas;
	}

	public void setHerramientas(List<Herramienta> herramientas) {
		this.herramientas = herramientas;
	}

	public OrdenDeTrabajo getOrdenDeTrabajo() {
		return ordenDeTrabajo;
	}

	public void setOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	public Calendar getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Calendar fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

}
