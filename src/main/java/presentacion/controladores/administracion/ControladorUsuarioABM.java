package presentacion.controladores.administracion;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import domain.model.Usuario;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import services.UsuarioService;
import util.Dialogos;

public class ControladorUsuarioABM implements Initializable {

	@FXML
	private ScrollPane panelUsuario;

	@FXML
	private TableView<Usuario> tablaUsuario;

	@FXML
	private TableColumn<Usuario, String> columnaNombre;

	@FXML
	private TableColumn<Usuario, String> columnaApellido;

	@FXML
	private TableColumn<Usuario, String> columnaUserName;

	@FXML
	private TableColumn<Usuario, String> columnaMail;

	@FXML
	private TableColumn<Usuario, String> columnaRecibeAlertas;
	
	@FXML
	private TableColumn<Usuario, String> columnaUserNameMantis;
	
	@FXML
	private Button btnAgregar;

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnEliminar;

	private ObservableList<Usuario> masterData = FXCollections.observableArrayList();

	private UsuarioService servicio = new UsuarioService(new DAOSQLFactory());

	Usuario usuarioAModificar;

	@FXML
	void agregar(MouseEvent event) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/administracion/agregar_Usuario.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);

			ControladorUsuarioAgregar controlador = root.getController();
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

	@FXML
	void editar(MouseEvent event) {
		FXMLLoader root;
		try {
			if (!tablaUsuario.getSelectionModel().isEmpty()) {
				root = new FXMLLoader(getClass().getResource("/vistas/administracion/modificar_Usuario.fxml"));
				Scene scene = new Scene(root.load());
				scene.setFill(Color.TRANSPARENT);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.TRANSPARENT);
				ControladorUsuarioModificar controlador = root.getController();
				usuarioAModificar = masterData.get(tablaUsuario.getSelectionModel().getSelectedIndex());
				controlador.cargarMailAmodificar(usuarioAModificar);
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
			} else {
				Notifications.create().title("Atencion").text("Seleccione el Usuario para Modificar").darkStyle()
						.showWarning();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void eliminar(MouseEvent event) {
		if (!tablaUsuario.getSelectionModel().isEmpty()) {
			if (Dialogos.confirmacion("Alerta", " Desea eliminar el Usuario ?", "", "SI", "NO")) {
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(
						masterData.get(tablaUsuario.getSelectionModel().getSelectedIndex()).getIdUsuario());
				servicio.eliminarUsuario(usuario);
				masterData.remove(masterData.get(tablaUsuario.getSelectionModel().getSelectedIndex()));
				this.btnEditar.setDisable(true);
				this.btnEliminar.setDisable(true);
			}
		} else {
			Notifications.create().title("Atencion").text("Seleccione el Usuario a Eliminar").darkStyle().showWarning();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		llenarTabla();
	}

	private void llenarTabla() {
		masterData.clear();
		this.tablaUsuario.getItems().clear();
		List<Usuario> usuarios = servicio.obtenerUsuario();
		for (Usuario usuario : usuarios) {
			masterData.add(usuario);
		}
		columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
		columnaUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
		columnaUserNameMantis.setCellValueFactory(new PropertyValueFactory<>("userMantis"));
		columnaMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
		columnaRecibeAlertas.setCellValueFactory(cellData -> cellData.getValue().getRecibeAlertasStringProperty());
		this.tablaUsuario.getItems().addAll(masterData);
	}

}
