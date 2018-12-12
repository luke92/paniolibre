package util;

import java.text.Normalizer;

public class CadenasTexto {

	private CadenasTexto() {
	}

	public static String borrarEspacios(String texto) {
		texto = texto.trim();
		return texto.trim();
	}

	public static String obtenerCadenaSinTildes(String cadena) {
		String cadenaSinTildes = cadena;
		cadenaSinTildes = Normalizer.normalize(cadenaSinTildes, Normalizer.Form.NFD);
		cadenaSinTildes = cadenaSinTildes.replaceAll("[^\\p{ASCII}]", "");
		return cadenaSinTildes;
	}
	
	public static boolean sonIguales(String cadena1, String cadena2)
	{
		return obtenerCadenaSinTildes(cadena1).equalsIgnoreCase(obtenerCadenaSinTildes(cadena2));
	}
}
