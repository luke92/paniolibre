package presentacion.controladores.insumos;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.UbicacionDepositoDTO;
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
import services.AdministracionService;
import util.Dialogos;

public class ControladorListaUbicaciones implements Initializable {

	@FXML
	private DialogPane ventanaVerUbicaciones;

	@FXML
	private TableView<UbicacionDepositoDTO> tablaUbicaciones;

	@FXML
	private TableColumn<UbicacionDepositoDTO, String> columnaUbicacion;

	@FXML
	private TableColumn<UbicacionDepositoDTO, String> columnaDeposito;

	@FXML
	private TextField textBusqueda;

	@FXML
	private Button btnSeleccionar;

	private ObservableList<UbicacionDepositoDTO> masterData = FXCollections.observableArrayList();

	private AdministracionService administracionService = new AdministracionService(new DAOSQLFactory());

	UbicacionDepositoDTO ubicacionDTO;

	@FXML
	void seleccionar(MouseEvent event) {
		if (!tablaUbicaciones.getSelectionModel().isEmpty()) {
			obtenerUbicacion();
			cerrarVentana();
		} else {
			Dialogos.error("Seleccione una Ubicacion", "", "No selecciono ninguna Ubicacion");
		}
	}

	public UbicacionDepositoDTO obtenerUbicacion() {
		ubicacionDTO = ubicacionSeleccionada();
		return ubicacionDTO;

	}

	private UbicacionDepositoDTO ubicacionSeleccionada() {
		return masterData.get(tablaUbicaciones.getSelectionModel().getSelectedIndex());
	}

	private void cerrarVentana() {
		Stage stage = (Stage) ventanaVerUbicaciones.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		llenarTabla();
	}

	private void llenarTabla() {
		masterData.clear();
		List<UbicacionDepositoDTO> ubicaciones = administracionService.obtenerUbicacionDepositosDTO();
		for (int i = 0; i < ubicaciones.size(); i++) {
			masterData.add(ubicaciones.get(i));
		}

		columnaUbicacion.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaDeposito.setCellValueFactory(cellData -> cellData.getValue().getDeposito().getNombre());

		FilteredList<UbicacionDepositoDTO> filteredData = new FilteredList<>(masterData, p -> true);

		textBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(ubicacion -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (ubicacion.getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (ubicacion.getDeposito().getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false; // Does not match.
			});
		});
		SortedList<UbicacionDepositoDTO> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablaUbicaciones.comparatorProperty());
		tablaUbicaciones.setItems(sortedData);

	}

}