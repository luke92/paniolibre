
package presentacion.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain.model.Usuario;
import domain.model.UsuarioLogueado;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AlertaService;
import services.UsuarioService;
import util.Dialogos;
import util.Email;
import util.Paths;

public class MenuPrincipalControlador implements Initializable {
	@FXML
	private BorderPane principal;
	@FXML
	private Button btnVolver;
	
	@FXML
	private Button btnAcercaDe;
	
	@FXML
	private ImageView imageMaximizar;
	@FXML
	private Separator separatorVolver;
	@FXML
	private VBox contenedorBarras;
	@FXML
	private Text txtRuta;
	@FXML
	private HBox barraPrincipal;
	@FXML
	private Button btnInsumos;
	@FXML
	private Button btnHerramientas;
	@FXML
	private Button btnOrdenesTrabajo;
	@FXML
	private HBox barraStage;
	@FXML
	private Label lblNombreSeccion;
	@FXML
	private Button btnBottonRight;
	@FXML
	private Button btnMinimizar;
	@FXML
	private Button btnMaximizar;
	@FXML
	private Button btnCerrar;
	@FXML
	private Button btnUsuario;
	@FXML
	private HBox HboxSeparador1;
	@FXML
	private HBox HboxSeparador2;
	private double x, y;
	@FXML
	private Button btnAdministracion;
	@FXML
	private AnchorPane AnchorSeparatorTop;
	@FXML
	private AnchorPane AnchorSeparatorBotton;
	@FXML
	private HBox HboxSeparadorLeft;
	@FXML
	private HBox HboxSeparadorRight;
	@FXML
	private Button btnBottonLeft;
	@FXML
	private Button btnTopRight;
	@FXML
	private Button btnTopLeft;
	private double initX, initY, initHeight, initWidth;
	@FXML
	private BorderPane VentanaPrincipal;
	@FXML
	private AnchorPane panel_login;
	@FXML
	private TextField textUsuario;
	@FXML
	private TextField textPass;
	@FXML
	private Button btnLogin;
	@FXML
	private ImageView imageUsuario;
	@FXML
	private ImageView imagePass;

	private boolean barraprincipalActiva;

	@FXML
	private Label lblAlertas;

	@FXML
	private Button btnAlertas;

	@FXML
	private Button btnConfiguracion;

	private Image imgTilde;
	private Image imgCrus;

	UsuarioService service = new UsuarioService(new DAOSQLFactory());

	private MenuItem itemCerrar;

	@FXML
	void abrirInsumos(MouseEvent event) {
		quitarBarraPrincipal();
		this.agregarBotonVolver();
		this.loadUI("/vistas/menuinsumos");
		lblNombreSeccion.setText(">Insumos");
	}

	@FXML
	void abrirHerramientas(MouseEvent event) {
		quitarBarraPrincipal();
		this.agregarBotonVolver();
		this.loadUI("/vistas/herramientas/menuherramientas");
		lblNombreSeccion.setText(">Herramientas");
	}

	@FXML
	void abrirOrdenesTrabajo(MouseEvent event) {
		quitarBarraPrincipal();
		this.agregarBotonVolver();
		this.loadUI("/vistas/menuordendetrabajo");
		barraStage.getChildren().get(0).setVisible(true);
		lblNombreSeccion.setText("\u00d3RDENES DE TRABAJO");
	}

	private void quitarBarraPrincipal() {
		this.contenedorBarras.getChildren().remove(barraPrincipal);
		this.contenedorBarras.getChildren().remove(this.HboxSeparador2);
		this.barraprincipalActiva = false;
	}

	@FXML
	void cerrar(MouseEvent event) {
		cerrarVentanaPrincipal();
	}

	@FXML
	void abrirAlertas(MouseEvent event) {
		getRoot("/vistas/administracion/verListaAlertas.fxml");
	}

	@FXML
	void volverMenuPrincipal(MouseEvent event) {
		Node center = principal.getCenter();
		ScaleTransition scale = new ScaleTransition(Duration.seconds(0.20), center);
		scale.setToX(0.60);
		scale.setToY(0.60);
		scale.play();
		scale.setOnFinished(e -> principal.setCenter(null));
		agregarBarraPrincipal();
		this.quitarBotonVolver();
		lblNombreSeccion.setText("");
	}

	private void agregarBarraPrincipal() {
		this.contenedorBarras.getChildren().add(barraPrincipal);
		this.contenedorBarras.getChildren().add(this.HboxSeparador2);
		this.barraprincipalActiva = true;
	}

	@FXML
	void abrirAdministracion(MouseEvent event) {
		quitarBarraPrincipal();
		this.agregarBotonVolver();
		this.loadUI("/vistas/administracion/menuadministracion");
		lblNombreSeccion.setText("ADMINISTRACI\u00D3N");
	}

	private void loadUI(String ui) {
		FXMLLoader root = null;
		try {
			ScaleTransition scale;
			root = new FXMLLoader(getClass().getResource(ui + ".fxml"));
			Scene scene = new Scene(root.load()); // (root)
			Node root2 = scene.getRoot();
			root2.setScaleX(0.80);
			root2.setScaleY(0.80);
			scale = new ScaleTransition(Duration.seconds(0.20), root2);
			scale.setToX(1);
			scale.setToY(1);
			scale.play();
			principal.setCenter(root2);
		} catch (IOException e) {
			Dialogos.error("Error cargando ventana", "", "Error para cargar la ventana solicitada");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		actualizarNotificacion();
		asignarEventoBtnCerrar();
		asignarEventoBtnMaximizar();
		asignarEventoBtnMinimizar();

		arrastrarVenta2(true);

		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		this.itemCerrar = new MenuItem("Cerrar");

		// Add MenuItem to ContextMenu
		contextMenu.getItems().add(itemCerrar);

		this.btnUsuario
				.setOnMouseClicked(event -> contextMenu.show(btnUsuario, event.getScreenX(), event.getScreenY()));

		this.quitarBotonVolver();
		this.redimecionar();
		this.quitarBarraPrincipal();
		this.quitarBotonUsuario();
		this.cargarImagenesUsuarios();

		asignarEventoTextUsuario();
		asignarEventoBtnLogin();
		asignarEventoItemCerrar();
		asignarEventoBtnConfiguracion();
		
		this.btnAcercaDe.setOnMouseClicked(event -> {
			FXMLLoader root;
			try {
				root = new FXMLLoader(getClass().getResource("/vistas/administracion/acerca_de.fxml"));
				Scene scene = new Scene(root.load());
				scene.setFill(Color.TRANSPARENT);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.show();

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
			
		});

	}

	private void asignarEventoBtnConfiguracion() {
		btnConfiguracion.setOnMouseClicked(event -> getRoot("/vistas/administracion/ventana_conexion.fxml"));
	}

	private FXMLLoader getRoot(String rutaFXML) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource(rutaFXML));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ESCAPE) {
						stage.close();
					}
				}
			});
		} catch (IOException e) {
			Dialogos.error("Error abriendo ventana", "", "No se puede abrir la ventana seleccionada");
			return null;
		}
		return root;
	}

	private void asignarEventoItemCerrar() {
		this.itemCerrar.setOnAction(event -> {
			if (principal.getCenter() != null) {
				Node center = principal.getCenter();
				ScaleTransition scale = new ScaleTransition(Duration.seconds(0.20), center);
				scale.setToX(0.60);
				scale.setToY(0.60);
				scale.play();
				scale.setOnFinished(e -> {
					principal.setCenter(null);
					this.quitarBotonVolver();
					lblNombreSeccion.setText("");
					panel_login.setScaleX(0.60);
					panel_login.setScaleY(0.60);
					ScaleTransition scale2 = new ScaleTransition(Duration.seconds(0.20), panel_login);
					scale2.setToX(1);
					scale2.setToY(1);
					scale2.play();
					principal.setCenter(panel_login);
				});
			} else {
				this.quitarBarraPrincipal();
				panel_login.setScaleX(0.60);
				panel_login.setScaleY(0.60);
				ScaleTransition scale2 = new ScaleTransition(Duration.seconds(0.20), panel_login);
				scale2.setToX(1);
				scale2.setToY(1);
				scale2.play();
				principal.setCenter(panel_login);
			}
			UsuarioLogueado usuariologueado = UsuarioLogueado.getInstancia();
			if (usuariologueado != null) {
				usuariologueado.desloguearUsuario();
				this.btnUsuario.setText("");
				this.quitarBotonUsuario();
			}
		});
	}

	private void asignarEventoBtnLogin() {
		this.btnLogin.setOnMouseClicked(event -> {
			UsuarioService serviceUsuario = new UsuarioService(new DAOSQLFactory());
			Usuario userLog = new Usuario();
			userLog.setUserName(textUsuario.getText());
			userLog.setClave(textPass.getText());
			if (serviceUsuario.validarUserName(userLog) && serviceUsuario.validarUserClave(userLog)) {
				Usuario usuario = serviceUsuario.obtenerUsuarioxUserName(userLog);
				FXMLLoader root = null;
				try {
					ScaleTransition scale;
					root = new FXMLLoader(getClass().getResource("/vistas/login/bienvenido.fxml"));
					Scene scene = new Scene(root.load()); // (root)
					ControladorBienvenida controlador = root.getController();
					controlador.getSaludo().setText("Bienvenido"+" "+userLog.getUserName());
					Node root2 = scene.getRoot();
					root2.setScaleX(0.80);
					root2.setScaleY(0.80);
					scale = new ScaleTransition(Duration.seconds(1), root2);
					scale.setToX(1);
					scale.setToY(1);
					scale.play();
					principal.setCenter(root2);
					UsuarioLogueado usuariologueado = UsuarioLogueado.logearUsuario(usuario);
					this.btnUsuario.setText(usuariologueado.getUsuarioLogueado().getUserName());

					scale.setOnFinished(event2 -> {
						scale.setToX(0.60);
						scale.setToY(0.60);
						scale.play();

						scale.setOnFinished(e -> {
							agregarBarraPrincipal();
							this.agregarBotonUsuario();
							this.principal.setCenter(null);
							this.btnMaximizar.setDisable(false);
						});
					});
					imagePass.setImage(null);
					textPass.setText("");
				} catch (IOException e) {
					Dialogos.error("Error Login", "", "Error en pantalla de login");
				}
			} else {
				if (imageUsuario.getImage() == null) {
					imageUsuario.setImage(imgCrus);
				}
				imagePass.setImage(imgCrus);
			}
		});
	}

	private void asignarEventoTextUsuario() {
		this.textUsuario.setOnKeyReleased(event -> {
			Usuario userLog = new Usuario();
			userLog.setUserName(textUsuario.getText());
			if (service.validarUserName(userLog)) {
				imageUsuario.setImage(imgTilde);
			} else {
				imageUsuario.setImage(null);
			}
		});
	}

	private void asignarEventoBtnMinimizar() {
		this.btnMinimizar.setOnMouseClicked(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			stage.setIconified(true);
		});
	}

	private void asignarEventoBtnMaximizar() {
		this.btnMaximizar.setOnMouseClicked(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.isMaximized()) {
				stage.setMaximized(false);
				this.arrastrarVenta2(true);
				Image imgEdit = new Image(getClass().getResourceAsStream("/iconos/maximize_verde.png"));
				this.redimencionarSetDisable(false);
				this.imageMaximizar.setImage(imgEdit);
			} else {
				stage.setMaximized(true);
				this.arrastrarVenta2(false);
				Image imgEdit = new Image(getClass().getResourceAsStream("/iconos/maximizar.png"));
				this.redimencionarSetDisable(true);
				this.imageMaximizar.setImage(imgEdit);
			}
		});
	}

	private void asignarEventoBtnCerrar() {
		this.btnCerrar.setOnMouseClicked(event -> {
			if (Dialogos.confirmacion("Cerrando aplicacion", "Realizar respaldo del Sistema",
					"Desea realizar un respaldo automatico del sistema?", "SI", "NO")) {
				Conexion conexion = Conexion.getConexion();
				conexion.backup(Paths.respaldos() + "\\" + Paths.nombreArchivoBackupActual());
			}
			Paths.borrarArchivoRestoreBat();
			cerrarVentanaPrincipal();
			MensajeMail.enviarMail();
		});
	}

	private void actualizarNotificacion() {
		Timeline timer = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
			int numeroAlertas = 0;

			numeroAlertas = new AlertaService(new DAOSQLFactory()).obtenerCantidadAlertas();

			if (numeroAlertas == 0) {
				lblAlertas.setVisible(false);
			} else {
				if (numeroAlertas > 99) {
					lblAlertas.setVisible(true);
					lblAlertas.setText("99+");
				} else {
					lblAlertas.setVisible(true);
					lblAlertas.setText(Integer.toString(numeroAlertas));
				}
			}
			actualizarNotificacion();
		}));
		timer.play();
	}

	private void redimecionar() {
		this.agrandarVentanaTop();
		this.agrandarVentanaBotton();
		this.agrandarVentanaRight();
		this.agrandarVentanaleft();
		this.agrandarVentanaBottonRight();
		this.agrandarVentanaBottonLeft();
		this.agrandarVentanaTopLeft();
		this.agrandarVentanaTopRigth();
	}

	private void arrastrarVenta2(boolean arrastrar) {

		if (arrastrar) {
			barraStage.setOnMousePressed(event -> {
				Stage stage = (Stage) principal.getScene().getWindow();
				initX = event.getSceneX();
				initY = event.getSceneY();
				VentanaPrincipal.setOpacity(0.7);
			});
			barraStage.setOnMouseDragged(event -> {
				Stage stage = (Stage) principal.getScene().getWindow();
				stage.setX(event.getScreenX() - initX);
				stage.setY(event.getScreenY() - initY);

			});
		} else {
			barraStage.setOnMousePressed(event -> {
			});
			barraStage.setOnMouseDragged(event -> {
			});
		}
		barraStage.setOnMouseReleased(event -> VentanaPrincipal.setOpacity(1.0));
	}

	private void quitarBotonVolver() {
		this.barraStage.getChildren().remove(this.btnVolver);
		this.barraStage.getChildren().remove(this.separatorVolver);
	}

	private void agregarBotonVolver() {
		this.barraStage.getChildren().add(0, this.btnVolver);
		this.barraStage.getChildren().add(1, this.separatorVolver);
	}

	private void agrandarVentanaTop() {
		AnchorSeparatorTop.setOnMousePressed(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			initY = stage.getY();
			initHeight = stage.getHeight();
		});

		AnchorSeparatorTop.setOnMouseDragged(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() < 600) {
				stage.setHeight(600);
				AnchorSeparatorTop.setOnMouseDragged(null);
			} else {
				stage.setHeight(initHeight - (event.getScreenY() - initY));
				stage.setY(event.getScreenY());
			}
		});

		AnchorSeparatorTop.setOnMouseReleased(event -> {
			AnchorSeparatorTop.setOnMouseDragged(event2 -> {
				Stage stage = (Stage) principal.getScene().getWindow();
				if (stage.getHeight() < 600) {
					stage.setHeight(600);
					AnchorSeparatorTop.setOnMouseDragged(null);
				} else {
					stage.setHeight(initHeight - (event2.getScreenY() - initY));
					stage.setY(event2.getScreenY());
				}

			});
		});
	}

	private void agrandarVentanaBotton() {
		AnchorSeparatorBotton.setOnMousePressed(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			initHeight = stage.getHeight();
		});

		AnchorSeparatorTop.setOnMouseDragged(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() < 600) {
				stage.setHeight(600);
				AnchorSeparatorTop.setOnMouseDragged(null);
			} else {
				stage.setHeight(initHeight - (event.getScreenY() - initY));
				stage.setY(event.getScreenY());
			}

		});

		AnchorSeparatorTop.setOnMouseReleased(event -> {
			AnchorSeparatorTop.setOnMouseDragged(event2 -> {
				Stage stage = (Stage) principal.getScene().getWindow();
				if (stage.getHeight() < 600) {
					stage.setHeight(600);
					AnchorSeparatorTop.setOnMouseDragged(null);
				} else {
					stage.setHeight(initHeight - (event2.getScreenY() - initY));
					stage.setY(event2.getScreenY());
				}
			});
		});
	}

	private void agrandarVentanaRight() {
		HboxSeparadorRight.setOnMousePressed(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			initWidth = stage.getWidth();
			initX = stage.getX();
		});
		HboxSeparadorRight.setOnMouseDragged(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getWidth() < 700) {
				stage.setWidth(800);
				HboxSeparadorRight.setOnMouseDragged(null);
			} else {
				stage.setWidth((event.getScreenX() - initX));
			}
		});
		HboxSeparadorRight.setOnMouseReleased(event2 -> {
			HboxSeparadorRight.setOnMouseDragged(event -> {
				Stage stage = (Stage) principal.getScene().getWindow();
				if (stage.getWidth() < 700) {
					stage.setWidth(800);
					HboxSeparadorRight.setOnMouseDragged(null);
				} else {
					stage.setWidth((event.getScreenX() - initX));
				}
			});
		});

	}

	private void agrandarVentanaleft() {
		HboxSeparadorLeft.setOnMousePressed(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			initWidth = stage.getWidth();
			initX = stage.getX();
		});
		HboxSeparadorLeft.setOnMouseDragged(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getWidth() < 800) {
				stage.setWidth(800);
				HboxSeparadorLeft.setOnMouseDragged(null);
			} else {
				stage.setWidth(initWidth + (initX - event.getScreenX()));
				stage.setX(event.getScreenX());
			}
		});
		HboxSeparadorLeft.setOnMouseReleased(event2 -> {
			HboxSeparadorLeft.setOnMouseDragged(event -> {
				Stage stage = (Stage) principal.getScene().getWindow();
				if (stage.getWidth() < 800) {
					stage.setWidth(800);
					HboxSeparadorLeft.setOnMouseDragged(null);
				} else {
					stage.setWidth(initWidth + (initX - event.getScreenX()));
					stage.setX(event.getScreenX());
				}

			});
		});
	}

	private void agrandarVentanaBottonRight() {

		btnBottonRight.setOnMousePressed(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			initHeight = stage.getHeight();
			initY = stage.getY();
			initWidth = stage.getWidth();
			initX = stage.getX();
		});

		btnBottonRight.setOnMouseDragged(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() > 500 || stage.getWidth() > 700) {
				stage.setHeight((event.getScreenY() - initY));
				stage.setWidth((event.getScreenX() - initX));
			}
		});

		btnBottonRight.setOnMouseReleased(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() < 500 || stage.getWidth() < 700) {
				stage.setWidth(800);
				stage.setHeight(600);
			}
		});
	}

	private void agrandarVentanaBottonLeft() {

		btnBottonLeft.setOnMousePressed(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			initHeight = stage.getHeight();
			initY = stage.getY();
			initWidth = stage.getWidth();
			initX = stage.getX();
		});

		btnBottonLeft.setOnMouseDragged(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() > 500 || stage.getWidth() > 700) {
				stage.setWidth(initWidth + (initX - event.getScreenX()));
				stage.setX(event.getScreenX());
				stage.setHeight((event.getScreenY() - initY));
			}
		});

		btnBottonLeft.setOnMouseReleased(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() < 500 || stage.getWidth() < 700) {
				stage.setWidth(800);
				stage.setHeight(600);
			}
		});
	}

	private void agrandarVentanaTopLeft() {
		btnTopLeft.setOnMousePressed(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			initHeight = stage.getHeight();
			initY = stage.getY();
			initWidth = stage.getWidth();
			initX = stage.getX();
		});

		btnTopLeft.setOnMouseDragged(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() > 500 || stage.getWidth() > 700) {
				stage.setHeight(initHeight - (event.getScreenY() - initY));
				stage.setWidth(initWidth + (initX - event.getScreenX()));
				stage.setY(event.getScreenY());
				stage.setX(event.getScreenX());
			}

		});

		btnTopLeft.setOnMouseReleased(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() < 500 || stage.getWidth() < 700) {
				stage.setWidth(800);
				stage.setHeight(600);
			}
		});
	}

	private void agrandarVentanaTopRigth() {
		btnTopRight.setOnMousePressed(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			initHeight = stage.getHeight();
			initY = stage.getY();
			initWidth = stage.getWidth();
			initX = stage.getX();
		});

		btnTopRight.setOnMouseDragged(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() > 500 || stage.getWidth() > 700) {
				stage.setWidth((event.getScreenX() - initX));
				stage.setHeight(initHeight - (event.getScreenY() - initY));
				stage.setY(event.getScreenY());
			}
		});

		btnTopRight.setOnMouseReleased(event -> {
			Stage stage = (Stage) principal.getScene().getWindow();
			if (stage.getHeight() < 500 || stage.getWidth() < 700) {
				stage.setWidth(800);
				stage.setHeight(600);
			}
		});
	}

	private void redimencionarSetDisable(boolean deshabilitado) {
		this.btnBottonLeft.setDisable(deshabilitado);
		this.btnBottonRight.setDisable(deshabilitado);
		this.btnTopLeft.setDisable(deshabilitado);
		this.btnTopRight.setDisable(deshabilitado);
		this.AnchorSeparatorBotton.setDisable(deshabilitado);
		this.AnchorSeparatorTop.setDisable(deshabilitado);
		this.HboxSeparadorLeft.setDisable(deshabilitado);
		this.HboxSeparadorRight.setDisable(deshabilitado);
	}

	private void quitarBotonUsuario() {
		this.barraStage.getChildren().remove(btnUsuario);
	}

	private void agregarBotonUsuario() {
		this.barraStage.getChildren().add(5, btnUsuario);
	}

	private void cargarImagenesUsuarios() {
		imgTilde = new Image(getClass().getResourceAsStream("/iconos/marca-de-verificacion.png"));
		imgCrus = new Image(getClass().getResourceAsStream("/iconos/cerrar-cruz-en-boton-de-interfaz-circular(1).png"));
	}

	private void cerrarVentanaPrincipal() {
		Stage stage = (Stage) principal.getScene().getWindow();
		stage.close();
	}

}