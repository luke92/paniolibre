package presentacion.controladores.administracion;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.UsuarioService;
import util.ExpReg;

public class ControladorUsuarioAgregar {

	@FXML
	private DialogPane ventanaUsuario;

	@FXML
	private Button btnAgregar;

	@FXML
	private TextField textNombre;

	@FXML
	private TextField txtApellido;

	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtMail;

	@FXML
	private PasswordField txtClave;
	
	@FXML
	private TextField txtUserNameMantis;
	
	@FXML
	private PasswordField txtClaveMantis;
	
	@FXML
	private CheckBox chkRecibeAlertasPorMail;

	private ObservableList<Usuario> masterData = FXCollections.observableArrayList();

	private ValidationSupport validationSupport = new ValidationSupport();

	private UsuarioService servicio = new UsuarioService(new DAOSQLFactory());

	private String campoRequerido = "Campo requerido";

	@FXML
	private Button btnDescartar;

	@FXML
	void descartar(MouseEvent event) {
		cerrarVentana();
	}

	@FXML
	void agregar(MouseEvent event) {
		if (camposRequeridosCompletos()) {
			if (ExpReg.mailValido(txtMail.getText())) {
				Usuario usuario = new Usuario(0, textNombre.getText(), txtApellido.getText(), txtUserName.getText(),
						txtClave.getText(), txtMail.getText(), 1);
				usuario.setRecibeAlertasPorMail(chkRecibeAlertasPorMail.isSelected());
				usuario.setUserMantis(txtUserNameMantis.getText().trim());
				usuario.setClaveMantis(txtClaveMantis.getText());
				servicio.agregarUsuario(usuario);
				masterData.add(usuario);
				cerrarVentana();
			} else
				Notifications.create().title("Atencion").text("Ingrese Mail Valido").darkStyle().showError();
		} else {

			registrarValidadores();
		}

	}

	boolean camposRequeridosCompletos() {
		return (!txtUserName.getText().isEmpty() && !txtClave.getText().isEmpty() && !textNombre.getText().isEmpty()
				&& !txtApellido.getText().isEmpty() && !txtMail.getText().isEmpty());
	}

	@FXML
	void validar(InputMethodEvent event) {
		if (txtUserName.getText().isEmpty() || txtClave.getText().isEmpty()) {
			btnAgregar.setDisable(false);
		} else {
			btnAgregar.setDisable(true);
		}

		registrarValidadores();
	}

	public ObservableList<Usuario> getMasterData() {
		return masterData;
	}

	public void setMasterData(ObservableList<Usuario> masterData) {
		this.masterData = masterData;
	}
	
	private void registrarValidadores()
	{
		validationSupport.registerValidator(textNombre, Validator.createEmptyValidator(campoRequerido));
		validationSupport.registerValidator(txtApellido, Validator.createEmptyValidator(campoRequerido));
		validationSupport.registerValidator(txtMail, Validator.createEmptyValidator(campoRequerido));
		validationSupport.registerValidator(txtUserName, Validator.createEmptyValidator(campoRequerido));
		validationSupport.registerValidator(txtClave, Validator.createEmptyValidator(campoRequerido));
	}
	
	private void cerrarVentana()
	{
		Stage stage = (Stage) ventanaUsuario.getScene().getWindow();
		stage.close();
	}

}
