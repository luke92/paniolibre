package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EspecialidadDTO {
	private IntegerProperty idEspecialidad;
	private StringProperty nombre;

	public EspecialidadDTO() {
		this.idEspecialidad = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty();
	}

	public EspecialidadDTO(int idEspecialidad, String nombre) {

		this.idEspecialidad = new SimpleIntegerProperty(idEspecialidad);
		this.nombre = new SimpleStringProperty(nombre);
	}

	public IntegerProperty getIdEspecialidad() {
		return idEspecialidad;
	}

	public void setIdEspecialidad(int idEspecialidad) {
		this.idEspecialidad.set(idEspecialidad);
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	@Override
	public String toString() {
		return nombre.get();
	}

}
