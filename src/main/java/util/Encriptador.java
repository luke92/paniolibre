package util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Encriptador {

	private Encriptador() {
	}

	public static String obtenerClaveEncriptada(String claveSinEncriptar) {
		return encriptar(claveSinEncriptar);
	}

	public static boolean clavesIguales(String claveSinEncriptar, String claveEncriptada) {
		return claveSinEncriptar.equals(desencriptar(claveEncriptada));
	}
	
	public static String obtenerClaveDesencriptada(String claveEncriptada)
	{
		return desencriptar(claveEncriptada);
	}
	
	private static String encriptar(String textoPlano){
        try {
			return Base64.getEncoder().encodeToString(textoPlano.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
    }
    
    private static String desencriptar(String textoEncriptado){
        byte[] decode = Base64.getDecoder().decode(textoEncriptado.getBytes());
        try {
			return new String(decode, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
    }
}
