package domain.model;

import dto.EstadoDTO;

public enum Estado {
	ALTA("Alta", 1), BAJA("Baja", 0);

	private final String estadoString;
	private final int estadoInt;

	Estado(String string, int valor) {
		this.estadoString = string;
		this.estadoInt = valor;
	}

	public String getString() {
		return this.estadoString;
	}

	public int getInt() {
		return this.estadoInt;
	}

	public static Estado getEstado(int valor) {
		if (valor == Estado.BAJA.estadoInt)
			return Estado.ALTA;
		else
			return Estado.BAJA;
	}

	public EstadoDTO getDTO() {
		EstadoDTO estadoDTO = EstadoDTO.ALTA;
		estadoDTO.getValor().set(this.getInt());
		estadoDTO.getEstado().set(this.getString());
		return estadoDTO;
	}
}