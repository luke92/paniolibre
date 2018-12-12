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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.UsuarioService;
import util.ExpReg;

public class ControladorUsuarioModificar {

	@FXML
	private DialogPane ventanaUsuario;

	@FXML
	private Button btnModificar;

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

	Usuario usuarioModificar;

	@FXML
	private Button btnDescartar;

	@FXML
	void descartar(MouseEvent event) {
		cerrarVentana();
	}

	@FXML
	void modificar(MouseEvent event) {
		if (camposRequeridosCompletos()) {
			if (ExpReg.mailValido(txtMail.getText())) {
				Usuario usuarioModf = new Usuario();
				usuarioModf.setActivo(1);
				usuarioModf.setIdUsuario(usuarioModificar.getIdUsuario());
				usuarioModf.setNombre(textNombre.getText());
				usuarioModf.setApellido(txtApellido.getText());
				usuarioModf.setUserName(txtUserName.getText());
				usuarioModf.setMail(txtMail.getText());
				usuarioModf.setClave(txtClave.getText());
				usuarioModf.setRecibeAlertasPorMail(chkRecibeAlertasPorMail.isSelected());
				usuarioModf.setUserMantis(txtUserNameMantis.getText().trim());
				usuarioModf.setClaveMantis(txtClaveMantis.getText());
				servicio.editarUsuario(usuarioModf);
				masterData.remove(usuarioModificar);
				masterData.add(usuarioModf);
				cerrarVentana();
			} else
				Notifications.create().title("Atencion").text("Ingrese Mail Valido").darkStyle().showError();
		} else {
			
			registrarValidadores();
		}
	}

	public ObservableList<Usuario> getMasterData() {
		return masterData;
	}

	public void setMasterData(ObservableList<Usuario> masterData) {
		this.masterData = masterData;
	}

	boolean camposRequeridosCompletos() {
		return (!txtUserName.getText().isEmpty() && !txtClave.getText().isEmpty() && !textNombre.getText().isEmpty()
				&& !txtApellido.getText().isEmpty() && !txtMail.getText().isEmpty());
	}

	public void cargarMailAmodificar(Usuario usuarioAModificar) {
		usuarioModificar = usuarioAModificar;
		textNombre.setText(usuarioAModificar.getNombre());
		txtApellido.setText(usuarioAModificar.getApellido());
		txtUserName.setText(usuarioAModificar.getUserName());
		txtMail.setText(usuarioAModificar.getMail());
		txtClave.setText(usuarioAModificar.getClave());
		chkRecibeAlertasPorMail.setSelected(usuarioAModificar.isRecibeAlertasPorMail());
		txtUserNameMantis.setText(usuarioAModificar.getUserMantis());
		txtClaveMantis.setText(usuarioAModificar.getClaveMantis());
	}
	
	private void registrarValidadores()
	{
		String campoRequerido = "Campo requerido";
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
