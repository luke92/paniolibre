package domain.model;

import dto.UnidadMedidaDTO;

public enum EnumUnidadMedida {
	MILILITROS("Mililitros", 1), LITROS("Litros", 2), GRAMOS("Gramos", 3), KILOGRAMOS("Kilogramos", 4), CENTIMETROS(
			"Centimetros", 5), METROS("Metros", 6), PULGADAS("Pulgadas",
					7), PAQUETES("Paquetes", 8), PARES("Pares", 9), UNIDADES("Unidades", 10), VALIJA("Valija", 11);

	private final String estadoString;
	private final int estadoInt;

	EnumUnidadMedida(String string, int valor) {
		this.estadoString = string;
		this.estadoInt = valor;
	}

	public static EnumUnidadMedida getUnidadMedida(int valor) {
		EnumUnidadMedida e = null;
		switch (valor) {
		case 1:
			e = EnumUnidadMedida.MILILITROS;
			break;

		case 2:
			e = EnumUnidadMedida.LITROS;
			break;

		case 3:
			e = EnumUnidadMedida.GRAMOS;
			break;

		case 4:
			e = EnumUnidadMedida.KILOGRAMOS;
			break;

		case 5:
			e = EnumUnidadMedida.CENTIMETROS;
			break;

		case 6:
			e = EnumUnidadMedida.METROS;
			break;

		case 7:
			e = EnumUnidadMedida.PULGADAS;
			break;

		case 8:
			e = EnumUnidadMedida.PAQUETES;
			break;

		case 9:
			e = EnumUnidadMedida.PARES;
			break;

		case 10:
			e = EnumUnidadMedida.UNIDADES;
			break;

		case 11:
			e = EnumUnidadMedida.VALIJA;
			break;

		default:
			break;
		}

		return e;
	}

	public String getNombre() {
		return this.estadoString;
	}

	public int getValor() {
		return this.estadoInt;
	}

	public UnidadMedidaDTO getUnidadMedidaDTO() {
		UnidadMedidaDTO unidad = new UnidadMedidaDTO();

		unidad.getIdUnidadMedida().set(this.getValor());
		unidad.getNombre().set(this.getNombre());

		return unidad;
	}
}
