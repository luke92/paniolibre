package persistencia.conexion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import com.mysql.jdbc.Statement;
import dto.ConexionDTO;
import util.ConfigProperties;
import util.Logs;
import util.Paths;

public class Conexion {

	private Logs logger;
	public static Conexion instancia;
	private Connection connection;
	private Statement sentencia = null;
	private ConexionDTO conexionDTO;

	private Conexion() {
		try {
			logger = new Logs(this);
			conexionDTO = ConfigProperties.loadConfigurationDB();

			getConnection();
			getSentencia();

			logger.info("Conexion exitosa");
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			logger.info("Conexion fallida");
		}
	}

	private void getConnection() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:mysql://" + conexionDTO.getIp() + ":"
				+ conexionDTO.getPuerto() + "/" + conexionDTO.getBaseDatos(), conexionDTO.getUsername(),
				conexionDTO.getPassword());
	}

	private void getSentencia() throws SQLException {
		sentencia = (Statement) connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
	}

	public boolean cambiarBase(ConexionDTO conexionNueva) {
		try {
			this.cerrarConexion();
			conexionDTO = conexionNueva;
			getConnection();
			getSentencia();
			logger.info("Conexion exitosa");
			ConfigProperties.editConfigurationDB(conexionNueva);
			return true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			logger.info("Conexion fallida");
			getConexion();
			return false;
		}
	}

	public static Conexion getConexion() {
		if (instancia == null) {
			instancia = new Conexion();
		}
		return instancia;
	}

	public Connection getSQLConexion() {
		return this.connection;
	}

	public void cerrarConexion() {
		try {
			this.connection.close();
			this.sentencia.close();
			logger.info("Conexion cerrada");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		instancia = null;
	}

	public String getIp() {
		return conexionDTO.getIp();
	}

	public String getPuerto() {
		return conexionDTO.getPuerto();
	}

	public String getUsername() {
		return conexionDTO.getUsername();
	}

	public String getPassword() {
		return conexionDTO.getPassword();
	}

	public String getBaseDatos() {
		return conexionDTO.getBaseDatos();
	}

	public boolean backup(String filePath) {
		String executeCmd = "";
		executeCmd = Paths.rutaMySQL() + "mysqldump -u" + getUsername() + " -p" + getPassword() + " " + " --host="
				+ getIp() + " --port=" + getPuerto() + " " + getBaseDatos() + " -r " + '"' + filePath + '"';
		Process runtimeProcess = null;
		try {
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();
			if (processComplete == 0) {
				String mensaje = "Backup Successfully " + LocalDate.now();
				logger.info(mensaje);
				return true;
			} else {
				logger.info("No se pudo hacer el backup");
				return false;
			}
		} catch (IOException | InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}

	public boolean restore(String filePath) {
		String comando = '"' + Paths.rutaMySQL() + "mysql" + '"' + " " + getBaseDatos() + " --host=" + getIp()
				+ " --port=" + getPuerto() + " --user=" + getUsername() + " --password=" + getPassword() + " < " + '"'
				+ filePath + '"' + " \n exit";
		File f = new File(Paths.respaldos() + "\\restore.bat");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(f);
			fos.write(comando.getBytes());
			fos.close();
			Process run = Runtime.getRuntime().exec("cmd /C start /min " + Paths.batRestore());
			int processComplete = run.waitFor();
			if (processComplete == 0) {
				String mensaje = "Restore Successfully " + LocalDate.now();
				logger.info(mensaje);
				return true;
			} else {
				logger.info("No se pudo hacer el restore");
				return false;
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	public ConexionDTO getDatosConexion() {
		return conexionDTO;
	}
}