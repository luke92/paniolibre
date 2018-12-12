package presentacion.controladores;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Deposito;
import domain.model.Estado;
import dto.DepositoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AdministracionService;
import services.DepositoService;

public class ControladorDepositoABM {

	@FXML
	private DialogPane ventanaAgregarDeposito;

	@FXML
	private DialogPane ventanaEditarDeposito;

	@FXML
	private Button btnConfirmarAgregarDeposito;

	@FXML
	private Button btnConfirmarEditarDeposito;

	@FXML
	private Button btnDescartarDeposito;

	@FXML
	private TextField textNombre;

	@FXML
	private TextField textComentario;

	private ObservableList<DepositoDTO> masterData = FXCollections.observableArrayList();

	private DepositoService depositoService = new DepositoService(new DAOSQLFactory());

	DepositoDTO depositoAmodificar;

	@FXML
	private TextField textId;

	private ValidationSupport validationSupport = new ValidationSupport();

	@FXML
	void confirmarAgregarDeposito(MouseEvent event) {
		AdministracionService administracionService = new AdministracionService(new DAOSQLFactory());
		Deposito nuevoDeposito = new Deposito();
		nuevoDeposito.setIdDeposito(0);
		nuevoDeposito.setNombre(this.textNombre.getText());
		nuevoDeposito.setComentario(this.textComentario.getText());

		depositoService.agregarDeposito(nuevoDeposito);
		nuevoDeposito.setIdDeposito(administracionService.obtenerIdDeposito(nuevoDeposito));
		this.masterData.add(nuevoDeposito.getDTO());
		Stage stage = (Stage) this.ventanaAgregarDeposito.getScene().getWindow();
		stage.close();
	}

	@FXML
	void confirmarEditarDeposito(MouseEvent event) {
		Deposito nuevoDeposito = new Deposito();
		nuevoDeposito.setIdDeposito(depositoAmodificar.getIdDeposito().get());
		nuevoDeposito.setNombre(this.textNombre.getText());
		nuevoDeposito.setComentario(this.textComentario.getText());
		nuevoDeposito.setActivo(Estado.ALTA);
		depositoService.editarDeposito(nuevoDeposito);
		DepositoDTO depositoDTO = new DepositoDTO();
		depositoDTO.setIdDeposito(depositoAmodificar.getIdDeposito().get());
		depositoDTO.setNombre(this.textNombre.getText());
		depositoDTO.setComentario(this.textComentario.getText());
		depositoDTO.setActivo(depositoAmodificar.getActivo());
		this.masterData.remove(depositoAmodificar);
		this.masterData.add(depositoDTO);

		Stage stage = (Stage) this.ventanaEditarDeposito.getScene().getWindow();
		stage.close();
	}

	void cargarDepositoAmodificar(DepositoDTO deposito) {
		this.depositoAmodificar = deposito;
		textNombre.setText(deposito.getNombre().get());
		textComentario.setText(deposito.getComentario().get());
		textId.setText(deposito.getIdDeposito().asString().get());

	}

	@FXML
	void descartarDeposito(MouseEvent event) {
		Stage stage = (Stage) ventanaAgregarDeposito.getScene().getWindow();
		stage.close();
	}

	@FXML
	void descartarDepositoEditar(MouseEvent event) {
		Stage stage = (Stage) ventanaEditarDeposito.getScene().getWindow();
		stage.close();
	}

	public ObservableList<DepositoDTO> getMasterData() {
		return masterData;
	}

	public void setMasterData(ObservableList<DepositoDTO> masterData) {
		this.masterData = masterData;
	}

	@FXML
	void validarAgregarDeposito() {
		if (!textNombre.getText().isEmpty()) {
			btnConfirmarAgregarDeposito.setDisable(false);
		} else {
			btnConfirmarAgregarDeposito.setDisable(true);
		}

		validationSupport.registerValidator(textNombre, Validator.createEmptyValidator("Campo requerido"));
	}

	@FXML
	void validarModificarDeposito() {
		if (!textNombre.getText().isEmpty()) {
			btnConfirmarEditarDeposito.setDisable(false);
		} else {
			btnConfirmarEditarDeposito.setDisable(true);
		}

		validationSupport.registerValidator(textNombre, Validator.createEmptyValidator("Campo requerido"));
	}
}