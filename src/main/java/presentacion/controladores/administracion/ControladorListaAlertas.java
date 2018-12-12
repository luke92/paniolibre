package presentacion.controladores.administracion;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.AlertaDTO;
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
import services.AlertaService;

public class ControladorListaAlertas implements Initializable {

	@FXML
	private DialogPane ventanaVerAlertas;

	@FXML
	private TableView<AlertaDTO> tablaAlertas;

	@FXML
	private TableColumn<AlertaDTO, String> columnaTipoAlerta;

	@FXML
	private TableColumn<AlertaDTO, String> columnaDetalle;

	@FXML
	private TextField textBusqueda;

	@FXML
	private Button btnSalir;

	private ObservableList<AlertaDTO> masterData = FXCollections.observableArrayList();

	private AlertaService service = new AlertaService(new DAOSQLFactory());

	@FXML
	void salir(MouseEvent event) {
		Stage stage = (Stage) ventanaVerAlertas.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		masterData.clear();
		List<AlertaDTO> alertas = service.obtenerAlertas();
		for (int i = 0; i < alertas.size(); i++) {
			masterData.add(alertas.get(i));
		}

		columnaTipoAlerta.setCellValueFactory(cellData -> cellData.getValue().getTipoAlerta());
		columnaDetalle.setCellValueFactory(cellData -> cellData.getValue().getDetalleAlerta());

		FilteredList<AlertaDTO> filteredData = new FilteredList<>(masterData, p -> true);

		textBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(alerta -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (alerta.getTipoAlertaString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (alerta.getDetalleAlertaString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false; // Does not match.
			});
		});
		SortedList<AlertaDTO> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablaAlertas.comparatorProperty());
		tablaAlertas.setItems(sortedData);
	}

}