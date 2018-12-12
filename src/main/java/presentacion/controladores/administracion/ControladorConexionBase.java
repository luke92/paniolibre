package presentacion.controladores.administracion;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import dto.ConexionDTO;
import dto.MantisDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import persistencia.conexion.Conexion;
import util.ConfigProperties;
import util.Dialogos;
import util.ExpReg;

public class ControladorConexionBase implements Initializable {

	@FXML
	private BorderPane ventanaConexion;

	@FXML
	private Button btnCerrar;

	@FXML
	private TextField textNombre;

	@FXML
	private TextField textIP;

	@FXML
	private TextField textPuerto;

	@FXML
	private TextField textUsuario;

	@FXML
	private TextField textMantisIP;

	@FXML
	private TextField textMantisPuerto;

	@FXML
	private TextField textMantisNombre;

	@FXML
	private Button btnModificar;

	@FXML
	private PasswordField textPass;

	private double initX;

	private double initY;

	private ValidationSupport validation;

	private ConexionDTO datosConexion;

	private MantisDTO mantisConexion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarTextBoxs();
		registrarValidadores();

		btnCerrar.setOnMouseClicked(event -> cerrarVentana());

		btnModificar.setOnMouseClicked(event -> {
			if (validation.isInvalid()) {
				validation.initInitialDecoration();
			} else {

				modificarConexion();
			}
		});

		this.arrastrarVenta2(true);

	}

	private void arrastrarVenta2(boolean arrastrar) {

		if (arrastrar) {
			ventanaConexion.setOnMousePressed(event -> {
				initX = event.getSceneX();
				initY = event.getSceneY();
				ventanaConexion.setOpacity(0.7);
			});
			ventanaConexion.setOnMouseDragged(event -> {
				Stage stage = (Stage) ventanaConexion.getScene().getWindow();
				stage.setX(event.getScreenX() - initX);
				stage.setY(event.getScreenY() - initY);

			});
		} else {
			ventanaConexion.setOnMousePressed(event -> {
			});
			ventanaConexion.setOnMouseDragged(event -> {
			});
		}
		ventanaConexion.setOnMouseReleased(event -> ventanaConexion.setOpacity(1.0));

	}

	private void registrarValidadores() {
		validation = new ValidationSupport();

		Validator<String> validatorIP = new Validator<String>() {
			@Override
			public ValidationResult apply(Control control, String value) {
				boolean esValido = (ExpReg.ipValido(value) || ExpReg.servidorValido(value));
				boolean condition = value != null ? !esValido : value == null;
				return ValidationResult.fromMessageIf(control, "No es una IP o nombre de servidor valido",
						Severity.ERROR, condition);
			}
		};

		Validator<String> validatorNumeros = new Validator<String>() {
			@Override
			public ValidationResult apply(Control control, String value) {
				boolean condition = value != null ? !ExpReg.puertoValido(value) : value == null;
				return ValidationResult.fromMessageIf(control, "No es un puerto valido", Severity.ERROR, condition);
			}
		};
		
		Validator<String> validatorPuerto = new Validator<String>() {
			@Override
			public ValidationResult apply(Control control, String value) {
				
				boolean condition = value != null ? !ExpReg.puertoValido(value) : value == null;
				if(value.trim().isEmpty()) condition = false;
				return ValidationResult.fromMessageIf(control, "No es un puerto valido", Severity.ERROR, condition);
			}
		};
		
		validation.registerValidator(textNombre,
				Validator.createEmptyValidator("Escriba el nombre de la base de datos"));
		validation.registerValidator(textPuerto, true, validatorNumeros);
		validation.registerValidator(textIP, validatorIP);
		validation.registerValidator(textUsuario,
				Validator.createEmptyValidator("Escriba el nombre de usuario de la base de datos"));
		validation.registerValidator(textPass,
				Validator.createEmptyValidator("Escriba la clave del usuario de la base de datos"));
		validation.registerValidator(textMantisNombre,
				Validator.createEmptyValidator("Escriba el nombre de la aplicacion de Mantis"));
		validation.registerValidator(textMantisIP, validatorIP);
		validation.registerValidator(textMantisPuerto, false, validatorPuerto);
	}

	private void cerrarVentana() {
		Stage stage = (Stage) ventanaConexion.getScene().getWindow();
		stage.close();
	}

	private void modificarConexion() {
		ConexionDTO datosConexionNueva = new ConexionDTO(textIP.getText().trim(), textPuerto.getText().trim(),
				textUsuario.getText().trim(), textPass.getText().trim(), textNombre.getText().trim());
		
		if (datosConexionNueva.getIp().isEmpty() || datosConexionNueva.getPuerto().isEmpty()
				|| datosConexionNueva.getUsername().isEmpty() || datosConexionNueva.getPassword().isEmpty()
				|| datosConexionNueva.getBaseDatos().isEmpty() || textMantisIP.getText().trim().isEmpty()
				|| textMantisNombre.getText().trim().isEmpty()) {
			Dialogos.error("Error", "No se puede modificar la conexion", "Hay datos vacios");
		} else if (datosConexionNueva.equals(datosConexion) && configuracionMantisIgual()) {
			Dialogos.advertencia("Advertencia", "No se puede modificar la conexion",
					"No se han modificado los datos de conexion");
		} else {
			Conexion conexion = Conexion.getConexion();
			if (conexion.cambiarBase(datosConexionNueva)) {
				modificarConfiguracionMantis();
				Dialogos.informacion("Configuracion de Base de Datos",
						"Se establecio y configuro el nuevo acceso a la base de datos correctamente!");
				cerrarVentana();
			} else {
				Dialogos.error("Error al cambiar de Base de Datos", "No se puede cambiar de base de datos",
						"Hay un problema para conectarse la base de datos con los datos ingresados");
			}
		}
	}

	private void cargarTextBoxs() {
		Conexion conexion = Conexion.getConexion();
		datosConexion = conexion.getDatosConexion();
		textIP.setText(datosConexion.getIp());
		textNombre.setText(datosConexion.getBaseDatos());
		textPass.setText(datosConexion.getPassword());
		textPuerto.setText(datosConexion.getPuerto());
		textUsuario.setText(datosConexion.getUsername());
		mantisConexion = ConfigProperties.loadConfigurationMantis();
		textMantisIP.setText(mantisConexion.getMantisIP());
		textMantisNombre.setText(mantisConexion.getMantisNombreApp());
		textMantisPuerto.setText(mantisConexion.getMantisPuerto());
	}

	private void modificarConfiguracionMantis() {
		mantisConexion.setMantisIP(textMantisIP.getText().trim());
		mantisConexion.setMantisNombreApp(textMantisNombre.getText().trim());
		if(textMantisPuerto.getText().trim().isEmpty()) textMantisPuerto.setText("");
		mantisConexion.setMantisPuerto(textMantisPuerto.getText());
		ConfigProperties.editConfigurationMantis(mantisConexion);
	}

	private boolean configuracionMantisIgual() {
		return (mantisConexion.getMantisIP().equals(textMantisIP.getText().trim())
				&& mantisConexion.getMantisNombreApp().equals(textMantisNombre.getText().trim())
				&& mantisConexion.getMantisPuerto().equals(textMantisPuerto.getText()));
	}

}
