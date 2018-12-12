package presentacion.controladores.tecnicos;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Especialidad;
import domain.model.Tecnico;
import dto.EspecialidadDTO;
import dto.TecnicoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AdministracionService;
import services.TecnicoService;
import util.Dialogos;

public class ControladorABMTecnicosAgregar implements Initializable {

	@FXML
	private DialogPane ventanaAgregarTecnico;

	@FXML
	private TextField txtDni;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtApellido;

	@FXML
	private TextField txtMantis;

	@FXML
	private Button btnAgregar;

	@FXML
	private Button btnDescartar;

	@FXML
	private TextField txtLegajo;

	@FXML
	private Button btnenviar;

	@FXML
	private Button btndevolver;

	@FXML
	private ListView<EspecialidadDTO> listViewEspecialidadesDisponibles;

	@FXML
	private ListView<EspecialidadDTO> listViewEspecialidadesElegidas;

	private ObservableList<TecnicoDTO> masterData = FXCollections.observableArrayList();

	ObservableList<EspecialidadDTO> listViewEspecialidadesDisponiblesMaster = FXCollections.observableArrayList();

	ObservableList<EspecialidadDTO> listViewEspecialidadesElegidasMaster = FXCollections.observableArrayList();

	TecnicoDTO tecnico;

	TecnicoService tecnicoService = new TecnicoService(new DAOSQLFactory());

	private ValidationSupport validationSupport = new ValidationSupport();

	@FXML
	void agregarTecnico(MouseEvent event) {
		List<EspecialidadDTO> especialidadesDTO = this.listViewEspecialidadesElegidasMaster;
		List<Especialidad> especialidades = new ArrayList<>();
		for (EspecialidadDTO especialidadDTO : especialidadesDTO) {
			Especialidad especialidad = new Especialidad(especialidadDTO.getIdEspecialidad().get(),
					especialidadDTO.getNombre().get());
			especialidades.add(especialidad);
		}
		Tecnico tecnicoModelo = new Tecnico(0, txtDni.getText(), txtLegajo.getText(), txtNombre.getText(),
				txtApellido.getText(), especialidades, 1);
		tecnicoModelo.setEtiquetaMantis(txtMantis.getText().trim());
		if (!existeLegajo(tecnicoModelo)) {
			tecnicoService.agregarTecnico(tecnicoModelo);

			TecnicoDTO tecnicoDTO = new TecnicoDTO(0, txtNombre.getText(), txtDni.getText(), txtLegajo.getText(),
					especialidadesDTO, 1, txtApellido.getText(), txtMantis.getText().trim());
			tecnicoModelo.setIdTecnico(tecnicoService.obtenerIdTecnico(tecnicoModelo));

			if (!listViewEspecialidadesElegidas.getItems().isEmpty()) {
				for (Especialidad especialidad2 : especialidades) {
					tecnicoService.agregarTecnicoConEspecialidad(tecnicoModelo, especialidad2);
					masterData.add(tecnicoDTO);
					Stage stage = (Stage) this.ventanaAgregarTecnico.getScene().getWindow();
					stage.close();
				}
			} else {
				Dialogos.error("Error al Agregar un Tecnico", "Revise los siguientes datos:",
						"Debe elegir al menos una especialidad");
			}
		} else {
			Dialogos.error("Error al Agregar un Tecnico", "Revise los siguientes datos:",
					"El Legajo ingresado ya existe en la base de datos");
		}
	}

	boolean existeLegajo(Tecnico tecnico) {
		boolean ret = false;
		for (Tecnico t : tecnicoService.obtenerTecnicos())
			if (t.getLegajo().equals(tecnico.getLegajo()))
				ret = true;
		return ret;
	}

	@FXML
	void descartarTecnico(MouseEvent event) {
		Stage stage = (Stage) ventanaAgregarTecnico.getScene().getWindow();
		stage.close();
	}

	@FXML
	void validarAgregarTecnico(KeyEvent event) {
		validarCamposNulos();
	}

	public void setMasterData(ObservableList<TecnicoDTO> masterData) {
		this.masterData = masterData;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.btnAgregar.setDisable(true);
		AdministracionService administracionService = new AdministracionService(new DAOSQLFactory());
		this.listViewEspecialidadesDisponibles.setItems(listViewEspecialidadesDisponiblesMaster);
		this.listViewEspecialidadesElegidas.setItems(listViewEspecialidadesElegidasMaster);
		List<Especialidad> especialidades = administracionService.obtenerEspecialidades();
		for (Especialidad especialidad : especialidades) {
			EspecialidadDTO especialidadDTO = new EspecialidadDTO(especialidad.getIdEspecialidad(),
					especialidad.getNombre());
			this.listViewEspecialidadesDisponiblesMaster.addAll(especialidadDTO);
		}
		btnenviar.setOnMouseClicked(event -> {
			if (listViewEspecialidadesDisponibles.getSelectionModel().getSelectedIndex() != -1)
				this.transferenciaLista(listViewEspecialidadesDisponiblesMaster, listViewEspecialidadesElegidasMaster,
						listViewEspecialidadesDisponibles);
		});

		btndevolver.setOnMouseClicked(event -> {
			if (listViewEspecialidadesElegidas.getSelectionModel().getSelectedIndex() != -1)
				this.transferenciaLista(listViewEspecialidadesElegidasMaster, listViewEspecialidadesDisponiblesMaster,
						listViewEspecialidadesElegidas);
		});

	}

	private void transferenciaLista(ObservableList<EspecialidadDTO> baseRemover,
			ObservableList<EspecialidadDTO> baseAdicionar, ListView<EspecialidadDTO> tablaRemover) {
		EspecialidadDTO herramienta = tablaRemover.getSelectionModel().getSelectedItem();
		tablaRemover.getSelectionModel().clearSelection();
		baseRemover.remove(herramienta);
		baseAdicionar.add(herramienta);

	}

	public void cargarTecnicoAmodificar(TecnicoDTO tecnico) {
		this.tecnico = tecnico;
		this.cargarCampos();
	}

	public void cargarCampos() {
		txtDni.setText(tecnico.getDni().get());
		txtNombre.setText(tecnico.getNombre().get());
		txtApellido.setText(tecnico.getApellido().get());
		txtLegajo.setText(tecnico.getLegajo().get());
	}

	private void validarCamposNulos() {
		if (!txtNombre.getText().trim().isEmpty() && !txtApellido.getText().trim().isEmpty()
				&& !txtDni.getText().trim().isEmpty() && !txtLegajo.getText().trim().isEmpty()
				&& !txtMantis.getText().trim().isEmpty()) {
			btnAgregar.setDisable(false);
		} else {
			btnAgregar.setDisable(true);
		}
		
		String mensaje = "Campo requerido";
		validationSupport.registerValidator(txtNombre, Validator.createEmptyValidator(mensaje));
		validationSupport.registerValidator(txtApellido, Validator.createEmptyValidator(mensaje));
		validationSupport.registerValidator(txtDni, Validator.createEmptyValidator(mensaje));
		validationSupport.registerValidator(txtLegajo, Validator.createEmptyValidator(mensaje));
	}

}
