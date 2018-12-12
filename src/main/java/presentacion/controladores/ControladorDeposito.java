package presentacion.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import domain.model.Deposito;
import dto.DepositoDTO;
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
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AdministracionService;
import services.DepositoService;
import util.Dialogos;

public class ControladorDeposito implements Initializable {
	@FXML
	private ScrollPane panelDeposito;
	@FXML
	private TableView<DepositoDTO> tablaDeposito;
	@FXML
	private TableColumn<DepositoDTO, String> columnaId;
	@FXML
	private TableColumn<DepositoDTO, String> columnaNombre;
	@FXML
	private TableColumn<DepositoDTO, String> columnaComentario;
	@FXML
	private Button btnAgregarDeposito;
	@FXML
	private Button btnEliminarDeposito;
	@FXML
	private Button btnEditarDeposito;

	private ObservableList<DepositoDTO> masterData = FXCollections.observableArrayList();
	private DepositoService depositoService = new DepositoService(new DAOSQLFactory());

	@FXML
	void agregarDeposito(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/agregardeposito.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			ControladorDepositoABM controlador = root.getController();
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

	@FXML
	void borrarDeposito(MouseEvent event) {
		if (!tablaDeposito.getSelectionModel().isEmpty()) {
			AdministracionService administracionService = new AdministracionService(new DAOSQLFactory());
			if (Dialogos.confirmacion("Alerta", "¿Desea eliminar el Deposito?", "", "SI", "NO")) {
				Deposito deposito = new Deposito();
				deposito.setIdDeposito(
						masterData.get(tablaDeposito.getSelectionModel().getSelectedIndex()).getIdDeposito().get());
				administracionService.eliminarDeposito(deposito);
				masterData.remove(tablaDeposito.getSelectionModel().getSelectedIndex());
				this.btnEditarDeposito.setDisable(true);
				this.btnEliminarDeposito.setDisable(true);
			}
		} else {
			Notifications.create().title("Atencion").text("Seleccione el Deposito a Eliminar").darkStyle()
					.showWarning();

		}
	}

	@FXML
	void editarDeposito(MouseEvent event) {
		if (!tablaDeposito.getSelectionModel().isEmpty()) {
			FXMLLoader root;
			try {
				root = new FXMLLoader(getClass().getResource("/vistas/editardeposito.fxml"));
				Scene scene = new Scene(root.load());
				scene.setFill(Color.TRANSPARENT);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.TRANSPARENT);

				ControladorDepositoABM controlador = root.getController();
				controlador
						.cargarDepositoAmodificar(masterData.get(tablaDeposito.getSelectionModel().getSelectedIndex()));
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
		} else {
			Notifications.create().title("Atencion").text("Seleccione el Deposito a Modificar").darkStyle()
					.showWarning();

		}
	}

	public void llenarTabla() {
		List<DepositoDTO> depositos = depositoService.obtenerDepositosDTO();

		for (DepositoDTO deposito : depositos) {
			this.masterData.add(deposito);
		}

		this.tablaDeposito.setItems(masterData);

		// 0. Initialize the columns.
		columnaId.setCellValueFactory(cellData -> cellData.getValue().getIdDeposito().asString());
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaComentario.setCellValueFactory(cellData -> cellData.getValue().getComentario());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<DepositoDTO> filteredData = new FilteredList<>(masterData, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		// TODO Revisar si es necesario este paso en la tabla de Depositos

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<DepositoDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaDeposito.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaDeposito.setItems(sortedData);
	}

	// VENTANA AGREGAR Deposito
	@FXML
	private DialogPane ventanaAgregarDeposito;
	@FXML
	private DialogPane ventanaEditarDeposito;
	@FXML
	private Button btnConfirmarAgregarDeposito;
	@FXML
	private Button btnConfirmarEditarDeposito;
	@FXML
	private Button btnDescartarDeposito;
	@FXML
	private TextField textNombre;
	@FXML
	private TextField textComentario;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		llenarTabla();
		this.btnEditarDeposito.setDisable(true);
		this.btnEliminarDeposito.setDisable(true);
		tablaDeposito.setOnMouseClicked((MouseEvent event) -> {
			if (btnEditarDeposito != null) {
				if (event.getClickCount() > 0) {
					btnEditarDeposito.setDisable(false);
					btnEliminarDeposito.setDisable(false);
				} else {
					btnEditarDeposito.setDisable(true);
					btnEliminarDeposito.setDisable(false);
				}
			}
		});
	}

	@FXML
	void descartarDeposito(MouseEvent event) {
		Stage stage = (Stage) this.ventanaAgregarDeposito.getScene().getWindow();
		stage.close();
	}
}
