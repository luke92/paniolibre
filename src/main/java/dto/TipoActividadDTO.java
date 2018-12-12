package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TipoActividadDTO {
	private IntegerProperty idTipoActividad;
	private StringProperty nombre;

	public TipoActividadDTO() {
		this.idTipoActividad = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty();
	}

	public TipoActividadDTO(IntegerProperty idTipoActividad, StringProperty nombre) {
		super();
		this.idTipoActividad = idTipoActividad;
		this.nombre = nombre;
	}

	public IntegerProperty getIdTipoActividad() {
		return idTipoActividad;
	}

	public void setIdTipoActividad(IntegerProperty idTipoActividad) {
		this.idTipoActividad = idTipoActividad;
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
		result = prime * result + ((idTipoActividad == null) ? 0 : idTipoActividad.get());
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
		TipoActividadDTO other = (TipoActividadDTO) obj;
		if (idTipoActividad == null) {
			if (other.idTipoActividad != null)
				return false;
		} else if (idTipoActividad.get() != other.idTipoActividad.get())
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.get().equals(other.nombre.get()))
			return false;
		return true;
	}

}
