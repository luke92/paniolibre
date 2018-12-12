package presentacion.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import domain.model.DevolucionHerramienta;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import persistencia.dao.mysql.DAOSQLFactory;
import services.CategoriaHerramientaService;
import services.HerramientaService;
import util.Fechas;

public class ReparacionesAgregarControlador implements Initializable {

	@FXML
	private ReparacionesABM panelABMReparacionesController;

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
	private TableColumn<HerramientaDTO, String> columnaEstado;

	@FXML
	private TableColumn<HerramientaDTO, Boolean> columnaBntReparar;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaComentario;

	@FXML
	private TextField txtBusqueda;

	@FXML
	private Text textTitulo;

	@FXML
	private Button btnPendientes;

	@FXML
	private Button btnEnCurso;

	@FXML
	void llenarTablaEnCurso(MouseEvent event) {
		panelABMReparacionesController.modoFinalizarReparacion();
		masterData.clear();
		HerramientaService servicio = new HerramientaService(new DAOSQLFactory());
		CategoriaHerramientaService categoriaHerramientaService = new CategoriaHerramientaService(new DAOSQLFactory());
		List<Herramienta> herramientaAveriadas = servicio.obtenerHerramientaEnReparacion();
		for (Herramienta herramienta : herramientaAveriadas) {
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
			DevolucionHerramienta devolucionAveriada = servicio.obtenerComentarioAveriado(herramienta);
			herramientaDTO.setComentarioAveriado(devolucionAveriada.getComentario());
			masterData.add(herramientaDTO);
		}
	}

	@FXML
	void llenarTablaPendientes(MouseEvent event) {
		panelABMReparacionesController.modoNuevaReparacion();
		masterData.clear();
		HerramientaService servicio = new HerramientaService(new DAOSQLFactory());
		CategoriaHerramientaService categoriaHerramientaService = new CategoriaHerramientaService(new DAOSQLFactory());
		List<Herramienta> herramientaAveriadas = servicio.obtenerHerramientaAveriadas();
		for (Herramienta herramienta : herramientaAveriadas) {
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
			DevolucionHerramienta devolucionAveriada = servicio.obtenerComentarioAveriado(herramienta);
			herramientaDTO.setComentarioAveriado(devolucionAveriada.getComentario());
			masterData.add(herramientaDTO);
		}
	}

	private ObservableList<HerramientaDTO> masterData = FXCollections.observableArrayList();

	public ReparacionesABM getReparacionesABM() { // que es esto??

		return getReparacionesABM();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		panelHerramientasReparacion.setLeft(null);
		// LLamar Herramientas con estados Averiada.
		HerramientaService servicio = new HerramientaService(new DAOSQLFactory());
		CategoriaHerramientaService categoriaHerramientaService = new CategoriaHerramientaService(new DAOSQLFactory());
		List<Herramienta> herramientaAveriadas = servicio.obtenerHerramientaAveriadas();
		for (Herramienta herramienta : herramientaAveriadas) {
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
			DevolucionHerramienta devolucionAveriada = servicio.obtenerComentarioAveriado(herramienta);
			herramientaDTO.setComentarioAveriado(devolucionAveriada.getComentario());
			masterData.add(herramientaDTO);
		}

		Callback<TableColumn<HerramientaDTO, Boolean>, TableCell<HerramientaDTO, Boolean>> cellFactory = new Callback<TableColumn<HerramientaDTO, Boolean>, TableCell<HerramientaDTO, Boolean>>() {
			@Override
			public TableCell<HerramientaDTO, Boolean> call(final TableColumn<HerramientaDTO, Boolean> param) {
				final TableCell<HerramientaDTO, Boolean> cell = new TableCell<HerramientaDTO, Boolean>() {
					Image imgEdit = new Image(getClass().getResourceAsStream("/iconos/edit.png"));
					final Button btnEdit = new Button();

					@Override
					public void updateItem(Boolean check, boolean empty) {
						super.updateItem(check, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btnEdit.setOnAction(e -> {
								HerramientaDTO user = getTableView().getItems().get(getIndex());
								updateUser(user);
							});

							btnEdit.setStyle("-fx-background-color: transparent;");
							ImageView iv = new ImageView();
							iv.setImage(imgEdit);
							iv.setPreserveRatio(true);
							iv.setSmooth(true);
							iv.setCache(true);
							btnEdit.setGraphic(iv);

							setGraphic(btnEdit);
							setAlignment(Pos.CENTER);
							setText(null);
						}
					}

					private void updateUser(HerramientaDTO user) {
						if (user.getEstadoHerramienta() == EnumEstadoHerramientaDTO.AVERIADA) {

							panelABMReparacionesController.cargarHerramienta(user);
							panelHerramientasReparacion
									.setLeft(panelABMReparacionesController.getPanelABMReparaciones());

						} else {
							panelABMReparacionesController.cargarHerramientaRep(user);
							panelHerramientasReparacion
									.setLeft(panelABMReparacionesController.getPanelABMReparaciones());
						}
					}
				};
				return cell;
			}

		};

		// 0. Initialize the columns.
		columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().getCodigo());
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaCategoria.setCellValueFactory(cellData -> cellData.getValue().getCategoria().getNombre());
		columnaEstado.setCellValueFactory(cellData -> cellData.getValue().getEstadoHerramienta().getNombre());
		columnaBntReparar.setCellFactory(cellFactory); /// aca el boton magico
		columnaComentario.setCellValueFactory(cellData -> cellData.getValue().getComentarioAveriadoProperti());

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

		panelABMReparacionesController.modoNuevaReparacion();

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