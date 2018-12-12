package presentacion.controladores.herramientas;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Herramienta;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroHerramienta;
import domain.model.Tecnico;
import domain.model.Usuario;
import domain.model.UsuarioLogueado;
import dto.CategoriaHerramientaDTO;
import dto.EnumEstadoHerramientaDTO;
import dto.HerramientaDTO;
import dto.OrdenTrabajoDTO;
import dto.TecnicoDTO;
import dto.UbicacionDepositoDTO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controladores.insumos.ControladorListaOrdenesDeTrabajo;
import services.CategoriaHerramientaService;
import services.HerramientaService;
import services.OrdenDeTrabajoService;
import services.RetiroHerramientaService;
import services.TecnicoService;
import util.Fechas;

public class DevolucionesControlador implements Initializable {

	@FXML
	private BorderPane bordelPanelDevolucion;

	@FXML
	private DevolucionesHerramientasAbmControlador panelDevolucionAbmHerramientasController;

	@FXML
	private VBox vboxPanelTablas;

	@FXML
	private TableView<HerramientaDTO> tablaHerramientas;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCodigo;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaNombre;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCategoria;

	@FXML
	private ComboBox<TecnicoDTO> comboTecnico;

	@FXML
	private TextField textNumOrdenTrabajo;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnObtnerOrdenes;

	@FXML
	private TextField txtBusqueda;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnDevolver;

	@FXML
	private TableView<HerramientaDTO> tablaHerramientas1;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCodigo1;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaNombre1;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCategoria1;

	@FXML
	private TableColumn<HerramientaDTO, Boolean> columnaDisponible;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaAveriada;

	@FXML
	private Button btnAceptar;

	public VBox getVboxPanelTablas() {
		return vboxPanelTablas;
	}

	public BorderPane getBordelPanelRetiro() {
		return bordelPanelDevolucion;
	}

	public void setBordelPanelRetiro(BorderPane bordelPanelDevoluciones) {
		this.bordelPanelDevolucion = bordelPanelDevoluciones;
	}

	private ObservableList<HerramientaDTO> masterData = FXCollections.observableArrayList();

	private ObservableList<HerramientaDTO> masterData1 = FXCollections.observableArrayList();

	private TecnicoDTO tecnicoDTO;

	private OrdenTrabajoDTO orden;

	public void ocultarPanel() {
		this.bordelPanelDevolucion.setLeft(null);
	}

	public OrdenTrabajoDTO obtenerorden() {
		return orden;
	}

	public TecnicoDTO obtenerTecnico() {
		tecnicoDTO = comboTecnico.getValue();
		return tecnicoDTO;
	}

	@FXML
	void obtenerOrdenes() {
		orden = new OrdenTrabajoDTO();
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/insumos/verListaOrdenes.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
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
			ControladorListaOrdenesDeTrabajo controlador = root.getController();
			orden = controlador.obtenerOrden();
			this.textNumOrdenTrabajo.setText(orden.getIdOrdenTrabajo().get());

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void devolver(MouseEvent event) {
		HerramientaDTO herramienta = tablaHerramientas1.getSelectionModel().getSelectedItem();

		if (this.masterData1.size() == 1) {
			this.btnDevolver.setDisable(true);
			this.btnAceptar.setDisable(true);
		} else {
			this.btnAceptar.setDisable(false);
		}

		if (herramienta != null) {
			transferenciaTabla(masterData1, masterData, tablaHerramientas1);
		}
	}

	@FXML
	void enviar(MouseEvent event) {
		HerramientaDTO herramienta = tablaHerramientas.getSelectionModel().getSelectedItem();

		if (this.masterData1.size() == 1) {
			this.btnAceptar.setDisable(true);
		} else {
			this.btnAceptar.setDisable(false);
		}

		if (herramienta != null) {
			transferenciaTabla(masterData, masterData1, tablaHerramientas);
		}
	}

	@FXML
	void llenarTabla(MouseEvent event) {
		masterData.clear();
		UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstancia();
		Usuario usuario = usuarioLogueado.getUsuarioLogueado();
		this.panelDevolucionAbmHerramientasController.setTexUsuario(usuario.getUserName());
		RetiroHerramientaService servicio = new RetiroHerramientaService(new DAOSQLFactory());
		HerramientaService herramientaService = new HerramientaService(new DAOSQLFactory());
		CategoriaHerramientaService categoriaHerramientaService = new CategoriaHerramientaService(new DAOSQLFactory());
		if (textNumOrdenTrabajo.getText().equals("")) {
			if (comboTecnico.getSelectionModel().isEmpty()) {
				ValidationSupport validationSupport = new ValidationSupport();
				validationSupport.registerValidator(comboTecnico, Validator.createEmptyValidator("Campo Requerido"));
				Notifications.create().title("Atencion").text("Seleccione un Tecnico.").darkStyle().showWarning();
			}
			this.panelDevolucionAbmHerramientasController.setDateFechaDevolucion(LocalDate.now());
			this.panelDevolucionAbmHerramientasController.setTexTecnico(comboTecnico.getValue().getNombre().get());
			Tecnico tecnico = new Tecnico();
			tecnico.setIdTecnico(comboTecnico.getValue().getIdTecnico().get());
			List<RetiroHerramienta> retiroDeTecnico = servicio.obtenerRetiroPorTecnicos(tecnico);
			for (RetiroHerramienta retiroHerramienta : retiroDeTecnico) {
				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(retiroHerramienta.getHerramienta().getIdHerramienta());
				Herramienta herramientaMaestro = herramientaService.obtenerHerramientaMaestro(herramienta);
				UbicacionDepositoDTO ubicacion = new UbicacionDepositoDTO();
				ubicacion.setIdUbicacionDeposito(herramientaMaestro.getUbicacion().getIdUbicacionDeposito());
				CategoriaHerramientaDTO categoria = new CategoriaHerramientaDTO();
				categoria.setIdCategoria(herramientaMaestro.getCategoria().getIdCategoria());
				categoria.setNombre(categoriaHerramientaService
						.obtenerNombreCategoriaHerramienta(herramientaMaestro.getCategoria()));

				HerramientaDTO herramientaDTO = new HerramientaDTO(herramientaMaestro.getIdHerramienta(),
						herramientaMaestro.getCodigo(), herramientaMaestro.getNombre(), ubicacion, categoria,
						herramientaMaestro.getFactura(), herramientaMaestro.getNumeroActivo(),
						herramientaMaestro.getComentario(), herramientaMaestro.getMecanismoAdquisicion(),
						Fechas.CalendarTolocalDate(herramientaMaestro.getFechaAdquisicion()),
						Fechas.CalendarTolocalDate(herramientaMaestro.getFechaGarantiaExpiracion()),
						Fechas.CalendarTolocalDate(herramientaMaestro.getFechaUltimaModificacion()),
						obtenerEstado(herramientaMaestro.getEstado().getValor()), herramientaMaestro.getActivo(),
						herramientaMaestro.getProveedor(), herramientaMaestro.getMarca(), false);

				this.masterData.add(herramientaDTO);

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

		} else {
			this.panelDevolucionAbmHerramientasController.setDateFechaDevolucion(LocalDate.now());
			this.panelDevolucionAbmHerramientasController.setTexOrdenDeTrabajo(textNumOrdenTrabajo.getText());
			OrdenDeTrabajo orden = new OrdenDeTrabajo();
			OrdenDeTrabajoService ordenDeTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());
			orden.setIdOrdenDeTrabajo(textNumOrdenTrabajo.getText());
			int id = ordenDeTrabajoService.obtenerIdOrden(orden);
			orden.setId(id);
			this.comboTecnico.getItems().clear();
			List<TecnicoDTO> tecnicos = ordenDeTrabajoService.obtenerTecnicos(orden);
			for (TecnicoDTO tecnicoDTO : tecnicos) {
				this.comboTecnico.getItems().add(tecnicoDTO);
			}
			List<RetiroHerramienta> retiroPorOrden = servicio.obtenerReiroPorOrdenDeTrabajo(orden);
			for (RetiroHerramienta retiroHerramienta2 : retiroPorOrden) {

				Herramienta herramienta = new Herramienta();
				herramienta.setIdHerramienta(retiroHerramienta2.getHerramienta().getIdHerramienta());
				Herramienta herramientaMaestro = herramientaService.obtenerHerramientaMaestro(herramienta);
				UbicacionDepositoDTO ubicacion = new UbicacionDepositoDTO();
				ubicacion.setIdUbicacionDeposito(herramientaMaestro.getUbicacion().getIdUbicacionDeposito());
				CategoriaHerramientaDTO categoria = new CategoriaHerramientaDTO();
				categoria.setIdCategoria(herramientaMaestro.getCategoria().getIdCategoria());
				categoria.setNombre(categoriaHerramientaService
						.obtenerNombreCategoriaHerramienta(herramientaMaestro.getCategoria()));

				HerramientaDTO herramientaDTO = new HerramientaDTO(herramientaMaestro.getIdHerramienta(),
						herramientaMaestro.getCodigo(), herramientaMaestro.getNombre(), ubicacion, categoria,
						herramientaMaestro.getFactura(), herramientaMaestro.getNumeroActivo(),
						herramientaMaestro.getComentario(), herramientaMaestro.getMecanismoAdquisicion(),
						Fechas.CalendarTolocalDate(herramientaMaestro.getFechaAdquisicion()),
						Fechas.CalendarTolocalDate(herramientaMaestro.getFechaGarantiaExpiracion()),
						Fechas.CalendarTolocalDate(herramientaMaestro.getFechaUltimaModificacion()),
						obtenerEstado(herramientaMaestro.getEstado().getValor()), herramientaMaestro.getActivo(),
						herramientaMaestro.getProveedor(), herramientaMaestro.getMarca(), false);

				this.masterData.add(herramientaDTO);
			}
		}
	}

	@FXML
	void aceptarDevolucion(MouseEvent event) {
		if (comboTecnico.getSelectionModel().isEmpty()) {
			ValidationSupport validationSupport = new ValidationSupport();
			validationSupport.registerValidator(comboTecnico, Validator.createEmptyValidator("Campo Requerido"));
			Notifications.create().title("Atencion").text("Seleccione un Tecnico.").darkStyle().showWarning();
		}else {
		ObservableList<HerramientaDTO> masterDataHerramientas = FXCollections.observableArrayList();
		masterDataHerramientas.addAll(masterData1);
		this.panelDevolucionAbmHerramientasController.setListaPrestamo(masterDataHerramientas);
		this.bordelPanelDevolucion.setLeft(panelDevolucionAbmHerramientasController.getPanelABMDevoluciones());
		this.panelDevolucionAbmHerramientasController.setTexTecnico(comboTecnico.getValue().getNombre().get());
		this.obtenerTecnico();
		this.obtenerorden();
		this.masterData.clear();
		this.masterData1.clear();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ESCONDO EL PANEL ABM Y CARGO EL PADRE EL CONTROLADOR DE ABM
		this.ocultarPanel();
		this.panelDevolucionAbmHerramientasController.setControladorPadre(this);
		TecnicoService tecnicoServicio = new TecnicoService(new DAOSQLFactory());
		List<Tecnico> tecnicos = tecnicoServicio.obtenerTecnicos();
		for (Tecnico tecnico : tecnicos) {
			TecnicoDTO tecnicoDTO = new TecnicoDTO();
			tecnicoDTO.setIdTecnico(tecnico.getIdTecnico());
			tecnicoDTO.setNombre(tecnico.getNombre());
			tecnicoDTO.setApellido(tecnico.getApellido());
			tecnicoDTO.setDni(tecnico.getDni());
			tecnicoDTO.setLegajo(tecnico.getLegajo());
			comboTecnico.getItems().addAll(tecnicoDTO);
		}
		inicilizarTabla();
		this.inicilizarTabla1();
		if (columnaDisponible.getCellFactory().call(columnaDisponible).isEmpty()) {
			this.columnaAveriada.setVisible(true);
			columnaAveriada.setCellValueFactory(new PropertyValueFactory<>("comentarioAveriado"));
			columnaAveriada.setCellFactory(TextFieldTableCell.<HerramientaDTO>forTableColumn());
			columnaAveriada.setEditable(true);
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

	private void inicilizarTabla1() {
		this.tablaHerramientas1.setEditable(true);
		columnaCodigo1.setCellValueFactory(cellData -> cellData.getValue().getCodigo());
		columnaNombre1.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaCategoria1.setCellValueFactory(cellData -> cellData.getValue().getCategoria().getNombre());
		columnaDisponible.setCellValueFactory(cell -> cell.getValue().activoDisponibleProperty());
		columnaDisponible.setCellFactory(CheckBoxTableCell.forTableColumn(columnaDisponible));
		columnaDisponible.setVisible(true);
		columnaDisponible.setEditable(true);
		columnaAveriada.setEditable(false);
		tablaHerramientas1.setItems(masterData1);
	}

	private void transferenciaTabla(ObservableList<HerramientaDTO> baseRemover,
			ObservableList<HerramientaDTO> baseAdicionar, TableView<HerramientaDTO> tablaRemover) {
		HerramientaDTO herramienta = tablaRemover.getSelectionModel().getSelectedItem();
		tablaRemover.getSelectionModel().clearSelection();
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
}