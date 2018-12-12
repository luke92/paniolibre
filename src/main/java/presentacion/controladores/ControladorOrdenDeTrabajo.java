
package presentacion.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import domain.model.EnumEstadoOrdenTrabajo;
import domain.model.OrdenDeTrabajo;
import dto.OrdenTrabajoDTO;
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
import javafx.scene.control.ScrollPane;
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
import presentacion.controladores.ordenes.ControladorOrdenDeTrabajoAsignar;
import presentacion.controladores.ordenes.VerAsignacionesControlador;
import services.OrdenDeTrabajoService;
import services.TecnicoService;
import util.Dialogos;

public class ControladorOrdenDeTrabajo implements Initializable {

	@FXML
	private ScrollPane panelOrdenDeTrabajo;

	@FXML
	private TableView<OrdenTrabajoDTO> tablaOrdenDeTrabajo;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaID;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaProyecto;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaTipoActividad;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaResumen;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaModuloSede;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaDescripcion;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaFechaInicio;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaEstado;

	@FXML
	private TableColumn<OrdenTrabajoDTO, String> columnaFechaUltimaModificacion;

	@FXML
	private Button btnAsignarOrden;

	@FXML
	private Button btnSuspenderOrden;

	@FXML
	private Button btnCerrarOrden;

	@FXML
	private Button btnReporte;

	@FXML
	private Button btnVerAsignaciones;

	@FXML
	private TextField txtBuscqueda;

	private ObservableList<OrdenTrabajoDTO> masterData = FXCollections.observableArrayList();

	private OrdenDeTrabajoService ordenTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());

	@FXML
	void cerrarOrden(MouseEvent event) {
		if (masterData.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getEstadoOrdenTrabajo()
				.getEstado().get().equals("resuelta")) {
			OrdenDeTrabajoService service = new OrdenDeTrabajoService(new DAOSQLFactory());
			if (!tablaOrdenDeTrabajo.getSelectionModel().isEmpty()) {

				if (Dialogos.confirmacion("Alerta", " Desea dar como Cerrada la OT.?", "", "SI", "NO")) {
					int idMantis = Integer.parseInt(masterData
							.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getIdOrdenTrabajo().get());
					service.cambiarOrdenACerrada(idMantis);
					OrdenDeTrabajo ot = new OrdenDeTrabajo();
					ot.setIdOrdenDeTrabajo(masterData.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex())
							.getIdOrdenTrabajo().get());
					int id = service.obtenerIdOrden(ot);
					ot.setId(id);
					service.cambiarEstadoCerrada(ot);
				}
			}
		} else {
			Dialogos.error("Error", "La Orden de Trabajo no esta Resuelta para poder cerrarla. ",
					" Seleccione una Orden de Trabajo con estado Resuelta. ");
		}
	}

	@FXML
	void suspenderOrden(MouseEvent event) {
		if (masterData.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getEstadoOrdenTrabajo()
				.getEstado().get().equals("asignada")) {
			OrdenDeTrabajoService service = new OrdenDeTrabajoService(new DAOSQLFactory());
			if (!tablaOrdenDeTrabajo.getSelectionModel().isEmpty()) {

				if (Dialogos.confirmacion("Alerta", " Desea dar como Suspendida la OT.?", "", "SI", "NO")) {
					int idMantis = Integer.parseInt(masterData
							.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getIdOrdenTrabajo().get());
					service.cambiarOrdenSuspendida(idMantis);
					OrdenDeTrabajo ot = new OrdenDeTrabajo();
					ot.setIdOrdenDeTrabajo(masterData.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex())
							.getIdOrdenTrabajo().get());
					int id = service.obtenerIdOrden(ot);
					ot.setId(id);
					service.cambiarEstadoSuspendida(ot);
				}
			}
		} else {
			Dialogos.error("Error", "La Orden de Trabajo no esta Asignada para poder Suspenderla. ",
					" Seleccione una Orden de Trabajo con estado Asignada. ");
		}
	}

	@FXML
	void verAsignaciones(MouseEvent event) {
		if (masterData.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getEstadoOrdenTrabajo()
				.getEstado().get().equals("asignada")|| masterData.get(this.tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getEstadoOrdenTrabajo()
				.getEstado().get().equals("cerrada") || masterData.get(this.tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getEstadoOrdenTrabajo()
		.getEstado().get().equals("resuelta")) {
			FXMLLoader root;
			try {
				root = new FXMLLoader(getClass().getResource("/vistas/ordenesdetrabajo/dialog_ver_asignaciones.fxml"));
				Scene scene = new Scene(root.load());
				scene.setFill(Color.TRANSPARENT);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.TRANSPARENT);
				VerAsignacionesControlador controlador = root.getController();
				controlador.cargarOrden(masterData.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()));
				controlador.llenarTabla();
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
			Dialogos.error("Error", "OT sin Tecnicos Asignados",
					" Seleccione una Orden de Trabajo con Tecnicos Asignados ");
		}
	}

	@FXML
	void asignarOrdenDeTrabajo(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/ordenesdetrabajo/asiganarordendetrabajo.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			ControladorOrdenDeTrabajoAsignar controlador = root.getController();
			controlador.cargarOrden(masterData.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			this.llenarTabla();
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
	void reporte(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/ordenesdetrabajo/reporteOrdenesDeTrabajo.fxml"));
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

	void llenarTabla() {
		masterData.clear();
		// List<OrdenTrabajoDTO> ordenes =
		// ordenTrabajoService.obtenerOrdenDeTrabajosDTO();
		// for (OrdenTrabajoDTO ordenDeTrabajo : ordenes) {
		// masterData.add(ordenDeTrabajo);
		// }
		List<OrdenTrabajoDTO> ordenes = ordenTrabajoService.obtenerOrdenDeTrabajosMantis();

		for (OrdenTrabajoDTO ordenDeTrabajo : ordenes) {
			masterData.add(ordenDeTrabajo);
		}
		columnaID.setCellValueFactory(cellData -> cellData.getValue().getIdOrdenTrabajo());
		columnaModuloSede.setCellValueFactory(cellData -> cellData.getValue().getModuloSede());
		columnaTipoActividad.setCellValueFactory(cellData -> cellData.getValue().getTipoActividad().getNombre());
		columnaProyecto.setCellValueFactory(cellData -> cellData.getValue().getProyecto().getNombre());
		columnaResumen.setCellValueFactory(cellData -> cellData.getValue().getResumen());
		columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().getDescripcion());
		columnaFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
		columnaEstado.setCellValueFactory(cellData -> cellData.getValue().getEstadoOrdenTrabajo().getEstado());
		columnaFechaUltimaModificacion.setCellValueFactory(new PropertyValueFactory<>("fechaUltimaModificacion"));

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<OrdenTrabajoDTO> filteredData = new FilteredList<>(masterData, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		txtBuscqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(ordenTrabajo -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (ordenTrabajo.getIdOrdenTrabajo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (ordenTrabajo.getEstadoOrdenTrabajo().getEstado().get().toLowerCase()
						.indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.

				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<OrdenTrabajoDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaOrdenDeTrabajo.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaOrdenDeTrabajo.setItems(sortedData);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.llenarTabla();
		// Evento para seleccion de filas en tabla de insumos
				tablaOrdenDeTrabajo.setOnMouseClicked((MouseEvent event) -> {
					if (masterData.get(this.tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getEstadoOrdenTrabajo()
							.getEstado().get().equals("asignada") || masterData.get(this.tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getEstadoOrdenTrabajo()
							.getEstado().get().equals("cerrada") || masterData.get(this.tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getEstadoOrdenTrabajo()
					.getEstado().get().equals("resuelta")){
						btnAsignarOrden.setDisable(true);
					} else {
						btnAsignarOrden.setDisable(false);
					}
					if (masterData.get(this.tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex()).getEstadoOrdenTrabajo()
							.getEstado().get().equals("nueva")) {
						btnCerrarOrden.setDisable(true);
						btnSuspenderOrden.setDisable(true);
					}else {
						btnCerrarOrden.setDisable(false);
						btnSuspenderOrden.setDisable(false);
					}
				});
	}

}