package domain.model;

public enum EnumTipoDeIngreso {
	CAJA_CHICA("Caja Chica", 1), PEDIDO("Pedido", 2), ORDEN_DE_TRABAJO("Orden de Trabajo", 3);

	private final String estadoString;
	private final int estadoInt;

	EnumTipoDeIngreso(String string, int valor) {
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
