package presentacion.controladores.insumos;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import domain.model.Insumo;
import dto.DepositoDTO;
import dto.InsumoDTO;
import dto.InsumoDepositoDTO;
import dto.UbicacionDepositoDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AdministracionService;
import services.DepositoService;
import services.InsumoDepositoService;
import services.InsumoService;
import util.Dialogos;

public class ControladorUbicacionInsumos implements Initializable {

	@FXML
	private BorderPane borderPrincipal;

	@FXML
	private BorderPane ventanaUbicacion;

	@FXML
	private AnchorPane barraPrincipal;

	@FXML
	private Text lblTitulo;

	@FXML
	private Button btnDescartar;

	@FXML
	private HBox hboxSeparador1;

	@FXML
	private HBox hboxSeparador11;

	@FXML
	private HBox hboxSeparador111;

	@FXML
	private HBox hboxSeparador112;

	@FXML
	private TextField txtIdInsumo;

	@FXML
	private TextField txtCodigoInsumo;

	@FXML
	private Button btnBuscarInsumo;

	@FXML
	private Button btnBuscarUbicacion;

	@FXML
	private ComboBox<DepositoDTO> chbDeposito;

	@FXML
	private ComboBox<UbicacionDepositoDTO> chbUbicacion;

	@FXML
	private Button btnAsignarUbicacion;

	@FXML
	private HBox hboxSeparadorLeft;

	@FXML
	private HBox hboxSeparadorLeft1;

	private DAOAbstractFactory dao = new DAOSQLFactory();
	private DepositoService depositoService = new DepositoService(dao);
	private AdministracionService administracionService = new AdministracionService(dao);
	private InsumoDepositoService insumoDepositoService = new InsumoDepositoService(dao);
	private InsumoService insumoService = new InsumoService(dao);

	private InsumoDepositoDTO insumo;
	private List<UbicacionDepositoDTO> ubicaciones;
	private boolean esVentanaAgregar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inicializarComboBoxs();
		btnDescartar.setOnMouseClicked(event -> cerrarVentana());
		btnAsignarUbicacion.setOnMouseClicked(event -> asignarUbicacion());
		btnBuscarInsumo.setOnMouseClicked(event -> buscarInsumo());
		btnBuscarUbicacion.setOnMouseClicked(event -> buscarUbicacion());
		chbDeposito.setOnAction(event -> depositoSeleccionado());
	}

	private void depositoSeleccionado() {
		chbUbicacion.getItems().clear();
		for (UbicacionDepositoDTO ubicacion : ubicaciones) {
			if (obtenerDepositoSeleccionado().getIdDeposito().get() == ubicacion.getDeposito().getIdDeposito().get()) {
				chbUbicacion.getItems().add(ubicacion);
			}
		}
	}

	private void inicializarComboBoxs() {
		chbDeposito.getItems().clear();
		chbUbicacion.getItems().clear();
		List<DepositoDTO> depositos;
		depositos = depositoService.obtenerDepositosDTO();
		ubicaciones = administracionService.obtenerUbicacionDepositosDTO();
		chbDeposito.getItems().addAll(depositos);
	}

	private void buscarUbicacion() {
		FXMLLoader root = fxmlLoaderRoot("/vistas/insumos/verListaUbicaciones.fxml");
		if (root != null) {
			ControladorListaUbicaciones controlador = root.getController();
			UbicacionDepositoDTO ubicacionDTO = controlador.obtenerUbicacion();
			seleccionarUbicacion(ubicacionDTO);
		}
	}

	private void buscarInsumo() {
		FXMLLoader root = fxmlLoaderRoot("/vistas/insumos/verListaInsumosMaestros.fxml");
		if (root != null) {
			ControladorListaInsumosMaestros controlador = root.getController();
			InsumoDTO insumoDTO = controlador.obtenerInsumo();
			txtIdInsumo.setText(String.valueOf(insumoDTO.getIdInsumo().get()));
			txtCodigoInsumo.setText(insumoDTO.getCodigo().get());
		}
	}

	private void asignarUbicacion() {

		obtenerIdInsumo();

		if (txtIdInsumo.getText().trim().isEmpty() || obtenerDepositoSeleccionado() == null
				|| obtenerUbicacionSeleccionada() == null) {
			Dialogos.advertencia("Asignar Ubicacion", "",
					"Debe ingresar o elegir el insumo, el deposito, y ubicacion donde sera ubicado");
			return;
		}

		UbicacionDepositoDTO ubicacionNueva = obtenerUbicacionSeleccionada();
		InsumoDTO insumoDTO = new InsumoDTO();
		insumoDTO.setIdInsumo(Integer.parseInt(txtIdInsumo.getText()));
		insumo.setInsumo(insumoDTO);
		if (esVentanaAgregar) {
			insumo.setUbicacion(ubicacionNueva);
			if (insumoDepositoService.agregarInsumoDeposito(insumo)) {
				Dialogos.informacion("Asignando Insumo en nueva Ubicacion",
						"Se ha agregado correctamente el insumo en la ubicacion seleccionada");
				cerrarVentana();
			} else {
				Dialogos.error("Error", "Hubo un problema al asignarle una ubicacion al insumo",
						"No se puede asignar una ubicacion nueva al insumo");
			}
		} else if (insumoDepositoService.cambiarUbicacionInsumoDeposito(insumo, ubicacionNueva)) {
			Dialogos.informacion("Modificando Insumo en nueva Ubicacion",
					"Se ha modificado correctamente la ubicacion del insumo");
			cerrarVentana();
		} else {
			Dialogos.error("Error", "Hubo un problema al modificar la ubicacion del insumo",
					"No se puede modificar la ubicacion del insumo");
		}
	}

	private void cerrarVentana() {
		Stage stage = (Stage) ventanaUbicacion.getScene().getWindow();
		stage.close();
	}

	public void prepararVentanaAgregar() {
		lblTitulo.setText("Asignar Ubicaci\u00f3n");
		insumo = new InsumoDepositoDTO();
		habilitarCamposInsumo();
		esVentanaAgregar = true;
	}

	public void prepararVentanaModificar(InsumoDepositoDTO insumo) {
		this.insumo = insumo;
		lblTitulo.setText("Modificar Ubicaci\u00f3n");
		txtIdInsumo.setText(String.valueOf(insumo.getInsumo().getIdInsumo().get()));
		txtCodigoInsumo.setText(insumo.getInsumo().getCodigo().get());
		seleccionarUbicacion(insumo.getUbicacion());
		esVentanaAgregar = false;
	}
	
	private void seleccionarUbicacion(UbicacionDepositoDTO ubicacionDTO)
	{
		chbDeposito.getSelectionModel().select(ubicacionDTO.getDeposito());
		depositoSeleccionado();
		chbUbicacion.getSelectionModel().select(ubicacionDTO);
	}

	private DepositoDTO obtenerDepositoSeleccionado() {
		return chbDeposito.getSelectionModel().getSelectedItem();
	}

	private UbicacionDepositoDTO obtenerUbicacionSeleccionada() {
		return chbUbicacion.getSelectionModel().getSelectedItem();
	}

	private void obtenerIdInsumo() {
		if (txtIdInsumo.getText().isEmpty() && !txtCodigoInsumo.getText().trim().isEmpty()) {
			Insumo insumoModel = new Insumo();
			insumoModel.setCodigoInsumo(txtCodigoInsumo.getText().trim());
			int idInsumo = insumoService.obtenerIdInsumo(insumoModel);
			txtIdInsumo.setText(String.valueOf(idInsumo));
		}
	}

	private void habilitarCamposInsumo() {
		btnBuscarInsumo.setDisable(false);
		txtCodigoInsumo.setDisable(false);
	}

	private FXMLLoader fxmlLoaderRoot(String rutaFXML) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource(rutaFXML));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.initModality(Modality.APPLICATION_MODAL);
			// Eventos del teclado
			scene.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.ESCAPE) {
					stage.close();
				}
			});
			stage.showAndWait();
			return root;
		} catch (IOException e) {
			return null;
		}
	}

}
