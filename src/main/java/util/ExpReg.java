package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ExpReg {
	public static boolean contieneSoloNumeros(String text) {
		return Pattern.matches("[0-9]\\d*", text);
	}

	public static boolean mailValido(String string) {
		Pattern pattern = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		String email = string;
		Matcher mather = pattern.matcher(email);

		return mather.find();
	}

	public static boolean telefonoValido(String text) {
		return Pattern.matches("^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$", text);
	}

	public static boolean contieneLetrasyEspacios(String text) {
		return Pattern.matches("^[\\w\\.\\-\\s]+$", text);
	}

	public static boolean contieneLetrasNumerosyEspacios(String text) {
		return Pattern.matches("^[\\w\\d\\-_\\s]+$", text);
	}

	public static boolean ipValido(String text) {
		return Pattern.matches(
				"^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
				text);
	}

	public static boolean servidorValido(String text) {
		return Pattern.matches(
				"^([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])(\\.([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9]))*$",
				text);
	}

	public static boolean nombreServidorValido(String text) {
		return Pattern.matches(
				"^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$",
				text);
	}

	public static boolean nombreUsuarioValido(String text) {
		return Pattern.matches("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$", text);
	}

	public static boolean fechaValida(String text) {
		return Pattern.matches(
				"^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$",
				text);
	}
	
	public static boolean puertoValido(String text)
	{
		return Pattern.matches("\\d{1,6}", text);
	}
}
