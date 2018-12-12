package presentacion.controladores.administracion;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import domain.model.Deposito;
import domain.model.UbicacionDeposito;
import dto.DepositoDTO;
import dto.UbicacionDepositoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AdministracionService;
import util.Dialogos;

public class ControladorABMUbicaciones implements Initializable {

	@FXML
	private ScrollPane panelUbicaciones;

	@FXML
	private TableView<UbicacionDepositoDTO> tablaUbicacion;

	@FXML
	private TableColumn<?, ?> columnaId;

	@FXML
	private TableColumn<UbicacionDepositoDTO, String> columnaNombre;

	@FXML
	private TableColumn<UbicacionDepositoDTO, String> columnaDeposito;

	private ObservableList<UbicacionDepositoDTO> masterData = FXCollections.observableArrayList();

	@FXML
	private Button btnAgregarUbicacion;

	@FXML
	private Button btnEditarUbicacion;

	@FXML
	private Button btnEliminarUbicacion;

	private AdministracionService administracionService = new AdministracionService(new DAOSQLFactory());

	@FXML
	void agregarUbicacion(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/administracion/agregar_Ubicacion.fxml"));
			Scene scene = new Scene(root.load());
			ControladorAgregarUbicacion controlador = root.getController();
			controlador.setMasterData(masterData);
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
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

			e.printStackTrace();
		}
	}

	@FXML
	void borrarUbicacion(MouseEvent event) {
		if (!tablaUbicacion.getSelectionModel().isEmpty()) {
			if (Dialogos.confirmacion("Alerta", " Desea eliminar el Deposito ?", "", "SI", "NO")) {
				UbicacionDeposito ubicacionDeposito = new UbicacionDeposito();
				ubicacionDeposito.setIdUbicacionDeposito(masterData
						.get(tablaUbicacion.getSelectionModel().getSelectedIndex()).getIdUbicacionDeposito().get());
				administracionService.eliminarUbicacion(ubicacionDeposito);
				masterData.remove(masterData
						.get(tablaUbicacion.getSelectionModel().getSelectedIndex()));
				this.btnEditarUbicacion.setDisable(true);
				this.btnEliminarUbicacion.setDisable(true);
			}
		} else {
			Notifications.create().title("Atencion").text("Seleccione la Ubicación a Eliminar").darkStyle()
					.showWarning();
		}

	}

	@FXML
	void editarUbicacion(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/administracion/editar_Ubicacion.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);

			ControladorModificarUbicacion controlador = root.getController();
			controlador
					.cargarUbicacionAmodificar(masterData.get(tablaUbicacion.getSelectionModel().getSelectedIndex()));
			controlador.setMasterData(masterData);
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
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.btnEditarUbicacion.setDisable(true);
		this.btnEliminarUbicacion.setDisable(true);
		
		llenarTabla();
		
		tablaUbicacion.setOnMouseClicked((MouseEvent event) -> {
			if (btnEditarUbicacion != null) {
				if (event.getClickCount() > 0) {
					btnEditarUbicacion.setDisable(false);
					btnEliminarUbicacion.setDisable(false);
				} else {
					btnEditarUbicacion.setDisable(true);
					btnEliminarUbicacion.setDisable(false);
				}
			}
		});
	}
	
	public void llenarTabla()
	{
		masterData.clear();
		this.tablaUbicacion.getItems().clear();
		List<UbicacionDeposito> ubicaciones = administracionService.obtenerUbicacionDepositos();
		for (UbicacionDeposito ubicacionDeposito : ubicaciones) {
			UbicacionDepositoDTO ubicacionDepositoDTO = new UbicacionDepositoDTO();
			ubicacionDepositoDTO.setIdUbicacionDeposito(ubicacionDeposito.getIdUbicacionDeposito());
			ubicacionDepositoDTO.setNombre(ubicacionDeposito.getNombre());
			DepositoDTO deposito = new DepositoDTO();
			deposito.setIdDeposito(ubicacionDeposito.getDeposito().getIdDeposito());
			Deposito d = new Deposito();
			d.setIdDeposito(ubicacionDeposito.getDeposito().getIdDeposito());
			String nombreDeposito = administracionService.obtenerNombreDeposito(d);
			deposito.setNombre(nombreDeposito);
			ubicacionDepositoDTO.setDeposito(deposito);
			masterData.add(ubicacionDepositoDTO);
		}
		columnaNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
		columnaDeposito.setCellValueFactory(cellData -> cellData.getValue().getDeposito().getNombre());
		this.tablaUbicacion.getItems().addAll(masterData);
	}

}
