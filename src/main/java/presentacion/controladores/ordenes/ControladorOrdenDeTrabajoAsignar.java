package presentacion.controladores.ordenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domain.model.EnumEstadoOrdenTrabajo;
import domain.model.Especialidad;
import domain.model.OrdenDeTrabajo;
import domain.model.OrdenesTrabajoTecnicos;
import domain.model.Tecnico;
import dto.EspecialidadDTO;
import dto.EstadoOrdenTrabajoDTO;
import dto.OrdenTrabajoDTO;
import dto.ProyectoDTO;
import dto.TecnicoDTO;
import dto.TipoActividadDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.OrdenDeTrabajoService;
import services.OrdenesTrabajoTecnicosService;
import services.TecnicoService;
import util.Dialogos;

public class ControladorOrdenDeTrabajoAsignar implements Initializable {

	@FXML
	private DialogPane ventanaRegistrarOrden;

	@FXML
	private TextField txtIdMantis;

	@FXML
	private ChoiceBox<ProyectoDTO> chbProyecto;

	@FXML
	private DatePicker datePickerFechaInicio;

	@FXML
	private TextField txtResumen;

	@FXML
	private ChoiceBox<TipoActividadDTO> chbTipoActividad;

	@FXML
	private TextField txtModuloSede;

	@FXML
	private TextArea textDescripcion;

	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnDescartar;

	@FXML
	private TableView<TecnicoDTO> tablaTecnicosDisponibles;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaNombre;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaApellido;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaEspecialidad;

	@FXML
	private TextField txtBusqueda;

	@FXML
	private TableView<TecnicoDTO> tablaTecnicosAsignados;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaNombre1;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaApellido1;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaEspecialidad1;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnDevolver;

	private OrdenTrabajoDTO ordenAModificar;

	OrdenDeTrabajoService service = new OrdenDeTrabajoService(new DAOSQLFactory());

	private ObservableList<TecnicoDTO> masterData = FXCollections.observableArrayList();

	private ObservableList<TecnicoDTO> masterData1 = FXCollections.observableArrayList();

	@SuppressWarnings("static-access")
	@FXML
	void asignarOrden(MouseEvent event) {
		int idMantis = Integer.parseInt(txtIdMantis.getText());
		List<String> nombreTecnicos = new ArrayList<>();
		String contenido = "";
		if (masterData1.size() == 1)
			contenido = "Desea Asignar el Tecnico?";
		else
			contenido = "Desea Asignar los Tecnicos?";
		if (Dialogos.confirmacion("Alerta", contenido, "", "SI", "NO")) {
			OrdenesTrabajoTecnicosService ordenesTrabajoTecnicosService = new OrdenesTrabajoTecnicosService(
					new DAOSQLFactory());
			OrdenDeTrabajoService ordenDeTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());
			List<TecnicoDTO> tecnicos = masterData1;
			List<Especialidad> especialidades = new ArrayList<>();
			for (TecnicoDTO tecnicoDTO : tecnicos) {
				List<EspecialidadDTO> especialidadDTOs = tecnicoDTO.getEspecialidades();
				for (EspecialidadDTO especialidadDTO : especialidadDTOs) {
					Especialidad especialidad = new Especialidad(especialidadDTO.getIdEspecialidad().get(),
							especialidadDTO.getNombre().get());
					especialidades.add(especialidad);
				}

				Tecnico tecnico = new Tecnico(tecnicoDTO.getIdTecnico().get(), tecnicoDTO.getDni().get(),
						tecnicoDTO.getLegajo().get(), tecnicoDTO.getNombre().get(), tecnicoDTO.getApellido().get(),
						especialidades, 1);
				OrdenDeTrabajo ot = new OrdenDeTrabajo();
				ot.setIdOrdenDeTrabajo(txtIdMantis.getText());
				int id = ordenDeTrabajoService.obtenerIdOrden(ot);
				ot.setId(id);
				OrdenesTrabajoTecnicos ordenesTrabajoTecnicos = new OrdenesTrabajoTecnicos(
						id, tecnico.getIdTecnico());
				ordenesTrabajoTecnicosService.asignarOrdenesTrabajoTecnicos(ordenesTrabajoTecnicos);
				ordenDeTrabajoService.cambiarEstadoAsignada(ot);
				nombreTecnicos.add(tecnicoDTO.getStringEtiquetaMantis());
			}
			ordenDeTrabajoService.asignarTecnicos(idMantis, nombreTecnicos);
			Stage stage = (Stage) ventanaRegistrarOrden.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	void devolver(MouseEvent event) {
		TecnicoDTO tecnicoDTO = tablaTecnicosAsignados.getSelectionModel().getSelectedItem();
		if (this.masterData1.size() == 1) {
			this.btnDevolver.setDisable(true);
			this.btnAceptar.setDisable(true);
		} else {
			this.btnAceptar.setDisable(false);
		}

		if (tecnicoDTO != null) {
			transferenciaTabla(masterData1, masterData, tablaTecnicosAsignados);
			this.btnDevolver.setDisable(false);
		}
	}

	@FXML
	void enviar(MouseEvent event) {
		if (this.masterData1.size() == 1) {
			this.btnAceptar.setDisable(true);
		} else {
			this.btnAceptar.setDisable(false);
		}

		TecnicoDTO tecnicoDTO = tablaTecnicosDisponibles.getSelectionModel().getSelectedItem();
		if (tecnicoDTO != null) {
			transferenciaTabla(masterData, masterData1, tablaTecnicosDisponibles);
			btnEnviar.setDisable(false);
			this.btnAceptar.setDisable(false);
		}
	}

	@FXML
	void validarAgregarOrden(KeyEvent event) {
		// TODO Completar o quitar metodo
	}

	public void cargarOrden(OrdenTrabajoDTO ordenTrabajoDTO) {
		this.ordenAModificar = ordenTrabajoDTO;
		this.ordenAModificar.setId(ordenTrabajoDTO.getId().get());
		txtIdMantis.setText(ordenTrabajoDTO.getIdOrdenTrabajo().get());
		txtModuloSede.setText(ordenAModificar.getModuloSede().get());
		txtResumen.setText(ordenAModificar.getResumen().get());
		textDescripcion.setText(ordenAModificar.getDescripcion().get());
		chbProyecto.setValue(ordenAModificar.getProyecto());
		chbTipoActividad.setValue(ordenAModificar.getTipoActividad());
		datePickerFechaInicio.setValue(ordenAModificar.getFechaInicio());
	}

	private void cargarTiposActividad() {
		List<TipoActividadDTO> tiposActividad = service.obtenerTiposActividadDTO();
		chbTipoActividad.getItems().addAll(tiposActividad);
	}

	private void cargarEstados() {
		List<EstadoOrdenTrabajoDTO> estados = new ArrayList<EstadoOrdenTrabajoDTO>();
		estados.add(EnumEstadoOrdenTrabajo.NUEVA.getDTO());
		estados.add(EnumEstadoOrdenTrabajo.ASIGNADA.getDTO());
		estados.add(EnumEstadoOrdenTrabajo.REALIZADA.getDTO());
		estados.add(EnumEstadoOrdenTrabajo.CERRADA.getDTO());
		estados.add(EnumEstadoOrdenTrabajo.SUSPENDIDA.getDTO());
	}

	private void cargarProyectos() {
		List<ProyectoDTO> proyectos = service.obtenerProyectosDTO();
		for (int i = 0; i < proyectos.size(); i++) {
			chbProyecto.getItems().add(proyectos.get(i));
		}
		chbProyecto.getSelectionModel().selectFirst();
	}

	private void inicilizarTablaDisponibles() {
		masterData.clear();
		this.btnAceptar.setDisable(true);
		TecnicoService tecnicoService = new TecnicoService(new DAOSQLFactory());
		List<Tecnico> tecnicos = tecnicoService.obtenerTecnicos();
		for (Tecnico tecnico2 : tecnicos) {
			List<EspecialidadDTO> especialidadesDTO = new ArrayList<>();
			TecnicoDTO tecnicoDTO = new TecnicoDTO();
			tecnicoDTO.setIdTecnico(tecnico2.getIdTecnico());
			tecnicoDTO.setActivo(1);
			tecnicoDTO.setDni(tecnico2.getDni());
			tecnicoDTO.setNombre(tecnico2.getNombre());
			tecnicoDTO.setApellido(tecnico2.getApellido());
			tecnicoDTO.setLegajo(tecnico2.getLegajo());
			tecnicoDTO.setEtiquetaMantis(tecnico2.getEtiquetaMantis());
			Tecnico tecnicoConEspecialidad = new Tecnico();
			tecnicoConEspecialidad.setIdTecnico(tecnicoDTO.getIdTecnico().get());
			List<Especialidad> especialidades = tecnicoService.obtenerEspecialidadesXTecnico(tecnicoConEspecialidad);
			for (Especialidad especialidad : especialidades) {
				EspecialidadDTO especialidadDTO = new EspecialidadDTO(especialidad.getIdEspecialidad(),
						especialidad.getNombre());
				especialidadesDTO.add(especialidadDTO);
			}
			tecnicoDTO.setEspecialidad(especialidadesDTO);
			this.masterData.add(tecnicoDTO);
		}
		this.masterData.remove(0);
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaApellido.setCellValueFactory(cellData -> cellData.getValue().getApellido());
		columnaEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidades"));
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<TecnicoDTO> filteredData = new FilteredList<>(masterData, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tecnico -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (tecnico.getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (tecnico.getApellido().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (tecnico.getEspecialidadesString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<TecnicoDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaTecnicosDisponibles.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaTecnicosDisponibles.setItems(sortedData);
	}

	private void inicilizarTablaAsignadas() {
		this.tablaTecnicosAsignados.setEditable(true);
		columnaNombre1.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaApellido1.setCellValueFactory(callData -> callData.getValue().getApellido());
		columnaEspecialidad1.setCellValueFactory(new PropertyValueFactory<>("especialidades"));
		tablaTecnicosAsignados.setItems(masterData1);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.cargarEstados();
		this.cargarTiposActividad();
		this.cargarProyectos();
		this.inicilizarTablaDisponibles();
		this.inicilizarTablaAsignadas();
	}

	@FXML
	void descartar(MouseEvent event) {
		Stage stage = (Stage) ventanaRegistrarOrden.getScene().getWindow();
		stage.close();
	}

	private void transferenciaTabla(ObservableList<TecnicoDTO> baseRemover, ObservableList<TecnicoDTO> baseAdicionar,
			TableView<TecnicoDTO> tablaRemover) {

		TecnicoDTO tecnicoDTO = tablaRemover.getSelectionModel().getSelectedItem();
		tablaRemover.getSelectionModel().clearSelection();
		baseRemover.remove(tecnicoDTO);
		baseAdicionar.add(tecnicoDTO);
	}
}

