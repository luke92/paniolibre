package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProyectoDTO {
	private IntegerProperty idProyecto;
	private StringProperty nombre;

	public ProyectoDTO() {
		this.idProyecto = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty();
	}

	public ProyectoDTO(IntegerProperty idProyecto, StringProperty nombre) {
		super();
		this.idProyecto = idProyecto;
		this.nombre = nombre;
	}

	public IntegerProperty getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(IntegerProperty idProyecto) {
		this.idProyecto = idProyecto;
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return getNombre().get();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProyecto == null) ? 0 : idProyecto.get());
		result = prime * result + ((nombre == null) ? 0 : nombre.get().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProyectoDTO other = (ProyectoDTO) obj;
		if (idProyecto == null) {
			if (other.idProyecto != null)
				return false;
		} else if (this.idProyecto.get() != other.idProyecto.get())
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.get().equals(other.nombre.get()))
			return false;
		return true;
	}

}
