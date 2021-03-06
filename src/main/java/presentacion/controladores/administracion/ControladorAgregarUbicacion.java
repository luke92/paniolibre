package presentacion.controladores.administracion;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Deposito;
import domain.model.Estado;
import domain.model.UbicacionDeposito;
import dto.DepositoDTO;
import dto.UbicacionDepositoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AdministracionService;

public class ControladorAgregarUbicacion implements Initializable {

	@FXML
	private DialogPane ventanaAgregarUbicacion;

	@FXML
	private Button btnConfirmarAgregar;

	@FXML
	private Button btnDescartar;

	@FXML
	private TextField textNombre;

	private ValidationSupport validationSupport = new ValidationSupport();

	@FXML
	private ComboBox<DepositoDTO> comboDeposito;

	private ObservableList<UbicacionDepositoDTO> masterData = FXCollections.observableArrayList();

	private AdministracionService servicio = new AdministracionService(new DAOSQLFactory());

	@FXML
	void confirmarAgregar(MouseEvent event) {
		Deposito deposito = new Deposito();
		deposito.setIdDeposito(comboDeposito.getValue().getIdDeposito().get());
		UbicacionDeposito ubicacionDeposito = new UbicacionDeposito(0, textNombre.getText(), deposito, Estado.ALTA);
		if(servicio.existeNombreUbicacion(ubicacionDeposito) == false) {
		servicio.agregarUbicacion(ubicacionDeposito);
		masterData.add(ubicacionDeposito.getDTO());
		Stage stage = (Stage) ventanaAgregarUbicacion.getScene().getWindow();
		stage.close();
		}else {
			Notifications.create().title("Atencion el nombre que elijio ya existe ").text("Seleccione otro nombre para la ubicacion.").darkStyle().showWarning();
			
		}
	}

	@FXML
	void validarUbicacion(KeyEvent event) {
		if (camposCompletos()) {
			btnConfirmarAgregar.setDisable(false);
		} else {
			btnConfirmarAgregar.setDisable(true);
		}

		validationSupport.registerValidator(textNombre, Validator.createEmptyValidator("Campo requerido"));
		validationSupport.registerValidator(comboDeposito, Validator.createEmptyValidator("Campo requerido"));
	}

	boolean camposCompletos() {
		boolean ret;
		if (textNombre.getText().isEmpty() || comboDeposito.getSelectionModel().isEmpty())
			ret = false;
		else
			ret = true;
		return ret;
	}

	@FXML
	void descartar(MouseEvent event) {
		Stage stage = (Stage) ventanaAgregarUbicacion.getScene().getWindow();
		stage.close();
	}

	public ObservableList<UbicacionDepositoDTO> getMasterData() {
		return masterData;
	}

	public void setMasterData(ObservableList<UbicacionDepositoDTO> masterData) {
		this.masterData = masterData;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Deposito> depositos = servicio.obtenerDepositos();
		for (Deposito deposito : depositos) {
			DepositoDTO depositoDTO = new DepositoDTO();
			depositoDTO.setNombre(deposito.getNombre());
			depositoDTO.setIdDeposito(deposito.getIdDeposito());
			this.comboDeposito.getItems().add(depositoDTO);
		}
	}

}
