package presentacion.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.validation.ValidationSupport;

import domain.model.Insumo;
import dto.InsumoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controladores.insumos.VentanaIngresarInsumoControlador;
import services.InsumoDepositoService;
import services.InsumoService;
import util.Dialogos;
import util.Spinners;

public class ControladorABMinsumos {
	InsumoService insumoService = new InsumoService(new DAOSQLFactory());
	List<InsumoDTO> insumos = new ArrayList<>();

	// PANEL INSUMO MAESTRO //

	@FXML
	private ScrollPane panelInsumoMaestro;

	@FXML
	private TableView<InsumoDTO> tablaInsumos;

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
	private Text lblCategoriaActual;

	@FXML
	private Button btnAgregarInsumo;

	@FXML
	private Button btnModificarInsumo;

	@FXML
	private Button btnEliminarInsumo;

	@FXML
	private Button btnReporte;

	@FXML
	private TextField txtBusqueda;

	private ObservableList<InsumoDTO> masterData = FXCollections.observableArrayList();

	@FXML
	private Spinner<Integer> spnUmbralMinimo;

	ValidationSupport validationSupport = new ValidationSupport();

	// METODOS //
	@FXML
	void reporte(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/insumos/reporteInsumo.fxml"));
			Scene scene = new Scene(root.load());
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
	void agregarInsumo(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/agregarinsumomaestro2.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			VentanaIngresarInsumoControlador controlador = root.getController();
			controlador.vincularArreglos(masterData);
			controlador.cargarSpinnerUmbralMinimo();
			controlador.llenarTreeView();
			controlador.cargarChoiceBoxUnidadMedida();
			stage.initModality(Modality.APPLICATION_MODAL);
			// Eventos del teclado
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ESCAPE) {
						stage.close();
					}
				}
			});
			stage.showAndWait();
			this.llenarTablaInsumos();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cargarSpinnerUmbralMinimo() {
		new Spinners().setValoresSpinner(spnUmbralMinimo, 0, 99999999, 0);
	}

	@FXML
	void eliminarInsumo(MouseEvent event) {
		// retorna true si el usuario hizo click en SI
		Insumo insumo = new Insumo();
		insumo.setIdInsumo(masterData.get(tablaInsumos.getSelectionModel().getSelectedIndex()).getIdInsumo().get());
		InsumoDepositoService insumoDepositoService = new InsumoDepositoService(new DAOSQLFactory());
		if (insumoDepositoService.existeInsumo(insumo.getIdInsumo())) {
			Dialogos.error("Error", "Error al borrar Insumo",
					"El insumo seleccionado existe en otras secciones del sistema");
		} else if (Dialogos.confirmacion("Alerta", "Desea eliminar este insumo?", "", "SI", "NO")) {
			insumoService.eliminarInsumo(insumo);
			masterData.remove(tablaInsumos.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	void modificarInsumo(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/modificarinsumomaestro2.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);

			VentanaIngresarInsumoControlador controlador = root.getController();
			controlador.cargarInsumoAmodificar(masterData.get(tablaInsumos.getSelectionModel().getSelectedIndex()));

			stage.initModality(Modality.APPLICATION_MODAL);
			// Eventos del teclado
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ESCAPE) {
						stage.close();
					}
				}
			});
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void llenarTablaInsumos() {

		masterData.clear();
		this.insumos = insumoService.obtenerInsumosDTO();

		for (int i = 0; i < this.insumos.size(); i++) {
			masterData.add(insumos.get(i));
		}

		// 0. Initialize the columns.
		columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().getCodigo());
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaMarca.setCellValueFactory(cellData -> cellData.getValue().getMarca());
		columnaCategoria.setCellValueFactory(cellData -> cellData.getValue().getCategoria().getNombreProperty());
		columnaUnidadMedida.setCellValueFactory(cellData -> cellData.getValue().getUnidadMedida().getNombre());
		columnaComentario.setCellValueFactory(cellData -> cellData.getValue().getComentario());
		columnaUmbralMinimo.setCellValueFactory(cellData -> cellData.getValue().getUmbralMinimo().asString());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<InsumoDTO> filteredData = new FilteredList<>(masterData, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(insumo -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
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

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<InsumoDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaInsumos.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaInsumos.setItems(sortedData);

		// Evento para seleccion de filas en tabla de insumos
		tablaInsumos.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (tablaInsumos.getSelectionModel().getSelectedItem() != null) {
				btnModificarInsumo.setDisable(false);
				btnEliminarInsumo.setDisable(false);
			} else {
				btnModificarInsumo.setDisable(true);
				btnEliminarInsumo.setDisable(true);
			}
		});
	}

}