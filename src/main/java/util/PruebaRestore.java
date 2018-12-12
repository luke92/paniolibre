package util;

import persistencia.conexion.Conexion;

public class PruebaRestore {

	public static void main(String[] args) {
		Conexion conexion = Conexion.getConexion();
		conexion.restore(Paths.app() + "\\" + Paths.nombreArchivoBackupActual());
	}
}
