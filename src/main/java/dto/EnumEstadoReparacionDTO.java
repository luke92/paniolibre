package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public enum EnumEstadoReparacionDTO {
	REPARACION_EXTERNA("Reparacion_Externa", 1), REPARACION_INTRERNA("Reparacion_Interna", 0);

	private final StringProperty estadoString;
	private final IntegerProperty estadoInt;

	EnumEstadoReparacionDTO(String string, int valor) {
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
