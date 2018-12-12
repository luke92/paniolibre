package util;

public class PruebaEncriptador {

	public static void main(String[] args) {
		String claveSinEncriptar = "root";
		String claveEncriptada = "";
		claveEncriptada = Encriptador.obtenerClaveEncriptada(claveSinEncriptar);
		System.out.println(claveSinEncriptar);
		System.out.println(claveEncriptada);
		System.out.println(Encriptador.clavesIguales(claveSinEncriptar, claveEncriptada));
		System.out.println("PanioLibre2018");
		System.out.println(Encriptador.obtenerClaveEncriptada("PanioLibre2018"));
		System.out.println(Encriptador.clavesIguales("PanioLibre2018", Encriptador.obtenerClaveEncriptada("PanioLibre2018")));
		System.out.println(Encriptador.obtenerClaveEncriptada("admin"));
	}

}
