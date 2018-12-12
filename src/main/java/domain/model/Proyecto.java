package domain.model;

import dto.ProyectoDTO;

public class Proyecto {

	private Integer idProyecto;
	private String nombre;

	public Proyecto() {
	}

	public Proyecto(Integer idProyecto, String nombre) {
		super();
		this.idProyecto = idProyecto;
		this.nombre = nombre;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ProyectoDTO getDTO() {
		ProyectoDTO proyecto = new ProyectoDTO();
		proyecto.getIdProyecto().set(this.getIdProyecto());
		proyecto.getNombre().set(this.getNombre());
		return proyecto;
	}

}
