package presentacion.controladores.insumos;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Deposito;
import domain.model.DevolucionInsumo;
import domain.model.Insumo;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroInsumo;
import domain.model.Tecnico;
import domain.model.Usuario;
import dto.DepositoDTO;
import dto.InsumoDTO;
import dto.OrdenTrabajoDTO;
import dto.RetiroInsumoDTO;
import dto.TecnicoDTO;
import dto.UsuarioDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import services.CategoriaInsumoService;
import services.DevolucionInsumoService;
import services.InsumoService;
import services.OrdenDeTrabajoService;
import services.RetiroInsumoService;
import services.TecnicoService;
import util.Dialogos;
import util.Fechas;

public class DevolucionesInsumoControlador implements Initializable {

	// @FXML
	// private DevolucionesInsumoAbmControlador
	// panelABMRinsumosDevolucionesController;
	@FXML
	private Button btnObtenerOrdenes;
	@FXML
	private BorderPane bordelPanelDevolucion;

	@FXML
	private VBox vboxPanelTablas;

	@FXML
	private TableView<RetiroInsumoDTO> tablaHerramientas;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaTecnico;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaInsumo;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaDeposito;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaFecha;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaCantNuevo;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaCantUsado;

	@FXML
	private ComboBox<TecnicoDTO> comboTecnico;

	@FXML
	private TextField textNumOrdenTrabajo;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnDevolver;

	@FXML
	private TableView<RetiroInsumoDTO> tablaHerramientas1;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaTecnico1;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaInsumo1;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaDeposito1;

	@FXML
	private TableColumn<RetiroInsumoDTO, String> columnaFecha1;

	@FXML
	private TableColumn<RetiroInsumoDTO, Spinner<Integer>> columnaCantNuevo1;

	@FXML
	private TableColumn<RetiroInsumoDTO, Spinner<Integer>> columnaCantUsado1;

	@FXML
	private Button btnAceptar;
	
	private OrdenTrabajoDTO orden;

	public OrdenTrabajoDTO obtenerordenM() {
		return orden;
	}
	@FXML
	void obtenerOrdenes(MouseEvent event) {
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

	public VBox getVboxPanelTablas() {
		return vboxPanelTablas;
	}

	public BorderPane getBordelPanelRetiro() {
		return bordelPanelDevolucion;
	}

	public void setBordelPanelRetiro(BorderPane bordelPanelDevoluciones) {
		this.bordelPanelDevolucion = bordelPanelDevoluciones;
	}

	private ObservableList<RetiroInsumoDTO> masterData = FXCollections.observableArrayList();

	private ObservableList<RetiroInsumoDTO> masterData1 = FXCollections.observableArrayList();

	private TecnicoDTO tecnicoDTO;

	private RetiroInsumo retiroInsumoM;

	public void ocultarPanel() {
		this.bordelPanelDevolucion.setLeft(null);
	}

	public String obtenerorden() {
		return this.textNumOrdenTrabajo.getText();
	}

	public TecnicoDTO obtenerTecnico() {
		tecnicoDTO = comboTecnico.getValue();
		return tecnicoDTO;
	}

	@FXML
	void aceptarDevolucion(MouseEvent event) {
		if (Dialogos.confirmacion("Confirmar Devolucion", "Devolucion de Insumos",
				"Desea confirmar la devolucion de los insumos seleccionados?", "Aceptar", "Cancelar")) {
			DevolucionInsumoService devolucionInsumoService = new DevolucionInsumoService(new DAOSQLFactory());
			List<DevolucionInsumo> devoluciones = new ArrayList<DevolucionInsumo>();

			for (RetiroInsumoDTO retiro : masterData1) {
				DevolucionInsumo devolucion = new DevolucionInsumo();
				RetiroInsumo retiroInsumo = new RetiroInsumo();
				retiroInsumoM = new RetiroInsumo();
				retiroInsumoM.setOrdenDeTrabajo(retiroInsumo.getOrdenDeTrabajo());
				retiroInsumo.setIdRetiroInsumo(retiro.getIdRetiroInsumo().get());
				devolucion.setRetiroInsumo(retiroInsumo);

				devolucion.setCantidadNueva(retiro.getValorSpinnerCantNuevo());
				devolucion.setCantidadUsado(retiro.getValorSpinnerCantUsado());

				Deposito deposito = new Deposito();
				deposito.setIdDeposito(retiro.getDeposito().getIdDeposito().get());
				devolucion.setDeposito(deposito);

				Usuario usuario = new Usuario();
				usuario.setIdUsuario(1);
				devolucion.setUsuario(usuario);

				Insumo insumo = new Insumo();
				insumo.setIdInsumo(retiro.getInsumo().getIdInsumo().get());
				devolucion.setInsumo(insumo);

				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(retiro.getTecnico().getIdTecnico().get());
				devolucion.setTecnico(tecnico);

				if (retiro.getOrdenDeTrabajo() != null) {
					OrdenDeTrabajo ordenTrabajo = new OrdenDeTrabajo();
					ordenTrabajo.setId(retiro.getOrdenDeTrabajo().getId().get());
					ordenTrabajo.setIdOrdenDeTrabajo(retiro.getOrdenDeTrabajo().getIdOrdenTrabajo().get());
					devolucion.setOrdenDeTrabajo(ordenTrabajo);
				}
				devoluciones.add(devolucion);
			}
			devolucionInsumoService.agregarDevolucionesInsumo(devoluciones);
			if (!this.textNumOrdenTrabajo.getText().equals("")) {
				OrdenDeTrabajoService service = new OrdenDeTrabajoService(new DAOSQLFactory());
				if (service.ordenTrabajoSinDevolucionesPendientes(textNumOrdenTrabajo.getText()) == true) {
					if (Dialogos.confirmacion("Confirmar operacion",
							"La Orden de Trabajo no adeuda ninguna devolucion ",
							"Desea dar como Realizada la Orden de Trabajo ?", "Aceptar", "Cancelar")) {
						int idMantis = Integer.parseInt(this.textNumOrdenTrabajo.getText());
						service.cambiarEstadoOrdenResuelta(idMantis);
						OrdenDeTrabajo ot = new OrdenDeTrabajo();
						ot.setIdOrdenDeTrabajo(this.textNumOrdenTrabajo.getText());
						ot.setId(service.obtenerIdOrden(ot));
						service.cambiarEstadoResuelta(ot);
					}
				}
			}
			masterData1.clear();
			btnAceptar.setDisable(true);
		}
	}

	@FXML
	void devolver(MouseEvent event) {
		RetiroInsumoDTO retiroInsumo = tablaHerramientas1.getSelectionModel().getSelectedItem();
		if (retiroInsumo != null) {
			transferenciaTabla(masterData1, masterData, tablaHerramientas1);
		}
		if (masterData1.isEmpty())
			btnAceptar.setDisable(true);
	}

	@FXML
	void enviar(MouseEvent event) {
		RetiroInsumoDTO herramienta = tablaHerramientas.getSelectionModel().getSelectedItem();
		if (herramienta != null) {
			transferenciaTabla(masterData, masterData1, tablaHerramientas);
		}
		if (!masterData1.isEmpty())
			btnAceptar.setDisable(false);
	}

	@FXML
	void llenarTabla(MouseEvent event) {
		masterData.clear();
		if (comboTecnico.getSelectionModel().isEmpty() && textNumOrdenTrabajo.getText().trim().isEmpty()) {
			ValidationSupport validationSupport = new ValidationSupport();
			validationSupport.registerValidator(comboTecnico, Validator.createEmptyValidator("Campo Requerido"));
			Notifications.create().title("Atencion").text("Seleccione un Tecnico.").darkStyle().showWarning();
		}
		RetiroInsumoService servicio = new RetiroInsumoService(new DAOSQLFactory());
		InsumoService insumoService = new InsumoService(new DAOSQLFactory());
		CategoriaInsumoService categoriaInsumoService = new CategoriaInsumoService(new DAOSQLFactory());
		List<RetiroInsumo> retiros = null;
		Tecnico tecnico = null;
		OrdenDeTrabajo ordenM = null;
		if (textNumOrdenTrabajo.getText().equals("")) {
			tecnico = new Tecnico();
			tecnico.setIdTecnico(comboTecnico.getValue().getIdTecnico().get());
			tecnico.setNombre(comboTecnico.getValue().getNombre().get());
			retiros = servicio.obtenerRetiroPorTecnicos(tecnico);
		} else {
			OrdenDeTrabajoService ordenDeTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());
			ordenM = new OrdenDeTrabajo();
			ordenM.setIdOrdenDeTrabajo(this.textNumOrdenTrabajo.getText());
			int id = ordenDeTrabajoService.obtenerIdOrden(ordenM);
			ordenM.setId(id);
			retiros = servicio.obtenerRetiroPorOrden(ordenM);
			this.comboTecnico.getItems().clear();
			List<TecnicoDTO> listaT = ordenDeTrabajoService.obtenerTecnicos(ordenM);
			for (TecnicoDTO tecnicoDTO : listaT) {
				comboTecnico.getItems().add(tecnicoDTO);
			}
		}
		if (retiros.size() == 0) {
			Dialogos.advertencia("Informacion", "No hay Retiros",
					"No existen retiros sin ser devueltos con los filtros ingresados");
		}
		for (RetiroInsumo retiroInsumo : retiros) {
			RetiroInsumo retiroInsumoMaestro = new RetiroInsumo();

			Insumo insumo = new Insumo();
			insumo.setIdInsumo(retiroInsumo.getInsumo().getIdInsumo());

			Insumo insumoMaestro = insumoService.obtenerInsumoMaestro(insumo);
			insumo.setNombre(insumoMaestro.getNombre());
			retiroInsumoMaestro.setIdRetiroInsumo(retiroInsumo.getIdRetiroInsumo());

			Usuario usuario = new Usuario();
			usuario.setIdUsuario(retiroInsumo.getUsuario().getIdUsuario());
			retiroInsumoMaestro.setUsuario(usuario);

			retiroInsumoMaestro.setFecha(retiroInsumo.getFecha());

			Deposito deposito = new Deposito();
			deposito.setIdDeposito(retiroInsumo.getDeposito().getIdDeposito());
			deposito.setNombre(retiroInsumo.getDeposito().getNombre());
			retiroInsumoMaestro.setDeposito(deposito);

			retiroInsumoMaestro.setInsumo(insumoMaestro);

			Tecnico tecnicoRet = new Tecnico();
			tecnicoRet.setIdTecnico(retiroInsumo.getTecnico().getIdTecnico());
			tecnicoRet.setNombre(retiroInsumo.getTecnico().getNombre());
			retiroInsumoMaestro.setTecnico(tecnicoRet);

			retiroInsumoMaestro.setCantidadNueva(retiroInsumo.getCantidadNueva());
			retiroInsumoMaestro.setCantidadUsado(retiroInsumo.getCantidadUsado());
			retiroInsumoMaestro.setCantidadReservada(retiroInsumo.getCantidadReservada());
			retiroInsumoMaestro.setDevuelto(retiroInsumo.getDevuelto());

			RetiroInsumoDTO retiroInsumoDTO = new RetiroInsumoDTO();
			retiroInsumoDTO.setIdRetiroInsumo(retiroInsumoMaestro.getIdRetiroInsumo());
			InsumoDTO insumoDTO = new InsumoDTO();
			insumoDTO.setIdInsumo(retiroInsumoMaestro.getInsumo().getIdInsumo());
			insumoDTO.getNombre().set(retiroInsumo.getInsumo().getNombre());
			retiroInsumoDTO.setInsumo(insumoDTO);
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setIdUsuario(usuario.getIdUsuario());
			retiroInsumoDTO.setUsuario(usuarioDTO);
			DepositoDTO depositoDTO = new DepositoDTO();
			depositoDTO.setIdDeposito(deposito.getIdDeposito());
			depositoDTO.setNombre(deposito.getNombre());
			retiroInsumoDTO.setDeposito(depositoDTO);
			TecnicoDTO tecnicoDTO = new TecnicoDTO();
			if (textNumOrdenTrabajo.getText().equals("")) {
			tecnicoDTO.setIdTecnico(tecnico.getIdTecnico());
			tecnicoDTO.setNombre(tecnico.getNombre());
			}else {
				tecnicoDTO.setIdTecnico(retiroInsumo.getTecnico().getIdTecnico());
				tecnicoDTO.setNombre(retiroInsumo.getTecnico().getNombre());
			}
			retiroInsumoDTO.setTecnico(tecnicoDTO);

			if (retiroInsumo.getOrdenDeTrabajo() != null) {
				OrdenTrabajoDTO ordenTrabajoDTO = new OrdenTrabajoDTO();
				ordenTrabajoDTO.setId(retiroInsumo.getOrdenDeTrabajo().getId());
				retiroInsumoDTO.setOrdenDeTrabajo(ordenTrabajoDTO);
			}
			retiroInsumoDTO.setCantidadNueva(retiroInsumo.getCantidadNueva());
			retiroInsumoDTO.setCantidadUsado(retiroInsumo.getCantidadUsado());
			retiroInsumoDTO.setCantidadReservada(retiroInsumo.getCantidadReservada());
			retiroInsumoDTO.setDevuelto(0);
			retiroInsumoDTO.setSpnCantidadNuevo(retiroInsumoDTO.getCantidadNueva().get());
			retiroInsumoDTO.setSpnCantidadUsado(retiroInsumoDTO.getCantidadUsado().get());
			retiroInsumoDTO.setFecha(Fechas.CalendarTolocalDate(retiroInsumo.getFecha()));
			this.masterData.addAll(retiroInsumoDTO);
		}

	}

	public void cerrarPanelLateral() {
		this.bordelPanelDevolucion.setLeft(null);
	}

	public void abrirPanelLateral() {
		this.vboxPanelTablas.setDisable(true);
		// this.panelABMRinsumosDevolucionesController.setListaPrestamo(masterData1);
		// this.bordelPanelDevolucion.setLeft(panelABMRinsumosDevolucionesController.getPanelABMDevoluciones());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// this.panelABMRinsumosDevolucionesController.setControladorPadre(this);
		this.cerrarPanelLateral();
		this.ocultarPanel();
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
		this.inicilizarTablaIzquierda();
		this.inicilizarTablaDerecha();

	}

	private void inicilizarTablaIzquierda() {
		columnaTecnico.setCellValueFactory(cellData -> cellData.getValue().getTecnico().getNombre());
		columnaInsumo.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getNombre());
		columnaDeposito.setCellValueFactory(cellData -> cellData.getValue().getDeposito().getNombre());
		columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		columnaCantNuevo.setCellValueFactory(cellData -> cellData.getValue().getCantidadNueva().asString());
		columnaCantUsado.setCellValueFactory(cellData -> cellData.getValue().getCantidadUsado().asString());

		tablaHerramientas.setItems(masterData);
	}

	private void inicilizarTablaDerecha() {
		columnaTecnico1.setCellValueFactory(cellData -> cellData.getValue().getTecnico().getNombreCompleto());
		columnaInsumo1.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getNombre());
		columnaDeposito1.setCellValueFactory(cellData -> cellData.getValue().getDeposito().getNombre());
		columnaFecha1.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		columnaCantNuevo1.setCellValueFactory(new PropertyValueFactory<>("spnCantidadNuevo"));
		columnaCantUsado1.setCellValueFactory(new PropertyValueFactory<>("spnCantidadUsado"));
		tablaHerramientas1.setItems(masterData1);
	}

	private void transferenciaTabla(ObservableList<RetiroInsumoDTO> baseRemover,
			ObservableList<RetiroInsumoDTO> baseAdicionar, TableView<RetiroInsumoDTO> tablaRemover) {
		RetiroInsumoDTO retiroInsumo = tablaRemover.getSelectionModel().getSelectedItem();
		tablaRemover.getSelectionModel().clearSelection();
		baseRemover.remove(retiroInsumo);
		baseAdicionar.add(retiroInsumo);
	}

}