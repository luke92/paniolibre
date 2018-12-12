package presentacion.controladores.insumos;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.InsumoDTO;
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
import services.InsumoService;
import util.Dialogos;

public class ControladorListaInsumosMaestros implements Initializable {

	@FXML
	private DialogPane ventanaVerInsumosMaestros;

	@FXML
	private TableView<InsumoDTO> tablaInsumos;

	@FXML
	private TableColumn<InsumoDTO, String> columnaIdInsumo;

	@FXML
	private TableColumn<InsumoDTO, String> columnaCodigo;

	@FXML
	private TableColumn<InsumoDTO, String> columnaNombre;

	@FXML
	private TableColumn<InsumoDTO, String> columnaMarca;

	@FXML
	private TableColumn<InsumoDTO, String> columnaCategoria;

	@FXML
	private TableColumn<InsumoDTO, String> columnaUnidadMedida;

	@FXML
	private TableColumn<InsumoDTO, String> columnaUmbralMinimo;

	@FXML
	private TableColumn<InsumoDTO, String> columnaComentario;

	@FXML
	private TextField textBusqueda;

	@FXML
	private Button btnSeleccionar;

	private ObservableList<InsumoDTO> masterData = FXCollections.observableArrayList();

	private InsumoService insumoService = new InsumoService(new DAOSQLFactory());
	
	InsumoDTO insumoDTO;

	@FXML
	void seleccionar(MouseEvent event) {
		if (!tablaInsumos.getSelectionModel().isEmpty()) {
			obtenerInsumo();
			cerrarVentana();
		} else {
			Dialogos.error("Seleccione un Insumo", "", "No selecciono ningun Insumo");
		}
	}
	
	private void cerrarVentana() {
		Stage stage = (Stage) ventanaVerInsumosMaestros.getScene().getWindow();
		stage.close();
	}

	public InsumoDTO obtenerInsumo() {
		insumoDTO = insumoSeleccionado();
		return insumoDTO;
	}
	
	private InsumoDTO insumoSeleccionado() {
		return masterData.get(tablaInsumos.getSelectionModel().getSelectedIndex());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		llenarTabla();
	}

	private void llenarTabla() {
		masterData.clear();
		List<InsumoDTO> insumos = insumoService.obtenerInsumosDTO();

		for (int i = 0; i < insumos.size(); i++) {
			masterData.add(insumos.get(i));
		}

		columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().getCodigo());
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaMarca.setCellValueFactory(cellData -> cellData.getValue().getMarca());
		columnaCategoria.setCellValueFactory(cellData -> cellData.getValue().getCategoria().getNombreProperty());
		columnaUnidadMedida.setCellValueFactory(cellData -> cellData.getValue().getUnidadMedida().getNombre());
		columnaComentario.setCellValueFactory(cellData -> cellData.getValue().getComentario());
		columnaUmbralMinimo.setCellValueFactory(cellData -> cellData.getValue().getUmbralMinimo().asString());

		FilteredList<InsumoDTO> filteredData = new FilteredList<>(masterData, p -> true);

		textBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(insumo -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (insumo.getUnidadMedida().getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (insumo.getCategoria().getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (insumo.getMarca().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (insumo.getCodigo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (insumo.getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (insumo.getComentario().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (insumo.getUmbralMinimo().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (insumo.getIdInsumo().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});
		SortedList<InsumoDTO> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablaInsumos.comparatorProperty());
		tablaInsumos.setItems(sortedData);

	}

}
