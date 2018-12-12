package domain.model;

public class Especialidad {
	private int idEspecialidad;
	private String nombre;

	public Especialidad() {

	}

	public Especialidad(int idEspecialidad, String nombre) {
		this.idEspecialidad = idEspecialidad;
		this.nombre = nombre;
	}

	public int getIdEspecialidad() {
		return idEspecialidad;
	}

	public void setIdEspecialidad(int idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
