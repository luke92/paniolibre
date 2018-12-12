package presentacion.controladores;

import java.io.IOException;
import java.util.List;

import domain.model.InsumoDeposito;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import services.InsumoDepositoService;

public class ControladorInsumoDeposito {

	@FXML
	private ScrollPane panelInsumoDeposito;
	@FXML
	private TableView<InsumoDeposito> tablaInsumoDeposito;
	@FXML
	private TableColumn<InsumoDeposito, String> columnaDepositoID;
	@FXML
	private TableColumn<InsumoDeposito, String> columnaInsumoID;
	@FXML
	private TableColumn<InsumoDeposito, String> columnaUbicacionID;
	@FXML
	private TableColumn<InsumoDeposito, String> columnaStockNuevo;
	@FXML
	private TableColumn<InsumoDeposito, String> columnaStockUsado;
	@FXML
	private TableColumn<InsumoDeposito, String> columnaStockReservado;
	@FXML
	private Button btnAgregarInsumoDeposito;
	@FXML
	private Button btnEliminarInsumoDeposito;
	@FXML
	private Button btnEditarInsumoDeposito;
	@FXML
	private TextField txtBusqueda;

	private ObservableList<InsumoDeposito> masterData = FXCollections.observableArrayList();
	private InsumoDepositoService insumoDepositoService = new InsumoDepositoService(new DAOSQLFactory());

	@FXML
	void agregarInsumoDeposito(MouseEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/vistas/agregarinsumodeposito.fxml"));
			Scene scene = new Scene(root);
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
	void borrarInsumoDeposito(MouseEvent event) {
		// TODO Completar o quitar metodo
	}

	@FXML
	void editarInsumoDeposito(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/editarinsumodeposito.fxml"));
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

	public void llenarTabla() {
		List<InsumoDeposito> insumosDeposito = InsumoDepositoService.obtenerInsumos();
		for (InsumoDeposito insumoDeposito : insumosDeposito) {
			this.masterData.add(insumoDeposito);
		}
		this.tablaInsumoDeposito.setItems(masterData);
	}

	// VENTANA AGREGAR Insumo Deposito
	@FXML
	private DialogPane ventanaAgregarInsumoDeposito;
	@FXML
	private TextField textDepositoId;
	@FXML
	private TextField textInsumoId;
	@FXML
	private TextField textUbicacionId;
	@FXML
	private TextField textStockNuevo;
	@FXML
	private TextField textStockUsado;
	@FXML
	private TextField textStockReservado;
	@FXML
	private Button btnConfirmarInsumoDeposito;
	@FXML
	private Button btnDescartarInsumoDeposito;

	@FXML
	void confirmarAgregarInsumoDeposito(MouseEvent event) {
		if (validarCampos()) {
			Stage stage = (Stage) this.ventanaAgregarInsumoDeposito.getScene().getWindow();
			stage.close();
		}
	}

	private boolean validarCampos() {
		// TODO Completar o quitar metodo
		return true;
	}

	@FXML
	void descartarInsumoDeposito(MouseEvent event) {
		Stage stage = (Stage) this.ventanaAgregarInsumoDeposito.getScene().getWindow();
		stage.close();
	}

	///////////////////////////////////////////////////////////////////////////////

	@FXML
	void validarAgregarPedido() {
		// TODO Completar o quitar metodo
	}

}
