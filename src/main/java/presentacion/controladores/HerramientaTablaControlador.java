package presentacion.controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.MasterDetailPane;

import domain.model.CategoriaHerramienta;
import domain.model.Deposito;
import domain.model.Herramienta;
import domain.model.UbicacionDeposito;
import dto.CategoriaHerramientaDTO;
import dto.DepositoDTO;
import dto.EnumEstadoHerramientaDTO;
import dto.HerramientaDTO;
import dto.UbicacionDepositoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AdministracionService;
import services.CategoriaHerramientaService;
import services.HerramientaService;
import util.Fechas;

public class HerramientaTablaControlador implements Initializable {

	@FXML
	private TableView<HerramientaDTO> tablaHerramientas;

	@FXML
	private BorderPane panel;

	@FXML
	private ScrollPane scrollpanel;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCodigo;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaNombre;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaMarca;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaCategoria;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaEstado;

	@FXML
	private TableColumn<HerramientaDTO, LocalDate> columnaGarantia;

	@FXML
	private TableColumn<HerramientaDTO, LocalDate> columnaUltimaModificacion;

	@FXML
	private TableColumn<HerramientaDTO, String> columnaDetalle;

	@FXML
	private Button btnPanelABM;

	@FXML
	private Button btnReportes;

	@FXML
	private TextField txtBusqueda;

	MasterDetailPane pane = new MasterDetailPane();

	HerramientaABM controlador;

	private ObservableList<HerramientaDTO> masterData = FXCollections.observableArrayList();

	private HerramientaService servicio;

	@FXML
	void abrirReporteHerramienta(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/administracion/reporteHerramienta.fxml"));
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
	void abrirPanel(MouseEvent event) {
		pane.setShowDetailNode(true);
		controlador.cargarDTOnuevo(btnPanelABM, masterData);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		servicio = new HerramientaService(new DAOSQLFactory());
		CategoriaHerramientaService categoriaHerramientaService = new CategoriaHerramientaService(new DAOSQLFactory());
		List<Herramienta> herramientas = servicio.obtenerHerramienta();
		panel.setCenter(null);
		pane.setMasterNode(this.tablaHerramientas);
		pane.setDetailNode(this.CargarNodo("/vistas/herramientas/agregarherramientamaestra"));
		pane.setDetailSide(Side.RIGHT);
		pane.setAnimated(true);
		pane.setDividerPosition(0);
		pane.setShowDetailNode(false);
		this.controlador.getBtnCerrar().setOnMouseClicked(event -> pane.setShowDetailNode(false));
		panel.setCenter(pane);

		btnPanelABM.setOnAction(event -> {
			pane.setShowDetailNode(true);
		});
		AdministracionService administracionService = new AdministracionService(new DAOSQLFactory());
		this.controlador.llenarTreeView();

		List<UbicacionDeposito> ubicaciones = administracionService.obtenerUbicacionDepositos();
		for (UbicacionDeposito ubicacionDeposito : ubicaciones) {
			Deposito deposito = new Deposito();
			deposito.setIdDeposito(ubicacionDeposito.getDeposito().getIdDeposito());
			deposito.setNombre(administracionService.obtenerNombreDeposito(deposito));
			DepositoDTO depositoDTO = new DepositoDTO();
			depositoDTO.setIdDeposito(deposito.getIdDeposito());
			depositoDTO.setNombre(deposito.getNombre());
			UbicacionDepositoDTO ubicacion = new UbicacionDepositoDTO(ubicacionDeposito.getIdUbicacionDeposito(),
					ubicacionDeposito.getNombre(), depositoDTO, ubicacionDeposito.getEstado().getInt());
			this.controlador.getChoUbicacion().getItems().add(ubicacion);
		}
		for (Herramienta herramienta : herramientas) {
			UbicacionDepositoDTO ubicacion = new UbicacionDepositoDTO();
			ubicacion.setIdUbicacionDeposito(herramienta.getUbicacion().getIdUbicacionDeposito());
			CategoriaHerramienta categoriaHerramienta = new CategoriaHerramienta();
			categoriaHerramienta.setIdCategoria(herramienta.getCategoria().getIdCategoria());
			CategoriaHerramientaDTO categoria = new CategoriaHerramientaDTO();
			categoria.setIdCategoria(herramienta.getCategoria().getIdCategoria());
			categoria.setNombre(categoriaHerramientaService.obtenerNombreCategoriaHerramienta(categoriaHerramienta));
			masterData.add(new HerramientaDTO(herramienta.getIdHerramienta(), herramienta.getCodigo(),
					herramienta.getNombre(), ubicacion, categoria, herramienta.getFactura(),
					herramienta.getNumeroActivo(), herramienta.getComentario(), herramienta.getMecanismoAdquisicion(),
					Fechas.CalendarTolocalDate(herramienta.getFechaAdquisicion()),
					Fechas.CalendarTolocalDate(herramienta.getFechaGarantiaExpiracion()),
					Fechas.CalendarTolocalDate(herramienta.getFechaUltimaModificacion()),
					obtenerEstado(herramienta.getEstado().getValor()), herramienta.getActivo(),
					herramienta.getProveedor(), herramienta.getMarca(), new Button("Ver"), masterData, controlador,
					btnPanelABM));
		}

		// 0. Initialize the columns.
		columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().getCodigo());
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaMarca.setCellValueFactory(cellData -> cellData.getValue().getMarca());
		columnaCategoria.setCellValueFactory(cellData -> cellData.getValue().getCategoria().getNombre());
		columnaEstado.setCellValueFactory(cellData -> cellData.getValue().getEstadoHerramienta().getNombre());
		columnaGarantia.setCellValueFactory(new PropertyValueFactory<>("fechaGarantia"));
		columnaUltimaModificacion.setCellValueFactory(new PropertyValueFactory<>("fechaUltimaModificacion"));
		columnaDetalle.setCellValueFactory(new PropertyValueFactory<>("btnBorrar"));

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

	}

	public void setComboBoxUbicacion(UbicacionDepositoDTO uDepositoDTO) {
		this.controlador.getChoUbicacion().setValue(uDepositoDTO);
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

	private Node CargarNodo(String ui) {
		FXMLLoader root = null;
		try {
			root = new FXMLLoader(getClass().getResource(ui + ".fxml"));
			Scene scene = new Scene(root.load()); // (root)
			this.controlador = root.getController();
			return scene.getRoot();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
