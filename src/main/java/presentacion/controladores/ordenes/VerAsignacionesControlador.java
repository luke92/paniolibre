package presentacion.controladores.ordenes;

import java.util.List;

import domain.model.OrdenDeTrabajo;
import dto.OrdenDeTrabajoPorTecnicoDTO;
import dto.OrdenTrabajoDTO;
import dto.TecnicoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.OrdenDeTrabajoService;

public class VerAsignacionesControlador {

	@FXML
	private DialogPane ventanaVerAsignaciones;

	@FXML
	private Button btnVovler;

	@FXML
	private TableView<OrdenDeTrabajoPorTecnicoDTO> tablaAsignaciones;

	@FXML
	private TableColumn<OrdenDeTrabajoPorTecnicoDTO, String> columnaID;

	@FXML
	private TableColumn<OrdenDeTrabajoPorTecnicoDTO, String> columnaProyecto;

	@FXML
	private TableColumn<OrdenDeTrabajoPorTecnicoDTO, String> columnaTecnico;

	@FXML
	private TextField textBusqueda;

	private ObservableList<OrdenDeTrabajoPorTecnicoDTO> masterData = FXCollections.observableArrayList();

	private OrdenTrabajoDTO orden;

	@FXML
	void volver(MouseEvent event) {
		Stage stage = (Stage) this.ventanaVerAsignaciones.getScene().getWindow();
		stage.close();
	}

	public void cargarOrden(OrdenTrabajoDTO ordenTrabajoDTO) {
		this.orden = ordenTrabajoDTO;
	}

	public void llenarTabla() {
		OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
		ordenDeTrabajo.setIdOrdenDeTrabajo(orden.getIdOrdenTrabajo().get());
		OrdenDeTrabajoService servivo = new OrdenDeTrabajoService(new DAOSQLFactory());
		int id = servivo.obtenerIdOrden(ordenDeTrabajo);
		ordenDeTrabajo.setId(id);
		List<TecnicoDTO> listaTecnicos = servivo.obtenerTecnicos(ordenDeTrabajo);
		OrdenDeTrabajoPorTecnicoDTO ordenDeTrabajoPorTecnicoDTO = new OrdenDeTrabajoPorTecnicoDTO();
		ordenDeTrabajoPorTecnicoDTO.setIdOrdenDeTrabajo(ordenDeTrabajo.getIdOrdenDeTrabajo());
		ordenDeTrabajoPorTecnicoDTO.setTecnicos(listaTecnicos);
		this.masterData.add(ordenDeTrabajoPorTecnicoDTO);
		columnaID.setCellValueFactory(cellData -> cellData.getValue().getIdOrdenDeTrabajo());
		columnaTecnico.setCellValueFactory(new PropertyValueFactory<>("tecnicos"));
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<OrdenDeTrabajoPorTecnicoDTO> filteredData = new FilteredList<>(masterData, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		textBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tecnico -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (tecnico.getNombreProyecto().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (tecnico.getIdOrdenDeTrabajo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (tecnico.getTecnicos().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false;

			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<OrdenDeTrabajoPorTecnicoDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaAsignaciones.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaAsignaciones.setItems(sortedData);

	}

	public OrdenTrabajoDTO getOrden() {
		return orden;
	}

	public void setOrden(OrdenTrabajoDTO orden) {
		this.orden = orden;
	}

}
