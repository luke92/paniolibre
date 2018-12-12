package presentacion.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.ArbolCategoria;
import domain.model.EnumRecibido;
import domain.model.Estado;
import domain.model.Insumo;
import domain.model.OrdenDeTrabajo;
import domain.model.PedidoInsumo;
import domain.model.PedidoInsumoDetalle;
import dto.OrdenTrabajoDTO;
import dto.PedidoInsumoDTO;
import dto.PedidoInsumoDetalleDTO;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
import presentacion.controladores.insumos.ControladorListaOrdenesDeTrabajo;
import services.InsumoService;
import services.PedidoInsumoService;
import util.Dialogos;
import util.ExpReg;
import util.Fechas;

public class ControladorPedidosDetalle implements Initializable {

	@FXML
	private DialogPane ventanaAgregarPedido;

	@FXML
	private DatePicker datePickerFechaProbableRecepcion;

	@FXML
	private TextArea textAreaComentarios;

	@FXML
	private TextField textNumeroOrdenCompra;

	@FXML
	private TextField textProveedor;

	@FXML
	private TextField txtBusqueda;

	@FXML
	private Button btnConfirmarAgregarPedido;

	@FXML
	private Button btnDescartarPedido;

	@FXML
	private TextField txtOrdenDeTrabajo;

	@FXML
	private TableView<PedidoInsumoDetalleDTO> tablaInsumosDisponibles;

	@FXML
	private TableColumn<PedidoInsumoDetalleDTO, String> columnaCodigoIzquierda;

	@FXML
	private TableColumn<PedidoInsumoDetalleDTO, String> columnaNombreIzquierda;

	@FXML
	private TableColumn<PedidoInsumoDetalleDTO, String> columnaIzquierda;

	@FXML
	private TableView<PedidoInsumoDetalleDTO> tablaInsumosPedidos;

	@FXML
	private TableColumn<PedidoInsumoDetalleDTO, String> columnaCodigoDerecha;

	@FXML
	private TableColumn<PedidoInsumoDetalleDTO, String> columnaNombreDerecha;

	@FXML
	private TableColumn<PedidoInsumoDetalleDTO, String> columnaCategoriaDerecha;

	@FXML
	private TableColumn<PedidoInsumoDetalleDTO, Spinner<Integer>> columnaCantidadNuevaDerecha;

	private ObservableList<PedidoInsumoDetalleDTO> masterDataInsumos = FXCollections.observableArrayList();

	private ObservableList<PedidoInsumoDetalleDTO> masterDataPedidoInsumoDetalle = FXCollections.observableArrayList();

	private PedidoInsumoService pedidosService = new PedidoInsumoService(new DAOSQLFactory());

	private ObservableList<PedidoInsumoDTO> masterData = FXCollections.observableArrayList();

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnDevolver;

	private ValidationSupport validationSupport = new ValidationSupport();

	private OrdenTrabajoDTO ordenDeTrabajoDTO;

	private TextField idOrden = new TextField();

	@FXML
	void mostrarListaOrdenes(MouseEvent event) {
		this.txtOrdenDeTrabajo.setText("");
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
			ordenDeTrabajoDTO = controlador.obtenerOrden();
			txtOrdenDeTrabajo.setText(ordenDeTrabajoDTO.getIdOrdenTrabajo().get());
			idOrden.setText(String.valueOf(ordenDeTrabajoDTO.getId().get()));

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void confirmarAgregarPedido(MouseEvent event) {
		if (validarCampos()) {
			PedidoInsumo nuevoPedido = new PedidoInsumo();

			nuevoPedido
					.setFechaProbableRecepcion(Fechas.localDateToCalendar(datePickerFechaProbableRecepcion.getValue()));
			nuevoPedido.setComentario(this.textAreaComentarios.getText());
			nuevoPedido.setNumeroOrdenCompra(Integer.valueOf(this.textNumeroOrdenCompra.getText()));
			nuevoPedido.setProveedor(this.textProveedor.getText());
			nuevoPedido.setFechaSolicitud(Calendar.getInstance());
			nuevoPedido.setRecibido(EnumRecibido.PENDIENTE);
			nuevoPedido.setIdPedidoInsumo(0);
			if (txtOrdenDeTrabajo.getText().equals("")) {
				this.pedidosService.agregarPedidoInsumo(nuevoPedido);
			} else {
				OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
				String id = idOrden.getText();
				ordenDeTrabajo.setId(Integer.parseInt(id));
				nuevoPedido.setOrdenDeTrabajo(ordenDeTrabajo);
				this.pedidosService.agregarPedidoInsumo(nuevoPedido);
			}
			List<PedidoInsumoDetalleDTO> pedidosDetalle = tablaInsumosPedidos.getItems();
			for (PedidoInsumoDetalleDTO pedidoInsumoDetalleDTO : pedidosDetalle) {
				ArbolCategoria categoriaInsumo = new ArbolCategoria();
				categoriaInsumo.setId(pedidoInsumoDetalleDTO.getInsumo().getCategoria().getId());
				Estado estado = Estado.ALTA;
				Insumo insumo = new Insumo(pedidoInsumoDetalleDTO.getInsumo().getIdInsumo().get(),
						pedidoInsumoDetalleDTO.getInsumo().getCodigo().get(),
						pedidoInsumoDetalleDTO.getInsumo().getNombre().get(),
						pedidoInsumoDetalleDTO.getInsumo().getMarca().get(), categoriaInsumo, null,
						pedidoInsumoDetalleDTO.getInsumo().getComentario().get(),
						pedidoInsumoDetalleDTO.getInsumo().getUmbralMinimo().get(), estado);
				int idPedido = this.pedidosService.obtenerIdPedido(nuevoPedido);
				PedidoInsumo pedidoInsumo = new PedidoInsumo();
				pedidoInsumo.setIdPedidoInsumo(idPedido);
				PedidoInsumoDetalle detalle = new PedidoInsumoDetalle(pedidoInsumo, insumo,
						pedidoInsumoDetalleDTO.getSpnCantidad().getValue());
				this.pedidosService.agregarPedidoInsumoDetalle(detalle);
			}

			this.masterData.add(nuevoPedido.getDTO());
//			Stage stage = (Stage) this.ventanaAgregarPedido.getScene().getWindow();
//			stage.close();
		}
	}

	@FXML
	void descartarPedido(MouseEvent event) {
		Stage stage = (Stage) this.ventanaAgregarPedido.getScene().getWindow();
		stage.close();
	}

	@FXML
	void devolver(MouseEvent event) {
		PedidoInsumoDetalleDTO pedidoInsumo = tablaInsumosPedidos.getSelectionModel().getSelectedItem();
		if (pedidoInsumo != null) {
			transferenciaTabla(masterDataPedidoInsumoDetalle, masterDataInsumos, tablaInsumosPedidos);
		}
	}

	@FXML
	void enviar(MouseEvent event) {
		PedidoInsumoDetalleDTO pedidoInsumo = tablaInsumosDisponibles.getSelectionModel().getSelectedItem();
		if (pedidoInsumo != null) {
			transferenciaTabla(masterDataInsumos, masterDataPedidoInsumoDetalle, tablaInsumosDisponibles);
			this.btnConfirmarAgregarPedido.setDisable(false);
		}
	}

	void llenarTablaDerecha() {
		columnaCodigoDerecha.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getCodigo());
		columnaNombreDerecha.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getNombre());
		columnaCategoriaDerecha
				.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getCategoria().getNombreProperty());
		columnaCantidadNuevaDerecha.setCellValueFactory(new PropertyValueFactory<>("spnCantidad"));
		tablaInsumosPedidos.setItems(masterDataPedidoInsumoDetalle);
	}

	void llenarTablaIzquierda() {
		InsumoService insumoService = new InsumoService(new DAOSQLFactory());
		List<Insumo> insumos = insumoService.obtenerInsumos();
		for (Insumo insumo : insumos) {
			PedidoInsumoDetalleDTO detalleDTO = new PedidoInsumoDetalleDTO();
			detalleDTO.setInsumo(insumo.getInsumoDTO());
			masterDataInsumos.add(detalleDTO);
		}
		columnaCodigoIzquierda.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getCodigo());
		columnaNombreIzquierda.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getNombre());
		columnaIzquierda
				.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getCategoria().getNombreProperty());
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<PedidoInsumoDetalleDTO> filteredData = new FilteredList<>(masterDataInsumos, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(insumo -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (insumo.getInsumo().getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (insumo.getInsumo().getCodigo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (insumo.getInsumo().getCategoria().getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<PedidoInsumoDetalleDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaInsumosDisponibles.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaInsumosDisponibles.setItems(sortedData);
	}

	private void transferenciaTabla(ObservableList<PedidoInsumoDetalleDTO> baseRemover,
			ObservableList<PedidoInsumoDetalleDTO> baseAdicionar, TableView<PedidoInsumoDetalleDTO> tablaRemover) {

		PedidoInsumoDetalleDTO pepidoInsumo = tablaRemover.getSelectionModel().getSelectedItem();
		tablaRemover.getSelectionModel().clearSelection();
		baseRemover.remove(pepidoInsumo);
		baseAdicionar.add(pepidoInsumo);
	}

	@FXML
	void validarAgregarPedido(KeyEvent event) {
		if (!camposVacios()) {
			this.btnConfirmarAgregarPedido.setDisable(false);
		} else {
			this.btnConfirmarAgregarPedido.setDisable(true);
		}
	}

	boolean camposVacios() {
		if (this.textNumeroOrdenCompra.getText().isEmpty() || this.textProveedor.getText().isEmpty()
				|| this.textAreaComentarios.getText().isEmpty()
				|| this.datePickerFechaProbableRecepcion.getValue() == null
				|| this.masterDataPedidoInsumoDetalle.isEmpty())
			return true;
		else
			return false;
	}

	private boolean validarCampos() {
		if (this.textNumeroOrdenCompra.getText().isEmpty()) {
			Dialogos.error("Error al Agregar Pedido", "Revise los datos ingresados",
					"El campo 'Numero de compra' no puede estar vacio");
			return false;
		} else if (!ExpReg.contieneSoloNumeros(this.textNumeroOrdenCompra.getText())) {
			Dialogos.error("Error al Agregar Pedido", "Revise los datos ingresados",
					"El campo 'Numero de compra' debe contener solo numeros");
			return false;
		}
		if (this.textProveedor.getText().isEmpty()) {
			Dialogos.error("Error al Agregar Pedido", "Revise los datos ingresados",
					"El campo 'Proveedor' no puede estar vacio");
			return false;
		}
		if (this.textAreaComentarios.getText().isEmpty()) {
			Dialogos.error("Error al Agregar Pedido", "Revise los datos ingresados",
					"El campo 'Comentarios' no puede estar vacio");
			return false;
		}
		if (this.datePickerFechaProbableRecepcion.getValue() == null) {
			Dialogos.error("Error al Agregar Pedido", "Revise los datos ingresados",
					"El campo 'Fecha provable de Recepcion' no puede estar vacio");
			return false;
		}
		if (this.masterDataPedidoInsumoDetalle.isEmpty()) {
			Dialogos.error("Error al Agregar Pedido", "Revise los datos ingresados",
					"La tabla 'Pedidos' no puede estar vacia");
			return false;
		}

		validationSupport.registerValidator(textAreaComentarios, Validator.createEmptyValidator("Campo requerido"));
		validationSupport.registerValidator(textNumeroOrdenCompra, Validator.createEmptyValidator("Campo requerido"));
		validationSupport.registerValidator(textProveedor, Validator.createEmptyValidator("Campo requerido"));
		validationSupport.registerValidator(datePickerFechaProbableRecepcion,
				Validator.createEmptyValidator("Campo requerido"));

		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.llenarTablaIzquierda();
		this.llenarTablaDerecha();

	}

	public ObservableList<PedidoInsumoDetalleDTO> getMasterDataInsumos() {
		return masterDataInsumos;
	}

	public void setMasterDataInsumos(ObservableList<PedidoInsumoDetalleDTO> masterDataInsumos) {
		this.masterDataInsumos = masterDataInsumos;
	}

	public ObservableList<PedidoInsumoDetalleDTO> getMasterDataPedidoInsumoDetalle() {
		return masterDataPedidoInsumoDetalle;
	}

	public void setMasterDataPedidoInsumoDetalle(ObservableList<PedidoInsumoDetalleDTO> masterDataPedidoInsumoDetalle) {
		this.masterDataPedidoInsumoDetalle = masterDataPedidoInsumoDetalle;
	}

	public ObservableList<PedidoInsumoDTO> getMasterData() {
		return masterData;
	}

	public void setMasterData(ObservableList<PedidoInsumoDTO> masterData) {
		this.masterData = masterData;
	}

}
