package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public enum EnumEstadoPedidoDTO {
	PENDIENTE("Pendiente", 1), PARCIAL("Parcial", 2), COMPLETO("Completo", 3), INCOMPLETO("Incompleto", 4);

	private final StringProperty estadoString;
	private final IntegerProperty estadoInt;

	EnumEstadoPedidoDTO(String string, int valor) {
		this.estadoString = new SimpleStringProperty(string);
		this.estadoInt = new SimpleIntegerProperty(valor);
	}

	public StringProperty getEstado() {
		return this.estadoString;
	}

	public IntegerProperty getValor() {
		return this.estadoInt;
	}

	public int getValorInt() {
		return this.estadoInt.get();
	}

	public String getEstadoString() {
		return this.estadoString.get();
	}
}
