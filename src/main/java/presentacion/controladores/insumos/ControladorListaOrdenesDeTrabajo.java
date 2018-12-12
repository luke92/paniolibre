package presentacion.controladores.insumos;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.OrdenTrabajoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.OrdenDeTrabajoService;
import util.Dialogos;

public class ControladorListaOrdenesDeTrabajo implements Initializable {
	@FXML
	private DialogPane ventanaVerOrdenesDeTrabajo;

	@FXML
	private TableView<OrdenTrabajoDTO> tablaOrdenes;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaIDMantis;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaProyecto;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaResumen;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaDescripcion;

	@FXML
	private TextField textBusqueda;

	@FXML
	private Button btnSeleccionar;

	private ObservableList<OrdenTrabajoDTO> masterData = FXCollections.observableArrayList();

	private OrdenDeTrabajoService ordenTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());

	OrdenTrabajoDTO orden;

	ControladorIngresoInsumo controlador;

	@FXML
	void seleccionar(MouseEvent event) {
		if (!tablaOrdenes.getSelectionModel().isEmpty()) {
			obtenerOrden();
			Stage stage = (Stage) ventanaVerOrdenesDeTrabajo.getScene().getWindow();
			stage.close();
		} else {
			Dialogos.error("Seleccione una Orden de Trabajo", "", "No selecciono ninguna Orden de Trabajo ");
		}
	}

	public OrdenTrabajoDTO obtenerOrden() {
		orden = new OrdenTrabajoDTO();
		orden.setIdOrdenTrabajo(
				masterData.get(tablaOrdenes.getSelectionModel().getSelectedIndex()).getIdOrdenTrabajo().get());
		orden.setId(masterData.get(tablaOrdenes.getSelectionModel().getSelectedIndex()).getId().get());
		return orden;

	}

	@SuppressWarnings("static-access")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		masterData.clear();
		List<OrdenTrabajoDTO> ordenes = ordenTrabajoService.obtenerOrdenDeTrabajosDTO();
		for (OrdenTrabajoDTO ordenDeTrabajo : ordenes) {
			masterData.add(ordenDeTrabajo);
		}
		columnaIDMantis.setCellValueFactory(cellData -> cellData.getValue().getIdOrdenTrabajo());
		columnaResumen.setCellValueFactory(cellData -> cellData.getValue().getResumen());
		columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().getDescripcion());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<OrdenTrabajoDTO> filteredData = new FilteredList<>(masterData, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		textBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(ordenTrabajo -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (ordenTrabajo.getIdOrdenTrabajo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (ordenTrabajo.getProyecto().getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.

				}
				return false; // Does not match.
			});
		});
		SortedList<OrdenTrabajoDTO> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablaOrdenes.comparatorProperty());
		tablaOrdenes.setItems(sortedData);

	}

	public Button getBtnSeleccionar() {
		return btnSeleccionar;
	}

	public void setBtnSeleccionar(Button btnSeleccionar) {
		this.btnSeleccionar = btnSeleccionar;
	}

}
