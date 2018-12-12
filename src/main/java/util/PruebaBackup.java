package util;

import persistencia.conexion.Conexion;

public class PruebaBackup {

	public static void main(String[] args) {
		Conexion conexion = Conexion.getConexion();
		conexion.backup(Paths.app() + "\\" + Paths.nombreArchivoBackupActual());
		
		System.out.println(Paths.programFiles());
	}

}
