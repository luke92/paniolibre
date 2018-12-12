package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EstadoOrdenTrabajoDTO {

	private StringProperty estadoString;
	private IntegerProperty estadoInt;

	public EstadoOrdenTrabajoDTO(String string, int valor) {
		this.estadoString = new SimpleStringProperty(string);
		this.estadoInt = new SimpleIntegerProperty(valor);
	}

	public EstadoOrdenTrabajoDTO() {
		this.estadoInt = new SimpleIntegerProperty();
		this.estadoString = new SimpleStringProperty();
	}

	public EstadoOrdenTrabajoDTO(IntegerProperty integer, StringProperty string) {
		this.estadoInt = integer;
		this.estadoString = string;
	}

	public StringProperty getEstado() {
		return this.estadoString;
	}

	public IntegerProperty getValor() {
		return this.estadoInt;
	}

	@Override
	public String toString() {
		return getEstado().get();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estadoInt == null) ? 0 : estadoInt.get());
		result = prime * result + ((estadoString == null) ? 0 : estadoString.get().hashCode());
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
		EstadoOrdenTrabajoDTO other = (EstadoOrdenTrabajoDTO) obj;
		if (estadoInt == null) {
			if (other.estadoInt != null)
				return false;
		} else if (estadoInt.get() != other.estadoInt.get())
			return false;
		if (estadoString == null) {
			if (other.estadoString != null)
				return false;
		} else if (!estadoString.get().equals(other.estadoString.get()))
			return false;
		return true;
	}

}
