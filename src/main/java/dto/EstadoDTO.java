package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public enum EstadoDTO {
	ALTA("alta", 1), BAJA("baja", 0);

	private final StringProperty estadoString;
	private final IntegerProperty estadoInt;

	EstadoDTO(String string, int valor) {
		this.estadoString = new SimpleStringProperty(string);
		this.estadoInt = new SimpleIntegerProperty(valor);
	}

	public StringProperty getEstado() {
		return this.estadoString;
	}

	public IntegerProperty getValor() {
		return this.estadoInt;
	}

}