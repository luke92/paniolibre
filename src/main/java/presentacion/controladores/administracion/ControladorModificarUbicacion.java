package presentacion.controladores.administracion;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Deposito;
import domain.model.UbicacionDeposito;
import dto.DepositoDTO;
import dto.UbicacionDepositoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AdministracionService;

public class ControladorModificarUbicacion implements Initializable {

	@FXML
	private DialogPane ventanaAgregarUbicacion;

	@FXML
	private Button btnConfirmarModificar;

	@FXML
	private Button btnDescartar;

	@FXML
	private TextField textNombre;

	private ObservableList<UbicacionDepositoDTO> masterData = FXCollections.observableArrayList();

	private AdministracionService servicio = new AdministracionService(new DAOSQLFactory());

	UbicacionDepositoDTO ubicacionAModificar;

	private ValidationSupport validationSupport = new ValidationSupport();

	@FXML
	void validarUbicacion(KeyEvent event) {
		if (camposCompletos()) {
			btnConfirmarModificar.setDisable(false);
		} else {
			btnConfirmarModificar.setDisable(true);
		}

		validationSupport.registerValidator(textNombre, Validator.createEmptyValidator("Campo requerido"));
	}

	boolean camposCompletos() {
		boolean ret;
		if (textNombre.getText().isEmpty())
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

	@FXML
	void modificar(MouseEvent event) {
		UbicacionDeposito deposito = new UbicacionDeposito();
		deposito.setIdUbicacionDeposito(ubicacionAModificar.getIdUbicacionDeposito().get());
		deposito.setNombre(textNombre.getText());
		Deposito d = new Deposito();
		d.setIdDeposito(ubicacionAModificar.getDeposito().getIdDeposito().get());
		d.setNombre(ubicacionAModificar.getDeposito().getNombre().get());
		deposito.setDeposito(d);
		if(servicio.existeNombreUbicacion(deposito) == false) {
		servicio.editarUbicacionDeposito(deposito);
		masterData.remove(ubicacionAModificar);
		masterData.add(deposito.getDTO());
		Stage stage = (Stage) ventanaAgregarUbicacion.getScene().getWindow();
		stage.close();
		}else {
			Notifications.create().title("Atencion el nombre que elijio ya existe ").text("Seleccione otro nombre para la ubicacion.").darkStyle().showWarning();
			
		}
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

		}

	}

	public void cargarUbicacionAmodificar(UbicacionDepositoDTO ubicacionDepositoDTO) {
		this.ubicacionAModificar = ubicacionDepositoDTO;
		this.textNombre.setText(ubicacionDepositoDTO.getNombre().get());

	}

}
