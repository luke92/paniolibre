package presentacion.controladores;

import java.io.IOException;
import java.util.List;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Deposito;
import domain.model.Insumo;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroInsumo;
import domain.model.Tecnico;
import domain.model.Usuario;
import domain.model.UsuarioLogueado;
import dto.InsumoDepositoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controladores.insumos.ControladorUbicacionInsumos;
import services.InsumoDepositoService;
import services.OrdenDeTrabajoService;
import services.RetiroInsumoService;
import services.TecnicoService;
import util.CadenasTexto;
import util.Dialogos;
import util.ExpReg;

public class ControladorStockInsumos {

	@FXML
	private ScrollPane panelRetiro;

	@FXML
	private BorderPane panelSecundario2;

	@FXML
	private Button btnDevolverStock;

	@FXML
	private TableView<InsumoDepositoDTO> tablaOrdenDeTrabajo;

	@FXML
	private TableColumn<InsumoDepositoDTO, String> columnaCodigoInsumo;

	@FXML
	private TableColumn<InsumoDepositoDTO, String> columnaNombreInsumo;

	@FXML
	private TableColumn<InsumoDepositoDTO, String> columnaStockNuevo;

	@FXML
	private TableColumn<InsumoDepositoDTO, String> columnaStockUsado;

	@FXML
	private TableColumn<InsumoDepositoDTO, String> columnaStockReservado;

	@FXML
	private TableColumn<InsumoDepositoDTO, String> columnaUbicacion;

	@FXML
	private TableColumn<InsumoDepositoDTO, String> columnaDeposito;

	private ObservableList<InsumoDepositoDTO> masterData = FXCollections.observableArrayList();

	private InsumoDepositoService service = new InsumoDepositoService(new DAOSQLFactory());

	@FXML
	private Button btnRetirarStock;

	@FXML
	private Button btnIngresarStock;

	@FXML
	private Button btnAjustarStock;

	@FXML
	private TextField txtBuscqueda;

	@FXML
	private Button btnUbicaciones;

	@FXML
	private Button btnModificarUbicaciones;

	ValidationSupport validationSupport = new ValidationSupport();

	private String error = "Error";

	@FXML
	void devolucionStock(MouseEvent event) {
		FXMLLoader root;

		root = new FXMLLoader(getClass().getResource("/vistas/insumos/insumo_devoluciones2.fxml"));
		try {
			Scene scene = new Scene(root.load());
			this.panelSecundario2.setCenter(scene.getRoot());

		} catch (IOException e) {
			Dialogos.error(error, "No se puede abrir la pantalla de devoluciones",
					"Hubo una falla para abrir la pantalla de devoluciones de insumos");
		}

	}

	@FXML
	void retirarStock(MouseEvent event) {
		FXMLLoader root;

		root = new FXMLLoader(getClass().getResource("/vistas/insumos/insumo_retiro_dialog2.fxml"));
		try {
			Scene scene = new Scene(root.load());
			this.panelSecundario2.setCenter(scene.getRoot());

		} catch (IOException e) {
			Dialogos.error(error, "No se puede abrir la pantalla de retiros",
					"Hubo una falla para abrir la pantalla de retiros de insumos");
		}

	}

	@FXML
	void abrirPanelUbicaciones(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/insumos/insumo_ubicacion.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.initModality(Modality.APPLICATION_MODAL);
			
			ControladorUbicacionInsumos controlador = root.getController();
			controlador.prepararVentanaAgregar();
			stage.showAndWait();
			llenarTabla();
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
			Dialogos.error("Error", "No se puede abrir la pantalla de ubicaciones de insumos",
					"Hubo una falla para abrir la pantalla de ubicaciones de insumos");
		}

	}

	@FXML
	void abrirPanelModificarUbicaciones(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/insumos/insumo_ubicacion.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.initModality(Modality.APPLICATION_MODAL);

			ControladorUbicacionInsumos controlador = root.getController();
			controlador.prepararVentanaModificar(obtenerInsumoSeleccionado());
			stage.showAndWait();
			llenarTabla();
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
			Dialogos.error("Error", "No se puede abrir la pantalla de ubicaciones de insumos",
					"Hubo una falla para abrir la pantalla de ubicaciones de insumos");
		}

	}

	@FXML
	void ingresarStock(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/insumos/ingreso_de_stock_sin_terminar.fxml"));
			Scene scene = new Scene(root.load());
			this.panelSecundario2.setCenter(scene.getRoot());

		} catch (IOException e) {
			Dialogos.error(error, "No se puede abrir la pantalla de Ingreso de stock",
					"Hubo una falla para abrir la pantalla de Ingreso de stock");
		}
	}

	@FXML
	void filaSeleccionada(MouseEvent event) {
		if (hayFilaSeleccionada()) {
			btnAjustarStock.setDisable(false);
			btnModificarUbicaciones.setDisable(false);
		} else {
			btnAjustarStock.setDisable(true);
			btnModificarUbicaciones.setDisable(true);
		}
	}

	@FXML
	void ajustarStock(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/agregarAjusteStock2.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);

			ControladorAjusteStock controlador = root.getController();

			controlador.cargarCampos(obtenerInsumoSeleccionado());

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			llenarTabla();
			
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
			Dialogos.error(error, "No se puede abrir la pantalla de Ajuste de Stock",
					"Hubo una falla para abrir la pantalla de Ajuste de Stock");

		}
	}

	void llenarTabla() {
		masterData.clear();
		List<InsumoDepositoDTO> stock = service.obtenerInsumosDTO();
		for (InsumoDepositoDTO ordenDeTrabajo : stock) {
			masterData.add(ordenDeTrabajo);
		}

		columnaCodigoInsumo.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getCodigo());
		columnaNombreInsumo.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getNombre());
		columnaStockNuevo.setCellValueFactory(cellData -> cellData.getValue().getStockNuevo().asString());
		columnaStockUsado.setCellValueFactory(cellData -> cellData.getValue().getStockUsado().asString());
		columnaStockReservado.setCellValueFactory(cellData -> cellData.getValue().getStockReservado().asString());
		columnaUbicacion.setCellValueFactory(cellData -> cellData.getValue().getUbicacion().getNombre());
		columnaDeposito.setCellValueFactory(cellData -> cellData.getValue().getUbicacion().getDeposito().getNombre());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<InsumoDepositoDTO> filteredData = new FilteredList<>(masterData, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		txtBuscqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(insumoDeposito -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (insumoDeposito.getInsumo().getCodigo().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (insumoDeposito.getInsumo().getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (insumoDeposito.getUbicacion().getNombre().get().toLowerCase()
						.indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (Integer.toString(insumoDeposito.getStockNuevo().get()).toLowerCase()
						.indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (Integer.toString(insumoDeposito.getStockReservado().get()).toLowerCase()
						.indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (Integer.toString(insumoDeposito.getStockUsado().get()).toLowerCase()
						.indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (insumoDeposito.getUbicacion().getDeposito().getNombre().get().toLowerCase()
						.indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<InsumoDepositoDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaOrdenDeTrabajo.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaOrdenDeTrabajo.setItems(sortedData);
	}

	////////////// VENTANA RETIRO
	////////////// ///////////////////////////////////////////////////

	@FXML
	private DialogPane ventanaRetiro;

	@FXML
	private TextField textNombreInsumo;

	@FXML
	private TextField textCantNuevo;

	@FXML
	private Button btnRetirar;

	@FXML
	private Button btnDescartar;

	@FXML
	private ChoiceBox<Tecnico> chbTecnico;

	@FXML
	private TextField textNombreDeposito;

	@FXML
	private TextField textCantUsado;

	@FXML
	private TextField textNumeroOrden;

	private InsumoDepositoDTO insumo;

	RetiroInsumoService serviceRetiro = new RetiroInsumoService(new DAOSQLFactory());

	TecnicoService tecnicoService = new TecnicoService(new DAOSQLFactory());

	@FXML
	void agregarInsumoMaestro(MouseEvent event) {
		/// VALIDAR NUMERO DE ORDEN DE TRABAJO
		String errorAgregar = "";
		RetiroInsumo retiroInsumo = new RetiroInsumo();
		Deposito deposito = new Deposito();
		deposito.setIdDeposito(insumo.getUbicacion().getDeposito().getIdDeposito().get());
		retiroInsumo.setDeposito(deposito);
		UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstancia();
		Usuario user = usuarioLogueado.getUsuarioLogueado();
		retiroInsumo.setUsuario(user);
		Insumo insumoModel = new Insumo();
		insumoModel.setIdInsumo(insumo.getInsumo().getIdInsumo().get());
		retiroInsumo.setInsumo(insumoModel);

		if (!ExpReg.contieneSoloNumeros(CadenasTexto.borrarEspacios(textCantNuevo.getText()))) {
			errorAgregar += "El campo Cantidad nuevo debe ser numero \n";
		} else {
			retiroInsumo.setCantidadNueva(Integer.parseInt(textCantNuevo.getText()));
		}

		if (!CadenasTexto.borrarEspacios(textNumeroOrden.getText()).isEmpty()) {
			if (!ExpReg.contieneSoloNumeros(CadenasTexto.borrarEspacios(textNumeroOrden.getText()))) {
				errorAgregar += "El campo Nro de orden de trabajo debe ser numero \n";
			} else {

				// VALIDAR QUE EXISTA LA ORDEN DE TRABAJO
				OrdenDeTrabajo ot = new OrdenDeTrabajo();
				String myString = textNumeroOrden.getText();
				int idOt = Integer.parseInt(myString);
				ot.setId(idOt);
				ot.setIdOrdenDeTrabajo(textNumeroOrden.getText());
				OrdenDeTrabajoService ordenTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());
				OrdenDeTrabajo ordenDeTrabajo = ordenTrabajoService.obtenerOrdenDeTrabajo(ot);
				if (ordenDeTrabajo != null)
					retiroInsumo.setOrdenDeTrabajo(ordenDeTrabajo);
				else
					errorAgregar += "El nro de orden de trabajo no existe \n";

				if (insumo.getStockReservado().get() < retiroInsumo.getCantidadNueva())
					errorAgregar += "El stock reservado es menor del que se puede retirar \n";

			}
		} else {
			if (!CadenasTexto.borrarEspacios(textCantUsado.getText()).isEmpty()) {
				if (!ExpReg.contieneSoloNumeros(CadenasTexto.borrarEspacios(textCantUsado.getText()))) {
					errorAgregar += "El campo cantidad Usado debe ser numero \n";
				} else {
					retiroInsumo.setCantidadUsado(Integer.parseInt(textCantUsado.getText()));
					if (insumo.getStockUsado().get() < retiroInsumo.getCantidadUsado())
						errorAgregar += "El stock usado es menor del que se puede retirar \n";
				}
			}

			if (ExpReg.contieneSoloNumeros(CadenasTexto.borrarEspacios(textCantNuevo.getText()))
					&& (insumo.getStockNuevo().get() < retiroInsumo.getCantidadNueva()))
				errorAgregar += "El stock nuevo es menor del que se puede retirar \n";

		}

		Tecnico tecnico = chbTecnico.getSelectionModel().getSelectedItem();
		retiroInsumo.setTecnico(tecnico);

		if (errorAgregar == "") {
			serviceRetiro.agregarRetiroInsumo(retiroInsumo);
		} else {
			Dialogos.error("Error al Retirar Insumo", "Revise los datos ingresados", errorAgregar);
		}

		cerrarVentanaRetiro();
	}

	@FXML
	void descartarInsumoMaestro(MouseEvent event) {
		cerrarVentanaRetiro();
	}

	@FXML
	void validarRetiro(KeyEvent event) {
		if (!textCantNuevo.getText().isEmpty() && !textCantUsado.getText().isEmpty() && chbTecnico.getValue() != null) {
			btnRetirar.setDisable(false);
		} else {
			btnRetirar.setDisable(true);
		}

		String campoRequerido = "Campo requerido";
		validationSupport.registerValidator(textCantNuevo, Validator.createEmptyValidator(campoRequerido));
		validationSupport.registerValidator(textCantUsado, Validator.createEmptyValidator(campoRequerido));
		validationSupport.registerValidator(chbTecnico, Validator.createEmptyValidator(campoRequerido));
	}

	void cargarCampos(InsumoDepositoDTO insumo) {
		this.insumo = insumo;
		textNombreInsumo.setText(insumo.getInsumo().getNombre().get());
		textNombreDeposito.setText(insumo.getUbicacion().getDeposito().getNombre().get());
		for (int i = 0; i < tecnicoService.obtenerTecnicos().size(); i++) {
			chbTecnico.getItems().add(tecnicoService.obtenerTecnicos().get(i));
		}
		chbTecnico.getSelectionModel().selectFirst();
	}

	private void cerrarVentanaRetiro() {
		Stage stage = (Stage) ventanaRetiro.getScene().getWindow();
		stage.close();
	}
	
	private InsumoDepositoDTO obtenerInsumoSeleccionado()
	{
		return masterData.get(tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex());
	}
	
	private boolean hayFilaSeleccionada()
	{
		return (tablaOrdenDeTrabajo.getSelectionModel().getSelectedIndex() != -1);
	}

	//////////////////////////////////////////
}