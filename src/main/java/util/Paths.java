package util;

import java.io.File;
import java.time.LocalDateTime;

public class Paths {

	private static final String PROGRAMFILESX86 = "Program Files (x86)";
	private static final String PROGRAMFILES = "Program Files";
	private static final String INSTALLDIRDB1 = "\\MariaDB 10.2\\bin\\";
	private static final String INSTALLDIRDB2 = "\\MySQL\\MySQL Server 5.7\\bin\\";

	private Paths() {
	}

	public static String rutaMySQL() {
		String rutaDB = "";
		if (OSValidator.isUnix())
			return rutaDB;
		if (FileChecker.existsFolder(programFiles() + INSTALLDIRDB1))
			rutaDB = programFiles() + INSTALLDIRDB1;
		else if (FileChecker.existsFolder(programFiles() + INSTALLDIRDB2))
			rutaDB = programFiles() + INSTALLDIRDB2;
		else if (FileChecker.existsFolder(programFilesx86() + INSTALLDIRDB1))
			rutaDB = programFilesx86() + INSTALLDIRDB1;
		else if (FileChecker.existsFolder(programFilesx86() + INSTALLDIRDB2))
			rutaDB = programFilesx86() + INSTALLDIRDB2;

		return rutaDB;
	}

	public static String programFiles() {
		return System.getenv("ProgramFiles");
	}

	public static String programFilesx86() {
		return System.getenv("ProgramFiles(x86)");
	}

	public static String app() {
		return System.getProperty("user.dir");
	}

	public static String nombreArchivoBackupActual() {
		LocalDateTime fecha = LocalDateTime.now();
		return "panioLibre_" + fecha.getYear() + fecha.getMonthValue() + fecha.getDayOfMonth() + fecha.getHour()
				+ fecha.getMinute() + fecha.getSecond() + ".sql";
	}

	public static String respaldos() {
		String rutaRespaldos = Paths.app() + "\\" + "respaldos";
		File directory = new File(rutaRespaldos);
		if (!directory.exists()) {

			directory.mkdir();
		}
		return rutaRespaldos;
	}

	public static String batRestore() {
		String programFilesx86 = '"' + PROGRAMFILESX86 + '"';
		String programFiles = '"' + PROGRAMFILES + '"';
		String rutaRespaldos = Paths.respaldos() + "\\restore.bat";
		String rutaBat = rutaRespaldos;
		if (rutaRespaldos.contains(PROGRAMFILESX86)) {
			rutaBat = rutaRespaldos.replace(PROGRAMFILESX86, programFilesx86);
		} else if (rutaRespaldos.contains(PROGRAMFILES)) {
			rutaBat = rutaRespaldos.replace(PROGRAMFILES, programFiles);
		}
		return rutaBat;
	}

	public static String logs() {
		String rutaLogs = Paths.app() + "\\" + "logs";
		File directory = new File(rutaLogs);
		if (!directory.exists()) {

			directory.mkdir();
		}
		return rutaLogs;
	}

	public static boolean borrarArchivoRestoreBat() {
		File file = new File(Paths.respaldos() + "\\restore.bat");
		return file.delete();
	}
}
