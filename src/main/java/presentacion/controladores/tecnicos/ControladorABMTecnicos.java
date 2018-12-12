package presentacion.controladores.tecnicos;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import domain.model.Especialidad;
import domain.model.Tecnico;
import dto.EspecialidadDTO;
import dto.TecnicoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import services.TecnicoService;
import util.Dialogos;

public class ControladorABMTecnicos implements Initializable {

	@FXML
	private TableView<TecnicoDTO> tablaTecnicos;

	@FXML
	private TableColumn<?, ?> columnaId;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaDNI;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaNombre;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaApellido;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaLegajo;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaEspecialidades;

	@FXML
	private TableColumn<TecnicoDTO, String> columnaMantis;
	
	@FXML
	private Button btnAgregarTecnico;

	@FXML
	private Button btnModificarTecnico;

	@FXML
	private Button btnEliminarTecnico;

	@FXML
	private TextField txtBusqueda;

	private ObservableList<TecnicoDTO> masterData = FXCollections.observableArrayList();

	@FXML
	void agregarTecnico(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/tecnicos/agregarTecnico.fxml"));
			Scene scene = new Scene(root.load());
			ControladorABMTecnicosAgregar controlador = root.getController();
			controlador.setMasterData(masterData);
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();

			// Eventos del teclado
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ESCAPE) {
						stage.close();
					}
				}
			});
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@FXML
	void eliminarTecnico(MouseEvent event) {
		if (!tablaTecnicos.getSelectionModel().isEmpty()) {
			TecnicoService tecnicoService = new TecnicoService(new DAOSQLFactory());
			if (Dialogos.confirmacion("Alerta", " Desea eliminar el Tecnico?", "", "SI", "NO")) {
				List<EspecialidadDTO> especialidadesDTO = masterData
						.get(tablaTecnicos.getSelectionModel().getSelectedIndex()).getEspecialidades();
				Tecnico tecnicoEliminar = new Tecnico();
				tecnicoEliminar.setIdTecnico(
						masterData.get(tablaTecnicos.getSelectionModel().getSelectedIndex()).getIdTecnico().get());
				for (EspecialidadDTO especialidadDTO : especialidadesDTO) {
					Especialidad especialidad = new Especialidad(especialidadDTO.getIdEspecialidad().get(),
							especialidadDTO.getNombre().get());
					tecnicoService.eliminarTecnicoXEspecialidad(tecnicoEliminar, especialidad);
				}
				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(
						masterData.get(tablaTecnicos.getSelectionModel().getSelectedIndex()).getIdTecnico().get());
				tecnicoService.eliminarTecnico(tecnico);
				masterData.remove(tablaTecnicos.getSelectionModel().getSelectedIndex());
				this.btnModificarTecnico.setDisable(true);
				this.btnEliminarTecnico.setDisable(true);
			}
		} else {
			Notifications.create().title("Atencion").text("Seleccione el Tecnico a Eliminar").darkStyle().showWarning();
		}

	}

	@FXML
	void modificarTecnico(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/tecnicos/modificarTecnico.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);

			ControladorABMTecnicosModificar controlador = root.getController();
			controlador.cargarTecnicoAmodificar(masterData.get(tablaTecnicos.getSelectionModel().getSelectedIndex()));
			controlador.setMasterData(masterData);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();

			// Eventos del teclado
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ESCAPE) {
						stage.close();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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

		// 0. Initialize the columns.
		columnaDNI.setCellValueFactory(cellData -> cellData.getValue().getDni());
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaApellido.setCellValueFactory(cellData -> cellData.getValue().getApellido());
		columnaLegajo.setCellValueFactory(cellData -> cellData.getValue().getLegajo());
		columnaEspecialidades.setCellValueFactory(new PropertyValueFactory<>("especialidades"));
		columnaMantis.setCellValueFactory(cellData -> cellData.getValue().getEtiquetaMantis());

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
				} else if (tecnico.getDni().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (tecnico.getEspecialidadesString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (tecnico.getStringEtiquetaMantis().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (tecnico.getLegajo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<TecnicoDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaTecnicos.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaTecnicos.setItems(sortedData);
		tablaTecnicos.setOnMouseClicked((MouseEvent event) -> {
			if (btnModificarTecnico != null) {
				if (event.getClickCount() > 0 && itemSeleccionado()) {
					btnModificarTecnico.setDisable(false);
					btnEliminarTecnico.setDisable(false);
				} else {
					btnModificarTecnico.setDisable(true);
					btnEliminarTecnico.setDisable(true);
				}
			}
		});

	}
	
	private boolean itemSeleccionado()
	{
		if(tablaTecnicos.getSelectionModel().getSelectedIndex() == -1)
			return false;
		return true;
	}

}
