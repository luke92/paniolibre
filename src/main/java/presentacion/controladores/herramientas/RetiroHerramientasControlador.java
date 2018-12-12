package presentacion.controladores.herramientas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import domain.model.Herramienta;
import dto.CategoriaHerramientaDTO;
import dto.EnumEstadoHerramientaDTO;
import dto.HerramientaDTO;
import dto.UbicacionDepositoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import persistencia.dao.mysql.DAOSQLFactory;
import services.CategoriaHerramientaService;
import services.HerramientaService;
import util.Fechas;

public class RetiroHerramientasControlador implements Initializable {

	@FXML
	AbmRetiroHerramientaControlador panelABMRetirosHerramientasController;

	@FXML
	private VBox VboxPanelTablas;

	@FXML
	private BorderPane bordelPanelRetiro;

	@FXML
	private BorderPane panelHerramientasReparacion;

	@FXML
	private TableView<HerramientaDTO> tablaHerramientas;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCodigo;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaNombre;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCategoria;

	@FXML
	private TextField txtBusqueda;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnDevolver;

	@FXML
	private BorderPane panelHerramientasReparacion1;

	@FXML
	private TableView<HerramientaDTO> tablaHerramientas1;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCodigo1;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaNombre1;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCategoria1;

	@FXML
	private TextField txtBusqueda1;

	@FXML
	private Button btnPrestar;

	public VBox getVboxPanelTablas() {
		return VboxPanelTablas;
	}

	public BorderPane getBordelPanelRetiro() {
		return bordelPanelRetiro;
	}

	public void setBordelPanelRetiro(BorderPane bordelPanelRetiro) {
		this.bordelPanelRetiro = bordelPanelRetiro;
	}

	private ObservableList<HerramientaDTO> masterData = FXCollections.observableArrayList();

	private ObservableList<HerramientaDTO> masterData1 = FXCollections.observableArrayList();

	@FXML
	void enviar(MouseEvent event) {
		HerramientaDTO herramienta = tablaHerramientas.getSelectionModel().getSelectedItem();
		if (this.masterData1.size() == 1) {
			this.btnPrestar.setDisable(true);
		} else {
			this.btnPrestar.setDisable(false);
		}
		if (herramienta != null) {
			transferenciaTabla(masterData, masterData1, tablaHerramientas);
			btnEnviar.setDisable(true);
			this.btnPrestar.setDisable(false);
		}
	}

	@FXML
	void devolver(MouseEvent event) {
		HerramientaDTO herramienta = tablaHerramientas1.getSelectionModel().getSelectedItem();
		if (this.masterData1.size() == 1) {
			this.btnDevolver.setDisable(true);
			this.btnPrestar.setDisable(true);
		} else {
			this.btnPrestar.setDisable(false);
		}
		if (herramienta != null) {
			transferenciaTabla(masterData1, masterData, tablaHerramientas1);
		}
	}

	@FXML
	void prestar(MouseEvent event) {
//		this.VboxPanelTablas.setDisable(true);
		this.panelABMRetirosHerramientasController.setListaPrestamo(masterData1);
		this.bordelPanelRetiro.setLeft(this.panelABMRetirosHerramientasController.getPanelABMRetiroHerramienta());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.bordelPanelRetiro.setLeft(null);
		this.panelABMRetirosHerramientasController.setControladorPadre(this);
		HerramientaService servicio = new HerramientaService(new DAOSQLFactory());
		CategoriaHerramientaService categoriaHerramientaService = new CategoriaHerramientaService(new DAOSQLFactory());
		List<Herramienta> herramientaDisponibles = servicio.obtenerHerramientaDisponibles();
		for (Herramienta herramienta : herramientaDisponibles) {
			UbicacionDepositoDTO ubicacion = new UbicacionDepositoDTO();
			ubicacion.setIdUbicacionDeposito(herramienta.getUbicacion().getIdUbicacionDeposito());
			CategoriaHerramientaDTO categoria = new CategoriaHerramientaDTO();
			categoria.setIdCategoria(herramienta.getCategoria().getIdCategoria());
			categoria.setNombre(
					categoriaHerramientaService.obtenerNombreCategoriaHerramienta(herramienta.getCategoria()));
			HerramientaDTO herramientaDTO = new HerramientaDTO(herramienta.getIdHerramienta(), herramienta.getCodigo(),
					herramienta.getNombre(), ubicacion, categoria, herramienta.getFactura(),
					herramienta.getNumeroActivo(), herramienta.getComentario(), herramienta.getMecanismoAdquisicion(),
					Fechas.CalendarTolocalDate(herramienta.getFechaAdquisicion()),
					Fechas.CalendarTolocalDate(herramienta.getFechaGarantiaExpiracion()),
					Fechas.CalendarTolocalDate(herramienta.getFechaUltimaModificacion()),
					obtenerEstado(herramienta.getEstado().getValor()), herramienta.getActivo(),
					herramienta.getProveedor(), herramienta.getMarca(), true);

			this.masterData.add(herramientaDTO);
			inicilizarTabla();
			this.inicilizarTabla1();
		}
	}

	private void inicilizarTabla() {
		columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().getCodigo());
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaCategoria.setCellValueFactory(cellData -> cellData.getValue().getCategoria().getNombre());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<HerramientaDTO> filteredData = new FilteredList<>(masterData, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(herramienta -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (herramienta.getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (herramienta.getCodigo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<HerramientaDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaHerramientas.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaHerramientas.setItems(sortedData);

		// Evento para seleccion de filas en tabla de herramientas a prestar
		tablaHerramientas.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldValue, newValue) -> {
					if (tablaHerramientas.getSelectionModel().getSelectedItem() != null) {
						btnEnviar.setDisable(false);
					} else {
						btnEnviar.setDisable(true);
					}
				});
	}

	private void inicilizarTabla1() {
		columnaCodigo1.setCellValueFactory(cellData -> cellData.getValue().getCodigo());
		columnaNombre1.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaCategoria1.setCellValueFactory(cellData -> cellData.getValue().getCategoria().getNombre());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<HerramientaDTO> filteredData1 = new FilteredList<>(masterData1, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		txtBusqueda1.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData1.setPredicate(herramienta -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (herramienta.getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (herramienta.getCodigo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<HerramientaDTO> sortedData1 = new SortedList<>(filteredData1);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData1.comparatorProperty().bind(tablaHerramientas1.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaHerramientas1.setItems(sortedData1);

		// Evento para seleccion de filas en tabla de herramientas a devolver
		tablaHerramientas1.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldValue, newValue) -> {
					if (tablaHerramientas1.getSelectionModel().getSelectedItem() != null) {
						btnDevolver.setDisable(false);
					} else {
						btnDevolver.setDisable(true);
					}
				});
	}

	private void transferenciaTabla(ObservableList<HerramientaDTO> baseRemover,
			ObservableList<HerramientaDTO> baseAdicionar, TableView<HerramientaDTO> TablaRemover) {

		HerramientaDTO herramienta = TablaRemover.getSelectionModel().getSelectedItem();
		TablaRemover.getSelectionModel().clearSelection();
		baseRemover.remove(herramienta);
		baseAdicionar.add(herramienta);

	}

	public EnumEstadoHerramientaDTO obtenerEstado(int estado) {
		if (estado == 1)
			return EnumEstadoHerramientaDTO.DISPONIBLE;
		if (estado == 2)
			return EnumEstadoHerramientaDTO.PRESTADO;
		if (estado == 3)
			return EnumEstadoHerramientaDTO.AVERIADA;
		if (estado == 4)
			return EnumEstadoHerramientaDTO.EN_REPARACION;
		return null;
	}

	public ObservableList<HerramientaDTO> getMasterData1() {
		return masterData1;
	}

	public void setMasterData1(ObservableList<HerramientaDTO> masterData1) {
		this.masterData1 = masterData1;
	}

}
