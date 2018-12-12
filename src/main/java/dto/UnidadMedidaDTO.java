package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UnidadMedidaDTO {
	private IntegerProperty idUnidadMedida;
	private StringProperty nombre;

	public UnidadMedidaDTO() {
		this.idUnidadMedida = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty();
	}

	public UnidadMedidaDTO(IntegerProperty idUnidadMedida, StringProperty nombre) {
		super();
		this.idUnidadMedida = idUnidadMedida;
		this.nombre = nombre;
	}

	public IntegerProperty getIdUnidadMedida() {
		return idUnidadMedida;
	}

	public void setIdUnidadMedida(IntegerProperty idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return this.getNombre().get();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre.get() == null) ? 0 : nombre.get().hashCode());
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
		UnidadMedidaDTO other = (UnidadMedidaDTO) obj;
		if (nombre.get() == null) {
			if (other.nombre.get() != null)
				return false;
		} else if (!nombre.get().equals(other.nombre.get()))
			return false;
		return true;
	}

}