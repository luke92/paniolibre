package domain.model;

public enum EnumEstadoDevolucion {
	NO_DEVUELTO("No Devuelto", 0), DEVUELTO("Prestado", 1);

	private final String estadoString;
	private final int estadoInt;

	EnumEstadoDevolucion(String string, int valor) {
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
