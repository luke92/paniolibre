package presentacion.controladores.insumos;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import domain.model.Deposito;
import domain.model.Estado;
import domain.model.Insumo;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroInsumo;
import domain.model.Tecnico;
import domain.model.Usuario;
import domain.model.UsuarioLogueado;
import dto.InsumoDepositoSpinnerDTO;
import dto.OrdenTrabajoDTO;
import dto.TecnicoDTO;
import javafx.beans.InvalidationListener;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
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
import services.InsumoDepositoService;
import services.MailService;
import services.OrdenDeTrabajoService;
import services.RetiroInsumoService;
import services.TecnicoService;
import util.Dialogos;

public class StockInsumosControlador2 implements Initializable {

	@FXML
	private DialogPane ventanaRetiro;

	@FXML
	private Button btnRetirar;

	@FXML
	private Button btnDescartar;

	@FXML
	private ChoiceBox<Tecnico> chbTecnico;

	@FXML
	private TextField textNumeroOrden;

	@FXML
	private BorderPane panelHerramientasReparacion;

	@FXML
	private TableView<InsumoDepositoSpinnerDTO> tablaHerramientas;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, String> columnaNombreIzquuierda;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, Integer> columnaStockNuevoIzquierda;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, Integer> columnaStockUsadoIzquierda;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, Integer> columnaStockReservadoIzquierda;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, String> columnaUbicacionIzquierda;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, String> columnaDepositoIzquierda;

	@FXML
	private TextField txtBusqueda;

	@FXML
	private BorderPane panelHerramientasReparacion1;

	@FXML
	private TableView<InsumoDepositoSpinnerDTO> tablaHerramientas1;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, String> columnaNombreDerecha;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, Integer> columnaStockNuevoDerecha;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, Integer> columnaStockUsadoDerecha;

	@FXML
	private TableColumn<InsumoDepositoSpinnerDTO, Integer> columnaStockReservadoDerecha;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnDevolver;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnObtenerOrdenes;
	
	private OrdenTrabajoDTO orden;

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
			this.textNumeroOrden.setText(orden.getIdOrdenTrabajo().get());
			this.chbTecnico.getItems().clear();
			OrdenDeTrabajoService ordenDeTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());
			OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
			ordenDeTrabajo.setId(orden.getId().get());
			ordenDeTrabajo.setIdOrdenDeTrabajo(orden.getIdOrdenTrabajo().get());
			List<TecnicoDTO> tecnicos = ordenDeTrabajoService.obtenerTecnicos(ordenDeTrabajo);
			for (TecnicoDTO tecnicoDTO : tecnicos) {
				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(tecnicoDTO.getIdTecnico().get());
				tecnico.setNombre(tecnicoDTO.getNombre().get());
				this.chbTecnico.getItems().add(tecnico);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private ObservableList<InsumoDepositoSpinnerDTO> masterDataDerecha = FXCollections.observableArrayList();

	private ObservableList<InsumoDepositoSpinnerDTO> masterDataIzquierda = FXCollections.observableArrayList();

	private TecnicoService serviceTecnico = new TecnicoService(new DAOSQLFactory());

	private MailService serviceMail = new MailService(new DAOSQLFactory());

	private InsumoDepositoService serviceInsumoDeposito = new InsumoDepositoService(new DAOSQLFactory());

	private OrdenDeTrabajoService serviceOrdenDeTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());

	private RetiroInsumoService serviceRetiroInsumo = new RetiroInsumoService(new DAOSQLFactory());

	private OrdenDeTrabajo ordenBusqueda;

	@FXML
	void buscar(MouseEvent event) {
		chbTecnico.getItems().clear();
		if (!textNumeroOrden.getText().equals("")) {
			ordenBusqueda = new OrdenDeTrabajo();
			ordenBusqueda.setIdOrdenDeTrabajo(textNumeroOrden.getText());
			int idOrden = serviceOrdenDeTrabajoService.obtenerIdOrden(ordenBusqueda);
			ordenBusqueda.setId(idOrden);
			ordenBusqueda = serviceOrdenDeTrabajoService.obtenerOrdenDeTrabajo(ordenBusqueda);
			OrdenDeTrabajo ot = new OrdenDeTrabajo();
			ot.setIdOrdenDeTrabajo(this.textNumeroOrden.getText());
			ot.setId(idOrden);
			List<TecnicoDTO> listaTecnicos = serviceOrdenDeTrabajoService.obtenerTecnicos(ot);
			for (TecnicoDTO tecnicoDTO : listaTecnicos) {
				Tecnico tecnico = new Tecnico();
				tecnico.setIdTecnico(tecnicoDTO.getIdTecnico().get());
				tecnico.setNombre(tecnicoDTO.getNombre().get());
				this.chbTecnico.getItems().add(tecnico);
			}
		} else {
			Dialogos.advertencia("Alerta!", "La orde de trabajo no existe", "");
			this.CargarTecnicos();
			ordenBusqueda = null;
			textNumeroOrden.clear();
			this.llenarMasterDataIzquierda();
		}

	}

	@FXML
	void descartar(MouseEvent event) {
		Stage stage = (Stage) ventanaRetiro.getScene().getWindow();
		stage.close();
	}

	@FXML
	void devolver(MouseEvent event) {
		InsumoDepositoSpinnerDTO insumoDepositoSpinnerDTO = tablaHerramientas1.getSelectionModel().getSelectedItem();
		if (insumoDepositoSpinnerDTO != null)
			transferenciaTabla(masterDataDerecha, masterDataIzquierda, tablaHerramientas1);
	}

	@FXML
	void enviar(MouseEvent event) {
		InsumoDepositoSpinnerDTO insumoDepositoSpinnerDTO = tablaHerramientas.getSelectionModel().getSelectedItem();
		if (insumoDepositoSpinnerDTO != null)
			transferenciaTabla(masterDataIzquierda, masterDataDerecha, tablaHerramientas);

	}

	@FXML
	void retirar(MouseEvent event) {
		if (this.validadciones()) {

			// LLAMAR AL SERVICE
			for (InsumoDepositoSpinnerDTO insumo : masterDataDerecha) {
				RetiroInsumo retiroInsumo = new RetiroInsumo();
				Usuario user = new Usuario();
				UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstancia();
				retiroInsumo.setUsuario(usuarioLogueado.getUsuarioLogueado());
				retiroInsumo.setCantidadNueva(insumo.getValorSpinnerCantNuevo());
				retiroInsumo.setCantidadUsado(insumo.getValorSpinnerCantUsado());
				retiroInsumo.setCantidadReservada(insumo.getValorSpinnerCantReservado());
				Deposito deposito = new Deposito();
				deposito.setIdDeposito(insumo.getUbicacion().getDeposito().getIdDeposito().get());
				deposito.setNombre(insumo.getUbicacion().getDeposito().getNombre().get());
				deposito.setActivo(Estado.ALTA);
				retiroInsumo.setDeposito(deposito);
				Insumo ins = new Insumo();
				ins.setIdInsumo(insumo.getInsumo().getIdInsumo().get());
				retiroInsumo.setInsumo(ins);
				retiroInsumo.setTecnico(chbTecnico.getSelectionModel().getSelectedItem());
				if (!textNumeroOrden.getText().equals("")) {
					OrdenDeTrabajo ot = new OrdenDeTrabajo();
					ot.setIdOrdenDeTrabajo(this.textNumeroOrden.getText());
					int idOrden = serviceOrdenDeTrabajoService.obtenerIdOrden(ot);
					ot.setId(idOrden);
					retiroInsumo.setOrdenDeTrabajo(ot);
				}
				serviceRetiroInsumo.agregarRetiroInsumo(retiroInsumo);
			}

			if (textNumeroOrden.getText().isEmpty()) {
				this.alertas(masterDataDerecha);
			}
			this.llenarMasterDataIzquierda();
			ordenBusqueda = null;
			textNumeroOrden.clear();
		} else {
			// hubo algun error
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tablaHerramientas.setOnMouseClicked(event -> {
			if (tablaHerramientas.getSelectionModel().getSelectedIndex() != -1) {
				btnEnviar.setDisable(false);
				btnDevolver.setDisable(true);
			}

		});

		tablaHerramientas1.setOnMouseClicked(event -> {
			if (tablaHerramientas1.getSelectionModel().getSelectedIndex() != -1) {
				btnEnviar.setDisable(true);
				btnDevolver.setDisable(false);
			}

		});

		masterDataDerecha.addListener((InvalidationListener) observable -> {
			if (masterDataDerecha.isEmpty()) {
				btnRetirar.setDisable(true);
			} else {
				btnRetirar.setDisable(false);
			}
		});

		List<InsumoDepositoSpinnerDTO> listaInsumosDepositoSpinner = serviceInsumoDeposito
				.obtenerInsumosDepositoSpinnerDTO();
		for (InsumoDepositoSpinnerDTO i : listaInsumosDepositoSpinner) {
			masterDataIzquierda.add(i);
		}

		columnaNombreIzquuierda.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getNombre());
		columnaStockNuevoIzquierda.setCellValueFactory(new PropertyValueFactory<>("stockNuevo"));
		columnaStockUsadoIzquierda.setCellValueFactory(new PropertyValueFactory<>("stockUsado"));
		columnaStockReservadoIzquierda.setCellValueFactory(new PropertyValueFactory<>("stockReservado"));
		columnaUbicacionIzquierda.setCellValueFactory(cellData -> cellData.getValue().getUbicacion().getNombre());
		columnaDepositoIzquierda
				.setCellValueFactory(cellData -> cellData.getValue().getUbicacion().getDeposito().getNombre());

		tablaHerramientas.setItems(masterDataIzquierda);

		columnaNombreDerecha.setCellValueFactory(cellData -> cellData.getValue().getInsumo().getNombre());
		columnaStockNuevoDerecha.setCellValueFactory(new PropertyValueFactory<>("spnCantidadNuevo"));
		columnaStockUsadoDerecha.setCellValueFactory(new PropertyValueFactory<>("spnCantidadUsado"));

		tablaHerramientas1.setItems(masterDataDerecha);

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<InsumoDepositoSpinnerDTO> filteredData = new FilteredList<>(masterDataIzquierda, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(insumo -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (insumo.getInsumo().getNombre().getValue().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (insumo.getUbicacion().getNombre().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (insumo.getUbicacion().getDeposito().getNombre().get().toLowerCase()
						.indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<InsumoDepositoSpinnerDTO> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tablaHerramientas.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaHerramientas.setItems(sortedData);

		this.CargarTecnicos();
	}

	private void transferenciaTabla(ObservableList<InsumoDepositoSpinnerDTO> baseRemover,
			ObservableList<InsumoDepositoSpinnerDTO> baseAdicionar, TableView<InsumoDepositoSpinnerDTO> TablaRemover) {

		InsumoDepositoSpinnerDTO insumoDepositoSpinnerDTO = TablaRemover.getSelectionModel().getSelectedItem();
		TablaRemover.getSelectionModel().clearSelection();
		baseRemover.remove(insumoDepositoSpinnerDTO);
		baseAdicionar.add(insumoDepositoSpinnerDTO);
	}

	private void alertas(ObservableList<InsumoDepositoSpinnerDTO> listaInsumoDepositoSpinnerDTO) {
		for (InsumoDepositoSpinnerDTO i : listaInsumoDepositoSpinnerDTO) {
			int total = i.getStockNuevo() + i.getStockUsado();
			int cantidadRetirada = i.getValorSpinnerCantNuevo() + i.getValorSpinnerCantUsado();
			int umbralminimoInsumo = i.getInsumo().getUmbralMinimo().get();
			if (total - cantidadRetirada == 0) {
				String mensaje = "Stock en 0 de:" + " '" + i.getInsumo().getNombre().get() + "'";
				Notifications.create().title("Alerta! Umbral Minimo").text(mensaje).darkStyle().showError();
			} else {
				if (total - cantidadRetirada <= umbralminimoInsumo) {
					String mensaje = "Stock limitado de" + " '" + i.getInsumo().getNombre().get() + "'";
					Notifications.create().title("Alerta! Umbral Minimo").text(mensaje).darkStyle().showWarning();
				}
			}
		}
	}

	private boolean validadciones() {

		boolean acumulador = true;

		if (this.cantidadNuevaEstaEnCero() && this.cantidadUsadaEstaEnCero()) {
			acumulador = acumulador && false;
			Dialogos.error("Error cantidad nueva", "Revise los datos ingresados",
					"El retiro de cantidad nueva no puede ser 0");
		} else {
			// TODO Completar o quitar ELSE
		}

		return acumulador;
	}

	private boolean cantidadNuevaEstaEnCero() {

		boolean acumulador = true;

		for (InsumoDepositoSpinnerDTO i : masterDataDerecha) {
			if (i.getValorSpinnerCantNuevo() == 0) {
				i.getSpnCantidadNuevo().getEditor().setStyle("-fx-background-color: red");
				acumulador = acumulador && true;
			} else
				acumulador = acumulador && false;
		}

		return acumulador;
	}

	private boolean cantidadUsadaEstaEnCero() {

		boolean acumulador = true;

		for (InsumoDepositoSpinnerDTO i : masterDataDerecha) {
			if (i.getValorSpinnerCantUsado().intValue() == 0) {
				i.getSpnCantidadUsado().getEditor().setStyle("-fx-background-color: red");
				acumulador = acumulador && true;
			} else
				acumulador = acumulador && false;
		}

		return acumulador;
	}

	private boolean cantidadReservadaEstaEnCero() {

		boolean acumulador = true;

		for (InsumoDepositoSpinnerDTO i : masterDataDerecha) {
			if (i.getValorSpinnerCantReservado().intValue() == 0) {
				i.getSpnCantidadReservado().getEditor().setStyle("-fx-background-color: red");
				acumulador = acumulador && true;
			} else
				acumulador = acumulador && false;
		}

		return acumulador;
	}

	private void llenarMasterDataIzquierda() {
		this.masterDataDerecha.clear();
		this.masterDataIzquierda.clear();
		List<InsumoDepositoSpinnerDTO> listaInsumosDepositoSpinner = serviceInsumoDeposito
				.obtenerInsumosDepositoSpinnerDTO();
		for (InsumoDepositoSpinnerDTO i : listaInsumosDepositoSpinner) {
			masterDataIzquierda.add(i);
		}
	}

	private void CargarTecnicos() {
		for (int i = 0; i < serviceTecnico.obtenerTecnicos().size(); i++) {
			chbTecnico.getItems().add(serviceTecnico.obtenerTecnicos().get(i));
		}
		chbTecnico.getSelectionModel().selectFirst();
	}
}