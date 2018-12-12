package domain.model;

import dto.EstadoOrdenTrabajoDTO;

public enum EnumEstadoOrdenTrabajo {

	NUEVA("Nueva", 1), ASIGNADA("Asignada", 2), REALIZADA("Realizada", 3), CERRADA("Cerrada",
			4), SUSPENDIDA("Suspendida", 5);

	private final String estadoString;
	private final int estadoInt;

	EnumEstadoOrdenTrabajo(String string, int valor) {
		this.estadoString = string;
		this.estadoInt = valor;
	}

	public String getNombre() {
		return this.estadoString;
	}

	public int getValor() {
		return this.estadoInt;
	}

	public static EnumEstadoOrdenTrabajo getEstado(int valor) {
		if (valor == EnumEstadoOrdenTrabajo.ASIGNADA.estadoInt)
			return EnumEstadoOrdenTrabajo.ASIGNADA;
		if (valor == EnumEstadoOrdenTrabajo.NUEVA.estadoInt)
			return EnumEstadoOrdenTrabajo.NUEVA;
		if (valor == EnumEstadoOrdenTrabajo.REALIZADA.estadoInt)
			return EnumEstadoOrdenTrabajo.REALIZADA;
		if (valor == EnumEstadoOrdenTrabajo.CERRADA.estadoInt)
			return EnumEstadoOrdenTrabajo.CERRADA;
		if (valor == EnumEstadoOrdenTrabajo.SUSPENDIDA.estadoInt)
			return EnumEstadoOrdenTrabajo.SUSPENDIDA;
		return EnumEstadoOrdenTrabajo.NUEVA;
	}

	public EstadoOrdenTrabajoDTO getDTO() {
		EstadoOrdenTrabajoDTO estado = new EstadoOrdenTrabajoDTO();
		estado.getValor().set(this.getValor());
		estado.getEstado().set(this.getNombre());
		return estado;
	}
}