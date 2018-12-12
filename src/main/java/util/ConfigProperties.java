package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import dto.ConexionDTO;
import dto.MantisDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;
import services.MantisService;

public class ConfigProperties {

	private ConfigProperties() {
	}

	private static final String RUTA_DB_CONF = "configuration/db.properties";
	private static final String RUTA_MANTIS_CONF = "configuration/mantis.properties";

	private static final String IP = "jdbc.ip";
	private static final String PUERTO = "jdbc.puerto";
	private static final String USERNAME = "jdbc.username";
	private static final String CLAVE = "jdbc.password";
	private static final String BASEDATOS = "jdbc.baseDatos";

	// #---- url de la api soap ------
	private static final String APIURL = "apiUrl"; // =/api/soap/mantisconnect.php
	// #---- id de los filtros de mantis -----

	private static final String FILTRONUEVASID = "filtroNuevasId"; // = 2
	private static final String FILTROTODASID = "filtroTodasId"; // = 3
	private static final String FILTRORESUELTASID = "filtroResueltasId"; // = 4
	private static final String FILTROCERRADASID = "filtroCerradasId"; // = 5
	private static final String FILTROASIGNADASID = "filtroAsignadasId"; // = 6

	// #---- id del custom field de tecnicos
	private static final String IDCUSTOMFIELDTECNICOS = "idCustomFieldTecnicos"; // = 2

	// #---- datos de la base de datos -----

	private static final String BASEIP = "baseIP"; // = localhost
	private static final String BASEPUERTO = "basePuerto"; // = 3306
	private static final String BASECHEMA = "baseSchema"; // = paniolibre
	private static final String BASEUSUARIO = "baseUsuario"; // = root
	private static final String BASECONTRASENIA = "baseContrasenia"; // = root

	// #---- datos mantis -------
	// #-- si su url no tiene ----
	// #-- puerto dejarlo en blanco ---

	private static final String MANTISPUERTO = "mantisPuerto"; // = 8080
	private static final String MANTISIP = "mantisIP"; // = localhost
	private static final String MANTISNOMBREAPP = "mantisNombreApp"; // = mantis

	public static String configurationDB() throws IOException {
		if (!FileChecker.exists(RUTA_DB_CONF)) {
			createConfigurationDB();
		}
		return RUTA_DB_CONF;
	}

	public static String configurationMantis()  {
		if (!FileChecker.exists(RUTA_MANTIS_CONF)) {
			try {
				createConfigurationMantis();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return RUTA_MANTIS_CONF;
	}

	private static void createConfigurationDB() throws IOException {
		Properties props = new Properties();
		OutputStream output = null;

		output = new FileOutputStream(RUTA_DB_CONF);
		ConexionDTO conexion = new ConexionDTO("localhost", "3306", "root", Encriptador.obtenerClaveEncriptada("root"),
				"paniolibre");
		props.setProperty(IP, conexion.getIp());
		props.setProperty(PUERTO, conexion.getPuerto());
		props.setProperty(USERNAME, conexion.getUsername());
		props.setProperty(CLAVE, conexion.getPassword());
		props.setProperty(BASEDATOS, conexion.getBaseDatos());
		props.store(output, null);
	}

	public static ConexionDTO loadConfigurationDB() throws IOException {
		Properties props = new Properties();
		FileInputStream in;
		in = new FileInputStream(configurationDB());
		props.load(in);
		in.close();
		String ip = props.getProperty(IP);
		String puerto = props.getProperty(PUERTO);
		String username = props.getProperty(USERNAME);
		String password = Encriptador.obtenerClaveDesencriptada(props.getProperty(CLAVE));
		String baseDatos = props.getProperty(BASEDATOS);
		return new ConexionDTO(ip, puerto, username, password, baseDatos);
	}

	public static void editConfigurationDB(ConexionDTO conexionDTO) throws IOException {
		FileInputStream in = new FileInputStream(configurationDB());
		Properties props = new Properties();
		props.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream(configurationDB());

		props.setProperty(IP, conexionDTO.getIp());
		props.setProperty(PUERTO, conexionDTO.getPuerto());
		props.setProperty(USERNAME, conexionDTO.getUsername());
		props.setProperty(CLAVE, Encriptador.obtenerClaveEncriptada(conexionDTO.getPassword()));
		props.setProperty(BASEDATOS, conexionDTO.getBaseDatos());
		props.store(out, null);
		out.close();
	}

	public static void createConfigurationMantis() throws IOException {
		Properties props = new Properties();
		OutputStream output = null;

		output = new FileOutputStream(RUTA_MANTIS_CONF);
		MantisService mantisService = new MantisService(new DAOSQLFactory());
		MantisDTO mantis = mantisService.obtenerConfiguracionMantis();
		Conexion conexion = Conexion.getConexion();

		props.setProperty(BASEIP, conexion.getIp());
		props.setProperty(BASEPUERTO, conexion.getPuerto());
		props.setProperty(BASEUSUARIO, conexion.getUsername());
		props.setProperty(BASECONTRASENIA, conexion.getPassword());
		props.setProperty(BASECHEMA, conexion.getBaseDatos());

		props.setProperty(APIURL, mantis.getApiUrl());
		props.setProperty(FILTRONUEVASID, mantis.getFiltroNuevasId());
		props.setProperty(FILTROASIGNADASID, mantis.getFiltroAsignadasId());
		props.setProperty(FILTRORESUELTASID, mantis.getFiltroResueltasId());
		props.setProperty(FILTROCERRADASID, mantis.getFiltroCerradasId());
		props.setProperty(FILTROTODASID, mantis.getFiltroTodasId());
		props.setProperty(IDCUSTOMFIELDTECNICOS, mantis.getIdCustomFieldTecnicos());

		props.setProperty(MANTISIP, mantis.getMantisIP());
		props.setProperty(MANTISPUERTO, mantis.getMantisPuerto());
		props.setProperty(MANTISNOMBREAPP, mantis.getMantisNombreApp());
		props.store(output, null);
	}

	public static MantisDTO loadConfigurationMantis() {
		configurationMantis();
		MantisService mantisService = new MantisService(new DAOSQLFactory());
		return mantisService.obtenerConfiguracionMantis();
		
	}

	public static void editConfigurationMantis(MantisDTO mantis) {
		FileInputStream in;
		try {
			in = new FileInputStream(configurationMantis());
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(configurationMantis());

			Conexion conexion = Conexion.getConexion();

			props.setProperty(BASEIP, conexion.getIp());
			props.setProperty(BASEPUERTO, conexion.getPuerto());
			props.setProperty(BASEUSUARIO, conexion.getUsername());
			props.setProperty(BASECONTRASENIA, conexion.getPassword());
			props.setProperty(BASECHEMA, conexion.getBaseDatos());

			MantisService mantisService = new MantisService(new DAOSQLFactory());
			mantisService.actualizarConfiguracionMantis(mantis);

			props.setProperty(APIURL, mantis.getApiUrl());
			props.setProperty(FILTRONUEVASID, mantis.getFiltroNuevasId());
			props.setProperty(FILTROASIGNADASID, mantis.getFiltroAsignadasId());
			props.setProperty(FILTRORESUELTASID, mantis.getFiltroResueltasId());
			props.setProperty(FILTROCERRADASID, mantis.getFiltroCerradasId());
			props.setProperty(FILTROTODASID, mantis.getFiltroTodasId());
			props.setProperty(IDCUSTOMFIELDTECNICOS, mantis.getIdCustomFieldTecnicos());

			props.setProperty(MANTISIP, mantis.getMantisIP());
			if(mantis.getMantisPuerto().trim().isEmpty())
				props.setProperty(MANTISPUERTO, new String());
			else 
				props.setProperty(MANTISPUERTO, mantis.getMantisPuerto());
			props.setProperty(MANTISNOMBREAPP, mantis.getMantisNombreApp());
			props.store(out, null);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
