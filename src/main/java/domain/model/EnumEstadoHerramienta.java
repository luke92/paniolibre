package domain.model;

public enum EnumEstadoHerramienta {
	DISPONIBLE("Disponible", 1), PRESTADO("Prestado", 2), AVERIADO("Averiado", 3), EN_REPARACION("En Reparacion", 4);

	private final String estadoString;
	private final int estadoInt;

	EnumEstadoHerramienta(String string, int valor) {
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
