package domain.model;

public enum EnumRecibido {
	PENDIENTE("Pendiente", 1), PARCIAL("Parcial", 2), COMPLETO("Completo", 3), INCOMPLETO("Incompleto", 4);

	private final String estadoString;
	private final int estadoInt;

	EnumRecibido(String string, int valor) {
		this.estadoString = string;
		this.estadoInt = valor;
	}

	public String getNombre() {
		return this.estadoString;
	}

	public int getValor() {
		return this.estadoInt;
	}

}
