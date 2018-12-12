package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logs {
	private Logger logger;
	private String archivoLog;
	private String nombreClase;
	private FileHandler fileHandler;

	public Logs(Object clase) {
		nombreClase = clase.getClass().getSimpleName();
		logger = Logger.getLogger(clase.getClass().getName());
		archivoLog = Paths.logs() + "\\" + nombreClase + ".log";
		try {
			fileHandler = new FileHandler(archivoLog, true);
			logger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void log(Level level, String msg, Throwable thrown) {
		logger.log(level, msg, thrown);
	}

}
