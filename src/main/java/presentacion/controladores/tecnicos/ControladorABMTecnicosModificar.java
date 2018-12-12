package presentacion.controladores.tecnicos;

import java.net.URL;
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

public class ControladorABMTecnicosModificar implements Initializable {

	@FXML
	private DialogPane ventanaModificarTecnico;

	@FXML
	private TextField txtDni;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtApellido;

	@FXML
	private TextField txtMantis;

	@FXML
	private Button btnModificar;

	@FXML
	private Button btnDescartar;

	@FXML
	private TextField txtLegajo;

	@FXML
	private ListView<EspecialidadDTO> listViewEspecialidadesDisponibles;

	@FXML
	private ListView<EspecialidadDTO> listViewEspecialidadesElegidas;

	@FXML
	private TextField txtId;

	@FXML
	private Button btnenviar;

	@FXML
	private Button btndevolver;

	private TecnicoDTO tecnicoAmodificar;

	ObservableList<EspecialidadDTO> listViewEspecialidadesDisponiblesMaster = FXCollections.observableArrayList();

	ObservableList<EspecialidadDTO> listViewEspecialidadesElegidasMaster = FXCollections.observableArrayList();

	private ValidationSupport validationSupport = new ValidationSupport();

	TecnicoService tecnicoService = new TecnicoService(new DAOSQLFactory());

	private ObservableList<TecnicoDTO> masterData = FXCollections.observableArrayList();

	@FXML
	void modificarTecnico(MouseEvent event) {
		if (!listViewEspecialidadesElegidas.getItems().isEmpty()) {
			Tecnico tecnico = new Tecnico();
			tecnico.setIdTecnico(tecnicoAmodificar.getIdTecnico().get());
			tecnico.setLegajo(txtLegajo.getText().trim());
			tecnico.setDni(txtDni.getText().trim());
			tecnico.setNombre(txtNombre.getText().trim());
			tecnico.setApellido(txtApellido.getText().trim());
			tecnico.setActivo(1);
			tecnico.setEtiquetaMantis(txtMantis.getText().trim());
			tecnicoService.editarTecnico(tecnico);
			TecnicoDTO tecnicoDTO = new TecnicoDTO();
			tecnicoDTO.setIdTecnico(tecnico.getIdTecnico());
			tecnicoDTO.setLegajo(tecnico.getLegajo());
			tecnicoDTO.setDni(tecnico.getDni());
			tecnicoDTO.setNombre(tecnico.getNombre());
			tecnicoDTO.setApellido(tecnico.getApellido());
			tecnicoDTO.setEtiquetaMantis(tecnico.getEtiquetaMantis());
			tecnicoDTO.setActivo(1);

			List<EspecialidadDTO> especialidadesAEliminar = this.listViewEspecialidadesDisponiblesMaster;
			for (EspecialidadDTO especialidadDTO : especialidadesAEliminar) {
				Especialidad especialidad = new Especialidad(especialidadDTO.getIdEspecialidad().get(),
						especialidadDTO.getNombre().get());
				if (tecnicoService.existeTecnicoXEspecialidad(tecnico, especialidad)) {
					tecnicoService.eliminarTecnicoXEspecialidad(tecnico, especialidad);
				}
			}
			List<EspecialidadDTO> especialidadesAGuardar = this.listViewEspecialidadesElegidasMaster;
			for (EspecialidadDTO especialidadDTO : especialidadesAGuardar) {
				Especialidad especialidad = new Especialidad(especialidadDTO.getIdEspecialidad().get(),
						especialidadDTO.getNombre().get());
				if (!tecnicoService.existeTecnicoXEspecialidad(tecnico, especialidad))
					tecnicoService.agregarTecnicoConEspecialidad(tecnico, especialidad);
			}
			tecnicoDTO.setEspecialidad(especialidadesAGuardar);

			masterData.remove(tecnicoAmodificar);
			masterData.add(tecnicoDTO);

			Stage stage = (Stage) this.ventanaModificarTecnico.getScene().getWindow();
			stage.close();
		} else {
			Dialogos.error("Error al Agregar un Tecnico", "Revise los siguientes datos:",
					"Debe elegir al menos una especialidad");
		}
	}

	@FXML
	void descartarTecnico(MouseEvent event) {
		Stage stage = (Stage) ventanaModificarTecnico.getScene().getWindow();
		stage.close();
	}

	@FXML
	void validarModificarTecnico(KeyEvent event) {
		if (!camposObligatoriosVacios()) {
			btnModificar.setDisable(false);
		} else {
			btnModificar.setDisable(true);
		}
		
		String mensaje = "Campo requerido";
		validationSupport.registerValidator(txtNombre, Validator.createEmptyValidator(mensaje));
		validationSupport.registerValidator(txtApellido, Validator.createEmptyValidator(mensaje));
		validationSupport.registerValidator(txtDni, Validator.createEmptyValidator(mensaje));
		validationSupport.registerValidator(txtLegajo, Validator.createEmptyValidator(mensaje));
	}

	boolean camposObligatoriosVacios() {
		return (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()
				|| txtDni.getText().trim().isEmpty() || txtLegajo.getText().trim().isEmpty()
				|| txtMantis.getText().trim().isEmpty());
	}

	public void cargarTecnicoAmodificar(TecnicoDTO tecnico) {
		this.tecnicoAmodificar = tecnico;
		txtId.setText(tecnico.getIdTecnico().getValue().toString());
		txtNombre.setText(tecnico.getNombre().get());
		txtApellido.setText(tecnico.getApellido().get());
		txtDni.setText(tecnico.getDni().get());
		txtLegajo.setText(tecnico.getLegajo().get());
		txtMantis.setText(tecnico.getStringEtiquetaMantis());
		List<EspecialidadDTO> especialidades = tecnico.getEspecialidades();
		for (EspecialidadDTO especialidadDTO : especialidades) {
			listViewEspecialidadesElegidas.getItems().add(especialidadDTO);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.btnModificar.setDisable(false);
		this.listViewEspecialidadesDisponibles.setItems(listViewEspecialidadesDisponiblesMaster);
		this.listViewEspecialidadesElegidas.setItems(listViewEspecialidadesElegidasMaster);
		AdministracionService administracionService = new AdministracionService(new DAOSQLFactory());
		List<Especialidad> especialidades = administracionService.obtenerEspecialidades();
		for (Especialidad especialidad : especialidades) {
			EspecialidadDTO especialidadDTO = new EspecialidadDTO(especialidad.getIdEspecialidad(),
					especialidad.getNombre());
			listViewEspecialidadesDisponibles.getItems().add(especialidadDTO);
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

	public ObservableList<TecnicoDTO> getMasterData() {
		return masterData;
	}

	public void setMasterData(ObservableList<TecnicoDTO> masterData) {
		this.masterData = masterData;
	}

}