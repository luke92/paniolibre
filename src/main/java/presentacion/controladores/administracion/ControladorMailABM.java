package presentacion.controladores.administracion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Mail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import services.MailService;
import util.Dialogos;
import util.ExpReg;

public class ControladorMailABM implements Initializable {

	@FXML
	private DialogPane ventanaConfigurarMail;
	@FXML
	private ScrollPane panelMail;
	@FXML
	private TableView<Mail> tablaMail;
	@FXML
	private Button btnConfirmarModificarMail;
	@FXML
	private Button btnDescartarMail;
	@FXML
	private TextField textMail;
	@FXML
	private PasswordField textClave;
	@FXML
	private TableColumn<Mail, String> columnamail;
	@FXML
	private TableColumn<Mail, String> columnaclave;

	private ObservableList<Mail> masterData = FXCollections.observableArrayList();

	private ValidationSupport validationSupport = new ValidationSupport();

	private MailService mailService = new MailService(new DAOSQLFactory());

	Mail mailAmodificar;

	@FXML
	void editarMail(MouseEvent event) {
		FXMLLoader root;
		try {
			if (tablaMail.getSelectionModel().getSelectedIndex() == 0) {
				root = new FXMLLoader(getClass().getResource("/vistas/administracion/editarmail.fxml"));
				Scene scene = new Scene(root.load());
				scene.setFill(Color.TRANSPARENT);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.TRANSPARENT);

				ControladorMailABM controlador = root.getController();

				mailAmodificar = masterData.get(tablaMail.getSelectionModel().getSelectedIndex());
				controlador.cargarMailAmodificar(mailAmodificar);
				controlador.setMasterData(masterData);

				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();

				// Eventos del teclado
				scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if (event.getCode() == KeyCode.ESCAPE) {
							stage.close();
						}
					}
				});
			} else {
				Notifications.create().title("Atencion").text("Seleccione el Mail para Modificar").darkStyle()
						.showWarning();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cargarMailAmodificar(Mail mail) {
		textMail.setText(mail.getMail());
		textClave.setText(mail.getClave());
	}

	private void setMasterData(ObservableList<Mail> masterData) {
		this.masterData = masterData;
	}

	@FXML
	void confirmarModificarMail(MouseEvent event) {
		if (ExpReg.mailValido(textMail.getText())) {
			Mail nuevoMail = new Mail();
			nuevoMail.setIdMail(1);
			nuevoMail.setMail(textMail.getText());
			nuevoMail.setClave(textClave.getText());

			mailService.editarMail(nuevoMail);
			this.masterData.remove(mailAmodificar);
			this.masterData.add(nuevoMail);

			Stage stage = (Stage) this.ventanaConfigurarMail.getScene().getWindow();
			stage.close();
		} else {
			Dialogos.error("Error", "Error al modificar mail", "Ingrese Mail v\u00e1lido");
		}
	}

	@FXML
	void validarModificarMail(InputMethodEvent event) {
		if (textMail.getText().isEmpty() || textClave.getText().isEmpty()) {
			btnConfirmarModificarMail.setDisable(true);
		} else {
			btnConfirmarModificarMail.setDisable(true);
		}

		validationSupport.registerValidator(textMail, Validator.createEmptyValidator("Campo requerido"));
		validationSupport.registerValidator(textClave, Validator.createEmptyValidator("Campo requerido"));
	}

	boolean camposRequeridosCompletos() {
		if (!textMail.getText().isEmpty() && !textClave.getText().isEmpty())
			return true;
		else
			return false;
	}

	@FXML
	void descartarMail(MouseEvent event) {
		Stage stage = (Stage) ventanaConfigurarMail.getScene().getWindow();
		stage.close();
	}

	public void llenarTablaMail() {
		Mail mail = mailService.obtenerMail(1);

		this.masterData.add(mail);

		this.tablaMail.setItems(masterData);

		columnamail.setCellValueFactory(new PropertyValueFactory<>("mail"));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Revisar si es necesario este metodo
	}
}