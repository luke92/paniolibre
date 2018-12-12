package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public enum EnumEstadoHerramientaDTO {
	DISPONIBLE("Disponible", 1), PRESTADO("Prestado", 2), AVERIADA("Averiada", 3), EN_REPARACION("En Reparacion", 4);

	private final StringProperty estadoString;
	private final IntegerProperty estadoInt;

	EnumEstadoHerramientaDTO(String string, int valor) {
		this.estadoString = new SimpleStringProperty(string);
		this.estadoInt = new SimpleIntegerProperty(valor);
	}

	public StringProperty getNombre() {
		return this.estadoString;
	}

	public IntegerProperty getValor() {
		return this.estadoInt;
	}
}
