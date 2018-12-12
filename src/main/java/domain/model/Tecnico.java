package domain.model;

import java.util.List;

public class Tecnico {
	private int idTecnico;
	private String nombre;
	private String dni;
	private String legajo;
	private List<Especialidad> especialidades;
	private int activo;
	private String apellido;
	private String etiquetaMantis;
	
	public Tecnico() {
	}

	public Tecnico(int idTecnico, String dni, String legajo, String nombre, String apellido,
			List<Especialidad> especialidades, int activo) {
		this.idTecnico = idTecnico;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.legajo = legajo;
		this.especialidades = especialidades;
		this.activo = activo;
		this.etiquetaMantis = "";
	}
	
	public Tecnico(int idTecnico, String dni, String legajo, String nombre, String apellido,
			List<Especialidad> especialidades, int activo, String etiquetaMantis) {
		this.idTecnico = idTecnico;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.legajo = legajo;
		this.especialidades = especialidades;
		this.activo = activo;
		this.etiquetaMantis = etiquetaMantis;
	}

	public int getIdTecnico() {
		return idTecnico;
	}

	public void setIdTecnico(int idTecnico) {
		this.idTecnico = idTecnico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public List<Especialidad> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public String getEtiquetaMantis() {
		return etiquetaMantis;
	}

	public void setEtiquetaMantis(String etiquetaMantis) {
		this.etiquetaMantis = etiquetaMantis;
	}
}