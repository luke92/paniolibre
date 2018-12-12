package domain.model;

import dto.TipoActividadDTO;

public class TipoActividad {

	private Integer idTipoActividad;
	private String nombre;

	public TipoActividad() {
	}

	public TipoActividad(Integer idTipoActividad, String nombre) {
		super();
		this.idTipoActividad = idTipoActividad;
		this.nombre = nombre;
	}

	public Integer getIdTipoActividad() {
		return idTipoActividad;
	}

	public void setIdTipoActividad(Integer idTipoActividad) {
		this.idTipoActividad = idTipoActividad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoActividadDTO getDTO() {
		TipoActividadDTO tipoActividad = new TipoActividadDTO();
		tipoActividad.getIdTipoActividad().set(this.getIdTipoActividad());
		tipoActividad.getNombre().set(this.getNombre());
		return tipoActividad;
	}
}
