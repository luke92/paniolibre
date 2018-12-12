package presentacion.controladores.insumos;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.CategoriaInsumo;
import domain.model.Deposito;
import domain.model.EnumRecibido;
import domain.model.IngresoInsumo;
import domain.model.Insumo;
import domain.model.OrdenDeTrabajo;
import domain.model.PedidoInsumo;
import domain.model.PedidoInsumoDetalle;
import domain.model.UbicacionDeposito;
import dto.ArbolCategoriaDTO;
import dto.CategoriaInsumoDTO;
import dto.DepositoDTO;
import dto.InsumoDTO;
import dto.OrdenTrabajoDTO;
import dto.PedidoInsumoDTO;
import dto.PedidoInsumoDetalleDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Spinner;
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
import services.AdministracionService;
import services.CategoriaInsumoService;
import services.IngresoInsumoService;
import services.InsumoService;
import services.PedidoInsumoService;
import util.Dialogos;
import util.Fechas;

public class ControladorIngresoInsumo implements Initializable {

	@FXML
	private DialogPane ventanaIngresarInsumo;

	@FXML
	private DatePicker datePickerFecha;

	@FXML
	private Button btnIngresar;

	@FXML
	private Button btnDescartar;

	@FXML
	private TableView<PedidoInsumoDTO> tablaPedido;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaNumeroOrdenPedido;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaPedidoProveedor;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaFechaSolicitud;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaPedidoEstado;

	@FXML
	private TableColumn<PedidoInsumoDTO, String> columnaPedidoIdMantis;

	@FXML
	private ComboBox<DepositoDTO> comboDeposito;

	@FXML
	private TextField txtBusquedaInsumo;

	@FXML
	private TextField txtBusquedaPedido;

	@FXML
	private TableView<InsumoDTO> tablaInsumos;

	@FXML
	private TableColumn<InsumoDTO, String> columnaCodigo;

	@FXML
	private TableColumn<InsumoDTO, String> columnaNombre;

	@FXML
	private TableColumn<InsumoDTO, String> columnaCategoria;

	@FXML
	private TableColumn<InsumoDTO, String> columnaCantidad;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnDevolver;

	@FXML
	private TableView<InsumoDTO> tablaInsumosCantidades;

	@FXML
	private TableColumn<InsumoDTO, String> columnaCodigoDerecha;

	@FXML
	private TableColumn<InsumoDTO, String> columnaNombreDerecha;

	@FXML
	private TableColumn<InsumoDTO, Spinner<Integer>> columnaCantidadDerecha;

	@FXML
	private ComboBox<String> comboTipoDeIngreso;

	@FXML
	private TextField textCodigoOrden;

	@FXML
	private Button btnProcesarPedido;

	@FXML
	private Button btnObtenerOrdenes;

	private ObservableList<PedidoInsumoDetalleDTO> masterDataPedidosDetalles = FXCollections.observableArrayList();

	private ObservableList<PedidoInsumoDTO> masterDataPedidos = FXCollections.observableArrayList();

	private ObservableList<InsumoDTO> masterDataInsumos = FXCollections.observableArrayList();

	private ObservableList<InsumoDTO> masterDataInsumoCantidades = FXCollections.observableArrayList();

	private PedidoInsumoService pedidoInsumoService = new PedidoInsumoService(new DAOSQLFactory());

	private ValidationSupport validationSupport = new ValidationSupport();

	private boolean estadoPedido;

	private Button btnIngresoStock;

	@FXML
	void obtenerOrdenes(MouseEvent event) {
		this.textCodigoOrden.setText("");
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
			OrdenTrabajoDTO orden = controlador.obtenerOrden();
			textCodigoOrden.setText(orden.getIdOrdenTrabajo().get());

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@FXML
	void confirmarIngresar(MouseEvent event) {
		if (validarCampos()) {
			IngresoInsumoService ingresoInsumoService = new IngresoInsumoService(new DAOSQLFactory());
			Deposito deposito = new Deposito();
			deposito.setIdDeposito(this.comboDeposito.getValue().getIdDeposito().get());
			Calendar fechaIngreso = Calendar.getInstance();
			fechaIngreso.setTime(Date.valueOf(LocalDate.now()));
			IngresoInsumo ingresoInsumo = new IngresoInsumo();
			ingresoInsumo.setIdIngresoInsumo(0);
			ingresoInsumo.setDeposito(deposito);
			if (this.comboTipoDeIngreso.getValue().equals("Caja Chica")) {
				List<InsumoDTO> insumos = masterDataInsumoCantidades;
				for (InsumoDTO insumoDTO : insumos) {
					Insumo insumo = new Insumo();
					insumo.setIdInsumo(insumoDTO.getIdInsumo().get());
					ingresoInsumo.setInsumo(insumo);
					ingresoInsumo.setCantidadIngreso(insumoDTO.getValorSpinnerCantidad());
				}
				ingresoInsumoService.agregarIngresoInsumo(ingresoInsumo);
			}
			if (this.comboTipoDeIngreso.getValue().equals("Orden de Trabajo")) {
				OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
				ordenDeTrabajo.setIdOrdenDeTrabajo(textCodigoOrden.getText());
				ingresoInsumo.setOrdenDeTrabajo(ordenDeTrabajo);
				List<InsumoDTO> insumos = masterDataInsumoCantidades;
				for (InsumoDTO insumoDTO : insumos) {
					Insumo insumo = new Insumo();
					insumo.setIdInsumo(insumoDTO.getIdInsumo().get());
					ingresoInsumo.setInsumo(insumo);
					ingresoInsumo.setCantidadIngreso(insumoDTO.getValorSpinnerCantidad());
				}
				ingresoInsumoService.agregarIngresoInsumoConOrden(ingresoInsumo);
			}
			if (this.comboTipoDeIngreso.getValue().equals("Pedido")) {
				PedidoInsumo pedidoInsumo = new PedidoInsumo();
				pedidoInsumo.setFechaRealRecepcion(fechaIngreso);
				List<InsumoDTO> insumos = masterDataInsumoCantidades;
				for (InsumoDTO insumoDTO : insumos) {
					Insumo insumo = new Insumo();
					insumo.setIdInsumo(insumoDTO.getIdInsumo().get());
					ingresoInsumo.setInsumo(insumo);
					ingresoInsumo.setCantidadIngreso(insumoDTO.getValorSpinnerCantidad());
					pedidoInsumo.setIdPedidoInsumo(insumoDTO.getPedidoInsumoDTO().getIdPedidoInsumo());
					PedidoInsumoDetalle detalle = new PedidoInsumoDetalle();
					detalle.setInsumo(insumo);
					detalle.setPedidoInsumo(pedidoInsumo);
					if (!insumoDTO.getOrdenTrabajoDTO().equals(null)) {
						OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
						ordenDeTrabajo.setId(insumoDTO.getOrdenTrabajoDTO().getId().get());
						ordenDeTrabajo.setIdOrdenDeTrabajo(insumoDTO.getOrdenTrabajoDTO().getIdOrdenTrabajo().get());
						pedidoInsumo.setOrdenDeTrabajo(ordenDeTrabajo);
						ingresoInsumo.setOrdenDeTrabajo(ordenDeTrabajo);
					}
					ingresoInsumo.setPedidoInsumo(pedidoInsumo);
					if (ingresoInsumo.getCantidadIngreso() == insumoDTO.getCantidad().get()) {
						pedidoInsumoService.cambiarEstadoRecibido(pedidoInsumo);
						ingresoInsumoService.agregarIngresoInsumo(ingresoInsumo);
						pedidoInsumoService.cambiarEstadoProcesado(detalle);
					} else {
						ChoiceDialog<String> dialog = Dialogos.tipoIngresoPedido(insumoDTO.getNombre().get());
						Optional<String> result = dialog.showAndWait();
						if (result.get().equals("Parcial")) {
							detalle.setCantidad(insumoDTO.getValorSpinnerCantidad());
							ingresoInsumoService.agregarIngresoInsumo(ingresoInsumo);
							pedidoInsumoService.cambiarEstadoParcial(pedidoInsumo);
							pedidoInsumoService.cambiarEstadoSinProcesar(detalle);
							pedidoInsumoService.editarPedidoInsumoDetalle(detalle);
							pedidoInsumo.setRecibido(EnumRecibido.PARCIAL);
							estadoPedido = true;
						}
						if (result.get().equals("Incompleto")) {
							detalle.setCantidad(insumoDTO.getValorSpinnerCantidad());
							ingresoInsumoService.agregarIngresoInsumo(ingresoInsumo);
							pedidoInsumo.setRecibido(EnumRecibido.INCOMPLETO);
							pedidoInsumoService.cambiarEstadoIncompleto(pedidoInsumo);
							pedidoInsumoService.editarPedidoInsumoDetalle(detalle);
							pedidoInsumoService.cambiarEstadoProcesado(detalle);
						}
						if (estadoPedido == true) {
							pedidoInsumoService.cambiarEstadoParcial(pedidoInsumo);
						}
					}
				}
			}

		}
//		this.btnIngresoStock.fire();
	}

	private boolean validarCampos() {
		boolean ret = true;
		if (comboDeposito.getSelectionModel().isEmpty()) {
			validationSupport.registerValidator(comboDeposito, Validator.createEmptyValidator("Campo requerido"));
			ret = false;
		}
		if (comboTipoDeIngreso.getSelectionModel().isEmpty()) {
			validationSupport.registerValidator(comboDeposito, Validator.createEmptyValidator("Campo requerido"));
			ret = false;
		}
		return ret;
	}

	@FXML
	void descartarPedido(MouseEvent event) {
		Stage stage = (Stage) ventanaIngresarInsumo.getScene().getWindow();
		stage.close();
	}

	@FXML
	void devolver(MouseEvent event) {
		InsumoDTO insumo = tablaInsumosCantidades.getSelectionModel().getSelectedItem();

		if (this.masterDataInsumoCantidades.size() == 1) {
			this.btnIngresar.setDisable(true);
		}
		if (insumo != null) {
			transferenciaTabla(masterDataInsumoCantidades, masterDataInsumos, tablaInsumosCantidades);
		}
	}

	@FXML
	void enviar(MouseEvent event) {
		InsumoDTO insumo = tablaInsumos.getSelectionModel().getSelectedItem();

		if (this.masterDataInsumoCantidades.size() == 1) {
			this.btnIngresar.setDisable(true);
		}
		if (insumo != null) {
			transferenciaTabla(masterDataInsumos, masterDataInsumoCantidades, tablaInsumos);
			this.btnIngresar.setDisable(false);
		}
	}

	@FXML
	void procesarPedido(MouseEvent event) {
		if (!tablaPedido.getSelectionModel().isEmpty()) {
			InsumoService insumoService = new InsumoService(new DAOSQLFactory());
			CategoriaInsumoService categoriaInsumoService = new CategoriaInsumoService(new DAOSQLFactory());
			masterDataInsumos.clear();
			PedidoInsumoDTO pedido = masterDataPedidos.get(tablaPedido.getSelectionModel().getSelectedIndex());
			PedidoInsumo pedidoM = new PedidoInsumo();
			pedidoM.setIdPedidoInsumo(pedido.getIdPedidoInsumo());
			pedidoInsumoService.obtenerPedidoDetallePorIdPedido(pedidoM);
			List<PedidoInsumoDetalle> listaPedidos = pedidoInsumoService.obtenerPedidoDetallePorIdPedido(pedidoM);
			for (PedidoInsumoDetalle pedidoInsumoDetalle : listaPedidos) {
				InsumoDTO insumoDTO = new InsumoDTO();
				Insumo insumo = new Insumo();
				insumo.setIdInsumo(pedidoInsumoDetalle.getInsumo().getIdInsumo());
				Insumo insumoM = insumoService.obtenerInsumoMaestro(insumo);
				insumoDTO.setIdInsumo(pedidoInsumoDetalle.getInsumo().getIdInsumo());
				insumoDTO.setNombre(insumoM.getNombre());
				insumoDTO.setCodigo(insumoM.getCodigoInsumo());
				insumoDTO.setPedidoInsumoDTO(pedido);
				CategoriaInsumoDTO categoriaInsumoDTO = new CategoriaInsumoDTO();
				CategoriaInsumo categoriaInsumo = new CategoriaInsumo();
				categoriaInsumo.setIdCategoriaInsumo(insumoM.getCategoriaInsumo().getId());
				categoriaInsumoDTO.setNombre(categoriaInsumoService.obtenerNombre(categoriaInsumo));
				ArbolCategoriaDTO arbolCategoriaDTO = new ArbolCategoriaDTO();
				arbolCategoriaDTO.setNombre(categoriaInsumoDTO.getNombre().get());
				insumoDTO.setCategoria(arbolCategoriaDTO);
				PedidoInsumoDetalleDTO detalleDTO = new PedidoInsumoDetalleDTO();
				detalleDTO.setInsumo(insumoDTO);
				detalleDTO.setPedidoInsumo(pedido);
				detalleDTO.setCantidad(pedidoInsumoDetalle.getCantidad());
				insumoDTO.setCantidad(pedidoInsumoDetalle.getCantidad());
				insumoDTO.setMaxCantidad(insumoDTO.getCantidad().get());
				List<PedidoInsumoDTO> listaPedidoDTO = this.masterDataPedidos;
				for (PedidoInsumoDTO pedidoInsumoDTO : listaPedidoDTO) {
					insumoDTO.setOrdenTrabajoDTO(pedidoInsumoDTO.getOrdenTrabajoDTO());
				}
				masterDataInsumos.add(insumoDTO);
			}
			this.masterDataPedidosDetalles.clear();
			this.tablaPedido.setItems(masterDataPedidos);
			this.txtBusquedaInsumo.setDisable(false);
			this.tablaInsumos.setDisable(false);
			this.tablaInsumosCantidades.setDisable(false);
			columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().getCantidad().asString());
			columnaCantidad.setVisible(true);
			iniciarTablaInsumos();
		} else {
			Notifications.create().title("Atencion").text("Seleccione el Pedido a Procesar ").darkStyle().showWarning();

		}

	}

	@FXML
	void tipoDeIngreso(ActionEvent event) {
		if (comboTipoDeIngreso.getValue().equals("Pedido")) {
			this.btnProcesarPedido.setDisable(false);
			this.tablaPedido.setDisable(false);
			this.txtBusquedaPedido.setDisable(false);
			this.tablaInsumos.setDisable(true);
			this.txtBusquedaInsumo.setDisable(true);
			this.textCodigoOrden.setDisable(true);
			this.tablaInsumosCantidades.setDisable(true);
			this.columnaCantidad.setVisible(true);
			this.btnObtenerOrdenes.setDisable(true);
			this.textCodigoOrden.setText("");
		} else {
			this.tablaPedido.setDisable(true);
			this.txtBusquedaPedido.setDisable(false);
			this.btnProcesarPedido.setDisable(true);
			this.columnaCantidad.setVisible(false);
		}
		if (comboTipoDeIngreso.getValue().equals("Caja Chica")) {
			this.tablaInsumos.setDisable(false);
			this.txtBusquedaInsumo.setDisable(false);
			this.tablaInsumosCantidades.setDisable(false);
			this.txtBusquedaPedido.setDisable(true);
			this.textCodigoOrden.setDisable(true);
			this.columnaCantidad.setVisible(false);
			this.btnObtenerOrdenes.setDisable(true);
			this.textCodigoOrden.setText("");
		}
		if (comboTipoDeIngreso.getValue().equals("Orden de Trabajo")) {
			this.textCodigoOrden.setDisable(true);
			this.tablaInsumos.setDisable(false);
			this.tablaInsumosCantidades.setDisable(false);
			this.txtBusquedaInsumo.setDisable(false);
			this.txtBusquedaPedido.setDisable(true);
			this.columnaCantidad.setVisible(false);
			this.btnObtenerOrdenes.setDisable(false);
		}
	}

	private void llenarTablaInsumos() {
		InsumoService insumoService = new InsumoService(new DAOSQLFactory());
		List<Insumo> listaInsumos = insumoService.obtenerInsumos();
		for (Insumo insumo : listaInsumos) {
			InsumoDTO insumoDTO = insumo.getInsumoDTO();
			masterDataInsumos.add(insumoDTO);
		}
		iniciarTablaInsumos();
	}

	private void iniciarTablaInsumos() {
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().getCodigo());
		columnaCategoria.setCellValueFactory(cellData -> cellData.getValue().getCategoria().getNombreProperty());
		FilteredList<InsumoDTO> filteredData = new FilteredList<>(masterDataInsumos, p -> true);
		txtBusquedaInsumo.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(insumo -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (insumo.getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (insumo.getCodigo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (insumo.getCategoria().getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false; // Does not match.
			});
		});
		SortedList<InsumoDTO> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablaInsumos.comparatorProperty());
		tablaInsumos.setItems(sortedData);
	}

	private void llenarTablaPedidos() {
		PedidoInsumoService pedidoInsumoService = new PedidoInsumoService(new DAOSQLFactory());
		InsumoService insumoService = new InsumoService(new DAOSQLFactory());
		List<PedidoInsumo> listaPedidos = pedidoInsumoService.obtenerPedidoInsumo();
		for (PedidoInsumo pedidoInsumo : listaPedidos) {
			PedidoInsumoDTO pedidoInsumoDTO = new PedidoInsumoDTO();
			pedidoInsumoDTO.setIdPedidoInsumo(pedidoInsumo.getIdPedidoInsumo());
			pedidoInsumoDTO.setProveedor(pedidoInsumo.getProveedor());
			pedidoInsumoDTO.setNroOrdenCompra(pedidoInsumo.getNumeroOrdenCompra());
			pedidoInsumoDTO.setFechaSolicitud(Fechas.CalendarTolocalDate(pedidoInsumo.getFechaSolicitud()));
			pedidoInsumoDTO.setRecibido(pedidoInsumoDTO.obtenerEstado(pedidoInsumo.getRecibido().getValor()));
			OrdenTrabajoDTO ordenTrabajoDTO = new OrdenTrabajoDTO();
			if (!pedidoInsumo.getOrdenDeTrabajo().equals(null)) {
				ordenTrabajoDTO.setId(pedidoInsumo.getOrdenDeTrabajo().getId());
				ordenTrabajoDTO.setIdOrdenTrabajo(pedidoInsumoService.obtenerIdMantis(pedidoInsumo));
				pedidoInsumoDTO.setOrdenTrabajoDTO(ordenTrabajoDTO);
			}
			this.masterDataPedidos.add(pedidoInsumoDTO);
			List<PedidoInsumoDetalle> listaPedidosInsumoDetalles = pedidoInsumoService
					.obtenerPedidoDetallePorIdPedido(pedidoInsumo);
			for (PedidoInsumoDetalle pedidoInsumoDetalle : listaPedidosInsumoDetalles) {
				PedidoInsumoDetalleDTO pedidoInsumoDetalleDTO = new PedidoInsumoDetalleDTO();
				Insumo insumo = new Insumo();
				insumo.setIdInsumo(pedidoInsumoDetalle.getInsumo().getIdInsumo());
				Insumo insumoMaestro = insumoService.obtenerInsumoMaestro(insumo);
				InsumoDTO insumoDTO = new InsumoDTO();
				insumoDTO.setIdInsumo(insumoMaestro.getIdInsumo());
				insumoDTO.setNombre(insumoMaestro.getNombre());
				insumoDTO.setCodigo(insumoMaestro.getCodigoInsumo());
				ArbolCategoriaDTO arbolCategoriaDTO = new ArbolCategoriaDTO();
				arbolCategoriaDTO.setId(insumoMaestro.getCategoriaInsumo().getId());
				CategoriaInsumoService categoriaInsumoService = new CategoriaInsumoService(new DAOSQLFactory());
				CategoriaInsumo categoriaInsumo = new CategoriaInsumo();
				categoriaInsumo.setIdCategoriaInsumo(insumoMaestro.getCategoriaInsumo().getId());
				String nombreCategoria = categoriaInsumoService.obtenerNombre(categoriaInsumo);
				arbolCategoriaDTO.setNombre(nombreCategoria);
				categoriaInsumo.setNombre(nombreCategoria);
				insumoDTO.setCategoria(arbolCategoriaDTO);
				pedidoInsumoDetalleDTO.setInsumo(insumoDTO);
				pedidoInsumoDetalleDTO.setCantidad(pedidoInsumoDetalle.getCantidad());
				pedidoInsumoDetalleDTO.setPedidoInsumo(pedidoInsumoDTO);
				this.masterDataPedidosDetalles.add(pedidoInsumoDetalleDTO);
			}
		}
		columnaFechaSolicitud.setCellValueFactory(cellData -> cellData.getValue().getFechaSol());
		columnaPedidoProveedor.setCellValueFactory(cellData -> cellData.getValue().getProveedorProperty());
		columnaNumeroOrdenPedido
				.setCellValueFactory(cellData -> cellData.getValue().getNroOrdenCompraProperty().asString());
		columnaPedidoIdMantis
				.setCellValueFactory(cellData -> cellData.getValue().getOrdenTrabajoDTO().getIdOrdenTrabajo());
		columnaPedidoEstado.setCellValueFactory(cellData -> cellData.getValue().getRecibido().getEstado());
		FilteredList<PedidoInsumoDTO> filteredData = new FilteredList<>(masterDataPedidos, p -> true);
		txtBusquedaPedido.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(pedido -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (pedido.getRecibido().getEstado().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (pedido.getNroOrdenCompra().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (pedido.getProveedor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false;
			});
		});
		SortedList<PedidoInsumoDTO> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablaPedido.comparatorProperty());
		tablaPedido.setItems(sortedData);
	}

	private void llenarTablaInsumosCantidades() {
		this.tablaInsumosCantidades.setEditable(true);
		columnaNombreDerecha.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaCodigoDerecha.setCellValueFactory(cellData -> cellData.getValue().getCodigo());
		columnaCantidadDerecha.setCellValueFactory(new PropertyValueFactory<>("spnCantidad"));
		this.tablaInsumosCantidades.setItems(masterDataInsumoCantidades);

	}

	void transferenciaTabla(ObservableList<InsumoDTO> baseRemover, ObservableList<InsumoDTO> baseAdicionar,
			TableView<InsumoDTO> tablaRemover) {

		InsumoDTO insumoDTO = tablaRemover.getSelectionModel().getSelectedItem();
		tablaRemover.getSelectionModel().clearSelection();
		baseRemover.remove(insumoDTO);
		baseAdicionar.add(insumoDTO);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inicializar();

	}

	private void inicializar() {
		columnaCantidad.setVisible(false);
		this.tablaPedido.setDisable(true);
		this.btnObtenerOrdenes.setDisable(true);
		this.columnaCantidad.setVisible(false);
		this.tablaInsumosCantidades.setDisable(true);
		this.textCodigoOrden.setDisable(true);
		this.tablaInsumos.setDisable(true);
		this.txtBusquedaInsumo.setDisable(true);
		this.txtBusquedaPedido.setDisable(true);
		this.btnEnviar.setDisable(false);
		this.btnDevolver.setDisable(false);
		AdministracionService administracionService = new AdministracionService(new DAOSQLFactory());
		List<UbicacionDeposito> ubicaciones = administracionService.obtenerUbicacionDepositos();
		for (UbicacionDeposito ubicacionDeposito : ubicaciones) {
			Deposito deposito = new Deposito();
			deposito.setIdDeposito(ubicacionDeposito.getIdUbicacionDeposito());
			deposito.setNombre(administracionService.obtenerNombreDeposito(deposito));
			DepositoDTO depositoDTO = new DepositoDTO();
			depositoDTO.setIdDeposito(deposito.getIdDeposito());
			depositoDTO.setNombre(deposito.getNombre());
			this.comboDeposito.getItems().add(depositoDTO);
		}
		this.comboTipoDeIngreso.getItems().addAll("Caja Chica", "Pedido", "Orden de Trabajo");
		this.datePickerFecha.setValue(LocalDate.now());
		this.llenarTablaPedidos();
		this.llenarTablaInsumos();
		this.llenarTablaInsumosCantidades();
	}

	public TextField getTextCodigoOrden() {
		return textCodigoOrden;
	}

	public void setTextCodigoOrden(TextField textCodigoOrden) {
		this.textCodigoOrden = textCodigoOrden;
	}
	public void CargarBotonIngresoStock(Button btnIngresarStock) {
		this.btnIngresoStock = btnIngresarStock;
		
	}
}
